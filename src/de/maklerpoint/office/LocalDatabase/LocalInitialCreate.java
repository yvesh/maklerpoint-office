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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class LocalInitialCreate {

    public static void createDB() throws IOException, SQLException {
        File dbfile = new File("includes" + File.separatorChar + "derby.sql");

        Connection con = DatabaseConnection.openLocalDB();

        if(dbfile.exists()) {
            String[] sql_statements = LocalDatabaseTools.getSQLStatementsFromFile(dbfile);
            LocalDatabaseTools.executeSQL(con, sql_statements);
        } else {
            
        }

        con.close();
    }

}
