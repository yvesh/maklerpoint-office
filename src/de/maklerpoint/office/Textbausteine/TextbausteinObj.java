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

package de.maklerpoint.office.Textbausteine;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author yves
 */
public class TextbausteinObj {

    private int _id;
    private int _group;
    private int _benutzerId = -1; // System
    private int _prodId = -1; // Für Grp 3
        
    private String _name;
    private String _beschreibung;
    
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int status = Status.NORMAL;

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

    public int getProdId() {
        return _prodId;
    }

    public void setProdId(int _prodId) {
        this._prodId = _prodId;
    }

    public int getGroup() {
        return _group;
    }

    public void setGroup(int _group) {
        this._group = _group;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return _name;
    }

}