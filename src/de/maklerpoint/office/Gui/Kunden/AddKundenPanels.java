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

package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Gui.Tabs.TabPanelBankkonten;
import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelBeratungsdokumentation;
import de.maklerpoint.office.Gui.Tabs.TabPanelBetreuungPrivat;
import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelKinder;
import de.maklerpoint.office.Gui.Tabs.TabPanelRisiken;
import de.maklerpoint.office.Gui.Tabs.TabPanelSchaeden;
import de.maklerpoint.office.Gui.Tabs.TabPanelStoerfaelle;
import de.maklerpoint.office.Gui.Tabs.TabPanelTermine;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.TabPanelVertraege;
import de.maklerpoint.office.Gui.Tabs.TabPanelZusatzadressen;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Kunden.KundenObj;
import java.awt.Component;

/**
 *
 * @author Yves Hoppe
 */
public class AddKundenPanels {

    // Betreuung
    // Notizen
    // Dokumente
    // Beratungsdokumnetation
    // Zusatzadressen
    // Bankkonten
    // Kinder
    // Termine
    // Verträge
    // Risiken
    // Störfälle
    // Schäden
    // Verlauf
    // Zusatzdaten
    
    private Class[] panelcls = new Class[]{ TabPanelDokumente.class, TabPanelBetreuungPrivat.class, 
                    TabPanelBeratungsdokumentation.class, TabPanelZusatzadressen.class, TabPanelBankkonten.class,
                    TabPanelKinder.class, TabPanelTermine.class, TabPanelVertraege.class, TabPanelRisiken.class,
                    TabPanelStoerfaelle.class, TabPanelSchaeden.class, TabPanelNotizen.class, 
                    TabPanelHinterlegteKontakte.class, TabPanelVerlauf.class};
       
    
    private iTabs[] panels = new iTabs[panelcls.length];

    private boolean added = false;

    public void add(PanelKundenUebersicht pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];

            pnl.pane_kundendetails.add(panels[i].getTabName(), comp);
        }

        added = true;
    }

    public void load(KundenObj kunde) {
        for(int i = 0; i < panels.length; i++) {            
            panels[i].enableElements();
            panels[i].load(kunde);              
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
