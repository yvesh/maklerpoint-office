/*
 *  vsogram:    MaklerPoint System
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
import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.Kunden_BetreuungObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.Tools.Kunden_BetreuungSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayStringTools;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import org.openide.util.Exceptions;

/**
 *
 * @author yves
 */
public class FirmenHashtable {

    private static Hashtable ht;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static Hashtable generatFirmenhash(FirmenObj fa) {
        ht = new Hashtable();

        hashPut("GK_ID", fa.getId());

        hashPut("GK_PARENTID", fa.getParentFirma());
        hashPut("GK_PARENT", KundenRegistry.getFirmenKunde(fa.getParentFirma(), true));
        hashPut("GK_MUTTERKONZERN", KundenRegistry.getFirmenKunde(fa.getParentFirma(), true)); // syn
        hashPut("GK_PARENTNAME", KundenRegistry.getFirmenKunde(fa.getParentFirma(), true)); // syn

        BenutzerObj creator = BenutzerRegistry.getBenutzer(fa.getCreator());

        hashPut("GK_BETREUER", BenutzerRegistry.getBenutzer(fa.getBetreuer()));
        if(creator != null) {
            hashPut("GK_ERSTELLER", creator.toString());
            hashPut("GK_CREATOR", creator.toString());
        }

        hashPut("GK_MANDANT", MandantenRegistry.getMandant(fa.getMandantenId(), true));
        hashPut("GK_MANDANT_ID", fa.getMandantenId());

        hashPut("GK_TYP_INT", fa.getType());

        hashPut("GK_KUNDENNUMMER", fa.getKundenNr());
        hashPut("GK_KDNR", fa.getKundenNr());
        hashPut("GK_KENNUNG", fa.getKundenNr());
        hashPut("GK_KNR", fa.getKundenNr());

        hashPut("GK_NAME", fa.getFirmenName());
        hashPut("GK_FIRMENNAME", fa.getFirmenName());

        hashPut("GK_NAME_ZUSATZ", fa.getFirmenNameZusatz());
        hashPut("GK_NAME_ZUSATZ2", fa.getFirmenNameZusatz2());

        hashPut("GK_STRASSE", fa.getFirmenStrasse());
        hashPut("GK_PLZ", fa.getFirmenPLZ());
        hashPut("GK_ORT", fa.getFirmenStadt());
        hashPut("GK_STADT", fa.getFirmenStadt());
        hashPut("GK_BUNDESLAND", fa.getFirmenBundesland());
        hashPut("GK_LAND", fa.getFirmenLand());

        hashPut("GK_FIRMEN_TYP", fa.getFirmenTyp());
        hashPut("GK_SIZE", fa.getFirmenSize());
        hashPut("GK_GROESSE", fa.getFirmenSize()); //syn
        hashPut("GK_MITARBEITERZAHL", fa.getFirmenSize()); // syn
        hashPut("GK_FIRMENSITZ", fa.getFirmenSitz());

        hashPut("GK_POSTFACH_USE", BooleanTools.getBooleanJaNein(fa.isFirmenPostfach()));
        hashPut("GK_POSTFACH_VERWENDEN", BooleanTools.getBooleanJaNein(fa.isFirmenPostfach()));  // syn

        hashPut("GK_POSTFACH", fa.getFirmenPostfachName());
        hashPut("GK_POSTFACH_NAME", fa.getFirmenPostfachName()); // syn
        hashPut("GK_POSTFACH_PLZ", fa.getFirmenPostfachPlz());
        hashPut("GK_POSTFACH_ORT", fa.getFirmenStadt());

        hashPut("GK_UMSATZ", fa.getFirmenEinkommen());
        hashPut("GK_BRANCHE", fa.getFirmenBranche());
        hashPut("GK_RECHTSFORM", fa.getFirmenRechtsform());
        hashPut("GK_GRUENDUNG", df.format(fa.getFirmenGruendungDatum()));
        hashPut("GK_GRUENDUNG_DATUM", df.format(fa.getFirmenGruendungDatum())); // syn

        // ##    GK_GESCHAEFTSFUEHRER##
        hashPut("GK_GESCHAEFTSFUEHRER", fa.getFirmenGeschaeftsfuehrer());
        hashPut("GK_GESCHAEFTSLEITER", fa.getFirmenGeschaeftsfuehrer()); // syn

        hashPut("GK_PROKURA", ArrayStringTools.arrayToString(fa.getFirmenProKura(), ", "));
        hashPut("GK_VERTETER", ArrayStringTools.arrayToString(fa.getFirmenProKura(), ", "));
        hashPut("GK_STANDORTE", ArrayStringTools.arrayToString(fa.getFirmenStandorte(), ", "));

        hashPut("GK_KOMMUNIKATION1", fa.getCommunication1());
        hashPut("GK_KOMMUNIKATION2", fa.getCommunication2());
        hashPut("GK_KOMMUNIKATION3", fa.getCommunication3());
        hashPut("GK_KOMMUNIKATION4", fa.getCommunication4());
        hashPut("GK_KOMMUNIKATION5", fa.getCommunication5());
        hashPut("GK_KOMMUNIKATION6", fa.getCommunication6());

        hashPut("GK_KOM1", fa.getCommunication1()); // syn
        hashPut("GK_KOM2", fa.getCommunication2()); //..
        hashPut("GK_KOM3", fa.getCommunication3());
        hashPut("GK_KOM4", fa.getCommunication4());
        hashPut("GK_KOM5", fa.getCommunication5());
        hashPut("GK_KOM6", fa.getCommunication6()); // syn

        hashPut("GK_KOMMUNIKATION1_TYP", CommunicationTypes.getName(fa.getCommunication1Type()));
        hashPut("GK_KOMMUNIKATION2_TYP", CommunicationTypes.getName(fa.getCommunication2Type()));
        hashPut("GK_KOMMUNIKATION3_TYP", CommunicationTypes.getName(fa.getCommunication3Type()));
        hashPut("GK_KOMMUNIKATION4_TYP", CommunicationTypes.getName(fa.getCommunication4Type()));
        hashPut("GK_KOMMUNIKATION5_TYP", CommunicationTypes.getName(fa.getCommunication5Type()));
        hashPut("GK_KOMMUNIKATION6_TYP", CommunicationTypes.getName(fa.getCommunication6Type()));

        hashPut("GK_KOM1_TYP", CommunicationTypes.getName(fa.getCommunication1Type())); // syn
        hashPut("GK_KOM2_TYP", CommunicationTypes.getName(fa.getCommunication2Type())); // ..
        hashPut("GK_KOM3_TYP", CommunicationTypes.getName(fa.getCommunication3Type()));
        hashPut("GK_KOM4_TYP", CommunicationTypes.getName(fa.getCommunication4Type()));
        hashPut("GK_KOM5_TYP", CommunicationTypes.getName(fa.getCommunication5Type()));
        hashPut("GK_KOM6_TYP", CommunicationTypes.getName(fa.getCommunication6Type())); // syn

        
        hashPut("GK_EMAIL", KundenMailHelper.getGeschKundenMail(fa)); // syn
        String defaultkonto = null;

        if (fa.getDefaultAnsprechpartner() == -1) {
            defaultkonto = "Keines";
        } else {
            try {
                defaultkonto = BankKontoSQLMethods.getKonto(DatabaseConnection.open(),
                        fa.getDefaultKonto()).toString();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                defaultkonto = "";
            }
        }
        hashPut("GK_STANDARDKONTO", defaultkonto);

        String ansprech = null;

        if (fa.getDefaultAnsprechpartner() == -1) {
            ansprech = "Niemand";
        } else {
            try {
                ansprech = FirmenAnsprechpartnerSQLMethods.getAnsprechpartner(DatabaseConnection.open(),
                        fa.getDefaultAnsprechpartner()).toString();
                hashPut("GK_STANDARD_ANSPRECHPARTNER", ansprech);
                
            } catch (SQLException e) {
                Log.databaselogger.fatal("Konnte den Standard Ansprechpartner nicht laden.", e);
                ShowException.showException("Datenbankfehler: Der Standardansprechpartner konnten nicht geladen werden.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Standardansprechpartner nicht laden");
                ansprech = "";
            }
        }

        try {
            BankKontoObj[] bks = BankKontoSQLMethods.getKonten(DatabaseConnection.open(), 
                    fa.getKundenNr(), Status.NORMAL);

            if (bks != null) {
                hashPut("GK_BANK_KONTONUMMER", bks[0].getKontonummer());
                hashPut("GK_BANK_LEITZAHL", bks[0].getBankleitzahl());
                hashPut("GK_BANK_NAME", bks[0].getBankinstitut());
                hashPut("GK_BANK_KONTOEIGENTUEMER", bks[0].getKontoinhaber());
                hashPut("GK_BANK_IBAN", bks[0].getIban());
                hashPut("GK_BANK_BIC", bks[0].getBic());

                if (bks.length > 1) {
                    for (int i = 1; i <= bks.length; i++) {
                        int zahl = i + 1;
                        hashPut("GK_BANK_KONTONUMMER" + zahl, bks[i].getKontonummer());
                        hashPut("GK_BANK_LEITZAHL" + zahl, bks[i].getBankleitzahl());
                        hashPut("GK_BANK_NAME" + zahl, bks[i].getBankinstitut());
                        hashPut("GK_BANK_KONTOEIGENTUEMER" + zahl, bks[i].getKontoinhaber());
                        hashPut("GK_BANK_IBAN" + zahl, bks[i].getIban());
                        hashPut("GK_BANK_BIC" + zahl, bks[i].getBic());
                    }
                }

            } else {
                hashPut("GK_BANK_KONTONUMMER", "-");
                hashPut("GK_BANK_LEITZAHL", "-");
                hashPut("GK_BANK_NAME", "-");
                hashPut("GK_BANK_KONTOEIGENTUEMER", "-");
                hashPut("GK_BANK_IBAN", "-");
                hashPut("GK_BANK_BIC", "-");
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das Bankkonto für den Kunden nicht laden.", e);
            ShowException.showException("Datenbankfehler: Die Bankkonten für den Brief / E-Mail konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Bankkonten nicht laden");
        }

        hashPut("GK_WERBER_KUNDENNUMMER", fa.getWerber());

        if (!"unbekannt".equalsIgnoreCase(fa.getWerber()) && fa.getWerber() != null) {
//            System.out.println("Kennung: " + kunde.getWerberKennung());
            hashPut("GK_WERBER", KundenRegistry.getKunde(fa.getWerber()));
        } else {
            hashPut("GK_WERBER", fa.getWerber());
        }

        hashPut("GK_STANDARDANSPRECHPARTNER", ansprech);

        hashPut("GK_KOMMENTARE", fa.getComments());
        hashPut("GK_COMMENTS", fa.getComments());

        hashPut("GK_CUSTOM1", fa.getCustom1());
        hashPut("GK_CUSTOM2", fa.getCustom2());
        hashPut("GK_CUSTOM3", fa.getCustom3());
        hashPut("GK_CUSTOM4", fa.getCustom4());
        hashPut("GK_CUSTOM5", fa.getCustom5());

        hashPut("GK_BENUTZERDEFINIERT1", fa.getCustom1()); // syn
        hashPut("GK_BENUTZERDEFINIERT2", fa.getCustom2()); // syn
        hashPut("GK_BENUTZERDEFINIERT3", fa.getCustom3()); // syn
        hashPut("GK_BENUTZERDEFINIERT4", fa.getCustom4()); // syn
        hashPut("GK_BENUTZERDEFINIERT5", fa.getCustom5()); // syn

        hashPut("GK_CREATED", df2.format(fa.getCreated()));
        hashPut("GK_MODIFIED", df2.format(fa.getModified()));

        hashPut("GK_ERSTELLT", df2.format(fa.getCreated()));// syn
        hashPut("GK_ZULETZT_GEAENDERT", df2.format(fa.getModified()));// syn

        hashPut("GK_STATUS", Status.getName(fa.getStatus()));

        try {
            Kunden_BetreuungObj btr = Kunden_BetreuungSQLMethods.getKunden_Betreuung(
                    DatabaseConnection.open(), fa.getKundenNr());

            hashPut("GK_KUNDENTYP", btr.getKundenTyp());
            hashPut("GK_PRIORITAET", btr.getPrioritaet());
            hashPut("GK_LOYALITAET", btr.getLoyalitaet());
            hashPut("GK_ZIELGRUPPE", btr.getZielgruppe());
            
            hashPut("GK_ERSTER_KONTAKT", btr.getErsterKontakt());
            hashPut("GK_LETZTER_KONTAKT", btr.getLetzerKontakt());
            hashPut("GK_LETZTE_ROUTINE", btr.getLetzteRoutine());

            hashPut("GK_MAKLERVERTRAG", BooleanTools.getBooleanJaNein(btr.isMaklerVertrag()));
            if (btr.isMaklerVertrag()) {
                String mk = "Vorhanden";

                if (btr.getMaklerBeginn() != null && btr.getMaklerBeginn().length() > 0) {
                    mk = "Vorhanden vom " + btr.getMaklerBeginn();
                }

                if (btr.getMaklerEnde() != null && btr.getMaklerBeginn().length() > 0) {
                    mk = mk + " bis " + btr.getMaklerBeginn();
                }

                hashPut("GK_MAKLERVERTRAG_INFO", mk);
            } else {
                hashPut("GK_MAKLERVERTRAG_INFO", "Nicht vorhanden");
            }            
            
            hashPut("GK_MAKLERVERTRAG_BEGINN", btr.getMaklerBeginn());
            hashPut("GK_MAKLERVERTRAG_ENDE", btr.getMaklerEnde());

            hashPut("GK_KUNDENANALYSE", BooleanTools.getBooleanJaNein(btr.isAnalyse()));
            hashPut("GK_KUNDENANALYSE_LETZTE", btr.getAnalyseLetzte());
            hashPut("GK_KUNDENANALYSE_NAECHSTE", btr.getAnalyseNaechste());

            hashPut("GK_ERSTINFORMATIONEN", BooleanTools.getBooleanJaNein(btr.isErstinformationen()));

            hashPut("GK_VERWALTUNGSKOSTEN", WaehrungFormat.getFormatedWaehrung(btr.getVerwaltungskosten(), -1));

            hashPut("GK_AUFGELOEST", BooleanTools.getBooleanJaNein(btr.isGestorben()));
            hashPut("GK_AUFGELOEST_DATUM", btr.getGestorbenDatum());

            hashPut("GK_NEWSLETTER", BooleanTools.getBooleanJaNein(btr.isNewsletter()));
            hashPut("GK_KUNDENZEITSCHRIFT", BooleanTools.getBooleanJaNein(btr.isKundenzeitschrift()));
            hashPut("GK_GEBURTSTAGSKARTE", BooleanTools.getBooleanJaNein(btr.isGeburtstagskarte()));
            hashPut("GK_WEIHNACHTSKARTE", BooleanTools.getBooleanJaNein(btr.isWeihnachtskarte()));
            hashPut("GK_OSTERKARTE", BooleanTools.getBooleanJaNein(btr.isOsterkarte()));

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das GKundenbetreuungsObj für den Kunden nicht laden.", e);
            ShowException.showException("Datenbankfehler: Die Kundenbetreuungsdaten für den Brief / E-Mail konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Kundenbetreuungsdaten nicht speichern");
        }
        
        return ht;
    }

    private static void hashPut(String val, Object val2) {
        if (val == null) {
            return;
        }

        if (val2 == null) {
            val2 = "";
        }

        ht.put(val, val2);
    }
}
