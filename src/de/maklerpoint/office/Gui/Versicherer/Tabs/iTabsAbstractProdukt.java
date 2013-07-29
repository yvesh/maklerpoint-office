/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 14:32:25
 *  File:       iTabsAbstractProdukt
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
package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public abstract class iTabsAbstractProdukt extends javax.swing.JPanel implements iTabs  {
    
    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(FirmenObj firma) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(VersichererObj versicherer) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(BenutzerObj benutzer) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Der Tab ist nur für Produkte.");
    }
    
}