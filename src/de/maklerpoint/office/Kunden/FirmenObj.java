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
import java.sql.Date;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
@Root(name = "geschaeftskunde")
public class FirmenObj {

    @Attribute(name = "id")
    private int _id;
        
    @Element(name = "mandantenId", required = false)
    private int _mandantenId = -1;
    
    @Element(name = "creatorId", required = false)
    private int _creator;
    
    @Element(name = "parentId", required = false)
    private int _parentFirma = -1;
    
    @Element(name = "betreuerId", required = false)
    private int _betreuerId;
    
    @Element(name = "type", required = false)
    private int _type;
    
    @Element(name = "kundenNr")
    private String _kundenNr;
    
    @Element(name = "name")
    private String _firmenName;
    
    @Element(name = "nameZusatz", required = false)
    private String _firmenNameZusatz;
    
    @Element(name = "nameZusatz2", required = false)
    private String _firmenNameZusatz2;
    
    @Element(name = "strasse", required = false)
    private String _firmenStrasse;
    
    @Element(name = "plz", required = false)
    private String _firmenPLZ;
    
    @Element(name = "ort", required = false)
    private String _firmenStadt;
    
    @Element(name = "bundesland", required = false)
    private String _firmenBundesland;
    
    @Element(name = "land", required = false)
    private String _firmenLand;
    
    @Element(name = "firmentyp", required = false)
    private String _firmenTyp;
    
    @Element(name = "anzahlMitarbeiter", required = false)
    private String _firmenSize;
    
    @Element(name = "firmenSitz", required = false)    
    private String _firmenSitz;
    
    @Element(name = "boolPostfach", required = false)
    private boolean _firmenPostfach = false;
    
    @Element(name = "postfach", required = false)
    private String _firmenPostfachName;
    
    @Element(name = "postfachPlz", required = false)
    private String _firmenPostfachPlz;
    
    @Element(name = "postfachOrt", required = false)
    private String _firmenPostfachOrt;
    
    @Element(name = "rechtsform", required = false)
    private String _firmenRechtsform;
    
    @Element(name = "umsatz", required = false)
    private String _firmenEinkommen;
    
    @Element(name = "branche", required = false)
    private String _firmenBranche;
    
    @Element(name = "gruendung", required = false)
    private Date _firmenGruendungDatum;
    
    @Element(name = "geschaeftsfuehrer", required = false)
    private String _firmenGeschaeftsfuehrer;
    
    private String[] _firmenProKura;
    private String[] _firmenStandorte;
    
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
    
    @Element(name = "defaultKonto", required = false)
    private int _defaultKonto = -1;
    
    @Element(name = "defaultAnsprechpartner", required = false)
    private int _defaultAnsprechpartner = -1;
    
    @Element(name = "werberKennung", required = false)
    private String _werber = "Unbekannt";
    
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

    public FirmenObj() {
    }

    public String getFirmenName() {
        return _firmenName;
    }

    public void setFirmenName(String _firmenName) {
        this._firmenName = _firmenName;
    }

    public String getFirmenNameZusatz() {
        return _firmenNameZusatz;
    }

    public void setFirmenNameZusatz(String _firmenNameZusatz) {
        this._firmenNameZusatz = _firmenNameZusatz;
    }

    public String getFirmenNameZusatz2() {
        return _firmenNameZusatz2;
    }

    public void setFirmenNameZusatz2(String _firmenNameZusatz2) {
        this._firmenNameZusatz2 = _firmenNameZusatz2;
    }

