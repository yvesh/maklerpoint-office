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
package de.maklerpoint.office.Briefe;

import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class BriefObj {
    
    private int _id;
    
    private int _type = Briefe.TYPE_BRIEF;
    
    private int _categoryId = 1;      
    
    private int _creatorId = -1;
    private int _benutzerId = -1;
        
    private boolean _loeschbar = true;
    
    private boolean _privatKunde = false;
    private boolean _geschKunde = false;
    private boolean _versicherer = false;
    private boolean _produkt = false;
    private boolean _benutzer = false;
    private boolean _stoerfall = false;
    private boolean _vertrag = false;    
    
    private String _filename;
    private String _fullpath; // tmplpath + rest
    
    private String _checksum;
    
    private String _name;
    private String _beschreibung;
    
    private String _tag = "Standard";
    
    private String _comments;
    
    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;
    
    private int _status = Status.NORMAL;

    public BriefObj(){        
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }        
    
    public boolean isBenutzer() {
        return _benutzer;
    }

    public void setBenutzer(boolean _benutzer) {
        this._benutzer = _benutzer;
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

    public int getCategoryId() {
        return _categoryId;
    }

    public void setCategoryId(int _categoryId) {
        this._categoryId = _categoryId;
    }

    public String getChecksum() {
        return _checksum;
    }

    public void setChecksum(String _checksum) {
        this._checksum = _checksum;
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

    public int getCreatorId() {
        return _creatorId;
    }

    public void setCreatorId(int _creatorId) {
        this._creatorId = _creatorId;
    }

    public String getFilename() {
        return _filename;
    }

    public void setFilename(String _filename) {
        this._filename = _filename;
    }

    public String getFullpath() {
        return _fullpath;
    }

    public void setFullpath(String _fullpath) {
        this._fullpath = _fullpath;
    }

    public boolean isGeschKunde() {
        return _geschKunde;
    }

    public void setGeschKunde(boolean _geschKunde) {
        this._geschKunde = _geschKunde;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public boolean isLoeschbar() {
        return _loeschbar;
    }

    public void setLoeschbar(boolean _loeschbar) {
        this._loeschbar = _loeschbar;
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

    public boolean isPrivatKunde() {
        return _privatKunde;
    }

    public void setPrivatKunde(boolean _privatKunde) {
        this._privatKunde = _privatKunde;
    }

    public boolean isProdukt() {
        return _produkt;
    }

    public void setProdukt(boolean _produkt) {
        this._produkt = _produkt;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public boolean isStoerfall() {
        return _stoerfall;
    }

    public void setStoerfall(boolean _stoerfall) {
        this._stoerfall = _stoerfall;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public boolean isVersicherer() {
        return _versicherer;
    }

    public void setVersicherer(boolean _versicherer) {
        this._versicherer = _versicherer;
    }

    public boolean isVertrag() {
        return _vertrag;
    }

    public void setVertrag(boolean _vertrag) {
        this._vertrag = _vertrag;
    }

    @Override
    public String toString() {
        return this._name;
    }
    
    
}
