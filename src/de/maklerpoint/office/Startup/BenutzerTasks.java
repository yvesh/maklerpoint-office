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

package de.maklerpoint.office.Startup;

import de.maklerpoint.office.Backup.BackupDatabase;
import de.maklerpoint.office.Backup.BackupFiletypes;
import de.maklerpoint.office.Backup.BackupInfoFile;
import de.maklerpoint.office.Backup.BackupObj;
import de.maklerpoint.office.Backup.BackupTypes;
import de.maklerpoint.office.Backup.Tools.BackupSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Filesystem.FilesystemMediaScanner;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Splashscreen.FakeSplashScreenDeprecated;
import de.maklerpoint.office.Idle.ComputerState;
import de.maklerpoint.office.Idle.Execute.AwayExecute;
import de.maklerpoint.office.Idle.Execute.IdleExecute;
import de.maklerpoint.office.Idle.Execute.OnlineExecute;
import de.maklerpoint.office.Idle.IdleTrigger;
import de.maklerpoint.office.Idle.Starter;
import de.maklerpoint.office.Kalender.Tools.GeburtstageSQLMethods;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mime.InitializeMime;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.DokumentenRegistry;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Scheduler.SchedulerTask;
import de.maklerpoint.office.Scheduler.SchedulerTime;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.System.Update;
import de.maklerpoint.office.System.Version;
import de.maklerpoint.office.Tags.InitializeTags;
import de.maklerpoint.office.Tools.DirectoryTools;
import de.maklerpoint.office.Tools.ZipFiles;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerTasks {

    /**
     * nur für wechsel zwischen on-/offline modus
     */

    public static void doBenutzerTasks(){
        InitializeMime.setUp();       
        setVMSettings();
        MandantenRegistry.getAlleMandanten(true);
        KundenRegistry.getFirmenKunden(true, Status.NORMAL); // Eigene
        KundenRegistry.getFirmenKunden(false, Status.NORMAL); // Alle
        BenutzerRegistry.getEmpfNachrichten();
        BenutzerRegistry.getSendNachrichten();
        setupTermine();
        setupAufgaben();
        InitializeTags.loadTags();
        new SchedulerTask().start();
        setupStateDetection();
        setupGeburtstage();
        DokumentenRegistry.getWissenDokumente();
        scheduleAutoBackup();
        VersicherungsRegistry.getSparten(true);
        VersicherungsRegistry.getWaehrungen(true);
        VersicherungsRegistry.getVersicherer(Status.NORMAL);
        ToolsRegistry.getTextBausteinGroups(true);
        ToolsRegistry.getTextBausteine(true);
        //ToolsRegistry.getAlleNotizen(Status.NORMAL); Zu viele
        ToolsRegistry.getEigeneNotizen(Status.NORMAL);
        SwingWorker update = new SwingWorker<Void, Void>(){            
         
            public Void doInBackground() {
                checkforUpdate();
                return null;
            }
        };
                        
        update.execute();
    }

    /**
     * 
     * @param sp 
     */
    
    public static void doBenutzerTasksSplash(FakeSplashScreenDeprecated sp){
        sp.setStatus(20, "Lade MIME Werkzeuge.");
        InitializeMime.setUp();
        sp.setStatus(23, "Initialisiere JVM Einstellungen.");
        setVMSettings();
        sp.setStatus(26, "Starte Dateisystem Scanner.");
        SwingWorker mediascan = new SwingWorker<Void, Void>(){            
            public Void doInBackground() {
                FilesystemMediaScanner.scan();  
                return null;
            }
        };
        
        mediascan.execute();
        
        sp.setStatus(30, "Lade Mandantenliste.");
        MandantenRegistry.getAlleMandanten(true);
        sp.setStatus(33, "Lade Benutzernachrichten.");
        BenutzerRegistry.getEmpfNachrichten();
        BenutzerRegistry.getSendNachrichten();
        sp.setStatus(35, "Lade eigene Geschäftskunden.");
        KundenRegistry.getFirmenKunden(true, Status.NORMAL); // Eigene
        sp.setStatus(38, "Lade alle Geschäftskunden.");
        KundenRegistry.getFirmenKunden(false, Status.NORMAL); // Eigene
        sp.setStatus(43, "Lade Termine.");
        setupTermine();
        sp.setStatus(47, "Lade Aufgaben.");
        setupAufgaben();
        sp.setStatus(50, "Lade Markierungen und Tags.");
        InitializeTags.loadTags();
        sp.setStatus(53, "Initialisiere Aufgaben Planer.");
        new SchedulerTask().start();
        sp.setStatus(57, "Initialisiere Hilfsdienste.");
        setupStateDetection();
        sp.setStatus(60, "Initialisiere Geburtstagsliste.");
        setupGeburtstage();
        sp.setStatus(65, "Initialisiere Wissensdokumente.");
        DokumentenRegistry.getWissenDokumente();
        sp.setStatus(70, "Initialisiere Autobackup.");
        scheduleAutoBackup();
        sp.setStatus(73, "Lade Versicherungssparten.");
        VersicherungsRegistry.getSparten(true);        
        sp.setStatus(75, "Lade Versicherer.");        
        VersicherungsRegistry.getVersicherer(Status.NORMAL);
        sp.setStatus(75, "Lade Produkte.");        
        VersicherungsRegistry.getProdukte(Status.NORMAL);
        sp.setStatus(81, "Lade Währungen.");
        VersicherungsRegistry.getWaehrungen(true);
        sp.setStatus(90, "Lade Textbausteine.");
        ToolsRegistry.getTextBausteinGroups(true);
        ToolsRegistry.getTextBausteine(true);
        sp.setStatus(93, "Lade Notizen.");
//        ToolsRegistry.getNotizen(true);
        ToolsRegistry.getEigeneNotizen(Status.NORMAL);
        sp.setStatus(96, "Initialisierung der Aufgaben beendet.");
        SwingWorker update = new SwingWorker<Void, Void>(){            
         
            public Void doInBackground() {
                checkforUpdate();
                return null;
            }
        };
                        
        update.execute();
        sp.setStatus(99, "Suche nach Updates");
    }

    /**
     * 
     */

    public static void setupTermine(){
        KalenderRegistry.getTermine(true);        
    }

    /**
     * 
     */

    public static void setupAufgaben(){
//        KalenderRegistry.getAufgaben(); // TOD Überflüssig
    }

    /**
     * 
     */

    public static void setupGeburtstage() {
        try {
//            KalenderRegistry.heutigeBenutzerGeburtstage = GeburtstageSQLMethods.getBenutzerGeburtstag(DatabaseConnection.open(),
//                    new Date(System.currentTimeMillis()));
//            KalenderRegistry.heutigeKundenGeburtstage = GeburtstageSQLMethods.getKundenGeburtstag(DatabaseConnection.open(),
//                    new Date(System.currentTimeMillis()), Config.getConfigBoolean("eigenekundenGeburtstage", true));

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte Geburtstagsliste nicht aus der Datenbank laden", e);
            ShowException.showException("Die Geburtstagslisten konnten nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Aufgaben nicht laden");           
        }
    }

    /**
     * 
     */

    public static void setupStateDetection() {
        Starter scheduler = new Starter();
        IdleTrigger onlinejob = new IdleTrigger("Working", ComputerState.ONLINE, OnlineExecute.class);
        IdleTrigger awayjob = new IdleTrigger("Away", ComputerState.AWAY, AwayExecute.class);
        IdleTrigger idlejob = new IdleTrigger("Idle", ComputerState.IDLE, IdleExecute.class);

        try {
            scheduler.scheduleJob(onlinejob);
            scheduler.scheduleJob(awayjob); // optional
            scheduler.scheduleJob(idlejob);
            scheduler.start();

        } catch(Exception e) {
            Log.logger.warn("Konnte Computer State Detection nicht starten", e);
        }

    }

    public static void setVMSettings() {
        System.setProperty("http.agent", "MaklerPoint Office " +  Version.version + ";+http://www.maklerpoint.de)");

        if(Config.getConfigBoolean("proxy", false)) {
            System.setProperty("http.proxyHost", Config.get("proxyHost", null));
            System.setProperty("http.proxyPort", Config.get("proxyPort", null));
            System.setProperty("http.noProxyHost",Config.get("proxyNohosts", null));
        }
    }


    public static void scheduleAutoBackup() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(new Date(System.currentTimeMillis()));

        if(Config.getConfigBoolean("autoBackup", false) == true) {
            String nextBackup = Config.get("autoBackupNextTime", null);
            if(nextBackup == null) {
                System.out.println("ADD Exception: autobackup true, aber kein termin?!");
                return;
            }
            
            Date nextDate = null;

            try {
                nextDate = df.parse(nextBackup);
            } catch(Exception e) {
                System.out.println("ADD Exception: konnte Datum nicht parsen? Unmöglich");
                return;
            }
            c2.setTime(nextDate);

            boolean same = SchedulerTime.sameDay(c1, c2);

            if(same == true || nextDate.before(new Date(System.currentTimeMillis()))) {
                String tmpDatabaseFile = null;
                ArrayList fileNames = new ArrayList();

                BackupObj backup = new BackupObj();
                backup.setFiletype(Config.getConfigInt("backupType", BackupFiletypes.ZIP));
                backup.setAutomatic(true);
                backup.setBenutzerId(BasicRegistry.currentUser.getId());
                backup.setPath(Config.get("autoBackupZiel", null) + "autobackup_" + 
                        df.format(new Date(System.currentTimeMillis())) +
                        BackupFiletypes.getFileEnding(Config.getConfigInt("backupType", BackupFiletypes.ZIP)));

                backup.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                backup.setType(BackupTypes.DATENBANK);
                backup.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));

                String tmpFolder = Filesystem.getTmpPath() + File.separatorChar
                        + df.format(new Date(System.currentTimeMillis()));
                File folder = new File(tmpFolder);
                folder.mkdirs();

                try {
                    tmpDatabaseFile = tmpFolder + File.separatorChar + "database.sql";
                    BackupDatabase back = new BackupDatabase(tmpDatabaseFile);
                    back.backup();
                    fileNames.add(tmpDatabaseFile);
                } catch (Exception e) {
                    Log.databaselogger.fatal("Fehler: Konnte automatische Datenbanksicherung nicht durchführen", e);
                    ShowException.showException("Die automatische Datenbank Sicherung konnte nicht durchgeführt werden",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht durchführen");                    
                }

                backup.setSuccess(false);

                try {
                    int id = BackupSQLMethods.insertIntoBackup(DatabaseConnection.open(),
                            backup.getPath(), backup.getType(), backup.getCreated(),
                            backup.isAutomatic(), backup.getBenutzerId(), backup.isSuccess(),
                            backup.isFileAvailable(), backup.getBackupSize(), backup.getFiletype());

                    if(id == -1)
                        System.out.println("ADD EXCEPTION");

                    backup.setId(id);

                    BackupInfoFile binfo = new BackupInfoFile(tmpFolder + File.separatorChar + ".backup", backup);
                    binfo.write();

                    fileNames.add(tmpFolder + File.separatorChar + ".backup");

                    String[] filePaths = new String[fileNames.toArray().length];

                    for(int i = 0; i < fileNames.size(); i++) {
                        filePaths[i] = (String) fileNames.get(i);
                    }

                    boolean success = ZipFiles.zipFiles(backup.getPath(), filePaths);

                    if(!success) {
                        System.out.println("ADD EXCEPTION: Fehler beim sichern");
                    }

                    //Todo: Delete all files
                    //File tmpFolderFile = new File(tmpFolder);
                    //tmpFolderFile.delete();

                    File backFile = new File(backup.getPath());
                    backup.setBackupSize((int) (backFile.length() / 1000));
                    backup.setSuccess(true);


                    success = BackupSQLMethods.updateBackup(DatabaseConnection.open(), backup);
                    if(!success) {
                        System.out.println("ADD EXCEPTION: Fehler beim DB update");
                    }

                    DirectoryTools.deleteDirectory(folder);


                } catch (SQLException e) {
                    Log.databaselogger.fatal("Fehler: Konnte Sicherung nicht speichern", e);
                    ShowException.showException("Die Sicherungsinformationen konnte nicht in der Datenbank gespeichert werden",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht speichern");
                    
                } catch (IOException e) {
                    Log.logger.fatal("Fehler: Konnte Sicherung nicht speichern", e);
                    ShowException.showException("Konnte ZIP Datei nicht schreiben",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht speichern");
                    
                }

                String backIntv = Config.get("autoBackupInterval", "").trim();

                if(backIntv.equalsIgnoreCase("täglich")) {
                    Config.set("autoBackupNextTime", df.format(new Date(System.currentTimeMillis())));
                } else if (backIntv.equalsIgnoreCase("wöchentlich")) {
                    Config.set("autoBackupNextTime", df.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)));
                } else if (backIntv.equalsIgnoreCase("monatlich")) {
                    Config.set("autoBackupNextTime", df.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30)));
                } else {
                    System.out.println("ADD EXCEPTION: Was ist bitte ausgewählt? xx");
                }

            } // End same or earlier Day
        }
    }

    private static void checkforUpdate() {
        Update up = new Update();
        boolean update = up.check();

        if(update)
            Log.logger.warn("Neues Update vorhanden.");
        else
            Log.logger.info("Keine Updates vorhanden.");
    }

}