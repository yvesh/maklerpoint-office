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
package de.maklerpoint.office.Bank.Tools;

import de.maklerpoint.office.Bank.BankKontoObj;
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
public class BankKontoSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param BankKontoObj konto
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoBankkonten(Connection con, BankKontoObj konto) throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO bankkonten (type, kundenKennung, versichererId, benutzerId, kontonummer, bankleitzahl, "
                + "bankinstitut, kontoinhaber, iban, bic, comments, status, creatorId"
                + ")"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, konto.getType());
        statement.setString(2, konto.getKundenKennung());
        statement.setInt(3, konto.getVersichererId());
        statement.setInt(4, konto.getBenutzerId());
        statement.setString(5, konto.getKontonummer());
        statement.setString(6, konto.getBankleitzahl());
        statement.setString(7, konto.getBankinstitut());
        statement.setString(8, konto.getKontoinhaber());
        statement.setString(9, konto.getIban());
        statement.setString(10, konto.getBic());
        statement.setString(11, konto.getComments());
        statement.setInt(12, konto.getStatus());
        statement.setInt(13, konto.getCreatorId());
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
     * @param BankKontoObj konto
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateBankkonten(Connection con, BankKontoObj konto) throws SQLException {
        String sql = "SELECT * FROM bankkonten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, konto.getId());
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

        entry.updateInt("type", konto.getType());        
        entry.updateInt("creatorId", konto.getCreatorId());
        entry.updateString("kundenKennung", konto.getKundenKennung());
        entry.updateInt("versichererId", konto.getVersichererId());
        entry.updateInt("benutzerId", konto.getBenutzerId());
        entry.updateString("kontonummer", konto.getKontonummer());
        entry.updateString("bankleitzahl", konto.getBankleitzahl());
        entry.updateString("bankinstitut", konto.getBankinstitut());
        entry.updateString("kontoinhaber", konto.getKontoinhaber());
        entry.updateString("iban", konto.getIban());
        entry.updateString("bic", konto.getBic());
        entry.updateString("comments", konto.getComments());
        entry.updateInt("status", konto.getStatus());
        entry.updateTimestamp("created", konto.getCreated());
        entry.updateTimestamp("modified", konto.getModified());

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
    public static void deleteEndgueltigFromBankkonten(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM bankkonten WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
    
    public static void deleteFromBankkonten(Connection con, BankKontoObj bk) throws SQLException {
        if(bk == null)
            return;
        
        String sql = "UPDATE bankkonten SET status = " + Status.DELETED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, bk.getId());
        statement.executeUpdate();
        statement.close();        
        con.close();
        bk.setStatus(Status.DELETED);
    }
    
    public static void archiveFromBankkonten(Connection con, BankKontoObj bk) throws SQLException {
        if(bk == null)
            return;
        
        String sql = "UPDATE bankkonten SET status = " + Status.ARCHIVED + " WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, bk.getId());
        statement.executeUpdate();
        statement.close();
        con.close();
        bk.setStatus(Status.DELETED);
    }
    
    
    
    /**
     * 
     * @param con
     * @param id
     * @return
     * @throws SQLException 
     */
    
    public static BankKontoObj getKonto(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM bankkonten WHERE id = ?";
        
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
        
        BankKontoObj bk = getBankEntry(entry);
        
        entry.close();
        statement.close();
        con.close();
        
        return bk;
    }
    
    /**
     * 
     * @param con
     * @param kundenKennung
     * @param status
     * @return
     * @throws SQLException 
     */
    
    public static BankKontoObj[] getKonten(Connection con, String kundenKennung, int status) throws SQLException {
        String sql = "SELECT * FROM bankkonten WHERE kundenKennung = ? AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM bankkonten WHERE kundenKennung = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, kundenKennung);
        
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
        
        BankKontoObj[] bks = new BankKontoObj[rows];
        
        for(int i = 0; i < rows; i++){
            entry.next();
            bks[i] = getBankEntry(entry);
        }
 
        entry.close();
        statement.close();
        con.close();
        
        return bks;
    }
    
    
    public static BankKontoObj[] getBenutzerKonten(Connection con, int benutzerId, int status) throws SQLException {
        String sql = "SELECT * FROM bankkonten WHERE benutzerId = ? AND status = ?";
        
        if(status == -1)
            sql = "SELECT * FROM bankkonten WHERE benutzerId = ?";
        
        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, benutzerId);
        
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
        
        BankKontoObj[] bks = new BankKontoObj[rows];
        
        for(int i = 0; i < rows; i++){
            entry.next();
            bks[i] = getBankEntry(entry);
        }
 
        entry.close();
        statement.close();
        con.close();
        
        return bks;
    }   
    
    /**
     * 
     * @param entry
     * @return
     * @throws SQLException 
     */
    
    public static BankKontoObj getBankEntry(ResultSet entry) throws SQLException {
        BankKontoObj bk = new BankKontoObj();
        
        bk.setId(entry.getInt("id"));
        bk.setType(entry.getInt("type"));
        bk.setCreatorId(entry.getInt("creatorId"));
        bk.setKundenKennung(entry.getString("kundenKennung"));
        bk.setVersichererId(entry.getInt("versichererId"));
        bk.setBenutzerId(entry.getInt("benutzerId"));
        bk.setKontonummer(entry.getString("kontonummer"));
        bk.setBankleitzahl(entry.getString("bankleitzahl"));
        bk.setBankinstitut(entry.getString("bankinstitut"));
        bk.setKontoinhaber(entry.getString("kontoinhaber"));
        bk.setIban(entry.getString("iban"));
        bk.setBic(entry.getString("bic"));
        bk.setComments(entry.getString("comments"));
        bk.setCreated(entry.getTimestamp("created"));
        bk.setModified(entry.getTimestamp("modified"));
        bk.setType(entry.getInt("status"));
        
        return bk;
    }
}
