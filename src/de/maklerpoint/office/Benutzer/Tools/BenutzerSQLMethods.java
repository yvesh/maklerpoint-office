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
package de.maklerpoint.office.Benutzer.Tools;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.NoUserException;
import de.maklerpoint.office.Session.SessionObj;
import de.maklerpoint.office.Session.Tools.SessionSQLMethods;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param parentId
     * @param firmenId
     * @param level
     * @param unterVermittler
     * @param kennung
     * @param anrede
     * @param vorname
     * @param vorname2
     * @param weitereVornamen
     * @param nachname
     * @param firma
     * @param strasse
     * @param strasse2
     * @param plz
     * @param ort
     * @param addresseZusatz
     * @param addresseZusatz2
     * @param land
     * @param telefon
     * @param telefon2
     * @param fax
     * @param fax2
     * @param mobil
     * @param mobil2
     * @param geburtsDatum
     * @param email
     * @param email2
     * @param homepage
     * @param homepage2
     * @param username
     * @param password
     * @param comments
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param created
     * @param modified
     * @param lastlogin
     * @param logincount
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     * @deprecated BenutzerObj benutzen
     */
    public static int insertIntoBenutzer(Connection con, int parentId, int firmenId, int level, boolean unterVermittler, String kennung,
            String anrede, String vorname, String vorname2, String weitereVornamen, String nachname,
            String firma, String strasse, String strasse2, String plz, String ort,
            String addresseZusatz, String addresseZusatz2, String land, String telefon, String telefon2,
            String fax, String fax2, String mobil, String mobil2, String geburtsDatum,
            String email, String email2, String homepage, String homepage2, String username,
            String password, String comments, String custom1, String custom2, String custom3,
            String custom4, String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, java.sql.Timestamp lastlogin,
            int logincount, int status, String bundesland) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO Benutzer (parentId, firmenId, level, unterVermittler, kennung, anrede, "
                + "vorname, vorname2, weitereVornamen, nachname, firma, strasse, strasse2, plz, ort, "
                + "addresseZusatz, addresseZusatz2, land, telefon, telefon2, fax, fax2, mobil, mobil2, "
                + "geburtsDatum, email, email2, homepage, homepage2, username, password, comments, custom1, "
                + "custom2, custom3, custom4, custom5, created, modified, lastlogin, logincount, status, bundesland)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, parentId);
        statement.setInt(2, firmenId);
        statement.setInt(3, level);
        statement.setBoolean(4, unterVermittler);
        statement.setString(5, kennung);
        statement.setString(6, anrede);
        statement.setString(7, vorname);
        statement.setString(8, vorname2);
        statement.setString(9, weitereVornamen);
        statement.setString(10, nachname);
        statement.setString(11, firma);
        statement.setString(12, strasse);
        statement.setString(13, strasse2);
        statement.setString(14, plz);
        statement.setString(15, ort);
        statement.setString(16, addresseZusatz);
        statement.setString(17, addresseZusatz2);
        statement.setString(18, land);
        statement.setString(19, telefon);
        statement.setString(20, telefon2);
        statement.setString(21, fax);
        statement.setString(22, fax2);
        statement.setString(23, mobil);
        statement.setString(24, mobil2);
        statement.setString(25, geburtsDatum);
        statement.setString(26, email);
        statement.setString(27, email2);
        statement.setString(28, homepage);
        statement.setString(29, homepage2);
        statement.setString(30, username);
        statement.setString(31, password);
        statement.setString(32, comments);
        statement.setString(33, custom1);
        statement.setString(34, custom2);
        statement.setString(35, custom3);
        statement.setString(36, custom4);
        statement.setString(37, custom5);
        statement.setTimestamp(38, created);
        statement.setTimestamp(39, modified);
        statement.setTimestamp(40, lastlogin);
        statement.setInt(41, logincount);
        statement.setInt(42, status);
        statement.setString(43, bundesland);
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
     * @param ben
     * @return
     * @throws SQLException 
     */
    public static int insertIntoBenutzer(Connection con, BenutzerObj ben) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO benutzer (parentId, firmenId, level, unterVermittler, kennung, anrede, "
                + "vorname, vorname2, weitereVornamen, nachname, firma, strasse, "
                + "strasse2, plz, ort, addresseZusatz, addresseZusatz2, land, "
                + "telefon, telefon2, fax, fax2, mobil, mobil2, "
                + "geburtsDatum, email, email2, homepage, homepage2, username, "
                + "password, comments, custom1, custom2, custom3, custom4, "
                + "custom5, created, modified, lastlogin, logincount, status, bundesland"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, ben.getParentId());
        statement.setInt(2, ben.getFirmenId());
        statement.setInt(3, ben.getLevel());
        statement.setBoolean(4, ben.isUnterVermittler());
        statement.setString(5, ben.getKennung());
        statement.setString(6, ben.getAnrede());
        statement.setString(7, ben.getVorname());
        statement.setString(8, ben.getVorname2());
        statement.setString(9, ben.getWeitereVornamen());
        statement.setString(10, ben.getNachname());
        statement.setString(11, null);
        statement.setString(12, ben.getStrasse());
        statement.setString(13, ben.getStrasse2());
        statement.setString(14, ben.getPlz());
        statement.setString(15, ben.getOrt());
        statement.setString(16, ben.getAddresseZusatz());
        statement.setString(17, ben.getAddresseZusatz2());
        statement.setString(18, ben.getLand());
        statement.setString(19, ben.getTelefon());
        statement.setString(20, ben.getTelefon2());
        statement.setString(21, ben.getFax());
        statement.setString(22, ben.getFax2());
        statement.setString(23, ben.getMobil());
        statement.setString(24, ben.getMobil2());
        statement.setString(25, ben.getGeburtsDatum());
        statement.setString(26, ben.getEmail());
        statement.setString(27, ben.getEmail2());
        statement.setString(28, ben.getHomepage());
        statement.setString(29, ben.getHomepage2());
        statement.setString(30, ben.getUsername());
        statement.setString(31, ben.getPassword());
        statement.setString(32, ben.getComments());
        statement.setString(33, ben.getCustom1());
        statement.setString(34, ben.getCustom2());
        statement.setString(35, ben.getCustom3());
        statement.setString(36, ben.getCustom4());
        statement.setString(37, ben.getCustom5());
        statement.setTimestamp(38, ben.getCreated());
        statement.setTimestamp(39, ben.getModified());
        statement.setTimestamp(40, ben.getLastlogin());
        statement.setInt(41, ben.getLogincount());
        statement.setInt(42, ben.getStatus());
        statement.setString(43, ben.getBundesland());
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
     * @param firmenId
     * @param level
     * @param unterVermittler
     * @param kennung
     * @param anrede
     * @param vorname
     * @param vorname2
     * @param weitereVornamen
     * @param nachname
     * @param firma
     * @param strasse
     * @param strasse2
     * @param plz
     * @param ort
     * @param addresseZusatz
     * @param addresseZusatz2
     * @param land
     * @param telefon
     * @param telefon2
     * @param fax
     * @param fax2
     * @param mobil
     * @param mobil2
     * @param geburtsDatum
     * @param email
     * @param email2
     * @param homepage
     * @param homepage2
     * @param username
     * @param password
     * @param comments
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param created
     * @param modified
     * @param lastlogin
     * @param logincount
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateBenutzer(Connection con, int keyId, int parentId, int firmenId, int level,
            boolean unterVermittler, String kennung,
            String anrede, String vorname, String vorname2, String weitereVornamen, String nachname,
            String firma, String strasse, String strasse2, String plz, String ort,
            String addresseZusatz, String addresseZusatz2, String land, String telefon, String telefon2,
            String fax, String fax2, String mobil, String mobil2, String geburtsDatum,
            String email, String email2, String homepage, String homepage2, String username,
            String password, String comments, String custom1, String custom2, String custom3,
            String custom4, String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, java.sql.Timestamp lastlogin,
            int logincount, int status) throws SQLException {
        String sql = "SELECT * FROM Benutzer WHERE id = ?";
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

        entry.updateInt("parentId", parentId);
        entry.updateInt("firmenId", firmenId);
        entry.updateInt("level", level);
        entry.updateBoolean("unterVermittler", unterVermittler);
        if (kennung != null) {
            entry.updateString("kennung", kennung);
        }
        if (anrede != null) {
            entry.updateString("anrede", anrede);
        }
        if (vorname != null) {
            entry.updateString("vorname", vorname);
        }
        if (vorname2 != null) {
            entry.updateString("vorname2", vorname2);
        }
        if (weitereVornamen != null) {
            entry.updateString("weitereVornamen", weitereVornamen);
        }
        if (nachname != null) {
            entry.updateString("nachname", nachname);
        }
        if (firma != null) {
            entry.updateString("firma", firma);
        }
        if (strasse != null) {
            entry.updateString("strasse", strasse);
        }
        if (strasse2 != null) {
            entry.updateString("strasse2", strasse2);
        }
        if (plz != null) {
            entry.updateString("plz", plz);
        }
        if (ort != null) {
            entry.updateString("ort", ort);
        }
        if (addresseZusatz != null) {
            entry.updateString("addresseZusatz", addresseZusatz);
        }
        if (addresseZusatz2 != null) {
            entry.updateString("addresseZusatz2", addresseZusatz2);
        }
        if (land != null) {
            entry.updateString("land", land);
        }
        if (telefon != null) {
            entry.updateString("telefon", telefon);
        }
        if (telefon2 != null) {
            entry.updateString("telefon2", telefon2);
        }
        if (fax != null) {
            entry.updateString("fax", fax);
        }
        if (fax2 != null) {
            entry.updateString("fax2", fax2);
        }
        if (mobil != null) {
            entry.updateString("mobil", mobil);
        }
        if (mobil2 != null) {
            entry.updateString("mobil2", mobil2);
        }
        if (geburtsDatum != null) {
            entry.updateString("geburtsDatum", geburtsDatum);
        }
        if (email != null) {
            entry.updateString("email", email);
        }
        if (email2 != null) {
            entry.updateString("email2", email2);
        }
        if (homepage != null) {
            entry.updateString("homepage", homepage);
        }
        if (homepage2 != null) {
            entry.updateString("homepage2", homepage2);
        }
        if (username != null) {
            entry.updateString("username", username);
        }
        if (password != null) {
            entry.updateString("password", password);
        }
        if (comments != null) {
            entry.updateString("comments", comments);
        }
        if (custom1 != null) {
            entry.updateString("custom1", custom1);
        }
        if (custom2 != null) {
            entry.updateString("custom2", custom2);
        }
        if (custom3 != null) {
            entry.updateString("custom3", custom3);
        }
        if (custom4 != null) {
            entry.updateString("custom4", custom4);
        }
        if (custom5 != null) {
            entry.updateString("custom5", custom5);
        }
        if (created != null) {
            entry.updateTimestamp("created", created);
        }
        if (modified != null) {
            entry.updateTimestamp("modified", modified);
        }
        if (lastlogin != null) {
            entry.updateTimestamp("lastlogin", lastlogin);
        }
        entry.updateInt("logincount", logincount);
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
     * @param user
     * @throws SQLException
     */
    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException
     */
    public static void deleteEndgueltigFrombenutzer(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM benutzer WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param userkenn
     * @param pw
     * @return
     * @throws SQLException
     */
    public static BenutzerObj loadBenutzer(Connection con, String userkenn, String pw) throws SQLException {
        String sql = "Select * FROM benutzer WHERE kennung = ? AND status = 0";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, userkenn);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        String login = null;
        String password = null;

        if (rows == 0) {
            entry.close();
            statement.close();
            sql = "Select * FROM benutzer WHERE username = ? AND status = 0";
            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, userkenn);

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

        password = entry.getString("password");

        if (!password.equals(pw)) {
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        BenutzerObj benutzer = new BenutzerObj(entry.getInt("id"), entry.getString("kennung"), entry.getInt("level"));
        benutzer.setParentId(entry.getInt("parentId"));
        benutzer.setFirmenId(entry.getInt("firmenId"));
        benutzer.setLevel(entry.getInt("level"));
        benutzer.setUnterVermittler(entry.getBoolean("unterVermittler"));
        benutzer.setAnrede(entry.getString("anrede"));
        benutzer.setVorname(entry.getString("vorname"));
        benutzer.setVorname2(entry.getString("vorname2"));
        benutzer.setWeitereVornamen(entry.getString("weitereVornamen"));
        benutzer.setNachname(entry.getString("nachname"));
        benutzer.setStrasse(entry.getString("strasse"));
        benutzer.setStrasse2(entry.getString("strasse2"));
        benutzer.setPlz(entry.getString("plz"));
        benutzer.setOrt(entry.getString("ort"));
        benutzer.setAddresseZusatz(entry.getString("addresseZusatz"));
        benutzer.setAddresseZusatz2(entry.getString("addresseZusatz2"));
        benutzer.setBundesland(entry.getString("bundesland"));
        benutzer.setLand(entry.getString("land"));
        benutzer.setGeburtsDatum(entry.getString("geburtsdatum"));
        benutzer.setTelefon(entry.getString("telefon"));
        benutzer.setTelefon2(entry.getString("telefon2"));
        benutzer.setFax(entry.getString("fax"));
        benutzer.setFax2(entry.getString("fax2"));
        benutzer.setMobil(entry.getString("mobil"));
        benutzer.setMobil2(entry.getString("mobil2"));
        benutzer.setEmail(entry.getString("email"));
        benutzer.setEmail2(entry.getString("email2"));
        benutzer.setHomepage(entry.getString("homepage"));
        benutzer.setHomepage2(entry.getString("homepage2"));
        benutzer.setUsername(entry.getString("username"));
        benutzer.setPassword(entry.getString("password"));
        benutzer.setComments(entry.getString("comments"));
        benutzer.setCustom1(entry.getString("custom1"));
        benutzer.setCustom2(entry.getString("custom2"));
        benutzer.setCustom3(entry.getString("custom3"));
        benutzer.setCustom4(entry.getString("custom4"));
        benutzer.setCustom5(entry.getString("custom5"));

        int logincount = entry.getInt("logincount") + 1;
        benutzer.setLogincount(logincount);
        benutzer.setCreated(entry.getTimestamp("created"));
        benutzer.setModified(entry.getTimestamp("modified"));
        benutzer.setLastlogin(entry.getTimestamp("lastlogin"));
        benutzer.setWebenabled(entry.getBoolean("webenabled"));

        entry.updateTimestamp("lastlogin", new java.sql.Timestamp(System.currentTimeMillis()));
        entry.updateInt("logincount", logincount);

        entry.updateRow();

        entry.close();
        statement.close();
        con.close();

        return benutzer;
    }

    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     * @throws NoUserException
     */
    public static BenutzerObj[] loadAlleBenutzer(Connection con, int status) throws SQLException, NoUserException {
        BenutzerObj[] alleBenutzer = null;
        String sql = "Select * FROM benutzer WHERE status = ?";

        if (status == -1) {
            sql = "Select * FROM benutzer";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE);

        if (status != -1) {
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
            if (status == Status.NORMAL || status == -1) {
                throw new NoUserException();
            } else {
                return null;
            }
        }

        alleBenutzer = new BenutzerObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            alleBenutzer[i] = getResultSet(entry, false);
        }

        entry.close();
        statement.close();
        con.close();

        return alleBenutzer;
    }

    public static BenutzerObj getResultSet(ResultSet entry, boolean pw) throws SQLException {
        BenutzerObj ben = new BenutzerObj();

        ben.setId(entry.getInt("id"));
        ben.setParentId(entry.getInt("parentId"));
        ben.setFirmenId(entry.getInt("firmenId"));
        ben.setLevel(entry.getInt("level"));
        ben.setUnterVermittler(entry.getBoolean("unterVermittler"));
        ben.setKennung(entry.getString("kennung"));

        ben.setAnrede(entry.getString("anrede"));
        ben.setVorname(entry.getString("vorname"));
        ben.setVorname2(entry.getString("vorname2"));
        ben.setWeitereVornamen(entry.getString("weitereVornamen"));
        ben.setNachname(entry.getString("nachname"));
        ben.setStrasse(entry.getString("strasse"));
        ben.setStrasse2(entry.getString("strasse2"));      

        ben.setPlz(entry.getString("plz"));
        ben.setOrt(entry.getString("ort"));
        ben.setAddresseZusatz(entry.getString("addresseZusatz"));
        ben.setAddresseZusatz2(entry.getString("addresseZusatz2"));
        ben.setBundesland(entry.getString("bundesland"));
        ben.setLand(entry.getString("land"));

        ben.setGeburtsDatum(entry.getString("geburtsdatum"));
        ben.setTelefon(entry.getString("telefon"));
        ben.setTelefon2(entry.getString("telefon2"));
        ben.setFax(entry.getString("fax"));
        ben.setFax2(entry.getString("fax2"));
        ben.setMobil(entry.getString("mobil"));
        ben.setMobil2(entry.getString("mobil2"));
        ben.setEmail(entry.getString("email"));
        ben.setEmail2(entry.getString("email2"));
        ben.setHomepage(entry.getString("homepage"));
        ben.setHomepage2(entry.getString("homepage2"));
        ben.setUsername(entry.getString("username"));

        if (pw) {
            ben.setPassword(entry.getString("password"));
        }

        ben.setComments(entry.getString("comments"));
        ben.setCustom1(entry.getString("custom1"));
        ben.setCustom2(entry.getString("custom2"));
        ben.setCustom3(entry.getString("custom3"));
        ben.setCustom4(entry.getString("custom4"));
        ben.setCustom5(entry.getString("custom5"));
        ben.setCreated(entry.getTimestamp("created"));
        ben.setModified(entry.getTimestamp("modified"));
        ben.setLastlogin(entry.getTimestamp("lastlogin"));
        ben.setLogincount(entry.getInt("logincount"));
        ben.setWebenabled(entry.getBoolean("webenabled"));
        ben.setStatus(entry.getInt("status"));

        return ben;
    }

    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     */
    public static BenutzerObj getBenutzer(Connection con, int id) throws SQLException {
        String sql = "Select * FROM benutzer WHERE id = ? AND status = 0";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);

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
        BenutzerObj benutzer = BenutzerSQLMethods.getResultSet(entry, false);

        entry.close();
        statement.close();
        con.close();

        return benutzer;
    }

    public static BenutzerObj getBenutzer(Connection con, String username) throws SQLException {
        String sql = "Select * FROM benutzer WHERE username = ? AND status = 0";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, username);

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

        BenutzerObj benutzer = BenutzerSQLMethods.getResultSet(entry, false);
        
        entry.close();
        statement.close();
        con.close();

        return benutzer;
    }

    /**
     * HMM ob das geht TODO     
     * @return
     * @throws SQLException 
     */
    public static BenutzerObj[] getActiveBenutzer() throws SQLException {

        SessionObj[] activeSessions = SessionSQLMethods.getAllSessions(DatabaseConnection.open());

        if (activeSessions == null) {
            return null;
        }

        ArrayList<Integer> al = new ArrayList<Integer>();

        BenutzerObj[] benutzer = new BenutzerObj[activeSessions.length];

        for (int i = 0; i < activeSessions.length; i++) {
            if (!al.contains(activeSessions[i].getBenutzerid())) {
                al.add(activeSessions[i].getBenutzerid());
                benutzer[i] = BenutzerSQLMethods.getBenutzer(DatabaseConnection.open(), activeSessions[i].getBenutzerid());
            }
        }

        return benutzer;
    }

    public static void deleteFromBenutzer(Connection con, BenutzerObj ben) throws SQLException {
        if (ben == null) {
            return;
        }

        String sql = "UPDATE benutzer SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, ben.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        ben.setStatus(Status.DELETED);
    }

    public static void archiveFromBenutzer(Connection con, BenutzerObj ben) throws SQLException {
        if (ben == null) {
            return;
        }

        String sql = "UPDATE benutzer SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, ben.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        ben.setStatus(Status.ARCHIVED);
    }
}
