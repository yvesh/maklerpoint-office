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

import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
@Root(name = "privatkunde")
public class KundenObj {

    @Attribute(name = "id")
    private int _id;
    @Element(name = "betreuerId", required = false)
    private int _betreuerId = -1; 
    @Element(name = "creatorId", required = false)
    private int _creatorId = -1;
    @Element(name = "mandantenId", required = false)
    private int _mandantenId = -1;
    @Element(name = "kundenNr")
    private String _kundenNr;
    @Element(name = "anrede", required = false)
    private String _anrede;
    @Element(name = "titel", required = false)
    private String _titel;
    @Element(name = "arbeitgeber", required = false)
    private String _firma;
    @Element(name = "vorname")
    private String _vorname;
    @Element(name = "vorname2", required = false)
    private String _vorname2;
    @Element(name = "vornameWeitere", required = false)
    private String _vornameWeitere;
    @Element(name = "nachname")
    private String _nachname;
    @Element(name = "adresseZusatz", required = false)
    private String _adresseZusatz;
    @Element(name = "adresseZusatz2", required = false)
    private String _adresseZusatz2;
    @Element(name = "strasse", required = false)
    private String _street;
    @Element(name = "plz", required = false)
    private String _plz;
    @Element(name = "ort", required = false)
    private String _stadt;
    @Element(name = "bundesland", required = false)
    private String _bundesland;
    @Element(name = "land", required = false)
    private String _land;
    
    @Element(name = "typ", required = false)
    private String _typ; // TODO Remove
    
    @Element(name = "familienstand", required = false)
    private String _familienStand;
    @Element(name = "ehepartnerKennung", required = false)
    private String _ehepartnerKennung;
    @Element(name = "geburtsdatum", required = false)
    private String _geburtsdatum;
    @Element(name = "nationalitaet", required = false)
    private String _nationalitaet;
    @Element(name = "beruf", required = false)
    private String _beruf;
    @Element(name = "berufsTyp", required = false)
    private String _berufsTyp;
    @Element(name = "berufsOptionen", required = false)
    private String _berufsOptionen;
    @Element(name = "berufsBesonderheiten", required = false)
    private String _berufsBesonderheiten;
    @Element(name = "anteilBuerotaetigkeit", required = false)
    private String _anteilBuerotaetigkeit;
    @Element(name = "beginnRente", required = false)
    private String _beginnRente;
    @Element(name = "beamter", required = false)
    private boolean _beamter;
    @Element(name = "oeffentlicherDienst", required = false)
    private boolean _oeffentlicherDienst;
    @Element(name = "communication1", required = false)
    private String _communication1;
    @Element(name = "communication2", required = false)
    private String _communication2;
    @Element(name = "communication3", required = false)
    private String _communication3;
    @Element(name = "communication4", required = false)
    private String _communication4;
    @Element(name = "communication5", required = false)
    private String _communication5;
    @Element(name = "communication6", required = false)
    private String _communication6;
    @Element(name = "communication1Type", required = false)
    private int _communication1Type = CommunicationTypes.TELEFON;
    @Element(name = "communication2Type", required = false)
    private int _communication2Type = CommunicationTypes.TELEFON2;
    @Element(name = "communication3Type", required = false)
    private int _communication3Type = CommunicationTypes.FAX;
    @Element(name = "communication4Type", required = false)
    private int _communication4Type = CommunicationTypes.MOBIL;
    @Element(name = "communication5Type", required = false)
    private int _communication5Type = CommunicationTypes.EMAIL;
    @Element(name = "communication6Type", required = false)
    private int _communication6Type = CommunicationTypes.WEBSEITE;
    @Element(name = "einkommenBrutto", required = false)
    private double _einkommen = 0.00;
    @Element(name = "einkommenNetto", required = false)
    private double _einkommenNetto = 0.00;
    @Element(name = "steuertabelle", required = false)
    private String _Steuertabelle;
    @Element(name = "steuerklasse", required = false)
    private String _Steuerklasse;
    @Element(name = "kirchensteuer", required = false)
    private String _kirchenSteuer;
    private int _kinderZahl = 0;
    private String _kinderFreibetrag = "0";
    @Element(name = "religion", required = false)
    private String _religion;
    @Element(name = "rolleImHaushalt", required = false)
    private String _rolleImHaushalt;
    @Element(name = "weiterePersonenHaushalt", required = false)
    private String _weiterePersonen;
    @Element(name = "weiterePersonenInfo", required = false)
    private String _weiterePersonenInfo;
    @Element(name = "familienPlanung", required = false)
    private String _familienPlanung;
    @Element(name = "werberKennung", required = false)
    private String _werberKennung;
    @Element(name = "defaultKonto", required = false)
    private int _defaultKonto = -1;
    @Element(name = "kommentare", required = false)
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
    // Später hinzugefügte Felder
    @Element(name = "geburtsname", required = false)
    private String _geburtsname;
    @Element(name = "ehedatum", required = false)
    private String _ehedatum;
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;
    @Element(name = "status", required = false)
    private int status = Status.NORMAL;

