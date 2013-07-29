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

import de.maklerpoint.office.Bank.BankKontoObj;
import de.maklerpoint.office.Bank.Tools.BankKontoSQLMethods;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Kunden_BetreuungObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.Tools.Kunden_BetreuungSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Tools.ProcentFormat;
import de.maklerpoint.office.Tools.WaehrungFormat;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import org.openide.util.Exceptions;

/**
 *
 * @author yves
 */
public class KundenHashtable {

    private static Hashtable ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static Hashtable generatKundenhash(KundenObj kunde, boolean refresh) {
        if (ht != null && refresh == false) {
            return ht;
        }

        ht = new Hashtable();

        hashPut("ID", kunde.getId());

        hashPut("KENNUNG", kunde.getKundenNr());
        hashPut("KNDR", kunde.getKundenNr()); // syn
        hashPut("KUNDENNUMMER", kunde.getKundenNr()); // syn

        hashPut("NAME", kunde.getVorname() + " " + kunde.getNachname());

        hashPut("K_MANDANT", MandantenRegistry.getMandant(kunde.getMandantenId(), true));

        hashPut("KUNDENNUMMER", kunde.getKundenNr());

        hashPut("ANREDE", kunde.getAnrede());
        hashPut("TITEL", kunde.getTitel());

        hashPut("ARBEITGEBER", kunde.getFirma());
        hashPut("FIRMA", kunde.getFirma()); // syn

        hashPut("ADRESSE_ZUSATZ", kunde.getAdresseZusatz());
        hashPut("ADRESSE_ZUSATZ2", kunde.getAdresseZusatz2());

        hashPut("VORNAME", kunde.getVorname());
        hashPut("VORNAME2", kunde.getVorname2());

        hashPut("NACHNAME", kunde.getNachname());

        hashPut("VORNAMEWEITERE", kunde.getVornameWeitere());
        hashPut("WEITEREVORNAMEN", kunde.getVornameWeitere()); // syn

        hashPut("STREET", kunde.getStreet());
        hashPut("STRASSE", kunde.getStreet());

        hashPut("PLZ", kunde.getPlz());
        hashPut("ORT", kunde.getStadt());
        hashPut("BUNDESLAND", kunde.getBundesland());
        hashPut("LAND", kunde.getLand());
        hashPut("TYP", kunde.getTyp());

        hashPut("KOMMUNIKATION1", kunde.getCommunication1());
        hashPut("KOMMUNIKATION2", kunde.getCommunication2());
        hashPut("KOMMUNIKATION3", kunde.getCommunication3());
        hashPut("KOMMUNIKATION4", kunde.getCommunication4());
        hashPut("KOMMUNIKATION5", kunde.getCommunication5());
        hashPut("KOMMUNIKATION6", kunde.getCommunication6());

        hashPut("KOM1", kunde.getCommunication1()); // syn
        hashPut("KOM2", kunde.getCommunication2()); //..
        hashPut("KOM3", kunde.getCommunication3());
        hashPut("KOM4", kunde.getCommunication4());
        hashPut("KOM5", kunde.getCommunication5());
        hashPut("KOM6", kunde.getCommunication6()); // syn

        hashPut("KOMMUNIKATION1_TYP", CommunicationTypes.getName(kunde.getCommunication1Type()));
        hashPut("KOMMUNIKATION2_TYP", CommunicationTypes.getName(kunde.getCommunication2Type()));
        hashPut("KOMMUNIKATION3_TYP", CommunicationTypes.getName(kunde.getCommunication3Type()));
        hashPut("KOMMUNIKATION4_TYP", CommunicationTypes.getName(kunde.getCommunication4Type()));
        hashPut("KOMMUNIKATION5_TYP", CommunicationTypes.getName(kunde.getCommunication5Type()));
        hashPut("KOMMUNIKATION6_TYP", CommunicationTypes.getName(kunde.getCommunication6Type()));

        hashPut("KOM1_TYP", CommunicationTypes.getName(kunde.getCommunication1Type())); // syn
        hashPut("KOM2_TYP", CommunicationTypes.getName(kunde.getCommunication2Type())); // ..
        hashPut("KOM3_TYP", CommunicationTypes.getName(kunde.getCommunication3Type()));
        hashPut("KOM4_TYP", CommunicationTypes.getName(kunde.getCommunication4Type()));
        hashPut("KOM5_TYP", CommunicationTypes.getName(kunde.getCommunication5Type()));
        hashPut("KOM6_TYP", CommunicationTypes.getName(kunde.getCommunication6Type())); // syn

        hashPut("EMAIL", KundenMailHelper.getKundenMail(kunde));

        hashPut("FAMILIENSTAND", kunde.getFamilienStand());
        hashPut("NATIONALITAET", kunde.getNationalitaet());

        hashPut("GEBURTSDATUM", kunde.getGeburtsdatum());
        hashPut("KINDERZAHL", kunde.getKinderZahl());

        hashPut("BERUF", kunde.getBeruf());
        hashPut("BERUFSBESONDERHEITEN", kunde.getBerufsBesonderheiten());
        hashPut("BERUF_BESONDERHEITEN", kunde.getBerufsBesonderheiten());
        hashPut("BERUF_TYP", kunde.getBerufsTyp());
        hashPut("BERUF_OPTIONEN", kunde.getBerufsOptionen());

        hashPut("BERUF_BUEROTAETIGKEIT", kunde.getAnteilBuerotaetigkeit());
        hashPut("RENTENBEGINN", kunde.getBeginnRente());

        hashPut("BEAMTER", BooleanTools.getBooleanJaNein(kunde.isBeamter()));
        hashPut("OEFFENTLICHERDIENST", BooleanTools.getBooleanJaNein(kunde.isOeffentlicherDienst()));

        hashPut("EINKOMMENBRUTTO", WaehrungFormat.getFormatedWaehrung(kunde.getEinkommen(), -1));
        hashPut("EINKOMMENNETTO", WaehrungFormat.getFormatedWaehrung(kunde.getEinkommenNetto(), -1));
        hashPut("STEUERTABELLE", kunde.getSteuertabelle());
        hashPut("STEUERKLASSE", kunde.getSteuerklasse());
        hashPut("KIRCHENSTEUER", kunde.getKirchenSteuer());

        hashPut("KINDERFREIBETRAG", kunde.getKinderFreibetrag());
        hashPut("RELIGION", kunde.getReligion());

        hashPut("HAUSHALT_ROLLE", kunde.getRolleImHaushalt());
        hashPut("HAUSHALT_WEITEREPERSONEN", kunde.getWeiterePersonen());
        hashPut("HAUSHALT_WEITEREPERSONEN_INFO", kunde.getWeiterePersonenInfo());
        hashPut("FAMILIENPLANUNG", kunde.getFamilienPlanung());

        hashPut("WERBER_KUNDENNUMMER", kunde.getWerberKennung());

        if (!"unbekannt".equalsIgnoreCase(kunde.getWerberKennung()) && kunde.getWerberKennung() != null) {
//            System.out.println("Kennung: " + kunde.getWerberKennung());
            hashPut("WERBER", KundenRegistry.getKunde(kunde.getWerberKennung()));
        } else {
            hashPut("WERBER", kunde.getWerberKennung());
        }

        try {
            BankKontoObj[] bks = BankKontoSQLMethods.getKonten(DatabaseConnection.open(), kunde.getKundenNr(), Status.NORMAL);

            if (bks != null) {
                hashPut("BANK_KONTONUMMER", bks[0].getKontonummer());
                hashPut("BANK_LEITZAHL", bks[0].getBankleitzahl());
                hashPut("BANK_NAME", bks[0].getBankinstitut());
                hashPut("BANK_KONTOEIGENTUEMER", bks[0].getKontoinhaber());
                hashPut("BANK_IBAN", bks[0].getIban());
                hashPut("BANK_BIC", bks[0].getBic());

                if (bks.length > 1) {
                    for (int i = 1; i <= bks.length; i++) {
                        int zahl = i + 1;
                        hashPut("BANK_KONTONUMMER" + zahl, bks[i].getKontonummer());
                        hashPut("BANK_LEITZAHL" + zahl, bks[i].getBankleitzahl());
                        hashPut("BANK_NAME" + zahl, bks[i].getBankinstitut());
                        hashPut("BANK_KONTOEIGENTUEMER" + zahl, bks[i].getKontoinhaber());
                        hashPut("BANK_IBAN" + zahl, bks[i].getIban());
                        hashPut("BANK_BIC" + zahl, bks[i].getBic());
                    }
                }

            } else {
                hashPut("BANK_KONTONUMMER", "-");
                hashPut("BANK_LEITZAHL", "-");
                hashPut("BANK_NAME", "-");
                hashPut("BANK_KONTOEIGENTUEMER", "-");
                hashPut("BANK_IBAN", "-");
                hashPut("BANK_BIC", "-");
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das Bankkonto für die Kunden nicht laden.", e);
            ShowException.showException("Datenbankfehler: Die Bankkonten für den Brief / E-Mail konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Bankkonten nicht laden");
        }

        hashPut("KOMMENTARE", kunde.getComments());
        hashPut("COMMENTS", kunde.getComments());

        hashPut("CUSTOM1", kunde.getCustom1());
        hashPut("CUSTOM2", kunde.getCustom2());
        hashPut("CUSTOM3", kunde.getCustom3());
        hashPut("CUSTOM4", kunde.getCustom4());
        hashPut("CUSTOM5", kunde.getCustom5());

        hashPut("BENUTZERDEFINIERT1", kunde.getCustom1()); // syn
        hashPut("BENUTZERDEFINIERT2", kunde.getCustom2()); // syn
        hashPut("BENUTZERDEFINIERT3", kunde.getCustom3()); // syn
        hashPut("BENUTZERDEFINIERT4", kunde.getCustom4()); // syn
        hashPut("BENUTZERDEFINIERT5", kunde.getCustom5()); // syn

        hashPut("CREATED", df2.format(kunde.getCreated()));
        hashPut("MODIFIED", df2.format(kunde.getModified()));

        hashPut("ERSTELLT", df2.format(kunde.getCreated()));// syn
        hashPut("ZULETZT_GEAENDERT", df2.format(kunde.getModified()));// syn

        hashPut("BETREUER", BenutzerRegistry.getBenutzer(kunde.getBetreuerId()));

        hashPut("STATUS", Status.getName(kunde.getStatus()));

        try {
            Kunden_BetreuungObj btr = Kunden_BetreuungSQLMethods.getKunden_Betreuung(
                    DatabaseConnection.open(), kunde.getKundenNr());

            hashPut("KUNDENTYP", btr.getKundenTyp());
            hashPut("PRIORITAET", btr.getPrioritaet());
            hashPut("LOYALITAET", btr.getLoyalitaet());
            hashPut("ZIELGRUPPE", btr.getZielgruppe());

            hashPut("KV_TYP", btr.getKvTyp());
            hashPut("KV", btr.getKrankenversicherung());
            hashPut("KRANKENVERSICHERER", btr.getKrankenversicherung()); //syn     
            hashPut("KV_NUMMER", btr.getKvNummer());

            hashPut("GKV_BEITRAG", ProcentFormat.formatPercent(btr.getGkvBeitrag()));
            hashPut("KV_BEITRAG", WaehrungFormat.getFormatedWaehrung(btr.getKvBeitrag(), 1));
            hashPut("PFLEGE_BEITRAG", WaehrungFormat.getFormatedWaehrung(btr.getPflegeBeitrag(), 1));

            hashPut("ERSTER_KONTAKT", btr.getErsterKontakt());
            hashPut("LETZTER_KONTAKT", btr.getLetzerKontakt());
            hashPut("LETZTE_ROUTINE", btr.getLetzteRoutine());

            hashPut("MAKLERVERTRAG", BooleanTools.getBooleanJaNein(btr.isMaklerVertrag()));
            if (btr.isMaklerVertrag()) {
                String mk = "Vorhanden";

                if (btr.getMaklerBeginn() != null && btr.getMaklerBeginn().length() > 0) {
                    mk = "Vorhanden vom " + btr.getMaklerBeginn();
                }

                if (btr.getMaklerEnde() != null && btr.getMaklerBeginn().length() > 0) {
                    mk = mk + " bis " + btr.getMaklerBeginn();
                }

                hashPut("MAKLERVERTRAG_INFO", mk);
            } else {
                hashPut("MAKLERVERTRAG_INFO", "Nicht vorhanden");
            }            
            
            hashPut("MAKLERVERTRAG_BEGINN", btr.getMaklerBeginn());
            hashPut("MAKLERVERTRAG_ENDE", btr.getMaklerEnde());

            hashPut("KUNDENANALYSE", BooleanTools.getBooleanJaNein(btr.isAnalyse()));
            hashPut("KUNDENANALYSE_LETZTE", btr.getAnalyseLetzte());
            hashPut("KUNDENANALYSE_NAECHSTE", btr.getAnalyseNaechste());

            hashPut("ERSTINFORMATIONEN", BooleanTools.getBooleanJaNein(btr.isErstinformationen()));

            hashPut("VERWALTUNGSKOSTEN", WaehrungFormat.getFormatedWaehrung(btr.getVerwaltungskosten(), -1));

            hashPut("GESTORBEN", BooleanTools.getBooleanJaNein(btr.isGestorben()));
            hashPut("GESTORBEN_DATUM", btr.getGestorbenDatum());

            hashPut("NEWSLETTER", BooleanTools.getBooleanJaNein(btr.isNewsletter()));
            hashPut("KUNDENZEITSCHRIFT", BooleanTools.getBooleanJaNein(btr.isKundenzeitschrift()));
            hashPut("GEBURTSTAGSKARTE", BooleanTools.getBooleanJaNein(btr.isGeburtstagskarte()));
            hashPut("WEIHNACHTSKARTE", BooleanTools.getBooleanJaNein(btr.isWeihnachtskarte()));
            hashPut("OSTERKARTE", BooleanTools.getBooleanJaNein(btr.isOsterkarte()));

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das KundenbetreuungsObj für den Kunden nicht laden.", e);
            ShowException.showException("Datenbankfehler: Die Kundenbetreuungsdaten für den Brief / E-Mail konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Kundenbetreuungsdaten nicht speichern");
        }

        return ht;
    }

    private static void hashPut(String val, Object val2) {
        if (val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if (val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }
}
