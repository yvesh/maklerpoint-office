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
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yves
 */
public class VersichererSQLMethods {


    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param parentId
     * @param parentName
     * @param vuNummer
     * @param name
     * @param nameZusatz
     * @param nameZusatz2
     * @param kuerzel
     * @param gesellschaftsNr
     * @param strasse
     * @param plz
     * @param stadt
     * @param bundesLand
     * @param land
     * @param postfach
     * @param postfachName
     * @param postfachPlz
     * @param postfachOrt
     * @param vermittelbar
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

    public static int insertIntoversicherer(Connection con, int parentId, String parentName, String vuNummer, String name, String nameZusatz,
            String nameZusatz2, String kuerzel, String gesellschaftsNr, String strasse, String plz,
            String stadt, String bundesLand, String land, boolean postfach, String postfachName,
            String postfachPlz, String postfachOrt, boolean vermittelbar, String communication1, String communication2,
            String communication3, String communication4, String communication5, String communication6, int communication1Type,
            int communication2Type, int communication3Type, int communication4Type, int communication5Type, int communication6Type,
            String comments, String custom1, String custom2, String custom3, String custom4,
            String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO versicherer (parentId, parentName, vuNummer, name, nameZusatz, nameZusatz2, kuerzel, gesellschaftsNr, strasse, plz, stadt, bundesLand, land, postfach, postfachName, postfachPlz, postfachOrt, vermittelbar, communication1, communication2, communication3, communication4, communication5, communication6, communication1Type, communication2Type, communication3Type, communication4Type, communication5Type, communication6Type, comments, custom1, custom2, custom3, custom4, custom5, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, parentId);
        statement.setString(2, parentName);
        statement.setString(3, vuNummer);
        statement.setString(4, name);
        statement.setString(5, nameZusatz);
        statement.setString(6, nameZusatz2);
        statement.setString(7, kuerzel);
        statement.setString(8, gesellschaftsNr);
        statement.setString(9, strasse);
        statement.setString(10, plz);
        statement.setString(11, stadt);
        statement.setString(12, bundesLand);
        statement.setString(13, land);
        statement.setBoolean(14, postfach);
        statement.setString(15, postfachName);
        statement.setString(16, postfachPlz);
        statement.setString(17, postfachOrt);
        statement.setBoolean(18, vermittelbar);
        statement.setString(19, communication1);
        statement.setString(20, communication2);
        statement.setString(21, communication3);
        statement.setString(22, communication4);
        statement.setString(23, communication5);
        statement.setString(24, communication6);
        statement.setInt(25, communication1Type);
        statement.setInt(26, communication2Type);
        statement.setInt(27, communication3Type);
        statement.setInt(28, communication4Type);
        statement.setInt(29, communication5Type);
        statement.setInt(30, communication6Type);
        statement.setString(31, comments);
        statement.setString(32, custom1);
        statement.setString(33, custom2);
        statement.setString(34, custom3);
        statement.setString(35, custom4);
        statement.setString(36, custom5);
        statement.setTimestamp(37, created);
        statement.setTimestamp(38, modified);
        statement.setInt(39, status);
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
     * @param vs
     * @return
     * @throws SQLException
     */

    public static int insertIntoversicherer(Connection con, VersichererObj vs) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO versicherer (parentId, parentName, vuNummer, name, nameZusatz, "
                + "nameZusatz2, kuerzel, gesellschaftsNr, strasse, plz, stadt, bundesLand, land, "
                + "postfach, postfachName, postfachPlz, postfachOrt, vermittelbar, communication1, "
                + "communication2, communication3, communication4, communication5, communication6, "
                + "communication1Type, communication2Type, communication3Type, communication4Type, "
                + "communication5Type, communication6Type, comments, custom1, custom2, custom3, "
                + "custom4, custom5, created, modified, status)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, vs.getParentId());
        statement.setString(2, vs.getParentName());
        statement.setString(3, vs.getVuNummer());
        statement.setString(4, vs.getName());
        statement.setString(5, vs.getNameZusatz());
        statement.setString(6, vs.getNameZusatz2());
        statement.setString(7, vs.getKuerzel());
        statement.setString(8, vs.getGesellschaftsNr());
        statement.setString(9, vs.getStrasse());
        statement.setString(10, vs.getPlz());
        statement.setString(11, vs.getStadt());
        statement.setString(12, vs.getBundesLand());
        statement.setString(13, vs.getLand());
        statement.setBoolean(14, vs.isPostfach());
        statement.setString(15, vs.getPostfachName());
        statement.setString(16, vs.getPostfachPlz());
        statement.setString(17, vs.getPostfachOrt());
        statement.setBoolean(18, vs.isVermittelbar());
        statement.setString(19, vs.getCommunication1());
        statement.setString(20, vs.getCommunication2());
        statement.setString(21, vs.getCommunication3());
        statement.setString(22, vs.getCommunication4());
        statement.setString(23, vs.getCommunication5());
        statement.setString(24, vs.getCommunication6());
        statement.setInt(25, vs.getCommunication1Type());
        statement.setInt(26, vs.getCommunication2Type());
        statement.setInt(27, vs.getCommunication3Type());
        statement.setInt(28, vs.getCommunication4Type());
        statement.setInt(29, vs.getCommunication5Type());
        statement.setInt(30, vs.getCommunication6Type());
        statement.setString(31, vs.getComments());
        statement.setString(32, vs.getCustom1());
        statement.setString(33, vs.getCustom2());
        statement.setString(34, vs.getCustom3());
        statement.setString(35, vs.getCustom4());
        statement.setString(36, vs.getCustom5());
        statement.setTimestamp(37, vs.getCreated());
        statement.setTimestamp(38, vs.getModified());
        statement.setInt(39, vs.getStatus());
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
     * @param parentName
     * @param vuNummer
     * @param name
     * @param nameZusatz
     * @param nameZusatz2
     * @param kuerzel
     * @param gesellschaftsNr
     * @param strasse
     * @param plz
     * @param stadt
     * @param bundesLand
     * @param land
     * @param postfach
     * @param postfachName
     * @param postfachPlz
     * @param postfachOrt
     * @param vermittelbar
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
     */

    public static boolean updateversicherer(Connection con, int keyId, int parentId, String parentName, String vuNummer, String name, String nameZusatz,
            String nameZusatz2, String kuerzel, String gesellschaftsNr, String strasse, String plz,
            String stadt, String bundesLand, String land, boolean postfach, String postfachName,
            String postfachPlz, String postfachOrt, boolean vermittelbar, String communication1, String communication2,
            String communication3, String communication4, String communication5, String communication6, int communication1Type,
            int communication2Type, int communication3Type, int communication4Type, int communication5Type, int communication6Type,
            String comments, String custom1, String custom2, String custom3, String custom4,
            String custom5, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        String sql = "SELECT * FROM versicherer WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if(rows == 0){
            entry.close();
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("parentId", parentId);
        if(parentName != null)
            entry.updateString("parentName", parentName);
        if(vuNummer != null)
            entry.updateString("vuNummer", vuNummer);
        if(name != null)
            entry.updateString("name", name);
        if(nameZusatz != null)
            entry.updateString("nameZusatz", nameZusatz);
        if(nameZusatz2 != null)
            entry.updateString("nameZusatz2", nameZusatz2);
        if(kuerzel != null)
            entry.updateString("kuerzel", kuerzel);
        if(gesellschaftsNr != null)
            entry.updateString("gesellschaftsNr", gesellschaftsNr);
        if(strasse != null)
            entry.updateString("strasse", strasse);
        entry.updateString("plz", plz);
        if(stadt != null)
            entry.updateString("stadt", stadt);
        if(bundesLand != null)
            entry.updateString("bundesLand", bundesLand);
        if(land != null)
            entry.updateString("land", land);
        entry.updateBoolean("postfach", postfach);
        if(postfachName != null)
            entry.updateString("postfachName", postfachName);
        if(postfachPlz != null)
            entry.updateString("postfachPlz", postfachPlz);
        if(postfachOrt != null)
            entry.updateString("postfachOrt", postfachOrt);
        entry.updateBoolean("vermittelbar", vermittelbar);
        if(communication1 != null)
            entry.updateString("communication1", communication1);
        if(communication2 != null)
            entry.updateString("communication2", communication2);
        if(communication3 != null)
            entry.updateString("communication3", communication3);
        if(communication4 != null)
            entry.updateString("communication4", communication4);
        if(communication5 != null)
            entry.updateString("communication5", communication5);
        if(communication6 != null)
            entry.updateString("communication6", communication6);
        entry.updateInt("communication1Type", communication1Type);
        entry.updateInt("communication2Type", communication2Type);
        entry.updateInt("communication3Type", communication3Type);
        entry.updateInt("communication4Type", communication4Type);
        entry.updateInt("communication5Type", communication5Type);
        entry.updateInt("communication6Type", communication6Type);
        if(comments != null)
            entry.updateString("comments", comments);
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
        if(created != null)
            entry.updateTimestamp("created", created);
        if(modified != null)
            entry.updateTimestamp("modified", modified);
        entry.updateInt("status", status);

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }


    public static void archiveFromVersicherer(Connection con, VersichererObj vers) throws SQLException {
        if(vers == null)
            return;
        
        String sql = "UPDATE versicherer SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, vers.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        vers.setStatus(Status.ARCHIVED);
    }
    
    public static void deleteFromVersicherer(Connection con, VersichererObj vers) throws SQLException {
        if(vers == null)
            return;
        
        String sql = "UPDATE versicherer SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, vers.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        vers.setStatus(Status.DELETED);
    }        

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */

    public static void deleteEndgueltigFromversicherer(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM versicherer WHERE id = ?";
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

    public static VersichererObj[] loadVersicherer(Connection con, int status) throws SQLException {
        String sql = "SELECT * FROM versicherer WHERE status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM versicherer";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        if(status != -1)
            statement.setInt(1, status);

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

        VersichererObj[] versicherer = new VersichererObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            versicherer[i] = new VersichererObj();

            versicherer[i].setId(entry.getInt("id"));
            versicherer[i].setParentId(entry.getInt("parentId"));
            versicherer[i].setParentName(entry.getString("parentName"));
            versicherer[i].setVuNummer(entry.getString("vuNummer"));
            versicherer[i].setName(entry.getString("name"));
            versicherer[i].setNameZusatz(entry.getString("nameZusatz"));
            versicherer[i].setNameZusatz2(entry.getString("nameZusatz2"));
            versicherer[i].setKuerzel(entry.getString("kuerzel"));
            versicherer[i].setGesellschaftsNr(entry.getString("gesellschaftsNr"));
            versicherer[i].setStrasse(entry.getString("strasse"));
            versicherer[i].setPlz(entry.getString("plz"));
            versicherer[i].setStadt(entry.getString("stadt"));
            versicherer[i].setBundesLand(entry.getString("bundesLand"));
            versicherer[i].setLand(entry.getString("land"));

            versicherer[i].setPostfach(entry.getBoolean("postfach"));
            
            versicherer[i].setPostfachName(entry.getString("postfachName"));
            versicherer[i].setPostfachPlz(entry.getString("postfachPlz"));
            versicherer[i].setPostfachOrt(entry.getString("postfachOrt"));

            versicherer[i].setVermittelbar(entry.getBoolean("vermittelbar"));

            versicherer[i].setCommunication1(entry.getString("communication1"));
            versicherer[i].setCommunication2(entry.getString("communication2"));
            versicherer[i].setCommunication3(entry.getString("communication3"));
            versicherer[i].setCommunication4(entry.getString("communication4"));
            versicherer[i].setCommunication5(entry.getString("communication5"));
            versicherer[i].setCommunication6(entry.getString("communication6"));

            versicherer[i].setCommunication1Type(entry.getInt("communication1Type"));
            versicherer[i].setCommunication2Type(entry.getInt("communication2Type"));
            versicherer[i].setCommunication3Type(entry.getInt("communication3Type"));
            versicherer[i].setCommunication4Type(entry.getInt("communication4Type"));
            versicherer[i].setCommunication5Type(entry.getInt("communication5Type"));
            versicherer[i].setCommunication6Type(entry.getInt("communication6Type"));

            versicherer[i].setComments(entry.getString("comments"));
            versicherer[i].setCustom1(entry.getString("custom1"));
            versicherer[i].setCustom2(entry.getString("custom2"));
            versicherer[i].setCustom3(entry.getString("custom3"));
            versicherer[i].setCustom4(entry.getString("custom4"));
            versicherer[i].setCustom5(entry.getString("custom5"));

            versicherer[i].setCreated(entry.getTimestamp("created"));
            versicherer[i].setModified(entry.getTimestamp("modified"));
            versicherer[i].setStatus(entry.getInt("status"));            
        }

        entry.close();
        statement.close();
        con.close();

        return versicherer;
    }

    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     */

    public static VersichererObj[] loadAlleVersicherer(Connection con) throws SQLException {
        String sql = "SELECT * FROM versicherer_all WHERE";

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

        VersichererObj[] versicherer = new VersichererObj[rows];

        for(int i = 0; i < rows; i++) {
            entry.next();
            versicherer[i] = new VersichererObj();

            versicherer[i].setId(entry.getInt("id"));
            versicherer[i].setParentId(entry.getInt("parentId"));
            versicherer[i].setParentName(entry.getString("parentName"));
            versicherer[i].setVuNummer(entry.getString("vuNummer"));
            versicherer[i].setName(entry.getString("name"));
            versicherer[i].setNameZusatz(entry.getString("nameZusatz"));
            versicherer[i].setNameZusatz2(entry.getString("nameZusatz2"));
            versicherer[i].setKuerzel(entry.getString("kuerzel"));
            versicherer[i].setGesellschaftsNr(entry.getString("gesellschaftsNr"));
            versicherer[i].setStrasse(entry.getString("strasse"));
            versicherer[i].setPlz(entry.getString("plz"));
            versicherer[i].setStadt(entry.getString("stadt"));
            versicherer[i].setBundesLand(entry.getString("bundesLand"));
            versicherer[i].setLand(entry.getString("land"));

            versicherer[i].setPostfach(entry.getBoolean("postfach"));

            versicherer[i].setPostfachName(entry.getString("postfachName"));
            versicherer[i].setPostfachPlz(entry.getString("postfachPlz"));
            versicherer[i].setPostfachOrt(entry.getString("postfachOrt"));

            versicherer[i].setVermittelbar(entry.getBoolean("vermittelbar"));

            versicherer[i].setCommunication1(entry.getString("communication1"));
            versicherer[i].setCommunication2(entry.getString("communication2"));
            versicherer[i].setCommunication3(entry.getString("communication3"));
            versicherer[i].setCommunication4(entry.getString("communication4"));
            versicherer[i].setCommunication5(entry.getString("communication5"));
            versicherer[i].setCommunication6(entry.getString("communication6"));

            versicherer[i].setCommunication1Type(entry.getInt("communication1Type"));
            versicherer[i].setCommunication2Type(entry.getInt("communication2Type"));
            versicherer[i].setCommunication3Type(entry.getInt("communication3Type"));
            versicherer[i].setCommunication4Type(entry.getInt("communication4Type"));
            versicherer[i].setCommunication5Type(entry.getInt("communication5Type"));
            versicherer[i].setCommunication6Type(entry.getInt("communication6Type"));

            versicherer[i].setComments(entry.getString("comments"));
            versicherer[i].setCustom1(entry.getString("custom1"));
            versicherer[i].setCustom2(entry.getString("custom2"));
            versicherer[i].setCustom3(entry.getString("custom3"));
            versicherer[i].setCustom4(entry.getString("custom4"));
            versicherer[i].setCustom5(entry.getString("custom5"));

            versicherer[i].setCreated(entry.getTimestamp("created"));
            versicherer[i].setModified(entry.getTimestamp("modified"));
            versicherer[i].setStatus(entry.getInt("status"));
        }

        entry.close();
        statement.close();
        con.close();

        return versicherer;
    }

    /**
     * 
     * @param con
     * @param keyId
     * @return
     * @throws SQLException
     */


    public static VersichererObj getVersicherer(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM versicherer WHERE id = ?";

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
            return null;
        }

        VersichererObj versicherer = new VersichererObj();

        entry.next();

        versicherer.setId(entry.getInt("id"));
        versicherer.setParentId(entry.getInt("parentId"));
        versicherer.setParentName(entry.getString("parentName"));
        versicherer.setVuNummer(entry.getString("vuNummer"));
        versicherer.setName(entry.getString("name"));
        versicherer.setNameZusatz(entry.getString("nameZusatz"));
        versicherer.setNameZusatz2(entry.getString("nameZusatz2"));
        versicherer.setKuerzel(entry.getString("kuerzel"));
        versicherer.setGesellschaftsNr(entry.getString("gesellschaftsNr"));
        versicherer.setStrasse(entry.getString("strasse"));
        versicherer.setPlz(entry.getString("plz"));
        versicherer.setStadt(entry.getString("stadt"));
        versicherer.setBundesLand(entry.getString("bundesLand"));
        versicherer.setLand(entry.getString("land"));

        versicherer.setPostfach(entry.getBoolean("postfach"));

        versicherer.setPostfachName(entry.getString("postfachName"));
        versicherer.setPostfachPlz(entry.getString("postfachPlz"));
        versicherer.setPostfachOrt(entry.getString("postfachOrt"));

        versicherer.setVermittelbar(entry.getBoolean("vermittelbar"));

        versicherer.setCommunication1(entry.getString("communication1"));
        versicherer.setCommunication2(entry.getString("communication2"));
        versicherer.setCommunication3(entry.getString("communication3"));
        versicherer.setCommunication4(entry.getString("communication4"));
        versicherer.setCommunication5(entry.getString("communication5"));
        versicherer.setCommunication6(entry.getString("communication6"));

        versicherer.setCommunication1Type(entry.getInt("communication1Type"));
        versicherer.setCommunication2Type(entry.getInt("communication2Type"));
        versicherer.setCommunication3Type(entry.getInt("communication3Type"));
        versicherer.setCommunication4Type(entry.getInt("communication4Type"));
        versicherer.setCommunication5Type(entry.getInt("communication5Type"));
        versicherer.setCommunication6Type(entry.getInt("communication6Type"));

        versicherer.setComments(entry.getString("comments"));
        versicherer.setCustom1(entry.getString("custom1"));
        versicherer.setCustom2(entry.getString("custom2"));
        versicherer.setCustom3(entry.getString("custom3"));
        versicherer.setCustom4(entry.getString("custom4"));
        versicherer.setCustom5(entry.getString("custom5"));

        versicherer.setCreated(entry.getTimestamp("created"));
        versicherer.setModified(entry.getTimestamp("modified"));
        versicherer.setStatus(entry.getInt("status"));
        

        entry.close();
        statement.close();
        con.close();

        return versicherer;
    }

}
