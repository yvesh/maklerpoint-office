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
package de.maklerpoint.office.Vertraege;

import de.maklerpoint.office.Konstanten.Vertraege;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
@Root(name = "vertrag")
public class VertragObj {

    @Attribute(name = "id")
    private int _id = -1;
    @Element(name = "parentId", required = false)
    private int _parentId = -1; // -1 = kein Untervertrag / Ergänzung
    @Element(name = "versichererId")
    private int _versichererId; // Not null
    @Element(name = "produktId")
    private int _produktId; // Not null
    @Element(name = "kundenNr")
    private String _kundenKennung;
    @Element(name = "bankkontoId", required = false)
    private int _bankkontoId = -1;
    @Element(name = "zusatzadresseId", required = false)
    private int _zusatzadresseId = -1;
    @Element(name = "beratungsprotokollId", required = false)
    private int _beratungsprotokollId = -1; // -1 = kein BP
    @Element(name = "mandantenId", required = false)
    private int _mandantenId = -1; // für später
    @Element(name = "benutzerId", required = false)
    private int _benutzerId = -1;
    @Element(name = "vertragsTyp", required = false)
    private int _vertragsTyp = Vertraege.TYP_NORMAL; // Folgevertrag etc.
    @Element(name = "vertragsGrpId", required = false)
    private int _vertragGrp = -1; //Für mehrere mit Vor- und Folgeverträgen tbl vertraege_grp, sonst -1
    // Police
    @Element(name = "policenNr", required = false)
    private String _policennr;
    @Element(name = "policeDatum", required = false)
    private java.sql.Timestamp _policeDatum;
    @Element(name = "wertungDatum", required = false)
    private java.sql.Timestamp _wertungDatum;
    @Element(name = "boolCourtage", required = false)
    private boolean _courtage = true; // berechnen
    @Element(name = "zahlweise", required = false)
    private int _zahlWeise = Vertraege.ZAHLUNG_JAEHRLICH;
    @Element(name = "zahlart", required = false)
    private String _zahlArt;
    private double _versicherungsSumme = 0.00;
    @Element(name = "selbstbeteiligung", required = false)
    private int _selbstbeteiligung = 0;
    @Element(name = "jahresNetto", required = false)
    private double _jahresNetto = 0.00;
    @Element(name = "steuer", required = false)
    private double _steuer = 0.00;
    @Element(name = "gebuehr", required = false)
    private double _gebuehr = 0.00;
    @Element(name = "jahresBrutto", required = false)
    private double _jahresBrutto = 0.00;
    @Element(name = "rabatt", required = false)
    private double _rabatt = 0.00; // in Prozent
    @Element(name = "zuschlag", required = false)
    private double _zuschlag = 0.00; // in Prozent
    @Element(name = "waehrungId", required = false)
    private int _waehrungId = 1; // DEFAULT EURO
    // laufzeit    
    @Element(name = "antragDatum", required = false)
    private java.sql.Timestamp _antrag;
    @Element(name = "faelligDatum", required = false)
    private java.sql.Timestamp _faellig;
    @Element(name = "hauptfealligDatum", required = false)
    private java.sql.Timestamp _hauptfaellig; // Nur tag + monat    
    @Element(name = "beginnDatum", required = false)
    private java.sql.Timestamp _beginn;
    @Element(name = "ablaufDatum", required = false)
    private java.sql.Timestamp _ablauf;
    // Makler    
    @Element(name = "eingangDatum", required = false)
    private java.sql.Timestamp _maklerEingang;
    // Storno
    @Element(name = "stornoDatum", required = false)
    private java.sql.Timestamp _stornoDatum;
    @Element(name = "stornoEingangDatum", required = false)
    private java.sql.Timestamp _storno;
    @Element(name = "stornoGrund", required = false)
    private String stornoGrund;
    @Element(name = "laufzeitJahre", required = false)
    private int _laufzeit = 0; // Jahre
    @Element(name = "courtageDatum", required = false)
    private java.sql.Timestamp _courtage_datum;
    @Element(name = "comments", required = false)
    private String _comments;
    @Element(name = "custom1", required = false)
    private String _custom1;
    @Element(name = "custom2", required = false)
    private String _custom2;
    @Element(name = "custom3", required = false)
    private String _custom3;
    @Element(name = "custom4", required = false)
    private String _custom4;
    @Element(name = "custom5", required = false)
    private String _custom5;
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;
    @Element(name = "status", required = false)
    private int _status = Vertraege.STATUS_AKTIV; // VORSICHT ANDERER STATUS!!

