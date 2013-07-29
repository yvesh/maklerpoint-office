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
 * KundenDokumente.java
 *
 * Created on Jul 13, 2010, 4:46:28 PM
 */
package de.maklerpoint.office.Gui.Dokumente;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Dokumente.Tools.DokumentenSQLMethods;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Filesystem.FilesystemKunden;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.DokumentenRegistry;
import de.maklerpoint.office.Scanner.ScannerGetImage;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityRoles;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.FileCopy;
import de.maklerpoint.office.Tools.FileTools;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenDokumente extends javax.swing.JDialog {

    private KundenObj kunde;
    private FirmenObj firma;
    private String kundenDirectory;
    private String kennung;
    private Desktop desktop = Desktop.getDesktop();

    private class DateiColumn {

        private String name;
        private File file;
        private DokumentenObj dokument = null;

        public DateiColumn(String name, File file, DokumentenObj dok) {
            this.name = name;
            this.file = file;
            this.dokument = dok;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /** Creates new form KundenDokumente */
    public KundenDokumente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.dispose();
        initComponents();
    }

    public KundenDokumente(java.awt.Frame parent, boolean modal, KundenObj kunde) {
        super(parent, modal);
        this.kunde = kunde;
        this.kennung = kunde.getKundenNr();
        initComponents();
        this.setTitle("Dateien von " + kunde.getVorname() + " "
                + kunde.getNachname() + " [" + kunde.getKundenNr() + "]");
        setUp();
    }

    public KundenDokumente(java.awt.Frame parent, boolean modal, FirmenObj firma) {
        super(parent, modal);
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        initComponents();
        this.setTitle("Dateien von " + firma.getFirmenName() + " [" + firma.getKundenNr() + "]");
        setUp();
    }

    private void setUp() {
        this.addButtons();
        this.toolbarFile.setBackground(Color.white);
        this.toolbarFile.revalidate();
        this.setBackground(Color.white);
        this.repaint();
        setupRechte();
        this.loadDokumente(Config.get("vertragOrdner", "vertraege"));
    }

    public void addButtons() {
        addImportCommandButton();
    }

    public void setupRechte() {
        if (!Security.isAllowed(SecurityTasks.DOKUMENT_DELETE)) {
            this.btnDelete.setEnabled(false);
        }

        if (!Security.isAllowed(SecurityTasks.DOKUMENT_EDIT)) {
            this.btnEdit.setEnabled(false);
        }
    }

    /**
     * 
     */
    public void addFile() {
        String filePath = FileTools.openFile("Datei zum importieren wählen");

        if (filePath == null) {
            return;
        }

        File file = new File(filePath);

        String filename = file.getName();
        String ziel = this.getFolderPath(0);

        ziel = ziel + File.separatorChar + filename;

        File zielFile = new File(ziel);
        FileCopy copy = new FileCopy();
        copy.copy(file, zielFile);

        try {
            DokumentenSQLMethods.createKundenFile(DatabaseConnection.open(), zielFile, kennung);
        } catch (SQLException e) {
            Log.logger.fatal("Konnte Dateiinformationen nicht in der Datenbank speichern", e);
            ShowException.showException("Konnte Dateiinformationen nicht in der Datenbank speichern.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Dateiinformationen nicht speichern");
        } catch (IOException e) {
            Log.logger.fatal("Bei der Speicherung der Dateiinformationen trat ein Lese-/Schreib- Fehler auf", e);
            ShowException.showException("Bei der Speicherung der Dateiinformationen trat ein Lese-/Schreib- Fehler Fehler auf.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: IO / Fehler");
        }

        loadDokumente(this.getFolderPath(1));
    }

    /**
     *
     */
    private class ImportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton datei = new JCommandMenuButton("per Datei Dialog",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/file-manager.png"));
            datei.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    addFile();
                }
            });

            popupMenu.addMenuButton(datei);

            JCommandMenuButton scanner = new JCommandMenuButton("per Scanner (Via twain)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/scanner.png"));
            scanner.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String filename = FilesystemKunden.getKundenPath(kennung) + File.separatorChar
                            + Config.get("scanOrdner", "scan") + "scan01." + Config.get("scannerImageformat", "png");
                    try {
//                        new File(filename).createFile();
                        ScannerGetImage scan = new ScannerGetImage(filename);
//                        DokumentenSQLMethods.createFile(DatabaseConnection.open(), new File(filename), kunde.getId());
                    } catch (Exception ex) {
                        Log.logger.fatal("Konnte Datei nicht kopieren", ex);
                        ShowException.showException("Konnte Scanner nicht initialisieren. "
                                + "Bitte überprüfen Sie die Einstellungen",
                                ExceptionDialogGui.LEVEL_WARNING, ex, "Schwerwiegend: Konnte Scanner nicht initialisieren");
                    }
                }
            });

            popupMenu.addMenuButton(scanner);

            return popupMenu;
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    public void addImportCommandButton() {

        JCommandButton importButton = new JCommandButton("Import", getResizableIconFromResource(
                "de/acyrance/CRM/Gui/resources/import.png"));
        importButton.setExtraText("Datei Importieren");
        importButton.setPopupCallback(new ImportPopupCallback());
        importButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);

        RichTooltip tooltip = new RichTooltip("Datei importieren", "Klicken Sie hier um eine Datei zu dem Kunden hinzuzufügen. "
                + "Sie können diese entweder neu einscannen oder per Datei Dialog hinzufügen");

        importButton.setPopupRichTooltip(tooltip);

        importButton.setDisplayState(CommandButtonDisplayState.SMALL);
        importButton.setFlat(true);

        this.toolbarActions.add(importButton, 1);
    }

    /**
     *
     * @param which
     */
    public void loadDokumente(String which) {
        String path = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("File Path: " + path);
        }
        kundenDirectory = path;

        File folder = new File(path);
        long count = Filesystem.getFolderFileCount(folder);

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("File Count: " + count);
        }

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");

        if (count <= 0) {
            top = new DefaultMutableTreeNode("Keine Dateien");
            if (Log.logger.isDebugEnabled()) {
                Log.logger.debug("Keine Dateien im Kunden Ordner \"" + which + "\"");
            }
            DefaultMutableTreeNode show = new DefaultMutableTreeNode("Keine Dateien vorhanden");
            top.add(show);
        } else {

            File[] files = folder.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    if (Log.logger.isDebugEnabled()) {
                        Log.logger.debug("Die Datei ist ein Ordner, es sollten keine Unterordner "
                                + "im Kundenverzeichnis erstellt werden");
                    }
                } else {
                    try {
                        long checksum = FileTools.getChecksum(files[i].getPath());
                        DokumentenObj dokument = DokumentenSQLMethods.getDokument(DatabaseConnection.open(),
                                files[i].getName(), String.valueOf(checksum));
                        DefaultMutableTreeNode show = new DefaultMutableTreeNode(
                                new DateiColumn(files[i].getName(), files[i], dokument));
                        top.add(show);
                    } catch (SQLException e) {
                        Log.logger.fatal("Konnte die Datei Informationen für \""
                                + files[i].getName() + "\" nicht aus der Datenbank laden", e);
                        ShowException.showException("Konnte die Datei Informationen für \""
                                + files[i].getName() + "\" nicht aus der Datenbank laden.",
                                ExceptionDialogGui.LEVEL_WARNING, e, "Fehler: Konnte Dateiinformationen nicht laden");
                    }
                }
            }
        }

        tree_files.removeAll();

        DefaultTreeModel treeMod = (DefaultTreeModel) tree_files.getModel();
        treeMod.setRoot(top);
        treeMod.reload();

        tree_files.setRootVisible(false);
        tree_files.validate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttons = new javax.swing.ButtonGroup();
        panel_holder = new javax.swing.JPanel();
        toolbarFile = new javax.swing.JToolBar();
        btnVertraege = new javax.swing.JToggleButton();
        btnRechnung = new javax.swing.JToggleButton();
        btnVersicherung = new javax.swing.JToggleButton();
        btnBeratung = new javax.swing.JToggleButton();
        btnScans = new javax.swing.JToggleButton();
        btnVerlauf = new javax.swing.JToggleButton();
        btnSonstiges = new javax.swing.JToggleButton();
        panel_holdintern = new javax.swing.JPanel();
        toolbarActions = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        btnShowinfo = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnSearch = new javax.swing.JButton();
        panel_content = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree_files = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(KundenDokumente.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setBounds(new java.awt.Rectangle(0, 0, 636, 479));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        panel_holder.setBackground(resourceMap.getColor("panel_holder.background")); // NOI18N
        panel_holder.setName("panel_holder"); // NOI18N
        panel_holder.setLayout(new java.awt.BorderLayout());

        toolbarFile.setBackground(resourceMap.getColor("toolbarFile.background")); // NOI18N
        toolbarFile.setFloatable(false);
        toolbarFile.setRollover(true);
        toolbarFile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toolbarFile.setName("toolbarFile"); // NOI18N

        btnVertraege.setBackground(resourceMap.getColor("btnVertraege.background")); // NOI18N
        buttons.add(btnVertraege);
        btnVertraege.setIcon(resourceMap.getIcon("btnVertraege.icon")); // NOI18N
        btnVertraege.setMnemonic('V');
        btnVertraege.setSelected(true);
        btnVertraege.setText(resourceMap.getString("btnVertraege.text")); // NOI18N
        btnVertraege.setFocusable(false);
        btnVertraege.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVertraege.setName("btnVertraege"); // NOI18N
        btnVertraege.setPreferredSize(new java.awt.Dimension(100, 95));
        btnVertraege.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVertraege.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVertraegeActionPerformed(evt);
            }
        });
        toolbarFile.add(btnVertraege);

        btnRechnung.setBackground(resourceMap.getColor("btnRechnung.background")); // NOI18N
        buttons.add(btnRechnung);
        btnRechnung.setIcon(resourceMap.getIcon("btnRechnung.icon")); // NOI18N
        btnRechnung.setMnemonic('R');
        btnRechnung.setText(resourceMap.getString("btnRechnung.text")); // NOI18N
        btnRechnung.setFocusable(false);
        btnRechnung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRechnung.setName("btnRechnung"); // NOI18N
        btnRechnung.setPreferredSize(new java.awt.Dimension(100, 95));
        btnRechnung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRechnung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechnungActionPerformed(evt);
            }
        });
        toolbarFile.add(btnRechnung);

        btnVersicherung.setBackground(resourceMap.getColor("btnVersicherung.background")); // NOI18N
        buttons.add(btnVersicherung);
        btnVersicherung.setIcon(resourceMap.getIcon("btnVersicherung.icon")); // NOI18N
        btnVersicherung.setMnemonic('V');
        btnVersicherung.setText(resourceMap.getString("btnVersicherung.text")); // NOI18N
        btnVersicherung.setFocusable(false);
        btnVersicherung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVersicherung.setName("btnVersicherung"); // NOI18N
        btnVersicherung.setPreferredSize(new java.awt.Dimension(111, 95));
        btnVersicherung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVersicherung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVersicherungActionPerformed(evt);
            }
        });
        toolbarFile.add(btnVersicherung);

        btnBeratung.setBackground(resourceMap.getColor("btnBeratung.background")); // NOI18N
        buttons.add(btnBeratung);
        btnBeratung.setIcon(resourceMap.getIcon("btnBeratung.icon")); // NOI18N
        btnBeratung.setMnemonic('B');
        btnBeratung.setText(resourceMap.getString("btnBeratung.text")); // NOI18N
        btnBeratung.setFocusable(false);
        btnBeratung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBeratung.setName("btnBeratung"); // NOI18N
        btnBeratung.setPreferredSize(new java.awt.Dimension(100, 95));
        btnBeratung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBeratung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeratungActionPerformed(evt);
            }
        });
        toolbarFile.add(btnBeratung);

        btnScans.setBackground(resourceMap.getColor("btnScans.background")); // NOI18N
        buttons.add(btnScans);
        btnScans.setIcon(resourceMap.getIcon("btnScans.icon")); // NOI18N
        btnScans.setMnemonic('S');
        btnScans.setText(resourceMap.getString("btnScans.text")); // NOI18N
        btnScans.setFocusable(false);
        btnScans.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnScans.setName("btnScans"); // NOI18N
        btnScans.setPreferredSize(new java.awt.Dimension(105, 95));
        btnScans.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnScans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScansActionPerformed(evt);
            }
        });
        toolbarFile.add(btnScans);

        btnVerlauf.setBackground(resourceMap.getColor("btnVerlauf.background")); // NOI18N
        buttons.add(btnVerlauf);
        btnVerlauf.setIcon(resourceMap.getIcon("btnVerlauf.icon")); // NOI18N
        btnVerlauf.setMnemonic('V');
        btnVerlauf.setText(resourceMap.getString("btnVerlauf.text")); // NOI18N
        btnVerlauf.setFocusable(false);
        btnVerlauf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerlauf.setName("btnVerlauf"); // NOI18N
        btnVerlauf.setPreferredSize(new java.awt.Dimension(105, 95));
        btnVerlauf.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerlauf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerlaufActionPerformed(evt);
            }
        });
        toolbarFile.add(btnVerlauf);

        btnSonstiges.setBackground(resourceMap.getColor("btnSonstiges.background")); // NOI18N
        buttons.add(btnSonstiges);
        btnSonstiges.setIcon(resourceMap.getIcon("btnSonstiges.icon")); // NOI18N
        btnSonstiges.setMnemonic('S');
        btnSonstiges.setText(resourceMap.getString("btnSonstiges.text")); // NOI18N
        btnSonstiges.setFocusable(false);
        btnSonstiges.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSonstiges.setName("btnSonstiges"); // NOI18N
        btnSonstiges.setPreferredSize(new java.awt.Dimension(100, 95));
        btnSonstiges.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSonstiges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSonstigesActionPerformed(evt);
            }
        });
        toolbarFile.add(btnSonstiges);

        panel_holder.add(toolbarFile, java.awt.BorderLayout.PAGE_START);

        panel_holdintern.setName("panel_holdintern"); // NOI18N

        toolbarActions.setFloatable(false);
        toolbarActions.setOrientation(1);
        toolbarActions.setRollover(true);
        toolbarActions.setMinimumSize(new java.awt.Dimension(100, 25));
        toolbarActions.setName("toolbarActions"); // NOI18N
        toolbarActions.setOpaque(false);

        jSeparator1.setName("jSeparator1"); // NOI18N
        toolbarActions.add(jSeparator1);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbarActions.add(jSeparator3);

        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setToolTipText(resourceMap.getString("btnEdit.toolTipText")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        toolbarActions.add(btnEdit);

        btnShowinfo.setIcon(resourceMap.getIcon("btnShowinfo.icon")); // NOI18N
        btnShowinfo.setText(resourceMap.getString("btnShowinfo.text")); // NOI18N
        btnShowinfo.setToolTipText(resourceMap.getString("btnShowinfo.toolTipText")); // NOI18N
        btnShowinfo.setFocusable(false);
        btnShowinfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowinfo.setName("btnShowinfo"); // NOI18N
        btnShowinfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowinfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowinfoActionPerformed(evt);
            }
        });
        toolbarActions.add(btnShowinfo);

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setToolTipText(resourceMap.getString("btnArchive.toolTipText")); // NOI18N
        btnArchive.setFocusable(false);
        btnArchive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArchive.setName("btnArchive"); // NOI18N
        btnArchive.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        toolbarActions.add(btnArchive);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setToolTipText(resourceMap.getString("btnDelete.toolTipText")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        toolbarActions.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbarActions.add(jSeparator2);

        btnSearch.setIcon(resourceMap.getIcon("btnSearch.icon")); // NOI18N
        btnSearch.setToolTipText(resourceMap.getString("btnSearch.toolTipText")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarActions.add(btnSearch);

        panel_content.setBackground(resourceMap.getColor("panel_content.background")); // NOI18N
        panel_content.setAutoscrolls(true);
        panel_content.setName("panel_content"); // NOI18N
        panel_content.setPreferredSize(new java.awt.Dimension(600, 399));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportBorder(null);
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setOpaque(false);

        tree_files.setBorder(null);
        tree_files.setName("tree_files"); // NOI18N
        tree_files.setOpaque(false);
        tree_files.setShowsRootHandles(false);
        tree_files.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tree_filesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tree_files);

        javax.swing.GroupLayout panel_contentLayout = new javax.swing.GroupLayout(panel_content);
        panel_content.setLayout(panel_contentLayout);
        panel_contentLayout.setHorizontalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );
        panel_contentLayout.setVerticalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_holdinternLayout = new javax.swing.GroupLayout(panel_holdintern);
        panel_holdintern.setLayout(panel_holdinternLayout);
        panel_holdinternLayout.setHorizontalGroup(
            panel_holdinternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_holdinternLayout.createSequentialGroup()
                .addComponent(panel_content, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toolbarActions, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_holdinternLayout.setVerticalGroup(
            panel_holdinternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_content, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
            .addComponent(toolbarActions, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );

        panel_holder.add(panel_holdintern, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_holder, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_holder, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRechnungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechnungActionPerformed
        loadDokumente(Config.get("rechnungOrdner", "rechnungen"));
    }//GEN-LAST:event_btnRechnungActionPerformed

    private void btnVersicherungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVersicherungActionPerformed
        loadDokumente(Config.get("versicherungOrdner", "versicherung"));
    }//GEN-LAST:event_btnVersicherungActionPerformed

    private void btnScansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScansActionPerformed
        loadDokumente(Config.get("scanOrdner", "scan"));
    }//GEN-LAST:event_btnScansActionPerformed

    private void btnVerlaufActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerlaufActionPerformed
        loadDokumente(Config.get("historyOrdner", "history"));
    }//GEN-LAST:event_btnVerlaufActionPerformed

    private void btnVertraegeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVertraegeActionPerformed
        loadDokumente(Config.get("vertragOrdner", "vertraege"));
    }//GEN-LAST:event_btnVertraegeActionPerformed

    private void tree_filesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tree_filesMouseClicked
        int selRow = this.tree_files.getRowForLocation(evt.getX(), evt.getY());
        TreePath selPath = tree_files.getPathForLocation(evt.getX(), evt.getY());

        if (selRow == -1) {
            return;
        }

