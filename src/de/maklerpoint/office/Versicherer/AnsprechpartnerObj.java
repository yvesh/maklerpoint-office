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

package de.maklerpoint.office.Versicherer;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class AnsprechpartnerObj {

    private int _id;
    private int _parentId;
    private String _parentString;

    private String _vorname;
    private String _nachname;
    private String _abteilung;
    private String _telefon;
    private String _telefon2;
    private String _fax;
    private String _email;
    private String _mobil;

    private String _strasse;
    private int _plz;
    private String _stadt;
    private String _bundesLand;
    private String _land;

    private String _comments;
    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _custom4;
    private String _custom5;


    public AnsprechpartnerObj(String _vorname, String _nachname){
        super();
        this._vorname = _vorname;
        this._nachname = _nachname;
    }


    public AnsprechpartnerObj(int _id, int _parentId, String _parentString,
            String _vorname, String _nachname, String _abteilung, String _telefon,
            String _telefon2, String _fax, String _email, String _mobil,
            String _strasse, int _plz, String _stadt, String _bundesLand,
            String _land, String _comments, String _custom1, String _custom2,
            String _custom3, String _custom4, String _custom5) {
        super();
        this._id = _id;
        this._parentId = _parentId;
        this._parentString = _parentString;
        this._vorname = _vorname;
        this._nachname = _nachname;
        this._abteilung = _abteilung;
        this._telefon = _telefon;
        this._telefon2 = _telefon2;
        this._fax = _fax;
        this._email = _email;
        this._mobil = _mobil;
        this._strasse = _strasse;
        this._plz = _plz;
        this._stadt = _stadt;
        this._bundesLand = _bundesLand;
        this._land = _land;
        this._comments = _comments;
        this._custom1 = _custom1;
        this._custom2 = _custom2;
        this._custom3 = _custom3;
        this._custom4 = _custom4;
        this._custom5 = _custom5;
    }

    public String getAbteilung() {
        return _abteilung;
    }

    public void setAbteilung(String _abteilung) {
        this._abteilung = _abteilung;
    }

    public String getBundesLand() {
        return _bundesLand;
    }

    public void setBundesLand(String _bundesLand) {
        this._bundesLand = _bundesLand;
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

    public String getFax() {
        return _fax;
    }

    public void setFax(String _fax) {
        this._fax = _fax;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getLand() {
        return _land;
    }

    public void setLand(String _land) {
        this._land = _land;
    }

    public String getMobil() {
        return _mobil;
    }

    public void setMobil(String _mobil) {
        this._mobil = _mobil;
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

    public String getParentString() {
        return _parentString;
    }

    public void setParentString(String _parentString) {
        this._parentString = _parentString;
    }

    public int getPlz() {
        return _plz;
    }

    public void setPlz(int _plz) {
        this._plz = _plz;
    }

    public String getStadt() {
        return _stadt;
    }

    public void setStadt(String _stadt) {
        this._stadt = _stadt;
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

    public String getVorname() {
        return _vorname;
    }

    public void setVorname(String _vorname) {
        this._vorname = _vorname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnsprechpartnerObj other = (AnsprechpartnerObj) obj;
        if ((this._vorname == null) ? (other._vorname != null) : !this._vorname.equals(other._vorname)) {
            return false;
        }
        if ((this._nachname == null) ? (other._nachname != null) : !this._nachname.equals(other._nachname)) {
            return false;
        }
        if ((this._strasse == null) ? (other._strasse != null) : !this._strasse.equals(other._strasse)) {
            return false;
        }
        if (this._plz != other._plz) {
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
        hash = 79 * hash + (this._vorname != null ? this._vorname.hashCode() : 0);
        hash = 79 * hash + (this._nachname != null ? this._nachname.hashCode() : 0);
        hash = 79 * hash + (this._strasse != null ? this._strasse.hashCode() : 0);
        hash = 79 * hash + this._plz;
        hash = 79 * hash + (this._stadt != null ? this._stadt.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this._vorname + " " + this._nachname;
    }

}
