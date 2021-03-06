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

package de.maklerpoint.office.Session.Tools;

import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Session.SessionObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SessionSQLMethods {

     /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param benutzerid
     * @param start
     * @param lastrefresh
     * @param session_id
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntoSession(Connection con, SessionObj session)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO session (benutzerid, start, lastrefresh, session_id, anwendung, build, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, session.getBenutzerid());
        statement.setTimestamp(2, session.getStart());
        statement.setTimestamp(3, session.getLastrefresh());
        statement.setString(4, session.getSession_id());
        statement.setInt(5, session.getAnwendung());
        statement.setString(6, session.getBuild());
        statement.setInt(7, session.getStatus());
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
     * @param benutzerid
     * @param start
     * @param lastrefresh
     * @param session_id
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updateSession(Connection con, SessionObj session)
            throws SQLException {
        String sql = "SELECT * FROM session WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, session.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        entry.updateInt("benutzerid", session.getBenutzerid());
        if(session.getStart() != null)
            entry.updateTimestamp("start", session.getStart());
        if(session.getLastrefresh() != null)
            entry.updateTimestamp("lastrefresh", session.getLastrefresh());
        if(session.getSession_id() != null)
            entry.updateString("session_id", session.getSession_id());
        entry.updateInt("status", session.getStatus());

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

    public static void deleteEndgueltigFromSession(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM session WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    /**
     * 
     * @param con
     * @param session
     * @throws SQLException 
     */
    
    public static void deleteFromSession(Connection con, SessionObj session) throws SQLException {
        if(session == null)
            return;
        
        String sql = "UPDATE session SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, session.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        session.setStatus(Status.DELETED);
    }
    
    /**
     * 
     * @param con
     * @throws SQLException 
     */
    
    public static void updateCurrentSession(Connection con) throws SQLException {
        SessionObj cr = BasicRegistry.currentSession;        
        String sql = "UPDATE session SET lastrefresh = ? WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        cr.setLastrefresh(new java.sql.Timestamp(System.currentTimeMillis()));
        statement.setTimestamp(1, cr.getLastrefresh());
        statement.setInt(2, cr.getId());
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

    public static SessionObj[] getAllSessions(Connection con) throws SQLException {
        SessionObj[] sessions = null;

        String sql = "SELECT * FROM session WHERE status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        sessions = new SessionObj[rows];
//
//        System.out.println("Rows: " + rows);

        for(int i = 0; i < rows; i++) {
            entry.next();
            sessions[i] = new SessionObj();
            sessions[i].setId(entry.getInt("id"));
            sessions[i].setBenutzerid(entry.getInt("benutzerId"));
            sessions[i].setLastrefresh(entry.getTimestamp("lastrefresh"));
            sessions[i].setStart(entry.getTimestamp("start"));
            sessions[i].setSession_id(entry.getString("session_id"));
            sessions[i].setAnwendung(entry.getInt("anwendung"));
            sessions[i].setBuild(entry.getString("build"));
            sessions[i].setStatus(0);
        }

        entry.close();
        statement.close();
        con.close();

        return sessions;
    }

}