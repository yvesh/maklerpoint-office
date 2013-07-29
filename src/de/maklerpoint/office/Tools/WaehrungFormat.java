/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       19.07.2011 13:07:55
 *  File:       WaehrungFormat
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

import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.text.NumberFormat;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class WaehrungFormat {
    
    /**
     * 
     * @param value
     * @param waer
     * @return formated String
     */
    
    public static String getFormatedWaehrung(double value, WaehrungenObj waer) {       
        
        String bezeichnung = "€";
        
        if(waer != null) {
            bezeichnung = waer.getBezeichnung();
        }
        
        String ergebnis = null;
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);        
        ergebnis = nf.format(value).concat(" ").concat(bezeichnung);
        
        return ergebnis;
    }
    
    
    // TODO Im Währungsdialog Unlöschbar machen
    
    /**
     * Schnelle Version mit hardcoded Ids!!
     * @param value
     * @param waerId
     * @return 
     */
    public static String getFormatedWaehrung(double value, int waerId) {       
               
        String bezeichnung = "€";
        
        if(waerId == 1) {
            bezeichnung = "€";
        } else if (waerId == 2) {
            bezeichnung = "DM";
        } else if (waerId == 3) {
            bezeichnung = "$";
        } else if (waerId == 4) {
            bezeichnung = "£";
        } else if (waerId == 5) {
            bezeichnung = "SFr";
        } else if (waerId == -1) {
            bezeichnung = "";
        } else {
            bezeichnung = VersicherungsRegistry.getWaehrung(waerId).getBezeichnung();
        }
        
        String ergebnis = null;
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);        
        ergebnis = nf.format(value).concat(" ").concat(bezeichnung);
        
        return ergebnis;
    }
    
}
