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
package de.maklerpoint.office.Database;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.MySQL.MySQL;
import de.maklerpoint.office.Database.PostgreSQL.PostgreSQL;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Configuration.ConfigurationDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.LocalDatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DatabaseConnection extends Thread {

    public static Connection open() {
        if (Config.getConfigBoolean("offlineModus", false) || Config.getConfigInt("databaseType",
                DatabaseTypes.MYSQL) == DatabaseTypes.EMBEDDED_DERBY) {
            return openLocalDB();
        } else {
            return openExternal();
        }
    }

    /**
     * TODO: Vereinfachen
     * @return
     */
    public static String getURL() {
        String url = null;

        if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MYSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:mysql://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:mysql://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }

            //"jdbc:mysql://localhost/acyrancecrm";
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.POSTGRESQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:posgresql://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:posgresql://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //"jdbc:posgresql://localhost/acyrancecrm";
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.ORACLE) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:oracle:thin:@//" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:oracle:thin:@//" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //"jdbc:oracle:thin:@//server.local:1521/prod";
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MSSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //jdbc:jtds:sqlserver://;
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MSSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //jdbc:jtds:sqlserver://;
        }

//        System.out.println("URL: " + url);

        return url;
    }

    public static String getURL(int type) {
        String url = null;

        if (type == DatabaseTypes.MYSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:mysql://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:mysql://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }

            //"jdbc:mysql://localhost/acyrancecrm";
        } else if (type == DatabaseTypes.POSTGRESQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:posgresql://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:posgresql://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //"jdbc:posgresql://localhost/acyrancecrm";
        }
        if (type == DatabaseTypes.ORACLE) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:oracle:thin:@//" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:oracle:thin:@//" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //"jdbc:oracle:thin:@//server.local:1521/prod";
        }
        if (type == DatabaseTypes.MSSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //jdbc:jtds:sqlserver://;
        }
        if (type == DatabaseTypes.MSSQL) {
            if (Config.get("databasePort", null) == null || Config.get("databasePort", null).length() < 1) {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + "/" + Config.get("databaseName", "maklerpointdb");
            } else {
                url = "jdbc:jtds:sqlserver://" + Config.get("databaseServer", "localhost") + ":" + Config.get("databasePort", null)
                        + "/" + Config.get("databaseName", "maklerpointdb");
            }
            //jdbc:jtds:sqlserver://;
        }

//        System.out.println("URL: " + url);

        return url;
    }
    
    public static Connection openExternal() {
        String url = getURL();

        Connection conn = null;
        try {
            if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MYSQL) {
                conn = (new MySQL()).connect(new DatabaseHelper(url, Config.get("databaseUsername", null),
                        Config.get("databasePassword", null)));
            } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.POSTGRESQL) {
                conn = (new PostgreSQL()).connect(new DatabaseHelper(url, Config.get("databaseUsername", null),
                        Config.get("databasePassword", null)));
            } else {
                Log.databaselogger.warn("Keine Datenbank ausgewählt, falle zum Standard zurück (PostgreSQL)");
                conn = (new MySQL()).connect(new DatabaseHelper(url, Config.get("databaseUsername", null),
                        Config.get("databasePassword", null)));
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte keine Verbindung zur Datenbank herstellen. Starte DB-Konfiguration", e);
            JOptionPane.showMessageDialog(null, "MaklerPoint Office konnte keine Verbindung zur Datenbank herstellen.\n"
                    + "Bitte überprüfen Sie Ihre Datenbankeinstellungen und die Details der Fehlermeldung.\n"
                    + "Die Datenbankeinstellungen werden jetzt geöffnet.",
                    "Keine Verbindung zur Datenbank", JOptionPane.ERROR_MESSAGE);

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            settingsBox = new ConfigurationDialog(mainFrame, true);
            settingsBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(settingsBox);
        } catch (Exception ex) {
            Log.databaselogger.fatal("Konnte keine Verbindung zur Datenbank herstellen.", ex);
            ShowException.showException("Es konnte keine Verbindung zur Datenbank hergestellt werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator. Die Anwendung wird beendet.",
                    ExceptionDialogGui.LEVEL_FATAL, ex, "Schwerwiegend: Keine Datenbank Verbindung");
        }
