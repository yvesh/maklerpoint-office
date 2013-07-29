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

/*
 * TabPanelProduktUmsatzsteuer.java
 *
 * Created on 17.09.2010, 12:47:59
 */

package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;

/**
 *
 * @author Yves Hoppe
 */
public class TabPanelProduktUmsatzsteuer extends iTabsAbstractProdukt {

    private ProduktObj prod;
    private VersichererObj vers;

    /** Creates new form TabPanelProduktUmsatzsteuer */
    public TabPanelProduktUmsatzsteuer() {
        initComponents();
    }


    public String getTabName() {
        return "Umsatzsteuer";
    }

    public void load(ProduktObj prod) {
        this.prod = prod;
    }

    public void load(ProduktObj prod, VersichererObj vers) {
        this.prod = prod;
        this.vers = vers;
    }

    public void disableElements() {
   
    }

    public void enableElements() {
 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}