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
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenKennungHelper;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayTools;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenRegistry {

    public static Object[] getGeworbeneKunden(String werberkdnr) {
        try {
            KundenObj[] knd = KundenSQLMethods.loadgeworbeneKunden(DatabaseConnection.open(), werberkdnr);
            FirmenObj[] fir = FirmenSQLMethods.loadgeworbenenFirmenKunden(DatabaseConnection.open(), werberkdnr);

            Object[] allknd = ArrayTools.arrayMerge(knd, fir);
            return allknd;

        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die von " + werberkdnr + " geworbenen Kunden nicht aus der Datenbank laden", e);
            ShowException.showException("Der von " + werberkdnr + " geworbenen Kunden konnten nicht aus der Datenbank geladen werden. "
                    + "Bitte überprüfen Sie die Datenbankeinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde nicht laden");
        }

        return null;
    }

    /**
     * Alle aktiven Kunden (STATUS.NORMAL!!)
     * @param reload
     * @return
     */
    public static Object[] getAlleAktivenKunden() {

        KundenObj[] knd = getKunden(Status.NORMAL);
        FirmenObj[] fir = getFirmenKunden(false, Status.NORMAL);

        return ArrayTools.arrayMerge(knd, fir);
    }

    public static String getNewKundenNummer() {
        try {
            return KundenKennungHelper.getNextKundennummer(DatabaseConnection.open());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte keine Kunden-Nr. für den neuen Kunden generieren", e);
            ShowException.showException("Es konnte keine Kunden-Nr. für den neuen Kunden generiert werden. "
                    + "Bitte überprüfen Sie die Datenbankeinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden-Nr. nicht generieren");
        }

        return null;
    }

    /**
     * 
     * @param reload
     * @return
     */
    public static Object[] getAlleEigenenKunden() {
        KundenObj[] knd = getEigeneKunden(Status.NORMAL);
        FirmenObj[] fir = getFirmenKunden(true, Status.NORMAL);

        return ArrayTools.arrayMerge(knd, fir);
    }

    /**
     * TODO Update to query
     * @param reload
     * @param kennung
     * @return
     */
    public static Object getKunde(String kennung) {

        Object kunde = getPrivatkunde(kennung);

        if (kunde == null) {
            kunde = getFirmenKunde(kennung);
        }

        return kunde;
    }

    public static KundenObj getPrivatkunde(String kennung) {
        try {
            return KundenSQLMethods.loadKunde(DatabaseConnection.open(), kennung);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte den Kunden mit der Kennung " + kennung + " nicht aus der Datenbank laden", e);
            ShowException.showException("Der Kunde mit der Kennung " + kennung + " konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte überprüfen Sie die Datenbankeinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde nicht laden");
        }

        return null;
    }

    public static KundenObj[] getKunden() {
        return getKunden(true, Status.NORMAL);
    }

    /**
     * Compatibilty
     * @param forcereload
     * @param status
     * @return 
     * @deprecated 
     */
    public static KundenObj[] getKunden(boolean forcereload, int status) {
        return getKunden(status);
    }

    public static KundenObj[] getKunden(int status) {
        try {
            return KundenSQLMethods.loadKunden(DatabaseConnection.open(), status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Kunden nicht aus der Datenbank laden", e);
            ShowException.showException("Die Kundenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte überprüfen Sie die Datenbankeinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht laden");
        }
        return null;
    }

    /**
     * Compatibilty
     * @return 
     * @deprecated 
     */
    public static KundenObj[] getEigeneKunden() {
        return getEigeneKunden(true, Status.NORMAL);
    }

    /**
     * Compatibilty
     * @param forcereload
     * @param status
     * @return 
     * @deprecated 
     */
    public static KundenObj[] getEigeneKunden(boolean forcereload, int status) {
        return getEigeneKunden(status);
    }

    public static KundenObj[] getEigeneKunden(int status) {
        try {
            return KundenSQLMethods.loadEigeneKunden(DatabaseConnection.open(), BasicRegistry.currentUser, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte eigene Kunden nicht aus der Datenbank laden", e);
            ShowException.showException("Die eigene Kundenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte überprüfen Sie die Datenbankeinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht laden");
        }
        return null;
    }

    /**
     * Compatibilty
     * @param reload
     * @param eigene
     * @return 
     * @deprecated
     */
    public static FirmenObj[] getFirmenKunden(boolean reload, boolean eigene, int status) {
        return getFirmenKunden(eigene, status);
    }

    public static FirmenObj[] getFirmenKunden(boolean eigene, int status) {
        if (eigene) {
            try {
                return FirmenSQLMethods.loadFirmenKunden(DatabaseConnection.open(), true, status);

            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte eigene Geschäftskunden nicht aus der Datenbank laden", e);
                ShowException.showException("Die eigene Geschäftskundenliste konnte nicht aus der Datenbank geladen werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte eigene Geschäftskunden nicht laden");
            }
        } else {
            try {
                return FirmenSQLMethods.loadFirmenKunden(DatabaseConnection.open(), false, status);
            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte Geschäftskunden nicht aus der Datenbank laden", e);
                ShowException.showException("Die Geschäftskundenliste konnte nicht aus der Datenbank geladen werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Geschäftskunden nicht laden");
            }
        }
        return null;
    }

    public static FirmenObj getFirmenKunde(String kennung) {
        try {
            return FirmenSQLMethods.getFirmenKunde(DatabaseConnection.open(), kennung);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Geschäftskunde mit der Kennung " + kennung + " nicht aus der Datenbank laden", e);
            ShowException.showException("Der Geschäftskunde konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Geschäftskunde nicht laden");
        }
        return null;
    }

    /**
     * Compat
     * @param id
     * @param reload
     * @return 
     */
    public static FirmenObj getFirmenKunde(int id, boolean reload) {
        return getFirmenKunde(id);
    }

    public static FirmenObj getFirmenKunde(int id) {
        try {
            return FirmenSQLMethods.getFirmenKunde(DatabaseConnection.open(), id);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Geschäftskunde mit der Id " + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Der Geschäftskunde konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Geschäftskunde nicht laden");
        }
        return null;
    }

    public static boolean isPrivatKunde(Object knd) {
        if (knd.getClass().equals(KundenObj.class)) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param knd 
     * @return success
     */
    public static boolean archiveKunde(Object knd) {
        if (knd.getClass().equals(KundenObj.class)) {
            return archivePK((KundenObj) knd);
        } else if (knd.getClass().equals(FirmenObj.class)) {
            return archiveGK((FirmenObj) knd);
        } else {
            throw new NullPointerException("Kein Kunden POJO");
        }
    }
    
    /**
     * 
     * @param knd
     * @return success
     */
    
    public static boolean deleteKunde(Object knd) {
        if (knd.getClass().equals(KundenObj.class)) {
            return deletePK((KundenObj) knd);
        } else if (knd.getClass().equals(FirmenObj.class)) {
            return deleteGK((FirmenObj) knd);
        } else {
            throw new NullPointerException("Kein Kunden POJO");
        }
    }
    
    /**
     * 
     * @param kunde 
     * @return success
     */
    public static boolean archivePK(KundenObj kunde) {
        if (kunde == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kunden wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            KundenSQLMethods.archiveFromkunden(DatabaseConnection.open(), kunde.getId());
            //TODO Add File archive
        } catch (SQLException e) {
            Log.databaselogger.fatal("Der Kunde konnte nicht archiviert werden", e);
            ShowException.showException("Datenbankfehler: Der Kunde konnten nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht archivieren");
        }

        return true;
    }

    /**
     * 
     * @param gk 
     * @return success
     */
    public static boolean archiveGK(FirmenObj gk) {
        if (gk == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kunden wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            FirmenSQLMethods.archiveFromfirmenkunden(DatabaseConnection.open(), gk.getId());
            // TODO archive files
        } catch (SQLException ex) {
            Log.databaselogger.fatal("Der Kunde konnte nicht archiviert werden", ex);
            ShowException.showException("Datenbankfehler: Der Kunde konnten nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, ex, "Schwerwiegend: Konnte Kunden nicht archivieren");
        }
        
        return true;
    }
    
    
    /**
     * 
     * @param kunde 
     * @return success
     */
    public static boolean deletePK(KundenObj kunde) {
        if (kunde == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kunden wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            KundenSQLMethods.deleteFromkunden(DatabaseConnection.open(), kunde.getId());
            //TODO Add File archive
        } catch (SQLException e) {
            Log.databaselogger.fatal("Der Kunde konnte nicht gelöscht werden", e);
            ShowException.showException("Datenbankfehler: Der Kunde konnten nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht archivieren");
        }

        return true;
    }
    
    /**
     * 
     * @param gk
     * @return success
     */
    public static boolean deleteGK(FirmenObj gk) {
        if (gk == null) {
            return false;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kunden wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try {
            FirmenSQLMethods.deleteFromfirmenkunden(DatabaseConnection.open(), gk.getId());
            // TODO archive files
        } catch (Exception ex) {
            Log.databaselogger.fatal("Der Kunde konnte nicht gelöscht werden", ex);
            ShowException.showException("Datenbankfehler: Der Kunde konnten nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, ex, "Schwerwiegend: Konnte Kunden nicht löschen");
        }
        
        return true;
    }
}
