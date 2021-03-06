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

package de.maklerpoint.office.Kalender.Aufgaben;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class AufgabenObj {
    private int _id;
    private int _creatorId = -1;

    private boolean _pub = false;

    private String _beschreibung;

    private String _tag = "Standard";
    private String _kundenKennung = "-1";
    
    private int _versId = -1;
    private int _vertragId = -1;
    private int _stoerfallId = -1;
    private int _schadenId = -1;
    private int _benutzerId = -1;       

    private java.sql.Timestamp _start;
    private java.sql.Timestamp _ende;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    public AufgabenObj(){
        super();
    }

    public AufgabenObj(int id) {
        super();
        this._id = id;
    }

    public String getBeschreibung() {
        return _beschreibung;
    }

    public void setBeschreibung(String _beschreibung) {
        this._beschreibung = _beschreibung;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public java.sql.Timestamp getEnde() {
        return _ende;
    }

    public void setEnde(java.sql.Timestamp _ende) {
        this._ende = _ende;
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

    public boolean isPublic() {
        return _pub;
    }

    public void setPublic(boolean _public) {
        this._pub = _public;
    }

    public java.sql.Timestamp getStart() {
        return _start;
    }

    public void setStart(java.sql.Timestamp _start) {
        this._start = _start;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
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

    public int getVersId() {
        return _versId;
    }

    public void setVersId(int _versId) {
        this._versId = _versId;
    }

    public int getVertragId() {
        return _vertragId;
    }

    public void setVertragId(int _vertragId) {
        this._vertragId = _vertragId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this._creatorId;
        hash = 41 * hash + (this._beschreibung != null ? this._beschreibung.hashCode() : 0);
        hash = 41 * hash + (this._start != null ? this._start.hashCode() : 0);
        hash = 41 * hash + (this._ende != null ? this._ende.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return  _beschreibung;
    }




}
