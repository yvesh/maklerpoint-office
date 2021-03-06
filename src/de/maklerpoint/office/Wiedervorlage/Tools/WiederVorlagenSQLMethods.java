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


package de.maklerpoint.office.Wiedervorlage.Tools;

import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Wiedervorlage.WiedervorlagenObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class WiederVorlagenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param benutzerId
     * @param kundenId
     * @param type
     * @param public
     * @param beschreibung
     * @param tag
     * @param params
     * @param erinnerung
     * @param date
     * @param created
     * @param lastmodified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntowiedervorlagen(Connection con, int benutzerId, int kundenId, int type, boolean _public, java.lang.String beschreibung,
    java.lang.String tag, java.lang.String params, java.sql.Timestamp erinnerung, java.util.Date date, java.sql.Timestamp created,
    java.sql.Timestamp lastmodified, int status) throws SQLException {
    int generatedId = -1;
    String sql = "INSERT INTO wiedervorlagen (benutzerId, kundenId, type, public, beschreibung, tag, params, erinnerung, date, created, lastmodified, status)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setInt(1, benutzerId);
    statement.setInt(2, kundenId);
    statement.setInt(3, type);
    statement.setBoolean(4, _public);
    statement.setString(5, beschreibung);
    statement.setString(6, tag);
    statement.setString(7, params);
    statement.setTimestamp(8, erinnerung);
    statement.setDate(9, new java.sql.Date(date.getTime()));
    statement.setTimestamp(10, created);
    statement.setTimestamp(11, lastmodified);
    statement.setInt(12, status);
    statement.execute();
    ResultSet auto = statement.getGeneratedKeys();

    if(auto.next()){
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
     * @param benutzerId
     * @param kundenId
     * @param type
     * @param public
     * @param beschreibung
     * @param tag
     * @param params
     * @param erinnerung
     * @param date
     * @param created
     * @param lastmodified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatewiedervorlagen(Connection con, int keyId, int benutzerId, int kundenId, int type, boolean _public, java.lang.String beschreibung,
    java.lang.String tag, java.lang.String params, java.sql.Timestamp erinnerung, java.util.Date date, java.sql.Timestamp created,
    java.sql.Timestamp lastmodified, int status) throws SQLException {
    String sql = "SELECT * FROM wiedervorlagen WHERE id = ?";
    PreparedStatement statement = con.prepareStatement(sql,
    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    statement.setInt(1, keyId);
    ResultSet entry = statement.executeQuery();

    entry.last();
    int rows = entry.getRow();
    entry.beforeFirst();
    if(rows == 0)
        return false;

    entry.next();

    entry.updateInt("benutzerId", benutzerId);
    entry.updateInt("kundenId", kundenId);
    entry.updateInt("type", type);
    entry.updateBoolean("public", _public);
    if(beschreibung != null)
        entry.updateString("beschreibung", beschreibung);
    if(tag != null)
        entry.updateString("tag", tag);
    if(params != null)
        entry.updateString("params", params);
    if(erinnerung != null)
        entry.updateTimestamp("erinnerung", erinnerung);
    if(date != null)
        entry.updateDate("date", new java.sql.Date(date.getTime()));
    if(created != null)
        entry.updateTimestamp("created", created);
    if(lastmodified != null)
        entry.updateTimestamp("lastmodified", lastmodified);
    entry.updateInt("status", status);

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

    public static void deleteEndgueltigFromwiedervorlagen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM wiedervorlagen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param eigene
     * @return
     * @throws SQLException
     */

    public static WiedervorlagenObj[] loadWiedervorlagen(Connection con, boolean eigene) throws SQLException {
        
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM wiedervorlagen WHERE benutzerId = ? AND status = 0";
        else
            sql = "SELECT * FROM wiedervorlagen WHERE status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(eigene)
            statement.setInt(1, BasicRegistry.currentUser.getId());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        WiedervorlagenObj[] vorl = new WiedervorlagenObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            vorl[i] = new WiedervorlagenObj();

            vorl[i].setId(entry.getInt("id"));
            vorl[i].setBenutzerId(entry.getInt("benutzerId"));
            vorl[i].setKundenId(entry.getInt("kundenId"));
            vorl[i].setType(entry.getInt("type"));
            vorl[i].setPublic(entry.getBoolean("public"));
            vorl[i].setBeschreibung(entry.getString("beschreibung"));
            vorl[i].setTag(entry.getString("tag"));
            vorl[i].setParams(entry.getString("params"));
            vorl[i].setErinnerung(entry.getTimestamp("erinnerung"));
            vorl[i].setDate(entry.getDate("date"));
            vorl[i].setCreated(entry.getTimestamp("created"));
            vorl[i].setLastmodified(entry.getTimestamp("lastmodified"));
            vorl[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return vorl;
    }


    public static WiedervorlagenObj[] loadWiedervorlagen(Connection con, String kennung) throws SQLException {

        String sql = null;

        sql = "SELECT * FROM wiedervorlagen WHERE kundeKennung = ? AND status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

       statement.setString(1, kennung);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        WiedervorlagenObj[] vorl = new WiedervorlagenObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            vorl[i] = new WiedervorlagenObj();

            vorl[i].setId(entry.getInt("id"));
            vorl[i].setBenutzerId(entry.getInt("benutzerId"));
            vorl[i].setKundenId(entry.getInt("kundenId"));
            vorl[i].setType(entry.getInt("type"));
            vorl[i].setPublic(entry.getBoolean("public"));
            vorl[i].setBeschreibung(entry.getString("beschreibung"));
            vorl[i].setTag(entry.getString("tag"));
            vorl[i].setParams(entry.getString("params"));
            vorl[i].setErinnerung(entry.getTimestamp("erinnerung"));
            vorl[i].setDate(entry.getDate("date"));
            vorl[i].setCreated(entry.getTimestamp("created"));
            vorl[i].setLastmodified(entry.getTimestamp("lastmodified"));
            vorl[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return vorl;
    }

    /**
     * 
     * @param con
     * @param eigene
     * @return
     * @throws SQLException
     */
    
    public static WiedervorlagenObj getWiedervorlage(Connection con, int keyId) throws SQLException {

        String sql = "SELECT * FROM wiedervorlagen WHERE id = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, keyId);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        WiedervorlagenObj vorl = new WiedervorlagenObj();
            
        vorl.setId(entry.getInt("id"));
        vorl.setBenutzerId(entry.getInt("benutzerId"));
        vorl.setKundenId(entry.getInt("kundenId"));
        vorl.setType(entry.getInt("type"));
        vorl.setPublic(entry.getBoolean("public"));
        vorl.setBeschreibung(entry.getString("beschreibung"));
        vorl.setTag(entry.getString("tag"));
        vorl.setParams(entry.getString("params"));
        vorl.setErinnerung(entry.getTimestamp("erinnerung"));
        vorl.setDate(entry.getDate("date"));
        vorl.setCreated(entry.getTimestamp("created"));
        vorl.setLastmodified(entry.getTimestamp("lastmodified"));
        vorl.setStatus(entry.getInt("status"));

        entry.close();
        statement.close();
        con.close();

        return vorl;
    }

}
