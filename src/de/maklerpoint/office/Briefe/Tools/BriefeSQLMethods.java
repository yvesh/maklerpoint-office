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
package de.maklerpoint.office.Briefe.Tools;

import de.maklerpoint.office.Briefe.BriefCategoryObj;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Logging.Log;
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
public class BriefeSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @throws SQLException
     */
    public static int insertIntoBriefe(Connection con, BriefObj brief) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO briefe (categoryId, creatorId, benutzerId, loeschbar, privatKunde, geschKunde, "
                + "versicherer, produkt, benutzer, stoerfall, vertrag, filename, "
                + "fullpath, checksum, name, beschreibung, tag, comments, "
                + "created, modified, status, type)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, brief.getCategoryId());
        statement.setInt(2, brief.getCreatorId());
        statement.setInt(3, brief.getBenutzerId());
        statement.setBoolean(4, brief.isLoeschbar());
        statement.setBoolean(5, brief.isPrivatKunde());
        statement.setBoolean(6, brief.isGeschKunde());
        statement.setBoolean(7, brief.isVersicherer());
        statement.setBoolean(8, brief.isProdukt());
        statement.setBoolean(9, brief.isBenutzer());
        statement.setBoolean(10, brief.isStoerfall());
        statement.setBoolean(11, brief.isVertrag());
        statement.setString(12, brief.getFilename());
        statement.setString(13, brief.getFullpath());
        statement.setString(14, brief.getChecksum());
        statement.setString(15, brief.getName());
        statement.setString(16, brief.getBeschreibung());
        statement.setString(17, brief.getTag());
        statement.setString(18, brief.getComments());
        statement.setTimestamp(19, brief.getCreated());
        statement.setTimestamp(20, brief.getModified());
        statement.setInt(21, brief.getStatus());
        statement.setInt(22, brief.getType());
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
     * @return boolean (true on success)
     * @throws SQLException
     */
    
    public static boolean updateBriefe(Connection con, BriefObj brief) throws SQLException {
        String sql = "SELECT * FROM briefe WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, brief.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("type", brief.getType()); // TODO Sollt nicht änderbar sein
        entry.updateInt("categoryId", brief.getCategoryId());
        entry.updateInt("creatorId", brief.getCreatorId());
        entry.updateInt("benutzerId", brief.getBenutzerId());
        entry.updateBoolean("loeschbar", brief.isLoeschbar());
        entry.updateBoolean("privatKunde", brief.isPrivatKunde());
        entry.updateBoolean("geschKunde", brief.isGeschKunde());
        entry.updateBoolean("versicherer", brief.isVersicherer());
        entry.updateBoolean("produkt", brief.isProdukt());
        entry.updateBoolean("benutzer", brief.isBenutzer());
        entry.updateBoolean("stoerfall", brief.isStoerfall());
        entry.updateBoolean("vertrag", brief.isVertrag());

        entry.updateString("filename", brief.getFilename());

        entry.updateString("fullpath", brief.getFullpath());

        entry.updateString("checksum", brief.getChecksum());

        entry.updateString("name", brief.getName());

        entry.updateString("beschreibung", brief.getBeschreibung());

        entry.updateString("tag", brief.getTag());

        entry.updateString("comments", brief.getComments());

        entry.updateTimestamp("created", brief.getCreated());

        entry.updateTimestamp("modified", brief.getModified());
        entry.updateInt("status", brief.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    public static void deleteFromBriefe(Connection con, BriefObj brief) throws SQLException{       
        String sql = "UPDATE vertraege SET status = ? WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);        
        statement.setInt(1, Status.DELETED);
        statement.setInt(2, brief.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromBriefe(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM briefe WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
   
    /**
     * 
     * @param con
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static BriefObj getBrief(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM briefe WHERE id = ?";              
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, id);
        
        ResultSet entry = statement.executeQuery();

        BriefObj[] brf = getBriefeEntry(entry);
                                        
        entry.close();
        statement.close();
        con.close();
        return brf[0];
    }
    
    
    public static BriefCategoryObj getBriefCategory(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM briefe_cat WHERE id = ?";              
        
        PreparedStatement statement = con.prepareStatement(sql);
        
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
        
        entry.next();
        
        BriefCategoryObj brcat = new BriefCategoryObj();
        
        brcat.setId(entry.getInt("id"));
        brcat.setName(entry.getString("name"));
        brcat.setCreated(entry.getTimestamp("created"));
        brcat.setModified(entry.getTimestamp("modified"));
        brcat.setStatus(entry.getInt("status"));
                  
        entry.close();
        statement.close();
        con.close();
        return brcat;
    }
        
    /**
     * 
     * @param con
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static BriefCategoryObj[] getBriefCategories(Connection con, int status) throws SQLException{
        BriefCategoryObj[] brf = null;
        String sql = "SELECT * FROM briefe_cat WHERE status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM briefe_cat";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        
        if(status != -1)
            statement.setInt(1, status);
        
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
        
        brf = new BriefCategoryObj[rows];
        
        for(int i = 0; i < rows; i++)
        {
            entry.next();
            
            brf[i] = new BriefCategoryObj();            
            brf[i].setId(entry.getInt("id"));
            brf[i].setName(entry.getString("name"));
            brf[i].setCreated(entry.getTimestamp("created"));
            brf[i].setModified(entry.getTimestamp("modified"));
            brf[i].setStatus(entry.getInt("status"));
        }
        
        entry.close();
        statement.close();
        con.close();
        
        return brf;
    }
    
    /**
     * 
     * @param con
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static BriefObj[] getBriefeOhneCat(Connection con, int type, int status) throws SQLException {
        String sql = "SELECT * FROM briefe WHERE type = ? AND status = ?";
        
        if(status == -1 && type == -1)
            sql = "SELECT * FROM briefe";
        else if(status == -1)
            sql = "SELECT * FROM briefe WHERE type = ?";
        else if(type == -1)
            sql = "SELECT * FROM briefe WHERE status = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        
        if(status != -1 && type != -1){
            statement.setInt(1, type);
            statement.setInt(2, status);
        } else if(status != -1)
            statement.setInt(1, status);
        else if(type != -1)
            statement.setInt(1, type);
        
        if(Log.databaselogger.isDebugEnabled())
            Log.databaselogger.debug("Statement Briefe ohne Category: " + statement.toString());
        
        ResultSet entry = statement.executeQuery();

        BriefObj[] brf = getBriefeEntry(entry);
                                        
        entry.close();
        statement.close();
        con.close();
        return brf;
    }
    
    public static BriefObj[] getBriefe(Connection con, int category, int type, int status) throws SQLException {
        String sql = "SELECT * FROM briefe WHERE categoryId = ? AND type = ? AND status = ?";
        
        if(status == -1 && type == -1)
            sql = "SELECT * FROM briefe WHERE categoryId = ?";
        else if(status == -1)
            sql = "SELECT * FROM briefe WHERE categoryId = ? AND type = ?";
        else if(type == -1)
            sql = "SELECT * FROM briefe WHERE categoryId = ? AND status = ?";
            
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        
        statement.setInt(1, category);        
        
        if(status != -1 && type != -1) {
            statement.setInt(2, type);
            statement.setInt(3, status);
        } else if(status != -1) {
            statement.setInt(2, status);
        } else if(type != -1) {
            statement.setInt(2, type);
        }
        
        if(Log.databaselogger.isDebugEnabled())
            Log.databaselogger.debug("Statement Briefe mit Category: " + statement.toString());
        
        ResultSet entry = statement.executeQuery();

        BriefObj[] brf = getBriefeEntry(entry);
                                        
        entry.close();
        statement.close();
        con.close();
        return brf;
    }
    
    /**
     * 
     * @param entry
     * @return
     * @throws SQLException 
     */
    
    private static BriefObj[] getBriefeEntry(ResultSet entry) throws SQLException {
        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0) {
            return null;
        }
        
        BriefObj[] brf = new BriefObj [rows];
        
        for(int i = 0; i < rows; i++)
        {
            entry.next();
            
            brf[i] = new BriefObj();
            
            brf[i].setId(entry.getInt("id"));
            brf[i].setType(entry.getInt("type"));
            brf[i].setCategoryId(entry.getInt("categoryId"));
            brf[i].setCreatorId(entry.getInt("creatorId"));
            brf[i].setBenutzerId(entry.getInt("benutzerId"));
            brf[i].setLoeschbar(entry.getBoolean("loeschbar"));
            brf[i].setPrivatKunde(entry.getBoolean("privatKunde"));
            brf[i].setGeschKunde(entry.getBoolean("geschKunde"));
            brf[i].setVersicherer(entry.getBoolean("versicherer"));
            brf[i].setProdukt(entry.getBoolean("produkt"));
            brf[i].setBenutzer(entry.getBoolean("benutzer"));
            brf[i].setStoerfall(entry.getBoolean("stoerfall"));
            brf[i].setVertrag(entry.getBoolean("vertrag"));
            brf[i].setFilename(entry.getString("filename"));
            brf[i].setFullpath(entry.getString("fullpath"));
            brf[i].setChecksum(entry.getString("checksum"));
            brf[i].setName(entry.getString("name"));
            brf[i].setBeschreibung(entry.getString("beschreibung"));
            brf[i].setTag(entry.getString("tag"));
            brf[i].setComments(entry.getString("comments"));
            brf[i].setCreated(entry.getTimestamp("created"));
            brf[i].setModified(entry.getTimestamp("modified"));
            brf[i].setStatus(entry.getInt("status"));
        }
        
        return brf;
    }
}
