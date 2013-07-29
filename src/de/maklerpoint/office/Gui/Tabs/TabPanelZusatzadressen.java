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
 * TabPanelZusatzadressen.java
 *
 * Created on 31.08.2010, 10:49:25
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Gui.Kunden.NewKundenAdresse;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.KundenUebersichtModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author yves
 */
public class TabPanelZusatzadressen extends javax.swing.JPanel implements iTabs {

    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private JDialog newKundeBox;
    private String kennung = null;
    private String[] Columns = new String[]{"Hidden", "Id", "Name", "Adresse Zusatz", "Adresse Zusatz 2", "Strasse",
        "PLZ", "Ort", "Bundesland", "Land", "Kommunikation 1", "Kommunikation 2", "Kommunikation 3",
        "Kommunikation 4", "Kommunikation 5", "Kommunikation 6",
        "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Kommentare",
        "Ersteller", "Erstellt am", "Zuletzt geändert", "Status"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private boolean enabled = true;

    /** Creates new form TabPanelZusatzadressen */
    public TabPanelZusatzadressen() {
        initComponents();
        addAnsichtButtons();
        table_zusatzadressen.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_zusatzadressen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_zusatzadressen.setColumnSelectionAllowed(false);
        table_zusatzadressen.setCellSelectionEnabled(false);
        table_zusatzadressen.setRowSelectionAllowed(true);
        table_zusatzadressen.setAutoCreateRowSorter(true);

        table_zusatzadressen.setFillsViewportHeight(true);
    }

    public String getTabName() {
        return "Zusatzadressen";
    }

    public void load(KundenObj kunde) {

        this.type = PRIVAT;
        this.kunde = kunde;
        this.kennung = kunde.getKundenNr();
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Zusatzadressen für Kunden " + kunde.getKundenNr());
        }

        loadTable();

    }

