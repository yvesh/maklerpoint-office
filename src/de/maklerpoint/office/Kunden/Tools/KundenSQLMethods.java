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

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
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
public class KundenSQLMethods {

    public static int insertIntoKunden(Connection con, KundenObj kunde) throws SQLException {
        int generatedId = -1;
//        String sql = "INSERT INTO kunden (betreuer, besitzer, mandantenId, kundenNr, anrede, titel, " // 5
//                + "firma, vorname, vorname2, vornameWeitere, nachname, street, plz, stadt, "// 13
//                + "bundesland, land, adresseZusatz, adresseZusatz2" // 17
//                + "communication1, communication2, communication3, communication4, " //21
//                + "communication5, communication6, communication1Type, communication2Type, communication3Type, communication4Type, " // 27
//                + "communication5Type, communication6Type, typ, familienStand, ehepartnerId, geburtsdatum, nationalität, " // 34
//                + "beruf, berufsTyp, berufsOptionen, berufsBesonderheiten, anteilBuerotaetigkeit, beginnRente, " //  40
//                + "beamter, oeffentlicherDienst, einkommen, einkommenNetto, steuertabelle, steuerklasse, " // 46
//                + "kirchenSteuer, kinderZahl, kinderFreibetrag, religion, rolleImHaushalt, weiterePersonen, " // 52
//                + "weiterePersonenInfo, familienPlanung, werberKennung, defaultKonto, comments, custom1, custom2, " // 59
//                + "custom3, custom4, custom5, geburtsname, ehedatum, created, " // 65
//                + "modified, status)" + // 67
//                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " // 29
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " // 61
//                + "?, ?, ?, ?, ?, ?, ?)"; // 68 (-1)
        String sql = "INSERT INTO kunden (betreuerId, creatorId, mandantenId, kundenNr, anrede, titel, "
                + "firma, vorname, vorname2, vornameWeitere, nachname, street, plz, stadt, bundesland, land, adresseZusatz, "
                + "adresseZusatz2, communication1, communication2, communication3, communication4, communication5, "
                + "communication6, communication1Type, communication2Type, communication3Type, communication4Type, communication5Type, "
                + "communication6Type,"
                + "typ, familienStand, ehepartnerId, geburtsdatum, nationalitaet, beruf, "
                + "berufsTyp, berufsOptionen, berufsBesonderheiten, anteilBuerotaetigkeit, beginnRente, beamter, "
                + "oeffentlicherDienst,  einkommen, einkommenNetto, steuertabelle, steuerklasse, kirchenSteuer, "
                + "kinderZahl, kinderFreibetrag, religion, rolleImHaushalt, weiterePersonen, weiterePersonenInfo, "
                + "familienPlanung, werberKennung, defaultKonto, comments, custom1, custom2, "
                + "custom3, custom4, custom5, geburtsname, ehedatum, created, "
                + "modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, kunde.getBetreuerId());
        statement.setInt(2, kunde.getCreatorId());
        statement.setInt(3, kunde.getMandantenId());
        statement.setString(4, kunde.getKundenNr());
        statement.setString(5, kunde.getAnrede());

        statement.setString(6, kunde.getTitel());
        statement.setString(7, kunde.getFirma());
        statement.setString(8, kunde.getVorname());
        statement.setString(9, kunde.getVorname2());
        statement.setString(10, kunde.getVornameWeitere());

        statement.setString(11, kunde.getNachname());
        statement.setString(12, kunde.getStreet());
        statement.setString(13, kunde.getPlz());
        statement.setString(14, kunde.getStadt());
        statement.setString(15, kunde.getBundesland());

        statement.setString(16, kunde.getLand());
        statement.setString(17, kunde.getAdresseZusatz());
        statement.setString(18, kunde.getAdresseZusatz2());

        statement.setString(19, kunde.getCommunication1());
        statement.setString(20, kunde.getCommunication2());

        statement.setString(21, kunde.getCommunication3());
        statement.setString(22, kunde.getCommunication4());
        statement.setString(23, kunde.getCommunication5());
        statement.setString(24, kunde.getCommunication6());

        statement.setInt(25, kunde.getCommunication1Type());
        statement.setInt(26, kunde.getCommunication2Type());
        statement.setInt(27, kunde.getCommunication3Type());
        statement.setInt(28, kunde.getCommunication4Type());
        statement.setInt(29, kunde.getCommunication5Type());
        statement.setInt(30, kunde.getCommunication6Type());

        statement.setString(31, kunde.getTyp());
        statement.setString(32, kunde.getFamilienStand());
        statement.setString(33, kunde.getEhepartnerKennung());
        statement.setString(34, kunde.getGeburtsdatum());
        statement.setString(35, kunde.getNationalitaet());

        statement.setString(36, kunde.getBeruf());
        statement.setString(37, kunde.getBerufsTyp());
        statement.setString(38, kunde.getBerufsOptionen());
        statement.setString(39, kunde.getBerufsBesonderheiten());
        statement.setString(40, kunde.getAnteilBuerotaetigkeit());

        statement.setString(41, kunde.getBeginnRente());

        statement.setBoolean(42, kunde.isBeamter());
        statement.setBoolean(43, kunde.isOeffentlicherDienst());

        statement.setDouble(44, kunde.getEinkommen());
        statement.setDouble(45, kunde.getEinkommenNetto());

        statement.setString(46, kunde.getSteuertabelle());
        statement.setString(47, kunde.getSteuerklasse());
        statement.setString(48, kunde.getKirchenSteuer());
        statement.setInt(49, kunde.getKinderZahl());
        statement.setString(50, kunde.getKinderFreibetrag());

        statement.setString(51, kunde.getReligion());
        statement.setString(52, kunde.getRolleImHaushalt());
        statement.setString(53, kunde.getWeiterePersonen());
        statement.setString(54, kunde.getWeiterePersonenInfo());
        statement.setString(55, kunde.getFamilienPlanung());

        statement.setString(56, kunde.getWerberKennung());
        statement.setInt(57, kunde.getDefaultKonto());
        statement.setString(58, kunde.getComments());
        statement.setString(59, kunde.getCustom1());
        statement.setString(60, kunde.getCustom2());

        statement.setString(61, kunde.getCustom3());
        statement.setString(62, kunde.getCustom4());
        statement.setString(63, kunde.getCustom5());
        statement.setString(64, kunde.getGeburtsname());
        statement.setString(65, kunde.getEhedatum());

        statement.setTimestamp(66, kunde.getCreated());
        statement.setTimestamp(67, kunde.getModified());
        statement.setInt(68, kunde.getStatus());

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("Privatkunden Insert Statement: " + statement.toString());
        }

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
     * @param kunde
     * @return
     * @throws SQLException 
     */
    public static boolean updateKunden(Connection con, KundenObj kunde) throws SQLException {
        String sql = "SELECT * FROM kunden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, kunde.getId());
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

        entry.updateInt("betreuerId", kunde.getBetreuerId());
        entry.updateInt("creatorId", kunde.getCreatorId());
        entry.updateInt("mandantenId", kunde.getMandantenId());

        entry.updateString("kundenNr", kunde.getKundenNr());

        entry.updateString("anrede", kunde.getAnrede());

        entry.updateString("titel", kunde.getTitel());

        entry.updateString("firma", kunde.getFirma());

        entry.updateString("vorname", kunde.getVorname());
        entry.updateString("vorname2", kunde.getVorname2());

        entry.updateString("vornameWeitere", kunde.getVornameWeitere());

        entry.updateString("nachname", kunde.getNachname());

        entry.updateString("street", kunde.getStreet());

        entry.updateString("plz", kunde.getPlz());

        entry.updateString("stadt", kunde.getStadt());
        entry.updateString("bundesland", kunde.getBundesland());

        entry.updateString("land", kunde.getLand());
        entry.updateString("adresseZusatz", kunde.getAdresseZusatz());

        entry.updateString("adresseZusatz2", kunde.getAdresseZusatz2());

        entry.updateString("communication1", kunde.getCommunication1());
        entry.updateString("communication2", kunde.getCommunication2());
        entry.updateString("communication3", kunde.getCommunication3());
        entry.updateString("communication4", kunde.getCommunication4());
        entry.updateString("communication5", kunde.getCommunication5());
        entry.updateString("communication6", kunde.getCommunication6());

        entry.updateInt("communication1Type", kunde.getCommunication1Type());
        entry.updateInt("communication2Type", kunde.getCommunication2Type());
        entry.updateInt("communication3Type", kunde.getCommunication3Type());
        entry.updateInt("communication4Type", kunde.getCommunication4Type());
        entry.updateInt("communication5Type", kunde.getCommunication5Type());
        entry.updateInt("communication6Type", kunde.getCommunication6Type());


        entry.updateString("typ", kunde.getTyp());

        entry.updateString("familienStand", kunde.getFamilienStand());

        entry.updateString("ehepartnerId", kunde.getEhepartnerKennung());

        entry.updateString("geburtsdatum", kunde.getGeburtsdatum());

        entry.updateString("nationalitaet", kunde.getNationalitaet());
        entry.updateString("beruf", kunde.getBeruf());

        entry.updateString("berufsTyp", kunde.getBerufsTyp());

        entry.updateString("berufsOptionen", kunde.getBerufsOptionen());

        entry.updateString("berufsBesonderheiten", kunde.getBerufsBesonderheiten());
        entry.updateString("anteilBuerotaetigkeit", kunde.getAnteilBuerotaetigkeit());

        entry.updateString("beginnRente", kunde.getBeginnRente());
        entry.updateBoolean("beamter", kunde.isBeamter());
        entry.updateBoolean("oeffentlicherDienst", kunde.isOeffentlicherDienst());

        entry.updateDouble("einkommen", kunde.getEinkommen());

        entry.updateDouble("einkommenNetto", kunde.getEinkommenNetto());

        entry.updateString("steuertabelle", kunde.getSteuertabelle());

        entry.updateString("steuerklasse", kunde.getSteuerklasse());
        entry.updateString("kirchenSteuer", kunde.getKirchenSteuer());
        entry.updateInt("kinderZahl", kunde.getKinderZahl());

        entry.updateString("kinderFreibetrag", kunde.getKinderFreibetrag());
        entry.updateString("religion", kunde.getReligion());

        entry.updateString("rolleImHaushalt", kunde.getRolleImHaushalt());

        entry.updateString("weiterePersonen", kunde.getWeiterePersonen());

        entry.updateString("weiterePersonenInfo", kunde.getWeiterePersonenInfo());

        entry.updateString("familienPlanung", kunde.getFamilienPlanung());
        entry.updateString("werberKennung", kunde.getWerberKennung());

        entry.updateInt("defaultKonto", kunde.getDefaultKonto());
        entry.updateString("comments", kunde.getComments());

        entry.updateString("custom1", kunde.getCustom1());

        entry.updateString("custom2", kunde.getCustom2());

        entry.updateString("custom3", kunde.getCustom3());

        entry.updateString("custom4", kunde.getCustom4());

        entry.updateString("custom5", kunde.getCustom5());
        entry.updateString("geburtsname", kunde.getGeburtsname());

        entry.updateString("ehedatum", kunde.getEhedatum());

        entry.updateTimestamp("created", kunde.getCreated());
        entry.updateTimestamp("modified", kunde.getModified());
        entry.updateInt("status", kunde.getStatus());

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
    
    public static void deleteFromkunden(Connection con, int keyId) throws SQLException {
        String sql = "UPDATE kunden SET status = " + Status.DELETED + " WHERE id = ?";
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
    
    public static void archiveFromkunden(Connection con, int keyId) throws SQLException {
        String sql = "UPDATE kunden SET status = " + Status.ARCHIVED + " WHERE id = ?";
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
    public static void deleteEndgueltigFromkunden(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM kunden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static KundenObj[] loadKunden(Connection con, int status) throws SQLException {
        return loadKunden(con, null, status);
    }

    public static KundenObj[] loadgeworbeneKunden(Connection con, String werberkennung) throws SQLException {
        String sql = "SELECT * FROM kunden WHERE werberKennung = ?";
       
        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
     
        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("load-geworbene-kunden-statement: " + statement.toString());
        }
        
        statement.setString(1, werberkennung);

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

        kunden = new KundenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kunden[i] = getKundeEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * 
     * @param con
     * @param orderby
     * @param status
     * @return
     * @throws SQLException
     */
    public static KundenObj[] loadKunden(Connection con, String orderby, int status) throws SQLException {
        String sql = null;

        if (orderby == null && status == -1) {
            sql = "SELECT * FROM kunden";
        } else if (orderby == null) {
            sql = "SELECT * FROM kunden WHERE status = ?";
        } else if (status == -1) {
            sql = "SELECT * FROM kunden ORDER BY " + orderby;
        } else {
            sql = "SELECT * FROM kunden WHERE status = ? ORDER BY " + orderby;
        }

        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if (status != -1) {
            statement.setInt(1, status);
        }

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("loadkunden-statement: " + statement.toString());
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

        kunden = new KundenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kunden[i] = getKundeEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    public static KundenObj[] loadEigeneKunden(Connection con, BenutzerObj benutzer, int status) throws SQLException {
        return loadEigeneKunden(con, benutzer, null, status);
    }

    /**
     * 
     * @param con
     * @param benutzer
     * @param orderby
     * @param status
     * @return
     * @throws SQLException
     */
    public static KundenObj[] loadEigeneKunden(Connection con, BenutzerObj benutzer, String orderby, int status) throws SQLException {
        String sql = null;

        if (orderby == null && status == -1) {
            sql = "SELECT * FROM kunden WHERE betreuerId = ?";
        } else if (orderby == null) {
            sql = "SELECT * FROM kunden WHERE betreuerId = ? AND status = ?";
        } else if (status == -1) {
            sql = "SELECT * FROM kunden WHERE betreuerId = ? ORDER BY " + orderby;
        } else {
            sql = "SELECT * FROM kunden WHERE betreuerId = ? AND status = ? ORDER BY " + orderby;
        }


        KundenObj[] kunden = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, benutzer.getId());

        if (status != -1) {
            statement.setInt(2, status);
        }

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("loadeigkunden-statement: " + statement.toString());
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

        kunden = new KundenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            kunden[i] = getKundeEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * TODO Implement it
     * @param con
     * @param Kennung
     * @return
     * @throws SQLException
     */
    public static KundenObj loadKunde(Connection con, String kdnr) throws SQLException {
        String sql = "SELECT * FROM kunden WHERE kundenNr = ?";

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

        KundenObj kunde = getKundeEntry(entry);

        entry.close();
        statement.close();
        con.close();

        return kunde;
    }

    public static KundenObj getKundeEntry(ResultSet entry) throws SQLException {
        KundenObj kunde = new KundenObj();

        kunde.setId(entry.getInt("id"));
        kunde.setBetreuerId(entry.getInt("betreuerId"));
        kunde.setCreatorId(entry.getInt("creatorId"));
        kunde.setMandantenId(entry.getInt("mandantenId"));
        kunde.setKundenNr(entry.getString("kundenNr"));
        kunde.setAnrede(entry.getString("anrede"));
        kunde.setTitel(entry.getString("titel"));
        kunde.setFirma(entry.getString("firma")); // arbeitgeber
        kunde.setVorname(entry.getString("vorname"));
        kunde.setVorname2(entry.getString("vorname2"));
        kunde.setVornameWeitere(entry.getString("vornameWeitere"));

        kunde.setNachname(entry.getString("nachname"));
        kunde.setStreet(entry.getString("street"));
        kunde.setPlz(entry.getString("plz"));
        kunde.setStadt(entry.getString("stadt"));
        kunde.setBundesland(entry.getString("bundesland"));

        kunde.setLand(entry.getString("land"));
        kunde.setAdresseZusatz(entry.getString("adresseZusatz"));
        kunde.setAdresseZusatz2(entry.getString("adresseZusatz2"));

        kunde.setCommunication1(entry.getString("communication1"));
        kunde.setCommunication2(entry.getString("communication2"));
        kunde.setCommunication3(entry.getString("communication3"));
        kunde.setCommunication4(entry.getString("communication4"));
        kunde.setCommunication5(entry.getString("communication5"));
        kunde.setCommunication6(entry.getString("communication6"));

        kunde.setCommunication1Type(entry.getInt("communication1Type"));
        kunde.setCommunication2Type(entry.getInt("communication2Type"));
        kunde.setCommunication3Type(entry.getInt("communication3Type"));
        kunde.setCommunication4Type(entry.getInt("communication4Type"));
        kunde.setCommunication5Type(entry.getInt("communication5Type"));
        kunde.setCommunication6Type(entry.getInt("communication6Type"));

        kunde.setTyp(entry.getString("typ"));
        kunde.setFamilienStand(entry.getString("familienStand"));
        kunde.setEhepartnerId(entry.getString("ehepartnerId"));

        kunde.setGeburtsdatum(entry.getString("geburtsdatum"));
        kunde.setNationalitaet(entry.getString("nationalitaet"));

        kunde.setBeruf(entry.getString("beruf"));
        kunde.setBerufsTyp(entry.getString("berufsTyp"));
        kunde.setBerufsOptionen(entry.getString("berufsOptionen"));

        kunde.setAnteilBuerotaetigkeit(entry.getString("anteilBuerotaetigkeit"));
        kunde.setBeginnRente(entry.getString("beginnRente"));

        kunde.setBeamter(entry.getBoolean("beamter"));
        kunde.setOeffentlicherDienst(entry.getBoolean("oeffentlicherDienst"));

        kunde.setEinkommen(entry.getDouble("einkommen"));
        kunde.setEinkommenNetto(entry.getDouble("einkommenNetto"));

        kunde.setSteuertabelle(entry.getString("steuertabelle"));
        kunde.setSteuerklasse(entry.getString("steuerklasse"));
        kunde.setKirchenSteuer(entry.getString("kirchenSteuer"));

        kunde.setKinderZahl(entry.getInt("kinderZahl"));
        kunde.setKinderFreibetrag(entry.getString("kinderFreibetrag"));

        kunde.setReligion(entry.getString("religion"));
        kunde.setRolleImHaushalt(entry.getString("rolleImHaushalt"));

        kunde.setWeiterePersonen(entry.getString("weiterePersonen"));
        kunde.setWeiterePersonenInfo(entry.getString("weiterePersonenInfo"));
        kunde.setFamilienPlanung(entry.getString("familienPlanung"));
        kunde.setWerberKennung(entry.getString("werberKennung"));

        kunde.setDefaultKonto(entry.getInt("defaultKonto"));

        kunde.setComments(entry.getString("comments"));
        kunde.setCustom1(entry.getString("custom1"));
        kunde.setCustom2(entry.getString("custom2"));
        kunde.setCustom3(entry.getString("custom3"));
        kunde.setCustom4(entry.getString("custom4"));
        kunde.setCustom5(entry.getString("custom5"));

        kunde.setGeburtsname(entry.getString("geburtsname"));
        kunde.setEhedatum(entry.getString("ehedatum"));

        kunde.setCreated(entry.getTimestamp("created"));
        kunde.setModified(entry.getTimestamp("modified"));
        kunde.setStatus(entry.getInt("status"));

        return kunde;
    }

    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException
     */
    public static KundenObj loadKunde(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM kunden WHERE id = ?";

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

        KundenObj kunde = getKundeEntry(entry);

        entry.close();
        statement.close();
        con.close();

        return kunde;
    }
}
