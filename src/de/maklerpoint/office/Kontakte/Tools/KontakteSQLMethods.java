/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 18:44:15
 *  File:       KontakteSQLMethods
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
package de.maklerpoint.office.Kontakte.Tools;

import de.maklerpoint.office.Kontakte.KontaktObj;
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
public class KontakteSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param KontaktObj
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoKontakte(Connection con, KontaktObj kontakt) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO kontakte (creatorId, kundenKennung, versichererId, produktId, vertragId, schadenId, "
                + "stoerId, benutzerId, name, adresse, communication1, communication2, "
                + "communication3, communication4, communication5, communication6, communication1Type, communication2Type, "
                + "communication3Type, communication4Type, communication5Type, communication6Type, comments, custom1, "
                + "custom2, custom3, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, kontakt.getCreatorId());
        statement.setString(2, kontakt.getKundenKennung());
        statement.setInt(3, kontakt.getVersichererId());
        statement.setInt(4, kontakt.getProduktId());
        statement.setInt(5, kontakt.getVertragId());
        statement.setInt(6, kontakt.getSchadenId());
        statement.setInt(7, kontakt.getStoerId());
        statement.setInt(8, kontakt.getBenutzerId());
        statement.setString(9, kontakt.getName());
        statement.setString(10, kontakt.getAdresse());
        statement.setString(11, kontakt.getCommunication1());
        statement.setString(12, kontakt.getCommunication2());
        statement.setString(13, kontakt.getCommunication3());
        statement.setString(14, kontakt.getCommunication4());
        statement.setString(15, kontakt.getCommunication5());
        statement.setString(16, kontakt.getCommunication6());
        statement.setInt(17, kontakt.getCommunication1Type());
        statement.setInt(18, kontakt.getCommunication2Type());
        statement.setInt(19, kontakt.getCommunication3Type());
        statement.setInt(20, kontakt.getCommunication4Type());
        statement.setInt(21, kontakt.getCommunication5Type());
        statement.setInt(22, kontakt.getCommunication6Type());
        statement.setString(23, kontakt.getComments());
        statement.setString(24, kontakt.getCustom1());
        statement.setString(25, kontakt.getCustom2());
        statement.setString(26, kontakt.getCustom3());
        statement.setTimestamp(27, kontakt.getCreated());
        statement.setTimestamp(28, kontakt.getModified());
        statement.setInt(29, kontakt.getStatus());
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
     * @param KontaktObj
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateKontakte(Connection con, KontaktObj kontakt) throws SQLException {
        String sql = "SELECT * FROM kontakte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, kontakt.getId());
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

        entry.updateInt("creatorId", kontakt.getCreatorId());
        entry.updateString("kundenKennung", kontakt.getKundenKennung());
        entry.updateInt("versichererId", kontakt.getVersichererId());
        entry.updateInt("produktId", kontakt.getProduktId());
        entry.updateInt("vertragId", kontakt.getVertragId());
        entry.updateInt("schadenId", kontakt.getSchadenId());
        entry.updateInt("stoerId", kontakt.getStoerId());
        entry.updateInt("benutzerId", kontakt.getBenutzerId());
        entry.updateString("name", kontakt.getName());
        entry.updateString("adresse", kontakt.getAdresse());
        entry.updateString("communication1", kontakt.getCommunication1());
        entry.updateString("communication2", kontakt.getCommunication2());
        entry.updateString("communication3", kontakt.getCommunication3());
        entry.updateString("communication4", kontakt.getCommunication4());
        entry.updateString("communication5", kontakt.getCommunication5());
        entry.updateString("communication6", kontakt.getCommunication6());
        entry.updateInt("communication1Type", kontakt.getCommunication1Type());
        entry.updateInt("communication2Type", kontakt.getCommunication2Type());
        entry.updateInt("communication3Type", kontakt.getCommunication3Type());
        entry.updateInt("communication4Type", kontakt.getCommunication4Type());
        entry.updateInt("communication5Type", kontakt.getCommunication5Type());
        entry.updateInt("communication6Type", kontakt.getCommunication6Type());
        entry.updateString("comments", kontakt.getComments());
        entry.updateString("custom1", kontakt.getCustom1());
        entry.updateString("custom2", kontakt.getCustom2());
        entry.updateString("custom3", kontakt.getCustom3());
        entry.updateTimestamp("created", kontakt.getCreated());
        entry.updateTimestamp("modified", kontakt.getModified());
        entry.updateInt("status", kontakt.getStatus());

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
    public static void deleteEndgueltigFromKontakte(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM kontakte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    
    
    /**
     * 
     * @param con
     * @param kundenNr
     * @param status
     * @return Kontakt Objekte
     * @throws SQLException 
     */
    public static KontaktObj[] getKundenKontakte(Connection con, String kundenNr, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kundenNr);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    /**
     * 
     * @param con
     * @param creatorId
     * @param status
     * @return Kontakt Objekte
     * @throws SQLException 
     */
    public static KontaktObj[] getEigeneKontakte(Connection con, int creatorId, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE creatorId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE creatorId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, creatorId);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    /**
     * 
     * @param con
     * @param versid
     * @param status
     * @return Kontakte
     * @throws SQLException 
     */
    public static KontaktObj[] getVersichererKontakte(Connection con, int versid, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE versichererId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE versichererId = ?";
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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    /**
     * 
     * @param con
     * @param prodid
     * @param status
     * @return Kontakte
     * @throws SQLException 
     */
    
    public static KontaktObj[] getProduktKontakte(Connection con, int prodid, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE produktId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE produktId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, prodid);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    
     /**
     * 
     * @param con
     * @param vertragid
     * @param status
     * @return Kontakte
     * @throws SQLException 
     */
    
    public static KontaktObj[] getVertragKontakte(Connection con, int vertragid, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE vertragId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE vertragId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, vertragid);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    /**
     * 
     * @param con
     * @param schadenid
     * @param status
     * @return Kontakte
     * @throws SQLException 
     */
    
    public static KontaktObj[] getSchadenKontakte(Connection con, int schadenid, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE schadenId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE schadenId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, schadenid);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }
    
    public static void deleteFromKontakte(Connection con, int id) throws SQLException {
        String sql = "UPDATE kontakte SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();        
        con.close();
    }
    
    public static void archiveFromKontakte(Connection con, int id) throws SQLException {        
        String sql = "UPDATE bankkonten SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    /**
     * 
     * @param con
     * @param stoerfall
     * @param status
     * @return Kontakte
     * @throws SQLException 
     */
    
    public static KontaktObj[] getStoerfallKontakte(Connection con, int stoerfall, int status) throws SQLException {
        KontaktObj[] kontakte = null;

        String sql = "SELECT * FROM kontakte WHERE stoerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kontakte WHERE stoerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, stoerfall);

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

        kontakte = new KontaktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kontakte[i] = getResultSet(entry);
        }

        statement.close();
        con.close();
        return kontakte;
    }

    /**
     * 
     * @param entry
     * @return KontaktObj ResultSet
     * @throws SQLException 
     */
    public static KontaktObj getResultSet(ResultSet entry) throws SQLException {
        KontaktObj ko = new KontaktObj();

        ko.setId(entry.getInt("id"));
        ko.setCreatorId(entry.getInt("creatorId"));

        ko.setKundenKennung(entry.getString("kundenKennung"));
        ko.setVersichererId(entry.getInt("versichererId"));
        ko.setProduktId(entry.getInt("produktId"));
        ko.setVertragId(entry.getInt("vertragId"));
        ko.setSchadenId(entry.getInt("schadenId"));
        ko.setStoerId(entry.getInt("stoerId"));
        ko.setBenutzerId(entry.getInt("benutzerId"));

        ko.setName(entry.getString("name"));
        ko.setAdresse(entry.getString("adresse"));

        ko.setCommunication1(entry.getString("communication1"));
        ko.setCommunication2(entry.getString("communication2"));
        ko.setCommunication3(entry.getString("communication3"));
        ko.setCommunication4(entry.getString("communication4"));
        ko.setCommunication5(entry.getString("communication5"));
        ko.setCommunication6(entry.getString("communication6"));

        ko.setCommunication1Type(entry.getInt("communication1Type"));
        ko.setCommunication2Type(entry.getInt("communication2Type"));
        ko.setCommunication3Type(entry.getInt("communication3Type"));
        ko.setCommunication4Type(entry.getInt("communication4Type"));
        ko.setCommunication5Type(entry.getInt("communication5Type"));
        ko.setCommunication6Type(entry.getInt("communication6Type"));

        ko.setComments(entry.getString("comments"));
        ko.setCustom1(entry.getString("custom1"));
        ko.setCustom2(entry.getString("custom2"));
        ko.setCustom3(entry.getString("custom3"));

        ko.setCreated(entry.getTimestamp("created"));
        ko.setCreated(entry.getTimestamp("modified"));

        ko.setStatus(entry.getInt("status"));

        return ko;
    }
}
