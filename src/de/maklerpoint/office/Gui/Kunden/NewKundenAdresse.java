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
 * NewKundenAdresse.java
 *
 * Created on 01.09.2010, 12:19:09
 */
package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Tools.ImageComboBoxRenderer;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class NewKundenAdresse extends javax.swing.JDialog {

    private boolean update = false;
    private static int PRIVAT = 0;
    private static int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private int type = -1;
    private ZusatzadressenObj za = null;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private String kennung = null;

    /** Creates new form NewKundenAdresse */
    public NewKundenAdresse(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupCombos();
    }

    public NewKundenAdresse(java.awt.Frame parent, boolean modal, KundenObj kunde) {
        super(parent, modal);
        this.type = PRIVAT;
        this.kunde = kunde;
        this.update = false;
        kennung = kunde.getKundenNr();
        initComponents();
        setupCombos();
    }

    public NewKundenAdresse(java.awt.Frame parent, boolean modal, FirmenObj firma) {
        super(parent, modal);
        this.type = FIRMA;
        this.firma = firma;
        this.update = false;
        kennung = firma.getKundenNr();
        initComponents();
        setupCombos();
    }

    public NewKundenAdresse(java.awt.Frame parent, boolean modal, VersichererObj vers) {
        super(parent, modal);
        this.type = VERSICHERER;
        this.versicherer = vers;
        this.update = false;
        initComponents();
        setupCombos();
    }

    public NewKundenAdresse(java.awt.Frame parent, boolean modal, BenutzerObj ben) {
        super(parent, modal);
        this.type = BENUTZER;
        this.benutzer = ben;
        this.update = false;
        initComponents();
        setupCombos();
    }

    public NewKundenAdresse(java.awt.Frame parent, boolean modal, ZusatzadressenObj za) {
        super(parent, modal);
        this.za = za;
        this.update = true;
        initComponents();
        setupCombos();
        loadAdresse();
    }

    private void setupCombos() {
//        this.combo_comtype1 = new JComboBox(CommunicationTypes.COMMUNICATION_INTARRAY)M
        this.combo_comtype1.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype1.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype1.setSelectedIndex(0);

        this.combo_comtype2.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype2.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype2.setSelectedIndex(1);

        this.combo_comtype3.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype3.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype3.setSelectedIndex(4);

        this.combo_comtype4.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype4.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype4.setSelectedIndex(7);

        this.combo_comtype5.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype5.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype5.setSelectedIndex(10);

        this.combo_comtype6.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype6.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype6.setSelectedIndex(14);

        setTitle();
    }

    private void setTitle() {
        if (za != null) {
            this.setTitle("Zusatzadresse " + za.getName() + " bearbeiten");
        } else if (type == PRIVAT) {
            this.setTitle("Neue Zusatzadresse für " + kunde.getVorname() + " " + kunde.getNachname() + " [" + kunde.getKundenNr() + "]");
        } else if (type == FIRMA) {
            this.setTitle("Neue Zusatzadresse für " + firma.getFirmenName() + " [" + firma.getKundenNr() + "]");
        } else if (type == VERSICHERER) {
            this.setTitle("Neue Zusatzadresse für " + versicherer.getName() + " [" + versicherer.getVuNummer() + "]");
        } else if (type == BENUTZER) {
            this.setTitle("Neue Zusatzadresse für " + benutzer.getVorname() + " "
                    + benutzer.getNachname() + " [" + benutzer.getKennung() + "]");
        }
    }

    /**
     * Adressen laden
     */
    private void loadAdresse() {
        if (this.za == null) {
            return;
        }

        this.field_name.setText(za.getName());
        this.field_nameZusatz.setText(za.getNameZusatz());

        this.field_ort.setText(za.getOrt());
        this.field_plz.setText(za.getPlz());

        this.combo_bundesland.setSelectedItem(za.getBundesland());
        this.combo_land.setSelectedItem(za.getLand());

        this.field_communication1.setText(za.getCommunication1());
        this.field_communication2.setText(za.getCommunication2());
        this.field_communication3.setText(za.getCommunication3());
        this.field_communication4.setText(za.getCommunication4());
        this.field_communication5.setText(za.getCommunication5());
        this.field_communication6.setText(za.getCommunication6());

        this.field_custom1.setText(za.getCustom1());
        this.field_custom2.setText(za.getCustom2());
        this.field_custom3.setText(za.getCustom3());

        this.combo_comtype1.setSelectedIndex(za.getCommunication1Type());
        this.combo_comtype2.setSelectedIndex(za.getCommunication2Type());
        this.combo_comtype3.setSelectedIndex(za.getCommunication3Type());
        this.combo_comtype4.setSelectedIndex(za.getCommunication4Type());
        this.combo_comtype5.setSelectedIndex(za.getCommunication5Type());
        this.combo_comtype6.setSelectedIndex(za.getCommunication6Type());
        
        this.text_comments.setText(za.getComments());        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        pane_kundeninfos = new javax.swing.JTabbedPane();
        panel_anschrift = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        field_strasse = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        field_plz = new javax.swing.JTextField();
        field_ort = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        combo_land = new javax.swing.JComboBox();
        combo_bundesland = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        combo_comtype1 = new javax.swing.JComboBox();
        combo_comtype2 = new javax.swing.JComboBox();
        combo_comtype3 = new javax.swing.JComboBox();
        combo_comtype5 = new javax.swing.JComboBox();
        combo_comtype4 = new javax.swing.JComboBox();
        combo_comtype6 = new javax.swing.JComboBox();
        field_communication6 = new javax.swing.JTextField();
        field_communication5 = new javax.swing.JTextField();
        field_communication4 = new javax.swing.JTextField();
        field_communication3 = new javax.swing.JTextField();
        field_communication2 = new javax.swing.JTextField();
        field_communication1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        field_name = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        field_nameZusatz = new javax.swing.JTextField();
        panel_sonstiges = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        field_custom1 = new javax.swing.JTextField();
        btnMaxCustom1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        field_custom2 = new javax.swing.JTextField();
        btnMaxCustom2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        field_custom3 = new javax.swing.JTextField();
        btnMaxCustom3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        btnMaxTextKommentar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NewKundenAdresse.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        pane_kundeninfos.setName("pane_kundeninfos"); // NOI18N

        panel_anschrift.setName("panel_anschrift"); // NOI18N

        jSeparator6.setName("jSeparator6"); // NOI18N

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        field_strasse.setName("field_strasse"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        field_plz.setName("field_plz"); // NOI18N

        field_ort.setName("field_ort"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        combo_land.setEditable(true);
        combo_land.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_land.setName("combo_land"); // NOI18N

        combo_bundesland.setEditable(true);
        combo_bundesland.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_bundesland.setName("combo_bundesland"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        combo_comtype1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype1.setName("combo_comtype1"); // NOI18N

        combo_comtype2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype2.setName("combo_comtype2"); // NOI18N

        combo_comtype3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype3.setName("combo_comtype3"); // NOI18N

        combo_comtype5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype5.setName("combo_comtype5"); // NOI18N

        combo_comtype4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype4.setName("combo_comtype4"); // NOI18N

        combo_comtype6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype6.setName("combo_comtype6"); // NOI18N

        field_communication6.setName("field_communication6"); // NOI18N

        field_communication5.setName("field_communication5"); // NOI18N

        field_communication4.setName("field_communication4"); // NOI18N

        field_communication3.setName("field_communication3"); // NOI18N

        field_communication2.setName("field_communication2"); // NOI18N

        field_communication1.setName("field_communication1"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        field_name.setName("field_name"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        field_nameZusatz.setName("field_nameZusatz"); // NOI18N

        javax.swing.GroupLayout panel_anschriftLayout = new javax.swing.GroupLayout(panel_anschrift);
        panel_anschrift.setLayout(panel_anschriftLayout);
        panel_anschriftLayout.setHorizontalGroup(
            panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_anschriftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(field_name, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(combo_land, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_bundesland, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(field_nameZusatz, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_anschriftLayout.setVerticalGroup(
            panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_anschriftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(field_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(field_nameZusatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(combo_bundesland, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_land, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        NewKundeHelper.loadLaender(this);
        NewKundeHelper.loadBundeslaender(0, this);

        pane_kundeninfos.addTab(resourceMap.getString("panel_anschrift.TabConstraints.tabTitle"), panel_anschrift); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_custom1.setName("field_custom1"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane1.setViewportView(text_comments);

        btnMaxTextKommentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxTextKommentar.setToolTipText(resourceMap.getString("btnMaxTextKommentar.toolTipText")); // NOI18N
        btnMaxTextKommentar.setName("btnMaxTextKommentar"); // NOI18N
        btnMaxTextKommentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxTextKommentarActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnMaxCustom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(btnMaxCustom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(btnMaxCustom3))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        pane_kundeninfos.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane_kundeninfos, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pane_kundeninfos, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.update == false) {
            int dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
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
            za = new ZusatzadressenObj();
        }

        if (this.field_name.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen an.");
            return;
        }

        if (type == PRIVAT || type == FIRMA) {
            za.setKundenKennung(kennung);
        } else if (type == VERSICHERER) {
            za.setVersichererId(versicherer.getId());
        } else if (type == BENUTZER) {
            za.setBenutzerId(benutzer.getId());
        }

        if (!update) {
            za.setCreator(BasicRegistry.currentUser.getId());
        }
        za.setName(field_name.getText());
        za.setNameZusatz(this.field_nameZusatz.getText());

        za.setStreet(field_strasse.getText());
        za.setOrt(field_ort.getText());
        za.setPlz(field_plz.getText());
        za.setBundesland((String) combo_bundesland.getSelectedItem());
        za.setLand((String) combo_land.getSelectedItem());

        za.setCommunication1(field_communication1.getText());
        za.setCommunication2(field_communication2.getText());
        za.setCommunication3(field_communication3.getText());
        za.setCommunication4(field_communication4.getText());
        za.setCommunication5(field_communication5.getText());
        za.setCommunication6(field_communication6.getText());
        za.setCustom1(field_custom1.getText());
        za.setCustom2(field_custom2.getText());
        za.setCustom3(field_custom3.getText());

        za.setCommunication1Type(this.combo_comtype1.getSelectedIndex());
        za.setCommunication2Type(this.combo_comtype2.getSelectedIndex());
        za.setCommunication3Type(this.combo_comtype3.getSelectedIndex());
        za.setCommunication4Type(this.combo_comtype4.getSelectedIndex());
        za.setCommunication5Type(this.combo_comtype5.getSelectedIndex());
        za.setCommunication6Type(this.combo_comtype6.getSelectedIndex());

        za.setComments(text_comments.getText());

        za.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        try {
            if (update == false) {
                za.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                int id = ZusatzadressenSQLMethods.insertIntoKunden_zusatzadressen(DatabaseConnection.open(), za);

            } else {
                boolean success = ZusatzadressenSQLMethods.updateKunden_zusatzadressen(DatabaseConnection.open(), za);

                if (!success) {
                    Log.databaselogger.fatal("Konnte die Zusatzadresse nicht updaten (nicht gefunden).");
                    ShowException.showException("Datenbankfehler: Die Zusatzadresse konnte nicht aktualisiert werden",
                        ExceptionDialogGui.LEVEL_WARNING,  "Konnte Zusatzadresse nicht speichern");
                }
            }
            this.dispose();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Zusatzadresse nicht updaten / neu hinzufügen.");
            ShowException.showException("Datenbankfehler: Der Zusatzadresse konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Zusatzadresse nicht speichern");
        }
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed

    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewKundenAdresse dialog = new NewKundenAdresse(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    public javax.swing.JComboBox combo_bundesland;
    private javax.swing.JComboBox combo_comtype1;
    private javax.swing.JComboBox combo_comtype2;
    private javax.swing.JComboBox combo_comtype3;
    private javax.swing.JComboBox combo_comtype4;
    private javax.swing.JComboBox combo_comtype5;
    private javax.swing.JComboBox combo_comtype6;
    public javax.swing.JComboBox combo_land;
    private javax.swing.JTextField field_communication1;
    private javax.swing.JTextField field_communication2;
    private javax.swing.JTextField field_communication3;
    private javax.swing.JTextField field_communication4;
    private javax.swing.JTextField field_communication5;
    private javax.swing.JTextField field_communication6;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JTextField field_name;
    private javax.swing.JTextField field_nameZusatz;
    private javax.swing.JTextField field_ort;
    private javax.swing.JTextField field_plz;
    private javax.swing.JTextField field_strasse;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane pane_kundeninfos;
    public javax.swing.JPanel panel_anschrift;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JTextArea text_comments;
    // End of variables declaration//GEN-END:variables
}
