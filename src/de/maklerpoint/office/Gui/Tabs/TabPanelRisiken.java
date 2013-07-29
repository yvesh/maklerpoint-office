/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelRisiken.java
 *
 * Created on 12.06.2011, 12:09:12
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Risiken.RisikoObj;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelRisiken extends javax.swing.JPanel implements iTabs {

    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private String kennung = null;
    private String[] Columns = new String[]{"Hidden", "Risikoart", "Eigentümer", "Beschreibung",
        "Aktiv", "Erstellt am"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private boolean enabled = true;

    /** Creates new form TabPanelRisiken */
    public TabPanelRisiken() {
        initComponents();
    }

    public String getTabName() {
        return "Risiken";
    }

    public void load(KundenObj kunde) {
        this.type = PRIVAT;
        this.kunde = kunde;
        this.kennung = kunde.getKundenNr();
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Risiken für Kunden " + kunde.getKundenNr());
        }
    }

    public void load(FirmenObj firma) {
        this.type = FIRMA;
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Risiken für Geschäftskunde " + firma.getKundenNr());
        }
        loadTable();
    }
    
    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void loadTable() {
        try {
            Object[][] data = null;
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Risiken für den Kunden \"" + kennung + "\" nicht aus der Datenbank laden", e);
            ShowException.showException("Konnte die Risiken für den Kunden \"" + kennung + "\" nicht aus der Datenbank laden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Risiken nicht laden");
        }
    }

    private void setTable(Object[][] data, String[] columns) {

        this.table_risiken.setModel(new AbstractStandardModel(data, columns));

        table_risiken.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_risiken.setColumnSelectionAllowed(false);
        table_risiken.setCellSelectionEnabled(false);
        table_risiken.setRowSelectionAllowed(true);
        table_risiken.setAutoCreateRowSorter(true);

        table_risiken.setFillsViewportHeight(true);
        table_risiken.removeColumn(table_risiken.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_risiken.addMouseListener(popupListener);
        table_risiken.setColumnControlVisible(true);

        JTableHeader header = table_risiken.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_risiken.packAll();

        table_risiken.tableChanged(new TableModelEvent(table_risiken.getModel()));
        table_risiken.revalidate();
    }

    /**
     * 
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
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private int getStatus() {
        int status = Status.NORMAL;

        if (this.alleDBMenuItem.isSelected()) {
            return -1;
        } else if (this.aktiveDBMenuItem.isSelected()) {
            return Status.NORMAL;
        } else if (this.archivedDBMenuItem.isSelected()) {
            return Status.ARCHIVED;
        } else if (this.deletedDBMenuItem.isSelected()) {
            return Status.DELETED;
        }

        return status;
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

    public void disableElements() {
        enabled = false;

        setTable(null, Columns);

        this.table_risiken.setEnabled(false);
        this.btnArchive.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.btnEdit.setEnabled(false);
    }

    public void enableElements() {
        enabled = true;

        this.table_risiken.setEnabled(true);
        this.btnArchive.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnRefresh.setEnabled(true);
    }

    private void searchTable() {
        int result = table_risiken.getSearchable().search(field_search.getText());
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
        archivedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_risiken = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelRisiken.class);
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
        aktiveDBMenuItem.setMnemonic('A');
        aktiveDBMenuItem.setSelected(true);
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setName("aktiveDBMenuItem"); // NOI18N
        aktiveDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktiveDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(aktiveDBMenuItem);

        grp_dbstatus.add(archivedDBMenuItem);
        archivedDBMenuItem.setMnemonic('A');
        archivedDBMenuItem.setText(resourceMap.getString("archivedDBMenuItem.text")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('A');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

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

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setText(resourceMap.getString("btnArchive.text")); // NOI18N
        btnArchive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnArchive.setFocusable(false);
        btnArchive.setName("btnArchive"); // NOI18N
        btnArchive.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        toolbar.add(btnArchive);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        toolbar.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbar.add(jSeparator2);

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

        table_risiken.setModel(new javax.swing.table.DefaultTableModel(
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
        table_risiken.setColumnControlVisible(true);
        table_risiken.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_risiken.setName("table_risiken"); // NOI18N
        scroll_protokolle.setViewportView(table_risiken);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
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
        JFrame mainFrame = CRM.getApplication().getMainFrame();

//        newKundeBox.setLocationRelativeTo(mainFrame);
//        CRM.getApplication().show(newKundeBox);

        this.loadTable();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int row = table_risiken.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Risiko ausgewählt.");
            return;
        }

        RisikoObj risk = (RisikoObj) this.table_risiken.getModel().getValueAt(row, 0);

        if (risk == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
//        newKundeBox = new NewKundenAdresse(mainFrame, true, za);
//        newKundeBox.setLocationRelativeTo(mainFrame);
//        CRM.getApplication().show(newKundeBox);

        this.loadTable();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        int row = table_risiken.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Adresse aus.");
            return;
        }

        RisikoObj risk = (RisikoObj) this.table_risiken.getModel().getValueAt(row, 0);

        if (risk == null) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Risiko wirklich archivieren?",
                "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

//        try {
//            ZusatzadressenSQLMethods.archiveFromkundenzusatz(DatabaseConnection.open(), za.getId());
//        } catch (Exception e) {
//            Log.databaselogger.fatal("Konnte die Zusatzadresse " + za.getId() + " für den Kunden nicht aus der Datenbank löschen", e);
//            ShowException.showException("Datenbankfehler: Die Zusatzadresse konnten nicht gelöscht werden werden. "
//                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
//                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadresse nicht löschen");
//        }

        this.loadTable();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int row = table_risiken.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Risiko ausgewählt.");
            return;
        }

        RisikoObj risk = (RisikoObj) this.table_risiken.getModel().getValueAt(row, 0);

        if (risk == null) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Risiko wirklich löschen?",
                "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

//        try {
//            ZusatzadressenSQLMethods.deleteFromkundenzusatz(DatabaseConnection.open(), za.getId());
//        } catch (Exception e) {
//            Log.databaselogger.fatal("Konnte die Zusatzadresse für den Kunden nicht aus der Datenbank löschen", e);
//            ShowException.showException("Datenbankfehler: Die Zusatzadresse konnten nicht gelöscht werden werden. "
//                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
//                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadresse nicht löschen");
//        }

        this.loadTable();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
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

    private void archivedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_archivedDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_deletedDBMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_risiken;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
}
