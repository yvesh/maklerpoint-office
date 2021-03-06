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
 * TabPanelProduktHaftung.java
 *
 * Created on 17.09.2010, 12:44:13
 */
package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.Produkte;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Versicherer.Produkte.ProduktHaftungObj;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.Tools.ProduktHaftungSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *
 * @author yves
 * extends javax.swing.JPanel
 */
public class TabPanelProduktHaftung extends iTabsAbstractProdukt {

    private ProduktObj prod;
    private VersichererObj vers;
    private ProduktHaftungObj currentPH = null;

    /** Creates new form TabPanelProduktHaftung */
    public TabPanelProduktHaftung() {
        initComponents();
        this.panel_ratierlich.setVisible(false);
        this.panel_kombiniert.setVisible(false);
        this.ffield_haftungszeit.setValue(0);
        this.ffield_vollhaftungszeit.setValue(0);
    }

    public String getTabName() {
        return "Haftung";
    }

    public void load(ProduktObj prod) {
        this.prod = prod;
        setUp();
    }

    public void load(ProduktObj prod, VersichererObj vers) {
        this.prod = prod;
        this.vers = vers;
        setUp();
    }

    public void disableElements() {
        this.ffield_haftungszeit.setEnabled(false);
        this.ffield_vollhaftungszeit.setEnabled(false);
        this.radio_gesamthaftungszeit.setEnabled(false);
        this.radio_kombiniert.setEnabled(false);
        this.radio_ratierlich.setEnabled(false);
        this.radio_ratierlichIntervall.setEnabled(false);
        this.radio_ratierlichTaggenau.setEnabled(false);
        this.radio_resthaftung.setEnabled(false);
        this.radio_vollhaftung.setEnabled(false);
        this.spinnerIntervalle.setEnabled(false);
    }

    public void enableElements() {
        this.ffield_haftungszeit.setEnabled(true);
        this.ffield_vollhaftungszeit.setEnabled(true);
        this.radio_gesamthaftungszeit.setEnabled(true);
        this.radio_kombiniert.setEnabled(true);
        this.radio_ratierlich.setEnabled(true);
        this.radio_ratierlichIntervall.setEnabled(true);
        this.radio_ratierlichTaggenau.setEnabled(true);
        this.radio_resthaftung.setEnabled(true);
        this.radio_vollhaftung.setEnabled(true);
        this.spinnerIntervalle.setEnabled(true);
    }
    
    private void addActionListener(){
        this.ffield_haftungszeit.addActionListener(update);
        this.ffield_vollhaftungszeit.addActionListener(update);
        this.radio_gesamthaftungszeit.addActionListener(update);
        this.radio_kombiniert.addActionListener(update);
        this.radio_ratierlich.addActionListener(update);
        this.radio_ratierlichIntervall.addActionListener(update);
        this.radio_ratierlichTaggenau.addActionListener(update);
        this.radio_resthaftung.addActionListener(update);
        this.radio_vollhaftung.addActionListener(update);
//        this.spinnerIntervalle.addActionListener(update);
    }

