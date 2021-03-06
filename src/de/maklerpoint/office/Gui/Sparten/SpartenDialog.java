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
 * SpartenDialog.java
 *
 * Created on Aug 6, 2010, 2:36:14 PM
 */

package de.maklerpoint.office.Gui.Sparten;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Sparten.SpartenObj;
import de.maklerpoint.office.Sparten.Tools.SpartenSQLMethods;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SpartenDialog extends javax.swing.JDialog {

    private SpartenObj[] sparten = null;
    private SpartenObj currentSparte = null;


    /** Creates new form SpartenDialog */
    public SpartenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.sparten = VersicherungsRegistry.getSparten(true);
        initComponents();
        
    }

    private void loadSparten() {
        if(sparten == null) {

            System.out.println("Keine Sparten vorhanden");
            return;
        }

        Object[] columnnames = new Object[]{"Hidden", "Spartennummer", "Bezeichnung", "Gruppe", "Steuersatz"};

        Object data[][] = new Object[sparten.length][5];
        
        for(int i = 0; i < sparten.length; i++) {
            data[i][0] = sparten[i];
            data[i][1] = sparten[i].getSpartenNummer();
            data[i][2] = sparten[i].getBezeichnung();
            if(sparten[i].getGruppe() != null)
                data[i][3] = sparten[i].getGruppe();
            else {
                data[i][3] = "";
                sparten[i].setGruppe("");
            }
            data[i][4] = sparten[i].getSteuersatz();
        }

        this.table_sparten.setModel(new DefaultTableModel(data, columnnames));
        this.table_sparten.removeColumn(this.table_sparten.getColumn(0));
        this.table_sparten.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table_sparten.revalidate();
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
        btnNeu = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_sparten = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        field_bezeichnung = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_gruppe = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        field_steuersatz = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        field_spartennummer = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(SpartenDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setMnemonic('N');
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNeu);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setMnemonic('L');
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDelete);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel6);

        field_search.setText(resourceMap.getString("field_search.text")); // NOI18N
        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(120, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        jToolBar1.add(field_search);

        btnSearch.setIcon(resourceMap.getIcon("btnSearch.icon")); // NOI18N
        btnSearch.setText(resourceMap.getString("btnSearch.text")); // NOI18N
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table_sparten.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Spartennummer", "Beschreibung", "Gruppe", "Steuersatz"
            }
        ));
        table_sparten.setName("table_sparten"); // NOI18N
        loadSparten();
        table_sparten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_spartenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_sparten);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        field_bezeichnung.setText(resourceMap.getString("field_bezeichnung.text")); // NOI18N
        field_bezeichnung.setName("field_bezeichnung"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_gruppe.setEditable(true);
        combo_gruppe.setName("combo_gruppe"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_steuersatz.setText(resourceMap.getString("field_steuersatz.text")); // NOI18N
        field_steuersatz.setName("field_steuersatz"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_spartennummer.setText(resourceMap.getString("field_spartennummer.text")); // NOI18N
        field_spartennummer.setName("field_spartennummer"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(field_steuersatz, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(field_spartennummer, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addComponent(combo_gruppe, javax.swing.GroupLayout.Alignment.TRAILING, 0, 331, Short.MAX_VALUE)
                    .addComponent(field_bezeichnung, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(field_bezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_gruppe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(field_steuersatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(field_spartennummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_spartenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_spartenMouseClicked
        int row = this.table_sparten.getSelectedRow();

        if(row == -1)
            return;

        if(currentSparte != null) {

            String gruppe = null;

            if(this.combo_gruppe.getSelectedItem() != null)
                gruppe = this.combo_gruppe.getSelectedItem().toString();
            else
                gruppe = "";

            if(!this.field_bezeichnung.getText().equalsIgnoreCase(currentSparte.getBezeichnung())
                    || !this.field_spartennummer.getText().equalsIgnoreCase(currentSparte.getSpartenNummer())
                    || !gruppe.equalsIgnoreCase(currentSparte.getGruppe())
                    || !this.field_steuersatz.getText().equalsIgnoreCase(String.valueOf(currentSparte.getSteuersatz())))
            {
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die veränderte Sparte speichern?",
                        "Datensatz speichern", JOptionPane.YES_NO_OPTION);
                
                if(answer == JOptionPane.YES_OPTION) {
                    currentSparte.setBezeichnung(this.field_bezeichnung.getText());
                    currentSparte.setSpartenNummer(this.field_spartennummer.getText());
                    currentSparte.setGruppe((String) this.combo_gruppe.getSelectedItem());
                    currentSparte.setSteuersatz(Integer.valueOf(this.field_steuersatz.getText()));
                    try {
                        boolean success = SpartenSQLMethods.updatesparten(DatabaseConnection.open(), currentSparte);

                        if(success == false) {
                            System.out.println("ADD Exception: Das geupdate Sparten Objekt wurde nicht gefunden.");
                        }

                    } catch(SQLException e) {
                        ShowException.showException("Die Sparte konnte nicht geupdated werden.  ",
                                ExceptionDialogGui.LEVEL_WARNING, e,
                                "Datenbankfehler: Konnte sparte nicht speichern");
                        Log.databaselogger.fatal("Fehler: Konnte Sparte nicht speichern.", e);
                    }
                }
            }
        }

        SpartenObj sparte = (SpartenObj) table_sparten.getModel().getValueAt(row, 0);

        if(sparte == null)
            return;

        currentSparte = sparte;

        this.field_bezeichnung.setText(sparte.getBezeichnung());
        this.field_spartennummer.setText(sparte.getSpartenNummer());
        this.field_steuersatz.setText("" + sparte.getSteuersatz());
        this.combo_gruppe.setSelectedItem(sparte.getGruppe());        
    }//GEN-LAST:event_table_spartenMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if(field_search.getText().length() > 3)
            searchTable();
    }//GEN-LAST:event_field_searchKeyTyped

    public void searchTable() {
        int result = table_sparten.getSearchable().search(field_search.getText());

//        System.out.println("Res: " + result);
        if(result == -1)
            return;

        if(currentSparte != null) {

            String gruppe = null;

            if(this.combo_gruppe.getSelectedItem() != null)
                gruppe = this.combo_gruppe.getSelectedItem().toString();
            else
                gruppe = "";

            if(!this.field_bezeichnung.getText().equalsIgnoreCase(currentSparte.getBezeichnung())
                    || !this.field_spartennummer.getText().equalsIgnoreCase(currentSparte.getSpartenNummer())
                    || !gruppe.equalsIgnoreCase(currentSparte.getGruppe())
                    || !this.field_steuersatz.getText().equalsIgnoreCase(String.valueOf(currentSparte.getSteuersatz())))
            {
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die geänderte Sparte speichern?",
                        "Datensatz speichern", JOptionPane.YES_NO_OPTION);

                if(answer == JOptionPane.YES_OPTION) {
                    currentSparte.setBezeichnung(this.field_bezeichnung.getText());
                    currentSparte.setSpartenNummer(this.field_spartennummer.getText());
                    currentSparte.setGruppe((String) this.combo_gruppe.getSelectedItem());
                    currentSparte.setSteuersatz(Integer.valueOf(this.field_steuersatz.getText()));
                    try {
                        boolean success = SpartenSQLMethods.updatesparten(DatabaseConnection.open(), currentSparte);

                        if(success == false) {
                            System.out.println("ADD Exception: Das geupdate Sparten Objekt wurde nicht gefunden.");
                        }

                    } catch(SQLException e) {
                        ShowException.showException("Die Sparte konnte nicht geupdated werden.  ",
                                ExceptionDialogGui.LEVEL_WARNING, e,
                                "Datenbankfehler: Konnte sparte nicht speichern");
                        Log.databaselogger.fatal("Fehler: Konnte Sparte nicht speichern.", e);
                    }
                }
            }
        }

        SpartenObj sparte = (SpartenObj) table_sparten.getModel().getValueAt(result, 0);

        currentSparte = sparte;

        this.field_bezeichnung.setText(sparte.getBezeichnung());
        this.field_spartennummer.setText(sparte.getSpartenNummer());
        this.field_steuersatz.setText("" + sparte.getSteuersatz());
        this.combo_gruppe.setSelectedItem(sparte.getGruppe());
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SpartenDialog dialog = new SpartenDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox combo_gruppe;
    private javax.swing.JTextField field_bezeichnung;
    private javax.swing.JTextField field_search;
    private javax.swing.JTextField field_spartennummer;
    private javax.swing.JTextField field_steuersatz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private org.jdesktop.swingx.JXTable table_sparten;
    // End of variables declaration//GEN-END:variables

}
