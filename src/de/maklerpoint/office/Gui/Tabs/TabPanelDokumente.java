/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelDokumente.java
 *
 * Created on 01.06.2011, 16:46:16
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Dokumente.Tools.DokumentenSQLMethods;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemKunden;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.DokumentenRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Scanner.ScannerGetImage;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.FileCopy;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelDokumente extends javax.swing.JPanel implements iTabs {

    /** Creates new form TabPanelDokumente */
    public TabPanelDokumente() {
        initComponents();
        addButtons();
    }
    public static final int KUNDE = 0;
    public static final int GESCHAEFT = 1;
    public static final int VERSICHERER = 2;
    public static final int BENUTZER = 3;
    public static final int PRODUKT = 4;
    public static final int VERTRAG = 5;
    public static final int STOERFALL = 6;
    public static final int SCHADEN = 7;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private ProduktObj produkt = null;
    private VertragObj vertrag = null;
    private StoerfallObj stoer = null;
    private SchadenObj schaden = null;
    private String kdnr = null;
    private String[] kundenColumn = {"Hidden", "Typ", "Erstellt von", "Dateiname", "Dateipfad",
        "Beschreibung", "Markierung", "Erstellt am"};
    private String[] nonkundenColumn = {"Hidden", "Typ", "Kunde", "Erstellt von", "Dateiname", "Dateipfad",
        "Beschreibung", "Markierung", "Erstellt am"};

    public String getTabName() {
        return ("Dokumente");
    }

    public void load(KundenObj kunde) {
        this.type = KUNDE;
        this.kunde = kunde;
        kdnr = kunde.getKundenNr();
        importButton.setPopupCallback(new ImportKundenPopupCallback());
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.type = GESCHAEFT;
        this.firma = firma;
        kdnr = firma.getKundenNr();
        importButton.setPopupCallback(new ImportKundenPopupCallback());
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
        this.type = VERTRAG;
        this.vertrag = vertrag;
        this.kdnr = vertrag.getKundenKennung();
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        this.type = STOERFALL;
        this.stoer = stoerfall;
        this.kdnr = stoer.getKundenNr();
        loadTable();
    }

    public void load(SchadenObj schaden) {
        this.type = SCHADEN;
        this.schaden = schaden;
        this.kdnr = schaden.getKundenNr();
        loadTable();
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            DokumentenObj[] doks = null;

            if (type == KUNDE || type == GESCHAEFT) {
                doks = DokumentenSQLMethods.loadKundenDokumente(DatabaseConnection.open(), kdnr, getStatus());
            } else if (type == BENUTZER) {
                doks = DokumentenSQLMethods.loadBenutzerDokumente(DatabaseConnection.open(), benutzer.getId(), getStatus());
            } else if (type == VERSICHERER) {
                doks = DokumentenSQLMethods.loadVersichererDokumente(DatabaseConnection.open(), versicherer.getId(), getStatus());
            } else if (type == PRODUKT) {
                doks = DokumentenSQLMethods.loadVersichererDokumente(DatabaseConnection.open(), produkt.getId(), getStatus());
            } else if (type == VERTRAG) {
                doks = DokumentenSQLMethods.loadVersichererDokumente(DatabaseConnection.open(), vertrag.getId(), getStatus());
            } else if (type == SCHADEN) {
                doks = DokumentenSQLMethods.loadSchadenDokumente(DatabaseConnection.open(), schaden.getId(), getStatus());
            } else if (type == STOERFALL) {
                doks = DokumentenSQLMethods.loadStoerfaelleDokumente(DatabaseConnection.open(), stoer.getId(), getStatus());
            }

            if (doks != null) {
                if (type == KUNDE || type == GESCHAEFT) {
                    data = new Object[doks.length][8];
                } else {
                    data = new Object[doks.length][9];
                }

                for (int i = 0; i < doks.length; i++) {
                    data[i][0] = doks[i];
                    ImageIcon icon = FileTypes.UNKNOWN_IMAGE;

                    if (doks[i].getFiletype() != -1) {
                        icon = FileTypes.TYPE_IMAGES[doks[i].getFiletype()];
                    }

                    icon.setDescription(FileTypes.getName(doks[i].getFiletype()));

                    data[i][1] = icon;

                    if (type == KUNDE || type == GESCHAEFT) {
                        data[i][2] = BenutzerRegistry.getBenutzer(doks[i].getCreatorId());
                        data[i][3] = doks[i].getName();
                        data[i][4] = doks[i].getFullPath();
                        data[i][5] = doks[i].getBeschreibung();
                        data[i][6] = doks[i].getTag();
                        data[i][7] = df.format(doks[i].getCreated());

                    } else {
                        data[i][2] = KundenRegistry.getKunde(doks[i].getKundenKennung());
                        data[i][3] = BenutzerRegistry.getBenutzer(doks[i].getCreatorId());
                        data[i][4] = doks[i].getName();
                        data[i][5] = doks[i].getFullPath();
                        data[i][6] = doks[i].getBeschreibung();
                        data[i][7] = doks[i].getTag();
                        data[i][8] = df.format(doks[i].getCreated());
                    }
                }
            }
            if (type == GESCHAEFT || type == KUNDE) {
                setTable(data, kundenColumn);
            } else {
                setTable(data, nonkundenColumn);
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Die Dokumente konnten nicht geladen werden.", e);
            ShowException.showException("Datenbankfehler: Die Dokumente konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dokumente nicht laden");
        }
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {

        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_dokumente.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_dokumente.getModel();
            atm.setData(data);
            table_dokumente.packAll();
            return;
        }

        table_dokumente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_dokumente.setColumnSelectionAllowed(false);
        table_dokumente.setCellSelectionEnabled(false);
        table_dokumente.setRowSelectionAllowed(true);
        table_dokumente.setAutoCreateRowSorter(true);

        table_dokumente.setFillsViewportHeight(true);
        table_dokumente.removeColumn(table_dokumente.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_dokumente.addMouseListener(popupListener);
        table_dokumente.setColumnControlVisible(true);

        JTableHeader header = table_dokumente.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_dokumente.packAll();
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
                int row = table_dokumente.rowAtPoint(point);
                table_dokumente.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void disableElements() {
        this.btnArchive.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.table_dokumente.setEnabled(false);
        this.importButton.setEnabled(false);
        this.dropDownButton.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        setTable(null, kundenColumn);
    }

    public void enableElements() {
        this.btnArchive.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.table_dokumente.setEnabled(true);
        this.dropDownButton.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.importButton.setEnabled(true);
    }

    private void addButtons() {
        // Import Button
        importButton = new JCommandButton("Import", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/import.png"));
        importButton.setExtraText("Datei Importieren");

        if (type == KUNDE || type == GESCHAEFT) {
            importButton.setPopupCallback(new ImportKundenPopupCallback());
        }

        importButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);

        RichTooltip tooltip = new RichTooltip("Datei hinzufügen", "Klicken Sie hier um eine Datei hinzuzufügen. "
                + "Die können Sie entweder per Scan oder per Datei Dialog hinzufügen");

        importButton.setPopupRichTooltip(tooltip);

        importButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        importButton.setFlat(true);

        this.toolbar.add(importButton, 0);

        // Ansicht Button

        dropDownButton = DropDownButtonFactory.createDropDownButton(
                ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Dokumente Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);

        if (!Security.isAllowed(SecurityTasks.DOKUMENT_DELETE)) {
            this.btnDelete.setEnabled(false);
            this.deleteMenuItem.setEnabled(false);
        }

        if (!Security.isAllowed(SecurityTasks.DOKUMENT_ARCHIVE)) {
            this.btnArchive.setEnabled(false);
            this.archiveMenuItem.setEnabled(false);
        }

        if (!Security.isAllowed(SecurityTasks.DOKUMENT_EDIT)) {
            this.btnEdit.setEnabled(false);
            this.editMenuItem.setEnabled(false);
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    private void searchTable() {
        int result = table_dokumente.getSearchable().search(field_search.getText());
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

    private class ImportKundenPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton datei = new JCommandMenuButton("per Datei Dialog",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/file-manager.png"));
            datei.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    addKundenFile();
                }
            });

            popupMenu.addMenuButton(datei);

            JCommandMenuButton scanner = new JCommandMenuButton("per Scanner (Via twain)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/scanner.png"));
            scanner.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String filename = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                            + Config.get("scanOrdner", "scan") + "scan-" + df.format(System.currentTimeMillis())
                            + Config.get("scannerImageformat", "png");
                    try {
//                        new File(filename).createFile();
                        ScannerGetImage scan = new ScannerGetImage(filename);
//                        DokumentenSQLMethods.createFile(DatabaseConnection.open(), new File(filename), kunde.getId());
                    } catch (Exception ex) {
                        Log.logger.fatal("Konnte Datei nicht kopieren", ex);
                        ShowException.showException("Konnte Scanner nicht initialisieren. Bitte überprüfen Sie die Einstellungen",
                                ExceptionDialogGui.LEVEL_WARNING, ex, "Schwerwiegend: Konnte Scanner nicht initialisieren");
                    }
                }
            });

            popupMenu.addMenuButton(scanner);

            return popupMenu;
        }
    }

    public void addKundenFile() {
        String filePath = FileTools.openFile("Datei zum importieren wählen"); // TODO In Dialog ändern

        if (filePath == null) {
            return;
        }

        File file = new File(filePath);

        String[] result = filePath.split(File.separator);
        String filename = result[result.length - 1];
        String ziel = FilesystemKunden.getKundenPath(kdnr);


        ziel = ziel + File.separatorChar + filename;

        File zielFile = new File(ziel);
        FileCopy copy = new FileCopy();
        copy.copy(file, zielFile);

        try {
            DokumentenSQLMethods.createKundenFile(DatabaseConnection.open(), zielFile, kdnr);
        } catch (SQLException e) {
            Log.logger.fatal("Konnte Dateiinformationen nicht in der Datenbank speichern", e);
            ShowException.showException("Konnte Dateiinformationen nicht in der Datenbank speichern.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dateiinformationen nicht speichern");
        } catch (IOException e) {
            Log.logger.fatal("Bei der Speicherung der Datei trat ein Lese-/Schreib- Fehler auf", e);
            ShowException.showException("Bei der Speicherung der Dateiinformationen trat ein Lese-/Schreib- Fehler Fehler auf.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: IO / Fehler");
        }

        loadTable();
    }

    private void editDokument() {
    }

    private void archiveDokument() {
        int row = table_dokumente.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Dokument aus.");
            return;
        }

        DokumentenObj dok = (DokumentenObj) table_dokumente.getModel().getValueAt(row, 0);

        if (dok == null) {
            return;
        }

        DokumentenRegistry.archivDokument(dok);

        loadTable();
    }

    private void deleteDokument() {
        int row = table_dokumente.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Dokument aus.");
            return;
        }

        DokumentenObj dok = (DokumentenObj) table_dokumente.getModel().getValueAt(row, 0);

        if (dok == null) {
            return;
        }

        DokumentenRegistry.deleteDokument(dok);

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
        editMenuItem = new javax.swing.JMenuItem();
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        toolbar = new javax.swing.JToolBar();
        btnEdit = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_dokumente = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelDokumente.class);
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
        btnRefresh.setText(resourceMap.getString("btnRefresh.text")); // NOI18N
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

        table_dokumente.setModel(new javax.swing.table.DefaultTableModel(
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
        table_dokumente.setColumnControlVisible(true);
        table_dokumente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_dokumente.setName("table_dokumente"); // NOI18N
        table_dokumente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_dokumenteMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_dokumente);
        table_dokumente.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("table_dokumente.columnModel.title0")); // NOI18N
        table_dokumente.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table_dokumente.columnModel.title1")); // NOI18N
        table_dokumente.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table_dokumente.columnModel.title2")); // NOI18N
        table_dokumente.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table_dokumente.columnModel.title3")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveDokument();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteDokument();
}//GEN-LAST:event_btnDeleteActionPerformed

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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 2) {
            searchTable();
        }
    }//GEN-LAST:event_field_searchKeyTyped

    private void table_dokumenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_dokumenteMouseClicked
        if (evt.getClickCount() > 1) {
            editDokument();
        }
    }//GEN-LAST:event_table_dokumenteMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editDokument();
    }//GEN-LAST:event_btnEditActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editDokument();
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveDokument();
    }//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteDokument();
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
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_dokumente;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    JCommandButton importButton;
    JButton dropDownButton;
}
