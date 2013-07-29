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
package de.maklerpoint.office.Kunden.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class KundenKennungHelper {

    public static String getNextKundennummer(Connection con) throws SQLException {
        int next = -1;

        String sql = "SELECT MAX(kundenNr) FROM kunden";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet entry = statement.executeQuery();
        entry.next();
        int num = entry.getInt(1);

//        System.out.println("Kunden Num: " + num);

        sql = "SELECT MAX(kundenNr) FROM firmenkunden";

        statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        entry = statement.executeQuery();
        entry.next();
        int num2 = entry.getInt(1);

//        System.out.println("Firmen Num: " + num2);

        if (num2 > num) {
            next = num2 + 1;
        } else {
            next = num + 1;
        }

        if (next == 1) {
            next = Config.getConfigInt("initialKundennr", 11001);
        }


        statement.close();
        con.close();

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Nächste freie Kundennummer: " + next);
        }

        return String.valueOf(next);
    }

    public static String verifyNochaktuell(String kdnr) throws SQLException {
        KundenObj kunde = KundenSQLMethods.loadKunde(DatabaseConnection.open(), kdnr);
        if (kunde == null) {
            FirmenObj firma = FirmenSQLMethods.getFirmenKunde(DatabaseConnection.open(), kdnr);
            if (firma == null) {
                return kdnr;
            } else {
                return getNextKundennummer(DatabaseConnection.open());
            }
        } else {
            return getNextKundennummer(DatabaseConnection.open());
        }
    }
}
