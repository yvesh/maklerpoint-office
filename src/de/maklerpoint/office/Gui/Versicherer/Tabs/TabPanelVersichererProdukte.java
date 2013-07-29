/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelVersichererProdukte.java
 *
 * Created on 18.09.2010, 12:30:44
 */

package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tabs.iTabs;
import de.maklerpoint.office.Gui.Versicherer.ProduktDialog;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.TermineModel;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.Tools.ProdukteSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author yves
 */
public class TabPanelVersichererProdukte extends javax.swing.JPanel implements iTabs {

    private VersichererObj vs = null;
    private boolean enabled = true;

    private String[] Columns = {"Hidden", "Bezeichnung", "Sparte", "Tarif",
                            "Tarif Basis", "Nettoprämie (gesamt)", "Versicherungs Summe"};

    /** Creates new form TabPanelVersichererProdukte */
    public TabPanelVersichererProdukte() {
        initComponents();
    }

    public String getTabName() {
        return "Produkte";
    }

    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(FirmenObj firma) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VersichererObj versicherer) {
        this.vs = versicherer;
        loadTable();
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

        this.table_produkte.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.table_produkte.setEnabled(false);
    }

    public void enableElements() {
         if(enabled == true)
            return;

        enabled = true;

        this.table_produkte.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.table_produkte.setEnabled(true);
    }

    private int getStatus(){
        return Status.NORMAL;
    }
    
    private void loadTable() {
        try {
            Object[][] data = null;
            ProduktObj[] pa = ProdukteSQLMethods.loadProdukte(DatabaseConnection.open(), 
                    vs.getId(), this.check_vermittelbar.isSelected(), getStatus());

            if(pa != null) {
                data = new Object[pa.length][7];

                for(int i = 0; i < pa.length; i++)
                {
//                    {""Hidden", "Bezeichnung", "Sparte", "Tarif",
//                            "Tarif Basis", "Nettoprämie (gesamt)", "Versicherungs Summe"

                    data[i][0] = pa[i];
                    data[i][1] = pa[i].getBezeichnung();
                    data[i][2] = VersicherungsRegistry.getSparte(pa[i].getSparteId());
                    data[i][3] = pa[i].getTarif();
                    data[i][4] = pa[i].getTarifBasis();
                    data[i][5] = pa[i].getNettopraemieGesamt();
                    data[i][6] = pa[i].getVersicherungsSumme();
                }
            }

            setTable(data, Columns);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Produkte nicht aus der Datenbank laden", e);
            ShowException.showException("Konnte die Produkte nicht aus der Datenbank laden",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadressen nicht laden");
        }
    }

    private void setTable(Object[][] data, String[] columns) {

        this.table_produkte.setModel(new TermineModel(data, columns));

        table_produkte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_produkte.setColumnSelectionAllowed(false);
        table_produkte.setCellSelectionEnabled(false);
        table_produkte.setRowSelectionAllowed(true);
        table_produkte.setAutoCreateRowSorter(true);

        table_produkte.setFillsViewportHeight(true);
        table_produkte.removeColumn(table_produkte.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_produkte.addMouseListener(popupListener);
        table_produkte.setColumnControlVisible(true);

        JTableHeader header = table_produkte.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_produkte.tableChanged(new TableModelEvent(table_produkte.getModel()));
        table_produkte.revalidate();
    }


    private void searchTable() {
        int result = this.table_produkte.getSearchable().search(field_search.getText());
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class TablePopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
          showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
          showPopup(e);
        }

        private void showPopup(MouseEvent e) {
          if (e.isPopupTrigger()) {
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
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

        scroll_protokolle = new javax.swing.JScrollPane();
        table_produkte = new org.jdesktop.swingx.JXTable();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        check_vermittelbar = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_produkte.setModel(new javax.swing.table.DefaultTableModel(
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
        table_produkte.setColumnControlVisible(true);
        table_produkte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_produkte.setName("table_produkte"); // NOI18N
        table_produkte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_produkteMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_produkte);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelVersichererProdukte.class);
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
        jToolBar1.add(btnEdit);

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

        check_vermittelbar.setSelected(true);
        check_vermittelbar.setText(resourceMap.getString("check_vermittelbar.text")); // NOI18N
        check_vermittelbar.setName("check_vermittelbar"); // NOI18N
        check_vermittelbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_vermittelbarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check_vermittelbar)
                .addContainerGap(461, Short.MAX_VALUE))
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_vermittelbar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
//        System.out.println("VS: " + vs.getName());

        produktBox = new ProduktDialog(mainFrame, false, vs);
        produktBox.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        return null;
                    }
                });
            }
        });
        produktBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(produktBox);
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int row = this.table_produkte.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Produkt ausgewählt.");
            return;
        }

        ProduktObj pa = (ProduktObj) this.table_produkte.getModel().getValueAt(row, 0);

        if(pa == null)
            return;

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        //TODo

        this.loadTable();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int row = table_produkte.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Produkt ausgewählt.");
            return;
        }

        ProduktObj za = (ProduktObj) this.table_produkte.getModel().getValueAt(row, 0);

        if(za == null)
            return;

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Zusatzadresse wirklich löschen?", "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

        if(answer != JOptionPane.YES_OPTION)
            return;

        try {
            ProdukteSQLMethods.deleteFromProdukte(DatabaseConnection.open(), za.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das Produkt nicht aus der Datenbank löschen", e);
            ShowException.showException("Datenbankfehler: Das Produkt konnten nicht gelöscht werden werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkt nicht löschen");
        }
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if(field_search.getText().length() > 3)
            searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void check_vermittelbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_vermittelbarActionPerformed
        loadTable();
    }//GEN-LAST:event_check_vermittelbarActionPerformed

    private void table_produkteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_produkteMouseClicked
        if(evt.getClickCount() >= 2) {
            int row = this.table_produkte.getSelectedRow();

            if(row == -1) {
                JOptionPane.showMessageDialog(null, "Keine Produkt ausgewählt.");
                return;
            }

            ProduktObj pa = (ProduktObj) this.table_produkte.getModel().getValueAt(row, 0);

            if(pa == null)
                return;

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            produktBox = new ProduktDialog(mainFrame, false, pa);
            produktBox.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            loadTable();
                            return null;
                        }
                    });
                }
            });
            produktBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(produktBox);
        }
    }//GEN-LAST:event_table_produkteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnNeu;
    public javax.swing.JButton btnSearch;
    public javax.swing.JCheckBox check_vermittelbar;
    public javax.swing.JTextField field_search;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator4;
    public javax.swing.JToolBar.Separator jSeparator5;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JScrollPane scroll_protokolle;
    public org.jdesktop.swingx.JXTable table_produkte;
    // End of variables declaration//GEN-END:variables

    private JDialog produktBox;

}
