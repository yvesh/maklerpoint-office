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

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Kalender.panelKalenderHolder;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Kalender.Aufgaben.Tools.AufgabenSQLMethods;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kalender.Termine.Tools.TermineSQLMethods;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Wiedervorlage.Tools.WiederVorlagenSQLMethods;
import de.maklerpoint.office.Wiedervorlage.WiedervorlagenObj;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KalenderRegistry {

    public static TerminObj[] benutzerTermine;
    public static long terminRefresh = -1;
    public static AufgabenObj[] benutzerAufgaben;
    public static long aufgabenRefresh = -1;
    public static WiedervorlagenObj[] wiedervorlagen;
    private static SimpleDateFormat df_geb = new SimpleDateFormat("dd.MM.yyyy");

    public static KundenObj[] getKundenGeburtstag(Date date) {
        ArrayList<KundenObj> al = new ArrayList<KundenObj>();

        KundenObj[] knd = KundenRegistry.getEigeneKunden(Status.NORMAL);
        Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal2.setTime(date);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Geburtstags Datum: " + df_geb.format(date));
        }

        if (knd == null) {
            return null;
        }

        for (int i = 0; i < knd.length; i++) {
            if (knd[i].getGeburtsdatum() != null) {
                try {
                    Date geb = df_geb.parse(knd[i].getGeburtsdatum());
                    cal.setTime(geb);
                    cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
                    geb = cal.getTime();

//                    System.out.println("Kundengeburtstag: " + df_geb.format(geb));
//                    LocalDate ld = new LocalDate(geb);

                    if (geb == date) {
//                        System.out.println("Geb: " + geb + " == " + date);
                        al.add(knd[i]);
                    }

                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        if (al.isEmpty()) {
            return null;
        }

        //Object[] kndl = al.toArray();
        KundenObj kndAr[] = new KundenObj[al.size()];
        kndAr = al.toArray(kndAr);

        return knd;
    }

    public static FirmenObj[] getFirmenGeburtstag(Date date) {
        ArrayList<FirmenObj> al = new ArrayList<FirmenObj>();

        FirmenObj[] knd = KundenRegistry.getFirmenKunden(true, Status.NORMAL);
        Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal2.setTime(date);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Jubiläums Datum: " + df_geb.format(date));
        }

        if (knd == null) {
            return null;
        }

        for (int i = 0; i < knd.length; i++) {
            if (knd[i].getFirmenGruendungDatum() != null) {
                Date geb = knd[i].getFirmenGruendungDatum();
                cal.setTime(geb);
                cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
                geb = cal.getTime();

//                    System.out.println("Kundengeburtstag: " + df_geb.format(geb));
//                    LocalDate ld = new LocalDate(geb);

                if (geb == date) {
//                        System.out.println("Geb: " + geb + " == " + date);
                    al.add(knd[i]);
                }
            }
        }

        if (al.isEmpty()) {
            return null;
        }

        //Object[] kndl = al.toArray();
        FirmenObj kndAr[] = new FirmenObj[al.size()];
        kndAr = al.toArray(kndAr);

        return knd;
    }

    public static BenutzerObj[] getBenutzerGeburtstage(Date date) {
        ArrayList<BenutzerObj> al = new ArrayList<BenutzerObj>();

        BenutzerObj[] ben = BenutzerRegistry.getAllBenutzer(Status.NORMAL);
        Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal2.setTime(date);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Geburtstags Datum: " + df_geb.format(date));
        }

        if (ben == null) {
            return null;
        }

        for (int i = 0; i < ben.length; i++) {
            if (ben[i].getGeburtsDatum() != null) {
                try {
                    Date geb = df_geb.parse(ben[i].getGeburtsDatum());
                    cal.setTime(geb);
                    cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
                    geb = cal.getTime();

//                    System.out.println("Kundengeburtstag: " + df_geb.format(geb));
//                    LocalDate ld = new LocalDate(geb);

                    if (geb == date) {
//                        System.out.println("Geb: " + geb + " == " + date);
                        al.add(ben[i]);
                    }

                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        if (al.isEmpty()) {
            return null;
        }

        //Object[] kndl = al.toArray();
        BenutzerObj benAr[] = new BenutzerObj[al.size()];
        benAr = al.toArray(benAr);

        return benAr;
    }

    /**
     * 
     * @return
     */
    public static AufgabenObj[] getAufgaben() {
        return getEigeneAufgaben(BasicRegistry.currentUser.getId(), Status.NORMAL);
    }


    public static AufgabenObj[] getEigeneAufgaben(int benutzerId, int status) {
        Preferences prefs = Preferences.userRoot().node(panelKalenderHolder.class.getName());;
        boolean getPublicTermine = prefs.getBoolean("showPublic", true);

        try {
            return AufgabenSQLMethods.loadEigeneAufgaben(DatabaseConnection.open(), benutzerId,
                    getPublicTermine, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Aufgaben nicht aus der Datenbank laden", e);
            ShowException.showException("Die Aufgabenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Aufgaben nicht laden");
        }


        return null;
    }
    
    
    /**
     * 
     * @param forcereload
     * @param benutzerId
     * @return
     */
    public static AufgabenObj[] getBenutzerAufgaben(int benutzerId, int status) {
        Preferences prefs = Preferences.userRoot().node(panelKalenderHolder.class.getName());;
        boolean getPublicTermine = prefs.getBoolean("showPublic", true);

        try {
            return AufgabenSQLMethods.loadBenutzerAufgaben(DatabaseConnection.open(), benutzerId,
                    getPublicTermine, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Aufgaben nicht aus der Datenbank laden", e);
            ShowException.showException("Die Aufgabenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Aufgaben nicht laden");
        }


        return null;
    }

    public static AufgabenObj[] getVersichererAufgaben(int versid, int status) {
        try {
            return AufgabenSQLMethods.loadVersichererAufgaben(DatabaseConnection.open(), versid, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Aufgaben nicht aus der Datenbank laden", e);
            ShowException.showException("Die Aufgabenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Aufgaben nicht laden");
        }

        return null;
    }

    public static AufgabenObj[] getKundenAufgaben(String kennung, int status) {
        try {
            return AufgabenSQLMethods.loadKundenAufgaben(DatabaseConnection.open(), kennung, status);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Aufgaben nicht aus der Datenbank laden", e);
            ShowException.showException("Die Aufgaben konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Aufgaben nicht laden");
        }

        return null;
    }

    /**
     * 
     * @return
     */
    public static TerminObj[] getTermine() {
        return getTermine(false, BasicRegistry.currentUser.getId());
    }

    /**
     * 
     * @param forcereload
     * @return
     */
    public static TerminObj[] getTermine(boolean forcereload) {
//         System.out.println("id: " + BasicRegistry.currentUser.getId());
        return getTermine(forcereload, BasicRegistry.currentUser.getId());
    }

    /**
     * 
     * @param forcereload
     * @param benutzerId
     * @return
     */
    public static TerminObj[] getTermine(boolean forcereload, int benutzerId) {
        Preferences prefs = Preferences.userRoot().node(panelKalenderHolder.class.getName());
        boolean getPublicTermine = prefs.getBoolean("showPublic", true);

        if (benutzerTermine == null || terminRefresh == -1 
                || forcereload == true) {
            try {
                benutzerTermine = TermineSQLMethods.loadTermine(DatabaseConnection.open(), benutzerId, getPublicTermine);
                terminRefresh = new Date(System.currentTimeMillis()).getTime();
                return benutzerTermine;
            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte Termine nicht aus der Datenbank laden", e);
                ShowException.showException("Die Terminliste konnte nicht aus der Datenbank geladen werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht laden");
            }
        } else {
            if ((new Date(System.currentTimeMillis()).getTime() - (60 * 1000)) > terminRefresh) {
                try {
                    benutzerTermine = TermineSQLMethods.loadTermine(DatabaseConnection.open(), benutzerId, getPublicTermine);
                    terminRefresh = new Date(System.currentTimeMillis()).getTime();
                    return benutzerTermine;
                } catch (SQLException e) {
                    Log.databaselogger.fatal("Fehler: Konnte Termine nicht aus der Datenbank laden", e);
                    ShowException.showException("Die Terminliste konnte nicht aus der Datenbank geladen werden. "
                            + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht laden");
                }
            } else {
                return benutzerTermine;
            }
        }


        return null;
    }

    public static TerminObj[] getTermine(String kennung) {
        try {
            TerminObj[] termine = TermineSQLMethods.loadTermine(DatabaseConnection.open(), kennung);
            return termine;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Termine nicht aus der Datenbank laden", e);
            ShowException.showException("Die Terminliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht laden");
        }

        return null;
    }

    public static TerminObj[] getVersichererTermine(int id) {
        try {
            TerminObj[] termine = TermineSQLMethods.loadVersichererTermine(DatabaseConnection.open(), id);
            return termine;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Termine nicht aus der Datenbank laden", e);
            ShowException.showException("Die Terminliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht laden");
        }

        return null;
    }

    public static WiedervorlagenObj[] getWiedervorlagen(boolean eigene) {
        try {
            wiedervorlagen = WiederVorlagenSQLMethods.loadWiedervorlagen(DatabaseConnection.open(), eigene);
            return wiedervorlagen;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Wiedervorlagen nicht aus der Datenbank laden", e);
            ShowException.showException("Die Wiedervorlagenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Wiedervorlagen nicht laden");
        }
        return null;
    }

    public static WiedervorlagenObj[] getWiedervorlagen(String kennung) {
        try {
            WiedervorlagenObj[] wv = WiederVorlagenSQLMethods.loadWiedervorlagen(DatabaseConnection.open(), kennung);
            return wv;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Wiedervorlagen nicht aus der Datenbank laden", e);
            ShowException.showException("Die Wiedervorlagenliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Wiedervorlagen nicht laden");
        }
        return null;
    }
}
