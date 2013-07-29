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
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Dokumente.Tools.DokumentenSQLMethods;
import de.maklerpoint.office.Dokumente.Tools.WissenDokumenteSQLMethods;
import de.maklerpoint.office.Dokumente.WissenDokumentenObj;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemBenutzer;
import de.maklerpoint.office.Filesystem.FilesystemKunden;
import de.maklerpoint.office.Filesystem.FilesystemProdukte;
import de.maklerpoint.office.Filesystem.FilesystemSchaeden;
import de.maklerpoint.office.Filesystem.FilesystemStoerfaelle;
import de.maklerpoint.office.Filesystem.FilesystemVersicherer;
import de.maklerpoint.office.Filesystem.FilesystemVertraege;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.FileCopy;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DokumentenRegistry {

    public static WissenDokumentenObj[] wissensDokumente = null;

    public static WissenDokumentenObj[] getWissenDokumente() {
        try {
            wissensDokumente = WissenDokumenteSQLMethods.loadWissenDokumente(DatabaseConnection.open());
            return wissensDokumente;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Dokumente nicht aus der Datenbank laden", e);
            ShowException.showException("Die Wissens Dokumente Liste konnte nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dokumentenliste nicht laden");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Archiv Dok
     * @param dok 
     */
    public static void archivDokument(DokumentenObj dok) {
        // TODO testen
        if (dok.getBenutzerId() != BasicRegistry.currentUser.getId()
                && !(Security.isAllowed(SecurityTasks.DOKUMENT_ARCHIVE_ANDERERBENUTZER))) {
            Log.logger.warn("Benutzer " + BasicRegistry.currentUser
                    + " hatte nicht die nötigen Rechte um die Datei \""
                    + dok.getFullPath() + "\" zu archivieren");
            JOptionPane.showMessageDialog(null, "Sie haben nicht die nötigen Rechte um die Datei zu archivieren.",
                    "Fehler: Keine Rechte", JOptionPane.WARNING_MESSAGE);
            return;
        }

//        System.out.println("Dok id: " + dok.getId());
//        System.out.println("Dok: " + dok.toString());
//        System.out.println("Dok kunde: " + dok.getKundenKennung());
//        
        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Dokument wirklich archivieren?",
                    "Wirklich archivieren?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Herausfinden was für ein Dokument

        File file = new File(dok.getFullPath());

        if (!file.exists()) {
            Log.logger.warn("Die ausgewählte Datei " + dok.getFullPath() + " existiert nicht");
            JOptionPane.showMessageDialog(null, "Die ausgewählte Datei wurde nicht gefunden. Bitte überprüfen Sie den Dateipfad",
                    "Die Datei existiert nicht", JOptionPane.WARNING_MESSAGE);

            return;
        }


        if (dok.getVersichererId() != -1) {
            String archivefilep = FilesystemVersicherer.getVersichererArchivePath(dok.getVersichererId())
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getProduktId() != -1) {
            String archivefilep = FilesystemProdukte.getProduktArchivePath(
                    VersicherungsRegistry.getProdukt(dok.getProduktId()))
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getStoerId() != -1) {
            String archivefilep = FilesystemStoerfaelle.getStoerfallArchivePath(
                    VertragRegistry.getStoerfall(dok.getStoerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getSchadenId() != -1) {
            String archivefilep = FilesystemSchaeden.getSchadenArchivePath(
                    VertragRegistry.getSchaden(dok.getStoerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getVertragId() != -1) {
            String archivefilep = FilesystemVertraege.getVertragArchivePath(
                    VertragRegistry.getVertrag(dok.getVertragId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (!dok.getKundenKennung().equalsIgnoreCase("-1")) {
            String archivefilep = FilesystemKunden.getKundenArchivePath(dok.getKundenKennung())
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getBenutzerId() != -1) {
            String archivefilep = FilesystemBenutzer.getBenutzerArchivePath(
                    BenutzerRegistry.getBenutzer(dok.getBenutzerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.ARCHIVED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else {
            //FALLBACK
            Log.logger.warn("Konnte Dokument " + dok.getFullPath()
                    + " keinem Typ zuordnen");
        }

        try {
            boolean upd = DokumentenSQLMethods.updatedokumente(DatabaseConnection.open(), dok);
            if (!upd) {
                Log.databaselogger.fatal("Das Dokument konnte nicht aktualisiert werden");
                ShowException.showException("Datenbankfehler: Das Dokument konnte nicht aktualisiert werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, "Schwerwiegend: Konnte Dokument nicht aktualisieren");
            }

        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte das Dokument nicht archivieren", e);
            ShowException.showException("Datenbankfehler: Das Dokument konnte nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dokument nicht archivieren");
        }
    }

    /**
     * 
     * Archiv Dok
     * @param dok 
     */
    public static void deleteDokument(DokumentenObj dok) {
        // TODO testen
        if (dok.getBenutzerId() != BasicRegistry.currentUser.getId()
                && !(Security.isAllowed(SecurityTasks.DOKUMENT_ARCHIVE_ANDERERBENUTZER))) {
            Log.logger.warn("Benutzer " + BasicRegistry.currentUser
                    + " hatte nicht die nötigen Rechte um die Datei \""
                    + dok.getFullPath() + "\" zu löschen");
            JOptionPane.showMessageDialog(null, "Sie haben nicht die nötigen Rechte um die Datei zu löschen.",
                    "Fehler: Keine Rechte", JOptionPane.WARNING_MESSAGE);
            return;
        }

//        System.out.println("Dok id: " + dok.getId());
//        System.out.println("Dok: " + dok.toString());
//        System.out.println("Dok kunde: " + dok.getKundenKennung());
//        
        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Dokument wirklich löschen?",
                    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Herausfinden was für ein Dokument

        File file = new File(dok.getFullPath());

        if (!file.exists()) {
            Log.logger.warn("Die ausgewählte Datei " + dok.getFullPath() + " existiert nicht");
            JOptionPane.showMessageDialog(null, "Die ausgewählte Datei wurde nicht gefunden. Bitte überprüfen Sie den Dateipfad",
                    "Die Datei existiert nicht", JOptionPane.WARNING_MESSAGE);

            return;
        }


        if (dok.getVersichererId() != -1) {
            String archivefilep = FilesystemVersicherer.getVersichererDeletePath(dok.getVersichererId())
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getProduktId() != -1) {
            String archivefilep = FilesystemProdukte.getProduktDeletePath(
                    VersicherungsRegistry.getProdukt(dok.getProduktId()))
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getStoerId() != -1) {
            String archivefilep = FilesystemStoerfaelle.getStoerfallDeletePath(
                    VertragRegistry.getStoerfall(dok.getStoerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getSchadenId() != -1) {
            String archivefilep = FilesystemSchaeden.getSchadenDeletePath(
                    VertragRegistry.getSchaden(dok.getStoerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getVertragId() != -1) {
            String archivefilep = FilesystemVertraege.getVertragDeletePath(
                    VertragRegistry.getVertrag(dok.getVertragId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (!dok.getKundenKennung().equalsIgnoreCase("-1")) {
            String archivefilep = FilesystemKunden.getKundenDeletedPath(dok.getKundenKennung())
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else if (dok.getBenutzerId() != -1) {
             String archivefilep = FilesystemBenutzer.getBenutzerArchivePath(
                    BenutzerRegistry.getBenutzer(dok.getBenutzerId())) // TODO catch Exception falls null
                    + File.separator + file.getName();
            File archivefile = new File(archivefilep);

            FileCopy fc = new FileCopy();
            fc.copy(file, archivefile);

            file.delete();

            dok.setStatus(Status.DELETED);
            dok.setFullPath(archivefilep);
            dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        } else {
            //FALLBACK
            Log.logger.warn("Konnte Dokument " + dok.getFullPath()
                    + " keinem Typ zuordnen");
        }

        try {
            boolean upd = DokumentenSQLMethods.updatedokumente(DatabaseConnection.open(), dok);
            if (!upd) {
                Log.databaselogger.fatal("Das Dokument konnte nicht aktualisiert werden");
                ShowException.showException("Datenbankfehler: Das Dokument konnte nicht aktualisiert werden. "
                        + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                        ExceptionDialogGui.LEVEL_WARNING, "Schwerwiegend: Konnte Dokument nicht aktualisieren");
            }

        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte das Dokument nicht löschen", e);
            ShowException.showException("Datenbankfehler: Das Dokument konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dokument nicht löschen");
        }
    }
}
