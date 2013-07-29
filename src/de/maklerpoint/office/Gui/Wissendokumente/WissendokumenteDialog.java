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
 * WissendokumenteDialog.java
 *
 * Created on 22.08.2010, 17:30:35
 */

package de.maklerpoint.office.Gui.Wissendokumente;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Dokumente.Tools.WissenDokumenteSQLMethods;
import de.maklerpoint.office.Dokumente.WissenDokumentenObj;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Configuration.TagDialogHelper;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.TextbauSteine.TextbausteinDialogHelper;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.DokumentenRegistry;
import de.maklerpoint.office.Tags.Tags;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class WissendokumenteDialog extends javax.swing.JDialog {

    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private WissenDokumentenObj currentDokument = null;

    /** Creates new form WissendokumenteDialog */
    public WissendokumenteDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setUp(-1);
    }

    public void loadTags() {
//        dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));
        this.combo_tag.setModel(new DefaultComboBoxModel(Tags.tags));
    }

    public void setUp(int id) {
        WissenDokumentenObj show = null;
        WissenDokumentenObj[] dokumente = DokumentenRegistry.getWissenDokumente();
        if(dokumente == null)
            return;

        TreeSet categories = new TreeSet();

        DefaultListModel catModel = new DefaultListModel();
        DefaultListModel dokModel = new DefaultListModel();
        DefaultComboBoxModel cm = new DefaultComboBoxModel();
        LinkedHashSet hs = new LinkedHashSet();

        int cnt = 0;

        categories.add("Allgemein");
        hs.add("Allgemein");

        for(int i = 0; i < dokumente.length; i++) {
            WissenDokumentenObj dokument = dokumente[i];

            if(!dokument.getCategory().equalsIgnoreCase("allgemein")) {
                categories.add(dokument.getCategory());
                hs.add(dokument.getCategory());
            }

            if(dokument.getCategory().equalsIgnoreCase("allgemein")) {
                dokModel.add(cnt, dokument);
                cnt++;
            }

            if(id != -1) {
                if(dokument.getId() == id) {
                    show = dokument;
                }
            }
        }

        if(id == -1)
            show = dokumente[0];

        if(id == -1)
            this.combo_category.setModel(new DefaultComboBoxModel(hs.toArray()));

        for(int i = 0; i < categories.size(); i++) {
            catModel.add(i, categories.toArray()[i]);
        }

        list_gruppen.setModel(catModel);
        list_items.setModel(dokModel);
        
        list_gruppen.revalidate();
        list_items.revalidate();

        showDokument(show);
    }

    public void showDokument(WissenDokumentenObj wd) {
        if(currentDokument != null && field_dokname != null) {
            if(!currentDokument.getBeschreibung().equalsIgnoreCase(wd.getBeschreibung()) ||
               !currentDokument.getName().equalsIgnoreCase(wd.getName()) ||
               !currentDokument.getCategory().equalsIgnoreCase(wd.getCategory()) ||
               !currentDokument.getTag().equalsIgnoreCase(wd.getTag()) ||
               !currentDokument.getFullPath().equalsIgnoreCase(wd.getFullPath())) {
                saveDokument();
            }
        }

        this.field_dokname.setText(wd.getName());
        this.field_path.setText(wd.getFullPath());
        this.combo_category.setSelectedItem(wd.getCategory());
        this.combo_tag.setSelectedItem(Tags.getTag(wd.getTag()));
        this.label_createdate.setText(df.format(wd.getCreated()));
        this.label_moddate.setText(df.format(wd.getModified()));

        String create = "MaklerPoint Office";
        if (wd.getCreator() != -1) {
            BenutzerObj creator = BenutzerRegistry.getBenutzer(wd.getCreator(), false);
            if(creator != null)
                create = creator.toString();
        }
        
        this.label_creator.setText(create);
        
        this.area_beschreibung.setText(wd.getBeschreibung());
        currentDokument = wd;
    }

    public void saveDokument(){
        if(currentDokument == null) {
            return;
        }

        WissenDokumentenObj wd = currentDokument;

        wd.setName(this.field_dokname.getText());
        wd.setBeschreibung(this.area_beschreibung.getText());
        wd.setFullPath(this.field_path.getText());
        wd.setCategory(this.combo_category.getSelectedItem().toString());
        wd.setTag(combo_tag.getSelectedItem().toString());
        wd.setModified(new java.sql.Timestamp(System.currentTimeMillis()));       
        try {
            WissenDokumenteSQLMethods.updatewissendokumente(DatabaseConnection.open(), wd);
        } catch (SQLException ex) {
            Log.databaselogger.fatal("Fehler: Konnte Dokument nicht aktualisieren", ex);
            ShowException.showException("Das Dokument konnte nicht aktualisiert werden. ",
                ExceptionDialogGui.LEVEL_WARNING, ex, "Schwerwiegend: Konnte das Dokument nicht speichern");
        }
        
    }

    /**
     * 
     * @param category
     */

     public void loadDokumente(String category) {
        WissenDokumentenObj[] dokumente = DokumentenRegistry.getWissenDokumente();

        DefaultListModel inactive = (DefaultListModel) list_items.getModel();
        inactive.removeAllElements();

        int cnt = 0;

        for(int i = 0; i < dokumente.length; i++) {
            WissenDokumentenObj dokument = dokumente[i];

            if(dokument.getCategory().equalsIgnoreCase(category)) {
                inactive.addElement(dokument);
                cnt++;
            }
        }
        if(cnt == 0) {
            inactive.addElement("Keine Dateien");
        }

        list_items.setModel(inactive);
        list_items.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        list_items = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNeu = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jLabel12 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jScrollPane4 = new javax.swing.JScrollPane();
        list_gruppen = new javax.swing.JList();
        field_dokname = new javax.swing.JTextField();
        btnMaxDokumente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        combo_category = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label_creator = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        label_createdate = new javax.swing.JLabel();
        btnTags = new javax.swing.JButton();
        combo_tag = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        field_path = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        label_moddate = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_beschreibung = new javax.swing.JTextArea();
        btnMaxBeschreibung = new javax.swing.JButton();
        btnTbBeschreibung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(WissendokumenteDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        list_items.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Keine Dokumente" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_items.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_items.setName("list_items"); // NOI18N
        list_items.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_itemsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(list_items);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

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

        jSeparator5.setName("jSeparator5"); // NOI18N
        jToolBar1.add(jSeparator5);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(resourceMap.getIcon("jLabel12.icon")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel12);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jToolBar1.add(jSeparator6);

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

        jSeparator7.setName("jSeparator7"); // NOI18N
        jToolBar1.add(jSeparator7);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        list_gruppen.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Keine Dokumentengruppen" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_gruppen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_gruppen.setName("list_gruppen"); // NOI18N
        list_gruppen.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_gruppenValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(list_gruppen);

        field_dokname.setName("field_dokname"); // NOI18N

        btnMaxDokumente.setIcon(resourceMap.getIcon("btnMaxDokumente.icon")); // NOI18N
        btnMaxDokumente.setToolTipText(resourceMap.getString("btnMaxDokumente.toolTipText")); // NOI18N
        btnMaxDokumente.setName("btnMaxDokumente"); // NOI18N
        btnMaxDokumente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxDokumenteActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_category.setEditable(true);
        combo_category.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_category.setName("combo_category"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        label_creator.setText(resourceMap.getString("label_creator.text")); // NOI18N
        label_creator.setName("label_creator"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        label_createdate.setText(resourceMap.getString("label_createdate.text")); // NOI18N
        label_createdate.setName("label_createdate"); // NOI18N

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

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        field_path.setText(resourceMap.getString("field_path.text")); // NOI18N
        field_path.setName("field_path"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        label_moddate.setText(resourceMap.getString("label_moddate.text")); // NOI18N
        label_moddate.setName("label_moddate"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        area_beschreibung.setColumns(20);
        area_beschreibung.setLineWrap(true);
        area_beschreibung.setRows(5);
        area_beschreibung.setWrapStyleWord(true);
        area_beschreibung.setName("area_beschreibung"); // NOI18N
        area_beschreibung.setPreferredSize(new java.awt.Dimension(290, 75));
        jScrollPane1.setViewportView(area_beschreibung);

        btnMaxBeschreibung.setIcon(resourceMap.getIcon("btnMaxBeschreibung.icon")); // NOI18N
        btnMaxBeschreibung.setToolTipText(resourceMap.getString("btnMaxBeschreibung.toolTipText")); // NOI18N
        btnMaxBeschreibung.setName("btnMaxBeschreibung"); // NOI18N
        btnMaxBeschreibung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxBeschreibungActionPerformed(evt);
            }
        });

        btnTbBeschreibung.setIcon(resourceMap.getIcon("btnTbBeschreibung.icon")); // NOI18N
        btnTbBeschreibung.setName("btnTbBeschreibung"); // NOI18N
        btnTbBeschreibung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbBeschreibungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(field_dokname, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combo_category, 0, 313, Short.MAX_VALUE)
                                    .addComponent(combo_tag, 0, 313, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMaxDokumente)
                            .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(field_path, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 337, Short.MAX_VALUE)
                        .addComponent(label_creator))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                        .addComponent(label_createdate))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                        .addComponent(label_moddate))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMaxBeschreibung)
                            .addComponent(btnTbBeschreibung))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(8, 8, 8)
                                .addComponent(field_dokname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMaxDokumente))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(combo_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(combo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(label_creator))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(label_createdate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(label_moddate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnMaxBeschreibung)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTbBeschreibung))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        this.loadTags();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void list_itemsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_itemsValueChanged
        if(evt.getValueIsAdjusting())
            return;

        if(list_items.getSelectedIndex() == -1)
            return;

        if(!list_items.getSelectedValue().toString().equalsIgnoreCase("keine dateien")) {
            WissenDokumentenObj dok = (WissenDokumentenObj) list_items.getSelectedValue();

            showDokument(dok);
        }
}//GEN-LAST:event_list_itemsValueChanged

    private void list_gruppenValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_gruppenValueChanged
        if(evt.getValueIsAdjusting())
            return;

        if(list_gruppen.getSelectedIndex() == -1)
            return;

        if(list_gruppen.getSelectedValue().toString().equalsIgnoreCase("keine dokumentengruppen"))
            return;

        String category = (String) list_gruppen.getSelectedValue();

        loadDokumente(category);
    }//GEN-LAST:event_list_gruppenValueChanged

    private void btnMaxDokumenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxDokumenteActionPerformed
        MaximizeHelper.openMax(this.field_dokname, "Dokumenten Name");
}//GEN-LAST:event_btnMaxDokumenteActionPerformed

    private void btnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagsActionPerformed
        TagDialogHelper.showTagDialog();
}//GEN-LAST:event_btnTagsActionPerformed

    private void btnMaxBeschreibungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxBeschreibungActionPerformed
        MaximizeHelper.openMax(this.area_beschreibung, "Dokumenten Beschreibung");
    }//GEN-LAST:event_btnMaxBeschreibungActionPerformed

    private void btnTbBeschreibungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbBeschreibungActionPerformed
        TextbausteinDialogHelper.openTb(area_beschreibung);
}//GEN-LAST:event_btnTbBeschreibungActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Namen für das neue Dokument ein.",
                "Name des neuen Dokuments", JOptionPane.INFORMATION_MESSAGE);

        if(name == null)
            return;
        
