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

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayStringTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FirmenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creator
     * @param parentFirma
     * @param betreuer
     * @param type
     * @param kundenNr
     * @param firmenName
     * @param firmenNameZusatz
     * @param firmenNameZusatz2
     * @param firmenStrasse
     * @param firmenPLZ
     * @param firmenStadt
     * @param firmenBundesland
     * @param firmenLand
     * @param firmenTyp
     * @param firmenSize
     * @param firmenSitz
     * @param firmenPostfach
     * @param firmenPostfachName
     * @param firmenPostfachPlz
     * @param firmenPostfachOrt
     * @param firmenRechtsform
     * @param firmenEinkommen
     * @param firmenBranche
     * @param firmenGruendungDatum
     * @param firmenGeschaeftsfuehrer
     * @param firmenProKura
     * @param firmenStandorte
     * @param communication1
     * @param communication2
     * @param communication3
     * @param communication4
     * @param communication5
     * @param communication6
     * @param communication1Type
     * @param communication2Type
     * @param communication3Type
     * @param communication4Type
     * @param communication5Type
     * @param communication6Type
     * @param kontonummer
     * @param bankleitzahl
     * @param bankname
     * @param bankeigentuemer
     * @param kontoiban
     * @param kontobic
     * @param werber
     * @param comments
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     * @deprecated 
     */
    public static int insertIntofirmenkunden(Connection con, int creator, int parentFirma, int betreuer, int type, String kundenNr,
            String firmenName, String firmenNameZusatz, String firmenNameZusatz2, String firmenStrasse, String firmenPLZ,
            String firmenStadt, String firmenBundesland, String firmenLand, String firmenTyp, String firmenSize,
            String firmenSitz, boolean firmenPostfach, String firmenPostfachName, String firmenPostfachPlz, String firmenPostfachOrt,
            String firmenRechtsform, String firmenEinkommen, String firmenBranche, java.sql.Date firmenGruendungDatum, String firmenGeschaeftsfuehrer,
            String firmenProKura, String firmenStandorte, String communication1, String communication2, String communication3,
            String communication4, String communication5, String communication6, int communication1Type, int communication2Type,
            int communication3Type, int communication4Type, int communication5Type, int communication6Type, String kontonummer,
            String bankleitzahl, String bankname, String bankeigentuemer, String kontoiban, String kontobic,
            int werber, String comments, String custom1, String custom2, String custom3,
            String custom4, String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, int status)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO firmenkunden (creator, parentFirma, betreuer, type, kundenNr, firmenName, firmenNameZusatz, firmenNameZusatz2, firmenStrasse, firmenPLZ, firmenStadt, firmenBundesland, firmenLand, firmenTyp, firmenSize, firmenSitz, firmenPostfach, firmenPostfachName, firmenPostfachPlz, firmenPostfachOrt, firmenRechtsform, firmenEinkommen, firmenBranche, firmenGruendungDatum, firmenGeschaeftsfuehrer, firmenProKura, firmenStandorte, communication1, communication2, communication3, communication4, communication5, communication6, communication1Type, communication2Type, communication3Type, communication4Type, communication5Type, communication6Type, kontonummer, bankleitzahl, bankname, bankeigentuemer, kontoiban, kontobic, werber, comments, custom1, custom2, custom3, custom4, custom5, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, creator);
        statement.setInt(2, parentFirma);
        statement.setInt(3, betreuer);
        statement.setInt(4, type);
        statement.setString(5, kundenNr);
        statement.setString(6, firmenName);
        statement.setString(7, firmenNameZusatz);
        statement.setString(8, firmenNameZusatz2);
        statement.setString(9, firmenStrasse);
        statement.setString(10, firmenPLZ);
        statement.setString(11, firmenStadt);
        statement.setString(12, firmenBundesland);
        statement.setString(13, firmenLand);
        statement.setString(14, firmenTyp);
        statement.setString(15, firmenSize);
        statement.setString(16, firmenSitz);
        statement.setBoolean(17, firmenPostfach);
        statement.setString(18, firmenPostfachName);
        statement.setString(19, firmenPostfachPlz);
        statement.setString(20, firmenPostfachOrt);
        statement.setString(21, firmenRechtsform);
        statement.setString(22, firmenEinkommen);
        statement.setString(23, firmenBranche);
        statement.setDate(24, firmenGruendungDatum);
        statement.setString(25, firmenGeschaeftsfuehrer);
        statement.setString(26, firmenProKura);
        statement.setString(27, firmenStandorte);
        statement.setString(28, communication1);
        statement.setString(29, communication2);
        statement.setString(30, communication3);
        statement.setString(31, communication4);
        statement.setString(32, communication5);
        statement.setString(33, communication6);
        statement.setInt(34, communication1Type);
        statement.setInt(35, communication2Type);
        statement.setInt(36, communication3Type);
        statement.setInt(37, communication4Type);
        statement.setInt(38, communication5Type);
        statement.setInt(39, communication6Type);
        statement.setString(40, kontonummer);
        statement.setString(41, bankleitzahl);
        statement.setString(42, bankname);
        statement.setString(43, bankeigentuemer);
        statement.setString(44, kontoiban);
        statement.setString(45, kontobic);
        statement.setInt(46, werber);
        statement.setString(47, comments);
        statement.setString(48, custom1);
        statement.setString(49, custom2);
        statement.setString(50, custom3);
        statement.setString(51, custom4);
        statement.setString(52, custom5);
        statement.setTimestamp(53, created);
        statement.setTimestamp(54, modified);
        statement.setInt(55, status);
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
     * @param creator
     * @param parentFirma
     * @param betreuer
     * @param type
     * @param kundenNr
     * @param firmenName
     * @param firmenNameZusatz
     * @param firmenNameZusatz2
     * @param firmenStrasse
     * @param firmenPLZ
     * @param firmenStadt
     * @param firmenBundesland
     * @param firmenLand
     * @param firmenTyp
     * @param firmenSize
     * @param firmenSitz
     * @param firmenPostfach
     * @param firmenPostfachName
     * @param firmenPostfachPlz
     * @param firmenPostfachOrt
     * @param firmenRechtsform
     * @param firmenEinkommen
     * @param firmenBranche
     * @param firmenGruendungDatum
     * @param firmenGeschaeftsfuehrer
     * @param firmenProKura
     * @param firmenStandorte
     * @param communication1
     * @param communication2
     * @param communication3
     * @param communication4
     * @param communication5
     * @param communication6
     * @param communication1Type
     * @param communication2Type
     * @param communication3Type
     * @param communication4Type
     * @param communication5Type
     * @param communication6Type
     * @param kontonummer
     * @param bankleitzahl
     * @param bankname
     * @param bankeigentuemer
     * @param kontoiban
     * @param kontobic
     * @param werber
     * @param comments
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated 
     */
    public static boolean updatefirmenkunden(Connection con, int keyId, int creator, int parentFirma, int betreuer, int type, String kundenNr,
            String firmenName, String firmenNameZusatz, String firmenNameZusatz2, String firmenStrasse, String firmenPLZ,
            String firmenStadt, String firmenBundesland, String firmenLand, String firmenTyp, String firmenSize,
            String firmenSitz, boolean firmenPostfach, String firmenPostfachName, String firmenPostfachPlz, String firmenPostfachOrt,
            String firmenRechtsform, String firmenEinkommen, String firmenBranche, java.sql.Date firmenGruendungDatum, String firmenGeschaeftsfuehrer,
            String firmenProKura, String firmenStandorte, String communication1, String communication2, String communication3,
            String communication4, String communication5, String communication6, int communication1Type, int communication2Type,
            int communication3Type, int communication4Type, int communication5Type, int communication6Type, String kontonummer,
            String bankleitzahl, String bankname, String bankeigentuemer, String kontoiban, String kontobic,
            int werber, String comments, String custom1, String custom2, String custom3,
            String custom4, String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, int status)
            throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            return false;
        }

        entry.next();

        entry.updateInt("creator", creator);
        entry.updateInt("parentFirma", parentFirma);
        entry.updateInt("betreuer", betreuer);
        entry.updateInt("type", type);
        if (kundenNr != null) {
            entry.updateString("kundenNr", kundenNr);
        }
        if (firmenName != null) {
            entry.updateString("firmenName", firmenName);
        }
        if (firmenNameZusatz != null) {
            entry.updateString("firmenNameZusatz", firmenNameZusatz);
        }
        if (firmenNameZusatz2 != null) {
            entry.updateString("firmenNameZusatz2", firmenNameZusatz2);
        }
        if (firmenStrasse != null) {
            entry.updateString("firmenStrasse", firmenStrasse);
        }
        if (firmenPLZ != null) {
            entry.updateString("firmenPLZ", firmenPLZ);
        }
        if (firmenStadt != null) {
            entry.updateString("firmenStadt", firmenStadt);
        }
        if (firmenBundesland != null) {
            entry.updateString("firmenBundesland", firmenBundesland);
        }
        if (firmenLand != null) {
            entry.updateString("firmenLand", firmenLand);
        }
        if (firmenTyp != null) {
            entry.updateString("firmenTyp", firmenTyp);
        }
        if (firmenSize != null) {
            entry.updateString("firmenSize", firmenSize);
        }
        if (firmenSitz != null) {
            entry.updateString("firmenSitz", firmenSitz);
        }
        entry.updateBoolean("firmenPostfach", firmenPostfach);
        if (firmenPostfachName != null) {
            entry.updateString("firmenPostfachName", firmenPostfachName);
        }
        if (firmenPostfachPlz != null) {
            entry.updateString("firmenPostfachPlz", firmenPostfachPlz);
        }
        if (firmenPostfachOrt != null) {
            entry.updateString("firmenPostfachOrt", firmenPostfachOrt);
        }
        if (firmenRechtsform != null) {
            entry.updateString("firmenRechtsform", firmenRechtsform);
        }
        if (firmenEinkommen != null) {
            entry.updateString("firmenEinkommen", firmenEinkommen);
        }
        if (firmenBranche != null) {
            entry.updateString("firmenBranche", firmenBranche);
        }
        if (firmenGruendungDatum != null) {
            entry.updateDate("firmenGruendungDatum", firmenGruendungDatum);
        }
        if (firmenGeschaeftsfuehrer != null) {
            entry.updateString("firmenGeschaeftsfuehrer", firmenGeschaeftsfuehrer);
        }
        if (firmenProKura != null) {
            entry.updateString("firmenProKura", firmenProKura);
        }
        if (firmenStandorte != null) {
            entry.updateString("firmenStandorte", firmenStandorte);
        }
        if (communication1 != null) {
            entry.updateString("communication1", communication1);
        }
        if (communication2 != null) {
            entry.updateString("communication2", communication2);
        }
        if (communication3 != null) {
            entry.updateString("communication3", communication3);
        }
        if (communication4 != null) {
            entry.updateString("communication4", communication4);
        }
        if (communication5 != null) {
            entry.updateString("communication5", communication5);
        }
        if (communication6 != null) {
            entry.updateString("communication6", communication6);
        }
        entry.updateInt("communication1Type", communication1Type);
        entry.updateInt("communication2Type", communication2Type);
        entry.updateInt("communication3Type", communication3Type);
        entry.updateInt("communication4Type", communication4Type);
        entry.updateInt("communication5Type", communication5Type);
        entry.updateInt("communication6Type", communication6Type);
        if (kontonummer != null) {
            entry.updateString("kontonummer", kontonummer);
        }
        if (bankleitzahl != null) {
            entry.updateString("bankleitzahl", bankleitzahl);
        }
        if (bankname != null) {
            entry.updateString("bankname", bankname);
        }
        if (bankeigentuemer != null) {
            entry.updateString("bankeigentuemer", bankeigentuemer);
        }
        if (kontoiban != null) {
            entry.updateString("kontoiban", kontoiban);
        }
        if (kontobic != null) {
            entry.updateString("kontobic", kontobic);
        }
        entry.updateInt("werber", werber);
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
     * @param keyId
     * @throws SQLException
     */
    public static void archiveFromfirmenkunden(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE id = ?";
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
    public static void deleteFromfirmenkunden(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE id = ?";
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
    public static void deleteEndgueltigFromfirmenkunden(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM firmenkunden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static FirmenObj getFirmenKunde(Connection con, String kdnr) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE kundenNr = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kdnr);

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
        FirmenObj firma = getFirmenEntry(entry);

        statement.close();
        con.close();

        return firma;

    }

    public static FirmenObj getFirmenKunde(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE id = ?";

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
        FirmenObj firma = getFirmenEntry(entry);

        statement.close();
        con.close();

        return firma;

    }

    public static FirmenObj getFirmenEntry(ResultSet entry) throws SQLException {
        FirmenObj firma = new FirmenObj();

        firma.setId(entry.getInt("id"));
        firma.setCreator(entry.getInt("creator"));
        firma.setParentFirma(entry.getInt("parentFirma"));
        firma.setBetreuer(entry.getInt("betreuer"));
        firma.setType(entry.getInt("type"));
        firma.setKundenNr(entry.getString("kundenNr"));
        firma.setFirmenName(entry.getString("firmenName"));
        firma.setFirmenNameZusatz(entry.getString("firmenNameZusatz"));
        firma.setFirmenNameZusatz2(entry.getString("firmenNameZusatz2"));
        firma.setFirmenStrasse(entry.getString("firmenStrasse"));

        firma.setFirmenPLZ(entry.getString("firmenPLZ"));
        firma.setFirmenStadt(entry.getString("firmenStadt"));
        firma.setFirmenBundesland(entry.getString("firmenBundesland"));
        firma.setFirmenLand(entry.getString("firmenLand"));
        firma.setFirmenTyp(entry.getString("firmenTyp"));
        firma.setFirmenSize(entry.getString("firmenSize"));
        firma.setFirmenSitz(entry.getString("firmenSitz"));

        firma.setFirmenPostfach(entry.getBoolean("firmenPostfach"));

        firma.setFirmenPostfachName(entry.getString("firmenPostfachName"));
        firma.setFirmenPostfachPlz(entry.getString("firmenPostfachPlz"));
        firma.setFirmenPostfachOrt(entry.getString("firmenPostfachOrt"));
        firma.setFirmenRechtsform(entry.getString("firmenRechtsform"));
        firma.setFirmenEinkommen(entry.getString("firmenEinkommen"));
        firma.setFirmenBranche(entry.getString("firmenBranche"));
        firma.setFirmenGruendungDatum(entry.getDate("firmenGruendungDatum"));
        firma.setFirmenGeschaeftsfuehrer(entry.getString("firmenGeschaeftsfuehrer"));
        firma.setFirmenProKura(ArrayStringTools.stringToArrayKomma(entry.getString("firmenProKura")));
        firma.setFirmenStandorte(ArrayStringTools.stringToArrayKomma(entry.getString("firmenStandorte")));

        firma.setCommunication1(entry.getString("communication1"));
        firma.setCommunication2(entry.getString("communication2"));
        firma.setCommunication3(entry.getString("communication3"));
        firma.setCommunication4(entry.getString("communication4"));
        firma.setCommunication5(entry.getString("communication5"));
        firma.setCommunication6(entry.getString("communication6"));

        firma.setCommunication1Type(entry.getInt("communication1Type"));
        firma.setCommunication2Type(entry.getInt("communication2Type"));
        firma.setCommunication3Type(entry.getInt("communication3Type"));
        firma.setCommunication4Type(entry.getInt("communication4Type"));
        firma.setCommunication5Type(entry.getInt("communication5Type"));
        firma.setCommunication6Type(entry.getInt("communication6Type"));

        firma.setDefaultKonto(entry.getInt("defaultKonto"));
        firma.setDefaultKonto(entry.getInt("defaultAnsprechpartner"));

        firma.setWerber(entry.getString("werber"));

        firma.setComments(entry.getString("comments"));
        firma.setCustom1(entry.getString("custom1"));
        firma.setCustom2(entry.getString("custom2"));
        firma.setCustom3(entry.getString("custom3"));
        firma.setCustom4(entry.getString("custom4"));
        firma.setCustom5(entry.getString("custom5"));
        firma.setCreated(entry.getTimestamp("created"));
        firma.setModified(entry.getTimestamp("modified"));
        firma.setStatus(entry.getInt("status"));

        return firma;
    }

    /**
     * 
     * @param con
     * @param eigene
     * @return
     * @throws SQLException
     */
    public static FirmenObj[] loadFirmenKunden(Connection con, boolean eigene, int status) throws SQLException {
        String sql = "SELECT * FROM firmenkunden";

        if (eigene && status != -1) {
            sql = "SELECT * FROM firmenkunden WHERE betreuer = ? AND status = ?";
        } else if (status != -1) {
            sql = "SELECT * FROM firmenkunden WHERE status = ?";
        } else if (eigene) {
            sql = "SELECT * FROM firmenkunden WHERE betreuer = ?";
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

        FirmenObj[] firmen = new FirmenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            firmen[i] = getFirmenEntry(entry);

        }

        entry.close();
        statement.close();
        con.close();



        return firmen;
    }

    public static FirmenObj[] loadFirmenKunden(Connection con, int betreuerId, int status) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE betreuer = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM firmenkunden WHERE betreuer = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, betreuerId);

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

        FirmenObj[] firmen = new FirmenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            firmen[i] = getFirmenEntry(entry);

        }

        entry.close();
        statement.close();
        con.close();



        return firmen;
    }

    public static FirmenObj[] loadgeworbenenFirmenKunden(Connection con, String werberKennung) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE werber = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, werberKennung);

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

        FirmenObj[] firmen = new FirmenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            firmen[i] = getFirmenEntry(entry);

        }

        entry.close();
        statement.close();
        con.close();

        return firmen;
    }

    /**
     * 
     * @param con
     * @param firma
     * @return
     * @throws SQLException
     */
    public static int insertIntofirmenkunden(Connection con, FirmenObj firma)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO firmenkunden (creator, parentFirma, betreuer, type, kundenNr, "
                + "firmenName, firmenNameZusatz, firmenNameZusatz2, firmenStrasse, firmenPLZ, "
                + "firmenStadt, firmenBundesland, firmenLand, firmenTyp, firmenSize, firmenSitz, "
                + "firmenPostfach, firmenPostfachName, firmenPostfachPlz, firmenPostfachOrt, "
                + "firmenRechtsform, firmenEinkommen, firmenBranche, firmenGruendungDatum, "
                + "firmenGeschaeftsfuehrer, firmenProKura, firmenStandorte, communication1, "
                + "communication2, communication3, communication4, communication5, communication6, "
                + "communication1Type, communication2Type, communication3Type, communication4Type, "
                + "communication5Type, communication6Type, defaultKonto, defaultAnsprechpartner, "
                + "werber, comments, custom1, custom2, "
                + "custom3, custom4, custom5, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, firma.getCreator());
        statement.setInt(2, firma.getParentFirma());
        statement.setInt(3, firma.getBetreuer());
        statement.setInt(4, firma.getType());
        statement.setString(5, firma.getKundenNr());
        statement.setString(6, firma.getFirmenName());
        statement.setString(7, firma.getFirmenNameZusatz());
        statement.setString(8, firma.getFirmenNameZusatz2());
        statement.setString(9, firma.getFirmenStrasse());
        statement.setString(10, firma.getFirmenPLZ());
        statement.setString(11, firma.getFirmenStadt());
        statement.setString(12, firma.getFirmenBundesland());
        statement.setString(13, firma.getFirmenLand());
        statement.setString(14, firma.getFirmenTyp());
        statement.setString(15, firma.getFirmenSize());
        statement.setString(16, firma.getFirmenSitz());
        statement.setBoolean(17, firma.getFirmenPostfach());
        statement.setString(18, firma.getFirmenPostfachName());
        statement.setString(19, firma.getFirmenPostfachPlz());
        statement.setString(20, firma.getFirmenPostfachOrt());
        statement.setString(21, firma.getFirmenRechtsform());
        statement.setString(22, firma.getFirmenEinkommen());
        statement.setString(23, firma.getFirmenBranche());
        statement.setDate(24, firma.getFirmenGruendungDatum());
        statement.setString(25, firma.getFirmenGeschaeftsfuehrer());
        statement.setString(26, ArrayStringTools.arrayToString(firma.getFirmenProKura(), ","));
        statement.setString(27, ArrayStringTools.arrayToString(firma.getFirmenStandorte(), ","));
        statement.setString(28, firma.getCommunication1());
        statement.setString(29, firma.getCommunication2());
        statement.setString(30, firma.getCommunication3());
        statement.setString(31, firma.getCommunication4());
        statement.setString(32, firma.getCommunication5());
        statement.setString(33, firma.getCommunication6());
        statement.setInt(34, firma.getCommunication1Type());
        statement.setInt(35, firma.getCommunication2Type());
        statement.setInt(36, firma.getCommunication3Type());
        statement.setInt(37, firma.getCommunication4Type());
        statement.setInt(38, firma.getCommunication5Type());
        statement.setInt(39, firma.getCommunication6Type());

        statement.setInt(40, firma.getDefaultKonto());
        statement.setInt(41, firma.getDefaultAnsprechpartner());

        statement.setString(42, firma.getWerber());
        statement.setString(43, firma.getComments());
        statement.setString(44, firma.getCustom1());
        statement.setString(45, firma.getCustom2());
        statement.setString(46, firma.getCustom3());
        statement.setString(47, firma.getCustom4());
        statement.setString(48, firma.getCustom5());
        statement.setTimestamp(49, firma.getCreated());
        statement.setTimestamp(50, firma.getModified());
        statement.setInt(51, firma.getStatus());
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

    public static boolean updatefirmenkunden(Connection con, FirmenObj firma) throws SQLException {
        String sql = "SELECT * FROM firmenkunden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, firma.getId());
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

        entry.updateInt("creator", firma.getCreator());
        entry.updateInt("parentFirma", firma.getParentFirma());
        entry.updateInt("betreuer", firma.getBetreuer());
        entry.updateInt("type", firma.getType());

        entry.updateString("kundenNr", firma.getKundenNr());

        entry.updateString("firmenName", firma.getFirmenName());

        entry.updateString("firmenNameZusatz", firma.getFirmenNameZusatz());

        entry.updateString("firmenNameZusatz2", firma.getFirmenNameZusatz2());

        entry.updateString("firmenStrasse", firma.getFirmenStrasse());

        entry.updateString("firmenPLZ", firma.getFirmenPLZ());

        entry.updateString("firmenStadt", firma.getFirmenStadt());

        entry.updateString("firmenBundesland", firma.getFirmenBundesland());

        entry.updateString("firmenLand", firma.getFirmenLand());

        entry.updateString("firmenTyp", firma.getFirmenTyp());

        entry.updateString("firmenSize", firma.getFirmenSize());

        entry.updateString("firmenSitz", firma.getFirmenSitz());
        entry.updateBoolean("firmenPostfach", firma.getFirmenPostfach());

        entry.updateString("firmenPostfachName", firma.getFirmenPostfachName());

        entry.updateString("firmenPostfachPlz", firma.getFirmenPostfachPlz());
        entry.updateString("firmenPostfachOrt", firma.getFirmenPostfachOrt());

        entry.updateString("firmenRechtsform", firma.getFirmenRechtsform());

        entry.updateString("firmenEinkommen", firma.getFirmenEinkommen());

        entry.updateString("firmenBranche", firma.getFirmenBranche());

        entry.updateDate("firmenGruendungDatum", firma.getFirmenGruendungDatum());

        entry.updateString("firmenGeschaeftsfuehrer", firma.getFirmenGeschaeftsfuehrer());

        entry.updateString("firmenProKura", ArrayStringTools.arrayToString(firma.getFirmenProKura(), ","));

        entry.updateString("firmenStandorte", ArrayStringTools.arrayToString(firma.getFirmenStandorte(), ","));

        entry.updateString("communication1", firma.getCommunication1());

        entry.updateString("communication2", firma.getCommunication2());

        entry.updateString("communication3", firma.getCommunication3());

        entry.updateString("communication4", firma.getCommunication4());

        entry.updateString("communication5", firma.getCommunication5());

        entry.updateString("communication6", firma.getCommunication6());
        entry.updateInt("communication1Type", firma.getCommunication1Type());
        entry.updateInt("communication2Type", firma.getCommunication2Type());
        entry.updateInt("communication3Type", firma.getCommunication3Type());
        entry.updateInt("communication4Type", firma.getCommunication4Type());
        entry.updateInt("communication5Type", firma.getCommunication5Type());
        entry.updateInt("communication6Type", firma.getCommunication6Type());

        entry.updateInt("defaultKonto", firma.getDefaultKonto());
        entry.updateInt("defaultAnsprechpartner", firma.getDefaultAnsprechpartner());

        entry.updateString("werber", firma.getWerber());

        entry.updateString("comments", firma.getComments());

        entry.updateString("custom1", firma.getCustom1());

        entry.updateString("custom2", firma.getCustom2());

        entry.updateString("custom3", firma.getCustom3());

        entry.updateString("custom4", firma.getCustom4());

        entry.updateString("custom5", firma.getCustom5());

        entry.updateTimestamp("created", firma.getCreated());

        entry.updateTimestamp("modified", firma.getModified());
        entry.updateInt("status", firma.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }
}
