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
 * ConfigurationDialog.java
 *
 * Created on Jul 13, 2010, 3:27:41 PM
 */

package de.maklerpoint.office.Gui.Configuration;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Tools.FileTools;
import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.prefs.Preferences;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ConfigurationDialog extends javax.swing.JDialog {

    private int activePan = -1;

    /** Creates new form ConfigurationDialog */
    public ConfigurationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        activePan = -1;
        initComponents();
        loadPanels();
    }

    public ConfigurationDialog(java.awt.Frame parent, boolean modal, int activePanel) {
        super(parent, modal);
        this.activePan = activePanel;
        initComponents();
        loadPanels();
    }


    private void loadPanels() {
        AddConfigurationPanels add = new AddConfigurationPanels();
        try {
            add.load(this);
            Log.logger.debug("Laden der Konfiguration beendet.");
            setActive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6

    private void setActive() {
        if (this.btnDatabase.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_dbholder, BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(true);
//            this.scroll_briefholder.setVisible(false);
//            this.scroll_kalholder.setVisible(false);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(false);
//            this.scroll_schnittholder.setVisible(false);
        } else if(this.btnBriefe.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_briefholder, BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(false);
//            this.scroll_briefholder.setVisible(true);
//            this.scroll_kalholder.setVisible(false);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(false);
//            this.scroll_schnittholder.setVisible(false);
        } else if(this.btnDruck.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_printholder,  BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(false);
//            this.scroll_briefholder.setVisible(false);
//            this.scroll_kalholder.setVisible(false);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(true);
//            this.scroll_schnittholder.setVisible(false);
        } else if(this.btnSchnitstellen.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_schnittholder,  BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(false);
//            this.scroll_briefholder.setVisible(false);
//            this.scroll_kalholder.setVisible(false);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(false);
//            this.scroll_schnittholder.setVisible(true);
        } else if(this.btnKalender.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_kalholder,  BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(false);
//            this.scroll_briefholder.setVisible(false);
//            this.scroll_kalholder.setVisible(true);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(false);
//            this.scroll_schnittholder.setVisible(false);
        } else if(this.btnVerschiedenes.isSelected()) {
            this.panel_tophold.removeAll();
            this.panel_tophold.add(scroll_mischolder,  BorderLayout.CENTER);
//            this.scroll_dbholder.setVisible(false);
//            this.scroll_briefholder.setVisible(false);
//            this.scroll_kalholder.setVisible(false);
//            this.scroll_mischolder.setVisible(false);
//            this.scroll_printholder.setVisible(false);
//            this.scroll_schnittholder.setVisible(true);
        } else {
            System.out.println("WTF");
        }

        this.panel_tophold.revalidate();
        this.panel_tophold.repaint();
        this.repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpHeadbtn = new javax.swing.ButtonGroup();
        toolbarSettings = new javax.swing.JToolBar();
        btnDatabase = new javax.swing.JToggleButton();
        btnBriefe = new javax.swing.JToggleButton();
        btnSchnitstellen = new javax.swing.JToggleButton();
        btnKalender = new javax.swing.JToggleButton();
        btnDruck = new javax.swing.JToggleButton();
        btnVerschiedenes = new javax.swing.JToggleButton();
        panel_tophold = new javax.swing.JPanel();
        scroll_dbholder = new javax.swing.JScrollPane();
        pane_db = new javax.swing.JTabbedPane();
        scroll_briefholder = new javax.swing.JScrollPane();
        pane_brief = new javax.swing.JTabbedPane();
        scroll_schnittholder = new javax.swing.JScrollPane();
        pane_schnitt = new javax.swing.JTabbedPane();
        scroll_kalholder = new javax.swing.JScrollPane();
        pane_kal = new javax.swing.JTabbedPane();
        scroll_printholder = new javax.swing.JScrollPane();
        pane_print = new javax.swing.JTabbedPane();
        scroll_mischolder = new javax.swing.JScrollPane();
        pane_misc = new javax.swing.JTabbedPane();
        panel_controls = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(ConfigurationDialog.class);
        setTitle(resourceMap.getString("settingsDialog.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(700, 600));
        setName("settingsDialog"); // NOI18N
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        toolbarSettings.setBackground(resourceMap.getColor("toolbarSettings.background")); // NOI18N
        toolbarSettings.setFloatable(false);
        toolbarSettings.setRollover(true);
        toolbarSettings.setMaximumSize(new java.awt.Dimension(1500, 97));
        toolbarSettings.setName("toolbarSettings"); // NOI18N

        grpHeadbtn.add(btnDatabase);
        btnDatabase.setIcon(resourceMap.getIcon("btnDatabase.icon")); // NOI18N
        btnDatabase.setMnemonic('D');
        btnDatabase.setSelected(true);
        btnDatabase.setText(resourceMap.getString("btnDatabase.text")); // NOI18N
        btnDatabase.setFocusable(false);
        btnDatabase.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDatabase.setMinimumSize(new java.awt.Dimension(81, 95));
        btnDatabase.setName("btnDatabase"); // NOI18N
        btnDatabase.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatabaseActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnDatabase);

        grpHeadbtn.add(btnBriefe);
        btnBriefe.setIcon(resourceMap.getIcon("btnBriefe.icon")); // NOI18N
        btnBriefe.setMnemonic('B');
        btnBriefe.setText(resourceMap.getString("btnBriefe.text")); // NOI18N
        btnBriefe.setFocusable(false);
        btnBriefe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBriefe.setName("btnBriefe"); // NOI18N
        btnBriefe.setPreferredSize(new java.awt.Dimension(105, 95));
        btnBriefe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBriefe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBriefeActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnBriefe);

        grpHeadbtn.add(btnSchnitstellen);
        btnSchnitstellen.setIcon(resourceMap.getIcon("btnSchnitstellen.icon")); // NOI18N
        btnSchnitstellen.setMnemonic('S');
        btnSchnitstellen.setText(resourceMap.getString("btnSchnitstellen.text")); // NOI18N
        btnSchnitstellen.setFocusable(false);
        btnSchnitstellen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSchnitstellen.setName("btnSchnitstellen"); // NOI18N
        btnSchnitstellen.setPreferredSize(new java.awt.Dimension(105, 95));
        btnSchnitstellen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSchnitstellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSchnitstellenActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnSchnitstellen);

        grpHeadbtn.add(btnKalender);
        btnKalender.setIcon(resourceMap.getIcon("btnKalender.icon")); // NOI18N
        btnKalender.setMnemonic('K');
        btnKalender.setText(resourceMap.getString("btnKalender.text")); // NOI18N
        btnKalender.setFocusable(false);
        btnKalender.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKalender.setName("btnKalender"); // NOI18N
        btnKalender.setPreferredSize(new java.awt.Dimension(105, 95));
        btnKalender.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKalender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKalenderActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnKalender);

        grpHeadbtn.add(btnDruck);
        btnDruck.setIcon(resourceMap.getIcon("btnDruck.icon")); // NOI18N
        btnDruck.setMnemonic('D');
        btnDruck.setText(resourceMap.getString("btnDruck.text")); // NOI18N
        btnDruck.setFocusable(false);
        btnDruck.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDruck.setName("btnDruck"); // NOI18N
        btnDruck.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDruck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDruckActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnDruck);

        grpHeadbtn.add(btnVerschiedenes);
        btnVerschiedenes.setIcon(resourceMap.getIcon("btnVerschiedenes.icon")); // NOI18N
        btnVerschiedenes.setMnemonic('V');
        btnVerschiedenes.setText(resourceMap.getString("btnVerschiedenes.text")); // NOI18N
        btnVerschiedenes.setFocusable(false);
        btnVerschiedenes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerschiedenes.setName("btnVerschiedenes"); // NOI18N
        btnVerschiedenes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerschiedenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerschiedenesActionPerformed(evt);
            }
        });
        toolbarSettings.add(btnVerschiedenes);

        getContentPane().add(toolbarSettings);

        panel_tophold.setMinimumSize(new java.awt.Dimension(400, 300));
        panel_tophold.setName("panel_tophold"); // NOI18N
        panel_tophold.setPreferredSize(new java.awt.Dimension(400, 300));
        panel_tophold.setLayout(new java.awt.BorderLayout());

        scroll_dbholder.setName("scroll_dbholder"); // NOI18N

        pane_db.setName("pane_db"); // NOI18N
        scroll_dbholder.setViewportView(pane_db);

        panel_tophold.add(scroll_dbholder, java.awt.BorderLayout.CENTER);

        scroll_briefholder.setName("scroll_briefholder"); // NOI18N

        pane_brief.setName("pane_brief"); // NOI18N
        scroll_briefholder.setViewportView(pane_brief);

        panel_tophold.add(scroll_briefholder, java.awt.BorderLayout.CENTER);

        scroll_schnittholder.setName("scroll_schnittholder"); // NOI18N

        pane_schnitt.setName("pane_schnitt"); // NOI18N
        scroll_schnittholder.setViewportView(pane_schnitt);

        panel_tophold.add(scroll_schnittholder, java.awt.BorderLayout.CENTER);

        scroll_kalholder.setName("scroll_kalholder"); // NOI18N

        pane_kal.setName("pane_kal"); // NOI18N
        scroll_kalholder.setViewportView(pane_kal);

        panel_tophold.add(scroll_kalholder, java.awt.BorderLayout.CENTER);

        scroll_printholder.setName("scroll_printholder"); // NOI18N

        pane_print.setName("pane_print"); // NOI18N
        scroll_printholder.setViewportView(pane_print);

        panel_tophold.add(scroll_printholder, java.awt.BorderLayout.CENTER);

        scroll_mischolder.setName("scroll_mischolder"); // NOI18N

        pane_misc.setName("pane_misc"); // NOI18N
        scroll_mischolder.setViewportView(pane_misc);

        panel_tophold.add(scroll_mischolder, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_tophold);

        panel_controls.setMaximumSize(new java.awt.Dimension(1500, 60));
        panel_controls.setMinimumSize(new java.awt.Dimension(400, 60));
        panel_controls.setName("panel_controls"); // NOI18N
        panel_controls.setPreferredSize(new java.awt.Dimension(954, 60));

        btnOk.setIcon(resourceMap.getIcon("btnOk.icon")); // NOI18N
        btnOk.setMnemonic('S');
        btnOk.setText(resourceMap.getString("btnOk.text")); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnHelp.setIcon(resourceMap.getIcon("btnHelp.icon")); // NOI18N
        btnHelp.setMnemonic('H');
        btnHelp.setText(resourceMap.getString("btnHelp.text")); // NOI18N
        btnHelp.setName("btnHelp"); // NOI18N

        btnExport.setIcon(resourceMap.getIcon("btnExport.icon")); // NOI18N
        btnExport.setMnemonic('E');
        btnExport.setText(resourceMap.getString("btnExport.text")); // NOI18N
        btnExport.setName("btnExport"); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setIcon(resourceMap.getIcon("btnImport.icon")); // NOI18N
        btnImport.setMnemonic('I');
        btnImport.setText(resourceMap.getString("btnImport.text")); // NOI18N
        btnImport.setName("btnImport"); // NOI18N
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_controlsLayout = new javax.swing.GroupLayout(panel_controls);
        panel_controls.setLayout(panel_controlsLayout);
        panel_controlsLayout.setHorizontalGroup(
            panel_controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_controlsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_controlsLayout.setVerticalGroup(
            panel_controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_controlsLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panel_controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel)
                    .addComponent(btnHelp)
                    .addComponent(btnExport)
                    .addComponent(btnImport))
                .addContainerGap())
        );

        getContentPane().add(panel_controls);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatabaseActionPerformed
        setActive();
    }//GEN-LAST:event_btnDatabaseActionPerformed

    private void btnBriefeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBriefeActionPerformed
        setActive();
    }//GEN-LAST:event_btnBriefeActionPerformed

    private void btnSchnitstellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSchnitstellenActionPerformed
        setActive();
    }//GEN-LAST:event_btnSchnitstellenActionPerformed

    private void btnKalenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKalenderActionPerformed
        setActive();
    }//GEN-LAST:event_btnKalenderActionPerformed

    private void btnDruckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDruckActionPerformed
        setActive();
    }//GEN-LAST:event_btnDruckActionPerformed

    private void btnVerschiedenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerschiedenesActionPerformed
        setActive();
    }//GEN-LAST:event_btnVerschiedenesActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

        
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        String expfile = FileTools.saveFile("Einstellungen exportieren", ".xml");
        try {
            FileOutputStream fos = new FileOutputStream(expfile);
            Config.getPreferences().exportSubtree(fos);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }                
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        String imfile = FileTools.openFile("Einstellungen importieren");
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(imfile));
            Preferences.importPreferences(is);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }                
    }//GEN-LAST:event_btnImportActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigurationDialog dialog = new ConfigurationDialog(new javax.swing.JFrame(), true);
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
    public javax.swing.JToggleButton btnBriefe;
    public javax.swing.JButton btnCancel;
    public javax.swing.JToggleButton btnDatabase;
    public javax.swing.JToggleButton btnDruck;
    public javax.swing.JButton btnExport;
    public javax.swing.JButton btnHelp;
    public javax.swing.JButton btnImport;
    public javax.swing.JToggleButton btnKalender;
    public javax.swing.JButton btnOk;
    public javax.swing.JToggleButton btnSchnitstellen;
    public javax.swing.JToggleButton btnVerschiedenes;
    public javax.swing.ButtonGroup grpHeadbtn;
    public javax.swing.JTabbedPane pane_brief;
    public javax.swing.JTabbedPane pane_db;
    public javax.swing.JTabbedPane pane_kal;
    public javax.swing.JTabbedPane pane_misc;
    public javax.swing.JTabbedPane pane_print;
    public javax.swing.JTabbedPane pane_schnitt;
    public javax.swing.JPanel panel_controls;
    public javax.swing.JPanel panel_tophold;
    public javax.swing.JScrollPane scroll_briefholder;
    public javax.swing.JScrollPane scroll_dbholder;
    public javax.swing.JScrollPane scroll_kalholder;
    public javax.swing.JScrollPane scroll_mischolder;
    public javax.swing.JScrollPane scroll_printholder;
    public javax.swing.JScrollPane scroll_schnittholder;
    public javax.swing.JToolBar toolbarSettings;
    // End of variables declaration//GEN-END:variables

}
