/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Nachrichten.Tools;

import de.maklerpoint.office.Nachrichten.MessageObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class MessageSQLMethods {
    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param mandantenId
     * @param senderId
     * @param empfaengerId
     * @param md5sum
     * @param betreff
     * @param context
     * @param tag
     * @param read
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntoMessages(Connection con, MessageObj msg) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO messages (mandantenId, senderId, empfaengerId, md5sum, betreff, context, "
                         + "tag, mread, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, msg.getMandantenId());
        statement.setInt(2, msg.getSenderId());
        statement.setInt(3, msg.getEmpfaengerId());
        statement.setString(4, msg.getMd5sum());
        statement.setString(5, msg.getBetreff());
        statement.setString(6, msg.getContext());
        statement.setString(7, msg.getTag());
        statement.setBoolean(8, msg.isRead());
        statement.setTimestamp(9, msg.getCreated());
        statement.setTimestamp(10, msg.getModified());
        statement.setInt(11, msg.getStatus());
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
     * @param senderId
     * @param empfaengerId
     * @param md5sum
     * @param betreff
     * @param context
     * @param tag
     * @param read
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updateMessages(Connection con, int keyId, int mandantenId, 
                int senderId, int empfaengerId, String md5sum, String betreff, 
                String context, String tag, boolean read, java.sql.Timestamp created, 
                java.sql.Timestamp modified, int status) throws SQLException {
        String sql = "SELECT * FROM messages WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, 
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0){
            entry.close();
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("mandantenId", mandantenId);
        entry.updateInt("senderId", senderId);
        entry.updateInt("empfaengerId", empfaengerId);
        if(md5sum != null)
            entry.updateString("md5sum", md5sum);
        if(betreff != null)
            entry.updateString("betreff", betreff);
        if(context != null)
            entry.updateString("context", context);
        if(tag != null)
            entry.updateString("tag", tag);
        entry.updateBoolean("mread", read);
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

    public static void deleteEndgueltigFromMessages(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM messages WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    /**
     * 
     * @param con
     * @param msg
     * @throws SQLException 
     */
    
    public static void deleteFromMessage(Connection con, MessageObj msg) throws SQLException {
        String sql = "UPDATE messages SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, msg.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();        
    }
    
    public static void archiveFromMessage(Connection con, MessageObj msg) throws SQLException {
        String sql = "UPDATE messages SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, msg.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();        
    }     
    
    public static void setRead(Connection con, MessageObj msg) throws SQLException {
        String sql = "UPDATE messages SET mread = true WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, msg.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();  
    }
    
    /**
     * -1 für ohne status
     * @param con
     * @param userId
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static MessageObj[] getAllMessagesEmpfang(Connection con, int userId, int status) throws SQLException {
        MessageObj[] msgs = null;
        
        String sql = "SELECT * FROM messages WHERE empfaengerId = ? AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM messages WHERE empfaengerId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, userId);
        
        if(status != -1)
            statement.setInt(2, status);
        
        ResultSet entry = statement.executeQuery();
        
        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            statement.close();
            con.close();
            return null;
        }
        
        msgs = new MessageObj[rows];
        
        for(int i = 0; i < rows; i++) {
            entry.next();
            msgs[i] = new MessageObj();
            msgs[i].setId(entry.getInt("id"));
            msgs[i].setMandantenId(entry.getInt("mandantenId"));
            msgs[i].setSender(entry.getInt("senderId"));
            msgs[i].setEmpfaengerId(userId);
            msgs[i].setMd5sum(entry.getString("md5sum"));
            msgs[i].setBetreff(entry.getString("betreff"));
            msgs[i].setContext(entry.getString("context"));
            msgs[i].setTag(entry.getString("tag"));
            msgs[i].setRead(entry.getBoolean("mread"));
            msgs[i].setCreated(entry.getTimestamp("created"));
            msgs[i].setModified(entry.getTimestamp("modified"));
            msgs[i].setStatus(entry.getInt("status"));
        }
        
        entry.close();
        statement.close();
        con.close();
        
        return msgs;
    }
    
    public static boolean checkNewMessages(Connection con, int userId, int status) throws SQLException {
        MessageObj[] msgs = null;
        
        String sql = "SELECT * FROM messages WHERE empfaengerId = ? AND mread = 0 AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM messages WHERE empfaengerId = ? AND mread = 0";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, userId);
        
        if(status != -1)
            statement.setInt(2, status);
        
        ResultSet entry = statement.executeQuery();
        
        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        
        entry.close();
        statement.close();
        con.close();

        if(rows == 0) {            
            return false;
        } else {        
            return true;
        }

    }
    
    /**
     * 
     * @param con
     * @param userId
     * @param status
     * @return
     * @throws SQLException 
     */    
    
    public static MessageObj[] getAllMessagesSend(Connection con, int userId, int status) throws SQLException {
        MessageObj[] msgs = null;
        
        String sql = "SELECT * FROM messages WHERE senderId = ? AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM messages WHERE senderId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, userId);
        
        if(status != -1)
            statement.setInt(2, status);
        
        ResultSet entry = statement.executeQuery();
        
        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            statement.close();
            con.close();
            return null;
        }
        
        msgs = new MessageObj[rows];
        
        for(int i = 0; i < rows; i++) {
            entry.next();
            msgs[i] = new MessageObj();
            msgs[i].setId(entry.getInt("id"));
            msgs[i].setMandantenId(entry.getInt("mandantenId"));
            msgs[i].setSender(userId);
            msgs[i].setEmpfaengerId(entry.getInt("empfaengerId"));
            msgs[i].setMd5sum(entry.getString("md5sum"));
            msgs[i].setBetreff(entry.getString("betreff"));
            msgs[i].setContext(entry.getString("context"));
            msgs[i].setTag(entry.getString("tag"));
            msgs[i].setRead(entry.getBoolean("mread"));
            msgs[i].setCreated(entry.getTimestamp("created"));
            msgs[i].setModified(entry.getTimestamp("modified"));
            msgs[i].setStatus(entry.getInt("status"));
        }
        
        entry.close();
        statement.close();
        con.close();
        
        return msgs;
    }
    
}