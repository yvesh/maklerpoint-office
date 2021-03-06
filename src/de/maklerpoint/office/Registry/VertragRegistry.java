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
import de.maklerpoint.office.Konstanten.Vertraege;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schaeden.Tools.SchaedenSQLMethods;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Stoerfalle.Tools.StoerfaelleSQLMethods;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Vertraege.Tools.VertraegeSQLMethods;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class VertragRegistry {

    public static VertragObj[] getKundenVertraege(String kennung) {
        return getKundenVertraege(kennung, -1);
    }

    /**
     * Vorsicht andere Status!!
     * @param kennung
     * @param status
     * @return 
     */
    public static VertragObj[] getKundenVertraege(String kennung, int status) {
        try {
            return VertraegeSQLMethods.getKundenVertraege(DatabaseConnection.open(), kennung, status);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Verträge für den Kunden "
                    + kennung + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Verträge für den Kunden " + kennung + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Verträge nicht laden");
        }
        return null;
    }

    public static VertragObj[] loadKundenVertraege(boolean eigene, int status) {
        try {
            if (eigene) {
                return VertraegeSQLMethods.getBenutzerVertraege(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), status);
            } else {
                return VertraegeSQLMethods.loadVertraege(DatabaseConnection.open(), status);
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Verträge für den nicht aus der Datenbank laden", e);
            ShowException.showException("Die Verträge konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Verträge nicht laden");
        }
        return null;
    }

    public static VertragObj getVertrag(int id) {
        if (id == -1) {
            return null;
        }

        try {
            return VertraegeSQLMethods.getVertrag(DatabaseConnection.open(), id);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte den Vertrag mit der Id "
                    + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Der Vertrag mit der Id " + id + " konnte"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Vertrag nicht laden");
        }

        return null;
    }

    public static StoerfallObj[] getVertragStoerfaelle(int vtrId, int status) {
        try {
            return StoerfaelleSQLMethods.getVertragStoerfaelle(DatabaseConnection.open(), vtrId, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Störfalle zum Vertrag mit der Id "
                    + vtrId + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Störfälle zum Vertrag mit der Id " + vtrId + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfälle nicht laden");
        }

        return null;
    }

    public static StoerfallObj[] getKundenStoerfaelle(String kdnr, int status) {
        try {
            return StoerfaelleSQLMethods.getKundenStoerfaelle(DatabaseConnection.open(), kdnr, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Störfalle zum Kunden mit der Kennung "
                    + kdnr + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Störfälle zum Kunden mit der Kennung " + kdnr + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfälle nicht laden");
        }

        return null;
    }

    public static StoerfallObj[] getBenutzerStoerfaelle(int benid, int status) {
        try {
            return StoerfaelleSQLMethods.getBenutzerStoerfaelle(DatabaseConnection.open(), benid, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Störfalle des Benutzers mit der Id "
                    + benid + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Störfälle des Benutzers mit der Id " + benid + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfälle nicht laden");
        }

        return null;
    }

    public static StoerfallObj[] getAlleStoerfaelle(int status) {
        try {
            return StoerfaelleSQLMethods.loadAlleStoerfaelle(DatabaseConnection.open(), status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte alle Störfalle nicht aus der Datenbank laden", e);
            ShowException.showException("Die Störfälle konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfälle nicht laden");
        }

        return null;
    }

    public static StoerfallObj getStoerfall(int id) {
        try {
            return StoerfaelleSQLMethods.getStoerfall(DatabaseConnection.open(), id);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Störfall mit der Id "
                    + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Der Störfall mit der Id " + id + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfall nicht laden");
        }

        return null;
    }

    /**
     * Schäden
     **/
    public static SchadenObj[] getVertragSchaeden(int vtrId, int status) {
        try {
            return SchaedenSQLMethods.getVertragSchaeden(DatabaseConnection.open(), vtrId, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Schäden zum Vertrag mit der Id "
                    + vtrId + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Schäden zum Vertrag mit der Id " + vtrId + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schäden nicht laden");
        }

        return null;
    }

    public static SchadenObj[] getBenutzerSchaeden(int benid, int status) {
        try {
            return SchaedenSQLMethods.getBenutzerSchaeden(DatabaseConnection.open(), benid, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Schäden zum Benutzer mit der Id "
                    + benid + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Schäden zum Benutzer mit der Id " + benid + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schäden nicht laden");
        }

        return null;
    }

    public static SchadenObj[] getAlleSchaeden(int status) {
        try {
            return SchaedenSQLMethods.loadSchaeden(DatabaseConnection.open(), status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Schäden nicht aus der Datenbank laden", e);
            ShowException.showException("Die Schadensfälle konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schäden nicht laden");
        }

        return null;
    }

    /**
     * 
     * @param kdnr
     * @param status
     * @return Schäden mit der kdnr
     */
    public static SchadenObj[] getKundenSchaeden(String kdnr, int status) {
        try {
            return SchaedenSQLMethods.getKundenSchaeden(DatabaseConnection.open(), kdnr, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Schäden zum Kunden mit der Kennung "
                    + kdnr + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Schäden zum Kunden mit der Kennung " + kdnr + " konnten"
                    + " nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schäden nicht laden");
        }

        return null;
    }

    /**
     * 
     * @param id
     * @return SchadenObj
     */
    public static SchadenObj getSchaden(int id) {
        try {
            return SchaedenSQLMethods.getSchaden(DatabaseConnection.open(), id);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte den Schaden mit der Id "
                    + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Die Schaden mit der Id " + id
                    + " konnte nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schaden nicht laden");
        }

        return null;
    }
//    
//    public static String getVertragGrpName(int id) {
//        try {
//            VertraegeSQLMethods.
//        } catch (Exception e) {
//             Log.databaselogger.fatal("Fehler: Konnte die Vertragsgruppe mit der Id " 
//                    + id + " nicht aus der Datenbank laden", e);
//            ShowException.showException("Die Vertragsgruppe mit der Id " + id + " konnte"
//                    + " nicht aus der Datenbank geladen werden.",
//                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Vertragsgruppe nicht laden"); 
//        }
//        
//        return null;
//    }
    
    /**
     * 
     * @param vtr
     * @return success
     */
    public static boolean archiveVertrag(VertragObj vtr) {
        if (vtr == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Vertrag wirklich archivieren?",
                    "Wirklich archivieren?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            VertraegeSQLMethods.archiveFromVertraege(DatabaseConnection.open(), vtr);
            //TODO Add Datei 
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Vertrag nicht archivieren", e);
            ShowException.showException("Datenbankfehler: Der Vertrag konnte nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Vertrag nicht archivieren");
        }
        
        return true;
    }
    /**
     * 
     * @param vtr
     * @return success
     */
    public static boolean deleteVertrag(VertragObj vtr) {
        if (vtr == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Vertrag wirklich löschen?",
                    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            VertraegeSQLMethods.deleteFromVertraege(DatabaseConnection.open(), vtr);
            //TODO Add Datei 
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Vertrag nicht löschen", e);
            ShowException.showException("Datenbankfehler: Der Vertrag konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Vertrag nicht löschen");
        }
        
        return true;
    }
    
}
