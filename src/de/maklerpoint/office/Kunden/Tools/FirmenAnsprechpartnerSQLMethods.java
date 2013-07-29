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

import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.System.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */
public class FirmenAnsprechpartnerSQLMethods {

    /**
     * 
     * @param con
     * @param fa
     * @return
     * @throws SQLException
     */
    public static int insertIntoFirmen_ansprechpartner(Connection con, FirmenAnsprechpartnerObj fa) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO firmen_ansprechpartner (kundenKennung, versichererId, creatorId, ordering, anrede, title, vorname, "
                + "nachname, geburtsdatum, abteilung, funktion, prioritaet, communication1, "
                + "communication2, communication3, communication4, communication5, communication1Type, communication2Type, "
                + "communication3Type, communication4Type, communication5Type, created, modified, status"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, fa.getKundenKennung());
        statement.setInt(2, fa.getVersichererId());
        statement.setInt(3, fa.getCreatorId());
        statement.setInt(4, fa.getOrdering());
        statement.setString(5, fa.getAnrede());
        statement.setString(6, fa.getTitle());
        statement.setString(7, fa.getVorname());
        statement.setString(8, fa.getNachname());
        statement.setString(9, fa.getGeburtdatum());
        statement.setString(10, fa.getAbteilung());
        statement.setString(11, fa.getFunktion());
        statement.setInt(12, fa.getPrioritaet());
        statement.setString(13, fa.getCommunication1());
        statement.setString(14, fa.getCommunication2());
        statement.setString(15, fa.getCommunication3());
        statement.setString(16, fa.getCommunication4());
        statement.setString(17, fa.getCommunication5());
        statement.setInt(18, fa.getCommunication1Type());
        statement.setInt(19, fa.getCommunication2Type());
        statement.setInt(20, fa.getCommunication3Type());
        statement.setInt(21, fa.getCommunication4Type());
        statement.setInt(22, fa.getCommunication5Type());
        statement.setTimestamp(23, fa.getCreated());
        statement.setTimestamp(24, fa.getModified());
        statement.setInt(25, fa.getStatus());
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
     * @param kundenKennung
     * @param creatorId
     * @param ordering
     * @param anrede
     * @param title
     * @param vorname
     * @param nachname
     * @param geburtdatum
     * @param abteilung
     * @param funktion
     * @param prioritaet
     * @param communication1
     * @param communication2
     * @param communication3
     * @param communication4
     * @param communication5
     * @param communication1Type
     * @param communication2Type
     * @param communication3Type
     * @param communication4Type
     * @param communication5Type
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated nicht komplett
     */
    public static boolean updateFirmen_ansprechpartner(Connection con, int keyId, String kundenKennung, int versid, int creatorId, int ordering, String anrede, String title,
            String vorname, String nachname, java.sql.Date geburtdatum, String abteilung, String funktion,
            int prioritaet, String communication1, String communication2, String communication3, String communication4,
            String communication5, int communication1Type, int communication2Type, int communication3Type, int communication4Type,
            int communication5Type, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        String sql = "SELECT * FROM firmen_ansprechpartner WHERE id = ?";
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

        if (kundenKennung != null) {
            entry.updateString("kundenKennung", kundenKennung);
        }
        entry.updateInt("versichererId", versid);
        entry.updateInt("creatorId", creatorId);
        entry.updateInt("ordering", ordering);
        if (anrede != null) {
            entry.updateString("anrede", anrede);
        }
        if (title != null) {
            entry.updateString("title", title);
        }
        if (vorname != null) {
            entry.updateString("vorname", vorname);
        }
        if (nachname != null) {
            entry.updateString("nachname", nachname);
        }
        if (geburtdatum != null) {
            entry.updateDate("geburtsdatum", geburtdatum);
        }
        if (abteilung != null) {
            entry.updateString("abteilung", abteilung);
        }
        if (funktion != null) {
            entry.updateString("funktion", funktion);
        }
        entry.updateInt("prioritaet", prioritaet);
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
        entry.updateInt("communication1Type", communication1Type);
        entry.updateInt("communication2Type", communication2Type);
        entry.updateInt("communication3Type", communication3Type);
        entry.updateInt("communication4Type", communication4Type);
        entry.updateInt("communication5Type", communication5Type);
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
     * @param fa
     * @return
     * @throws SQLException
     */
    public static boolean updateFirmen_ansprechpartner(Connection con, FirmenAnsprechpartnerObj fa) throws SQLException {
        String sql = "SELECT * FROM firmen_ansprechpartner WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, fa.getId());
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

        entry.updateString("kundenKennung", fa.getKundenKennung());
        entry.updateInt("versichererId", fa.getVersichererId());
        entry.updateInt("creatorId", fa.getCreatorId());
        entry.updateInt("ordering", fa.getOrdering());
        entry.updateString("anrede", fa.getAnrede());
        entry.updateString("title", fa.getTitle());
        entry.updateString("vorname", fa.getVorname());
        entry.updateString("nachname", fa.getNachname());
        entry.updateString("geburtsdatum", fa.getGeburtdatum());
        entry.updateString("abteilung", fa.getAbteilung());
        entry.updateString("funktion", fa.getFunktion());
        entry.updateInt("prioritaet", fa.getPrioritaet());
        entry.updateString("communication1", fa.getCommunication1());
        entry.updateString("communication2", fa.getCommunication2());
        entry.updateString("communication3", fa.getCommunication3());
        entry.updateString("communication4", fa.getCommunication4());
        entry.updateString("communication5", fa.getCommunication5());
        entry.updateInt("communication1Type", fa.getCommunication1Type());
        entry.updateInt("communication2Type", fa.getCommunication2Type());
        entry.updateInt("communication3Type", fa.getCommunication3Type());
        entry.updateInt("communication4Type", fa.getCommunication4Type());
        entry.updateInt("communication5Type", fa.getCommunication5Type());
        entry.updateTimestamp("created", fa.getCreated());
        entry.updateTimestamp("modified", fa.getModified());
        entry.updateInt("status", fa.getStatus());

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
    public static void deleteEndgueltigFromFirmen_ansprechpartner(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM firmen_ansprechpartner WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

   
    public static void deleteFromfirmen_ansprechpartner(Connection con, FirmenAnsprechpartnerObj ansp) throws SQLException {
        if(ansp == null)
            return;
        
        String sql = "UPDATE firmen_ansprechpartner SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, ansp.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        ansp.setStatus(Status.DELETED);
    }
    
    public static void archiveFromfirmen_ansprechpartner(Connection con, FirmenAnsprechpartnerObj ansp) throws SQLException {
        if(ansp == null)
            return;
        
        String sql = "UPDATE firmen_ansprechpartner SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, ansp.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        ansp.setStatus(Status.ARCHIVED);
    }

    /**
     * Für Geburtstagsliste
     * @param con
     * @param status
     * @return 
     */
    public static FirmenAnsprechpartnerObj[] loadAnsprechpartner(Connection con, int status) throws SQLException {
         String sql = "SELECT * FROM firmen_ansprechpartner WHERE status = ?";

        if(status == -1)
            sql = "SELECT * FROM firmen_ansprechpartner";                
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(status != -1)
            statement.setInt(1, status);

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

        FirmenAnsprechpartnerObj[] fa = new FirmenAnsprechpartnerObj[rows];


        for (int i = 0; i < rows; i++) {
            entry.next();
            fa[i] = getResultSetEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return fa;
    }
    
    /**
     *
     * @param con
     * @param kennung
     * @return
     * @throws SQLException
     */
    public static FirmenAnsprechpartnerObj[] loadAnsprechpartner(Connection con, String kennung, int status) throws SQLException {
        String sql = "SELECT * FROM firmen_ansprechpartner WHERE kundenKennung = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM firmen_ansprechpartner WHERE kundenKennung = ?";                
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);
        
        if(status != -1)
            statement.setInt(2, status);

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

        FirmenAnsprechpartnerObj[] fa = new FirmenAnsprechpartnerObj[rows];


        for (int i = 0; i < rows; i++) {
            entry.next();
            fa[i] = getResultSetEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return fa;
    }

    public static FirmenAnsprechpartnerObj[] loadAnsprechpartnerVers(Connection con, int vers, int status) throws SQLException {
        String sql = "SELECT * FROM firmen_ansprechpartner WHERE versichererId = ? AND status = ?";

        if(status == -1)
            sql = "SELECT * FROM firmen_ansprechpartner WHERE versichererId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, vers);
        
        if(status != -1)
            statement.setInt(2, status);

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

        FirmenAnsprechpartnerObj[] fa = new FirmenAnsprechpartnerObj[rows];


        for (int i = 0; i < rows; i++) {
            entry.next();
            fa[i] = getResultSetEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return fa;
    }

    public static FirmenAnsprechpartnerObj getAnsprechpartner(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM firmen_ansprechpartner WHERE id = ?";

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
        FirmenAnsprechpartnerObj fa = getResultSetEntry(entry);

        entry.close();
        statement.close();
        con.close();

        return fa;
    }
    
    
    public static FirmenAnsprechpartnerObj getResultSetEntry(ResultSet entry) throws SQLException {
        FirmenAnsprechpartnerObj fa = new FirmenAnsprechpartnerObj();
        
        fa.setId(entry.getInt("id"));
        fa.setKundenKennung(entry.getString("kundenKennung"));
        fa.setVersichererId(entry.getInt("versichererId"));
        fa.setCreatorId(entry.getInt("creatorId"));
        fa.setOrdering(entry.getInt("ordering"));
        fa.setAnrede(entry.getString("anrede"));
        fa.setTitle(entry.getString("title"));
        fa.setVorname(entry.getString("vorname"));
        fa.setNachname(entry.getString("nachname"));
        fa.setGeburtdatum(entry.getString("geburtsdatum"));
        fa.setAbteilung(entry.getString("abteilung"));
        fa.setFunktion(entry.getString("funktion"));
        fa.setPrioritaet(entry.getInt("prioritaet"));
        fa.setCommunication1(entry.getString("communication1"));
        fa.setCommunication2(entry.getString("communication2"));
        fa.setCommunication3(entry.getString("communication3"));
        fa.setCommunication4(entry.getString("communication4"));
        fa.setCommunication5(entry.getString("communication5"));
        fa.setCommunication1Type(entry.getInt("communication1Type"));
        fa.setCommunication2Type(entry.getInt("communication2Type"));
        fa.setCommunication3Type(entry.getInt("communication3Type"));
        fa.setCommunication4Type(entry.getInt("communication4Type"));
        fa.setCommunication5Type(entry.getInt("communication5Type"));
        fa.setCreated(entry.getTimestamp("created"));
        fa.setModified(entry.getTimestamp("modified"));
        fa.setStatus(entry.getInt("status"));
        
        return fa;
    }
}
