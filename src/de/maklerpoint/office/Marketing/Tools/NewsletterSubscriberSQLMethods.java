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
import de.maklerpoint.office.Marketing.NewsletterSubscriberObj;
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

public class NewsletterSubscriberSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param kennung
     * @param name
     * @param email
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntonewsletter_sub(Connection con, String kennung, String name,
            String email, java.sql.Timestamp created, java.sql.Timestamp modified,
            int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO newsletter_sub (kennung, name, email, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, kennung);
        statement.setString(2, name);
        statement.setString(3, email);
        statement.setTimestamp(4, created);
        statement.setTimestamp(5, modified);
        statement.setInt(6, status);
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

    public static int insertIntonewsletter_sub(Connection con, NewsletterSubscriberObj nl) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO newsletter_sub (kennung, zaId, ansprechpartnerId, name, email, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, nl.getKennung());
        statement.setInt(2, nl.getZaId());
        statement.setInt(3, nl.getAnsprechpartnerId());
        statement.setString(4, nl.getName());
        statement.setString(5, nl.getEmail());
        statement.setTimestamp(6, nl.getCreated());
        statement.setTimestamp(7, nl.getModified());
        statement.setInt(8, nl.getStatus());
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
     * @param kennung
     * @param name
     * @param email
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatenewsletter_sub(Connection con, int keyId, String kennung,
            String name, String email, java.sql.Timestamp created, java.sql.Timestamp modified,
            int status) throws SQLException {
        String sql = "SELECT * FROM newsletter_sub WHERE id = ?";
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

        if(kennung != null)
            entry.updateString("kennung", kennung);
        if(name != null)
            entry.updateString("name", name);
        if(email != null)
            entry.updateString("email", email);
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
     * 
     * @param con
     * @param nl
     * @return
     * @throws SQLException
     */

    public static boolean updatenewsletter_sub(Connection con, NewsletterSubscriberObj nl) throws SQLException {
        String sql = "SELECT * FROM newsletter_sub WHERE id = ?";
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
       
        entry.updateString("kennung", nl.getKennung());
        entry.updateString("name", nl.getName());
        entry.updateString("email", nl.getEmail());
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

    public static void deleteFromNewsletter_sub(Connection con, int keyId) throws SQLException {
        String sql = "SELECT id, status FROM newsletter_sub WHERE id = ?";
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

    public static void deleteEndgueltigFromnewsletter_sub(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM newsletter_sub WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }


    public static NewsletterSubscriberObj[] loadNewsletter_sub(Connection con, int status) throws SQLException {
        String sql = "SELECT * FROM newsletter_sub WHERE status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM newsletter_sub";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(status != -1)
            statement.setInt(1, status);
        
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

        NewsletterSubscriberObj[] nz = new NewsletterSubscriberObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();

            nz[i] = new NewsletterSubscriberObj();
            nz[i].setId(entry.getInt("id"));
            nz[i].setKennung(entry.getString("kennung"));
            nz[i].setName(entry.getString("name"));
            nz[i].setEmail(entry.getString("email"));
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
