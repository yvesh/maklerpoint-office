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

package de.maklerpoint.office.Gui.Firmenkunden;

import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelAnsprechpartner;
import de.maklerpoint.office.Gui.Tabs.TabPanelBankkonten;
import de.maklerpoint.office.Gui.Tabs.TabPanelBeratungsdokumentation;
import de.maklerpoint.office.Gui.Tabs.TabPanelBetreuungFirmen;
import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelRisiken;
import de.maklerpoint.office.Gui.Tabs.TabPanelSchaeden;
import de.maklerpoint.office.Gui.Tabs.TabPanelStoerfaelle;
import de.maklerpoint.office.Gui.Tabs.TabPanelTermine;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.TabPanelVertraege;
import de.maklerpoint.office.Gui.Tabs.TabPanelZusatzadressen;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Kunden.FirmenObj;
import java.awt.Component;

/**
 *
 * @author yves
 */

public class AddFirmenPanels {

    private Class[] panelcls = new Class[]{TabPanelBetreuungFirmen.class, TabPanelAnsprechpartner.class,
                    TabPanelDokumente.class, TabPanelVertraege.class,
                    TabPanelTermine.class, TabPanelRisiken.class,
                    TabPanelBeratungsdokumentation.class, TabPanelZusatzadressen.class, TabPanelBankkonten.class,
                    TabPanelStoerfaelle.class, TabPanelSchaeden.class, 
                    TabPanelNotizen.class, TabPanelHinterlegteKontakte.class,
                    TabPanelVerlauf.class};
    private iTabs[] panels = new iTabs[panelcls.length];

    private boolean added = false;

    public void add(PanelFirmenKundenUebersicht pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];
            pnl.pane_contentholder.add(panels[i].getTabName(), comp);
        }

        added = true;
    }

     public void load(FirmenObj firma) {
        for(int i = 0; i < panels.length; i++) {
            panels[i].enableElements();
            panels[i].load(firma);            
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