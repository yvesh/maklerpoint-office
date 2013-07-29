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

import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Registry.BasicRegistry;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class MaklerBriefTools {
    
    /**
     * 
     * @return 
     */
    
    public static String generateMaklerAnschrift() {
        String anschrift = "";

        MandantenObj mandant = BasicRegistry.currentMandant;
        
        if(mandant.getFirmenZusatz() == null && mandant.getFirmenZusatz2() == null) {
            anschrift = mandant.getFirmenName() + "\n" + mandant.getStrasse()
                    + "\n" + mandant.getPlz() + " " + mandant.getOrt();
        } else {
            if(mandant.getFirmenZusatz() != null && mandant.getFirmenZusatz2() != null) {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz() + "\n"
                        + mandant.getFirmenZusatz2() + "\n" + mandant.getStrasse()
                        + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            } else if (mandant.getFirmenName() != null) {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz() + "\n"
                         + mandant.getStrasse() + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            } else {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz2() + "\n"
                         + mandant.getStrasse() + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            }
        }
        
        return anschrift;
    }
    
}
