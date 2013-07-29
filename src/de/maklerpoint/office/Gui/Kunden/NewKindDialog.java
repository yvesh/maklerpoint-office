/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewKindDialog.java
 *
 * Created on 12.06.2011, 10:34:22
 */
package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Kunden.KinderObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KinderSQLMethods;
import de.maklerpoint.office.Logging.Log;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class NewKindDialog extends javax.swing.JDialog {

    
    private boolean update = false;
    private KinderObj kind = null;
    private KundenObj kunde = null;
    
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
    /** Creates new form NewKindDialog */
    public NewKindDialog(java.awt.Frame parent, boolean modal, KundenObj kunde) {
        super(parent, modal);
        this.update = false;
        this.kind = null;
        this.kunde = kunde;
        initComponents();
        setUp();        
    }
    
    public NewKindDialog(java.awt.Frame parent, boolean modal, KinderObj kind, KundenObj kunde) {
        super(parent, modal);
        this.update = true;
        this.kind = kind;
        this.kunde = kunde;
        initComponents();
        setUp();
    }
    
    private void setUp(){
        this.field_nachname.setText(kunde.getNachname());
        this.setTitle("Neues Kind für " + kunde.getNachname() + " (" + kunde.getKundenNr() + ")");
        loadKind();
    }
    
    private void loadKind(){
        if(kind == null)
            return;
        
        this.field_beruf.setText(kind.getKindBeruf());
        this.field_custom.setText(kind.getCustom());
        this.field_vorname.setText(kind.getKindVorname());
        this.field_nachname.setText(kind.getKindName());
        this.combo_wohnort.setSelectedItem(kind.getKindWohnort());
        try {
            if(kind.getKindGeburtsdatum() != null)            
                this.date_geburtsdatum.setDate(df.parse(kind.getKindGeburtsdatum()));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        this.text_comments.setText(kind.getComments());
        this.setTitle("Kind bearbeiten " + kind.getKindName() + " (" + kunde.getKundenNr() + ")");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel28 = new javax.swing.JLabel();
        field_vorname = new javax.swing.JTextField();
        field_nachname = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        date_geburtsdatum = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        field_beruf = new javax.swing.JTextField();
        combo_wohnort = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        field_custom = new javax.swing.JTextField();
        btnMaxCustom1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        btnMaxTextKommentar = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NewKindDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        field_vorname.setName("field_vorname"); // NOI18N

        field_nachname.setName("field_nachname"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jSeparator6.setName("jSeparator6"); // NOI18N

        date_geburtsdatum.setName("date_geburtsdatum"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        field_beruf.setName("field_beruf"); // NOI18N
        field_beruf.setPreferredSize(new java.awt.Dimension(180, 25));

        combo_wohnort.setEditable(true);
        combo_wohnort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "Beim VN", "Alleine", "Sonstiges" }));
        combo_wohnort.setName("combo_wohnort"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_custom.setName("field_custom"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane1.setViewportView(text_comments);

        btnMaxTextKommentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxTextKommentar.setToolTipText(resourceMap.getString("btnMaxTextKommentar.toolTipText")); // NOI18N
        btnMaxTextKommentar.setName("btnMaxTextKommentar"); // NOI18N
        btnMaxTextKommentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxTextKommentarActionPerformed(evt);
            }
        });

        jSeparator9.setName("jSeparator9"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_vorname, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(field_nachname, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(combo_wohnort, 0, 237, Short.MAX_VALUE)
                            .addComponent(field_beruf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_custom, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxCustom1))
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_nachname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_beruf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(combo_wohnort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnMaxCustom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if(this.update == false) {
            int dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
                    + "gehen in diesem Fall verloren.", "Wollen Sie das Fenster schließen?", JOptionPane.YES_NO_OPTION);
            if(dial == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        } else {
            this.dispose();
        }
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(update == false) {
            kind = new KinderObj();
        }
        
        if(this.field_nachname.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Nachnamen an.");
            return;
        }
        
        if(this.field_vorname.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Vornamen an.");
            return;
        }
        
        kind.setKindBeruf(this.field_beruf.getText());
        kind.setCustom(this.field_custom.getText());
        kind.setKindVorname(this.field_vorname.getText());
        kind.setKindName(this.field_nachname.getText());
        kind.setKindWohnort(this.combo_wohnort.getSelectedItem().toString());
        kind.setKindGeburtsdatum(df.format(this.date_geburtsdatum.getDate()));
        kind.setComments(this.text_comments.getText());
        
        kind.setParentId(kunde.getId());        
        
        
        try {
            if(update == false) {
                kind.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));                
                int id = KinderSQLMethods.insertIntoKinder(DatabaseConnection.open(), kind);
                
            } else {
                boolean success = KinderSQLMethods.updateKinder(DatabaseConnection.open(), kind);
                
                if(!success) {
                    Log.databaselogger.fatal("Konnte das Kind nicht aktualisieren (nicht gefunden).");
                    System.out.println("ADD Exception: Kind nicht geupdated");
                }
            }
            this.dispose();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte das Kind nicht aktualisieren / neu hinzufügen.");
            ShowException.showException("Datenbankfehler: Das Kind konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte das Kind nicht speichern");
        }
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom, "Benutzerdefiniert");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewKindDialog dialog = new NewKindDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox combo_wohnort;
    private com.toedter.calendar.JDateChooser date_geburtsdatum;
    private javax.swing.JTextField field_beruf;
    private javax.swing.JTextField field_custom;
    private javax.swing.JTextField field_nachname;
    private javax.swing.JTextField field_vorname;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextArea text_comments;
    // End of variables declaration//GEN-END:variables
}
