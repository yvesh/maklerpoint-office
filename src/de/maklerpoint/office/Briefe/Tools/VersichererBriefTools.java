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
package de.maklerpoint.office.Briefe.Tools;

import de.maklerpoint.office.Versicherer.VersichererObj;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class VersichererBriefTools {

    public static String getVersichererAnrede(VersichererObj vers) {
        StringBuilder sb = new StringBuilder();

        sb.append("Sehr geehrte Damen und Herren,"); // TODO implement Ansprechpartner

        return (sb.toString());
    }

    public static String getVersichererAnschriftONLINE(VersichererObj vers) {
        String sb = null;


        if (!vers.isPostfach()) {
            if (vers.getNameZusatz() != null && vers.getNameZusatz().length() > 0) {
                sb = vers.getName() + " " + vers.getNameZusatz() + ",  "
                        + vers.getStrasse() + ", " + vers.getPlz() + " " + vers.getStadt();
            } else {
                 sb = vers.getName() + ",  " + vers.getStrasse() + ", " 
                         + vers.getPlz() + " " + vers.getStadt();
            }
        } else {
            if (vers.getNameZusatz() != null && vers.getNameZusatz().length() > 0) {
                sb = vers.getName() + " " + vers.getNameZusatz() + ",  " +
                        vers.getPostfachName() + ", " + vers.getPostfachPlz() + " " + vers.getPostfachOrt();
            } else {
                sb = vers.getName() + ",  " +
                        vers.getPostfachName() + ", " + vers.getPostfachPlz() + " " + vers.getPostfachOrt();
            }
        }


        return sb;
    }
    
    public static String getVersichererAnschrift(VersichererObj vers) {
        String anschrift = null;
        
        if (!vers.isPostfach()) {
            if (vers.getNameZusatz() == null && vers.getNameZusatz2() == null){
                anschrift = vers.getName() + "\n" + vers.getStrasse() + "\n\n"  +vers.getPlz() + " " + vers.getStadt();                                                
            } else if (vers.getNameZusatz() != null && vers.getNameZusatz2() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz() + "\n" + vers.getNameZusatz2() + "\n"                                                                        
                        + vers.getStrasse() + "\n\n"  +vers.getPlz() + " " + vers.getStadt();                 
            } else if (vers.getNameZusatz() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz() + "\n"                                                                    
                        + vers.getStrasse() + "\n\n"  +vers.getPlz() + " " + vers.getStadt();   
            } else if (vers.getNameZusatz2() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz2() + "\n"                                                                    
                        + vers.getStrasse() + "\n\n"  +vers.getPlz() + " " + vers.getStadt();   
            }            
        } else {
            if (vers.getNameZusatz() == null && vers.getNameZusatz2() == null){
                anschrift = vers.getName() + "\n" + vers.getPostfachName() + "\n\n" + 
                        vers.getPostfachPlz() + " " + vers.getPostfachOrt();                                                
            } else if (vers.getNameZusatz() != null && vers.getNameZusatz2() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz() + "\n" + vers.getNameZusatz2() + "\n"                                                                        
                        + vers.getPostfachName() + "\n\n"  +vers.getPostfachPlz() + " " + vers.getPostfachOrt();                 
            } else if (vers.getNameZusatz() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz() + "\n"                                                                    
                        + vers.getPostfachName() + "\n\n"  +vers.getPostfachPlz() + " " + vers.getPostfachOrt();   
            } else if (vers.getNameZusatz2() != null) {
                anschrift = vers.getName() + "\n" + vers.getNameZusatz2() + "\n"                                                                    
                        + vers.getPostfachName() + "\n\n"  +vers.getPostfachPlz() + " " + vers.getPostfachOrt();   
            }
            
        }                
        
        return anschrift;
    }
    
}
