/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelRegulierung.java
 *
 * Created on 24.07.2011, 15:57:25
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Schaden.SchadenForderungDialog;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenForderungObj;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schaeden.Tools.SchadenForderungenSQLMethods;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelForderungen extends javax.swing.JPanel implements iTabs {

    private boolean enabled = false;
    private SchadenObj schaden = null;
    private static final String[] Columns = {"Hidden", "Beleg von", "Anspruchsteller",
        "Anspruchart", "Gesamtforderung", "Selbstbeteiligung",
        "Effektivforderung", "Zahltext", "Zahlung von", "Kommentare", "Erstellt am",
        "Zuletzt geändert", "Status"
    };
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /** Creates new form TabPanelRegulierung */
    public TabPanelForderungen() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Forderungen";
    }

    public void load(SchadenObj schaden) {
        this.schaden = schaden;
        loadTable();
    }

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

    private void loadTable() {
        try {
            Object[][] data = null;
            SchadenForderungObj[] sfs = SchadenForderungenSQLMethods.getSchadenForderungen(
                    DatabaseConnection.open(), schaden.getId(), getStatus());


            if (sfs != null) {
                data = new Object[sfs.length][13];

                for (int i = 0; i < sfs.length; i++) {
                    data[i][0] = sfs[i];
                    data[i][1] = df.format(sfs[i].getBelegVon());
                    data[i][2] = sfs[i].getAnspruchSteller();
                    data[i][3] = sfs[i].getAnspruchArt();

                    data[i][4] = WaehrungFormat.getFormatedWaehrung(sfs[i].getGesamtforderung(), -1);
                    data[i][5] = WaehrungFormat.getFormatedWaehrung(sfs[i].getSelbstbeteiligung(), -1);
                    double effektiv = sfs[i].getGesamtforderung() - sfs[i].getSelbstbeteiligung();
                    data[i][6] = WaehrungFormat.getFormatedWaehrung(effektiv, -1);

                    data[i][7] = sfs[i].getZahltext();
                    data[i][8] = sfs[i].getZahlungvon();
                    data[i][9] = sfs[i].getComments();
                    data[i][10] = df.format(sfs[i].getCreated());
                    data[i][11] = df.format(sfs[i].getModified());
                    data[i][12] = Status.getName(sfs[i].getStatus());
                }
            }

            setTable(data, Columns);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Schadensforderungen konnten nicht geladen werden.", e);
            ShowException.showException("Datenbankfehler: Die Schadensforderungen konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schadensforderungen nicht laden");
            setTable(null, Columns);
        }
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_forderungen.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_forderungen.getModel();
            atm.setData(data);
            table_forderungen.packAll();
            return;
        }

        table_forderungen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_forderungen.setColumnSelectionAllowed(false);
        table_forderungen.setCellSelectionEnabled(false);
        table_forderungen.setRowSelectionAllowed(true);
        table_forderungen.setAutoCreateRowSorter(true);

        table_forderungen.setFillsViewportHeight(true);
        table_forderungen.removeColumn(table_forderungen.getColumnModel().getColumn(0));

        table_forderungen.getColumnExt("Gesamtforderung").setVisible(false);
        table_forderungen.getColumnExt("Selbstbeteiligung").setVisible(false);

//        table_forderungen.getColumnExt("Zahltext").setVisible(false);
//        table_forderungen.getColumnExt("Zahlung von").setVisible(false);

        table_forderungen.getColumnExt("Kommentare").setVisible(false);

        table_forderungen.getColumnExt("Erstellt am").setVisible(false);
        table_forderungen.getColumnExt("Zuletzt geändert").setVisible(false);
        table_forderungen.getColumnExt("Status").setVisible(false);

        MouseListener popupListener = new TablePopupListener();
        table_forderungen.addMouseListener(popupListener);
        table_forderungen.setColumnControlVisible(true);

        JTableHeader header = table_forderungen.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_forderungen.packAll();
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
                int row = table_forderungen.rowAtPoint(point);
                table_forderungen.changeSelection(row, 0, false, false);
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
        this.table_forderungen.setEnabled(false);
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
        this.table_forderungen.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void newForderung() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        forderungDialog = new SchadenForderungDialog(mainFrame, true, schaden);
        forderungDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(forderungDialog);

        this.loadTable();
    }

    /**
     * Forderung bearbeiten
     */
    private void editForderung() {
        int row = table_forderungen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Bankkonto aus.");
            return;
        }

        SchadenForderungObj sf = (SchadenForderungObj) this.table_forderungen.getModel().getValueAt(row, 0);

        if (sf == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        forderungDialog = new SchadenForderungDialog(mainFrame, true, sf);
        forderungDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(forderungDialog);

        this.loadTable();
    }

    private void deleteForderung() {
        int row = table_forderungen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Forderung aus.");
            return;
        }

        SchadenForderungObj sf = (SchadenForderungObj) this.table_forderungen.getModel().getValueAt(row, 0);

        if (sf == null) {
            return;
        }
        
        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Forderung wirklich löschen?",
                    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            SchadenForderungenSQLMethods.deleteFromSchaeden_forderungen(DatabaseConnection.open(), sf.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Schadensforderung nicht aus der Datenbank löschen", e);
            ShowException.showException("Datenbankfehler: Die Forderung konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Forderung nicht löschen");
        }
    }

    private void searchTable() {
        int result = table_forderungen.getSearchable().search(field_search.getText());
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
        table_forderungen = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelForderungen.class);
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

        table_forderungen.setModel(new javax.swing.table.DefaultTableModel(
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
        table_forderungen.setColumnControlVisible(true);
        table_forderungen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_forderungen.setName("table_forderungen"); // NOI18N
        table_forderungen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_forderungenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_forderungen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newForderung();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editForderung();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteForderung();
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

    private void table_forderungenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_forderungenMouseClicked
        if (evt.getClickCount() > 1) {
            editForderung();
        }
}//GEN-LAST:event_table_forderungenMouseClicked

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
        newForderung();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editForderung();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void reguliertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reguliertMenuItemActionPerformed
        deleteForderung();
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
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JMenuItem reguliertMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_forderungen;
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
