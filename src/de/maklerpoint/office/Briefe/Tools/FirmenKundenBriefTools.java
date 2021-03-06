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

package de.maklerpoint.office.Briefe.Tools;

import de.maklerpoint.office.Kunden.FirmenObj;
import java.util.Hashtable;

/**
 *
 * @author yves
 */
public class FirmenKundenBriefTools {

    private static Hashtable htAnschreiben;

    public static String getKundenAnsprechpartnerAnrede(){
        return "";
    }
    
    public static String getKundenBriefAnrede() {
        return "Sehr geehrte Damen und Herren,";
    }
    
     public static String getKundenBriefAnschriftONELINE(FirmenObj kunde) {
        String sb = null;
        
        if(!kunde.isFirmenPostfach())
            sb = kunde.getFirmenName() + ", " + kunde.getFirmenStrasse() + ", " + kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt();
        else
            sb = kunde.getFirmenName() + ", " + kunde.getFirmenPostfachName() + ", "
                    + kunde.getFirmenPostfachPlz() + " " + kunde.getFirmenPostfachOrt();
        
        return sb;
     }

    

    /**
     * 
     * @param kunde
     * @return
     */

    public static String generateKundeAnschrift(FirmenObj kunde) {
        String anschrift = "";

        if(kunde.getFirmenNameZusatz() == null && kunde.getFirmenNameZusatz2() ==  null) {
              anschrift = kunde.getFirmenName() + "\n";
              anschrift = anschrift + kunde.getFirmenStrasse() + "\n\n" + kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt();
        } else {
            anschrift = kunde.getFirmenName() + "\n";

            if(kunde.getFirmenNameZusatz() != null && kunde.getFirmenNameZusatz2() !=  null) {
                anschrift = anschrift + kunde.getFirmenNameZusatz() + "\n" + kunde.getFirmenNameZusatz2()
                        + "\n" + kunde.getFirmenStrasse() + "\n" + kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt();

            } else {
                if(kunde.getFirmenNameZusatz() != null)
                     anschrift = anschrift + kunde.getFirmenNameZusatz() + "\n";
                else
                     anschrift = anschrift + kunde.getFirmenNameZusatz2() + "\n";

                anschrift = anschrift + kunde.getFirmenStrasse() + "\n";
                anschrift = anschrift + kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt();
            }
        }

        return anschrift;
    }
    
    public static Hashtable generateBriefAdresse(FirmenObj kunde) {
        htAnschreiben = new Hashtable();

        if(kunde.getFirmenNameZusatz() == null && kunde.getFirmenNameZusatz2() ==  null) {
            hashPut("ANSCHRIFT1", kunde.getFirmenName());
            hashPut("ANSCHRIFT2", kunde.getFirmenStrasse());
            hashPut("ANSCHRIFT3", "");
            hashPut("ANSCHRIFT4", kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt());
            hashPut("ANSCHRIFT5", "");
        } else {
            hashPut("ANSCHRIFT1", kunde.getFirmenName());
            
            if(kunde.getFirmenNameZusatz() != null && kunde.getFirmenNameZusatz2() !=  null) {
                hashPut("ANSCHRIFT2", kunde.getFirmenNameZusatz());
                hashPut("ANSCHRIFT3", kunde.getFirmenNameZusatz2());
                hashPut("ANSCHRIFT4", kunde.getFirmenStrasse());
                hashPut("ANSCHRIFT5", kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt());
            } else {
                if(kunde.getFirmenNameZusatz() != null)
                    hashPut("ANSCHRIFT2", kunde.getFirmenNameZusatz());
                else
                    hashPut("ANSCHRIFT2", kunde.getFirmenNameZusatz2());
                hashPut("ANSCHRIFT3", kunde.getFirmenStrasse());
                hashPut("ANSCHRIFT4", "");
                hashPut("ANSCHRIFT5", kunde.getFirmenPLZ() + " " + kunde.getFirmenStadt());
            }
        }

        return htAnschreiben;
    }

    /**
     * 
     * @param val
     * @param val2
     */

    private static void hashPut(String val, Object val2) {
        if(val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if(val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        htAnschreiben.put(val, val2);
    }

}
