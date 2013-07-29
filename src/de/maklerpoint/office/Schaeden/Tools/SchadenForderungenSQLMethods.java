/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       25.07.2011 18:48:26
 *  File:       SchadenForderungenSQLMethods
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

import de.maklerpoint.office.Schaeden.SchadenForderungObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SchadenForderungenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param SchadenForderungObj
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoSchaeden_forderungen(Connection con, SchadenForderungObj sf)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO schaeden_forderungen (creatorId, mandantenId, schadenId, belegVon, anspruchSteller, anspruchArt, "
                + "gesamtforderung, selbstbeteiligung, effektiveforderung, zahltext, zahlungvon, comments, "
                + "created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, sf.getCreatorId());
        statement.setInt(2, sf.getMandantenId());
        statement.setInt(3, sf.getSchadenId());
        statement.setTimestamp(4, sf.getBelegVon());
        statement.setString(5, sf.getAnspruchSteller());
        statement.setString(6, sf.getAnspruchArt());
        statement.setDouble(7, sf.getGesamtforderung());
        statement.setDouble(8, sf.getSelbstbeteiligung());
        statement.setDouble(9, sf.getEffektiveforderung());
        statement.setString(10, sf.getZahltext());
        statement.setString(11, sf.getZahlungvon());
        statement.setString(12, sf.getComments());
        statement.setTimestamp(13, sf.getCreated());
        statement.setTimestamp(14, sf.getModified());
        statement.setInt(15, sf.getStatus());
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
     * @param Schadenforderung
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateSchaeden_forderungen(Connection con, SchadenForderungObj sf)
            throws SQLException {
        String sql = "SELECT * FROM schaeden_forderungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, sf.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("creatorId", sf.getCreatorId());
        entry.updateInt("mandantenId", sf.getMandantenId());
        entry.updateInt("schadenId", sf.getSchadenId());
        entry.updateTimestamp("belegVon", sf.getBelegVon());
        entry.updateString("anspruchSteller", sf.getAnspruchSteller());
        entry.updateString("anspruchArt", sf.getAnspruchArt());
        entry.updateDouble("gesamtforderung", sf.getGesamtforderung());
        entry.updateDouble("selbstbeteiligung", sf.getSelbstbeteiligung());
        entry.updateDouble("effektiveforderung", sf.getEffektiveforderung());
        entry.updateString("zahltext", sf.getZahltext());
        entry.updateString("zahlungvon", sf.getZahlungvon());
        entry.updateString("comments", sf.getComments());
        entry.updateTimestamp("created", sf.getCreated());
        entry.updateTimestamp("modified", sf.getModified());
        entry.updateInt("status", sf.getStatus());

        entry.updateRow();
        
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param schadenId
     * @param status
     * @return Schädenforderungen
     * @throws SQLException 
     */
    public static SchadenForderungObj[] getSchadenForderungen(Connection con, int schadenId, int status) throws SQLException{
        SchadenForderungObj[] sfs = null;

        String sql = "SELECT * FROM  schaeden_forderungen WHERE schadenId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM  schaeden_forderungen WHERE schadenId = ?";
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

        sfs = new SchadenForderungObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sfs[i] = getResultSet(entry);
        }


        statement.close();
        con.close();

        return sfs;
    }
    
    public static SchadenForderungObj getResultSet(ResultSet entry) throws SQLException {
        SchadenForderungObj sf = new SchadenForderungObj();
        
        sf.setId(entry.getInt("id"));
        sf.setCreatorId(entry.getInt("creatorId"));
        sf.setMandantenId(entry.getInt("mandantenId"));
        sf.setSchadenId(entry.getInt("schadenId"));
        sf.setBelegVon(entry.getTimestamp("belegVon"));
        sf.setAnspruchSteller(entry.getString("anspruchSteller"));
        sf.setAnspruchArt(entry.getString("anspruchArt"));
        sf.setGesamtforderung(entry.getDouble("gesamtforderung"));
        sf.setSelbstbeteiligung(entry.getDouble("selbstbeteiligung"));
        sf.setZahltext(entry.getString("zahltext"));
        sf.setZahlungvon(entry.getString("zahlungvon"));
        sf.setComments(entry.getString("comments"));
        sf.setCreated(entry.getTimestamp("created"));
        sf.setCreated(entry.getTimestamp("modified"));
        sf.setStatus(entry.getInt("status"));
        
        return sf;
    }
    
    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteFromSchaeden_forderungen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM schaeden_forderungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
}
