/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelStoerfaelle.java
 *
 * Created on 30.05.2011, 19:08:50
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Stoerfall.StoerfallDialog;
import de.maklerpoint.office.Konstanten.Stoerfaelle;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Stoerfalle.Tools.StoerfaelleSQLMethods;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelStoerfaelle extends javax.swing.JPanel implements iTabs {

    public static final int KUNDE = 0;
    public static final int GESCHAEFT = 1;
    public static final int VERSICHERER = 2;
    public static final int BENUTZER = 3;
    public static final int PRODUKT = 4;
    public static final int VERTRAG = 5;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleDateFormat df_day = new SimpleDateFormat("dd.MM.yyyy");
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private ProduktObj produkt = null;
    private VertragObj vertrag = null;
    private String kdnr = null;
    private static final String[] kundenColumn = {"Hidden", "Id", "Betreuer", "Vertrag",
        "Interne Nr.", "Grund", "Eingang", "Fälligkeit", "Frist", "Rückstand", "Mahnstatus",
        "Kategorie", "Notiz", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Angelegt von", "Erstellt am", "Zuletzt geändert", "Status"
    };
    private static final String[] benutzerColumn = {"Hidden", "Id", "Kunde", "Vertrag",
        "Interne Nr.", "Grund", "Eingang", "Fälligkeit", "Frist", "Rückstand", "Mahnstatus",
        "Kategorie", "Notiz", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Angelegt von", "Erstellt am", "Zuletzt geändert", "Status"
    };
    private static final String[] vertragColumn = {"Hidden", "Id", "Betreuer", "Kunde",
        "Interne Nr.", "Grund", "Eingang", "Fälligkeit", "Frist", "Rückstand", "Mahnstatus",
        "Kategorie", "Notiz", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Angelegt von", "Erstellt am", "Zuletzt geändert", "Status"
    };

    /** Creates new form TabPanelStoerfaelle */
    public TabPanelStoerfaelle() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Störfälle";
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
//        this.type = VERSICHERER;
//        this.versicherer = versicherer;
//        kdnr = null;
//        loadTable();
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        kdnr = null;
        loadTable();
    }

    private int getStatus() {
        // TODO Implement
        return Stoerfaelle.STATUS_OFFEN;
    }

    public void load(ProduktObj produkt) {
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vertrag = vertrag;
        kdnr = vertrag.getKundenKennung();
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            StoerfallObj[] stoer = null;

            if (type == KUNDE || type == GESCHAEFT) {
                stoer = StoerfaelleSQLMethods.getKundenStoerfaelle(DatabaseConnection.open(), kdnr, getStatus());
            } else if (type == VERTRAG) {
                stoer = StoerfaelleSQLMethods.getVertragStoerfaelle(DatabaseConnection.open(), vertrag.getId(), getStatus());
            } else if (type == BENUTZER) {
                stoer = StoerfaelleSQLMethods.getBenutzerStoerfaelle(DatabaseConnection.open(), benutzer.getId(), getStatus());
            }

            if (stoer != null) {
                data = new Object[stoer.length][kundenColumn.length];

                for (int i = 0; i < stoer.length; i++) {
                    VertragObj vtr = VertragRegistry.getVertrag(stoer[i].getVertragsId());
                    WaehrungenObj waer = VersicherungsRegistry.getWaehrung(vtr.getWaehrungId());

                    data[i][0] = stoer[i];
                    data[i][1] = stoer[i].getId();

                    if (type == KUNDE || type == GESCHAEFT || type == VERTRAG) {
                        data[i][2] = BenutzerRegistry.getBenutzer(stoer[i].getBetreuerId());
                    } else if (type == BENUTZER) {
                        data[i][2] = KundenRegistry.getKunde(stoer[i].getKundenNr());
                    }

                    if (type == KUNDE || type == GESCHAEFT || type == BENUTZER) {
                        data[i][3] = vtr;
                    } else if (type == VERTRAG) {
                        data[i][3] = KundenRegistry.getKunde(stoer[i].getKundenNr());
                    }

                    data[i][4] = stoer[i].getStoerfallNr();
                    data[i][5] = stoer[i].getGrund();
                    data[i][6] = df.format(stoer[i].getEingang());
                    data[i][7] = df_day.format(stoer[i].getFaelligkeit());
                    data[i][8] = stoer[i].getFristTage() + " Tage";
                    data[i][9] = stoer[i].getRueckstand() + " " + waer.getBezeichnung();
                    data[i][10] = stoer[i].getMahnstatus();
                    data[i][11] = stoer[i].getKategorie();
                    data[i][12] = stoer[i].getNotiz();
                    data[i][13] = stoer[i].getCustom1();
                    data[i][14] = stoer[i].getCustom2();
                    data[i][15] = stoer[i].getCustom3();
                    data[i][16] = BenutzerRegistry.getBenutzer(stoer[i].getCreatorId());
                    data[i][17] = df.format(stoer[i].getCreated());
                    data[i][18] = df.format(stoer[i].getModified());
                    data[i][19] = Status.getName(stoer[i].getStatus());
                }
            }

//            long start = System.currentTimeMillis();
            if (type == KUNDE || type == GESCHAEFT) {
                setTable(data, kundenColumn);
            } else if (type == VERTRAG) {
                setTable(data, vertragColumn);
            } else if (type == BENUTZER) {
                setTable(data, benutzerColumn);
            }
//            long end = System.currentTimeMillis();
//            System.out.println("Settable: "
//                    + (end - start) + " milliseconds");

        } catch (Exception e) {
            Log.databaselogger.fatal("Die Störfalle konnten nicht geladen werden.", e);
            ShowException.showException("Die Störfalle konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfalle nicht laden");
        }
    }
    
    // Atm model
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {

        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_stoerfaelle.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_stoerfaelle.getModel();
            atm.setData(data);
            table_stoerfaelle.packAll();
            return;
        }

        table_stoerfaelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_stoerfaelle.setColumnSelectionAllowed(false);
        table_stoerfaelle.setCellSelectionEnabled(false);
        table_stoerfaelle.setRowSelectionAllowed(true);
        table_stoerfaelle.setAutoCreateRowSorter(true);

        table_stoerfaelle.setFillsViewportHeight(true);
        table_stoerfaelle.removeColumn(table_stoerfaelle.getColumnModel().getColumn(0));

        table_stoerfaelle.getColumnExt("Id").setVisible(false);
        table_stoerfaelle.getColumnExt("Grund").setVisible(false);
        table_stoerfaelle.getColumnExt("Frist").setVisible(false);
