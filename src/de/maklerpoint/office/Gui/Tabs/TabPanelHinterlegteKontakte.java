/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelHinterlegteKontakte.java
 *
 * Created on 24.07.2011, 15:59:36
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
import de.maklerpoint.office.Gui.Kontakte.KontakteDialog;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kontakte.Tools.KontakteSQLMethods;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
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
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelHinterlegteKontakte extends javax.swing.JPanel implements iTabs {

    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private static final int PRODUKT = 4;
    private static final int VERTRAG = 5;
    public static final int STOERFALL = 6;
    public static final int SCHADEN = 7;
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private ProduktObj prod = null;
    private VertragObj vtr = null;
    private StoerfallObj stoer = null;
    private SchadenObj schaden = null;
    private boolean enabled = false;
    private String kdnr = null;
    private String[] Columns = new String[]{"Hidden", "Name", "Adresse", "Kommunikation 1",
        "Kommunikation 2", "Kommunikation 3", "Kommunikation 4",
        "Kommunikation 5", "Kommunikation 6", "Benutzerdefiniert 1",
        "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Kommentare",
        "Erstellt am", "Zuletzt geändert am", "Status"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /** Creates new form TabPanelHinterlegteKontakte */
    public TabPanelHinterlegteKontakte() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Hinterlegte Kontakte";
    }

    public void load(KundenObj kunde) {
        this.kunde = kunde;
        this.type = PRIVAT;
        this.kdnr = kunde.getKundenNr();
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.firma = firma;
        this.type = FIRMA;
        this.kdnr = firma.getKundenNr();
        loadTable();
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.vers = versicherer;
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        loadTable();
    }

    public void load(ProduktObj produkt) {
        this.type = PRODUKT;
        this.prod = produkt;
        loadTable();
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vtr = vertrag;
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        this.type = STOERFALL;
        this.stoer = stoerfall;
        loadTable();
    }

    public void load(SchadenObj schaden) {
        this.type = SCHADEN;
        this.schaden = schaden;
        loadTable();
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Kontakte Ansicht");
        this.toolbar.add(dropDownButton);
    }

    public void disableElements() {
        enabled = false;
        setTable(null, Columns);

        this.table_kontakte.setEnabled(false);
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
        if (enabled == true) {
            return;
        }

        enabled = true;

        this.table_kontakte.setEnabled(true);
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
            KontaktObj[] kontakte = null;

            if (type == PRIVAT || type == FIRMA) {
                kontakte = KontakteSQLMethods.getKundenKontakte(DatabaseConnection.open(), kdnr, getStatus());
            } else if (type == VERSICHERER) {
                kontakte = KontakteSQLMethods.getVersichererKontakte(DatabaseConnection.open(), vers.getId(), getStatus());
            } else if (type == VERTRAG) {
                kontakte = KontakteSQLMethods.getVertragKontakte(DatabaseConnection.open(), vtr.getId(), getStatus());
            } else if (type == STOERFALL) {
                kontakte = KontakteSQLMethods.getStoerfallKontakte(DatabaseConnection.open(), stoer.getId(), getStatus());
            } else if (type == SCHADEN) {
                kontakte = KontakteSQLMethods.getSchadenKontakte(DatabaseConnection.open(), schaden.getId(), getStatus());
            } else if (type == PRODUKT) {
                kontakte = KontakteSQLMethods.getProduktKontakte(DatabaseConnection.open(), prod.getId(), getStatus());
            } else if (type == BENUTZER) {
//                kontakte = KontakteSQLMethods.(DatabaseConnection.open(), vers.getId(), getStatus());
            }

            if (kontakte != null) {
                data = new Object[kontakte.length][16];

                for (int i = 0; i < kontakte.length; i++) {
                    data[i][0] = kontakte[i];
                    data[i][1] = kontakte[i].getName();
                    data[i][2] = kontakte[i].getAdresse();
                    data[i][3] = CommunicationTypes.getCommunicationLabel(kontakte[i], 1);
                    data[i][4] = CommunicationTypes.getCommunicationLabel(kontakte[i], 2);
                    data[i][5] = CommunicationTypes.getCommunicationLabel(kontakte[i], 3);
                    data[i][6] = CommunicationTypes.getCommunicationLabel(kontakte[i], 4);
                    data[i][7] = CommunicationTypes.getCommunicationLabel(kontakte[i], 5);
                    data[i][8] = CommunicationTypes.getCommunicationLabel(kontakte[i], 6);
                    data[i][9] = kontakte[i].getCustom1();
                    data[i][10] = kontakte[i].getCustom2();
                    data[i][11] = kontakte[i].getCustom3();
                    data[i][12] = kontakte[i].getComments();
                    data[i][13] = df.format(kontakte[i].getCreated());
                    data[i][14] = df.format(kontakte[i].getModified());
                    data[i][15] = Status.getName(kontakte[i].getStatus());
                }
            }

            setTable(data, Columns);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Kontakte nicht aus der Datenbank laden", e);
            ShowException.showException("Die Kontakte konnten nicht aus der Datenbank geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kontakte nicht laden");
            setTable(null, Columns);
        }
    }
    private AbstractStandardModel atm = null;
    /**
     * Set Table
     * @param data
     * @param columns 
     */
    private void setTable(Object[][] data, String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_kontakte.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_kontakte.getModel();
            atm.setData(data);
            table_kontakte.packAll();
            return;
        }

        table_kontakte.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_kontakte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kontakte.setColumnSelectionAllowed(false);
        table_kontakte.setCellSelectionEnabled(false);
        table_kontakte.setRowSelectionAllowed(true);
        table_kontakte.setAutoCreateRowSorter(true);

        table_kontakte.setFillsViewportHeight(true);
        table_kontakte.removeColumn(table_kontakte.getColumnModel().getColumn(0));

        table_kontakte.getColumnExt("Kommunikation 4").setVisible(false);
        table_kontakte.getColumnExt("Kommunikation 5").setVisible(false);
        table_kontakte.getColumnExt("Kommunikation 6").setVisible(false);

        table_kontakte.getColumnExt("Benutzerdefiniert 1").setVisible(false);
        table_kontakte.getColumnExt("Benutzerdefiniert 2").setVisible(false);
        table_kontakte.getColumnExt("Benutzerdefiniert 3").setVisible(false);

        table_kontakte.getColumnExt("Erstellt am").setVisible(false);
        table_kontakte.getColumnExt("Zuletzt geändert am").setVisible(false);

        table_kontakte.getColumnExt("Status").setVisible(false);

        MouseListener popupListener = new TablePopupListener();
        table_kontakte.addMouseListener(popupListener);
        table_kontakte.setColumnControlVisible(true);

        JTableHeader header = table_kontakte.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_kontakte.packAll();
    }

    private void searchTable() {
        int result = table_kontakte.getSearchable().search(field_search.getText());
    }

    private void showKarte() {
        int row = table_kontakte.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Kontakt ausgewählt.");
            return;
        }

        KontaktObj kontakt = (KontaktObj) this.table_kontakte.getModel().getValueAt(row, 0);

        if (kontakt == null) {
            return;
        }
        
        if(kontakt.getAdresse() == null || kontakt.getAdresse().length() < 1) {
            JOptionPane.showMessageDialog(null, "Der Kontakt verfügt über keine Adresse.");
            return;
        }

        KarteSuche.doExteneralSearch(kontakt.getAdresse(), CRMView.getInstance());

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
                int row = table_kontakte.rowAtPoint(point);
                table_kontakte.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void newKontakt() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        
        if (type == PRIVAT || type == FIRMA) {
            kontaktDialog = new KontakteDialog(mainFrame, true, kdnr);
        } else if (type == VERSICHERER) {
            kontaktDialog = new KontakteDialog(mainFrame, true, vers);
        } else if (type == VERTRAG) {
            kontaktDialog = new KontakteDialog(mainFrame, true, vtr);
        } else if (type == STOERFALL) {
            kontaktDialog = new KontakteDialog(mainFrame, true, stoer);
        } else if (type == SCHADEN) {
            kontaktDialog = new KontakteDialog(mainFrame, true, schaden);
        } else if (type == PRODUKT) {
            kontaktDialog = new KontakteDialog(mainFrame, true, prod);
        } else if (type == BENUTZER) {
//                kontakte = KontakteSQLMethods.(DatabaseConnection.open(), vers.getId(), getStatus());
        }
        
        kontaktDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(kontaktDialog);

        this.loadTable();
    }

    private void editKontakt() {
        int row = table_kontakte.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kontakt aus.");
            return;
        }

        KontaktObj kon = (KontaktObj) this.table_kontakte.getModel().getValueAt(row, 0);

        if (kon == null) {
            return;
        }
        
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        kontaktDialog = new KontakteDialog(mainFrame, true, kon);
        kontaktDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(kontaktDialog);

        this.loadTable();        
    }

    private void deleteKontakt() {
        int row = table_kontakte.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kontakt aus.");
            return;
        }

        KontaktObj kontakt = (KontaktObj) this.table_kontakte.getModel().getValueAt(row, 0);

        if (kontakt == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kontakt wirklich löschen?",
                    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            KontakteSQLMethods.deleteFromKontakte(DatabaseConnection.open(), kontakt.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Kontakt nicht löschen", e);
            ShowException.showException("Datenbankfehler: Der Kontakt konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kontakt nicht löschen");
        }

        this.loadTable();
    }

    private void archiveKontakt() {
        int row = table_kontakte.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kontakt aus.");
            return;
        }

        KontaktObj kontakt = (KontaktObj) this.table_kontakte.getModel().getValueAt(row, 0);

        if (kontakt == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kontakt wirklich archivieren?",
                    "Wirklich archivieren?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            KontakteSQLMethods.archiveFromKontakte(DatabaseConnection.open(), kontakt.getId());
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Kontakt nicht archivieren", e);
            ShowException.showException("Datenbankfehler: Der Kontakt konnte nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kontakt nicht archivieren");
        }

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
        grpDBStatus = new javax.swing.ButtonGroup();
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        editMenuItem = new javax.swing.JMenuItem();
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        karteMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        refreshMenuItem = new javax.swing.JMenuItem();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_kontakte = new org.jdesktop.swingx.JXTable();
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

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grpDBStatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelHinterlegteKontakte.class);
        alleDBMenuItem.setText(resourceMap.getString("alleDBMenuItem.text")); // NOI18N
        alleDBMenuItem.setToolTipText(resourceMap.getString("alleDBMenuItem.toolTipText")); // NOI18N
        alleDBMenuItem.setName("alleDBMenuItem"); // NOI18N
        alleDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alleDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(alleDBMenuItem);

        grpDBStatus.add(aktiveDBMenuItem);
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

        grpDBStatus.add(archivedDBMenuItem);
        archivedDBMenuItem.setMnemonic('A');
        archivedDBMenuItem.setText(resourceMap.getString("archivedDBMenuItem.text")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grpDBStatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('G');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        newMenuItem.setMnemonic('P');
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        tablePopupMenu.add(jSeparator1);

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

        jSeparator3.setName("jSeparator3"); // NOI18N
        tablePopupMenu.add(jSeparator3);

        karteMenuItem.setMnemonic('K');
        karteMenuItem.setText(resourceMap.getString("karteMenuItem.text")); // NOI18N
        karteMenuItem.setName("karteMenuItem"); // NOI18N
        karteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karteMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(karteMenuItem);

        jSeparator4.setName("jSeparator4"); // NOI18N
        tablePopupMenu.add(jSeparator4);

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

        table_kontakte.setModel(new javax.swing.table.DefaultTableModel(
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
        table_kontakte.setColumnControlVisible(true);
        table_kontakte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kontakte.setName("table_kontakte"); // NOI18N
        table_kontakte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kontakteMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_kontakte);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
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

    private void table_kontakteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kontakteMouseClicked

        if (evt.getClickCount() >= 2) {
            final int row = table_kontakte.getSelectedRow();

            if (row == -1) {
                return;
            }

            editKontakt();
        }
}//GEN-LAST:event_table_kontakteMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editKontakt();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveKontakt();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteKontakt();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        showKarte();
}//GEN-LAST:event_btnKarteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newKontakt();
    }//GEN-LAST:event_btnNeuActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newKontakt();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editKontakt();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveKontakt();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteKontakt();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void karteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karteMenuItemActionPerformed
        showKarte();
}//GEN-LAST:event_karteMenuItemActionPerformed

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
    private javax.swing.JButton btnKarte;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grpDBStatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JMenuItem karteMenuItem;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_kontakte;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
    private JDialog kontaktDialog;
}
