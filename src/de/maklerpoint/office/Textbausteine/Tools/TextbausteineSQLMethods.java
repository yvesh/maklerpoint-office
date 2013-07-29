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
package de.maklerpoint.office.Textbausteine.Tools;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Textbausteine.TextbausteinGroupObj;
import de.maklerpoint.office.Textbausteine.TextbausteinObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author yves
 */
public class TextbausteineSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param group
     * @param benutzerId
     * @param name
     * @param beschreibung
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     * @deprecated insert TbObj
     */
    public static int insertIntotextbausteine(Connection con, int group, int benutzerId, String name,
            String beschreibung, java.sql.Timestamp created,
            java.sql.Timestamp modified, int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO textbausteine (grp, benutzerId, name, beschreibung, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, group);
        statement.setInt(2, benutzerId);
        statement.setString(3, name);
        statement.setString(4, beschreibung);
        statement.setTimestamp(5, created);
        statement.setTimestamp(6, modified);
        statement.setInt(7, status);
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
     *
     * @param con
     * @param tb
     * @return id des Textbausteins
     * @throws SQLException
     */
    public static int insertIntotextbausteine(Connection con, TextbausteinObj tb) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO textbausteine (grp, benutzerId, produktId, name, beschreibung, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, tb.getGroup());
        statement.setInt(2, tb.getBenutzerId());
        statement.setInt(3, tb.getProdId());
        statement.setString(4, tb.getName());
        statement.setString(5, tb.getBeschreibung());
        statement.setTimestamp(6, tb.getCreated());
        statement.setTimestamp(7, tb.getModified());
        statement.setInt(8, tb.getStatus());
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
     * @param group
     * @param benutzerId
     * @param name
     * @param beschreibung
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated TbObj
     */
    public static boolean updatetextbausteine(Connection con, int keyId, int group,
            int benutzerId, String name, String beschreibung, java.sql.Timestamp created,
            java.sql.Timestamp modified, int status) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
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

        entry.updateInt("grp", group);
        entry.updateInt("benutzerId", benutzerId);
        if (name != null) {
            entry.updateString("name", name);
        }
        if (beschreibung != null) {
            entry.updateString("beschreibung", beschreibung);
        }
        if (created != null) {
            entry.updateTimestamp("created", created);
        }
        if (modified != null) {
            entry.updateTimestamp("modified", modified);
        }
        entry.updateInt("status", status);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param Textbaustein tb
     * @return success
     * @throws SQLException
     */
    public static boolean updatetextbausteine(Connection con, TextbausteinObj tb) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, tb.getId());
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

        entry.updateInt("grp", tb.getGroup());
        entry.updateInt("benutzerId", tb.getBenutzerId());
        entry.updateInt("produktId", tb.getProdId());
        entry.updateString("name", tb.getName());
        entry.updateString("beschreibung", tb.getBeschreibung());
        entry.updateTimestamp("created", tb.getCreated());
        entry.updateTimestamp("modified", tb.getModified());
        entry.updateInt("status", tb.getStatus());

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
    public static void deleteEngueltigFromtextbausteine(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM textbausteine WHERE id = ?";
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
    public static void deleteFromtextbausteine(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
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
    public static TextbausteinObj[] loadTextbausteine(Connection con) throws SQLException {
        return loadTextbausteine(con, 0);
    }

    /**
     * 
     * @param con
     * @return Textbausteine
     * @throws SQLException
     */
    public static TextbausteinObj[] loadTextbausteine(Connection con, int status) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE status = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, status);

        Logger.getLogger(TextbausteineSQLMethods.class).debug("SQL Statement: " + statement.toString());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        TextbausteinObj[] tb = new TextbausteinObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            tb[i] = getResultSetEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return tb;
    }

    /**
     * 
     * @param con
     * @param id
     * @return Textbaustein
     * @throws SQLException 
     */
    public static TextbausteinObj getTextbaustein(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE id = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);

        Logger.getLogger(TextbausteineSQLMethods.class).debug("SQL Statement: " + statement.toString());

        ResultSet entry = statement.executeQuery();


        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        entry.next();

        TextbausteinObj tb = getResultSetEntry(entry);

        entry.close();
        statement.close();
        con.close();

        return tb;
    }

    public static TextbausteinObj getProduktTextbaustein(Connection con, int produktId) throws SQLException {
        String sql = "SELECT * FROM textbausteine WHERE produktId = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, produktId);

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("SQL Statement: " + statement.toString());
        }

        ResultSet entry = statement.executeQuery();


        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        entry.next();

        TextbausteinObj tb = getResultSetEntry(entry);

        entry.close();
        statement.close();
        con.close();

        return tb;
    }

    /**
     * 
     * @param entry
     * @return Textbaustein
     * @throws SQLException 
     */
    public static TextbausteinObj getResultSetEntry(ResultSet entry) throws SQLException {
        TextbausteinObj tb = new TextbausteinObj();

        tb.setId(entry.getInt("id"));
        tb.setGroup(entry.getInt("grp"));
        tb.setBenutzerId(entry.getInt("benutzerId"));
        tb.setProdId(entry.getInt("produktId"));
        tb.setName(entry.getString("name"));
        tb.setBeschreibung(entry.getString("beschreibung"));
        tb.setCreated(entry.getTimestamp("created"));
        tb.setModified(entry.getTimestamp("modified"));
        tb.setStatus(entry.getInt("status"));

        return tb;
    }

    /**
     * 
     * @param con
     * @return TextbaunsteinGruppen
     * @throws SQLException
     */
    public static TextbausteinGroupObj[] loadTextbausteinGroups(Connection con) throws SQLException {
        return loadTextbausteinGroups(con, 0);
    }

    /**
     * 
     * @param con
     * @return TextbausteinGruppen
     * @throws SQLException
     */
    public static TextbausteinGroupObj[] loadTextbausteinGroups(Connection con, int status) throws SQLException {
        String sql = "SELECT * FROM textbausteine_grp WHERE status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        Logger.getLogger(TextbausteineSQLMethods.class).debug("SQL Statement: " + statement.toString());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        TextbausteinGroupObj[] bs = new TextbausteinGroupObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            bs[i] = new TextbausteinGroupObj();

            bs[i].setId(entry.getInt("id"));
//             System.out.println("I: " + i + " | id: " + entry.getInt("id"));
            bs[i].setName(entry.getString("name"));
            bs[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bs;
    }

    /**
     * 
     * @param con
     * @param tb
     * @return id der neuen Grp
     * @throws SQLException 
     */
    public static int newTextBausteinGrp(Connection con, TextbausteinGroupObj tb) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO textbausteine_grp (name, status)"
                + "VALUES (?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, tb.getName());
        statement.setInt(2, tb.getStatus());

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
}
