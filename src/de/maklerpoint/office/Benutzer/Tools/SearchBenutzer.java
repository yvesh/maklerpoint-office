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

package de.maklerpoint.office.Benutzer.Tools;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class SearchBenutzer {

    public static String[] searchFields = new String[] {"kennung", "vorname", "nachname", "firma",
        "strasse","ort", "addresseZusatz", "land", "telefon","email","mobil","fax",
        "telefon2","addresseZusatz","addresseZusatz2","custom1","custom2","comments"
    };


    public static BenutzerObj[] quickSearch(Connection con, Object value) throws SQLException {

        for(int i = 0; i < searchFields.length; i++)
        {
            BenutzerObj[] bn = searchBenutzerObject(con, searchFields[i], value);
            if(bn != null)
                return bn;
        }

        return null;
    }


    public static BenutzerObj[] quickSearchMySQL(Connection con, Object value) throws SQLException    {
        String sql = null;

        sql = "SELECT * FROM benutzer WHERE MATCH(kennung,vorname,nachname,firma,"
                    + "strasse,ort,telefon,email,mobil,fax,"
                    + "telefon2,addresseZusatz,addresseZusatz2,custom1,custom2,comments)"
                    + "AGAINST (? IN BOOLEAN MODE) AND status = 0";

        BenutzerObj[] benutzer = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        System.out.println("Value: " + (String) value);
        statement.setString(1, (String) value);


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

            sql = "SELECT * FROM benutzer WHERE plz "
                    + "LIKE '" + val +  "%' AND status = 0";

            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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

        benutzer = getBenutzerEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return benutzer;
    }

    /**
     * 
     * @param con
     * @param field
     * @param value
     * @return
     * @throws SQLException
     */

     public static BenutzerObj[] searchBenutzerObject(Connection con, String field, Object value) throws SQLException {

        String sql = "SELECT * FROM benutzer WHERE " + field + " LIKE '%" + value + "%' AND status = 0";

        BenutzerObj[] benutzer = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


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

        benutzer = getBenutzerEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return benutzer;
    }

    /**
     * 
     * @param entry
     * @param rows
     * @return
     * @throws SQLException
     */

    private static BenutzerObj[] getBenutzerEntries(ResultSet entry, int rows) throws SQLException {

        BenutzerObj[] benutzer = new BenutzerObj[rows];

          for(int i = 0; i < rows; i++) {
                entry.next();
                benutzer[i] = BenutzerSQLMethods.getResultSet(entry, false);
        }
       
        return benutzer;
    }

}
