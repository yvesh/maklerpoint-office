/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelProduktCourtage.java
 *
 * Created on 25.06.2011, 10:32:44
 */
package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelProduktCourtage extends iTabsAbstractProdukt {

    /** Creates new form TabPanelProduktCourtage */
    public TabPanelProduktCourtage() {
        initComponents();
    }

    public String getTabName() {
        return ("Courtage / Provision");
    }

    public void load(ProduktObj produkt) {
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
