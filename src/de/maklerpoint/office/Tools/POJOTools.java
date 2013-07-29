/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       27.07.2011 10:50:07
 *  File:       POJOTools
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Tools;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class POJOTools {

    public static int calulcateLenght(Object[]... objects) {
        int val = 0;

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                val += objects[i].length;
            }
        }

        return val;
    }
}
