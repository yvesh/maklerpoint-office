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

import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Notizen.NotizenObj;
import de.maklerpoint.office.Notizen.Tools.NotizenSQLMethods;
import de.maklerpoint.office.Textbausteine.TextbausteinGroupObj;
import de.maklerpoint.office.Textbausteine.TextbausteinObj;
import de.maklerpoint.office.Textbausteine.Tools.TextbausteineSQLMethods;
import de.maklerpoint.office.Waehrungen.Tools.WaehrungenSQLMethods;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.sql.SQLException;
import java.util.ArrayList;
import org.openide.util.Exceptions;

/**
 *
 * @author yves
 */
public class ToolsRegistry {

    public static TextbausteinGroupObj[] TEXTBAUSTEIN_GRP = null;
    public static TextbausteinObj[] TEXTBAUSTEINE = null;
    /**
     * 
     * @param reload
     * @return
     */
    public static TextbausteinObj[] getTextBausteine(boolean reload) {
        if (TEXTBAUSTEINE == null || reload == true) {
            try {
                TEXTBAUSTEINE = TextbausteineSQLMethods.loadTextbausteine(DatabaseConnection.open());
                return TEXTBAUSTEINE;
            } catch (Exception e) {
                Log.databaselogger.fatal("Konnte die Textbausteine nicht aus der Datenbank laden", e);
                ShowException.showException("Datenbankfehler: Die Textbausteine konnten nicht aus der Datenbank geladen werden.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Textbausteine nicht laden");
            }
        } else {
            return TEXTBAUSTEINE;
        }

        return TEXTBAUSTEINE;
    }

    /**
     * 
     * @param reload
     * @return
     */
    public static TextbausteinGroupObj[] getTextBausteinGroups(boolean reload) {
        if (TEXTBAUSTEIN_GRP == null || reload == true) {
            try {
                TEXTBAUSTEIN_GRP = TextbausteineSQLMethods.loadTextbausteinGroups(DatabaseConnection.open());
                return TEXTBAUSTEIN_GRP;
            } catch (Exception e) {
                Log.databaselogger.fatal("Konnte die Textbaustein Gruppen nicht aus der Datenbank laden", e);
                ShowException.showException("Datenbankfehler: Die Textbaustein Gruppen konnten nicht aus der Datenbank geladen werden.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Textbaustein Gruppen nicht laden");
            }
        } else {
            return TEXTBAUSTEIN_GRP;
        }

        return TEXTBAUSTEIN_GRP;
    }

    /**
     * 
     * @param grpid
     * @param reload
     * @return
     */
    public static TextbausteinObj[] getTextBausteine(int grpid, boolean reload) {
        if (reload || TEXTBAUSTEINE == null) {
            getTextBausteine(true);
        }

        if (TEXTBAUSTEINE == null) {
            return null;
        }

        ArrayList bs = new ArrayList();

        for (int i = 0; i < TEXTBAUSTEINE.length; i++) {
            if (TEXTBAUSTEINE[i].getGroup() == grpid) {
                bs.add(TEXTBAUSTEINE[i]);
            }
        }

        TextbausteinObj[] bsa = new TextbausteinObj[bs.size()];

        for (int i = 0; i < bs.size(); i++) {
            bsa[i] = (TextbausteinObj) bs.toArray()[i];
        }

        return bsa;
    }

    /**
     *
     * @param reload
     * @return
     */
    public static NotizenObj[] getAlleNotizen(int status) {
        try {
            NotizenObj[] notizen = NotizenSQLMethods.loadNotizen(DatabaseConnection.open(), false, status);
            return notizen;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Notizen nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Notizen konnten nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notizen nicht laden");
        }

        return null;
    }

    /**
     * 
     * @param reload
     * @return
     */
    public static NotizenObj[] getEigeneNotizen(int status) {
        try {
            NotizenObj[] notizen = NotizenSQLMethods.loadNotizen(DatabaseConnection.open(), true, status);
            return notizen;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Notizen nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Notizen konnten nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notizen nicht laden");
        }

        return null;
    }

    public static BriefObj getBrief(int id) {
        try {
            BriefObj brief = BriefeSQLMethods.getBrief(DatabaseConnection.open(), id);
            return brief;
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Brief mit der Id " + id + " nicht laden.", e);
            ShowException.showException("Datenbankfehler: Der Brief mit der Id " + id + " konnte nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Brief nicht laden");
        }

        return null;
    }
}
