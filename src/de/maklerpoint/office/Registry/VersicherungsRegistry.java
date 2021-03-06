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
import de.maklerpoint.office.Sparten.SpartenObj;
import de.maklerpoint.office.Sparten.Tools.SpartenSQLMethods;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.Tools.ProdukteSQLMethods;
import de.maklerpoint.office.Versicherer.Tools.VersichererSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Waehrungen.Tools.WaehrungenSQLMethods;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.sql.SQLException;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class VersicherungsRegistry {

    public static SpartenObj[] SPARTEN = null;
    public static WaehrungenObj[] WAEHRUNGEN = null;
    public static VersichererObj[] VERSICHERER = null;
    public static ProduktObj[] PRODUKTE = null;

    /**
     * 
     * @param reload
     * @return
     */
    public static SpartenObj[] getSparten(boolean reload) {
        if (SPARTEN == null || reload == true) {
            try {
                SPARTEN = SpartenSQLMethods.loadSparten(DatabaseConnection.open());
                return SPARTEN;
            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte Sparten nicht aus der Datenbank laden", e);
                ShowException.showException("Die Spartenliste konnte nicht aus der Datenbank geladen werden. ",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sparten nicht laden");
            }
        } else {
            return SPARTEN;
        }

        return null;
    }

    /**
     * 
     * @param id
     * @param reload
     * @return
     */
    public static SpartenObj getSparte(int id) {
        try {
            SpartenObj sp = SpartenSQLMethods.getSparte(DatabaseConnection.open(), id);
            return sp;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Sparte mit der Id " + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Sparte mit der Id " + id + " konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sparte nicht laden");
        }
        return null;
    }

    /**
     *
     */
    public static WaehrungenObj[] getWaehrungen(boolean reload) {
        if (WAEHRUNGEN == null || reload == true) {
            try {
                WAEHRUNGEN = WaehrungenSQLMethods.loadWaehrungen(DatabaseConnection.open());
                return WAEHRUNGEN;
            } catch (SQLException e) {
                Log.databaselogger.fatal("Konnte Währungen nicht aus der Datenbank laden", e);
                ShowException.showException("Datenbankfehler: Die Währungen konnte nicht aus der Datenbank geladen werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Währungen nicht laden");
            }
        } else {
            return WAEHRUNGEN;
        }

        return null;
    }

    public static VersichererObj[] getVersicherer(int status) {

        try {
            VERSICHERER = VersichererSQLMethods.loadVersicherer(DatabaseConnection.open(), status);
//                System.out.println("length: " + VERSICHERER.length);
            return VERSICHERER;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Versicherungsgesellschaften nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Versicherungsgesellschaften konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Versicherungen nicht laden");
        }

        return null;
    }

    public static VersichererObj getVersicher(int id) {
        try {
            VersichererObj vs = VersichererSQLMethods.getVersicherer(DatabaseConnection.open(), id);
            return vs;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Versicherung mit der id " + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Versicherung mit der Id " + id
                    + " kontne nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Versicherung nicht laden");
        }

        return null;
    }

    public static ProduktObj[] getProduktVersicherer(int versid, int status) {
        try {
            ProduktObj[] pr = ProdukteSQLMethods.loadProdukte(DatabaseConnection.open(), versid, true, status);
            return pr;
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Produkte für die Versicherung nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Produkte für die Versicherung konnten "
                    + "nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Versicherungen nicht laden");
        }

        return null;
    }

    public static ProduktObj[] getProdukte(int status) {

        try {
            PRODUKTE = ProdukteSQLMethods.loadProdukte(DatabaseConnection.open(), status);
            return PRODUKTE;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die (Versicherungs-) Produkte nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die (Versicherungs-) Produkte konnte nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Versicherungen nicht laden");
        }

        return null;
    }

    public static ProduktObj getProdukt(int id) {
        if (id == -1) {
            return null;
        }

        try {
            ProduktObj prd = ProdukteSQLMethods.getProdukt(DatabaseConnection.open(), id);
            return prd;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte das Produkt mit der id " + id + " nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Das Produkt konnte nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkt nicht laden");
        }

        return null;
    }
    
    public static WaehrungenObj getWaehrung(int id) {
        try {
            WaehrungenObj waer = WaehrungenSQLMethods.getWaehrung(DatabaseConnection.open(), id);
            return waer;
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }
}
