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

package de.maklerpoint.office.Sparten;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class SpartenObj {

    private int _id;
    private String _spartenNummer;
    private String _bezeichnung;
    private String _gruppe;
    private int _steuersatz;

    private int status = 0;

    public SpartenObj(){
        
    }

    public String getBezeichnung() {
        return _bezeichnung;
    }

    public void setBezeichnung(String _bezeichnung) {
        this._bezeichnung = _bezeichnung;
    }

    public String getGruppe() {
        return _gruppe;
    }

    public void setGruppe(String _gruppe) {
        this._gruppe = _gruppe;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getSpartenNummer() {
        return _spartenNummer;
    }

    public void setSpartenNummer(String _spartenNummer) {
        this._spartenNummer = _spartenNummer;
    }

    public int getSteuersatz() {
        return _steuersatz;
    }

    public void setSteuersatz(int _steuersatz) {
        this._steuersatz = _steuersatz;
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
