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

import de.maklerpoint.office.Kunden.ZusatzadressenObj;
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
public class ZusatzadressenSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creator
     * @param kundenKennung
     * @param name
     * @param nameZusatz
     * @param nameZusatz2
     * @param street
     * @param plz
     * @param bundesland
     * @param land
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
     * @param custom1
     * @param custom2
     * @param custom3
     * @param comments
     * @param created
     * @param modified
     * @param status
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoKunden_zusatzadressen(Connection con, int creator, String kundenKennung, int versid, int benid, String name, String nameZusatz, String nameZusatz2,
            String street, String plz, String ort, String bundesland, String land,
            String communication1, String communication2, String communication3, String communication4, String communication5,
            String communication6, int communication1Type, int communication2Type, int communication3Type, int communication4Type,
            int communication5Type, int communication6Type, String custom1, String custom2, String custom3,
            String comments, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO kunden_zusatzadressen (creator, kundenKennung, name, nameZusatz, nameZusatz2, street, "
                + "plz, ort, bundesland, land, communication1, communication2, "
                + "communication3, communication4, communication5, communication6, communication1Type, communication2Type, "
                + "communication3Type, communication4Type, communication5Type, communication6Type, custom1, custom2, "
                + "custom3, comments, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, creator);
        statement.setString(2, kundenKennung);
        statement.setString(3, name);
        statement.setString(4, nameZusatz);
        statement.setString(5, nameZusatz2);
        statement.setString(6, street);
        statement.setString(7, plz);
        statement.setString(8, ort);
        statement.setString(9, bundesland);
        statement.setString(10, land);
        statement.setString(11, communication1);
        statement.setString(12, communication2);
        statement.setString(13, communication3);
        statement.setString(14, communication4);
        statement.setString(15, communication5);
        statement.setString(16, communication6);
        statement.setInt(17, communication1Type);
        statement.setInt(18, communication2Type);
        statement.setInt(19, communication3Type);
        statement.setInt(20, communication4Type);
        statement.setInt(21, communication5Type);
        statement.setInt(22, communication6Type);
        statement.setString(23, custom1);
        statement.setString(24, custom2);
        statement.setString(25, custom3);
        statement.setString(26, comments);
        statement.setTimestamp(27, created);
        statement.setTimestamp(28, modified);
        statement.setInt(29, status);
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

    public static int insertIntoKunden_zusatzadressen(Connection con, ZusatzadressenObj za) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO kunden_zusatzadressen (creator, kundenKennung, versichererId, benutzerId, name, "
                + "nameZusatz, nameZusatz2, street, "
                + "plz, ort, bundesland, land, communication1, communication2, "
                + "communication3, communication4, communication5, communication6, communication1Type, communication2Type, "
                + "communication3Type, communication4Type, communication5Type, communication6Type, custom1, custom2, "
                + "custom3, comments, created, modified, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, za.getCreator());
        statement.setString(2, za.getKundenKennung());
        statement.setInt(3, za.getVersichererId());
        statement.setInt(4, za.getBenutzerId());
        statement.setString(5, za.getName());
        statement.setString(6, za.getNameZusatz());
        statement.setString(7, za.getNameZusatz2());
        statement.setString(8, za.getStreet());
        statement.setString(9, za.getPlz());
        statement.setString(10, za.getOrt());
        statement.setString(11, za.getBundesland());
        statement.setString(12, za.getLand());
        statement.setString(13, za.getCommunication1());
        statement.setString(14, za.getCommunication2());
        statement.setString(15, za.getCommunication3());
        statement.setString(16, za.getCommunication4());
        statement.setString(17, za.getCommunication5());
        statement.setString(18, za.getCommunication6());
        statement.setInt(19, za.getCommunication1Type());
        statement.setInt(20, za.getCommunication2Type());
        statement.setInt(21, za.getCommunication3Type());
        statement.setInt(22, za.getCommunication4Type());
        statement.setInt(23, za.getCommunication5Type());
        statement.setInt(24, za.getCommunication6Type());
        statement.setString(25, za.getCustom1());
        statement.setString(26, za.getCustom2());
        statement.setString(27, za.getCustom3());
        statement.setString(28, za.getComments());
        statement.setTimestamp(29, za.getCreated());
        statement.setTimestamp(30, za.getModified());
        statement.setInt(31, za.getStatus());
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
     * @param kundenKennung
     * @param name
     * @param nameZusatz
     * @param nameZusatz2
     * @param street
     * @param plz
     * @param ort
     * @param bundesland
     * @param land
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
     * @param custom1
     * @param custom2
     * @param custom3
     * @param comments
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     * @deprecated verwaltet
     */
    public static boolean updateKunden_zusatzadressen(Connection con, int keyId, int creator, String kundenKennung, int versid, int benid, String name, String nameZusatz, String nameZusatz2,
            String street, String plz, String ort, String bundesland, String land,
            String communication1, String communication2, String communication3, String communication4, String communication5,
            String communication6, int communication1Type, int communication2Type, int communication3Type, int communication4Type,
            int communication5Type, int communication6Type, String custom1, String custom2, String custom3,
            String comments, java.sql.Timestamp created, java.sql.Timestamp modified, int status) throws SQLException {
        String sql = "SELECT * FROM kunden_zusatzadressen WHERE id = ?";
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

        entry.updateInt("creator", creator);
        if (kundenKennung != null) {
            entry.updateString("kundenKennung", kundenKennung);
        }
        entry.updateInt("versichererId", versid);
        entry.updateInt("benutzerId", benid);

        if (name != null) {
            entry.updateString("name", name);
        }
        if (nameZusatz != null) {
            entry.updateString("nameZusatz", nameZusatz);
        }
        if (nameZusatz2 != null) {
            entry.updateString("nameZusatz2", nameZusatz2);
        }
        if (street != null) {
            entry.updateString("street", street);
        }
        if (plz != null) {
            entry.updateString("plz", plz);
        }
        if (ort != null) {
            entry.updateString("ort", ort);
        }
        if (bundesland != null) {
            entry.updateString("bundesland", bundesland);
        }
        if (land != null) {
            entry.updateString("land", land);
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
        if (custom1 != null) {
            entry.updateString("custom1", custom1);
        }
        if (custom2 != null) {
            entry.updateString("custom2", custom2);
        }
        if (custom3 != null) {
            entry.updateString("custom3", custom3);
        }
        if (comments != null) {
            entry.updateString("comments", comments);
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

    public static void deleteEndgueltigFromKunden_zusatzadressen(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM kunden_zusatzadressen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }

    public static boolean updateKunden_zusatzadressen(Connection con, ZusatzadressenObj za) throws SQLException {
        String sql = "SELECT * FROM kunden_zusatzadressen WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, za.getId());
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

        entry.updateInt("creator", za.getCreator());

        entry.updateString("kundenKennung", za.getKundenKennung());

        entry.updateInt("versichererId", za.getVersichererId());
        entry.updateInt("benutzerId", za.getBenutzerId());

        entry.updateString("name", za.getName());

        entry.updateString("nameZusatz", za.getNameZusatz());

        entry.updateString("nameZusatz2", za.getNameZusatz2());

        entry.updateString("street", za.getStreet());

        entry.updateString("plz", za.getPlz());

        entry.updateString("ort", za.getOrt());

        entry.updateString("bundesland", za.getBundesland());

        entry.updateString("land", za.getLand());

        entry.updateString("communication1", za.getCommunication1());

        entry.updateString("communication2", za.getCommunication2());

        entry.updateString("communication3", za.getCommunication3());

        entry.updateString("communication4", za.getCommunication4());

        entry.updateString("communication5", za.getCommunication5());

        entry.updateString("communication6", za.getCommunication6());
        entry.updateInt("communication1Type", za.getCommunication1Type());
        entry.updateInt("communication2Type", za.getCommunication2Type());
        entry.updateInt("communication3Type", za.getCommunication3Type());
        entry.updateInt("communication4Type", za.getCommunication4Type());
        entry.updateInt("communication5Type", za.getCommunication5Type());
        entry.updateInt("communication6Type", za.getCommunication6Type());
        entry.updateString("custom1", za.getCustom1());

        entry.updateString("custom2", za.getCustom2());

        entry.updateString("custom3", za.getCustom3());

        entry.updateString("comments", za.getComments());

        entry.updateTimestamp("created", za.getCreated());

        entry.updateTimestamp("modified", za.getModified());
        entry.updateInt("status", za.getStatus());

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
    public static void deleteFromkundenzusatz(Connection con, int keyId) throws SQLException {
        String sql = "SELECT *  FROM kunden_zusatzadressen WHERE id = ?";
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
     * 
     * @param con
     * @param keyId
     * @throws SQLException
     */
    public static void archiveFromkundenzusatz(Connection con, int keyId) throws SQLException {
        String sql = "SELECT * FROM kunden_zusatzadressen WHERE id = ?";
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
     * @return
     * @throws SQLException
     */
    public static ZusatzadressenObj[] loadZusatzadressen(Connection con, String kennung, int status) throws SQLException {
        String sql = "SELECT * FROM kunden_zusatzadressen WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT * FROM kunden_zusatzadressen WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kennung);

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

        ZusatzadressenObj[] za = new ZusatzadressenObj[rows];

        for (int i = 0; i < rows; i++) {
            entry.next();
            za[i] = getResultSetEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return za;
    }

    public static ZusatzadressenObj getResultSetEntry(ResultSet entry) throws SQLException {
        ZusatzadressenObj za = new ZusatzadressenObj();

        za.setId(entry.getInt("id"));
        za.setCreator(entry.getInt("creator"));
        za.setKundenKennung(entry.getString("kundenKennung"));
        za.setVersichererId(entry.getInt("versichererId"));
        za.setBenutzerId(entry.getInt("benutzerId"));
        za.setName(entry.getString("name"));
        za.setNameZusatz(entry.getString("nameZusatz"));
        za.setNameZusatz2(entry.getString("nameZusatz2"));
        za.setStreet(entry.getString("street"));
        za.setPlz(entry.getString("plz"));
        za.setOrt(entry.getString("ort"));
        za.setBundesland(entry.getString("bundesland"));
        za.setLand(entry.getString("land"));
        za.setCommunication1(entry.getString("communication1"));
        za.setCommunication2(entry.getString("communication2"));
        za.setCommunication3(entry.getString("communication3"));
        za.setCommunication4(entry.getString("communication4"));
        za.setCommunication5(entry.getString("communication5"));
        za.setCommunication6(entry.getString("communication6"));
        za.setCommunication1Type(entry.getInt("communication1Type"));
        za.setCommunication2Type(entry.getInt("communication2Type"));
        za.setCommunication3Type(entry.getInt("communication3Type"));
        za.setCommunication4Type(entry.getInt("communication4Type"));
        za.setCommunication5Type(entry.getInt("communication5Type"));
        za.setCommunication6Type(entry.getInt("communication6Type"));
        za.setCustom1(entry.getString("custom1"));
        za.setCustom2(entry.getString("custom2"));
        za.setCustom3(entry.getString("custom3"));
        za.setComments(entry.getString("comments"));
        za.setCreated(entry.getTimestamp("created"));
        za.setModified(entry.getTimestamp("modified"));
        za.setStatus(entry.getInt("status"));

        return za;
    }
}
