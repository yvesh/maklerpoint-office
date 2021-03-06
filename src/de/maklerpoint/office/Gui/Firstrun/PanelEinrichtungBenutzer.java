/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelEinrichtungBenutzer.java
 *
 * Created on 10.09.2010, 16:21:27
 */
package de.maklerpoint.office.Gui.Firstrun;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Benutzer.Tools.BenutzerSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Status;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author yves
 */
public class PanelEinrichtungBenutzer extends javax.swing.JPanel implements iAssistent {

    private EinrichtungsAssistentDialog as;

    /** Creates new form PanelEinrichtungBenutzer */
    public PanelEinrichtungBenutzer() {
        initComponents();
    }

    public int getId() {
        return 6;
    }

    public String getStepName() {
        return "Benutzer anlegen";
    }

    public int next() {
        return -1;
    }

    public void loadSettings(EinrichtungsAssistentDialog as) {
        this.as = as;
    }

    public boolean validateSettings() {
        if (this.field_username.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Benutzernamen an.");
            return false;
        }

        if (this.field_password.getPassword() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Passwort an.");
            return false;
        }

        if (this.field_password.getPassword() == this.field_password2.getPassword()) {
            JOptionPane.showMessageDialog(null, "Die Passwörter stimmen nicht überein.");
            return false;
        }

        if (this.field_vorname.getText() == null) {
            JOptionPane.showMessageDialog(null, "Die geben Sie Ihren Vornamen an.");
            return false;
        }

        if (this.field_nachname.getText() == null) {
            JOptionPane.showMessageDialog(null, "Die geben Sie Ihren Nachnamen an.");
            return false;
        }

        if (Integer.valueOf(this.spin_kennung.getValue().toString()) == 0) {
            JOptionPane.showMessageDialog(null, "Die wählen Sie eine Benutzerkennung aus (nicht 0).");
            return false;
        }

        return true;
    }

    public void saveSettings() {
        BenutzerObj ben = new BenutzerObj();

        ben.setFirmenId(BasicRegistry.currentMandant.getId());

        ben.setKennung(this.spin_kennung.toString());

        ben.setVorname(field_vorname.getText());
        ben.setNachname(field_nachname.getText());

        ben.setLevel(this.combo_benutzerlevel.getSelectedIndex());
        ben.setUsername(this.field_username.getText());
        ben.setPassword(this.field_password.getPassword().toString());

        ben.setStrasse(field_strasse.getText());
        ben.setPlz(field_plz.getText());
        ben.setOrt(field_ort.getText());

        ben.setTelefon(this.field_telefon.getText());
        ben.setFax(this.field_fax.getText());
        ben.setEmail(this.field_mail.getText());
        ben.setMobil(this.field_mobil.getText());

        ben.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        ben.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        ben.setStatus(Status.NORMAL);

        try {
            int id = BenutzerSQLMethods.insertIntoBenutzer(DatabaseConnection.open(), ben);
            ben.setId(id);
            BasicRegistry.currentUser = ben;
            
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Benutzer bei der Einrichtung nicht anlegen", e);
            ShowException.showException("Der Benutzer konnte nicht angelegt werden. "
                    + "Unter Details finden Sie weitere Informationen zur Fehlermeldung",
                    ExceptionDialogGui.LEVEL_FATAL, e, "Schwerwiegend: Konnte Benutzer nicht anlegen.");
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
        jLabel10 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        field_vorname = new javax.swing.JTextField();
        field_nachname = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        field_strasse = new javax.swing.JTextField();
        field_ort = new javax.swing.JTextField();
        field_plz = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        field_telefon = new javax.swing.JTextField();
        field_fax = new javax.swing.JTextField();
        field_mobil = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        field_mail = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        field_username = new javax.swing.JTextField();
        label_obervermittler1 = new javax.swing.JLabel();
        combo_benutzerlevel = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        field_password = new javax.swing.JPasswordField();
        field_password2 = new javax.swing.JPasswordField();
        spin_kennung = new javax.swing.JSpinner();
        jToolTipLabel1 = new de.maklerpoint.office.Tools.JToolTipLabel();
        jToolTipLabel2 = new de.maklerpoint.office.Tools.JToolTipLabel();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(478, 452));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelEinrichtungBenutzer.class);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_vorname.setName("field_vorname"); // NOI18N
        field_vorname.setPreferredSize(new java.awt.Dimension(130, 25));

