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

package de.maklerpoint.office.LocalDatabase;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class CheckLocalDatabase {

    public static boolean exists() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Connection con = DatabaseConnection.openLocalDB();

        for(int i = 0; i < DatabaseConfig.TABLES.length; i++) {
            if(!con.getMetaData().getTables(null, null, DatabaseConfig.TABLES[i].toUpperCase(), null).next()){
                Log.databaselogger.warn("Die Tabelle \"" + DatabaseConfig.TABLES[i] + "\" existiert nicht in der lokalen Datenbank.");
                con.close();
                return false;
            }
        }

        con.close();
        return true;
    }

}
