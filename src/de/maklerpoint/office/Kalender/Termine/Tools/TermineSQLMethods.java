/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/03 13:10
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Kalender.Termine.Tools;

import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TermineSQLMethods {

    public static int insertIntoTermine(Connection con, TerminObj termin) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO termine (creatorId, pub, beschreibung, ort, tag, kundeKennung, "
                + "versichererId, vertragId, benutzerId, stoerfallId, schadenId, teilnehmer, "
                + "erinnerung, start, ende, created, modified, status"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, termin.getCreatorId());
        statement.setBoolean(2, termin.isPublic());
        statement.setString(3, termin.getBeschreibung());
        statement.setString(4, termin.getOrt());
        statement.setString(5, termin.getTag());
        statement.setString(6, termin.getKundeKennung());
        statement.setInt(7, termin.getVersichererId());
        statement.setInt(8, termin.getVertragId());
        statement.setInt(9, termin.getBenutzerId());
        statement.setInt(10, termin.getStoerfallId());
        statement.setInt(11, termin.getSchadenId());
        statement.setString(12, termin.getTeilnehmer());
        statement.setTimestamp(13, termin.getErinnerung());
        statement.setTimestamp(14, termin.getStart());
        statement.setTimestamp(15, termin.getEnde());
        statement.setTimestamp(16, termin.getCreated());
        statement.setTimestamp(17, termin.getLastmodified());
        statement.setInt(18, termin.getStatus());
        statement.execute();
        ResultSet auto = statement.getGeneratedKeys();

        if (auto.next()) {
            generatedId = auto.getInt(1);
        } else {
            generatedId = -1;
        }

        statement.close();
        con.close();
        return generatedId;
    }

    /**
     * Java method that updates a row in the generated sql table
     * @param con (open java.sql.Connection)
     * @param creatorId
     * @param pub
     * @param beschreibung
     * @param ort
     * @param tag
     * @param kundeKennung
     * @param versichererId
     * @param vertragId
     * @param benutzerId
     * @param stoerfallId
     * @param schadenId
     * @param teilnehmer
     * @param erinnerung
     * @param start
     * @param ende
     * @param created
     * @param lastmodified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateTermine(Connection con, TerminObj termin) throws SQLException {
        String sql = "SELECT * FROM termine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, termin.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("creatorId", termin.getCreatorId());
        entry.updateBoolean("pub", termin.isPublic());

        entry.updateString("beschreibung", termin.getBeschreibung());

        entry.updateString("ort", termin.getOrt());

        entry.updateString("tag", termin.getTag());

        entry.updateString("kundeKennung", termin.getKundeKennung());

        entry.updateInt("versichererId", termin.getVersichererId());
        entry.updateInt("vertragId", termin.getVertragId());
        entry.updateInt("benutzerId", termin.getBenutzerId());
        entry.updateInt("stoerfallId", termin.getStoerfallId());
        entry.updateInt("schadenId", termin.getSchadenId());

        entry.updateString("teilnehmer", termin.getTeilnehmer());
        entry.updateTimestamp("erinnerung", termin.getErinnerung());

        entry.updateTimestamp("start", termin.getStart());

        entry.updateTimestamp("ende", termin.getEnde());

        entry.updateTimestamp("created", termin.getCreated());

        entry.updateTimestamp("lastmodified", termin.getLastmodified());

        entry.updateInt("status", termin.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException
     */
    public static void deleteFromTermine(Connection con, int keyId) throws SQLException {
        String sql = "SELECT id, status FROM termine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return;
        }

        entry.next();

        entry.updateInt("status", Status.DELETED);
        entry.updateRow();

        entry.close();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param termin
     * @return
     * @throws SQLException
     */
    public static boolean updateTermin(Connection con, TerminObj termin) throws SQLException {
        
        String sql = "SELECT * FROM termine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, termin.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return false;
        }

        entry.next();

        entry.updateInt("besitzer", termin.getBesitzer());
        entry.updateBoolean("pub", termin.isPublic());
        entry.updateString("beschreibung", termin.getBeschreibung());
        entry.updateString("ort", termin.getOrt());
        entry.updateString("tag", termin.getTag());
        entry.updateString("kundeKennung", termin.getKundeKennung());
        entry.updateInt("versichererId", termin.getVersichererId());
        entry.updateString("teilnehmer", termin.getTeilnehmer());

        entry.updateTimestamp("erinnerung", termin.getErinnerung());
        entry.updateTimestamp("start", termin.getStart());
        entry.updateTimestamp("ende", termin.getEnde());
        entry.updateTimestamp("modified", termin.getLastmodified());
        entry.updateInt("status", termin.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();

        return true;
    }

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromTermine(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM termine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param besitzer
     * @param _public
     * @return
     * @throws SQLException
     */
    public static TerminObj[] loadTermine(Connection con, int besitzer, boolean _public) throws SQLException {
        TerminObj[] termine = null;
        String sql = null;

        if (_public) {
            sql = "SELECT * FROM termine WHERE (creatorId = ? OR pub = ?) AND status = 0";
        } else {
            sql = "SELECT * FROM termine WHERE creatorId = ? AND status = 0";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, besitzer);

        if (_public) {
            statement.setBoolean(2, true);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        termine = new TerminObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            termine[i] = getTerminEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return termine;
    }

    /**
     * 
     * @param con
     * @param kennung
     * @return
     * @throws SQLException
     */
    public static TerminObj[] loadTermine(Connection con, String kennung) throws SQLException {
        TerminObj[] termine = null;
        String sql = null;

        sql = "SELECT * FROM termine WHERE kundeKennung = ? AND status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        termine = new TerminObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            termine[i] = getTerminEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return termine;
    }

    public static TerminObj getTerminEntry(ResultSet entry) throws SQLException {
        TerminObj tm = new TerminObj();

        tm.setBesitzer(entry.getInt("creatorId"));
        tm.setPublic(entry.getBoolean("pub"));
        tm.setBeschreibung(entry.getString("beschreibung"));
        tm.setOrt(entry.getString("ort"));
        tm.setTag(entry.getString("tag"));
        tm.setKundeKennung(entry.getString("kundeKennung"));
        tm.setVersichererId(entry.getInt("versichererId"));
        tm.setVertragId(entry.getInt("vertragId"));
        tm.setBenutzerId(entry.getInt("benutzerId"));
        tm.setStoerfallId(entry.getInt("stoerfallId"));
        tm.setSchadenId(entry.getInt("schadenId"));
        tm.setTeilnehmer(entry.getString("teilnehmer"));
        tm.setErinnerung(entry.getTimestamp("erinnerung"));
        tm.setStart(entry.getTimestamp("start"));
        tm.setEnde(entry.getTimestamp("ende"));
        tm.setCreated(entry.getTimestamp("created"));
        tm.setLastmodified(entry.getTimestamp("modified"));
        tm.setStatus(entry.getInt("status"));

        return tm;
    }

    public static TerminObj[] loadVersichererTermine(Connection con, int versid) throws SQLException {
        TerminObj[] termine = null;
        String sql = null;

        sql = "SELECT * FROM termine WHERE versichererId = ? AND status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, versid);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        termine = new TerminObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            termine[i] = getTerminEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return termine;
    }
}
