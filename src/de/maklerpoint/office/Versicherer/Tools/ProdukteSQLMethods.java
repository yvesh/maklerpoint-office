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
package de.maklerpoint.office.Versicherer.Tools;

import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayStringTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */
public class ProdukteSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param versichererId
     * @param sparteId
     * @param creatorId
     * @param art
     * @param tarif
     * @param tarifBasis
     * @param bezeichnung
     * @param kuerzel
     * @param vertragsmaske
     * @param vermittelbar
     * @param versicherungsart
     * @param risikotyp
     * @param versicherungsSumme
     * @param bewertungsSumme
     * @param bedingungen
     * @param selbstbeteiligung
     * @param nettopraemiePauschal
     * @param nettopraemieZusatz
     * @param nettopraemieGesamt
     * @param zusatzEinschluesse
     * @param zusatzInfo
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
     */
    public static int insertIntoProdukteDeprec(Connection con, int versichererId, int sparteId, int creatorId, int art, String tarif,
            String tarifBasis, String bezeichnung, String kuerzel, int vertragsmaske, boolean vermittelbar,
            int versicherungsart, int risikotyp, double versicherungsSumme, double bewertungsSumme, String bedingungen,
            double selbstbeteiligung, double nettopraemiePauschal, double nettopraemieZusatz, double nettopraemieGesamt, int[] zusatzEinschluesse,
            String zusatzInfo, String comments, String custom1, String custom2, String custom3,
            String custom4, String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, int status)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO produkte (versichererId, sparteId, creatorId, art, tarif, tarifBasis, "
                + "bezeichnung, kuerzel, vertragsmaske, vermittelbar, versicherungsart, risikotyp, "
                + "versicherungsSumme, bewertungsSumme, bedingungen, selbstbeteiligung, nettopraemiePauschal, nettopraemieZusatz, "
                + "nettopraemieGesamt, zusatzEinschluesse, zusatzInfo, comments, custom1, custom2, "
                + "custom3, custom4, custom5, created, modified, status"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, versichererId);
        statement.setInt(2, sparteId);
        statement.setInt(3, creatorId);
        statement.setInt(4, art);
        statement.setString(5, tarif);
        statement.setString(6, tarifBasis);
        statement.setString(7, bezeichnung);
        statement.setString(8, kuerzel);
        statement.setInt(9, vertragsmaske);
        statement.setBoolean(10, vermittelbar);
        statement.setInt(11, versicherungsart);
        statement.setInt(12, risikotyp);
        statement.setDouble(13, versicherungsSumme);
        statement.setDouble(14, bewertungsSumme);
        statement.setString(15, bedingungen);
        statement.setDouble(16, selbstbeteiligung);
        statement.setDouble(17, nettopraemiePauschal);
        statement.setDouble(18, nettopraemieZusatz);
        statement.setDouble(19, nettopraemieGesamt);
        statement.setString(20, ArrayStringTools.arrayToString(zusatzEinschluesse, ","));
        statement.setString(21, zusatzInfo);
        statement.setString(22, comments);
        statement.setString(23, custom1);
        statement.setString(24, custom2);
        statement.setString(25, custom3);
        statement.setString(26, custom4);
        statement.setString(27, custom5);
        statement.setTimestamp(28, created);
        statement.setTimestamp(29, modified);
        statement.setInt(30, status);
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
     * @param prod
     * @return
     * @throws SQLException
     */
    public static int insertIntoProdukte(Connection con, ProduktObj prod)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO produkte (versichererId, sparteId, creatorId, art, tarif, tarifBasis, "
                + "bezeichnung, kuerzel, vertragsmaske, vermittelbar, versicherungsart, risikotyp, "
                + "versicherungsSumme, bewertungsSumme, bedingungen, selbstbeteiligung, nettopraemiePauschal, nettopraemieZusatz, "
                + "nettopraemieGesamt, zusatzEinschluesse, zusatzInfo, comments, custom1, custom2, "
                + "custom3, custom4, custom5, created, modified, status, waehrung"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, prod.getVersichererId());
        statement.setInt(2, prod.getSparteId());
        statement.setInt(3, prod.getCreatorId());
        statement.setInt(4, prod.getArt());
        statement.setString(5, prod.getTarif());
        statement.setString(6, prod.getTarifBasis());
        statement.setString(7, prod.getBezeichnung());
        statement.setString(8, prod.getKuerzel());
        statement.setInt(9, prod.getVertragsmaske());
        statement.setBoolean(10, prod.isVermittelbar());
        statement.setInt(11, prod.getVersicherungsart());
        statement.setInt(12, prod.getRisikotyp());
        statement.setDouble(13, prod.getVersicherungsSumme());
        statement.setDouble(14, prod.getBewertungsSumme());
        statement.setString(15, prod.getBedingungen());
        statement.setDouble(16, prod.getSelbstbeteiligung());
        statement.setDouble(17, prod.getNettopraemiePauschal());
        statement.setDouble(18, prod.getNettopraemieZusatz());
        statement.setDouble(19, prod.getNettopraemieGesamt());
        if (prod.getZusatzEinschluesse() != null) {
            statement.setString(20, prod.getZusatzEinschluesse().toString());
        } else {
            statement.setString(20, "");
        }
        statement.setString(21, prod.getZusatzInfo());
        statement.setString(22, prod.getComments());
        statement.setString(23, prod.getCustom1());
        statement.setString(24, prod.getCustom2());
        statement.setString(25, prod.getCustom3());
        statement.setString(26, prod.getCustom4());
        statement.setString(27, prod.getCustom5());
        statement.setTimestamp(28, prod.getCreated());
        statement.setTimestamp(29, prod.getModified());
        statement.setInt(30, prod.getStatus());
        statement.setInt(31, prod.getWaehrung());
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
     * @param prod
     * @return
     * @throws SQLException
     */
    public static boolean updateProdukte(Connection con, ProduktObj prod)
            throws SQLException {
        String sql = "SELECT * FROM produkte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, prod.getId());
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

        entry.updateInt("versichererId", prod.getVersichererId());
        entry.updateInt("sparteId", prod.getSparteId());
        entry.updateInt("creatorId", prod.getCreatorId());
        entry.updateInt("art", prod.getArt());
        entry.updateString("tarif", prod.getTarif());
        entry.updateString("tarifBasis", prod.getTarifBasis());
        entry.updateString("bezeichnung", prod.getBezeichnung());
        entry.updateString("kuerzel", prod.getKuerzel());
        entry.updateInt("vertragsmaske", prod.getVertragsmaske());
        entry.updateBoolean("vermittelbar", prod.isVermittelbar());
        entry.updateInt("versicherungsart", prod.getVersicherungsart());
        entry.updateInt("risikotyp", prod.getRisikotyp());
        entry.updateInt("waehrung", prod.getWaehrung());
        entry.updateDouble("versicherungsSumme", prod.getVersicherungsSumme());
        entry.updateDouble("bewertungsSumme", prod.getBewertungsSumme());
        entry.updateString("bedingungen", prod.getBedingungen());
        entry.updateDouble("selbstbeteiligung", prod.getSelbstbeteiligung());
        entry.updateDouble("nettopraemiePauschal", prod.getNettopraemiePauschal());
        entry.updateDouble("nettopraemieZusatz", prod.getNettopraemieZusatz());
        entry.updateDouble("nettopraemieGesamt", prod.getNettopraemieGesamt());
        entry.updateString("zusatzEinschluesse", prod.getZusatzEinschluesse().toString()); // TODO WEG
        entry.updateString("zusatzInfo", prod.getZusatzInfo());
        entry.updateString("comments", prod.getComments());
        entry.updateString("custom1", prod.getCustom1());
        entry.updateString("custom2", prod.getCustom2());
        entry.updateString("custom3", prod.getCustom3());
        entry.updateString("custom4", prod.getCustom4());
        entry.updateString("custom5", prod.getCustom5());
        entry.updateTimestamp("created", prod.getCreated());
        entry.updateTimestamp("modified", prod.getModified());
        entry.updateInt("status", prod.getStatus());

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
    public static void archiveFromProdukte(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM produkte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
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
    public static void deleteFromProdukte(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM produkte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
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
    public static void deleteEndgueltigFromProdukte(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM produkte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static ProduktObj[] loadProdukte(Connection con, int status) throws SQLException {
        String sql = "SELECT * FROM produkte WHERE status = ?";

        if (status == -1) {
            sql = "SELECT * FROM produkte";
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
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        ProduktObj[] produkte = new ProduktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            produkte[i] = getProduktEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return produkte;
    }

    /**
     * 
     * @param versid
     * @param con
     * @return
     * @throws SQLException
     */
    public static ProduktObj[] loadProdukte(Connection con, int versid, boolean vermittelbar, int status) throws SQLException {
        String sql = "SELECT * FROM produkte WHERE versichererId = ? AND vermittelbar = ? AND status = ?";;

        if (vermittelbar && status != -1) {
            sql = "SELECT * FROM produkte WHERE versichererId = ? AND vermittelbar = ? AND status = ?";
        } else if (!vermittelbar) {
            sql = "SELECT * FROM produkte WHERE versichererId = ? AND status = ?";
        } else if (status == -1) {
            sql = "SELECT * FROM produkte WHERE versichererId = ? AND vermittelbar = ?";
        }


        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, versid);

        if (vermittelbar && status != -1) {
            statement.setBoolean(2, true);
            statement.setInt(3, status);
        } else if (status != -1) {
            statement.setInt(2, status);
        } else if (vermittelbar) {
            statement.setBoolean(2, true);
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

        ProduktObj[] produkte = new ProduktObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            produkte[i] = getProduktEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return produkte;
    }

    public static ProduktObj getProdukt(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM produkte WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
        ProduktObj pr = getProduktEntry(entry);
        
        entry.close();
        statement.close();
        con.close();
        return pr;
    }

    /**
     * 
     * @param entry
     * @return
     * @throws SQLException
     */
    public static ProduktObj getProduktEntry(ResultSet entry) throws SQLException {
        ProduktObj pr = new ProduktObj();

        pr.setId(entry.getInt("id"));
        pr.setVersichererId(entry.getInt("versichererId"));
        pr.setSparteId(entry.getInt("sparteId"));
        pr.setCreatorId(entry.getInt("creatorId"));
        pr.setArt(entry.getInt("art"));
        pr.setTarif(entry.getString("tarif"));
        pr.setTarifBasis(entry.getString("tarifBasis"));
        pr.setBezeichnung(entry.getString("bezeichnung"));
        pr.setKuerzel(entry.getString("kuerzel"));

        pr.setVertragsmaske(entry.getInt("vertragsmaske"));
        pr.setVermittelbar(entry.getBoolean("vermittelbar"));
        pr.setVersicherungsart(entry.getInt("versicherungsart"));
        pr.setRisikotyp(entry.getInt("risikotyp"));

        pr.setWaehrung(entry.getInt("waehrung"));

        pr.setVersicherungsSumme(entry.getDouble("versicherungsSumme"));
        pr.setBewertungsSumme(entry.getDouble("bewertungsSumme"));

        pr.setBedingungen(entry.getString("bedingungen"));

        pr.setSelbstbeteiligung(entry.getDouble("selbstbeteiligung"));
        pr.setNettopraemiePauschal(entry.getDouble("nettopraemiePauschal"));
        pr.setNettopraemieZusatz(entry.getDouble("nettopraemieZusatz"));
        pr.setNettopraemieGesamt(entry.getDouble("nettopraemieGesamt"));

        //pr.setZusatzEinschluesse(ArrayStringTools.entry.getString("zusatzEinschluesse")); TODO remove

        pr.setZusatzInfo(entry.getString("zusatzInfo"));

        pr.setComments(entry.getString("comments"));
        pr.setCustom1(entry.getString("custom1"));
        pr.setCustom2(entry.getString("custom2"));
        pr.setCustom3(entry.getString("custom3"));
        pr.setCustom4(entry.getString("custom4"));
        pr.setCustom5(entry.getString("custom5"));

        pr.setCreated(entry.getTimestamp("created"));
        pr.setModified(entry.getTimestamp("modified"));
        pr.setStatus(entry.getInt("status"));
        return pr;
    }
}
