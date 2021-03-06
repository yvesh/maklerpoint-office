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

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author yves
 */
public class FilesystemVersicherer {

    /**
     * 
     * @param vers
     * @return Versicherer Path
     */
    
    public static String getVersichererPath(VersichererObj vers) {

        String filePath = Filesystem.getRootPath();

        String path = filePath + File.separatorChar + "versicherer" + File.separatorChar + vers.getId();

        return path;
    }
    
    /**
     * 
     * @param versid
     * @return VersichererPath
     */
    
    public static String getVersichererPath(int versid) {

        String filePath = Filesystem.getRootPath();

        String path = filePath + File.separatorChar + "versicherer" + File.separatorChar + versid;

        return path;
    }
    
    /**
     * 
     * @param vers
     * @return Versicherer Archive Path
     */
    
    public static String getVersichererArchivePath(VersichererObj vers) {
        String path = Filesystem.getArchivePath() + File.separatorChar 
                + "versicherer" + File.separatorChar + vers.getId();
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param versId
     * @return  Versicherer Archive Path
     */
    
    public static String getVersichererArchivePath(int versId) {
        String path = Filesystem.getArchivePath() + File.separatorChar 
                + "versicherer" + File.separatorChar + versId;
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param versId
     * @return Versicherer Delete path
     */
    public static String getVersichererDeletePath(int versId) {
        String path = Filesystem.getDeletePath() + File.separatorChar 
                + "versicherer" + File.separatorChar + versId;
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }

    /**
     * 
     * @param vers
     * @return Writable
     */
    
    public static boolean isWritable(VersichererObj vers) {
        String pathName = getVersichererPath(vers);

        File path = new File(pathName);

        return path.canWrite();
    }

    /**
     * 
     * @param vers
     * @return 
     */
    
    public static boolean isDirectory(VersichererObj vers) {
        String pathName = getVersichererPath(vers);

        File path = new File(pathName);

        return path.exists();
    }

    /**
     * 
     * @param vers
     * @return
     * @throws IOException
     */
    public static boolean createVersichererDirectory(VersichererObj vers) throws IOException {
        String pathName = getVersichererPath(vers);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Neuer Gesellschafts Pfad: " + pathName);
        }

        File file = new File(pathName);
        
        if(file.exists()) {
            Log.logger.warn("Ein Ordner mit der neuen GesellschaftsId existiert bereits.");
        }
        
        file.mkdirs();

        createIndexFile(pathName, vers);

        String prodPath = pathName + File.separatorChar + Config.get("produktOrdner", "produkte");
        String vertragsPath = pathName + File.separatorChar + Config.get("vertragOrdner", "vertraege");
        String sonstigesPath = pathName + File.separatorChar + Config.get("sonstigesOrdner", "sonstiges");
        String historyPath = pathName + File.separatorChar + Config.get("historyOrdner", "history");
        String scansPath = pathName + File.separatorChar + Config.get("scanOrdner", "scan");

        file = new File(vertragsPath);
        file.mkdirs();

        file = new File(prodPath);
        file.mkdirs();

        file = new File(sonstigesPath);
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
     * @param vers
     * @return
     * @throws IOException
     */
    public static boolean createIndexFile(String directoryPath, VersichererObj vers) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Versicherer]\n");
        index.append("Id=");
        index.append(vers.getId());
        index.append("\nVUNummer=");
        index.append(vers.getVuNummer());
        index.append("\nName=");
        index.append(vers.getName());

        String file = directoryPath + File.separator + ".versicherer";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
