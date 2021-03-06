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

package de.maklerpoint.office.Mandanten.Tools;

import de.maklerpoint.office.Mandanten.MandantenObj;
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
public class MandantenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param parentId
     * @param creatorId
     * @param firmenName
     * @param firmenZusatz
     * @param firmenZusatz2
     * @param vermittlungNamen
     * @param vertretungsBerechtigtePosition
     * @param vertretungsBerechtigteVorname
     * @param vertretungsBerechtigteNachname
     * @param vertretungsBerechtigteIHKErlaubnis
     * @param firmenTyp
     * @param firmenRechtsform
     * @param postfach
     * @param postfachPlz
     * @param postfachOrt
     * @param filialTyp
     * @param filialMitarbeiterZahl
     * @param geschaeftsleiter
     * @param gesellschafter
     * @param steuerNummer
     * @param ustNummer
     * @param vermoegensHaftpflicht
     * @param beteiligungenVU
     * @param beteiligungenMAK
     * @param verbandsMitgliedschaften
     * @param beraterTyp
     * @param ihkName
     * @param ihkRegistriernummer
     * @param ihkStatus
     * @param ihkAbweichungen
     * @param versicherListe
     * @param 34c
     * @param 34d
     * @param gewerbeamtShow
     * @param gewerbeamtName
     * @param gewerbeamtPLZ
     * @param gewerbeamtOrt
     * @param gewerbeamtStrasse
     * @param handelsregisterShow
     * @param handelsregisterName
     * @param handelsregisterStrasse
     * @param handelsregisterPLZ
     * @param handelsregisterOrt
     * @param handelsregisterRegistrierNummer
     * @param logo
     * @param beschwerdeStellen
     * @param adressZusatz
     * @param adressZusatz2
     * @param strasse
     * @param plz
     * @param ort
     * @param bundesland
     * @param land
     * @param bankName
     * @param bankKonto
     * @param bankEigentuemer
     * @param bankLeitzahl
     * @param bankIBAN
     * @param bankBIC
     * @param telefon
     * @param telefon2
     * @param telefon3
     * @param mobil
     * @param mobil2
     * @param fax
     * @param fax2
     * @param email
     * @param email2
     * @param secureMail
     * @param emailSignatur
     * @param homepage
     * @param homepage2
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param custom6
     * @param custom7
     * @param custom8
     * @param custom9
     * @param custom10
     * @param comments
     * @param created
     * @param modified
     * @param lastUsed
     * @return id (database row id [id])
     * @throws SQLException
     */

    public static int insertIntomandanten(Connection con, int parentId, int creatorId, String firmenName, String firmenZusatz, String firmenZusatz2,
            String vermittlungNamen, String[] vertretungsBerechtigtePosition, String[] vertretungsBerechtigteVorname, String[] vertretungsBerechtigteNachname, String[] vertretungsBerechtigteIHKErlaubnis,
            String firmenTyp, String firmenRechtsform, String postfach, String postfachPlz, String postfachOrt,
            String filialTyp, String filialMitarbeiterZahl, String geschaeftsleiter, String[] gesellschafter, String steuerNummer,
            String ustNummer, String vermoegensHaftpflicht, boolean beteiligungenVU, boolean beteiligungenMAK, String[] verbandsMitgliedschaften,
            String beraterTyp, String ihkName, String ihkRegistriernummer, String ihkStatus, String ihkAbweichungen,
            String[] versicherListe, boolean _34c, boolean _34d, boolean gewerbeamtShow, String gewerbeamtName,
            String gewerbeamtPLZ, String gewerbeamtOrt, String gewerbeamtStrasse, boolean handelsregisterShow, String handelsregisterName,
            String handelsregisterStrasse, String handelsregisterPLZ, String handelsregisterOrt, String handelsregisterRegistrierNummer, String logo,
            String[] beschwerdeStellen, String adressZusatz, String adressZusatz2, String strasse, String plz,
            String ort, String bundesland, String land, String bankName, String bankKonto,
            String bankEigentuemer, String bankLeitzahl, String bankIBAN, String bankBIC, String telefon,
            String telefon2, String telefon3, String mobil, String mobil2, String fax,
            String fax2, String email, String email2, String secureMail, String emailSignatur,
            String homepage, String homepage2, String custom1, String custom2, String custom3,
            String custom4, String custom5, String custom6, String custom7, String custom8,
            String custom9, String custom10, String comments, java.sql.Timestamp created, java.sql.Timestamp modified,
            java.sql.Timestamp lastUsed, int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO mandanten (parentId, creatorId, firmenName, firmenZusatz, "
                + "firmenZusatz2, vermittlungNamen, vertretungsBerechtigtePosition, "
                + "vertretungsBerechtigteVorname, vertretungsBerechtigteNachname, "
                + "vertretungsBerechtigteIHKErlaubnis, firmenTyp, firmenRechtsform, "
                + "postfach, postfachPlz, postfachOrt, filialTyp, filialMitarbeiterZahl, "
                + "geschaeftsleiter, gesellschafter, steuerNummer, ustNummer, "
                + "vermoegensHaftpflicht, beteiligungenVU, beteiligungenMAK, "
                + "verbandsMitgliedschaften, beraterTyp, ihkName, ihkRegistriernummer, "
                + "ihkStatus, ihkAbweichungen, versicherListe, is34c, is34d, gewerbeamtShow, "
                + "gewerbeamtName, gewerbeamtPLZ, gewerbeamtOrt, gewerbeamtStrasse, "
                + "handelsregisterShow, handelsregisterName, handelsregisterStrasse, "
                + "handelsregisterPLZ, handelsregisterOrt, handelsregisterRegistrierNummer, "
                + "logo, beschwerdeStellen, adressZusatz, adressZusatz2, strasse, plz, ort, "
                + "bundesland, land, bankName, bankKonto, bankEigentuemer, bankLeitzahl,"
                + " bankIBAN, bankBIC, telefon, telefon2, telefon3, mobil, mobil2, fax, "
                + "fax2, email, email2, secureMail, emailSignatur, homepage, homepage2, "
                + "custom1, custom2, custom3, custom4, custom5, custom6, custom7, custom8, "
                + "custom9, custom10, comments, created, modified, lastUsed, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, parentId);
        statement.setInt(2, creatorId);
        statement.setString(3, firmenName);
        statement.setString(4, firmenZusatz);
        statement.setString(5, firmenZusatz2);
        statement.setString(6, vermittlungNamen);
        statement.setString(7, ArrayStringTools.arrayToString(vertretungsBerechtigtePosition, ","));
        statement.setString(8, ArrayStringTools.arrayToString(vertretungsBerechtigteVorname, ","));
        statement.setString(9, ArrayStringTools.arrayToString(vertretungsBerechtigteNachname, ","));
        statement.setString(10, ArrayStringTools.arrayToString(vertretungsBerechtigteIHKErlaubnis, ","));
        statement.setString(11, firmenTyp);
        statement.setString(12, firmenRechtsform);
        statement.setString(13, postfach);
        statement.setString(14, postfachPlz);
        statement.setString(15, postfachOrt);
        statement.setString(16, filialTyp);
        statement.setString(17, filialMitarbeiterZahl);
        statement.setString(18, geschaeftsleiter);
        statement.setString(19, ArrayStringTools.arrayToString(gesellschafter, ","));
        statement.setString(20, steuerNummer);
        statement.setString(21, ustNummer);
        statement.setString(22, vermoegensHaftpflicht);
        statement.setBoolean(23, beteiligungenVU);
        statement.setBoolean(24, beteiligungenMAK);
        statement.setString(25, ArrayStringTools.arrayToString(verbandsMitgliedschaften, ","));
        statement.setString(26, beraterTyp);
        statement.setString(27, ihkName);
        statement.setString(28, ihkRegistriernummer);
        statement.setString(29, ihkStatus);
        statement.setString(30, ihkAbweichungen);
        statement.setString(31, ArrayStringTools.arrayToString(versicherListe, ","));
        statement.setBoolean(32, _34c);
        statement.setBoolean(33, _34d);
        statement.setBoolean(34, gewerbeamtShow);
        statement.setString(35, gewerbeamtName);
        statement.setString(36, gewerbeamtPLZ);
        statement.setString(37, gewerbeamtOrt);
        statement.setString(38, gewerbeamtStrasse);
        statement.setBoolean(39, handelsregisterShow);
        statement.setString(40, handelsregisterName);
        statement.setString(41, handelsregisterStrasse);
        statement.setString(42, handelsregisterPLZ);
        statement.setString(43, handelsregisterOrt);
        statement.setString(44, handelsregisterRegistrierNummer);
        statement.setString(45, logo);
        statement.setString(46, ArrayStringTools.arrayToString(beschwerdeStellen, ","));
        statement.setString(47, adressZusatz);
        statement.setString(48, adressZusatz2);
        statement.setString(49, strasse);
        statement.setString(50, plz);
        statement.setString(51, ort);
        statement.setString(52, bundesland);
        statement.setString(53, land);
        statement.setString(54, bankName);
        statement.setString(55, bankKonto);
        statement.setString(56, bankEigentuemer);
        statement.setString(57, bankLeitzahl);
        statement.setString(58, bankIBAN);
        statement.setString(59, bankBIC);
        statement.setString(60, telefon);
        statement.setString(61, telefon2);
        statement.setString(62, telefon3);
        statement.setString(63, mobil);
        statement.setString(64, mobil2);
        statement.setString(65, fax);
        statement.setString(66, fax2);
        statement.setString(67, email);
        statement.setString(68, email2);
        statement.setString(69, secureMail);
        statement.setString(70, emailSignatur);
        statement.setString(71, homepage);
        statement.setString(72, homepage2);
        statement.setString(73, custom1);
        statement.setString(74, custom2);
        statement.setString(75, custom3);
        statement.setString(76, custom4);
        statement.setString(77, custom5);
        statement.setString(78, custom6);
        statement.setString(79, custom7);
        statement.setString(80, custom8);
        statement.setString(81, custom9);
        statement.setString(82, custom10);
        statement.setString(83, comments);
        statement.setTimestamp(84, created);
        statement.setTimestamp(85, modified);
        statement.setTimestamp(86, lastUsed);
        statement.setInt(87, status);
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


    public static int insertIntomandanten(Connection con, MandantenObj md) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO mandanten (parentId, creatorId, firmenName, firmenZusatz, "
                + "firmenZusatz2, vermittlungNamen, vertretungsBerechtigtePosition, "
                + "vertretungsBerechtigteVorname, vertretungsBerechtigteNachname, "
                + "vertretungsBerechtigteIHKErlaubnis, firmenTyp, firmenRechtsform, "
                + "postfach, postfachPlz, postfachOrt, filialTyp, filialMitarbeiterZahl, "
                + "geschaeftsleiter, gesellschafter, steuerNummer, ustNummer, "
                + "vermoegensHaftpflicht, beteiligungenVU, beteiligungenMAK, "
                + "verbandsMitgliedschaften, beraterTyp, ihkName, ihkRegistriernummer, "
                + "ihkStatus, ihkAbweichungen, versicherListe, is34c, is34d, gewerbeamtShow, "
                + "gewerbeamtName, gewerbeamtPLZ, gewerbeamtOrt, gewerbeamtStrasse, "
                + "handelsregisterShow, handelsregisterName, handelsregisterStrasse, "
                + "handelsregisterPLZ, handelsregisterOrt, handelsregisterRegistrierNummer, "
                + "logo, beschwerdeStellen, adressZusatz, adressZusatz2, strasse, plz, ort, "
                + "bundesland, land, bankName, bankKonto, bankEigentuemer, bankLeitzahl,"
                + " bankIBAN, bankBIC, telefon, telefon2, telefon3, mobil, mobil2, fax, "
                + "fax2, email, email2, secureMail, emailSignatur, homepage, homepage2, "
                + "custom1, custom2, custom3, custom4, custom5, custom6, custom7, custom8, "
                + "custom9, custom10, comments, created, modified, lastUsed, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, md.getParentId());
        statement.setInt(2, md.getCreatorId());
        statement.setString(3, md.getFirmenName());
        statement.setString(4, md.getFirmenZusatz());
        statement.setString(5, md.getFirmenZusatz2());
        statement.setString(6, md.getVermittlungNamen());
        statement.setString(7, ArrayStringTools.arrayToString(md.getVertretungsBerechtigtePosition(), ","));
        statement.setString(8, ArrayStringTools.arrayToString(md.getVertretungsBerechtigteVorname(), ","));
        statement.setString(9, ArrayStringTools.arrayToString(md.getVertretungsBerechtigteNachname(), ","));
        statement.setString(10, ArrayStringTools.arrayToString(md.getVertretungsBerechtigteIHKErlaubnis(), ","));
        statement.setString(11, md.getFirmenTyp());
        statement.setString(12, md.getFirmenRechtsform());
        statement.setString(13, md.getPostfach());
        statement.setString(14, md.getPostfachPlz());
        statement.setString(15, md.getPostfachOrt());
        statement.setString(16, md.getFilialTyp());
        statement.setString(17, md.getFilialMitarbeiterZahl());
        statement.setString(18, md.getGeschaeftsleiter());
        statement.setString(19,ArrayStringTools.arrayToString(md.getGesellschafter(), ","));
        statement.setString(20, md.getSteuerNummer());
        statement.setString(21, md.getUstNummer());
        statement.setString(22, md.getVermoegensHaftpflicht());
        statement.setBoolean(23, md.isBeteiligungenVU());
        statement.setBoolean(24, md.isBeteiligungenMAK());
        statement.setString(25, ArrayStringTools.arrayToString(md.getVerbandsMitgliedschaften(), ","));
        statement.setString(26, md.getBeraterTyp());
        statement.setString(27, md.getIhkName());
        statement.setString(28, md.getIhkRegistriernummer());
        statement.setString(29, md.getIhkStatus());
        statement.setString(30, md.getIhkAbweichungen());
        statement.setString(31, ArrayStringTools.arrayToString(md.getVersicherListe(), ","));
        statement.setBoolean(32, md.is34c());
        statement.setBoolean(33, md.is34d());
        statement.setBoolean(34, md.isGewerbeamtShow());
        statement.setString(35, md.getGewerbeamtName());
        statement.setString(36, md.getGewerbeamtPLZ());
        statement.setString(37, md.getGewerbeamtOrt());
        statement.setString(38, md.getGewerbeamtStrasse());
        statement.setBoolean(39, md.isHandelsregisterShow());
        statement.setString(40, md.getHandelsregisterName());
        statement.setString(41, md.getHandelsregisterStrasse());
        statement.setString(42, md.getHandelsregisterPLZ());
        statement.setString(43, md.getHandelsregisterOrt());
        statement.setString(44, md.getHandelsregisterRegistrierNummer());
        statement.setString(45, md.getLogo());
        statement.setString(46, ArrayStringTools.arrayToString(md.getBeschwerdeStellen(), ","));
        statement.setString(47, md.getAdressZusatz());
        statement.setString(48, md.getAdressZusatz2());
        statement.setString(49, md.getStrasse());
        statement.setString(50, md.getPlz());
        statement.setString(51, md.getOrt());
        statement.setString(52, md.getBundesland());
        statement.setString(53, md.getLand());
        statement.setString(54, md.getBankName());
        statement.setString(55, md.getBankKonto());
        statement.setString(56, md.getBankEigentuemer());
        statement.setString(57, md.getBankLeitzahl());
        statement.setString(58, md.getBankIBAN());
        statement.setString(59, md.getBankBIC());
        statement.setString(60, md.getTelefon());
        statement.setString(61, md.getTelefon2());
        statement.setString(62, md.getTelefon3());
        statement.setString(63, md.getMobil());
        statement.setString(64, md.getMobil2());
        statement.setString(65, md.getFax());
        statement.setString(66, md.getFax2());
        statement.setString(67, md.getEmail());
        statement.setString(68, md.getEmail2());
        statement.setString(69, md.getSecureMail());
        statement.setString(70, md.getEmailSignatur());
        statement.setString(71, md.getHomepage());
        statement.setString(72, md.getHomepage2());
        statement.setString(73, md.getCustom1());
        statement.setString(74, md.getCustom2());
        statement.setString(75, md.getCustom3());
        statement.setString(76, md.getCustom4());
        statement.setString(77, md.getCustom5());
        statement.setString(78, md.getCustom6());
        statement.setString(79, md.getCustom7());
        statement.setString(80, md.getCustom8());
        statement.setString(81, md.getCustom9());
        statement.setString(82, md.getCustom10());
        statement.setString(83, md.getComments());
        statement.setTimestamp(84, md.getCreated());
        statement.setTimestamp(85, md.getModified());
        statement.setTimestamp(86, md.getLastUsed());
        statement.setInt(87, md.getStatus());
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
     * @param parentId
     * @param creatorId
     * @param firmenName
     * @param firmenZusatz
     * @param firmenZusatz2
     * @param vermittlungNamen
     * @param vertretungsBerechtigtePosition
     * @param vertretungsBerechtigteVorname
     * @param vertretungsBerechtigteNachname
     * @param vertretungsBerechtigteIHKErlaubnis
     * @param firmenTyp
     * @param firmenRechtsform
     * @param postfach
     * @param postfachPlz
     * @param postfachOrt
     * @param filialTyp
     * @param filialMitarbeiterZahl
     * @param geschaeftsleiter
     * @param gesellschafter
     * @param steuerNummer
     * @param ustNummer
     * @param vermoegensHaftpflicht
     * @param beteiligungenVU
     * @param beteiligungenMAK
     * @param verbandsMitgliedschaften
     * @param beraterTyp
     * @param ihkName
     * @param ihkRegistriernummer
     * @param ihkStatus
     * @param ihkAbweichungen
     * @param versicherListe
     * @param 34c
     * @param 34d
     * @param gewerbeamtShow
     * @param gewerbeamtName
     * @param gewerbeamtPLZ
     * @param gewerbeamtOrt
     * @param gewerbeamtStrasse
     * @param handelsregisterShow
     * @param handelsregisterName
     * @param handelsregisterStrasse
     * @param handelsregisterPLZ
     * @param handelsregisterOrt
     * @param handelsregisterRegistrierNummer
     * @param logo
     * @param beschwerdeStellen
     * @param adressZusatz
     * @param adressZusatz2
     * @param strasse
     * @param plz
     * @param ort
     * @param bundesland
     * @param land
     * @param bankName
     * @param bankKonto
     * @param bankEigentuemer
     * @param bankLeitzahl
     * @param bankIBAN
     * @param bankBIC
     * @param telefon
     * @param telefon2
     * @param telefon3
     * @param mobil
     * @param mobil2
     * @param fax
     * @param fax2
     * @param email
     * @param email2
     * @param secureMail
     * @param emailSignatur
     * @param homepage
     * @param homepage2
     * @param custom1
     * @param custom2
     * @param custom3
     * @param custom4
     * @param custom5
     * @param custom6
     * @param custom7
     * @param custom8
     * @param custom9
     * @param custom10
     * @param comments
     * @param created
     * @param modified
     * @param lastUsed
     * @return boolean (true on success)
     * @throws SQLException
     */

    public static boolean updatemandanten(Connection con, int keyId, int parentId, int creatorId, String firmenName, String firmenZusatz, String firmenZusatz2,
            String vermittlungNamen, String[] vertretungsBerechtigtePosition, String[] vertretungsBerechtigteVorname, String[] vertretungsBerechtigteNachname, String[] vertretungsBerechtigteIHKErlaubnis,
            String firmenTyp, String firmenRechtsform, String postfach, String postfachPlz, String postfachOrt,
            String filialTyp, String filialMitarbeiterZahl, String geschaeftsleiter, String[] gesellschafter, String steuerNummer,
            String ustNummer, String vermoegensHaftpflicht, boolean beteiligungenVU, boolean beteiligungenMAK, String[] verbandsMitgliedschaften,
            String beraterTyp, String ihkName, String ihkRegistriernummer, String ihkStatus, String ihkAbweichungen,
            String[] versicherListe, boolean _34c, boolean _34d, boolean gewerbeamtShow, String gewerbeamtName,
            String gewerbeamtPLZ, String gewerbeamtOrt, String gewerbeamtStrasse, boolean handelsregisterShow, String handelsregisterName,
            String handelsregisterStrasse, String handelsregisterPLZ, String handelsregisterOrt, String handelsregisterRegistrierNummer, String logo,
            String[] beschwerdeStellen, String adressZusatz, String adressZusatz2, String strasse, String plz,
            String ort, String bundesland, String land, String bankName, String bankKonto,
            String bankEigentuemer, String bankLeitzahl, String bankIBAN, String bankBIC, String telefon,
            String telefon2, String telefon3, String mobil, String mobil2, String fax,
            String fax2, String email, String email2, String secureMail, String emailSignatur,
            String homepage, String homepage2, String custom1, String custom2, String custom3,
            String custom4, String custom5, String custom6, String custom7, String custom8,
            String custom9, String custom10, String comments, java.sql.Timestamp created, java.sql.Timestamp modified,
            java.sql.Timestamp lastUsed) throws SQLException {
    String sql = "SELECT * FROM mandanten WHERE id = ?";
    PreparedStatement statement = con.prepareStatement(sql,
    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    statement.setInt(1, keyId);
    ResultSet entry = statement.executeQuery();

    entry.last();
    int rows = entry.getRow();
    entry.beforeFirst();
    if(rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return false;
    }

    entry.next();

    entry.updateInt("parentId", parentId);
    entry.updateInt("creatorId", creatorId);
    if(firmenName != null)
        entry.updateString("firmenName", firmenName);
    if(firmenZusatz != null)
        entry.updateString("firmenZusatz", firmenZusatz);
    if(firmenZusatz2 != null)
        entry.updateString("firmenZusatz2", firmenZusatz2);
    if(vermittlungNamen != null)
        entry.updateString("vermittlungNamen", vermittlungNamen);
    if(vertretungsBerechtigtePosition != null)
        entry.updateString("vertretungsBerechtigtePosition", ArrayStringTools.arrayToString(vertretungsBerechtigtePosition,","));
    if(vertretungsBerechtigteVorname != null)
        entry.updateString("vertretungsBerechtigteVorname", ArrayStringTools.arrayToString(vertretungsBerechtigteVorname, ","));
    if(vertretungsBerechtigteNachname != null)
        entry.updateString("vertretungsBerechtigteNachname", ArrayStringTools.arrayToString(vertretungsBerechtigteNachname, ","));
    if(vertretungsBerechtigteIHKErlaubnis != null)
        entry.updateString("vertretungsBerechtigteIHKErlaubnis", ArrayStringTools.arrayToString(vertretungsBerechtigteIHKErlaubnis, ","));
    if(firmenTyp != null)
        entry.updateString("firmenTyp", firmenTyp);
    if(firmenRechtsform != null)
        entry.updateString("firmenRechtsform", firmenRechtsform);
    if(postfach != null)
        entry.updateString("postfach", postfach);
    if(postfachPlz != null)
        entry.updateString("postfachPlz", postfachPlz);
    if(postfachOrt != null)
        entry.updateString("postfachOrt", postfachOrt);
    if(filialTyp != null)
        entry.updateString("filialTyp", filialTyp);
    if(filialMitarbeiterZahl != null)
        entry.updateString("filialMitarbeiterZahl", filialMitarbeiterZahl);
    if(geschaeftsleiter != null)
        entry.updateString("geschaeftsleiter", geschaeftsleiter);
    if(gesellschafter != null)
        entry.updateString("gesellschafter", ArrayStringTools.arrayToString(gesellschafter, ","));
    if(steuerNummer != null)
        entry.updateString("steuerNummer", steuerNummer);
    if(ustNummer != null)
        entry.updateString("ustNummer", ustNummer);
    if(vermoegensHaftpflicht != null)
        entry.updateString("vermoegensHaftpflicht", vermoegensHaftpflicht);
    entry.updateBoolean("beteiligungenVU", beteiligungenVU);
    entry.updateBoolean("beteiligungenMAK", beteiligungenMAK);
    if(verbandsMitgliedschaften != null)
        entry.updateString("verbandsMitgliedschaften", ArrayStringTools.arrayToString(verbandsMitgliedschaften, ","));
    if(beraterTyp != null)
        entry.updateString("beraterTyp", beraterTyp);
    if(ihkName != null)
        entry.updateString("ihkName", ihkName);
    if(ihkRegistriernummer != null)
        entry.updateString("ihkRegistriernummer", ihkRegistriernummer);
    if(ihkStatus != null)
        entry.updateString("ihkStatus", ihkStatus);
    if(ihkAbweichungen != null)
        entry.updateString("ihkAbweichungen", ihkAbweichungen);
    if(versicherListe != null)
        entry.updateString("versicherListe", ArrayStringTools.arrayToString(versicherListe, ","));
    entry.updateBoolean("is34c", _34c);
    entry.updateBoolean("is34d", _34d);
    entry.updateBoolean("gewerbeamtShow", gewerbeamtShow);
    if(gewerbeamtName != null)
        entry.updateString("gewerbeamtName", gewerbeamtName);
    if(gewerbeamtPLZ != null)
        entry.updateString("gewerbeamtPLZ", gewerbeamtPLZ);
    if(gewerbeamtOrt != null)
        entry.updateString("gewerbeamtOrt", gewerbeamtOrt);
    if(gewerbeamtStrasse != null)
        entry.updateString("gewerbeamtStrasse", gewerbeamtStrasse);
    entry.updateBoolean("handelsregisterShow", handelsregisterShow);
    if(handelsregisterName != null)
        entry.updateString("handelsregisterName", handelsregisterName);
    if(handelsregisterStrasse != null)
        entry.updateString("handelsregisterStrasse", handelsregisterStrasse);
    if(handelsregisterPLZ != null)
        entry.updateString("handelsregisterPLZ", handelsregisterPLZ);
    if(handelsregisterOrt != null)
        entry.updateString("handelsregisterOrt", handelsregisterOrt);
    if(handelsregisterRegistrierNummer != null)
        entry.updateString("handelsregisterRegistrierNummer", handelsregisterRegistrierNummer);
    if(logo != null)
        entry.updateString("logo", logo);
    if(beschwerdeStellen != null)
        entry.updateString("beschwerdeStellen", ArrayStringTools.arrayToString(beschwerdeStellen, ","));
    if(adressZusatz != null)
        entry.updateString("adressZusatz", adressZusatz);
    if(adressZusatz2 != null)
        entry.updateString("adressZusatz2", adressZusatz2);
    if(strasse != null)
        entry.updateString("strasse", strasse);
    if(plz != null)
        entry.updateString("plz", plz);
    if(ort != null)
        entry.updateString("ort", ort);
    if(bundesland != null)
        entry.updateString("bundesland", bundesland);
    if(land != null)
        entry.updateString("land", land);
    if(bankName != null)
        entry.updateString("bankName", bankName);
    if(bankKonto != null)
        entry.updateString("bankKonto", bankKonto);
    if(bankEigentuemer != null)
        entry.updateString("bankEigentuemer", bankEigentuemer);
    if(bankLeitzahl != null)
        entry.updateString("bankLeitzahl", bankLeitzahl);
    if(bankIBAN != null)
        entry.updateString("bankIBAN", bankIBAN);
    if(bankBIC != null)
        entry.updateString("bankBIC", bankBIC);
    if(telefon != null)
        entry.updateString("telefon", telefon);
    if(telefon2 != null)
        entry.updateString("telefon2", telefon2);
    if(telefon3 != null)
        entry.updateString("telefon3", telefon3);
    if(mobil != null)
        entry.updateString("mobil", mobil);
    if(mobil2 != null)
        entry.updateString("mobil2", mobil2);
    if(fax != null)
        entry.updateString("fax", fax);
    if(fax2 != null)
        entry.updateString("fax2", fax2);
    if(email != null)
        entry.updateString("email", email);
    if(email2 != null)
        entry.updateString("email2", email2);
    if(secureMail != null)
        entry.updateString("secureMail", secureMail);
    if(emailSignatur != null)
        entry.updateString("emailSignatur", emailSignatur);
    if(homepage != null)
        entry.updateString("homepage", homepage);
    if(homepage2 != null)
        entry.updateString("homepage2", homepage2);
    if(custom1 != null)
        entry.updateString("custom1", custom1);
    if(custom2 != null)
        entry.updateString("custom2", custom2);
    if(custom3 != null)
        entry.updateString("custom3", custom3);
    if(custom4 != null)
        entry.updateString("custom4", custom4);
    if(custom5 != null)
        entry.updateString("custom5", custom5);
    if(custom6 != null)
        entry.updateString("custom6", custom6);
    if(custom7 != null)
        entry.updateString("custom7", custom7);
    if(custom8 != null)
        entry.updateString("custom8", custom8);
    if(custom9 != null)
        entry.updateString("custom9", custom9);
    if(custom10 != null)
        entry.updateString("custom10", custom10);
    if(comments != null)
        entry.updateString("comments", comments);
    if(created != null)
        entry.updateTimestamp("created", created);
    if(modified != null)
        entry.updateTimestamp("modified", modified);
    if(lastUsed != null)
        entry.updateTimestamp("lastUsed", lastUsed);

    entry.updateRow();
    entry.close();
    statement.close();
    con.close();
    return true;
    }

    public static boolean updatemandanten(Connection con, MandantenObj mandant) throws SQLException {
        String sql = "SELECT * FROM mandanten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, mandant.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0) {
                entry.close();
                statement.close();
                con.close();
                return false;
        }

        entry.next();

        entry.updateInt("parentId", mandant.getParentId());
        entry.updateInt("creatorId", mandant.getCreatorId());
        entry.updateString("firmenName", mandant.getFirmenName());

        entry.updateString("firmenZusatz", mandant.getFirmenZusatz());

        entry.updateString("firmenZusatz2", mandant.getFirmenZusatz2());

        entry.updateString("vermittlungNamen", mandant.getVermittlungNamen());

        entry.updateString("vertretungsBerechtigtePosition", ArrayStringTools.arrayToString(mandant.getVertretungsBerechtigtePosition(),","));

        entry.updateString("vertretungsBerechtigteVorname", ArrayStringTools.arrayToString(mandant.getVertretungsBerechtigteVorname(), ","));

        entry.updateString("vertretungsBerechtigteNachname", ArrayStringTools.arrayToString(mandant.getVertretungsBerechtigteNachname(), ","));

        entry.updateString("vertretungsBerechtigteIHKErlaubnis", ArrayStringTools.arrayToString(mandant.getVertretungsBerechtigteIHKErlaubnis(), ","));

        entry.updateString("firmenTyp", mandant.getFirmenTyp());

        entry.updateString("firmenRechtsform", mandant.getFirmenRechtsform());

        entry.updateString("postfach", mandant.getPostfach());

        entry.updateString("postfachPlz", mandant.getPostfachPlz());

        entry.updateString("postfachOrt", mandant.getPostfachOrt());

        entry.updateString("filialTyp", mandant.getFilialTyp());

        entry.updateString("filialMitarbeiterZahl", mandant.getFilialMitarbeiterZahl());

        entry.updateString("geschaeftsleiter", mandant.getGeschaeftsleiter());

        entry.updateString("gesellschafter", ArrayStringTools.arrayToString(mandant.getGesellschafter(), ","));

        entry.updateString("steuerNummer", mandant.getSteuerNummer());

        entry.updateString("ustNummer", mandant.getUstNummer());

        entry.updateString("vermoegensHaftpflicht", mandant.getVermoegensHaftpflicht());
        entry.updateBoolean("beteiligungenVU", mandant.isBeteiligungenVU());
        entry.updateBoolean("beteiligungenMAK", mandant.isBeteiligungenMAK());

        entry.updateString("verbandsMitgliedschaften", ArrayStringTools.arrayToString(mandant.getVerbandsMitgliedschaften(), ","));

        entry.updateString("beraterTyp", mandant.getBeraterTyp());

        entry.updateString("ihkName", mandant.getIhkName());

        entry.updateString("ihkRegistriernummer", mandant.getIhkRegistriernummer());

        entry.updateString("ihkStatus", mandant.getIhkStatus());

        entry.updateString("ihkAbweichungen", mandant.getIhkAbweichungen());

        entry.updateString("versicherListe", ArrayStringTools.arrayToString(mandant.getVersicherListe(), ","));
        entry.updateBoolean("is34c", mandant.is34c());
        entry.updateBoolean("is34d", mandant.is34d());
        entry.updateBoolean("gewerbeamtShow", mandant.isGewerbeamtShow());

        entry.updateString("gewerbeamtName", mandant.getGewerbeamtName());

        entry.updateString("gewerbeamtPLZ", mandant.getGewerbeamtPLZ());

        entry.updateString("gewerbeamtOrt", mandant.getGewerbeamtOrt());

        entry.updateString("gewerbeamtStrasse", mandant.getGewerbeamtStrasse());
        entry.updateBoolean("handelsregisterShow", mandant.isHandelsregisterShow());

        entry.updateString("handelsregisterName", mandant.getHandelsregisterName());

        entry.updateString("handelsregisterStrasse", mandant.getHandelsregisterStrasse());

        entry.updateString("handelsregisterPLZ", mandant.getHandelsregisterPLZ());

        entry.updateString("handelsregisterOrt", mandant.getHandelsregisterOrt());

        entry.updateString("handelsregisterRegistrierNummer", mandant.getHandelsregisterRegistrierNummer());

        entry.updateString("logo", mandant.getLogo());

        entry.updateString("beschwerdeStellen", ArrayStringTools.arrayToString(mandant.getBeschwerdeStellen(), ","));

        entry.updateString("adressZusatz", mandant.getAdressZusatz());

        entry.updateString("adressZusatz2", mandant.getAdressZusatz2());

        entry.updateString("strasse", mandant.getStrasse());

        entry.updateString("plz", mandant.getPlz());

        entry.updateString("ort", mandant.getOrt());

        entry.updateString("bundesland", mandant.getBundesland());

        entry.updateString("land", mandant.getLand());

        entry.updateString("bankName", mandant.getBankName());

        entry.updateString("bankKonto", mandant.getBankKonto());

        entry.updateString("bankEigentuemer", mandant.getBankEigentuemer());

        entry.updateString("bankLeitzahl", mandant.getBankLeitzahl());

        entry.updateString("bankIBAN", mandant.getBankIBAN());

        entry.updateString("bankBIC", mandant.getBankBIC());

        entry.updateString("telefon", mandant.getTelefon());

        entry.updateString("telefon2", mandant.getTelefon2());

        entry.updateString("telefon3", mandant.getTelefon3());

        entry.updateString("mobil", mandant.getMobil());

        entry.updateString("mobil2", mandant.getMobil2());

        entry.updateString("fax", mandant.getFax());

        entry.updateString("fax2", mandant.getFax2());

        entry.updateString("email", mandant.getEmail());

        entry.updateString("email2", mandant.getEmail2());

        entry.updateString("secureMail", mandant.getSecureMail());

        entry.updateString("emailSignatur", mandant.getEmailSignatur());

        entry.updateString("homepage", mandant.getHomepage());

        entry.updateString("homepage2", mandant.getHomepage2());
        entry.updateString("custom1", mandant.getCustom1());
        entry.updateString("custom2", mandant.getCustom2());
        entry.updateString("custom3", mandant.getCustom3());
        entry.updateString("custom4", mandant.getCustom4());
        entry.updateString("custom5", mandant.getCustom5());
        entry.updateString("custom6", mandant.getCustom6());
        entry.updateString("custom7", mandant.getCustom7());
        entry.updateString("custom8", mandant.getCustom8());
        entry.updateString("custom9", mandant.getCustom9());
        entry.updateString("custom10", mandant.getCustom10());
        entry.updateString("comments", mandant.getComments());
        entry.updateTimestamp("created", mandant.getCreated());
        entry.updateTimestamp("modified", mandant.getModified());
        entry.updateTimestamp("lastUsed", mandant.getLastUsed());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    public static void deleteFrommandanten(Connection con, MandantenObj mandant) throws SQLException {
        deleteFrommandanten(con, mandant.getId());
    }


    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */

    public static void deleteFrommandanten(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM mandanten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static MandantenObj[] loadMandaten(Connection con) throws SQLException {
        String sql = "SELECT * FROM mandanten WHERE status = 0";

        MandantenObj[] mandanten = null;

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

        mandanten = new MandantenObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
            mandanten[i] = new MandantenObj(entry.getString("firmenName"), entry.getString("strasse"),
                 entry.getString("plz"), entry.getString("ort"));

            mandanten[i].setId(entry.getInt("id"));
            mandanten[i].setParentId(entry.getInt("parentId"));
            mandanten[i].setCreatorId(entry.getInt("creatorId"));
            mandanten[i].setFirmenName(entry.getString("firmenName"));
            mandanten[i].setFirmenZusatz(entry.getString("firmenZusatz"));
            mandanten[i].setFirmenZusatz2(entry.getString("firmenZusatz2"));
            mandanten[i].setVermittlungNamen(entry.getString("vermittlungNamen"));
            mandanten[i].setVertretungsBerechtigtePosition(ArrayStringTools.stringToArray(entry.getString("vertretungsBerechtigtePosition"), ","));
            mandanten[i].setVertretungsBerechtigteVorname(ArrayStringTools.stringToArray(entry.getString("vertretungsBerechtigteVorname"), ","));
            mandanten[i].setVertretungsBerechtigteNachname(ArrayStringTools.stringToArrayKomma(entry.getString("vertretungsBerechtigteNachname")));
            mandanten[i].setVertretungsBerechtigteIHKErlaubnis(ArrayStringTools.stringToArrayKomma(entry.getString("vertretungsBerechtigteIHKErlaubnis")));
            mandanten[i].setFirmenTyp(entry.getString("firmenTyp"));
            mandanten[i].setFirmenRechtsform(entry.getString("firmenRechtsform"));
            mandanten[i].setPostfach(entry.getString("postfach"));
            mandanten[i].setPostfachPlz(entry.getString("postfachPlz"));
            mandanten[i].setPostfachOrt(entry.getString("postfachOrt"));
            mandanten[i].setFilialTyp(entry.getString("filialTyp"));
            mandanten[i].setFilialMitarbeiterZahl(entry.getString("filialMitarbeiterZahl"));
            mandanten[i].setGeschaeftsleiter(entry.getString("geschaeftsleiter"));
            mandanten[i].setGesellschafter(ArrayStringTools.stringToArrayKomma(entry.getString("gesellschafter")));
            mandanten[i].setSteuerNummer(entry.getString("steuerNummer"));
            mandanten[i].setUstNummer(entry.getString("ustNummer"));
            mandanten[i].setVermoegensHaftpflicht(entry.getString("vermoegensHaftpflicht"));
            mandanten[i].setBeteiligungenVU(entry.getBoolean("beteiligungenVU"));
            mandanten[i].setBeteiligungenMAK(entry.getBoolean("beteiligungenMAK"));
            mandanten[i].setVerbandsMitgliedschaften(ArrayStringTools.stringToArrayKomma(entry.getString("verbandsMitgliedschaften")));
            mandanten[i].setBeraterTyp(entry.getString("beraterTyp"));
            mandanten[i].setIhkName(entry.getString("ihkName"));
            mandanten[i].setIhkRegistriernummer(entry.getString("ihkRegistriernummer"));
            mandanten[i].setIhkStatus(entry.getString("ihkStatus"));
            mandanten[i].setIhkAbweichungen(entry.getString("ihkAbweichungen"));
            mandanten[i].setVersicherListe(ArrayStringTools.stringToArrayKomma(entry.getString("versicherListe")));
            mandanten[i].set34c(entry.getBoolean("is34c"));
            mandanten[i].set34d(entry.getBoolean("is34d"));
            mandanten[i].setGewerbeamtShow(entry.getBoolean("gewerbeamtShow"));
            mandanten[i].setGewerbeamtName(entry.getString("gewerbeamtName"));
            mandanten[i].setGewerbeamtPLZ(entry.getString("gewerbeamtPLZ"));
            mandanten[i].setGewerbeamtOrt(entry.getString("gewerbeamtOrt"));
            mandanten[i].setGewerbeamtStrasse(entry.getString("gewerbeamtStrasse"));
            mandanten[i].setHandelsregisterShow(entry.getBoolean("handelsregisterShow"));
            mandanten[i].setHandelsregisterName(entry.getString("handelsregisterName"));
            mandanten[i].setHandelsregisterStrasse(entry.getString("handelsregisterStrasse"));
            mandanten[i].setHandelsregisterPLZ(entry.getString("handelsregisterPLZ"));
            mandanten[i].setHandelsregisterOrt(entry.getString("handelsregisterOrt"));
            mandanten[i].setHandelsregisterRegistrierNummer(entry.getString("handelsregisterRegistrierNummer"));
            mandanten[i].setLogo(entry.getString("logo"));
            mandanten[i].setBeschwerdeStellen(ArrayStringTools.stringToArrayKomma(entry.getString("beschwerdeStellen")));
            mandanten[i].setAdressZusatz(entry.getString("adressZusatz"));
            mandanten[i].setAdressZusatz2(entry.getString("adressZusatz2"));
            mandanten[i].setStrasse(entry.getString("strasse"));
            mandanten[i].setPlz(entry.getString("plz"));
            mandanten[i].setOrt(entry.getString("ort"));
            mandanten[i].setBundesland(entry.getString("bundesland"));
            mandanten[i].setLand(entry.getString("land"));
            mandanten[i].setBankName(entry.getString("bankName"));
            mandanten[i].setBankKonto(entry.getString("bankKonto"));
            mandanten[i].setBankEigentuemer(entry.getString("bankEigentuemer"));
            mandanten[i].setBankLeitzahl(entry.getString("bankLeitzahl"));
            mandanten[i].setBankIBAN(entry.getString("bankIBAN"));
            mandanten[i].setBankBIC(entry.getString("bankBIC"));
            mandanten[i].setTelefon(entry.getString("telefon"));
            mandanten[i].setTelefon2(entry.getString("telefon2"));
            mandanten[i].setTelefon3(entry.getString("telefon3"));
            mandanten[i].setMobil(entry.getString("mobil"));
            mandanten[i].setMobil2(entry.getString("mobil2"));
            mandanten[i].setFax(entry.getString("fax"));
            mandanten[i].setFax2(entry.getString("fax2"));
            mandanten[i].setEmail(entry.getString("email"));
            mandanten[i].setEmail2(entry.getString("email2"));
            mandanten[i].setSecureMail(entry.getString("secureMail"));
            mandanten[i].setEmailSignatur(entry.getString("emailSignatur"));
            mandanten[i].setHomepage(entry.getString("homepage"));
            mandanten[i].setHomepage2(entry.getString("homepage2"));
            mandanten[i].setCustom1(entry.getString("custom1"));
            mandanten[i].setCustom2(entry.getString("custom2"));
            mandanten[i].setCustom3(entry.getString("custom3"));
            mandanten[i].setCustom4(entry.getString("custom4"));
            mandanten[i].setCustom5(entry.getString("custom5"));
            mandanten[i].setCustom6(entry.getString("custom6"));
            mandanten[i].setCustom7(entry.getString("custom7"));
            mandanten[i].setCustom8(entry.getString("custom8"));
            mandanten[i].setCustom9(entry.getString("custom9"));
            mandanten[i].setCustom10(entry.getString("custom10"));
            mandanten[i].setComments(entry.getString("comments"));
            mandanten[i].setCreated(entry.getTimestamp("created"));
            mandanten[i].setModified(entry.getTimestamp("modified"));
            mandanten[i].setLastUsed(entry.getTimestamp("lastUsed"));
            mandanten[i].setStatus(entry.getInt("status"));
        }
        
        return mandanten;
    }

    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     */

    public static MandantenObj loadMandat(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM mandanten WHERE id = ? AND status = 0";

        MandantenObj mandant = new MandantenObj();

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

        mandant.setId(entry.getInt("id"));
        mandant.setParentId(entry.getInt("parentId"));
        mandant.setCreatorId(entry.getInt("creatorId"));
        mandant.setFirmenName(entry.getString("firmenName"));
        mandant.setFirmenZusatz(entry.getString("firmenZusatz"));
        mandant.setFirmenZusatz2(entry.getString("firmenZusatz2"));
        mandant.setVermittlungNamen(entry.getString("vermittlungNamen"));
        mandant.setVertretungsBerechtigtePosition(ArrayStringTools.stringToArray(entry.getString("vertretungsBerechtigtePosition"), ","));
        mandant.setVertretungsBerechtigteVorname(ArrayStringTools.stringToArray(entry.getString("vertretungsBerechtigteVorname"), ","));
        mandant.setVertretungsBerechtigteNachname(ArrayStringTools.stringToArrayKomma(entry.getString("vertretungsBerechtigteNachname")));
        mandant.setVertretungsBerechtigteIHKErlaubnis(ArrayStringTools.stringToArrayKomma(entry.getString("vertretungsBerechtigteIHKErlaubnis")));
        mandant.setFirmenTyp(entry.getString("firmenTyp"));
        mandant.setFirmenRechtsform(entry.getString("firmenRechtsform"));
        mandant.setPostfach(entry.getString("postfach"));
        mandant.setPostfachPlz(entry.getString("postfachPlz"));
        mandant.setPostfachOrt(entry.getString("postfachOrt"));
        mandant.setFilialTyp(entry.getString("filialTyp"));
        mandant.setFilialMitarbeiterZahl(entry.getString("filialMitarbeiterZahl"));
        mandant.setGeschaeftsleiter(entry.getString("geschaeftsleiter"));
        mandant.setGesellschafter(ArrayStringTools.stringToArrayKomma(entry.getString("gesellschafter")));
        mandant.setSteuerNummer(entry.getString("steuerNummer"));
        mandant.setUstNummer(entry.getString("ustNummer"));
        mandant.setVermoegensHaftpflicht(entry.getString("vermoegensHaftpflicht"));
        mandant.setBeteiligungenVU(entry.getBoolean("beteiligungenVU"));
        mandant.setBeteiligungenMAK(entry.getBoolean("beteiligungenMAK"));
        mandant.setVerbandsMitgliedschaften(ArrayStringTools.stringToArrayKomma(entry.getString("verbandsMitgliedschaften")));
        mandant.setBeraterTyp(entry.getString("beraterTyp"));
        mandant.setIhkName(entry.getString("ihkName"));
        mandant.setIhkRegistriernummer(entry.getString("ihkRegistriernummer"));
        mandant.setIhkStatus(entry.getString("ihkStatus"));
        mandant.setIhkAbweichungen(entry.getString("ihkAbweichungen"));
        mandant.setVersicherListe(ArrayStringTools.stringToArrayKomma(entry.getString("versicherListe")));
        mandant.set34c(entry.getBoolean("is34c"));
        mandant.set34d(entry.getBoolean("is34d"));
        mandant.setGewerbeamtShow(entry.getBoolean("gewerbeamtShow"));
        mandant.setGewerbeamtName(entry.getString("gewerbeamtName"));
        mandant.setGewerbeamtPLZ(entry.getString("gewerbeamtPLZ"));
        mandant.setGewerbeamtOrt(entry.getString("gewerbeamtOrt"));
        mandant.setGewerbeamtStrasse(entry.getString("gewerbeamtStrasse"));
        mandant.setHandelsregisterShow(entry.getBoolean("handelsregisterShow"));
        mandant.setHandelsregisterName(entry.getString("handelsregisterName"));
        mandant.setHandelsregisterStrasse(entry.getString("handelsregisterStrasse"));
        mandant.setHandelsregisterPLZ(entry.getString("handelsregisterPLZ"));
        mandant.setHandelsregisterOrt(entry.getString("handelsregisterOrt"));
        mandant.setHandelsregisterRegistrierNummer(entry.getString("handelsregisterRegistrierNummer"));
        mandant.setLogo(entry.getString("logo"));
        mandant.setBeschwerdeStellen(ArrayStringTools.stringToArrayKomma(entry.getString("beschwerdeStellen")));
        mandant.setAdressZusatz(entry.getString("adressZusatz"));
        mandant.setAdressZusatz2(entry.getString("adressZusatz2"));
        mandant.setStrasse(entry.getString("strasse"));
        mandant.setPlz(entry.getString("plz"));
        mandant.setOrt(entry.getString("ort"));
        mandant.setBundesland(entry.getString("bundesland"));
        mandant.setLand(entry.getString("land"));
        mandant.setBankName(entry.getString("bankName"));
        mandant.setBankKonto(entry.getString("bankKonto"));
        mandant.setBankEigentuemer(entry.getString("bankEigentuemer"));
        mandant.setBankLeitzahl(entry.getString("bankLeitzahl"));
        mandant.setBankIBAN(entry.getString("bankIBAN"));
        mandant.setBankBIC(entry.getString("bankBIC"));
        mandant.setTelefon(entry.getString("telefon"));
        mandant.setTelefon2(entry.getString("telefon2"));
        mandant.setTelefon3(entry.getString("telefon3"));
        mandant.setMobil(entry.getString("mobil"));
        mandant.setMobil2(entry.getString("mobil2"));
        mandant.setFax(entry.getString("fax"));
        mandant.setFax2(entry.getString("fax2"));
        mandant.setEmail(entry.getString("email"));
        mandant.setEmail2(entry.getString("email2"));
        mandant.setSecureMail(entry.getString("secureMail"));
        mandant.setEmailSignatur(entry.getString("emailSignatur"));
        mandant.setHomepage(entry.getString("homepage"));
        mandant.setHomepage2(entry.getString("homepage2"));
        mandant.setCustom1(entry.getString("custom1"));
        mandant.setCustom2(entry.getString("custom2"));
        mandant.setCustom3(entry.getString("custom3"));
        mandant.setCustom4(entry.getString("custom4"));
        mandant.setCustom5(entry.getString("custom5"));
        mandant.setCustom6(entry.getString("custom6"));
        mandant.setCustom7(entry.getString("custom7"));
        mandant.setCustom8(entry.getString("custom8"));
        mandant.setCustom9(entry.getString("custom9"));
        mandant.setCustom10(entry.getString("custom10"));
        mandant.setComments(entry.getString("comments"));
        mandant.setCreated(entry.getTimestamp("created"));
        mandant.setModified(entry.getTimestamp("modified"));
        mandant.setLastUsed(entry.getTimestamp("lastUsed"));
        mandant.setStatus(entry.getInt("status"));

        entry.close();
        statement.close();
        con.close();

        return mandant;
    }
}