    public void load(FirmenObj firma) {
        this.type = FIRMA;
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Zusatzadressen für Geschäftskunde " + firma.getKundenNr());
        }
        loadTable();
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.vers = versicherer;
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Zusatzadressen für Versicherer " + vers.getName());
        }
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Zusatzadressen für den Benutzer " + benutzer);
        }
        loadTable();
    }

    public void load(ProduktObj produkt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disableElements() {
        enabled = false;

        setTable(null, Columns);

        this.table_zusatzadressen.setEnabled(false);
        this.btnArchive.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.btnKarte.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);
    }

    public void enableElements() {
        enabled = true;

        this.table_zusatzadressen.setEnabled(true);
        this.btnArchive.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.btnKarte.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Zusatzadressen Ansicht");
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

    private void loadTable() {
        try {
            Object[][] data = null;
            ZusatzadressenObj[] za = null;


            if (type == PRIVAT || type == FIRMA) {
                za = ZusatzadressenSQLMethods.loadZusatzadressen(DatabaseConnection.open(), kennung, getStatus());
            } else if (type == VERSICHERER) {
                za = ZusatzadressenSQLMethods.loadZusatzadressen(DatabaseConnection.open(), kennung, getStatus());
            }

            if (za != null) {
                data = new Object[za.length][24];

                for (int i = 0; i < za.length; i++) {
                    data[i][0] = za[i];
                    data[i][1] = za[i].getId();
                    data[i][2] = za[i].getName();
                    data[i][3] = za[i].getNameZusatz();
                    data[i][4] = za[i].getNameZusatz2();
                    data[i][5] = za[i].getStreet();
                    data[i][6] = za[i].getPlz();
                    data[i][7] = za[i].getOrt();
                    data[i][8] = za[i].getBundesland();
                    data[i][9] = za[i].getLand();
                    data[i][10] = CommunicationTypes.getCommunicationLabel(za[i], 1);
                    data[i][11] = CommunicationTypes.getCommunicationLabel(za[i], 2);
                    data[i][12] = CommunicationTypes.getCommunicationLabel(za[i], 3);
                    data[i][13] = CommunicationTypes.getCommunicationLabel(za[i], 4);
                    data[i][14] = CommunicationTypes.getCommunicationLabel(za[i], 5);
                    data[i][15] = CommunicationTypes.getCommunicationLabel(za[i], 6);
                    data[i][16] = za[i].getCustom1();
                    data[i][17] = za[i].getCustom2();
                    data[i][18] = za[i].getCustom3();
                    data[i][19] = za[i].getComments();
                    data[i][20] = BenutzerRegistry.getBenutzer(za[i].getCreator());
                    data[i][21] = df.format(za[i].getCreated());
                    data[i][22] = df.format(za[i].getModified());
                    data[i][23] = Status.getName(za[i].getStatus());
                }
            }

//            long start = System.currentTimeMillis();
            setTable(data, Columns);

//            long end = System.currentTimeMillis();
//            System.out.println("Settable: "
//                    + (end - start) + " milliseconds");
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Zusatzadressen für den Kunden \"" + kennung + "\" nicht aus der Datenbank laden", e);
            ShowException.showException("Konnte die Zusatzadressen für den Kunden \"" + kennung + "\" nicht aus der Datenbank laden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadressen nicht laden");
        }
    }
    /**
     * 
     * @param data
     * @param columns
     */
    private AbstractStandardModel kum = null;

    private void setTable(Object[][] data, String[] columns) {

        if (kum == null) {
            kum = new AbstractStandardModel(data, columns);
            table_zusatzadressen.setModel(kum);
        } else {
            // TODO implement this everywhere
            kum = (AbstractStandardModel) table_zusatzadressen.getModel();
            kum.setData(data);
            table_zusatzadressen.packAll();
            return;
        }

        table_zusatzadressen.removeColumn(table_zusatzadressen.getColumnModel().getColumn(0));

        table_zusatzadressen.getColumnExt("Id").setVisible(false);
        table_zusatzadressen.getColumnExt("Adresse Zusatz 2").setVisible(false);
        table_zusatzadressen.getColumnExt("Bundesland").setVisible(false);
        table_zusatzadressen.getColumnExt("Land").setVisible(false);
        table_zusatzadressen.getColumnExt("Kommunikation 3").setVisible(false);
        table_zusatzadressen.getColumnExt("Kommunikation 4").setVisible(false);
        table_zusatzadressen.getColumnExt("Kommunikation 5").setVisible(false);
        table_zusatzadressen.getColumnExt("Kommunikation 6").setVisible(false);

        table_zusatzadressen.getColumnExt("Benutzerdefiniert 1").setVisible(false);
        table_zusatzadressen.getColumnExt("Benutzerdefiniert 2").setVisible(false);
        table_zusatzadressen.getColumnExt("Benutzerdefiniert 3").setVisible(false);
        table_zusatzadressen.getColumnExt("Kommentare").setVisible(false);
        table_zusatzadressen.getColumnExt("Ersteller").setVisible(false);
        table_zusatzadressen.getColumnExt("Erstellt am").setVisible(false);
        table_zusatzadressen.getColumnExt("Zuletzt geändert").setVisible(false);
        table_zusatzadressen.getColumnExt("Status").setVisible(false);

        MouseListener popupListener = new TablePopupListener();
        table_zusatzadressen.addMouseListener(popupListener);
        table_zusatzadressen.setColumnControlVisible(true);

        JTableHeader header = table_zusatzadressen.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_zusatzadressen.packAll();
    }

    /**
     * POPUP LISTENER
     */
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
                int row = table_zusatzadressen.rowAtPoint(point);
                table_zusatzadressen.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void searchTable() {
        System.out.println("Suche nach: " + field_search.getText());
        int result = table_zusatzadressen.getSearchable().search(field_search.getText());
    }

    private void archiveZusatzadresse() {
        int row = table_zusatzadressen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Adresse aus.");
            return;
        }

        ZusatzadressenObj za = (ZusatzadressenObj) this.table_zusatzadressen.getModel().getValueAt(row, 0);

        if (za == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Adresse wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            ZusatzadressenSQLMethods.archiveFromkundenzusatz(DatabaseConnection.open(), za.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Zusatzadresse " + za.getId() + " für den Kunden nicht aus der Datenbank archivieren", e);
            ShowException.showException("Datenbankfehler: Die Zusatzadresse konnten nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadresse nicht archivieren");
        }

        this.loadTable();
    }

    private void deleteAdresse() {
        int row = table_zusatzadressen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Zusatzadresse ausgewählt.");
            return;
        }

        ZusatzadressenObj za = (ZusatzadressenObj) this.table_zusatzadressen.getModel().getValueAt(row, 0);

        if (za == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Zusatzadresse wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            ZusatzadressenSQLMethods.deleteFromkundenzusatz(DatabaseConnection.open(), za.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Zusatzadresse für den Kunden nicht löschen", e);
            ShowException.showException("Datenbankfehler: Die Zusatzadresse konnten nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadresse nicht löschen");
        }

        this.loadTable();
    }

    private void showKarteAdresse() {
        int row = table_zusatzadressen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Zusatzadresse ausgewählt.");
            return;
        }

        ZusatzadressenObj za = (ZusatzadressenObj) this.table_zusatzadressen.getModel().getValueAt(row, 0);

        if (za == null) {
            return;
        }

        // TODO fix this --> static var ??!
        KarteSuche.doExteneralSearch(za.getStreet() + ", " + za.getOrt(), CRMView.getInstance());
    }

    private void editAdresse() {
        int row = table_zusatzadressen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Zusatzadresse ausgewählt.");
            return;
        }

        ZusatzadressenObj za = (ZusatzadressenObj) this.table_zusatzadressen.getModel().getValueAt(row, 0);

        if (za == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newKundeBox = new NewKundenAdresse(mainFrame, true, za);
        newKundeBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(newKundeBox);

        this.loadTable();
    }

    private void newAdresse() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();

        if (type == PRIVAT) {
            newKundeBox = new NewKundenAdresse(mainFrame, true, kunde);
        } else if (type == FIRMA) {
            newKundeBox = new NewKundenAdresse(mainFrame, true, firma);
        } else if (type == VERSICHERER) {
            newKundeBox = new NewKundenAdresse(mainFrame, true, vers);
        } else if (type == BENUTZER) {
            newKundeBox = new NewKundenAdresse(mainFrame, true, benutzer);
        }

        newKundeBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(newKundeBox);

        this.loadTable();
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
        btnKarte = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_zusatzadressen = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelZusatzadressen.class);
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
        deletedDBMenuItem.setMnemonic('A');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
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

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N
        toolbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                toolbarKeyTyped(evt);
            }
        });

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

        btnKarte.setIcon(resourceMap.getIcon("btnKarte.icon")); // NOI18N
        btnKarte.setText(resourceMap.getString("btnKarte.text")); // NOI18N
        btnKarte.setToolTipText(resourceMap.getString("btnKarte.toolTipText")); // NOI18N
        btnKarte.setFocusable(false);
        btnKarte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnKarte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKarte.setName("btnKarte"); // NOI18N
        btnKarte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKarte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKarteActionPerformed(evt);
            }
        });
        toolbar.add(btnKarte);

        jSeparator6.setName("jSeparator6"); // NOI18N
        toolbar.add(jSeparator6);

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

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_zusatzadressen.setModel(new javax.swing.table.DefaultTableModel(
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
        table_zusatzadressen.setColumnControlVisible(true);
        table_zusatzadressen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_zusatzadressen.setName("table_zusatzadressen"); // NOI18N
        table_zusatzadressen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_zusatzadressenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_zusatzadressen);

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
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newAdresse();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteAdresse();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editAdresse();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        showKarteAdresse();
}//GEN-LAST:event_btnKarteActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveZusatzadresse();
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

    private void toolbarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toolbarKeyTyped
        searchTable();
    }//GEN-LAST:event_toolbarKeyTyped

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newAdresse();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editAdresse();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveZusatzadresse();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteAdresse();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    private void table_zusatzadressenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_zusatzadressenMouseClicked
        if (evt.getClickCount() > 1) {
            editAdresse();
        }
    }//GEN-LAST:event_table_zusatzadressenMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JMenuItem archiveMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnKarte;
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
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_zusatzadressen;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
}
