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
import de.maklerpoint.office.Registry.BasicRegistry;
import java.sql.Timestamp;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

@Root(name = "schadenfall")
public class SchadenObj {
    
    private int _id;
    
    private int creatorId = -1;
    private int mandantenId = -1;
    
    private String kundenNr; // compat
    
    private int vertragsId; // Pflicht
    
    private String schadenNr;
    
    private String meldungArt = Schaeden.MELDUNG_ART[0];
    
    private String meldungVon;
    
    private java.sql.Timestamp meldungTime;
    private java.sql.Timestamp schaedenTime;
    
    private boolean schadenPolizei = false;
    
    private String schadenKategorie;
    
    private int schadenBearbeiter = BasicRegistry.currentUser.getId();
    private String schadenOrt;
       
    private String schadenUmfang; // logn
    private String schadenHergang; // long
    
    private java.sql.Timestamp vuWeiterleitungTime;
    
    private String vuMeldungArt = "Telefonisch";
    
    private String risiko;
    private double schadenHoehe = 0.00;
    private int schadenAbrechnungArt = Schaeden.SCHADEN_BRUTTO;
    
    private boolean vuGutachten = false;
    
    private String vuSchadennummer;
    private java.sql.Timestamp vuStatusDatum;
    
    private int wiedervorlagenId; // Mit CHeckbox in der FOrm und dann erstellen
    
    private String interneInfo;
    
    private String notiz;
    
    @Element(name = "custom1", required = false)
    private String _custom1;
    @Element(name = "custom2", required = false)    
    private String _custom2;
    @Element(name = "custom3", required = false)    
    private String _custom3;
    @Element(name = "custom4", required = false)
    private String _custom4;
    @Element(name = "custom5", required = false)
    private String _custom5;

    @Element(name = "erstelltDatum", required = false)
    private java.sql.Timestamp _created;
    
    @Element(name = "modifiziertDatum", required = false)
    private java.sql.Timestamp _modified;

    @Element(name = "status", required = false)
    private int status = Schaeden.STATUS_OFFEN;

    public int getMandantenId() {
        return mandantenId;
    }

    public void setMandantenId(int mandantenId) {
        this.mandantenId = mandantenId;
    }

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

    public String getCustom4() {
        return _custom4;
    }

    public void setCustom4(String _custom4) {
        this._custom4 = _custom4;
    }

    public String getCustom5() {
        return _custom5;
    }

    public void setCustom5(String _custom5) {
        this._custom5 = _custom5;
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

    public String getInterneInfo() {
        return interneInfo;
    }

    public void setInterneInfo(String interneInfo) {
        this.interneInfo = interneInfo;
    }

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }

    public String getMeldungArt() {
        return meldungArt;
    }

    public void setMeldungArt(String meldungArt) {
        this.meldungArt = meldungArt;
    }

    public Timestamp getMeldungTime() {
        return meldungTime;
    }

    public void setMeldungTime(Timestamp meldungTime) {
        this.meldungTime = meldungTime;
    }

    public String getMeldungVon() {
        return meldungVon;
    }

    public void setMeldungVon(String meldungVon) {
        this.meldungVon = meldungVon;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public String getRisiko() {
        return risiko;
    }

    public void setRisiko(String risiko) {
        this.risiko = risiko;
    }

    public int getSchadenAbrechnungArt() {
        return schadenAbrechnungArt;
    }

    public void setSchadenAbrechnungArt(int schadenAbrechnungArt) {
        this.schadenAbrechnungArt = schadenAbrechnungArt;
    }

    public int getSchadenBearbeiter() {
        return schadenBearbeiter;
    }

    public void setSchadenBearbeiter(int schadenBearbeiter) {
        this.schadenBearbeiter = schadenBearbeiter;
    }

    public String getSchadenHergang() {
        return schadenHergang;
    }

    public void setSchadenHergang(String schadenHergang) {
        this.schadenHergang = schadenHergang;
    }

    public double getSchadenHoehe() {
        return schadenHoehe;
    }

    public void setSchadenHoehe(double schadenHoehe) {
        this.schadenHoehe = schadenHoehe;
    }

    public String getSchadenKategorie() {
        return schadenKategorie;
    }

    public void setSchadenKategorie(String schadenKategorie) {
        this.schadenKategorie = schadenKategorie;
    }

    public String getSchadenNr() {
        return schadenNr;
    }

    public void setSchadenNr(String schadenNr) {
        this.schadenNr = schadenNr;
    }

    public String getSchadenOrt() {
        return schadenOrt;
    }

    public void setSchadenOrt(String schadenOrt) {
        this.schadenOrt = schadenOrt;
    }

    public boolean isSchadenPolizei() {
        return schadenPolizei;
    }

    public void setSchadenPolizei(boolean schadenPolizei) {
        this.schadenPolizei = schadenPolizei;
    }

    public String getSchadenUmfang() {
        return schadenUmfang;
    }

    public void setSchadenUmfang(String schadenUmfang) {
        this.schadenUmfang = schadenUmfang;
    }

    public Timestamp getSchaedenTime() {
        return schaedenTime;
    }

    public void setSchaedenTime(Timestamp schaedenTime) {
        this.schaedenTime = schaedenTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVertragsId() {
        return vertragsId;
    }

    public void setVertragsId(int vertragsId) {
        this.vertragsId = vertragsId;
    }

    public boolean isVuGutachten() {
        return vuGutachten;
    }

    public void setVuGutachten(boolean vuGutachten) {
        this.vuGutachten = vuGutachten;
    }

    public String getVuMeldungArt() {
        return vuMeldungArt;
    }

    public void setVuMeldungArt(String vuMeldungArt) {
        this.vuMeldungArt = vuMeldungArt;
    }

    public String getVuSchadennummer() {
        return vuSchadennummer;
    }

    public void setVuSchadennummer(String vuSchadennummer) {
        this.vuSchadennummer = vuSchadennummer;
    }

    public Timestamp getVuStatusDatum() {
        return vuStatusDatum;
    }

    public void setVuStatusDatum(Timestamp vuStatusDatum) {
        this.vuStatusDatum = vuStatusDatum;
    }

    public Timestamp getVuWeiterleitungTime() {
        return vuWeiterleitungTime;
    }

    public void setVuWeiterleitungTime(Timestamp vuWeiterleitungTime) {
        this.vuWeiterleitungTime = vuWeiterleitungTime;
    }

    public int getWiedervorlagenId() {
        return wiedervorlagenId;
    }

    public void setWiedervorlagenId(int wiedervorlagenId) {
        this.wiedervorlagenId = wiedervorlagenId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }        

    @Override
    public String toString() {
        return this.schadenNr + " (" + this.kundenNr + ")";
    }
 
}