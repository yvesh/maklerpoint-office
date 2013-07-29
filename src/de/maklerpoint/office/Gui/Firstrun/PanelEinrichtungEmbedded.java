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
 * PanelEinrichtungEmbedded.java
 *
 * Created on 09.09.2010, 12:39:30
 */

package de.maklerpoint.office.Gui.Firstrun;

import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Tools.DirectoryTools;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class PanelEinrichtungEmbedded extends javax.swing.JPanel implements iAssistent {

    private EinrichtungsAssistentDialog as;

    /** Creates new form PanelEinrichtungEmbedded */
    public PanelEinrichtungEmbedded() {
        initComponents();
    }

    public int getId() {
        return 3;
    }

    public String getStepName() {
        return "Einstellungen der Lokale Datenbank";
    }

    public void loadSettings(EinrichtungsAssistentDialog as) {
        this.as = as;

        this.field_filepath.setText(Config.get("filesystemPath", null));

        this.field_password.setText(Config.get("embeddedPassword", null));
        this.field_password2.setText(Config.get("embeddedPassword", null));

        this.check_encrypt.setSelected(Config.getConfigBoolean("embeddedEncrypted", false));
        this.check_rememberpassword.setSelected(Config.getConfigBoolean("embeddedRememberencrypted", true));;
                
    }

    public boolean validateSettings() {
        if(this.field_filepath.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Pfad für das Speichern der MaklerPoint Daten an.");
            return false;
        }

        if(this.check_encrypt.isSelected()) {
            if(this.field_password.getPassword() == null) {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Passwort an, wenn Sie die Verschlüsselung verwenden wollen.");
                return false;
            }

            if(!String.valueOf(this.field_password.getPassword()).equals(String.valueOf(this.field_password2.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Die Passwörter stimmen nicht überein.");
                return false;
            }
        }

        return true;
    }

    public void saveSettings() {
        Config.setInt("databaseType", DatabaseTypes.EMBEDDED_DERBY);

        Config.set("embeddedPassword", String.valueOf(this.field_password.getPassword()));
//        this.field_password2.getPassword();

        Config.setBoolean("embeddedEncrypted", this.check_encrypt.isSelected());
        Config.setBoolean("embeddedRememberencrypted", check_rememberpassword.isSelected());
        Config.set("filesystemPath", this.field_filepath.getText());

        try {
            Filesystem.initializeFilesystem();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Die Verzeichnisstruktur konnte im ausgewählten "
                    + "Wurzelverzeichnis nicht erstellt werden. Bitte überprüfen Sie Ihre Einstellungen.");
        }
    }

    public int next() {
        return 5;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        field_filepath = new javax.swing.JTextField();
        jToolTipLabel6 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jLabel7 = new javax.swing.JLabel();
        check_encrypt = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        field_password = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        field_password2 = new javax.swing.JPasswordField();
        jToolTipLabel7 = new de.maklerpoint.office.Tools.JToolTipLabel();
        check_rememberpassword = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(478, 452));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelEinrichtungEmbedded.class);
        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        field_filepath.setName("field_filepath"); // NOI18N
        field_filepath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_filepathMouseClicked(evt);
            }
        });

        jToolTipLabel6.setToolTipText(resourceMap.getString("jToolTipLabel6.toolTipText")); // NOI18N
        jToolTipLabel6.setName("jToolTipLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        check_encrypt.setText(resourceMap.getString("check_encrypt.text")); // NOI18N
        check_encrypt.setName("check_encrypt"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_password.setName("field_password"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_password2.setName("field_password2"); // NOI18N

        jToolTipLabel7.setToolTipText(resourceMap.getString("jToolTipLabel7.toolTipText")); // NOI18N
        jToolTipLabel7.setName("jToolTipLabel7"); // NOI18N

        check_rememberpassword.setSelected(true);
        check_rememberpassword.setText(resourceMap.getString("check_rememberpassword.text")); // NOI18N
        check_rememberpassword.setName("check_rememberpassword"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(check_encrypt)
                                .addGap(229, 229, 229)
                                .addComponent(jToolTipLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(field_password)
                                    .addComponent(field_password2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(check_rememberpassword))))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(field_filepath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolTipLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolTipLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(check_encrypt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(field_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_rememberpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolTipLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(field_filepath, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addGap(172, 172, 172))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void field_filepathMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_filepathMouseClicked
        String file = DirectoryTools.getDirectory();

         if(file == null)
            return;

        if(!new File(file).isDirectory()) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Ordner aus.");
        }

        if(file != null)
            this.field_filepath.setText(file);
}//GEN-LAST:event_field_filepathMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox check_encrypt;
    public javax.swing.JCheckBox check_rememberpassword;
    public javax.swing.JTextField field_filepath;
    public javax.swing.JPasswordField field_password;
    public javax.swing.JPasswordField field_password2;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel6;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel7;
    // End of variables declaration//GEN-END:variables



}