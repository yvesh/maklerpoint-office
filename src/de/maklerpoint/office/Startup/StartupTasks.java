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
package de.maklerpoint.office.Startup;

import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.System.OsDetection;
import de.maklerpoint.office.LocalDatabase.CheckLocalDatabase;
import de.maklerpoint.office.LocalDatabase.LocalDatabaseTools;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Lucene.Indexer;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Status;
import javax.swing.SwingWorker;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class StartupTasks {

    public static boolean indexing = true;

    public static void doStartupTasks() {
        OsDetection.getOS();
//        initializeLocalDatabase();
//        SynchronizeLocalDatabase.downloadDatabaseXML();
        loadBenutzer();

        SwingWorker index = new SwingWorker<Void, Void>() {

            public Void doInBackground() {
                Indexer indexer = new Indexer();
                indexer.start();
                return null;
            }

            @Override
            protected void done() {
                try {
                    indexing = false;
                } catch (Exception ignore) {
                }
            }
        };

        index.execute();
        
        SecurityTasks.initializeDefault();
    }

    private static void loadBenutzer() {
        BenutzerRegistry.allUser = BenutzerRegistry.getAllBenutzer(Status.NORMAL);
    }

    /**
     * @deprecated nicht benutzen, killt die Datenbank!!
     */
    public static void checkLocalDatabase() {
        try {
            if (CheckLocalDatabase.exists() == false) {
                LocalDatabaseTools.reinitializeLocalDatabase();
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Die lokale Datenbank nicht initialisieren", e);
            ShowException.showException("Die lokale Datenbank konnte nicht initialisiert werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte lokale Datenbank nicht initialisieren");
        }
    }
}