//        try {
//            NotizenSQLMethods.insertIntonotizen(DatabaseConnection.open(), nz);
//        } catch (SQLException e) {
//            Log.databaselogger.fatal("Die neue Notiz  \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.", e);
//            ShowException.showException("Datenbankfehler: Die Notiz \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.",
//                    ExceptionDialogGui.LEVEL_WARNING, e.getLocalizedMessage(), "Schwerwiegend: Konnte Notiz nicht speichern");
//        }
//        setUp(-1);
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

}//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(list_items.getSelectedIndex() == -1)
            return;

        if(list_items.getSelectedValue().toString().equalsIgnoreCase("keine dokumente"))
            return;

        WissenDokumentenObj wd = (WissenDokumentenObj) list_items.getSelectedValue();

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Wissensdokument \"" + wd.getName() + "\" wirklich löschen?");

        if(answer != JOptionPane.YES_OPTION)
            return;

        try {
            WissenDokumenteSQLMethods.deleteFromDokumente(DatabaseConnection.open(), wd.getId());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Das Wissensdokument  \"" + wd.getName() + "\" konnte nicht gelöscht werden.", e);
            ShowException.showException("Datenbankfehler: Das Wissensdokument \"" + wd.getName() + "\" konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dokument nicht löschen");
        }
        setUp(-1);
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        //        if(field_search.getText().length() > 3)
        //            searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        //        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        WissendokumenteDialogHelper.open = false;
    }//GEN-LAST:event_formWindowClosing

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WissendokumenteDialog dialog = new WissendokumenteDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea area_beschreibung;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMaxBeschreibung;
    private javax.swing.JButton btnMaxDokumente;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnTags;
    private javax.swing.JButton btnTbBeschreibung;
    private javax.swing.JComboBox combo_category;
    private javax.swing.JComboBox combo_tag;
    private javax.swing.JTextField field_dokname;
    private javax.swing.JTextField field_path;
    private javax.swing.JTextField field_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel label_createdate;
    private javax.swing.JLabel label_creator;
    private javax.swing.JLabel label_moddate;
    private javax.swing.JList list_gruppen;
    private javax.swing.JList list_items;
    // End of variables declaration//GEN-END:variables

}
