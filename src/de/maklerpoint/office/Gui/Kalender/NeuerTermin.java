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
 * NeuerTermin.java
 *
 * Created on Jul 12, 2010, 12:54:34 PM
 */
package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Configuration.TagDialogHelper;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kalender.Termine.Tools.TermineSQLMethods;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tags.TagObj;
import de.maklerpoint.office.Tags.Tags;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NeuerTermin extends javax.swing.JDialog {

    private boolean update = false;
    private TerminObj termin = null;
    private Date curdate = null;

    /** Creates new form NeuerTermin */
    public NeuerTermin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        this.curdate = null;
        initComponents();
        initT();
    }

    public NeuerTermin(java.awt.Frame parent, boolean modal, TerminObj termin) {
        super(parent, modal);
        this.curdate = null;
        this.update = true;
        this.termin = termin;
        initComponents();
        initT();
        loadTermin();
    }

    public NeuerTermin(java.awt.Frame parent, boolean modal, Date date) {
        super(parent, modal);
        this.curdate = date;
        this.update = false;
        initComponents();
        initT();
    }

    private void initT() {
        loadTags();
        this.loadKunden();
        if (curdate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(curdate);

            cal.set(Calendar.HOUR_OF_DAY, 9);

            this.date_start.setDate(cal.getTime());
            cal.set(Calendar.HOUR_OF_DAY, 10);
            this.date_ende.setDate(cal.getTime());
        }

        this.date_erinnerung.setDate(curdate);
    }

    public void loadTags() {
//        dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));

        this.combo_tag.setModel(new DefaultComboBoxModel(Tags.tags));
    }

    /**
     * 
     */
    private void loadTermin() {
        if (this.update == false) {
            return;
        }

        this.text_beschreibung.setText(termin.getBeschreibung());
        this.text_ort.setText(termin.getOrt());

        this.combo_tag.setSelectedItem(termin.getTag());
        this.date_start.setDate(termin.getStart());
        this.date_ende.setDate(termin.getEnde());

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(termin.getStart());
        cal2.setTime(termin.getEnde());
        if (cal1.get(Calendar.HOUR_OF_DAY) == 0 && cal2.get(Calendar.HOUR_OF_DAY) == 23
                && cal2.get(Calendar.MINUTE) == 59) {
            this.check_ganztag.setSelected(true);
        } else {
            this.check_ganztag.setSelected(false);
        }

        // Keine
        //30 Minuten vorher
        //1 Stunde vorher
        //2 Stunden vorher
        //1 Tag vorher
        //Benutzerdefiniert

        if (termin.getErinnerung() == null) {
            this.combo_erinnerung.setSelectedIndex(0);
        } else {
            cal2.setTime(termin.getErinnerung());
            long difftime = (termin.getErinnerung().getTime() - termin.getStart().getTime()) / 1000;
            if (difftime == 30) {
                this.combo_erinnerung.setSelectedIndex(1);
            } else if (difftime == 60) {
                this.combo_erinnerung.setSelectedIndex(2);
            } else if (difftime == 120) {
                this.combo_erinnerung.setSelectedIndex(3);
            } else if (difftime == 60 * 24) {
                this.combo_erinnerung.setSelectedIndex(4);
            } else {
                // Benutzerdefiniert
                this.combo_erinnerung.setSelectedIndex(5);
                date_erinnerung.setVisible(true);
                date_erinnerung.setDate(termin.getErinnerung());
            }
        }

        if (termin.getKundeKennung() == null) {
            this.combo_kunden.setSelectedIndex(0);
        } else {
            this.combo_kunden.setSelectedItem(termin.getKundeKennung());
        }

        if (termin.isPublic() == false) {
            this.check_public.setSelected(false);
        } else {
            this.check_public.setSelected(true);
            if (termin.getBesitzer() != BasicRegistry.currentUser.getId()) {
                if (!Security.isAllowed(SecurityTasks.TERMIN_EDIT_ANDERERBENUTZER)) {
                    this.text_beschreibung.setEnabled(false);
                    this.text_ort.setEnabled(false);
                    this.combo_tag.setEnabled(false);
                    this.date_start.setEnabled(false);
                    this.date_ende.setEnabled(false);
                    this.check_ganztag.setEnabled(false);
                    this.combo_erinnerung.setEnabled(false);
                    this.combo_kunden.setEnabled(false);
                    this.combo_tag.setEnabled(false);
                    this.btnSave.setEnabled(false);
                }
            }
        }

        this.setTitle("Termin bearbeiten: " + termin.getId());
    }

    public void loadKunden() {
        this.combo_kunden.setModel(new DefaultComboBoxModel(
                ComboBoxGetter.getAlleKundenCombo("Kein Kunde", Status.NORMAL)));
    }

    public void checkPublic() {
        if (!Security.isAllowed(SecurityTasks.TERMIN_PUBLIC)) {
            this.check_public.setEnabled(false);
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

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        scroll_beschreibung = new javax.swing.JScrollPane();
        text_beschreibung = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        scroll_ort = new javax.swing.JScrollPane();
        text_ort = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        combo_tag = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        date_start = new com.toedter.calendar.JDateChooser();
        date_ende = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        check_ganztag = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        combo_erinnerung = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        combo_kunden = new javax.swing.JComboBox();
        check_public = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        date_erinnerung = new com.toedter.calendar.JDateChooser();
        btnTags = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NeuerTermin.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        scroll_beschreibung.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_beschreibung.setName("scroll_beschreibung"); // NOI18N

        text_beschreibung.setColumns(20);
        text_beschreibung.setLineWrap(true);
        text_beschreibung.setRows(5);
        text_beschreibung.setWrapStyleWord(true);
        text_beschreibung.setName("text_beschreibung"); // NOI18N
        scroll_beschreibung.setViewportView(text_beschreibung);

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        scroll_ort.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_ort.setName("scroll_ort"); // NOI18N

        text_ort.setColumns(20);
        text_ort.setLineWrap(true);
        text_ort.setRows(5);
        text_ort.setWrapStyleWord(true);
        text_ort.setName("text_ort"); // NOI18N
        scroll_ort.setViewportView(text_ort);

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        combo_tag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_tag.setName("combo_tag"); // NOI18N
        combo_tag.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        date_start.setDateFormatString(resourceMap.getString("date_start.dateFormatString")); // NOI18N
        date_start.setName("date_start"); // NOI18N

        date_ende.setDateFormatString(resourceMap.getString("date_ende.dateFormatString")); // NOI18N
        date_ende.setName("date_ende"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        check_ganztag.setText(resourceMap.getString("check_ganztag.text")); // NOI18N
        check_ganztag.setName("check_ganztag"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        combo_erinnerung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine", "30 Minuten vorher", "Eine Stunde vorher", "Zwei Stunden vorher", "Ein Tag vorher", "Benutzerdefiniert" }));
        combo_erinnerung.setName("combo_erinnerung"); // NOI18N
        combo_erinnerung.setPreferredSize(new java.awt.Dimension(200, 25));
        combo_erinnerung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_erinnerungActionPerformed(evt);
            }
        });

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
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

        check_public.setText(resourceMap.getString("check_public.text")); // NOI18N
        check_public.setToolTipText(resourceMap.getString("check_public.toolTipText")); // NOI18N
        check_public.setName("check_public"); // NOI18N

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(120, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jSeparator4.setName("jSeparator4"); // NOI18N

        date_erinnerung.setDateFormatString(resourceMap.getString("date_erinnerung.dateFormatString")); // NOI18N
        date_erinnerung.setName("date_erinnerung"); // NOI18N

        btnTags.setIcon(resourceMap.getIcon("btnTags.icon")); // NOI18N
        btnTags.setText(resourceMap.getString("btnTags.text")); // NOI18N
        btnTags.setName("btnTags"); // NOI18N
        btnTags.setPreferredSize(new java.awt.Dimension(28, 26));
        btnTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_beschreibung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(22, 22, 22))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(combo_tag, 0, 350, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scroll_ort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)))
                    .addComponent(check_public)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(date_ende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(date_start, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                        .addComponent(check_ganztag))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                        .addComponent(combo_erinnerung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_kunden, 0, 374, Short.MAX_VALUE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(date_erinnerung, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_beschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_ort, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(check_public)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(date_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(date_ende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_ganztag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_erinnerung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date_erinnerung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        this.checkPublic();
        date_erinnerung.setVisible(false);
        this.validate();

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
            termin = new TerminObj();
        }

        if (this.text_beschreibung.getText() == null || this.text_beschreibung.getText().length() < 1) {
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

        if (update == false) {
            termin.setBesitzer(BasicRegistry.currentUser.getId());
        }

        termin.setBeschreibung(this.text_beschreibung.getText());
        termin.setOrt(this.text_ort.getText());

        termin.setEnde(new java.sql.Timestamp(this.date_ende.getDate().getTime()));
        termin.setStart(new java.sql.Timestamp(this.date_start.getDate().getTime()));

        if (this.check_ganztag.isSelected()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.date_start.getDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            termin.setStart(new java.sql.Timestamp(cal.getTimeInMillis()));

            cal.setTime(this.date_ende.getDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            termin.setEnde(new java.sql.Timestamp(cal.getTimeInMillis()));
        }

        if (this.check_public.isSelected()) {
            termin.setPublic(true);
        } else {
            termin.setPublic(false);
        }

        //termin.setErinnerung(null);
//        this.combo_erinnerung.getSelectedIndex();

// Keine
//30 Minuten vorher
//1 Stunde vorher
//2 Stunden vorher
//1 Tag vorher
//Benutzerdefiniert

        if (this.combo_erinnerung.getSelectedIndex() == 1) {
            long time = this.date_start.getDate().getTime() - (30 * 60 * 1000);
            termin.setErinnerung(new java.sql.Timestamp(time));
        } else if (this.combo_erinnerung.getSelectedIndex() == 2) {
            long time = this.date_start.getDate().getTime() - (60 * 60 * 1000);
            termin.setErinnerung(new java.sql.Timestamp(time));
        } else if (this.combo_erinnerung.getSelectedIndex() == 3) {
            long time = this.date_start.getDate().getTime() - (120 * 60 * 1000);
            termin.setErinnerung(new java.sql.Timestamp(time));
        } else if (this.combo_erinnerung.getSelectedIndex() == 4) {
            long time = this.date_start.getDate().getTime() - (24 * 60 * 60 * 1000);
            termin.setErinnerung(new java.sql.Timestamp(time));
        } else if (this.combo_erinnerung.getSelectedIndex() == 5) {
            long time = this.date_erinnerung.getDate().getTime();
            termin.setErinnerung(new java.sql.Timestamp(time));
        } else {
            termin.setErinnerung(null);
        }

        if (this.combo_kunden.getSelectedIndex() != 0) {
            Object sel = this.combo_kunden.getSelectedItem();
            if(sel.getClass().equals(KundenObj.class)) {
                KundenObj kunde = (KundenObj) sel;
                termin.setKundeKennung(kunde.getKundenNr());
            } else if (sel.equals(FirmenObj.class)) {
                FirmenObj firma = (FirmenObj) sel;
                termin.setKundeKennung(firma.getKundenNr());
            }
        } else {
            termin.setKundeKennung(null);
        }

        TagObj selected = (TagObj) this.combo_tag.getSelectedItem();

        termin.setTag(selected.getName());
        termin.setStatus(Status.NORMAL);

        termin.setLastmodified(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            if (update == false) {
                termin.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                TermineSQLMethods.insertIntoTermine(DatabaseConnection.open(), termin);
            } else {
                boolean work = TermineSQLMethods.updateTermin(DatabaseConnection.open(), termin);

                if (work == false) {
                    Log.databaselogger.fatal("Konnte Termin nicht aktualisieren");
                    ShowException.showException("Bei der Aktualisierung des Termins ist ein Fehler aufgetretten. "
                            + "Der Termin wurde nicht gefunden. "
                            + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, "Schwerwiegend: Konnte den Termin nicht speichern");                    
                }

            }
            this.dispose();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte neuen Termin nicht speichern", e);
            ShowException.showException("Beim Speichern des Termins ist ein Datenbankfehler aufgetretten. "
                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Termin nicht speichern");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagsActionPerformed
        TagDialogHelper.showTagDialog();
    }//GEN-LAST:event_btnTagsActionPerformed

    private void combo_erinnerungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_erinnerungActionPerformed
        if (combo_erinnerung.getSelectedIndex() == 5) {
            this.date_erinnerung.setEnabled(true);
        }
    }//GEN-LAST:event_combo_erinnerungActionPerformed

    private void combo_kundenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundenActionPerformed
        if (this.text_beschreibung.getText() == null || this.text_beschreibung.getText().length() < 1) {
            Object sel = combo_kunden.getSelectedItem();

            if (sel.getClass().equals(KundenObj.class)) {
                KundenObj knd = (KundenObj) sel;
                this.text_ort.setText(knd.getVorname() + " " + knd.getNachname() + ", "
                        + knd.getStreet() + ", " + knd.getPlz() + " " + knd.getStadt());
            } else if (sel.getClass().equals(FirmenObj.class)) {
                FirmenObj knd = (FirmenObj) sel;
                this.text_ort.setText(knd.getFirmenName() + ", " + knd.getFirmenStrasse() 
                        + ", " + knd.getFirmenPLZ() + " " + knd.getFirmenStadt());
            }            
        }
    }//GEN-LAST:event_combo_kundenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NeuerTermin dialog = new NeuerTermin(new javax.swing.JFrame(), true);
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
    public javax.swing.JCheckBox check_ganztag;
    public javax.swing.JCheckBox check_public;
    public javax.swing.JComboBox combo_erinnerung;
    public javax.swing.JComboBox combo_kunden;
    public javax.swing.JComboBox combo_tag;
    public com.toedter.calendar.JDateChooser date_ende;
    public com.toedter.calendar.JDateChooser date_erinnerung;
    public com.toedter.calendar.JDateChooser date_start;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JScrollPane scroll_beschreibung;
    public javax.swing.JScrollPane scroll_ort;
    public javax.swing.JTextArea text_beschreibung;
    public javax.swing.JTextArea text_ort;
    // End of variables declaration//GEN-END:variables
}
