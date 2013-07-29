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

import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.FileTypeDetection;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DokumentenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creatorId
     * @param kundenId
     * @param benutzerId
     * @param filetype
     * @param name
     * @param fullPath
     * @param label
     * @param beschreibung
     * @param checksum
     * @param tag
     * @param created
     * @param modified
     * @param lastviewed
     * @param viewcount
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntodokumente(Connection con, DokumentenObj dok)
            throws SQLException {

        int generatedId = -1;
        String sql = "INSERT INTO dokumente (creatorId, kundenKennung, benutzerId, versichererId,"
                + "produktId, bpId, stoerId, vertragId, filetype, name, "
                + "fullPath, label, beschreibung, checksum, tag, created, modified, "
                + "lastviewed, viewcount, status, schadenId)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, dok.getCreatorId());
        statement.setString(2, dok.getKundenKennung());
        statement.setInt(3, dok.getBenutzerId());
        statement.setInt(4, dok.getVersichererId());
        statement.setInt(5, dok.getProduktId());
        statement.setInt(6, dok.getBpId());
        statement.setInt(7, dok.getStoerId());
        statement.setInt(8, dok.getVertragId());
        statement.setInt(9, dok.getFiletype());
        statement.setString(10, dok.getName());
        statement.setString(11, dok.getFullPath());
        statement.setString(12, dok.getLabel());
        statement.setString(13, dok.getBeschreibung());
        statement.setString(14, dok.getChecksum());
        statement.setString(15, dok.getTag());
        statement.setTimestamp(16, dok.getCreated());
        statement.setTimestamp(17, dok.getModified());
        statement.setTimestamp(18, dok.getLastviewed());
        statement.setInt(19, dok.getViewcount());
        statement.setInt(20, dok.getStatus());
        statement.setInt(21, dok.getStoerId());
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
     * @param creatorId
     * @param kundenId
     * @param benutzerId
     * @param filetype
     * @param name
     * @param fullPath
     * @param label
     * @param beschreibung
     * @param checksum
     * @param tag
     * @param created
     * @param modified
     * @param lastviewed
     * @param viewcount
     * @param status
     * @return
     * @throws SQLException
     * @deprecated 
     */
    public static int insertIntodokumente(Connection con, int creatorId, String kundenId, int benutzerId, int filetype, String name,
            String fullPath, String label, String beschreibung, String checksum, String tag,
            java.sql.Timestamp created, java.sql.Timestamp modified, java.sql.Timestamp lastviewed, int viewcount, int status)
            throws SQLException {

        int generatedId = -1;
        String sql = "INSERT INTO dokumente (creatorId, kundenKennung, benutzerId, filetype, name, "
                + "fullPath, label, beschreibung, checksum, tag, created, modified, lastviewed, viewcount, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, creatorId);
        statement.setString(2, kundenId);
        statement.setInt(3, benutzerId);
        statement.setInt(4, filetype);
        statement.setString(5, name);
        statement.setString(6, fullPath);
        statement.setString(7, label);
        statement.setString(8, beschreibung);
        statement.setString(9, checksum);
        statement.setString(10, tag);
        statement.setTimestamp(11, created);
        statement.setTimestamp(12, modified);
        statement.setTimestamp(13, lastviewed);
        statement.setInt(14, viewcount);
        statement.setInt(15, status);
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
     * @param kundenId
     * @param benutzerId
     * @param filetype
     * @param name
     * @param fullPath
     * @param label
     * @param beschreibung
     * @param checksum
     * @param tag
     * @param created
     * @param modified
     * @param lastviewed
     * @param viewcount
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated 
     */
    public static boolean updatedokumente(Connection con, int keyId, int creatorId, int kundenId, int benutzerId, int filetype, String name,
            String fullPath, String label, String beschreibung, String checksum, String tag,
            java.sql.Timestamp created, java.sql.Timestamp modified, java.sql.Timestamp lastviewed, int viewcount, int status)
            throws SQLException {
        String sql = "SELECT * FROM dokumente WHERE id = ?";
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
        entry.updateInt("kundenId", kundenId);
        entry.updateInt("benutzerId", benutzerId);
        entry.updateInt("filetype", filetype);
        if (name != null) {
            entry.updateString("name", name);
        }
        if (fullPath != null) {
            entry.updateString("fullPath", fullPath.replaceAll("/", File.separator));
        }
        if (label != null) {
            entry.updateString("label", label);
        }
        if (beschreibung != null) {
            entry.updateString("beschreibung", beschreibung);
        }
        if (checksum != null) {
            entry.updateString("checksum", checksum);
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
        if (lastviewed != null) {
            entry.updateTimestamp("lastviewed", lastviewed);
        }
        entry.updateInt("viewcount", viewcount);
        entry.updateInt("status", status);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    public static boolean updatedokumente(Connection con, DokumentenObj dok)
            throws SQLException {
        String sql = "SELECT * FROM dokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, dok.getId());
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

        entry.updateInt("creatorId", dok.getCreatorId());
        entry.updateString("kundenKennung", dok.getKundenKennung());
        entry.updateInt("benutzerId", dok.getBenutzerId());
        entry.updateInt("versichererId", dok.getVersichererId());
        entry.updateInt("produktId", dok.getProduktId());
        entry.updateInt("bpId", dok.getBpId());
        entry.updateInt("stoerId", dok.getStoerId());
        entry.updateInt("schadenId", dok.getSchadenId());
        entry.updateInt("vertragId", dok.getVertragId());
        entry.updateInt("filetype", dok.getFiletype());
        entry.updateString("name", dok.getName());
        entry.updateString("fullPath", dok.getFullPath().replaceAll(File.separator, "/"));
        entry.updateString("label", dok.getLabel());
        entry.updateString("beschreibung", dok.getBeschreibung());
        entry.updateString("checksum", dok.getChecksum());
        entry.updateString("tag", dok.getTag());
        entry.updateTimestamp("created", dok.getCreated());
        entry.updateTimestamp("modified", dok.getModified());
        entry.updateTimestamp("lastviewed", dok.getLastviewed());
        entry.updateInt("viewcount", dok.getViewcount());
        entry.updateInt("status", dok.getStatus());

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
    public static void deleteEndgueltigFromDokumente(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM dokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static void archiveFromDokumente(Connection con, DokumentenObj dok) throws SQLException {
        if (dok == null) {
            return;
        }

        String sql = "UPDATE dokumente SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, dok.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        dok.setStatus(Status.DELETED);
    }

    public static void deleteFromDokumente(Connection con, DokumentenObj dok) throws SQLException {
        if (dok == null) {
            return;
        }

        String sql = "UPDATE dokumente SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, dok.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        dok.setStatus(Status.DELETED);
    }

    /**
     * 
     * @param con
     * @param Dok
     * @return
     * @throws SQLException
     */
    public static DokumentenObj getDokument(Connection con, String name, String checksum) throws SQLException {
        DokumentenObj dokument = null;
        //System.out.println("name: " + dok.getName());
        String sql = "Select * FROM dokumente WHERE name = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, name);

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

        // Check nach mehreren Doks. die gleich heissen
        if (rows > 1) {
            entry.close();
            sql = "Select * FROM dokumente WHERE name = ? AND checksum = ?";
            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, name);
            statement.setString(2, checksum);

            entry = statement.executeQuery();

            entry.last();
            rows = entry.getRow();
            entry.beforeFirst();

            if (rows == 0) {
                entry.close();
                statement.close();
                con.close();
                return null;
            }
        }

        entry.next();

        dokument = new DokumentenObj();

        int viewcount = entry.getByte("viewcount");
        entry.updateInt("viewcount", viewcount++);

        dokument.setBenutzerId(entry.getInt("benutzerId"));
        dokument.setVersichererId(entry.getInt("versichererId"));
        dokument.setProduktId(entry.getInt("produktId"));
        dokument.setBpId(entry.getInt("bpId"));
        dokument.setStoerId(entry.getInt("stoerId"));
        dokument.setSchadenId(entry.getInt("schadenId"));        
        dokument.setVertragId(entry.getInt("vertragId"));
        dokument.setBeschreibung(entry.getString("beschreibung"));
        dokument.setChecksum(entry.getString("checksum"));
        dokument.setCreated(entry.getTimestamp("created"));
        dokument.setCreatorId(entry.getInt("creatorId"));
        dokument.setFiletype(entry.getInt("filetype"));
        dokument.setFullPath(entry.getString("fullPath").replaceAll("/", File.separator));
        dokument.setId(entry.getInt("id"));
        dokument.setKundenKennung(entry.getString("kundenKennung"));
        dokument.setLabel(entry.getString("label"));
        dokument.setLastviewed(entry.getTimestamp("lastviewed"));
        dokument.setModified(entry.getTimestamp("modified"));
        dokument.setName(entry.getString("name"));
        dokument.setStatus(entry.getInt("status"));
        dokument.setTag(entry.getString("tag"));
        dokument.setViewcount(entry.getInt("viewcount"));

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();

        return dokument;
    }

    /**
     * 
     * @param con
     * @param file
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static boolean createKundenFile(Connection con, File file, String kennung) throws SQLException, IOException {
        int generatedId = -1;
        String sql = "INSERT INTO dokumente (creatorId, kundenKennung, benutzerId, filetype, name, "
                + "fullPath, label, beschreibung, checksum, tag, created, modified, lastviewed, viewcount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, BasicRegistry.currentUser.getId());
        statement.setString(2, kennung);
        statement.setInt(3, BasicRegistry.currentUser.getId());

        statement.setInt(4, FileTypeDetection.getFileType(file));

        statement.setString(5, file.getName());
        statement.setString(6, file.getCanonicalPath());
        statement.setString(7, null);
        statement.setString(8, null);

        long checksum = FileTools.getChecksum(file.getPath());

        statement.setString(9, String.valueOf(checksum));
        statement.setString(10, "Standard");
        statement.setTimestamp(11, new java.sql.Timestamp(System.currentTimeMillis()));
        statement.setTimestamp(12, new java.sql.Timestamp(System.currentTimeMillis()));
        statement.setTimestamp(13, new java.sql.Timestamp(System.currentTimeMillis()));
        statement.setInt(14, 1);
        statement.setInt(15, 0);
        statement.execute();
        ResultSet auto = statement.getGeneratedKeys();

        if (auto.next()) {
            generatedId = auto.getInt(1);
        } else {
            generatedId = -1;
        }

        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param doc
     * @throws SQLException
     */
    public static void increaseViewcount(Connection con, DokumentenObj doc) throws SQLException {
        if (doc == null) {
            return;
        }

        String sql = "Select id, lastviewed, viewcount FROM dokumente WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, doc.getId());

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            return;
        }

        entry.next();

        int viewcount = entry.getInt("viewcount");
        viewcount++;

        entry.updateInt("viewcount", viewcount);
        entry.updateTimestamp("lastviewed", new java.sql.Timestamp(System.currentTimeMillis()));

        entry.close();
        statement.close();
        con.close();
    }

    
    /**
     * 
     */
    
    public static DokumentenObj[] loadAlleDokumente(Connection con, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        if (status != -1) {
            statement.setInt(1, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }
    
    /**
     * 
     * @param con
     * @param kdnr
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadKundenDokumente(Connection con, String kdnr, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kdnr);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param bid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadBenutzerDokumente(Connection con, int bid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE benutzerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE benutzerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, bid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param versid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadVersichererDokumente(Connection con, int versid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE versichererId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE versichererId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, versid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param pid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadProduktDokumente(Connection con, int pid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE produktId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE produktId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, pid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param pid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadBeratungsprotokollDokumente(Connection con, int bpid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE bpId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE bpId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, bpid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param stoerid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadStoerfaelleDokumente(Connection con, int stoerid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE stoerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE stoerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, stoerid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }
    
    
    /**
     * 
     * @param con
     * @param stoerid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadSchadenDokumente(Connection con, int stoerid, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE schadenId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE schadenId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, stoerid);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param con
     * @param vertragId
     * @param status
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadVertragDokumente(Connection con, int vertragId, int status) throws SQLException {

        String sql = "SELECT * FROM dokumente WHERE vertragId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM dokumente WHERE vertragId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, vertragId);

        if (status != -1) {
            statement.setInt(2, status); // Andere STatus!! Vertraege!
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

        DokumentenObj[] doks = loadDokumente(entry, rows);
        entry.close();
        statement.close();
        con.close();

        return doks;
    }

    /**
     * 
     * @param entry
     * @param rows
     * @return
     * @throws SQLException 
     */
    public static DokumentenObj[] loadDokumente(ResultSet entry, int rows) throws SQLException {
        DokumentenObj[] doks = new DokumentenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            doks[i] = new DokumentenObj();

            doks[i].setId(entry.getInt("id"));
            doks[i].setCreatorId(entry.getInt("creatorId"));
            doks[i].setKundenKennung(entry.getString("kundenKennung"));
            doks[i].setBenutzerId(entry.getInt("benutzerId"));
            doks[i].setVersichererId(entry.getInt("versichererId"));
            doks[i].setProduktId(entry.getInt("produktId"));
            doks[i].setBpId(entry.getInt("bpId"));
            doks[i].setStoerId(entry.getInt("stoerId"));
            doks[i].setSchadenId(entry.getInt("schadenId"));
            doks[i].setVertragId(entry.getInt("vertragId"));

            doks[i].setFiletype(entry.getInt("filetype"));
            doks[i].setName(entry.getString("name"));
            doks[i].setFullPath(entry.getString("fullPath"));
            doks[i].setLabel(entry.getString("label"));
            doks[i].setBeschreibung(entry.getString("beschreibung"));
            doks[i].setChecksum(entry.getString("checksum"));

            doks[i].setTag(entry.getString("tag"));
            doks[i].setCreated(entry.getTimestamp("created"));
            doks[i].setModified(entry.getTimestamp("modified"));
            doks[i].setLastviewed(entry.getTimestamp("lastviewed"));
            doks[i].setViewcount(entry.getInt("viewcount"));
            doks[i].setStatus(entry.getInt("status"));
        }

        return doks;
    }
}
