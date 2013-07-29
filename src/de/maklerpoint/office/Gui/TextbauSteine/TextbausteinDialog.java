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
 * TextbausteinDialog.java
 *
 * Created on Jul 29, 2010, 12:49:24 PM
 */

package de.maklerpoint.office.Gui.TextbauSteine;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Textbausteine.TextbausteinGroupObj;
import de.maklerpoint.office.Textbausteine.TextbausteinObj;
import de.maklerpoint.office.Textbausteine.Tools.TextbausteineSQLMethods;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TextbausteinDialog extends javax.swing.JDialog {

    private static int TEXTFIELD = 0;
    private static int TEXTAREA = 1;


    private TextbausteinObj currentBaustein = null;
    private int TYPE = -1;

    private JTextField tf = null;
    private JTextArea ta = null;


    /** Creates new form TextbausteinDialog */
    public TextbausteinDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.TYPE = -1;
        initComponents();
        setUp();
    }

    public TextbausteinDialog(java.awt.Frame parent, boolean modal, JTextField text) {
        super(parent, modal);
        this.TYPE = TEXTFIELD;
        this.tf = text;
        initComponents();                
        setUp();
    }

    public TextbausteinDialog(java.awt.Frame parent, boolean modal, JTextArea text) {
        super(parent, modal);
        this.TYPE = TEXTAREA;
        this.ta = text;
        initComponents();
        setUp();
    }

    /**
     * 
     */

    private void setUp() {
        setUp(-1);
    }

    private void setUp(int id) {
        int gid = setUpGrps();
        TextbausteinObj tb = setUpBausteine(gid);
        showTextBaustein(tb);
    }

    /**
     * 
     * @return
     */

    public int setUpGrps() {
        int id = -1;
        TextbausteinGroupObj[] bs = ToolsRegistry.getTextBausteinGroups(true);

        DefaultListModel grpModel = new DefaultListModel();

        if(bs == null) {
            Log.logger.info("Keine Textbaustein Gruppen in der Datenbank");
            grpModel.add(0, "Keine Textbausteingruppen");

        } else {

            for(int i = 0; i < bs.length; i++) {
                grpModel.add(i, bs[i]);
//                System.out.println("BS[i] id " + bs[i].getId());
            }

            id = bs[0].getId();
        }

        list_gruppe.setModel(grpModel);
        list_gruppe.revalidate();

        return id;
    }

    /**
     * 
     * @param grpId
     * @return
     */

    public TextbausteinObj setUpBausteine(int grpId) {
        Log.logger.debug("GrpId: " + grpId);

        TextbausteinObj rettb = null;
        TextbausteinObj[] tb = ToolsRegistry.getTextBausteine(grpId, true);

        DefaultListModel bauModel = new DefaultListModel();

        if(grpId == -1 || tb == null || tb.length == 0) {
            Log.logger.debug("Keine Textbausteine in der Gruppe mit der Id \"" + grpId + "\" vorhanden");
            bauModel.add(0, "Keine Textbausteine");
        } else {

            Log.logger.debug("TB length: " + tb.length);

            for(int i = 0; i < tb.length; i++) {
                bauModel.add(i, tb[i]);
            }

            rettb = tb[0];
        }

        list_bausteine.setModel(bauModel);
        list_bausteine.revalidate();

        return rettb;
    }

    /**
     * 
     * @param tb
     */

    public void showTextBaustein(TextbausteinObj tb) {
        if(currentBaustein != null && this.field_bausteinname.getText() != null) {
            if(!this.field_bausteinname.getText().equalsIgnoreCase(currentBaustein.getName())
                    || !this.editor_textbaustein.getText().equalsIgnoreCase(currentBaustein.getBeschreibung())) {
                
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den veränderten Textbaustein speichern?",
                        "Änderungen speichern", JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {
                    TextbausteinObj oldtb = currentBaustein;
                    oldtb.setName(field_bausteinname.getText());
                    oldtb.setBeschreibung(editor_textbaustein.getText());
                    oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                    try {
                        TextbausteineSQLMethods.updatetextbausteine(DatabaseConnection.open(), oldtb);
                    } catch (SQLException e) {
                        Log.databaselogger.fatal("Der Textbaustein mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden.", e);
                        ShowException.showException("Datenbankfehler: Der Textbaustein mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden",
                              ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Textbaustein nicht updaten");
                    }
                    setUp();
                }
            }
        }


        if(tb == null) {
            this.editor_textbaustein.setText(null);
            this.field_bausteinname.setText(null);
            currentBaustein = null;
            return;
        }

        this.editor_textbaustein.setText(tb.getBeschreibung());
        this.field_bausteinname.setText(tb.getName());

        currentBaustein = tb;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        field_bausteinname = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNeu = new javax.swing.JButton();
        btnNeuGrp = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDelete = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_gruppe = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_bausteine = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        editor_textbaustein = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TextbausteinDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setIconImage(null);
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        field_bausteinname.setText(resourceMap.getString("field_bausteinname.text")); // NOI18N
        field_bausteinname.setName("field_bausteinname"); // NOI18N

        btnInsert.setIcon(resourceMap.getIcon("btnInsert.icon")); // NOI18N
        btnInsert.setMnemonic('E');
        btnInsert.setText(resourceMap.getString("btnInsert.text")); // NOI18N
        btnInsert.setName("btnInsert"); // NOI18N
        btnInsert.setPreferredSize(new java.awt.Dimension(90, 27));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(90, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setMnemonic('N');
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNeu);

        btnNeuGrp.setIcon(resourceMap.getIcon("btnNeuGrp.icon")); // NOI18N
        btnNeuGrp.setMnemonic('N');
        btnNeuGrp.setText(resourceMap.getString("btnNeuGrp.text")); // NOI18N
        btnNeuGrp.setFocusable(false);
        btnNeuGrp.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnNeuGrp.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuGrp.setName("btnNeuGrp"); // NOI18N
        btnNeuGrp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuGrpActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNeuGrp);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar1.add(jSeparator2);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setMnemonic('L');
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_searchActionPerformed(evt);
            }
        });
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_gruppe.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_gruppe.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_gruppe.setName("list_gruppe"); // NOI18N
        list_gruppe.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_gruppeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_gruppe);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        list_bausteine.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_bausteine.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_bausteine.setName("list_bausteine"); // NOI18N
        list_bausteine.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_bausteineValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(list_bausteine);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        editor_textbaustein.setColumns(20);
        editor_textbaustein.setLineWrap(true);
        editor_textbaustein.setRows(5);
        editor_textbaustein.setWrapStyleWord(true);
        editor_textbaustein.setName("editor_textbaustein"); // NOI18N
        jScrollPane3.setViewportView(editor_textbaustein);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(field_bausteinname, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(150, 150, 150)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(field_bausteinname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void field_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_searchActionPerformed

}//GEN-LAST:event_field_searchActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        //        if(field_search.getText().length() > 3)
        //            searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        //        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnNeuGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuGrpActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Namen für die neue Gruppe ein.",
                "Name der neuen Gruppe", JOptionPane.INFORMATION_MESSAGE);

        if(name == null)
            return;

        TextbausteinGroupObj tb = new TextbausteinGroupObj();

        tb.setName(name);
        tb.setStatus(Status.NORMAL);

        try {
            TextbausteineSQLMethods.newTextBausteinGrp(DatabaseConnection.open(), tb);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Textbaustein Gruppe nicht in der Datenbank speichern", e);
            ShowException.showException("Datenbankfehler: Die Textbaustein Gruppe konnte nicht in der Datenbank gespeichert werden. ",
                  ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Textbaustein Gruppe nicht speichern");
        }

        setUp();
    }//GEN-LAST:event_btnNeuGrpActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        TextbausteinDialogHelper.open = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        if(TYPE == TextbausteinDialog.TEXTAREA) {
            this.ta.append(" " + this.editor_textbaustein.getText());
        } else if (TYPE == TextbausteinDialog.TEXTFIELD) {
            this.tf.setText(tf.getText() + " " + this.editor_textbaustein.getText());
        }

        TextbausteinDialogHelper.open = false;
        this.dispose();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       TextbausteinDialogHelper.open = false;
    }//GEN-LAST:event_formWindowClosing

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Namen für den neuen Textbaustein ein.",
                "Name des neuen Textbausteins", JOptionPane.INFORMATION_MESSAGE);

        if(name == null)
            return;

        int id = -1;

        TextbausteinObj tb = new TextbausteinObj();
        tb.setName(name);
        // TODO
        tb.setGroup(0);

        tb.setBenutzerId(BasicRegistry.currentUser.getId());
        tb.setBeschreibung("");
        tb.setStatus(Status.NORMAL);
        tb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        tb.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            id = TextbausteineSQLMethods.insertIntotextbausteine(DatabaseConnection.open(), tb);
            tb.setId(id);
            
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den neuen Textbaustein nicht in der Datenbank speichern", e);
            ShowException.showException("Datenbankfehler: Der neue Textbaustein konnte nicht in der Datenbank gespeichert werden. ",
                  ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Textbaustein nicht speichern");
        }

        // TODO:
        // ADD SETUP (id)
        setUp(id);
    }//GEN-LAST:event_btnNeuActionPerformed

    private void list_gruppeValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_gruppeValueChanged
        if(evt.getValueIsAdjusting())
            return;

