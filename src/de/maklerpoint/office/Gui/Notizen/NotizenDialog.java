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
 * NotizenDialog.java
 *
 * Created on 21.08.2010, 12:11:54
 */

package de.maklerpoint.office.Gui.Notizen;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Configuration.TagDialogHelper;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Notizen.NotizenObj;
import de.maklerpoint.office.Notizen.Tools.NotizenSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tags.TagObj;
import de.maklerpoint.office.Tags.Tags;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class NotizenDialog extends javax.swing.JDialog {

    private NotizenObj notiz;
    private boolean update = false;
    private NotizenObj currentNotiz;

    /** Creates new form NotizenDialog */
    public NotizenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        initComponents();
        setUp(-1);
    }

    public NotizenDialog(java.awt.Frame parent, boolean modal, NotizenObj notiz) {
        super(parent, modal);
        this.update = true;
        this.notiz = notiz;
        initComponents();
        setUp(-1);
    }

    private void setUp(int cnt){
        DefaultListModel dm = new DefaultListModel();

        NotizenObj[] notizen = ToolsRegistry.getEigeneNotizen(Status.NORMAL);
        if(notizen == null) {
            return;
        }

        for(int i = 0; i < notizen.length; i++) {
            dm.add(i, notizen[i]);
        }

        this.list_notizen.setModel(dm);
        this.list_notizen.revalidate();

        if(cnt == -2) {

        } else if(cnt == -1) {
            showNotiz(notizen[0]);
        } else {
            showNotiz(notizen[cnt]);
        }
    }

    public void showNotiz(NotizenObj nz) {
        if(currentNotiz != null && this.combo_betreff.getSelectedItem() != null) {
            if(!this.combo_betreff.getSelectedItem().toString().equalsIgnoreCase(currentNotiz.getBetreff())
                    || !this.editorpane.getText().equalsIgnoreCase(currentNotiz.getText())) {
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die veränderte Notiz speichern?",
                        "Änderungen speichern", JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {
                    NotizenObj oldtb = currentNotiz;
                    oldtb.setBetreff(combo_betreff.getSelectedItem().toString());
                    oldtb.setText(editorpane.getText());
                    TagObj tag = (TagObj) this.combo_tag.getSelectedItem();
                    oldtb.setTag(tag.getName());

                    if(this.combo_kunden.getSelectedIndex() != 0) {
                        try {
                            KundenObj kunde = (KundenObj) combo_kunden.getSelectedItem();
                            oldtb.setKundenKennung(kunde.getKundenNr());
                        } catch (Exception e) {
                            FirmenObj firma = (FirmenObj) combo_kunden.getSelectedItem();
                            oldtb.setKundenKennung(firma.getKundenNr());
                        }
                    } else {
                        oldtb.setKundenKennung(null);
                    }

                    oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                    try {
                        NotizenSQLMethods.updatenotizen(DatabaseConnection.open(), oldtb);
                    } catch (SQLException e) {
                        Log.databaselogger.fatal("Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden.", e);
                        ShowException.showException("Datenbankfehler: Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht aktualisiert werden",
                              ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht updaten");
                    }

                    setUp(-2);
                }
            }
        }

        this.combo_betreff.setSelectedItem(nz.getBetreff());

        if(nz.getText() != null)
            this.editorpane.setText(nz.getText());

        this.combo_tag.setSelectedItem(nz.getTag());
        
        if(nz.getKundenKennung() != null)
            this.combo_kunden.setSelectedItem(nz.getKundenKennung());
        else {
            this.combo_kunden.setSelectedIndex(0);
        }

        currentNotiz = nz;
    }   

    public void loadTags() {
//        dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));
        this.combo_tag.setModel(new DefaultComboBoxModel(Tags.tags));
    }

    public void loadKunden() {
        Object[] kunden = KundenRegistry.getAlleAktivenKunden();
        Object[] data = new Object[kunden.length + 1];

        data[0] = "Keiner";

        for(int i = 1; i <= kunden.length; i++) {
            data[i] = kunden[i-1];
        }

        this.combo_kunden.setModel(new DefaultComboBoxModel(data));
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnNeu = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        editorpane = new net.atlanticbb.tantlinger.shef.HTMLEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_notizen = new javax.swing.JList();
        combo_betreff = new javax.swing.JComboBox();
        btnTags = new javax.swing.JButton();
        combo_tag = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        combo_kunden = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NotizenDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNeu);

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar1.add(jSeparator2);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel6);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        jToolBar1.add(field_search);

        btnSearch.setIcon(resourceMap.getIcon("btnSearch.icon")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearch);

        jSeparator5.setName("jSeparator5"); // NOI18N
        jToolBar1.add(jSeparator5);

        editorpane.setName("editorpane"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_notizen.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Keine Notizen" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_notizen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_notizen.setName("list_notizen"); // NOI18N
        list_notizen.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_notizenValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_notizen);

        combo_betreff.setEditable(true);
        combo_betreff.setName("combo_betreff"); // NOI18N

        btnTags.setIcon(resourceMap.getIcon("btnTags.icon")); // NOI18N
        btnTags.setName("btnTags"); // NOI18N
        btnTags.setPreferredSize(new java.awt.Dimension(28, 26));
        btnTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagsActionPerformed(evt);
            }
        });

        combo_tag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_tag.setName("combo_tag"); // NOI18N
        combo_tag.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        combo_kunden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keiner" }));
        combo_kunden.setName("combo_kunden"); // NOI18N
        combo_kunden.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editorpane, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_betreff, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(combo_tag, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_betreff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(combo_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editorpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        this.loadTags();
        this.loadKunden();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagsActionPerformed
        TagDialogHelper.showTagDialog();
}//GEN-LAST:event_btnTagsActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        //        if(field_search.getText().length() > 3)
        //            searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        //        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        NotizenDialogHelper.open = false;
    }//GEN-LAST:event_formWindowClosing

    private void list_notizenValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_notizenValueChanged
        if(evt.getValueIsAdjusting())
            return;

        if(list_notizen.getSelectedIndex() == -1)
            return;

        if(list_notizen.getSelectedValue().toString().equalsIgnoreCase("keine notizen"))
            return;

        NotizenObj nz = (NotizenObj) list_notizen.getSelectedValue();

        if(nz == null)
            return;

        this.showNotiz(nz);
    }//GEN-LAST:event_list_notizenValueChanged

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Namen für die neue Notiz ein.",
                "Name der neuen Notiz", JOptionPane.INFORMATION_MESSAGE);

        if(name == null)
            return;

        NotizenObj nz = new NotizenObj();

        nz.setBetreff(name);
        nz.setText("");
        nz.setCreatorId(BasicRegistry.currentUser.getId());
        nz.setKundenKennung(null);
        nz.setPrivate(true);
        nz.setTag("Standard");

        nz.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        nz.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        nz.setStatus(Status.NORMAL);

        try {
            NotizenSQLMethods.insertIntonotizen(DatabaseConnection.open(), nz);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die neue Notiz \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.", e);
            ShowException.showException("Datenbankfehler: Die Notiz \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.",
                  ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht speichern");
        }
        setUp(-1);
        this.showNotiz(nz);
    }//GEN-LAST:event_btnNeuActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(list_notizen.getSelectedIndex() == -1)
            return;

        if(list_notizen.getSelectedValue().toString().equalsIgnoreCase("keine notizen"))
            return;

        NotizenObj nz = (NotizenObj) list_notizen.getSelectedValue();

        if(nz == null)
            return;

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Notiz \"" + nz.getBetreff() + "\" wirklich löschen?");

        if(answer != JOptionPane.YES_OPTION)
            return;

        try {
            NotizenSQLMethods.deleteFromNotizen(DatabaseConnection.open(), nz.getId());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Notiz  \"" + nz.getBetreff() + "\" konnte nicht gelöscht werden.", e);
            ShowException.showException("Datenbankfehler: Die Notiz \"" + nz.getBetreff() + "\" konnte nicht gelöscht werden.",
                  ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht löschen");
        }
        setUp(-1);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(currentNotiz != null && this.combo_betreff.getSelectedItem() != null) {
            if(!this.combo_betreff.getSelectedItem().toString().equalsIgnoreCase(currentNotiz.getBetreff())
                    || !this.editorpane.getText().equalsIgnoreCase(currentNotiz.getText())) {
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die veränderte Notiz speichern?",
                        "Änderungen speichern", JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {
                    NotizenObj oldtb = currentNotiz;
                    oldtb.setBetreff(combo_betreff.getSelectedItem().toString());
                    oldtb.setText(editorpane.getText());
                    TagObj tag = (TagObj) this.combo_tag.getSelectedItem();
                    oldtb.setTag(tag.getName());

                    if(this.combo_kunden.getSelectedIndex() != 0) {
                        try {
                            KundenObj kunde = (KundenObj) combo_kunden.getSelectedItem();
                            oldtb.setKundenKennung(kunde.getKundenNr());
                        } catch (Exception e) {
                            FirmenObj firma = (FirmenObj) combo_kunden.getSelectedItem();
                            oldtb.setKundenKennung(firma.getKundenNr());
                        }
                    } else {
                        oldtb.setKundenKennung(null);
                    }

                    oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                    try {
                        NotizenSQLMethods.updatenotizen(DatabaseConnection.open(), oldtb);
                    } catch (SQLException e) {
                        Log.databaselogger.fatal("Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden.", e);
                        ShowException.showException("Datenbankfehler: Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden",
                              ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht updaten");
                    }
                    setUp(-1);
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NotizenDialog dialog = new NotizenDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTags;
    private javax.swing.JComboBox combo_betreff;
    private javax.swing.JComboBox combo_kunden;
    private javax.swing.JComboBox combo_tag;
    private net.atlanticbb.tantlinger.shef.HTMLEditorPane editorpane;
    private javax.swing.JTextField field_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList list_notizen;
    // End of variables declaration//GEN-END:variables

}
