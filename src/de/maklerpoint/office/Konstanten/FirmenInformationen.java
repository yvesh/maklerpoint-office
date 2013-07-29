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

package de.maklerpoint.office.Konstanten;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class FirmenInformationen {

    public static final String[] FIRMEN_RECHTSFORMEN_SHORT = new String[] {
        "e. K.", "e. Kfm.", "e. Kfr.", "Einzelunternehmen", "OHG", "GbR", "EWIV", "KG", "GmbH", "UG (haftungsbeschränkt)",
        "AG", "SE", "VVaG", "eG", "SCE", "eGmbH", "eGmuH", "KGaA", "GmbH & Co. KG",
        "AG & Co. Kg", "GmbH & Co. KGaA", "AG & Co. KGaA", "Ltd.", "e.V.", "KdöR", "AdöR"
    };

    public static final String[] FIRMEN_RECHTSFORMEN = new String[] {
        "Eingetragener Kaufmann", "Eingetragener Kaufmann", "Eingetragener Kauffrau", "Einzelunternehmen",
        "Offene Handelsgesellschaft", "Gesellschaft bürgerlichen Rechts", "Europäische wirtschaftliche Interessenvereinigung",
        "Kommanditgesellschaft", "Gesellschaft mit beschränkter Haftung", "Unternehmergesellschaft (haftungsbeschränkt)",
        "Aktiengesellschaft", "Europäische Gesellschaft (Societas Europaea)", "Versicherungsverein auf Gegenseitigkeit",
        "eingetragene Genossenschaft", "Europäische Genossenschaft (Societas Cooperativa Europaea)",
        "eingetragene Genossenschaft mit beschränkter Haftung (veraltet)", "eingetragene Genossenschaft mit unbeschränkter Haftung (veraltet)",
        "Kommanditgesellschaft auf Aktien", "Kommanditgesellschaft mit einer Gesellschaft mit beschränkter Haftung als Komplementärin",
        "Kommanditgesellschaft mit einer Aktiengesellschaft als Komplementärin",
        "Kommanditgesellschaft auf Aktien mit einer Gesellschaft mit beschränkter Haftung als Komplementärin",
        "Kommanditgesellschaft auf Aktien mit einer Aktiengesellschaft als Komplementärin", "Englische Limited",
        "Eintegtragener Verein", "Körperschaft des öffentlichen Rechts", "Anstalt des öffentlichen Rechts"
    };

    public static final String[] FIRMEN_FILIAL_TYPEN = new String[] {
        "Hauptsitz", "Selbständige Zweigniederlassung", "Unselbständige Zweigniederlassung", "Tochterunternehmen"
    };

    public static final String[] FIRMEN_BRANCHEN = new String[] { "Unbekannt", "Sonstige",
        "Abfallwirtschaft", "Altenpflege", "Apotheken", "Anwälte", "Arztpraxis", "Banken",
        "Baugewerbe", "Bildung", "Bundeswehr",  "Chemische Industrie", "Detektei", "Diakonie",
        "Dienstleistungen - sonstige", "Druckgewerbe", "Einzelhandel",  "Energiewirtschaft", "Forschung",
        "Gaststättengewerbe", "Gesundheitswesen", "Großhandel", "Hotelgewerbe", "IT", "Journalismus", "Kultur",
         "Künstler", "Logistik",  "Öffentlicher Dienst", "Reise", "Schule", "Selbständige", "Versicherung", "Verlag", "Verwaltung",
         "Wissenschaft"
    };

    public static final String[] FIRMEN_ABTEILUNGEN = new String[] { "Unbekannt",
        "Geschäftsleitung", "Kundenbetreuung", "Verwaltung", "Sekretariat", "Schadensabteilung",
        "Hotline / Support", "Vorstand", "Marketing", "Rechtsabeteilung"
    };

    public static final String[] FIRMEN_FUNKTIONEN = new String[] { "Unbekannt",
        "Abteilungsleiter/in", "Geschäftsführer/in", "Prokurist/in", "Sachbearbeiter/in",
        "Projektleiter/in", "Sekretär/in", "Vorstand"
    };
}
