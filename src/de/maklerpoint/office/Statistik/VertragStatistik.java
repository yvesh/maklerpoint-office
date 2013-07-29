/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       25.07.2011 16:20:33
 *  File:       VertragStatistik
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
public class VertragStatistik {

    /**
     * 
     * @param con
     * @param kdnr
     * @param status
     * @return VertragCount
     * @throws SQLException 
     */
    public static int getAnzahlKundenVertraege(Connection con, String kdnr, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM vertraege WHERE kundenKennung = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM vertraege WHERE kundenKennung = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, kdnr);

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
     * @return Anzahl Verträge
     * @throws SQLException 
     */
    public static int getAnzahlBenutzerVertraege(Connection con, int benid, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM vertraege WHERE benutzerId = ? AND status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM vertraege WHERE benutzerId = ?";
        }

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setInt(1, benid);
        
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
    
    public static int getAnzahlVertraege(Connection con, int status) throws SQLException {
        int vertr = 0;
        String sql = "SELECT count(*) FROM vertraege WHERE status = ?";

        if (status == -1) {
            sql = "SELECT count(*) FROM vertraege";
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
