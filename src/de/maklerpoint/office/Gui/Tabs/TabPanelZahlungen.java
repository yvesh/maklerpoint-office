/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       24.07.2011 18:44:15
 *  File:       KontakteSQLMethods
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
 * TabPanelZahlungen.java
 *
 * Created on 26.07.2011, 13:11:51
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenForderungObj;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schaeden.SchadenZahlungObj;
import de.maklerpoint.office.Schaeden.Tools.SchadenForderungenSQLMethods;
import de.maklerpoint.office.Schaeden.Tools.SchadenZahlungenSQLMethods;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelZahlungen extends javax.swing.JPanel implements iTabs {

    private boolean enabled = false;
    private SchadenObj schaden = null;
    private static final String[] Columns = {"Hidden", "Beleg von", "Begünstigt",
        "Forderungsart", "Zahlung", "Zahltext", "Zahlung von", "Kommentare", "Erstellt am",
        "Zuletzt geändert", "Status"
    };
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /** Creates new form TabPanelZahlungen */
    public TabPanelZahlungen() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Zahlungen";
    }

    public void load(SchadenObj schaden) {
        this.schaden = schaden;
        loadTable();
        calculateLabel();
    }
    /**
     * 
     * @return Status
     */
    private int getStatus() {
        int status = Status.NORMAL;

        if (this.aktiveDBMenuItem.isSelected()) {
            status = Status.NORMAL;
        } else if (this.alleDBMenuItem.isSelected()) {
            status = -1;
        }
        if (this.deletedDBMenuItem.isSelected()) {
            status = Status.DELETED;
        }

        return status;
    }
    
    private void calculateLabel(){
        try {
            SchadenForderungObj[] sfs = SchadenForderungenSQLMethods.getSchadenForderungen(
                        DatabaseConnection.open(), schaden.getId(), Status.NORMAL);
            SchadenZahlungObj[] szs = SchadenZahlungenSQLMethods.getSchadenZahlungen(
                        DatabaseConnection.open(), schaden.getId(), Status.NORMAL);
            
            if(sfs == null) {
                this.label_summe.setText("Summe Forderung: 0,00        Summe Zahlung: 0,00        Offen sind: 0,00");
                return;
            }
            
            double forderungen = 0.00;
            
            for(int i = 0; i < sfs.length; i++){
                forderungen += sfs[i].getEffektiveforderung();
            }
            
            if(szs == null) {
                this.label_summe.setText("Summe Forderung: " + WaehrungFormat.getFormatedWaehrung(forderungen, -1) 
                        + "        Summe Zahlung: 0,00        Offen sind: " + WaehrungFormat.getFormatedWaehrung(forderungen, -1));
            }
                            
            double zahlungen = 0.00;
            
            for(int i = 0; i < szs.length; i++){
                zahlungen += szs[i].getZahlung();
            }
            
            this.label_summe.setText("Summe Forderung: " + WaehrungFormat.getFormatedWaehrung(forderungen, -1) 
                        + "        Summe Zahlung: " + WaehrungFormat.getFormatedWaehrung(zahlungen, -1) +"        Offen sind: " 
                    + WaehrungFormat.getFormatedWaehrung(forderungen - zahlungen, -1));
            
            
            
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Schadenssummen konnten nicht kalkuliert werden.", e);
            ShowException.showException("Datenbankfehler: Die Schadenssummen konnten nicht kalkuliert werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schadenssummen nicht kalkulieren");
        }
        
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            SchadenZahlungObj[] szs = SchadenZahlungenSQLMethods.getSchadenZahlungen(
                    DatabaseConnection.open(), schaden.getId(), getStatus());

            if (szs != null) {
                data = new Object[szs.length][11];
                
                for (int i = 0; i < szs.length; i++) {
                    data[i][0] = szs[i];
                    data[i][1] = df.format(szs[i].getBelegVon());
                    data[i][2] = szs[i].getBeguenstigt();
                    data[i][3] = szs[i].getForderungsArt();
                    data[i][4] = WaehrungFormat.getFormatedWaehrung(szs[i].getZahlung(), -1);
                    data[i][5] = szs[i].getZahltext();
                    data[i][6] = szs[i].getZahlungvon();
                    data[i][7] = szs[i].getComments();   
                    data[i][8] = df.format(szs[i].getCreated());
                    data[i][9] = df.format(szs[i].getModified());
                    data[i][10] = Status.getName(szs[i].getStatus());                    
                }
            }

            setTable(data, Columns);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Schadenszahlungen konnten nicht geladen werden.", e);
            ShowException.showException("Datenbankfehler: Die Schadenszahlungen konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schadenszahlungen nicht laden");
            setTable(null, Columns);
        }
    }
    private AbstractStandardModel atm = null;

    /**
     * 
     * @param data
     * @param columns 
     */
    private void setTable(Object[][] data, String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_zahlungen.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_zahlungen.getModel();
            atm.setData(data);
            table_zahlungen.packAll();
            return;
        }

        table_zahlungen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_zahlungen.setColumnSelectionAllowed(false);
        table_zahlungen.setCellSelectionEnabled(false);
        table_zahlungen.setRowSelectionAllowed(true);
        table_zahlungen.setAutoCreateRowSorter(true);

        table_zahlungen.setFillsViewportHeight(true);
        table_zahlungen.removeColumn(table_zahlungen.getColumnModel().getColumn(0));
        
