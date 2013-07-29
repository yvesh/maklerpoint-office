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
package de.maklerpoint.office.Database.PostgreSQL;

import de.maklerpoint.office.Database.DatabaseDrivers;
import de.maklerpoint.office.Database.DatabaseHelper;
import de.maklerpoint.office.Database.iDBTools;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.activation.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc2.optional.SimpleDataSource;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class PostgreSQL extends Thread implements iDBTools {
    
    /**
     * Stellt die Verbindung zur Datenbank her
     * TODO irgendwann: Erweitern um Connection pool und Stats
     * @param dbcreds
     * @return
     * @throws Exception 
     */
    public Connection connect(DatabaseHelper dbcreds) throws Exception {
        Class.forName(DatabaseDrivers.PostgreSQLDriver);
        return DriverManager.getConnection(dbcreds.getConnectionURL(), 
                dbcreds.getUsername(), dbcreds.getPassword());
    }

    /**
     * Limit in Postgreversion
     * @param limit
     * @param offset
     * @return 
     */
    public static String buildPostgreLimit(int limit, int offset) {
        String build = "LIMIT " + limit + " OFFSET " + offset;
        return build;
    }

    /**
     * PostgreSQL DataSource aus Config daten
     * @return 
     */
    public static DataSource getPostgreDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(DatabaseConfig.POSTGRESQLUSER);
        ds.setPassword(DatabaseConfig.POSTGRESQLPASSWORD);
        ds.setServerName(DatabaseConfig.POSTGRESQLURL);
        if (Config.get("databasePort", null) != null && Config.get("databasePort", null).length() > 0) {
            ds.setPortNumber(Integer.valueOf(Config.get("databasePort", null)));
        }
        ds.setDatabaseName(DatabaseConfig.MYSQL_DBNAME);

        return (DataSource) ds;
    }

    /**
     * Testet die PosgreSQLDatenbankverbindung
     * @param dbcreds
     * @return 
     */
    public static boolean testPostgreSQLConnection(DatabaseHelper dbcreds) {
        try {
            Class.forName(DatabaseDrivers.PostgreSQLDriver);
            Connection con = DriverManager.getConnection(dbcreds.getConnectionURL(), dbcreds.getUsername(), dbcreds.getPassword());
            con.close();
            return true;
        } catch (Exception e) {
            Log.databaselogger.warn("Der Test der PostgreSQL Datenbankverbindung ist fehlgeschlagen", e);
            return false;
        }
    }
}
