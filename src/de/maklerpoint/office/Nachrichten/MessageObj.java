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
package de.maklerpoint.office.Nachrichten;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class MessageObj {
    
    private int _id;
    private int _mandantenId = -1;
         
    private int _senderId;
    private int _empfaengerId;
    
    private String _md5sum;
     
    private String _betreff;
    private String _context;
    
    private String _tag;    
    private boolean _read = false; // Empfänger

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int _status = Status.NORMAL;   
    

    public String getBetreff() {
        return _betreff;
    }

    public void setBetreff(String _betreff) {
        this._betreff = _betreff;
    }

    public String getContext() {
        return _context;
    }

    public void setContext(String _context) {
        this._context = _context;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public int getEmpfaengerId() {
        return _empfaengerId;
    }

    public void setEmpfaengerId(int _empfaenger) {
        this._empfaengerId = _empfaenger;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getMd5sum() {
        return _md5sum;
    }

    public void setMd5sum(String _md5sum) {
        this._md5sum = _md5sum;
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

    public boolean isRead() {
        return _read;
    }

    public void setRead(boolean _read) {
        this._read = _read;
    }

    public int getSenderId() {
        return _senderId;
    }

    public void setSender(int _sender) {
        this._senderId = _sender;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    
}
