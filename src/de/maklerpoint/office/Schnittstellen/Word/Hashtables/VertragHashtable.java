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

import de.maklerpoint.office.Konstanten.Vertraege;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Vertraege.VertragObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class VertragHashtable {
    private static HashMap ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    
    public static HashMap generatVersichererhash(VertragObj vtr) {
        ht = new HashMap();
        
        WaehrungenObj waer = VersicherungsRegistry.getWaehrung(vtr.getWaehrungId());

        hashPut("VTR_ID", vtr.getId());

        hashPut("VTR_PARENTID", vtr.getParentId());
        hashPut("VTR_PARENT", VertragRegistry.getVertrag(vtr.getParentId()));
        
        hashPut("VTR_VERSICHERERID", vtr.getVersichererId());
        hashPut("VTR_VERSICHERER", VersicherungsRegistry.getVersicher(vtr.getVersichererId()));
        
        hashPut("VTR_PRODUKTID", vtr.getProduktId());
        hashPut("VTR_PRODUKT", VersicherungsRegistry.getProdukt(vtr.getProduktId()));
        
        hashPut("VTR_KUNDENKENNUNG", vtr.getKundenKennung());
        hashPut("VTR_KUNDE", KundenRegistry.getKunde(vtr.getKundenKennung()));
        
        hashPut("VTR_MANDANTENID", vtr.getMandantenId());
        hashPut("VTR_MANDANT", MandantenRegistry.getMandant(vtr.getMandantenId(), true));        
        
        hashPut("VTR_BENUTZERID", vtr.getBenutzerId());
        hashPut("VTR_BENUTZER", BenutzerRegistry.getBenutzer(vtr.getBenutzerId(), true));
        
        hashPut("VTR_VERTRAGSTYP", Vertraege.getTypName(vtr.getVertragsTyp()));
        
        hashPut("VTR_VERTRAGSGRP", vtr.getVertragGrp());
        hashPut("VTR_VERTRAGSGRUPPE", vtr.getVertragGrp()); // syn
        
        hashPut("VTR_POLICENNR", vtr.getPolicennr());
        hashPut("VTR_POLICENNUMMER", vtr.getPolicennr()); // syn
        
        hashPut("VTR_POLICEN_DATUM", df2.format(vtr.getPoliceDatum()));
        
        hashPut("VTR_WERTUNG_DATUM", df2.format(vtr.getWertungDatum()));
                
        hashPut("VTR_COURTAGE", BooleanTools.getBooleanJaNein(vtr.isCourtage()));
        
        hashPut("VTR_ZAHLWEISE", Vertraege.getZahlungName(vtr.getZahlWeise()));
        hashPut("VTR_ZAHLUNGSWEISE", Vertraege.getZahlungName(vtr.getZahlWeise())); // syn
        
        hashPut("VTR_ZAHLART", vtr.getZahlArt());
        hashPut("VTR_ZAHLUNGSART", vtr.getZahlArt());
        
        hashPut("VTR_SELBSTBETEILIGUNG", vtr.getSelbstbeteiligung() + " " + waer.getBezeichnung()); 
        hashPut("VTR_SB", vtr.getSelbstbeteiligung() + " " + waer.getBezeichnung()); 
        
        hashPut("VTR_JAHRESNETTO", vtr.getJahresNetto() + " " + waer.getBezeichnung()); 
                        
        hashPut("VTR_STEUER", vtr.getSteuer()+ " " + waer.getBezeichnung()); 
        hashPut("VTR_GEBUEHR", vtr.getGebuehr()+ " " + waer.getBezeichnung()); 
        
        hashPut("VTR_JAHRESBRUTTO", vtr.getJahresBrutto()+ " " + waer.getBezeichnung()); 
        
        hashPut("VTR_RABATT", vtr.getRabatt() + " %");
        hashPut("VTR_ZUSCHLAG", vtr.getZuschlag() + " %");
        
        hashPut("VTR_ANTRAG", df.format(vtr.getAntrag()));
        hashPut("VTR_ANTRAG_DATUM", df.format(vtr.getAntrag())); // syn
        
        hashPut("VTR_FAELLIG", df.format(vtr.getAntrag()));
        hashPut("VTR_HAUPTFAELLIG", df.format(vtr.getAntrag()));
        
        hashPut("VTR_WAEHRUNGID", vtr.getWaehrungId());
        hashPut("VTR_WAEHRUNG", waer.getBezeichnung());
        
        hashPut("VTR_BEGINN", df.format(vtr.getAntrag()));
        hashPut("VTR_ABLAUF", df.format(vtr.getAntrag()));
        
        hashPut("VTR_EINGANG_MAKLER", df2.format(vtr.getAntrag()));
        
        hashPut("VTR_STORNO", df2.format(vtr.getAntrag()));
        hashPut("VTR_STORNO_DATUM", df.format(vtr.getAntrag()));
        
        hashPut("VTR_STORNO_GRUND", vtr.getVersichererId());
        
        hashPut("VTR_LAUFZEIT", vtr.getVersichererId());
        
        hashPut("VTR_ZUSCHLAG", vtr.getVersichererId());
        
        hashPut("VTR_COURTAGE_DATUM", df.format(vtr.getAntrag()));
        
        hashPut("VTR_KOMMENTARE", vtr.getComments());
        hashPut("VTR_COMMENTS", vtr.getComments());

        hashPut("VTR_CUSTOM1", vtr.getCustom1());
        hashPut("VTR_CUSTOM2", vtr.getCustom2());
        hashPut("VTR_CUSTOM3", vtr.getCustom3());
        hashPut("VTR_CUSTOM4", vtr.getCustom4());
        hashPut("VTR_CUSTOM5", vtr.getCustom5());

        hashPut("VTR_BENUTZERDEFINIERT1", vtr.getCustom1()); // syn
        hashPut("VTR_BENUTZERDEFINIERT2", vtr.getCustom2()); // syn
        hashPut("VTR_BENUTZERDEFINIERT3", vtr.getCustom3()); // syn
        hashPut("VTR_BENUTZERDEFINIERT4", vtr.getCustom4()); // syn
        hashPut("VTR_BENUTZERDEFINIERT5", vtr.getCustom5()); // syn

        hashPut("VTR_CREATED", df2.format(vtr.getCreated()));
        hashPut("VTR_ERSTELLT", df2.format(vtr.getCreated())); // syn
        
        hashPut("VTR_MODIFIED", df2.format(vtr.getModified()));
        hashPut("VTR_ZULETZT_GEAENDERT", df2.format(vtr.getModified())); // syn
        
        hashPut("VTR_STATUS", Vertraege.getStatusName(vtr.getStatus()));

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
