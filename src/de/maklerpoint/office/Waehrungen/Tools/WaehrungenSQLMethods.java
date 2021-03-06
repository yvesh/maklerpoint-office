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

package de.maklerpoint.office.Waehrungen.Tools;

import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class WaehrungenSQLMethods {

    /**
 * Java method that inserts a row in the generated sql table
 * and returns the new generated id
 * @param con (open java.sql.Connection)
 * @param ordering
 * @param isocode
 * @param bezeichnung
 * @param neuanlage
 * @return id (database row id [id])
 * @throws SQLException
 */

    public static int insertIntowaehrungen(Connection con, int ordering, String isocode,
            String bezeichnung, boolean neuanlage) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO waehrungen (ordering, isocode, bezeichnung, neuanlage)" +
                        "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, ordering);
        statement.setString(2, isocode);
        statement.setString(3, bezeichnung);
        statement.setBoolean(4, neuanlage);
        statement.execute();
        ResultSet auto = statement.getGeneratedKeys();

        if(auto.next()){
           generatedId = auto.getInt(1);
        } else {
           generatedId = -1;
        }

        statement.close();
        con.close();
        return generatedId;
    }

    /**
     * Java method that updates a row in the generated sql table
     * @param con (open java.sql.Connection)
     * @param ordering
     * @param isocode
     * @param bezeichnung
     * @param neuanlage
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatewaehrungen(Connection con, int keyId, int ordering,
            String isocode, String bezeichnung, boolean neuanlage) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)  {
            entry.close();
            statement.close();
            con.close();
            return false;
        }

        entry.next();

        entry.updateInt("ordering", ordering);
        if(isocode != null)
            entry.updateString("isocode", isocode);
        if(bezeichnung != null)
            entry.updateString("bezeichnung", bezeichnung);
        entry.updateBoolean("neuanlage", neuanlage);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    public static boolean updatewaehrungen(Connection con, WaehrungenObj waehrung) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, waehrung.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)  {
            entry.close();
            statement.close();
            con.close();
            return false;
        }

        entry.next();

        entry.updateInt("ordering", waehrung.getOrdering());
        entry.updateString("isocode", waehrung.getIsocode());
        entry.updateString("bezeichnung", waehrung.getBezeichnung());
        entry.updateBoolean("neuanlage", waehrung.isNeuanlage());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */

    public static void deleteEndgueltigFromWaehrungen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM waehrungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }


    public static void archiveFromWaehrungen(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return;
        }

        entry.next();

        entry.updateInt("status", Status.ARCHIVED);
        entry.updateRow();

        entry.close();
        statement.close();
        con.close();
    }

     /**
      *
      * @param con
      * @param keyId
      * @throws SQLException
      */

    public static void deleteFromWaehrungen(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return;
        }

        entry.next();

        entry.updateInt("status", Status.DELETED);
        entry.updateRow();

        entry.close();
        statement.close();
        con.close();
    }
    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     */

    public static WaehrungenObj[] loadWaehrungen(Connection con) throws SQLException {
        return loadWaehrungen(con, null, 0);
    }


    public static WaehrungenObj[] loadWaehrungen(Connection con, String orderby, int status) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE status = ?";

         if(orderby != null)
            sql = "SELECT * FROM waehrungen WHERE status = ? ORDER BY " + orderby.trim();

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, status);

        Logger.getLogger(WaehrungenSQLMethods.class).debug("Währungen load statement: " + statement.toString());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        WaehrungenObj[] waehrungen = new WaehrungenObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            waehrungen[i] = new WaehrungenObj();
            waehrungen[i].setId(entry.getInt("id"));
            waehrungen[i].setOrdering(entry.getInt("ordering"));
            waehrungen[i].setIsocode(entry.getString("isocode"));
            waehrungen[i].setBezeichnung(entry.getString("bezeichnung"));
            waehrungen[i].setNeuanlage(entry.getBoolean("neuanlage"));
        }

        entry.close();
        statement.close();
        con.close();

        return waehrungen;
    }
    
    
    public static WaehrungenObj getWaehrung(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM waehrungen WHERE id = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, id);
        
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        WaehrungenObj waehrung = new WaehrungenObj();

        entry.next();
        waehrung.setId(entry.getInt("id"));
        waehrung.setOrdering(entry.getInt("ordering"));
        waehrung.setIsocode(entry.getString("isocode"));
        waehrung.setBezeichnung(entry.getString("bezeichnung"));
        waehrung.setNeuanlage(entry.getBoolean("neuanlage"));
        

        entry.close();
        statement.close();
        con.close();

        return waehrung;
    }




}
