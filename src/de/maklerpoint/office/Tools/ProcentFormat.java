/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       28.07.2011 09:53:00
 *  File:       ProcentFormat
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

import java.text.NumberFormat;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ProcentFormat {
    
    /**
     * 
     * @param value
     * @return Formated String
     */
    public static String formatPercent(double value) {
        String ergebnis = null;
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);        
        ergebnis = nf.format(value).concat(" %");
        
        return ergebnis;
    }
    
}
