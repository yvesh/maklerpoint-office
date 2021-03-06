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
package de.maklerpoint.office.Schaeden;

import de.maklerpoint.office.Konstanten.Schaeden;
import java.sql.Timestamp;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

@Root(name = "schadenforderung")
public class SchadenForderungObj {
    
    private int id;
    
    private int creatorId = -1;
    private int mandantenId = -1;
    
    private int schadenId = -1; // IMPORTANT
    
    private java.sql.Timestamp belegVon;
    
    private String anspruchSteller = "";
    private String anspruchArt = "";
    
    private double gesamtforderung = 0.00;
    
    private double selbstbeteiligung = 0.00;
        
    private String zahltext = "";
    private String zahlungvon = "";
    
    private String comments;
    
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Schaeden.STATUS_FORDERUNG_OFFEN;

    public Timestamp getBelegVon() {
        return belegVon;
    }

    public void setBelegVon(Timestamp belegVon) {
        this.belegVon = belegVon;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public String getAnspruchArt() {
        return anspruchArt;
    }

    public void setAnspruchArt(String anspruchArt) {
        this.anspruchArt = anspruchArt;
    }

    public String getAnspruchSteller() {
        return anspruchSteller;
    }

    public void setAnspruchSteller(String anspruchSteller) {
        this.anspruchSteller = anspruchSteller;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public double getEffektiveforderung() {
        return this.gesamtforderung - this.selbstbeteiligung;
    } 

    public double getGesamtforderung() {
        return gesamtforderung;
    }

    public void setGesamtforderung(double gesamtforderung) {
        this.gesamtforderung = gesamtforderung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMandantenId() {
        return mandantenId;
    }

    public void setMandantenId(int mandantenId) {
        this.mandantenId = mandantenId;
    }

    public int getSchadenId() {
        return schadenId;
    }

    public void setSchadenId(int schadenId) {
        this.schadenId = schadenId;
    }

    public double getSelbstbeteiligung() {
        return selbstbeteiligung;
    }

    public void setSelbstbeteiligung(double selbstbeteiligung) {
        this.selbstbeteiligung = selbstbeteiligung;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getZahltext() {
        return zahltext;
    }

    public void setZahltext(String zahltext) {
        this.zahltext = zahltext;
    }

    public String getZahlungvon() {
        return zahlungvon;
    }

    public void setZahlungvon(String zahlungvon) {
        this.zahlungvon = zahlungvon;
    }
    
    
}
