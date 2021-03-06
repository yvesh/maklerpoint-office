/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/08 13:10
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
 * PanelEinrichtungVersicherer.java
 *
 * Created on 10.09.2010, 17:08:48
 */

package de.maklerpoint.office.Gui.Firstrun;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemVersicherer;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Versicherer.Tools.VersichererSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class PanelEinrichtungVersicherer extends javax.swing.JPanel implements iAssistent {


    private DefaultListModel model;
    private EinrichtungsAssistentDialog as;
    /** Creates new form PanelEinrichtungVersicherer */
    public PanelEinrichtungVersicherer() {
        initComponents();
        loadList();
    }

    public int getId() {
        return 7;
    }

    public String getStepName() {
        return "Auswahl der Versicherungsunternehmen";
    }

    public void loadSettings(EinrichtungsAssistentDialog as) {
        this.as = as;
    }

    /**
     * 
     * @return
     */

    public boolean validateSettings() {
        if(this.list_versicherer.getSelectedIndices() == null) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich keine Versicherungsunternehmen übernehmen?",
                    "Keine Versicherer ausgewählt", JOptionPane.WARNING_MESSAGE);
            if(answer != JOptionPane.YES_OPTION)
                return false;
        }

        return true;
    }

    /**
     * 
     */

    public void saveSettings() {
        VersichererObj[] vs = (VersichererObj[]) this.list_versicherer.getSelectedValues();
        try {
            if(vs != null) {
                for(int i = 0; i < vs.length; i++) {
                    int id = VersichererSQLMethods.insertIntoversicherer(DatabaseConnection.open(), vs[i]);
                    vs[i].setId(id);
                    FilesystemVersicherer.createVersichererDirectory(vs[i]);
                }
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte Versicherer nicht speichern", e);
            ShowException.showException("Die Versicherer konnten nicht importiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Versicherer nicht importieren");
        } catch (IOException e) {
            Log.logger.fatal("Konnte Verzeichnis für den Versicherer nicht speichern", e);
            ShowException.showException("Die Verzeichnisstruktur für die Versicherer konnten nicht angelegt werden. "
                    + "Bitte überprüfen Sie die Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Versicherer Ordner nicht anlegen");
        }
    }

    public int next() {
        return -2;
    }

    /**
     * 
     */

    private void loadList() {
        model = (DefaultListModel) this.list_versicherer.getModel();
        model.removeAllElements();

        try {
            VersichererObj[] allvers = VersichererSQLMethods.loadAlleVersicherer(DatabaseConnection.open());

            for(int i = 0; i < allvers.length; i++) {
                model.addElement(allvers[i]);
            }

            this.list_versicherer.setModel(model);
            this.list_versicherer.revalidate();

        } catch (Exception e) {            
            Log.databaselogger.fatal("Konnte Versicherer zum Import nicht laden", e);
            ShowException.showException("Die Liste der Versicherer konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Versicherer nicht laden");
        }
    }

    private void search(String text) {
        DefaultListModel searchmod = (DefaultListModel) this.list_versicherer.getModel();

        if(searchmod.contains(text)) {
            int index = model.indexOf(text);
            list_versicherer.setSelectedIndex(index);
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

        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_versicherer = new javax.swing.JList();
        field_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnAll = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(478, 452));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelEinrichtungVersicherer.class);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_versicherer.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_versicherer.setName("list_versicherer"); // NOI18N
        jScrollPane1.setViewportView(list_versicherer);

        field_search.setText(resourceMap.getString("field_search.text")); // NOI18N
        field_search.setName("field_search"); // NOI18N
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                field_searchKeyReleased(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        btnAll.setMnemonic('A');
        btnAll.setText(resourceMap.getString("btnAll.text")); // NOI18N
        btnAll.setName("btnAll"); // NOI18N
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_search, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(field_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAll))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        int start = 0;
        int end = list_versicherer.getModel().getSize()-1;
        if (end >= 0) {
            list_versicherer.setSelectionInterval(start, end);   // A, B, C, D
        }
    }//GEN-LAST:event_btnAllActionPerformed

    private void field_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyReleased
         this.search(this.field_search.getText());
    }//GEN-LAST:event_field_searchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAll;
    public javax.swing.JTextField field_search;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JList list_versicherer;
    // End of variables declaration//GEN-END:variables



}
