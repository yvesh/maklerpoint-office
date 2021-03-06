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

package de.maklerpoint.office.Versicherer.Produkte;

import de.maklerpoint.office.Konstanten.Produkte;
import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;

/**
 *
 * @author yves
 */
public class ProduktHaftungObj {

    private int _id;
    private int _produkt = -1; // Nie leer

    private int _haftungZeit = Produkte.HAFTUNGTYP_FIX;
    private int _haftungMonate = 0;

    private String _haftungFormel = null; // Inaktiv
    
    private int _haftungsart = Produkte.HAFTUNGART_VOLL;

    private int _ratierlich = Produkte.HAFTUNGSART_RATIERLICH_TAGGENAU;
    private int _ratierlichIntervalle = 1;
    private int _ratierlichTabelle = 0; // Inaktiv

    private int _kombiniert = Produkte.HAFTUNGSART_KOMBINIERT_FEST;
    private int _kombiniertVollhaftungsZeit = 0;
    
    private int _kombiniertRatierlich = Produkte.HAFTUNGSART_KOMBINIERT_RATIERLICH_RESTHAFTUNGSZEIT;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getHaftungFormel() {
        return _haftungFormel;
    }

    public void setHaftungFormel(String _haftungFormel) {
        this._haftungFormel = _haftungFormel;
    }

    public int getHaftungMonate() {
        return _haftungMonate;
    }

    public void setHaftungMonate(int _haftungMonate) {
        this._haftungMonate = _haftungMonate;
    }

    public int getHaftungZeit() {
        return _haftungZeit;
    }

    public void setHaftungZeit(int _haftungZeit) {
        this._haftungZeit = _haftungZeit;
    }

    public int getKombiniertVollhaftungsZeit() {
        return _kombiniertVollhaftungsZeit;
    }

    public void setKombiniertVollhaftungsZeit(int _kombiniertVollhaftungsZeit) {
        this._kombiniertVollhaftungsZeit = _kombiniertVollhaftungsZeit;
    }

    public int getHaftungsart() {
        return _haftungsart;
    }

    public void setHaftungsart(int _haftungsart) {
        this._haftungsart = _haftungsart;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getKombiniert() {
        return _kombiniert;
    }

    public void setKombiniert(int _kombiniert) {
        this._kombiniert = _kombiniert;
    }

    public int getKombiniertRatierlich() {
        return _kombiniertRatierlich;
    }

    public void setKombiniertRatierlich(int _kombiniertRatierlich) {
        this._kombiniertRatierlich = _kombiniertRatierlich;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getProdukt() {
        return _produkt;
    }

    public void setProdukt(int _produkt) {
        this._produkt = _produkt;
    }

    public int getRatierlich() {
        return _ratierlich;
    }

    public void setRatierlich(int _ratierlich) {
        this._ratierlich = _ratierlich;
    }

    public int getRatierlichIntervalle() {
        return _ratierlichIntervalle;
    }

    public void setRatierlichIntervalle(int _ratierlichIntervalle) {
        this._ratierlichIntervalle = _ratierlichIntervalle;
    }

    public int getRatierlichTabelle() {
        return _ratierlichTabelle;
    }

    public void setRatierlichTabelle(int _ratierlichTabelle) {
        this._ratierlichTabelle = _ratierlichTabelle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }        
    
}