    /**
     * 
     * @param vorname
     * @param nachname
     */
    public KundenObj() {
        super();
    }

    public KundenObj(String vorname, String nachname) {
        super();
        this._vorname = vorname;
        this._nachname = nachname;
    }

    public KundenObj(int _id, int _betreuerId, String _kundenNr, String _anrede,
            String _titel, String _firma, String _vorname, String _vorname2, String _vornameWeitere,
            String _nachname, String _adresseZusatz, String _adresseZusatz2, String _street,
            String _plz, String _stadt, String _bundesland, String _land, String _typ,
            String _familienStand, String _ehepartnerKennung, String _geburtsdatum,
            String _nationalitaet, String _beruf, String _berufsTyp, String _berufsOptionen,
            String _berufsBesonderheiten, String _anteilBuerotaetigkeit, String _beginnRente,
            boolean _beamter, boolean _oeffentlicherDienst, String _communication1,
            String _communication2, String _communication3, String _communication4, String _communication5,
            String _communication6, double _einkommen, double _einkommenNetto, String _Steuertabelle,
            String _Steuerklasse, String _kirchenSteuer, String _religion, String _rolleImHaushalt,
            String _weiterePersonen, String _weiterePersonenInfo, String _familienPlanung,
            String _werberKennung, String _comments, String _custom1, String _custom2,
            String _custom3, String _custom4, String _custom5, String _geburtsname,
            String _ehedatum, Timestamp _created, Timestamp _modified) {
        this._id = _id;
        this._betreuerId = _betreuerId;
        this._kundenNr = _kundenNr;
        this._anrede = _anrede;
        this._titel = _titel;
        this._firma = _firma;
        this._vorname = _vorname;
        this._vorname2 = _vorname2;
        this._vornameWeitere = _vornameWeitere;
        this._nachname = _nachname;
        this._adresseZusatz = _adresseZusatz;
        this._adresseZusatz2 = _adresseZusatz2;
        this._street = _street;
        this._plz = _plz;
        this._stadt = _stadt;
        this._bundesland = _bundesland;
        this._land = _land;
        this._typ = _typ;
        this._familienStand = _familienStand;
        this._ehepartnerKennung = _ehepartnerKennung;
        this._geburtsdatum = _geburtsdatum;
        this._nationalitaet = _nationalitaet;
        this._beruf = _beruf;
        this._berufsTyp = _berufsTyp;
        this._berufsOptionen = _berufsOptionen;
        this._berufsBesonderheiten = _berufsBesonderheiten;
        this._anteilBuerotaetigkeit = _anteilBuerotaetigkeit;
        this._beginnRente = _beginnRente;
        this._beamter = _beamter;
        this._oeffentlicherDienst = _oeffentlicherDienst;
        this._communication1 = _communication1;
        this._communication2 = _communication2;
        this._communication3 = _communication3;
        this._communication4 = _communication4;
        this._communication5 = _communication5;
        this._communication6 = _communication6;
        this._einkommen = _einkommen;
        this._einkommenNetto = _einkommenNetto;
        this._Steuertabelle = _Steuertabelle;
        this._Steuerklasse = _Steuerklasse;
        this._kirchenSteuer = _kirchenSteuer;
        this._religion = _religion;
        this._rolleImHaushalt = _rolleImHaushalt;
        this._weiterePersonen = _weiterePersonen;
        this._weiterePersonenInfo = _weiterePersonenInfo;
        this._familienPlanung = _familienPlanung;
        this._werberKennung = _werberKennung;
        this._comments = _comments;
        this._custom1 = _custom1;
        this._custom2 = _custom2;
        this._custom3 = _custom3;
        this._custom4 = _custom4;
        this._custom5 = _custom5;
        this._geburtsname = _geburtsname;
        this._ehedatum = _ehedatum;
        this._created = _created;
        this._modified = _modified;
    }

