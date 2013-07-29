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
package de.maklerpoint.office.Schaeden.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemSchaeden;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.System.Configuration.Config;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SchaedenHelper {

    public static int getNextSchadennummer(Connection con) throws SQLException {
        int next = -1;

        String sql = "SELECT MAX(schadenNr) FROM schaeden";

        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet entry = statement.executeQuery();
        entry.next();
        int num = entry.getInt(1);

        if (num == 0) { // Startvalue
            next = Config.getConfigInt("initialSchadennr", 50001);
        } else {
            next = num + 1;
        }

        statement.close();
        con.close();

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Nächste freie interne Schadensnummer: " + next);
        }

        return next;
    }

    public static String verifyNochaktuell(String schadennr) throws SQLException {
        if (schadennr.equalsIgnoreCase("0")) {
            return String.valueOf(getNextSchadennummer(DatabaseConnection.open()));
        }

        SchadenObj sch = SchaedenSQLMethods.getSchaden(DatabaseConnection.open(), schadennr);
        if (sch == null) {
            return schadennr;
        } else {
            return String.valueOf(getNextSchadennummer(DatabaseConnection.open()));
        }
    }

    public static void doCreationTasks(SchadenObj schaden) {
        try {
            FilesystemSchaeden.createSchadenDirectory(schaden);
        } catch (IOException e) {
            Log.logger.fatal("Konnte Verzeichnisstruktur für den Schaden nicht erstellen", e);
            ShowException.showException("Beim erstellen der Vereichnisstruktur für den Schadensfall "
                    + "ist ein schwerer Fehler aufgetretten.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Schaden nicht speichern");
        }
    }
}
