/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/03 13:10
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */

package de.maklerpoint.office.Schnittstellen.Word.Hashtables;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class GenerateAnsprechpartnerHashtable {

    private static Hashtable htAn;
    private static MandantenObj mandant;
    private static BenutzerObj benutzer;

    public static Hashtable generateAnsprechpartnerHashmap() {
        return generateAnsprechpartnerHashmap(BasicRegistry.currentUser, BasicRegistry.currentMandant);
    }

    public static Hashtable generateAnsprechpartnerHashmap(BenutzerObj benutzer, MandantenObj mandant) {
        GenerateAnsprechpartnerHashtable.mandant = mandant;
        GenerateAnsprechpartnerHashtable.benutzer = benutzer;

        htAn = new Hashtable();
        hashPut("MNAME", mandant.getFirmenName());
        hashPut("M_NAME", mandant.getFirmenName()); // syn
        
        hashPut("MNAME2", mandant.getFirmenZusatz());
        hashPut("M_NAME2", mandant.getFirmenZusatz()); // syn
        
        hashPut("MAKLERINFORMATIONEN", getMaklerInformationen());
        hashPut("M_INFORMATIONEN", getMaklerInformationen());
        
        hashPut("ANSPRECHPARTNER", getAnString());

        hashPut("BRIEFKOPF", getBriefkopfString());
        hashPut("MFG", getMFG());
        
        hashPut("HTML_MFG", getHTMLMFG());

        return htAn;
    }

    private static String getBriefkopfString() {
        if(mandant.getBriefkopf() != null)
            return mandant.getBriefkopf();
        else {
            String bkop = mandant.getFirmenName()  + " · " + mandant.getStrasse() + " · " + mandant.getPlz() + " " + mandant.getOrt();
            return bkop;
        }
    }
    
    // TODO Konfigurierbar
    
    private static String getMFG(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Mit freundlichen Grüßen,");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(benutzer.getVorname()).append(" ").append(benutzer.getNachname());
        sb.append("\n");
        sb.append(mandant.getFirmenName());
        sb.append("\n");
        sb.append("Tel. ").append(benutzer.getTelefon());
        sb.append("\n");
        
        return sb.toString();
    }
    
    private static String getHTMLMFG(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Mit freundlichen Grüßen,");
        sb.append("<br />");
        sb.append("<br />");
        sb.append("<br />");
        sb.append(benutzer.getVorname()).append(" ").append(benutzer.getNachname());
        sb.append("<br />");
        sb.append(mandant.getFirmenName());
        sb.append("<br />");
        sb.append("Tel. ").append(benutzer.getTelefon());
        sb.append("<br />");
        
        return sb.toString();
    }
    
    private static String getMaklerInformationen(){
        StringBuilder sb = new StringBuilder();
        sb.append(mandant.getFirmenName());
        
        if(mandant.getFirmenZusatz() != null) {
            sb.append("\n");
            sb.append(mandant.getFirmenZusatz());
        }
        
        if(mandant.getFirmenZusatz2() != null) {
            sb.append("\n");
            sb.append(mandant.getFirmenZusatz2());
        }
        
        return sb.toString();
    }

    private static String getAnString() {
        StringBuilder sb = new StringBuilder();

        sb.append(benutzer.getVorname()).append(" ").append(benutzer.getNachname());
        sb.append("\n");

        if(benutzer.getTelefon() != null) {
            sb.append("Tel. ").append(benutzer.getTelefon());
            sb.append("\n");
        } else {
            if(mandant.getTelefon() != null) {
                sb.append("Fax. ").append(mandant.getTelefon());
                sb.append("\n");
            }
        }
        
        if(benutzer.getFax() != null) {
            sb.append("Fax. ").append(benutzer.getFax());
            sb.append("\n");
        } else {
            if(mandant.getFax() != null) {
                sb.append("Fax. ").append(mandant.getFax());
                sb.append("\n");
            }
        }

        if(benutzer.getMobil() != null) {
            sb.append("Mobil. ").append(benutzer.getMobil());
            sb.append("\n");
        }

        if(benutzer.getEmail() != null) {
            sb.append("E-Mail. ").append(benutzer.getEmail());
            sb.append("\n");
        } else {
            if(mandant.getEmail() != null) {
                sb.append("Fax. ").append(mandant.getEmail());
                sb.append("\n");
            }
        }
        
        return sb.toString();
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

        htAn.put(val, val2);
    }
    

}
