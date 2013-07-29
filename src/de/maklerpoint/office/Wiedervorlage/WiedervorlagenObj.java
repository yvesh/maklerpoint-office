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

package de.maklerpoint.office.Wiedervorlage;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class WiedervorlagenObj {

    private int _id;
    private int _benutzerId;    
    private int _kundenId;

    private int _type = WiederVorlagenTypen.UNKNOWN;

    private boolean _public = false;
    
    private String _beschreibung;
    private String _tag = "Standard";
    private String _params;
    
    private java.sql.Timestamp _erinnerung;

    private Date _date;
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _lastmodified;

    private int status = 0;

    public WiedervorlagenObj() {        
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
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

    public Date getDate() {
        return _date;
    }

    public void setDate(Date _date) {
        this._date = _date;
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

    public int getKundenId() {
        return _kundenId;
    }

    public void setKundenId(int _kundenId) {
        this._kundenId = _kundenId;
    }

    public Timestamp getLastmodified() {
        return _lastmodified;
    }

    public void setLastmodified(Timestamp _lastmodified) {
        this._lastmodified = _lastmodified;
    }

    public String getParams() {
        return _params;
    }

    public void setParams(String _params) {
        this._params = _params;
    }

    public boolean isPublic() {
        return _public;
    }

    public void setPublic(boolean _public) {
        this._public = _public;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return _beschreibung;
    }



}
