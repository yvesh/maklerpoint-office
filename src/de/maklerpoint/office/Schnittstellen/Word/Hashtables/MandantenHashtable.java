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

import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayStringTools;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe
 */

public class MandantenHashtable {

    private static Hashtable ht = null;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static Hashtable generateMandantenhash(boolean refresh) {
        return generateMandantenhash(BasicRegistry.currentMandant, refresh);
    }

    public static Hashtable generateMandantenhash(MandantenObj mandant, boolean refresh) {
        if(ht != null && refresh == false) {
            return ht;
        }

        ht = new Hashtable();

        hashPut("M_ID", mandant.getId());
        hashPut("M_PARENTID", mandant.getParentId());
        hashPut("M_PARENT", MandantenRegistry.getMandant(mandant.getParentId(), true));
        hashPut("M_CREATORID", mandant.getCreatorId());
        
        if(mandant.getCreatorId() == -1)
            hashPut("M_CREATOR", "System");
        else
            hashPut("M_CREATOR", "System");

        hashPut("M_NAME", mandant.getFirmenName());
        hashPut("M_NAME2", mandant.getFirmenZusatz());

        hashPut("M_FIRMENNAME", mandant.getFirmenName());
        hashPut("M_FIRMENNAME_ZUSATZ", mandant.getFirmenZusatz());
        hashPut("M_FIRMENNAME_ZUSATZ2", mandant.getFirmenZusatz2());
        hashPut("M_VERMITTLUNGNAMEN", mandant.getVermittlungNamen());
        hashPut("M_VERTRETUNGSBERECHTIGTE", ArrayStringTools.arrayToString(mandant.getVertretungsBerechtigteNachname(), ", "));
        hashPut("M_FIRMENTYP", mandant.getFirmenTyp()); // @depr
        hashPut("M_RECHTSFORM", mandant.getFirmenRechtsform());
        hashPut("M_POSTFACH", mandant.getPostfach());
        hashPut("M_POSTFACH_PLZ", mandant.getPostfachPlz());
        hashPut("M_POSTFACH_ORT", mandant.getPostfachOrt());
        hashPut("M_FILIALTYP", mandant.getFilialTyp());
        hashPut("M_MITARBEITERZAHL", mandant.getFilialMitarbeiterZahl());

        hashPut("M_GESCHAEFTSLEITER", mandant.getGeschaeftsleiter());
        hashPut("M_GESCHAEFTSFUEHRER", mandant.getGeschaeftsleiter()); // SYNONYM

        hashPut("M_GESELLSCHAFTER", ArrayStringTools.arrayToString(mandant.getGesellschafter(), ", "));
        hashPut("M_STEUERNUMMER", mandant.getSteuerNummer());
        hashPut("M_USTIDNUMMER", mandant.getUstNummer());
        hashPut("M_VERMOEGENSHAFTPFLICHT", mandant.getVermoegensHaftpflicht());
        hashPut("M_BETEILIGUNGEN_VU", mandant.isBeteiligungenVU());
        hashPut("M_BETEILIGUNGEN_MAK", mandant.isBeteiligungenMAK());
        hashPut("M_VERBANDSMITGLIEDSCHAFTEN", ArrayStringTools.arrayToString(mandant.getVerbandsMitgliedschaften(), ","));
        hashPut("M_BERATERTYP", mandant.getBeraterTyp());
        hashPut("M_IHK_NAME", mandant.getIhkName());
        hashPut("M_IHK_REGISTRIERNUMMER", mandant.getIhkRegistriernummer());
        hashPut("M_IHK_STATUS", mandant.getIhkStatus());
        hashPut("M_IHK_ABWEICHUNGEN", mandant.getIhkAbweichungen());
        hashPut("M_VERSICHERER", mandant.getVersicherListe()); // TODO
        hashPut("M_34C", mandant.is34c());
        hashPut("M_34D", mandant.is34d());
        hashPut("M_BRIEFKOPF", mandant.getBriefkopf());
        hashPut("M_GEWERBEAMT_SHOW", mandant.isGewerbeamtShow());
        hashPut("M_GEWERBEAMT_NAME", mandant.getGewerbeamtName());
        hashPut("M_GEWERBEAMT_PLZ", mandant.getGewerbeamtPLZ());
        hashPut("M_GEWERBEAMT_ORT", mandant.getGewerbeamtOrt());
        hashPut("M_GEWERBEAMT_STRASSE", mandant.getGewerbeamtStrasse());
        hashPut("M_HANDELSREGISTER_SHOW", mandant.isHandelsregisterShow());
        hashPut("M_HANDELSREGISTER_NAME", mandant.getHandelsregisterName());
        hashPut("M_HANDELSREGISTER_STRASSE", mandant.getHandelsregisterStrasse());
        hashPut("M_HANDELSREGISTER_PLZ", mandant.getHandelsregisterPLZ());
        hashPut("M_HANDELSREGISTER_ORT", mandant.getHandelsregisterOrt());
        hashPut("M_HANDELSREGISTER_NUMMER", mandant.getHandelsregisterRegistrierNummer());
        hashPut("M_BESCHWERDESTELLEN", ArrayStringTools.arrayToString(mandant.getBeschwerdeStellen(), ",")); // TODO
        hashPut("M_ADRESSE_ZUSATZ", mandant.getAdressZusatz()); //@deprec
        hashPut("M_ADRESSE_ZUSATZ2", mandant.getAdressZusatz2()); //@deprec
        hashPut("M_STRASSE", mandant.getStrasse());
        hashPut("M_PLZ", mandant.getPlz());
        hashPut("M_ORT", mandant.getOrt());
        hashPut("M_BUNDESLAND", mandant.getBundesland());
        hashPut("M_LAND", mandant.getLand());
        hashPut("M_BANK_NAME", mandant.getBankName());
        hashPut("M_BANK_KONTONUMMER", mandant.getBankKonto());
        hashPut("M_BANK_KONTOEIGENTUEMER", mandant.getBankEigentuemer());
        hashPut("M_BANK_LEITZAHL", mandant.getBankLeitzahl());
        hashPut("M_BANK_IBAN", mandant.getBankIBAN());
        hashPut("M_BANK_BIC", mandant.getBankBIC());
        hashPut("M_TELEFON", mandant.getTelefon());
        hashPut("M_TELEFON2", mandant.getTelefon2());
        hashPut("M_TELEFON3", mandant.getTelefon3());
        hashPut("M_FAX", mandant.getFax());
        hashPut("M_FAX2", mandant.getFax2());
        hashPut("M_MOBIL", mandant.getMobil());
        hashPut("M_MOBIL2", mandant.getMobil2());
        hashPut("M_EMAIL", mandant.getEmail());
        hashPut("M_EMAIL2", mandant.getEmail2());
        hashPut("M_SECURE_EMAIL", mandant.getSecureMail());
        hashPut("M_EMAIL_SIGNATUR", mandant.getEmailSignatur());
        hashPut("M_HOMEPAGE", mandant.getHomepage());
        hashPut("M_HOMPEAGE2", mandant.getHomepage2());

        hashPut("M_CUSTOM1", mandant.getCustom1());
        hashPut("M_CUSTOM2", mandant.getCustom2());
        hashPut("M_CUSTOM3", mandant.getCustom3());
        hashPut("M_CUSTOM4", mandant.getCustom4());
        hashPut("M_CUSTOM5", mandant.getCustom5());
        hashPut("M_CUSTOM6", mandant.getCustom6());
        hashPut("M_CUSTOM7", mandant.getCustom7());
        hashPut("M_CUSTOM8", mandant.getCustom8());
        hashPut("M_CUSTOM9", mandant.getCustom9());
        hashPut("M_CUSTOM10", mandant.getCustom10());

        hashPut("BENUTZERDEFINIERT1", mandant.getCustom1()); // syn
        hashPut("BENUTZERDEFINIERT2", mandant.getCustom2()); // syn
        hashPut("BENUTZERDEFINIERT3", mandant.getCustom3()); // syn
        hashPut("BENUTZERDEFINIERT4", mandant.getCustom4()); // syn
        hashPut("BENUTZERDEFINIERT5", mandant.getCustom5()); // syn
        hashPut("BENUTZERDEFINIERT6", mandant.getCustom6()); // syn
        hashPut("BENUTZERDEFINIERT7", mandant.getCustom7()); // syn
        hashPut("BENUTZERDEFINIERT8", mandant.getCustom8()); // syn
        hashPut("BENUTZERDEFINIERT9", mandant.getCustom9()); // syn
        hashPut("BENUTZERDEFINIERT10", mandant.getCustom10()); // syn

        hashPut("M_COMMENTS", mandant.getComments());
        hashPut("M_KOMMENTARE", mandant.getComments()); // syn
        
        hashPut("M_CREATED", df2.format(mandant.getCreated()));
        hashPut("M_MODIFIED", df2.format(mandant.getModified()));
        hashPut("M_LETZTEVERWENDUNG", df2.format(mandant.getLastUsed()));

        hashPut("M_STATUS", Status.getName(mandant.getStatus()));

        String footer = getCompleteFooter(mandant);
        String htmlfooter = footer; // getCompleteFooter(mandant);
        
        hashPut("FOOTER", footer);
        hashPut("M_FOOTER", footer); // syn
        hashPut("MAKLER_FOOTER", footer); // syn
        
        hashPut("HTML_FOOTER", htmlfooter);
        hashPut("M_HTMLFOOTER", htmlfooter);// syn
        hashPut("MAKLER_HTMLFOOTER", htmlfooter);// syn

        return ht;
    }

