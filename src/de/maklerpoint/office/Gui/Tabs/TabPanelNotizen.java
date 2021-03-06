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
 * TabNotizenPanel.java
 *
 * Created on 27.08.2010, 14:46:27
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Notizen.NotizenObj;
import de.maklerpoint.office.Notizen.Tools.NotizenSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author yves
 */
public class TabPanelNotizen extends javax.swing.JPanel implements iTabs {

    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private static final int PRODUKTE = 4;
    private static final int VERTRAG = 5;
    public static final int STOERFALL = 6;
    public static final int SCHADEN = 7;
    
    private int type = -1;
    private NotizenObj currentNotiz = null;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private ProduktObj prod = null;
    private VertragObj vtr = null;
    private StoerfallObj stoer = null;
    private SchadenObj schaden = null;
    
    private String kennung = null;
    private boolean loaded = false;
    private boolean enabled = true;

    /** Creates new form TabNotizenPanel */
    public TabPanelNotizen() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Notizen";
    }

    public void load(KundenObj kunde) {
        this.kunde = kunde;
        this.type = PRIVAT;
        setUp(-1);
    }

    public void load(FirmenObj firma) {
        this.firma = firma;
        this.type = FIRMA;
        setUp(-1);
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.vers = versicherer;
        setUp(-1);
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        setUp(-1);
    }

    public void load(ProduktObj prod) {
        this.type = PRODUKTE;
        this.prod = prod;
        setUp(-1);
    }

    public void load(ProduktObj prod, VersichererObj vers) {
        this.type = PRODUKTE;
        this.prod = prod;
        setUp(-1);
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vtr = vertrag;
        setUp(-1);
    }
    
     public void load(StoerfallObj stoerfall) {
        this.type = STOERFALL;
        this.stoer = stoerfall;
        setUp(-1);
    }

    public void load(SchadenObj schaden) {
        this.type = SCHADEN;
        this.schaden = schaden;
        setUp(-1);
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Notizen Ansicht");
        //dropDownButton.setText();
        this.jToolBar1.add(dropDownButton);
    }

    public void disableElements() {
        enabled = false;

        this.combo_notizen.setEnabled(false);
//        combo_notizen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Notizen" }));

        this.editor_notizen.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnSave.setEnabled(false);
        this.btnRefresh.setEnabled(false);      
        this.dropDownButton.setEnabled(false);        
    }

    public void enableElements() {
        if (enabled == true) {
            return;
        }

        enabled = true;

        this.btnSave.setEnabled(false);
        this.combo_notizen.setEnabled(true);
        this.editor_notizen.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnNeu.setEnabled(true);        
        this.btnRefresh.setEnabled(true);  
        this.dropDownButton.setEnabled(true);       
    }

    private int getStatus() {
        int status = Status.NORMAL;

        if (this.alleDBMenuItem.isSelected()) {
            return -1;
        } else if (this.aktiveDBMenuItem.isSelected()) {
            return Status.NORMAL;
        } else if (this.archivedDBMenuItem.isSelected()) {
            return Status.ARCHIVED;
        } else if (this.deletedDBMenuItem.isSelected()) {
            return Status.DELETED;
        }

        return status;
    }

    private void setUp(int id) {
        currentNotiz = null;

//        this.editor_notizen.remove(editor_notizen.getComponent(1));

        if (loaded == false) {
            JTabbedPane comp = (JTabbedPane) editor_notizen.getComponent(1);
            comp.remove(1);
            comp.getComponentAt(0).setName("Bearbeiten");
            comp.repaint();
            loaded = true;
        }

        NotizenObj[] nz = null;

        try {
            if (type == PRIVAT) {
                nz = NotizenSQLMethods.loadKundenNotizen(DatabaseConnection.open(), kunde.getKundenNr(), getStatus());
            } else if (type == FIRMA) {
                nz = NotizenSQLMethods.loadKundenNotizen(DatabaseConnection.open(), firma.getKundenNr(), getStatus());
            } else if (type == VERSICHERER) {
                nz = NotizenSQLMethods.loadNotizenVersicherer(DatabaseConnection.open(), vers.getId(), getStatus());
            } else if (type == BENUTZER) {
                nz = NotizenSQLMethods.loadNotizenBenutzer(DatabaseConnection.open(), benutzer.getId(), getStatus());
            } else if (type == PRODUKTE) {
                nz = NotizenSQLMethods.loadNotizenProdukt(DatabaseConnection.open(), prod.getId(), getStatus());
            } else if (type == VERTRAG) {
                nz = NotizenSQLMethods.loadNotizenVertrag(DatabaseConnection.open(), vtr.getId(), getStatus());
            } else if (type == STOERFALL) {
                nz = NotizenSQLMethods.loadNotizenStoerfall(DatabaseConnection.open(), stoer.getId(), getStatus());
            } else if (type == SCHADEN) {
                nz = NotizenSQLMethods.loadNotizenStoerfall(DatabaseConnection.open(), schaden.getId(), getStatus());
            }
            
            
        } catch (Exception e) {
            Log.databaselogger.fatal("Die Notizen konnten nicht geladen werden.", e);
            ShowException.showException("Datenbankfehler: Die Notizen konnte nicht geupdated werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notizen nicht laden");
        }

        if (nz == null) {
            this.editor_notizen.setText("Erstellen Sie eine Notiz mit einem Klick auf Neu");
            this.editor_notizen.setEnabled(false);
            this.combo_notizen.setModel(new DefaultComboBoxModel(new Object[]{"Keine Notizen"}));
            return;
        }

        this.editor_notizen.setEnabled(true);
        this.combo_notizen.setModel(new DefaultComboBoxModel(nz));

        if (id == -2) {
            // NICHTS why? Damit nur das ganze refresh wird und danach mit showNOtiz(geladen wird)
        } else if (id == -1) {
            showNotiz(nz[0]);
        } else {
            NotizenObj nzshow = (NotizenObj) this.combo_notizen.getItemAt(id); // WTF TODO Fix this
            this.combo_notizen.setSelectedIndex(id);
            showNotiz(nzshow);
        }
    }

    private void showNotiz(NotizenObj nz) {
        if (currentNotiz != null) {
            if (!this.editor_notizen.getText().equalsIgnoreCase(currentNotiz.getText())) {
                int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die geänderte Notiz speichern?",
                        "Änderungen speichern", JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    NotizenObj oldtb = currentNotiz;
                    oldtb.setText(editor_notizen.getText());

                    oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                    try {
                        NotizenSQLMethods.updatenotizen(DatabaseConnection.open(), oldtb);
                    } catch (SQLException e) {
                        Log.databaselogger.fatal("Die Notiz mit der id \"" + oldtb.getId() 
                                + "\" konnte nicht geupdated werden.", e);
                        ShowException.showException("Datenbankfehler: Die Notiz mit der id \"" + oldtb.getId() 
                                + "\" konnte nicht geupdated werden",
                                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht updaten");
                    }
                    setUp(-2);
                }
            }
        }

        if (nz.getText() != null) {
            this.editor_notizen.setText(nz.getText());
        }

        currentNotiz = nz;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        combo_notizen = new javax.swing.JComboBox();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        editor_notizen = new net.atlanticbb.tantlinger.shef.HTMLEditorPane();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelNotizen.class);
        alleDBMenuItem.setText(resourceMap.getString("alleDBMenuItem.text")); // NOI18N
        alleDBMenuItem.setToolTipText(resourceMap.getString("alleDBMenuItem.toolTipText")); // NOI18N
        alleDBMenuItem.setName("alleDBMenuItem"); // NOI18N
        alleDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alleDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(alleDBMenuItem);

        grp_dbstatus.add(aktiveDBMenuItem);
        aktiveDBMenuItem.setMnemonic('A');
        aktiveDBMenuItem.setSelected(true);
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setName("aktiveDBMenuItem"); // NOI18N
        aktiveDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktiveDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(aktiveDBMenuItem);

        grp_dbstatus.add(archivedDBMenuItem);
        archivedDBMenuItem.setMnemonic('A');
        archivedDBMenuItem.setText(resourceMap.getString("archivedDBMenuItem.text")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('G');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        setName("Form"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNeu);

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar1.add(jSeparator2);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(68, 15));
        jToolBar1.add(jLabel7);

        combo_notizen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Notizen" }));
        combo_notizen.setName("combo_notizen"); // NOI18N
        combo_notizen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_notizenActionPerformed(evt);
            }
        });
        jToolBar1.add(combo_notizen);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jToolBar1.add(jSeparator6);

        btnRefresh.setIcon(resourceMap.getIcon("btnRefresh.icon")); // NOI18N
        btnRefresh.setToolTipText(resourceMap.getString("btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        editor_notizen.setName("editor_notizen"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
            .addComponent(editor_notizen, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editor_notizen, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Namen für die neue Notiz ein.",
                "Name der neuen Notiz", JOptionPane.INFORMATION_MESSAGE);

        if (name == null) {
            return;
        }

        NotizenObj nz = new NotizenObj();

        nz.setBetreff(name);
        nz.setText("");
        nz.setCreatorId(BasicRegistry.currentUser.getId());

        if (type == PRIVAT) {
            nz.setKundenKennung(kunde.getKundenNr());
        } else if (type == FIRMA) {
            nz.setKundenKennung(firma.getKundenNr());
        } else if (type == VERSICHERER) {
            nz.setVersichererKennung(vers.getId());
        } else if (type == BENUTZER) {
            nz.setBenutzerKennung(benutzer.getId());
        } else if (type == PRODUKTE) {
            nz.setProduktId(prod.getId());
        } else if (type == VERTRAG) {
            nz.setVertragId(vtr.getId());
        }

        nz.setPrivate(true);
        nz.setTag("Standard");

        nz.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        nz.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        nz.setStatus(Status.NORMAL);

        try {
            int id = NotizenSQLMethods.insertIntonotizen(DatabaseConnection.open(), nz);
            nz.setId(id);
            setUp(-2);
            showNotiz(nz);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die neue Notiz  \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.", e);
            ShowException.showException("Datenbankfehler: Die Notiz \"" + nz.getBetreff() + "\" konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht speichern");
        }
//        setUp(-1);
//        this.showNotiz(nz);
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (currentNotiz != null) {
            if (!this.editor_notizen.getText().equalsIgnoreCase(currentNotiz.getText())) {

                NotizenObj oldtb = currentNotiz;
                oldtb.setText(editor_notizen.getText());

                oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                try {
                    NotizenSQLMethods.updatenotizen(DatabaseConnection.open(), oldtb);
                } catch (SQLException e) {
                    Log.databaselogger.fatal("Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden.", e);
                    ShowException.showException("Datenbankfehler: Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden",
                            ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht updaten");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Keine Notiz zum speichern ausgewählt.");
            return;
        }
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (currentNotiz == null) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Notiz \"" + currentNotiz.getBetreff() + "\" wirklich löschen?");

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            NotizenSQLMethods.deleteFromNotizen(DatabaseConnection.open(), currentNotiz.getId());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Notiz  \"" + currentNotiz.getBetreff() + "\" konnte nicht gelöscht werden.", e);
            ShowException.showException("Datenbankfehler: Die Notiz \"" + currentNotiz.getBetreff() + "\" konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht löschen");
        }
//        setUp(-1);
}//GEN-LAST:event_btnDeleteActionPerformed

    private void combo_notizenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_notizenActionPerformed
        if (combo_notizen.getSelectedIndex() == -1) {
            return;
        }

        if (combo_notizen.getSelectedItem().toString().equalsIgnoreCase("keine notizen")) {
            return;
        }

        if (currentNotiz != null) {
            if (!this.editor_notizen.getText().equalsIgnoreCase(currentNotiz.getText())) {

                NotizenObj oldtb = currentNotiz;
                oldtb.setText(editor_notizen.getText());

                oldtb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                try {
                    NotizenSQLMethods.updatenotizen(DatabaseConnection.open(), oldtb);
                } catch (SQLException e) {
                    Log.databaselogger.fatal("Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden.", e);
                    ShowException.showException("Datenbankfehler: Die Notiz mit der id \"" + oldtb.getId() + "\" konnte nicht geupdated werden",
                            ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Notiz nicht updaten");
                }
            }
        }

        NotizenObj selnotiz = (NotizenObj) combo_notizen.getSelectedItem();

        currentNotiz = selnotiz;
        showNotiz(selnotiz);
    }//GEN-LAST:event_combo_notizenActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        setUp(-1);
}//GEN-LAST:event_btnRefreshActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void archivedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_archivedDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_deletedDBMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox combo_notizen;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private net.atlanticbb.tantlinger.shef.HTMLEditorPane editor_notizen;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu popupDBStatus;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
}