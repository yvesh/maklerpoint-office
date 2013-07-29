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
public class KontoBuchungObj {

    private int _Id;
    private String _Name;
    private int _kontoId;
    private int _ParentId = 0;

    private int _way = 0; // 0 = empfangen, 1 = gesendet
        
    private String _empfaengerKontonummer;
    private String _empfaengerBankleitzahl;
    private String _empfaengerName;
    private String _empfaengerIban;
    private String _empfaengerBic;
    
    private String _senderKontonummer;
    private String _senderBankleitzahl;
    private String _senderName;
    private String _senderIban;
    private String _senderBic;
    
    private String _zweck;
    private int _wert;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _gebucht;
    private java.sql.Timestamp _lastAction;

    private String _comments;
    private String _custom1;
    private String _custom2;
    private String _custom3;
    

    public int getId() {
        return _Id;
    }

    public void setId(int _Id) {
        this._Id = _Id;
    }

    public String getName() {
        return _Name;
    }

    public void setName(String _Name) {
        this._Name = _Name;
    }

    public int getParentId() {
        return _ParentId;
    }

    public void setParentId(int _ParentId) {
        this._ParentId = _ParentId;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getEmpfaengerBankleitzahl() {
        return _empfaengerBankleitzahl;
    }

    public void setEmpfaengerBankleitzahl(String _empfaengerBankleitzahl) {
        this._empfaengerBankleitzahl = _empfaengerBankleitzahl;
    }

    public String getEmpfaengerBic() {
        return _empfaengerBic;
    }

    public void setEmpfaengerBic(String _empfaengerBic) {
        this._empfaengerBic = _empfaengerBic;
    }

    public String getEmpfaengerIban() {
        return _empfaengerIban;
    }

    public void setEmpfaengerIban(String _empfaengerIban) {
        this._empfaengerIban = _empfaengerIban;
    }

    public String getEmpfaengerKontonummer() {
        return _empfaengerKontonummer;
    }

    public void setEmpfaengerKontonummer(String _empfaengerKontonummer) {
        this._empfaengerKontonummer = _empfaengerKontonummer;
    }

    public String getEmpfaengerName() {
        return _empfaengerName;
    }

    public void setEmpfaengerName(String _empfaengerName) {
        this._empfaengerName = _empfaengerName;
    }

    public Timestamp getGebucht() {
        return _gebucht;
    }

    public void setGebucht(Timestamp _gebucht) {
        this._gebucht = _gebucht;
    }

    public int getKontoId() {
        return _kontoId;
    }

    public void setKontoId(int _kontoId) {
        this._kontoId = _kontoId;
    }

    public Timestamp getLastAction() {
        return _lastAction;
    }

    public void setLastAction(Timestamp _lastAction) {
        this._lastAction = _lastAction;
    }

    public String getSenderBankleitzahl() {
        return _senderBankleitzahl;
    }

    public void setSenderBankleitzahl(String _senderBankleitzahl) {
        this._senderBankleitzahl = _senderBankleitzahl;
    }

    public String getSenderBic() {
        return _senderBic;
    }

    public void setSenderBic(String _senderBic) {
        this._senderBic = _senderBic;
    }

    public String getSenderIban() {
        return _senderIban;
    }

    public void setSenderIban(String _senderIban) {
        this._senderIban = _senderIban;
    }

    public String getSenderKontonummer() {
        return _senderKontonummer;
    }

    public void setSenderKontonummer(String _senderKontonummer) {
        this._senderKontonummer = _senderKontonummer;
    }

    public String getSenderName() {
        return _senderName;
    }

    public void setSenderName(String _senderName) {
        this._senderName = _senderName;
    }

    public int getWay() {
        return _way;
    }

    public void setWay(int _way) {
        this._way = _way;
    }

    public int getWert() {
        return _wert;
    }

    public void setWert(int _wert) {
        this._wert = _wert;
    }

    public String getZweck() {
        return _zweck;
    }

    public void setZweck(String _zweck) {
        this._zweck = _zweck;
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

    

    @Override
    public String toString() {
        return _Name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KontoBuchungObj other = (KontoBuchungObj) obj;
        if ((this._Name == null) ? (other._Name != null) : !this._Name.equals(other._Name)) {
            return false;
        }
        if (this._kontoId != other._kontoId) {
            return false;
        }
        if (this._ParentId != other._ParentId) {
            return false;
        }
        if ((this._zweck == null) ? (other._zweck != null) : !this._zweck.equals(other._zweck)) {
            return false;
        }
        if (this._wert != other._wert) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this._Name != null ? this._Name.hashCode() : 0);
        hash = 97 * hash + this._kontoId;
        hash = 97 * hash + this._ParentId;
        hash = 97 * hash + (this._zweck != null ? this._zweck.hashCode() : 0);
        hash = 97 * hash + this._wert;
        return hash;
    }

}