    /**
     * 
     * @param _id
     * @param _betreuer
     * @param _besitzer
     * @param _kundenNr
     * @param _anrede
     * @param _titel
     * @param _firma
     * @param _vorname
     * @param _vorname2
     * @param _vornameWeitere
     * @param _nachname
     * @param _street
     * @param _plz
     * @param _stadt
     * @param _bundesland
     * @param _land
     * @param _typ
     * @param _familienStand
     * @param _telefon
     * @param _telefon2
     * @param _privatTelefon
     * @param _dienstlichTelefon
     * @param _fax
     * @param _mobil
     * @param _mail
     * @param _mail2
     * @param _geburtsdatum
     * @param _homepage
     * @param _beruf
     * @param _berufsTyp
     * @param _berufsOptionen
     * @param _beamter
     * @param _oeffentlicherDienst
     * @param _einkommen
     * @param _Steuerklasse
     * @param _kinderZahl
     * @param _religion
     * @param _weiterePersonen
     * @param _weiterePersonenInfo
     * @param _familienPlanung
     * @param _comments
     * @param _custom1
     * @param _custom2
     * @param _custom3
     * @param _custom4
     * @param _custom5
     */
    public KundenObj(int _id, int _betreuerId, int _creatorId, String _kundenNr,
            String _anrede, String _titel, String _firma, String _vorname,
            String _vorname2, String _vornameWeitere, String _nachname,
            String _street, String _plz, String _stadt, String _bundesland, String _land,
            String _typ, String _familienStand, String _telefon, String _telefon2,
            String _privatTelefon, String _dienstlichTelefon, String _fax, String _mobil,
            String _mail, String _mail2, String _geburtsdatum,
            String _beruf, String _berufsTyp, String _berufsOptionen, boolean _beamter,
            boolean _oeffentlicherDienst, double _einkommen, String _Steuerklasse,
            int _kinderZahl, String _religion, String _weiterePersonen,
            String _weiterePersonenInfo, String _familienPlanung,
            String _comments, String _custom1, String _custom2,
            String _custom3, String _custom4, String _custom5) {
        super();
        this._id = _id;
        this._betreuerId = _betreuerId;
        this._creatorId = _creatorId;
        this._kundenNr = _kundenNr;
        this._anrede = _anrede;
        this._titel = _titel;
        this._firma = _firma;
        this._vorname = _vorname;
        this._vorname2 = _vorname2;
        this._vornameWeitere = _vornameWeitere;
        this._nachname = _nachname;
        this._street = _street;
        this._plz = _plz;
        this._stadt = _stadt;
        this._bundesland = _bundesland;
        this._land = _land;
        this._typ = _typ;
        this._familienStand = _familienStand;
        this._geburtsdatum = _geburtsdatum;
        this._beruf = _beruf;
        this._berufsTyp = _berufsTyp;
        this._berufsOptionen = _berufsOptionen;
        this._beamter = _beamter;
        this._oeffentlicherDienst = _oeffentlicherDienst;
        this._einkommen = _einkommen;
        this._Steuerklasse = _Steuerklasse;
        this._kinderZahl = _kinderZahl;
        this._religion = _religion;
        this._weiterePersonen = _weiterePersonen;
        this._weiterePersonenInfo = _weiterePersonenInfo;
        this._familienPlanung = _familienPlanung;
        this._comments = _comments;
        this._custom1 = _custom1;
        this._custom2 = _custom2;
        this._custom3 = _custom3;
        this._custom4 = _custom4;
        this._custom5 = _custom5;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public String getSteuerklasse() {
        return _Steuerklasse;
    }

    public void setSteuerklasse(String _Steuerklasse) {
        this._Steuerklasse = _Steuerklasse;
    }

    public int getDefaultKonto() {
        return _defaultKonto;
    }

    public void setDefaultKonto(int _defaultKonto) {
        this._defaultKonto = _defaultKonto;
    }

    public String getAnrede() {
        return _anrede;
    }

    public void setAnrede(String _anrede) {
        this._anrede = _anrede;
    }

    public boolean isBeamter() {
        return _beamter;
    }

    public void setBeamter(boolean _beamter) {
        this._beamter = _beamter;
    }

    public String getBeruf() {
        return _beruf;
    }

    public void setBeruf(String _beruf) {
        this._beruf = _beruf;
    }

    public String getBerufsOptionen() {
        return _berufsOptionen;
    }

    public String getRolleImHaushalt() {
        return _rolleImHaushalt;
    }

    public void setRolleImHaushalt(String _rolleImHaushalt) {
        this._rolleImHaushalt = _rolleImHaushalt;
    }

    public void setBerufsOptionen(String _berufsOptionen) {
        this._berufsOptionen = _berufsOptionen;
    }

    public String getBerufsTyp() {
        return _berufsTyp;
    }

    public void setBerufsTyp(String _berufsTyp) {
        this._berufsTyp = _berufsTyp;
    }

    public int getBetreuerId() {
        return _betreuerId;
    }

    public void setBetreuerId(int _betreuerId) {
        this._betreuerId = _betreuerId;
    }

    public int getCreatorId() {
        return _creatorId;
    }

    public void setCreatorId(int _creatorId) {
        this._creatorId = _creatorId;
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

    public String getEhepartnerKennung() {
        return _ehepartnerKennung;
    }

    public void setEhepartnerId(String _ehepartnerKennung) {
        this._ehepartnerKennung = _ehepartnerKennung;
    }


    public String getFamilienPlanung() {
        return _familienPlanung;
    }

    public void setFamilienPlanung(String _familienPlanung) {
        this._familienPlanung = _familienPlanung;
    }

    public String getFamilienStand() {
        return _familienStand;
    }

    public void setFamilienStand(String _familienStand) {
        this._familienStand = _familienStand;
    }

    public String getFirma() {
        return _firma;
    }

    public void setFirma(String _firma) {
        this._firma = _firma;
    }

    public String getGeburtsdatum() {
        return _geburtsdatum;
    }

    public void setGeburtsdatum(String _geburtsdatum) {
        this._geburtsdatum = _geburtsdatum;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getKinderZahl() {
        return _kinderZahl;
    }

    public void setKinderZahl(int _kinderZahl) {
        this._kinderZahl = _kinderZahl;
    }

    public String getKundenNr() {
        return _kundenNr;
    }

    public void setKundenNr(String _kundenNr) {
        this._kundenNr = _kundenNr;
    }

    public String getLand() {
        return _land;
    }

    public void setLand(String _land) {
        this._land = _land;
    }

    public String getNachname() {
        return _nachname;
    }

    public void setNachname(String _nachname) {
        this._nachname = _nachname;
    }

    public boolean isOeffentlicherDienst() {
        return _oeffentlicherDienst;
    }

    public void setOeffentlicherDienst(boolean _oeffentlicherDienst) {
        this._oeffentlicherDienst = _oeffentlicherDienst;
    }

    public String getPlz() {
        return _plz;
    }

    public void setPlz(String _plz) {
        this._plz = _plz;
    }

    public String getReligion() {
        return _religion;
    }

    public void setReligion(String _religion) {
        this._religion = _religion;
    }

    public String getStadt() {
        return _stadt;
    }

    public void setStadt(String _stadt) {
        this._stadt = _stadt;
    }

    public String getStreet() {
        return _street;
    }

    public void setStreet(String _street) {
        this._street = _street;
    }

    public String getTitel() {
        return _titel;
    }

    public void setTitel(String _titel) {
        this._titel = _titel;
    }

    public String getTyp() {
        return _typ;
    }

    public void setTyp(String _typ) {
        this._typ = _typ;
    }

    public String getVorname() {
        return _vorname;
    }

    public void setVorname(String _vorname) {
        this._vorname = _vorname;
    }

    public String getVorname2() {
        return _vorname2;
    }

    public void setVorname2(String _vorname2) {
        this._vorname2 = _vorname2;
    }

    public String getVornameWeitere() {
        return _vornameWeitere;
    }

    public void setVornameWeitere(String _vornameWeitere) {
        this._vornameWeitere = _vornameWeitere;
    }

    public String getWeiterePersonen() {
        return _weiterePersonen;
    }

    public void setWeiterePersonen(String _weiterePersonen) {
        this._weiterePersonen = _weiterePersonen;
    }

    public String getWeiterePersonenInfo() {
        return _weiterePersonenInfo;
    }

    public void setWeiterePersonenInfo(String _weiterePersonenInfo) {
        this._weiterePersonenInfo = _weiterePersonenInfo;
    }

    public String getWerberKennung() {
        return _werberKennung;
    }

    public void setWerberKennung(String _werberKennung) {
        this._werberKennung = _werberKennung;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSteuertabelle() {
        return _Steuertabelle;
    }

    public void setSteuertabelle(String _Steuertabelle) {
        this._Steuertabelle = _Steuertabelle;
    }

    public String getAnteilBuerotaetigkeit() {
        return _anteilBuerotaetigkeit;
    }

    public void setAnteilBuerotaetigkeit(String _anteilBuerotaetigkeit) {
        this._anteilBuerotaetigkeit = _anteilBuerotaetigkeit;
    }

    public String getBeginnRente() {
        return _beginnRente;
    }

    public void setBeginnRente(String _beginnRente) {
        this._beginnRente = _beginnRente;
    }

    public String getBerufsBesonderheiten() {
        return _berufsBesonderheiten;
    }

    public void setBerufsBesonderheiten(String _berufsBesonderheiten) {
        this._berufsBesonderheiten = _berufsBesonderheiten;
    }

    public double getEinkommen() {
        return _einkommen;
    }

    public void setEinkommen(double _einkommen) {
        this._einkommen = _einkommen;
    }

    public double getEinkommenNetto() {
        return _einkommenNetto;
    }

    public void setEinkommenNetto(double _einkommenNetto) {
        this._einkommenNetto = _einkommenNetto;
    }    
    
    public String getKirchenSteuer() {
        return _kirchenSteuer;
    }

    public void setKirchenSteuer(String _kirchenSteuer) {
        this._kirchenSteuer = _kirchenSteuer;
    }

    public String getKinderFreibetrag() {
        return _kinderFreibetrag;
    }

    public void setKinderFreibetrag(String _kinderFreibetrag) {
        this._kinderFreibetrag = _kinderFreibetrag;
    }

    public String getAdresseZusatz2() {
        return _adresseZusatz2;
    }

    public void setAdresseZusatz2(String _addresseZusatz2) {
        this._adresseZusatz2 = _addresseZusatz2;
    }

    public String getAdresseZusatz() {
        return _adresseZusatz;
    }

    public void setAdresseZusatz(String _adresseZusatz) {
        this._adresseZusatz = _adresseZusatz;
    }

    public String getNationalitaet() {
        return _nationalitaet;
    }

    public void setNationalitaet(String _nationalität) {
        this._nationalitaet = _nationalität;
    }

    public String getGeburtsname() {
        return _geburtsname;
    }

    public void setGeburtsname(String _geburtsname) {
        this._geburtsname = _geburtsname;
    }

    public String getEhedatum() {
        return _ehedatum;
    }

    public void setEhedatum(String _ehedatum) {
        this._ehedatum = _ehedatum;
    }

    public String getCommunication1() {
        return _communication1;
    }

    public void setCommunication1(String _communication1) {
        this._communication1 = _communication1;
    }

    public int getCommunication1Type() {
        return _communication1Type;
    }

    public void setCommunication1Type(int _communication1Type) {
        this._communication1Type = _communication1Type;
    }

    public String getCommunication2() {
        return _communication2;
    }

    public void setCommunication2(String _communication2) {
        this._communication2 = _communication2;
    }

    public int getCommunication2Type() {
        return _communication2Type;
    }

    public void setCommunication2Type(int _communication2Type) {
        this._communication2Type = _communication2Type;
    }

    public String getCommunication3() {
        return _communication3;
    }

    public void setCommunication3(String _communication3) {
        this._communication3 = _communication3;
    }

    public int getCommunication3Type() {
        return _communication3Type;
    }

    public void setCommunication3Type(int _communication3Type) {
        this._communication3Type = _communication3Type;
    }

    public String getCommunication4() {
        return _communication4;
    }

    public void setCommunication4(String _communication4) {
        this._communication4 = _communication4;
    }

    public int getCommunication4Type() {
        return _communication4Type;
    }

    public void setCommunication4Type(int _communication4Type) {
        this._communication4Type = _communication4Type;
    }

    public String getCommunication5() {
        return _communication5;
    }

    public void setCommunication5(String _communication5) {
        this._communication5 = _communication5;
    }

    public int getCommunication5Type() {
        return _communication5Type;
    }

    public void setCommunication5Type(int _communication5Type) {
        this._communication5Type = _communication5Type;
    }

    public String getCommunication6() {
        return _communication6;
    }

    public void setCommunication6(String _communication6) {
        this._communication6 = _communication6;
    }

    public int getCommunication6Type() {
        return _communication6Type;
    }

    public void setCommunication6Type(int _communication6Type) {
        this._communication6Type = _communication6Type;
    }

    @Override
    public String toString() {
        int sett = Config.getInt("kundenToString", 0);

        if (sett == 0) {
            return this._vorname.concat(" ").concat(this._nachname).concat(" (").concat(this._kundenNr).concat(")");
        } else if (sett == 1) {
            return this._nachname + " " + this._vorname + " (" + this._kundenNr + ")";
        } else if (sett == 2) {
            return this._vorname + " " + this._nachname;
        } else if (sett == 3) {
            return this._kundenNr;
        } else if (sett == 4) {
            return this._kundenNr + " " + this._nachname;
        } else {
            return this._vorname.concat(" ").concat(this._nachname).concat(" (").concat(this._kundenNr).concat(")"); // Fallback
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KundenObj other = (KundenObj) obj;
        if ((this._vorname == null) ? (other._vorname != null) : !this._vorname.equals(other._vorname)) {
            return false;
        }
        if ((this._nachname == null) ? (other._nachname != null) : !this._nachname.equals(other._nachname)) {
            return false;
        }
        if ((this._street == null) ? (other._street != null) : !this._street.equals(other._street)) {
            return false;
        }


        if ((this._stadt == null) ? (other._stadt != null) : !this._stadt.equals(other._stadt)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this._vorname != null ? this._vorname.hashCode() : 0);
        hash = 73 * hash + (this._nachname != null ? this._nachname.hashCode() : 0);
        hash = 73 * hash + (this._street != null ? this._street.hashCode() : 0);
        hash = 73 * hash + (this._stadt != null ? this._stadt.hashCode() : 0);
        return hash;
    }
}