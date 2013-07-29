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
import de.maklerpoint.office.Benutzer.Tools.BenutzerSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.NoUserException;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Nachrichten.MessageObj;
import de.maklerpoint.office.Nachrichten.Tools.MessageSQLMethods;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import java.sql.SQLException;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerRegistry {

    public static BenutzerObj[] allUser;
    public static MessageObj[] empfNachrichten;
    public static MessageObj[] sendNachrichten;

    public static BenutzerObj[] getAllBenutzer(int status) {
        try {
            allUser = BenutzerSQLMethods.loadAlleBenutzer(DatabaseConnection.open(), status);
            return allUser;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Benutzerliste nicht aus der Datenbank laden", e);
            ShowException.showException("Die Benutzerliste konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Benutzerliste nicht laden");
        } catch (NoUserException e) {

            if (status == Status.NORMAL || status == -1) {
                // TODO Fix this
                if (Config.getConfigBoolean("offlineModus", false)) {
                    Log.logger.fatal("Gehe zurück in den Datenbankmodus - Keine Benutzer in der lokalen Datenbank?");
                    Config.setBoolean("offlineModus", false);

                    try {
                        allUser = BenutzerSQLMethods.loadAlleBenutzer(DatabaseConnection.open(), status);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (NoUserException ex) {
                        Exceptions.printStackTrace(ex);
                    }


                    if (allUser != null) {
                        return allUser;
                    }
                }

                Log.databaselogger.fatal("Fehler: Es sind keine Benutzer in der Datenbank vorhanden", e);
                ShowException.showException("In der Datenbank sind keine Benutzer vorhanden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Keine Benutzer vorhanden");
            }

        }
        return null;
    }

    /**
     * Compatibility
     * @param id
     * @param reload
     * @return 
     */
    public static BenutzerObj getBenutzer(int id, boolean reload) {
        return getBenutzer(id);
    }

    public static BenutzerObj getBenutzer(int id) {
        if (id == -1) {
            return systemBenutzer();
        }

        if (id == BasicRegistry.currentUser.getId()) // Performance
        {
            return BasicRegistry.currentUser;
        }

        try {
            BenutzerObj ben = BenutzerSQLMethods.getBenutzer(DatabaseConnection.open(), id);
            return ben;
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Der Benutzer mit der Id " + id + " konnte nicht geladen werden", e);
            ShowException.showException("Der Benutzer " + id + "  konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Benutzer nicht laden");
        }
        return null;
    }

    public static BenutzerObj systemBenutzer() {
        BenutzerObj ben = new BenutzerObj();
        ben.setId(-1);
        ben.setLevel(0);
        ben.setKennung("MaklerPoint");
        ben.setVorname("Benutzer");
        ben.setNachname("System");

        ben.setEmail("support@maklerpoint.de");
        ben.setTelefon("(089) 322 08 134");

        ben.setHomepage("http://www.maklerpoint.de");

        return ben;
    }

    /**
     * 
     * @param reload
     * @param status
     * @return 
     */
    public static MessageObj[] getEmpfNachrichten() {
        return getEmpfNachrichten(true, Status.NORMAL);
    }

    /**
     * 
     * @param reload
     * @param status
     * @return 
     */
    public static MessageObj[] getEmpfNachrichten(boolean reload, int status) {
        if (reload == false && empfNachrichten != null) {
            return empfNachrichten;
        }
        try {

            empfNachrichten = MessageSQLMethods.getAllMessagesEmpfang(DatabaseConnection.open(),
                    BasicRegistry.currentUser.getId(), status);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Messages nicht laden", e);
            ShowException.showException("Die privaten Nachrichten konnten nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte privaten Nachrichten nicht laden");
        }

        return empfNachrichten;
    }

    public static MessageObj[] getSendNachrichten() {
        return getSendNachrichten(true, Status.NORMAL);
    }

    /**
     * 
     * @param reload
     * @param status
     * @return 
     */
    public static MessageObj[] getSendNachrichten(boolean reload, int status) {
        if (reload == false && sendNachrichten != null) {
            return sendNachrichten;
        }
        try {
            sendNachrichten = MessageSQLMethods.getAllMessagesSend(DatabaseConnection.open(),
                    BasicRegistry.currentUser.getId(), status);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Messages nicht laden", e);
            ShowException.showException("Die privaten Nachrichten konnten nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte privaten Nachrichten nicht laden");
        }

        return sendNachrichten;
    }

    public static boolean isNewMail() {
        try {
            return MessageSQLMethods.checkNewMessages(DatabaseConnection.open(),
                    BasicRegistry.currentUser.getId(), Status.NORMAL);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte nicht auf neue Nachrichten prüfen", e);
            ShowException.showException("Konnte nicht auf neue Nachrichten prüfen. "
                    + "Bitte überprüfen Sie die Verbindungseinstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte nicht auf neue Nachrichten prüfen.");
        }
        return false;
    }
}