    public int getBankkontoId() {
        return _bankkontoId;
    }

    public void setBankkontoId(int _bankkontoId) {
        this._bankkontoId = _bankkontoId;
    }

    public int getWaehrungId() {
        return _waehrungId;
    }

    public void setWaehrungId(int _waehrungId) {
        this._waehrungId = _waehrungId;
    }

    public int getZusatzadresseId() {
        return _zusatzadresseId;
    }

    public void setZusatzadresseId(int _zusatzadresseId) {
        this._zusatzadresseId = _zusatzadresseId;
    }

    public Timestamp getAblauf() {
        return _ablauf;
    }

    public void setAblauf(Timestamp _ablauf) {
        this._ablauf = _ablauf;
    }

    public Timestamp getAntrag() {
        return _antrag;
    }

    public void setAntrag(Timestamp _antrag) {
        this._antrag = _antrag;
    }

    public Timestamp getBeginn() {
        return _beginn;
    }

    public void setBeginn(Timestamp _beginn) {
        this._beginn = _beginn;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public int getBeratungsprotokollId() {
        return _beratungsprotokollId;
    }

    public void setBeratungsprotokollId(int _beratungsprotokollId) {
        this._beratungsprotokollId = _beratungsprotokollId;
    }

    public String getComments() {
        return _comments;
    }

    public void setComments(String _comments) {
        this._comments = _comments;
    }

    public boolean isCourtage() {
        return _courtage;
    }

    public void setCourtage(boolean _courtage) {
        this._courtage = _courtage;
    }

    public Timestamp getCourtage_datum() {
        return _courtage_datum;
    }

    public void setCourtage_datum(Timestamp _courtage_datum) {
        this._courtage_datum = _courtage_datum;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getCustom1() {
        return _custom1;
    }

    public void setCustom1(String _custom1) {
        this._custom1 = _custom1;
    }

    public String getCustom2() {
        return _custom2;
    }

    public void setCustom2(String _custom2) {
        this._custom2 = _custom2;
    }

    public String getCustom3() {
        return _custom3;
    }

    public void setCustom3(String _custom3) {
        this._custom3 = _custom3;
    }

    public String getCustom4() {
        return _custom4;
    }

    public void setCustom4(String _custom4) {
        this._custom4 = _custom4;
    }

    public String getCustom5() {
        return _custom5;
    }

    public void setCustom5(String _custom5) {
        this._custom5 = _custom5;
    }

    public Timestamp getFaellig() {
        return _faellig;
    }

    public void setFaellig(Timestamp _faellig) {
        this._faellig = _faellig;
    }

    public double getGebuehr() {
        return _gebuehr;
    }

    public void setGebuehr(double _gebuehr) {
        this._gebuehr = _gebuehr;
    }

    public Timestamp getHauptfaellig() {
        return _hauptfaellig;
    }

    public void setHauptfaellig(Timestamp _hauptfaellig) {
        this._hauptfaellig = _hauptfaellig;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public double getVersicherungsSumme() {
        return _versicherungsSumme;
    }

    public void setVersicherungsSumme(double _versicherungsSumme) {
        this._versicherungsSumme = _versicherungsSumme;
    }

    public double getJahresBrutto() {
        return _jahresBrutto;
    }

    public void setJahresBrutto(double _jahresBrutto) {
        this._jahresBrutto = _jahresBrutto;
    }

    public double getJahresNetto() {
        return _jahresNetto;
    }

    public void setJahresNetto(double _jahresNetto) {
        this._jahresNetto = _jahresNetto;
    }

    public String getKundenKennung() {
        return _kundenKennung;
    }

    public void setKundenKennung(String _kundenKennung) {
        this._kundenKennung = _kundenKennung;
    }

    public int getLaufzeit() {
        return _laufzeit;
    }

    public void setLaufzeit(int _laufzeit) {
        this._laufzeit = _laufzeit;
    }

    public Timestamp getMaklerEingang() {
        return _maklerEingang;
    }

    public void setMaklerEingang(Timestamp _maklerEingang) {
        this._maklerEingang = _maklerEingang;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public Timestamp getPoliceDatum() {
        return _policeDatum;
    }

    public void setPoliceDatum(Timestamp _policeDatum) {
        this._policeDatum = _policeDatum;
    }

    public String getPolicennr() {
        return _policennr;
    }

    public void setPolicennr(String _policennr) {
        this._policennr = _policennr;
    }

    public int getProduktId() {
        return _produktId;
    }

    public void setProduktId(int _produktId) {
        this._produktId = _produktId;
    }

    public double getRabatt() {
        return _rabatt;
    }

    public void setRabatt(double _rabatt) {
        this._rabatt = _rabatt;
    }

    public int getSelbstbeteiligung() {
        return _selbstbeteiligung;
    }

    public void setSelbstbeteiligung(int _selbstbeteiligung) {
        this._selbstbeteiligung = _selbstbeteiligung;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public double getSteuer() {
        return _steuer;
    }

    public void setSteuer(double _steuer) {
        this._steuer = _steuer;
    }

    public Timestamp getStorno() {
        return _storno;
    }

    public void setStorno(Timestamp _storno) {
        this._storno = _storno;
    }

    public Timestamp getStornoDatum() {
        return _stornoDatum;
    }

    public void setStornoDatum(Timestamp _stornoDatum) {
        this._stornoDatum = _stornoDatum;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public int getVertragGrp() {
        return _vertragGrp;
    }

    public void setVertragGrp(int _vertragGrp) {
        this._vertragGrp = _vertragGrp;
    }

    public int getVertragsTyp() {
        return _vertragsTyp;
    }

    public void setVertragsTyp(int _vertragsTyp) {
        this._vertragsTyp = _vertragsTyp;
    }

    public Timestamp getWertungDatum() {
        return _wertungDatum;
    }

    public void setWertungDatum(Timestamp _wertungDatum) {
        this._wertungDatum = _wertungDatum;
    }

    public String getZahlArt() {
        return _zahlArt;
    }

    public void setZahlArt(String _zahlArt) {
        this._zahlArt = _zahlArt;
    }

    public int getZahlWeise() {
        return _zahlWeise;
    }

    public void setZahlWeise(int _zahlWeise) {
        this._zahlWeise = _zahlWeise;
    }

    public double getZuschlag() {
        return _zuschlag;
    }

    public void setZuschlag(double _zuschlag) {
        this._zuschlag = _zuschlag;
    }

    public String getStornoGrund() {
        return stornoGrund;
    }

    public void setStornoGrund(String stornoGrund) {
        this.stornoGrund = stornoGrund;
    }

    @Override
    public String toString() {
        if (this._produktId == -1) {
            return this._id + "_" + this._kundenKennung;
        }

        ProduktObj prod = VersicherungsRegistry.getProdukt(this._produktId);

        if (prod == null) {
            try  {
                // TODO Ausklammern.. soll geworfen werden
//                throw new IllegalStateException("Kein Produkt vorhanden"); 
            } catch (Exception e) {
                 Log.logger.fatal("Das Produkt zum Vertrag existiert nicht.", e);
            }
           
            prod = new ProduktObj();
        }
        if (this._policennr == null) {
            return prod.getBezeichnung() + " Vertrag (" + this._kundenKennung + ")";
        }

        int sett = Config.getInt("vertragToString", 0);

        if (sett == 0) {

            if (prod.getBezeichnung() != null) {
                return this._policennr + " - " + prod.getBezeichnung() + " (" + this.getKundenKennung() + ")";
            } else {
                return this._policennr + " (" + this.getKundenKennung() + ")";
            }
        } else if (sett == 1) {
            return this._policennr + " [" + prod.getBezeichnung() + "]";
        } else if (sett == 2) {
            return this._policennr + " (" + this.getKundenKennung() + ")";
        } else if (sett == 3) {
            return this._policennr;
        } else {
            if (prod.getBezeichnung() != null && prod.getBezeichnung().length() > 0) {
                return this._policennr + " - " + prod.getBezeichnung() + " (" + this.getKundenKennung() + ")";
            } else {
                return this._policennr + " (" + this.getKundenKennung() + ")";
            }
        }
    }
}
