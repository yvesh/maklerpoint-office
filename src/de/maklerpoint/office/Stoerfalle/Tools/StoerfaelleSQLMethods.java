/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Stoerfalle.Tools;

import de.maklerpoint.office.Konstanten.Stoerfaelle;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class StoerfaelleSQLMethods {

    public static int insertIntoStoerfaelle(Connection con, StoerfallObj stoer) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO stoerfaelle (creatorId, mandantenId, kundenNr, vertragsId, betreuerId, stoerfallNr, "
                + "grund, eingang, faelligkeit, fristTage, aufgabenId, rueckstand, "
                + "mahnstatus, kategorie, positivErledigt, notiz, custom1, custom2, "
                + "custom3, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, stoer.getCreatorId());
        statement.setInt(2, stoer.getMandantenId());
        statement.setString(3, stoer.getKundenNr());
        statement.setInt(4, stoer.getVertragsId());
        statement.setInt(5, stoer.getBetreuerId());
        statement.setString(6, stoer.getStoerfallNr());
        statement.setString(7, stoer.getGrund());
        statement.setTimestamp(8, stoer.getEingang());
        statement.setTimestamp(9, stoer.getFaelligkeit());
        statement.setInt(10, stoer.getFristTage());
        statement.setInt(11, stoer.getAufgabenId());
        statement.setDouble(12, stoer.getRueckstand());
        statement.setString(13, stoer.getMahnstatus());
        statement.setString(14, stoer.getKategorie());
        statement.setBoolean(15, stoer.isPositivErledigt());
        statement.setString(16, stoer.getNotiz());
        statement.setString(17, stoer.getCustom1());
        statement.setString(18, stoer.getCustom2());
        statement.setString(19, stoer.getCustom3());
        statement.setTimestamp(20, stoer.getCreated());
        statement.setTimestamp(21, stoer.getModified());
        statement.setInt(22, stoer.getStatus());
        statement.execute();
        ResultSet auto = statement.getGeneratedKeys();

        if (auto.next()) {
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
     * @param StoerfallObj stoer
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateStoerfaelle(Connection con, StoerfallObj stoer) throws SQLException {
        String sql = "SELECT * FROM stoerfaelle WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, stoer.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        
        if (rows == 0) {
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("creatorId", stoer.getCreatorId());
        entry.updateInt("mandantenId", stoer.getMandantenId());
        entry.updateString("kundenNr", stoer.getKundenNr());

        entry.updateInt("vertragsId", stoer.getVertragsId());
        entry.updateInt("betreuerId", stoer.getBetreuerId());

        entry.updateString("stoerfallNr", stoer.getStoerfallNr());

        entry.updateString("grund", stoer.getGrund());

        entry.updateTimestamp("eingang", stoer.getEingang());

        entry.updateTimestamp("faelligkeit", stoer.getFaelligkeit());
        entry.updateInt("fristTage", stoer.getFristTage());
        entry.updateInt("aufgabenId", stoer.getAufgabenId());
        entry.updateDouble("rueckstand", stoer.getRueckstand());
        entry.updateString("mahnstatus", stoer.getMahnstatus());
        entry.updateString("kategorie", stoer.getKategorie());
        entry.updateBoolean("positivErledigt", stoer.isPositivErledigt());
        entry.updateString("notiz", stoer.getNotiz());
        entry.updateString("custom1", stoer.getCustom1());
        entry.updateString("custom2", stoer.getCustom2());
        entry.updateString("custom3", stoer.getCustom3());
        entry.updateTimestamp("created", stoer.getCreated());
        entry.updateTimestamp("modified", stoer.getModified());
        entry.updateInt("status", stoer.getStatus());

        entry.updateRow();

        statement.close();
        con.close();
        return true;
    }
    
//    public static void deleteFromStoerfaelle(Connection con, int keyId) throws SQLException {
//        String sql = "UPDATE stoerfaelle SET status = " + Status.DELETED + " WHERE id = ?";
//        PreparedStatement statement = con.prepareStatement(sql);
//        statement.setInt(1, keyId);
//        statement.executeUpdate();
//        statement.close();        
//        con.close();
//    }
//    
//     public static void archiveFromStoerfaelle(Connection con, int keyId) throws SQLException {
//        String sql = "UPDATE stoerfaelle SET status = " + Status.ARCHIVED + " WHERE id = ?";
//        PreparedStatement statement = con.prepareStatement(sql);
//        statement.setInt(1, keyId);
//        statement.executeUpdate();
//        statement.close();        
//        con.close();
//    }
//    
    
    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException 
     */
    public static void setErledigtFromStoerfaelle(Connection con, int keyId) throws SQLException {
        String sql = "UPDATE stoerfaelle SET status = " + Stoerfaelle.STATUS_ERLEDIGT + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();        
        con.close();
    }
    
    
    /**
     * 
     * @param con
     * @param kdnr
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static StoerfallObj[] getKundenStoerfaelle(Connection con, String kdnr, int status) throws SQLException {
        StoerfallObj[] sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE kundenNr = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM stoerfaelle WHERE kundenNr = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kdnr);

        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        sch = new StoerfallObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }
    
    /**
     * 
     * @param con
     * @param benid
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static StoerfallObj[] getBenutzerStoerfaelle(Connection con, int benid, int status) throws SQLException {
        StoerfallObj[] sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE betreuerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM stoerfaelle WHERE betreuerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benid);

        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        sch = new StoerfallObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }    
    /**
     * 
     * @param con
     * @param vtrid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static StoerfallObj[] getVertragStoerfaelle(Connection con, int vtrid, int status) throws SQLException {
        StoerfallObj[] sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE vertragsId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM stoerfaelle WHERE vertragsId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, vtrid);

        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        sch = new StoerfallObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }
    /**
     * 
     * @param con
     * @param status
     * @return
     * @throws SQLException 
     */
    public static StoerfallObj[] loadAlleStoerfaelle(Connection con, int status) throws SQLException {
        StoerfallObj[] sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE status = ?";

        if (status == -1) {
            sql = "SELECT * FROM stoerfaelle";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      

        if (status != -1) {
            statement.setInt(1, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        sch = new StoerfallObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }
    
    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException 
     */
    
    public static StoerfallObj getStoerfall(Connection con, int id) throws SQLException {
        StoerfallObj sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE id = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
        
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        sch = getObjResult(entry);

        statement.close();
        con.close();

        return sch;
    }
    /**
     * 
     * @param con
     * @param stfallnr
     * @return
     * @throws SQLException 
     */
    public static StoerfallObj getStoerfall(Connection con, String stfallnr) throws SQLException {
        StoerfallObj sch = null;

        String sql = "SELECT * FROM stoerfaelle WHERE stoerfallNr = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, stfallnr);
        
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        sch = getObjResult(entry);

        statement.close();
        con.close();

        return sch;
    }
    
    /**
     * 
     * @param entry
     * @return
     * @throws SQLException 
     */
    
    public static StoerfallObj getObjResult(ResultSet entry) throws SQLException {
        StoerfallObj sch = new StoerfallObj();

        sch.setId(entry.getInt("id"));
        sch.setCreatorId(entry.getInt("creatorId"));
        sch.setMandantenId(entry.getInt("mandantenId"));
        sch.setKundenNr(entry.getString("kundenNr"));
        sch.setVertragsId(entry.getInt("vertragsId"));
        sch.setBetreuerId(entry.getInt("betreuerId"));
        
        sch.setStoerfallNr(entry.getString("stoerfallNr"));
//        System.out.println("Störfall nr: " + sch.getStoerfallNr());
        
        sch.setGrund(entry.getString("grund"));
        sch.setEingang(entry.getTimestamp("eingang"));
        sch.setFaelligkeit(entry.getTimestamp("faelligkeit"));
        sch.setFristTage(entry.getInt("fristtage"));
        sch.setAufgabenId(entry.getInt("aufgabenId"));
        sch.setRueckstand(entry.getDouble("rueckstand"));
        sch.setMahnstatus(entry.getString("mahnstatus"));
        sch.setKategorie(entry.getString("kategorie"));
        sch.setPositivErledigt(entry.getBoolean("positivErledigt"));

        sch.setNotiz(entry.getString("notiz"));

        sch.setCustom1(entry.getString("custom1"));
        sch.setCustom2(entry.getString("custom2"));
        sch.setCustom3(entry.getString("custom3"));

        sch.setCreated(entry.getTimestamp("created"));
        sch.setModified(entry.getTimestamp("modified"));

        sch.setStatus(entry.getInt("status"));

        return sch;
    }
     
    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndugeltigFromStoerfaelle(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM stoerfaelle WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
}
