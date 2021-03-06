/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NeuBankkontoDialog.java
 *
 * Created on 07.06.2011, 11:15:08
 */
package de.maklerpoint.office.Gui.Bank;

import de.maklerpoint.office.Bank.BankKontoObj;
import de.maklerpoint.office.Bank.Tools.BankKontoSQLMethods;
import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.System.Status;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class NeuBankkontoDialog extends javax.swing.JDialog {

    private boolean update = false;
    private String kundenKennung = "-1";
    private BankKontoObj bk = null;
    private BenutzerObj ben = null;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

    /** Creates new form NeuBankkontoDialog */
    public NeuBankkontoDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public NeuBankkontoDialog(java.awt.Frame parent, boolean modal, String kdnr) {
        super(parent, modal);
        this.kundenKennung = kdnr;
        this.bk = null;
        this.ben = null;
        this.update = false;
        initComponents();
        setUp();
    }

    public NeuBankkontoDialog(java.awt.Frame parent, boolean modal, BankKontoObj bk) {
        super(parent, modal);
        if (!bk.getKundenKennung().equalsIgnoreCase("-1")) {
            this.kundenKennung = bk.getKundenKennung();
        }
        this.bk = bk;
        if (bk.getBenutzerId() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(bk.getBenutzerId());
        }
        this.update = true;
        initComponents();
        setUp();
    }

    public NeuBankkontoDialog(java.awt.Frame parent, boolean modal, BenutzerObj ben) {
        super(parent, modal);
        this.kundenKennung = "-1";
        this.bk = null;
        this.ben = ben;
        this.update = false;
        initComponents();
        setUp();
    }

    private void setUp() {
        if (update == true) {
            this.setTitle("Bankkonto " + bk.getKontonummer() + " bearbeiten");
        } else {
            this.setTitle("Neues Bankkonto");
        }
        loadKonto();
    }

    private void loadKonto() {
        if (bk == null) {
            return;
        }

        this.field_bankinstitut.setText(bk.getBankinstitut());
        this.field_bankleitzahl.setText(bk.getBankleitzahl());
        this.field_bic.setText(bk.getBic());
        this.field_iban.setText(bk.getIban());
        this.field_kontoinhaber.setText(bk.getKontoinhaber());
        this.field_kontonummer.setText(bk.getKontonummer());

        this.text_comments.setText(bk.getComments());

        if (bk.getStatus() == Status.NORMAL) {
            this.combo_status.setSelectedIndex(0);
        } else if (bk.getStatus() == Status.ARCHIVED) {
            this.combo_status.setSelectedIndex(1);
        } else if (bk.getStatus() == Status.DELETED) {
            this.combo_status.setSelectedIndex(2);
        }


        this.combo_type.setSelectedIndex(bk.getType());

        this.label_created.setText(df.format(bk.getCreated()));
        this.label_modified.setText(df.format(bk.getModified()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        paneKonto = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        field_kontonummer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_type = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        field_bankleitzahl = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        field_bankinstitut = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        field_kontoinhaber = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        field_iban = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        field_bic = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        combo_status = new javax.swing.JComboBox();
        panel_advanced = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        label_created = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        label_modified = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        btnMaxTextKommentar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NeuBankkontoDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(120, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        paneKonto.setName("paneKonto"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        field_kontonummer.setName("field_kontonummer"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Standardkonto", "Guthabenkonto" }));
        combo_type.setName("combo_type"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_bankleitzahl.setName("field_bankleitzahl"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_bankinstitut.setName("field_bankinstitut"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_kontoinhaber.setName("field_kontoinhaber"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_iban.setName("field_iban"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        field_bic.setName("field_bic"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        combo_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktiv", "Archiviert", "Gelöscht" }));
        combo_status.setName("combo_status"); // NOI18N

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(field_kontonummer, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(field_bankleitzahl, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(field_bankinstitut, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(field_kontoinhaber, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(field_iban, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(field_bic, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                        .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(field_kontonummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(field_bankleitzahl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(field_bankinstitut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(field_kontoinhaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(field_iban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(field_bic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        paneKonto.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_advanced.setName("panel_advanced"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        label_created.setText(resourceMap.getString("label_created.text")); // NOI18N
        label_created.setName("label_created"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        label_modified.setText(resourceMap.getString("label_modified.text")); // NOI18N
        label_modified.setName("label_modified"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane2.setViewportView(text_comments);

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        btnMaxTextKommentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxTextKommentar.setToolTipText(resourceMap.getString("btnMaxTextKommentar.toolTipText")); // NOI18N
        btnMaxTextKommentar.setName("btnMaxTextKommentar"); // NOI18N
        btnMaxTextKommentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxTextKommentarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_advancedLayout = new javax.swing.GroupLayout(panel_advanced);
        panel_advanced.setLayout(panel_advancedLayout);
        panel_advancedLayout.setHorizontalGroup(
            panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_advancedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_advancedLayout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_advancedLayout.createSequentialGroup()
                        .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                        .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_created)
                            .addComponent(label_modified))
                        .addContainerGap())
                    .addGroup(panel_advancedLayout.createSequentialGroup()
                        .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMaxTextKommentar)
                        .addGap(18, 18, 18))))
        );
        panel_advancedLayout.setVerticalGroup(
            panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_advancedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_advancedLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12))
                    .addGroup(panel_advancedLayout.createSequentialGroup()
                        .addComponent(label_created)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label_modified)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_advancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaxTextKommentar))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        paneKonto.addTab(resourceMap.getString("panel_advanced.TabConstraints.tabTitle"), panel_advanced); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paneKonto, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(404, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(paneKonto, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(51, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.update == false) {
            int dial = JOptionPane.NO_OPTION;
            dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
                    + "gehen in diesem Fall verloren.", "Wollen Sie das Fenster schließen?", JOptionPane.YES_NO_OPTION);
            if (dial == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        } else {
            this.dispose();
        }
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            bk = new BankKontoObj();
        }

        bk.setBankinstitut(field_bankinstitut.getText());
        bk.setBankleitzahl(field_bankleitzahl.getText());
        bk.setBic(field_bic.getText());
        bk.setIban(field_iban.getText());
        bk.setKontoinhaber(field_kontoinhaber.getText());
        bk.setKontonummer(field_kontonummer.getText());

        bk.setComments(text_comments.getText());
        int bst = this.combo_status.getSelectedIndex();

        if (bst == 0) {
            bk.setStatus(Status.NORMAL);
        } else if (bst == 1) {
            bk.setStatus(Status.ARCHIVED);
        } else if (bst == 2) {
            bk.setStatus(Status.DELETED);
        } else {
            Log.logger.warn("Bankkonto " + bk.getId() + " hat einen unbekannten Status: "
                    + bk.getStatus() + " - Falle auf Status Normal zurück.");
            bk.setStatus(Status.NORMAL);
        }

        this.combo_type.getSelectedIndex();

        try {
            if (update) {
                boolean success = BankKontoSQLMethods.updateBankkonten(DatabaseConnection.open(), bk);
                if (!success) {
                    Log.databaselogger.fatal("Das Bankkonto konnte nicht aktualisiert werden");
                }
            } else {
                if (ben != null) {
                    bk.setBenutzerId(ben.getId());
                }

                if (!kundenKennung.equalsIgnoreCase("-1")) {
                    bk.setKundenKennung(kundenKennung);
                }

                bk.setCreatorId(BasicRegistry.currentUser.getId());
                bk.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                BankKontoSQLMethods.insertIntoBankkonten(DatabaseConnection.open(), bk);
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte Bankkonto nicht speichern", e);
            ShowException.showException("Bei der Speicherung des Bankkontos ist ein Datenbank Fehler aufgetretten. "
                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Bankkonto nicht speichern");
        }

        this.dispose();
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NeuBankkontoDialog dialog = new NeuBankkontoDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox combo_status;
    private javax.swing.JComboBox combo_type;
    private javax.swing.JTextField field_bankinstitut;
    private javax.swing.JTextField field_bankleitzahl;
    private javax.swing.JTextField field_bic;
    private javax.swing.JTextField field_iban;
    private javax.swing.JTextField field_kontoinhaber;
    private javax.swing.JTextField field_kontonummer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel label_created;
    private javax.swing.JLabel label_modified;
    private javax.swing.JTabbedPane paneKonto;
    private javax.swing.JPanel panel_advanced;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JTextArea text_comments;
    // End of variables declaration//GEN-END:variables
}