//        table_stoerfaelle.getColumnExt("Rückstand").setVisible(false);
        table_stoerfaelle.getColumnExt("Notiz").setVisible(false);

        table_stoerfaelle.getColumnExt("Benutzerdefiniert 1").setVisible(false);
        table_stoerfaelle.getColumnExt("Benutzerdefiniert 2").setVisible(false);
        table_stoerfaelle.getColumnExt("Benutzerdefiniert 3").setVisible(false);
        table_stoerfaelle.getColumnExt("Notiz").setVisible(false);
        table_stoerfaelle.getColumnExt("Angelegt von").setVisible(false);
        table_stoerfaelle.getColumnExt("Erstellt am").setVisible(false);
        table_stoerfaelle.getColumnExt("Zuletzt geändert").setVisible(false);
        table_stoerfaelle.getColumnExt("Status").setVisible(false);

        MouseListener popupListener = new TablePopupListener();
        table_stoerfaelle.addMouseListener(popupListener);
        table_stoerfaelle.setColumnControlVisible(true);

        JTableHeader header = table_stoerfaelle.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_stoerfaelle.packAll();
    }

    /**
     * Mousepopup
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
                int row = table_stoerfaelle.rowAtPoint(point);
                table_stoerfaelle.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void disableElements() {
        this.btnRegulate.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.table_stoerfaelle.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);

        setTable(null, kundenColumn);
    }

    public void enableElements() {
        this.btnRegulate.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.table_stoerfaelle.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Störfälle Auswahl");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private void searchTable() {
        int result = table_stoerfaelle.getSearchable().search(field_search.getText());
    }

    private void newStoerfall() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();

        if (type == KUNDE) {
            stoerfallDialog = new StoerfallDialog(mainFrame, true, kunde.getKundenNr());
        } else if (type == GESCHAEFT) {
            stoerfallDialog = new StoerfallDialog(mainFrame, true, firma.getKundenNr());
        } else if (type == VERTRAG) {
            stoerfallDialog = new StoerfallDialog(mainFrame, true, vertrag);
        } else {
            stoerfallDialog = new StoerfallDialog(mainFrame, true);
        }

        stoerfallDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(stoerfallDialog);

        this.loadTable();
    }

    private void editStoerfall() {
        int row = table_stoerfaelle.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Schaden ausgewählt.");
            return;
        }

        StoerfallObj stoer = (StoerfallObj) this.table_stoerfaelle.getModel().getValueAt(row, 0);

        if (stoer == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        stoerfallDialog = new StoerfallDialog(mainFrame, true, stoer);
        stoerfallDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(stoerfallDialog);

        this.loadTable();
    }

    private void reguliereStoerfall() {
        int row = table_stoerfaelle.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Störfall aus.");
            return;
        }

        StoerfallObj stoer = (StoerfallObj) this.table_stoerfaelle.getModel().getValueAt(row, 0);

        if (stoer == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Störfall wirklich als erledigt markieren?",
                    "Bestätigung Regulation", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
//            StoerfaelleSQLMethods.archive(DatabaseConnection.open(), stoer);
            StoerfaelleSQLMethods.setErledigtFromStoerfaelle(DatabaseConnection.open(), stoer.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Störfall " + stoer.getId() + " nicht als erledigt markieren", e);
            ShowException.showException("Datenbankfehler: Der Störfall konnte nicht als erledigt markiert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfall nicht regulieren.");
        }

        this.loadTable();
    }

//    private void deleteStoerfall() {
//        int row = table_stoerfaelle.getSelectedRow();
//
//        if (row == -1) {
//            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Störfall aus.");
//            return;
//        }
//
//        StoerfallObj stoer = (StoerfallObj) this.table_stoerfaelle.getModel().getValueAt(row, 0);
//
//        if (stoer == null) {
//            return;
//        }
//
//        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
//            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Störfall wirklich löschen?",
//                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);
//
//            if (answer != JOptionPane.YES_OPTION) {
//                return;
//            }
//        }
//
//        try {
////            StoerfaelleSQLMethods.delete(DatabaseConnection.open(), stoer);
//        } catch (Exception e) {
//            Log.databaselogger.fatal("Konnte den Störfall " + stoer.getId() + " nicht löschen", e);
//            ShowException.showException("Datenbankfehler: Der Störfall konnte nicht gelöscht werden. "
//                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
//                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Störfall nicht löschen");
//        }
//
//        this.loadTable();
//    }

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
        reguliertMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_stoerfaelle = new org.jdesktop.swingx.JXTable();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnRegulate = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelStoerfaelle.class);
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

        reguliertMenuItem.setText(resourceMap.getString("reguliertMenuItem.text")); // NOI18N
        reguliertMenuItem.setName("reguliertMenuItem"); // NOI18N
        reguliertMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reguliertMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(reguliertMenuItem);

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

        table_stoerfaelle.setModel(new javax.swing.table.DefaultTableModel(
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
        table_stoerfaelle.setColumnControlVisible(true);
        table_stoerfaelle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_stoerfaelle.setName("table_stoerfaelle"); // NOI18N
        table_stoerfaelle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_stoerfaelleMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_stoerfaelle);

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

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbar.add(jSeparator3);

        btnRegulate.setIcon(resourceMap.getIcon("btnRegulate.icon")); // NOI18N
        btnRegulate.setText(resourceMap.getString("btnRegulate.text")); // NOI18N
        btnRegulate.setToolTipText(resourceMap.getString("btnRegulate.toolTipText")); // NOI18N
        btnRegulate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRegulate.setFocusable(false);
        btnRegulate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRegulate.setName("btnRegulate"); // NOI18N
        btnRegulate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegulateActionPerformed(evt);
            }
        });
        toolbar.add(btnRegulate);

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
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
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
        newStoerfall();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editStoerfall();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnRegulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegulateActionPerformed
        reguliereStoerfall();
}//GEN-LAST:event_btnRegulateActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

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

    private void table_stoerfaelleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_stoerfaelleMouseClicked
        if (evt.getClickCount() > 1) {
            editStoerfall();
        }
    }//GEN-LAST:event_table_stoerfaelleMouseClicked

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newStoerfall();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editStoerfall();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void reguliertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reguliertMenuItemActionPerformed
        reguliereStoerfall();
}//GEN-LAST:event_reguliertMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRegulate;
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JMenuItem reguliertMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_stoerfaelle;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
    private JDialog stoerfallDialog;
}
