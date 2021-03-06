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

package de.maklerpoint.office.Backup;

import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BackupDatabase {

    private String filename;

    /**
     * 
     * @param file
     */

    public BackupDatabase(String file) {
        this.filename = file;
    }

    /**
     * 
     * @throws IOException
     * @throws SQLException
     */

    public void backup() throws IOException, SQLException {
        File destination = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));

        if(Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.MYSQL)
        {
            MySQLDump myDump = new MySQLDump();

            writer.write(myDump.getHeader());

            for(int i = 0; i < DatabaseConfig.TABLES.length; i++) {
                writer.write(myDump.dumpCreateTable(DatabaseConfig.TABLES[i]));
                myDump.dumpTable(writer, DatabaseConfig.TABLES[i]);
            }

            writer.flush();
            writer.close();
            myDump.cleanup();
        }
    }

}
