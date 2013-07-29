/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       27.07.2011 10:42:22
 *  File:       KontakteRegistry
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
package de.maklerpoint.office.Registry;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kontakte.Tools.KontakteSQLMethods;
import de.maklerpoint.office.Logging.Log;
import java.sql.SQLException;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class KontakteRegistry {
    
    public static KontaktObj[] getEigeneKontakte(int status) {
        try {
            return KontakteSQLMethods.getEigeneKontakte(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte eigene Kontakte nicht aus der Datenbank laden", e);
            ShowException.showException("Die eigenen Kontakte konnten nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kontake nicht laden");
        }
        
        return null;
    }
    
}
