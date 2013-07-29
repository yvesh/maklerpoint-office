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

package de.maklerpoint.office.System.Configuration;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Database.DatabaseTypes;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class DatabaseConfig {

    public static int DATABASE = Config.getConfigInt("databaseType", DatabaseTypes.MYSQL);

    public static String MYSQL_USER = Config.get("databaseUsername", null);
    public static String MYSQL_PASSWORD = Config.get("databasePassword", null);

    public static String MYSQL_SERVER = Config.get("databaseServer", "localhost");
    public static String MYSQL_PORT = Config.get("databasePort", null);

    public static String MYSQL_URL = DatabaseConnection.getURL();

    public static String MYSQL_DBNAME = Config.get("databaseName", "maklerpointdb");

    public static String DBNAME = Config.get("databaseName", "maklerpointdb");

    public static String POSTGRESQLUSER = Config.get("databaseUsername", null);
    public static String POSTGRESQLPASSWORD = Config.get("databasePassword", null);
    public static String POSTGRESQLURL = DatabaseConnection.getURL();

    public final static String[] TABLES = new String[]{"aufgaben", "backup", "bankkonten",         
        "benutzer", "beratungsprotokolle", "briefe", "briefe_cat", "dokumente", "firmenkunden",
        "firmen_ansprechpartner",
        "kinder", "kunden", "kunden_betreuung", "kunden_grp", "kunden_zusatzadressen", "mandanten",
        "messages", "nachrichten", "newsletter", "newsletter_sub", "notizen", "produkte",       
        "session", "sparten", "termine",
        "textbausteine", "textbausteine_grp", "versicherer", "versicherer_all", "vertraege",
        "vertraege_grp",
        "waehrungen", "webconfig",
        "wiedervorlagen", "wissendokumente"
    };
    
    public final static String[] TABLES_INDEX = new String[]{"aufgaben", "bankkonten",         
        "benutzer", "beratungsprotokolle", "briefe", "dokumente", "firmenkunden",
        "firmen_ansprechpartner",
        "kinder", "kunden", "kunden_betreuung", "kunden_grp", "kunden_zusatzadressen", "mandanten",
        "messages", "nachrichten", "newsletter", "newsletter_sub", "notizen", "produkte",       
         "termine",
        "textbausteine", "versicherer", "versicherer_all", "vertraege",               
        "wissendokumente"
    };

    public static String getDatabaseName() {
        return getDatabaseName(Config.getConfigInt("databaseType", DatabaseTypes.MYSQL));
    }

    public static String getDatabaseName(int dbtype) {
        switch (dbtype) {
            
            case DatabaseTypes.MYSQL:
                return "MySQL";

            case DatabaseTypes.POSTGRESQL:
                return "PostgreSQL";

            case DatabaseTypes.MSSQL:
                return "Microsoft MSSQL";

            case DatabaseTypes.ORACLE:
                return "Oracle DB";

            case DatabaseTypes.DERBY:
                return "Apache Derby";

            case DatabaseTypes.EMBEDDED_DERBY:
                return "Derby (embedded)";

            default:
                return "Unbekannt";
        }
    }
}
