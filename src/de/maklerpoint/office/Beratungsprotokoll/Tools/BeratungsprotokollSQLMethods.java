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

package de.maklerpoint.office.Beratungsprotokoll.Tools;

import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
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
public class BeratungsprotokollSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param kundenId
     * @param benutzerId
     * @param versicherungsId
     * @param produktId
     * @param beratungsVerzicht
     * @param dokumentationsVerzicht
     * @param wiederVorlage
     * @param kundenWuensche
     * @param kundenBedarf
     * @param rat
     * @param entscheidung
     * @param marktuntersuchung
     * @param versicherungsSparte
     * @param versicherungsGesellschaft
     * @param beratungsVerzichtArt
     * @param kundenAnschreiben
     * @param dokumente
     * @param checkKundenAnschreiben
     * @param checkBeratungsDokumentation
     * @param checkBeratungsDokuVerzicht
     * @param checkInformationsPflichten
     * @param checkDruckstuecke
     * @param created
     * @param modified
     * @return id (database row id [id])
     * @throws SQLException
     * @deprecated
     * kundenBemerkungen, vertragsId fehlt!!
     */

    public static int insertIntoberatungsprotokolle(Connection con, String kundenKennung, int benutzerId, int versicherungsId, int produktId, boolean beratungsVerzicht,
                boolean dokumentationsVerzicht, boolean wiederVorlage, String kundenWuensche, String kundenBedarf, String rat,
                String entscheidung, String marktuntersuchung, String versicherungsSparte, String versicherungsGesellschaft, String beratungsVerzichtArt,
                String kundenAnschreiben, String[] dokumente, boolean checkKundenAnschreiben, boolean checkBeratungsDokumentation, boolean checkBeratungsDokuVerzicht,
                boolean checkInformationsPflichten, boolean checkDruckstuecke, java.sql.Timestamp created, java.sql.Timestamp modified) throws SQLException {
            int generatedId = -1;
            String sql = "INSERT INTO beratungsprotokolle (kundenKennung, benutzerId, versicherungsId, produktId, beratungsVerzicht, "
                    + "dokumentationsVerzicht, wiederVorlage, kundenWuensche, kundenBedarf, rat, entscheidung, marktuntersuchung, versicherungsSparte, versicherungsGesellschaft, beratungsVerzichtArt, kundenAnschreiben, dokumente, checkKundenAnschreiben, checkBeratungsDokumentation, checkBeratungsDokuVerzicht, checkInformationsPflichten, checkDruckstuecke, created, modified)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, kundenKennung);
            statement.setInt(2, benutzerId);
            statement.setInt(3, versicherungsId);
            statement.setInt(4, produktId);
            statement.setBoolean(5, beratungsVerzicht);
            statement.setBoolean(6, dokumentationsVerzicht);
            statement.setBoolean(7, wiederVorlage);
            statement.setString(8, kundenWuensche);
            statement.setString(9, kundenBedarf);
            statement.setString(10, rat);
            statement.setString(11, entscheidung);
            statement.setString(12, marktuntersuchung);
            statement.setString(13, versicherungsSparte);
            statement.setString(14, versicherungsGesellschaft);
            statement.setString(15, beratungsVerzichtArt);
            statement.setString(16, kundenAnschreiben);
            statement.setString(17, ArrayStringTools.arrayToString(dokumente, ","));
            statement.setBoolean(18, checkKundenAnschreiben);
            statement.setBoolean(19, checkBeratungsDokumentation);
            statement.setBoolean(20, checkBeratungsDokuVerzicht);
            statement.setBoolean(21, checkInformationsPflichten);
            statement.setBoolean(22, checkDruckstuecke);
            statement.setTimestamp(23, created);
            statement.setTimestamp(24, modified);
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
     * 
     * @param con
     * @param protokoll
     * @return
     * @throws SQLException
     */

    public static int insertIntoberatungsprotokolle(Connection con, BeratungsprotokollObj protokoll) throws SQLException {
            int generatedId = -1;
            String sql = "INSERT INTO beratungsprotokolle (kundenKennung, benutzerId, versicherungsId, produktId, beratungsVerzicht, "
                    + "dokumentationsVerzicht, wiederVorlage, kundenWuensche, kundenBedarf, rat, entscheidung, marktuntersuchung, "
                    + "versicherungsSparte, versicherungsGesellschaft, beratungsVerzichtArt, kundenAnschreiben, dokumente, "
                    + "checkKundenAnschreiben, checkBeratungsDokumentation, checkBeratungsDokuVerzicht, "
                    + "checkInformationsPflichten, checkDruckstuecke, created, modified, kundenBemerkungen, status, vertragId)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, protokoll.getKundenKennung());
            statement.setInt(2, protokoll.getBenutzerId());
            statement.setInt(3, protokoll.getVersicherungsId());
            statement.setInt(4, protokoll.getProduktId());
            statement.setBoolean(5, protokoll.isBeratungsVerzicht());
            statement.setBoolean(6, protokoll.isDokumentationsVerzicht());
            statement.setBoolean(7, protokoll.isWiederVorlage());
            statement.setString(8, protokoll.getKundenWuensche());
            statement.setString(9, protokoll.getKundenBedarf());
            statement.setString(10, protokoll.getRat());
            statement.setString(11, protokoll.getEntscheidung());
            statement.setString(12, protokoll.getMarktuntersuchung());
            statement.setString(13, protokoll.getVersicherungsSparte());
            statement.setString(14, protokoll.getVersicherungsGesellschaft());
            statement.setString(15, protokoll.getBeratungsVerzichtArt());
            statement.setString(16, protokoll.getKundenAnschreiben());
            statement.setString(17, ArrayStringTools.arrayToString(protokoll.getDokumente(), ","));
            statement.setBoolean(18, protokoll.isCheckKundenAnschreiben());
            statement.setBoolean(19, protokoll.isCheckBeratungsDokumentation());
            statement.setBoolean(20, protokoll.isBeratungsVerzicht());
            statement.setBoolean(21, protokoll.isCheckInformationsPflichten());
            statement.setBoolean(22, protokoll.isCheckDruckstuecke());
            statement.setTimestamp(23, protokoll.getCreated());
            statement.setTimestamp(24, protokoll.getModified());
            statement.setString(25, protokoll.getKundenBemerkungen());
            statement.setInt(26, protokoll.getStatus());
            statement.setInt(27, protokoll.getVertragId());           
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
     * @param kundenId
     * @param benutzerId
     * @param versicherungsId
     * @param produktId
     * @param beratungsVerzicht
     * @param dokumentationsVerzicht
     * @param wiederVorlage
     * @param kundenWuensche
     * @param kundenBedarf
     * @param rat
     * @param entscheidung
     * @param marktuntersuchung
     * @param versicherungsSparte
     * @param versicherungsGesellschaft
     * @param beratungsVerzichtArt
     * @param kundenAnschreiben
     * @param dokumente
     * @param checkKundenAnschreiben
     * @param checkBeratungsDokumentation
     * @param checkBeratungsDokuVerzicht
     * @param checkInformationsPflichten
     * @param checkDruckstuecke
     * @param created
     * @param modified
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated Fehlen
     */

    public static boolean updateberatungsprotokolle2(Connection con, int keyId, int kundenId, int benutzerId, int versicherungsId, int produktId, boolean beratungsVerzicht,
            boolean dokumentationsVerzicht, boolean wiederVorlage, String kundenWuensche, String kundenBedarf, String rat,
            String entscheidung, String marktuntersuchung, String versicherungsSparte, String versicherungsGesellschaft, String beratungsVerzichtArt,
            String kundenAnschreiben, String[] dokumente, boolean checkKundenAnschreiben, boolean checkBeratungsDokumentation, boolean checkBeratungsDokuVerzicht,
            boolean checkInformationsPflichten, boolean checkDruckstuecke, java.sql.Timestamp created, java.sql.Timestamp modified) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        entry.updateInt("kundenId", kundenId);
        entry.updateInt("benutzerId", benutzerId);
        entry.updateInt("versicherungsId", versicherungsId);
        entry.updateInt("produktId", produktId);
        entry.updateBoolean("beratungsVerzicht", beratungsVerzicht);
        entry.updateBoolean("dokumentationsVerzicht", dokumentationsVerzicht);
        entry.updateBoolean("wiederVorlage", wiederVorlage);
        if(kundenWuensche != null)
            entry.updateString("kundenWuensche", kundenWuensche);
        if(kundenBedarf != null)
            entry.updateString("kundenBedarf", kundenBedarf);
        if(rat != null)
            entry.updateString("rat", rat);
        if(entscheidung != null)
            entry.updateString("entscheidung", entscheidung);
        if(marktuntersuchung != null)
            entry.updateString("marktuntersuchung", marktuntersuchung);
        if(versicherungsSparte != null)
            entry.updateString("versicherungsSparte", versicherungsSparte);
        if(versicherungsGesellschaft != null)
            entry.updateString("versicherungsGesellschaft", versicherungsGesellschaft);
        if(beratungsVerzichtArt != null)
            entry.updateString("beratungsVerzichtArt", beratungsVerzichtArt);
        if(kundenAnschreiben != null)
            entry.updateString("kundenAnschreiben", kundenAnschreiben);
        if(dokumente != null)
            entry.updateString("dokumente", ArrayStringTools.arrayToString(dokumente, ","));
        entry.updateBoolean("checkKundenAnschreiben", checkKundenAnschreiben);
        entry.updateBoolean("checkBeratungsDokumentation", checkBeratungsDokumentation);
        entry.updateBoolean("checkBeratungsDokuVerzicht", checkBeratungsDokuVerzicht);
        entry.updateBoolean("checkInformationsPflichten", checkInformationsPflichten);
        entry.updateBoolean("checkDruckstuecke", checkDruckstuecke);
        if(created != null)
            entry.updateTimestamp("created", created);
        if(modified != null)
            entry.updateTimestamp("modified", modified);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     *
     */

     public static boolean updateberatungsprotokolle(Connection con, BeratungsprotokollObj protokoll) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, protokoll.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0)
            return false;

        entry.next();

        entry.updateString("kundenId", protokoll.getKundenKennung());
        entry.updateInt("benutzerId", protokoll.getBenutzerId());
        entry.updateInt("versicherungsId", protokoll.getVersicherungsId());
        entry.updateInt("produktId", protokoll.getProduktId());
        entry.updateInt("vertragId", protokoll.getVertragId());
        entry.updateBoolean("beratungsVerzicht", protokoll.isBeratungsVerzicht());
        entry.updateBoolean("dokumentationsVerzicht", protokoll.isDokumentationsVerzicht());
        entry.updateBoolean("wiederVorlage", protokoll.isWiederVorlage());
        entry.updateString("kundenWuensche", protokoll.getKundenWuensche());
        entry.updateString("kundenBedarf", protokoll.getKundenBedarf());
        entry.updateString("rat", protokoll.getRat());
        entry.updateString("entscheidung", protokoll.getEntscheidung());
        entry.updateString("marktuntersuchung", protokoll.getMarktuntersuchung());
        entry.updateString("kundenBemerkungen", protokoll.getKundenBemerkungen());
        entry.updateString("versicherungsSparte", protokoll.getVersicherungsSparte());
        entry.updateString("versicherungsGesellschaft", protokoll.getVersicherungsGesellschaft());
        entry.updateString("beratungsVerzichtArt", protokoll.getBeratungsVerzichtArt());
        entry.updateString("kundenAnschreiben", protokoll.getKundenAnschreiben());
        entry.updateString("dokumente", ArrayStringTools.arrayToString(protokoll.getDokumente(), ","));
        entry.updateBoolean("checkKundenAnschreiben", protokoll.isCheckKundenAnschreiben());
        entry.updateBoolean("checkBeratungsDokumentation", protokoll.isCheckBeratungsDokumentation());
        entry.updateBoolean("checkBeratungsDokuVerzicht", protokoll.isCheckBeratungsDokuVerzicht());
        entry.updateBoolean("checkInformationsPflichten", protokoll.isCheckInformationsPflichten());
        entry.updateBoolean("checkDruckstuecke", protokoll.isCheckDruckstuecke());
        entry.updateTimestamp("created", protokoll.getCreated());
        entry.updateTimestamp("modified", protokoll.getModified());
        entry.updateInt("status", protokoll.getStatus());

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

    public static void deleteEndgueltigFromberatungsprotokolle(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM beratungsprotokolle WHERE id = ?";
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

    public static void deleteFromberatungsprotokolle(Connection con, int keyId) throws SQLException {
        String sql = "SELECT *  FROM beratungsprotokolle WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0) {
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
    
    public static void archiveFromBeratungsprotokolle(Connection con, BeratungsprotokollObj bk) throws SQLException {
        if(bk == null)
            return;
        
        String sql = "UPDATE beratungsprotokolle SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, bk.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        bk.setStatus(Status.DELETED);
    }


    public static BeratungsprotokollObj[] loadBeratungsprotokolle(Connection con, String kennung, int status) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE kundenKennung = ? AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM beratungsprotokolle WHERE kundenKennung = ?";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);
        
        if(status != -1)
            statement.setInt(2, status);

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

        BeratungsprotokollObj[] bp = new BeratungsprotokollObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            bp[i] = new BeratungsprotokollObj();

            bp[i].setId(entry.getInt("id"));
            bp[i].setKundenKennung(entry.getString("kundenKennung"));
            bp[i].setBenutzerId(entry.getInt("benutzerId"));
            bp[i].setVersicherungsId(entry.getInt("versicherungsId"));
            bp[i].setProduktId(entry.getInt("produktId"));
            bp[i].setVertragId(entry.getInt("vertragId"));
            bp[i].setBeratungsVerzicht(entry.getBoolean("beratungsVerzicht"));
            bp[i].setDokumentationsVerzicht(entry.getBoolean("dokumentationsVerzicht"));
            bp[i].setWiederVorlage(entry.getBoolean("wiederVorlage"));
            bp[i].setKundenWuensche(entry.getString("kundenWuensche"));
            bp[i].setKundenBedarf(entry.getString("kundenBedarf"));
            bp[i].setRat(entry.getString("rat"));
            bp[i].setEntscheidung(entry.getString("entscheidung"));
            bp[i].setMarktuntersuchung(entry.getString("marktuntersuchung"));
            bp[i].setKundenBemerkungen(entry.getString("kundenBemerkungen"));
            bp[i].setVersicherungsSparte(entry.getString("versicherungsSparte"));
            bp[i].setVersicherungsGesellschaft(entry.getString("versicherungsGesellschaft"));
            bp[i].setBeratungsVerzichtArt(entry.getString("beratungsVerzichtArt"));
            bp[i].setKundenAnschreiben(entry.getString("kundenAnschreiben"));
            bp[i].setDokumente(ArrayStringTools.stringToArray(entry.getString("dokumente"), ","));
            bp[i].setCheckKundenAnschreiben(entry.getBoolean("checkKundenAnschreiben"));
            bp[i].setCheckBeratungsDokumentation(entry.getBoolean("checkBeratungsDokumentation"));
            bp[i].setCheckBeratungsDokuVerzicht(entry.getBoolean("checkBeratungsDokuVerzicht"));
            bp[i].setCheckInformationsPflichten(entry.getBoolean("checkInformationsPflichten"));
            bp[i].setCheckDruckstuecke(entry.getBoolean("checkDruckstuecke"));
            bp[i].setCreated(entry.getTimestamp("created"));
            bp[i].setModified(entry.getTimestamp("modified"));
            bp[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bp;
    }

    /**
     * 
     * @param con
     * @param versid
     * @return
     * @throws SQLException
     */

    public static BeratungsprotokollObj[] loadBeratungsprotokolleVersicherer(Connection con, int versid, int status) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE versicherungsId = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM beratungsprotokolle WHERE versicherungsId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, versid);

        if(status != -1)
            statement.setInt(2, status);
        
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

        BeratungsprotokollObj[] bp = new BeratungsprotokollObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            bp[i] = new BeratungsprotokollObj();

            bp[i].setId(entry.getInt("id"));
            bp[i].setKundenKennung(entry.getString("kundenKennung"));
            bp[i].setBenutzerId(entry.getInt("benutzerId"));
            bp[i].setVersicherungsId(entry.getInt("versicherungsId"));
            bp[i].setProduktId(entry.getInt("produktId"));
            bp[i].setVertragId(entry.getInt("vertragId"));
            bp[i].setBeratungsVerzicht(entry.getBoolean("beratungsVerzicht"));
            bp[i].setDokumentationsVerzicht(entry.getBoolean("dokumentationsVerzicht"));
            bp[i].setWiederVorlage(entry.getBoolean("wiederVorlage"));
            bp[i].setKundenWuensche(entry.getString("kundenWuensche"));
            bp[i].setKundenBedarf(entry.getString("kundenBedarf"));
            bp[i].setRat(entry.getString("rat"));
            bp[i].setEntscheidung(entry.getString("entscheidung"));
            bp[i].setMarktuntersuchung(entry.getString("marktuntersuchung"));
            bp[i].setKundenBemerkungen(entry.getString("kundenBemerkungen"));
            bp[i].setVersicherungsSparte(entry.getString("versicherungsSparte"));
            bp[i].setVersicherungsGesellschaft(entry.getString("versicherungsGesellschaft"));
            bp[i].setBeratungsVerzichtArt(entry.getString("beratungsVerzichtArt"));
            bp[i].setKundenAnschreiben(entry.getString("kundenAnschreiben"));
            bp[i].setDokumente(ArrayStringTools.stringToArray(entry.getString("dokumente"), ","));
            bp[i].setCheckKundenAnschreiben(entry.getBoolean("checkKundenAnschreiben"));
            bp[i].setCheckBeratungsDokumentation(entry.getBoolean("checkBeratungsDokumentation"));
            bp[i].setCheckBeratungsDokuVerzicht(entry.getBoolean("checkBeratungsDokuVerzicht"));
            bp[i].setCheckInformationsPflichten(entry.getBoolean("checkInformationsPflichten"));
            bp[i].setCheckDruckstuecke(entry.getBoolean("checkDruckstuecke"));
            bp[i].setCreated(entry.getTimestamp("created"));
            bp[i].setModified(entry.getTimestamp("modified"));
            bp[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bp;
    }

    
    /**
     * 
     * @param con
     * @param benid
     * @return
     * @throws SQLException 
     */

     public static BeratungsprotokollObj[] loadBeratungsprotokolleBenutzer(Connection con, int benid, int status) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE benutzerId = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM beratungsprotokolle WHERE benutzerId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, benid);
        
        if(status != -1)
            statement.setInt(2, status);

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

        BeratungsprotokollObj[] bp = new BeratungsprotokollObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            bp[i] = new BeratungsprotokollObj();

            bp[i].setId(entry.getInt("id"));
            bp[i].setKundenKennung(entry.getString("kundenKennung"));
            bp[i].setBenutzerId(entry.getInt("benutzerId"));
            bp[i].setVersicherungsId(entry.getInt("versicherungsId"));
            bp[i].setProduktId(entry.getInt("produktId"));
            bp[i].setVertragId(entry.getInt("vertragId"));
            bp[i].setBeratungsVerzicht(entry.getBoolean("beratungsVerzicht"));
            bp[i].setDokumentationsVerzicht(entry.getBoolean("dokumentationsVerzicht"));
            bp[i].setWiederVorlage(entry.getBoolean("wiederVorlage"));
            bp[i].setKundenWuensche(entry.getString("kundenWuensche"));
            bp[i].setKundenBedarf(entry.getString("kundenBedarf"));
            bp[i].setRat(entry.getString("rat"));
            bp[i].setEntscheidung(entry.getString("entscheidung"));
            bp[i].setMarktuntersuchung(entry.getString("marktuntersuchung"));
            bp[i].setKundenBemerkungen(entry.getString("kundenBemerkungen"));
            bp[i].setVersicherungsSparte(entry.getString("versicherungsSparte"));
            bp[i].setVersicherungsGesellschaft(entry.getString("versicherungsGesellschaft"));
            bp[i].setBeratungsVerzichtArt(entry.getString("beratungsVerzichtArt"));
            bp[i].setKundenAnschreiben(entry.getString("kundenAnschreiben"));
            bp[i].setDokumente(ArrayStringTools.stringToArray(entry.getString("dokumente"), ","));
            bp[i].setCheckKundenAnschreiben(entry.getBoolean("checkKundenAnschreiben"));
            bp[i].setCheckBeratungsDokumentation(entry.getBoolean("checkBeratungsDokumentation"));
            bp[i].setCheckBeratungsDokuVerzicht(entry.getBoolean("checkBeratungsDokuVerzicht"));
            bp[i].setCheckInformationsPflichten(entry.getBoolean("checkInformationsPflichten"));
            bp[i].setCheckDruckstuecke(entry.getBoolean("checkDruckstuecke"));
            bp[i].setCreated(entry.getTimestamp("created"));
            bp[i].setModified(entry.getTimestamp("modified"));
            bp[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bp;
     }
     
     /**
      * 
      * @param con
      * @param pid
      * @param status
      * @return
      * @throws SQLException 
      */
     
     public static BeratungsprotokollObj[] loadBeratungsprotokolleProdukt(Connection con, int pid, int status) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE produktId = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM beratungsprotokolle WHERE produktId = ?";
                
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, pid);
        
        if(status != -1)
            statement.setInt(2, status);

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

        BeratungsprotokollObj[] bp = new BeratungsprotokollObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            bp[i] = new BeratungsprotokollObj();

            bp[i].setId(entry.getInt("id"));
            bp[i].setKundenKennung(entry.getString("kundenKennung"));
            bp[i].setBenutzerId(entry.getInt("benutzerId"));
            bp[i].setVersicherungsId(entry.getInt("versicherungsId"));
            bp[i].setProduktId(entry.getInt("produktId"));
            bp[i].setVertragId(entry.getInt("vertragId"));
            bp[i].setBeratungsVerzicht(entry.getBoolean("beratungsVerzicht"));
            bp[i].setDokumentationsVerzicht(entry.getBoolean("dokumentationsVerzicht"));
            bp[i].setWiederVorlage(entry.getBoolean("wiederVorlage"));
            bp[i].setKundenWuensche(entry.getString("kundenWuensche"));
            bp[i].setKundenBedarf(entry.getString("kundenBedarf"));
            bp[i].setRat(entry.getString("rat"));
            bp[i].setEntscheidung(entry.getString("entscheidung"));
            bp[i].setMarktuntersuchung(entry.getString("marktuntersuchung"));
            bp[i].setKundenBemerkungen(entry.getString("kundenBemerkungen"));
            bp[i].setVersicherungsSparte(entry.getString("versicherungsSparte"));
            bp[i].setVersicherungsGesellschaft(entry.getString("versicherungsGesellschaft"));
            bp[i].setBeratungsVerzichtArt(entry.getString("beratungsVerzichtArt"));
            bp[i].setKundenAnschreiben(entry.getString("kundenAnschreiben"));
            bp[i].setDokumente(ArrayStringTools.stringToArray(entry.getString("dokumente"), ","));
            bp[i].setCheckKundenAnschreiben(entry.getBoolean("checkKundenAnschreiben"));
            bp[i].setCheckBeratungsDokumentation(entry.getBoolean("checkBeratungsDokumentation"));
            bp[i].setCheckBeratungsDokuVerzicht(entry.getBoolean("checkBeratungsDokuVerzicht"));
            bp[i].setCheckInformationsPflichten(entry.getBoolean("checkInformationsPflichten"));
            bp[i].setCheckDruckstuecke(entry.getBoolean("checkDruckstuecke"));
            bp[i].setCreated(entry.getTimestamp("created"));
            bp[i].setModified(entry.getTimestamp("modified"));
            bp[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bp;
    }
    
    /**
      * 
      * @param con
      * @param vid
      * @param status
      * @return
      * @throws SQLException 
      */
     
     public static BeratungsprotokollObj[] loadBeratungsprotokolleVertrag(Connection con, 
                                        int vid, int status) throws SQLException {
        String sql = "SELECT * FROM beratungsprotokolle WHERE vertragId = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM beratungsprotokolle WHERE vertragId = ?";
                
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, vid);
        
        if(status != -1)
            statement.setInt(2, status);

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

        BeratungsprotokollObj[] bp = new BeratungsprotokollObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            bp[i] = new BeratungsprotokollObj();

            bp[i].setId(entry.getInt("id"));
            bp[i].setKundenKennung(entry.getString("kundenKennung"));
            bp[i].setBenutzerId(entry.getInt("benutzerId"));
            bp[i].setVersicherungsId(entry.getInt("versicherungsId"));
            bp[i].setProduktId(entry.getInt("produktId"));
            bp[i].setVertragId(entry.getInt("vertragId"));
            bp[i].setBeratungsVerzicht(entry.getBoolean("beratungsVerzicht"));
            bp[i].setDokumentationsVerzicht(entry.getBoolean("dokumentationsVerzicht"));
            bp[i].setWiederVorlage(entry.getBoolean("wiederVorlage"));
            bp[i].setKundenWuensche(entry.getString("kundenWuensche"));
            bp[i].setKundenBedarf(entry.getString("kundenBedarf"));
            bp[i].setRat(entry.getString("rat"));
            bp[i].setEntscheidung(entry.getString("entscheidung"));
            bp[i].setMarktuntersuchung(entry.getString("marktuntersuchung"));
            bp[i].setKundenBemerkungen(entry.getString("kundenBemerkungen"));
            bp[i].setVersicherungsSparte(entry.getString("versicherungsSparte"));
            bp[i].setVersicherungsGesellschaft(entry.getString("versicherungsGesellschaft"));
            bp[i].setBeratungsVerzichtArt(entry.getString("beratungsVerzichtArt"));
            bp[i].setKundenAnschreiben(entry.getString("kundenAnschreiben"));
            bp[i].setDokumente(ArrayStringTools.stringToArray(entry.getString("dokumente"), ","));
            bp[i].setCheckKundenAnschreiben(entry.getBoolean("checkKundenAnschreiben"));
            bp[i].setCheckBeratungsDokumentation(entry.getBoolean("checkBeratungsDokumentation"));
            bp[i].setCheckBeratungsDokuVerzicht(entry.getBoolean("checkBeratungsDokuVerzicht"));
            bp[i].setCheckInformationsPflichten(entry.getBoolean("checkInformationsPflichten"));
            bp[i].setCheckDruckstuecke(entry.getBoolean("checkDruckstuecke"));
            bp[i].setCreated(entry.getTimestamp("created"));
            bp[i].setModified(entry.getTimestamp("modified"));
            bp[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return bp;
    }     
}
