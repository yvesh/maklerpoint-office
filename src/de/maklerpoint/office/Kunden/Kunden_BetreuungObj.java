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
package de.maklerpoint.office.Kunden;

import de.maklerpoint.office.Konstanten.WeiteresKunden;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author yves
 */
public class Kunden_BetreuungObj {

    private int _id;
    private String _kundenKennung = "-1";
    private String _kundenTyp = "Interessent";
    private int _prioritaet = 0;
    private String _loyalitaet = "Normal";
    private String _zielgruppe = "Unbekannt";
    private String _ersterKontakt = null;
    private String _letzerKontakt = null;
    private String _letzteRoutine = null;
    private boolean _maklerVertrag = false;
    private String _maklerBeginn = null;
    private String _maklerEnde= null;
    private boolean _analyse = false;
    private String _analyseLetzte= null;
    private String _analyseNaechste= null;
    private boolean _erstinformationen = false;
    private int _verwaltungskosten = 0; // TODO Change to double
    private boolean _newsletter = false;
    private boolean _kundenzeitschrift = false;
    private boolean _geburtstagskarte = false;
    private boolean _weihnachtskarte = false;
    private boolean _osterkarte = false;
    // Neu 10 Jun. 2011
    private String _dtrentenversicherungNr = null;
    private double _gkvBeitrag = 0.00; // Prozent
    private double _kvBeitrag = 0.00; // Euro
    private double _pflegeBeitrag = 0.00; // Euro
    private String _krankenversicherung;
    private int _kvTyp = WeiteresKunden.KV_UNBEKANNT;
    private String _kvNummer;
    private boolean _gestorben = false;
    private String _gestorbenDatum;
    // Ende neu
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    private int status = Status.NORMAL;

    public Kunden_BetreuungObj() {
    }

    public boolean isAnalyse() {
        return _analyse;
    }

    public void setAnalyse(boolean _analyse) {
        this._analyse = _analyse;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public boolean isErstinformationen() {
        return _erstinformationen;
    }

    public void setErstinformationen(boolean _erstinformationen) {
        this._erstinformationen = _erstinformationen;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public boolean isGeburtstagskarte() {
        return _geburtstagskarte;
    }

    public void setGeburtstagskarte(boolean _geburtstagskarte) {
        this._geburtstagskarte = _geburtstagskarte;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKundenKennung() {
        return _kundenKennung;
    }

    public void setKundenKennung(String _kundenKennung) {
        this._kundenKennung = _kundenKennung;
    }

    public String getKundenTyp() {
        return _kundenTyp;
    }

    public void setKundenTyp(String _kundenTyp) {
        this._kundenTyp = _kundenTyp;
    }

    public boolean isKundenzeitschrift() {
        return _kundenzeitschrift;
    }

    public void setKundenzeitschrift(boolean _kundenzeitschrift) {
        this._kundenzeitschrift = _kundenzeitschrift;
    }

    public String getLoyalitaet() {
        return _loyalitaet;
    }

    public void setLoyalitaet(String _loyalitaet) {
        this._loyalitaet = _loyalitaet;
    }

    public boolean isMaklerVertrag() {
        return _maklerVertrag;
    }

    public void setMaklerVertrag(boolean _maklerVertrag) {
        this._maklerVertrag = _maklerVertrag;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public boolean isNewsletter() {
        return _newsletter;
    }

    public void setNewsletter(boolean _newsletter) {
        this._newsletter = _newsletter;
    }

    public boolean isOsterkarte() {
        return _osterkarte;
    }

    public void setOsterkarte(boolean _osterkarte) {
        this._osterkarte = _osterkarte;
    }

    public int getPrioritaet() {
        return _prioritaet;
    }

    public void setPrioritaet(int _prioritaet) {
        this._prioritaet = _prioritaet;
    }

    public int getVerwaltungskosten() {
        return _verwaltungskosten;
    }

    public void setVerwaltungskosten(int _verwaltungskosten) {
        this._verwaltungskosten = _verwaltungskosten;
    }

    public boolean isWeihnachtskarte() {
        return _weihnachtskarte;
    }

    public void setWeihnachtskarte(boolean _weihnachtskarte) {
        this._weihnachtskarte = _weihnachtskarte;
    }

    public String getZielgruppe() {
        return _zielgruppe;
    }

    public void setZielgruppe(String _zielgruppe) {
        this._zielgruppe = _zielgruppe;
    }

    public String getAnalyseLetzte() {
        return _analyseLetzte;
    }

    public void setAnalyseLetzte(String _analyseLetzte) {
        this._analyseLetzte = _analyseLetzte;
    }

    public String getAnalyseNaechste() {
        return _analyseNaechste;
    }

    public void setAnalyseNaechste(String _analyseNaechste) {
        this._analyseNaechste = _analyseNaechste;
    }

    public String getErsterKontakt() {
        return _ersterKontakt;
    }

    public void setErsterKontakt(String _ersterKontakt) {
        this._ersterKontakt = _ersterKontakt;
    }

    public String getLetzerKontakt() {
        return _letzerKontakt;
    }

    public void setLetzerKontakt(String _letzerKontakt) {
        this._letzerKontakt = _letzerKontakt;
    }

    public String getLetzteRoutine() {
        return _letzteRoutine;
    }

    public void setLetzteRoutine(String _letzteRoutine) {
        this._letzteRoutine = _letzteRoutine;
    }

    public String getMaklerBeginn() {
        return _maklerBeginn;
    }

    public void setMaklerBeginn(String _maklerBeginn) {
        this._maklerBeginn = _maklerBeginn;
    }

    public String getMaklerEnde() {
        return _maklerEnde;
    }

    public void setMaklerEnde(String _maklerEnde) {
        this._maklerEnde = _maklerEnde;
    }

    public String getDtrentenversicherungNr() {
        return _dtrentenversicherungNr;
    }

    public void setDtrentenversicherungNr(String _dtrentenversicherungNr) {
        this._dtrentenversicherungNr = _dtrentenversicherungNr;
    }

    public boolean isGestorben() {
        return _gestorben;
    }

    public void setGestorben(boolean _gestorben) {
        this._gestorben = _gestorben;
    }

    public String getGestorbenDatum() {
        return _gestorbenDatum;
    }

    public void setGestorbenDatum(String _gestorbenDatum) {
        this._gestorbenDatum = _gestorbenDatum;
    }

    public double getGkvBeitrag() {
        return _gkvBeitrag;
    }

    public void setGkvBeitrag(double _gkvBeitrag) {
        this._gkvBeitrag = _gkvBeitrag;
    }

    public String getKrankenversicherung() {
        return _krankenversicherung;
    }

    public void setKrankenversicherung(String _krankenversicherung) {
        this._krankenversicherung = _krankenversicherung;
    }

    public double getKvBeitrag() {
        return _kvBeitrag;
    }

    public void setKvBeitrag(double _kvBeitrag) {
        this._kvBeitrag = _kvBeitrag;
    }

    public String getKvNummer() {
        return _kvNummer;
    }

    public void setKvNummer(String _kvNummer) {
        this._kvNummer = _kvNummer;
    }

    public int getKvTyp() {
        return _kvTyp;
    }

    public void setKvTyp(int _kvTyp) {
        this._kvTyp = _kvTyp;
    }

    public double getPflegeBeitrag() {
        return _pflegeBeitrag;
    }

    public void setPflegeBeitrag(double _pflegeBeitrag) {
        this._pflegeBeitrag = _pflegeBeitrag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
