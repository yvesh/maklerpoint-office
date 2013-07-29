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
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class LocalDatabaseTools {

/**
     * Splits an sql file into it's single statements
     * could be used with executeSQL() command
     * @param file
     * @return String[] sql Statements
     * @throws IOException
     */

    public static String[] getSQLStatementsFromFile(File file) throws IOException {
        final StringWriter buffer = new StringWriter();
        Reader reader = new BufferedReader(new FileReader(file));

        ArrayList statements = new ArrayList();
        // the unicode value for ';' is 59
        final int semi_colon = 59;
        // Suck up the contents of the temporary file
        for (int read = reader.read(); read != -1; read = reader.read())
        {
            if (read == semi_colon) {
                statements.add(buffer.toString());
                buffer.getBuffer().delete(0,
                buffer.getBuffer().length());
            } else {
                buffer.write(read);
            }
        }
        buffer.close();
        reader.close();

        return (String[]) statements.toArray(new
        String[statements.size()]);
    }

    /**
     * Executes the given splitted SQL statements, returns the count of statements executed
     * @param conn (Database Connection, you can get the jadif one with DatabaseConnection.getJadifConnection)
     * @param statements (splitted sql statements)
     * @return count
     * @throws SQLException
     */

    public static int executeSQL(Connection conn, String[] statements) throws SQLException
    {
        int count = 0;
        conn.setAutoCommit(false);
        // create an sql statement and create the tables,.
        Statement stmt = conn.createStatement();
        for (int i = 0; i < statements.length; i++) {
            String sql_statement = statements[i];
            count += stmt.executeUpdate(sql_statement);
        }
        conn.commit();
        conn.setAutoCommit(true);
        stmt.close();

        return count;
    }

    /**
     * Drops the complete database and recreates it.
     * Caution: Destroys all history, config parameter etc.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */

    public static void reinitializeLocalDatabase() throws ClassNotFoundException, SQLException,
                              InstantiationException, IllegalAccessException, IOException{
        Connection con = DatabaseConnection.openLocalDB();

        Statement statement = con.createStatement();

        for(int i = 0; i < DatabaseConfig.TABLES.length; i++) {
            if(con.getMetaData().getTables(null, null, DatabaseConfig.TABLES[i].toUpperCase(), null).next()){
                statement.execute("DROP TABLE " + DatabaseConfig.TABLES[i]);
            }
        }

        statement.close();
        con.close();

        LocalInitialCreate.createDB();
    }


}