//        table_zahlungen.getColumnExt("Zahltext").setVisible(false);
//        table_zahlungen.getColumnExt("Zahlung von").setVisible(false);

        table_zahlungen.getColumnExt("Kommentare").setVisible(false);

        table_zahlungen.getColumnExt("Erstellt am").setVisible(false);
        table_zahlungen.getColumnExt("Zuletzt geändert").setVisible(false);
        table_zahlungen.getColumnExt("Status").setVisible(false);

        MouseListener popupListener = new TablePopupListener();
        table_zahlungen.addMouseListener(popupListener);
        table_zahlungen.setColumnControlVisible(true);

        JTableHeader header = table_zahlungen.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_zahlungen.packAll();
    }

    /**
     * Mousepopup
     */
    class TablePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point point = e.getPoint();
                int row = table_zahlungen.rowAtPoint(point);
                table_zahlungen.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void disableElements() {
        enabled = false;

        this.btnDelete.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.table_zahlungen.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);
        setTable(null, Columns);
    }

    public void enableElements() {
        if (enabled == true) {
            return;
        }

        enabled = true;
        this.btnDelete.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.table_zahlungen.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(
                ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"),
                popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Tabellenansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private void searchTable() {
        int result = table_zahlungen.getSearchable().search(field_search.getText());
    }

    // Edit, New, Delete, Archive
    private void newZahlung() {
    }

    private void editZahlung() {
    }

    private void deleteZahlung() {
        int row = table_zahlungen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Zahlung aus.");
            return;
        }

        SchadenZahlungObj sz = (SchadenZahlungObj) this.table_zahlungen.getModel().getValueAt(row, 0);

        if (sz == null) {
            return;
        }
        
        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Zahlungen wirklich löschen?",
                    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            SchadenZahlungenSQLMethods.deleteFromSchaeden_zahlungen(DatabaseConnection.open(), sz.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Schadenszahlung nicht aus der Datenbank löschen", e);
            ShowException.showException("Datenbankfehler: Die Zahlung konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zahlung nicht löschen");
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

        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        reguliertMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_zahlungen = new org.jdesktop.swingx.JXTable();
        statusbar = new javax.swing.JToolBar();
        label_summe = new javax.swing.JLabel();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelZahlungen.class);
        alleDBMenuItem.setText(resourceMap.getString("alleDBMenuItem.text")); // NOI18N
        alleDBMenuItem.setToolTipText(resourceMap.getString("alleDBMenuItem.toolTipText")); // NOI18N
        alleDBMenuItem.setName("alleDBMenuItem"); // NOI18N
        alleDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alleDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(alleDBMenuItem);

        grp_dbstatus.add(aktiveDBMenuItem);
        aktiveDBMenuItem.setMnemonic('O');
        aktiveDBMenuItem.setSelected(true);
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setName("aktiveDBMenuItem"); // NOI18N
        aktiveDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktiveDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(aktiveDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('R');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        newMenuItem.setMnemonic('N');
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

        editMenuItem.setMnemonic('b');
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(editMenuItem);

        reguliertMenuItem.setText(resourceMap.getString("reguliertMenuItem.text")); // NOI18N
        reguliertMenuItem.setName("reguliertMenuItem"); // NOI18N
        reguliertMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reguliertMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(reguliertMenuItem);

        refreshMenuItem.setText(resourceMap.getString("refreshMenuItem.text")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(refreshMenuItem);

        setName("Form"); // NOI18N

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

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
        toolbar.add(btnNeu);

        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        toolbar.add(btnEdit);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setFocusable(false);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        toolbar.add(btnDelete);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbar.add(jSeparator3);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        toolbar.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        toolbar.add(field_search);

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
        toolbar.add(btnSearch);

        jSeparator5.setName("jSeparator5"); // NOI18N
        toolbar.add(jSeparator5);

        btnRefresh.setIcon(resourceMap.getIcon("btnRefresh.icon")); // NOI18N
        btnRefresh.setToolTipText(resourceMap.getString("btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        toolbar.add(btnRefresh);

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_zahlungen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_zahlungen.setColumnControlVisible(true);
        table_zahlungen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_zahlungen.setName("table_zahlungen"); // NOI18N
        table_zahlungen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_zahlungenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_zahlungen);

        statusbar.setFloatable(false);
        statusbar.setRollover(true);
        statusbar.setName("statusbar"); // NOI18N

        label_summe.setText(resourceMap.getString("label_summe.text")); // NOI18N
        label_summe.setName("label_summe"); // NOI18N
        statusbar.add(label_summe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
            .addComponent(statusbar, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void table_zahlungenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_zahlungenMouseClicked
        if (evt.getClickCount() > 1) {
            editZahlung();
        }
}//GEN-LAST:event_table_zahlungenMouseClicked

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newZahlung();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editZahlung();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteZahlung();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_deletedDBMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newZahlung();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editZahlung();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void reguliertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reguliertMenuItemActionPerformed
        deleteZahlung();
}//GEN-LAST:event_reguliertMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JLabel label_summe;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JMenuItem reguliertMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JToolBar statusbar;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_zahlungen;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
    private JDialog forderungDialog;

    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(FirmenObj firma) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VersichererObj versicherer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(BenutzerObj benutzer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(ProduktObj produkt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}