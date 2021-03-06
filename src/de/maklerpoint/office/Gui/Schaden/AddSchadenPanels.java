/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       19.07.2011 10:06:00
 *  File:       AddSchadenPanels
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
package de.maklerpoint.office.Gui.Schaden;

import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelForderungen;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.TabPanelZahlungen;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Schaeden.SchadenObj;
import java.awt.Component;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class AddSchadenPanels {
    
    private Class[] panelcls = new Class[]{TabPanelDokumente.class, TabPanelForderungen.class, 
        TabPanelZahlungen.class, TabPanelNotizen.class, 
        TabPanelHinterlegteKontakte.class, TabPanelVerlauf.class};
    
    private iTabs[] panels = new iTabs[panelcls.length];
    
    private boolean added = false;

    public void add(PanelSchaeden pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];

            pnl.pane_schadendetails.add(panels[i].getTabName(), comp);
        }

        added = true;
    }
    
    public void load(SchadenObj sch) {
        for(int i = 0; i < panels.length; i++) {
            panels[i].enableElements();
            panels[i].load(sch);            
        }
    }
    
    public void disElements() {
        for(int i = 0; i < panels.length; i++) {
            panels[i].disableElements();
        }
    }

    public boolean isLoaded() {
        return added;
    }
    
}