    public boolean getFirmenPostfach() {
        return _firmenPostfach;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public int getDefaultKonto() {
        return _defaultKonto;
    }

    public void setDefaultKonto(int _defaultKonto) {
        this._defaultKonto = _defaultKonto;
    }

    public int getDefaultAnsprechpartner() {
        return _defaultAnsprechpartner;
    }

    public void setDefaultAnsprechpartner(int _defaultAnsprechpartner) {
        this._defaultAnsprechpartner = _defaultAnsprechpartner;
    }

    public void setFirmenPostfach(Boolean _firmenPostfach) {
        this._firmenPostfach = _firmenPostfach;
    }

    public int getBetreuer() {
        return _betreuerId;
    }

    public void setBetreuer(int _betreuer) {
        this._betreuerId = _betreuer;
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

    public boolean isFirmenPostfach() {
        return _firmenPostfach;
    }

    public void setFirmenPostfach(boolean _firmenPostfach) {
        this._firmenPostfach = _firmenPostfach;
    }

    public String getFirmenPostfachName() {
        return _firmenPostfachName;
    }

    public void setFirmenPostfachName(String _firmenPostfachName) {
        this._firmenPostfachName = _firmenPostfachName;
    }

    public String getFirmenPostfachOrt() {
        return _firmenPostfachOrt;
    }

    public void setFirmenPostfachOrt(String _firmenPostfachOrt) {
        this._firmenPostfachOrt = _firmenPostfachOrt;
    }

    public String getFirmenPostfachPlz() {
        return _firmenPostfachPlz;
    }

    public void setFirmenPostfachPlz(String _firmenPostfachPlz) {
        this._firmenPostfachPlz = _firmenPostfachPlz;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getParentFirma() {
        return _parentFirma;
    }

    public void setParentFirma(int _parentFirma) {
        this._parentFirma = _parentFirma;
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

    public String getFirmenBundesland() {
        return _firmenBundesland;
    }

    public void setFirmenBundesland(String _firmenBundesland) {
        this._firmenBundesland = _firmenBundesland;
    }

    public String getFirmenLand() {
        return _firmenLand;
    }

    public void setFirmenLand(String _firmenLand) {
        this._firmenLand = _firmenLand;
    }

    public String getFirmenPLZ() {
        return _firmenPLZ;
    }

    public void setFirmenPLZ(String _firmenPLZ) {
        this._firmenPLZ = _firmenPLZ;
    }

    public String getFirmenSitz() {
        return _firmenSitz;
    }

    public void setFirmenSitz(String _firmenSitz) {
        this._firmenSitz = _firmenSitz;
    }

    public String getFirmenSize() {
        return _firmenSize;
    }

    public void setFirmenSize(String _firmenSize) {
        this._firmenSize = _firmenSize;
    }

    public String getFirmenStadt() {
        return _firmenStadt;
    }

    public void setFirmenStadt(String _firmenStadt) {
        this._firmenStadt = _firmenStadt;
    }

    public String getFirmenStrasse() {
        return _firmenStrasse;
    }

    public void setFirmenStrasse(String _firmenStrasse) {
        this._firmenStrasse = _firmenStrasse;
    }

    public String getFirmenTyp() {
        return _firmenTyp;
    }

    public void setFirmenTyp(String _firmenTyp) {
        this._firmenTyp = _firmenTyp;
    }

    public String getFirmenBranche() {
        return _firmenBranche;
    }

    public void setFirmenBranche(String _firmenBranche) {
        this._firmenBranche = _firmenBranche;
    }

    public String getFirmenEinkommen() {
        return _firmenEinkommen;
    }

    public void setFirmenEinkommen(String _firmenEinkommen) {
        this._firmenEinkommen = _firmenEinkommen;
    }

    public String getFirmenGeschaeftsfuehrer() {
        return _firmenGeschaeftsfuehrer;
    }

    public void setFirmenGeschaeftsfuehrer(String _firmenGeschaeftsfuehrer) {
        this._firmenGeschaeftsfuehrer = _firmenGeschaeftsfuehrer;
    }

    public Date getFirmenGruendungDatum() {
        return _firmenGruendungDatum;
    }

    public void setFirmenGruendungDatum(Date _firmenGruendung) {
        this._firmenGruendungDatum = _firmenGruendung;
    }

    public String[] getFirmenProKura() {
        return _firmenProKura;
    }

    public void setFirmenProKura(String[] _firmenProKura) {
        this._firmenProKura = _firmenProKura;
    }

    public String getFirmenRechtsform() {
        return _firmenRechtsform;
    }

    public void setFirmenRechtsform(String _firmenRechtsform) {
        this._firmenRechtsform = _firmenRechtsform;
    }

    public String[] getFirmenStandorte() {
        return _firmenStandorte;
    }

    public void setFirmenStandorte(String[] _firmenStandorte) {
        this._firmenStandorte = _firmenStandorte;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKundenNr() {
        return _kundenNr;
    }

    public void setKundenNr(String _kundenNr) {
        this._kundenNr = _kundenNr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWerber() {
        return _werber;
    }

    public void setWerber(String _werber) {
        this._werber = _werber;
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FirmenObj other = (FirmenObj) obj;
        if ((this._firmenName == null) ? (other._firmenName != null) : !this._firmenName.equals(other._firmenName)) {
            return false;
        }
        if ((this._firmenStrasse == null) ? (other._firmenStrasse != null) : !this._firmenStrasse.equals(other._firmenStrasse)) {
            return false;
        }
        if ((this._firmenPLZ == null) ? (other._firmenPLZ != null) : !this._firmenPLZ.equals(other._firmenPLZ)) {
            return false;
        }
        if ((this._firmenStadt == null) ? (other._firmenStadt != null) : !this._firmenStadt.equals(other._firmenStadt)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this._firmenName != null ? this._firmenName.hashCode() : 0);
        hash = 29 * hash + (this._firmenStrasse != null ? this._firmenStrasse.hashCode() : 0);
        hash = 29 * hash + (this._firmenPLZ != null ? this._firmenPLZ.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        int sett = Config.getInt("firmenToString", 0);

        if (sett == 0) {
            return this.getFirmenName() + " (" + this.getKundenNr() + ")";
        } else if (sett == 1) {
            if (this.getFirmenNameZusatz() != null) {
                return this.getFirmenName() + " - " + this.getFirmenNameZusatz()
                        + " (" + this.getKundenNr() + ")";
            } else {
                return this.getFirmenName() + " (" + this.getKundenNr() + ")";
            }
        } else if (sett == 2) {
            return this.getFirmenName();
        } else if (sett == 3) {
            return this.getKundenNr();            
        } else if (sett == 4) {
            return this._kundenNr + " " + this._firmenName;
        } else {
            return this.getFirmenName() + " (" + this.getKundenNr() + ")"; // Fallback
        }        
    }
}
