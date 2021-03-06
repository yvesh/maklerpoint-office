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
 * TabPanelBeratungsdokumentation.java
 *
 * Created on 31.08.2010, 10:47:05
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
import de.maklerpoint.office.Beratungsprotokoll.Tools.BeratungsprotokollSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Beratungsprotokoll.BeratungsprotokollHelper;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.TermineModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author yves
 */
public class TabPanelBeratungsdokumentation extends javax.swing.JPanel implements iTabs {

    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private static final int VERTRAG = 4;
    private static final int PRODUKT = 5;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private ProduktObj produkt = null;
    private VertragObj vertrag = null;
    private JButton dropDownButton;
    private String kennung = null;
    private String[] kundenColumn = new String[]{"Hidden", "Vertrag", "Produkt", "Kundenwunsch",
        "Versicherungs-Sparte", "Erstellt am", "Benutzer"};
    private String[] nonkundenColumn = new String[]{"Hidden", "Kundennr.", "Vertrag", "Produkt", "Kundenwunsch",
        "Versicherungs-Sparte", "Erstellt am", "Benutzer",};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private boolean enabled = true;

    /** Creates new form TabPanelBeratungsdokumentation */
    public TabPanelBeratungsdokumentation() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Beratungsprotokolle";
    }

    public void load(KundenObj kunde) {
        this.type = PRIVAT;
        this.kunde = kunde;
        this.kennung = kunde.getKundenNr();
        Log.logger.debug("Lade Beratungsprotokolle für Kunden " + kunde.getKundenNr());
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.type = FIRMA;
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        Log.logger.debug("Lade Beratungsprotokolle für Firma " + firma.getKundenNr());
        loadTable();
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.vers = versicherer;
        Log.logger.debug("Lade Beratungsprotokolle für Versicherer " + vers.getName());
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        Log.logger.debug("Lade Beratungsprotokolle für den Benutzer " + benutzer);
        loadTable();
    }

    public void load(ProduktObj produkt) {
        this.type = PRODUKT;
        this.produkt = produkt;
        Log.logger.debug("Lade Beratungsprotokolle für das Produkt " + produkt);
        loadTable();
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vertrag = vertrag;
        Log.logger.debug("Lade Beratungsprotokolle für den Vertrag " + vertrag);
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Beratungsdokumentation Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
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

    public void disableElements() {
        enabled = false;

        setTable(null, kundenColumn);

        this.table_protokolle.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnArchive.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
    }

    public void enableElements() {
        if (enabled == true) {
            return;
        }

        enabled = true;

        this.table_protokolle.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnArchive.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            BeratungsprotokollObj[] bps = null;

            if (type == PRIVAT || type == FIRMA) {
                bps = BeratungsprotokollSQLMethods.loadBeratungsprotokolle(DatabaseConnection.open(),
                        kennung, getStatus());
            } else if (type == VERSICHERER) {
                bps = BeratungsprotokollSQLMethods.loadBeratungsprotokolleVersicherer(DatabaseConnection.open(),
                        vers.getId(), getStatus());
            } else if (type == BENUTZER) {
                bps = BeratungsprotokollSQLMethods.loadBeratungsprotokolleBenutzer(DatabaseConnection.open(),
                        benutzer.getId(), getStatus());
            } else if (type == PRODUKT) {
                bps = BeratungsprotokollSQLMethods.loadBeratungsprotokolleProdukt(DatabaseConnection.open(),
                        produkt.getId(), getStatus());
            } else if (type == VERTRAG) {
                bps = BeratungsprotokollSQLMethods.loadBeratungsprotokolleVertrag(DatabaseConnection.open(),
                        vertrag.getId(), getStatus());
            }

            if (bps != null) {
                if (type == PRIVAT || type == FIRMA) {
                    data = new Object[bps.length][7];

                    for (int i = 0; i < bps.length; i++) {
                        data[i][0] = bps[i];

                        if (bps[i].getVertragId() != -1) {
                            data[i][1] = VertragRegistry.getVertrag(bps[i].getVertragId());
                        } else {
                            data[i][1] = "-";
                        }

                        if (bps[i].getProduktId() != -1) {
                            data[i][2] = VersicherungsRegistry.getProdukt(bps[i].getVertragId());
                        } else {
                            data[i][2] = "-";
                        }

                        if (bps[i].getKundenWuensche() != null) {
                            data[i][3] = bps[i].getKundenWuensche();
                        } else {
                            data[i][3] = "-";
                        }

                        data[i][4] = bps[i].getVersicherungsSparte();

                        data[i][5] = df.format(bps[i].getCreated());

                        data[i][6] = BenutzerRegistry.getBenutzer(bps[i].getBenutzerId(), true);
                    }
                } else {
                    data = new Object[bps.length][8];

                    for (int i = 0; i < bps.length; i++) {
                        data[i][0] = bps[i];
                        data[i][1] = bps[i].getKundenKennung();

                        if (bps[i].getVertragId() != -1) {
                            data[i][2] = VertragRegistry.getVertrag(bps[i].getVertragId());
                        } else {
                            data[i][2] = "-";
                        }

                        if (bps[i].getProduktId() != -1) {
                            data[i][3] = VersicherungsRegistry.getProdukt(bps[i].getVertragId());
                        } else {
                            data[i][3] = "-";
                        }

                        if (bps[i].getKundenWuensche() != null) {
                            data[i][4] = bps[i].getKundenWuensche();
                        } else {
                            data[i][4] = "-";
                        }

                        data[i][5] = bps[i].getVersicherungsSparte();
                        data[i][6] = df.format(bps[i].getCreated());
                        data[i][7] = BenutzerRegistry.getBenutzer(bps[i].getBenutzerId(), true);
                    }
                }
            }

            if (type == PRIVAT || type == FIRMA) {
                setTable(data, kundenColumn);
            } else {
                setTable(data, nonkundenColumn);
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Beratungsprotkolle nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Beratungsprotokolle konnten nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Beratungsprotokolle nicht laden");
        }
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {

        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_protokolle.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_protokolle.getModel();
            atm.setData(data);
            table_protokolle.packAll();
            return;
        }

        table_protokolle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_protokolle.setColumnSelectionAllowed(false);
        table_protokolle.setCellSelectionEnabled(false);
        table_protokolle.setRowSelectionAllowed(true);
        table_protokolle.setAutoCreateRowSorter(true);

        table_protokolle.setFillsViewportHeight(true);
        table_protokolle.removeColumn(table_protokolle.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_protokolle.addMouseListener(popupListener);
        table_protokolle.setColumnControlVisible(true);
        table_protokolle.packAll();

        JTableHeader header = table_protokolle.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();
    }

    class TablePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point point = e.getPoint();
                int row = table_protokolle.rowAtPoint(point);
                table_protokolle.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void searchTable() {
        int result = table_protokolle.getSearchable().search(field_search.getText());
    }

    private void neuBerat() {
        if (type == PRIVAT) {
            BeratungsprotokollHelper.open(kunde);
        } else if (type == FIRMA) {
            BeratungsprotokollHelper.open(firma);
        }

        this.loadTable();
    }

    private void deleteBerat() {
        int row = table_protokolle.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Beratungsprotkoll ausgewählt.");
            return;
        }

        BeratungsprotokollObj bp = (BeratungsprotokollObj) table_protokolle.getModel().getValueAt(row, 0);

        if (bp == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Beratungsprokoll wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            BeratungsprotokollSQLMethods.deleteFromberatungsprotokolle(DatabaseConnection.open(), bp.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das Beratungsprotkoll nicht löschen", e);
            ShowException.showException("Datenbankfehler: Das Beratungsprotokoll konnte nicht aus der Datenbank gelöscht werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Beratungsprotokoll nicht löschen");
        }

        this.loadTable();
    }

    private void archiveBerat() {
        int row = table_protokolle.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Beratungsprotokoll aus.");
            return;
        }

        BeratungsprotokollObj bp = (BeratungsprotokollObj) table_protokolle.getModel().getValueAt(row, 0);

        if (bp == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie das Beratungsprokoll wirklich archivieren?",
                    "Wirklich archivieren?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            BeratungsprotokollSQLMethods.archiveFromBeratungsprotokolle(DatabaseConnection.open(), bp);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte das Beratungsprokoll nicht archivieren", e);
            ShowException.showException("Datenbankfehler: Das Beratungsprokoll konnte nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Beratungsprokoll nicht archivieren");
        }

        this.loadTable();
    }

    private void editBerat() {
        // TODO Create
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
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_protokolle = new org.jdesktop.swingx.JXTable();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelBeratungsdokumentation.class);
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
        aktiveDBMenuItem.setToolTipText(resourceMap.getString("aktiveDBMenuItem.toolTipText")); // NOI18N
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
        archivedDBMenuItem.setToolTipText(resourceMap.getString("archivedDBMenuItem.toolTipText")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('A');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setToolTipText(resourceMap.getString("deletedDBMenuItem.toolTipText")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        newMenuItem.setMnemonic('N');
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

        editMenuItem.setMnemonic('b');
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(editMenuItem);

        archiveMenuItem.setText(resourceMap.getString("archiveMenuItem.text")); // NOI18N
        archiveMenuItem.setName("archiveMenuItem"); // NOI18N
        archiveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(archiveMenuItem);

        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(deleteMenuItem);

        refreshMenuItem.setText(resourceMap.getString("refreshMenuItem.text")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(refreshMenuItem);

        setName("Form"); // NOI18N

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_protokolle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_protokolle.setName("table_protokolle"); // NOI18N
        table_protokolle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_protokolleMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_protokolle);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

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
        toolbar.add(btnNeu);

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setText(resourceMap.getString("btnArchive.text")); // NOI18N
        btnArchive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnArchive.setFocusable(false);
        btnArchive.setName("btnArchive"); // NOI18N
        btnArchive.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        toolbar.add(btnArchive);

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
        toolbar.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbar.add(jSeparator2);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        toolbar.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        toolbar.add(field_search);

        btnSearch.setIcon(resourceMap.getIcon("btnSearch.icon")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        toolbar.add(btnSearch);

        jSeparator5.setName("jSeparator5"); // NOI18N
        toolbar.add(jSeparator5);

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
        toolbar.add(btnRefresh);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        neuBerat();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteBerat();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveBerat();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void archivedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_archivedDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_deletedDBMenuItemActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        neuBerat();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editBerat();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveBerat();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteBerat();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    private void table_protokolleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_protokolleMouseClicked
        if (evt.getClickCount() > 1) {
            editBerat();
        }
    }//GEN-LAST:event_table_protokolleMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JMenuItem archiveMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_protokolle;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
}
