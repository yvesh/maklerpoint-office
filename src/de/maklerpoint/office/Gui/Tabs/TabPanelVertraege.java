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
 * TabPanelVertraege.java
 *
 * Created on 31.08.2010, 10:47:48
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.Vertraege;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.VertraegeModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.Tools.VertraegeSQLMethods;
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
public class TabPanelVertraege extends javax.swing.JPanel implements iTabs {

    public static final int KUNDE = 0;
    public static final int GESCHAEFT = 1;
    public static final int VERSICHERER = 2;
    public static final int BENUTZER = 3;
    public static final int PRODUKT = 4;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private ProduktObj produkt = null;
    private String kdnr = null;
    private String[] kundenColumn = {"Hidden", "Versicherer", "Produkt", "Benutzer", "Policennummer", "Jahres Brutto", "Status"};
    private String[] firmenColumn = {"Hidden", "Versicherer", "Produkt", "Benutzer", "Policennummer", "Jahres Brutto", "Status"};
    private String[] benutzerColumn = {"Hidden", "Kundenummer", "Versicherer", "Produkt", "Policennummer", "Jahres Brutto", "Status"};
    private String[] versichererColumn = {"Hidden", "Kundennummer", "Produkt", "Benutzer", "Policennummer", "Jahres Brutto", "Status"};
    private String[] produktColumn = {"Hidden", "Versicherer", "Kundennummer", "Benutzer", "Policennummer", "Jahres Brutto", "Status"};

