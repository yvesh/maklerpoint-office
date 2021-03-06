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
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Tools.ArrayStringTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yves
 */
public class SearchFirmen {

    public static String[] searchFields = new String[]{"betreuer", "kundenNr", "firmenName", "firmenNameZusatz2", "firmenStrasse",
        "firmenPLZ", "firmenStadt", "firmenBundesland", "firmenLand", "firmenPostfachName", "firmenPostfachPlz",
        "firmenRechtsform", "firmenBranche", "firmenGeschaeftsfuehrer", "communication1", "communication2", "communication3",
        "communication4", "communication5", "communication6", "mail", "custom1", "custom2", "custom3", "comments"
    };

    public static FirmenObj[] quickSearch(Connection con, Object value, boolean eigene, int status) throws SQLException {

        for(int i = 0; i < searchFields.length; i++)
        {
            FirmenObj[] firmen = SearchFirmen.searchFirmenObject(DatabaseConnection.open(), searchFields[i], value, eigene, status,
                    TableValueChooseDialog.ENTHAELT);

            if(firmen != null) {
                return firmen;
            }
        }

        return null;
    }

    public static FirmenObj[] searchFirmenObject(Connection con, String field, Object value, boolean eigene, int status,
            int operator) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '%" + value + "%'";

        if(operator == TableValueChooseDialog.ENTHAELT)
        {
            if(eigene && status != -1)            
                sql = "SELECT * FROM firmenkunden WHERE " + field +" LIKE '%" + value + "%' AND betreuer = ? AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '%" + value + "%' AND betreuer = ?";
            else if(status != -1)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '%" + value + "%' AND status = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '%" + value + "%'";
        } else if (operator == TableValueChooseDialog.BEGINNT_MIT) {
            if(eigene && status != -1)            
                sql = "SELECT * FROM firmenkunden WHERE " + field +" LIKE '" + value + "%' AND betreuer = ? AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '" + value + "%' AND betreuer = ?";
            else if(status != -1)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '" + value + "%' AND status = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " LIKE '" + value + "%'";
        } else {
            if(eigene && status != -1)            
                sql = "SELECT * FROM firmenkunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND betreuer = ? AND status = ?";
            else if(eigene)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND betreuer = ?";
            else if(status != -1)
                sql = "SELECT * FROM firmenkunden  WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' AND status = ?";
            else if(!eigene && status == -1)
                sql = "SELECT * FROM firmenkunden WHERE " + field + " " + TableValueChooseDialog.ZEICHEN[operator] + 
                        " '" + value + "' ";
        } 
            
        FirmenObj[] firmen = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(eigene && status != -1) {
           statement.setInt(1, BasicRegistry.currentUser.getId());
           statement.setInt(2, status);           
        } else if (eigene) {
            statement.setInt(1, BasicRegistry.currentUser.getId());
        } else if (status != -1) {
            statement.setInt(1, status);
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

        firmen = getEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return firmen;
    }

    private static FirmenObj[] getEntries(ResultSet entry, int rows) throws SQLException {
        FirmenObj[] firmen = new FirmenObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            firmen[i] = FirmenSQLMethods.getFirmenEntry(entry);
        }

        return firmen;
    }

    
}
