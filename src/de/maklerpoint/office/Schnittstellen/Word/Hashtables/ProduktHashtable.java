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

import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author yves
 */
public class ProduktHashtable {

    private static Hashtable ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static Hashtable generateProdukthash(ProduktObj pr) {     
        ht = new Hashtable();

        WaehrungenObj waer = VersicherungsRegistry.getWaehrung(pr.getWaehrung());
        
        hashPut("P_ID", pr.getId());

        hashPut("P_VERSICHERERID", pr.getVersichererId());
        hashPut("P_VERSICHERER", VersicherungsRegistry.getVersicher(pr.getVersichererId()));

        hashPut("P_SPARTEID", pr.getSparteId());
        hashPut("P_SPARTE", VersicherungsRegistry.getSparte(pr.getVersichererId()));

        hashPut("P_ARTID", pr.getArt());
        hashPut("P_ART", pr.getArt()); // TODO
        
        hashPut("P_TARIF", pr.getTarif());
        hashPut("P_TARIFBASIS" , pr.getTarifBasis());

        hashPut("P_BEZEICHNUNG", pr.getBezeichnung());
        hashPut("P_NAME", pr.getBezeichnung()); // syn

        hashPut("P_KUERZEL", pr.getKuerzel());

        hashPut("P_VERTRAGSMASKE", pr.getVertragsmaske()); // TODO

        hashPut("P_VERMITTELBAR", BooleanTools.getBooleanJaNein(pr.isVermittelbar()));

        hashPut("P_VERSICHERUNGSARTID", pr.getVersicherungsart());
        hashPut("P_VERSICHERUNGSART", pr.getVersicherungsart()); // TODO

        hashPut("P_RISIKOTYPID", pr.getRisikotyp());
        hashPut("P_RISIKOTYP", pr.getRisikotyp()); // TODO

        hashPut("P_VERSICHERUNGSSUMME", pr.getVersicherungsSumme());
        hashPut("P_BEWERTUNGSSUMME", pr.getBewertungsSumme());

        hashPut("P_BEDINGUNGEN", pr.getBedingungen());
        
        hashPut("P_WAEHRUNG_ID", pr.getWaehrung());
        hashPut("P_WAEHRUNG", waer.getBezeichnung());

        hashPut("P_NETTOPRAEMIE_PAUSCHAL", pr.getNettopraemiePauschal()); // TODO
        hashPut("P_NP_PAUSCHAL", pr.getNettopraemiePauschal()); // syn

        hashPut("P_NETTOPRAEMIE_ZUSATZ", pr.getNettopraemieZusatz());
        hashPut("P_NP_ZUSATZ", pr.getNettopraemieZusatz()); // syn

        hashPut("P_NETTOPRAEMIE_GESAMT", pr.getNettopraemieGesamt());
        hashPut("P_NP_GESAMT", pr.getNettopraemieGesamt()); // syn

        hashPut("P_ZUSATZINFO", pr.getZusatzInfo());
        hashPut("P_ZUSATZ", pr.getZusatzInfo());

        hashPut("P_KOMMENTARE", pr.getComments());
        hashPut("P_COMMENTS", pr.getComments());

        hashPut("P_CUSTOM1", pr.getCustom1());
        hashPut("P_CUSTOM2", pr.getCustom2());
        hashPut("P_CUSTOM3", pr.getCustom3());
        hashPut("P_CUSTOM4", pr.getCustom4());
        hashPut("P_CUSTOM5", pr.getCustom5());

        hashPut("P_BENUTZERDEFINIERT1", pr.getCustom1()); // syn
        hashPut("P_BENUTZERDEFINIERT2", pr.getCustom2()); // syn
        hashPut("P_BENUTZERDEFINIERT3", pr.getCustom3()); // syn
        hashPut("P_BENUTZERDEFINIERT4", pr.getCustom4()); // syn
        hashPut("P_BENUTZERDEFINIERT5", pr.getCustom5()); // syn

        hashPut("P_CREATED", df2.format(pr.getCreated()));
        hashPut("P_MODIFIED", df2.format(pr.getModified()));

        String bencr = BenutzerRegistry.getBenutzer(pr.getCreatorId(), true).toString();
        
        hashPut("P_CREATOR", bencr);
        hashPut("P_ERSTELLER", bencr); // syn

        hashPut("P_STATUS", Status.getName(pr.getStatus()));

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
