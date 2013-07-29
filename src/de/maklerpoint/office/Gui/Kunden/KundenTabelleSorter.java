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
 * KundenTabelleSorter.java
 *
 * Created on Jul 7, 2010, 6:55:06 PM
 */
package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht;
import de.maklerpoint.office.Gui.Schaden.PanelSchaeden;
import de.maklerpoint.office.Gui.Stoerfall.PanelStoerfaelle;
import de.maklerpoint.office.Gui.Versicherer.PanelProduktUebersicht;
import de.maklerpoint.office.Gui.Versicherer.PanelVersichererUebersicht;
import de.maklerpoint.office.Gui.Vertraege.PanelVertraege;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenTabelleSorter extends javax.swing.JDialog {

    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean changed = false;
    private Preferences prefs = Preferences.userRoot().node(PanelKundenUebersicht.class.getName());
    private Preferences prefsfk = Preferences.userRoot().node(PanelFirmenKundenUebersicht.class.getName());
    private Preferences prefsvtr = Preferences.userRoot().node(PanelVertraege.class.getName());
    private Preferences prefsvers = Preferences.userRoot().node(PanelVersichererUebersicht.class.getName());
    private Preferences prefsprod = Preferences.userRoot().node(PanelProduktUebersicht.class.getName());
    private Preferences prefsstoer = Preferences.userRoot().node(PanelStoerfaelle.class.getName());
    private Preferences prefssch = Preferences.userRoot().node(PanelSchaeden.class.getName());
    
    private PanelKundenUebersicht panel;
    private PanelFirmenKundenUebersicht panelfk;
    private PanelVertraege panelvtr;
    private PanelVersichererUebersicht panelvers;
    private PanelProduktUebersicht panelprod;
    private PanelStoerfaelle panelstoer;
    private PanelSchaeden panelsch;

    /** Creates new form KundenTabelleSorter */
    public KundenTabelleSorter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.dispose();
        initComponents();
    }

    /**
     * 
     * @param parent
     * @param modal
     * @param activeItems
     * @param inactiveItems
     */
    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelKundenUebersicht panel) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = panel;
        this.panelfk = null;
        this.panelvtr = null;
        this.panelvers = null;
        this.panelprod = null;
        this.panelstoer = null;
        this.panelsch = null;
        initComponents();
        setUp();
    }

    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelFirmenKundenUebersicht panelgk) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelvtr = null;
        this.panelfk = panelgk;
        this.panelvers = null;
        this.panelprod = null;
        this.panelstoer = null;
        this.panelsch = null;
        initComponents();
        setUp();
    }

    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelVertraege panelvtr) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelfk = null;
        this.panelvtr = panelvtr;
        this.panelvers = null;
        this.panelprod = null;
        this.panelstoer = null;
        this.panelsch = null;
        initComponents();
        setUp();
    }

    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelVersichererUebersicht panelvers) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelfk = null;
        this.panelvtr = null;
        this.panelprod = null;
        this.panelvers = panelvers;
        this.panelstoer = null;
        this.panelsch = null;
        initComponents();
        setUp();
    }
    
    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelProduktUebersicht panelprod) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelfk = null;
        this.panelvtr = null;
        this.panelprod = panelprod;
        this.panelvers = null;
        this.panelstoer = null;
        this.panelsch = null;
        initComponents();
        setUp();
    }

    
    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelStoerfaelle panelstoer) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelfk = null;
        this.panelvtr = null;
        this.panelprod = null;
        this.panelvers = null;
        this.panelstoer = panelstoer;
        this.panelsch = null;
        initComponents();
        setUp();
    }
    
    public KundenTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelSchaeden panelsch) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = null;
        this.panelfk = null;
        this.panelvtr = null;
        this.panelprod = null;
        this.panelvers = null;
        this.panelstoer = null;
        this.panelsch = panelsch;
        initComponents();
        setUp();
    }
    
    private void setUp() {
        this.loadActive();
        this.loadInactive();
    }

    /**
     *
     */
    public void loadActive() {
        DefaultListModel activeModel = new DefaultListModel();
        for (int i = 0; i < activeItems.length; i++) {
            activeModel.add(i, activeItems[i]);
        }
        list_active.setModel(activeModel);
        list_active.revalidate();
    }

    /**
     * 
     */
    public void loadInactive() {
        DefaultListModel inactiveModel = new DefaultListModel();
        for (int i = 0; i < inactiveItems.length; i++) {
            inactiveModel.add(i, inactiveItems[i]);
        }
        list_inactive.setModel(inactiveModel);
        list_inactive.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        list_active = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_inactive = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        btnUp = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(KundenTabelleSorter.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_active.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_active.setName("list_active"); // NOI18N
        jScrollPane1.setViewportView(list_active);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        list_inactive.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_inactive.setName("list_inactive"); // NOI18N
        jScrollPane2.setViewportView(list_inactive);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        btnUp.setIcon(resourceMap.getIcon("btnUp.icon")); // NOI18N
        btnUp.setText(resourceMap.getString("btnUp.text")); // NOI18N
        btnUp.setName("btnUp"); // NOI18N
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        btnLeft.setIcon(resourceMap.getIcon("btnLeft.icon")); // NOI18N
        btnLeft.setName("btnLeft"); // NOI18N
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setIcon(resourceMap.getIcon("btnRight.icon")); // NOI18N
        btnRight.setName("btnRight"); // NOI18N
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnDown.setIcon(resourceMap.getIcon("btnDown.icon")); // NOI18N
        btnDown.setName("btnDown"); // NOI18N
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnUp)
                                    .addComponent(btnLeft)
                                    .addComponent(btnRight)
                                    .addComponent(btnDown))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDown)
                                .addGap(72, 72, 72))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        Object[] selected = list_inactive.getSelectedValues();

        if (selected == null) {
            return;
        }

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for (int i = 0; i < selected.length; i++) {
            active.addElement(selected[i]);
            inactive.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        loadInactive();
        loadActive();
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for (int i = 0; i < selected.length; i++) {
            inactive.addElement(selected[i]);
            active.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();

        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        if (selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if (index != 0) {
                Object temp = active.remove(index);
                active.add(index - 1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index - 1);
                list_active.revalidate();
            }
        }
    }//GEN-LAST:event_btnUpActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();
        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        if (selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if (index < active.size() - 1) {
                Object temp = active.remove(index);
                active.add(index + 1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index + 1);
                list_active.revalidate();
            }
        }

    }//GEN-LAST:event_btnDownActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();

        StringBuilder ids = new StringBuilder();

        for (int i = 0; i < active.getSize(); i++) {
            Object selected = active.get(i);

            if(panel != null) {
                de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelfk != null) {
                de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelvtr != null) {
                de.maklerpoint.office.Gui.Vertraege.PanelVertraege.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Vertraege.PanelVertraege.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelvers != null) {                
                de.maklerpoint.office.Gui.Versicherer.PanelVersichererUebersicht.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Versicherer.PanelVersichererUebersicht.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelprod != null) {
                de.maklerpoint.office.Gui.Versicherer.PanelProduktUebersicht.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Versicherer.PanelProduktUebersicht.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelstoer != null) {
                de.maklerpoint.office.Gui.Stoerfall.PanelStoerfaelle.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Stoerfall.PanelStoerfaelle.ColumnHead) selected;
                ids.append(column.getId());
            } else if (panelsch != null) {
                de.maklerpoint.office.Gui.Schaden.PanelSchaeden.ColumnHead column = 
                        (de.maklerpoint.office.Gui.Schaden.PanelSchaeden.ColumnHead) selected;
                ids.append(column.getId());
            }
            
            if (i != active.getSize() - 1) {
                ids.append(",");
            }
        }

        if (panel != null) {
            prefs.put("tableColumns", ids.toString());
            panel.createColumnNames(); // Refresh Columns
            panel.loadTable();
        } else if (panelfk != null) {
            prefsfk.put("tableColumns", ids.toString());
            panelfk.loadTable();
        } else if (panelvtr != null) {
            prefsvtr.put("tableColumns", ids.toString());
            panelvtr.loadTable();
        } else if (panelvers != null) {
            prefsvers.put("tableColumns", ids.toString());
            panelvers.loadTable();
        } else if(panelprod != null) {
            prefsprod.put("tableColumns", ids.toString());
            panelprod.loadTable();
        } else if (panelstoer != null) {
            prefsstoer.put("tableColumns", ids.toString());
            panelstoer.createColumnNames(); // Refresh Columns
            panelstoer.loadTable(); // tabelle neuladen
        } else if (panelsch != null) {
            prefssch.put("tableColumns", ids.toString());
            panelsch.createColumnNames(); // Refresh Columns
            panelsch.loadTable(); // tabelle neuladen
        }

        this.dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                KundenTabelleSorter dialog = new KundenTabelleSorter(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnDown;
    public javax.swing.JButton btnLeft;
    public javax.swing.JButton btnRight;
    public javax.swing.JButton btnSave;
    public javax.swing.JButton btnUp;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JList list_active;
    public javax.swing.JList list_inactive;
    // End of variables declaration//GEN-END:variables
}
