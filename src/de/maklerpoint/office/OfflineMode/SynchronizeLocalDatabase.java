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

package de.maklerpoint.office.OfflineMode;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.LocalDatabase.DdlUtilsHelper;
import de.maklerpoint.office.Logging.Log;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.task.WriteDataToFileCommand;
import org.apache.derby.tools.dblook;
import org.apache.log4j.Level;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SynchronizeLocalDatabase {

    public static void synchronizeDatabase() {
        Connection con = DatabaseConnection.openLocalDB();

        String filename = "includes" + File.separatorChar + "localstorage" +
                File.separatorChar + "xml" + File.separatorChar + "ldb.xml";

        String filenamedata = "includes" + File.separatorChar + "localstorage" +
                File.separatorChar + "xml" + File.separatorChar + "ldbdata.xml";

        DataSource ds = DatabaseConnection.getExternDataSource();

        Database db = DdlUtilsHelper.readDatabase(ds, null);

        // Export als xml datei .. TODO Verschlüsselung hinzufügen
        DdlUtilsHelper.writeDatabaseToXML(db, filename);

        try {
            DdlUtilsHelper.writeDatabaseDataToXML(ds, db, filenamedata);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte die externe Datenbank nicht auslesen", e);
            ShowException.showException("Konnte lokale Datenbanke nicht synchronisieren.",
                ExceptionDialogGui.LEVEL_WARNING, e,
                "Schwerwiegend: Konnte externe Datenbank nicht auslesen (Sync).");
        }

        DataSource localDs = DatabaseConnection.getLocalDataSource();
        Database localDB = DdlUtilsHelper.readDatabase(localDs, "mp");

        try {
            // Kopiere Datenbank
            DdlUtilsHelper.copyDatabase(con, localDs, db, false);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte lokale Datenbank nicht synchronisieren", e);
            ShowException.showException("Konnte lokale Datenbank nicht synchronisieren",
                ExceptionDialogGui.LEVEL_WARNING, e,
                "Schwerwiegend: Synchronisierungsfehler.");
        }
        
//        DdlUtilsHelper.copyDatabaseData(ds, db, filenamedata);
        DdlUtilsHelper.copyDatabaseData(localDs, localDB, filenamedata);
//
//        WriteDataToFileCommand wdf = new WriteDataToFileCommand();
//        wdf.setOutputFile(new File(filenamedata));

        try {
            if(Log.databaselogger.isDebugEnabled()) {
                DatabaseMetaData dbmd = con.getMetaData();

//              Specify the type of object; in this case we want tables
                String[] types = {"TABLE"};
                ResultSet resultSet = dbmd.getTables(null, null, "%", types);

                Log.databaselogger.debug("Überprüfe lokale Datenbank");

                // Get the table names
                while (resultSet.next()) {
                    // Get the table name
                    String tableName = resultSet.getString(3);

                    // Get the table's catalog and schema names (if any)
                    String tableCatalog = resultSet.getString(1);
                    String tableSchema = resultSet.getString(2);

                    Log.databaselogger.debug("Tabellen Name: " + tableName);
                    Log.databaselogger.debug("Tabellen Katalog: " + tableCatalog);
                    Log.databaselogger.debug("Tabellen Schema: " + tableSchema);
                }
                
                String sql = "SELECT * FROM kunden";

                PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ResultSet entry = statement.executeQuery();

                entry.last();
                int rows = entry.getRow();
                entry.beforeFirst();

                if(rows == 0) {
                    Log.databaselogger.debug("Keine Kunden in der lokalen Datenbank! [Möglicher Fehler]");
                    return;
                }

                for(int i = 0; i < rows; i++)
                {
                    entry.next();
                    Log.databaselogger.debug("---Datensatz " + i + "---");
                    Log.databaselogger.debug("Id: " + entry.getInt("id"));
                    Log.databaselogger.debug("Name: " + entry.getString("nachname"));
                }

                statement.close();                
                con.close();
            }

        } catch (Exception e) {
            Log.databaselogger.warn("Fehler beim testen der lokalen Datenbank.", e);
        }

    }

}
