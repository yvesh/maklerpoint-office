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

import de.maklerpoint.office.Kunden.Kunden_BetreuungObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */
public class Kunden_BetreuungSQLMethods {

    public static int insertIntoKunden_betreuung(Connection con, Kunden_BetreuungObj kb) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO kunden_betreuung (kundenKennung, kundenTyp, prioritaet, loyalitaet, zielgruppe, ersterKontakt, "
                + "letzerKontakt, letzteRoutine, maklerVertrag, maklerBeginn, maklerEnde, analyse, "
                + "analyseLetzte, analyseNaechste, verwaltungskosten, newsletter, kundenzeitschrift, geburtstagskarte, "
                + "weihnachtskarte, osterkarte, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, kb.getKundenKennung());
        statement.setString(2, kb.getKundenTyp());
        statement.setInt(3, kb.getPrioritaet());
        statement.setString(4, kb.getLoyalitaet());
        statement.setString(5, kb.getZielgruppe());
        statement.setString(6, kb.getErsterKontakt());
        statement.setString(7, kb.getLetzerKontakt());
        statement.setString(8, kb.getLetzteRoutine());
        statement.setBoolean(9, kb.isMaklerVertrag());
        statement.setString(10, kb.getMaklerBeginn());
        statement.setString(11, kb.getMaklerEnde());
        statement.setBoolean(12, kb.isAnalyse());
        statement.setString(13, kb.getAnalyseLetzte());
        statement.setString(14, kb.getAnalyseNaechste());
        statement.setInt(15, kb.getVerwaltungskosten());
        statement.setBoolean(16, kb.isNewsletter());
        statement.setBoolean(17, kb.isKundenzeitschrift());
        statement.setBoolean(18, kb.isGeburtstagskarte());
        statement.setBoolean(19, kb.isWeihnachtskarte());
        statement.setBoolean(20, kb.isOsterkarte());
        statement.setTimestamp(21, kb.getCreated());
        statement.setTimestamp(22, kb.getModified());
        statement.setInt(23, kb.getStatus());
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
     * @param kb
     * @return
     * @throws SQLException
     */
    public static boolean updateKunden_betreuung(Connection con, Kunden_BetreuungObj kb) throws SQLException {
        String sql = "SELECT * FROM kunden_betreuung WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, kb.getId());
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

        entry.updateString("kundenKennung", kb.getKundenKennung());
        entry.updateString("kundenTyp", kb.getKundenTyp());
        entry.updateInt("prioritaet", kb.getPrioritaet());
        entry.updateString("loyalitaet", kb.getLoyalitaet());
        entry.updateString("zielgruppe", kb.getZielgruppe());
        entry.updateString("ersterKontakt", kb.getErsterKontakt());
        entry.updateString("letzerKontakt", kb.getLetzerKontakt());
        entry.updateString("letzteRoutine", kb.getLetzteRoutine());
        entry.updateBoolean("maklerVertrag", kb.isMaklerVertrag());
        entry.updateString("maklerBeginn", kb.getMaklerBeginn());
        entry.updateString("maklerEnde", kb.getMaklerEnde());
        entry.updateBoolean("analyse", kb.isAnalyse());
        entry.updateString("analyseLetzte", kb.getAnalyseLetzte());
        entry.updateString("analyseNaechste", kb.getAnalyseNaechste());
        entry.updateBoolean("erstinformationen", kb.isErstinformationen());
        entry.updateInt("verwaltungskosten", kb.getVerwaltungskosten());
        entry.updateBoolean("newsletter", kb.isNewsletter());
        entry.updateBoolean("kundenzeitschrift", kb.isKundenzeitschrift());
        entry.updateBoolean("geburtstagskarte", kb.isGeburtstagskarte());
        entry.updateBoolean("weihnachtskarte", kb.isWeihnachtskarte());
        entry.updateBoolean("osterkarte", kb.isOsterkarte());
        entry.updateString("dtrentenversicherungNr", kb.getDtrentenversicherungNr());
        entry.updateDouble("gkvBeitrag", kb.getGkvBeitrag());
        entry.updateDouble("kvBeitrag", kb.getKvBeitrag());
        entry.updateDouble("pflegeBeitrag", kb.getPflegeBeitrag());
        entry.updateString("krankenversicherung", kb.getKrankenversicherung());
        entry.updateInt("kvTyp", kb.getKvTyp());
        entry.updateString("kvNummer", kb.getKvNummer());
        entry.updateBoolean("gestorben", kb.isGestorben());
        entry.updateString("gestorbenDatum", kb.getGestorbenDatum());
        entry.updateTimestamp("created", kb.getCreated());
        entry.updateTimestamp("modified", kb.getModified());
        entry.updateInt("status", kb.getStatus());

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
    public static void deleteEndgueltigFromKunden_betreuung(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM kunden_betreuung WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param kennung
     * @return
     * @throws SQLException
     */
    public static Kunden_BetreuungObj getKunden_Betreuung(Connection con, String kennung) throws SQLException {
        String sql = "SELECT * FROM kunden_betreuung WHERE kundenKennung = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);

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

        Kunden_BetreuungObj btr = new Kunden_BetreuungObj();
        entry.next();

        btr.setId(entry.getInt("id"));
        btr.setKundenKennung(entry.getString("kundenKennung"));
        btr.setKundenTyp(entry.getString("kundenTyp"));
        btr.setPrioritaet(entry.getInt("prioritaet"));
        btr.setLoyalitaet(entry.getString("loyalitaet"));
        btr.setZielgruppe(entry.getString("zielgruppe"));

        btr.setErsterKontakt(entry.getString("ersterKontakt"));
        btr.setLetzerKontakt(entry.getString("letzerKontakt"));
        btr.setLetzteRoutine(entry.getString("letzteRoutine"));

        btr.setMaklerVertrag(entry.getBoolean("maklerVertrag"));

        btr.setMaklerBeginn(entry.getString("maklerBeginn"));
        btr.setMaklerEnde(entry.getString("maklerEnde"));

        btr.setAnalyse(entry.getBoolean("analyse"));
        btr.setAnalyseLetzte(entry.getString("analyseLetzte"));
        btr.setAnalyseNaechste(entry.getString("analyseNaechste"));
        
        btr.setErstinformationen(entry.getBoolean("erstinformationen"));

        btr.setVerwaltungskosten(entry.getInt("verwaltungskosten"));
        btr.setNewsletter(entry.getBoolean("newsletter"));
        btr.setKundenzeitschrift(entry.getBoolean("kundenzeitschrift"));
        btr.setGeburtstagskarte(entry.getBoolean("geburtstagskarte"));
        btr.setWeihnachtskarte(entry.getBoolean("weihnachtskarte"));
        btr.setOsterkarte(entry.getBoolean("osterkarte"));

        btr.setDtrentenversicherungNr(entry.getString("dtrentenversicherungNr"));
        btr.setGkvBeitrag(entry.getDouble("gkvBeitrag"));
        btr.setKvBeitrag(entry.getDouble("kvBeitrag"));
        btr.setPflegeBeitrag(entry.getDouble("pflegeBeitrag"));
        btr.setKrankenversicherung(entry.getString("krankenversicherung"));
        btr.setKvTyp(entry.getInt("kvTyp"));
        btr.setKvNummer(entry.getString("kvNummer"));
        btr.setGestorben(entry.getBoolean("gestorben"));
        btr.setGestorbenDatum(entry.getString("gestorbenDatum"));

        btr.setCreated(entry.getTimestamp("created"));
        btr.setModified(entry.getTimestamp("modified"));
        btr.setStatus(entry.getInt("status"));


        entry.close();
        statement.close();
        con.close();
        return btr;
    }
}
