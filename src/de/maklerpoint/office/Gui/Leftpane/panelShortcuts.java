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
 * panelShortcuts.java
 *
 * Created on Jul 28, 2010, 12:29:59 PM
 */

package de.maklerpoint.office.Gui.Leftpane;

import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.External.iReport;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Notizen.NotizenDialogHelper;
import de.maklerpoint.office.Gui.TextbauSteine.TextbausteinDialogHelper;
import de.maklerpoint.office.Logging.Log;
import java.awt.Desktop;
import java.net.URI;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class panelShortcuts extends javax.swing.JPanel {

    private Desktop desktop = Desktop.getDesktop();
    
    /** Creates new form panelShortcuts */
    public panelShortcuts() {
        initComponents();        
        setUp();
    }
        
    private void setUp(){      
        bbarextras.setOrientation(1);
        bbaransichten.setOrientation(1);        
        bbarsonstiges.setOrientation(1);        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOutlookBar1 = new com.l2fprod.common.swing.JOutlookBar();
        panel_ansichten = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bbaransichten = new com.l2fprod.common.swing.JButtonBar();
        btnShortcutStartseite = new javax.swing.JButton();
        btnShortcutTermine = new javax.swing.JButton();
        btnShortcutPrivatkunde = new javax.swing.JButton();
        btnShortcutGeschaeftskunden = new javax.swing.JButton();
        btnShortcutVersicherungen = new javax.swing.JButton();
        btnShortcutProdukte = new javax.swing.JButton();
        btnShortcutVertraege = new javax.swing.JButton();
        btnShortcutStoerfaelle = new javax.swing.JButton();
        btnShortcutSchaeden = new javax.swing.JButton();
        btnShortcutBenutzer = new javax.swing.JButton();
        panel_extras = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bbarextras = new com.l2fprod.common.swing.JButtonBar();
        btnShortcutWaehrung = new javax.swing.JButton();
        btnShortcutTags = new javax.swing.JButton();
        btnShortcutSicherung = new javax.swing.JButton();
        btnShortcutNotizen = new javax.swing.JButton();
        btnShortcutSparte = new javax.swing.JButton();
        btnShortcutTextbaustein = new javax.swing.JButton();
        panel_sonstiges = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        bbarsonstiges = new com.l2fprod.common.swing.JButtonBar();
        btnireport = new javax.swing.JButton();
        btnMaklerPoint = new javax.swing.JButton();
        btnVerlauf = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jOutlookBar1.setName("jOutlookBar1"); // NOI18N

        panel_ansichten.setName("panel_ansichten"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        bbaransichten.setName("bbaransichten"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelShortcuts.class);
        btnShortcutStartseite.setIcon(resourceMap.getIcon("btnShortcutStartseite.icon")); // NOI18N
        btnShortcutStartseite.setMnemonic('S');
        btnShortcutStartseite.setText(resourceMap.getString("btnShortcutStartseite.text")); // NOI18N
        btnShortcutStartseite.setIconTextGap(6);
        btnShortcutStartseite.setMinimumSize(new java.awt.Dimension(60, 27));
        btnShortcutStartseite.setName("btnShortcutStartseite"); // NOI18N
        btnShortcutStartseite.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutStartseite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutStartseiteActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutStartseite);

        btnShortcutTermine.setIcon(resourceMap.getIcon("btnShortcutTermine.icon")); // NOI18N
        btnShortcutTermine.setMnemonic('K');
        btnShortcutTermine.setText(resourceMap.getString("btnShortcutTermine.text")); // NOI18N
        btnShortcutTermine.setIconTextGap(6);
        btnShortcutTermine.setName("btnShortcutTermine"); // NOI18N
        btnShortcutTermine.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutTermine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutTermineActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutTermine);

        btnShortcutPrivatkunde.setIcon(resourceMap.getIcon("btnShortcutPrivatkunde.icon")); // NOI18N
        btnShortcutPrivatkunde.setMnemonic('P');
        btnShortcutPrivatkunde.setText(resourceMap.getString("btnShortcutPrivatkunde.text")); // NOI18N
        btnShortcutPrivatkunde.setIconTextGap(6);
        btnShortcutPrivatkunde.setName("btnShortcutPrivatkunde"); // NOI18N
        btnShortcutPrivatkunde.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutPrivatkunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutPrivatkundeActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutPrivatkunde);

        btnShortcutGeschaeftskunden.setIcon(resourceMap.getIcon("btnShortcutGeschaeftskunden.icon")); // NOI18N
        btnShortcutGeschaeftskunden.setMnemonic('G');
        btnShortcutGeschaeftskunden.setText(resourceMap.getString("btnShortcutGeschaeftskunden.text")); // NOI18N
        btnShortcutGeschaeftskunden.setIconTextGap(6);
        btnShortcutGeschaeftskunden.setName("btnShortcutGeschaeftskunden"); // NOI18N
        btnShortcutGeschaeftskunden.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutGeschaeftskunden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutGeschaeftskundenActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutGeschaeftskunden);

        btnShortcutVersicherungen.setIcon(resourceMap.getIcon("btnShortcutVersicherungen.icon")); // NOI18N
        btnShortcutVersicherungen.setMnemonic('G');
        btnShortcutVersicherungen.setText(resourceMap.getString("btnShortcutVersicherungen.text")); // NOI18N
        btnShortcutVersicherungen.setIconTextGap(6);
        btnShortcutVersicherungen.setName("btnShortcutVersicherungen"); // NOI18N
        btnShortcutVersicherungen.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutVersicherungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutVersicherungenActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutVersicherungen);

        btnShortcutProdukte.setIcon(resourceMap.getIcon("btnShortcutProdukte.icon")); // NOI18N
        btnShortcutProdukte.setMnemonic('P');
        btnShortcutProdukte.setText(resourceMap.getString("btnShortcutProdukte.text")); // NOI18N
        btnShortcutProdukte.setIconTextGap(6);
        btnShortcutProdukte.setName("btnShortcutProdukte"); // NOI18N
        btnShortcutProdukte.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutProdukte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutProdukteActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutProdukte);

        btnShortcutVertraege.setIcon(resourceMap.getIcon("btnShortcutVertraege.icon")); // NOI18N
        btnShortcutVertraege.setMnemonic('V');
        btnShortcutVertraege.setText(resourceMap.getString("btnShortcutVertraege.text")); // NOI18N
        btnShortcutVertraege.setIconTextGap(6);
        btnShortcutVertraege.setName("btnShortcutVertraege"); // NOI18N
        btnShortcutVertraege.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutVertraege.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutVertraegeActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutVertraege);

        btnShortcutStoerfaelle.setIcon(resourceMap.getIcon("btnShortcutStoerfaelle.icon")); // NOI18N
        btnShortcutStoerfaelle.setMnemonic('S');
        btnShortcutStoerfaelle.setText(resourceMap.getString("btnShortcutStoerfaelle.text")); // NOI18N
        btnShortcutStoerfaelle.setIconTextGap(6);
        btnShortcutStoerfaelle.setName("btnShortcutStoerfaelle"); // NOI18N
        btnShortcutStoerfaelle.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutStoerfaelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutStoerfaelleActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutStoerfaelle);

        btnShortcutSchaeden.setIcon(resourceMap.getIcon("btnShortcutSchaeden.icon")); // NOI18N
        btnShortcutSchaeden.setMnemonic('S');
        btnShortcutSchaeden.setText(resourceMap.getString("btnShortcutSchaeden.text")); // NOI18N
        btnShortcutSchaeden.setIconTextGap(6);
        btnShortcutSchaeden.setName("btnShortcutSchaeden"); // NOI18N
        btnShortcutSchaeden.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutSchaeden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutSchaedenActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutSchaeden);

        btnShortcutBenutzer.setIcon(resourceMap.getIcon("btnShortcutBenutzer.icon")); // NOI18N
        btnShortcutBenutzer.setMnemonic('B');
        btnShortcutBenutzer.setText(resourceMap.getString("btnShortcutBenutzer.text")); // NOI18N
        btnShortcutBenutzer.setIconTextGap(6);
        btnShortcutBenutzer.setName("btnShortcutBenutzer"); // NOI18N
        btnShortcutBenutzer.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutBenutzer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutBenutzerActionPerformed(evt);
            }
        });
        bbaransichten.add(btnShortcutBenutzer);

        jScrollPane1.setViewportView(bbaransichten);

        javax.swing.GroupLayout panel_ansichtenLayout = new javax.swing.GroupLayout(panel_ansichten);
        panel_ansichten.setLayout(panel_ansichtenLayout);
        panel_ansichtenLayout.setHorizontalGroup(
            panel_ansichtenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );
        panel_ansichtenLayout.setVerticalGroup(
            panel_ansichtenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );

        jOutlookBar1.addTab(resourceMap.getString("panel_ansichten.TabConstraints.tabTitle"), panel_ansichten); // NOI18N

        panel_extras.setName("panel_extras"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        bbarextras.setName("bbarextras"); // NOI18N

        btnShortcutWaehrung.setIcon(resourceMap.getIcon("btnShortcutWaehrung.icon")); // NOI18N
        btnShortcutWaehrung.setMnemonic('W');
        btnShortcutWaehrung.setText(resourceMap.getString("btnShortcutWaehrung.text")); // NOI18N
        btnShortcutWaehrung.setIconTextGap(6);
        btnShortcutWaehrung.setName("btnShortcutWaehrung"); // NOI18N
        btnShortcutWaehrung.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutWaehrung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutWaehrungActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutWaehrung);

        btnShortcutTags.setIcon(resourceMap.getIcon("btnShortcutTags.icon")); // NOI18N
        btnShortcutTags.setMnemonic('M');
        btnShortcutTags.setText(resourceMap.getString("btnShortcutTags.text")); // NOI18N
        btnShortcutTags.setIconTextGap(6);
        btnShortcutTags.setName("btnShortcutTags"); // NOI18N
        btnShortcutTags.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutTagsActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutTags);

        btnShortcutSicherung.setIcon(resourceMap.getIcon("btnShortcutSicherung.icon")); // NOI18N
        btnShortcutSicherung.setMnemonic('S');
        btnShortcutSicherung.setText(resourceMap.getString("btnShortcutSicherung.text")); // NOI18N
        btnShortcutSicherung.setIconTextGap(6);
        btnShortcutSicherung.setName("btnShortcutSicherung"); // NOI18N
        btnShortcutSicherung.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutSicherung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutSicherungActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutSicherung);

        btnShortcutNotizen.setIcon(resourceMap.getIcon("btnShortcutNotizen.icon")); // NOI18N
        btnShortcutNotizen.setMnemonic('N');
        btnShortcutNotizen.setText(resourceMap.getString("btnShortcutNotizen.text")); // NOI18N
        btnShortcutNotizen.setIconTextGap(6);
        btnShortcutNotizen.setName("btnShortcutNotizen"); // NOI18N
        btnShortcutNotizen.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutNotizen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutNotizenActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutNotizen);

        btnShortcutSparte.setIcon(resourceMap.getIcon("btnShortcutSparte.icon")); // NOI18N
        btnShortcutSparte.setMnemonic('S');
        btnShortcutSparte.setText(resourceMap.getString("btnShortcutSparte.text")); // NOI18N
        btnShortcutSparte.setIconTextGap(6);
        btnShortcutSparte.setName("btnShortcutSparte"); // NOI18N
        btnShortcutSparte.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutSparte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutSparteActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutSparte);

        btnShortcutTextbaustein.setIcon(resourceMap.getIcon("btnShortcutTextbaustein.icon")); // NOI18N
        btnShortcutTextbaustein.setMnemonic('T');
        btnShortcutTextbaustein.setText(resourceMap.getString("btnShortcutTextbaustein.text")); // NOI18N
        btnShortcutTextbaustein.setIconTextGap(6);
        btnShortcutTextbaustein.setName("btnShortcutTextbaustein"); // NOI18N
        btnShortcutTextbaustein.setPreferredSize(new java.awt.Dimension(100, 90));
        btnShortcutTextbaustein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShortcutTextbausteinActionPerformed(evt);
            }
        });
        bbarextras.add(btnShortcutTextbaustein);

        jScrollPane2.setViewportView(bbarextras);

        javax.swing.GroupLayout panel_extrasLayout = new javax.swing.GroupLayout(panel_extras);
        panel_extras.setLayout(panel_extrasLayout);
        panel_extrasLayout.setHorizontalGroup(
            panel_extrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
        );
        panel_extrasLayout.setVerticalGroup(
            panel_extrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
        );

        jOutlookBar1.addTab(resourceMap.getString("panel_extras.TabConstraints.tabTitle"), panel_extras); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        bbarsonstiges.setName("bbarsonstiges"); // NOI18N

        btnireport.setIcon(resourceMap.getIcon("btnireport.icon")); // NOI18N
        btnireport.setMnemonic('R');
        btnireport.setText(resourceMap.getString("btnireport.text")); // NOI18N
        btnireport.setIconTextGap(6);
        btnireport.setName("btnireport"); // NOI18N
        btnireport.setPreferredSize(new java.awt.Dimension(100, 90));
        btnireport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnireportActionPerformed(evt);
            }
        });
        bbarsonstiges.add(btnireport);

        btnMaklerPoint.setIcon(resourceMap.getIcon("btnMaklerPoint.icon")); // NOI18N
        btnMaklerPoint.setMnemonic('M');
        btnMaklerPoint.setText(resourceMap.getString("btnMaklerPoint.text")); // NOI18N
        btnMaklerPoint.setIconTextGap(6);
        btnMaklerPoint.setName("btnMaklerPoint"); // NOI18N
        btnMaklerPoint.setPreferredSize(new java.awt.Dimension(100, 90));
        btnMaklerPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaklerPointActionPerformed(evt);
            }
        });
        bbarsonstiges.add(btnMaklerPoint);

        btnVerlauf.setIcon(resourceMap.getIcon("btnVerlauf.icon")); // NOI18N
        btnVerlauf.setMnemonic('V');
        btnVerlauf.setText(resourceMap.getString("btnVerlauf.text")); // NOI18N
        btnVerlauf.setIconTextGap(6);
        btnVerlauf.setName("btnVerlauf"); // NOI18N
        btnVerlauf.setPreferredSize(new java.awt.Dimension(100, 90));
        bbarsonstiges.add(btnVerlauf);

        jScrollPane3.setViewportView(bbarsonstiges);

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
        );

        jOutlookBar1.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jOutlookBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jOutlookBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnShortcutStartseiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutStartseiteActionPerformed
        CRMView.getInstance().addToolStartseite();
    }//GEN-LAST:event_btnShortcutStartseiteActionPerformed

    private void btnShortcutTermineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutTermineActionPerformed
        CRMView.getInstance().addToolKalender();
    }//GEN-LAST:event_btnShortcutTermineActionPerformed

    private void btnShortcutPrivatkundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutPrivatkundeActionPerformed
        CRMView.getInstance().addToolPK();
    }//GEN-LAST:event_btnShortcutPrivatkundeActionPerformed

    private void btnShortcutGeschaeftskundenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutGeschaeftskundenActionPerformed
        CRMView.getInstance().addToolGK();
    }//GEN-LAST:event_btnShortcutGeschaeftskundenActionPerformed

    private void btnShortcutVersicherungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutVersicherungenActionPerformed
        CRMView.getInstance().addToolGesellschaften();
    }//GEN-LAST:event_btnShortcutVersicherungenActionPerformed

    private void btnShortcutProdukteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutProdukteActionPerformed
       CRMView.getInstance().addToolProdukte();
    }//GEN-LAST:event_btnShortcutProdukteActionPerformed

    private void btnShortcutVertraegeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutVertraegeActionPerformed
       CRMView.getInstance().addToolVertr();
    }//GEN-LAST:event_btnShortcutVertraegeActionPerformed

    private void btnShortcutStoerfaelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutStoerfaelleActionPerformed
       CRMView.getInstance().addToolStoer();
    }//GEN-LAST:event_btnShortcutStoerfaelleActionPerformed

    private void btnShortcutSchaedenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutSchaedenActionPerformed
        CRMView.getInstance().addToolSchaden();
    }//GEN-LAST:event_btnShortcutSchaedenActionPerformed

    private void btnShortcutBenutzerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutBenutzerActionPerformed
        CRMView.getInstance().addToolBenutzer();
    }//GEN-LAST:event_btnShortcutBenutzerActionPerformed

    private void btnireportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnireportActionPerformed
        iReport.runIREPORT();
    }//GEN-LAST:event_btnireportActionPerformed

    private void btnMaklerPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaklerPointActionPerformed
        try {
            desktop.browse(new URI("http://www.maklerpoint.de"));
        } catch (Exception ex) {
            Log.logger.fatal("Fehler: Konnte MaklerPoint Seite nicht im Browser öffnen", ex);
            ShowException.showException("Der Browser konnte nicht für die Anzeige der MaklerPoint Seite geöffnet werden. "
                    + "Gehen Sie bitte manuell auf http://www.maklerpoint.de",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Browser nicht öffnen");

        }
    }//GEN-LAST:event_btnMaklerPointActionPerformed

    private void btnShortcutWaehrungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutWaehrungActionPerformed
        CRMView.getInstance().openWaehrungenDialog();
    }//GEN-LAST:event_btnShortcutWaehrungActionPerformed

    private void btnShortcutTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutTagsActionPerformed
        CRMView.getInstance().openTagsDialog();
    }//GEN-LAST:event_btnShortcutTagsActionPerformed

    private void btnShortcutSicherungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutSicherungActionPerformed
        CRMView.getInstance().openSicherungDialog();
    }//GEN-LAST:event_btnShortcutSicherungActionPerformed

    private void btnShortcutNotizenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutNotizenActionPerformed
        NotizenDialogHelper.openNotizen();
    }//GEN-LAST:event_btnShortcutNotizenActionPerformed

    private void btnShortcutSparteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutSparteActionPerformed
        CRMView.getInstance().openSparteDialog();
    }//GEN-LAST:event_btnShortcutSparteActionPerformed

    private void btnShortcutTextbausteinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShortcutTextbausteinActionPerformed
        TextbausteinDialogHelper.openTb();
    }//GEN-LAST:event_btnShortcutTextbausteinActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.l2fprod.common.swing.JButtonBar bbaransichten;
    private com.l2fprod.common.swing.JButtonBar bbarextras;
    private com.l2fprod.common.swing.JButtonBar bbarsonstiges;
    private javax.swing.JButton btnMaklerPoint;
    private javax.swing.JButton btnShortcutBenutzer;
    private javax.swing.JButton btnShortcutGeschaeftskunden;
    private javax.swing.JButton btnShortcutNotizen;
    private javax.swing.JButton btnShortcutPrivatkunde;
    private javax.swing.JButton btnShortcutProdukte;
    private javax.swing.JButton btnShortcutSchaeden;
    private javax.swing.JButton btnShortcutSicherung;
    private javax.swing.JButton btnShortcutSparte;
    private javax.swing.JButton btnShortcutStartseite;
    private javax.swing.JButton btnShortcutStoerfaelle;
    private javax.swing.JButton btnShortcutTags;
    private javax.swing.JButton btnShortcutTermine;
    private javax.swing.JButton btnShortcutTextbaustein;
    private javax.swing.JButton btnShortcutVersicherungen;
    private javax.swing.JButton btnShortcutVertraege;
    private javax.swing.JButton btnShortcutWaehrung;
    private javax.swing.JButton btnVerlauf;
    private javax.swing.JButton btnireport;
    private com.l2fprod.common.swing.JOutlookBar jOutlookBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panel_ansichten;
    private javax.swing.JPanel panel_extras;
    private javax.swing.JPanel panel_sonstiges;
    // End of variables declaration//GEN-END:variables

}