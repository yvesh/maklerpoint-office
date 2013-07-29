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

package de.maklerpoint.office.Benutzer;

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

@Root(name = "benutzer")
public class BenutzerObj {

    @Attribute(name = "id")
    private int _Id = -1;
    
    @Element(name = "parentId", required = false)
    private int _parentId = -1;
    
    @Element(name = "mandantenId", required = false)
    private int _firmenId = -1;
    
    @Element(name = "level")
    private int _level = -1;
   
    @Element(name = "boolUntervermittler", required = false)
    private boolean _unterVermittler = false;

    @Element(name = "kennung")
    private String _kennung;

    @Element(name = "anrede", required = false)
    private String _anrede;

    @Element(name = "vorname")
    private String _vorname;
    
    @Element(name = "vorname2", required = false)
    private String _vorname2;
    
    @Element(name = "vornameWeitere", required = false)
    private String _weitereVornamen;
    
    @Element(name = "nachname")
    private String _nachname;        
    
    @Element(name = "strasse", required = false)
    private String _strasse;
    
    @Element(name = "strasse2", required = false)
    private String _strasse2;
    
    @Element(name = "plz", required = false)
    private String _plz;
    
    @Element(name = "ort", required = false)
    private String _Ort;
    
    @Element(name = "adresseZusatz", required = false)
    private String _addresseZusatz;
    
    @Element(name = "adresseZusatz2", required = false)
    private String _addresseZusatz2;
    
    @Element(name = "bundesland", required = false)
    private String _bundesland = "Unbekannt";
    
    @Element(name = "land", required = false)
    private String _land = "Deutschland";

    @Element(name = "telefon", required = false)
    private String _telefon;
    @Element(name = "telefon2", required = false)
    private String _telefon2;
    @Element(name = "fax", required = false)
    private String _fax;
    @Element(name = "fax2", required = false)
    private String _fax2;
    @Element(name = "mobil", required = false)
    private String _mobil;
    @Element(name = "mobil2", required = false)
    private String _mobil2;

    @Element(name = "geburtsdatum", required = false)
    private String _geburtsDatum;

    @Element(name = "email", required = false)
    private String _email;
    @Element(name = "email2", required = false)
    private String _email2;

    @Element(name = "webseite", required = false)
    private String _homepage;
    @Element(name = "webseite2", required = false)
    private String _homepage2;

    @Element(name = "username")
    private String _username; // username or kennung
    @Element(name = "password")
    private String _password;
    
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
    
    @Element(name = "letzteAnmeldungDatum", required = false)
    private java.sql.Timestamp _lastlogin;

    @Element(name = "logincount", required = false)
    private int _logincount;
    
    private boolean _webenabled = false;
    
    @Element(name = "status", required = false)
    private int _status = Status.NORMAL;

