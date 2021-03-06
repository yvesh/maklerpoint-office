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
package de.maklerpoint.office.Dokumente;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DokumentenObj {

    private int _id;
    private int _creatorId = -1;
    private String _kundenKennung = "-1";
    private int _benutzerId = -1;
    private int _versichererId = -1;
    private int _produktId = -1;
    private int _bpId = -1;
    private int _stoerId = -1;
    private int _schadenId = -1;
    private int _vertragId = -1;
    private int _filetype = -1;
    private String _name; // Filename
    private String _fullPath;
    private String _label;
    private String _beschreibung; // Beschreibung Name in der DB
    private String _checksum;
    private String _tag = "Standard";
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    private java.sql.Timestamp _lastviewed;
    private int _viewcount = 1;
    private int _status = Status.NORMAL;

    public DokumentenObj() {
    }

    public DokumentenObj(String _name, String _fullPath) {
        this._name = _name;
        this._fullPath = _fullPath;
    }

    public int getSchadenId() {
        return _schadenId;
    }

    public void setSchadenId(int _schadenId) {
        this._schadenId = _schadenId;
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

    public String getChecksum() {
        return _checksum;
    }

    public void setChecksum(String _checksum) {
        this._checksum = _checksum;
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

    public int getFiletype() {
        return _filetype;
    }

    public void setFiletype(int _filetype) {
        this._filetype = _filetype;
    }

    public String getFullPath() {
        return _fullPath;
    }

    public void setFullPath(String _fullPath) {
        this._fullPath = _fullPath;
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

    public String getLabel() {
        return _label;
    }

    public void setLabel(String _label) {
        this._label = _label;
    }

    public Timestamp getLastviewed() {
        return _lastviewed;
    }

    public void setLastviewed(Timestamp _lastviewed) {
        this._lastviewed = _lastviewed;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getBpId() {
        return _bpId;
    }

    public void setBpId(int _bpId) {
        this._bpId = _bpId;
    }

    public int getProduktId() {
        return _produktId;
    }

    public void setProduktId(int _produktId) {
        this._produktId = _produktId;
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

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public int getViewcount() {
        return _viewcount;
    }

    public void setViewcount(int _viewcount) {
        this._viewcount = _viewcount;
    }

    @Override
    public String toString() {
        return _name;
    }
}