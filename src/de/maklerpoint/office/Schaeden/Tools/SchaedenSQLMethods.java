/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2011/07/05 13:10
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

package de.maklerpoint.office.Schaeden.Tools;

/* Neccessary imports for the generated code */
import de.maklerpoint.office.Konstanten.Schaeden;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SchaedenSQLMethods {

    /**
     * 
     * @param con
     * @param sch
     * @return
     * @throws SQLException 
     */
    public static int insertIntoSchaeden(Connection con, SchadenObj sch) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO schaeden (creatorId, kundenNr, vertragsId, schadenNr, meldungArt, meldungVon, "
                + "meldungTime, schaedenTime, schadenPolizei, schadenKategorie, schadenBearbeiter, schadenOrt, "
                + "schadenUmfang, schadenHergang, vuWeiterleitungTime, vuMeldungArt, risiko, schadenHoehe, "
                + "schadenAbrechnungArt, vuGutachten, vuSchadennummer, vuStatusDatum, wiedervorlagenId, interneInfo, "
                + "notiz, custom1, custom2, custom3, custom4, custom5, "
                + "created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, sch.getCreatorId());
        statement.setString(2, sch.getKundenNr());
        statement.setInt(3, sch.getVertragsId());
        statement.setString(4, sch.getSchadenNr());
        statement.setString(5, sch.getMeldungArt());
        statement.setString(6, sch.getMeldungVon());
        statement.setTimestamp(7, sch.getMeldungTime());
        statement.setTimestamp(8, sch.getSchaedenTime());
        statement.setBoolean(9, sch.isSchadenPolizei());
        statement.setString(10, sch.getSchadenKategorie());
        statement.setInt(11, sch.getSchadenBearbeiter());
        statement.setString(12, sch.getSchadenOrt());
        statement.setString(13, sch.getSchadenUmfang());
        statement.setString(14, sch.getSchadenHergang());
        statement.setTimestamp(15, sch.getVuWeiterleitungTime());
        statement.setString(16, sch.getVuMeldungArt());
        statement.setString(17, sch.getRisiko());
        statement.setDouble(18, sch.getSchadenHoehe());
        statement.setInt(19, sch.getSchadenAbrechnungArt());
        statement.setBoolean(20, sch.isVuGutachten());
        statement.setString(21, sch.getVuSchadennummer());
        statement.setTimestamp(22, sch.getVuStatusDatum());
        statement.setInt(23, sch.getWiedervorlagenId());
        statement.setString(24, sch.getInterneInfo());
        statement.setString(25, sch.getNotiz());
        statement.setString(26, sch.getCustom1());
        statement.setString(27, sch.getCustom2());
        statement.setString(28, sch.getCustom3());
        statement.setString(29, sch.getCustom4());
        statement.setString(30, sch.getCustom5());
        statement.setTimestamp(31, sch.getCreated());
        statement.setTimestamp(32, sch.getModified());
        statement.setInt(33, sch.getStatus());
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
     * @param schaden
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateSchaeden(Connection con, SchadenObj sch) throws SQLException {
        String sql = "SELECT * FROM schaeden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, sch.getId());
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

        entry.updateInt("creatorId", sch.getCreatorId());

        entry.updateString("kundenNr", sch.getKundenNr());

        entry.updateInt("vertragsId", sch.getVertragsId());

        entry.updateString("schadenNr", sch.getSchadenNr());
        entry.updateString("meldungArt", sch.getMeldungArt());
        entry.updateString("meldungVon", sch.getMeldungVon());
        entry.updateTimestamp("meldungTime", sch.getMeldungTime());
        entry.updateTimestamp("schaedenTime", sch.getSchaedenTime());
        entry.updateBoolean("schadenPolizei", sch.isSchadenPolizei());
        entry.updateString("schadenKategorie", sch.getSchadenKategorie());
        entry.updateInt("schadenBearbeiter", sch.getSchadenBearbeiter());
        entry.updateString("schadenOrt", sch.getSchadenOrt());
        entry.updateString("schadenUmfang", sch.getSchadenUmfang());
        entry.updateString("schadenHergang", sch.getSchadenHergang());
        entry.updateTimestamp("vuWeiterleitungTime", sch.getVuWeiterleitungTime());
        entry.updateString("vuMeldungArt", sch.getVuMeldungArt());
        entry.updateString("risiko", sch.getRisiko());
        entry.updateDouble("schadenHoehe", sch.getSchadenHoehe());
        entry.updateInt("schadenAbrechnungArt", sch.getSchadenAbrechnungArt());
        entry.updateBoolean("vuGutachten", sch.isVuGutachten());
        entry.updateString("vuSchadennummer", sch.getVuSchadennummer());        
        entry.updateTimestamp("vuStatusDatum", sch.getVuStatusDatum());
        entry.updateInt("wiedervorlagenId", sch.getWiedervorlagenId());
        entry.updateString("interneInfo", sch.getInterneInfo());
        entry.updateString("notiz", sch.getNotiz());
        entry.updateString("custom1", sch.getCustom1());
        entry.updateString("custom2", sch.getCustom2());
        entry.updateString("custom3", sch.getCustom3());
        entry.updateString("custom4", sch.getCustom4());
        entry.updateString("custom5", sch.getCustom5());
        entry.updateTimestamp("created", sch.getCreated());
        entry.updateTimestamp("modified", sch.getModified());
        entry.updateInt("status", sch.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }
    
    /**
     * 
     * @param con
     * @param sch
     * @throws SQLException 
     */
    
    public static void deleteFromSchaeden(Connection con, SchadenObj sch) throws SQLException {
        if (sch == null) {
            return;
        }

        String sql = "UPDATE schaeden SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, sch.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        sch.setStatus(Status.DELETED);
    }
    
    /**
     * 
     * @param con
     * @param sch
     * @throws SQLException 
     */
    
    public static void reguliereFromSchaeden(Connection con, SchadenObj sch) throws SQLException {
        if (sch == null) {
            return;
        }

        String sql = "UPDATE schaeden SET status = " + Schaeden.STATUS_REGULIERT + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, sch.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        sch.setStatus(Schaeden.STATUS_REGULIERT);
    }
    
    /**
     * 
     * @param con
     * @param keyId
     * @throws SQLException 
     */
    
    public static void deleteEndgueltigFromSchaeden(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM schaeden WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    /**
     * 
     * @param con
     * @param kdnr
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static SchadenObj[] getKundenSchaeden(Connection con, String kdnr, int status) throws SQLException {
        SchadenObj[] sch = null;

        String sql = "SELECT * FROM schaeden WHERE kundenNr = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM schaeden WHERE kundenNr = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kdnr);

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

        sch = new SchadenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getSchadenObjResult(entry);
        }


        statement.close();
        con.close();

        return sch;
    }

    /**
     * 
     * @param con
     * @param vtrid
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static SchadenObj[] getVertragSchaeden(Connection con, int vtrid, int status) throws SQLException {
        SchadenObj[] sch = null;

        String sql = "SELECT * FROM schaeden WHERE vertragsId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM schaeden WHERE vertragsId = ?";
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
            statement.close();
            con.close();
            return null;
        }

        sch = new SchadenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getSchadenObjResult(entry);
        }


        statement.close();
        con.close();

        return sch;
    }
    
    
    public static SchadenObj getSchaden(Connection con, int id) throws SQLException {

        String sql = "SELECT * FROM schaeden WHERE id = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, id);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        SchadenObj sch = getSchadenObjResult(entry);

        statement.close();
        con.close();

        return sch;
    }
    
    public static SchadenObj getSchaden(Connection con, String schadennr) throws SQLException {

        String sql = "SELECT * FROM schaeden WHERE schadenNr = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, schadennr);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            statement.close();
            con.close();
            return null;
        }

        entry.next();
        SchadenObj sch = getSchadenObjResult(entry);

        statement.close();
        con.close();

        return sch;
    }
    
    /**
     * 
     * @param con
     * @param benid
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static SchadenObj[] getBenutzerSchaeden(Connection con, int benid, int status) throws SQLException {
        SchadenObj[] sch = null;

        String sql = "SELECT * FROM schaeden WHERE schadenBearbeiter = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM schaeden WHERE schadenBearbeiter = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benid);

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

        sch = new SchadenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getSchadenObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }
    
    public static SchadenObj[] loadSchaeden(Connection con, int status) throws SQLException {
        SchadenObj[] sch = null;

        String sql = "SELECT * FROM schaeden WHERE status = ?";

        if (status == -1) {
            sql = "SELECT * FROM schaeden";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if (status != -1) {
            statement.setInt(1, status);
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

        sch = new SchadenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            sch[i] = getSchadenObjResult(entry);
        }

        statement.close();
        con.close();

        return sch;
    }
    
    /**
     * 
     * @param entry
     * @return
     * @throws SQLException 
     */

    public static SchadenObj getSchadenObjResult(ResultSet entry) throws SQLException {
        SchadenObj sch = new SchadenObj();

        sch.setId(entry.getInt("id"));
        sch.setCreatorId(entry.getInt("creatorId"));

        sch.setKundenNr(entry.getString("kundenNr"));
        sch.setVertragsId(entry.getInt("vertragsId"));
        sch.setSchadenNr(entry.getString("schadenNr"));
        sch.setMeldungArt(entry.getString("meldungArt"));
        sch.setMeldungVon(entry.getString("meldungVon"));
        sch.setMeldungTime(entry.getTimestamp("meldungTime"));
        sch.setSchaedenTime(entry.getTimestamp("schaedenTime"));
        sch.setSchadenPolizei(entry.getBoolean("schadenPolizei"));
        sch.setSchadenKategorie(entry.getString("schadenKategorie"));
        sch.setSchadenBearbeiter(entry.getInt("schadenBearbeiter"));
        sch.setSchadenOrt(entry.getString("schadenOrt"));
        sch.setSchadenUmfang(entry.getString("schadenUmfang"));
        sch.setSchadenHergang(entry.getString("schadenHergang"));
        sch.setVuWeiterleitungTime(entry.getTimestamp("vuWeiterleitungTime"));
        sch.setVuMeldungArt(entry.getString("vuMeldungArt"));
        sch.setRisiko(entry.getString("risiko"));
        sch.setSchadenHoehe(entry.getDouble("schadenHoehe"));
        sch.setSchadenAbrechnungArt(entry.getInt("schadenAbrechnungArt"));
        sch.setVuGutachten(entry.getBoolean("vuGutachten"));
        sch.setVuSchadennummer(entry.getString("vuSchadennummer"));
        sch.setVuStatusDatum(entry.getTimestamp("vuStatusDatum"));
        sch.setWiedervorlagenId(entry.getInt("wiedervorlagenId"));
        sch.setInterneInfo(entry.getString("interneInfo"));
        sch.setNotiz(entry.getString("notiz"));

        sch.setCustom1(entry.getString("custom1"));
        sch.setCustom2(entry.getString("custom2"));
        sch.setCustom3(entry.getString("custom3"));
        sch.setCustom4(entry.getString("custom4"));
        sch.setCustom5(entry.getString("custom5"));

        sch.setCreated(entry.getTimestamp("created"));
        sch.setModified(entry.getTimestamp("modified"));

        sch.setStatus(entry.getInt("status"));

        return sch;
    }
}
