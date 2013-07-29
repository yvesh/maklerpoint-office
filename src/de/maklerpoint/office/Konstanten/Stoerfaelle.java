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
public class Stoerfaelle {
    
    public static final int STATUS_OFFEN = 0;
    public static final int STATUS_ERLEDIGT = 1;
    

    public static final String[] GRUENDE = {"Beitragsrückstand", "Kündigung Gesellschaft",
        "Kündigung Kunde", "Kündigung Wechsel", "Meldungbogen", "Risikowegfall", "Tarifanpassung",
        "Vertragserhöhung", "Vertragsreduzierung", "Widerspruch", "Sonstiges"
    };
    
    public static final String[] KATEGORIEN = {""};
    
    public static final String[] MAHNSTATUS = {"", "0", "1", "2", "3"};
    
    
    public static String getStatusName(int status){
        
        switch(status) {
            case STATUS_OFFEN:
                return "Offen";
                
            case STATUS_ERLEDIGT:
                return "Erledigt";
                
            default:
                return "Unbekannt";
        }
        
    }
}
