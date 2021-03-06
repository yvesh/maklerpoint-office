/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Versicherer.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yves
 */
public class SearchVersicherer {

    public static String[] searchFields = new String[]{"parentName", "vuNummer", "name", "nameZusatz", "nameZusatz2",
        "kuerzel", "gesellschaftsNr", "strasse", "plz", "stadt", "bundesLand",
        "land", "postfachName", "postfachPlz", "communication1", "communication2", "communication3",
        "communication4", "communication5", "communication6", "custom1", "custom2", "custom3", "comments"
    };

    public static VersichererObj[] quickSearch(Connection con, Object value) throws SQLException {

        for(int i = 0; i < searchFields.length; i++)
        {
            VersichererObj[] firmen = SearchVersicherer.searchVersichererObject(DatabaseConnection.open(), searchFields[i], value);

            if(firmen != null) {
                return firmen;
            }
        }

        return null;
    }

    /**
     * 
     * @param con
     * @param field
     * @param value
     * @return
     * @throws SQLException
     */

    public static VersichererObj[] searchVersichererObject(Connection con, String field, Object value) throws SQLException {
  
        String sql = "SELECT * FROM versicherer WHERE " + field + " LIKE '%" + value + "%' AND status = 0";

        VersichererObj[] vers = null;

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);     

//        System.out.println("Statement: " + statement);

        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();

        if(rows == 0){
            entry.close();
            statement.close();
            con.close();
            return null;
        }

        vers = getEntries(entry, rows);

        entry.close();
        statement.close();
        con.close();

        return vers;
    }

    /**
     * 
     * @param entry
     * @param rows
     * @return
     * @throws SQLException
     */

    private static VersichererObj[] getEntries(ResultSet entry, int rows) throws SQLException {
        VersichererObj[] versicherer = new VersichererObj[rows];

        for(int i = 0; i < rows; i++)
        {
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

        return versicherer;
    }

}
