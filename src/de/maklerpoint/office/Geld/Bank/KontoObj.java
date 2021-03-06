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

package de.maklerpoint.office.Geld.Bank;

import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KontoObj {

    private int _Id;
    private int _parentId = 0; // Für unterkonten

    private String _name;
    private String _kontonummer;
    private String _bankleitzahl;
    private String _iban;
    private String _bic;
    private String _inhaber;

    private String _country;
    private String _zweck;

    private int _stand;
    private int _soll;
    private int _haben;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _lastRefresh;
    private java.sql.Timestamp _lastAction;

    private String _comments;
    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _custom4;
    private String _custom5;

    public KontoObj(int _Id, int _parentId, String _name, String _kontonummer,
            String _bankleitzahl, String _iban, String _bic, String _inhaber,
            String _country, String _zweck, int _stand, int _soll, int _haben,
            Timestamp _created, Timestamp _lastRefresh, Timestamp _lastAction) {
        this._Id = _Id;
        this._parentId = _parentId;
        this._name = _name;
        this._kontonummer = _kontonummer;
        this._bankleitzahl = _bankleitzahl;
        this._iban = _iban;
        this._bic = _bic;
        this._inhaber = _inhaber;
        this._country = _country;
        this._zweck = _zweck;
        this._stand = _stand;
        this._soll = _soll;
        this._haben = _haben;
        this._created = _created;
        this._lastRefresh = _lastRefresh;
        this._lastAction = _lastAction;
    }

    public int getId() {
        return _Id;
    }

    public void setId(int _Id) {
        this._Id = _Id;
    }

    public String getBankleitzahl() {
        return _bankleitzahl;
    }

    public void setBankleitzahl(String _bankleitzahl) {
        this._bankleitzahl = _bankleitzahl;
    }

    public String getBic() {
        return _bic;
    }

    public void setBic(String _bic) {
        this._bic = _bic;
    }

    public String getComments() {
        return _comments;
    }

    public void setComments(String _comments) {
        this._comments = _comments;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String _country) {
        this._country = _country;
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

    public int getHaben() {
        return _haben;
    }

    public void setHaben(int _haben) {
        this._haben = _haben;
    }

    public String getIban() {
        return _iban;
    }

    public void setIban(String _iban) {
        this._iban = _iban;
    }

    public String getInhaber() {
        return _inhaber;
    }

    public void setInhaber(String _inhaber) {
        this._inhaber = _inhaber;
    }

    public String getKontonummer() {
        return _kontonummer;
    }

    public void setKontonummer(String _kontonummer) {
        this._kontonummer = _kontonummer;
    }

    public Timestamp getLastAction() {
        return _lastAction;
    }

    public void setLastAction(Timestamp _lastAction) {
        this._lastAction = _lastAction;
    }

    public Timestamp getLastRefresh() {
        return _lastRefresh;
    }

    public void setLastRefresh(Timestamp _lastRefresh) {
        this._lastRefresh = _lastRefresh;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public int getSoll() {
        return _soll;
    }

    public void setSoll(int _soll) {
        this._soll = _soll;
    }

    public int getStand() {
        return _stand;
    }

    public void setStand(int _stand) {
        this._stand = _stand;
    }

    public String getZweck() {
        return _zweck;
    }

    public void setZweck(String _zweck) {
        this._zweck = _zweck;
    }

    @Override
    public String toString() {
        return _name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KontoObj other = (KontoObj) obj;
        if ((this._name == null) ? (other._name != null) : !this._name.equals(other._name)) {
            return false;
        }
        if ((this._kontonummer == null) ? (other._kontonummer != null) : !this._kontonummer.equals(other._kontonummer)) {
            return false;
        }
        if ((this._bankleitzahl == null) ? (other._bankleitzahl != null) : !this._bankleitzahl.equals(other._bankleitzahl)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this._name != null ? this._name.hashCode() : 0);
        hash = 17 * hash + (this._kontonummer != null ? this._kontonummer.hashCode() : 0);
        hash = 17 * hash + (this._bankleitzahl != null ? this._bankleitzahl.hashCode() : 0);
        return hash;
    }


}