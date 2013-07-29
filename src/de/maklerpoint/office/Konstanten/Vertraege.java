/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2011/05/30 20:33
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

package de.maklerpoint.office.Konstanten;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class Vertraege {
    
    public static final int STATUS_AKTIV = 0;
    public static final int STATUS_STILLGELEGT = 1;
    public static final int STATUS_AUFGELOEST = 2;
    public static final int STATUS_NEUANTRAG = 3;
    public static final int STATUS_SONSTIGES = 4;
        
    public static final int ZAHLUNG_JAEHRLICH = 0;
    public static final int ZAHLUNG_HALBJAEHRLICH = 1;
    public static final int ZAHLUNG_QUARTAL = 2;
    public static final int ZAHLUNG_MONATLICH = 3;
    public static final int ZAHLUNG_SONSTIGES = 4; // Sollte nicht vorkommen
    
    public static final int TYP_NORMAL = 0;
    public static final int TYP_VORVERTRAG = 1;
    public static final int TYP_FOLGEVERTRAG = 2;
    
    /**
     * 
     * @param status
     * @return 
     */
    
    public static String getStatusName(int status){
        switch(status)
        {
            case 0:
                return "Aktiv";
                
            case 1:
                return "Beitragsfrei";
                
            case 2:
                return "Aufgelöst";
                
            case 3:
                return "Neuantrag";
                
            default:
            case 4:
                return "Sonstiges";
                
        }
    }
    
    /**
     * 
     * @param zahl
     * @return 
     */
    
    public static String getZahlungName(int zahl){
        switch(zahl)
        {
            case 0:
                return "Jährlich";
                
            case 1:
                return "Halbjährlich";
                
            case 2:
                return "Quartalsweise";
                
            case 3:
                return "Monatlich";
                
            default:
            case 4:
                return "Sonstige";
                
        }
    }
    
    /**
     * 
     * @param typ
     * @return 
     */
    
    public static String getTypName(int typ){
        switch(typ)
        {
            case 0:
                return "Normal";
                
            case 1:
                return "Folgevertrag";
                
            case 2:
                return "Vorvertrag";
                
            default:
                return "Unbekannter Typ";
                
        }
    }
    
}
