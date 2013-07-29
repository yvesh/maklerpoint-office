/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/08 13:10
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

package de.maklerpoint.office.Gui.Firstrun;

import de.maklerpoint.office.System.Configuration.Config;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */

public class WizardWatcher {
     
     private Class[] panels = new Class[]{PanelEinrichtungWillkommen.class, PanelEinrichtungInstallationstyp.class,
                     PanelEinrichtungConnect.class, PanelEinrichtungNetzwerk.class, PanelEinrichtungEmbedded.class, // 5 (-1)
                     PanelEinrichtungMandant.class, PanelEinrichtungBenutzer.class, PanelEinrichtungVersicherer.class
     }; 
     private iAssistent[] asis = new iAssistent[panels.length];

     private int position = 0;
     private int last = -1;

     /**
      * 
      * @param as
      * @throws InstantiationException
      * @throws IllegalAccessException
      */

     public void load(EinrichtungsAssistentDialog as) throws InstantiationException, IllegalAccessException {
         as.panel_holder.removeAll();

         asis[0] = (iAssistent) panels[0].newInstance();
         Component comp = (Component) asis[0];

         as.panel_holder.removeAll();
         as.label_stepname.setText(asis[0].getStepName());         

         as.panel_holder.add(comp, BorderLayout.CENTER);
         asis[0].loadSettings(as);
         position = 0;
         as.btn_back.setEnabled(false);
     }

     /**
      *
      * @param as
      * @throws InstantiationException
      * @throws IllegalAccessException
      */

     public void back(EinrichtungsAssistentDialog as) throws InstantiationException, IllegalAccessException {

//         if(asis[position].validateSettings()) {
//             asis[position].saveSettings();
//         }

         if(position <= panels.length - 1) {
             as.btn_next.setText("Weiter");
         }

         if(last == -1)
            position--;
         else {
            position = last;
            last = -1;
         }

         asis[position] = (iAssistent) panels[position].newInstance();
         Component comp = (Component) asis[position];
         as.btn_next.setEnabled(true);

         as.panel_holder.removeAll();
         as.label_stepname.setText(asis[position].getStepName());
         
         as.panel_holder.add(comp, BorderLayout.CENTER);

         asis[position].loadSettings(as);

         if(position == 0) {
             as.btn_back.setEnabled(false);
         }

     }

     /**
      *
      * @param as
      * @throws InstantiationException
      * @throws IllegalAccessException
      */

     public void next(EinrichtungsAssistentDialog as) throws InstantiationException, IllegalAccessException {
         if(position == panels.length -1) { // Finish Wizard
            if(!asis[position].validateSettings())
               return;

            asis[position].saveSettings();
            as.dispose();
            return;
         }

         if(!asis[position].validateSettings())
             return;
         
         asis[position].saveSettings();

         int next = asis[position].next();

         as.btn_back.setEnabled(true);

         if(next != -1) {
             if(next == -2) { // Beenden
                 Config.setBoolean("firstrun", false);
                 as.dispose();
                 JOptionPane.showMessageDialog(null, "Die Einrichtung von MaklerPoint wurde "
                         + "erfolgreich abgeschlossen. MaklerPoint wird jetzt gestartet.", "Einrichtung beendet",
                         JOptionPane.INFORMATION_MESSAGE);
                 return;
             } else {
                 last = position;
                 position = next;
             }
         } else {
             position++;
         }

         asis[position] = (iAssistent) panels[position].newInstance();
         Component comp = (Component) asis[position];

         as.panel_holder.removeAll();
         as.label_stepname.setText(asis[position].getStepName());

         as.panel_holder.add(comp, BorderLayout.CENTER);

         asis[position].loadSettings(as);

         if(position == panels.length -1) {
             as.btn_next.setText("Beenden");
             
         }

         as.panel_holder.revalidate();
     }

     public int getPosition() {
         return this.position;
     }

}
