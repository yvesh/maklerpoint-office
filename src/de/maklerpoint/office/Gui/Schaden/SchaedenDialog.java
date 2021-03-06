/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       27.07.2011 10:42:22
 *  File:       KontakteRegistry
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */

/*
 * SchaedenDialog.java
 *
 * Created on 05.07.2011, 10:17:44
 */
package de.maklerpoint.office.Gui.Schaden;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Konstanten.Schaeden;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schaeden.Tools.SchaedenHelper;
import de.maklerpoint.office.Schaeden.Tools.SchaedenSQLMethods;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SchaedenDialog extends javax.swing.JDialog {

    private boolean update = false;
    private SchadenObj schaden = null;
    private VertragObj vertrag = null;
    private String kdnr = null;

    /** Creates new form SchaedenDialog */
    public SchaedenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        this.schaden = null;
        this.vertrag = null;
        this.kdnr = null;
        initComponents();
        setUp();
    }

    public SchaedenDialog(java.awt.Frame parent, boolean modal, String kdnr) {
        super(parent, modal);
        this.update = false;
        this.schaden = null;
        this.vertrag = null;
        this.kdnr = kdnr;
        initComponents();
        setUp();
    }

    public SchaedenDialog(java.awt.Frame parent, boolean modal, VertragObj vtr) {
        super(parent, modal);
        this.update = false;
        this.schaden = null;
        this.kdnr = vtr.getKundenKennung();
        this.vertrag = vtr;
        initComponents();
        setUp();
    }

    public SchaedenDialog(java.awt.Frame parent, boolean modal, SchadenObj sch) {
        super(parent, modal);
        this.update = true;
        this.vertrag = VertragRegistry.getVertrag(sch.getVertragsId());
        this.kdnr = sch.getKundenNr();
        this.schaden = sch;
        initComponents();
        setUp();
    }

    private void setUp() {
        loadCombos();
        this.ffield_schadenhoehe.setValue(0.00);
        DateTime dt = new DateTime();
        dt.plusDays(14);
        this.date_wiedervorlage.setDate(dt.toDate());
        this.date_schadenTime.setDate(new Date());
        this.date_vuWeiterleitungTime.setDate(new Date());
        this.date_vuStatusDatum.setDate(new Date());
        this.spinnerSchadenNr.setEnabled(true);
        this.combo_kunde.setEnabled(true);
        this.combo_vertrag.setEnabled(true);
        if(this.schaden == null){
            this.setTitle("Neuer Schadensfall");
        } else {
            this.setTitle("Schadensfall " + schaden.getSchadenNr() + " (" 
                    + KundenRegistry.getKunde(schaden.getKundenNr()).toString() + ")");
        }
        
        loadSchaden();
    }

    private void loadCombos() {
        this.combo_bearbeiter.setModel(new DefaultComboBoxModel(ComboBoxGetter.getBenutzerCombo(null)));
        
        this.combo_bearbeiter.setSelectedItem(BasicRegistry.currentUser);

        Object[] kunden = ComboBoxGetter.getAlleKundenCombo(null, Status.NORMAL);
        this.combo_kunde.setModel(new DefaultComboBoxModel(kunden));
                               
        try {
            if (schaden == null) {
                this.spinnerSchadenNr.setValue(SchaedenHelper.getNextSchadennummer(DatabaseConnection.open()));
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex); // TODO
            this.spinnerSchadenNr.setValue(0);
            this.spinnerSchadenNr.setEnabled(false);
        }

        this.combo_meldungArt.setModel(new DefaultComboBoxModel(Schaeden.MELDUNG_ART));
        this.combo_meldungVon.setModel(new DefaultComboBoxModel(kunden));
        this.combo_schadenKategorie.setModel(new DefaultComboBoxModel(new Object[]{""})); // TODO
        this.combo_vuMeldungArt.setModel(new DefaultComboBoxModel(Schaeden.MELDUNG_ART));

        if (kdnr != null) {
            this.combo_kunde.setSelectedItem(KundenRegistry.getKunde(kdnr));
            this.combo_vertrag.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragCombo(null, kdnr)));
        } else {
            if (kunden != null) {
                if(kunden[0].getClass().equals(KundenObj.class)) {
                    KundenObj knd = (KundenObj) kunden[0];
                    this.combo_vertrag.setModel(new DefaultComboBoxModel(
                            ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));
                } else if(kunden[0].getClass().equals(FirmenObj.class)) {
                    FirmenObj knd = (FirmenObj) kunden[0];
                    this.combo_vertrag.setModel(new DefaultComboBoxModel(
                            ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));
                }
            }
        }
    }

    private void loadSchaden() {
        if (schaden == null) {
            return;
        }

        this.area_comments.setText(schaden.getNotiz());
        this.area_intern.setText(schaden.getInterneInfo());
        this.area_schadenHergang.setText(schaden.getSchadenHergang());
        this.area_schadenUmfang.setText(schaden.getSchadenUmfang());

        this.check_polizei.setSelected(schaden.isSchadenPolizei());

        if (schaden.getMeldungVon().equals(schaden.getKundenNr())) {
            this.check_vnmelder.setSelected(true);
        }

        this.check_vuGutachten.setSelected(schaden.isVuGutachten());

        if (schaden.getWiedervorlagenId() != -1) {
            this.check_wiedervorlage.setSelected(true);
            this.date_wiedervorlage.setEnabled(true);
            this.date_wiedervorlage.setDate(null); // TODO
        }

        this.combo_bearbeiter.setSelectedItem(BenutzerRegistry.getBenutzer(schaden.getSchadenBearbeiter()));
        this.combo_kunde.setSelectedItem(KundenRegistry.getKunde(schaden.getKundenNr()));
        this.combo_meldungArt.setSelectedItem(schaden.getMeldungArt());
        this.combo_meldungVon.setSelectedItem(KundenRegistry.getKunde(schaden.getKundenNr()));
        this.combo_schadenAbrechnungArt.setSelectedIndex(schaden.getSchadenAbrechnungArt());
        this.combo_schadenKategorie.setSelectedItem(schaden.getSchadenKategorie());
        this.combo_status.setSelectedIndex(schaden.getStatus());
        
        this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, schaden.getKundenNr())));
        
        this.combo_vertrag.setSelectedItem(VertragRegistry.getVertrag(schaden.getVertragsId()));
        this.combo_vuMeldungArt.setSelectedItem(schaden.getVuMeldungArt());

        this.date_schadenTime.setDate(schaden.getSchaedenTime());
        this.date_vuStatusDatum.setDate(schaden.getVuStatusDatum());
        this.date_vuWeiterleitungTime.setDate(schaden.getVuWeiterleitungTime());

        this.field_vuSchadennummer.setText(schaden.getVuSchadennummer());
        this.ffield_schadenhoehe.setValue(schaden.getSchadenHoehe());

        this.field_risiko.setText(schaden.getRisiko());
        this.field_schadenOrt.setText(schaden.getRisiko());

        this.spinnerSchadenNr.setValue(Integer.valueOf(schaden.getSchadenNr()));
        this.spinnerSchadenNr.setEnabled(false); // Nicht mehr änderbar
        this.combo_kunde.setEnabled(false);
        this.combo_vertrag.setEnabled(false);

        this.field_custom1.setText(schaden.getCustom1());
        this.field_custom2.setText(schaden.getCustom2());
        this.field_custom3.setText(schaden.getCustom3());
        this.field_custom4.setText(schaden.getCustom4());
        this.field_custom5.setText(schaden.getCustom5());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneschaeden = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_vertrag = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        combo_kunde = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        spinnerSchadenNr = new javax.swing.JSpinner();
        jSeparator4 = new javax.swing.JSeparator();
        combo_bearbeiter = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        combo_meldungArt = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        combo_meldungVon = new javax.swing.JComboBox();
        check_vnmelder = new javax.swing.JCheckBox();
        jSeparator12 = new javax.swing.JSeparator();
        check_wiedervorlage = new javax.swing.JCheckBox();
        date_wiedervorlage = new com.toedter.calendar.JDateChooser();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        field_risiko = new javax.swing.JTextField();
        panel_schaden = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        date_schadenTime = new com.toedter.calendar.JDateChooser();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        combo_schadenKategorie = new javax.swing.JComboBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        field_schadenOrt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        area_schadenUmfang = new javax.swing.JTextArea();
        btnMaxComments1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        area_schadenHergang = new javax.swing.JTextArea();
        btnMaxComments2 = new javax.swing.JButton();
        check_polizei = new javax.swing.JCheckBox();
        jSeparator7 = new javax.swing.JSeparator();
        ffield_schadenhoehe = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        combo_schadenAbrechnungArt = new javax.swing.JComboBox();
        panel_vu = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        date_vuWeiterleitungTime = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        combo_vuMeldungArt = new javax.swing.JComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        field_vuSchadennummer = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        date_vuStatusDatum = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        combo_status = new javax.swing.JComboBox();
        jSeparator11 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_intern = new javax.swing.JTextArea();
        btnMaxComments = new javax.swing.JButton();
        check_vuGutachten = new javax.swing.JCheckBox();
        panel_sonstiges = new javax.swing.JPanel();
        jSeparator17 = new javax.swing.JSeparator();
        btnMaxCustom5 = new javax.swing.JButton();
        field_custom5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        field_custom4 = new javax.swing.JTextField();
        btnMaxCustom4 = new javax.swing.JButton();
        btnMaxCustom3 = new javax.swing.JButton();
        btnMaxCustom2 = new javax.swing.JButton();
        btnMaxCustom1 = new javax.swing.JButton();
        field_custom1 = new javax.swing.JTextField();
        field_custom2 = new javax.swing.JTextField();
        field_custom3 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        area_comments = new javax.swing.JTextArea();
        btnMaxComments3 = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(SchaedenDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        paneschaeden.setName("paneschaeden"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_vertrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte wählen Sie zuerst einen Kunden" }));
        combo_vertrag.setName("combo_vertrag"); // NOI18N
        combo_vertrag.setPreferredSize(new java.awt.Dimension(134, 27));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_kunde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_kunde.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_kunde.setName("combo_kunde"); // NOI18N
        combo_kunde.setPreferredSize(new java.awt.Dimension(134, 27));
        combo_kunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kundeActionPerformed(evt);
            }
        });

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        spinnerSchadenNr.setName("spinnerSchadenNr"); // NOI18N
        spinnerSchadenNr.setPreferredSize(new java.awt.Dimension(29, 25));

        jSeparator4.setName("jSeparator4"); // NOI18N

        combo_bearbeiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Model" }));
        combo_bearbeiter.setName("combo_bearbeiter"); // NOI18N
        combo_bearbeiter.setPreferredSize(new java.awt.Dimension(134, 27));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        combo_meldungArt.setEditable(true);
        combo_meldungArt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Model" }));
        combo_meldungArt.setName("combo_meldungArt"); // NOI18N
        combo_meldungArt.setPreferredSize(new java.awt.Dimension(134, 27));

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        combo_meldungVon.setEditable(true);
        combo_meldungVon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Model" }));
        combo_meldungVon.setEnabled(false);
        combo_meldungVon.setName("combo_meldungVon"); // NOI18N
        combo_meldungVon.setPreferredSize(new java.awt.Dimension(134, 27));

        check_vnmelder.setSelected(true);
        check_vnmelder.setText(resourceMap.getString("check_vnmelder.text")); // NOI18N
        check_vnmelder.setName("check_vnmelder"); // NOI18N
        check_vnmelder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_vnmelderActionPerformed(evt);
            }
        });

        jSeparator12.setName("jSeparator12"); // NOI18N

        check_wiedervorlage.setText(resourceMap.getString("check_wiedervorlage.text")); // NOI18N
        check_wiedervorlage.setName("check_wiedervorlage"); // NOI18N
        check_wiedervorlage.setPreferredSize(new java.awt.Dimension(99, 25));
        check_wiedervorlage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_wiedervorlageActionPerformed(evt);
            }
        });

        date_wiedervorlage.setDateFormatString(resourceMap.getString("date_wiedervorlage.dateFormatString")); // NOI18N
        date_wiedervorlage.setEnabled(false);
        date_wiedervorlage.setName("date_wiedervorlage"); // NOI18N
        date_wiedervorlage.setPreferredSize(new java.awt.Dimension(87, 25));

        jSeparator13.setName("jSeparator13"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        field_risiko.setName("field_risiko"); // NOI18N
        field_risiko.setPreferredSize(new java.awt.Dimension(180, 25));

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_kunde, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_vertrag, 0, 249, Short.MAX_VALUE)))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addComponent(spinnerSchadenNr, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(combo_bearbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_basisLayout.createSequentialGroup()
                                .addComponent(check_vnmelder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combo_meldungVon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(combo_meldungArt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(check_wiedervorlage, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addGap(145, 145, 145)
                        .addComponent(date_wiedervorlage, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addComponent(field_risiko, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_kunde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_vertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(spinnerSchadenNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_bearbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(combo_meldungArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(combo_meldungVon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check_vnmelder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(date_wiedervorlage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(check_wiedervorlage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_risiko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        paneschaeden.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_schaden.setName("panel_schaden"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        date_schadenTime.setDateFormatString(resourceMap.getString("date_schadenTime.dateFormatString")); // NOI18N
        date_schadenTime.setName("date_schadenTime"); // NOI18N
        date_schadenTime.setPreferredSize(new java.awt.Dimension(124, 25));

        jSeparator5.setName("jSeparator5"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        combo_schadenKategorie.setEditable(true);
        combo_schadenKategorie.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_schadenKategorie.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_schadenKategorie.setName("combo_schadenKategorie"); // NOI18N
        combo_schadenKategorie.setPreferredSize(new java.awt.Dimension(134, 27));

        jSeparator6.setName("jSeparator6"); // NOI18N

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        field_schadenOrt.setName("field_schadenOrt"); // NOI18N
        field_schadenOrt.setPreferredSize(new java.awt.Dimension(180, 25));

        jScrollPane2.setBorder(null);
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane2.viewportBorder.title"))); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        area_schadenUmfang.setColumns(20);
        area_schadenUmfang.setRows(5);
        area_schadenUmfang.setName("area_schadenUmfang"); // NOI18N
        jScrollPane2.setViewportView(area_schadenUmfang);

        btnMaxComments1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments1.setToolTipText(resourceMap.getString("btnMaxComments1.toolTipText")); // NOI18N
        btnMaxComments1.setMinimumSize(new java.awt.Dimension(25, 25));
        btnMaxComments1.setName("btnMaxComments1"); // NOI18N
        btnMaxComments1.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxComments1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxComments1ActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(null);
        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane3.viewportBorder.title"))); // NOI18N
        jScrollPane3.setName("jScrollPane3"); // NOI18N

        area_schadenHergang.setColumns(20);
        area_schadenHergang.setRows(5);
        area_schadenHergang.setName("area_schadenHergang"); // NOI18N
        jScrollPane3.setViewportView(area_schadenHergang);

        btnMaxComments2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments2.setToolTipText(resourceMap.getString("btnMaxComments2.toolTipText")); // NOI18N
        btnMaxComments2.setMinimumSize(new java.awt.Dimension(25, 25));
        btnMaxComments2.setName("btnMaxComments2"); // NOI18N
        btnMaxComments2.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxComments2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxComments2ActionPerformed(evt);
            }
        });

        check_polizei.setText(resourceMap.getString("check_polizei.text")); // NOI18N
        check_polizei.setName("check_polizei"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        ffield_schadenhoehe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_schadenhoehe.setName("ffield_schadenhoehe"); // NOI18N
        ffield_schadenhoehe.setPreferredSize(new java.awt.Dimension(170, 25));

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(123, 25));

        combo_schadenAbrechnungArt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Brutto", "Netto" }));
        combo_schadenAbrechnungArt.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_schadenAbrechnungArt.setName("combo_schadenAbrechnungArt"); // NOI18N
        combo_schadenAbrechnungArt.setPreferredSize(new java.awt.Dimension(134, 27));

        javax.swing.GroupLayout panel_schadenLayout = new javax.swing.GroupLayout(panel_schaden);
        panel_schaden.setLayout(panel_schadenLayout);
        panel_schadenLayout.setHorizontalGroup(
            panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_schadenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_schadenLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(date_schadenTime, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(check_polizei)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_schadenOrt, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(combo_schadenKategorie, 0, 280, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_schadenLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxComments2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxComments1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(ffield_schadenhoehe, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_schadenAbrechnungArt, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_schadenLayout.setVerticalGroup(
            panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_schadenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(check_polizei))
                    .addComponent(date_schadenTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_schadenKategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_schadenOrt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(12, 12, 12)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addComponent(btnMaxComments2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96))
                    .addGroup(panel_schadenLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxComments1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_schadenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ffield_schadenhoehe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_schadenAbrechnungArt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        paneschaeden.addTab(resourceMap.getString("panel_schaden.TabConstraints.tabTitle"), panel_schaden); // NOI18N

        panel_vu.setName("panel_vu"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(146, 25));

        date_vuWeiterleitungTime.setDateFormatString(resourceMap.getString("date_vuWeiterleitungTime.dateFormatString")); // NOI18N
        date_vuWeiterleitungTime.setName("date_vuWeiterleitungTime"); // NOI18N
        date_vuWeiterleitungTime.setPreferredSize(new java.awt.Dimension(124, 25));

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        combo_vuMeldungArt.setEditable(true);
        combo_vuMeldungArt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Model" }));
        combo_vuMeldungArt.setName("combo_vuMeldungArt"); // NOI18N
        combo_vuMeldungArt.setPreferredSize(new java.awt.Dimension(134, 27));

        jSeparator9.setName("jSeparator9"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        field_vuSchadennummer.setName("field_vuSchadennummer"); // NOI18N
        field_vuSchadennummer.setPreferredSize(new java.awt.Dimension(180, 25));

        jSeparator10.setName("jSeparator10"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        date_vuStatusDatum.setDateFormatString(resourceMap.getString("date_vuStatusDatum.dateFormatString")); // NOI18N
        date_vuStatusDatum.setMinimumSize(new java.awt.Dimension(38, 27));
        date_vuStatusDatum.setName("date_vuStatusDatum"); // NOI18N

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        combo_status.setEditable(true);
        combo_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Offen", "Reguliert" }));
        combo_status.setName("combo_status"); // NOI18N
        combo_status.setPreferredSize(new java.awt.Dimension(134, 27));

        jSeparator11.setName("jSeparator11"); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane1.viewportBorder.title"))); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        area_intern.setColumns(20);
        area_intern.setRows(5);
        area_intern.setName("area_intern"); // NOI18N
        jScrollPane1.setViewportView(area_intern);

        btnMaxComments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments.setToolTipText(resourceMap.getString("btnMaxComments.toolTipText")); // NOI18N
        btnMaxComments.setName("btnMaxComments"); // NOI18N
        btnMaxComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCommentsActionPerformed(evt);
            }
        });

        check_vuGutachten.setText(resourceMap.getString("check_vuGutachten.text")); // NOI18N
        check_vuGutachten.setName("check_vuGutachten"); // NOI18N

        javax.swing.GroupLayout panel_vuLayout = new javax.swing.GroupLayout(panel_vu);
        panel_vu.setLayout(panel_vuLayout);
        panel_vuLayout.setHorizontalGroup(
            panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_vuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(date_vuWeiterleitungTime, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(combo_vuMeldungArt, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator10, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(field_vuSchadennummer, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                    .addComponent(check_vuGutachten)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                        .addComponent(date_vuStatusDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(combo_status, 0, 260, Short.MAX_VALUE))
                    .addComponent(jSeparator11, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_vuLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxComments, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_vuLayout.setVerticalGroup(
            panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_vuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_vuWeiterleitungTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(combo_vuMeldungArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_vuSchadennummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_vuGutachten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_vuLayout.createSequentialGroup()
                        .addComponent(date_vuStatusDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_vuLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_vuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxComments)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addContainerGap())
        );

        paneschaeden.addTab(resourceMap.getString("panel_vu.TabConstraints.tabTitle"), panel_vu); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jSeparator17.setName("jSeparator17"); // NOI18N

        btnMaxCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom5.setToolTipText(resourceMap.getString("btnMaxCustom5.toolTipText")); // NOI18N
        btnMaxCustom5.setName("btnMaxCustom5"); // NOI18N
        btnMaxCustom5.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom5ActionPerformed(evt);
            }
        });

        field_custom5.setName("field_custom5"); // NOI18N
        field_custom5.setPreferredSize(new java.awt.Dimension(240, 25));

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        field_custom4.setName("field_custom4"); // NOI18N
        field_custom4.setPreferredSize(new java.awt.Dimension(240, 25));

        btnMaxCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom4.setToolTipText(resourceMap.getString("btnMaxCustom4.toolTipText")); // NOI18N
        btnMaxCustom4.setName("btnMaxCustom4"); // NOI18N
        btnMaxCustom4.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom4ActionPerformed(evt);
            }
        });

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        field_custom1.setName("field_custom1"); // NOI18N
        field_custom1.setPreferredSize(new java.awt.Dimension(240, 25));

        field_custom2.setName("field_custom2"); // NOI18N
        field_custom2.setPreferredSize(new java.awt.Dimension(240, 25));

        field_custom3.setName("field_custom3"); // NOI18N
        field_custom3.setPreferredSize(new java.awt.Dimension(240, 25));

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jScrollPane4.setBorder(null);
        jScrollPane4.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane4.viewportBorder.title"))); // NOI18N
        jScrollPane4.setName("jScrollPane4"); // NOI18N

        area_comments.setColumns(20);
        area_comments.setRows(5);
        area_comments.setName("area_comments"); // NOI18N
        jScrollPane4.setViewportView(area_comments);

        btnMaxComments3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments3.setToolTipText(resourceMap.getString("btnMaxComments3.toolTipText")); // NOI18N
        btnMaxComments3.setName("btnMaxComments3"); // NOI18N
        btnMaxComments3.setPreferredSize(new java.awt.Dimension(25, 25));
        btnMaxComments3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxComments3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom5, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom4, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(btnMaxComments3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addComponent(btnMaxCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addComponent(btnMaxCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(btnMaxCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxComments3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
                .addContainerGap())
        );

        paneschaeden.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paneschaeden, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(555, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(paneschaeden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(49, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            schaden = new SchadenObj();
        }

        if (this.spinnerSchadenNr.getValue() == null || !(this.spinnerSchadenNr.getValue().toString().length() > 0)) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Schadensnummer aus",
                    "Fehler: Keine Schadensnummer", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        if (update == false) {
            schaden.setCreatorId(BasicRegistry.currentUser.getId());

            schaden.setMandantenId(BasicRegistry.currentMandant.getId());

            String snr = this.spinnerSchadenNr.getValue().toString();

            if (Integer.valueOf(snr) < 0) {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Schadensnummer aus",
                        "Fehler: Keine Schadensnummer", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                snr = SchaedenHelper.verifyNochaktuell(snr);
            } catch (SQLException e) {
                Exceptions.printStackTrace(e);
            }

            schaden.setSchadenNr(snr);
        }

        Object obj = combo_kunde.getSelectedItem();

        if(obj.getClass().equals(KundenObj.class)) {
            KundenObj knd = (KundenObj) obj;
            schaden.setKundenNr(knd.getKundenNr());
        } else if(obj.getClass().equals(FirmenObj.class)) {
            FirmenObj fir = (FirmenObj) obj;
            schaden.setKundenNr(fir.getKundenNr());
        } else {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus");
            return;
        }

        try {
            VertragObj vtr = (VertragObj) combo_vertrag.getSelectedItem();
            schaden.setVertragsId(vtr.getId());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Vertrag aus.");
            return;
        }

        schaden.setInterneInfo(this.area_intern.getText());
        schaden.setSchadenHergang(area_schadenHergang.getText());
        schaden.setSchadenUmfang(area_schadenUmfang.getText());

        schaden.setSchadenPolizei(check_polizei.isSelected());

        if (check_vnmelder.isSelected()) {
            schaden.setMeldungVon(schaden.getKundenNr());
        } else {
            Object obj2 = this.combo_meldungVon.getSelectedItem();
            try {
                KundenObj knd = (KundenObj) obj2;
                schaden.setMeldungVon(knd.getKundenNr());
            } catch (Exception e) {
                FirmenObj fir = (FirmenObj) obj2;
                schaden.setMeldungVon(fir.getKundenNr());
            }
        }

        schaden.setVuGutachten(check_vuGutachten.isSelected());
        if (check_wiedervorlage.isSelected()) {
            // TODO neue wiedervorlage
            // new java.sql.Timestamp(date_wiedervorlage.getDate.getTime())); // TODO
        } else {
            schaden.setWiedervorlagenId(-1);
        }

        try {
            BenutzerObj ben = (BenutzerObj) combo_bearbeiter.getSelectedItem();
            schaden.setSchadenBearbeiter(ben.getId());
        } catch (Exception e) {
        }

        schaden.setMeldungArt(combo_meldungArt.getSelectedItem().toString());
        schaden.setMeldungVon(combo_meldungVon.getSelectedItem().toString());
        schaden.setSchadenAbrechnungArt(combo_schadenAbrechnungArt.getSelectedIndex());
        schaden.setSchadenKategorie(combo_schadenKategorie.getSelectedItem().toString());
        schaden.setStatus(combo_status.getSelectedIndex());

        schaden.setVuMeldungArt(combo_vuMeldungArt.getSelectedItem().toString());

        if (date_schadenTime.getDate() != null) {
            schaden.setSchaedenTime(new java.sql.Timestamp(date_schadenTime.getDate().getTime()));
        }

        if (date_vuStatusDatum.getDate() != null) {
            schaden.setVuStatusDatum(new java.sql.Timestamp(date_vuStatusDatum.getDate().getTime()));
        }

        if (date_vuWeiterleitungTime.getDate() != null) {
            schaden.setVuWeiterleitungTime(new java.sql.Timestamp(date_vuWeiterleitungTime.getDate().getTime()));
        }

        schaden.setVuSchadennummer(field_vuSchadennummer.getText());
        schaden.setSchadenHoehe(Double.valueOf(ffield_schadenhoehe.getValue().toString()));

        schaden.setRisiko(field_risiko.getText());
        schaden.setSchadenOrt(field_schadenOrt.getText());

        schaden.setNotiz(this.area_comments.getText());
        schaden.setCustom1(this.field_custom1.getText());
        schaden.setCustom2(this.field_custom2.getText());
        schaden.setCustom3(this.field_custom3.getText());
        schaden.setCustom4(this.field_custom4.getText());
        schaden.setCustom5(this.field_custom5.getText());

        schaden.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            if (update == false) {
                schaden.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                int id = SchaedenSQLMethods.insertIntoSchaeden(DatabaseConnection.open(), schaden);
                schaden.setId(id);
                SchaedenHelper.doCreationTasks(schaden);
                this.dispose();
            } else {
                boolean success = SchaedenSQLMethods.updateSchaeden(DatabaseConnection.open(), schaden);

                if (!success) {
                    Log.databaselogger.fatal("Datenbankfehler: Konnte den Schadenfall nicht aktualisieren");
                    ShowException.showException("Beim aktualisieren des Schadenfalls ist ein Datenbank Fehler aufgetreten. "
                            + "Sollte dieser häufiger auftreten wenden Sie sich bitte an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte den Schadenfall nicht updaten");
                }
                this.dispose();
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Datenbankfehler: Konnte den Schaden nicht speichern", e);
            ShowException.showException("Beim Speichern des Schadenfalls ist ein Datenbank Fehler aufgetreten. "
                    + "Sollte dieser häufiger auftreten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Schadenfall nicht speichern");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnMaxCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCommentsActionPerformed
        MaximizeHelper.openMax(this.area_intern, "Interne Notizen");
}//GEN-LAST:event_btnMaxCommentsActionPerformed

    private void btnMaxCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom5ActionPerformed
        MaximizeHelper.openMax(this.field_custom5, "Benutzerdefiniert 5");
}//GEN-LAST:event_btnMaxCustom5ActionPerformed

    private void btnMaxCustom4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom4ActionPerformed
        MaximizeHelper.openMax(this.field_custom4, "Benutzerdefiniert 4");
}//GEN-LAST:event_btnMaxCustom4ActionPerformed

    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed

    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxComments1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxComments1ActionPerformed
        MaximizeHelper.openMax(this.area_schadenHergang, "Schadenshergang");
    }//GEN-LAST:event_btnMaxComments1ActionPerformed

    private void btnMaxComments2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxComments2ActionPerformed
        MaximizeHelper.openMax(this.area_schadenUmfang, "Schadensumfang");
    }//GEN-LAST:event_btnMaxComments2ActionPerformed

    private void btnMaxComments3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxComments3ActionPerformed
        MaximizeHelper.openMax(this.area_comments, "Kommentare");
    }//GEN-LAST:event_btnMaxComments3ActionPerformed

    private void combo_kundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundeActionPerformed
        try {
            KundenObj knd = (KundenObj) this.combo_kunde.getSelectedItem();
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));

            if (this.check_vnmelder.isSelected()) {
                this.combo_meldungVon.setSelectedItem(knd);
            }

        } catch (Exception e) {
            FirmenObj knd = (FirmenObj) this.combo_kunde.getSelectedItem();
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));

            if (this.check_vnmelder.isSelected()) {
                this.combo_meldungVon.setSelectedItem(knd);
            }
        }
    }//GEN-LAST:event_combo_kundeActionPerformed

    private void check_vnmelderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_vnmelderActionPerformed
        if (this.check_vnmelder.isSelected()) {
            this.combo_meldungVon.setSelectedItem(this.combo_kunde.getSelectedItem());
            this.combo_meldungVon.setEnabled(false);
        } else {
            this.combo_meldungVon.setEnabled(true);
        }
    }//GEN-LAST:event_check_vnmelderActionPerformed

    private void check_wiedervorlageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_wiedervorlageActionPerformed
        if (check_wiedervorlage.isSelected()) {
            this.date_wiedervorlage.setEnabled(true);
        } else {
            this.date_wiedervorlage.setEnabled(true);
        }
    }//GEN-LAST:event_check_wiedervorlageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SchaedenDialog dialog = new SchaedenDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea area_comments;
    private javax.swing.JTextArea area_intern;
    private javax.swing.JTextArea area_schadenHergang;
    private javax.swing.JTextArea area_schadenUmfang;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxComments;
    private javax.swing.JButton btnMaxComments1;
    private javax.swing.JButton btnMaxComments2;
    private javax.swing.JButton btnMaxComments3;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnMaxCustom4;
    private javax.swing.JButton btnMaxCustom5;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox check_polizei;
    private javax.swing.JCheckBox check_vnmelder;
    private javax.swing.JCheckBox check_vuGutachten;
    private javax.swing.JCheckBox check_wiedervorlage;
    private javax.swing.JComboBox combo_bearbeiter;
    private javax.swing.JComboBox combo_kunde;
    private javax.swing.JComboBox combo_meldungArt;
    private javax.swing.JComboBox combo_meldungVon;
    private javax.swing.JComboBox combo_schadenAbrechnungArt;
    private javax.swing.JComboBox combo_schadenKategorie;
    private javax.swing.JComboBox combo_status;
    private javax.swing.JComboBox combo_vertrag;
    private javax.swing.JComboBox combo_vuMeldungArt;
    private com.toedter.calendar.JDateChooser date_schadenTime;
    private com.toedter.calendar.JDateChooser date_vuStatusDatum;
    private com.toedter.calendar.JDateChooser date_vuWeiterleitungTime;
    private com.toedter.calendar.JDateChooser date_wiedervorlage;
    private javax.swing.JFormattedTextField ffield_schadenhoehe;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JTextField field_custom4;
    private javax.swing.JTextField field_custom5;
    private javax.swing.JTextField field_risiko;
    private javax.swing.JTextField field_schadenOrt;
    private javax.swing.JTextField field_vuSchadennummer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JPanel panel_schaden;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JPanel panel_vu;
    private javax.swing.JTabbedPane paneschaeden;
    private javax.swing.JSpinner spinnerSchadenNr;
    // End of variables declaration//GEN-END:variables
}
