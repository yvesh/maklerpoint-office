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

package de.maklerpoint.office.Session;

import de.maklerpoint.office.Konstanten.MPointKonstanten;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SessionObj {

    private int _id;
    private int _benutzerid;
    private java.sql.Timestamp _start;
    private java.sql.Timestamp _lastrefresh;
    private String _session_id;
    private int _anwendung = MPointKonstanten.MP_OFFICE_CLIENT;
    private String _build;
    
    private int _status = 0;

    public int getBenutzerid() {
        return _benutzerid;
    }

    public void setBenutzerid(int _benutzerid) {
        this._benutzerid = _benutzerid;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Timestamp getLastrefresh() {
        return _lastrefresh;
    }

    public void setLastrefresh(Timestamp _lastrefresh) {
        this._lastrefresh = _lastrefresh;
    }

    public String getSession_id() {
        return _session_id;
    }

    public void setSession_id(String _session_id) {
        this._session_id = _session_id;
    }

    public Timestamp getStart() {
        return _start;
    }

    public void setStart(Timestamp _start) {
        this._start = _start;
    }

    public int getAnwendung() {
        return _anwendung;
    }

    public void setAnwendung(int _anwendung) {
        this._anwendung = _anwendung;
    }

    public String getBuild() {
        return _build;
    }

    public void setBuild(String _build) {
        this._build = _build;
    }
    
    public int getStatus() {
        return _status;
    }
    
    public void setStatus(int _status) {
        this._status = _status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SessionObj other = (SessionObj) obj;
        if (this._benutzerid != other._benutzerid) {
            return false;
        }
        if ((this._session_id == null) ? (other._session_id != null) : !this._session_id.equals(other._session_id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this._benutzerid;
        hash = 79 * hash + (this._session_id != null ? this._session_id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return _session_id;
    }

}