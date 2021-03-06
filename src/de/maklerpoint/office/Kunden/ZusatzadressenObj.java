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
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author yves
 */
public class ZusatzadressenObj {

    private int _id = -1;
    private int _creator = -1;

    private String _kundenKennung = "-1";
    private int _versichererId = -1;
    private int _benutzerId = -1;

    private String _name;
    private String _nameZusatz;
    private String _nameZusatz2;

    private String _street;
    private String _plz;

    private String _ort;

    private String _bundesland;
    private String _land;

    private String _communication1;
    private String _communication2;
    private String _communication3;
    private String _communication4;
    private String _communication5;
    private String _communication6;

    private int _communication1Type = CommunicationTypes.TELEFON;
    private int _communication2Type = CommunicationTypes.TELEFON2;
    private int _communication3Type = CommunicationTypes.FAX;
    private int _communication4Type = CommunicationTypes.MOBIL;
    private int _communication5Type = CommunicationTypes.EMAIL;
    private int _communication6Type = CommunicationTypes.WEBSEITE;
    
    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _comments;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    @Override
    public String toString() {
        // TODO ?
        
        if(_nameZusatz == null || _nameZusatz.length() < 1)
            return _name;
        else
            return _name + " - " + _nameZusatz;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public String getOrt() {
        return _ort;
    }

    public void setOrt(String _ort) {
        this._ort = _ort;
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

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public int getCreator() {
        return _creator;
    }

    public void setCreator(int _creator) {
        this._creator = _creator;
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

    public String getLand() {
        return _land;
    }

    public void setLand(String _land) {
        this._land = _land;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getNameZusatz() {
        return _nameZusatz;
    }

    public void setNameZusatz(String _nameZusatz) {
        this._nameZusatz = _nameZusatz;
    }

    public String getNameZusatz2() {
        return _nameZusatz2;
    }

    public void setNameZusatz2(String _nameZusatz2) {
        this._nameZusatz2 = _nameZusatz2;
    }

    public String getPlz() {
        return _plz;
    }

    public void setPlz(String _plz) {
        this._plz = _plz;
    }

    public String getStreet() {
        return _street;
    }

    public void setStreet(String _street) {
        this._street = _street;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
