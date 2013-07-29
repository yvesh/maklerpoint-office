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

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class Schaeden {
    
    public static final String[] MELDUNG_ART = {
        "Telefonisch", "Fax", "Persönlich", "Brief", "E-Mail", "Internet",
        "Gesellschaft", "Über Werkstatt", "Außendienst"    
    };
        
    public static final int STATUS_OFFEN = 0;
    public static final int STATUS_REGULIERT = 1;
       
    public static final int SCHADEN_NETTO = 0;
    public static final int SCHADEN_BRUTTO = 1;
            
    public static final int STATUS_FORDERUNG_OFFEN = 0;
    public static final int STATUS_FORDERUNG_BEZAHLT = 1;
    
    public static String getStatusName(int status) {
        switch(status){
            case 0:
                return "Offen";
                
            case 1:
                return "Reguliert";
                
            default:
                return "Unbekannt";            
        }
    }
    
    public static String getAbrechnungsArt(int nr) {
        switch(nr){
            case 0:
                return "Netto";
                
            case 1:
                return "Brutto";
                
            default:
                return "Unbekannt";            
        }
    }
            
}