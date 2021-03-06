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

package de.maklerpoint.office.Backup.Tools;

import de.maklerpoint.office.Backup.BackupObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class BackupSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param path
     * @param type
     * @param created
     * @param automatic
     * @param benutzerId
     * @param success
     * @param fileAvailable
     * @param backupSize
     * @param filetype
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntoBackup(Connection con, String path, int type,
            java.sql.Timestamp created, boolean automatic, int benutzerId,
            boolean success, boolean fileAvailable, int backupSize, int filetype) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO backup (path, type, created, automatic, benutzerId, success, fileAvailable, backupSize, filetype)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, path);
        statement.setInt(2, type);
        statement.setTimestamp(3, created);
        statement.setBoolean(4, automatic);
        statement.setInt(5, benutzerId);
        statement.setBoolean(6, success);
        statement.setBoolean(7, fileAvailable);
        statement.setInt(8, backupSize);
        statement.setInt(9, filetype);
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
     * @param path
     * @param type
     * @param created
     * @param automatic
     * @param benutzerId
     * @param success
     * @param fileAvailable
     * @param backupSize
     * @param filetype
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updateBackup(Connection con, int keyId, String path, int type,
            java.sql.Timestamp created, boolean automatic, int benutzerId,
            boolean success, boolean fileAvailable, int backupSize, int filetype) throws SQLException {
        String sql = "SELECT * FROM backup WHERE id = ?";
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

        if(path != null)
            entry.updateString("path", path);
        entry.updateInt("type", type);
        if(created != null)
            entry.updateTimestamp("created", created);
        entry.updateBoolean("automatic", automatic);
        entry.updateInt("benutzerId", benutzerId);
        entry.updateBoolean("success", success);
        entry.updateBoolean("fileAvailable", fileAvailable);
        entry.updateInt("backupSize", backupSize);
        entry.updateInt("filetype", filetype);

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

    public static void deleteFromBackup(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM backup WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     */

    public static BackupObj[] loadBackupsUser(Connection con, int besitzer) throws SQLException {
        BackupObj[] backups = null;

        String sql = "SELECT * FROM backup WHERE besitzerId = ? OR benutzerId = -1";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, besitzer);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        backups = new BackupObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            backups[i] = new BackupObj(entry.getInt("id"));
            backups[i].setPath(entry.getString("path"));
            backups[i].setType(entry.getInt("type"));
            backups[i].setCreated(entry.getTimestamp("created"));
            backups[i].setAutomatic(entry.getBoolean("automatic"));
            backups[i].setBenutzerId(entry.getInt("benutzerId"));
            backups[i].setSuccess(entry.getBoolean("success"));
            backups[i].setFileAvailable(entry.getBoolean("fileAvailable"));
            backups[i].setBackupSize(entry.getInt("backupSize"));
            backups[i].setFiletype(entry.getInt("filetype"));
        }

        entry.close();
        statement.close();
        con.close();

        return backups;
    }

    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     */

    public static BackupObj[] loadBackups(Connection con) throws SQLException {
        BackupObj[] backups = null;

        String sql = "SELECT * FROM backup";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);        

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        backups = new BackupObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            backups[i] = new BackupObj(entry.getInt("id"));
            backups[i].setPath(entry.getString("path"));
            backups[i].setType(entry.getInt("type"));
            backups[i].setCreated(entry.getTimestamp("created"));
            backups[i].setAutomatic(entry.getBoolean("automatic"));
            backups[i].setBenutzerId(entry.getInt("benutzerId"));
            backups[i].setSuccess(entry.getBoolean("success"));
            backups[i].setFileAvailable(entry.getBoolean("fileAvailable"));
            backups[i].setBackupSize(entry.getInt("backupSize"));
            backups[i].setFiletype(entry.getInt("filetype"));
        }

        entry.close();
        statement.close();
        con.close();

        return backups;
    }

    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     */

    public static BackupObj loadLatestBackup(Connection con) throws SQLException {
        String sql = "SELECT * FROM backup ORDER by created DESC";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0)
            return null;

        entry.next();

        BackupObj backup = new BackupObj(entry.getInt("id"));
        backup.setPath(entry.getString("path"));
        backup.setType(entry.getInt("type"));
        backup.setCreated(entry.getTimestamp("created"));
        backup.setAutomatic(entry.getBoolean("automatic"));
        backup.setBenutzerId(entry.getInt("benutzerId"));
        backup.setSuccess(entry.getBoolean("success"));
        backup.setFileAvailable(entry.getBoolean("fileAvailable"));
        backup.setBackupSize(entry.getInt("backupSize"));
        backup.setFiletype(entry.getInt("filetype"));

        return backup;
    }

    /**
     *
     * @param con
     * @param backup
     * @return
     * @throws SQLException
     */

    public static boolean updateBackup(Connection con, BackupObj backup) throws SQLException {
        String sql = "SELECT * FROM backup WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, backup.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        
        if(rows == 0)
            return false;

        entry.next();

        entry.updateString("path", backup.getPath());
        entry.updateInt("type", backup.getType());
        //entry.updateTimestamp("created", created);
        entry.updateBoolean("automatic", backup.isAutomatic());
        entry.updateInt("benutzerId", backup.getBenutzerId());
        entry.updateBoolean("success", backup.isSuccess());
        entry.updateBoolean("fileAvailable", backup.isFileAvailable());
        entry.updateInt("backupSize", backup.getBackupSize());
        entry.updateInt("filetype", backup.getFiletype());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

}
