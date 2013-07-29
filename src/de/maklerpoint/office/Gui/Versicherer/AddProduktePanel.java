/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Gui.Versicherer;


import de.maklerpoint.office.Gui.Tabs.TabPanelBeratungsdokumentation;
import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Gui.Versicherer.Tabs.*;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import java.awt.Component;

/**
 *
 * @author yves
 */
public class AddProduktePanel {

    private Class[] panelcls = new Class[]{
        TabPanelDokumente.class, TabPanelProduktCourtage.class, TabPanelBeratungsdokumentation.class,  
        TabPanelProduktBewertung.class, TabPanelProduktGruppen.class,
        TabPanelProduktHaftung.class, TabPanelProduktUmsatzsteuer.class, TabPanelProduktRisikotexte.class,
        TabPanelProvisionsstatus.class, TabPanelNotizen.class, TabPanelHinterlegteKontakte.class, TabPanelVerlauf.class
    };
    private iTabs[] panels = new iTabs[panelcls.length];

    private boolean added = false;

    public void add(PanelProduktUebersicht pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];

            pnl.pane_content.add(panels[i].getTabName(), comp);
        }

        added = true;
    }

    public void load(ProduktObj prod) {
        for(int i = 0; i < panels.length; i++) {
            panels[i].enableElements();
            panels[i].load(prod);            
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
