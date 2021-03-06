/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Vertraege.Tools;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class VertraegeSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param VertragObj vertrag
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoVertraege(Connection con, VertragObj vertrag) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO vertraege (parentId, versichererId, produktId, kundenKennung, beratungsprotokollId, mandantenId, "
                + "benutzerId, vertragsTyp, vertragGrp, policennr, policeDatum, wertungDatum, "
                + "courtage, zahlWeise, zahlArt, selbstbeteiligung, jahresNetto, steuer, "
                + "gebuehr, jahresBrutto, rabatt, zuschlag, antrag, faellig, "
                + "hauptfaellig, beginn, ablauf, maklerEingang, stornoDatum, storno, "
                + "stornoGrund, laufzeit, courtage_datum, comments, custom1, custom2, "
                + "custom3, custom4, custom5, created, modified, status, bankkontoId, zusatzadresseId, waehrungId"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, vertrag.getParentId());
        statement.setInt(2, vertrag.getVersichererId());
        statement.setInt(3, vertrag.getProduktId());
        statement.setString(4, vertrag.getKundenKennung());
        statement.setInt(5, vertrag.getBeratungsprotokollId());
        statement.setInt(6, vertrag.getMandantenId());
        statement.setInt(7, vertrag.getBenutzerId());
        statement.setInt(8, vertrag.getVertragsTyp());
        statement.setInt(9, vertrag.getVertragGrp());
        statement.setString(10, vertrag.getPolicennr());
        statement.setTimestamp(11, vertrag.getPoliceDatum());
        statement.setTimestamp(12, vertrag.getWertungDatum());
        statement.setBoolean(13, vertrag.isCourtage());
        statement.setInt(14, vertrag.getZahlWeise());
        statement.setString(15, vertrag.getZahlArt());
        statement.setInt(16, vertrag.getSelbstbeteiligung());
        statement.setDouble(17, vertrag.getJahresNetto());
        statement.setDouble(18, vertrag.getSteuer());
        statement.setDouble(19, vertrag.getGebuehr());
        statement.setDouble(20, vertrag.getJahresBrutto());
        statement.setDouble(21, vertrag.getRabatt());
        statement.setDouble(22, vertrag.getZuschlag());
        statement.setTimestamp(23, vertrag.getAntrag());
        statement.setTimestamp(24, vertrag.getFaellig());
        statement.setTimestamp(25, vertrag.getHauptfaellig());
        statement.setTimestamp(26, vertrag.getBeginn());
        statement.setTimestamp(27, vertrag.getAblauf());
        statement.setTimestamp(28, vertrag.getMaklerEingang());
        statement.setTimestamp(29, vertrag.getStornoDatum());
        statement.setTimestamp(30, vertrag.getStorno());
        statement.setString(31, vertrag.getStornoGrund());
        statement.setInt(32, vertrag.getLaufzeit());
        statement.setTimestamp(33, vertrag.getCourtage_datum());
        statement.setString(34, vertrag.getComments());
        statement.setString(35, vertrag.getCustom1());
        statement.setString(36, vertrag.getCustom2());
        statement.setString(37, vertrag.getCustom3());
        statement.setString(38, vertrag.getCustom4());
        statement.setString(39, vertrag.getCustom5());
        statement.setTimestamp(40, vertrag.getCreated());
        statement.setTimestamp(41, vertrag.getModified());
        statement.setInt(42, vertrag.getStatus());
        statement.setInt(43, vertrag.getBankkontoId());
        statement.setInt(44, vertrag.getZusatzadresseId());
        statement.setInt(45, vertrag.getWaehrungId());
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
     * @param VertragObj vtr
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateVertraege(Connection con, VertragObj vtr) throws SQLException {
        String sql = "SELECT * FROM vertraege WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, vtr.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            Log.logger.warn("Ein Vertrag mit der ID " + vtr.getId() + " wurde nicht gefunden.");
            return false;
        }

        entry.next();

        entry.updateInt("parentId", vtr.getParentId());
        entry.updateInt("versichererId", vtr.getVersichererId());
        entry.updateInt("produktId", vtr.getProduktId());

        entry.updateString("kundenKennung", vtr.getKundenKennung());
        entry.updateInt("bankkontoId", vtr.getBankkontoId());
        entry.updateInt("zusatzadresseId", vtr.getZusatzadresseId());        
        
        entry.updateInt("beratungsprotokollId", vtr.getBeratungsprotokollId());
        entry.updateInt("mandantenId", vtr.getMandantenId());
        entry.updateInt("benutzerId", vtr.getBenutzerId());
        entry.updateInt("vertragsTyp", vtr.getVertragsTyp());
        entry.updateInt("vertragGrp", vtr.getVertragGrp());

        entry.updateString("policennr", vtr.getPolicennr());
        entry.updateTimestamp("policeDatum", vtr.getPoliceDatum());

        entry.updateTimestamp("wertungDatum", vtr.getWertungDatum());
        entry.updateBoolean("courtage", vtr.isCourtage());
        entry.updateInt("zahlWeise", vtr.getZahlWeise());

        entry.updateString("zahlArt", vtr.getZahlArt());
        entry.updateInt("selbstbeteiligung", vtr.getSelbstbeteiligung());
        entry.updateDouble("jahresNetto", vtr.getJahresNetto());
        entry.updateDouble("steuer", vtr.getSteuer());
        entry.updateDouble("gebuehr", vtr.getGebuehr());
        entry.updateDouble("jahresBrutto", vtr.getJahresBrutto());
        entry.updateDouble("rabatt", vtr.getRabatt());
        entry.updateDouble("zuschlag", vtr.getZuschlag());
        entry.updateInt("waehrungId", vtr.getWaehrungId());

        entry.updateTimestamp("antrag", vtr.getAntrag());
        entry.updateTimestamp("faellig", vtr.getFaellig());

        entry.updateTimestamp("hauptfaellig", vtr.getHauptfaellig());
        entry.updateTimestamp("beginn", vtr.getBeginn());

        entry.updateTimestamp("ablauf", vtr.getAblauf());
        entry.updateTimestamp("maklerEingang", vtr.getMaklerEingang());
        entry.updateTimestamp("stornoDatum", vtr.getStornoDatum());

        entry.updateTimestamp("storno", vtr.getStorno());

        entry.updateString("stornoGrund", vtr.getStornoGrund());
        entry.updateInt("laufzeit", vtr.getLaufzeit());

        entry.updateTimestamp("courtage_datum", vtr.getCourtage_datum());
        entry.updateString("comments", vtr.getComments());
        entry.updateString("custom1", vtr.getCustom1());
        entry.updateString("custom2", vtr.getCustom2());

        entry.updateString("custom3", vtr.getCustom3());
        entry.updateString("custom4", vtr.getCustom4());
        entry.updateString("custom5", vtr.getCustom5());
        entry.updateTimestamp("created", vtr.getCreated());
        entry.updateTimestamp("modified", vtr.getModified());
        entry.updateInt("status", vtr.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * 
     * @param con
     * @param vtr
     * @throws SQLException 
     */
    public static void updateVertragToGRP(Connection con, VertragObj vtr) throws SQLException {
        String sql = "UPDATE vertraege SET vertragGrp = ? WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, vtr.getVertragGrp());
        statement.setInt(2, vtr.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    public static void deleteFromVertraege(Connection con, VertragObj vtr) throws SQLException {
        if(vtr == null)
            return;
        
        String sql = "UPDATE vertraege SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, vtr.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        vtr.setStatus(Status.DELETED);
    }
    
    public static void archiveFromVertraege(Connection con, VertragObj vtr) throws SQLException {
        if(vtr == null)
            return;
        
        String sql = "UPDATE vertraege SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, vtr.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        vtr.setStatus(Status.ARCHIVED);
    }

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromVertraege(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM vertraege WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException 
     */
    
    public static VertragObj getVertrag(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM vertraege WHERE id = ?";

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

        VertragObj vtr = getVertrag(entry);

        entry.close();
        statement.close();
        con.close();
        
        return vtr;
    }

    public static VertragObj getVertrag(ResultSet entry) throws SQLException {
        VertragObj vtr = new VertragObj();

        vtr.setId(entry.getInt("id"));
        vtr.setParentId(entry.getInt("parentId"));
        vtr.setVersichererId(entry.getInt("versichererId"));
        vtr.setProduktId(entry.getInt("produktId"));
        vtr.setKundenKennung(entry.getString("kundenKennung"));
        vtr.setBankkontoId(entry.getInt("bankkontoId"));
        vtr.setZusatzadresseId(entry.getInt("zusatzadresseId"));
        vtr.setBeratungsprotokollId(entry.getInt("beratungsprotokollId"));
        vtr.setMandantenId(entry.getInt("mandantenId"));
        vtr.setBenutzerId(entry.getInt("benutzerId"));
        vtr.setVertragsTyp(entry.getInt("vertragsTyp"));
        vtr.setVertragGrp(entry.getInt("vertragGrp"));
        vtr.setPolicennr(entry.getString("policennr"));
        vtr.setPoliceDatum(entry.getTimestamp("policeDatum"));
        vtr.setWertungDatum(entry.getTimestamp("wertungDatum"));
        vtr.setCourtage(entry.getBoolean("courtage"));
        vtr.setZahlWeise(entry.getInt("zahlWeise"));
        vtr.setZahlArt(entry.getString("zahlArt"));
        vtr.setSelbstbeteiligung(entry.getInt("selbstbeteiligung"));
        vtr.setJahresNetto(entry.getDouble("jahresNetto"));
        vtr.setSteuer(entry.getDouble("steuer"));
        vtr.setGebuehr(entry.getDouble("gebuehr"));
        vtr.setJahresBrutto(entry.getDouble("jahresBrutto"));
        vtr.setRabatt(entry.getDouble("rabatt"));
        vtr.setZuschlag(entry.getDouble("zuschlag"));
        vtr.setWaehrungId(entry.getInt("waehrungId"));
        vtr.setAntrag(entry.getTimestamp("antrag"));
        vtr.setFaellig(entry.getTimestamp("faellig"));
        vtr.setHauptfaellig(entry.getTimestamp("hauptfaellig"));
        vtr.setBeginn(entry.getTimestamp("beginn"));
        vtr.setAblauf(entry.getTimestamp("ablauf"));
        vtr.setMaklerEingang(entry.getTimestamp("maklerEingang"));
        vtr.setStornoDatum(entry.getTimestamp("stornoDatum"));
        vtr.setStorno(entry.getTimestamp("storno"));
        vtr.setStornoGrund(entry.getString("stornoGrund"));
        vtr.setLaufzeit(entry.getInt("laufzeit"));
        vtr.setCourtage_datum(entry.getTimestamp("courtage_datum"));
        vtr.setComments(entry.getString("comments"));
        vtr.setCustom1(entry.getString("custom1"));
        vtr.setCustom2(entry.getString("custom2"));
        vtr.setCustom3(entry.getString("custom3"));
        vtr.setCustom4(entry.getString("custom4"));
        vtr.setCustom5(entry.getString("custom5"));
        vtr.setCreated(entry.getTimestamp("created"));
        vtr.setModified(entry.getTimestamp("modified"));
        vtr.setStatus(entry.getInt("status"));
        
        return vtr;
    }
    
    /**
     * 
     * @param con
     * @param kdnr
     * @param status
     * @return
     * @throws SQLException 
     */
    public static VertragObj[] getKundenVertraege(Connection con, String kdnr, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);           
        }

        entry.close();
        statement.close();
        con.close();

        return vtr;
    }

    /**
     * 
     * @param con
     * @param bnId
     * @param status
     * @return
     * @throws SQLException 
     */
    public static VertragObj[] getBenutzerVertraege(Connection con, int bnId, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE benutzerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege WHERE benutzerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, bnId);

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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);  
        }


        entry.close();
        statement.close();
        con.close();

        return vtr;
    }

    
    public static VertragObj[] loadVertraege(Connection con, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege";
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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);  
        }


        entry.close();
        statement.close();
        con.close();

        return vtr;
    }
    
    /**
     * 
     * @param con
     * @param vid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static VertragObj[] getVersichererVertraege(Connection con, int vid, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE versichererId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege WHERE versichererId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, vid);

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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);
        }


        entry.close();
        statement.close();
        con.close();

        return vtr;
    }

    /**
     * 
     * @param con
     * @param bpid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static VertragObj[] getBeratungsprotokollVertraege(Connection con, int bpid, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE beratungsprotokollId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege WHERE beratungsprotokollId = ?";
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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);  
        }


        entry.close();
        statement.close();
        con.close();

        return vtr;
    }

    /**
     * 
     * @param con
     * @param pid
     * @param status
     * @return
     * @throws SQLException 
     */
    public static VertragObj[] getProduktVertraege(Connection con, int pid, int status) throws SQLException {
        VertragObj[] vtr = null;

        String sql = "SELECT * FROM vertraege WHERE produktId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM vertraege WHERE produktId = ?";
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

        vtr = new VertragObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();

            vtr[i] = getVertrag(entry);  
        }

        entry.close();
        statement.close();
        con.close();

        return vtr;
    }
    
//    public static String getVertragGrpName(Connection con, int id) throws SQLException {
//        String sql = "SELECT * FROM vertraege_grp WHERE id = ?";
//         PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
//                ResultSet.CONCUR_UPDATABLE);
//        statement.setInt(1, id);
//        
//        ResultSet entry = statement.executeQuery();
//
//        entry.last();
//        int rows = entry.getRow();
//        entry.beforeFirst();
//
//        if (rows == 0) {
//            entry.close();
//            statement.close();
//            con.close();
//            return null;
//        }
//        
//        entry.next();
//        
//        String vtrname = entry.getString("")
//        
//    }
    
}
