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

import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Logging.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FileTools {

    /**
     * Opens a JFileChooser to select a directory and returns
     * the selected path and the given Dialog name
     * @param dialogText
     * @param fileFormat
     * @return filepath
     */
    public static String saveFile(String dialogText, String fileFormat) {
        String path = null;
        JFileChooser fc = new JFileChooser(Filesystem.getRootPath());
        fc.setDialogTitle(dialogText);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            path = file.getPath();
        } else {
            //System.out.println(returnVal);
            return null;
        }

        if (fileFormat == null) {
            return path;
        }

        if (!path.toLowerCase().endsWith(fileFormat)) {
            path = path + fileFormat;
        }

        if (new File(path).exists()) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die ausgewählte Datei wirklich überschreiben?",
                    "Die Datei ist bereits vorhanden", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION) {
                return null;
            }
        }

        return path;
    }

    /**
     * 
     * @param dialogText
     * @return filepath
     */
    public static String openFile(String dialogText) {
        String path = null;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dialogText);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            path = file.getPath();
        } else {
            return null;
        }

        return path;
    }
    /**
     * 
     * @param dialogText
     * @return File
     */
    public static File openFileFile(String dialogText) {

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dialogText);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return file;
        } else {
            return null;
        }
    }
    /**
     * 
     * @param filename
     * @return Checksum
     */
    public static long getChecksum(String filename) {
        long checksum = 0;
        CheckedInputStream cis = null;
        try {
            cis = new CheckedInputStream(new FileInputStream(filename), new CRC32());
            byte[] buf = new byte[128];

            while (cis.read(buf) >= 0) {
            }

            checksum = cis.getChecksum().getValue();
            cis.close();
        } catch (Exception e) {
            Log.logger.warn("Konnte die Checksume für die Datei nicht berechnen (crc32): " + filename, e);
        }
        return checksum;
    }
    /**
     * 
     * @param file
     * @return Filecontent
     * @throws IOException 
     */
    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder.toString();
    }
}
