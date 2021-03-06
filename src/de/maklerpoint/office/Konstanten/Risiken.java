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
public class Risiken {
    
    public static final int IMMOBILIE = 0;
    public static final int FAHRZEUG = 1;
    public static final int TIER = 2;
    public static final int SONSTIGES = 3;
    
    public static String getRisikoname(int type){
        switch(type)
        {
            case 0:
                return "Immobilie";
                
            case 1:
                return "Fahrzeug";
                
            case 2:
                return "Tier";
                
            case 3:
                return "Sonstiges";
                
            default:
                return "Unbekannt";
        }
    }
    
    
}