    public TabPanelVertraege() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return ("Verträge");
    }

    public void load(KundenObj kunde) {
        this.type = KUNDE;
        this.kunde = kunde;
        kdnr = kunde.getKundenNr();
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.type = GESCHAEFT;
        this.firma = firma;
        kdnr = firma.getKundenNr();
        loadTable();
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.versicherer = versicherer;
        kdnr = null;
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        kdnr = null;
        loadTable();
    }

    public void load(ProduktObj produkt) {
        this.type = PRODUKT;
        this.produkt = produkt;
        kdnr = null;
        loadTable();
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Sich selbst aufrufen??");
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void loadTable() {
        try {
            VertragObj[] vertraege = null;
            String[] column = null;

            if (type == KUNDE || type == GESCHAEFT) {
                vertraege = VertraegeSQLMethods.getKundenVertraege(DatabaseConnection.open(),
                        kdnr, getStatus());
                column = kundenColumn;
            } else if (type == VERSICHERER) {
                vertraege = VertraegeSQLMethods.getVersichererVertraege(DatabaseConnection.open(),
                        versicherer.getId(), getStatus());
                column = versichererColumn;
            } else if (type == BENUTZER) {
                vertraege = VertraegeSQLMethods.getBenutzerVertraege(DatabaseConnection.open(),
                        benutzer.getId(), getStatus());

                column = benutzerColumn;
            } else if (type == PRODUKT) {
                vertraege = VertraegeSQLMethods.getProduktVertraege(DatabaseConnection.open(),
                        produkt.getId(), getStatus());
                column = produktColumn;
            }



            loadData(vertraege, column);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte die Verträge nicht aus der Datenbank laden", e);
            ShowException.showException("Die Verträge konnten nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Verträge nicht laden");
        }
    }

    private void loadData(VertragObj[] vtr, String[] columns) {
        Object[][] data = null;

        if (vtr != null) {
            data = new Object[vtr.length][7];

            for (int i = 0; i < vtr.length; i++) {
                data[i][0] = vtr[i];

                if (type == KUNDE || type == GESCHAEFT || type == PRODUKT) {
                    data[i][1] = VersicherungsRegistry.getVersicher(vtr[i].getVersichererId());
                } else if (type == BENUTZER || type == VERSICHERER) {
                    data[i][1] = KundenRegistry.getKunde(vtr[i].getKundenKennung());
                }

                if (type == KUNDE || type == GESCHAEFT || type == VERSICHERER) {
                    data[i][2] = vtr[i].getProduktId();
                } else if (type == BENUTZER) {
                    data[i][2] = VersicherungsRegistry.getVersicher(vtr[i].getVersichererId());
                } else if (type == PRODUKT) {
                    data[i][2] = KundenRegistry.getKunde(vtr[i].getKundenKennung());
                }

                if (type == KUNDE || type == GESCHAEFT || type == PRODUKT || type == VERSICHERER) {
                    data[i][3] = BenutzerRegistry.getBenutzer(vtr[i].getBenutzerId(), true);
                } else if (type == BENUTZER) {
                    data[i][3] = vtr[i].getProduktId();
                }

                data[i][4] = vtr[i].getPolicennr();
                data[i][5] = vtr[i].getJahresBrutto();
                data[i][3] = Vertraege.getStatusName(vtr[i].getStatus());
            }
        }
        setTable(data, columns);
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object data[][], String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_vertraege.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_vertraege.getModel();
            atm.setData(data);
            table_vertraege.packAll();
            return;
        }

        table_vertraege.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_vertraege.setColumnSelectionAllowed(false);
        table_vertraege.setCellSelectionEnabled(false);
        table_vertraege.setRowSelectionAllowed(true);
        table_vertraege.setAutoCreateRowSorter(true);

        table_vertraege.setFillsViewportHeight(true);
        table_vertraege.removeColumn(table_vertraege.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_vertraege.addMouseListener(popupListener);
        table_vertraege.setColumnControlVisible(true);

        JTableHeader header = table_vertraege.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();
        table_vertraege.packAll();

        table_vertraege.tableChanged(new TableModelEvent(table_vertraege.getModel()));
        table_vertraege.revalidate();

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
                int row = table_vertraege.rowAtPoint(point);
                table_vertraege.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Verträge Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private int getStatus() {
        int status = Vertraege.STATUS_AKTIV;

        if (this.alleDBMenuItem.isSelected()) {
            return -1;
        } else if (this.aktiveDBMenuItem.isSelected()) {
            return Vertraege.STATUS_AKTIV;
        } else if (this.archivedDBMenuItem.isSelected()) {
            return Vertraege.STATUS_STILLGELEGT;
        } else if (this.deletedDBMenuItem.isSelected()) {
            return Vertraege.STATUS_AUFGELOEST;
        } else if (this.neuantragDBMenuItem.isSelected()) {
            return Vertraege.STATUS_NEUANTRAG;
        } else if (this.sonstigeDBMenuItem.isSelected()) {
            return Vertraege.STATUS_SONSTIGES;
        }

        return status;
    }

    public void disableElements() {
        this.btnNeu.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);

        this.table_vertraege.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);

        this.btnRefresh.setEnabled(false);
        this.btnArchive.setEnabled(false);

        this.dropDownButton.setEnabled(false);
    }

    public void enableElements() {
        this.btnNeu.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);

        this.table_vertraege.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);

        this.btnRefresh.setEnabled(true);
        this.btnArchive.setEnabled(true);

        this.dropDownButton.setEnabled(true);
    }

    private void searchTable() {
        int result = table_vertraege.getSearchable().search(field_search.getText());
    }

    private void newVertrag() {
    }

    private void editVertrag() {
        // TODO
    }

    private void archiveVertrag() {
        int row = table_vertraege.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Vertrag aus.");
            return;
        }

        VertragObj vtr = (VertragObj) table_vertraege.getModel().getValueAt(row, 0);

        boolean success = VertragRegistry.archiveVertrag(vtr);

        if (success) {
            this.loadTable();
        }
    }

    private void deleteVertrag() {
        int row = table_vertraege.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Vertrag aus.");
            return;
        }

        VertragObj vtr = (VertragObj) table_vertraege.getModel().getValueAt(row, 0);

        boolean success = VertragRegistry.deleteVertrag(vtr);

        if (success) {
            this.loadTable();
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

        toogleActive = new javax.swing.ButtonGroup();
        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        neuantragDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        sonstigeDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_vertraege = new org.jdesktop.swingx.JXTable();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
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
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelVertraege.class);
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
        archivedDBMenuItem.setMnemonic('S');
        archivedDBMenuItem.setText(resourceMap.getString("archivedDBMenuItem.text")); // NOI18N
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
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        grp_dbstatus.add(neuantragDBMenuItem);
        neuantragDBMenuItem.setMnemonic('N');
        neuantragDBMenuItem.setText(resourceMap.getString("neuantragDBMenuItem.text")); // NOI18N
        neuantragDBMenuItem.setName("neuantragDBMenuItem"); // NOI18N
        neuantragDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuantragDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(neuantragDBMenuItem);

        grp_dbstatus.add(sonstigeDBMenuItem);
        sonstigeDBMenuItem.setMnemonic('S');
        sonstigeDBMenuItem.setText(resourceMap.getString("sonstigeDBMenuItem.text")); // NOI18N
        sonstigeDBMenuItem.setName("sonstigeDBMenuItem"); // NOI18N
        sonstigeDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sonstigeDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(sonstigeDBMenuItem);

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

        table_vertraege.setModel(new javax.swing.table.DefaultTableModel(
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
        table_vertraege.setColumnControlVisible(true);
        table_vertraege.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_vertraege.setName("table_vertraege"); // NOI18N
        table_vertraege.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_vertraegeMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_vertraege);

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

        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        toolbar.add(btnEdit);

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
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveVertrag();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteVertrag();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void neuantragDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuantragDBMenuItemActionPerformed
        loadTable();
    }//GEN-LAST:event_neuantragDBMenuItemActionPerformed

    private void sonstigeDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sonstigeDBMenuItemActionPerformed
        loadTable();
    }//GEN-LAST:event_sonstigeDBMenuItemActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
    }//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void table_vertraegeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_vertraegeMouseClicked
        if (evt.getClickCount() > 1) {
            editVertrag();
        }
    }//GEN-LAST:event_table_vertraegeMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editVertrag();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newVertrag();
    }//GEN-LAST:event_btnNeuActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newVertrag();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editVertrag();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveVertrag();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteVertrag();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JMenuItem archiveMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
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
    private javax.swing.JCheckBoxMenuItem neuantragDBMenuItem;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JCheckBoxMenuItem sonstigeDBMenuItem;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_vertraege;
    private javax.swing.ButtonGroup toogleActive;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
}
