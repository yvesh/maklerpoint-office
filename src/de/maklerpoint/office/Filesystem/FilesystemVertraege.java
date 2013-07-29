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
import de.maklerpoint.office.Vertraege.VertragObj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class FilesystemVertraege {

    /**
     * 
     * @param vtr
     * @return Vertrags Path
     */
    public static String getVertragPath(VertragObj vtr) {

        String filePath = FilesystemKunden.getKundenPath(vtr.getKundenKennung());

        String path = filePath + File.separatorChar + "vertraege" + File.separatorChar + vtr.getId();

        return path;
    }

    /**
     * 
     * @param kdnr
     * @param vtrid
     * @return Vertrags Path
     */
    
    public static String getVertragPath(String kdnr, int vtrid) {

        String filePath = FilesystemKunden.getKundenPath(kdnr);

        String path = filePath + File.separatorChar + "vertraege" + File.separatorChar + vtrid;

        return path;
    }

    /**
     * 
     * @param vtr
     * @return Vertrags Archiv Path
     */
    public static String getVertragArchivePath(VertragObj vtr) {
        String path = FilesystemKunden.getKundenArchivePath(vtr.getKundenKennung())
                + File.separatorChar + "vertraege" + File.separatorChar + vtr.getId();

        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }

    /**
     * 
     * @param kdnr
     * @param vtrid
     * @return Vertrags Archiv Path
     */
    public static String getVertragArchivePath(String kdnr, int vtrid) {
        String path = FilesystemKunden.getKundenArchivePath(kdnr)
                + File.separatorChar + "vertraege" + File.separatorChar + vtrid;

        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    /**
     * 
     * @param kdnr
     * @param vtrid
     * @return Vertrag Delete Path
     */
    public static String getVertragDeletePath(String kdnr, int vtrid) {
        String path = FilesystemKunden.getKundenDeletedPath(kdnr)
                + File.separatorChar + "vertraege" + File.separatorChar + vtrid;

        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }
    
    public static String getVertragDeletePath(VertragObj vtr) {
        String path = FilesystemKunden.getKundenDeletedPath(vtr.getKundenKennung())
                + File.separatorChar + "vertraege" + File.separatorChar + vtr.getId();

        File pathf = new File(path);

        if (!pathf.isDirectory()) {
            pathf.mkdirs();
        }

        return path;
    }

    public static boolean isWritable(VertragObj vtr) {
        String pathName = getVertragPath(vtr);

        File path = new File(pathName);

        return path.canWrite();
    }

    public static boolean createVertragDirectory(VertragObj vtr) throws IOException {
        String pathName = getVertragPath(vtr);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Neuer Gesellschafts Pfad: " + pathName);
        }

        File file = new File(pathName);

        if (file.exists()) {
            Log.logger.warn("Ein Ordner mit der neuen VertragsId existiert bereits.");
        }

        file.mkdirs();

        createIndexFile(pathName, vtr);

        String prodPath = pathName + File.separatorChar + "unterlagen";
        String vertragsPath = pathName + File.separatorChar + "dokumentation";
        String sonstigesPath = pathName + File.separatorChar + "sonstiges";
        String historyPath = pathName + File.separatorChar + "history";
        String schadenPath = pathName + File.separatorChar + "schaeden";
        String stoerfallPath = pathName + File.separatorChar + "stoerfaelle";

        String scansPath = pathName + File.separatorChar + "scan";

        file = new File(vertragsPath);
        file.mkdirs();

        file = new File(prodPath);
        file.mkdirs();

        file = new File(sonstigesPath);
        file.mkdirs();

        file = new File(historyPath);
        file.mkdirs();

        file = new File(schadenPath);
        file.mkdirs();

        file = new File(stoerfallPath);
        file.mkdirs();

        file = new File(scansPath);
        file.mkdirs();

        return true;
    }

    public static boolean createIndexFile(String directoryPath, VertragObj vtr) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Vertrag]\n");
        index.append("Id=");
        index.append(vtr.getId());
        index.append("\nKunde=");
        index.append(vtr.getKundenKennung());
        index.append("\nProduktId=");
        index.append(vtr.getProduktId());

        String file = directoryPath + File.separator + ".vertrag";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
