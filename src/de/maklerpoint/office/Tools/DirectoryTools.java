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
package de.maklerpoint.office.Tools;

import com.l2fprod.common.swing.JDirectoryChooser;
import com.l2fprod.common.swing.plaf.JDirectoryChooserAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import de.maklerpoint.office.Filesystem.Filesystem;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DirectoryTools {

    /**
     * 
     * @param dir
     * @return
     */
    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] entries = dir.list();
            for (int x = 0; x < entries.length; x++) {
                File aktFile = new File(dir.getPath(), entries[x]);
                deleteDirectory(aktFile);
            }
            if (dir.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (dir.delete()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Opens a JFileChooser to select a directory and returns
     * the selected path
     * @return directory
     */
    public static String getDirectory() {
        return getDirectory("Ordner Auswahl");
    }

    public static String getDirectory(String dialogText) {
        String path = null;
        LookAndFeelAddons.contribute(new JDirectoryChooserAddon());

        JDirectoryChooser fc = new JDirectoryChooser();

        fc.setDialogTitle(dialogText);
        fc.setShowingCreateDirectory(true);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if (!file.isDirectory()) {
                System.out.println("Kein ordner!");
            }

            path = file.getPath();
        } else {
            return null;
        }

        return path;
    }
    /**
     * Opens a JFileChooser to select a directory and returns
     * the selected path and the given Dialog name
     * @param dialogText
     * @return
     */
//    public static String getDirectory(String dialogText){
//        String path = null;
//        JFileChooser fc = new JFileChooser(Filesystem.getRootPath());
//        fc.setDialogTitle(dialogText);
//        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int returnVal = fc.showOpenDialog(null);
//        File file = null;
//        if(returnVal == JFileChooser.APPROVE_OPTION){
//            file = fc.getCurrentDirectory();
//            path = file.getPath();
//        }
//
//        return path;
//    }
}
