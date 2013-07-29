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

package de.maklerpoint.office.Tools;

import de.maklerpoint.office.start.CRM;
import javax.swing.ImageIcon;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ImageTools {

    public static ImageIcon createImageIcon(String path) {
        if(path == null)
            return null;
        
        java.net.URL imgURL = CRM.class.getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Konnte Image Datei nicht finden: " + path);
                return null;
        }
    }

}
