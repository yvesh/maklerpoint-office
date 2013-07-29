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

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import de.maklerpoint.office.System.Version;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;


/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MySQLDump {

    private String schema = null;
    private Connection conn = null;
    private DatabaseMetaData databaseMetaData;
    private String databaseProductVersion = null;
    private int databaseProductMajorVersion = 0;
    private int databaseProductMinorVersion = 0;
    private String mysqlVersion = null;


    /**
    * Create a new instance of MySQLDump using default database.
    *
    * @param  host      MySQL Server Hostname
    * @param  username  MySQL Username
    * @param  password  MySQL Password
    */
    public MySQLDump() throws SQLException {
        connect();
    }


    /**
    * Connect to MySQL server
    *
    * @param  host      MySQL Server Hostname
    * @param  username  MySQL Username
    * @param  password  MySQL Password
    * @param  db        Default database
    */
    private void connect() throws SQLException{
        
        conn = DatabaseConnection.open();
        databaseMetaData = conn.getMetaData();
        databaseProductVersion = databaseMetaData.getDatabaseProductVersion();
        databaseProductMajorVersion = databaseMetaData.getDatabaseMajorVersion();
        databaseProductMinorVersion = databaseMetaData.getDatabaseMinorVersion();
        schema = DatabaseConfig.DBNAME;

    }


    public File dumpAllDatabases(){
        return null;
    }

    public String dumpCreateDatabase(String database) {
        String createDatabase = null;
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW CREATE DATABASE `" + database + "`");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createDatabase = rs.getString("Create Database") + ";";
            }
        } catch (SQLException e) {

        }
        return createDatabase;
    }

    public File dumpDatabase(String database){
        return null;
    }

    public File dumpAllTables(String database){
        return null;
    }

    public String dumpCreateEvent(String schema, String event) {
        String createEvent = "--\n-- Event structure for event `" + event + "`\n--\n\n";
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW CREATE EVENT " + schema + "." + event);
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createEvent += rs.getString("Create Event") + ";";
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        return createEvent;
    }

    public String dumpCreateTable(String table) {
       return dumpCreateTable(schema,table);
    }

    public String dumpCreateTable(String schema, String table) {
        String createTable = "\n\n--\n-- Table structure for table `" + table + "`\n--\n\n";
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW CREATE TABLE `" + schema + "`.`" + table + "`");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createTable += rs.getString("Create Table") + ";";
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
            createTable = "";
        }
        return createTable;
    }

    public String dumpCreateView(String view) {
       return dumpCreateView(schema,view);
    }

    public String dumpCreateView(String schema, String view) {
        String createView = "--\n-- View definition for view `" + view + "`\n--\n\n";
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW CREATE VIEW `" + schema + "`.`" + view + "`");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createView += rs.getString("Create View") + ";";
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
            createView = "";
        }
        return createView;
    }

    public String dumpCreateEvent(String event) {
       return dumpCreateEvent(schema,event);
    }

    public String dumpCreateRoutine(String schema, String routine) {
        String createRoutine = "--\n-- Routine structure for routine `" + routine + "`\n--\n\n";
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SELECT ROUTINE_DEFINITION FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME='" + routine + "'");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createRoutine += rs.getString("ROUTINE_DEFINITION") + ";";
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
            createRoutine = "";
        }
        return createRoutine;
    }

    public String dumpCreateRoutine(String routine) {
       return dumpCreateRoutine(schema,routine);
    }

    public String dumpCreateTrigger(String schema, String trigger) {
        String createTrigger = "--\n-- Trigger structure for trigger `" + trigger + "`\n--\n\n";
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW CREATE TRIGGER " + schema + "." + trigger);
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                createTrigger += rs.getString("SQL Original Statement") + ";";
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
            createTrigger = "";
        }
        return createTrigger;
    }

    public String dumpCreateTrigger(String trigger) {
       return dumpCreateTrigger(schema,trigger);
    }


    public void dumpTable(BufferedWriter out, String table) throws SQLException, IOException{
        
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SELECT /*!40001 SQL_NO_CACHE */ * FROM `" + table + "`");
            ResultSet rs = s.getResultSet();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            if (rs.last()){
                out.write("--\n-- Dumping data for table `" + table + "`\n--\n\n");
                rs.beforeFirst();
            }
            int columnCount = rsMetaData.getColumnCount();
            String prefix = new String("INSERT INTO `" + table + "` (");
            for (int i = 1; i <= columnCount; i++) {
                if (i == columnCount){
                    prefix += rsMetaData.getColumnName(i) + ") VALUES(";
                }else{
                    prefix += rsMetaData.getColumnName(i) + ",";
                }
            }
            String postfix = new String();
            int count = 0;
            while (rs.next())
            {
                postfix = "";
                for (int i = 1; i <= columnCount; i++) {
                    if (i == columnCount){
                        //System.err.println(rs.getMetaData().getColumnClassName(i));
                        postfix += "'" + rs.getString(i) + "');\n";
                    }else{
                        //System.err.println(rs.getMetaData().getColumnTypeName(i));
                        if (rs.getMetaData().getColumnTypeName(i).equalsIgnoreCase("LONGBLOB")){
                            try{
                                postfix += "'" + escapeString(rs.getBytes(i)).toString() + "',";
                            }catch (Exception e){
                                postfix += "NULL,";
                            }
                        }else{
                            try{
                                postfix += "'" + escapeString(rs.getBytes(i)).toString() + "',";
                            }catch (Exception e){
                                postfix += "NULL,";
                            }
                    }   }
                }
                out.write(prefix + postfix);
                ++count;
            }
            rs.close ();
            s.close();

    }

    public Map<String, String> dumpGlobalVariables() {
        Map<String, String> variables = new TreeMap<String, String>();
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW GLOBAL VARIABLES");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                variables.put(rs.getString(1),rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        return variables;
    }

    public File dumpAllViews(String database) {
        return null;
    }

    public File dumpView(String view) {
        return null;
    }

    public ArrayList<String> listSchemata() {
        ArrayList<String> schemata = new ArrayList<String>();
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SHOW DATABASES");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                //Skip Information Schema
                if (!rs.getString("Database").equalsIgnoreCase("information_schema")) {
                    schemata.add(rs.getString("Database"));
                }
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return schemata;
    }

    public ArrayList<String> listRoutines(String schema) {
        ArrayList<String> routines = new ArrayList<String>();
        //Triggers were included beginning with MySQL 5.0.2
        if (databaseProductMajorVersion < 5){
            return routines;
        }
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA='" + schema + "'");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                routines.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return routines;
    }

    public ArrayList<String> listTriggers(String schema) {
        ArrayList<String> triggers = new ArrayList<String>();
        //Triggers were included beginning with MySQL 5.0.2
        if (databaseProductMajorVersion < 5){
            return triggers;
        }
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.executeQuery ("SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='" + schema + "'");
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                triggers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return triggers;
    }

    public ArrayList<String> listGrantTables() {
        ArrayList<String> grantTables = new ArrayList<String>();
        grantTables.add("user");
        grantTables.add("db");
        grantTables.add("tables_priv");
        grantTables.add("columns_priv");
        //The procs_priv table exists as of MySQL 5.0.3.
        if (databaseProductMajorVersion > 4){
            grantTables.add("procs_priv");
        }
        return grantTables;
    }

    public ArrayList<String> listTables(String schema) {
        ArrayList<String> tables = new ArrayList<String>();
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Version 5 upwards use information_schema
            if (databaseProductMajorVersion < 5){
                s.executeQuery ("SHOW TABLES FROM `" + schema + "`");
            }else{
                s.executeQuery ("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = '" + schema + "'");
            }
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return tables;
    }

    /**
    * List views
    *
    * @param  schema        Schema to list views for
    */
    public ArrayList<String> listViews(String schema) {
        ArrayList<String> views = new ArrayList<String>();
        try{
            Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Version 5 upwards use information_schema
            if (databaseProductMajorVersion < 5){
                //Views were introduced in version 5.0.1
                return views;
            }else{
                s.executeQuery ("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA = '" + schema + "'");
            }
            ResultSet rs = s.getResultSet ();
            while (rs.next ())
            {
                views.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return views;
    }

    public ArrayList<String> listEvents(String schema) {
        ArrayList<String> events = new ArrayList<String>();
        try{
            //Version 5 upwards use information_schema
            if (databaseProductMajorVersion == 5 && databaseProductMinorVersion == 1 ){
                Statement s = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                s.executeQuery ("SELECT EVENT_NAME FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_SCHEMA='" + schema + "'");
                ResultSet rs = s.getResultSet ();
                while (rs.next ())
                {
                    events.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

        return events;
    }

    public String getSchema(){
        return schema;
    }

    public void setSchema(String schema){
        this.schema = schema;
    }



    /**
    * Escape string ready for insert via mysql client
    *
    * @param  bIn       String to be escaped passed in as byte array
    * @return bOut      MySQL compatible insert ready ByteArrayOutputStream
    */
    public ByteArrayOutputStream escapeString(byte[] bIn){
        int numBytes = bIn.length;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes+ 2);
        for (int i = 0; i < numBytes; ++i) {
            byte b = bIn[i];

            switch (b) {
            case 0: /* Must be escaped for 'mysql' */
                    bOut.write('\\');
                    bOut.write('0');
                    break;

            case '\n': /* Must be escaped for logs */
                    bOut.write('\\');
                    bOut.write('n');
                    break;

            case '\r':
                    bOut.write('\\');
                    bOut.write('r');
                    break;

            case '\\':
                    bOut.write('\\');
                    bOut.write('\\');

                    break;

            case '\'':
                    bOut.write('\\');
                    bOut.write('\'');

                    break;

            case '"': /* Better safe than sorry */
                    bOut.write('\\');
                    bOut.write('"');
                    break;

            case '\032': /* This gives problems on Win32 */
                    bOut.write('\\');
                    bOut.write('Z');
                    break;

            default:
                    bOut.write(b);
            }
        }
        return bOut;
    }

    public String getHeader(){
        //return Dump Header
        return "-- MaklerPoint MySQL Dump " + Version.version + " " + Version.version_add + "\n--\n-- Database name: " + DatabaseConfig.DBNAME + "\n-- ------------------------------------------------------\n-- Server Version: " + databaseProductVersion + "\n--";
    }  

    public int cleanup(){
        try
        {
            conn.close ();           
        }
        catch (Exception e) { /* ignore close errors */ }
        return 1;
    }



}
