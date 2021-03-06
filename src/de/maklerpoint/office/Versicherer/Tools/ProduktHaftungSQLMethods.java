/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 13:15:19
 *  File:       ProduktHaftungSQLMethods
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Versicherer.Tools;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Versicherer.Produkte.ProduktHaftungObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ProduktHaftungSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param ProduktHaftung
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoProdukte_haftung(Connection con, ProduktHaftungObj ph) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO produkte_haftung (produkt, haftungZeit, haftungMonate, haftungFormel, haftungsart, ratierlich, "
                + "ratierlichIntervalle, ratierlichTabelle, kombiniert, kombiniertVollhaftungsZeit, "
                + "kombiniertRatierlich, created, modified, "
                + "status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);        
        statement.setInt(1, ph.getProdukt());
        statement.setInt(2, ph.getHaftungZeit());
        statement.setInt(3, ph.getHaftungMonate());
        statement.setString(4, ph.getHaftungFormel());
        statement.setInt(5, ph.getHaftungsart());
        statement.setInt(6, ph.getRatierlich());
        statement.setInt(7, ph.getRatierlichIntervalle());
        statement.setInt(8, ph.getRatierlichTabelle());
        statement.setInt(9, ph.getKombiniert());
        statement.setInt(10, ph.getKombiniertVollhaftungsZeit());
        statement.setInt(11, ph.getKombiniertRatierlich());
        statement.setTimestamp(12, ph.getCreated());
        statement.setTimestamp(13, ph.getModified());
        statement.setInt(14, ph.getStatus());
        
        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("insertIntoProdukte_haftung Statement: " + statement.toString());
        }
        
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
     * @param ProduktHaftungObj
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateProdukte_haftung(Connection con, ProduktHaftungObj ph) throws SQLException {
        String sql = "SELECT * FROM produkte_haftung WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, ph.getId());

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("updateProdukte_haftung Statement: " + statement.toString());
        }

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

        entry.updateInt("produkt", ph.getProdukt());
        entry.updateInt("haftungZeit", ph.getHaftungZeit());
        entry.updateInt("haftungMonate", ph.getHaftungMonate());
        entry.updateString("haftungFormel", ph.getHaftungFormel());
        entry.updateInt("haftungsart", ph.getHaftungsart());
        entry.updateInt("ratierlich", ph.getRatierlich());
        entry.updateInt("ratierlichIntervalle", ph.getRatierlichIntervalle());
        entry.updateInt("ratierlichTabelle", ph.getRatierlichTabelle());
        entry.updateInt("kombiniert", ph.getKombiniert());
        entry.updateInt("kombiniertVollhaftungsZeit", ph.getKombiniertVollhaftungsZeit());
        entry.updateInt("kombiniertRatierlich", ph.getKombiniertRatierlich());
        entry.updateTimestamp("created", ph.getCreated());
        entry.updateTimestamp("modified", ph.getModified());
        entry.updateInt("status", ph.getStatus());

        entry.updateRow();

        statement.close();
        con.close();

        return true;
    }

    /**
     * 
     * @param con
     * @param produktId
     * @return ProduktHaftungObj
     * @throws SQLException 
     */
    public static ProduktHaftungObj getProduktHaftungsObj(Connection con, int produktId) throws SQLException {
        String sql = "SELECT * FROM produkte_haftung WHERE produkt = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, produktId);

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("getProduktHaftungsObj Statement: " + statement.toString());
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

        entry.next();
        ProduktHaftungObj pr = getResultSetEntry(entry);

        entry.close();
        statement.close();
        con.close();
        return pr;
    }

    /**
     * 
     * @param entry
     * @return ProdukthaftungObj
     * @throws SQLException 
     */
    public static ProduktHaftungObj getResultSetEntry(ResultSet entry) throws SQLException {
        ProduktHaftungObj pr = new ProduktHaftungObj();

        pr.setId(entry.getInt("id"));
        pr.setProdukt(entry.getInt("produkt"));
        pr.setHaftungZeit(entry.getInt("haftungZeit"));
        pr.setHaftungMonate(entry.getByte("haftungMonate"));
        pr.setHaftungFormel(entry.getString("haftungMonate"));
        pr.setHaftungsart(entry.getInt("haftungsart"));
        pr.setRatierlich(entry.getInt("ratierlich"));
        pr.setRatierlichIntervalle(entry.getInt("ratierlichIntervalle"));
        pr.setRatierlichTabelle(entry.getInt("ratierlichTabelle"));
        pr.setKombiniert(entry.getInt("kombiniert"));
        pr.setKombiniertVollhaftungsZeit(entry.getInt("kombiniertVollhaftungsZeit"));
        pr.setKombiniertRatierlich(entry.getInt("kombiniertRatierlich"));
        pr.setCreated(entry.getTimestamp("created"));
        pr.setModified(entry.getTimestamp("modified"));
        pr.setStatus(entry.getInt("status"));

        return pr;
    }

    /**
     * 
     * @param con
     * @param id
     * @throws SQLException 
     */
    public static void deleteFromProdukte_haftung(Connection con, int id) throws SQLException {
        String sql = "UPDATE produkte_haftung SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param id
     * @throws SQLException 
     */
    public static void archiveFromProdukte_haftung(Connection con, int id) throws SQLException {
        String sql = "UPDATE produkte_haftung SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromProdukte_haftung(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM produkte_haftung WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
}
