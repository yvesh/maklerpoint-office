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

package de.maklerpoint.office.Registry;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Mandanten.Tools.MandantenSQLMethods;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MandantenRegistry {

    public static MandantenObj[] alleMandanten;

    public static MandantenObj[] getAlleMandanten(boolean refresh) {
        try {
            if(refresh || alleMandanten == null) {
                alleMandanten = MandantenSQLMethods.loadMandaten(DatabaseConnection.open());
            } else {
                return alleMandanten;
            }
        } catch (SQLException e) {            
            Log.databaselogger.fatal("Die Mandantenliste konnte nicht aus der Datenbank geladen werden.", e);
            ShowException.showException("Die Mandantenliste konnte nicht aus der Datenbank geladen werden.",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Mandanten nicht laden");
        }

        return alleMandanten;
    }


    public static MandantenObj getMandant(int id, boolean refresh) {
        if(refresh || alleMandanten == null) {
            getAlleMandanten(true);
        }

        for(int i = 0; i < alleMandanten.length; i++) {
            if(alleMandanten[i].getId() == id)
                return alleMandanten[i];
        }

        return null;
    }
}
