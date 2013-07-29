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
public class Briefe {
    
    public static final int TYPE_BRIEF = 0;
    public static final int TYPE_FAX = 1;
    public static final int TYPE_EMAIL = 2;
    
    public static final String[] TYPES = {"Brief", "Fax", "E-Mail"};
    
    public static final ImageIcon[] TYPE_IMAGES = {
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-word-text.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone-fax.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/documents/document-outlook.png"),
    };
    
     public static String getName(int id) {
        switch(id) {
            case TYPE_BRIEF:
                return "Brief";
                
            case TYPE_FAX:
                return "Fax";
                
            case TYPE_EMAIL:
                return "E-Mail";
            
            default:
                return "Unbekannt"; // TODO Exception hinz.
        }
     }
    
    public static ImageIcon getIcon(int type){
        return null;
    }
    
}
