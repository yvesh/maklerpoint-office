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

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.System.Configuration.Config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FilesystemBenutzer {

    /**
     * 
     * @return Benutzer Root Path
     */
    public static String getBenutzerRoot() {
        String path = Filesystem.getRootPath() + File.separatorChar + "benutzer";
        return path;
    }

    /**
     * 
     * @param benutzer
     * @return Benutzer Path
     */

    public static String getBenutzerPath(BenutzerObj benutzer) {
        String path = Filesystem.getRootPath() + File.separatorChar + "benutzer"
                                    + File.separatorChar + benutzer.getKennung();
        return path;
    }
    
    /**
     * 
     * @param benutzer
     * @return Benutzer Archive Path
     */
    
    public static String getBenutzerArchivePath(BenutzerObj benutzer) {
        String path = Filesystem.getArchivePath() + File.separatorChar + "benutzer"
                                    + File.separatorChar + benutzer.getKennung();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }
        
        return path;
    }
    
    /**
     * 
     * @param benutzer
     * @return Benutzer Delete Path
     */
    
    public static String getBenutzerDeletePath(BenutzerObj benutzer) {
        String path = Filesystem.getDeletePath() + File.separatorChar + "benutzer"
                                    + File.separatorChar + benutzer.getKennung();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }
        
        return path;
    }
    
    /**
     * 
     * @param kennung
     * @return Benutzer Path
     */
    
    public static String getBenutzerPath(String kennung) {
        String path = Filesystem.getRootPath() + File.separatorChar + "benutzer"
                                    + File.separatorChar + kennung;
        return path;
    }

    /**
     * 
     * @param benutzer
     * @return Beschreibbar
     */

    public static boolean isBenutzerWritable(BenutzerObj benutzer) {
        String pathName = getBenutzerPath(benutzer);

        File path = new File(pathName);

        return path.canWrite();
    }

    /**
     * 
     * @return space in bytes L
     */

    public static long getFreeBenutzerSpace() {

        String path = getBenutzerRoot();

        File hdd = new File(path);
        return hdd.getFreeSpace();
    }

    /**
     * 
     * @param benutzer
     * @return success
     * @throws IOException
     */

    public static boolean createBenutzerDirectory(BenutzerObj benutzer) throws IOException {
        String pathName = FilesystemBenutzer.getBenutzerPath(benutzer);

        System.out.println("Pathname: " + pathName);

//        if(FilesystemKunden.isKundeWritable(kunde) == false){
//            throw new IOException("Kunden Dateisystem ist nicht beschreibar");
//        }

//      if (FilesystemKunden.isKundeDirectory(kunde) == true){
//           throw new IOException("Kunden Ordner existiert schon");
//      }

        File file = new File(pathName);
        file.mkdirs();

        createIndexFile(pathName, benutzer);

        String vertragsPath = pathName + File.separatorChar + Config.get("vertragOrdnerBenutzer", "vertraege");
        String sonstigesPath = pathName + File.separatorChar + Config.get("sonstigesOrdnerBenutzer", "sonstiges");
        String abrechnungsPath = pathName + File.separatorChar + Config.get("abrechnungOrdnerBenutzer", "abrechnungen");
        String historyPath = pathName + File.separatorChar + Config.get("historyOrdnerBenutzer", "history");
        String scansPath = pathName + File.separatorChar + Config.get("scanOrdnerBenutzer", "scan");

        file = new File(vertragsPath);
        file.mkdirs();

        file = new File(sonstigesPath);
        file.mkdirs();

        file = new File(abrechnungsPath);
        file.mkdirs();

        file = new File(historyPath);
        file.mkdirs();

        file = new File(scansPath);
        file.mkdirs();

        return true;
    }

    /**
     * 
     * @param directoryPath
     * @param kunde
     * @return success
     * @throws IOException
     */

     public static boolean createIndexFile(String directoryPath, BenutzerObj benutzer) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Benutzer]\n");
        index.append("Id=");
        index.append(benutzer.getId());
        index.append("\nKennung=");
        index.append(benutzer.getKennung());
        index.append("\nVorname=");
        index.append(benutzer.getVorname());
        index.append("\nNachname=");
        index.append(benutzer.getNachname());

        String file = directoryPath + File.separator + ".benutzer";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