//        concount++;
//        condif = concount - conclose;
//        System.out.println("Conncount: " + concount + " | Conclose: " + conclose + " | Conn open: " + condif);
        return conn;
    }

    public static boolean testConnection() {
        String url = getURL();

        if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MYSQL) {
            return MySQL.testMySQLConnection(new DatabaseHelper(url, Config.get("databaseUsername", null), Config.get("databasePassword", null)));
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.POSTGRESQL) {
            return PostgreSQL.testPostgreSQLConnection(new DatabaseHelper(url, Config.get("databaseUsername", null), Config.get("databasePassword", null)));
        } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.EMBEDDED_DERBY || Config.getConfigBoolean("offlineModus", false)) {
            return testLocalConnection();
        } else {
            Log.databaselogger.warn("Keine Datenbank ausgewählt, teste lokale MySQL Verbindung");
            return MySQL.testMySQLConnection(new DatabaseHelper(url, Config.get("databaseUsername", null), Config.get("databasePassword", null)));
        }
    }

    public static boolean testConnection(int type) {
        String url = getURL(type);

        if (type == DatabaseTypes.MYSQL) {
            return MySQL.testMySQLConnection(new DatabaseHelper(url, Config.get("databaseUsername", null), Config.get("databasePassword", null)));
        } else if (type == DatabaseTypes.POSTGRESQL) {
            return PostgreSQL.testPostgreSQLConnection(new DatabaseHelper(url, Config.get("databaseUsername", null), Config.get("databasePassword", null)));
        } else if (type == DatabaseTypes.EMBEDDED_DERBY || Config.getConfigBoolean("offlineModus", false)) {
            return testLocalConnection();
        } else {
            Log.databaselogger.warn("Keine Datenbank ausgewählt, teste lokale MySQL Verbindung");
            return false;
        }
    }

    public static DataSource getDataSource() {
        if (Config.getConfigBoolean("offlineModus", false) || Config.getConfigInt("databaseType",
                DatabaseTypes.MYSQL) == DatabaseTypes.EMBEDDED_DERBY) {
            return getLocalDataSource();
        } else {
            return getExternDataSource();
        }
    }

    /**
     * 
     * @return
     */
    public static DataSource getExternDataSource() {
        DataSource ds = null;
        try {
            if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MYSQL) {
                ds = MySQL.getMySQLDataSource();
            } else if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.POSTGRESQL) {
                ds = (DataSource) PostgreSQL.getPostgreDataSource();
            } else {
                ds = MySQL.getMySQLDataSource();
                Log.databaselogger.warn("No Database specified, falling back to mysql");
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte keine Verbindung zur Datenbank (DataSource) herstellen.", e);
            ShowException.showException("Es konnte keine Verbindung zur Datenbank (DataSource) hergestellt werden. Bitte wenden Sie sich an Ihren Systemadministrator. Die Anwendung wird beendet.",
                    ExceptionDialogGui.LEVEL_FATAL, e, "Schwerwiegend: Keine DataSource Verbindung");
        }
        return ds;
    }

    /**
     * Todo: Needs updating.. Verschlüsselung etc
     */
    public static DataSource getLocalDataSource() {
        try {
//             Context ctx = new InitialContext();
//             DataSource ds = (DataSource) ctx.lookup(LocalDatabaseConfig.DB_NAME);
            org.apache.derby.jdbc.EmbeddedDataSource ds = new org.apache.derby.jdbc.EmbeddedDataSource();
            ds.setDatabaseName(LocalDatabaseConfig.DB_NAME);
            ds.setConnectionAttributes("create=true");

            return ds;

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte keine Verbindung zur lokalen Datenbank (DataSource) herstellen.", e);
            ShowException.showException("Es konnte keine Verbindung zur lokalen Datenbank (DataSource) hergestellt werden."
                    + " Bitte wenden Sie sich an Ihren Systemadministrator. Die Anwendung wird beendet.",
                    ExceptionDialogGui.LEVEL_FATAL, e, "Schwerwiegend: Keine DataSource Verbindung");
        }

        return null;
    }

    /**
     * 
     * @return
     */
    public static Connection openLocalDB() {
        try {
            Class.forName(DatabaseDrivers.DERBY);
            Connection con = DriverManager.getConnection(LocalDatabaseConfig.PROTOCOL + LocalDatabaseConfig.DB_NAME + ";create=true");
            con.setAutoCommit(true);

            return con;
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte keine Verbindung zur lokalen Datenbank herstellen ", e);
            ShowException.showException("Es konnte keine Verbindung zur lokalen Datenbank hergestellt werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator. Die Anwendung wird beendet.",
                    ExceptionDialogGui.LEVEL_FATAL, e, "Schwerwiegend: Keine lokale Datenbank Verbindung");
        }

        return null;
    }

    public static boolean testLocalConnection() {
        try {
            Class.forName(DatabaseDrivers.DERBY);
            Connection con = DriverManager.getConnection(LocalDatabaseConfig.PROTOCOL + LocalDatabaseConfig.DB_NAME + ";create=true");
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
//        conclose++;
//        condif = concount - conclose;
        } catch (Exception e) {
            Log.databaselogger.warn("Error closing connection", e);
        }
    }
    private static JDialog settingsBox = null;
}
