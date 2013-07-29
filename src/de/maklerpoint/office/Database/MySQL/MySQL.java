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
package de.maklerpoint.office.Database.MySQL;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import de.maklerpoint.office.Database.DatabaseDrivers;
import de.maklerpoint.office.Database.DatabaseHelper;
import de.maklerpoint.office.Database.iDBTools;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MySQL extends Thread implements iDBTools {

    /**
     * Stellt die Verbindung zur Datenbank her
     * TODO irgendwann: Erweitern um Connection pool und Stats
     * @param dbcreds
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection connect(DatabaseHelper dbcreds) throws ClassNotFoundException, SQLException {
        Class.forName(DatabaseDrivers.MySQLDriver);
        return DriverManager.getConnection(dbcreds.getConnectionURL(),
                dbcreds.getUsername(), dbcreds.getPassword());
    }

    /**
     * MySQL Limit
     * @param limit
     * @param offset
     * @return 
     */
    public static String buildMysqlLimit(int limit, int offset) {
        String build = "LIMIT " + offset + ", " + limit;
        return build;
    }

    /**
     * MySQL DataSource aus Config daten
     * @return 
     */
    public static DataSource getMySQLDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(DatabaseConfig.MYSQL_USER);
        dataSource.setPassword(DatabaseConfig.MYSQL_PASSWORD);
        dataSource.setServerName(DatabaseConfig.MYSQL_SERVER);
        if (Config.get("databasePort", null) != null && Config.get("databasePort", null).length() > 0) {
            dataSource.setPort(Integer.valueOf(Config.get("databasePort", null)));
        }
        dataSource.setDatabaseName(DatabaseConfig.MYSQL_DBNAME);
        return dataSource;
    }

    /**
     * Testet die MySQLDatenbankverbindung
     * @param dbcreds
     * @return 
     */
    public static boolean testMySQLConnection(DatabaseHelper dbcreds) {
        try {
            Class.forName(DatabaseDrivers.MySQLDriver);
            Connection con = DriverManager.getConnection(dbcreds.getConnectionURL(), dbcreds.getUsername(), dbcreds.getPassword());
            con.close();
            return true;
        } catch (Exception e) {
            Log.databaselogger.warn("Der Test der MySQL Datenbankverbindung ist fehlgeschlagen", e);
            return false;
        }
    }
}
