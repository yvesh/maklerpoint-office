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
package de.maklerpoint.office.Briefe.Tools;

import de.maklerpoint.office.Briefe.BriefCategoryObj;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import java.sql.SQLException;
import javax.swing.JFrame;


/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class BriefeHelper {
    
    public static BriefCategoryObj getBriefCategoryObj(int id){
        try {
            BriefCategoryObj catname = BriefeSQLMethods.getBriefCategory(DatabaseConnection.open(), id);            
            return catname;
        } catch (SQLException e) {
           Log.databaselogger.fatal("Fehler: Konnte den Kategoriename für die id " + id + " nicht laden.", e);
           ShowException.showException("Datenbankfehler: Der Kategoriename für den Brief konnte nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Kategorie nicht laden");
        }
        return null;
    }
    
    
    public static BriefObj openBriefDialog(int type){
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        BriefDialog auswahl = new BriefDialog(mainFrame, true, type);
        auswahl.setLocationRelativeTo(mainFrame);                

        CRM.getApplication().show(auswahl);
        
        BriefObj brief = (BriefObj) auswahl.getReturnStatus();
                
        return brief;
    }
    
    
    BriefDialog auswahl = null;
}