    public ActionListener update = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
        
    };
    
    private void setUp() {
        if(currentPH != null) {
            save();
        }
        
        try {
            ProduktHaftungObj ph = ProduktHaftungSQLMethods.getProduktHaftungsObj(
                    DatabaseConnection.open(), prod.getId());

            if (ph == null) {
                ph = new ProduktHaftungObj();
                ph.setProdukt(prod.getId());
                ph.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                ph.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                int id = ProduktHaftungSQLMethods.insertIntoProdukte_haftung(DatabaseConnection.open(), ph);
                ph.setId(id);
            }

            currentPH = ph;

            this.ffield_haftungszeit.setValue(ph.getHaftungZeit());

            if (ph.getHaftungsart() == Produkte.HAFTUNGART_VOLL) {
                this.radio_vollhaftung.setSelected(true);
            } else if (ph.getHaftungsart() == Produkte.HAFTUNGART_RATIERLICH) {
                this.radio_ratierlich.setSelected(true);
            } else if (ph.getHaftungsart() == Produkte.HAFTUNGART_KOMBINIERT) {
                this.radio_kombiniert.setSelected(true);
            }

            if (ph.getRatierlich() == Produkte.HAFTUNGSART_RATIERLICH_TAGGENAU) {
                this.radio_ratierlichTaggenau.setSelected(true);
            } else if (ph.getRatierlich() == Produkte.HAFTUNGSART_RATIERLICH_INTERVALLE) {
                this.radio_ratierlichIntervall.setSelected(true);
                this.spinnerIntervalle.setEnabled(true);
                this.spinnerIntervalle.setValue(ph.getRatierlichIntervalle());
            } // Fehlt Tabelle

            this.ffield_vollhaftungszeit.setValue(ph.getKombiniertVollhaftungsZeit());

            if (ph.getKombiniert() == Produkte.HAFTUNGSART_KOMBINIERT_RATIERLICH_RESTHAFTUNGSZEIT) {
                this.radio_resthaftung.setSelected(true);
            } else {
                this.radio_gesamthaftungszeit.setSelected(true);
            }

            setVisible();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte Produkthaftung nicht laden", e);
            ShowException.showException("Datenbankfehler: Die Produkthaftungsinformationen konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte die Produkthaftungsinformationen nicht laden");
        }
    }

    private void save() {
        if (currentPH == null) {
            throw new NullPointerException("Kein Produkthaftungsobject aktiv");
        }

        currentPH.setHaftungZeit(Integer.valueOf(this.ffield_haftungszeit.getValue().toString()));

        if (this.radio_vollhaftung.isSelected()) {
            currentPH.setHaftungsart(Produkte.HAFTUNGART_VOLL);
        } else if (this.radio_ratierlich.isSelected()) {
            currentPH.setHaftungsart(Produkte.HAFTUNGART_RATIERLICH);
        } else if (this.radio_kombiniert.isSelected()) {
            currentPH.setHaftungsart(Produkte.HAFTUNGART_VOLL);
        }


        // Ratierlich

        if (this.radio_ratierlichTaggenau.isSelected()) {
            currentPH.setRatierlich(Produkte.HAFTUNGSART_RATIERLICH_TAGGENAU);
        } else if (this.radio_ratierlichIntervall.isSelected()) {
            currentPH.setRatierlich(Produkte.HAFTUNGSART_RATIERLICH_INTERVALLE);
            currentPH.setRatierlichIntervalle(Integer.valueOf(this.spinnerIntervalle.getValue().toString()));
        } // Fehlt Tabelle


        currentPH.setKombiniertVollhaftungsZeit(Integer.valueOf(this.ffield_vollhaftungszeit.getValue().toString()));

        if (this.radio_resthaftung.isSelected()) {
            currentPH.setKombiniertRatierlich(Produkte.HAFTUNGSART_KOMBINIERT_RATIERLICH_RESTHAFTUNGSZEIT);
        } else {
            currentPH.setKombiniertRatierlich(Produkte.HAFTUNGSART_KOMBINIERT_RATIERLICH_GESAMTHAFTUNGSZEIT);
        }

        try {
            boolean success = ProduktHaftungSQLMethods.updateProdukte_haftung(DatabaseConnection.open(), currentPH);
            if(!success) {
                Log.databaselogger.fatal("Konnte Produkthaftung nicht aktualisieren - PH existiert nicht?");
                ShowException.showException("Datenbankfehler: Die Produkthaftungsinformationen konnten nicht aktualisiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, 
                    "Schwerwiegend: Konnte die Produkthaftungsinformationen nicht aktualisieren");
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte Produkthaftung nicht aktualisieren", e);
            ShowException.showException("Datenbankfehler: Die Produkthaftungsinformationen konnten nicht aktualisiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte die Produkthaftungsinformationen nicht aktualisieren");
        }

    }

    private void setVisible() {
        if (this.radio_vollhaftung.isSelected()) {
            this.panel_ratierlich.setVisible(false);
            this.panel_kombiniert.setVisible(false);
        } else if (this.radio_ratierlich.isSelected()) {
            this.panel_ratierlich.setVisible(true);
            this.panel_kombiniert.setVisible(false);
        } else if (this.radio_kombiniert.isSelected()) {
            this.panel_ratierlich.setVisible(false);
            this.panel_kombiniert.setVisible(true);
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

        grpHaftungart = new javax.swing.ButtonGroup();
        grpRatierlich = new javax.swing.ButtonGroup();
        grpRestGesamthaftung = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        ffield_haftungszeit = new javax.swing.JFormattedTextField();
        radio_vollhaftung = new javax.swing.JRadioButton();
        radio_kombiniert = new javax.swing.JRadioButton();
        radio_ratierlich = new javax.swing.JRadioButton();
        panel_ratierlich = new javax.swing.JPanel();
        radio_ratierlichTaggenau = new javax.swing.JRadioButton();
        radio_ratierlichIntervall = new javax.swing.JRadioButton();
        spinnerIntervalle = new javax.swing.JSpinner();
        panel_kombiniert = new javax.swing.JPanel();
        ffield_vollhaftungszeit = new javax.swing.JFormattedTextField();
        radio_gesamthaftungszeit = new javax.swing.JRadioButton();
        radio_resthaftung = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelProduktHaftung.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        ffield_haftungszeit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0 Monaten"))));
        ffield_haftungszeit.setName("ffield_haftungszeit"); // NOI18N

        grpHaftungart.add(radio_vollhaftung);
        radio_vollhaftung.setSelected(true);
        radio_vollhaftung.setText(resourceMap.getString("radio_vollhaftung.text")); // NOI18N
        radio_vollhaftung.setName("radio_vollhaftung"); // NOI18N
        radio_vollhaftung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_vollhaftungActionPerformed(evt);
            }
        });

        grpHaftungart.add(radio_kombiniert);
        radio_kombiniert.setText(resourceMap.getString("radio_kombiniert.text")); // NOI18N
        radio_kombiniert.setName("radio_kombiniert"); // NOI18N
        radio_kombiniert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_kombiniertActionPerformed(evt);
            }
        });

        grpHaftungart.add(radio_ratierlich);
        radio_ratierlich.setText(resourceMap.getString("radio_ratierlich.text")); // NOI18N
        radio_ratierlich.setName("radio_ratierlich"); // NOI18N
        radio_ratierlich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_ratierlichActionPerformed(evt);
            }
        });

        panel_ratierlich.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("panel_ratierlich.border.title"))); // NOI18N
        panel_ratierlich.setName("panel_ratierlich"); // NOI18N

        grpRatierlich.add(radio_ratierlichTaggenau);
        radio_ratierlichTaggenau.setSelected(true);
        radio_ratierlichTaggenau.setText(resourceMap.getString("radio_ratierlichTaggenau.text")); // NOI18N
        radio_ratierlichTaggenau.setName("radio_ratierlichTaggenau"); // NOI18N

        grpRatierlich.add(radio_ratierlichIntervall);
        radio_ratierlichIntervall.setText(resourceMap.getString("radio_ratierlichIntervall.text")); // NOI18N
        radio_ratierlichIntervall.setName("radio_ratierlichIntervall"); // NOI18N

        spinnerIntervalle.setEnabled(false);
        spinnerIntervalle.setName("spinnerIntervalle"); // NOI18N

        javax.swing.GroupLayout panel_ratierlichLayout = new javax.swing.GroupLayout(panel_ratierlich);
        panel_ratierlich.setLayout(panel_ratierlichLayout);
        panel_ratierlichLayout.setHorizontalGroup(
            panel_ratierlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ratierlichLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_ratierlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ratierlichLayout.createSequentialGroup()
                        .addComponent(radio_ratierlichIntervall)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerIntervalle, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(radio_ratierlichTaggenau))
                .addContainerGap(369, Short.MAX_VALUE))
        );
        panel_ratierlichLayout.setVerticalGroup(
            panel_ratierlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ratierlichLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radio_ratierlichTaggenau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ratierlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_ratierlichIntervall)
                    .addComponent(spinnerIntervalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_kombiniert.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("panel_kombiniert.border.title"))); // NOI18N
        panel_kombiniert.setName("panel_kombiniert"); // NOI18N

        ffield_vollhaftungszeit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0 Monaten"))));
        ffield_vollhaftungszeit.setName("ffield_vollhaftungszeit"); // NOI18N

        grpRestGesamthaftung.add(radio_gesamthaftungszeit);
        radio_gesamthaftungszeit.setText(resourceMap.getString("radio_gesamthaftungszeit.text")); // NOI18N
        radio_gesamthaftungszeit.setName("radio_gesamthaftungszeit"); // NOI18N

        grpRestGesamthaftung.add(radio_resthaftung);
        radio_resthaftung.setSelected(true);
        radio_resthaftung.setText(resourceMap.getString("radio_resthaftung.text")); // NOI18N
        radio_resthaftung.setName("radio_resthaftung"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout panel_kombiniertLayout = new javax.swing.GroupLayout(panel_kombiniert);
        panel_kombiniert.setLayout(panel_kombiniertLayout);
        panel_kombiniertLayout.setHorizontalGroup(
            panel_kombiniertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kombiniertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kombiniertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_kombiniertLayout.createSequentialGroup()
                        .addComponent(radio_resthaftung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radio_gesamthaftungszeit))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_kombiniertLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(ffield_vollhaftungszeit)))
                .addContainerGap(342, Short.MAX_VALUE))
        );
        panel_kombiniertLayout.setVerticalGroup(
            panel_kombiniertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kombiniertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kombiniertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ffield_vollhaftungszeit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kombiniertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_resthaftung)
                    .addComponent(radio_gesamthaftungszeit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(ffield_haftungszeit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(radio_vollhaftung)
                            .addComponent(radio_ratierlich)
                            .addComponent(radio_kombiniert))
                        .addContainerGap(420, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_ratierlich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_kombiniert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ffield_haftungszeit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_vollhaftung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_ratierlich)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_kombiniert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_ratierlich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_kombiniert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radio_vollhaftungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_vollhaftungActionPerformed
        setVisible();
    }//GEN-LAST:event_radio_vollhaftungActionPerformed

    private void radio_ratierlichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_ratierlichActionPerformed
        setVisible();
    }//GEN-LAST:event_radio_ratierlichActionPerformed

    private void radio_kombiniertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_kombiniertActionPerformed
        setVisible();
    }//GEN-LAST:event_radio_kombiniertActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JFormattedTextField ffield_haftungszeit;
    public javax.swing.JFormattedTextField ffield_vollhaftungszeit;
    public javax.swing.ButtonGroup grpHaftungart;
    public javax.swing.ButtonGroup grpRatierlich;
    public javax.swing.ButtonGroup grpRestGesamthaftung;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JPanel panel_kombiniert;
    public javax.swing.JPanel panel_ratierlich;
    public javax.swing.JRadioButton radio_gesamthaftungszeit;
    public javax.swing.JRadioButton radio_kombiniert;
    public javax.swing.JRadioButton radio_ratierlich;
    public javax.swing.JRadioButton radio_ratierlichIntervall;
    public javax.swing.JRadioButton radio_ratierlichTaggenau;
    public javax.swing.JRadioButton radio_resthaftung;
    public javax.swing.JRadioButton radio_vollhaftung;
    public javax.swing.JSpinner spinnerIntervalle;
    // End of variables declaration//GEN-END:variables
    
}