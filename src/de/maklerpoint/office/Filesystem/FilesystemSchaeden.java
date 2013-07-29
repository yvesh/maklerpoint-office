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

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class FilesystemSchaeden {
 
    /**
     * 
     * @param sch
     * @return  Schaden Path
     */
    
    public static String getSchadenPath(SchadenObj sch) {
        String filePath = FilesystemVertraege.getVertragPath(sch.getKundenNr(), sch.getVertragsId());

        String path = filePath + File.separatorChar + "schaeden" + File.separatorChar + sch.getId();

        return path;
    }  
    
    /**
     * 
     * @param schaden
     * @return SChaden Archive Path
     */
    public static String getSchadenArchivePath(SchadenObj sch) {
        String path = FilesystemVertraege.getVertragArchivePath(sch.getKundenNr(), sch.getVertragsId())
                + File.separatorChar + "schaeden" + File.separatorChar + sch.getId();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param sch
     * @return Schaden Delete Path
     */
    public static String getSchadenDeletePath(SchadenObj sch) {
        String path = FilesystemVertraege.getVertragDeletePath(sch.getKundenNr(), sch.getVertragsId())
                + File.separatorChar + "schaeden" + File.separatorChar + sch.getId();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param sch
     * @return 
     */
    
    public static boolean isWritable(SchadenObj sch) {
        String pathName = getSchadenPath(sch);

        File path = new File(pathName);

        return path.canWrite();
    }
    
    /**
     * 
     * @param sch
     * @return
     * @throws IOException 
     */    
    
    public static boolean createSchadenDirectory(SchadenObj sch) throws IOException {
        String pathName = getSchadenPath(sch);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Neuer Schadenfall Pfad: " + pathName);
        }

        File file = new File(pathName);
        
        if(file.exists()) {
            Log.logger.warn("Ein Ordner mit der neuen SchadenfallId existiert bereits.");
        }
        
        file.mkdirs();

        createIndexFile(pathName, sch);

        String untPath = pathName + File.separatorChar + "unterlagen";
        String sonstigesPath = pathName + File.separatorChar + "sonstiges";
        String historyPath = pathName + File.separatorChar + "history";       
        String scansPath = pathName + File.separatorChar + "scan";

        file = new File(untPath);
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
     * @param sch
     * @return
     * @throws IOException 
     */    
    
    public static boolean createIndexFile(String directoryPath, SchadenObj sch) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Schadenfall]\n");
        index.append("Id=");
        index.append(sch.getId());
        index.append("\nKunde=");
        index.append(sch.getKundenNr());
        index.append("\nVertragId=");
        index.append(sch.getVertragsId());

        String file = directoryPath + File.separator + ".schaden";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
    
}
