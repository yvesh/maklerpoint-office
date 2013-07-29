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

/**
 *
 * @author yves
 */
public class ProduktUmsatzsteuerObj {

    private int _id;
    private int _produkt = -1; // Nie leer

    private int _umsatzsteuerStatus = Produkte.UMSATZSTEUER_FREI;

    private String _umsatzsteuerFrei4g;
    private int _umsatzsteueNichtbefreitBenutzer;

    private boolean _umsatzsteuerAbschluss = false; // Abschluss- / ratierliche Provision
    private boolean _umsatzsteuerFolge = false; // Folgeprovision
    private boolean _umsatzsteuerDynamik = false; // Dynamikprovision
    private boolean _umsatzsteuerBestand = false; // Bestandsprovision
    private boolean _umsatzsteuerAenderung = false; // Änderungsprovision
    private boolean _umsatzsteuerDifferenz = false; // Differenprovision

    private int _vorsteuerabzugStatus;
    private String _vorsteuerabzugZeichen;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;

}
