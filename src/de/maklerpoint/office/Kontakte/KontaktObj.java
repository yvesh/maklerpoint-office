/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 16:09:27
 *  File:       KontaktObj
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Kontakte;

import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

@Root(name = "kontakt")
public class KontaktObj {
    
    private int id;
    
    @Element(name = "creatorId", required = false)
    private int creatorId = -1;
    
    @Element(name = "kundenNr", required = false)
    private String kundenKennung = "-1";
    
    @Element(name = "versichererId", required = false)
    private int versichererId = -1;
    
    @Element(name = "produktId", required = false)
    private int produktId = -1;
    
    @Element(name = "vertragId", required = false)
    private int vertragId = -1;
    
    @Element(name = "schadenId", required = false)
    private int schadenId = -1;
    
    @Element(name = "stoerfallId", required = false)
    private int stoerId = -1;
   
    @Element(name = "benutzerId", required = false)
    private int benutzerId = -1;
        
    @Element(name = "name")    
    private String name = null;
    
    @Element(name = "adresse", required = false)
    private String adresse = null;
    
    @Element(name = "communication1", required = false)
    private String _communication1 = null;
    @Element(name = "communication2", required = false)
    private String _communication2 = null;
    @Element(name = "communication3", required = false)
    private String _communication3 = null;
    @Element(name = "communication4", required = false)
    private String _communication4 = null;
    @Element(name = "communication5", required = false)
    private String _communication5 = null;
    @Element(name = "communication6", required = false)
    private String _communication6 = null;
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
    
    @Element(name = "kommentare", required = false)
    private String _comments = null;
    @Element(name = "custom1", required = false)
    private String _custom1 = null;
    @Element(name = "custom2", required = false)
    private String _custom2 = null;
    @Element(name = "custom3", required = false)
    private String _custom3 = null;
    
    private java.sql.Timestamp created;
    private java.sql.Timestamp modified;
    
    private int status = Status.NORMAL;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(int benutzerId) {
        this.benutzerId = benutzerId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKundenKennung() {
        return kundenKennung;
    }

    public void setKundenKennung(String kundenKennung) {
        this.kundenKennung = kundenKennung;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduktId() {
        return produktId;
    }

    public void setProduktId(int produktId) {
        this.produktId = produktId;
    }

    public int getSchadenId() {
        return schadenId;
    }

    public void setSchadenId(int schadenId) {
        this.schadenId = schadenId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStoerId() {
        return stoerId;
    }

    public void setStoerId(int stoerId) {
        this.stoerId = stoerId;
    }

    public int getVersichererId() {
        return versichererId;
    }

    public void setVersichererId(int versichererId) {
        this.versichererId = versichererId;
    }

    public int getVertragId() {
        return vertragId;
    }

    public void setVertragId(int vertragId) {
        this.vertragId = vertragId;
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
    public String toString(){
        return this.name; // TODO customize
    }
}
