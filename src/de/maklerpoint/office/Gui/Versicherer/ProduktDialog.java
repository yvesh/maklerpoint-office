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
 * ProduktDialog.java
 *
 * Created on 18.09.2010, 10:27:03
 */
package de.maklerpoint.office.Gui.Versicherer;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemProdukte;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Sparten.SpartenDialog;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Gui.Waehrungen.WaehrungenDialog;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Sparten.SpartenObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.Tools.ProdukteSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class ProduktDialog extends javax.swing.JDialog {

    private boolean update = false;
    private ProduktObj produkt = null;
    private VersichererObj vers = null;

    /** Creates new form ProduktDialog */
    public ProduktDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        initComponents();
        setupCombos();
    }

    public ProduktDialog(java.awt.Frame parent, boolean modal, ProduktObj produkt) {
        super(parent, modal);
        this.produkt = produkt;
        this.update = true;
        initComponents();
        setupCombos();
        this.load();
    }

    public ProduktDialog(java.awt.Frame parent, boolean modal, VersichererObj vers) {
        super(parent, modal);
        this.vers = vers;
        this.update = false;
        initComponents();
        setupCombos();
        this.setVers();
    }

    private void setupCombos() {
        this.combo_gesellschaft.setModel(new DefaultComboBoxModel(VersicherungsRegistry.getVersicherer(Status.NORMAL)));
        this.combo_sparte.setModel(new DefaultComboBoxModel(VersicherungsRegistry.getSparten(true)));
        this.combo_waehrung.setModel(new DefaultComboBoxModel(VersicherungsRegistry.getWaehrungen(true)));
        setValue();
        setCurrency(null);
    }

    private void setValue() {
        this.ffield_bwsumme.setValue(0.00);
        this.ffield_npgesamt.setValue(0.00);
        this.ffield_nppauschal.setValue(0.00);
        this.ffield_npzusatz.setValue(0.00);
        this.ffield_sb.setValue(0.00);
        this.ffield_vssumme.setValue(0.00);
    }
    
    private void setCurrency(WaehrungenObj waer){
        Locale loc = Locale.GERMANY;

        
        try {
            if(waer == null)
                waer = (WaehrungenObj) this.combo_waehrung.getSelectedItem();

            // TODO Irgendwann aendern
            if (waer.getId() == 1) {
                loc = Locale.GERMANY;
            } else if (waer.getId() == 2) {
                loc = Locale.GERMAN;
            } else if (waer.getId() == 3) {
                loc = Locale.US;
            } else if (waer.getId() == 4) {
                loc = Locale.UK;
            } else if (waer.getId() == 5) {
                loc = new Locale("de", "CH");
            }
        } catch (Exception e) {
        }

        ffield_bwsumme.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_bwsumme.setValue(ffield_bwsumme.getValue());
        
        ffield_npgesamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_npgesamt.setValue(ffield_npgesamt.getValue());
        
        ffield_nppauschal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_nppauschal.setValue(ffield_nppauschal.getValue());
        
        ffield_npzusatz.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_npzusatz.setValue(ffield_npzusatz.getValue());
        
        ffield_sb.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_sb.setValue(ffield_sb.getValue());
        
        ffield_vssumme.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance(loc))));
        
        ffield_vssumme.setValue(ffield_vssumme.getValue());
    }

    private void setVers() {
        if (vers == null) {
            return;
        }

//        System.out.println("Vers: " + vers.getName());

        this.combo_gesellschaft.setEditable(true);
        this.combo_gesellschaft.setSelectedItem(vers);
        this.combo_gesellschaft.setEditable(false);
    }

    private void load() {
        if (update == false || this.produkt == null) {
            return;
        }

//        this.combo_art;
//        this.combo_gesellschaft;
//        this.combo_risiko;
//        this.combo_sparte;
//        this.combo_versicherungsart;
//
//        this.check_vermittelbar;
//
//        this.field_bedingungen;
//        this.field_bezeichnung;
//        this.field_custom1;
//        this.field_custom2;
//        this.field_custom3;
//        this.field_custom4;
//        this.field_custom5;
//
//        this.field_kuerzel;
//        this.field_tarif;
//        this.field_tarifbasis;
//
//        this.text_comments;
//        this.text_zusatzinfo;
//
//        this.ffield_bwsumme;
//        this.ffield_npgesamt;
//        this.ffield_nppauschal;
//        this.ffield_npzusatz;
//        this.ffield_sb;
//        this.ffield_vssumme;


        this.combo_art.setSelectedIndex(produkt.getArt()); // TODO Needs updating
        this.combo_gesellschaft.setSelectedItem(VersicherungsRegistry.getVersicher(produkt.getVersichererId()));
        this.combo_risiko.setSelectedIndex(produkt.getRisikotyp()); //TODO Needs updating
        this.combo_sparte.setSelectedItem(VersicherungsRegistry.getSparte(produkt.getSparteId())); // Already updated
        this.combo_versicherungsart.setSelectedItem(produkt.getVersicherungsart()); // TODO: Needs Updating

        this.check_vermittelbar.setSelected(produkt.isVermittelbar());

        this.field_bedingungen.setText(produkt.getBedingungen());
        this.field_bezeichnung.setText(produkt.getBezeichnung());
        this.field_custom1.setText(produkt.getCustom1());
        this.field_custom2.setText(produkt.getCustom2());
        this.field_custom3.setText(produkt.getCustom3());
        this.field_custom4.setText(produkt.getCustom4());
        this.field_custom5.setText(produkt.getCustom5());
        
        this.combo_waehrung.setSelectedItem(VersicherungsRegistry.getWaehrung(produkt.getWaehrung()));        

        this.field_kuerzel.setText(produkt.getKuerzel());
        this.field_tarif.setText(produkt.getTarif());
        this.field_tarifbasis.setText(produkt.getTarifBasis());

        this.text_comments.setText(produkt.getComments());
        this.text_zusatzinfo.setText(produkt.getZusatzInfo());
        this.ffield_bwsumme.setValue(produkt.getBewertungsSumme());
        this.ffield_npgesamt.setValue(produkt.getNettopraemieGesamt());
        this.ffield_nppauschal.setValue(produkt.getNettopraemiePauschal());
        this.ffield_npzusatz.setValue(produkt.getNettopraemieZusatz());
        this.ffield_sb.setValue(produkt.getSelbstbeteiligung());
        this.ffield_vssumme.setValue(produkt.getVersicherungsSumme());

        this.setTitle("Produkt: " + produkt.getBezeichnung());

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane_holder = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_gesellschaft = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        combo_sparte = new javax.swing.JComboBox();
        btn_sparten = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        combo_art = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        field_tarif = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        field_tarifbasis = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        field_bezeichnung = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        field_kuerzel = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        check_vermittelbar = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        combo_waehrung = new javax.swing.JComboBox();
        btn_waehrung = new javax.swing.JButton();
        panel_tarifdaten = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        combo_versicherungsart = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        combo_risiko = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        ffield_vssumme = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        ffield_bwsumme = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ffield_sb = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        ffield_nppauschal = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        ffield_npzusatz = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        ffield_npgesamt = new javax.swing.JFormattedTextField();
        jSeparator5 = new javax.swing.JSeparator();
        field_bedingungen = new javax.swing.JTextField();
        btnMaxBedingungen = new javax.swing.JButton();
        pane_sonstiges = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        field_custom1 = new javax.swing.JTextField();
        btnMaxCustom1 = new javax.swing.JButton();
        btnMaxCustom2 = new javax.swing.JButton();
        btnMaxCustom3 = new javax.swing.JButton();
        btnMaxCustom4 = new javax.swing.JButton();
        btnMaxCustom5 = new javax.swing.JButton();
        field_custom5 = new javax.swing.JTextField();
        field_custom4 = new javax.swing.JTextField();
        field_custom3 = new javax.swing.JTextField();
        field_custom2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnMaxTextKommentar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_zusatzinfo = new javax.swing.JTextArea();
        btnMaxZusatzinfo = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(ProduktDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        pane_holder.setName("pane_holder"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_gesellschaft.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_gesellschaft.setName("combo_gesellschaft"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_sparte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sparte.setName("combo_sparte"); // NOI18N

        btn_sparten.setIcon(resourceMap.getIcon("btn_sparten.icon")); // NOI18N
        btn_sparten.setText(resourceMap.getString("btn_sparten.text")); // NOI18N
        btn_sparten.setName("btn_sparten"); // NOI18N
        btn_sparten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_spartenActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        combo_art.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_art.setName("combo_art"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_tarif.setText(resourceMap.getString("field_tarif.text")); // NOI18N
        field_tarif.setName("field_tarif"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_tarifbasis.setName("field_tarifbasis"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_bezeichnung.setName("field_bezeichnung"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        field_kuerzel.setName("field_kuerzel"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        check_vermittelbar.setSelected(true);
        check_vermittelbar.setText(resourceMap.getString("check_vermittelbar.text")); // NOI18N
        check_vermittelbar.setName("check_vermittelbar"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        combo_waehrung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_waehrung.setName("combo_waehrung"); // NOI18N
        combo_waehrung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_waehrungActionPerformed(evt);
            }
        });

        btn_waehrung.setIcon(resourceMap.getIcon("btn_waehrung.icon")); // NOI18N
        btn_waehrung.setName("btn_waehrung"); // NOI18N
        btn_waehrung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_waehrungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_basisLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(combo_gesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_basisLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                                .addComponent(combo_sparte, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_sparten)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(combo_art, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(field_kuerzel)
                            .addComponent(field_tarifbasis)
                            .addComponent(field_tarif)
                            .addComponent(field_bezeichnung, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(check_vermittelbar)
                        .addContainerGap(243, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(combo_waehrung, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_waehrung)
                        .addContainerGap())))
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_gesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(combo_sparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_sparten))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_art, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(field_bezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(field_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(field_tarifbasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(field_kuerzel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_waehrung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24))
                    .addComponent(btn_waehrung))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(check_vermittelbar)
                .addContainerGap())
        );

        pane_holder.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_tarifdaten.setName("panel_tarifdaten"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        combo_versicherungsart.setName("combo_versicherungsart"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        combo_risiko.setName("combo_risiko"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        ffield_vssumme.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_vssumme.setName("ffield_vssumme"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        ffield_bwsumme.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_bwsumme.setText(resourceMap.getString("ffield_bwsumme.text")); // NOI18N
        ffield_bwsumme.setName("ffield_bwsumme"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        ffield_sb.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_sb.setText(resourceMap.getString("ffield_sb.text")); // NOI18N
        ffield_sb.setName("ffield_sb"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        ffield_nppauschal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_nppauschal.setText(resourceMap.getString("ffield_nppauschal.text")); // NOI18N
        ffield_nppauschal.setName("ffield_nppauschal"); // NOI18N
        ffield_nppauschal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ffield_nppauschalFocusLost(evt);
            }
        });

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        ffield_npzusatz.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_npzusatz.setText(resourceMap.getString("ffield_npzusatz.text")); // NOI18N
        ffield_npzusatz.setName("ffield_npzusatz"); // NOI18N
        ffield_npzusatz.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ffield_npzusatzFocusLost(evt);
            }
        });

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        ffield_npgesamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_npgesamt.setText(resourceMap.getString("ffield_npgesamt.text")); // NOI18N
        ffield_npgesamt.setName("ffield_npgesamt"); // NOI18N

        jSeparator5.setName("jSeparator5"); // NOI18N

        field_bedingungen.setName("field_bedingungen"); // NOI18N

        btnMaxBedingungen.setIcon(resourceMap.getIcon("btnMaxBedingungen.icon")); // NOI18N
        btnMaxBedingungen.setToolTipText(resourceMap.getString("btnMaxBedingungen.toolTipText")); // NOI18N
        btnMaxBedingungen.setName("btnMaxBedingungen"); // NOI18N
        btnMaxBedingungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxBedingungenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tarifdatenLayout = new javax.swing.GroupLayout(panel_tarifdaten);
        panel_tarifdaten.setLayout(panel_tarifdatenLayout);
        panel_tarifdatenLayout.setHorizontalGroup(
            panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tarifdatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tarifdatenLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(combo_versicherungsart, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tarifdatenLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(combo_risiko, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addGroup(panel_tarifdatenLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(42, 42, 42)
                        .addComponent(field_bedingungen, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxBedingungen))
                    .addGroup(panel_tarifdatenLayout.createSequentialGroup()
                        .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addGap(23, 23, 23)
                        .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ffield_vssumme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(ffield_npgesamt, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(ffield_npzusatz, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(ffield_nppauschal, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(ffield_sb, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(ffield_bwsumme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_tarifdatenLayout.setVerticalGroup(
            panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tarifdatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(combo_versicherungsart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_risiko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ffield_vssumme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(ffield_bwsumme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(ffield_sb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(ffield_nppauschal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(ffield_npzusatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(ffield_npgesamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_tarifdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_bedingungen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addComponent(btnMaxBedingungen))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pane_holder.addTab(resourceMap.getString("panel_tarifdaten.TabConstraints.tabTitle"), panel_tarifdaten); // NOI18N

        pane_sonstiges.setName("pane_sonstiges"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        field_custom1.setName("field_custom1"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        btnMaxCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom4.setToolTipText(resourceMap.getString("btnMaxCustom4.toolTipText")); // NOI18N
        btnMaxCustom4.setName("btnMaxCustom4"); // NOI18N
        btnMaxCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom4ActionPerformed(evt);
            }
        });

        btnMaxCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom5.setToolTipText(resourceMap.getString("btnMaxCustom5.toolTipText")); // NOI18N
        btnMaxCustom5.setName("btnMaxCustom5"); // NOI18N
        btnMaxCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom5ActionPerformed(evt);
            }
        });

        field_custom5.setName("field_custom5"); // NOI18N

        field_custom4.setName("field_custom4"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane1.setViewportView(text_comments);

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        btnMaxTextKommentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxTextKommentar.setToolTipText(resourceMap.getString("btnMaxTextKommentar.toolTipText")); // NOI18N
        btnMaxTextKommentar.setName("btnMaxTextKommentar"); // NOI18N
        btnMaxTextKommentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxTextKommentarActionPerformed(evt);
            }
        });

        jSeparator3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        text_zusatzinfo.setColumns(20);
        text_zusatzinfo.setRows(5);
        text_zusatzinfo.setName("text_zusatzinfo"); // NOI18N
        jScrollPane2.setViewportView(text_zusatzinfo);

        btnMaxZusatzinfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxZusatzinfo.setToolTipText(resourceMap.getString("btnMaxZusatzinfo.toolTipText")); // NOI18N
        btnMaxZusatzinfo.setName("btnMaxZusatzinfo"); // NOI18N
        btnMaxZusatzinfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxZusatzinfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pane_sonstigesLayout = new javax.swing.GroupLayout(pane_sonstiges);
        pane_sonstiges.setLayout(pane_sonstigesLayout);
        pane_sonstigesLayout.setHorizontalGroup(
            pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxZusatzinfo)))
                .addContainerGap())
            .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pane_sonstigesLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)
                        .addComponent(jLabel8))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                            .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnMaxCustom3))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                            .addComponent(field_custom5, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnMaxCustom5))
                        .addGroup(pane_sonstigesLayout.createSequentialGroup()
                            .addComponent(field_custom4, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnMaxCustom4))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                            .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGap(9, 9, 9)))
        );
        pane_sonstigesLayout.setVerticalGroup(
            pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                .addContainerGap(181, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxZusatzinfo)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_sonstigesLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addComponent(btnMaxCustom1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addComponent(btnMaxCustom2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addComponent(btnMaxCustom3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_custom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addComponent(btnMaxCustom4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pane_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_custom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addComponent(btnMaxCustom5))
                    .addGap(280, 280, 280)))
        );

        pane_holder.addTab(resourceMap.getString("pane_sonstiges.TabConstraints.tabTitle"), pane_sonstiges); // NOI18N

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pane_holder, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(480, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pane_holder, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed

    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed

    private void btnMaxCustom4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom4ActionPerformed
        MaximizeHelper.openMax(this.field_custom4, "Benutzerdefiniert 4");
}//GEN-LAST:event_btnMaxCustom4ActionPerformed

    private void btnMaxCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom5ActionPerformed
        MaximizeHelper.openMax(this.field_custom5, "Benutzerdefiniert 5");
}//GEN-LAST:event_btnMaxCustom5ActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    private void btnMaxZusatzinfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxZusatzinfoActionPerformed
        MaximizeHelper.openMax(this.text_zusatzinfo, "Zusatz Informationen");
    }//GEN-LAST:event_btnMaxZusatzinfoActionPerformed

    private void btnMaxBedingungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxBedingungenActionPerformed
        MaximizeHelper.openMax(this.field_bedingungen, "Bedingungen");
    }//GEN-LAST:event_btnMaxBedingungenActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.update == false) {
            int dial = JOptionPane.NO_OPTION;
            dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
                    + "gehen in diesem Fall verloren.", "Wollen Sie das Fenster schließen?", JOptionPane.YES_NO_OPTION);
            if (dial == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        } else {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            this.produkt = new ProduktObj();
        }

        if (this.field_bezeichnung.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Bezeichnung für das Produkt an.");
            return;
        }

        produkt.setArt(this.combo_art.getSelectedIndex()); // TODo

        VersichererObj vs = (VersichererObj) this.combo_gesellschaft.getSelectedItem();
        produkt.setVersichererId(vs.getId());

        produkt.setRisikotyp(this.combo_risiko.getSelectedIndex()); // TODO

        try {
            SpartenObj sp = (SpartenObj) this.combo_sparte.getSelectedItem();
            produkt.setSparteId(sp.getId());
            
            WaehrungenObj waer = (WaehrungenObj) this.combo_waehrung.getSelectedItem();
            produkt.setWaehrung(waer.getId());            
        
        } catch (Exception e) {
            
        }

        produkt.setVersicherungsart(combo_versicherungsart.getSelectedIndex()); // TODO

        produkt.setVermittelbar(check_vermittelbar.isSelected());

        produkt.setBedingungen(field_bedingungen.getText());
        produkt.setBezeichnung(field_bezeichnung.getText());
        produkt.setCustom1(field_custom1.getText());
        produkt.setCustom2(field_custom2.getText());
        produkt.setCustom3(field_custom3.getText());
        produkt.setCustom4(field_custom4.getText());
        produkt.setCustom5(field_custom5.getText());

        produkt.setKuerzel(field_kuerzel.getText());
        produkt.setTarif(field_tarif.getText());
        produkt.setTarifBasis(field_tarifbasis.getText());

        produkt.setComments(text_comments.getText());
        produkt.setZusatzInfo(text_zusatzinfo.getText());

        produkt.setBewertungsSumme((Double) ffield_bwsumme.getValue());
        produkt.setNettopraemieGesamt((Double) ffield_npgesamt.getValue());
        produkt.setNettopraemiePauschal((Double) ffield_nppauschal.getValue());
        produkt.setNettopraemieZusatz((Double) ffield_npzusatz.getValue());
        produkt.setSelbstbeteiligung((Double) ffield_sb.getValue());
        produkt.setVersicherungsSumme((Double) ffield_vssumme.getValue());

        produkt.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            if (!update) {
                produkt.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                produkt.setStatus(Status.NORMAL);

                FilesystemProdukte.createProduktDirectory(produkt);

                ProdukteSQLMethods.insertIntoProdukte(DatabaseConnection.open(), produkt);
            } else {
                boolean success = ProdukteSQLMethods.updateProdukte(DatabaseConnection.open(), produkt);

                if (!success) {
                    System.out.println("ADD Exception: Update des Produkts fehlgeschlagen..");
                    Log.databaselogger.fatal("Konnte das Produkt nicht aktualisieren.");
                }
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Das Produkt konnte nicht gespeichert werden.", e);
            ShowException.showException("Datenbankfehler: Beim Speichern des Produktes ist ein Fehler aufgetretten.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkt nicht speichern");
        } catch (IOException e) {
            Log.databaselogger.fatal("Die Verzeichnisse für das Produkt konnten nicht erstellt werden.", e);
            ShowException.showException("Dateisystemfehler: Die Dateistruktur für das Produkt konnte nicht erstellt werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkt nicht speichern");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btn_spartenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_spartenActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        spartenDialog = new SpartenDialog(mainFrame, true);
        spartenDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(spartenDialog);

        this.combo_sparte.setModel(new DefaultComboBoxModel(VersicherungsRegistry.getSparten(true)));
    }//GEN-LAST:event_btn_spartenActionPerformed

    private void ffield_npzusatzFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ffield_npzusatzFocusLost
        double pauschal = ((Number) ffield_nppauschal.getValue()).doubleValue();
        double zusatz = ((Number) ffield_npzusatz.getValue()).doubleValue();

        double price = pauschal + zusatz;
//        System.out.println("Preis: " + price);
        this.ffield_npgesamt.setValue(price);
    }//GEN-LAST:event_ffield_npzusatzFocusLost

    private void ffield_nppauschalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ffield_nppauschalFocusLost
        double pauschal = ((Number) ffield_nppauschal.getValue()).doubleValue();
        double zusatz = ((Number) ffield_npzusatz.getValue()).doubleValue();

        double price = pauschal + zusatz;
