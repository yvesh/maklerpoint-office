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

package de.maklerpoint.office.Beratungsprotokoll;

import de.maklerpoint.office.System.Status;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class BeratungsprotokollObj {

    private int _id;

    private String _kundenKennung;
    private int _benutzerId = -1;

    private int _versicherungsId = -1;
    private int _produktId = -1;
    
    private int _vertragId = -1;

    private boolean _beratungsVerzicht = false;
    private boolean _dokumentationsVerzicht = false;
    private boolean _wiederVorlage = false;

    private String _kundenWuensche;
    private String _kundenBedarf;
    private String _rat;
    private String _entscheidung;
    private String _marktuntersuchung;

    private String _versicherungsSparte;
    private String _versicherungsGesellschaft;

    private String _beratungsVerzichtArt;
    private String _kundenAnschreiben;

    private String _kundenBemerkungen;

    private String[] _dokumente;

    private boolean _checkKundenAnschreiben = true;
    private boolean _checkBeratungsDokumentation = true;
    private boolean _checkBeratungsDokuVerzicht = false;
    private boolean _checkInformationsPflichten = true;
    private boolean _checkDruckstuecke = true;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

    public BeratungsprotokollObj() {        
    }

    public BeratungsprotokollObj(String kundenkennung, int beraterId) {
        this._kundenKennung = kundenkennung;
        this._benutzerId = beraterId;
    }

    public String getKundenKennung() {
        return _kundenKennung;
    }

    public void setKundenKennung(String _kundenKennung) {
        this._kundenKennung = _kundenKennung;
    }

    public int getBenutzerId() {
        return _benutzerId;
    }    

    public void setBenutzerId(int _benutzerId) {
        this._benutzerId = _benutzerId;
    }
    
    public int getVertragId() {
        return _vertragId;
    }

    public void setVertragId(int _vertragId) {
        this._vertragId = _vertragId;
    }

    public boolean isBeratungsVerzicht() {
        return _beratungsVerzicht;
    }

    public void setBeratungsVerzicht(boolean _beratungsVerzicht) {
        this._beratungsVerzicht = _beratungsVerzicht;
    }

    public String getBeratungsVerzichtArt() {
        return _beratungsVerzichtArt;
    }

    public void setBeratungsVerzichtArt(String _beratungsVerzichtArt) {
        this._beratungsVerzichtArt = _beratungsVerzichtArt;
    }

    public boolean isCheckBeratungsDokumentation() {
        return _checkBeratungsDokumentation;
    }

    public void setCheckBeratungsDokumentation(boolean _checkBeratungsDokumentation) {
        this._checkBeratungsDokumentation = _checkBeratungsDokumentation;
    }

    public boolean isCheckBeratungsDokuVerzicht() {
        return _checkBeratungsDokuVerzicht;
    }

    public void setCheckBeratungsDokuVerzicht(boolean _checkBeratungsDokuVerzicht) {
        this._checkBeratungsDokuVerzicht = _checkBeratungsDokuVerzicht;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCheckDruckstuecke() {
        return _checkDruckstuecke;
    }

    public void setCheckDruckstuecke(boolean _checkDruckstuecke) {
        this._checkDruckstuecke = _checkDruckstuecke;
    }

    public boolean isCheckInformationsPflichten() {
        return _checkInformationsPflichten;
    }

    public void setCheckInformationsPflichten(boolean _checkInformationsPflichten) {
        this._checkInformationsPflichten = _checkInformationsPflichten;
    }

    public boolean isCheckKundenAnschreiben() {
        return _checkKundenAnschreiben;
    }

    public void setCheckKundenAnschreiben(boolean _checkKundenAnschreiben) {
        this._checkKundenAnschreiben = _checkKundenAnschreiben;
    }

    public Timestamp getCreated() {
        return _created;
    }

    public void setCreated(Timestamp _created) {
        this._created = _created;
    }

    public boolean isDokumentationsVerzicht() {
        return _dokumentationsVerzicht;
    }

    public void setDokumentationsVerzicht(boolean _dokumentationsVerzicht) {
        this._dokumentationsVerzicht = _dokumentationsVerzicht;
    }

    public String[] getDokumente() {
        return _dokumente;
    }

    public void setDokumente(String[] _dokumente) {
        this._dokumente = _dokumente;
    }

    public String getEntscheidung() {
        return _entscheidung;
    }

    public void setEntscheidung(String _entscheidung) {
        this._entscheidung = _entscheidung;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getKundenAnschreiben() {
        return _kundenAnschreiben;
    }

    public void setKundenAnschreiben(String _kundenAnschreiben) {
        this._kundenAnschreiben = _kundenAnschreiben;
    }

    public String getKundenBedarf() {
        return _kundenBedarf;
    }

    public void setKundenBedarf(String _kundenBedarf) {
        this._kundenBedarf = _kundenBedarf;
    }

    public String getKundenWuensche() {
        return _kundenWuensche;
    }

    public void setKundenWuensche(String _kundenWuensche) {
        this._kundenWuensche = _kundenWuensche;
    }

    public String getMarktuntersuchung() {
        return _marktuntersuchung;
    }

    public void setMarktuntersuchung(String _marktuntersuchung) {
        this._marktuntersuchung = _marktuntersuchung;
    }

    public Timestamp getModified() {
        return _modified;
    }

    public void setModified(Timestamp _modified) {
        this._modified = _modified;
    }

    public int getProduktId() {
        return _produktId;
    }

    public void setProduktId(int _produktId) {
        this._produktId = _produktId;
    }

    public String getRat() {
        return _rat;
    }

    public void setRat(String _rat) {
        this._rat = _rat;
    }

    public String getVersicherungsGesellschaft() {
        return _versicherungsGesellschaft;
    }

    public void setVersicherungsGesellschaft(String _versicherungsGesellschaft) {
        this._versicherungsGesellschaft = _versicherungsGesellschaft;
    }

    public int getVersicherungsId() {
        return _versicherungsId;
    }

    public void setVersicherungsId(int _versicherungsId) {
        this._versicherungsId = _versicherungsId;
    }

    public String getVersicherungsSparte() {
        return _versicherungsSparte;
    }

    public void setVersicherungsSparte(String _versicherungsSparte) {
        this._versicherungsSparte = _versicherungsSparte;
    }

    public String getKundenBemerkungen() {
        return _kundenBemerkungen;
    }

    public void setKundenBemerkungen(String _kundenBemerkungen) {
        this._kundenBemerkungen = _kundenBemerkungen;
    }

    public boolean isWiederVorlage() {
        return _wiederVorlage;
    }

    public void setWiederVorlage(boolean _wiederVorlage) {
        this._wiederVorlage = _wiederVorlage;
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return "Beratungsprtokoll für " + _kundenKennung + " erstellt am " + df.format(_created);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BeratungsprotokollObj other = (BeratungsprotokollObj) obj;
       
        if (this._benutzerId != other._benutzerId) {
            return false;
        }
        if (this._beratungsVerzicht != other._beratungsVerzicht) {
            return false;
        }
        if (this._dokumentationsVerzicht != other._dokumentationsVerzicht) {
            return false;
        }
        if (this._wiederVorlage != other._wiederVorlage) {
            return false;
        }
        if ((this._kundenWuensche == null) ? (other._kundenWuensche != null) : !this._kundenWuensche.equals(other._kundenWuensche)) {
            return false;
        }
        if ((this._kundenBedarf == null) ? (other._kundenBedarf != null) : !this._kundenBedarf.equals(other._kundenBedarf)) {
            return false;
        }
        if ((this._rat == null) ? (other._rat != null) : !this._rat.equals(other._rat)) {
            return false;
        }
        if ((this._entscheidung == null) ? (other._entscheidung != null) : !this._entscheidung.equals(other._entscheidung)) {
            return false;
        }
        if ((this._marktuntersuchung == null) ? (other._marktuntersuchung != null) : !this._marktuntersuchung.equals(other._marktuntersuchung)) {
            return false;
        }
        if ((this._versicherungsSparte == null) ? (other._versicherungsSparte != null) : !this._versicherungsSparte.equals(other._versicherungsSparte)) {
            return false;
        }
        if ((this._versicherungsGesellschaft == null) ? (other._versicherungsGesellschaft != null) : !this._versicherungsGesellschaft.equals(other._versicherungsGesellschaft)) {
            return false;
        }
        if ((this._beratungsVerzichtArt == null) ? (other._beratungsVerzichtArt != null) : !this._beratungsVerzichtArt.equals(other._beratungsVerzichtArt)) {
            return false;
        }
        if ((this._kundenAnschreiben == null) ? (other._kundenAnschreiben != null) : !this._kundenAnschreiben.equals(other._kundenAnschreiben)) {
            return false;
        }
        if (!Arrays.deepEquals(this._dokumente, other._dokumente)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this._benutzerId;
        hash = 37 * hash + (this._kundenWuensche != null ? this._kundenWuensche.hashCode() : 0);
        hash = 37 * hash + (this._kundenBedarf != null ? this._kundenBedarf.hashCode() : 0);
        hash = 37 * hash + (this._rat != null ? this._rat.hashCode() : 0);
        hash = 37 * hash + (this._entscheidung != null ? this._entscheidung.hashCode() : 0);
        hash = 37 * hash + (this._marktuntersuchung != null ? this._marktuntersuchung.hashCode() : 0);
        hash = 37 * hash + (this._versicherungsSparte != null ? this._versicherungsSparte.hashCode() : 0);
        hash = 37 * hash + (this._versicherungsGesellschaft != null ? this._versicherungsGesellschaft.hashCode() : 0);
        hash = 37 * hash + (this._beratungsVerzichtArt != null ? this._beratungsVerzichtArt.hashCode() : 0);
        hash = 37 * hash + (this._kundenAnschreiben != null ? this._kundenAnschreiben.hashCode() : 0);
        hash = 37 * hash + Arrays.deepHashCode(this._dokumente);
        return hash;
    }



}
