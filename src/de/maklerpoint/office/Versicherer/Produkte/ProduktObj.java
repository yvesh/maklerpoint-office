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
package de.maklerpoint.office.Versicherer.Produkte;

import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author yves
 */
@Root(name = "produkt")
public class ProduktObj {

    @Attribute(name = "id")
    private int _id;
    @Element(name = "versichererId")
    private int _versichererId = -1;
    @Element(name = "sparteId")
    private int _sparteId = -1;
    @Element(name = "creatorId", required = false)
    private int _creatorId = -1;
    @Element(name = "art", required = false)
    private int _art = -1;
    @Element(name = "tarif")
    private String _tarif;

    private String _tarifBasis;
    @Element(name = "bezeichnung")
    private String _bezeichnung;
    @Element(name = "kuerzel", required = false)
    private String _kuerzel;
    @Element(name = "vertragsmaske", required = false)
    private int _vertragsmaske = -1;
    @Element(name = "boolVermittelbar", required = false)
    private boolean _vermittelbar; // TODO: sync mit Versicherer vermittelbar
    @Element(name = "versicherungsart", required = false)
    private int _versicherungsart; // maybe syn zu art
    @Element(name = "risikotyp", required = false)
    private int _risikotyp;
    @Element(name = "waehrung", required = false)
    private int _waehrung = 1; // Default Euro Fallback
    @Element(name = "versicherungsSumme", required = false)
    private double _versicherungsSumme = 0.00;
    @Element(name = "bewertungsSumme", required = false)
    private double _bewertungsSumme = 0.00;
    @Element(name = "bedingungen", required = false)
    private String _bedingungen;
    @Element(name = "selbstbeteiligung", required = false)
    private double _selbstbeteiligung = 0.00;
    @Element(name = "nettopraemiePauschal", required = false)
    private double _nettopraemiePauschal = 0.00;
    @Element(name = "nettopraemieZusatz", required = false)
    private double _nettopraemieZusatz = 0.00;
    @Element(name = "nettopraemieGesamt", required = false)
    private double _nettopraemieGesamt = 0.00;
    private int[] _zusatzEinschluesse;
    @Element(name = "zusatzInfo", required = false)
    private String _zusatzInfo;
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
    private int status = Status.NORMAL;

    public ProduktObj() {
    }

    public double getBewertungsSumme() {
        return _bewertungsSumme;
    }

    public void setBewertungsSumme(double _bewertungsSumme) {
        this._bewertungsSumme = _bewertungsSumme;
    }

    public double getNettopraemieGesamt() {
        return _nettopraemieGesamt;
    }

    public void setNettopraemieGesamt(double _nettopraemieGesamt) {
        this._nettopraemieGesamt = _nettopraemieGesamt;
    }

    public double getNettopraemiePauschal() {
        return _nettopraemiePauschal;
    }

    public void setNettopraemiePauschal(double _nettopraemiePauschal) {
        this._nettopraemiePauschal = _nettopraemiePauschal;
    }

    public double getNettopraemieZusatz() {
        return _nettopraemieZusatz;
    }

    public int getWaehrung() {
        return _waehrung;
    }

    public void setWaehrung(int _waehrung) {
        this._waehrung = _waehrung;
    }

    public void setNettopraemieZusatz(double _nettopraemieZusatz) {
        this._nettopraemieZusatz = _nettopraemieZusatz;
    }

    public double getSelbstbeteiligung() {
        return _selbstbeteiligung;
    }

    public void setSelbstbeteiligung(double _selbstbeteiligung) {
        this._selbstbeteiligung = _selbstbeteiligung;
    }

    public double getVersicherungsSumme() {
        return _versicherungsSumme;
    }

    public void setVersicherungsSumme(double _versicherungsSumme) {
        this._versicherungsSumme = _versicherungsSumme;
    }

    public int getArt() {
        return _art;
    }

    public void setArt(int _art) {
        this._art = _art;
    }

    public String getBedingungen() {
        return _bedingungen;
    }

    public void setBedingungen(String _bedingungen) {
        this._bedingungen = _bedingungen;
    }

    public String getBezeichnung() {
        return _bezeichnung;
    }

    public void setBezeichnung(String _bezeichnung) {
        this._bezeichnung = _bezeichnung;
    }

    public String getComments() {
        return _comments;
    }

    public void setComments(String _comments) {
        this._comments = _comments;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public int getCreatorId() {
        return _creatorId;
    }

    public void setCreatorId(int _creatorId) {
        this._creatorId = _creatorId;
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

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKuerzel() {
        return _kuerzel;
    }

    public void setKuerzel(String _kuerzel) {
        this._kuerzel = _kuerzel;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getRisikotyp() {
        return _risikotyp;
    }

    public void setRisikotyp(int _risikotyp) {
        this._risikotyp = _risikotyp;
    }

    public int getSparteId() {
        return _sparteId;
    }

    public void setSparteId(int _sparteId) {
        this._sparteId = _sparteId;
    }

    public String getTarif() {
        return _tarif;
    }

    public void setTarif(String _tarif) {
        this._tarif = _tarif;
    }

    public String getTarifBasis() {
        return _tarifBasis;
    }

    public void setTarifBasis(String _tarifBasis) {
        this._tarifBasis = _tarifBasis;
    }

    public boolean isVermittelbar() {
        return _vermittelbar;
    }

    public void setVermittelbar(boolean _vermittelbar) {
        this._vermittelbar = _vermittelbar;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public int getVersicherungsart() {
        return _versicherungsart;
    }

    public void setVersicherungsart(int _versicherungsart) {
        this._versicherungsart = _versicherungsart;
    }

    public int getVertragsmaske() {
        return _vertragsmaske;
    }

    public void setVertragsmaske(int _vertragsmaske) {
        this._vertragsmaske = _vertragsmaske;
    }

    public int[] getZusatzEinschluesse() {
        return _zusatzEinschluesse;
    }

    public void setZusatzEinschluesse(int[] _zusatzEinschluesse) {
        this._zusatzEinschluesse = _zusatzEinschluesse;
    }

    public String getZusatzInfo() {
        return _zusatzInfo;
    }

    public void setZusatzInfo(String _zusatzInfo) {
        this._zusatzInfo = _zusatzInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (this._versichererId == -1) {
            return this._bezeichnung;
        }

        int sett = Config.getInt("produktToString", 0);

        VersichererObj vers = VersicherungsRegistry.getVersicher(this._versichererId);

        if (sett == 0) {
            return this._bezeichnung; 
        } else if (sett == 1) {                       
            return this._bezeichnung + " (" + vers.getName() + ")";        
        } else {
            return this._bezeichnung;
        }
    }
}
