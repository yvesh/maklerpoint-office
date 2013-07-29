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

package de.maklerpoint.office.Kalender.Tools;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Benutzer.Tools.BenutzerSQLMethods;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class GeburtstageSQLMethods {

    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * 
     * @param con
     * @param date
     * @param eigene
     * @return
     * @throws SQLException
     * @deprecated 
     */

    public static KundenObj[] getKundenGeburtstag(Connection con, Date date, boolean eigene) throws SQLException {
        
        String sql = null;

        if(eigene)
            sql = "SELECT * FROM kunden WHERE betreuer = ? AND geburtsdatum = ? AND status = 0";
        else
            sql = "SELECT * FROM kunden WHERE geburtsdatum = ? AND status = 0";


        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        if(eigene) {
            statement.setInt(1, BasicRegistry.currentUser.getId());
            statement.setString(2, df.format(date));
        } else {
            statement.setString(1, df.format(date));
        }

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

        KundenObj[] kunden = new KundenObj[rows];

        for(int i = 0; i< rows; i++)
        {
            entry.next();
            kunden[i] = KundenSQLMethods.getKundeEntry(entry);
        }

        entry.close();
        statement.close();
        con.close();

        return kunden;
    }

    /**
     * Use sf
     * @param con
     * @param date
     * @return
     * @throws SQLException
     */

    public static BenutzerObj[] getBenutzerGeburtstag(Connection con, Date date) throws SQLException {

        String sql = "SELECT * FROM benutzer WHERE geburtsdatum = ? AND status = 0";

        PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setString(1, df.format(date));

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

        BenutzerObj[] alleBenutzer = new BenutzerObj[rows];

        for(int i = 0; i < rows; i++)
        {
            entry.next();
             alleBenutzer[i] = BenutzerSQLMethods.getResultSet(entry, false);
        }

        entry.close();
        statement.close();
        con.close();

        return alleBenutzer;
    }

}