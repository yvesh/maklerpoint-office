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

package de.maklerpoint.office.Sparten.Tools;

import de.maklerpoint.office.Sparten.SpartenObj;
import de.maklerpoint.office.System.Status;
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
public class SpartenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param spartenNummer
     * @param bezeichnung
     * @param gruppe
     * @param steuersatz
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntosparten(Connection con, String spartenNummer, String bezeichnung, String gruppe, int steuersatz) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO sparten (spartenNummer, bezeichnung, gruppe, steuersatz)" +
                        "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, spartenNummer);
        statement.setString(2, bezeichnung);
        statement.setString(3, gruppe);
        statement.setInt(4, steuersatz);
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
     * 
     * @param con
     * @param sparte
     * @return
     * @throws SQLException
     */

    public static int insertIntosparten(Connection con, SpartenObj sparte) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO sparten (spartenNummer, bezeichnung, gruppe, steuersatz)" +
                        "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, sparte.getSpartenNummer());
        statement.setString(2, sparte.getBezeichnung());
        statement.setString(3, sparte.getGruppe());
        statement.setInt(4, sparte.getSteuersatz());
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
     * @param spartenNummer
     * @param bezeichnung
     * @param gruppe
     * @param steuersatz
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatesparten(Connection con, int keyId, String spartenNummer,
            String bezeichnung, String gruppe, int steuersatz) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        if(spartenNummer != null)
            entry.updateString("spartenNummer", spartenNummer);
        if(bezeichnung != null)
            entry.updateString("bezeichnung", bezeichnung);
        if(gruppe != null)
            entry.updateString("gruppe", gruppe);
        entry.updateInt("steuersatz", steuersatz);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }


    public static boolean updatesparten(Connection con, SpartenObj sparte) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, sparte.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        entry.updateString("spartenNummer", sparte.getSpartenNummer());
        entry.updateString("bezeichnung", sparte.getBezeichnung());
        entry.updateString("gruppe", sparte.getGruppe());
        entry.updateInt("steuersatz", sparte.getSteuersatz());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException
     */

     public static void archiveFromSparten(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE id = ?";
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

    public static void deleteFromSparten(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE id = ?";
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
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */

    public static void deleteEndgueltigFromSparten(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM sparten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     */

    public static SpartenObj[] loadSparten(Connection con, String orderby, int status) throws SQLException {
        
        String sql = "SELECT * FROM sparten WHERE status = ?";

        if(orderby != null)
            sql = "SELECT * FROM sparten WHERE status = ? ORDER BY " + orderby.trim();

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, status);

        Logger.getLogger(SpartenSQLMethods.class).debug("Sparten load statement: " + statement.toString());

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

        SpartenObj[] sparten = new SpartenObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            sparten[i] = new SpartenObj();
            sparten[i].setId(entry.getInt("id"));
            sparten[i].setSpartenNummer(entry.getString("spartenNummer"));
            sparten[i].setBezeichnung(entry.getString("bezeichnung"));
            sparten[i].setGruppe(entry.getString("gruppe"));
            sparten[i].setSteuersatz(entry.getInt("steuersatz"));
            sparten[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return sparten;
    }


     public static SpartenObj[] loadSparten(Connection con) throws SQLException {

        return loadSparten(con, null, 0);
    }
    /**
     * 
     * @param con
     * @param keyId
     * @return
     * @throws SQLException
     */

    public static SpartenObj getSparte(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE id = ?";

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
            return null;
        }

        entry.next();
        
        SpartenObj sparte = new SpartenObj();
           
        sparte = new SpartenObj();
        sparte.setId(entry.getInt("id"));
        sparte.setSpartenNummer(entry.getString("spartenNummer"));
        sparte.setBezeichnung(entry.getString("bezeichnung"));
        sparte.setGruppe(entry.getString("gruppe"));
        sparte.setSteuersatz(entry.getInt("steuersatz"));
        sparte.setStatus(entry.getInt("status"));
        
        entry.close();
        statement.close();
        con.close();

        return sparte;
    }

    /**
     *
     * @param con
     * @param keyId
     * @return
     * @throws SQLException
     */

    public static SpartenObj getSparte(Connection con, String spartenNummer) throws SQLException {
        String sql = "SELECT * FROM sparten WHERE spartenNummer = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, spartenNummer);

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

        entry.next();

        SpartenObj sparte = new SpartenObj();

        sparte = new SpartenObj();
        sparte.setId(entry.getInt("id"));
        sparte.setSpartenNummer(entry.getString("spartenNummer"));
        sparte.setBezeichnung(entry.getString("bezeichnung"));
        sparte.setGruppe(entry.getString("gruppe"));
        sparte.setSteuersatz(entry.getInt("steuersatz"));
        sparte.setStatus(entry.getInt("status"));

        entry.close();
        statement.close();
        con.close();

        return sparte;
    }

}
