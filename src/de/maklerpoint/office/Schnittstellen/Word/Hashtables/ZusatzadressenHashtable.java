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
package de.maklerpoint.office.Schnittstellen.Word.Hashtables;

import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ZusatzadressenHashtable {
    
    private static Hashtable ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    public static Hashtable generateZusatzadressenHash(ZusatzadressenObj za, boolean OVERWRITE){
        ht = new Hashtable();
        
        if(OVERWRITE)
        {
            hashPut("STREET", za.getStreet());
            hashPut("PLZ", za.getPlz());
            hashPut("ORT", za.getOrt());
            hashPut("BUNDESLAND", za.getBundesland());
            hashPut("LAND", za.getLand());    

            hashPut("KOMMUNIKATION1", za.getCommunication1());
            hashPut("KOMMUNIKATION2", za.getCommunication2());
            hashPut("KOMMUNIKATION3", za.getCommunication3());
            hashPut("KOMMUNIKATION4", za.getCommunication4());
            hashPut("KOMMUNIKATION5", za.getCommunication5());
            hashPut("KOMMUNIKATION6", za.getCommunication6());

            hashPut("KOM1", za.getCommunication1()); // syn
            hashPut("KOM2", za.getCommunication2()); //..
            hashPut("KOM3", za.getCommunication3());
            hashPut("KOM4", za.getCommunication4());
            hashPut("KOM5", za.getCommunication5());
            hashPut("KOM6", za.getCommunication6()); // syn    

            hashPut("KOMMUNIKATION1_TYP", CommunicationTypes.getName(za.getCommunication1Type()));
            hashPut("KOMMUNIKATION2_TYP", CommunicationTypes.getName(za.getCommunication2Type()));
            hashPut("KOMMUNIKATION3_TYP", CommunicationTypes.getName(za.getCommunication3Type()));
            hashPut("KOMMUNIKATION4_TYP", CommunicationTypes.getName(za.getCommunication4Type()));
            hashPut("KOMMUNIKATION5_TYP", CommunicationTypes.getName(za.getCommunication5Type()));
            hashPut("KOMMUNIKATION6_TYP", CommunicationTypes.getName(za.getCommunication6Type()));

            hashPut("KOM1_TYP", CommunicationTypes.getName(za.getCommunication1Type())); // syn
            hashPut("KOM2_TYP", CommunicationTypes.getName(za.getCommunication2Type())); // ..
            hashPut("KOM3_TYP", CommunicationTypes.getName(za.getCommunication3Type()));
            hashPut("KOM4_TYP", CommunicationTypes.getName(za.getCommunication4Type()));
            hashPut("KOM5_TYP", CommunicationTypes.getName(za.getCommunication5Type()));
            hashPut("KOM6_TYP", CommunicationTypes.getName(za.getCommunication6Type())); // syn
            
            // TODO Überlegen ob overwrite hier auch nötig ist
            
            hashPut("KOMMENTARE", za.getComments());
            hashPut("COMMENTS", za.getComments());

            hashPut("CUSTOM1", za.getCustom1());
            hashPut("CUSTOM2", za.getCustom2());
            hashPut("CUSTOM3", za.getCustom3());
            
            hashPut("BENUTZERDEFINIERT1", za.getCustom1()); // syn
            hashPut("BENUTZERDEFINIERT2", za.getCustom2()); // syn
            hashPut("BENUTZERDEFINIERT3", za.getCustom3()); // syn
        }
        
        
        return ht;
    }
    
    private static void hashPut(String val, Object val2) {
        if(val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if(val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }
}
