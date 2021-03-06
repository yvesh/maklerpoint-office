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

import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mime.MimeHelper;
import java.io.File;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FileTypeDetection {

    public static int getFileType(File file) {
        Object[] types = MimeHelper.getMimeTypes(file);

        for (int i = 0; i < types.length; i++) {
            if (types[i].toString().equalsIgnoreCase("text/xml")) {
                return FileTypes.XML;
            } else if (types[i].toString().equalsIgnoreCase("application/zip") 
                    || types[i].toString().equalsIgnoreCase("application/x-zip-compressed")) {
                if(file.getName().endsWith(".docx"))
                    return FileTypes.DOCX;
                
                return FileTypes.ZIP;
            } else if (types[i].toString().equalsIgnoreCase("image/jpeg")) {
                return FileTypes.JPG;
            } else if (types[i].toString().equalsIgnoreCase("image/png")) {
                return FileTypes.PNG;
            } else if (types[i].toString().equalsIgnoreCase("application/pdf")) {
                return FileTypes.PDF;
            } else if (types[i].toString().equalsIgnoreCase("application/excel")) {
                return FileTypes.XLS;
            } else if (types[i].toString().equalsIgnoreCase("text/csv")) {
                return FileTypes.CSV;
            } else if (types[i].toString().equalsIgnoreCase("text/html")) {
                return FileTypes.HTML;
            } else if (types[i].toString().equalsIgnoreCase("application/x-php")) {
                return FileTypes.HTML;
            } else if (types[i].toString().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                return FileTypes.XLSX;
            } else if (types[i].toString().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                return FileTypes.DOCX;
            } else if (types[i].toString().equalsIgnoreCase("text/plain")) {
                return FileTypes.TXT;
            } else if (types[i].toString().equalsIgnoreCase("application/octet-stream")) {
               return FileTypes.UNKNOWN;
            }

            Log.logger.info("Unbekannter Dateityp: " + types[i].toString() + " (Datei: " + file.getPath() + ")");
        }

        return -1;
    }
}
