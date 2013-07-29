/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Gui.Versicherer;

import de.maklerpoint.office.Gui.Tabs.TabPanelAnsprechpartner;
import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelBeratungsdokumentation;
import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelTermine;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.TabPanelZusatzadressen;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Gui.Versicherer.Tabs.TabPanelVersichererBestandsprovision;
import de.maklerpoint.office.Gui.Versicherer.Tabs.TabPanelVersichererProdukte;
import de.maklerpoint.office.Gui.Versicherer.Tabs.TabPanelVersichererWeiteres;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.awt.Component;

/**
 *
 * @author yves
 */

public class AddVersichererPanel {

    private Class[] panelcls = new Class[]{TabPanelDokumente.class,
                    TabPanelAnsprechpartner.class, TabPanelVersichererProdukte.class, TabPanelVersichererBestandsprovision.class,
                    TabPanelVersichererWeiteres.class,
                    TabPanelBeratungsdokumentation.class, TabPanelZusatzadressen.class, TabPanelHinterlegteKontakte.class, 
                    TabPanelNotizen.class, 
                    TabPanelVerlauf.class                    
    };
    private iTabs[] panels = new iTabs[panelcls.length];

    private boolean added = false;

    public void add(PanelVersichererUebersicht pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];

            pnl.pane_content.add(panels[i].getTabName(), comp);
        }

        added = true;
    }

     public void load(VersichererObj vers) {
        for(int i = 0; i < panels.length; i++) {
            panels[i].enableElements();
            panels[i].load(vers);            
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