//        System.out.println("Preis: " + price);
        this.ffield_npgesamt.setValue(price);
    }//GEN-LAST:event_ffield_nppauschalFocusLost

    private void btn_waehrungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_waehrungActionPerformed
        if (waehrungenDialog == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            waehrungenDialog = new WaehrungenDialog(mainFrame, true);
            waehrungenDialog.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(waehrungenDialog);
    }//GEN-LAST:event_btn_waehrungActionPerformed

    private void combo_waehrungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_waehrungActionPerformed
        this.setCurrency(null);
    }//GEN-LAST:event_combo_waehrungActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ProduktDialog dialog = new ProduktDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnMaxBedingungen;
    public javax.swing.JButton btnMaxCustom1;
    public javax.swing.JButton btnMaxCustom2;
    public javax.swing.JButton btnMaxCustom3;
    public javax.swing.JButton btnMaxCustom4;
    public javax.swing.JButton btnMaxCustom5;
    public javax.swing.JButton btnMaxTextKommentar;
    public javax.swing.JButton btnMaxZusatzinfo;
    public javax.swing.JButton btnSave;
    public javax.swing.JButton btn_sparten;
    public javax.swing.JButton btn_waehrung;
    public javax.swing.JCheckBox check_vermittelbar;
    public javax.swing.JComboBox combo_art;
    public javax.swing.JComboBox combo_gesellschaft;
    public javax.swing.JComboBox combo_risiko;
    public javax.swing.JComboBox combo_sparte;
    public javax.swing.JComboBox combo_versicherungsart;
    public javax.swing.JComboBox combo_waehrung;
    public javax.swing.JFormattedTextField ffield_bwsumme;
    public javax.swing.JFormattedTextField ffield_npgesamt;
    public javax.swing.JFormattedTextField ffield_nppauschal;
    public javax.swing.JFormattedTextField ffield_npzusatz;
    public javax.swing.JFormattedTextField ffield_sb;
    public javax.swing.JFormattedTextField ffield_vssumme;
    public javax.swing.JTextField field_bedingungen;
    public javax.swing.JTextField field_bezeichnung;
    public javax.swing.JTextField field_custom1;
    public javax.swing.JTextField field_custom2;
    public javax.swing.JTextField field_custom3;
    public javax.swing.JTextField field_custom4;
    public javax.swing.JTextField field_custom5;
    public javax.swing.JTextField field_kuerzel;
    public javax.swing.JTextField field_tarif;
    public javax.swing.JTextField field_tarifbasis;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JSeparator jSeparator5;
    public javax.swing.JTabbedPane pane_holder;
    public javax.swing.JPanel pane_sonstiges;
    public javax.swing.JPanel panel_basis;
    public javax.swing.JPanel panel_tarifdaten;
    public javax.swing.JTextArea text_comments;
    public javax.swing.JTextArea text_zusatzinfo;
    // End of variables declaration//GEN-END:variables
    private JDialog spartenDialog;
    private JDialog waehrungenDialog;
}
