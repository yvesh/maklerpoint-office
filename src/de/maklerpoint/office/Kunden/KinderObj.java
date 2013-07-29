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

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

@Root(name = "kind")
public class KinderObj {

    @Attribute(name = "id")
    private int id;
    
    @Element(name = "kundenId")
    private int _parentId;
    
    @Element(name = "mandantenId", required=false)
    private int _mandantenId = -1;
    
    @Element(name = "name")
    private String _KindName;
    
    @Element(name = "vorname")
    private String _KindVorname;
    
    @Element(name = "geburtsdatum")
    private String _KindGeburtsdatum;
    
    @Element(name = "beruf", required = false)
    private String _KindBeruf;
    
    @Element(name = "wohnort", required = false)
    private String _KindWohnort;
    
    @Element(name = "comments", required = false)
    private String _comments;
    
    @Element(name = "custom", required = false)
    private String _custom;
    
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;
    
    @Element(name = "status", required = false)
    private int _status = Status.NORMAL;

    public KinderObj() {
        super();
    }

    public String getKindBeruf() {
        return _KindBeruf;
    }

    public void setKindBeruf(String _KindBeruf) {
        this._KindBeruf = _KindBeruf;
    }

    public int getMandantenId() {
        return _mandantenId;
    }

    public void setMandantenId(int _mandantenId) {
        this._mandantenId = _mandantenId;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public String getKindGeburtsdatum() {
        return _KindGeburtsdatum;
    }

    public void setKindGeburtsdatum(String _KindGeburtsdatum) {
        this._KindGeburtsdatum = _KindGeburtsdatum;
    }

    public String getKindName() {
        return _KindName;
    }

    public void setKindName(String _KindName) {
        this._KindName = _KindName;
    }

    public String getKindVorname() {
        return _KindVorname;
    }

    public void setKindVorname(String _KindVorname) {
        this._KindVorname = _KindVorname;
    }

    public String getKindWohnort() {
        return _KindWohnort;
    }

    public void setKindWohnort(String _KindWohnort) {
        this._KindWohnort = _KindWohnort;
    }

    public int getParentId() {
        return _parentId;
    }

    public void setParentId(int _parentId) {
        this._parentId = _parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKindStatus() {
        return _status;
    }

    public void setKindStatus(int _KindStatus) {
        this._status = _KindStatus;
    }

    public String getComments() {
        return _comments;
    }

    public void setComments(String _comments) {
        this._comments = _comments;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getCustom() {
        return _custom;
    }

    public void setCustom(String _custom) {
        this._custom = _custom;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KinderObj other = (KinderObj) obj;
        if ((this._KindName == null) ? (other._KindName != null) : !this._KindName.equals(other._KindName)) {
            return false;
        }
        if ((this._KindVorname == null) ? (other._KindVorname != null) : !this._KindVorname.equals(other._KindVorname)) {
            return false;
        }
        if ((this._KindGeburtsdatum == null) ? (other._KindGeburtsdatum != null) : !this._KindGeburtsdatum.equals(other._KindGeburtsdatum)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this._KindName != null ? this._KindName.hashCode() : 0);
        hash = 37 * hash + (this._KindVorname != null ? this._KindVorname.hashCode() : 0);
        hash = 37 * hash + (this._KindGeburtsdatum != null ? this._KindGeburtsdatum.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this._KindVorname + " " + this._KindName;
    }
}
