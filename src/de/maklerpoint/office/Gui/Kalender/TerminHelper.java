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

package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kalender.Termine.Tools.TermineSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Tags.TagObj;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TerminHelper {

    /**
     * 
     * @param id
     * @param panel
     */

    public static void deleteTermin(int id, panelTagesAnsicht panel) {
        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin wirklich löschen?",
                                                "Bestätigung: löschen", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.NO_OPTION)
            return;

        try {
            TermineSQLMethods.deleteFromTermine(DatabaseConnection.open(), id);
            panel.loadPanels();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Termin mit der Id " + id + " nicht löschen", e);
            ShowException.showException("Der Termin mit der Id " + id + " konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termin nicht löschen");            
        }

    }

    /**
     * 
     * @param id
     * @param panel
     */

    public static void deleteTermin(int id, panelWochenAnsicht panel) {
        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin wirklich löschen?",
                                                "Bestätigung: löschen", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.NO_OPTION)
            return;

        try {
            TermineSQLMethods.deleteFromTermine(DatabaseConnection.open(), id);
            panel.loadPanels();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Termin mit der Id " + id + " nicht löschen", e);
            ShowException.showException("Der Termin mit der Id " + id + " konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termin nicht löschen");            
        }

    }

    /**
     * 
     * @param id
     */

    public static void deleteTermin(int id) {
        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin wirklich löschen?",
                                                "Bestätigung: löschen", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.NO_OPTION)
            return;

        try {
            TermineSQLMethods.deleteFromTermine(DatabaseConnection.open(), id);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Der Termin mit der Id " + id + " konnte nicht gelöscht werden", e);
            ShowException.showException("Der Termin mit der Id " + id + " konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termin nicht löschen");            
        }

    }

    public static void setTerminTag(TerminObj termin, TagObj tag) {
        
    }


}
