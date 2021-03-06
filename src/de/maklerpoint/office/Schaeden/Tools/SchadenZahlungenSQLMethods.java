/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       27.07.2011 09:34:08
 *  File:       SchadenZahlungenSQLMethods
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
package de.maklerpoint.office.Schaeden.Tools;

import de.maklerpoint.office.Schaeden.SchadenZahlungObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SchadenZahlungenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param sz SchadenZahlungObj
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoSchaeden_zahlungen(Connection con, SchadenZahlungObj sz) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO schaeden_zahlungen (creatorId, mandantenId, schadenId, schadenForderungId, belegVon, beguenstigt, "
                + "forderungsArt, zahlung, zahltext, zahlungvon, comments, created, "
                + "modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, sz.getCreatorId());
        statement.setInt(2, sz.getMandantenId());
        statement.setInt(3, sz.getSchadenId());
        statement.setInt(4, sz.getSchadenForderungId());
        statement.setTimestamp(5, sz.getBelegVon());
        statement.setString(6, sz.getBeguenstigt());
        statement.setString(7, sz.getForderungsArt());
        statement.setDouble(8, sz.getZahlung());
        statement.setString(9, sz.getZahltext());
        statement.setString(10, sz.getZahlungvon());
        statement.setString(11, sz.getComments());
        statement.setTimestamp(12, sz.getCreated());
        statement.setTimestamp(13, sz.getModified());
        statement.setInt(14, sz.getStatus());
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
     * @param SchadenZahlungObj sz
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateSchaeden_zahlungen(Connection con, SchadenZahlungObj sz) throws SQLException {
        String sql = "SELECT * FROM schaeden_zahlungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, sz.getId());
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

        entry.updateInt("creatorId", sz.getCreatorId());
        entry.updateInt("mandantenId", sz.getMandantenId());
        entry.updateInt("schadenId", sz.getSchadenId());
        entry.updateInt("schadenForderungId", sz.getSchadenForderungId());
        entry.updateTimestamp("belegVon", sz.getBelegVon());
        entry.updateString("beguenstigt", sz.getBeguenstigt());
        entry.updateString("forderungsArt", sz.getForderungsArt());
        entry.updateDouble("zahlung", sz.getZahlung());
        entry.updateString("zahltext", sz.getZahltext());
        entry.updateString("zahlungvon", sz.getZahlungvon());
        entry.updateString("comments", sz.getComments());
        entry.updateTimestamp("created", sz.getCreated());
        entry.updateTimestamp("modified", sz.getModified());
        entry.updateInt("status", sz.getStatus());

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
    public static void deleteEndgueltigFromSchaeden_zahlungen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM schaeden_zahlungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
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
    public static void deleteFromSchaeden_zahlungen(Connection con, int id) throws SQLException {
        String sql = "UPDATE schaeden_zahlungen SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    
     public static SchadenZahlungObj[] getSchadenZahlungen(Connection con, int schadenId, int status) throws SQLException{
        SchadenZahlungObj[] szs = null;

        String sql = "SELECT * FROM  schaeden_zahlungen WHERE schadenId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM  schaeden_zahlungen WHERE schadenId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, schadenId);

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

        szs = new SchadenZahlungObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            szs[i] = getResultSet(entry);
        }


        statement.close();
        con.close();

        return szs;
    }
     /**
      * 
      * @param entry
      * @return SchadenZalungObj
      * @throws SQLException 
      */
    public static SchadenZahlungObj getResultSet(ResultSet entry) throws SQLException {
    
        SchadenZahlungObj sz = new SchadenZahlungObj();
        
        sz.setId(entry.getInt("id"));
        sz.setCreatorId(entry.getInt("creatorId"));
        sz.setMandantenId(entry.getInt("mandantenId"));
        sz.setSchadenId(entry.getInt("schadenId"));
        sz.setSchadenForderungId(entry.getInt("schadenForderungId"));
        sz.setBelegVon(entry.getTimestamp("belegVon"));
        
        sz.setBeguenstigt(entry.getString("beguenstigt"));
        sz.setForderungsArt(entry.getString("forderungsArt"));
        sz.setZahlung(entry.getDouble("zahlung"));

        sz.setZahltext(entry.getString("zahltext"));
        sz.setZahlungvon(entry.getString("zahlungvon"));
        sz.setComments(entry.getString("comments"));
        sz.setCreated(entry.getTimestamp("created"));
        sz.setCreated(entry.getTimestamp("modified"));
        sz.setStatus(entry.getInt("status"));        
        
        return sz;
    }
    
}
