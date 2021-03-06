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
package de.maklerpoint.office.Notizen.Tools;

import de.maklerpoint.office.Notizen.NotizenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */
public class NotizenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creatorId
     * @param priv
     * @param kundenKennung
     * @param versichererKennung
     * @param benutzerKennung
     * @param betreff
     * @param text
     * @param tag
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     * @deprecated 
     */
    public static int insertIntonotizen(Connection con, int creatorId, boolean priv, String kundenKennung,
            int versichererKennung, int benutzerKennung, int vertragId, String betreff, String text, String tag,
            java.sql.Timestamp created, java.sql.Timestamp modified,
            int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO notizen (creatorId, priv, kundenKennung, versichererId, "
                + "benutzerId, betreff, text, tag, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, creatorId);
        statement.setBoolean(2, priv);
        statement.setString(3, kundenKennung);
        statement.setInt(4, versichererKennung);
        statement.setInt(5, benutzerKennung);
        statement.setString(6, betreff);
        statement.setString(7, text);
        statement.setString(8, tag);
        statement.setTimestamp(9, created);
        statement.setTimestamp(10, modified);
        statement.setInt(11, status);
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
     * @param nz
     * @return
     * @throws SQLException
     */
    public static int insertIntonotizen(Connection con, NotizenObj nz) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO notizen (creatorId, priv, kundenKennung, versichererId, "
                + "benutzerId, betreff, text, tag, created, modified, status, vertragId, stoerfallId, schadenId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, nz.getCreatorId());
        statement.setBoolean(2, nz.isPrivate());
        statement.setString(3, nz.getKundenKennung());
        statement.setInt(4, nz.getVersichererKennung());
        statement.setInt(5, nz.getBenutzerKennung());
        statement.setString(6, nz.getBetreff());
        statement.setString(7, nz.getText());
        statement.setString(8, nz.getTag());
        statement.setTimestamp(9, nz.getCreated());
        statement.setTimestamp(10, nz.getModified());
        statement.setInt(11, nz.getStatus());
        statement.setInt(12, nz.getVertragId());
        statement.setInt(13, nz.getStoerfallId());
        statement.setInt(14, nz.getSchadenId());
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
     * @param creatorId
     * @param priv
     * @param kundenKennung
     * @param versichererId
     * @param benutzerKennung
     * @param betreff
     * @param text
     * @param tag
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated 
     */
    public static boolean updatenotizen(Connection con, int keyId, int creatorId, boolean priv,
            String kundenKennung, int versichererKennung, int vertragId, int benutzerKennung,
            String betreff, String text, String tag, java.sql.Timestamp created, java.sql.Timestamp modified,
            int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE id = ?";
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

        entry.updateInt("creatorId", creatorId);
        entry.updateBoolean("priv", priv);
        if (kundenKennung != null) {
            entry.updateString("kundenKennung", kundenKennung);
        }
        entry.updateInt("versichererId", versichererKennung);
        entry.updateInt("benutzerId", benutzerKennung);
        if (betreff != null) {
            entry.updateString("betreff", betreff);
        }
        if (text != null) {
            entry.updateString("text", text);
        }
        if (tag != null) {
            entry.updateString("tag", tag);
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
     * @param nz
     * @return
     * @throws SQLException
     */
    public static boolean updatenotizen(Connection con, NotizenObj nz) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, nz.getId());
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

        entry.updateInt("creatorId", nz.getCreatorId());
        entry.updateBoolean("priv", nz.isPrivate());
        entry.updateString("kundenKennung", nz.getKundenKennung());
        entry.updateInt("versichererId", nz.getVersichererKennung());
        entry.updateInt("benutzerId", nz.getBenutzerKennung());
        entry.updateInt("vertragId", nz.getVertragId());
        entry.updateInt("stoerfallId", nz.getStoerfallId());
        entry.updateInt("schadenId", nz.getSchadenId());        
        entry.updateString("betreff", nz.getBetreff());
        entry.updateString("text", nz.getText());
        entry.updateString("tag", nz.getTag());
        entry.updateTimestamp("created", nz.getCreated());
        entry.updateTimestamp("modified", nz.getModified());
        entry.updateInt("status", nz.getStatus());

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
    public static void deleteFromNotizen(Connection con, int keyId) throws SQLException {
        String sql = "SELECT id, status FROM notizen WHERE id = ?";
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
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromnotizen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM notizen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param eigene
     * @return
     * @throws SQLException
     */
    public static NotizenObj[] loadNotizen(Connection con, boolean eigene, int status) throws SQLException {
        String sql = "SELECT * FROM notizen";

        if (eigene && status != -1) {
            sql = "SELECT * FROM notizen WHERE benutzerId = ? AND status = ?";
        } else if (eigene) {
            sql = "SELECT * FROM notizen WHERE benutzerId = ?";
        } else if (status != -1) {
            sql = "SELECT * FROM notizen WHERE status = ?";
        }


        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if (eigene && status != -1) {
            statement.setInt(1, BasicRegistry.currentUser.getId());
            statement.setInt(2, status);
        } else if (eigene) {
            statement.setInt(1, BasicRegistry.currentUser.getId());
        } else if (status != -1) {
            statement.setInt(1, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }

    public static NotizenObj getNotizenResult(ResultSet entry) throws SQLException {
        NotizenObj nz = new NotizenObj();

        nz.setId(entry.getInt("id"));
        nz.setCreatorId(entry.getInt("creatorId"));
        nz.setPrivate(entry.getBoolean("priv"));
        nz.setKundenKennung(entry.getString("kundenKennung"));
        nz.setVersichererKennung(entry.getInt("versichererId"));
        nz.setBenutzerKennung(entry.getInt("benutzerId"));
        nz.setProduktId(entry.getInt("produktId"));
        nz.setVertragId(entry.getInt("vertragId"));
        nz.setStoerfallId(entry.getInt("stoerfallId"));
        nz.setSchadenId(entry.getInt("schadenId"));
        nz.setBetreff(entry.getString("betreff"));
        nz.setText(entry.getString("text"));
        nz.setTag(entry.getString("tag"));
        nz.setCreated(entry.getTimestamp("created"));
        nz.setModified(entry.getTimestamp("modified"));
        nz.setStatus(entry.getInt("status"));

        return nz;
    }

    /**
     * 
     * @param con
     * @param kundenKennung
     * @return
     * @throws SQLException
     */
    public static NotizenObj[] loadKundenNotizen(Connection con, String kundenKennung, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kundenKennung);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }

    /**
     * 
     * @param con
     * @param versichererKennung
     * @return
     * @throws SQLException
     */
    public static NotizenObj[] loadNotizenVersicherer(Connection con, int versichererKennung, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE versichererId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, versichererKennung);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
            
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }

    /**
     *
     * @param con
     * @param benutzerKennung
     * @return
     * @throws SQLException
     */
    public static NotizenObj[] loadNotizenBenutzer(Connection con, int benutzerKennung, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE benutzerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE benutzerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benutzerKennung);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }
    
    /**
     * 
     * @param con
     * @param prodid
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static NotizenObj[] loadNotizenProdukt(Connection con, int prodid, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE produktId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE produktId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, prodid);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }
    
    /**
     * Notizen Vertrag
     * @param con
     * @param vtrid
     * @param status
     * @return NotizenObj
     * @throws SQLException 
     */
    
    public static NotizenObj[] loadNotizenVertrag(Connection con, int vtrid, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE vertragId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE vertragId = ?";
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
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }
    
    /**
     * 
     * @param con
     * @param vtrid
     * @param status
     * @return NotizenObj
     * @throws SQLException 
     */
    
    public static NotizenObj[] loadNotizenStoerfall(Connection con, int storeid, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE stoerfallId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE stoerfallId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, storeid);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }
   
    /**
     * 
     * @param con
     * @param schaden
     * @param status
     * @return NotizenObj.class
     * @throws SQLException 
     */
    
    public static NotizenObj[] loadNotizenSchaden(Connection con, int schaden, int status) throws SQLException {
        String sql = "SELECT * FROM notizen WHERE schadenId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM notizen WHERE schadenId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, schaden);

        if (status != -1) {
            statement.setInt(2, status);
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

        NotizenObj[] nz = new NotizenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            nz[i] = NotizenSQLMethods.getNotizenResult(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return nz;
    }
}
