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

import java.io.File;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class WissenDokumentenObj {

    private int _id;
    private int _creator = -1; // -1 = System

    private int _benutzerId = -1;
    private int _filetype = -1;

    private String _category = "Unbekannt";

    private String _name;
    private String _fileName;
    private String _fullPath;

    private String _label;
    private String _beschreibung;
    private String _checksum;

    private String _tag = "Standard";

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private Class triggerClass = null;

    private int _status;

    public WissenDokumentenObj() {
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public String getFileName() {
        return _fileName;
    }

    public void setFileName(String _fileName) {
        this._fileName = _fileName;
    }

    public String getBeschreibung() {
        return _beschreibung;
    }

    public void setBeschreibung(String _beschreibung) {
        this._beschreibung = _beschreibung;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String _category) {
        this._category = _category;
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

    public int getCreator() {
        return _creator;
    }

    public void setCreator(int _creator) {
        this._creator = _creator;
    }

    public int getFiletype() {
        return _filetype;
    }

    public void setFiletype(int _filetype) {
        this._filetype = _filetype;
    }

    public String getFullPath() {        
        return _fullPath.replaceAll("/", File.separator);
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

    public String getLabel() {
        return _label;
    }

    public void setLabel(String _label) {
        this._label = _label;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public Class getTriggerClass() {
        return triggerClass;
    }

    public void setTriggerClass(Class triggerClass) {
        this.triggerClass = triggerClass;
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

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    @Override
    public String toString() {
        return _name;
    }

}
