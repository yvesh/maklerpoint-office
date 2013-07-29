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
package de.maklerpoint.office.Gui.Vertraege;

import de.maklerpoint.office.Gui.Tabs.TabPanelBeratungsdokumentation;
import de.maklerpoint.office.Gui.Tabs.TabPanelDokumente;
import de.maklerpoint.office.Gui.Tabs.TabPanelHinterlegteKontakte;
import de.maklerpoint.office.Gui.Tabs.TabPanelKunden;
import de.maklerpoint.office.Gui.Tabs.TabPanelNotizen;
import de.maklerpoint.office.Gui.Tabs.TabPanelSchaeden;
import de.maklerpoint.office.Gui.Tabs.TabPanelStoerfaelle;
import de.maklerpoint.office.Gui.Tabs.TabPanelVerlauf;
import de.maklerpoint.office.Gui.Tabs.TabPanelVertraege;
import de.maklerpoint.office.Gui.Tabs.TabPanelVorFolgeVertraege;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Component;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class AddVertragePanels {
    
    private Class[] panelcls = new Class[]{TabPanelDokumente.class, TabPanelKunden.class, 
                    TabPanelVorFolgeVertraege.class,
                    TabPanelBeratungsdokumentation.class, 
                    TabPanelStoerfaelle.class, TabPanelSchaeden.class, 
                    TabPanelNotizen.class, TabPanelHinterlegteKontakte.class,
                    TabPanelVerlauf.class};
    
    private iTabs[] panels = new iTabs[panelcls.length];

    private boolean added = false;

    public void add(PanelVertraege pnl) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
            panels[i] = (iTabs) panelcls[i].newInstance();
            Component comp = (Component) panels[i];

            pnl.pane_contentholder.add(panels[i].getTabName(), comp);
        }

        added = true;
    }

     public void load(VertragObj vtr) {
        for(int i = 0; i < panels.length; i++) {
            panels[i].load(vtr);
            panels[i].enableElements();
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
