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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DataReader;
import org.apache.ddlutils.io.DatabaseDataIO;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DdlUtilsHelper {

    /**
     * 
     * @param db
     * @param fileName
     */

    public static void writeDatabaseToXML(Database db, String fileName)  {        
        new DatabaseIO().write(db, fileName);
    }

    /**
     * 
     * @param dataSource
     * @param model
     * @param filename
     * @throws FileNotFoundException
     */

    public static void writeDatabaseDataToXML(DataSource dataSource, Database model, String filename) throws FileNotFoundException {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        new DatabaseDataIO().writeDataToXML(platform, model, new FileOutputStream(filename), null);
    }


    /**
     * 
     * @param fileName
     * @return
     */

    public static Database readDatabaseFromXML(String fileName) {
        return new DatabaseIO().read(fileName);
    }

    /**
     * 
     * @param dataSource
     * @param dbname
     * @return
     */

    public static Database readDatabase(DataSource dataSource, String dbname)
    {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        return platform.readModelFromDatabase(dbname);
    }

    /**
     * 
     * @param dataSource
     * @param con
     * @param dbname
     * @return
     */

    public static Database readDatabase(DataSource dataSource, Connection con, String dbname)
    {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        return platform.readModelFromDatabase(con, dbname);
    }

    /**
     * 
     * @param dataSource
     * @param targetModel
     * @param alterDb
     */
    
    public static void copyDatabase(Connection con, DataSource dataSource, Database targetModel, boolean alterDb) throws SQLException
    {
        Platform platform = PlatformFactory.createNewPlatformInstance("derby");
        platform.createDatabase("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:maklerpointDB", null, null, null);

        if (alterDb)
        {
            platform.alterTables(targetModel, false);
        }
        else
        {           
            platform.createTables(con, targetModel, true, true);
            con.setAutoCommit(true);
        }
        
    }


    public static void copyDatabaseData(DataSource dataSource, Database dataModel, String sourcefile) {
       Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
       platform.setIdentityOverrideOn(false);
       DatabaseDataIO dio = new DatabaseDataIO();

       DataReader daread = dio.getConfiguredDataReader(platform, dataModel);
       daread.getSink().start();
       dio.writeDataToDatabase(daread, new File(sourcefile).getAbsolutePath());

       daread.getSink().end();
    }
    /**
     * 
     * @param dataSource
     * @param database
     * @param table
     * @param data
     */

    public static void insertData(DataSource dataSource, Database database, String table, Object[][] data)
    {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);

        // "author" is a table of the model
        DynaBean tab = database.createDynaBeanFor(table, false);

        // "name" and "whatever" are columns of table "author"

        for(int i = 0; i < data.length; i++)
        {
             tab.set((String) data[i][0],data[i][1]);
        }

        platform.insert(database, tab);
    }

    public void dumpDatabase(DataSource dataSource, Database database) {
        
        
    }
}
