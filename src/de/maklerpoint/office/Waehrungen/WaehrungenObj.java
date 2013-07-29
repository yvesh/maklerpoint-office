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

package de.maklerpoint.office.Waehrungen;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class WaehrungenObj {

    private int _id;
    private int _ordering;

    private String _isocode;
    private String _bezeichnung;

    private boolean _neuanlage = true;

    private int status = 0;

    public String getBezeichnung() {
        return _bezeichnung;
    }

    public void setBezeichnung(String _bezeichnung) {
        this._bezeichnung = _bezeichnung;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getIsocode() {
        return _isocode;
    }

    public void setIsocode(String _isocode) {
        this._isocode = _isocode;
    }

    public boolean isNeuanlage() {
        return _neuanlage;
    }

    public void setNeuanlage(boolean _neuanlage) {
        this._neuanlage = _neuanlage;
    }

    public int getOrdering() {
        return _ordering;
    }

    public void setOrdering(int _ordering) {
        this._ordering = _ordering;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return _bezeichnung;
    }

}
