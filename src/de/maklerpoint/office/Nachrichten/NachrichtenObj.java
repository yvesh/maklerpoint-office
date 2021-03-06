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

package de.maklerpoint.office.Nachrichten;

import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class NachrichtenObj {

    private int _id;
    private int _mandantenId = -1;
    private int _benutzerId;

    private String _betreff;
    private String _context;

    private String _tag;
    private boolean _read = false;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int _status = 0;

    public NachrichtenObj() {        
    }

    public NachrichtenObj(int _id) {
        this._id = _id;
    }

    public NachrichtenObj(int _benutzerId, String _betreff, String _context, String _tag) {
        this._benutzerId = _benutzerId;
        this._betreff = _betreff;
        this._context = _context;
        this._tag = _tag;
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

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NachrichtenObj other = (NachrichtenObj) obj;
        if ((this._betreff == null) ? (other._betreff != null) : !this._betreff.equals(other._betreff)) {
            return false;
        }
        if ((this._context == null) ? (other._context != null) : !this._context.equals(other._context)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this._betreff != null ? this._betreff.hashCode() : 0);
        hash = 41 * hash + (this._context != null ? this._context.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return _betreff;
    }

}