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

package de.maklerpoint.office.Marketing.Tools;

/* Neccessary imports for the generated code */
import de.maklerpoint.office.Marketing.NewsletterObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */

public class NewsletterSQLMethods {



        /**
         * Java method that inserts a row in the generated sql table
         * and returns the new generated id
         * @param con (open java.sql.Connection)
         * @param benutzerId
         * @param sender
         * @param senderMail
         * @param subject
         * @param text
         * @param send
         * @param created
         * @param modified
         * @param status
         * @return id (database row id [id])
         * @throws SQLException
         */

        public static int insertIntonewsletter(Connection con, int benutzerId, String sender, String senderMail, String subject, String text,
                boolean send, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
            int generatedId = -1;
            String sql = "INSERT INTO newsletter (benutzerId, sender, senderMail, subject, text, send, created, modified, status)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, benutzerId);
            statement.setString(2, sender);
            statement.setString(3, senderMail);
            statement.setString(4, subject);
            statement.setString(5, text);
            statement.setBoolean(6, send);
            statement.setTimestamp(7, created);
            statement.setTimestamp(8, modified);
            statement.setInt(9, status);
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
         * 
         * @param con
         * @param nl
         * @return
         * @throws SQLException
         */
        
        public static int insertIntonewsletter(Connection con, NewsletterObj nl) throws SQLException {
            int generatedId = -1;
            String sql = "INSERT INTO newsletter (benutzerId, sender, senderMail, subject, text, send, created, modified, status)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, nl.getBenutzerId());
            statement.setString(2, nl.getSender());
            statement.setString(3, nl.getSenderMail());
            statement.setString(4, nl.getSubject());
            statement.setString(5, nl.getText());
            statement.setBoolean(6, nl.isSend());
            statement.setTimestamp(7, nl.getCreated());
            statement.setTimestamp(8, nl.getModified());
            statement.setInt(9, nl.getStatus());
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
         * @param sender
         * @param senderMail
         * @param subject
         * @param text
         * @param send
         * @param created
         * @param modified
         * @param status
         * @return boolean (true on success)
         * @throws SQLException
         */

        public static boolean updatenewsletter(Connection con, int keyId, int benutzerId, String sender, String senderMail, String subject, String text,
              boolean send, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
            String sql = "SELECT * FROM newsletter WHERE id = ?";
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

            entry.updateInt("benutzerId", benutzerId);
            if(sender != null)
                entry.updateString("sender", sender);
            if(senderMail != null)
                entry.updateString("senderMail", senderMail);
            if(subject != null)
                entry.updateString("subject", subject);
            if(text != null)
                entry.updateString("text", text);
            entry.updateBoolean("send", send);
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

    public static boolean updatenewsletter(Connection con, NewsletterObj nl) throws SQLException {
        String sql = "SELECT * FROM newsletter WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, nl.getId());
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

        entry.updateInt("benutzerId", nl.getBenutzerId());
        entry.updateString("sender", nl.getSender());
        entry.updateString("senderMail", nl.getSenderMail());
        entry.updateString("subject", nl.getSubject());
        entry.updateString("text", nl.getText());
        entry.updateBoolean("send", nl.isSend());      
        entry.updateTimestamp("created", nl.getCreated());
        entry.updateTimestamp("modified", nl.getModified());
        entry.updateInt("status", nl.getStatus());

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

    public static void deleteFromNewsletter(Connection con, int keyId) throws SQLException {
        String sql = "SELECT id, status FROM newsletter WHERE id = ?";
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
         * Java method that deletes a row from the generated sql table
         * @param con (open java.sql.Connection)
         * @param keyId (the primary key to the row)
         * @throws SQLException
         */

        public static void deleteEndgueltigFromnewsletter(Connection con, int keyId) throws SQLException {
            String sql = "DELETE FROM newsletter WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, keyId);
            statement.executeUpdate();
            statement.close();
            con.close();
        }

        /**
         * 
         * @param con
         * @return
         * @throws SQLException
         */

        public static NewsletterObj[] loadNewsletter(Connection con) throws SQLException {
            String sql = "SELECT * FROM newsletter WHERE status = 0";

            PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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

            NewsletterObj[] nz = new NewsletterObj[rows];

            for(int i = 0; i < rows; i++) {
                entry.next();

                nz[i] = new NewsletterObj();
                nz[i].setId(entry.getInt("id"));
                nz[i].setBenutzerId(entry.getInt("benutzerId"));
                nz[i].setSender(entry.getString("sender"));
                nz[i].setSenderMail(entry.getString("senderMail"));
                nz[i].setSubject(entry.getString("subject"));
                nz[i].setText(entry.getString("text"));
                nz[i].setSend(entry.getBoolean("send"));
                nz[i].setCreated(entry.getTimestamp("created"));
                nz[i].setModified(entry.getTimestamp("modified"));
                nz[i].setStatus(entry.getInt("status"));
            }

            entry.close();
            statement.close();
            con.close();

            return nz;
        }


}