    public static String getCompleteFooter(MandantenObj mandant) {
        StringBuilder sb = new StringBuilder();

        sb.append(mandant.getFirmenName());
        sb.append(" · ");

        if(mandant.getFirmenZusatz() != null) {
            sb.append(mandant.getFirmenZusatz());
            sb.append(" · ");
        }

        if(mandant.getFirmenZusatz2() != null) {
            sb.append(mandant.getFirmenZusatz2());
            sb.append(" · ");
        }

        sb.append(mandant.getStrasse());
        sb.append(", ");
        sb.append(mandant.getPlz());
        sb.append(" ");
        sb.append(mandant.getOrt());
        sb.append(" · ");

        if(mandant.getPostfach() != null) {
            sb.append("Postfach: ");
            sb.append(mandant.getPostfach());
            sb.append(", ");
            sb.append(mandant.getPostfachPlz());
            sb.append(" ");
            sb.append(mandant.getPostfachOrt());
            sb.append(" · ");
        }

        sb.append("Tel.: ");
        sb.append(mandant.getTelefon());
        sb.append(" · ");

        if(mandant.getTelefon2() != null) {
            sb.append("Tel. 2: ");
            sb.append(mandant.getTelefon());
            sb.append(" · ");
        }

        if(mandant.getFax() != null) {
            sb.append("Fax: ");
            sb.append(mandant.getFax());
            sb.append(" · ");
        }

        if(mandant.getFax2() != null) {
            sb.append("Fax 2: ");
            sb.append(mandant.getFax2());
            sb.append(" · ");
        }

        if(mandant.getEmail() != null) {
            sb.append("E-Mail: ");
            sb.append(mandant.getEmail());
            sb.append(" · ");
        }

        if(mandant.getEmail2() != null) {
            sb.append("E-Mail 2: ");
            sb.append(mandant.getEmail());
            sb.append(" · ");
        }

        if(mandant.getHomepage() != null) {
            sb.append("Internet: ");
            sb.append(mandant.getHomepage());
            sb.append(" · ");
        }

        sb.append(getMandantBeraterTyp(mandant));
        sb.append(" · ");

        if(mandant.getIhkName() != null) {
            sb.append("Registerstelle: ");
            sb.append(mandant.getIhkName());

            if(mandant.getIhkRegistriernummer() != null) {
                sb.append(", ");
                sb.append("Registernummer: ");
                sb.append(mandant.getIhkRegistriernummer());
            }

            if(mandant.getIhkAbweichungen() != null) {
                sb.append(", ");
                sb.append(mandant.getIhkAbweichungen());
            }

            sb.append(" · ");
        }

        if(mandant.isHandelsregisterShow() == true) {
            sb.append("Handelsregister: ");
            sb.append(mandant.getHandelsregisterName());
            sb.append(", ");
            if(mandant.getHandelsregisterPLZ() != null) {
                sb.append(mandant.getHandelsregisterPLZ());
                sb.append(" ");
                sb.append(mandant.getHandelsregisterOrt());
                sb.append(" ");
            }
            sb.append(mandant.getHandelsregisterRegistrierNummer());
            sb.append(" · ");
        }

        if(mandant.isGewerbeamtShow() == true) {
            if(mandant.getGewerbeamtName() != null) {
                sb.append("Zuständiges Gewerbeamt: ");
                sb.append(mandant.getGewerbeamtName());
            }

            if(mandant.getGewerbeamtStrasse() != null) {
                sb.append(", ");
                sb.append(mandant.getGewerbeamtStrasse());
            }

            if(mandant.getGewerbeamtStrasse() == null) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }

            if(mandant.getGewerbeamtPLZ() != null) {
                sb.append(mandant.getGewerbeamtPLZ());
            }

            if(mandant.getGewerbeamtOrt() != null) {
                sb.append(" ");
                sb.append(mandant.getGewerbeamtOrt());
            }

            sb.append(" · ");
        }

