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

package de.maklerpoint.office.Kalender.Termine;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
@Root(name = "termin")
public class TerminObj {
    
    @Attribute(name = "id")
    private int _id;
    
    @Element(name = "mandantenId", required = false)
    private int _mandantenId = -1;
    
    @Element(name = "creatorId", required = false)
    private int _creatorId = -1;

    @Element(name = "boolOeffentlich", required = false)
    private boolean _pub = false;

    @Element(name = "beschreibung", required = false)
    private String _beschreibung;
    
    @Element(name = "ort", required = false)
    private String _ort;

    @Element(name = "markierung", required = false)
    private String _tag = "Standard";
    
    @Element(name = "kundenNr", required = false)
    private String _kundeKennung = "-1";
    
    @Element(name = "versichererId", required = false)
    private int _versichererId = -1;
    
    @Element(name = "vertragId", required = false)
    private int _vertragId = -1;
    
    @Element(name = "benutzerId", required = false)
    private int _benutzerId = -1;
    
    @Element(name = "stoerfallId", required = false)
    private int _stoerfallId = -1;
    
    @Element(name = "schadenId", required = false)
    private int _schadenId = -1;

    @Element(name = "teilnehmer", required = false)
    private String _teilnehmer;

    @Element(name = "erinnerungDatum", required = false)
    private java.sql.Timestamp _erinnerung;

    @Element(name = "startDatum", required = false)
    private java.sql.Timestamp _start;
    
    @Element(name = "endeDatum", required = false)
    private java.sql.Timestamp _ende;
    
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Status.NORMAL; // TODO ???
    

    public TerminObj(){
        super();
    }

    public TerminObj(int id){
        super();
        this._id = id;
    }

    public String getBeschreibung() {
        return _beschreibung;
    }

    public void setBeschreibung(String _beschreibung) {
        this._beschreibung = _beschreibung;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public boolean isPub() {
        return _pub;
    }

    public void setPub(boolean _pub) {
        this._pub = _pub;
    }

    public int getBesitzer() {
        return _creatorId;
    }

    public void setBesitzer(int _besitzer) {
        this._creatorId = _besitzer;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public int getCreatorId() {
        return _creatorId;
    }

    public void setCreatorId(int _creatorId) {
        this._creatorId = _creatorId;
    }

    public int getSchadenId() {
        return _schadenId;
    }

    public void setSchadenId(int _schadenId) {
        this._schadenId = _schadenId;
    }

    public int getStoerfallId() {
        return _stoerfallId;
    }

    public void setStoerfallId(int _stoerfallId) {
        this._stoerfallId = _stoerfallId;
    }

    public int getVertragId() {
        return _vertragId;
    }

    public void setVertragId(int _vertragId) {
        this._vertragId = _vertragId;
    }

    public Timestamp getEnde() {
        return _ende;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public void setEnde(Timestamp _ende) {
        this._ende = _ende;
    }

    public Timestamp getErinnerung() {
        return _erinnerung;
    }

    public void setErinnerung(Timestamp _erinnerung) {
        this._erinnerung = _erinnerung;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKundeKennung() {
        return _kundeKennung;
    }

    public void setKundeKennung(String kenn) {
        this._kundeKennung = kenn;
    }

    public Timestamp getLastmodified() {
        return _modified;
    }

    public void setLastmodified(Timestamp _lastmodified) {
        this._modified = _lastmodified;
    }

    public String getOrt() {
        return _ort;
    }

    public void setOrt(String _ort) {
        this._ort = _ort;
    }

    public boolean isPublic() {
        return _pub;
    }

    public void setPublic(boolean _public) {
        this._pub = _public;
    }

    public Timestamp getStart() {
        return _start;
    }

    public void setStart(Timestamp _start) {
        this._start = _start;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public String getTeilnehmer() {
        return _teilnehmer;
    }

    public void setTeilnehmer(String _teilnehmer) {
        this._teilnehmer = _teilnehmer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
   
    @Override
    public String toString() {
        return _beschreibung; // TODO Customize ?
    }

}
