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
package de.maklerpoint.office.Kalender.Aufgaben.Tools;

import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class AufgabenSQLMethods {

    public static int insertIntoAufgaben(Connection con, AufgabenObj aufgabe)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO aufgaben (creatorId, pub, beschreibung, tag, kundeKennung, versId, "
                + "vertragId, stoerfallId, schadenId, benutzerId, start, ende, "
                + "created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, aufgabe.getCreatorId());
        statement.setBoolean(2, aufgabe.isPublic());
        statement.setString(3, aufgabe.getBeschreibung());
        statement.setString(4, aufgabe.getTag());
        statement.setString(5, aufgabe.getKundenKennung());
        statement.setInt(6, aufgabe.getVersId());
        statement.setInt(7, aufgabe.getVertragId());
        statement.setInt(8, aufgabe.getStoerfallId());
        statement.setInt(9, aufgabe.getSchadenId());
        statement.setInt(10, aufgabe.getBenutzerId());
        
//        System.out.println("BenutzerId: " + aufgabe.getBenutzerId());
        
        statement.setTimestamp(11, aufgabe.getStart());
        statement.setTimestamp(12, aufgabe.getEnde());
        statement.setTimestamp(13, aufgabe.getCreated());
        statement.setTimestamp(14, aufgabe.getModified());
        statement.setInt(15, aufgabe.getStatus());
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

    public static boolean updateAufgaben(Connection con, AufgabenObj aufgabe) throws SQLException {
        String sql = "SELECT * FROM aufgaben WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, aufgabe.getId());
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

        entry.updateInt("creatorId", aufgabe.getCreatorId());
        entry.updateBoolean("pub", aufgabe.isPublic());

        entry.updateString("beschreibung", aufgabe.getBeschreibung());

        entry.updateString("tag", aufgabe.getTag());

        entry.updateString("kundeKennung", aufgabe.getKundenKennung());

        entry.updateInt("versId", aufgabe.getVersId());
        entry.updateInt("vertragId", aufgabe.getVertragId());
        entry.updateInt("stoerfallId", aufgabe.getStoerfallId());
        entry.updateInt("schadenId", aufgabe.getSchadenId());
        entry.updateInt("benutzerId", aufgabe.getBenutzerId());

        entry.updateTimestamp("start", aufgabe.getStart());

        entry.updateTimestamp("ende", aufgabe.getEnde());

        entry.updateTimestamp("created", aufgabe.getCreated());

        entry.updateTimestamp("modified", aufgabe.getModified());

        entry.updateInt("status", aufgabe.getStatus());

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
    public static void deleteEngueltigFromAufgaben(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM Aufgaben WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static AufgabenObj getResultEntry(ResultSet entry) throws SQLException {
        AufgabenObj aufgabe = new AufgabenObj();

        aufgabe = new AufgabenObj(entry.getInt("id"));
        aufgabe.setCreatorId(entry.getInt("creatorId"));
        aufgabe.setPublic(entry.getBoolean("pub"));
        aufgabe.setBeschreibung(entry.getString("beschreibung"));
        aufgabe.setTag(entry.getString("tag"));
        aufgabe.setKundenKennung(entry.getString("kundeKennung"));

        aufgabe.setVersId(entry.findColumn("versId"));
        aufgabe.setVertragId(entry.findColumn("vertragId"));
        aufgabe.setStoerfallId(entry.findColumn("stoerfallId"));
        aufgabe.setSchadenId(entry.findColumn("schadenId"));
        aufgabe.setBenutzerId(entry.findColumn("benutzerId"));

        aufgabe.setStart(entry.getTimestamp("start"));
        aufgabe.setEnde(entry.getTimestamp("ende"));
        aufgabe.setCreated(entry.getTimestamp("created"));
        aufgabe.setModified(entry.getTimestamp("modified"));
        aufgabe.setStatus(entry.getInt("status"));

        return aufgabe;
    }
    
    
    public static AufgabenObj[] loadEigeneAufgaben(Connection con, int benutzer, boolean _public, int status) throws SQLException {
        AufgabenObj[] aufgaben = null;
        String sql = null;

        if (_public) {
            sql = "SELECT * FROM aufgaben WHERE (creatorId = ? OR pub = ?)";
        } else {
            sql = "SELECT * FROM aufgaben WHERE creatorId = ?";
        }

        if (status != -1) {
            sql = sql + " AND STATUS = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, benutzer);

        if (_public) {
            statement.setBoolean(2, true);
            if (status != -1) {
                statement.setInt(3, status);
            }
        } else {
            if (status != -1) {
                statement.setInt(2, status);
            }
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        aufgaben = new AufgabenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            aufgaben[i] = AufgabenSQLMethods.getResultEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return aufgaben;
    }

    /**
     * 
     * @param con
     * @param besitzer
     * @param _public
     * @return
     * @throws SQLException
     */
    public static AufgabenObj[] loadBenutzerAufgaben(Connection con, int benutzer, boolean _public, int status) throws SQLException {
        AufgabenObj[] aufgaben = null;
        String sql = null;

        if (_public) {
            sql = "SELECT * FROM aufgaben WHERE (benutzerId = ? OR pub = ?)";
        } else {
            sql = "SELECT * FROM aufgaben WHERE benutzerId = ?";
        }

        if (status != -1) {
            sql = sql + " AND STATUS = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, benutzer);

        if (_public) {
            statement.setBoolean(2, true);
            if (status != -1) {
                statement.setInt(3, status);
            }
        } else {
            if (status != -1) {
                statement.setInt(2, status);
            }
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        aufgaben = new AufgabenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            aufgaben[i] = AufgabenSQLMethods.getResultEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return aufgaben;
    }

    /**
     * 
     * @param con
     * @param kennung
     * @return
     * @throws SQLException
     */
    public static AufgabenObj[] loadKundenAufgaben(Connection con, String kennung, int status) throws SQLException {
        AufgabenObj[] aufgaben = null;
        String sql = null;

        sql = "SELECT * FROM aufgaben WHERE kundeKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM aufgaben WHERE kundeKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);

        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        aufgaben = new AufgabenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            aufgaben[i] = AufgabenSQLMethods.getResultEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return aufgaben;
    }
    
    public static AufgabenObj getAufgabe(Connection con, int id) throws SQLException {
        AufgabenObj aufgabe = null;
        String sql = "SELECT * FROM aufgaben WHERE id = ?";


        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        aufgabe = AufgabenSQLMethods.getResultEntry(entry);

        statement.close();
        con.close();

        return aufgabe;
    }
    
    /**
     * 
     * @param con
     * @param versid
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static AufgabenObj[] loadVersichererAufgaben(Connection con, int versid, int status) throws SQLException {
        AufgabenObj[] aufgaben = null;
        String sql = null;

        sql = "SELECT * FROM aufgaben WHERE versId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM aufgaben WHERE versId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, versid);

        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        aufgaben = new AufgabenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            aufgaben[i] = AufgabenSQLMethods.getResultEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return aufgaben;
    }
}
