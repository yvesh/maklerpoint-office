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

package de.maklerpoint.office.Nachrichten.Tools;

import de.maklerpoint.office.Nachrichten.NachrichtenObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NachrichtenSQLMethods {


    private static Preferences prefs = Preferences.userRoot().node(NachrichtenObj.class.getName());

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param mandantenId
     * @param benutzerId
     * @param betreff
     * @param context
     * @param tag
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntonachrichten(Connection con, int mandantenId, int benutzerId, java.lang.String betreff, java.lang.String context, java.lang.String tag,
        java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO nachrichten (mandantenId, benutzerId, betreff, context, tag, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, mandantenId);
        statement.setInt(2, benutzerId);
        statement.setString(3, betreff);
        statement.setString(4, context);
        statement.setString(5, tag);
        statement.setTimestamp(6, created);
        statement.setTimestamp(7, modified);
        statement.setInt(8, status);
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
     * @param mandantenId
     * @param benutzerId
     * @param betreff
     * @param context
     * @param tag
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatenachrichten(Connection con, int keyId, int mandantenId,
              int benutzerId, java.lang.String betreff, java.lang.String context, java.lang.String tag,
              java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        
        String sql = "SELECT * FROM nachrichten WHERE id = ?";
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

        entry.updateInt("mandantenId", mandantenId);
        entry.updateInt("benutzerId", benutzerId);
        if(betreff != null)
            entry.updateString("betreff", betreff);
        if(context != null)
            entry.updateString("context", context);
        if(tag != null)
            entry.updateString("tag", tag);
        if(created != null)
            entry.updateTimestamp("created", created);
        if(modified != null)
            entry.updateTimestamp("modified", modified);
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

    public static void deleteFromnachrichten(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM nachrichten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param mandantId
     * @return
     * @throws SQLException
     */

//     public static NachrichtenObj[] loadNachrichten(Connection con, int mandantId) throws SQLException {
    public static NachrichtenObj[] loadNachrichten(Connection con) throws SQLException {
        NachrichtenObj[] nachrichten = null;
        String sql = "SELECT * FROM nachrichten WHERE status = 0 ORDER by created DESC";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        nachrichten = new NachrichtenObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            nachrichten[i] = new NachrichtenObj(entry.getInt("id"));
            nachrichten[i].setBenutzerId(entry.getInt("benutzerId"));
            nachrichten[i].setBetreff(entry.getString("betreff"));
            nachrichten[i].setContext(entry.getString("context"));
            nachrichten[i].setTag(entry.getString("tag"));
            nachrichten[i].setCreated(entry.getTimestamp("created"));
            nachrichten[i].setModified(entry.getTimestamp("modified"));
            nachrichten[i].setRead(prefs.getBoolean(entry.getInt("id") + "Read", false));
            nachrichten[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();
        
        return nachrichten;
    }

    public static boolean updateNachricht(Connection con, NachrichtenObj nachricht) throws SQLException {

        String sql = "SELECT * FROM nachrichten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, nachricht.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        
        if(rows == 0)
            return false;

        entry.next();

        entry.updateInt("mandantenId", nachricht.getMandantenId());
        entry.updateInt("benutzerId", nachricht.getBenutzerId());
        entry.updateString("betreff", nachricht.getBetreff());
        entry.updateString("context", nachricht.getContext());
        entry.updateString("tag", nachricht.getTag());
//        entry.updateTimestamp("created", nachricht.getCreated());
        
        entry.updateTimestamp("modified", nachricht.getModified());
        entry.updateInt("status", nachricht.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }
}
