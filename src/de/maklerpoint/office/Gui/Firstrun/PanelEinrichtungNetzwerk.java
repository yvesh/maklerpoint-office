/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/09 13:10
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
 * PanelEinrichtungNetzwerk.java
 *
 * Created on 09.09.2010, 10:46:56
 */

package de.maklerpoint.office.Gui.Firstrun;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Database.DatabaseHelper;
import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.Database.MySQL.MySQL;
import de.maklerpoint.office.Database.PostgreSQL.PostgreSQL;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Tools.DirectoryTools;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class PanelEinrichtungNetzwerk extends javax.swing.JPanel implements iAssistent {

    private EinrichtungsAssistentDialog as;

    /** Creates new form PanelEinrichtungNetzwerk */
    public PanelEinrichtungNetzwerk() {
        initComponents();
    }

    public int getId() {
        return 4;
    }

    public String getStepName() {
        return "MaklerPoint Netzwerk Neuinstallation";
    }

    public int next() {
        return 5;
    }

    public void loadSettings(EinrichtungsAssistentDialog as) {
        this.as = as;
        this.field_databasename.setText(Config.get("databaseName", "maklerpointdb"));
        this.field_password.setText(Config.get("databasePassword", null));
        this.field_port.setText(Config.get("databasePort", null));
        this.field_serveraddress.setText(Config.get("databaseServer", "localhost"));
        this.field_user.setText(Config.get("databaseUsername", null));
        this.combo_servertyp.setSelectedIndex(Config.getConfigInt("databaseType", DatabaseTypes.MYSQL));

        this.field_filepath.setText(Config.get("filesystemPath", null));
    }

    private boolean testDb() {
        int type = combo_servertyp.getSelectedIndex();

        boolean work = false;

        if(type == DatabaseTypes.MYSQL) {
            String url = "jdbc:mysql://" + this.field_serveraddress.getText() + "/" + this.field_databasename.getText();

            if(this.field_port.getText() != null && this.field_port.getText().length() > 0)
                url = "jdbc:mysql://" + this.field_serveraddress.getText() + ":" + this.field_port.getText() + "/" + this.field_databasename.getText();
            work = MySQL.testMySQLConnection(new DatabaseHelper(url,this.field_user.getText(),String.valueOf(this.field_password.getPassword())));

            System.out.println("URL: " + url);
            System.out.println("User: " + this.field_user.getText());
            System.out.println("PW: " + this.field_password.getPassword());
        } else if (type == DatabaseTypes.POSTGRESQL) {
            String url = "jdbc:posgresql://" + this.field_serveraddress.getText() + "/" + this.field_databasename;

            if(this.field_port.getText() != null)
                url = "jdbc:posgresql://" + this.field_serveraddress.getText() + ":" + this.field_port.getText() + "/" + this.field_databasename.getText();

            work = PostgreSQL.testPostgreSQLConnection(new DatabaseHelper(url,this.field_user.getText(),String.valueOf(this.field_password.getPassword())));
        } else if (type == DatabaseTypes.MSSQL) {
            work = false;
        } else if (type == DatabaseTypes.ORACLE) {
            work = false;
        } else if (type == DatabaseTypes.DERBY) {
            work = false;
        } else if (type == DatabaseTypes.EMBEDDED_DERBY) {
            work = DatabaseConnection.testLocalConnection();
        }

        if(work)
            JOptionPane.showMessageDialog(null, "Die Verbindung wurde erfolgreich hergestellt. "
                    + "Klicken Sie auf Beenden um diese zu übernehmen und die Einrichtung abzuschließen.", "Datenbankverbindung war erfolgreich.", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung zur Datenbank hergestellt werden. "
                    + "Bitte überprüfen Sie Ihre Einstellungen.", "Keine Verbindung zur Datenbank", JOptionPane.ERROR_MESSAGE);

        return work;
    }

    public boolean validateSettings() {
        if(this.field_filepath.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Pfad für das Speichern der MaklerPoint Daten an.");
            return false;
        }

        if(!testDb()) {
            return false;
        }

        return true;
    }

    public void saveSettings() {
        Config.setInt("databaseType", this.combo_servertyp.getSelectedIndex());
        Config.set("databaseServer", this.field_serveraddress.getText());
        Config.set("databasePort", this.field_port.getText());
        Config.set("databaseName", this.field_databasename.getText());
        Config.set("databaseUsername", this.field_user.getText());
        Config.set("databasePassword", String.valueOf(field_password.getPassword()));
        Config.set("filesystemPath", this.field_filepath.getText());

        try {
            Filesystem.initializeFilesystem();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Die Verzeichnisstruktur konnte im ausgewählten "
                    + "Wurzelverzeichnis nicht erstellt werden. Bitte überprüfen Sie Ihre Einstellungen.");
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

        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        combo_servertyp = new javax.swing.JComboBox();
        jToolTipLabel1 = new de.maklerpoint.office.Tools.JToolTipLabel();
        field_serveraddress = new javax.swing.JTextField();
        field_port = new javax.swing.JTextField();
        field_databasename = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        field_user = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        field_password = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btnTest = new javax.swing.JButton();
        jToolTipLabel2 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jToolTipLabel5 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jToolTipLabel4 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jToolTipLabel3 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        field_filepath = new javax.swing.JTextField();
        jToolTipLabel6 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnFileopen = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(478, 452));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelEinrichtungNetzwerk.class);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        combo_servertyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "PostgreSQL", "MSSQL", "Oracle", "Derby" }));
        combo_servertyp.setName("combo_servertyp"); // NOI18N
        combo_servertyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_servertypActionPerformed(evt);
            }
        });

        jToolTipLabel1.setIcon(resourceMap.getIcon("jToolTipLabel1.icon")); // NOI18N
        jToolTipLabel1.setToolTipText(resourceMap.getString("jToolTipLabel1.toolTipText")); // NOI18N
        jToolTipLabel1.setName("jToolTipLabel1"); // NOI18N

        field_serveraddress.setText(resourceMap.getString("field_serveraddress.text")); // NOI18N
        field_serveraddress.setName("field_serveraddress"); // NOI18N

        field_port.setName("field_port"); // NOI18N

        field_databasename.setText(resourceMap.getString("field_databasename.text")); // NOI18N
        field_databasename.setName("field_databasename"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_user.setName("field_user"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        field_password.setName("field_password"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        btnTest.setIcon(resourceMap.getIcon("btnTest.icon")); // NOI18N
        btnTest.setText(resourceMap.getString("btnTest.text")); // NOI18N
        btnTest.setName("btnTest"); // NOI18N
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        jToolTipLabel2.setIcon(resourceMap.getIcon("jToolTipLabel2.icon")); // NOI18N
        jToolTipLabel2.setToolTipText(resourceMap.getString("jToolTipLabel2.toolTipText")); // NOI18N
        jToolTipLabel2.setName("jToolTipLabel2"); // NOI18N

        jToolTipLabel5.setIcon(resourceMap.getIcon("jToolTipLabel5.icon")); // NOI18N
        jToolTipLabel5.setToolTipText(resourceMap.getString("jToolTipLabel5.toolTipText")); // NOI18N
        jToolTipLabel5.setName("jToolTipLabel5"); // NOI18N

        jToolTipLabel4.setIcon(resourceMap.getIcon("jToolTipLabel4.icon")); // NOI18N
        jToolTipLabel4.setToolTipText(resourceMap.getString("jToolTipLabel4.toolTipText")); // NOI18N
        jToolTipLabel4.setName("jToolTipLabel4"); // NOI18N

        jToolTipLabel3.setIcon(resourceMap.getIcon("jToolTipLabel3.icon")); // NOI18N
        jToolTipLabel3.setToolTipText(resourceMap.getString("jToolTipLabel3.toolTipText")); // NOI18N
        jToolTipLabel3.setName("jToolTipLabel3"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        field_filepath.setName("field_filepath"); // NOI18N

        jToolTipLabel6.setIcon(resourceMap.getIcon("jToolTipLabel6.icon")); // NOI18N
        jToolTipLabel6.setToolTipText(resourceMap.getString("jToolTipLabel6.toolTipText")); // NOI18N
        jToolTipLabel6.setName("jToolTipLabel6"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        btnFileopen.setIcon(null);
        btnFileopen.setToolTipText(resourceMap.getString("btnFileopen.toolTipText")); // NOI18N
        btnFileopen.setName("btnFileopen"); // NOI18N
        btnFileopen.setPreferredSize(new java.awt.Dimension(25, 25));
        btnFileopen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileopenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(52, 52, 52)
                                .addComponent(combo_servertyp, 0, 334, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolTipLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(field_databasename, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                    .addComponent(field_port, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(field_serveraddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jToolTipLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToolTipLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToolTipLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_password, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(field_user, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(btnTest, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolTipLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(field_filepath, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFileopen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolTipLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jToolTipLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_servertyp, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_serveraddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_databasename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolTipLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolTipLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolTipLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolTipLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_user)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFileopen, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jToolTipLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(field_filepath, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addGap(52, 52, 52))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void combo_servertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_servertypActionPerformed
        if(combo_servertyp.getSelectedIndex() == -1)
            return;

        if(combo_servertyp.getSelectedIndex() == 5) {
            this.field_user.setEnabled(false);
            this.field_password.setEnabled(false);
            this.field_databasename.setEnabled(false);
            this.field_port.setEnabled(false);
            this.field_serveraddress.setEnabled(false);
        } else {
            this.field_user.setEnabled(true);
            this.field_password.setEnabled(true);
            this.field_databasename.setEnabled(true);
            this.field_port.setEnabled(true);
            this.field_serveraddress.setEnabled(true);
        }
}//GEN-LAST:event_combo_servertypActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        testDb();
}//GEN-LAST:event_btnTestActionPerformed

    private void btnFileopenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileopenActionPerformed
        String file = DirectoryTools.getDirectory("Verzeichnis auswählen");
        
        if(file == null)
            return;
        
        this.field_filepath.setText(file);
}//GEN-LAST:event_btnFileopenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFileopen;
    public javax.swing.JButton btnTest;
    public javax.swing.JComboBox combo_servertyp;
    public javax.swing.JTextField field_databasename;
    public javax.swing.JTextField field_filepath;
    public javax.swing.JPasswordField field_password;
    public javax.swing.JTextField field_port;
    public javax.swing.JTextField field_serveraddress;
    public javax.swing.JTextField field_user;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel1;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel2;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel3;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel4;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel5;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel6;
    // End of variables declaration//GEN-END:variables



}
