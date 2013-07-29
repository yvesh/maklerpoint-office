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

package de.maklerpoint.office.Notizen;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class NotizenObj {

    private int _id;
    private int _creatorId = -1;

    private boolean _priv = false;

    private String _kundenKennung = "-1";
    private int _versichererKennung = -1;
    private int _benutzerKennung = -1;
    private int _produktId = -1;
    private int _vertragId = -1;
    
    private int _stoerfallId = -1;
    private int _schadenId = -1;

    private String _betreff;
    private String _text;

    private String _tag = "Standard";

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    public NotizenObj() {
    }

    public int getBenutzerKennung() {
        return _benutzerKennung;
    }

    public void setBenutzerKennung(int _benutzerKennung) {
        this._benutzerKennung = _benutzerKennung;
    }

    public String getBetreff() {
        return _betreff;
    }

    public void setBetreff(String _betreff) {
        this._betreff = _betreff;
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

    public boolean isPriv() {
        return _priv;
    }

    public int getSchadenId() {
        return _schadenId;
    }

    public void setSchadenId(int _schadenId) {
        this._schadenId = _schadenId;
    }

    public int getStoerfallId() {
        return _stoerfallId;
    }

    public void setStoerfallId(int _stoerfallid) {
        this._stoerfallId = _stoerfallid;
    }

    public void setPriv(boolean _priv) {
        this._priv = _priv;
    }

    public int getProduktId() {
        return _produktId;
    }

    public void setProduktId(int _produktId) {
        this._produktId = _produktId;
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

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public boolean isPrivate() {
        return _priv;
    }

    public void setPrivate(boolean _private) {
        this._priv = _private;
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String _tag) {
        this._tag = _tag;
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }

    public int getVersichererKennung() {
        return _versichererKennung;
    }

    public void setVersichererKennung(int _versichererKennung) {
        this._versichererKennung = _versichererKennung;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVertragId() {
        return _vertragId;
    }

    public void setVertragId(int _vertragId) {
        this._vertragId = _vertragId;
    }    

    @Override
    public String toString() {
        return _betreff;
    }
}
