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

package de.maklerpoint.office.Mandanten;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MandantenObj {

    private int _id;
    private int _parentId;
    private int _creatorId;

    private String _firmenName;
    private String _firmenZusatz;
    private String _firmenZusatz2;

    private String _vermittlungNamen;    

    private String[] _vertretungsBerechtigtePosition;
    private String[] _vertretungsBerechtigteVorname;
    private String[] _vertretungsBerechtigteNachname;
    private String[] _vertretungsBerechtigteIHKErlaubnis; // == BOOL,BOOL

    private String _firmenTyp; // Kleinunternehmen, Groess
    private String _firmenRechtsform;

    private String _postfach;
    private String _postfachPlz;
    private String _postfachOrt;

    private String _filialTyp; // Selbständige, unselbständige, einzige
    private String _filialMitarbeiterZahl;
   
    private String _geschaeftsleiter;
    private String[] _gesellschafter;
    
    private String _steuerNummer;
    private String _ustNummer;
    
    private String _vermoegensHaftpflicht;

    private boolean _beteiligungenVU = false; // Beteiligungen an Versicherungsunternehmen
    private boolean _beteiligungenMAK = false; // Beteiligungen am Makler

    private String[] _verbandsMitgliedschaften;

    private String _beraterTyp; // Versicherungsberater, Versicherungsmakler, Mehrfirmenvertretter, Versicherungsvertretter für ein VU
    private String _ihkName;
    private String _ihkRegistriernummer;
    private String _ihkStatus;
    private String _ihkAbweichungen = "";

    private String[] _versicherListe;

    private boolean _34c = false;
    private boolean _34d = false;

    private String _briefkopf;

    private boolean _gewerbeamtShow = false;
    private String _gewerbeamtName;
    private String _gewerbeamtPLZ;
    private String _gewerbeamtOrt;
    private String _gewerbeamtStrasse;

    private boolean _handelsregisterShow = false;
    private String _handelsregisterName;
    private String _handelsregisterStrasse;
    private String _handelsregisterPLZ;
    private String _handelsregisterOrt;
    private String _handelsregisterRegistrierNummer;
    private String _logo;

    private String[] _beschwerdeStellen;

    // Adress Daten
    private String _adressZusatz;
    private String _adressZusatz2;

    private String _strasse;
    private String _plz;
    private String _ort;
    private String _bundesland;
    private String _land;

    private String _bankName;
    private String _bankKonto;
    private String _bankEigentuemer;
    private String _bankLeitzahl;

    private String _bankIBAN;
    private String _bankBIC;

    // Kontaktdaten
    private String _telefon;
    private String _telefon2;
    private String _telefon3;

    private String _mobil;
    private String _mobil2;

    private String _fax;
    private String _fax2;

    private String _email;
    private String _email2;

    private String _secureMail;

    private String _emailSignatur;

    private String _homepage;
    private String _homepage2;

    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _custom4;
    private String _custom5;
    private String _custom6;
    private String _custom7;
    private String _custom8;
    private String _custom9;
    private String _custom10;

    private String _comments;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    private java.sql.Timestamp _lastUsed;

    private int status = Status.NORMAL;

    public MandantenObj() {
    }

    public MandantenObj(String _firmenName, String _strasse, String _plz, String _ort) {
        this._firmenName = _firmenName;
        this._strasse = _strasse;
        this._plz = _plz;
        this._ort = _ort;
    }

    @Override
    public String toString() {
        return _firmenName;
    }

    public String getEmailSignatur() {
        return _emailSignatur;
    }

    public void setEmailSignatur(String _emailSignatur) {
        this._emailSignatur = _emailSignatur;
    }

    public boolean is34c() {
        return _34c;
    }

    public void set34c(boolean _34c) {
        this._34c = _34c;
    }

    public boolean is34d() {
        return _34d;
    }

    public void set34d(boolean _34d) {
        this._34d = _34d;
    }

    public String getAdressZusatz() {
        return _adressZusatz;
    }

    public String getBriefkopf() {
        return _briefkopf;
    }

    public void setBriefkopf(String _briefkopf) {
        this._briefkopf = _briefkopf;
    }

    public void setAdressZusatz(String _adressZusatz) {
        this._adressZusatz = _adressZusatz;
    }

    public String getAdressZusatz2() {
        return _adressZusatz2;
    }

    public void setAdressZusatz2(String _adressZusatz2) {
        this._adressZusatz2 = _adressZusatz2;
    }

    public String getBankBIC() {
        return _bankBIC;
    }

    public void setBankBIC(String _bankBIC) {
        this._bankBIC = _bankBIC;
    }

    public String getBankEigentuemer() {
        return _bankEigentuemer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBankEigentuemer(String _bankEigentuemer) {
        this._bankEigentuemer = _bankEigentuemer;
    }

    public String getBankIBAN() {
        return _bankIBAN;
    }

    public void setBankIBAN(String _bankIBAN) {
        this._bankIBAN = _bankIBAN;
    }

    public String getBankKonto() {
        return _bankKonto;
    }

    public void setBankKonto(String _bankKonto) {
        this._bankKonto = _bankKonto;
    }

    public String getBankLeitzahl() {
        return _bankLeitzahl;
    }

    public void setBankLeitzahl(String _bankLeitzahl) {
        this._bankLeitzahl = _bankLeitzahl;
    }

    public String getBankName() {
        return _bankName;
    }

    public void setBankName(String _bankName) {
        this._bankName = _bankName;
    }

    public String getBeraterTyp() {
        return _beraterTyp;
    }

    public void setBeraterTyp(String _beraterTyp) {
        this._beraterTyp = _beraterTyp;
    }

    public String[] getBeschwerdeStellen() {
        return _beschwerdeStellen;
    }

    public void setBeschwerdeStellen(String[] _beschwerdeStellen) {
        this._beschwerdeStellen = _beschwerdeStellen;
    }

    public boolean isBeteiligungenMAK() {
        return _beteiligungenMAK;
    }

    public void setBeteiligungenMAK(boolean _beteiligungenMAK) {
        this._beteiligungenMAK = _beteiligungenMAK;
    }

    public boolean isBeteiligungenVU() {
        return _beteiligungenVU;
    }

    public void setBeteiligungenVU(boolean _beteiligungenVU) {
        this._beteiligungenVU = _beteiligungenVU;
    }

    public String getBundesland() {
        return _bundesland;
    }

    public void setBundesland(String _bundesland) {
        this._bundesland = _bundesland;
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

    public String getCustom10() {
        return _custom10;
    }

    public void setCustom10(String _custom10) {
        this._custom10 = _custom10;
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

    public String getCustom6() {
        return _custom6;
    }

    public void setCustom6(String _custom6) {
        this._custom6 = _custom6;
    }

    public String getCustom7() {
        return _custom7;
    }

    public void setCustom7(String _custom7) {
        this._custom7 = _custom7;
    }

    public String getCustom8() {
        return _custom8;
    }

    public void setCustom8(String _custom8) {
        this._custom8 = _custom8;
    }

    public String getCustom9() {
        return _custom9;
    }

    public void setCustom9(String _custom9) {
        this._custom9 = _custom9;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getEmail2() {
        return _email2;
    }

    public void setEmail2(String _email2) {
        this._email2 = _email2;
    }

    public String getFax() {
        return _fax;
    }

    public void setFax(String _fax) {
        this._fax = _fax;
    }

    public String getFax2() {
        return _fax2;
    }

    public void setFax2(String _fax2) {
        this._fax2 = _fax2;
    }

    public String getFilialMitarbeiterZahl() {
        return _filialMitarbeiterZahl;
    }

    public void setFilialMitarbeiterZahl(String _filialMitarbeiterZahl) {
        this._filialMitarbeiterZahl = _filialMitarbeiterZahl;
    }

    public String getFilialTyp() {
        return _filialTyp;
    }

    public void setFilialTyp(String _filialTyp) {
        this._filialTyp = _filialTyp;
    }

    public String getFirmenName() {
        return _firmenName;
    }

    public void setFirmenName(String _firmenName) {
        this._firmenName = _firmenName;
    }

    public String getFirmenRechtsform() {
        return _firmenRechtsform;
    }

    public void setFirmenRechtsform(String _firmenRechtsform) {
        this._firmenRechtsform = _firmenRechtsform;
    }

    public String getFirmenTyp() {
        return _firmenTyp;
    }

    public void setFirmenTyp(String _firmenTyp) {
        this._firmenTyp = _firmenTyp;
    }

    public String getFirmenZusatz() {
        return _firmenZusatz;
    }

    public void setFirmenZusatz(String _firmenZusatz) {
        this._firmenZusatz = _firmenZusatz;
    }

    public String getFirmenZusatz2() {
        return _firmenZusatz2;
    }

    public void setFirmenZusatz2(String _firmenZusatz2) {
        this._firmenZusatz2 = _firmenZusatz2;
    }

    public String getGeschaeftsleiter() {
        return _geschaeftsleiter;
    }

    public void setGeschaeftsleiter(String _geschaeftsleiter) {
        this._geschaeftsleiter = _geschaeftsleiter;
    }

    public String[] getGesellschafter() {
        return _gesellschafter;
    }

    public void setGesellschafter(String[] _gesellschafter) {
        this._gesellschafter = _gesellschafter;
    }

    public String getGewerbeamtName() {
        return _gewerbeamtName;
    }

    public void setGewerbeamtName(String _gewerbeamtName) {
        this._gewerbeamtName = _gewerbeamtName;
    }

    public String getGewerbeamtOrt() {
        return _gewerbeamtOrt;
    }

    public void setGewerbeamtOrt(String _gewerbeamtOrt) {
        this._gewerbeamtOrt = _gewerbeamtOrt;
    }

    public String getGewerbeamtPLZ() {
        return _gewerbeamtPLZ;
    }

    public void setGewerbeamtPLZ(String _gewerbeamtPLZ) {
        this._gewerbeamtPLZ = _gewerbeamtPLZ;
    }

    public boolean isGewerbeamtShow() {
        return _gewerbeamtShow;
    }

    public void setGewerbeamtShow(boolean _gewerbeamtShow) {
        this._gewerbeamtShow = _gewerbeamtShow;
    }

    public String getGewerbeamtStrasse() {
        return _gewerbeamtStrasse;
    }

    public void setGewerbeamtStrasse(String _gewerbeamtStrasse) {
        this._gewerbeamtStrasse = _gewerbeamtStrasse;
    }

    public String getHandelsregisterName() {
        return _handelsregisterName;
    }

    public void setHandelsregisterName(String _handelsregisterName) {
        this._handelsregisterName = _handelsregisterName;
    }

    public String getHandelsregisterOrt() {
        return _handelsregisterOrt;
    }

    public void setHandelsregisterOrt(String _handelsregisterOrt) {
        this._handelsregisterOrt = _handelsregisterOrt;
    }

    public String getHandelsregisterPLZ() {
        return _handelsregisterPLZ;
    }

    public void setHandelsregisterPLZ(String _handelsregisterPLZ) {
        this._handelsregisterPLZ = _handelsregisterPLZ;
    }

    public String getHandelsregisterRegistrierNummer() {
        return _handelsregisterRegistrierNummer;
    }

    public void setHandelsregisterRegistrierNummer(String _handelsregisterRegistrierNummer) {
        this._handelsregisterRegistrierNummer = _handelsregisterRegistrierNummer;
    }

    public boolean isHandelsregisterShow() {
        return _handelsregisterShow;
    }

    public void setHandelsregisterShow(boolean _handelsregisterShow) {
        this._handelsregisterShow = _handelsregisterShow;
    }

    public String getHandelsregisterStrasse() {
        return _handelsregisterStrasse;
    }

    public void setHandelsregisterStrasse(String _handelsregisterStrasse) {
        this._handelsregisterStrasse = _handelsregisterStrasse;
    }

    public String getHomepage() {
        return _homepage;
    }

    public void setHomepage(String _homepage) {
        this._homepage = _homepage;
    }

    public String getHomepage2() {
        return _homepage2;
    }

    public void setHomepage2(String _homepage2) {
        this._homepage2 = _homepage2;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getIhkAbweichungen() {
        return _ihkAbweichungen;
    }

    public void setIhkAbweichungen(String _ihkAbweichungen) {
        this._ihkAbweichungen = _ihkAbweichungen;
    }

    public String getIhkName() {
        return _ihkName;
    }

    public void setIhkName(String _ihkName) {
        this._ihkName = _ihkName;
    }

    public String getIhkRegistriernummer() {
        return _ihkRegistriernummer;
    }

    public void setIhkRegistriernummer(String _ihkRegistriernummer) {
        this._ihkRegistriernummer = _ihkRegistriernummer;
    }

    public String getIhkStatus() {
        return _ihkStatus;
    }

    public void setIhkStatus(String _ihkStatus) {
        this._ihkStatus = _ihkStatus;
    }

    public String getLand() {
        return _land;
    }

    public void setLand(String _land) {
        this._land = _land;
    }

    public Timestamp getLastUsed() {
        return _lastUsed;
    }

    public void setLastUsed(Timestamp _lastUsed) {
        this._lastUsed = _lastUsed;
    }

    public String getLogo() {
        return _logo;
    }

    public void setLogo(String _logo) {
        this._logo = _logo;
    }

    public String getMobil() {
        return _mobil;
    }

    public void setMobil(String _mobil) {
        this._mobil = _mobil;
    }

    public String getMobil2() {
        return _mobil2;
    }

    public void setMobil2(String _mobil2) {
        this._mobil2 = _mobil2;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public String getOrt() {
        return _ort;
    }

    public void setOrt(String _ort) {
        this._ort = _ort;
    }

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public String getPlz() {
        return _plz;
    }

    public void setPlz(String _plz) {
        this._plz = _plz;
    }

    public String getPostfach() {
        return _postfach;
    }

    public void setPostfach(String _postfach) {
        this._postfach = _postfach;
    }

    public String getPostfachOrt() {
        return _postfachOrt;
    }

    public void setPostfachOrt(String _postfachOrt) {
        this._postfachOrt = _postfachOrt;
    }

    public String getPostfachPlz() {
        return _postfachPlz;
    }

    public void setPostfachPlz(String _postfachPlz) {
        this._postfachPlz = _postfachPlz;
    }

    public String getSecureMail() {
        return _secureMail;
    }

    public void setSecureMail(String _secureMail) {
        this._secureMail = _secureMail;
    }

    public String getSteuerNummer() {
        return _steuerNummer;
    }

    public void setSteuerNummer(String _steuerNummer) {
        this._steuerNummer = _steuerNummer;
    }

    public String getStrasse() {
        return _strasse;
    }

    public void setStrasse(String _strasse) {
        this._strasse = _strasse;
    }

    public String getTelefon() {
        return _telefon;
    }

    public void setTelefon(String _telefon) {
        this._telefon = _telefon;
    }

    public String getTelefon2() {
        return _telefon2;
    }

    public void setTelefon2(String _telefon2) {
        this._telefon2 = _telefon2;
    }

    public String getTelefon3() {
        return _telefon3;
    }

    public void setTelefon3(String _telefon3) {
        this._telefon3 = _telefon3;
    }

    public String getUstNummer() {
        return _ustNummer;
    }

    public void setUstNummer(String _ustNummer) {
        this._ustNummer = _ustNummer;
    }

    public String[] getVerbandsMitgliedschaften() {
        return _verbandsMitgliedschaften;
    }

    public void setVerbandsMitgliedschaften(String[] _verbandsMitgliedschaften) {
        this._verbandsMitgliedschaften = _verbandsMitgliedschaften;
    }

    public String getVermittlungNamen() {
        return _vermittlungNamen;
    }

    public void setVermittlungNamen(String _vermittlungNamen) {
        this._vermittlungNamen = _vermittlungNamen;
    }

    public String getVermoegensHaftpflicht() {
        return _vermoegensHaftpflicht;
    }

    public void setVermoegensHaftpflicht(String _vermoegensHaftpflicht) {
        this._vermoegensHaftpflicht = _vermoegensHaftpflicht;
    }

    public String[] getVersicherListe() {
        return _versicherListe;
    }

    public void setVersicherListe(String[] _versicherListe) {
        this._versicherListe = _versicherListe;
    }

    public String[] getVertretungsBerechtigteIHKErlaubnis() {
        return _vertretungsBerechtigteIHKErlaubnis;
    }

    public void setVertretungsBerechtigteIHKErlaubnis(String[] _vertretungsBerechtigteIHKErlaubnis) {
        this._vertretungsBerechtigteIHKErlaubnis = _vertretungsBerechtigteIHKErlaubnis;
    }

    public String[] getVertretungsBerechtigteNachname() {
        return _vertretungsBerechtigteNachname;
    }

    public void setVertretungsBerechtigteNachname(String[] _vertretungsBerechtigteNachname) {
        this._vertretungsBerechtigteNachname = _vertretungsBerechtigteNachname;
    }

    public String[] getVertretungsBerechtigtePosition() {
        return _vertretungsBerechtigtePosition;
    }

    public void setVertretungsBerechtigtePosition(String[] _vertretungsBerechtigtePosition) {
        this._vertretungsBerechtigtePosition = _vertretungsBerechtigtePosition;
    }

    public String[] getVertretungsBerechtigteVorname() {
        return _vertretungsBerechtigteVorname;
    }

    public void setVertretungsBerechtigteVorname(String[] _vertretungsBerechtigteVorname) {
        this._vertretungsBerechtigteVorname = _vertretungsBerechtigteVorname;
    }

}