//        System.out.println("SelRow: " + selRow);

        if (evt.getClickCount() >= 2) {
//           DateiColumn datei = tree_files.getComponent(selRow).
            this.tree_files.setEnabled(false);
            Object[] paths = selPath.getPath();
            for (int i = 0; i < paths.length; i++) {
                Log.logger.debug("Path: " + i + " " + paths[i]);
                if (!paths[i].toString().equalsIgnoreCase("top") || !paths[i].toString().
                        equalsIgnoreCase("keine dateien vorhanden")) {
                    try {
                        String ziel = this.getFolderPath(0); // 0 = ziel

                        String path = ziel + File.separatorChar + paths[i].toString();
                        long checksum = FileTools.getChecksum(new File(path).getPath());
                        DokumentenObj dokument = DokumentenSQLMethods.getDokument(DatabaseConnection.open(),
                                paths[i].toString(), String.valueOf(checksum));
                        DokumentenSQLMethods.increaseViewcount(DatabaseConnection.open(), dokument);

                        File file = new File(path);
                        if (file.exists()) {
                            desktop.open(file);
                        } else {
                            System.out.println("ADD Exception: Datei existiert nicht");
                        }

                        Log.logger.debug("Ausgewählte Datei: \"" + paths[i].toString() + "\"");

                    } catch (Exception e) {
                        Log.logger.warn("Konnte Datei im externen Programm nicht öffnen", e);
                        ShowException.showException("Konnte Datei nicht mit dem Standard Programm öffnen.",
                                ExceptionDialogGui.LEVEL_WARNING, e, "Fehler: Konnte Datei nicht öffnen");
                    }
                }
            }
            this.tree_files.setEnabled(true);
        }
    }//GEN-LAST:event_tree_filesMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Object selected = tree_files.getLastSelectedPathComponent();

        if (selected == null) {
            return;
        }


        Log.logger.debug("selected: " + selected.toString());

        if (selected.toString().equalsIgnoreCase("keine dateien vorhanden")
                || selected.toString().equalsIgnoreCase("top") || selected == null) {
            return;
        }

        try {
            String path = getFolderPath(0) + File.separatorChar + selected.toString();
            desktop.open(new File(path));
        } catch (Exception e) {
            Log.logger.warn("Konnte Datei im externen Programm zum Bearbeiten nicht öffnen", e);
            ShowException.showException("Konnte Datei nicht zum bearbeiten öffnen.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Fehler: Konnte Datei nicht öffnen");
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Object selected = tree_files.getLastSelectedPathComponent();

        if (selected == null) {
            return;
        }

        Log.logger.debug("Zum löschen gewählte Datei: " + selected.toString());

        if (selected.toString().equalsIgnoreCase("keine dateien vorhanden")
                || selected.toString().equalsIgnoreCase("top") || selected == null) {
            return;
        }

        try {
            javax.swing.tree.DefaultMutableTreeNode df = (javax.swing.tree.DefaultMutableTreeNode) selected;
            DateiColumn dc = (DateiColumn) df.getUserObject();
            DokumentenObj dokument = dc.dokument;

            if (dokument != null) {
                DokumentenRegistry.deleteDokument(dokument);
            } else {
                String ziel = this.getFolderPath(0); // 0 = path

                String path = ziel + File.separatorChar + selected.toString();
                File file = new File(path);
                Log.databaselogger.warn("Das Dokument ist nicht in der Datenbank? "
                        + "Bitte starten Sie den Medienscanner " + selected.toString());
                String delfilep = FilesystemKunden.getKundenDeletedPath(kennung)
                        + File.separator + file.getName();
                File delfile = new File(delfilep);

                FileCopy fc = new FileCopy();
                fc.copy(file, delfile);

                file.delete();
            }

        } catch (Exception ex) {
            Log.databaselogger.fatal("Konnte die Datei Informationen für \""
                    + selected.toString() + "\" nicht aus der Datenbank laden", ex);
            ShowException.showException("Konnte die Datei Informationen für \""
                    + selected.toString() + "\" nicht aus der Datenbank laden.",
                    ExceptionDialogGui.LEVEL_WARNING, ex, "Fehler: Konnte Dateiinformationen nicht laden");
        }

        loadDokumente(this.getFolderPath(1)); // 1 = which
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSonstigesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSonstigesActionPerformed
        loadDokumente(Config.get("sonstigesOrdner", "sonstiges"));
    }//GEN-LAST:event_btnSonstigesActionPerformed

    private void btnBeratungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeratungActionPerformed
        loadDokumente(Config.get("bpOrdner", "beratungsprotokoll"));
}//GEN-LAST:event_btnBeratungActionPerformed

    private void btnShowinfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowinfoActionPerformed
        Object selected = tree_files.getLastSelectedPathComponent();

        if (selected == null) {
            return;
        }

        if (selected.toString().equalsIgnoreCase("keine dateien vorhanden")
                || selected.toString().equalsIgnoreCase("top")) {
            return;
        }

        DokumentenObj dok = null;

        javax.swing.tree.DefaultMutableTreeNode df =
                (javax.swing.tree.DefaultMutableTreeNode) selected;
        DateiColumn dc = (DateiColumn) df.getUserObject();
        dok = dc.dokument;

        if (dok == null) {
            // TODO Lösung finden e.g. neues Dokument
        }

        if (infodialog == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            infodialog = new DokumentenInfo(mainFrame, false, dok);
            infodialog.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(infodialog);
    }//GEN-LAST:event_btnShowinfoActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        Object selected = tree_files.getLastSelectedPathComponent();

        if (selected == null) {
            return;
        }

        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Zum archivieren gewählte Datei: " + selected.toString());
        }

        if (selected.toString().equalsIgnoreCase("keine dateien vorhanden")
                || selected.toString().equalsIgnoreCase("top") || selected == null) {
            return;
        }

        try {
            javax.swing.tree.DefaultMutableTreeNode df = (javax.swing.tree.DefaultMutableTreeNode) selected;
            DateiColumn dc = (DateiColumn) df.getUserObject();
            DokumentenObj dokument = dc.dokument;

            if (dokument != null) {
                DokumentenRegistry.archivDokument(dokument);
            } else {
                String ziel = this.getFolderPath(0); // 0 = path

                String path = ziel + File.separatorChar + selected.toString();
                File file = new File(path);

                Log.logger.warn("Das Dokument ist null (nicht in der Datenbank?) " + selected.toString());
                String archivefilep = FilesystemKunden.getKundenArchivePath(kennung) + File.separator + file.getName();
                File archivefile = new File(archivefilep);

                FileCopy fc = new FileCopy();
                fc.copy(file, archivefile);

                file.delete();
            }

        } catch (Exception ex) {
            Log.logger.fatal("Konnte die Datei Informationen für \"" + selected.toString()
                    + "\" nicht aus der Datenbank laden", ex);
            ShowException.showException("Konnte die Datei Informationen für \""
                    + selected.toString() + "\" nicht aus der Datenbank laden.",
                    ExceptionDialogGui.LEVEL_WARNING, ex, "Fehler: Konnte Dateiinformationen nicht laden");
        }

        loadDokumente(this.getFolderPath(1)); // 1 = which
    }//GEN-LAST:event_btnArchiveActionPerformed

    public String getFolderPath(int df) {
        String which = null;
        String ziel = null;

        if (btnVertraege.isSelected()) {
            which = Config.get("vertragOrdner", "vertraege");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (btnRechnung.isSelected()) {
            which = Config.get("rechnungOrdner", "rechnungen");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (btnVersicherung.isSelected()) {
            which = Config.get("versicherungOrdner", "versicherung");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (btnScans.isSelected()) {
            which = Config.get("scanOrdner", "scan");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (btnVerlauf.isSelected()) {
            which = Config.get("historyOrdner", "history");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (btnSonstiges.isSelected()) {
            which = Config.get("sonstigesOrdner", "sonstiges");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else if (this.btnBeratung.isSelected()) {
            which = Config.get("bpOrdner", "beratungsprotokoll");
            ziel = FilesystemKunden.getKundenPath(kennung) + File.separatorChar + which;
        } else {
            Log.logger.warn("Der ausgewählte Ordner existiert nicht");
            return null;
        }

        if (df == 0) {
            return ziel;
        } else {
            return which;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                KundenDokumente dialog = new KundenDokumente(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnArchive;
    public javax.swing.JToggleButton btnBeratung;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JToggleButton btnRechnung;
    public javax.swing.JToggleButton btnScans;
    public javax.swing.JButton btnSearch;
    public javax.swing.JButton btnShowinfo;
    public javax.swing.JToggleButton btnSonstiges;
    public javax.swing.JToggleButton btnVerlauf;
    public javax.swing.JToggleButton btnVersicherung;
    public javax.swing.JToggleButton btnVertraege;
    public javax.swing.ButtonGroup buttons;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JToolBar.Separator jSeparator1;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator3;
    public javax.swing.JPanel panel_content;
    public javax.swing.JPanel panel_holder;
    public javax.swing.JPanel panel_holdintern;
    public javax.swing.JToolBar toolbarActions;
    public javax.swing.JToolBar toolbarFile;
    public javax.swing.JTree tree_files;
    // End of variables declaration//GEN-END:variables
    private JDialog infodialog;
}