        if(mandant.getGeschaeftsleiter() != null) {
            sb.append("Geschäftsführer: ");
            sb.append(mandant.getGeschaeftsleiter());
            sb.append(" · ");
        }

        if(mandant.getGesellschafter() != null) {
            sb.append("Gesellschafter: ");
            sb.append(ArrayStringTools.arrayToString(mandant.getGesellschafter(), ", "));
            sb.append(" · ");
        }

        if(mandant.getUstNummer() != null) {
            sb.append("UST-ID-Nummer: ");
            sb.append(mandant.getUstNummer());
            sb.append(" · ");
        } else {
            if(mandant.getSteuerNummer() != null) {
                sb.append("Steuernummer: ");
                sb.append(mandant.getSteuerNummer());
                sb.append(" · ");
            }
        }

        if(mandant.getBankKonto() != null) {
            sb.append("Bankverbindung: ");
            sb.append("Kto. ");
            sb.append(mandant.getBankKonto());
            sb.append(", Blz. ");
            sb.append(mandant.getBankLeitzahl());
            sb.append(", ");
            sb.append(mandant.getBankName());
            if(mandant.getBankEigentuemer() != null && !mandant.getBankEigentuemer().equalsIgnoreCase(mandant.getFirmenName())) {
                sb.append(", Inh.  ");
                sb.append(mandant.getBankEigentuemer());
            }
            if(mandant.getBankIBAN() != null) {
                sb.append(", IBAN ");
                sb.append(mandant.getBankIBAN());
                sb.append(", BIC: ");
                sb.append(mandant.getBankBIC());
            }
            sb.append(" · ");
        }

