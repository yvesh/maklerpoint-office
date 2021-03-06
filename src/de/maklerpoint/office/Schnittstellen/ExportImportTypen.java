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

package de.maklerpoint.office.Schnittstellen;

import org.jdesktop.swingx.util.OS;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportImportTypen {

    public static final int CSV = 0;
    public static final int XLS = 1;
    public static final int DOC = 2;
    public static final int PDF = 3;
    public static final int XLSX = 4;
    public static final int SQL = 5;
    public static final int TXT = 6;
    public static final int ZIP = 7;
    public static final int XML = 8;
    public static final int ICAL = 9;

    /**
     * 
     * @param type
     * @return
     */

    public static String getTypeName(int type){
         switch(type) {
            case ExportImportTypen.CSV:
                return(".csv");
               
           case ExportImportTypen.XLS:
                return(".xls");
               
           case ExportImportTypen.DOC: {
                if(OS.isLinux()){
                    return (".xml");
                }
                return(".doc");
           }
               
           case ExportImportTypen.PDF:
                return(".pdf");

             case ExportImportTypen.XLSX:
                 return(".xlsx");
                
           case ExportImportTypen.SQL:
                return(".sql");

           case ExportImportTypen.TXT:
                 return(".txt");

           case ExportImportTypen.ZIP:
                 return(".zip");
               
           case ExportImportTypen.XML:
               return(".xml");
               
           case ExportImportTypen.ICAL:
               return(".ics");

           default:
                 return null;
        }
    }

    public static String getDialogName(int type){
         switch(type) {
            case ExportImportTypen.CSV:
                return("CSV Datei speichern (.csv)");

           case ExportImportTypen.XLS:
                return("Excel Datei speichern (.xls)");

           case ExportImportTypen.DOC:
                return("Word Datei speichern (.doc)");

           case ExportImportTypen.PDF:
                return("PDF Datei speichern (.pdf)");

           case ExportImportTypen.XLSX:
                return("Excel 2007 Datei speichern (.xlsx)");

           case ExportImportTypen.SQL:
                return("SQL Datei speichern (.sql)");

           case ExportImportTypen.TXT:
                return("Text Datei speichern (.txt)");

           case ExportImportTypen.ZIP:
                 return("Zip datei speichern (.zip)");

           case ExportImportTypen.XML:
               return("Als XML Datei speichern");
               
           case ExportImportTypen.ICAL:
               return("iCal Kalender speichern (.ics)");
               
           default:
                 return null;
        }
    }
    
}
