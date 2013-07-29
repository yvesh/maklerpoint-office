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
package de.maklerpoint.office.Risiken;

import de.maklerpoint.office.Konstanten.Vertraege;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class RisikoKfzObj {
    
    private int _id;
    
    private String _kundenKennung;
    
    // Halter Daten
    
    private String _eheDatum;
    private String _wohnhaftSeit;
    
    private boolean _vorsteuerAbzug = false;
    
    private double _steuernachlass = 0.00;
    
    private String _tarifGruppe;
    
    private boolean _aboOeffentlicherNahverkehr = false;
    private boolean _bausparer;
    
    private String _gebJuengstesKind;
    private String _gebAeltestesKind;
    
    
    // Führerschein
    private int _ausweisId = -1;
    
    private boolean _kl3OhneUnterbrechung = true;
    private boolean _euFuehrerschein = true;
    private boolean _sicherheitstraining = false;
    private String _unfallfreiDatum;
    
    // Mitarbeiter, Mitgliedbei
    
    private boolean _mitAutoHersteller = false;
    private boolean _mitArbeitsagentur = false;
    private boolean _mitAutomobilclub = false;
    private boolean _mitBund = false;
    private boolean _mitGenossenschaft = false;
    
    /* KFZ Daten */
    
    private String _herstellerSchluessNr;
    private String _typSchluesselNr;
    
    private int _waginskennziffer;
    private String _herstellerName;
    private String _modellName;
    
    private String _kfzIdent;
    
    private String _kfzKennzeichen;
    private String _kfzErstzulassung;
    private String _kfzErstbesitzer;
    
    private double _kfzNeuwert = 0.00;
    private boolean _kfzWegfahrsperreSerienmaessig = false;
        
    private boolean saisonKennzeichen = false;    
    private String _saisonStart;
    private String _saisonEnde;
    
    private boolean _diesel = false;
    
    private String _typklasseHP;
    private String _typklasseVK;
    private String _typklasseTK;
    private String _kw;
    private String _ps;
    
    private String _hubraumCCM;
    
    private String _kfzFarbe;
    
    private String _kaufDatum;
    private String _finanzierung;
    private String _stellplatz;
    
    private boolean _stellplatzUeberdacht = true;
    private boolean _stellplatzAbgeschlossen = true;
    private boolean _stellplatzOeffetnlich = false;
    
    private int _fahrleistungJahr;
    private String _kfzNutzung;
    private String _kfzZweck;
    private String _kfzNutzerkreis;
    
    private boolean _kfzWegfahrsperre = false;
    private boolean _kfzAbs = false;
    
    private String _gebJugensterFahrer;
    private String _gebAeltesterFahrer;
    
    private boolean _fahrtenMitFuererscheinweniger3 = false; // Fahrer der den Führerschein weniger als 3 Jahre besitzt.
    private boolean _fahrtenAusserhalbEU = false; // Fahrten ausserhalb der EU
    
    private boolean _vorversicherung = false;
    private String _vorversSeit;
    private String _vorversVersicherer;
    private String _vorversVSnummer;
    private boolean _vorversGekuendigtVU = false; // Gekündigt durch versicherungsunternehmen?
    
    // Voraussichtliche Vertragsoptionen
    
    private String _halterIst;
    private int _tachoStand = 0; // in km
    private double _zeitWert = 0.00;
    
    private int _kaskoVersicherung = 0;
    private int _sbVollkasko = 500;
    private int _sbTeilkasko = 500;
    
    private int _zahlweise = Vertraege.ZAHLUNG_HALBJAEHRLICH;
    private String khDeckung;
    
    private boolean _schutzbrief = false;
    
    // Schadenfreiheitsklasse
    
    private String _gueltigAb;
    private String _einstufungNach;
    
    private String _sfInhaber;
    private String _sfVersicherer;
    
    private String _sfHP; // Haftpflicht
    private String _sfVK; // Vollkasko
    
    private boolean _sfZweitwagen = false;
    private boolean _sfRabattretter = false;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
