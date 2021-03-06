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

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Konstanten.MPointKonstanten;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Session.SessionObj;
import de.maklerpoint.office.System.Version;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SessionTools {

    /**
     * 
     * @param benutzer
     * @return
     * @throws NoSuchAlgorithmException
     */

    public static SessionObj createSession(BenutzerObj benutzer) throws NoSuchAlgorithmException, SQLException {
        SessionObj session = new SessionObj();

        session.setBenutzerid(benutzer.getId());
        session.setLastrefresh(new java.sql.Timestamp(System.currentTimeMillis()));
        session.setStart(new java.sql.Timestamp(System.currentTimeMillis()));

        String sessionId = SessionId.generateSessionId(benutzer.getUsername());
        session.setSession_id(sessionId);
        session.setAnwendung(MPointKonstanten.MP_OFFICE_CLIENT);
        session.setBuild(Version.build);
        
        session.setStatus(0);

        int id = SessionSQLMethods.insertIntoSession(DatabaseConnection.open(), session);

        session.setId(id);
        BasicRegistry.currentSession = session;

        return session;
    }

    /**
     *
     * @param session
     * @return
     */

    public static SessionObj updateSession(SessionObj session) {        
        session.setLastrefresh(new java.sql.Timestamp(System.currentTimeMillis()));       
        return session;
    }

    /**
     * 
     * @param session
     * @return
     * @throws SQLException
     */

    public static boolean deleteSession(SessionObj session) throws SQLException {
        if(session == null)
            return true;

        SessionSQLMethods.deleteFromSession(DatabaseConnection.open(), session);
        //BasicRegistry.currentSession = null;
        return true;
    }
    
    /**
     * 
     * @throws SQLException 
     */
    
    public static void deleteCurrentSession() throws SQLException{
        SessionSQLMethods.deleteFromSession(DatabaseConnection.open(), BasicRegistry.currentSession);
    }

}
