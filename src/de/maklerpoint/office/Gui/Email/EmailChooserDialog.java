/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmailChooserDialog.java
 *
 * Created on 27.07.2011, 11:43:08
 */
package de.maklerpoint.office.Gui.Email;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Gui.Benutzer.PanelBenutzerUebersicht;
import de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht;
import de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht;
import de.maklerpoint.office.Gui.Versicherer.PanelVersichererUebersicht;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KontakteRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.POJOTools;
import de.maklerpoint.office.Versicherer.VersichererObj;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class EmailChooserDialog extends javax.swing.JDialog {

    private String[] Column = new String[]{"Hidden", "Name", "E-Mail"};
    public String returnMail = null;

    /** Creates new form EmailChooserDialog */
    public EmailChooserDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.returnMail = null;
        initComponents();
        loadTable();
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public String getReturnStatus() {
        return returnMail;
    }

    private void loadTable() {
        KundenObj[] privknd = null;
        FirmenObj[] firmknd = null;
        VersichererObj[] vers = null;
        BenutzerObj[] ben = null;
        KontaktObj[] kontakte = null;

        if (this.btnPK.isSelected()) {
            privknd = KundenRegistry.getKunden(Status.NORMAL);
        }

        if (this.btnGK.isSelected()) {
            firmknd = KundenRegistry.getFirmenKunden(false, Status.NORMAL);
        }

        if (this.btnVersicherer.isSelected()) {
            vers = VersicherungsRegistry.getVersicherer(Status.NORMAL);
        }

        if (this.btnBenutzer.isSelected()) {
            ben = BenutzerRegistry.getAllBenutzer(Status.NORMAL);
        }

        if (this.btnKontakte.isSelected()) {
            kontakte = KontakteRegistry.getEigeneKontakte(Status.NORMAL);
        }
        Object[][] data = null;

        data = new Object[POJOTools.calulcateLenght(privknd, firmknd, vers, ben, kontakte)][6];

        int cnt = 0;

        if (this.btnPK.isSelected()) {
            if (privknd != null) {

                for (int i = 0; i < privknd.length; i++) {
                    String mail = KundenMailHelper.getKundenMail(privknd[i]);

                    if (mail != null) {
                        data[cnt][0] = privknd[i];
                        data[cnt][1] = privknd[i].toString();
                        data[cnt][2] = mail;

                        cnt++;
                    }
                }
            }
        }

        if (this.btnGK.isSelected()) {
            if (firmknd != null) {
                for (int i = 0; i < firmknd.length; i++) {
                    String mail = KundenMailHelper.getGeschKundenMail(firmknd[i]);

                    if (mail != null) {
                        data[cnt][0] = firmknd[i];
                        data[cnt][1] = firmknd[i].toString();
                        data[cnt][2] = mail;

                        cnt++;
                    }
                }
            }
        }

        if (this.btnBenutzer.isSelected()) {
            if (ben != null) {
                for (int i = 0; i < ben.length; i++) {
                    String mail = ben[i].getEmail();

                    if (mail != null) {
                        data[cnt][0] = ben[i];
                        data[cnt][1] = ben[i].toString();
                        data[cnt][2] = mail;

                        cnt++;
                    }
                }
            }
        }

        if (this.btnKontakte.isSelected()) {
            if (kontakte != null) {
                for (int i = 0; i < kontakte.length; i++) {
                    String mail = KundenMailHelper.getKontaktMail(kontakte[i]);

                    if (mail != null) {
                        data[cnt][0] = firmknd[i];
                        data[cnt][1] = firmknd[i].toString();
                        data[cnt][2] = mail;

                        cnt++;
                    }
                }
            }
        }

        if (this.btnVersicherer.isSelected()) {
            if (vers != null) {
                for (int i = 0; i < vers.length; i++) {
                    String mail = KundenMailHelper.getVersichererMail(vers[i]);

                    if (mail != null) {
                        data[cnt][0] = vers[i];
                        data[cnt][1] = vers[i].toString();
                        data[cnt][2] = mail;

                        cnt++;
                    }
                }
            }
        }

        setTable(data, Column);
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_adressbuch.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_adressbuch.getModel();
            atm.setData(data);
            table_adressbuch.packAll();
            return;
        }

        table_adressbuch.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_adressbuch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_adressbuch.setColumnSelectionAllowed(false);
        table_adressbuch.setCellSelectionEnabled(false);
        table_adressbuch.setRowSelectionAllowed(true);
        table_adressbuch.setAutoCreateRowSorter(true);

        table_adressbuch.setFillsViewportHeight(true);
        table_adressbuch.removeColumn(table_adressbuch.getColumnModel().getColumn(0));

//        MouseListener popupListener = new TablePopupListener();
//        table_adressbuch.addMouseListener(popupListener);
        table_adressbuch.setColumnControlVisible(true);

//        JTableHeader header = table_adressbuch.getTableHeader();
//        header.addMouseListener(popupListener);
//        header.validate();

        table_adressbuch.packAll();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        table_adressbuch = new org.jdesktop.swingx.JXTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnPK = new javax.swing.JToggleButton();
        btnGK = new javax.swing.JToggleButton();
        btnKontakte = new javax.swing.JToggleButton();
        btnBenutzer = new javax.swing.JToggleButton();
        btnVersicherer = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        field_cardsearch = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnChose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        table_adressbuch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Kommunikation 1", "E-Mail"
            }
        ));
        table_adressbuch.setColumnControlVisible(true);
        table_adressbuch.setName("table_adressbuch"); // NOI18N
        table_adressbuch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_adressbuchMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_adressbuch);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnPK.setIcon(null);
        btnPK.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(EmailChooserDialog.class);
        btnPK.setToolTipText(resourceMap.getString("btnPK.toolTipText")); // NOI18N
        btnPK.setFocusable(false);
        btnPK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPK.setName("btnPK"); // NOI18N
        btnPK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKbtnGKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPK);

        btnGK.setIcon(null);
        btnGK.setSelected(true);
        btnGK.setToolTipText(resourceMap.getString("btnGK.toolTipText")); // NOI18N
        btnGK.setFocusable(false);
        btnGK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGK.setName("btnGK"); // NOI18N
        btnGK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGK);

        btnKontakte.setIcon(null);
        btnKontakte.setToolTipText(resourceMap.getString("btnKontakte.toolTipText")); // NOI18N
        btnKontakte.setFocusable(false);
        btnKontakte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKontakte.setName("btnKontakte"); // NOI18N
        btnKontakte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKontakte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKontaktebtnGKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnKontakte);

        btnBenutzer.setIcon(null);
        btnBenutzer.setSelected(true);
        btnBenutzer.setToolTipText(resourceMap.getString("btnBenutzer.toolTipText")); // NOI18N
        btnBenutzer.setFocusable(false);
        btnBenutzer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBenutzer.setName("btnBenutzer"); // NOI18N
        btnBenutzer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBenutzer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBenutzerbtnGKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBenutzer);

        btnVersicherer.setIcon(null);
        btnVersicherer.setToolTipText(resourceMap.getString("btnVersicherer.toolTipText")); // NOI18N
        btnVersicherer.setFocusable(false);
        btnVersicherer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVersicherer.setName("btnVersicherer"); // NOI18N
        btnVersicherer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVersicherer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVersichererbtnGKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVersicherer);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        field_cardsearch.setText(resourceMap.getString("field_cardsearch.text")); // NOI18N
        field_cardsearch.setName("field_cardsearch"); // NOI18N
        field_cardsearch.setPreferredSize(new java.awt.Dimension(180, 25));
        field_cardsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_cardsearchMouseClicked(evt);
            }
        });
        field_cardsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_cardsearchKeyTyped(evt);
            }
        });
        jToolBar1.add(field_cardsearch);

        btn_search.setIcon(null);
        btn_search.setToolTipText(resourceMap.getString("btn_search.toolTipText")); // NOI18N
        btn_search.setBorderPainted(false);
        btn_search.setFocusable(false);
        btn_search.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_search.setName("btn_search"); // NOI18N
        btn_search.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_search);

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnChose.setMnemonic('A');
        btnChose.setText(resourceMap.getString("btnChose.text")); // NOI18N
        btnChose.setName("btnChose"); // NOI18N
        btnChose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChose, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChose)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_adressbuchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_adressbuchMouseClicked

}//GEN-LAST:event_table_adressbuchMouseClicked

    private void btnPKbtnGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKbtnGKActionPerformed
        loadTable();
}//GEN-LAST:event_btnPKbtnGKActionPerformed

    private void btnGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGKActionPerformed
        loadTable();
}//GEN-LAST:event_btnGKActionPerformed

    private void btnKontaktebtnGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKontaktebtnGKActionPerformed
        loadTable();
}//GEN-LAST:event_btnKontaktebtnGKActionPerformed

    private void btnBenutzerbtnGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBenutzerbtnGKActionPerformed
        loadTable();
}//GEN-LAST:event_btnBenutzerbtnGKActionPerformed

    private void btnVersichererbtnGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVersichererbtnGKActionPerformed
        loadTable();
}//GEN-LAST:event_btnVersichererbtnGKActionPerformed

    private void field_cardsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_cardsearchMouseClicked
        if (this.field_cardsearch.getText().equalsIgnoreCase("suche..")) {
            this.field_cardsearch.setText("");
        }
}//GEN-LAST:event_field_cardsearchMouseClicked

    private void field_cardsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_cardsearchKeyTyped
        table_adressbuch.getSearchable().search(field_cardsearch.getText());
}//GEN-LAST:event_field_cardsearchKeyTyped

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        table_adressbuch.getSearchable().search(field_cardsearch.getText());
}//GEN-LAST:event_btn_searchActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        returnMail = null;
        this.dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnChoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoseActionPerformed
        int row = this.table_adressbuch.getSelectedRow();

        if (row == -1) {
            return;
        }

        Object obj = table_adressbuch.getModel().getValueAt(row, 2);
        
        if(obj != null){
            this.returnMail = obj.toString();
        }
        
        this.dispose();
}//GEN-LAST:event_btnChoseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EmailChooserDialog dialog = new EmailChooserDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JToggleButton btnBenutzer;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChose;
    private javax.swing.JToggleButton btnGK;
    private javax.swing.JToggleButton btnKontakte;
    private javax.swing.JToggleButton btnPK;
    private javax.swing.JToggleButton btnVersicherer;
    public static javax.swing.JButton btn_search;
    public static javax.swing.JTextField field_cardsearch;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private org.jdesktop.swingx.JXTable table_adressbuch;
    // End of variables declaration//GEN-END:variables
}
