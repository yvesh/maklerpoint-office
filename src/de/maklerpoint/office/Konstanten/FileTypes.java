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
package de.maklerpoint.office.Konstanten;

import de.maklerpoint.office.Tools.ImageTools;
import javax.swing.ImageIcon;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class FileTypes {
    
    public final static int UNKNOWN = -1;
    public final static int DOC = 0;
    public final static int DOCX = 1;
    public final static int WXML = 2;
    public final static int XLS = 3;
    public final static int XLSX = 4;
    public final static int CSV = 5;
    public final static int PDF = 6;
    public final static int JPG = 7;
    public final static int PNG = 8;
    public final static int GIF = 9;
    public final static int HTML = 10;
    public final static int XML = 11;
    public final static int ZIP = 12;
    public final static int RAR = 13;
    public final static int TXT = 14;
    public final static int PPT = 15; // powerpoint
        
    public final static int DATABASE_TABLE = 16;
    
    public static final ImageIcon UNKNOWN_IMAGE = ImageTools.createImageIcon(
            "de/acyrance/CRM/Gui/resources/icon_clean/documents/document--exclamation.png");
    
    public static final ImageIcon[] TYPE_IMAGES = {
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-word-text.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-word-text.png"), // TODO Find a icon for docx
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-word-text.png"), // TODO Find a icon for wxml
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-excel-csv.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-excel-table.png"),        
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-table.png"), // CSV        
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-pdf-icon.png"),        
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/blue-document-image.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/blue-document-image.png"), // TODO icon png
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/blue-document-image.png"), // TODO icon gif       
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-code.png"),        
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-xaml.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-zipper.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-zipper.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-text.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-powerpoint.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-table.png")
    };
    
    
    public static String getName(int id) {
        switch(id){
            default:
            case UNKNOWN:
                return "Unbekannt";
                
            case DOC:
                return "Word 1997-2003 Dokumente (.doc)";
                
            case DOCX:
                return "Word 2007-2010 Dokument (.docx)";
                
            case WXML:
                return "Word XML Dokument (.xml)";
                
            case XLS:
                return "Excel 1997-2003 Dokument (.xls)";
                
            case XLSX:
                return "Excel 2007-2010 Dokument (.xlsx)";
                
            case CSV:
                return "CSV (kommagetrennt) (.csv)";
                
            case PDF:
                return "Adobe PDF DOkument (.pdf)";
                
            case JPG:
                return "JPG Bild (.jpg)";
                
            case PNG:
                return "PNG Bild (.png)";
                
            case GIF:
                return "Gif Bild (.gif)";
                
            case HTML:
                return "HTML Dokument (.html)";
                
            case XML:
                return "XML Dokument (.xml)";
                
            case ZIP:
                return "ZIP Archiv (.zip)";
                
            case RAR:
                return "RAR Archiv (.rar)";
                
            case TXT:
                return "Text Datei (.txt)";
              
        }
    }
    
    public static String getShortName(int id) {
        switch(id){
            default:
            case UNKNOWN:
                return "Unbekannt";
                
            case DOC:
                return ".doc";
                
            case DOCX:
                return ".docx";
                
            case WXML:
                return ".xml";
                
            case XLS:
                return ".xls";
                
            case XLSX:
                return ".xlsx";
                
            case CSV:
                return ".csv";
                
            case PDF:
                return ".pdf";
                
            case JPG:
                return ".jpg";
                
            case PNG:
                return ".png";
                
            case GIF:
                return ".gif";
                
            case HTML:
                return ".html";
                
            case XML:
                return ".xml";
                
            case ZIP:
                return ".zip";
                
            case RAR:
                return ".rar";
       
             case TXT:
                return ".txt";
        }
    }
    
}