    public BenutzerObj(int id, String kennung, int userlevel){
        super();
        this._Id = id;
        this._kennung = kennung;
        this._level = userlevel;
        this._lastlogin = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public String getVorname2() {
        return _vorname2;
    }

    public void setVorname2(String _vorname2) {
        this._vorname2 = _vorname2;
    }

    public String getWeitereVornamen() {
        return _weitereVornamen;
    }

    public void setWeitereVornamen(String _weitereVornamen) {
        this._weitereVornamen = _weitereVornamen;
    }

    public String getAnrede() {
        return _anrede;
    }

    public void setAnrede(String _anrede) {
        this._anrede = _anrede;
    }

    public String getGeburtsDatum() {
        return _geburtsDatum;
    }

    public void setGeburtsDatum(String _geburtsDatum) {
        this._geburtsDatum = _geburtsDatum;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public String getLand() {
        return _land;
    }

    public void setLand(String _land) {
        this._land = _land;
    }


    public BenutzerObj(){
        super();
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public Timestamp getLastlogin() {
        return _lastlogin;
    }

    public void setLastlogin(Timestamp _lastlogin) {
        this._lastlogin = _lastlogin;
    }

    public int getId() {
        return _Id;
    }

    public void setId(int _Id) {
        this._Id = _Id;
    }

    public String getOrt() {
        return _Ort;
    }

    public void setOrt(String _Ort) {
        this._Ort = _Ort;
    }

    public String getAddresseZusatz() {
        return _addresseZusatz;
    }

    public void setAddresseZusatz(String _addresseZusatz) {
        this._addresseZusatz = _addresseZusatz;
    }

    public String getAddresseZusatz2() {
        return _addresseZusatz2;
    }

    public void setAddresseZusatz2(String _addresseZusatz2) {
        this._addresseZusatz2 = _addresseZusatz2;
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

    public int getFirmenId() {
        return _firmenId;
    }

    public void setFirmenId(int _firmenId) {
        this._firmenId = _firmenId;
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

    public String getKennung() {
        return _kennung;
    }

    public void setKennung(String _kennung) {
        this._kennung = _kennung;
    }

    public int getLevel() {
        return _level;
    }

    public void setLevel(int _level) {
        this._level = _level;
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

    public String getNachname() {
        return _nachname;
    }

    public void setNachname(String _nachname) {
        this._nachname = _nachname;
    }

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPlz() {
        return _plz;
    }

    public void setPlz(String _plz) {
        this._plz = _plz;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public String getStrasse() {
        return _strasse;
    }

    public void setStrasse(String _strasse) {
        this._strasse = _strasse;
    }

    public String getStrasse2() {
        return _strasse2;
    }

    public void setStrasse2(String _strasse2) {
        this._strasse2 = _strasse2;
    }

    public String getBundesland() {
        return _bundesland;
    }

    public void setBundesland(String _bundesland) {
        this._bundesland = _bundesland;
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

    public boolean isUnterVermittler() {
        return _unterVermittler;
    }

    public void setUnterVermittler(boolean _unterVermittler) {
        this._unterVermittler = _unterVermittler;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getVorname() {
        return _vorname;
    }

    public void setVorname(String _vorname) {
        this._vorname = _vorname;
    }

    public int getLogincount() {
        return _logincount;
    }

    public void setLogincount(int _logincount) {
        this._logincount = _logincount;
    }

    public boolean isWebenabled() {
        return _webenabled;
    }

    public void setWebenabled(boolean _webenabled) {
        this._webenabled = _webenabled;
    }

    @Override
    public String toString() {
        int sett = Config.getInt("benutzerToString", 0);  
        
        if(sett == 0) {
            return this.getVorname().concat(" ").concat(this.getNachname()).concat(" (").concat(this.getKennung()).concat(")");
        } else if (sett == 1) {
            return this.getNachname() + " " + this.getVorname() + " (" + this.getKennung() + ")";
        } else if (sett == 2) {
            return this.getVorname() + " " + this.getNachname();
        } else if (sett == 3) {
            return this.getKennung();
        } else if (sett == 4) {
            return _kennung + " " + _nachname;
        } else {
            return this.getVorname().concat(" ").concat(this.getNachname()).concat(" (").concat(this.getKennung()).concat(")");
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
        final BenutzerObj other = (BenutzerObj) obj;
        if ((this._kennung == null) ? (other._kennung != null) : !this._kennung.equals(other._kennung)) {
            return false;
        }
        if ((this._vorname == null) ? (other._vorname != null) : !this._vorname.equals(other._vorname)) {
            return false;
        }
        if ((this._nachname == null) ? (other._nachname != null) : !this._nachname.equals(other._nachname)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this._kennung != null ? this._kennung.hashCode() : 0);
        hash = 73 * hash + (this._vorname != null ? this._vorname.hashCode() : 0);
        hash = 73 * hash + (this._nachname != null ? this._nachname.hashCode() : 0);
        return hash;
    }
}
