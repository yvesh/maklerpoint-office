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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FirmenAnsprechpartnerObj {

    private SimpleDateFormat gebParse = new SimpleDateFormat("dd.MM.yyyy");
    
    private int _id;
    private String _kundenKennung;
    private int _versichererId = -1;

    private int _creatorId;

    private int _ordering = -1;

    private String _anrede = "Herr";
    private String _title = "";

    private String _vorname;
    private String _nachname;

    private String _geburtdatum;

    private String _abteilung = "Unbekannt";
    private String _funktion = "Unbekannt";

    private int _prioritaet = 0;

    private String _communication1;
    private String _communication2;
    private String _communication3;
    private String _communication4;
    private String _communication5;
    
    private int _communication1Type = CommunicationTypes.TELEFON;
    private int _communication2Type = CommunicationTypes.TELEFON2;
    private int _communication3Type = CommunicationTypes.FAX;
    private int _communication4Type = CommunicationTypes.MOBIL;
    private int _communication5Type = CommunicationTypes.EMAIL;
    
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    public FirmenAnsprechpartnerObj() {
    }

    @Override
    public String toString() {
        return _vorname + " " + _nachname;
    }

    public String getAbteilung() {
        return _abteilung;
    }

    public void setAbteilung(String _abteilung) {
        this._abteilung = _abteilung;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public String getAnrede() {
        return _anrede;
    }

    public void setAnrede(String _anrede) {
        this._anrede = _anrede;
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

    public String getFunktion() {
        return _funktion;
    }

    public void setFunktion(String _funktion) {
        this._funktion = _funktion;
    }

    public String getGeburtdatum() {
        return _geburtdatum;
    }
    
    public Date getGeburtdatumDate() throws ParseException {
        return gebParse.parse(_geburtdatum);
    }
    
    public void setGeburtdatum(String _geburtdatum) {
        this._geburtdatum = _geburtdatum;
    }
    
     public void setGeburtdatumDate(Date _gebdate) {
        this._geburtdatum = gebParse.format(_gebdate);
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

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public String getNachname() {
        return _nachname;
    }

    public void setNachname(String _nachname) {
        this._nachname = _nachname;
    }

    public int getOrdering() {
        return _ordering;
    }

    public void setOrdering(int _ordering) {
        this._ordering = _ordering;
    }

    public int getPrioritaet() {
        return _prioritaet;
    }

    public void setPrioritaet(int _prioritaet) {
        this._prioritaet = _prioritaet;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getVorname() {
        return _vorname;
    }

    public void setVorname(String _vorname) {
        this._vorname = _vorname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
