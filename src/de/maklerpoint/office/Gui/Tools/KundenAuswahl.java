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
 * KundenAuswahl.java
 *
 * Created on Jul 29, 2010, 4:58:23 PM
 */

package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.System.Status;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenAuswahl extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    public static KundenObj kunde = null;
    public static FirmenObj fakunde = null;

    /** Creates new form KundenAuswahl */
    public KundenAuswahl(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        KundenAuswahl.kunde = null;
        initComponents();
    }

    public void loadKunden() {
//        this.combo_kunden;
        if(radio_eigene.isSelected())
            combo_kunden.setModel(new DefaultComboBoxModel(KundenRegistry.getAlleEigenenKunden()));
        else
            combo_kunden.setModel(new DefaultComboBoxModel(KundenRegistry.getAlleAktivenKunden()));        

        try {
            try {
                kunde = (KundenObj) combo_kunden.getSelectedItem();
                loadText(kunde);
            } catch (Exception e) {
                fakunde = (FirmenObj) combo_kunden.getSelectedItem();

//                if(fakunde == null) {
//                    System.out.println("Fa is null");
//                } else {
//                    System.out.println("Fa " + fakunde.toString());
//                }

                loadText(fakunde);
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
//    public Object[] buildEigeneKundenlist() {
//
//       KundenObj[] privkunden = KundenRegistry.getEigeneKunden(Status.NORMAL);
//       FirmenObj[] firmkunden = KundenRegistry.getFirmenKunden(true, Status.NORMAL);
//
//       Object[] knd = null;
//
//       if(privkunden == null && firmkunden == null)
//           return null;
//       else if(privkunden != null && firmkunden != null)
//           knd = new Object[privkunden.length + firmkunden.length];
//       else if(privkunden != null)
//           knd = new Object[privkunden.length];
//       else
//           knd = new Object[firmkunden.length];
//
//       int cnt = 0;
//
//       for(int i = 0; i < privkunden.length; i++) {
//           knd[cnt] = privkunden[i];
//           cnt++;
//       }
//
//       if(firmkunden != null) {
//
//       for(int i = 0; i < firmkunden.length; i++) {
//           knd[cnt] = firmkunden[i];
//           cnt++;
//       }
//       
//       }
//
//       return knd;
//    }

//    public Object[] buildKundenlist() {
//
//       KundenObj[] privkunden = KundenRegistry.getKunden(Status.NORMAL);
//       FirmenObj[] firmkunden = KundenRegistry.getFirmenKunden(false, Status.NORMAL);
//
//       Object[] knd = null;
//
//       if(privkunden == null && firmkunden == null)
//           return null;
//       else if(privkunden != null && firmkunden != null)
//           knd = new Object[privkunden.length + firmkunden.length];
//       else if(privkunden != null)
//           knd = new Object[privkunden.length];
//       else
//           knd = new Object[firmkunden.length];
//
//       int cnt = 0;
//
//       if(privkunden != null) {
//           for(int i = 0; i < privkunden.length; i++) {
//               knd[cnt] = privkunden[i];
//               cnt++;
//           }
//       }
//
//       if(firmkunden != null) {
//           for(int i = 0; i < firmkunden.length; i++) {
//               knd[cnt] = firmkunden[i];
//               cnt++;
//           }
//       }
//
//       return knd;
//    }

    /**
     * 
     */

    public void setKunde() {
        try {
            kunde = (KundenObj) combo_kunden.getSelectedItem();
            loadText(kunde);
        } catch (Exception e) {
            fakunde = (FirmenObj) combo_kunden.getSelectedItem();
            loadText(fakunde);
        }
    }
    
    /**
     * 
     * @param kunde
     */

    public void loadText(KundenObj kunde) {        
        StringBuilder sb = new StringBuilder();

        sb.append("Kunden Nr.: ");
        sb.append(kunde.getKundenNr());
        sb.append("\n");
        sb.append("Name: ");
        sb.append(kunde.getVorname());
        sb.append(" ");
        sb.append(kunde.getNachname());
        sb.append("\n");
        sb.append("Strasse: ");
        sb.append(kunde.getStreet());
        sb.append("\n");
        sb.append("PLZ / Ort: ");
        sb.append(kunde.getPlz());
        sb.append(" ");
        sb.append(kunde.getStadt());
        sb.append("\n");

        this.text_kurzinfo.setText(sb.toString());
    }

    /**
     * 
     * @param kunde
     */

    public void loadText(FirmenObj kunde) {
        StringBuilder sb = new StringBuilder();

        sb.append("Kunden Nr.: ");
        sb.append(kunde.getKundenNr());
        sb.append("\n");
        sb.append("Name: ");
        sb.append(kunde.getFirmenName());
        sb.append("\n");
        sb.append("Strasse: ");
        sb.append(kunde.getFirmenStrasse());
        sb.append("\n");
        sb.append("PLZ / Ort: ");
        sb.append(kunde.getFirmenPLZ());
        sb.append(" ");
        sb.append(kunde.getFirmenStadt());
        sb.append("\n");

        this.text_kurzinfo.setText(sb.toString());
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public Object getReturnStatus() {
        return returnKunde;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioKunden = new javax.swing.ButtonGroup();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        radio_eigene = new javax.swing.JRadioButton();
        radio_alle = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_kurzinfo = new javax.swing.JTextArea();
        combo_kunden = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(KundenAuswahl.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setMnemonic('O');
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setMnemonic('A');
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        radioKunden.add(radio_eigene);
        radio_eigene.setSelected(true);
        radio_eigene.setText(resourceMap.getString("radio_eigene.text")); // NOI18N
        radio_eigene.setName("radio_eigene"); // NOI18N
        radio_eigene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_eigeneActionPerformed(evt);
            }
        });

        radioKunden.add(radio_alle);
        radio_alle.setText(resourceMap.getString("radio_alle.text")); // NOI18N
        radio_alle.setName("radio_alle"); // NOI18N
        radio_alle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_alleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radio_eigene)
                .addGap(18, 18, 18)
                .addComponent(radio_alle)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radio_eigene, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(radio_alle, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_kurzinfo.setColumns(20);
        text_kurzinfo.setEditable(false);
        text_kurzinfo.setRows(5);
        text_kurzinfo.setName("text_kurzinfo"); // NOI18N
        jScrollPane1.setViewportView(text_kurzinfo);

        combo_kunden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_kunden.setName("combo_kunden"); // NOI18N
        this.loadKunden();
        combo_kunden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kundenActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_kunden, 0, 305, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(162, Short.MAX_VALUE)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            doClose((KundenObj) combo_kunden.getSelectedItem());
        } catch (Exception e) {
            doClose((FirmenObj) combo_kunden.getSelectedItem());
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose();
    }//GEN-LAST:event_closeDialog

    private void radio_alleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_alleActionPerformed
        loadKunden();
    }//GEN-LAST:event_radio_alleActionPerformed

    private void radio_eigeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_eigeneActionPerformed
        loadKunden();
    }//GEN-LAST:event_radio_eigeneActionPerformed

    private void combo_kundenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundenActionPerformed
        if(combo_kunden.getSelectedIndex() == -1)
            return;

        try {
            KundenObj selkunde = (KundenObj) combo_kunden.getSelectedItem();
            loadText(selkunde);
        } catch (Exception e) {
            FirmenObj firma = (FirmenObj) combo_kunden.getSelectedItem();
            loadText(firma);
        }
    }//GEN-LAST:event_combo_kundenActionPerformed

    private void doClose() {
        returnKunde = null;
        setVisible(false);
        dispose();
    }

    private void doClose(FirmenObj dakunde) {
        returnKunde = dakunde;
        setVisible(false);
        dispose();
    }

    private void doClose(KundenObj dakunde) {
        returnKunde = dakunde;
        setVisible(false);
        dispose();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KundenAuswahl dialog = new KundenAuswahl(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox combo_kunden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    private javax.swing.ButtonGroup radioKunden;
    private javax.swing.JRadioButton radio_alle;
    private javax.swing.JRadioButton radio_eigene;
    private javax.swing.JTextArea text_kurzinfo;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
    private Object returnKunde = null;
}
