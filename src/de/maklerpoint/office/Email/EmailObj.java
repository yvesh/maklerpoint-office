/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Email;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class EmailObj {
    
    private int _id;
    private int _creatorId = -1;
    
    private int _mandantenId = -1;
    
    private String _kundenKennung = "-1";
    private int _benutzerId = -1;
    private int _versichererId = -1;
    private int _produktId = -1;
    private int _bpId = -1;
    private int _schadenId = -1;
    private int _stoerId = -1;
    private int _vertragId = -1;
    private int _filetype = -1;
    
    private String _empfaenger;
    private String _absender; // just for compat
    private String _cc;
    
    private String _betreff;
    private String _body;
    private String _nohtml;        
    
    private boolean _send = true;
    
    private java.sql.Timestamp _sendTime;
        
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int _status = Status.NORMAL;

    public Timestamp getSendTime() {
        return _sendTime;
    }

    public void setSendTime(Timestamp _sendTime) {
        this._sendTime = _sendTime;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public String getAbsender() {
        return _absender;
    }

    public void setAbsender(String _absender) {
        this._absender = _absender;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public String getBetreff() {
        return _betreff;
    }

    public void setBetreff(String _betreff) {
        this._betreff = _betreff;
    }

    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public int getBpId() {
        return _bpId;
    }

    public void setBpId(int _bpId) {
        this._bpId = _bpId;
    }

    public String getCc() {
        return _cc;
    }

    public void setCc(String _cc) {
        this._cc = _cc;
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

    public String getEmpfaenger() {
        return _empfaenger;
    }

    public void setEmpfaenger(String _empfaenger) {
        this._empfaenger = _empfaenger;
    }

    public int getFiletype() {
        return _filetype;
    }

    public void setFiletype(int _filetype) {
        this._filetype = _filetype;
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

    public String getNohtml() {
        return _nohtml;
    }

    public void setNohtml(String _nohtml) {
        this._nohtml = _nohtml;
    }

    public int getProduktId() {
        return _produktId;
    }

    public void setProduktId(int _produktId) {
        this._produktId = _produktId;
    }

    public int getSchadenId() {
        return _schadenId;
    }

    public void setSchadenId(int _schadenId) {
        this._schadenId = _schadenId;
    }

    public boolean isSend() {
        return _send;
    }

    public void setSend(boolean _send) {
        this._send = _send;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public int getStoerId() {
        return _stoerId;
    }

    public void setStoerId(int _stoerId) {
        this._stoerId = _stoerId;
    }

    public int getVersichererId() {
        return _versichererId;
    }

    public void setVersichererId(int _versichererId) {
        this._versichererId = _versichererId;
    }

    public int getVertragId() {
        return _vertragId;
    }

    public void setVertragId(int _vertragId) {
        this._vertragId = _vertragId;
    }
    
    
    
}