//        System.out.println("index: " + list_gruppe.getSelectedIndex());

        if(list_gruppe.getSelectedIndex() == -1)
            return;

        if(list_gruppe.getSelectedValue().toString().equalsIgnoreCase("keine textbausteingruppen"))
            return;

        TextbausteinGroupObj tg = (TextbausteinGroupObj) list_gruppe.getSelectedValue();

//        System.out.println("TG ID: " + tg.getId());
//        System.out.println("TG NAME: " + tg.getName());
        if(tg == null)
            return;

        setUpBausteine(tg.getId());
    }//GEN-LAST:event_list_gruppeValueChanged

    private void list_bausteineValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_bausteineValueChanged
        if(evt.getValueIsAdjusting())
            return;

        if(list_bausteine.getSelectedIndex() == -1)
            return;

        if(list_bausteine.getSelectedValue().toString().equalsIgnoreCase("keine textbausteine"))
            return;

        TextbausteinObj tb = (TextbausteinObj) list_bausteine.getSelectedValue();

        this.showTextBaustein(tb);
    }//GEN-LAST:event_list_bausteineValueChanged

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(list_bausteine.getSelectedIndex() == -1)
            return;

        if(list_bausteine.getSelectedValue().toString().equalsIgnoreCase("keine textbausteine"))
            return;

        TextbausteinObj tb = (TextbausteinObj) list_bausteine.getSelectedValue();

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Baustein \"" + tb.getName() + "\" wirklich löschen?");

        if(answer != JOptionPane.YES_OPTION)
            return;

        try {
            TextbausteineSQLMethods.deleteFromtextbausteine(DatabaseConnection.open(), tb.getId());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Textbaustein mit der id " + tb.getId() + " nicht löschen", e);
            ShowException.showException("Datenbankfehler: Der Textbaustein mit der id " + tb.getId() + " konnte nicht gelöscht werden. ",
                  ExceptionDialogGui.LEVEL_WARNING, e,
                  "Schwerwiegend: Konnte Textbaustein konnte nicht gelöscht werden");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TextbausteinDialog dialog = new TextbausteinDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnNeuGrp;
    private javax.swing.JButton btnSearch;
    private javax.swing.JTextArea editor_textbaustein;
    private javax.swing.JTextField field_bausteinname;
    private javax.swing.JTextField field_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList list_bausteine;
    private javax.swing.JList list_gruppe;
    // End of variables declaration//GEN-END:variables

}
