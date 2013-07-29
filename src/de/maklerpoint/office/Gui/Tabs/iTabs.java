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

package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;

/**
 *
 * @author yves
 */
public interface iTabs {

    public String getTabName();

    public void load(KundenObj kunde);
    public void load(FirmenObj firma);
    public void load(VersichererObj versicherer);
    public void load(BenutzerObj benutzer);
    public void load(ProduktObj produkt);
    public void load(VertragObj vertrag);
    public void load(StoerfallObj stoerfall);    
    public void load(SchadenObj schaden);

    public void disableElements();
    public void enableElements();
    
}
