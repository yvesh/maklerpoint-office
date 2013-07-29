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
package de.maklerpoint.office.Schnittstellen.XML;

import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.KundenXMLParent;
import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class PrivatkundenXMLExport {
    
    private String ziel;
    private KundenObj[] kunden = null;
    
    public PrivatkundenXMLExport(String ziel, KundenObj[] kunden){
        this.ziel = ziel;
        this.kunden = kunden;
    }
    
    public void write() throws Exception{
        Serializer serializer = new Persister();
        File result = new File(ziel);
        
        KundenXMLParent kml = new KundenXMLParent();
        kml.setKunden(kunden);
        
        serializer.write(kml, result);   
    }
    
}
