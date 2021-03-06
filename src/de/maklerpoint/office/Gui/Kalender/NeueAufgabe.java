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
 * NeueAufgabe.java
 *
 * Created on Jul 12, 2010, 8:46:10 PM
 */
package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Configuration.TagDialogHelper;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Kalender.Aufgaben.Tools.AufgabenSQLMethods;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tags.TagObj;
import de.maklerpoint.office.Tags.Tags;
import de.maklerpoint.office.Tools.BasicRegex;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NeueAufgabe extends javax.swing.JDialog {

    private boolean update = false;
    private AufgabenObj aufgabe = null;

    /** Creates new form NeueAufgabe */
    public NeueAufgabe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        this.aufgabe = null;
        initComponents();
        setUp();
    }

    /**
     * 
     * @param parent
     * @param modal
     * @param aufgabe
     */
    public NeueAufgabe(java.awt.Frame parent, boolean modal, AufgabenObj aufgabe) {
        super(parent, modal);
        this.update = true;
        this.aufgabe = aufgabe;
        initComponents();
        setUp();
    }

    public NeueAufgabe(java.awt.Frame parent, boolean modal, StoerfallObj stoer) {
        super(parent, modal);
        this.update = false;
        this.aufgabe = null;
        initComponents();
        setUp();
        Object acknd = KundenRegistry.getKunde(stoer.getKundenNr());
        this.combo_kunden.setSelectedItem(acknd);
        this.setVertragsCombo(acknd);
        VertragObj vtr = VertragRegistry.getVertrag(stoer.getVertragsId());
        this.combo_vertrag.setSelectedItem(vtr);
        this.setStoerfallCombo(vtr);
        this.setSchadenCombo(vtr);
        this.combo_stoerfall.setSelectedItem(stoer);
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Störfall Nr. ".concat(stoer.getStoerfallNr().concat("\n")));
        sb.append("Vertrag. ".concat(vtr.toString()).concat("\n"));
        sb.append("Grund: ".concat(stoer.getGrund()).concat("\n\n"));
        
        this.text_beschreibung.setText(sb.toString());
    }
    
    public NeueAufgabe(java.awt.Frame parent, boolean modal, SchadenObj sch) {
        super(parent, modal);
        this.update = false;
        this.aufgabe = null;
        initComponents();
        setUp();
        Object acknd = KundenRegistry.getKunde(sch.getKundenNr());
        this.combo_kunden.setSelectedItem(acknd);
        this.setVertragsCombo(acknd);
        VertragObj vtr = VertragRegistry.getVertrag(sch.getVertragsId());
        this.combo_vertrag.setSelectedItem(vtr);
        this.setStoerfallCombo(vtr);
        this.setSchadenCombo(vtr);
        this.combo_schaden.setSelectedItem(sch);
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Schadensfall Nr. ".concat(sch.getSchadenNr().concat("\n")));
        sb.append("Vertrag. ".concat(vtr.toString()).concat("\n\n"));
        
        this.text_beschreibung.setText(sb.toString());
    }

    private void setUp() {
        this.setUpTitle();
        this.loadTags();
        this.loadKunden();
        this.loadBenutzer();
        this.checkSecurity();
        loadAufgabe();
    }

    public void setUpTitle() {
        if (update) {
            this.setTitle("Aufgabe " + BasicRegex.shortenString(30, aufgabe.getBeschreibung(), true));
        } else {
            this.setTitle("Neue Aufgabe");
        }
    }

    public void loadAufgabe() {
        if (aufgabe == null) {
            return;
        }

        this.text_beschreibung.setText(aufgabe.getBeschreibung());
        TagObj tag = Tags.getTag(aufgabe.getTag());
        this.combo_tag.setSelectedItem(tag);

        this.date_start.setDate(aufgabe.getStart());
        this.date_ende.setDate(aufgabe.getEnde());

        this.check_public.setSelected(aufgabe.isPublic());

        if (!aufgabe.getKundenKennung().equalsIgnoreCase("-1")) {
            Object acknd = KundenRegistry.getKunde(aufgabe.getKundenKennung());
            this.combo_kunden.setSelectedItem(acknd);
            this.setVertragsCombo(acknd);
        }

        if (aufgabe.getVertragId() != -1) {
            VertragObj vtr1 = VertragRegistry.getVertrag(aufgabe.getVertragId());
            this.combo_vertrag.setSelectedItem(vtr1);
            this.setStoerfallCombo(vtr1);
            this.setSchadenCombo(vtr1);
        }

        if (aufgabe.getSchadenId() != -1) {
            this.combo_schaden.setSelectedItem(VertragRegistry.getSchaden(
                    aufgabe.getSchadenId()));
        }

        if (aufgabe.getStoerfallId() != -1) {
            this.combo_stoerfall.setSelectedItem(VertragRegistry.getStoerfall(
                    aufgabe.getStoerfallId()));
        }

        if (aufgabe.getBenutzerId() != BasicRegistry.currentUser.getId()) {
            this.combo_benutzer.setSelectedItem(BenutzerRegistry.getBenutzer(
                    aufgabe.getBenutzerId()));
        }

        if (aufgabe.getCreatorId() != BasicRegistry.currentUser.getId()
                && !Security.isAllowed(SecurityTasks.AUFGABE_EDIT_ANDERERBENUTZER)) {
            btnSave.setEnabled(false);
            text_beschreibung.setEnabled(false);
            combo_tag.setEnabled(false);
            combo_kunden.setEnabled(false);
            combo_vertrag.setEnabled(false);
            combo_stoerfall.setEnabled(false);
            combo_schaden.setEnabled(false);
            check_public.setEnabled(false);
            date_ende.setEnabled(false);
            date_start.setEnabled(false);
        }
    }

    /**
     * 
     */
    public void loadTags() {
        this.combo_tag.setModel(new DefaultComboBoxModel(Tags.tags));
    }

    /**
     * 
     */
    public void loadKunden() {
        this.combo_kunden.setModel(new DefaultComboBoxModel(
                ComboBoxGetter.getAlleKundenCombo("Kein Kunde", Status.NORMAL)));
    }

    private void loadBenutzer() {
        this.combo_benutzer.setModel(new DefaultComboBoxModel(
                ComboBoxGetter.getBenutzerCombo(null)));

        this.combo_benutzer.setSelectedItem(BasicRegistry.currentUser);
    }

    /**
     * 
     */
    public void checkSecurity() {
        if (!Security.isAllowed(SecurityTasks.AUFGABE_PUBLIC)) {
            this.check_public.setEnabled(false);
        }

        if (!Security.isAllowed(SecurityTasks.AUFGABE_EDIT_ANDERERBENUTZER)) {
            this.combo_benutzer.setEnabled(false);
        }
    }

    private void setVertragsCombo(Object sel) {
        if (sel.getClass().equals(KundenObj.class)) {
            KundenObj kunde = (KundenObj) sel;
            this.combo_vertrag.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragCombo("Keiner", kunde.getKundenNr())));
        } else if (sel.getClass().equals(KundenObj.class)) {
            FirmenObj firma = (FirmenObj) sel;
            this.combo_vertrag.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragCombo("Keiner", firma.getKundenNr())));
        } else {
            this.combo_vertrag.setModel(new DefaultComboBoxModel(new Object[]{"Keine Verträge vorhanden"}));
        }
    }

    private void setStoerfallCombo(Object vtr) {
        if(vtr == null)
            return;
        
        if (vtr.getClass().equals(VertragObj.class)) {
            VertragObj vt = (VertragObj) vtr;
            this.combo_stoerfall.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragStoerfallCombo("Keiner", vt.getId())));
        } else {
            this.combo_stoerfall.setModel(new DefaultComboBoxModel(new Object[]{"Keine Störfälle vorhanden"}));
        }
    }

    private void setSchadenCombo(Object vtr) {
        if(vtr == null)
            return;
        
        if (vtr.getClass().equals(VertragObj.class)) {
            VertragObj vt = (VertragObj) vtr;
            this.combo_schaden.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragSchaedenCombo("Keiner", vt.getId())));
        } else {
            this.combo_schaden.setModel(new DefaultComboBoxModel(new Object[]{"Keine Schäden vorhanden"}));
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

        jLabel2 = new javax.swing.JLabel();
        scroll_beschreibung = new javax.swing.JScrollPane();
        text_beschreibung = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        combo_tag = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        date_start = new com.toedter.calendar.JDateChooser();
        date_ende = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        combo_kunden = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        check_public = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        btnTags = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        combo_vertrag = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        combo_schaden = new javax.swing.JComboBox();
        combo_stoerfall = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        combo_benutzer = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NeueAufgabe.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        scroll_beschreibung.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_beschreibung.setName("scroll_beschreibung"); // NOI18N

        text_beschreibung.setColumns(20);
        text_beschreibung.setLineWrap(true);
        text_beschreibung.setRows(5);
        text_beschreibung.setWrapStyleWord(true);
        text_beschreibung.setName("text_beschreibung"); // NOI18N
        scroll_beschreibung.setViewportView(text_beschreibung);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        combo_tag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_tag.setName("combo_tag"); // NOI18N
        combo_tag.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        date_start.setDateFormatString(resourceMap.getString("date_start.dateFormatString")); // NOI18N
        date_start.setName("date_start"); // NOI18N

        date_ende.setDateFormatString(resourceMap.getString("date_ende.dateFormatString")); // NOI18N
        date_ende.setName("date_ende"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        combo_kunden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keiner" }));
        combo_kunden.setName("combo_kunden"); // NOI18N
        combo_kunden.setPreferredSize(new java.awt.Dimension(200, 25));
        combo_kunden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kundenActionPerformed(evt);
            }
        });

        jSeparator4.setName("jSeparator4"); // NOI18N

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(120, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        check_public.setText(resourceMap.getString("check_public.text")); // NOI18N
        check_public.setName("check_public"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        btnTags.setIcon(resourceMap.getIcon("btnTags.icon")); // NOI18N
        btnTags.setName("btnTags"); // NOI18N
        btnTags.setPreferredSize(new java.awt.Dimension(28, 26));
        btnTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagsActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        combo_vertrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kunden auswählen" }));
        combo_vertrag.setName("combo_vertrag"); // NOI18N
        combo_vertrag.setPreferredSize(new java.awt.Dimension(200, 25));
        combo_vertrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_vertragActionPerformed(evt);
            }
        });

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        combo_schaden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vertrag auswählen" }));
        combo_schaden.setName("combo_schaden"); // NOI18N
        combo_schaden.setPreferredSize(new java.awt.Dimension(200, 25));

        combo_stoerfall.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vertrag auswählen" }));
        combo_stoerfall.setName("combo_stoerfall"); // NOI18N
        combo_stoerfall.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        combo_benutzer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keiner" }));
        combo_benutzer.setName("combo_benutzer"); // NOI18N
        combo_benutzer.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(scroll_beschreibung, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(combo_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                                .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                                .addComponent(combo_vertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                                .addComponent(combo_schaden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                                .addComponent(combo_stoerfall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(date_ende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(date_start, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(177, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_benutzer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(check_public, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_beschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_benutzer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_public)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(combo_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(date_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date_ende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_vertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_schaden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_stoerfall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt
     */
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

    /**
     * 
     * @param evt
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            aufgabe = new AufgabenObj();
            aufgabe.setCreatorId(BasicRegistry.currentUser.getId());
        }

        if (this.text_beschreibung == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Beschreibung an",
                    "Fehler: Keine Beschreibung", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.date_start.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Startdatum an",
                    "Fehler: Kein Startdatum", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.date_ende.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Enddatum an",
                    "Fehler: Kein Enddatum", JOptionPane.WARNING_MESSAGE);
            return;
        }

        aufgabe.setBeschreibung(this.text_beschreibung.getText());

        aufgabe.setTag(this.combo_tag.getSelectedItem().toString());


        if (date_start.getDate() != null) {
            aufgabe.setStart(new java.sql.Timestamp(date_start.getDate().getTime()));
        }

        if (date_ende.getDate() != null) {
            aufgabe.setEnde(new java.sql.Timestamp(date_ende.getDate().getTime()));
        }

        Object sel = this.combo_kunden.getSelectedItem();

        if (sel.getClass().equals(KundenObj.class)) {
            KundenObj kund = (KundenObj) sel;
            aufgabe.setKundenKennung(kund.getKundenNr());
        } else if (sel.getClass().equals(FirmenObj.class)) {
            FirmenObj firm = (FirmenObj) this.combo_kunden.getSelectedItem();
            aufgabe.setKundenKennung(firm.getKundenNr());
        } else {
            aufgabe.setKundenKennung("-1");
        }
        
        Object selvtr = this.combo_vertrag.getSelectedItem();
        
        if(selvtr.getClass().equals(VertragObj.class)) {
            VertragObj vtr = (VertragObj) selvtr;
            aufgabe.setVertragId(vtr.getId());
        } else {
            aufgabe.setVertragId(-1);            
        }
        
        Object selsch = this.combo_schaden.getSelectedItem();
        
        if(selsch.getClass().equals(SchadenObj.class)) {
            SchadenObj sch = (SchadenObj) selsch;
            aufgabe.setSchadenId(sch.getId());
        } else {
            aufgabe.setSchadenId(-1);            
        }
        
        Object selstoer = this.combo_stoerfall.getSelectedItem();
        
        if(selstoer.getClass().equals(StoerfallObj.class)) {
            StoerfallObj stoe = (StoerfallObj) selstoer;
            aufgabe.setStoerfallId(stoe.getId());
        } else {
            aufgabe.setStoerfallId(-1);            
        }
        
        aufgabe.setPublic(check_public.isSelected());
        aufgabe.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            if (update == false) {
                aufgabe.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                AufgabenSQLMethods.insertIntoAufgaben(DatabaseConnection.open(), aufgabe);

            } else {
                boolean work = AufgabenSQLMethods.updateAufgaben(DatabaseConnection.open(), aufgabe);

                if (work == false) {
                    Log.databaselogger.fatal("Datenbankfehler: Konnte Aufgabe nicht updaten");
                    ShowException.showException("Bei der Speicherung der Aufgabe ist ein Fehler aufgetretten. "
                            + "Die Aufgabe wurde nicht gefunden. "
                            + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte die Aufgabe nicht speichern");

                }

            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte Aufgabe nicht speichern", e);
            ShowException.showException("Bei der Speicherung der Aufgabe ist ein Datenbank Fehler aufgetretten. "
                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte die Aufgabe nicht speichern");
        }

}//GEN-LAST:event_btnSaveActionPerformed

    private void btnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagsActionPerformed
        TagDialogHelper.showTagDialog();
}//GEN-LAST:event_btnTagsActionPerformed

    private void combo_kundenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundenActionPerformed
        this.setVertragsCombo(combo_kunden.getSelectedItem());
    }//GEN-LAST:event_combo_kundenActionPerformed

    private void combo_vertragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_vertragActionPerformed
        this.setSchadenCombo(combo_vertrag.getSelectedItem());
        this.setStoerfallCombo(combo_vertrag.getSelectedItem());
    }//GEN-LAST:event_combo_vertragActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NeueAufgabe dialog = new NeueAufgabe(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnSave;
    public javax.swing.JButton btnTags;
    public javax.swing.JCheckBox check_public;
    public javax.swing.JComboBox combo_benutzer;
    public javax.swing.JComboBox combo_kunden;
    public javax.swing.JComboBox combo_schaden;
    public javax.swing.JComboBox combo_stoerfall;
    public javax.swing.JComboBox combo_tag;
    public javax.swing.JComboBox combo_vertrag;
    public com.toedter.calendar.JDateChooser date_ende;
    public com.toedter.calendar.JDateChooser date_start;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JScrollPane scroll_beschreibung;
    public javax.swing.JTextArea text_beschreibung;
    // End of variables declaration//GEN-END:variables
}
