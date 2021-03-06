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
package de.maklerpoint.office.Stoerfalle;

import de.maklerpoint.office.Konstanten.Stoerfaelle;
import java.sql.Timestamp;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

@Root(name="Stoerfall")
public class StoerfallObj {
    
    private int _id;
    
    private int creatorId = -1;
    private int mandantenId = -1;
            
    private String kundenNr; // compat    
        
    private int vertragsId = -1; // Pflicht
    
    private int betreuerId = -1;
    
    private String stoerfallNr; // Pflicht
    
    private String grund = "";
    
    private java.sql.Timestamp eingang;
    private java.sql.Timestamp faelligkeit;
    
    private int fristTage;
    
    private int aufgabenId = -1;
    
    private double rueckstand = 0.00;
    
    private String mahnstatus = "";
    private String kategorie = "";      
    
    private boolean positivErledigt = false;
    
    private String notiz;
    
    @Element(name = "custom1", required = false)
    private String _custom1;
    @Element(name = "custom2", required = false)    
    private String _custom2;
    @Element(name = "custom3", required = false)    
    private String _custom3;
        
    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Stoerfaelle.STATUS_OFFEN;

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public String getCustom1() {
        return _custom1;
    }

    public void setCustom1(String _custom1) {
        this._custom1 = _custom1;
    }

    public String getCustom2() {
        return _custom2;
    }

    public void setCustom2(String _custom2) {
        this._custom2 = _custom2;
    }

    public String getCustom3() {
        return _custom3;
    }

    public void setCustom3(String _custom3) {
        this._custom3 = _custom3;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getAufgabenId() {
        return aufgabenId;
    }

    public void setAufgabenId(int aufgabenId) {
        this.aufgabenId = aufgabenId;
    }

    public int getBetreuerId() {
        return betreuerId;
    }

    public void setBetreuerId(int betreuerId) {
        this.betreuerId = betreuerId;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getEingang() {
        return eingang;
    }

    public void setEingang(Timestamp eingang) {
        this.eingang = eingang;
    }

    public Timestamp getFaelligkeit() {
        return faelligkeit;
    }

    public void setFaelligkeit(Timestamp faelligkeit) {
        this.faelligkeit = faelligkeit;
    }

    public int getFristTage() {
        return fristTage;
    }

    public void setFristTage(int fristTage) {
        this.fristTage = fristTage;
    }

    public String getGrund() {
        return grund;
    }

    public void setGrund(String grund) {
        this.grund = grund;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }

    public String getMahnstatus() {
        return mahnstatus;
    }

    public void setMahnstatus(String mahnstatus) {
        this.mahnstatus = mahnstatus;
    }

    public int getMandantenId() {
        return mandantenId;
    }

    public void setMandantenId(int mandantenId) {
        this.mandantenId = mandantenId;
    }

    public double getRueckstand() {
        return rueckstand;
    }

    public void setRueckstand(double rueckstand) {
        this.rueckstand = rueckstand;
    }

    public int getStatus() {
        return status;
    }

    public boolean isPositivErledigt() {
        return positivErledigt;
    }

    public void setPositivErledigt(boolean positivErledigt) {
        this.positivErledigt = positivErledigt;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStoerfallNr() {
        return stoerfallNr;
    }

    public void setStoerfallNr(String stoerfallNr) {
        this.stoerfallNr = stoerfallNr;
    }

    public int getVertragsId() {
        return vertragsId;
    }

    public void setVertragsId(int vertragsId) {
        this.vertragsId = vertragsId;
    }

    @Override
    public String toString() {
        // TODO
        return this.getStoerfallNr();
    }
    
    
    
}
