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

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Versicherer.VersichererObj;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class BriefTools {
    
    public static String generateKundenAnschrift(KundenObj kunde){
     
        String anschrift = "";
        if(kunde != null)
            anschrift = KundenBriefTools.generateKundeAnschrift(kunde);     

        return anschrift;
    }
    
    public static String generateKundenAnschrift(FirmenObj firma){
     
        String anschrift = "";
        if(firma != null)
            anschrift = FirmenKundenBriefTools.generateKundeAnschrift(firma);     

        return anschrift;
    }
    
    public static String generateVersichererAnschricht(VersichererObj vers) {
        String anschrift = "";
        
        if(vers != null)
            anschrift = VersichererBriefTools.getVersichererAnschrift(vers);
        
        return anschrift;
    }
    
    
}
