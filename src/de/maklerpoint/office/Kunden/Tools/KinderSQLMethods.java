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

import de.maklerpoint.office.Kunden.KinderObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KinderSQLMethods {

    /**
     * 
     * @param con
     * @param parentId
     * @return
     * @throws SQLException
     */
    public static KinderObj[] loadKinder(Connection con, int parentId, int status) throws SQLException {
        KinderObj[] kinder = null;

        String sql = "SELECT * FROM kinder WHERE parentId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kinder WHERE parentId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, parentId);

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

        kinder = new KinderObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            kinder[i] = new KinderObj();

            kinder[i].setId(entry.getInt("id"));
            kinder[i].setParentId(entry.getInt("parentId"));
            kinder[i].setKindName(entry.getString("kindName"));
            kinder[i].setKindVorname(entry.getString("kindVorname"));
            kinder[i].setKindGeburtsdatum(entry.getString("kindGeburtsdatum"));
            kinder[i].setKindBeruf(entry.getString("kindBeruf"));
            kinder[i].setKindWohnort(entry.getString("kindWohnort"));
            kinder[i].setComments(entry.getString("comments"));
            kinder[i].setCustom(entry.getString("custom"));
            kinder[i].setCreated(entry.getTimestamp("created"));
            kinder[i].setModified(entry.getTimestamp("modified"));
            kinder[i].setKindStatus(entry.getInt("status"));
        }

        statement.close();
        con.close();
        return kinder;
    }

    /**
     * 
     * @param con
     * @param kind
     * @return
     * @throws SQLException 
     */
    public static int insertIntoKinder(Connection con, KinderObj kind) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO kinder (parentId, mandantenId, kindName, kindVorname, kindGeburtsdatum, kindBeruf, "
                + "kindWohnort, comments, custom, created, modified, status"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, kind.getParentId());
        statement.setInt(2, kind.getMandantenId());
        statement.setString(3, kind.getKindName());
        statement.setString(4, kind.getKindVorname());
        statement.setString(5, kind.getKindGeburtsdatum());
        statement.setString(6, kind.getKindBeruf());
        statement.setString(7, kind.getKindWohnort());
        statement.setString(8, kind.getComments());
        statement.setString(9, kind.getCustom());
        statement.setTimestamp(10, kind.getCreated());
        statement.setTimestamp(11, kind.getModified());
        statement.setInt(12, kind.getStatus());
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
     * @param parentId
     * @param mandantenId
     * @param kindName
     * @param kindVorname
     * @param kindGeburtsdatum
     * @param kindBeruf
     * @param kindWohnort
     * @param comments
     * @param custom
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateKinder(Connection con, KinderObj kind) throws SQLException {
        String sql = "SELECT * FROM kinder WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, kind.getId());
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

        entry.updateInt("parentId", kind.getParentId());
        entry.updateInt("mandantenId", kind.getMandantenId());
        entry.updateString("kindName", kind.getKindName());
        entry.updateString("kindVorname", kind.getKindVorname());
        entry.updateString("kindGeburtsdatum", kind.getKindGeburtsdatum());
        entry.updateString("kindBeruf", kind.getKindBeruf());
        entry.updateString("kindWohnort", kind.getKindWohnort());

        entry.updateString("comments", kind.getComments());
        entry.updateString("custom", kind.getCustom());
        entry.updateTimestamp("created", kind.getCreated());
        entry.updateTimestamp("modified", kind.getModified());
        entry.updateInt("status", kind.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * Deletes a row from child
     * @param con
     * @param keyId
     * @throws SQLException
     */
    
    public static void deleteEndgueltigFromKinder(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM Kind WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    public static void deleteFromKinder(Connection con, KinderObj kind) throws SQLException {
        if(kind == null)
            return;
        
        String sql = "UPDATE kinder SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, kind.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        kind.setStatus(Status.DELETED);
    }
    
    public static void archiveFromKinder(Connection con, KinderObj kind) throws SQLException {
        if(kind == null)
            return;
        
        String sql = "UPDATE kinder SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, kind.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        kind.setStatus(Status.ARCHIVED);
    }
}
