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

package de.maklerpoint.office.Marketing;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author yves
 */
public class NewsletterSubscriberObj {

    private int _id;

    private String _kennung = "-1";
    private int _zaId = -1;
    private int _ansprechpartnerId = -1;    
    
    private String _name;

    private String _email;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int _status = Status.NORMAL;

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKennung() {
        return _kennung;
    }

    public void setKennung(String _kennung) {
        this._kennung = _kennung;
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
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public int getAnsprechpartnerId() {
        return _ansprechpartnerId;
    }

    public void setAnsprechpartnerId(int _ansprechpartnerId) {
        this._ansprechpartnerId = _ansprechpartnerId;
    }

    public int getZaId() {
        return _zaId;
    }

    public void setZaId(int _zaId) {
        this._zaId = _zaId;
    }

    @Override
    public String toString() {
        return _kennung + " " + _name;
    }

}
