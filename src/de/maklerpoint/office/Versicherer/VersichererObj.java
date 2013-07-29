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

@Root(name = "gesellschaft")
public class VersichererObj {

    
    @Attribute(name = "id")
    private int _id;
    
    @Element(name = "parentId", required = false)    
    private int _parentId = 0;
    
    @Element(name = "creatorId", required = false)
    private int _creatorID = -1;
    
    @Element(name = "mandantenId", required = false)
    private int _mandantenId = -1;
        
    private String _parentName = null;
    
    @Element(name = "vunummer", required = false)
    private String _vuNummer;

    @Element(name = "name")
    private String _name;
    
    @Element(name = "nameZusatz", required = false)
    private String _nameZusatz;
        
    @Element(name = "nameZusatz2", required = false)
    private String _nameZusatz2;

    @Element(name = "kuerzel", required = false)
    private String _kuerzel;
    
    @Element(name = "gesellschaftsnr", required = false)
    private String _gesellschaftsNr;

    @Element(name = "strasse", required = false)
    private String _strasse;
    
    @Element(name = "plz", required = false)
    private String _plz;
    
    @Element(name = "ort", required = false)
    private String _stadt;
    
    @Element(name = "bundesland", required = false)
    private String _bundesLand;
    
    @Element(name = "land", required = false)
    private String _land;
    
    @Element(name = "boolPostfach", required = false)
    private boolean _postfach = false;
    
    @Element(name = "postfach", required = false)
    private String _postfachName;
    
    @Element(name = "postfachPlz", required = false)
    private String _postfachPlz;
    
    @Element(name = "postfachOrt", required = false)
    private String _postfachOrt;

    @Element(name = "boolVermittelbar", required = false)    
    private boolean _vermittelbar = true;

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

    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Status.NORMAL;

    public VersichererObj() {
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

    public String getGesellschaftsNr() {
        return _gesellschaftsNr;
    }

    public void setGesellschaftsNr(String _gesellschaftsNr) {
        this._gesellschaftsNr = _gesellschaftsNr;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKuerzel() {
        return _kuerzel;
    }

    public void setKuerzel(String _kuerzel) {
        this._kuerzel = _kuerzel;
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

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public String getParentName() {
        return _parentName;
    }

    public void setParentName(String _parentName) {
        this._parentName = _parentName;
    }

    public String getPlz() {
        return _plz;
    }

    public void setPlz(String _plz) {
        this._plz = _plz;
    }

    public boolean isPostfach() {
        return _postfach;
    }

    public void setPostfach(boolean _postfach) {
        this._postfach = _postfach;
    }

    public String getPostfachName() {
        return _postfachName;
    }

    public void setPostfachName(String _postfachName) {
        this._postfachName = _postfachName;
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

    public boolean isVermittelbar() {
        return _vermittelbar;
    }

    public void setVermittelbar(boolean _vermittelbar) {
        this._vermittelbar = _vermittelbar;
    }

    public String getVuNummer() {
        return _vuNummer;
    }

    public void setVuNummer(String _vuNummer) {
        this._vuNummer = _vuNummer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatorID() {
        return _creatorID;
    }

    public void setCreatorID(int _creatorID) {
        this._creatorID = _creatorID;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    @Override
    public String toString(){
        int sett = Config.getInt("versichererToString", 0);
        
        if (sett == 0) {
            return this._name + " (" + this._vuNummer + ")";
        } else if (sett == 1) {
            return this._name;
        } else if (sett == 2) {
            return this._vuNummer;
        } else {
            return this._name + " (" + this._vuNummer + ")"; // Fallback
        }

    }

}
