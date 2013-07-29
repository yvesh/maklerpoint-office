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
package de.maklerpoint.office.Filesystem;

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FilesystemKunden {

    /**
     * 
     * @param kunde
     * @return Path to kunde
     */
    public static String getKundenPath(String kennung) {
        return Filesystem.getRootPath() + File.separatorChar + "kunden" + File.separatorChar + kennung;
    }

    /**
     * 
     * @param kennung
     * @return Path to archive
     */
    public static String getKundenArchivePath(String kennung) {
        String path = Filesystem.getRootPath() + File.separatorChar + "archive"
                + File.separatorChar + "kunden" + File.separatorChar + kennung;
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }

    /**
     * 
     * @param kennung
     * @return Delete Kunden Path
     */
    public static String getKundenDeletedPath(String kennung) {
        String path = Filesystem.getDeletePath()
                + File.separatorChar + "kunden" + File.separatorChar + kennung;

        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }

    /**
     * 
     * @param kunde
     * @return
     */
    public static boolean isKundeWritable(String kennung) {
        String pathName = getKundenPath(kennung);

        File path = new File(pathName);

        return path.canWrite();
    }

    /**
     * 
     * @param kunde
     * @return
     */
    public static boolean isKundeDirectory(String kennung) {
        String pathName = getKundenPath(kennung);

        File path = new File(pathName);

        return path.exists();
    }

    /**
     * 
     * @return
     */
    public static long getFreeSpace() {
        String filePath = Filesystem.getRootPath();

        File hdd = new File(filePath);
        return hdd.getFreeSpace();
    }

    /**
     * 
     * @param kunde
     * @return
     * @throws IOException
     */
    public static boolean createKundenDirectory(KundenObj kunde) throws IOException {
        String pathName = FilesystemKunden.getKundenPath(kunde.getKundenNr());

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Neuer Kundenpfad: " + pathName);
        }

//        if(FilesystemKunden.isKundeWritable(kunde) == false){
//            throw new IOException("Kunden Dateisystem ist nicht beschreibar");
//        }

//      if (FilesystemKunden.isKundeDirectory(kunde) == true){
//           throw new IOException("Kunden Ordner existiert schon");
//      }

        File file = new File(pathName);
        file.mkdirs();

        createIndexFile(pathName, kunde);

        String angebotsPath = pathName + File.separatorChar + Config.get("angebotOrdner", "angebote");
        String vertragsPath = pathName + File.separatorChar + Config.get("vertragOrdner", "vertraege");
        String bpPath = pathName + File.separatorChar + Config.get("bpOrdner", "beratungsprotokoll"); // BP
        String rechnungsPath = pathName + File.separatorChar + Config.get("rechnungOrdner", "rechnungen");
        String sonstigesPath = pathName + File.separatorChar + Config.get("sonstigesOrdner", "sonstiges");
        String historyPath = pathName + File.separatorChar + Config.get("historyOrdner", "history");
        String scansPath = pathName + File.separatorChar + Config.get("scanOrdner", "scan");



        file = new File(vertragsPath);
        file.mkdirs();

        file = new File(angebotsPath);
        file.mkdirs();

        file = new File(rechnungsPath);
        file.mkdirs();

        file = new File(sonstigesPath);
        file.mkdirs();

        file = new File(bpPath);
        file.mkdirs();

        file = new File(historyPath);
        file.mkdirs();

        file = new File(scansPath);
        file.mkdirs();

        return true;
    }

    /**
     * 
     * @param firma
     * @return
     * @throws IOException
     */
    public static boolean createKundenDirectory(FirmenObj firma) throws IOException {
        String pathName = FilesystemKunden.getKundenPath(firma.getKundenNr());

//        if(FilesystemKunden.isKundeWritable(kunde) == false){
//            throw new IOException("Kunden Dateisystem ist nicht beschreibar");
//        }

//      if (FilesystemKunden.isKundeDirectory(kunde) == true){
//           throw new IOException("Kunden Ordner existiert schon");
//      }

        File file = new File(pathName);
        file.mkdirs();

        createIndexFile(pathName, firma);

        String angebotsPath = pathName + File.separatorChar + Config.get("angebotOrdner", "angebote");
        String bpPath = pathName + File.separatorChar + Config.get("bpOrdner", "beratungsprotokoll"); // BP
        String vertragsPath = pathName + File.separatorChar + Config.get("vertragOrdner", "vertraege");
        String rechnungsPath = pathName + File.separatorChar + Config.get("rechnungOrdner", "rechnungen");
        String sonstigesPath = pathName + File.separatorChar + Config.get("sonstigesOrdner", "sonstiges");
        String versicherungsPath = pathName + File.separatorChar + Config.get("versicherungOrdner", "versicherung");
        String historyPath = pathName + File.separatorChar + Config.get("historyOrdner", "history");
        String scansPath = pathName + File.separatorChar + Config.get("scanOrdner", "scan");

        file = new File(vertragsPath);
        file.mkdirs();

        file = new File(bpPath);
        file.mkdirs();

        file = new File(angebotsPath);
        file.mkdirs();

        file = new File(rechnungsPath);
        file.mkdirs();

        file = new File(sonstigesPath);
        file.mkdirs();

        file = new File(versicherungsPath);
        file.mkdirs();

        file = new File(historyPath);
        file.mkdirs();

        file = new File(scansPath);
        file.mkdirs();

        return true;
    }

    /**
     * 
     * @param kunde
     * @return
     */
    public static boolean createIndexFile(String directoryPath, KundenObj kunde) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Kunde]\n");
        index.append("Id=");
        index.append(kunde.getId());
        index.append("\nKennung=");
        index.append(kunde.getKundenNr());
        index.append("\nVorname=");
        index.append(kunde.getVorname());
        index.append("\nNachname=");
        index.append(kunde.getNachname());

        String file = directoryPath + File.separator + ".kunde";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }

    public static boolean createIndexFile(String directoryPath, FirmenObj firma) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Firma]\n");
        index.append("Id=");
        index.append(firma.getId());
        index.append("\nKennung=");
        index.append(firma.getKundenNr());
        index.append("\nName=");
        index.append(firma.getFirmenName());

        String file = directoryPath + File.separator + ".kunde";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }

    /**
     * 
     * @param kunde
     * @param label
     * @return
     */
    public static File[] getKundenFilesFolder(String kennung, String fold) {
        String pathName = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + fold;

        File folder = new File(pathName);

        ArrayList files = getFiles(folder);

        File[] gatheredFiles = (File[]) files.toArray();

        return gatheredFiles;
    }

    /**
     * 
     * @param kunde
     * @return
     */
    public static File[] getAllKundenFiles(String kennung) {
        String pathName = FilesystemKunden.getKundenPath(kennung);

        File folder = new File(pathName);

        ArrayList files = getFiles(folder);

        File[] gatheredFiles = (File[]) files.toArray();

        return gatheredFiles;
    }

    /**
     * 
     * @param folder
     * @return
     */
    private static ArrayList getFiles(File folder) {

        ArrayList files = new ArrayList();

        File[] filelist = folder.listFiles();

        if (filelist == null || filelist.length == 0) {
            return null;
        }

        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
                files.add(getFiles(filelist[i]));
            } else {
                files.add(filelist[i]);
            }
        }

        return files;
    }
}