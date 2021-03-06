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
 * panelUebersicht.java
 *
 * Created on Jul 28, 2010, 12:39:46 PM
 */
package de.maklerpoint.office.Gui.Leftpane;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Tools.BasicRegex;
import java.util.Calendar;
import java.util.Date;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class panelUebersicht extends javax.swing.JPanel {

    /** Creates new form panelUebersicht */
    public panelUebersicht() {
        initComponents();
        setUp();
    }

    private void setUp() {
        loadHeutigeTermine();
        loadHeutigeAufgaben();
        loadGeburtstage();
        loadWiedervorlagen();
    }

    public void loadWiedervorlagen() {
        final JXLabel label = new JXLabel();
        label.setText("Keine Wiedervorlagen heute");
        this.taskpane_wiedervorlagen.add(label);
    }

    /**
     *
     */
    public void loadHeutigeTermine() {
        Date date = new Date(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);

        TerminObj[] termine = KalenderRegistry.getTermine();

        if (termine == null) {
            final JXLabel label = new JXLabel();
            label.setText("Keine Termine heute");
            taskpane_termine.add(label);
        } else {
            int cnt = 0;

            for (int i = 0; i < termine.length; i++) {
                TerminObj termin = termine[i];

                c2.setTime(termin.getStart());

                boolean same = sameDay(c1, c2);
                if (same) {
                    JXLabel label = new JXLabel();
                    label.setText(BasicRegex.shortenString(30,termin.toString(), true));
                    taskpane_termine.add(label);
                    cnt++;
                } else {
                    boolean after = this.afterToday(c1, c2); // Start Datum nicht heute
                    if (after == false) {
                        c2.setTime(termin.getEnde()); // End Datum heute?
                        same = sameDay(c1, c2);
                        if (same) {
                            JXLabel label = new JXLabel();
                            label.setText(BasicRegex.shortenString(30,termin.toString(), true));
                            taskpane_termine.add(label);
                            cnt++;
                        } else {
                            after = afterToday(c1, c2);
                            if (after) {
                                JXLabel label = new JXLabel();
                                label.setText(BasicRegex.shortenString(30,termin.toString(), true));
                                taskpane_termine.add(label);
                                cnt++;
                            }
                        }
                    }
                }
            }

            if (cnt == 0) {
                final JXLabel label = new JXLabel();
                label.setText("Keine Termine heute");
                taskpane_termine.add(label);
            }
        }
    }

    
    public void loadHeutigeAufgaben() {
        Date date = new Date(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);

        AufgabenObj[] aufgaben = KalenderRegistry.getAufgaben();

        if (aufgaben == null) {
            final JXLabel label = new JXLabel();
            label.setText("Keine Aufgaben heute");
            taskpane_aufgaben.add(label);
        } else {
            int cnt = 0;

            for (int i = 0; i < aufgaben.length; i++) {
                AufgabenObj aufgabe = aufgaben[i];
                c2.setTime(aufgabe.getStart());

                boolean same = sameDay(c1, c2);

                if (same) {
                    final JXLabel label = new JXLabel();
                    label.setText(BasicRegex.shortenString(30, aufgabe.toString(), true));
                    taskpane_aufgaben.add(label);
                    cnt++;
                } else {
                    boolean after = this.afterToday(c1, c2);
                    if (after == false) {
                        c2.setTime(aufgabe.getEnde());
                        same = sameDay(c1, c2);
                        if (same) {
                            final JXLabel label = new JXLabel();
                            label.setText(BasicRegex.shortenString(30, aufgabe.toString(), true));
                            taskpane_aufgaben.add(label);
                            cnt++;
                        } else {
                            after = afterToday(c1, c2);
                            if (after) {
                                final JXLabel label = new JXLabel();
                                label.setText(BasicRegex.shortenString(30, aufgabe.toString(), true));
                                taskpane_aufgaben.add(label);
                                cnt++;
                            }
                        }
                    }
                }
            }

            if (cnt == 0) {
                final JXLabel label = new JXLabel();
                label.setText("Keine Aufgaben heute");
                taskpane_aufgaben.add(label);
            }
        }

        taskpane_aufgaben.revalidate();
    }

    /**
     * 
     */
    public void loadGeburtstage() {

        
    }

    /**
     *
     * @param c1
     * @param c2
     * @return sameDay
     */
    public boolean sameDay(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public boolean afterToday(Calendar c1, Calendar c2) {

        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_holder = new javax.swing.JPanel();
        taskpane_termine = new org.jdesktop.swingx.JXTaskPane();
        taskpane_aufgaben = new org.jdesktop.swingx.JXTaskPane();
        taskpane_geburtstage = new org.jdesktop.swingx.JXTaskPane();
        taskpane_wiedervorlagen = new org.jdesktop.swingx.JXTaskPane();
        jLabel4 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        panel_holder.setName("panel_holder"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelUebersicht.class);
        taskpane_termine.setIcon(resourceMap.getIcon("taskpane_termine.icon")); // NOI18N
        taskpane_termine.setTitle(resourceMap.getString("taskpane_termine.title")); // NOI18N
        taskpane_termine.setName("taskpane_termine"); // NOI18N

        taskpane_aufgaben.setIcon(resourceMap.getIcon("taskpane_aufgaben.icon")); // NOI18N
        taskpane_aufgaben.setTitle(resourceMap.getString("taskpane_aufgaben.title")); // NOI18N
        taskpane_aufgaben.setName("taskpane_aufgaben"); // NOI18N

        taskpane_geburtstage.setIcon(resourceMap.getIcon("taskpane_geburtstage.icon")); // NOI18N
        taskpane_geburtstage.setTitle(resourceMap.getString("taskpane_geburtstage.title")); // NOI18N
        taskpane_geburtstage.setName("taskpane_geburtstage"); // NOI18N

        taskpane_wiedervorlagen.setIcon(resourceMap.getIcon("taskpane_wiedervorlagen.icon")); // NOI18N
        taskpane_wiedervorlagen.setTitle(resourceMap.getString("taskpane_wiedervorlagen.title")); // NOI18N
        taskpane_wiedervorlagen.setName("taskpane_wiedervorlagen"); // NOI18N

        javax.swing.GroupLayout panel_holderLayout = new javax.swing.GroupLayout(panel_holder);
        panel_holder.setLayout(panel_holderLayout);
        panel_holderLayout.setHorizontalGroup(
            panel_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_holderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(taskpane_termine, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(taskpane_aufgaben, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(taskpane_wiedervorlagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(taskpane_geburtstage, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_holderLayout.setVerticalGroup(
            panel_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_holderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskpane_termine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taskpane_aufgaben, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taskpane_wiedervorlagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taskpane_geburtstage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jLabel4.setIcon(resourceMap.getIcon("jLabel4.icon")); // NOI18N
        jLabel4.setToolTipText(resourceMap.getString("jLabel4.toolTipText")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_holder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_holder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panel_holder;
    private org.jdesktop.swingx.JXTaskPane taskpane_aufgaben;
    private org.jdesktop.swingx.JXTaskPane taskpane_geburtstage;
    private org.jdesktop.swingx.JXTaskPane taskpane_termine;
    private org.jdesktop.swingx.JXTaskPane taskpane_wiedervorlagen;
    // End of variables declaration//GEN-END:variables
}
