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

package de.maklerpoint.office.Idle.Execute;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Idle.IdleJob;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Session.Tools.SessionSQLMethods;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class OnlineExecute implements IdleJob {

    /**
     * Aufgaben während der Benutzer am PC ist
     * Session. Alle x Sekunden
     */
    
    public void execute() {
        try {
            Log.logger.debug("Aktualisiere DB Session");
            SessionSQLMethods.updateCurrentSession(DatabaseConnection.open());
        } catch (Exception e) {
            Log.logger.warn("Konnte Session nicht aktualisieren", e);
        }            
    }

}
