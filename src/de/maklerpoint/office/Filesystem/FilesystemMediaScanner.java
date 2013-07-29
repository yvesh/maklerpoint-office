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
package de.maklerpoint.office.Filesystem;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Dokumente.Tools.DokumentenSQLMethods;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mime.InitializeMime;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.FileTypeDetection;
import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class FilesystemMediaScanner {

    public static void scan(){
        if (Config.getConfigBoolean("offlineModus", false)) {
            Log.logger.warn("Stoppe Dateiscanner, im Offlinemodus");
            return;
        }
        
        InitializeMime.setUp();

        String rootpath = Filesystem.getRootPath();

        // Check Hauptdirs

        for (int i = 0; i < Filesystem.ROOT_Directories.length; i++) {
            File chkdir = new File(rootpath + File.separator + Filesystem.ROOT_Directories[i]);
            if (!chkdir.isDirectory()) {
                Log.logger.warn("Das Hauptverzeichnis " + Filesystem.ROOT_Directories[i]
                        + " existiert nicht. Das Verzeichnis wird jetzt neu erstellt");
                chkdir.mkdirs();
            }
        }       
        
        try {
            // Check Kunden Directory            
            String kundenpath = rootpath + File.separator + "kunden";
            scanKundenDokumente(new File(kundenpath));
            scanNichtvorhandenerKundenDokumente();
        } catch (Exception e) {
            Log.logger.warn("Beim überprüfen der Kundendokumente ist ein Fehler aufgetretten.", e);
        }
    }
    
    private static void scanNichtvorhandenerKundenDokumente() throws SQLException{
        DokumentenObj[] doks = DokumentenSQLMethods.loadAlleDokumente(DatabaseConnection.open(), Status.NORMAL);
        
        if(doks == null)
            return;
        
        for(int i = 0; i < doks.length; i++)
        {
            String path = doks[i].getFullPath();
            File file = new File(path);
            
            if(!file.exists()){
                Log.logger.warn("Warnung die Datei " + doks[i].getName() + " mit dem Pfad " 
                        + doks[i].getFullPath() + " existiert nicht. Bitte überprüfen Sie den Pfad. "
                        + "Der Status des Dokuments wird auf gelöscht gesetzt.");
                
                doks[i].setStatus(Status.DELETED);
                doks[i].setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                
                DokumentenSQLMethods.updatedokumente(DatabaseConnection.open(), doks[i]);
            }            
        }
    }

    private static void scanKundenDokumente(File quelle) throws SQLException {
        File[] files = quelle.listFiles();
        for (File file : files) {
//            System.out.println("Filename: " + file.getName());

            if (file.isDirectory()) {
                if (!file.getName().startsWith(".")) {
                    scanKundenDokumente(file);
                }
            } else {

                if (!file.getName().startsWith(".")) {
                    long checksum = FileTools.getChecksum(file.getPath());

                    DokumentenObj dokument = DokumentenSQLMethods.getDokument(DatabaseConnection.open(),
                            file.getName(), String.valueOf(checksum));

                    if (dokument == null) {
                        if (Log.logger.isDebugEnabled()) {
                            Log.logger.debug("Neue, nicht in der Datenbank vorhandene, Kundendatei "
                                    + "gefunden (" + file.getAbsolutePath() + "). Erstelle neuen Eintrag");
                        }

                        String fpclear = file.getPath().replaceAll(Filesystem.getRootPath()
                                + File.separator, "");

//                        System.out.println("Filepath cleared: " + fpclear);

                        if (fpclear.startsWith("kunden")) {
                            String[] res = fpclear.split(File.separator);
                            String kdnr = null;
//                            System.out.println("RES length: " + res.length);

                            if (res.length >= 3) {
                                kdnr = res[res.length - 3].trim();
//                                System.out.println("KDNR: " + kdnr);
                                KundenObj knd = KundenSQLMethods.loadKunde(DatabaseConnection.open(), kdnr);

//                                System.out.println("Kunde: " + knd.getNachname());

                                if (knd == null) {
                                    if (res.length >= 4) {
                                        kdnr = res[res.length - 4]; // try 4
                                        knd = KundenSQLMethods.loadKunde(DatabaseConnection.open(), kdnr);
                                    }

                                    if (knd == null) {
                                        kdnr = res[res.length - 2]; // try 2
                                        knd = KundenSQLMethods.loadKunde(DatabaseConnection.open(), kdnr);

                                        if (knd == null) {
                                            if (res.length >= 5) {
                                                kdnr = res[res.length - 5]; // try 2
                                                KundenSQLMethods.loadKunde(DatabaseConnection.open(), kdnr);
                                            }
                                        }
                                    }
                                }

                                if (knd != null) {
                                    if (Log.logger.isDebugEnabled()) {
                                        Log.logger.debug("Neue Datei für den Kunden " + knd.getKundenNr() + " gefunden.");
                                    }
                                    DokumentenObj newdok = new DokumentenObj();
                                    newdok.setBenutzerId(BasicRegistry.currentUser.getId());
                                    newdok.setCreatorId(BasicRegistry.currentUser.getId());
                                    newdok.setChecksum(String.valueOf(checksum));
                                    newdok.setFullPath(file.getPath());
                                    newdok.setName(file.getName());
                                    newdok.setKundenKennung(knd.getKundenNr());
                                    newdok.setFiletype(FileTypeDetection.getFileType(file));
                                    newdok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                                    newdok.setBeschreibung("Automatisch hinzugefügt");                                    
                                    newdok.setStatus(Status.NORMAL);

                                    DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), newdok);
                                } else {
                                    Log.logger.warn("Konnte den Kunden für die nicht indexierte Datei "
                                            + file.getPath() + " nicht ermitteln.");
                                }
                            }
                        }
                    }
                } else {
                    if (Log.logger.isDebugEnabled()) {
                        Log.logger.debug("Versteckte Datei gefunden: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }
}