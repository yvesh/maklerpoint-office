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

package de.maklerpoint.office.Kunden.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SearchKunden {

    public static String[] searchFields = new String[]{"betreuer", "kundenNr", "anrede", "titel", "firma", "vorname", "vorname2",
        "nachname", "street", "stadt", "bundesland", "land", "communication1", "communication2", "communication3", "communication4",
        "communication5", "communication6", "custom1", "custom2", "custom3", "comments"
    };

    public static KundenObj[] quickSearch(Connection con, Object value, boolean eigene, int status) throws SQLException {

        for(int i = 0; i < searchFields.length; i++)
        {
            KundenObj[] kunden = SearchKunden.searchKundenObject(DatabaseConnection.open(), searchFields[i], value, eigene, status, 
                    TableValueChooseDialog.ENTHAELT);

            if(kunden != null) {
                System.out.println("Kunde: " + kunden[0]);
                return kunden;
            }
        }

        return null;
    }

    /**
     * 
     * @param con
     * @param value
     * @param eigene
     * @return
     * @throws SQLException
     */

    public static KundenObj[] quickSearchMysql(Connection con, Object value, boolean eigene) throws SQLException    {
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE MATCH(betreuer,titel,firma,vorname,"
                    + "nachname,street,stadt,bundesland,telefon,communication1,"
                    + "communication2,geburtsdatum,beruf,kundenNr,custom1,comments)"
                    + "AGAINST (? IN BOOLEAN MODE) AND betreuer = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE MATCH(betreuer,titel,firma,vorname,"
                    + "nachname,street,stadt,bundesland,telefon,communication1,communication2,"
                    + "geburtsdatum,beruf,kundenNr,custom1,comments) "
                    + "AGAINST (? IN BOOLEAN MODE) AND status = 0";

        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        System.out.println("Value: " + (String) value);
        statement.setString(1, (String) value);

        if(eigene)
            statement.setString(2, BasicRegistry.currentUser.getKennung());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
       
        if(rows == 0) {
            String val = (String) value;
            val = val.replaceAll("\\*", "");

            try {               
                int number = Integer.valueOf(val);
            } catch (NumberFormatException e) {
                entry.close();
                statement.close();
                con.close();
                return null;
            }

            if(eigene)
                sql = "SELECT * FROM kunden WHERE plz "
                    + "LIKE '" + val +  "%' AND betreuer = ? AND status = 0";
            else
                sql = "SELECT * FROM kunden WHERE plz "
                    + "LIKE '" + val +  "%' AND status = 0";

            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);            

            if(eigene)
                statement.setString(1, BasicRegistry.currentUser.getKennung());

            entry = statement.executeQuery();

            entry.last();
            rows = entry.getRow();
            entry.beforeFirst();

            if(rows == 0) {
                 entry.close();
                statement.close();
                con.close();
                return null;
            }
        }
            
        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

//    /**
//     *
//     * @param con
//     * @param value
//     * @param eigene
//     * @return
//     * @throws SQLException
//     */
//
//    public static boolean quickSearchBoolean(Connection con, Object value, boolean eigene) throws SQLException    {
//        String sql = null;
//
//        if(eigene)
//            sql = "SELECT * FROM kunden WHERE MATCH(betreuer,titel,firma,vorname,"
//                    + "nachname,street,stadt,bundesland,telefon,mail,"
//                    + "mobil,geburtsdatum,beruf,kundenNr,custom1,comments)"
//                    + "AGAINST (? IN BOOLEAN MODE) AND betreuer = ? AND status = 0";
//        else
//            sql = "SELECT * FROM kunden WHERE MATCH(betreuer,titel,firma,vorname,"
//                    + "nachname,street,stadt,bundesland,telefon,mail,mobil,"
//                    + "geburtsdatum,beruf,kundenNr,custom1,comments) "
//                    + "AGAINST (? IN BOOLEAN MODE) AND status = 0";
//
//        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        statement.setString(1, (String) value);
//
//        if(eigene)
//            statement.setString(2, BasicRegistry.currentUser.getKennung());
////
//        ResultSet entry = statement.executeQuery();
//
//        entry.last();
//        int rows = entry.getRow();
//        entry.beforeFirst();
//
//
//        System.out.println("Bool Rows: " + rows);
//        entry.next();
//
//        boolean success = false;
//        System.out.println("Success " + success);
//
//        if(!success == false) {
//             try {
//                int number = Integer.valueOf(String.valueOf(value));
//            } catch (NumberFormatException e) {
//                return false;
//            }
//
//            if(eigene)
//                sql = "SELECT * FROM kunden WHERE plz "
//                    + "LIKE ? AND betreuer = ? AND status = 0";
//            else
//                sql = "SELECT * FROM kunden WHERE plz "
//                    + "LIKE ? AND status = 0";
//
//            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            statement.setObject(1, value);
//
//            if(eigene)
//                statement.setString(2, BasicRegistry.currentUser.getKennung());
//
////            entry = statement.executeQuery();
////
////            success = entry.getBoolean(1);
//            success = statement.execute();
//        }
//
//
////        entry.close();
//        statement.close();
//        con.close();
//
//        return success;
//    }


         /**
     *
     * @param con
     * @param field
     * @param value
     * @param eigene
     * @return
     * @throws SQLException
     */

    public static KundenObj[] searchKundenObject(Connection con, String field, Object value, boolean eigene, int status, int operator) throws SQLException {
        String sql = null;

        if(operator == TableValueChooseDialog.ENTHAELT)
        {
            if(eigene && status != -1)
                sql = "SELECT * FROM kunden WHERE " + field +" LIKE '%" + value + "%' AND betreuer = ? AND status = ?";
            else if(status != -1)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '%" + value + "%' AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '%" + value + "%' AND betreuer = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '%" + value + "%'";
            
        } else if (operator == TableValueChooseDialog.BEGINNT_MIT) {
            if(eigene && status != -1)
                sql = "SELECT * FROM kunden WHERE " + field +" LIKE '" + value + "%' AND betreuer = ? AND status = ?";
            else if(status != -1)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '" + value + "%' AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '" + value + "%' AND betreuer = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM kunden WHERE " + field + " LIKE '" + value + "%'";
        } else {
            if(eigene && status != -1)
                sql = "SELECT * FROM kunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND betreuer = ? AND status = ?";
            else if(status != -1)
                sql = "SELECT * FROM kunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM kunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND betreuer = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM kunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' ";
            
        }
    
        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(eigene && status != -1) {
           statement.setString(1, BasicRegistry.currentUser.getKennung());
           statement.setInt(2, status);
        } else if (status == -1) {
            statement.setInt(1, status);
        } else if (eigene) {
            statement.setString(1, BasicRegistry.currentUser.getKennung());
        }

//        System.out.println("Statement: " + statement);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0){
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

     /**
     * 
     * @param con
     * @param field
     * @param value
     * @param eigene
     * @return
     * @throws SQLException
     */

    public static KundenObj[] searchKunden(Connection con, String field, String value, boolean eigene) throws SQLException {
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND betreuer = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND status = 0";

      
        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, field);

        if(eigene)
            statement.setString(2, BasicRegistry.currentUser.getKennung());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * 
     * @param con
     * @param field
     * @param value
     * @param eigene
     * @return
     * @throws SQLException
     */

     public static KundenObj[] searchKundenInt(Connection con, String field, int value, boolean eigene) throws SQLException {
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND betreuer = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND status = 0";


        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, field);

        if(eigene)
            statement.setString(2, BasicRegistry.currentUser.getKennung());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * 
     * @param con
     * @param field
     * @param value
     * @param eigene
     * @return
     * @throws SQLException
     */

     public static KundenObj[] searchKundenTimestamp(Connection con, String field, java.sql.Timestamp value, boolean eigene) throws SQLException {
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND betreuer = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND status = 0";


        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, field);        

        if(eigene)
            statement.setString(2, BasicRegistry.currentUser.getKennung());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }


     /**
      * 
      * @param con
      * @param field
      * @param value
      * @param eigene
      * @return
      * @throws SQLException
      */

    public static KundenObj[] searchKundenBoolean(Connection con, String field, boolean value, boolean eigene) throws SQLException {
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND betreuer = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE ? LIKE '%" + value + "%' AND status = 0";


        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, field);

        if(eigene)
            statement.setString(2, BasicRegistry.currentUser.getKennung());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        kunden = getKundenEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * 
     * @param entry
     * @param rows
     * @return
     * @throws SQLException
     */

    private static KundenObj[] getKundenEntries(ResultSet entry, int rows) throws SQLException {

        KundenObj[] kunden = new KundenObj[rows];

        for(int i = 0; i< rows; i++)
        {
            entry.next();
            kunden[i] = KundenSQLMethods.getKundeEntry(entry);          
        }

        return kunden;        
    }

}
