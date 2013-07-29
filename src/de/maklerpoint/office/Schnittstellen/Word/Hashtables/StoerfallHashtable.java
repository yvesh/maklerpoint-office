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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class StoerfallHashtable {
    
    private static HashMap ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    
    public static HashMap generateStoerfallhash(Object stoerfall) {
        ht = new HashMap();

//        hashPut("V_ID", vs.getId());
//
//        hashPut("V_PARENTID", vs.getParentId());
//        hashPut("V_PARENT", VersicherungsRegistry.getVersicher(vs.getParentId(), true));
//        hashPut("V_MUTTERFIRMA", VersicherungsRegistry.getVersicher(vs.getParentId(), false)); // syn
//        hashPut("V_PARENTNAME", VersicherungsRegistry.getVersicher(vs.getParentId(), true)); // syn
//
//        hashPut("V_VUNUMMER", vs.getVuNummer());
//        hashPut("V_VUNR", vs.getVuNummer()); // syn
//        
//        hashPut("V_NAME", vs.getName());
//        hashPut("V_BEZEICHNUNG", vs.getName()); // syn
//
//        hashPut("V_NAME_ZUSATZ", vs.getNameZusatz());
//        hashPut("V_NAME_ZUSATZ2", vs.getNameZusatz2());
//        hashPut("V_KUERZEL", vs.getKuerzel());
//
//        hashPut("V_GESELLSCHAFTS_NUMMER", vs.getGesellschaftsNr());
//        hashPut("V_GNR", vs.getGesellschaftsNr()); //syn
//
//        hashPut("V_STRASSE", vs.getStrasse());
//        hashPut("V_PLZ", vs.getPlz());
//        hashPut("V_STADT", vs.getStadt());
//        hashPut("V_ORT", vs.getStadt());
//        hashPut("V_BUNDESLAND", vs.getBundesLand());
//        hashPut("V_LAND", vs.getLand());
//        
//        hashPut("V_POSTFACH_USE", vs.isPostfach());
//        hashPut("V_POSTFACH", vs.getPostfachName());
//        hashPut("V_POSTFACH_NAME", vs.getPostfachName()); // syn
//        hashPut("V_POSTFACH_PLZ", vs.getPostfachPlz());
//        hashPut("V_POSTFACH_ORT", vs.getStadt());
//
//        hashPut("V_VERMITTELBAR", vs.isVermittelbar()); 
//
//        hashPut("V_KOMMUNIKATION1", vs.getCommunication1());
//        hashPut("V_KOMMUNIKATION2", vs.getCommunication2());
//        hashPut("V_KOMMUNIKATION3", vs.getCommunication3());
//        hashPut("V_KOMMUNIKATION4", vs.getCommunication4());
//        hashPut("V_KOMMUNIKATION5", vs.getCommunication5());
//        hashPut("V_KOMMUNIKATION6", vs.getCommunication6());
//
//        hashPut("V_KOM1", vs.getCommunication1()); // syn
//        hashPut("V_KOM2", vs.getCommunication2()); //..
//        hashPut("V_KOM3", vs.getCommunication3());
//        hashPut("V_KOM4", vs.getCommunication4());
//        hashPut("V_KOM5", vs.getCommunication5());
//        hashPut("V_KOM6", vs.getCommunication6()); // syn
//
//        hashPut("V_KOMMUNIKATION1_TYP", CommunicationTypes.getName(vs.getCommunication1Type()));
//        hashPut("V_KOMMUNIKATION2_TYP", CommunicationTypes.getName(vs.getCommunication2Type()));
//        hashPut("V_KOMMUNIKATION3_TYP", CommunicationTypes.getName(vs.getCommunication3Type()));
//        hashPut("V_KOMMUNIKATION4_TYP", CommunicationTypes.getName(vs.getCommunication4Type()));
//        hashPut("V_KOMMUNIKATION5_TYP", CommunicationTypes.getName(vs.getCommunication5Type()));
//        hashPut("V_KOMMUNIKATION6_TYP", CommunicationTypes.getName(vs.getCommunication6Type()));
//
//        hashPut("V_KOM1_TYP", CommunicationTypes.getName(vs.getCommunication1Type())); // syn
//        hashPut("V_KOM2_TYP", CommunicationTypes.getName(vs.getCommunication2Type())); // ..
//        hashPut("V_KOM3_TYP", CommunicationTypes.getName(vs.getCommunication3Type()));
//        hashPut("V_KOM4_TYP", CommunicationTypes.getName(vs.getCommunication4Type()));
//        hashPut("V_KOM5_TYP", CommunicationTypes.getName(vs.getCommunication5Type()));
//        hashPut("V_KOM6_TYP", CommunicationTypes.getName(vs.getCommunication6Type())); // syn
// 
//        hashPut("V_KOMMENTARE", vs.getComments());
//        hashPut("V_COMMENTS", vs.getComments());
//
//        hashPut("V_CUSTOM1", vs.getCustom1());
//        hashPut("V_CUSTOM2", vs.getCustom2());
//        hashPut("V_CUSTOM3", vs.getCustom3());
//        hashPut("V_CUSTOM4", vs.getCustom4());
//        hashPut("V_CUSTOM5", vs.getCustom5());
//
//        hashPut("V_BENUTZERDEFINIERT1", vs.getCustom1()); // syn
//        hashPut("V_BENUTZERDEFINIERT2", vs.getCustom2()); // syn
//        hashPut("V_BENUTZERDEFINIERT3", vs.getCustom3()); // syn
//        hashPut("V_BENUTZERDEFINIERT4", vs.getCustom4()); // syn
//        hashPut("V_BENUTZERDEFINIERT5", vs.getCustom5()); // syn
//
//        hashPut("V_CREATED", df2.format(vs.getCreated()));
//        hashPut("V_MODIFIED", df2.format(vs.getModified()));
//        
//        hashPut("V_STATUS", Status.getName(vs.getStatus()));

        return ht;
    }
    
    
    private static void hashPut(String val, Object val2) {
        if(val == null) {
            return;
        }

        if(val2 == null) {
            val2 = "";
        }

        ht.put(val, val2);
    }
    
}
