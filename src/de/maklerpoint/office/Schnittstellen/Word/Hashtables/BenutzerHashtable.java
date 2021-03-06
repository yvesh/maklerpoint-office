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
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.Security.SecurityRoles;
import de.maklerpoint.office.System.Status;
import java.text.SimpleDateFormat;
import java.util.Hashtable;


/**
 *
 * @author yves
 */
public class BenutzerHashtable {

    private static Hashtable ht = null;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    public static Hashtable generateBenutzerhash(boolean refresh) {
        return generateBenutzerhash(BasicRegistry.currentUser, refresh);
    }

    public static Hashtable generateBenutzerhash(BenutzerObj benutzer, boolean refresh) {
        if(ht != null && refresh == false) {
            return ht;
        }

        ht = new Hashtable();

        hashPut("B_ID", benutzer.getId());
        hashPut("B_PARENTID", benutzer.getParentId()); // siehe B_BETREUER 
        MandantenObj mandant = MandantenRegistry.getMandant(benutzer.getFirmenId(), true);
        
        hashPut("B_MANDANT", mandant.toString());
        hashPut("B_MANDANT_ID", benutzer.getFirmenId());

        hashPut("B_ADRESSE_ZUSATZ", benutzer.getAddresseZusatz());
        hashPut("B_ADRESSE_ZUSATZ2", benutzer.getAddresseZusatz2());
        hashPut("B_ANREDE", benutzer.getAnrede());
        hashPut("B_COMMENTS", benutzer.getComments());
        
        hashPut("B_KENNUNG", benutzer.getKennung());
        hashPut("B_VORNAME", benutzer.getVorname());
        hashPut("B_VORNAME2", benutzer.getVorname2());
        hashPut("B_VORNAME_WEITERE", benutzer.getWeitereVornamen());
        hashPut("B_NACHNAME", benutzer.getNachname());        
        hashPut("B_WEITEREVORNAMEN", benutzer.getWeitereVornamen()); // @deprecated
        hashPut("B_STRASSE", benutzer.getStrasse());
        hashPut("B_STRASSE2", benutzer.getStrasse2());

        hashPut("B_PLZ", benutzer.getPlz());
        hashPut("B_ORT", benutzer.getOrt());
        hashPut("B_LAND", benutzer.getLand());
        hashPut("B_TELEFON", benutzer.getTelefon());
        hashPut("B_TELEFON2", benutzer.getTelefon2());
        hashPut("B_FAX", benutzer.getFax());
        hashPut("B_FAX2", benutzer.getFax2());
        hashPut("B_MOBIL", benutzer.getMobil());
        hashPut("B_MOBIL2", benutzer.getMobil2());
        hashPut("B_EMAIL", benutzer.getEmail());
        hashPut("B_EMAIL2", benutzer.getEmail2());
        hashPut("B_HOMEPAGE", benutzer.getHomepage());
        hashPut("B_HOMEPAGE2", benutzer.getHomepage2());

        hashPut("B_GEBURTSDATUM", benutzer.getGeburtsDatum());

        hashPut("B_CREATED", df2.format(benutzer.getCreated()));
        hashPut("B_MODIFIED", df2.format(benutzer.getModified()));
        hashPut("B_BENUTZERLEVEL", SecurityRoles.getUserLevelName(benutzer.getLevel()));
        hashPut("B_LETZTEANMELDUNG", df2.format(benutzer.getLastlogin()));
        hashPut("B_LOGINCOUNT", benutzer.getLogincount());
        hashPut("B_BENUTZERNAME", benutzer.getUsername());

        hashPut("B_CUSTOM1", benutzer.getCustom1());
        hashPut("B_CUSTOM2", benutzer.getCustom2());
        hashPut("B_CUSTOM3", benutzer.getCustom3());
        hashPut("B_CUSTOM4", benutzer.getCustom4());
        hashPut("B_CUSTOM5", benutzer.getCustom5());

        hashPut("B_COMMENTS", benutzer.getComments());
        hashPut("B_KOMMENTARE", benutzer.getComments()); // syn

        hashPut("B_STATUS", Status.getName(benutzer.getStatus()));

        if(!(benutzer.getParentId() == 0 || benutzer.getParentId() == -1)) {
            BenutzerObj parentBen = BenutzerRegistry.getBenutzer(benutzer.getParentId(), true);
            hashPut("B_BETREUER", parentBen.getVorname() + " " + parentBen.getNachname() + " [" + parentBen.getKennung() + "]");
        } else {
            hashPut("B_BETREUER", "");
        }

        if(benutzer.isUnterVermittler())
            hashPut("B_UNTERVERMITTLER", "Ja");
        else
            hashPut("B_UNTERVERMITTLER", "Nein");

        // TODO ADD FIELDS FROM custom tables (zusatzadressen etc.)

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
