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

package de.maklerpoint.office.Gui.Configuration;

import java.awt.Component;

/**
 *
 * @author yves
 */
public class AddConfigurationPanels {

    private Class[] panels = new Class[]{PanelConfigurationDatabase.class, PanelConfigurationEmail.class, 
        PanelConfigurationSonstiges.class, PanelConfigurationGoogle.class};


    private ConfigurationPanelInterface[] pan = new ConfigurationPanelInterface[panels.length];

    public void load(ConfigurationDialog ph) throws InstantiationException, IllegalAccessException {
        for(int i = 0; i < panels.length; i++) {
             pan[i] = (ConfigurationPanelInterface) panels[i].newInstance();
             Component comp = (Component) pan[i];

             if(pan[i].getCategory() == ConfigurationPanelTypes.DATENBANK)
                ph.pane_db.add(pan[i].getPanelName(), comp);
             else if(pan[i].getCategory() == ConfigurationPanelTypes.BRIEFE)
                ph.pane_brief.add(pan[i].getPanelName(), comp);
             else if(pan[i].getCategory() == ConfigurationPanelTypes.DRUCKER)
                ph.pane_print.add(pan[i].getPanelName(), comp);
             else if(pan[i].getCategory() == ConfigurationPanelTypes.KALENDER)
                ph.pane_kal.add(pan[i].getPanelName(), comp);
             else if(pan[i].getCategory() == ConfigurationPanelTypes.SCHNITTSTELLEN)
                ph.pane_schnitt.add(pan[i].getPanelName(), comp);
             else if(pan[i].getCategory() == ConfigurationPanelTypes.VERSCHIEDENES)
                ph.pane_misc.add(pan[i].getPanelName(), comp);

             pan[i].loadSettings();
        }
    }

    public void save(ConfigurationDialog ph) {
        for(int i = 0; i < panels.length; i++) {
            pan[i].saveSettings();
        }
    }

}
