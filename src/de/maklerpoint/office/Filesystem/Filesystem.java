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

import de.maklerpoint.office.System.Configuration.Config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class Filesystem {

    public final static String[] ROOT_Directories = {"archiv", "backup", 
        "benutzer", "export", "kunden", "muell", "templates", "tmp", "vorlagen",
        "versicherer", "vorlagen"};

    /**
     * 
     * @return Root Pfad
     */

    public static String getRootPath() {
        if(Config.getConfigBoolean("offlineModus", false)) {
            String filepath = "includes".concat(File.separator)
                    .concat("localstorage").concat(File.separator).concat("filesystem");

            return filepath;
        }

        String filePath = Config.getConfigValue("filesystemPath", "filesystem");
        if(filePath.endsWith(File.separator) && filePath.length() > 1)
            filePath = filePath.substring(0, filePath.length() -1);

        filePath = filePath.replaceAll("/", File.separator);
        
        return filePath;
    }
    
    /**
     * 
     * @return Template Pfad
     */
    
    public static String getTemplatePath() {
        String tmpl = getRootPath() + File.separatorChar + "templates";                        
        return tmpl;
    }
    
    /**
     * 
     * @return Archiv Pfad
     */
    public static String getArchivePath() {
        return getRootPath() + File.separatorChar + "archive";
    }
    
    /**
     * 
     * @return Vorlage PFad
     */
    
    public static String getVorlagenPath() {
        String tmpl = getRootPath() + File.separatorChar + "vorlagen";                        
        return tmpl;
    }
    
    public static String getDeletePath() {
        return getRootPath() + File.separatorChar + "muell";
    }
    
    

    /**
     * Returns the size (including all files in subfolders) of the
     * given Folder in Bytes
     *
     * @param folder
     * @return foldersize
     */

    public static long getFolderSize(File folder){
        long foldersize = 0;
        File[] filelist = folder.listFiles();
        if(filelist == null || filelist.length == 0)
            return 0;

        for (int i = 0; i < filelist.length; i++)
        {
            if(filelist[i].isDirectory())
            {
                foldersize += getFolderSize(filelist[i]);
            } else {
                foldersize += filelist[i].length();
            }
        }

        return foldersize;
    }

    /**
     * Returns the number of files (including files in subfolders) of the
     * given Folder
     *
     * @param folder
     * @return filecount
     */

    public static long getFolderFileCount(File folder){
        long filecount = 0;

        File[] filelist = folder.listFiles();
        if(filelist == null || filelist.length == 0)
            return 0;

        for (int i = 0; i < filelist.length; i++)
        {
            if(filelist[i].isDirectory())
            {
                filecount += getFolderFileCount(filelist[i]);
            } else {
                filecount++;
            }
        }

        return filecount;
    }

    /**
     * 
     * @param folder
     * @return
     */


    public static ArrayList getFiles(File folder) {

        ArrayList files = new ArrayList();

        File[] filelist = folder.listFiles();

        if(filelist == null || filelist.length == 0)
            return null;

        for (int i = 0; i < filelist.length; i++)
        {
            if(filelist[i].isDirectory())
            {
                files.add(getFiles(filelist[i]));
            } else {
                files.add(filelist[i]);
            }
        }

        return files;
    }

    /**
     * 
     * @return
     */

    public static long getFreeSpace() {
        String path = getRootPath();

        File hdd = new File(path);
        return hdd.getFreeSpace();
    }

    /**
     * 
     * @return
     */

    public static String getTmpPath() {
        String filePath = Filesystem.getRootPath();
        String path = filePath + File.separatorChar + Config.get("tmpOrdner", "tmp");
        return path;
    }



    public static void initializeFilesystem() throws IOException {
        String pathName = getRootPath();

        File file = new File(pathName);
        file.mkdirs();

        createIndexFile(pathName, "root");

        for(int i = 0; i < ROOT_Directories.length; i++) {
            String dirpath = pathName + File.separator + ROOT_Directories[i];

            new File(dirpath).mkdirs();
            createIndexFile(dirpath, ROOT_Directories[i]);
        }
    }


    public static boolean createIndexFile(String directoryPath, String directory) throws IOException {

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Directory]\n");
        index.append("\nDir=");
        index.append(directory);

        String file = directoryPath + File.separator + ".maklerpoint";

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();

        return true;
    }
}
