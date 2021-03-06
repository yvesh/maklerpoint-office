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

package de.maklerpoint.office.Backup;

import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BackupObj {

    private int _id;
    private String _path;
    private int _type;
    private java.sql.Timestamp _created;
    private boolean _automatic = true;
    private int _benutzerId = -1;
    private boolean _success = false;
    private boolean _fileAvailable = false;
    private int _backupSize = -1;
    private int _filetype = 0;

    public BackupObj() {
        super();
    }

    public BackupObj(int id) {
        super();
        this._id = id;
    }

    public boolean isAutomatic() {
        return _automatic;
    }

    public void setAutomatic(boolean _automatic) {
        this._automatic = _automatic;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public boolean isFileAvailable() {
        return _fileAvailable;
    }

    public void setFileAvailable(boolean _fileAvailable) {
        this._fileAvailable = _fileAvailable;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }

    public boolean isSuccess() {
        return _success;
    }

    public void setSuccess(boolean _success) {
        this._success = _success;
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    public int getBackupSize() {
        return _backupSize;
    }

    public void setBackupSize(int _backupSize) {
        this._backupSize = _backupSize;
    }

    public int getFiletype() {
        return _filetype;
    }

    public void setFiletype(int _filetype) {
        this._filetype = _filetype;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BackupObj other = (BackupObj) obj;
        if ((this._path == null) ? (other._path != null) : !this._path.equals(other._path)) {
            return false;
        }
        if (this._type != other._type) {
            return false;
        }
        if (this._created != other._created && (this._created == null || !this._created.equals(other._created))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this._path != null ? this._path.hashCode() : 0);
        hash = 17 * hash + this._type;
        hash = 17 * hash + (this._created != null ? this._created.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return _path;
    }

}