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

package de.maklerpoint.office.Dokumente.Tools;

import de.maklerpoint.office.Dokumente.WissenDokumentenObj;
import de.maklerpoint.office.Dokumente.Trigger.*;
import de.maklerpoint.office.System.Status;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class WissenDokumenteSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creator
     * @param benutzerId
     * @param filetype
     * @param category
     * @param name
     * @param fileName
     * @param fullPath
     * @param label
     * @param beschreibung
     * @param checksum
     * @param tag
     * @param created
     * @param modified
     * @param triggerClass
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntowissendokumente(Connection con, int creator, int benutzerId, int filetype, String category, String name,
            String fileName, String fullPath, String label, String beschreibung, String checksum,
            String tag, java.sql.Timestamp created, java.sql.Timestamp modified, java.lang.Class triggerClass, int status)
             throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO wissendokumente (creator, benutzerId, filetype, category, name, fileName, fullPath, label, beschreibung, checksum, tag, created, modified, triggerClass, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, creator);
        statement.setInt(2, benutzerId);
        statement.setInt(3, filetype);
        statement.setString(4, category);
        statement.setString(5, name);
        statement.setString(6, fileName);
        statement.setString(7, fullPath);
        statement.setString(8, label);
        statement.setString(9, beschreibung);
        statement.setString(10, checksum);
        statement.setString(11, tag);
        statement.setTimestamp(12, created);
        statement.setTimestamp(13, modified);
        statement.setString(14, triggerClass.getName());
        statement.setInt(15, status);
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
     * @param creator
     * @param benutzerId
     * @param filetype
     * @param category
     * @param name
     * @param fileName
     * @param fullPath
     * @param label
     * @param beschreibung
     * @param checksum
     * @param tag
     * @param created
     * @param modified
     * @param triggerClass
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatewissendokumente(Connection con, int keyId, int creator, int benutzerId, int filetype, 
            String category, String name,
            String fileName, String fullPath, String label, String beschreibung, String checksum,
            String tag, java.sql.Timestamp created, java.sql.Timestamp modified, java.lang.Class triggerClass, int status)
             throws SQLException {
        String sql = "SELECT * FROM wissendokumente WHERE id = ?";
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

        entry.updateInt("creator", creator);
        entry.updateInt("benutzerId", benutzerId);
        entry.updateInt("filetype", filetype);
        entry.updateString("category", category);
        if(name != null)
            entry.updateString("name", name);
        if(fileName != null)
            entry.updateString("fileName", fileName);
        if(fullPath != null)
            entry.updateString("fullPath", fullPath);
        if(label != null)
            entry.updateString("label", label);
        if(beschreibung != null)
            entry.updateString("beschreibung", beschreibung);
        if(checksum != null)
            entry.updateString("checksum", checksum);
        if(tag != null)
            entry.updateString("tag", tag);
        if(created != null)
            entry.updateTimestamp("created", created);
        if(modified != null)
            entry.updateTimestamp("modified", modified);
        if(triggerClass != null)
            entry.updateString("triggerClass", triggerClass.getName());
        entry.updateInt("status", status);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    public static boolean updatewissendokumente(Connection con, WissenDokumentenObj wd)
             throws SQLException {
        String sql = "SELECT * FROM wissendokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, wd.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        entry.updateInt("creator", wd.getCreator());
        entry.updateInt("benutzerId", wd.getBenutzerId());
        entry.updateInt("filetype", wd.getFiletype());
        entry.updateString("category", wd.getCategory());
        entry.updateString("name", wd.getName());        
        entry.updateString("fileName", wd.getFileName());
        entry.updateString("fullPath", wd.getFullPath());
        entry.updateString("label", wd.getLabel());
        entry.updateString("beschreibung", wd.getBeschreibung());
        entry.updateString("checksum", wd.getChecksum());
        entry.updateString("tag", wd.getTag());
        entry.updateTimestamp("modified", wd.getModified());
        if(wd.getTriggerClass() != null)
            entry.updateString("triggerClass", wd.getTriggerClass().getName());        
        entry.updateInt("status", wd.getStatus());

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

    public static void deleteEndgueltigFromWissendokumente(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM wissendokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException
     */

    public static void deleteFromDokumente(Connection con, int keyId) throws SQLException {
        String sql = "SELECT id, status FROM wissendokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
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
     * @throws ClassNotFoundException
     */

    public static WissenDokumentenObj[] loadWissenDokumente(Connection con) throws SQLException, ClassNotFoundException {
        WissenDokumentenObj[] dokumente = null;

        String sql = "Select * FROM wissendokumente WHERE status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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

        dokumente = new WissenDokumentenObj[rows];

        for(int i= 0; i < rows; i++) {
            entry.next();
            dokumente[i] = new WissenDokumentenObj();
            dokumente[i].setId(entry.getInt("id"));
            dokumente[i].setCreator(entry.getInt("creator"));
            dokumente[i].setBenutzerId(entry.getInt("benutzerId"));
            dokumente[i].setFiletype(entry.getInt("filetype"));
            dokumente[i].setCategory(entry.getString("category"));
            dokumente[i].setName(entry.getString("name"));
            dokumente[i].setFileName(entry.getString("fileName"));
            dokumente[i].setFullPath(entry.getString("fullPath"));

//            if(dokumente[i].getFullPath() != null)
//                dokumente[i].setFullPath(dokumente[i].getFullPath().replaceAll("/", File.separator));

            dokumente[i].setLabel(entry.getString("label"));
            dokumente[i].setBeschreibung(entry.getString("beschreibung"));
            dokumente[i].setChecksum(entry.getString("checksum"));
            dokumente[i].setTag(entry.getString("tag"));
            dokumente[i].setCreated(entry.getTimestamp("created"));
            dokumente[i].setModified(entry.getTimestamp("modified"));
            if(entry.getString("triggerClass") != null)
                dokumente[i].setTriggerClass(Class.forName(entry.getString("triggerClass")));
            dokumente[i].setStatus(0);
        }

        entry.close();
        statement.close();
        con.close();

        return dokumente;
    }
    
    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static WissenDokumentenObj getWissenDokument(Connection con, int id) throws SQLException, ClassNotFoundException {
        WissenDokumentenObj dokument = null;

        String sql = "Select * FROM wissendokumente WHERE id = ?";

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

        entry.next();
        dokument = new WissenDokumentenObj();
        dokument.setId(entry.getInt("id"));
        dokument.setCreator(entry.getInt("creator"));
        dokument.setBenutzerId(entry.getInt("benutzerId"));
        dokument.setFiletype(entry.getInt("filetype"));
        dokument.setCategory(entry.getString("category"));
        dokument.setName(entry.getString("name"));
        dokument.setFileName(entry.getString("fileName"));
        dokument.setFullPath(entry.getString("fullPath").replaceAll("/", File.separator));
        dokument.setLabel(entry.getString("label"));
        dokument.setBeschreibung(entry.getString("beschreibung"));
        dokument.setChecksum(entry.getString("checksum"));
        dokument.setTag(entry.getString("tag"));
        dokument.setCreated(entry.getTimestamp("created"));
        dokument.setModified(entry.getTimestamp("modified"));
        if(entry.getString("triggerClass") != null)
            dokument.setTriggerClass(Class.forName(entry.getString("triggerClass")));
        dokument.setStatus(entry.getInt("status"));
   
        entry.close();
        statement.close();
        con.close();

        return dokument;
    }

    public static WissenDokumentenObj getWissenDokument(Connection con, String name) throws SQLException, ClassNotFoundException {
        WissenDokumentenObj dokument = null;

        String sql = "Select * FROM wissendokumente WHERE name = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, name);

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
        dokument = new WissenDokumentenObj();
        dokument.setId(entry.getInt("id"));
        dokument.setCreator(entry.getInt("creator"));
        dokument.setBenutzerId(entry.getInt("benutzerId"));
        dokument.setFiletype(entry.getInt("filetype"));
        dokument.setCategory(entry.getString("category"));
        dokument.setName(entry.getString("name"));
        dokument.setFileName(entry.getString("fileName"));
        dokument.setFullPath(entry.getString("fullPath").replaceAll("/", File.separator));
        dokument.setLabel(entry.getString("label"));
        dokument.setBeschreibung(entry.getString("beschreibung"));
        dokument.setChecksum(entry.getString("checksum"));
        dokument.setTag(entry.getString("tag"));
        dokument.setCreated(entry.getTimestamp("created"));
        dokument.setModified(entry.getTimestamp("modified"));
        if(entry.getString("triggerClass") != null)
            dokument.setTriggerClass(Class.forName(entry.getString("triggerClass")));
        dokument.setStatus(entry.getInt("status"));

        entry.close();
        statement.close();
        con.close();

        return dokument;
    }
}