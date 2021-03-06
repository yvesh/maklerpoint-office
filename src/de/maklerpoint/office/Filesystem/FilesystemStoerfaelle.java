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
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class FilesystemStoerfaelle {

    /**
     * 
     * @param stoer
     * @return Stoerfall Path
     */
    
    public static String getStoerfallPath(StoerfallObj stoer) {
        String filePath = FilesystemVertraege.getVertragPath(stoer.getKundenNr(), stoer.getVertragsId());

        String path = filePath + File.separatorChar + "stoerfalle" + File.separator + stoer.getId();

        return path;
    }

    /**
     * 
     * @param stoer
     * @return Stoerfall Archive Path
     */
    public static String getStoerfallArchivePath(StoerfallObj stoer) {
        String path = FilesystemVertraege.getVertragArchivePath(stoer.getKundenNr(), stoer.getVertragsId())
                + File.separatorChar + "stoerfalle" + File.separator + stoer.getId();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param stoer
     * @return Stoerfall Delete Path
     */
    public static String getStoerfallDeletePath(StoerfallObj stoer) {
        String path = FilesystemVertraege.getVertragDeletePath(stoer.getKundenNr(), stoer.getVertragsId())
                + File.separatorChar + "stoerfalle" + File.separator + stoer.getId();
        
        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    /**
     * 
     * @param sch
     * @return Beschreibbar
     */
    
    public static boolean isWritable(StoerfallObj sch) {
        String pathName = getStoerfallPath(sch);

        File path = new File(pathName);

        return path.canWrite();
    }

    /**
     * 
     * @param stoer
     * @return Erfolg
     * @throws IOException 
     */
    
    public static boolean createSchadenDirectory(StoerfallObj stoer) throws IOException {
        String pathName = getStoerfallPath(stoer);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Neuer Störfall Pfad: " + pathName);
        }

        File file = new File(pathName);

        if (file.exists()) {
            Log.logger.warn("Ein Ordner mit der neuen StörfallId existiert bereits.");
        }

        file.mkdirs();

        createIndexFile(pathName, stoer);

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
     * @param stoer
     * @return Erfolg
     * @throws IOException 
     */

    public static boolean createIndexFile(String directoryPath, StoerfallObj stoer) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Stoerfall]\n");
        index.append("Id=");
        index.append(stoer.getId());
        index.append("\nKunde=");
        index.append(stoer.getKundenNr());
        index.append("\nVertragId=");
        index.append(stoer.getVertragsId());

        String file = directoryPath + File.separator + ".stoerfall";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
