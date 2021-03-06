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
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author yves
 */
public class FilesystemProdukte {

    /**
     * 
     * @param prod
     * @return Produkt Pfad
     */
    public static String getProduktPath(ProduktObj prod) {
 
        String path = FilesystemVersicherer.getVersichererPath(prod.getVersichererId()) + File.separator +
                Config.get("produktOrdner", "produkte") +
                + File.separatorChar + prod.getId();
        //  /versicherer/12323/produkte/23231

        return path;
    }
    
    /**
     * 
     * @param prod
     * @return Produkt Archive Path
     */
    public static String getProduktArchivePath(ProduktObj prod) {
        String path = FilesystemVersicherer.getVersichererArchivePath(prod.getVersichererId()) + File.separator +
                Config.get("produktOrdner", "produkte") +
                + File.separatorChar + prod.getId();
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param prod
     * @return Produkt Delete Path
     */
    public static String getProduktDeletePath(ProduktObj prod) {
        String path = FilesystemVersicherer.getVersichererDeletePath(prod.getVersichererId()) + File.separator +
                Config.get("produktOrdner", "produkte") +
                + File.separatorChar + prod.getId();
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
    public static boolean isWritable(ProduktObj vers) {
        String pathName = getProduktPath(vers);

        File path = new File(pathName);

        return path.canWrite();
    }

    /**
     *
     * @param kunde
     * @return exists
     */
    public static boolean isDirectory(ProduktObj vers) {
        String pathName = getProduktPath(vers);

        File path = new File(pathName);

        return path.exists();
    }

    /**
     * 
     * @param prod
     * @return success
     * @throws IOException 
     */
    
    public static boolean createProduktDirectory(ProduktObj prod) throws IOException {
        String pathName = getProduktPath(prod);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Pfad des neuen Produkts: " + pathName);
        }

//        if(FilesystemKunden.isKundeWritable(kunde) == false){
//            throw new IOException("Kunden Dateisystem ist nicht beschreibar");
//        }

//      if (FilesystemKunden.isKundeDirectory(kunde) == true){
//           throw new IOException("Kunden Ordner existiert schon");
//      }

        File file = new File(pathName);
        
        if(file.exists()) {
            Log.logger.warn("Ein Ordner mit der neuen ProduktId existiert bereits.");
        }
        
        file.mkdirs();

        createIndexFile(pathName, prod);

        String aentragePath = pathName + File.separatorChar + Config.get("antraegeOrder", "antraege");
        String infoPath = pathName + File.separatorChar + Config.get("informationsOrder", "informationen");
        String sonstigesPath = pathName + File.separatorChar + Config.get("sonstigesOrdner", "sonstiges");
        String historyPath = pathName + File.separatorChar + Config.get("historyOrdner", "history");
        String scansPath = pathName + File.separatorChar + Config.get("scanOrdner", "scan");

        file = new File(aentragePath);
        file.mkdirs();

        file = new File(infoPath);
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
     * @return success
     * @throws IOException
     */
    public static boolean createIndexFile(String directoryPath, ProduktObj prod) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Produkt]\n");
        index.append("Id=");
        index.append(prod.getId());
        index.append("\nVersichererId=");
        index.append(prod.getVersichererId());
        index.append("\nBezeichnung=");
        index.append(prod.getBezeichnung());

        String file = directoryPath + File.separator + ".produkt";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
