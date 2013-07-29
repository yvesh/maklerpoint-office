/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       25.07.2011 16:20:26
 *  File:       KundenStatistik
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Statistik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class KundenStatistik {
    
    
    /**
     * 
     * @param con
     * @param status
     * @return Anzahl Privatkunden
     * @throws SQLException 
     */
    public static int getAnzahlPrivatKunden(Connection con, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM kunden WHERE status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM kunden";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if (status != -1) {
            statement.setInt(1, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.next();
        vertr = entry.getInt(1);

        statement.close();
        con.close();

        return vertr;
    }
    
    /**
     *
     * @param con
     * @param benutzerId
     * @param status
     * @return Anzahl Privatkunden
     * @throws SQLException 
     */
    public static int getAnzahlPrivatKunden(Connection con, int benutzerId, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM kunden WHERE betreuerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM kunden WHERE betreuerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benutzerId);
        
        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.next();
        vertr = entry.getInt(1);

        statement.close();
        con.close();

        return vertr;
    }
    
    /**
     *
     * @param con
     * @param benutzerId
     * @param status
     * @return Anzahl Firmenkunden
     * @throws SQLException 
     */
    public static int getAnzahlFirmenKunden(Connection con, int benutzerId, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM firmenkunden WHERE betreuer = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM firmenkunden WHERE betreuer = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benutzerId);
        
        if (status != -1) {
            statement.setInt(2, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.next();
        vertr = entry.getInt(1);

        statement.close();
        con.close();

        return vertr;
    }
    
    /**
     * 
     * @param con
     * @param status
     * @return Anzahl Firmenkunden
     * @throws SQLException 
     */
    public static int getAnzahlFirmenKunden(Connection con, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM firmenkunden WHERE status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM firmenkunden";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if (status != -1) {
            statement.setInt(1, status);
        }

        ResultSet entry = statement.executeQuery();

        entry.next();
        vertr = entry.getInt(1);

        statement.close();
        con.close();

        return vertr;
    }
    
    
    
}