        field_nachname.setName("field_nachname"); // NOI18N
        field_nachname.setPreferredSize(new java.awt.Dimension(130, 25));

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        field_strasse.setName("field_strasse"); // NOI18N
        field_strasse.setPreferredSize(new java.awt.Dimension(150, 25));

        field_ort.setName("field_ort"); // NOI18N
        field_ort.setPreferredSize(new java.awt.Dimension(130, 25));

        field_plz.setName("field_plz"); // NOI18N
        field_plz.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        field_telefon.setName("field_telefon"); // NOI18N
        field_telefon.setPreferredSize(new java.awt.Dimension(130, 25));

        field_fax.setName("field_fax"); // NOI18N
        field_fax.setPreferredSize(new java.awt.Dimension(130, 25));

        field_mobil.setName("field_mobil"); // NOI18N
        field_mobil.setPreferredSize(new java.awt.Dimension(130, 25));

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        field_mail.setName("field_mail"); // NOI18N
        field_mail.setPreferredSize(new java.awt.Dimension(130, 25));

        jLabel43.setText(resourceMap.getString("jLabel43.text")); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N

        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        jLabel46.setText(resourceMap.getString("jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        field_username.setName("field_username"); // NOI18N
        field_username.setPreferredSize(new java.awt.Dimension(130, 25));

        label_obervermittler1.setText(resourceMap.getString("label_obervermittler1.text")); // NOI18N
        label_obervermittler1.setName("label_obervermittler1"); // NOI18N

        combo_benutzerlevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Untervermittler", "Vermittler", "Editor", "Administrator", "Super Administrator" }));
        combo_benutzerlevel.setSelectedIndex(4);
        combo_benutzerlevel.setName("combo_benutzerlevel"); // NOI18N

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        field_password.setText(resourceMap.getString("field_password.text")); // NOI18N
        field_password.setName("field_password"); // NOI18N

        field_password2.setText(resourceMap.getString("field_password2.text")); // NOI18N
        field_password2.setName("field_password2"); // NOI18N

        spin_kennung.setName("spin_kennung"); // NOI18N

        jToolTipLabel1.setIcon(null);
        jToolTipLabel1.setToolTipText(resourceMap.getString("jToolTipLabel1.toolTipText")); // NOI18N
        jToolTipLabel1.setName("jToolTipLabel1"); // NOI18N

        jToolTipLabel2.setIcon(null);
        jToolTipLabel2.setToolTipText(resourceMap.getString("jToolTipLabel2.toolTipText")); // NOI18N
        jToolTipLabel2.setName("jToolTipLabel2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(field_ort, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                            .addComponent(field_strasse, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(field_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(field_mail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(field_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(field_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_obervermittler1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                                .addComponent(combo_benutzerlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolTipLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel46))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(field_password2)
                                    .addComponent(field_password)
                                    .addComponent(field_username, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spin_kennung)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_nachname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jToolTipLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(spin_kennung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolTipLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(field_vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(field_nachname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(field_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(field_mail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(field_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(field_password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_obervermittler1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_benutzerlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolTipLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox combo_benutzerlevel;
    public javax.swing.JTextField field_fax;
    public javax.swing.JTextField field_mail;
    public javax.swing.JTextField field_mobil;
    public javax.swing.JTextField field_nachname;
    public javax.swing.JTextField field_ort;
    public javax.swing.JPasswordField field_password;
    public javax.swing.JPasswordField field_password2;
    public javax.swing.JTextField field_plz;
    public javax.swing.JTextField field_strasse;
    public javax.swing.JTextField field_telefon;
    public javax.swing.JTextField field_username;
    public javax.swing.JTextField field_vorname;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel43;
    public javax.swing.JLabel jLabel45;
    public javax.swing.JLabel jLabel46;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel1;
    public de.maklerpoint.office.Tools.JToolTipLabel jToolTipLabel2;
    public javax.swing.JLabel label_obervermittler1;
    public javax.swing.JSpinner spin_kennung;
    // End of variables declaration//GEN-END:variables
}