        return sb.toString();
    }

    private static String getMandantBeraterTyp(MandantenObj mandant) {
//        Versicherungsmakler
//Versicherungsberater
//Versicherungsvertretter für ein VU
//Mehrfirmenvertretter
        if(mandant.getBeraterTyp().equalsIgnoreCase("versicherungsmakler")) {
            return "Versicherungsmakler mit Erlaubnispflicht nach §34d Abs. 1 der Gewerbeordnung (GewO)";
        } else if (mandant.getBeraterTyp().equalsIgnoreCase("versicherungsberater")) {
            return "Versicherusberater mit Erlaubnispflicht nach §34e der Gewerbeordnung (GewO)";
        } else if (mandant.getBeraterTyp().equalsIgnoreCase("versicherungsvertretter für ein vu")) {
            return "Versicherungsvertretter mit Erlaubnispflicht nach §34d Abs. 1 der Gewerbeordnung (GewO)";
        } else if (mandant.getBeraterTyp().equalsIgnoreCase("mehrfirmenvertretter")) {
            return "Versicherungsvertretter mit Erlaubnispflicht nach §34d Abs. 1 der Gewerbeordnung (GewO)";
        } else {
            return "Versicherungsmakler mit Erlaubnispflicht nach §34d Abs. 1 der Gewerbeordnung (GewO)";
        }

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
