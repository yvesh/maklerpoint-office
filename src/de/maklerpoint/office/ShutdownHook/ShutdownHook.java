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

package de.maklerpoint.office.ShutdownHook;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Scheduler.SchedulerHelper;
import de.maklerpoint.office.Session.Tools.SessionTools;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ShutdownHook extends Thread {

    @Override
    public void run(){
        int exitcode = 0;
        Log.logger.fatal("Beende MaklerPoint Office");
        System.out.println("-----Schließe MaklerPoint Office und räume auf------");
        try {
            SessionTools.deleteCurrentSession();
        } catch (SQLException e) {
            System.out.println("Fehler: Konnte Session nicht beenden");
            Log.logger.fatal("Konnte Session nicht beenden.", e);
        }
        
        // Shutting down Plugin system
        // Plugins.ShutdownPluginSystem();

        // Shutting down Scheduler
        SchedulerHelper.shutdownScheduler(true);

        try {
//            ShutdownHook.sleep(1000);
        } catch (Exception e) {
            Log.logger.fatal("Konnte ShutdownHook nicht pausieren", e);
            exitcode = 104;
        }

        System.out.println("-----Aufräumen beendet, schließe(" + exitcode + ")------");
    }
}
