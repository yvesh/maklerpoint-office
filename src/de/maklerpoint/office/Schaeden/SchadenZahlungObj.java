/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 18:44:15
 *  File:       KontakteSQLMethods
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
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
public class SchadenZahlungObj {
    
    private int id;
    
    private int creatorId = -1;
    private int mandantenId = -1;
    
    private int schadenId = -1; // IMPORTANT
    private int schadenForderungId = -1;
    
    private java.sql.Timestamp belegVon;
    
    private String beguenstigt = "";
    private String forderungsArt = "";
    
    private double zahlung = 0.00;
            
    private String zahltext = "";
    private String zahlungvon = "";
    
    private String comments;
    
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Schaeden.STATUS_FORDERUNG_OFFEN;

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

    public int getSchadenForderungId() {
        return schadenForderungId;
    }

    public void setSchadenForderungId(int schadenForderungId) {
        this.schadenForderungId = schadenForderungId;
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

    public String getBeguenstigt() {
        return beguenstigt;
    }

    public void setBeguenstigt(String beguenstigt) {
        this.beguenstigt = beguenstigt;
    }

    public Timestamp getBelegVon() {
        return belegVon;
    }

    public void setBelegVon(Timestamp belegVon) {
        this.belegVon = belegVon;
    }

    public String getForderungsArt() {
        return forderungsArt;
    }

    public void setForderungsArt(String forderungsArt) {
        this.forderungsArt = forderungsArt;
    }

    public double getZahlung() {
        return zahlung;
    }

    public void setZahlung(double zahlung) {
        this.zahlung = zahlung;
    }
        
}