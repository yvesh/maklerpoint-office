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

package de.maklerpoint.office.Bank;

import de.maklerpoint.office.Konstanten.Banken;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class BankKontoObj {
    
    private int _id;
    
    private int _creatorId = -1;
    
    private int _type = Banken.STANDARD_KONTO;
    
    private String _kundenKennung = "-1";
    private int _versichererId = -1;
    private int _benutzerId = -1;
    
    private String _kontonummer;
    private String _bankleitzahl;
    private String _bankinstitut;
    private String _kontoinhaber;
    
    private String _iban;
    private String _bic;
    
    private String _comments;
    
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int _status = Status.NORMAL;

    public String getBankinstitut() {
        return _bankinstitut;
    }

    public void setBankinstitut(String _bankinstitut) {
        this._bankinstitut = _bankinstitut;
    }

    public String getBankleitzahl() {
        return _bankleitzahl;
    }

    public void setBankleitzahl(String _bankleitzahl) {
        this._bankleitzahl = _bankleitzahl;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
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

    public int getCreatorId() {
        return _creatorId;
    }

    public void setCreatorId(int _creatorId) {
        this._creatorId = _creatorId;
    }

    public String getIban() {
        return _iban;
    }

    public void setIban(String _iban) {
        this._iban = _iban;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKontoinhaber() {
        return _kontoinhaber;
    }

    public void setKontoinhaber(String _kontoinhaber) {
        this._kontoinhaber = _kontoinhaber;
    }

    public String getKontonummer() {
        return _kontonummer;
    }

    public void setKontonummer(String _kontonummer) {
        this._kontonummer = _kontonummer;
    }

    public String getKundenKennung() {
        return _kundenKennung;
    }

    public void setKundenKennung(String _kundenKennung) {
        this._kundenKennung = _kundenKennung;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
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
    
    @Override
    public String toString(){
        return _kontonummer;
    }
    
}
