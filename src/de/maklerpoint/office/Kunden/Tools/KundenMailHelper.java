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
package de.maklerpoint.office.Kunden.Tools;

import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class KundenMailHelper {
 
   
    /**
     * TODO improve für empty return values
     */
    /**
     * 
     * @param kunde
     * @return 
     */    
    public static String getKundenMail(KundenObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
        else if (kunde.getCommunication6Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication6();
                        
        return null;
    }
    /**
     * 
     * @param kunde
     * @return 
     */
    public static String getZusatzadressenMail(ZusatzadressenObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
        else if (kunde.getCommunication6Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication6();
                        
        return null;
    }
    
    /**
     * 
     * @param kunde
     * @return 
     */
    public static String getGeschKundenMail(FirmenObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
        else if (kunde.getCommunication6Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication6();
                        
        return null;
    }
    
    public static String getVersichererMail(VersichererObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
        else if (kunde.getCommunication6Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication6();
                        
        return null;
    }
    
    public static String getAnsprechpartnerMail(FirmenAnsprechpartnerObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
                        
        return null;
    }
    
    public static String getKontaktMail(KontaktObj kunde){
        if(kunde.getCommunication1Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication1();
        else if (kunde.getCommunication2Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication2();
        else if (kunde.getCommunication3Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication3();
        else if (kunde.getCommunication4Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication4();
        else if (kunde.getCommunication5Type() == CommunicationTypes.EMAIL)
            return kunde.getCommunication5();
                        
        return null;
    }
    
    public static String getProduktMail(ProduktObj prod){
        return getVersichererMail(VersicherungsRegistry.getVersicher(prod.getId()));
    }
    
    
}
