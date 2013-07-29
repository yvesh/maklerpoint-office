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
package de.maklerpoint.office.Gui;

import com.bric.swing.toolbar.CustomizedToolbar;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.roots.map.MapPanel;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.External.iReport;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Filesystem.FilesystemMediaScanner;
import de.maklerpoint.office.Gui.Backup.AutoBackupDialog;
import de.maklerpoint.office.Gui.Backup.BackupDialog;
import de.maklerpoint.office.Gui.Benutzer.NeuerBenutzerAssistent;
import de.maklerpoint.office.Gui.Benutzer.PanelBenutzerUebersicht;
import de.maklerpoint.office.Gui.Beratungsprotokoll.BeratungsprotokollHelper;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.Configuration.ConfigurationDialog;
import de.maklerpoint.office.Gui.Configuration.TagDialog;
import de.maklerpoint.office.Gui.Email.SendEmailDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht;
import de.maklerpoint.office.Gui.Import.KundenImportDialog;
import de.maklerpoint.office.Gui.Kalender.NeueAufgabe;
import de.maklerpoint.office.Gui.Kalender.NeuerTermin;
import de.maklerpoint.office.Gui.Kalender.panelKalenderHolder;
import de.maklerpoint.office.Gui.Karte.Karte;
import de.maklerpoint.office.Gui.Kunden.NewKundeHelper;
import de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht;
import de.maklerpoint.office.Gui.Leftpane.panelAdressbuch;
import de.maklerpoint.office.Gui.Leftpane.panelHilfe;
import de.maklerpoint.office.Gui.Leftpane.panelKarte;
import de.maklerpoint.office.Gui.Leftpane.panelShortcuts;
import de.maklerpoint.office.Gui.Leftpane.panelUebersicht;
import de.maklerpoint.office.Gui.Log.LogPanel;
import de.maklerpoint.office.Gui.Marketing.GeburtstagslistePanel;
import de.maklerpoint.office.Gui.Marketing.NewsletterPanel;
import de.maklerpoint.office.Gui.Nachrichten.BenutzerNachrichtenJxPanel;
import de.maklerpoint.office.Gui.Notizen.NotizenDialogHelper;
import de.maklerpoint.office.Gui.OfflineMode.GoOfflineDialog;
import de.maklerpoint.office.Gui.Schaden.PanelSchaeden;
import de.maklerpoint.office.Gui.Sparten.SpartenDialog;
import de.maklerpoint.office.Gui.Stammdaten.StammdatenDialog;
import de.maklerpoint.office.Gui.Startpage.JxPanelStartpage;
import de.maklerpoint.office.Gui.Stoerfall.PanelStoerfaelle;
import de.maklerpoint.office.Gui.Suche.PanelSuche;
import de.maklerpoint.office.Gui.TextbauSteine.TextbausteinDialogHelper;
import de.maklerpoint.office.Gui.Tools.KundenAuswahlHelper;
import de.maklerpoint.office.Gui.Tools.SQLExecutorDialog;
import de.maklerpoint.office.Gui.Uebersicht.UebersichtPanel;
import de.maklerpoint.office.Gui.Versicherer.PanelProduktUebersicht;
import de.maklerpoint.office.Gui.Versicherer.PanelVersichererUebersicht;
import de.maklerpoint.office.Gui.Vertraege.PanelVertraege;
import de.maklerpoint.office.Gui.Waehrungen.WaehrungenDialog;
import de.maklerpoint.office.Gui.Wissendokumente.WissendokumenteDialogHelper;
import de.maklerpoint.office.Konstanten.MPointKonstanten;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.LocalDatabase.LocalDatabaseTools;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Tools.DigitalClock;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.Session.Tools.SessionTools;
import de.maklerpoint.office.Startup.BenutzerTasks;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.System.Update;
import de.maklerpoint.office.System.Version;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
import de.acyrance.licensor.Config.LicenseConfig;
import de.acyrance.licensor.Gui.LicenseInformationDialog;
import de.acyrance.licensor.License;
import de.schlichtherle.io.FileInputStream;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.EventObject;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.noos.xing.mydoggy.ToolWindow;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.ToolWindowManager;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import org.jdesktop.application.Application.ExitListener;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.ContentManagerUIListener;
import org.noos.xing.mydoggy.DockedTypeDescriptor;
import org.noos.xing.mydoggy.FloatingTypeDescriptor;
import org.noos.xing.mydoggy.SlidingTypeDescriptor;
import org.noos.xing.mydoggy.TabbedContentManagerUI;
import org.noos.xing.mydoggy.ToolWindowType;
import org.noos.xing.mydoggy.event.ContentManagerUIEvent;

/**
 * The application's main frame.
 */
public class CRMView extends FrameView {

    public static boolean open = false;
    public static MyDoggyToolWindowManager toolWindowManager = null;
    private Desktop desktop = Desktop.getDesktop();
    public static CRMView crmview = null;
    private org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
            de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(CRMView.class);

    private void setUp() {
        this.configureUI(null);
        initComponents();
        setWindowTitle();
        addDoggyWindowListener();
        CRMView.super.getFrame().setLayout(new BorderLayout());
        initToolWindowManager();
        initializeToolbarButtons();
        onlinepostMenuItem.setEnabled(false);
    }

    public static CRMView getInstance() {        
        return crmview;
    }

//    private void start() {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
//                ToolWindow uebersichtTool = toolWindowManager.getToolWindow("Überblick");
//                uebersichtTool.setActive(true);
//                uebersichtTool.setSelected(true);
//                uebersichtTool.setVisible(true);                
//                System.out.println("Started");
//                MyDoggyTestApp.getApplication().getMainView().getFrame().setVisible(true);
//            }
//        });
//    }
    public void addDoggyWindowListener() {
        CRMView.super.getFrame().addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                try {
                    File workspaceFile = new File("workspace.xml");
                    if (workspaceFile.exists()) {
                        FileInputStream inputStream = new FileInputStream("workspace.xml");
                        toolWindowManager.getPersistenceDelegate().apply(inputStream);
                        inputStream.close();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream output = new FileOutputStream("workspace.xml");
                    toolWindowManager.getPersistenceDelegate().save(output);
                    output.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public CRMView(SingleFrameApplication app) {
        super(app);
        open = true;
        this.getApplication().addExitListener(new ExitListener() {

            public boolean canExit(EventObject eo) {
                return true;
            }

            public void willExit(EventObject eo) {
                open = false;
            }
        });
        try {
            String val203O44 = License.validate(LicenseConfig.get("licensor", ""), LicenseConfig.get("licenseKey", ""));
            if (!val203O44.equals("faf4s!l3s")) {
                Log.logger.fatal("Fehler beim verifiziereren der Lizenz. Beende MaklerPoint Office.");
                System.exit(108);
            }
        } catch (Exception e) {
            Log.logger.fatal("Fehler beim überprüfen des Lizenzkeys. Beende MaklerPoint Office.", e);
            System.exit(632);
        }
        setUp();

//        CRM.getApplication().getMainFrame().getContentPane().setLayout(new TableLayout(new double[][]{{0, -1, 0}, {0, -1, 0}}));
//        initToolWindowManager();
//        ToolWindow debugTool = toolWindowManager.getToolWindow("Debug");
//        debugTool.setActive(true);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");

        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {

                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();


                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());

                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                }
            }
        });

        crmview = this;
    }

    /**
     * 
     * @param uiTheme
     */
    public final void configureUI(String uiTheme) {
        UIManager.put(Options.USE_SYSTEM_FONTS_KEY, Boolean.TRUE);
        Options.setDefaultIconSize(new Dimension(18, 18));

        if (uiTheme == null) {
            uiTheme = UIManager.getSystemLookAndFeelClassName();
        }

//            uiTheme = LookUtils.IS_OS_WINDOWS_XP ? Options.getCrossPlatformLookAndFeelClassName()
//                    : Options.getSystemLookAndFeelClassName();

        try {
            UIManager.setLookAndFeel(uiTheme);
//            SwingUtilities.updateComponentTreeUI(super.getComponent());
            SwingUtilities.updateComponentTreeUI(CRMView.super.getFrame());
//            SwingUtilities.updateComponentTreeUI(CRMView.super.getRootPane().getJMenuBar());
        } catch (Exception ex) {
            Log.logger.fatal("Fehler: Konnte das Theme \"" + uiTheme + "\" nicht aktivieren", ex);
            ShowException.showException("Konnte das graphische Theme nicht aktivieren. Eventuell"
                    + " unterstützt Ihr Betriebssystem das gewählte Theme nicht.",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Theme nicht aktivieren");

        }
    }

    private void setupLeftToolWindow(String name) {
        ToolWindow debugTool = toolWindowManager.getToolWindow(name);

        DockedTypeDescriptor dockedTypeDescriptor = (DockedTypeDescriptor) debugTool.getTypeDescriptor(ToolWindowType.DOCKED);

        dockedTypeDescriptor.setDockLength(250);
        dockedTypeDescriptor.setPopupMenuEnabled(true);
        JMenu toolsMenu = dockedTypeDescriptor.getToolsMenu();
//        toolsMenu.add(new AbstractAction("Hello World!!!") {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(frame, "Hello World!!!");
//            }
//        });
//        dockedTypeDescriptor.setToolWindowActionHandler(new ToolWindowActionHandler() {
//            public void onHideButtonClick(ToolWindow toolWindow) {
//                JOptionPane.showMessageDialog(frame, "Hiding...");
//                toolWindow.setVisible(false);
//            }
//        });
        dockedTypeDescriptor.setAnimating(true);
//        dockedTypeDescriptor.setPreviewEnabled(true);
//        dockedTypeDescriptor.setPreviewDelay(1000);
//        dockedTypeDescriptor.setPreviewTransparentRatio(0.4f);

        SlidingTypeDescriptor slidingTypeDescriptor = (SlidingTypeDescriptor) debugTool.getTypeDescriptor(ToolWindowType.SLIDING);
        slidingTypeDescriptor.setEnabled(false);
        slidingTypeDescriptor.setTransparentMode(true);
        slidingTypeDescriptor.setTransparentRatio(0.8f);
        slidingTypeDescriptor.setTransparentDelay(0);
        slidingTypeDescriptor.setAnimating(true);

        FloatingTypeDescriptor floatingTypeDescriptor = (FloatingTypeDescriptor) debugTool.getTypeDescriptor(ToolWindowType.FLOATING);
        floatingTypeDescriptor.setEnabled(true);
        floatingTypeDescriptor.setLocation(150, 200);
        floatingTypeDescriptor.setSize(220, 200);
        floatingTypeDescriptor.setModal(false);
        floatingTypeDescriptor.setTransparentMode(true);
        floatingTypeDescriptor.setTransparentRatio(0.2f);
        floatingTypeDescriptor.setTransparentDelay(1000);
        floatingTypeDescriptor.setAnimating(true);
    }

    /**
     * 
     * @param id 
     */
    private void showHelp(String id) {
        JHelp helpViewer = null;
        try {
            ClassLoader cl = CRMView.class.getClassLoader();
            URL url = HelpSet.findHelpSet(cl, "mainset.hs");
            helpViewer = new JHelp(new HelpSet(cl, url));
            helpViewer.setCurrentID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame helpFrame = new JFrame();
        helpFrame.setTitle("MaklerPoint Office Hilfe");
        helpFrame.setIconImage(MPointKonstanten.icon);
        helpFrame.setSize(800, 600);
        helpFrame.getContentPane().add(helpViewer);
        helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helpFrame.setVisible(true);
    }

    /**
     * 
     */
    private void addToolWindows() {
        // Register a Tool.
        if (Log.logger.isDebugEnabled()) {
            toolWindowManager.registerToolWindow("Debug", // Id
                    "Debug Tool", // Title
                    null, // Icon
                    new JButton("Debug Tool"), // Component
                    getToolWindowPos(Config.getInt("debugPosToolwindow", 0)));       // Anchor

            setupLeftToolWindow("Debug");
        }

        if (Config.getConfigBoolean("ueberblickToolwindow", true)) {

            toolWindowManager.registerToolWindow("Überblick",
                    "Übersicht",
                    resourceMap.getIcon("uebersichtLeft.icon"),
                    new panelUebersicht(),
                    getToolWindowPos(Config.getInt("ueberblickPosToolwindow", 0)));

            setupLeftToolWindow("Überblick");
        }

        if (Config.getConfigBoolean("schnellzugriffToolwindow", true)) {
            toolWindowManager.registerToolWindow("Schnellzugriff",
                    "Schnellzugriff",
                    resourceMap.getIcon("shortcutsLeft.icon"),
                    new panelShortcuts(),
                    getToolWindowPos(Config.getInt("schnellzugriffPosToolwindow", 0)));


            setupLeftToolWindow("Schnellzugriff");
        }


        if (Config.getConfigBoolean("adressbuchToolwindow", true)) {
            toolWindowManager.registerToolWindow("Adressbuch",
                    "Adressbuch",
                    resourceMap.getIcon("adressbuchToolwindow.icon"),
                    new panelAdressbuch(this),
                    getToolWindowPos(Config.getInt("adressbuchPosToolwindow", 0)));

            setupLeftToolWindow("Adressbuch");
        }

        toolWindowManager.registerToolWindow("Karte (Suche)",
                "Karte (Suche)",
                resourceMap.getIcon("karteLeft.icon"),
                new panelKarte(this),
                getToolWindowPos(Config.getInt("kartePosToolwindow", 0)));

        setupLeftToolWindow("Karte (Suche)");

        if (Config.getConfigBoolean("hilfeToolwindow", true)) {
            toolWindowManager.registerToolWindow("Hilfe",
                    "Hilfe",
                    resourceMap.getIcon("hilfeLeft.icon"),
                    new panelHilfe(),
                    getToolWindowPos(Config.getInt("hilfePosToolwindow", 0)));

            setupLeftToolWindow("Hilfe");
        }


        // Made all tools available
        for (ToolWindow window : toolWindowManager.getToolWindows()) {
            window.setAvailable(true);
        }
    }

    private ToolWindowAnchor getToolWindowPos(int pos) {
        if (pos == 0) {
            return ToolWindowAnchor.LEFT;
        } else if (pos == 1) {
            return ToolWindowAnchor.RIGHT;
        } else if (pos == 2) {
            return ToolWindowAnchor.TOP;
        } else if (pos == 3) {
            return ToolWindowAnchor.BOTTOM;
        } else {
            return ToolWindowAnchor.LEFT; // Fallback
        }
    }

    /**
     * Seitenleiste
     * TODO irgendwann merge with addToolWindows
     */
    private void initToolWindowManager() {
        // Create a new instance of MyDoggyToolWindowManager passing the frame.
        if (CRMView.toolWindowManager == null) {
            MyDoggyToolWindowManager myDoggyToolWindowManager = new MyDoggyToolWindowManager();
            CRMView.toolWindowManager = myDoggyToolWindowManager;
        }

        // Register a Tool.
        if (Log.logger.isDebugEnabled()) {
            toolWindowManager.registerToolWindow("Debug", // Id
                    "Debug Tool", // Title
                    null, // Icon
                    new JButton("Debug Tool"), // Component
                    getToolWindowPos(Config.getInt("debugPosToolwindow", 0)));       // Anchor

            setupLeftToolWindow("Debug");
        }

        if (Config.getConfigBoolean("ueberblickToolwindow", true)) {
            toolWindowManager.registerToolWindow("Überblick",
                    "Übersicht",
                    resourceMap.getIcon("uebersichtLeft.icon"),
                    new panelUebersicht(),
                    getToolWindowPos(Config.getInt("ueberblickPosToolwindow", 0)));

            setupLeftToolWindow("Überblick");
        }


        if (Config.getConfigBoolean("schnellzugriffToolwindow", true)) {
            toolWindowManager.registerToolWindow("Schnellzugriff",
                    "Schnellzugriff",
                    resourceMap.getIcon("shortcutsLeft.icon"),
                    new panelShortcuts(),
                    getToolWindowPos(Config.getInt("schnellzugriffPosToolwindow", 0)));


            setupLeftToolWindow("Schnellzugriff");
        }

        if (Config.getConfigBoolean("adressbuchToolwindow", true)) {
            toolWindowManager.registerToolWindow("Adressbuch",
                    "Adressbuch",
                    resourceMap.getIcon("adressbuchToolwindow.icon"),
                    new panelAdressbuch(this),
                    getToolWindowPos(Config.getInt("adressbuchPosToolwindow", 0)));

            setupLeftToolWindow("Adressbuch");
        }

        toolWindowManager.registerToolWindow("Karte (Suche)",
                "Karte (Suche)",
                resourceMap.getIcon("karteLeft.icon"),
                new panelKarte(this),
                getToolWindowPos(Config.getInt("kartePosToolwindow", 0)));

        setupLeftToolWindow("Karte (Suche)");


        if (Config.getConfigBoolean("hilfeToolwindow", true)) {
            toolWindowManager.registerToolWindow("Hilfe",
                    "Hilfe",
                    resourceMap.getIcon("hilfeLeft.icon"),
                    new panelHilfe(),
                    getToolWindowPos(Config.getInt("hilfePosToolwindow", 0)));

            setupLeftToolWindow("Hilfe");
        }

        // Made all tools available
        for (ToolWindow window : toolWindowManager.getToolWindows()) {
            window.setAvailable(true);
        }

        initContentManager();

//        CRMView.super.getFrame().getContentPane().removeAll();
        CRMView.super.getFrame().add(toolBar, BorderLayout.NORTH);
        CRMView.super.getFrame().add(statusPanel, BorderLayout.SOUTH);
        CRMView.super.getFrame().add(toolWindowManager, BorderLayout.CENTER);
    }

    /**
     * 
     */
    protected void initContentManager() {
        setupContentManagerUI();
        toolWindowManager.getContentManager().addContent("start",
                "Startseite",
                ImageTools.createImageIcon(ResourceStrings.STARTSEITE_ICON), // An icon
                new JxPanelStartpage());

        setDoggyContent(toolWindowManager.getContentManager().getContent("start"));
    }

    /**
     * Content Manager
     */
    protected void setupContentManagerUI() {
        // By default a TabbedContentManagerUI is installed.
        TabbedContentManagerUI contentManagerUI = (TabbedContentManagerUI) toolWindowManager.getContentManager().getContentManagerUI();
        contentManagerUI.setShowAlwaysTab(true);
        contentManagerUI.setMaximizable(false);

        int mpos = Config.getConfigInt("contentposMydoggy", 0);

        if (mpos == 0) {
            contentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.BOTTOM);
        } else if (mpos == 1) {
            contentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.TOP);
        } else if (mpos == 2) {
            contentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.LEFT);
        } else if (mpos == 3) {
            contentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.RIGHT);
        }

        contentManagerUI.addContentManagerUIListener(new ContentManagerUIListener() {

            public boolean contentUIRemoving(ContentManagerUIEvent event) {
                if (Config.getConfigBoolean("closeConfirm", true)) {
                    return JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen?")
                            == JOptionPane.OK_OPTION;
                } else {
                    return true;
                }
            }

            public void contentUIDetached(ContentManagerUIEvent event) {
//                JOptionPane.showMessageDialog(frame, "Hello World!!!");
            }
        });

//        TabbedContentUI contentUI = (TabbedContentUI) toolWindowManager.getContentManager().getContent(0).getContentUI();
//        // Or you can use :
//        // TabbedContentUI contentUI = contentManagerUI.getContentUI(toolWindowManager.getContentManager().getContent(0));
//        // without the need of the cast
//
//        contentUI.setCloseable(true);
//        contentUI.setDetachable(true);
//        contentUI.setTransparentMode(true);
//        contentUI.setMaximizable(false);        
//        contentUI.setTransparentRatio(0.7f);
//        contentUI.setTransparentDelay(1000);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            aboutBox = new CRMAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(aboutBox);
    }

    private void loadKarte() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                if (BasicRegistry.isInternetAvailable() == false) {
                    JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung zu den "
                            + "OpenStreetMap Servern hergestellt werden. \n"
                            + "Bitte überprüfen Sie Ihre Internetverbindung.");
                    return;
                }

                if (toolWindowManager.getContentManager().getContent("map") == null) {
                    MapPanel map = Karte.getMap(6.94, 50.95, 10);
                    toolWindowManager.getContentManager().addContent("map",
                            "Karte",
                            ImageTools.createImageIcon(ResourceStrings.KARTE_ICON), // An icon
                            map);
                    setDoggyContent(toolWindowManager.getContentManager().getContent("map"));

                    ToolWindow karteTool = toolWindowManager.getToolWindow("Karte (Suche)");
                    karteTool.setActive(true);
                    karteTool.setSelected(true);
                    karteTool.setVisible(true);
                }

                toolWindowManager.getContentManager().getContent("map").setSelected(true);
            }
        });
    }

    private void initializeToolbarButtons() {

        btnKarte = new JButton();
        btnNeuTermin = new JButton();
        btnNeuAufgabe = new JButton();
        btnHilfe = new JButton();
        btnBeratungsdokumentation = new JButton();
        btnSendMail = new JButton();
        btnNewsletter = new JButton();
        btnSerienbrief = new JButton();
        btnSicherung = new JButton();
        btnBenutzerMail = new JButton();
        btnNotizen = new JButton();
        btnSettings = new JButton();
        btnWaehrungen = new JButton();
        btnSparten = new JButton();
        btnTextbausteine = new JButton();
        btnTags = new JButton();

        field_search = new JTextField();
        btnSearch = new JButton();

        // Search

        label_search = new JLabel();
        label_search.setText("Suche ");
        label_search.setIcon(ImageTools.createImageIcon(ResourceStrings.SEARCH_ICON));

        label_search_in = new JLabel();
        label_search_in.setText(" in ");

        Object[] searchbereiche = {"Allen Bereichen", "Kunden", "Privatkunden", "Geschäftskunden",
            "Versicherungen", "Produkte", "Verträge", "Dokumenten", "Benutzer", "Termine",
            "Aufgaben", "Störfällen", "Schäden"
        };

        combo_search = new JComboBox();
        combo_search.setModel(new DefaultComboBoxModel(searchbereiche));

        btnSearch.setText(""); // NOI18N
        btnSearch.setIcon(ImageTools.createImageIcon(ResourceStrings.SEARCH_BTN_ICON));
        btnSearch.setContentAreaFilled(false);
        btnSearch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (toolWindowManager.getContentManager().getContent("suche") != null) {
                    toolWindowManager.getContentManager().removeContent(sucheCont);
                }

                sucheCont = toolWindowManager.getContentManager().addContent("suche",
                        "Suche",
                        null, // An icon
                        new PanelSuche(field_search.getText()));

                toolWindowManager.getContentManager().getContent("suche").setSelected(true);
            }
        });

        field_search.setText("");
        field_search.setPreferredSize(new Dimension(150, 24));

        // Neu Button

        neuButton = new JCommandButton("Neuer Kunde", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/add.png"));
        neuButton.setExtraText("Neuer Privat- oder Geschäftskunde");
        neuButton.setName("neuButton");
        neuButton.setPopupCallback(new NeuToolbarPopupCallback());
        neuButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
        neuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        neuButton.setFlat(true);
        neuButton.setOpaque(false);

        neuButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                NewKundeHelper.openNewKundeBox();
            }
        });

        btnNeuTermin.setIcon(ImageTools.createImageIcon(ResourceStrings.TERMIN_ICON)); // NOI18N
        btnNeuTermin.setText("Neuer Termin"); // NOI18N
        btnNeuTermin.setName("btnNeuTermin");
        btnNeuTermin.setFocusable(false);
        btnNeuTermin.setContentAreaFilled(false);
        btnNeuTermin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNeuTermin.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuTermin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuTermin.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                terminDialog = new NeuerTermin(mainFrame, false, new Date(System.currentTimeMillis()));
                terminDialog.setLocationRelativeTo(mainFrame);
                CRM.getApplication().show(terminDialog);
            }
        });

        btnNeuAufgabe.setIcon(ImageTools.createImageIcon(ResourceStrings.AUFGABE_ICON)); // NOI18N
        btnNeuAufgabe.setText("Neue Aufgabe"); // NOI18N
        btnNeuAufgabe.setFocusable(false);
        btnNeuAufgabe.setContentAreaFilled(false);
        btnNeuAufgabe.setName("btnNeuAufgabe");
        btnNeuAufgabe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNeuAufgabe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuAufgabe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuAufgabe.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                aufgabenDialog = new NeueAufgabe(mainFrame, false);
                aufgabenDialog.setLocationRelativeTo(mainFrame);
                CRM.getApplication().show(aufgabenDialog);
            }
        });

        btnKarte.setIcon(ImageTools.createImageIcon(ResourceStrings.KARTE_ICON)); // NOI18N
        btnKarte.setText("Karte"); // NOI18N
        btnKarte.setFocusable(false);
        btnKarte.setContentAreaFilled(false);
        btnKarte.setName("btnKarte");
        btnKarte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnKarte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKarte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKarte.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadKarte();
            }
        });

        btnHilfe.setIcon(ImageTools.createImageIcon(ResourceStrings.HILFE_ICON)); // NOI18N
        btnHilfe.setText("Hilfe"); // NOI18N
        btnHilfe.setFocusable(false);
        btnHilfe.setContentAreaFilled(false);
        btnHilfe.setName("btnHilfe");
        btnHilfe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnHilfe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnHilfe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHilfe.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHelp("intro_html");
            }
        });

        btnBeratungsdokumentation.setIcon(ImageTools.createImageIcon(ResourceStrings.BERATUNG_ICON)); // NOI18N
        btnBeratungsdokumentation.setText("Neue Beratungsdokumentation"); // NOI18N
        btnBeratungsdokumentation.setFocusable(false);
        btnBeratungsdokumentation.setContentAreaFilled(false);
        btnBeratungsdokumentation.setName("btnBeratungsdokumentation");
        btnBeratungsdokumentation.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnBeratungsdokumentation.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBeratungsdokumentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBeratungsdokumentation.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Object kundeobj = KundenAuswahlHelper.getKunde();

                if (kundeobj == null) {
                    return;
                }

                try {
                    KundenObj kunde = (KundenObj) kundeobj;
                    BeratungsprotokollHelper.open(kunde);
                } catch (Exception e) {
                    FirmenObj kunde = (FirmenObj) kundeobj;
                    BeratungsprotokollHelper.open(kunde);
                }
            }
        });
        btnSendMail.setIcon(ImageTools.createImageIcon(ResourceStrings.SENDMAIL_ICON)); // NOI18N
        btnSendMail.setText("Neue E-Mail"); // NOI18N
        btnSendMail.setFocusable(false);
        btnSendMail.setContentAreaFilled(false);
        btnSendMail.setName("btnSendMail");
        btnSendMail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSendMail.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSendMail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSendMail.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mailDialog == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    mailDialog = new SendEmailDialog(mainFrame, false);
                    mailDialog.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(mailDialog);
            }
        });

        btnNewsletter.setIcon(ImageTools.createImageIcon(ResourceStrings.NEWSLETTER_ICON)); // NOI18N
        btnNewsletter.setText("Newsletter"); // NOI18N
        btnNewsletter.setFocusable(false);
        btnNewsletter.setContentAreaFilled(false);
        btnNewsletter.setName("btnNewsletter");
        btnNewsletter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNewsletter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNewsletter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewsletter.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (toolWindowManager.getContentManager().getContent("newsletter") == null) {
                    toolWindowManager.getContentManager().addContent("newsletter",
                            "Newsletter",
                            null, // An icon
                            new NewsletterPanel());
                }

                toolWindowManager.getContentManager().getContent("newsletter").setSelected(true);
            }
        });
        btnSerienbrief.setIcon(ImageTools.createImageIcon(ResourceStrings.SERIENBRIEF_ICON)); // NOI18N
        btnSerienbrief.setText("Serienbrief"); // NOI18N
        btnSerienbrief.setFocusable(false);
        btnSerienbrief.setContentAreaFilled(false);
        btnSerienbrief.setName("btnSerienbrief");
        btnSerienbrief.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSerienbrief.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSerienbrief.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSerienbrief.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO            
            }
        });

        btnSicherung.setIcon(ImageTools.createImageIcon(ResourceStrings.SICHERUNG_ICON)); // NOI18N
        btnSicherung.setText("Sicherung"); // NOI18N
        btnSicherung.setFocusable(false);
        btnSicherung.setContentAreaFilled(false);
        btnSicherung.setName("btnSicherung");
        btnSicherung.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSicherung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSicherung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSicherung.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (backupBox == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    backupBox = new BackupDialog(mainFrame, false);
                    backupBox.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(backupBox);
            }
        });

        btnBenutzerMail.setIcon(ImageTools.createImageIcon(ResourceStrings.BENUTZERMAIL_ICON)); // NOI18N
        btnBenutzerMail.setText("Benutzernachrichten"); // NOI18N
        btnBenutzerMail.setFocusable(false);
        btnBenutzerMail.setContentAreaFilled(false);
        btnBenutzerMail.setName("btnBenutzerMail");
        btnBenutzerMail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnBenutzerMail.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBenutzerMail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBenutzerMail.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (toolWindowManager.getContentManager().getContent("benutzernachrichten") == null) {
                    toolWindowManager.getContentManager().addContent("benutzernachrichten",
                            "BenutzerNachrichten",
                            null, // An icon
                            new BenutzerNachrichtenJxPanel());
                }

                toolWindowManager.getContentManager().getContent("benutzernachrichten").setSelected(true);
            }
        });

        btnNotizen.setIcon(ImageTools.createImageIcon(ResourceStrings.NOTIZEN_ICON)); // NOI18N
        btnNotizen.setText("Notizen"); // NOI18N
        btnNotizen.setName("btnNotizen");
        btnNotizen.setFocusable(false);
        btnNotizen.setContentAreaFilled(false);
        btnNotizen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNotizen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNotizen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNotizen.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotizenDialogHelper.openNotizen();
            }
        });

        btnSettings.setIcon(ImageTools.createImageIcon(ResourceStrings.SETTINGS_ICON)); // NOI18N
        btnSettings.setText("Einstellungen"); // NOI18N
        btnSettings.setName("btnSettings");
        btnSettings.setFocusable(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSettings.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (settingsBox == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    settingsBox = new ConfigurationDialog(mainFrame, false);
                    settingsBox.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(settingsBox);
            }
        });

        btnWaehrungen.setIcon(ImageTools.createImageIcon(ResourceStrings.WAEHRUGNEN_ICON)); // NOI18N
        btnWaehrungen.setText("Währungen"); // NOI18N
        btnWaehrungen.setName("btnWaehrungen");
        btnWaehrungen.setFocusable(false);
        btnWaehrungen.setContentAreaFilled(false);
        btnWaehrungen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnWaehrungen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnWaehrungen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnWaehrungen.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (waehrungenDialog == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    waehrungenDialog = new WaehrungenDialog(mainFrame, false);
                    waehrungenDialog.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(waehrungenDialog);
            }
        });

        btnSparten.setIcon(ImageTools.createImageIcon(ResourceStrings.SPARTEN_ICON)); // NOI18N
        btnSparten.setText("Sparten"); // NOI18N
        btnSparten.setFocusable(false);
        btnSparten.setContentAreaFilled(false);
        btnSparten.setName("btnSparten");
        btnSparten.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSparten.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSparten.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSparten.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (spartenDialog == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    spartenDialog = new SpartenDialog(mainFrame, false);
                    spartenDialog.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(spartenDialog);
            }
        });

        btnTextbausteine.setIcon(ImageTools.createImageIcon(ResourceStrings.TEXTBAUSTEINE_ICON)); // NOI18N
        btnTextbausteine.setText("Textbausteine"); // NOI18N
        btnTextbausteine.setFocusable(false);
        btnTextbausteine.setContentAreaFilled(false);
        btnTextbausteine.setName("btnTextbausteine");
        btnTextbausteine.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnTextbausteine.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTextbausteine.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTextbausteine.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextbausteinDialogHelper.openTb();
            }
        });

        btnTags.setIcon(ImageTools.createImageIcon(ResourceStrings.TAGS_ICON)); // NOI18N       
        btnTags.setText("Markierungen"); // NOI18N
        btnTags.setFocusable(false);
        btnTags.setContentAreaFilled(false);
        btnTags.setName("btnTags");
        btnTags.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnTags.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTags.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTags.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tagSettingsBox == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    tagSettingsBox = new TagDialog(mainFrame, false);
                    tagSettingsBox.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(tagSettingsBox);
            }
        });

        FlowLayout experimentLayout = new FlowLayout();

        search = new JPanel();
        search.setOpaque(false);
        search.setLayout(experimentLayout);
        search.setName("search");
        search.add(label_search);
        search.add(field_search);
        //search.add(label_search_in);
        //search.add(combo_search);
        search.add(btnSearch);

//        JButton customize = new JButton("Customize...");
//        customize.setOpaque(false);
//        customize.setName("customize");

        javax.swing.JComponent[] comps = {neuButton, btnNeuTermin, btnNeuAufgabe, btnKarte, btnHilfe,
            btnBeratungsdokumentation, btnSendMail, btnNewsletter, btnSerienbrief, btnSicherung,
            btnBenutzerMail, btnNotizen, btnSettings, btnWaehrungen, btnSparten, btnTextbausteine,
            search
        };

//        System.out.println("Neu Button name: " + neuButton.getName());
//        System.out.println("Sendmail name: " + btnSendMail.getName());

//        CustomizedToolbar.resetAllPreferences();

        String[] deft = {"neuButton", "-", "btnNeuTermin", "btnNeuAufgabe", "-", "btnSendMail", "-", "btnKarte", "-",
            "btnHilfe", "-", "\t", "search"
        };

        final CustomizedToolbar cstool = new CustomizedToolbar(comps, deft, "toolbarCustom");


        ActionListener showCustomizeAction = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cstool.displayDialog(280);
            }
        };

        JMenuItem customizeItem = new JMenuItem("Toolbar Einstellungen");
        this.windowMenu.add(customizeItem, 0);

        customizeItem.addActionListener(showCustomizeAction);


        this.toolBar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.toolBar.add(cstool, c);

    }

    private void checkForUpdate() {
        if (updatesearch) {
            return;
        }

        updatesearch = true;

        SwingWorker updatesearcher = new SwingWorker<Void, Void>() {

            public Void doInBackground() {
                Update up = new Update();
                boolean update = up.check();

                if (BasicRegistry.internetAvailable == false) {
                    JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung zum MaklerPoint "
                            + "Office Server hergestellt werden.\n"
                            + "Bitte überprüfen Sie Ihre Internetverbindung.");
                    return null;
                }

                if (update == false) {
                    JOptionPane.showMessageDialog(null, "Es sind keine neuen Updates für MaklerPoint Office vorhanden.");
                    return null;
                }

                if (update == true) {
                    int opt = JOptionPane.showConfirmDialog(null, "Es wurde ein neues Update für MaklerPoint Office gefunden."
                            + "\nSoll das Update jetzt installiert werden?", "Bestätigung: Update installieren",
                            JOptionPane.YES_NO_CANCEL_OPTION);

                    if (opt != JOptionPane.YES_OPTION) {
                        return null;
                    }

                    String app = "platform" + File.separatorChar + "lib" + File.separatorChar + "mp_update.jar ";
                    String options = "-current " + Version.version + " -newvers "
                            + BasicRegistry.updateVersion + " -changelog " + BasicRegistry.updateChangelogURL
                            + " -updateurl " + BasicRegistry.updateFilepath + " -updatesize " + BasicRegistry.updateSize
                            + " -checksum " + BasicRegistry.updateMd5;

                    String run = "java -jar " + app + options;

//                    System.out.println("Run: " + run);

                    try {
                        Process proc = Runtime.getRuntime().exec(run);
                        InputStream stderr = proc.getErrorStream();
                        InputStreamReader isr = new InputStreamReader(stderr);
                        BufferedReader br = new BufferedReader(isr);
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        int exitVal = proc.waitFor();
                        System.out.println("Process exitValue: " + exitVal);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

//                    System.exit(303);

                }


                return null;
            }

            @Override
            protected void done() {
                updatesearch = false;
            }
        };

        updatesearcher.execute();
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */
    public boolean sameDay(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */
    public boolean afterToday(Calendar c1, Calendar c2) {

        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }

    private void setDoggyContent(Content content) {
        content.getContentUI().setMaximizable(false);
    }

    // Public adding functions
    public void addToolStartseite() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("start") == null) {
                    toolWindowManager.getContentManager().addContent("start",
                            "Startseite",
                            ImageTools.createImageIcon(ResourceStrings.STARTSEITE_ICON), // An icon
                            new JxPanelStartpage());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("start"));
                }
                toolWindowManager.getContentManager().getContent("start").setSelected(true);
            }
        });
    }

    public void addToolUebersicht() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("uebersicht") == null) {
                    toolWindowManager.getContentManager().addContent("uebersicht",
                            "Übersicht",
                            ImageTools.createImageIcon(ResourceStrings.UEBERSICHT_ICON), // An icon
                            new UebersichtPanel());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("uebersicht"));
                }
                toolWindowManager.getContentManager().getContent("uebersicht").setSelected(true);
            }
        });
    }

    public void addToolKalender() {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(System.currentTimeMillis()));
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);

                JPanel kalenderHolder = new panelKalenderHolder(new Date(cal.getTimeInMillis()), getThis());
                if (toolWindowManager.getContentManager().getContent("kalender") == null) {
                    toolWindowManager.getContentManager().addContent("kalender",
                            "Kalender",
                            ImageTools.createImageIcon(ResourceStrings.KALENDER_ICON), // An icon
                            kalenderHolder);

                    setDoggyContent(toolWindowManager.getContentManager().getContent("kalender"));
                }
                toolWindowManager.getContentManager().getContent("kalender").setSelected(true);
            }
        });
    }

    public void addToolKarte() {
        loadKarte();
    }

    public void addToolPK() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("kunden") == null) {
                    toolWindowManager.getContentManager().addContent("kunden",
                            "Privatkunden",
                            ImageTools.createImageIcon(ResourceStrings.PRIVATKUNDEN_ICON), // An icon
                            new PanelKundenUebersicht(getThis()));

                    setDoggyContent(toolWindowManager.getContentManager().getContent("kunden"));
                }

                toolWindowManager.getContentManager().getContent("kunden").setSelected(true);
            }
        });
    }

    public void addToolGK() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("fkunden") == null) {

                    toolWindowManager.getContentManager().addContent("fkunden",
                            "Geschäftskunden",
                            ImageTools.createImageIcon(ResourceStrings.GESCHAEFTSKUNDEN_ICON), // An icon
                            new PanelFirmenKundenUebersicht(getThis()));

                    setDoggyContent(toolWindowManager.getContentManager().getContent("fkunden"));
                }

                toolWindowManager.getContentManager().getContent("fkunden").setSelected(true);
            }
        });
    }

    public void addToolStoer() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("stoerfaelle") == null) {
                    toolWindowManager.getContentManager().addContent("stoerfaelle",
                            "Störfälle",
                            ImageTools.createImageIcon(ResourceStrings.STOERFAELLE_ICON), // An icon
                            new PanelStoerfaelle());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("stoerfaelle"));
                }

                toolWindowManager.getContentManager().getContent("stoerfaelle").setSelected(true);
            }
        });
    }

    public void addToolSchaden() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("schaeden") == null) {
                    toolWindowManager.getContentManager().addContent("schaeden",
                            "Schadensfälle",
                            ImageTools.createImageIcon(ResourceStrings.SCHAEDEN_ICON), // An icon
                            new PanelSchaeden());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("schaeden"));
                }

                toolWindowManager.getContentManager().getContent("schaeden").setSelected(true);
            }
        });
    }

    public void addToolGesellschaften() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("vs") == null) {
                    toolWindowManager.getContentManager().addContent("vs",
                            "Gesellschaften",
                            ImageTools.createImageIcon(ResourceStrings.GESELLSCHAFTEN_ICON), // An icon
                            new PanelVersichererUebersicht(getThis()));

                    setDoggyContent(toolWindowManager.getContentManager().getContent("vs"));
                }

                toolWindowManager.getContentManager().getContent("vs").setSelected(true);
            }
        });
    }

    public void addToolProdukte() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("produkte") == null) {
                    toolWindowManager.getContentManager().addContent("produkte",
                            "Produkte",
                            ImageTools.createImageIcon(ResourceStrings.PRODUKTE_ICON), // An icon
                            new PanelProduktUebersicht());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("produkte"));
                }

                toolWindowManager.getContentManager().getContent("produkte").setSelected(true);
            }
        });
    }

    public void addToolVertr() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("vertraege") == null) {
                    toolWindowManager.getContentManager().addContent("vertraege",
                            "Verträge",
                            ImageTools.createImageIcon(ResourceStrings.VERTRAEGE_ICON), // An icon
                            new PanelVertraege());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("vertraege"));
                }

                toolWindowManager.getContentManager().getContent("vertraege").setSelected(true);
            }
        });
    }

    public void addToolBenutzer() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("benutzer") == null) {
                    toolWindowManager.getContentManager().addContent("benutzer",
                            "Benutzer",
                            ImageTools.createImageIcon(ResourceStrings.BENUTZER_ICON), // An icon
                            new PanelBenutzerUebersicht(getThis()));

                    setDoggyContent(toolWindowManager.getContentManager().getContent("benutzer"));
                }

                toolWindowManager.getContentManager().getContent("benutzer").setSelected(true);
            }
        });
    }

    public void addToolLog() {
        if (toolWindowManager.getContentManager().getContent("log") == null) {
            toolWindowManager.getContentManager().addContent("log",
                    "Protokoll",
                    ImageTools.createImageIcon(ResourceStrings.LOG_ICON), // An icon
                    new LogPanel());

            setDoggyContent(toolWindowManager.getContentManager().getContent("log"));
        }

        toolWindowManager.getContentManager().getContent("log").setSelected(true);
    }

    public void addToolGeburtstag() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("geburtstagsliste") == null) {
                    toolWindowManager.getContentManager().addContent("geburtstagsliste",
                            "Geburtstagsliste",
                            ImageTools.createImageIcon(ResourceStrings.GEBURTSTAGSLISTE_ICON), // An icon
                            new GeburtstagslistePanel());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("geburtstagsliste"));
                }

                toolWindowManager.getContentManager().getContent("geburtstagsliste").setSelected(true);
            }
        });
    }

    public void addToolNewsletter() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("newsletter") == null) {

                    toolWindowManager.getContentManager().addContent("newsletter",
                            "Newsletter",
                            ImageTools.createImageIcon(ResourceStrings.NEWSLETTER_ICON), // An icon
                            new NewsletterPanel());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("newsletter"));
                }

                toolWindowManager.getContentManager().getContent("newsletter").setSelected(true);
            }
        });
    }

    public void addToolSerienBrief() {
    }

    public void openWaehrungenDialog() {
        if (waehrungenDialog == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            waehrungenDialog = new WaehrungenDialog(mainFrame, false);
            waehrungenDialog.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(waehrungenDialog);
    }

    public void openTagsDialog() {
        if (tagSettingsBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tagSettingsBox = new TagDialog(mainFrame, false);
            tagSettingsBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(tagSettingsBox);
    }

    public void openSicherungDialog() {
        if (Security.isAllowed(SecurityTasks.BACKUP)) {
            if (backupBox == null) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                backupBox = new BackupDialog(mainFrame, false);
                backupBox.setLocationRelativeTo(mainFrame);
            }
            CRM.getApplication().show(backupBox);
        }
    }

    public void showChangelogDialog() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        changelogDialog = new ChangelogDialog(mainFrame, false);
        changelogDialog.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(changelogDialog);
    }

    public void openSparteDialog() {
        if (spartenDialog == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            spartenDialog = new SpartenDialog(mainFrame, false);
            spartenDialog.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(spartenDialog);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        offlineCheckMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        kundenImportMenuItem = new javax.swing.JMenuItem();
        kundenImportMenuItem1 = new javax.swing.JMenuItem();
        versichererImportMenuItem = new javax.swing.JMenuItem();
        produktImportMenuItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        startseiteMenuItem = new javax.swing.JMenuItem();
        uebersichtItem = new javax.swing.JMenuItem();
        kalenderMenuItem = new javax.swing.JMenuItem();
        kartenItem = new javax.swing.JMenuItem();
        privatkundenItem = new javax.swing.JMenuItem();
        firmenkundenMenuItem = new javax.swing.JMenuItem();
        stoerfaelleMenuItem = new javax.swing.JMenuItem();
        schaedenMenuItem = new javax.swing.JMenuItem();
        versichererMenuItem = new javax.swing.JMenuItem();
        produkteMenuItem = new javax.swing.JMenuItem();
        vertraegeMenuItem = new javax.swing.JMenuItem();
        benutzerManagementMenuItem = new javax.swing.JMenuItem();
        extrasMenu = new javax.swing.JMenu();
        beratungsdokumentationMenuItem = new javax.swing.JMenuItem();
        finanzanalyseMenuItem = new javax.swing.JMenuItem();
        inkassMenuItem = new javax.swing.JMenuItem();
        rechnungMenuItem = new javax.swing.JMenuItem();
        onlinepostMenuItem = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        sendmailMenuItem = new javax.swing.JMenuItem();
        notizenMenuItem = new javax.swing.JMenuItem();
        mahnungenMenuItem = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        sicherungMenuItem = new javax.swing.JMenuItem();
        autoSicherungMenuItem = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        MedenScannerMenuItem = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        benutzerNachrichtenMenuItem = new javax.swing.JMenuItem();
        neuerBenutzerMenuItem = new javax.swing.JMenuItem();
        marketingMenu = new javax.swing.JMenu();
        vmportalMenuItem = new javax.swing.JMenuItem();
        geburtstagslisteMenuItem = new javax.swing.JMenuItem();
        newsletterMenuItem = new javax.swing.JMenuItem();
        serienbriefMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        uiMenuItem = new javax.swing.JMenu();
        standardThemeMenuItem = new javax.swing.JRadioButtonMenuItem();
        motifMenuItem = new javax.swing.JRadioButtonMenuItem();
        plasticMenuItem = new javax.swing.JRadioButtonMenuItem();
        metalMenuItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        briefmailMenuItem = new javax.swing.JMenuItem();
        briefvorlagenMenuItem = new javax.swing.JMenuItem();
        faxvorlagenMenuItem = new javax.swing.JMenuItem();
        emailvorlagenMenuItem = new javax.swing.JMenuItem();
        wissendokumenteVorlagen = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        reportMenuItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        textbausteineMenuItem = new javax.swing.JMenuItem();
        tagSettingsMenuItem = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        checkdbMenuItem = new javax.swing.JMenuItem();
        scriptMenu = new javax.swing.JMenu();
        databaseSQLMenuItem = new javax.swing.JMenuItem();
        sqlskriptMenuItem = new javax.swing.JMenuItem();
        maklerpointskriptMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        spartenMenuItem = new javax.swing.JMenuItem();
        waehrungMenuItem = new javax.swing.JMenuItem();
        stammdatenMenuItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        shortcutSettingsMenuItem = new javax.swing.JMenuItem();
        settingsMenuItem = new javax.swing.JMenuItem();
        windowMenu = new javax.swing.JMenu();
        checkToolbarItem = new javax.swing.JCheckBoxMenuItem();
        checkLeftBar = new javax.swing.JCheckBoxMenuItem();
        checkStatusBarItem = new javax.swing.JCheckBoxMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        HelpMenuItem = new javax.swing.JMenuItem();
        changelogMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        onlineHelpMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        fehlerProtokollMenuItem = new javax.swing.JMenuItem();
        checkupdateMenuItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        lizenzInfoMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        panel_advertising = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panel_database = new javax.swing.JPanel();
        label_database = new javax.swing.JLabel();
        panel_user = new javax.swing.JPanel();
        label_username = new javax.swing.JLabel();
        panel_date = new javax.swing.JPanel();
        label_datetime = new javax.swing.JLabel();
        panel_icons = new javax.swing.JPanel();
        poplb_database = new javax.swing.JLabel();
        poplb_mail = new javax.swing.JLabel();
        poplb_update = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        grpThemes = new javax.swing.ButtonGroup();
        popupDatabase = new javax.swing.JPopupMenu();
        synchronizeLocalMenuItem = new javax.swing.JMenuItem();
        resetLocalMenuItem = new javax.swing.JCheckBoxMenuItem();
        popupUpdate = new javax.swing.JPopupMenu();
        checkforupdateMenuItem = new javax.swing.JMenuItem();

        mainPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(CRMView.class);
        fileMenu.setBackground(resourceMap.getColor("fileMenu.background")); // NOI18N
        fileMenu.setMnemonic('D');
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N

        offlineCheckMenuItem.setMnemonic('O');
        offlineCheckMenuItem.setText(resourceMap.getString("offlineCheckMenuItem.text")); // NOI18N
        offlineCheckMenuItem.setToolTipText(resourceMap.getString("offlineCheckMenuItem.toolTipText")); // NOI18N
        offlineCheckMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offlineCheckMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(offlineCheckMenuItem);
        fileMenu.add(jSeparator6);

        kundenImportMenuItem.setMnemonic('P');
        kundenImportMenuItem.setText(resourceMap.getString("kundenImportMenuItem.text")); // NOI18N
        kundenImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kundenImportMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(kundenImportMenuItem);

        kundenImportMenuItem1.setMnemonic('G');
        kundenImportMenuItem1.setText(resourceMap.getString("kundenImportMenuItem1.text")); // NOI18N
        fileMenu.add(kundenImportMenuItem1);

        versichererImportMenuItem.setMnemonic('G');
        versichererImportMenuItem.setText(resourceMap.getString("versichererImportMenuItem.text")); // NOI18N
        fileMenu.add(versichererImportMenuItem);

        produktImportMenuItem.setMnemonic('P');
        produktImportMenuItem.setText(resourceMap.getString("produktImportMenuItem.text")); // NOI18N
        fileMenu.add(produktImportMenuItem);
        fileMenu.add(jSeparator17);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getActionMap(CRMView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setMnemonic('B');
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setToolTipText(resourceMap.getString("exitMenuItem.toolTipText")); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        viewMenu.setBackground(resourceMap.getColor("viewMenu.background")); // NOI18N
        viewMenu.setMnemonic('A');
        viewMenu.setText(resourceMap.getString("viewMenu.text")); // NOI18N

        startseiteMenuItem.setIcon(resourceMap.getIcon("startseiteMenuItem.icon")); // NOI18N
        startseiteMenuItem.setMnemonic('S');
        startseiteMenuItem.setText(resourceMap.getString("startseiteMenuItem.text")); // NOI18N
        startseiteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startseiteMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(startseiteMenuItem);

        uebersichtItem.setIcon(resourceMap.getIcon("uebersichtItem.icon")); // NOI18N
        uebersichtItem.setMnemonic('\u00dc');
        uebersichtItem.setText(resourceMap.getString("uebersichtItem.text")); // NOI18N
        uebersichtItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uebersichtItemActionPerformed(evt);
            }
        });
        viewMenu.add(uebersichtItem);

        kalenderMenuItem.setIcon(resourceMap.getIcon("kalenderMenuItem.icon")); // NOI18N
        kalenderMenuItem.setMnemonic('K');
        kalenderMenuItem.setText(resourceMap.getString("kalenderMenuItem.text")); // NOI18N
        kalenderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalenderMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(kalenderMenuItem);

        kartenItem.setIcon(resourceMap.getIcon("kartenItem.icon")); // NOI18N
        kartenItem.setMnemonic('K');
        kartenItem.setText(resourceMap.getString("kartenItem.text")); // NOI18N
        kartenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kartenItemActionPerformed(evt);
            }
        });
        viewMenu.add(kartenItem);

        privatkundenItem.setIcon(resourceMap.getIcon("privatkundenItem.icon")); // NOI18N
        privatkundenItem.setMnemonic('P');
        privatkundenItem.setText(resourceMap.getString("privatkundenItem.text")); // NOI18N
        privatkundenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privatkundenItemActionPerformed(evt);
            }
        });
        viewMenu.add(privatkundenItem);

        firmenkundenMenuItem.setIcon(resourceMap.getIcon("firmenkundenMenuItem.icon")); // NOI18N
        firmenkundenMenuItem.setMnemonic('G');
        firmenkundenMenuItem.setText(resourceMap.getString("firmenkundenMenuItem.text")); // NOI18N
        firmenkundenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firmenkundenMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(firmenkundenMenuItem);

        stoerfaelleMenuItem.setIcon(resourceMap.getIcon("stoerfaelleMenuItem.icon")); // NOI18N
        stoerfaelleMenuItem.setMnemonic('S');
        stoerfaelleMenuItem.setText(resourceMap.getString("stoerfaelleMenuItem.text")); // NOI18N
        stoerfaelleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stoerfaelleMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(stoerfaelleMenuItem);

        schaedenMenuItem.setIcon(resourceMap.getIcon("schaedenMenuItem.icon")); // NOI18N
        schaedenMenuItem.setMnemonic('S');
        schaedenMenuItem.setText(resourceMap.getString("schaedenMenuItem.text")); // NOI18N
        schaedenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schaedenMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(schaedenMenuItem);

        versichererMenuItem.setIcon(resourceMap.getIcon("versichererMenuItem.icon")); // NOI18N
        versichererMenuItem.setMnemonic('G');
        versichererMenuItem.setText(resourceMap.getString("versichererMenuItem.text")); // NOI18N
        versichererMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                versichererMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(versichererMenuItem);

        produkteMenuItem.setIcon(resourceMap.getIcon("produkteMenuItem.icon")); // NOI18N
        produkteMenuItem.setMnemonic('P');
        produkteMenuItem.setText(resourceMap.getString("produkteMenuItem.text")); // NOI18N
        produkteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produkteMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(produkteMenuItem);

        vertraegeMenuItem.setIcon(resourceMap.getIcon("vertraegeMenuItem.icon")); // NOI18N
        vertraegeMenuItem.setMnemonic('V');
        vertraegeMenuItem.setText(resourceMap.getString("vertraegeMenuItem.text")); // NOI18N
        vertraegeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vertraegeMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(vertraegeMenuItem);

        benutzerManagementMenuItem.setBackground(resourceMap.getColor("benutzerManagementMenuItem.background")); // NOI18N
        benutzerManagementMenuItem.setIcon(resourceMap.getIcon("benutzerManagementMenuItem.icon")); // NOI18N
        benutzerManagementMenuItem.setMnemonic('B');
        benutzerManagementMenuItem.setText(resourceMap.getString("benutzerManagementMenuItem.text")); // NOI18N
        benutzerManagementMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                benutzerManagementMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(benutzerManagementMenuItem);

        menuBar.add(viewMenu);

        extrasMenu.setBackground(resourceMap.getColor("extrasMenu.background")); // NOI18N
        extrasMenu.setMnemonic('E');
        extrasMenu.setText(resourceMap.getString("extrasMenu.text")); // NOI18N

        beratungsdokumentationMenuItem.setIcon(resourceMap.getIcon("beratungsdokumentationMenuItem.icon")); // NOI18N
        beratungsdokumentationMenuItem.setMnemonic('B');
        beratungsdokumentationMenuItem.setText(resourceMap.getString("beratungsdokumentationMenuItem.text")); // NOI18N
        beratungsdokumentationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beratungsdokumentationMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(beratungsdokumentationMenuItem);

        finanzanalyseMenuItem.setText(resourceMap.getString("finanzanalyseMenuItem.text")); // NOI18N
        finanzanalyseMenuItem.setEnabled(false);
        extrasMenu.add(finanzanalyseMenuItem);

        inkassMenuItem.setText(resourceMap.getString("inkassMenuItem.text")); // NOI18N
        inkassMenuItem.setEnabled(false);
        extrasMenu.add(inkassMenuItem);

        rechnungMenuItem.setText(resourceMap.getString("rechnungMenuItem.text")); // NOI18N
        rechnungMenuItem.setEnabled(false);
        extrasMenu.add(rechnungMenuItem);

        onlinepostMenuItem.setMnemonic('O');
        onlinepostMenuItem.setText(resourceMap.getString("onlinepostMenuItem.text")); // NOI18N
        onlinepostMenuItem.setEnabled(false);
        extrasMenu.add(onlinepostMenuItem);
        extrasMenu.add(jSeparator19);

        sendmailMenuItem.setIcon(resourceMap.getIcon("sendmailMenuItem.icon")); // NOI18N
        sendmailMenuItem.setMnemonic('E');
        sendmailMenuItem.setText(resourceMap.getString("sendmailMenuItem.text")); // NOI18N
        sendmailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendmailMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(sendmailMenuItem);

        notizenMenuItem.setIcon(resourceMap.getIcon("notizenMenuItem.icon")); // NOI18N
        notizenMenuItem.setMnemonic('N');
        notizenMenuItem.setText(resourceMap.getString("notizenMenuItem.text")); // NOI18N
        notizenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notizenMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(notizenMenuItem);

        mahnungenMenuItem.setMnemonic('M');
        mahnungenMenuItem.setText(resourceMap.getString("mahnungenMenuItem.text")); // NOI18N
        mahnungenMenuItem.setEnabled(false);
        extrasMenu.add(mahnungenMenuItem);
        extrasMenu.add(jSeparator11);

        sicherungMenuItem.setIcon(resourceMap.getIcon("sicherungMenuItem.icon")); // NOI18N
        sicherungMenuItem.setMnemonic('S');
        sicherungMenuItem.setText(resourceMap.getString("sicherungMenuItem.text")); // NOI18N
        sicherungMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sicherungMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(sicherungMenuItem);

        autoSicherungMenuItem.setIcon(resourceMap.getIcon("autoSicherungMenuItem.icon")); // NOI18N
        autoSicherungMenuItem.setMnemonic('A');
        autoSicherungMenuItem.setText(resourceMap.getString("autoSicherungMenuItem.text")); // NOI18N
        autoSicherungMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoSicherungMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(autoSicherungMenuItem);
        extrasMenu.add(jSeparator18);

        MedenScannerMenuItem.setIcon(resourceMap.getIcon("MedenScannerMenuItem.icon")); // NOI18N
        MedenScannerMenuItem.setMnemonic('M');
        MedenScannerMenuItem.setText(resourceMap.getString("MedenScannerMenuItem.text")); // NOI18N
        MedenScannerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MedenScannerMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(MedenScannerMenuItem);
        extrasMenu.add(jSeparator12);

        benutzerNachrichtenMenuItem.setIcon(resourceMap.getIcon("benutzerNachrichtenMenuItem.icon")); // NOI18N
        benutzerNachrichtenMenuItem.setMnemonic('B');
        benutzerNachrichtenMenuItem.setText(resourceMap.getString("benutzerNachrichtenMenuItem.text")); // NOI18N
        benutzerNachrichtenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                benutzerNachrichtenMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(benutzerNachrichtenMenuItem);

        neuerBenutzerMenuItem.setBackground(resourceMap.getColor("neuerBenutzerMenuItem.background")); // NOI18N
        neuerBenutzerMenuItem.setIcon(resourceMap.getIcon("neuerBenutzerMenuItem.icon")); // NOI18N
        neuerBenutzerMenuItem.setMnemonic('B');
        neuerBenutzerMenuItem.setText(resourceMap.getString("neuerBenutzerMenuItem.text")); // NOI18N
        neuerBenutzerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuerBenutzerMenuItemActionPerformed(evt);
            }
        });
        extrasMenu.add(neuerBenutzerMenuItem);

        menuBar.add(extrasMenu);

        marketingMenu.setMnemonic('M');
        marketingMenu.setText(resourceMap.getString("marketingMenu.text")); // NOI18N

        vmportalMenuItem.setIcon(resourceMap.getIcon("vmportalMenuItem.icon")); // NOI18N
        vmportalMenuItem.setMnemonic('v');
        vmportalMenuItem.setText(resourceMap.getString("vmportalMenuItem.text")); // NOI18N
        vmportalMenuItem.setToolTipText(resourceMap.getString("vmportalMenuItem.toolTipText")); // NOI18N
        vmportalMenuItem.setEnabled(false);
        marketingMenu.add(vmportalMenuItem);

        geburtstagslisteMenuItem.setIcon(resourceMap.getIcon("geburtstagslisteMenuItem.icon")); // NOI18N
        geburtstagslisteMenuItem.setMnemonic('G');
        geburtstagslisteMenuItem.setText(resourceMap.getString("geburtstagslisteMenuItem.text")); // NOI18N
        geburtstagslisteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geburtstagslisteMenuItemActionPerformed(evt);
            }
        });
        marketingMenu.add(geburtstagslisteMenuItem);

        newsletterMenuItem.setIcon(resourceMap.getIcon("newsletterMenuItem.icon")); // NOI18N
        newsletterMenuItem.setMnemonic('N');
        newsletterMenuItem.setText(resourceMap.getString("newsletterMenuItem.text")); // NOI18N
        newsletterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newsletterMenuItemActionPerformed(evt);
            }
        });
        marketingMenu.add(newsletterMenuItem);

        serienbriefMenuItem.setIcon(resourceMap.getIcon("serienbriefMenuItem.icon")); // NOI18N
        serienbriefMenuItem.setMnemonic('S');
        serienbriefMenuItem.setText(resourceMap.getString("serienbriefMenuItem.text")); // NOI18N
        serienbriefMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serienbriefMenuItemActionPerformed(evt);
            }
        });
        marketingMenu.add(serienbriefMenuItem);

        menuBar.add(marketingMenu);

        optionsMenu.setBackground(resourceMap.getColor("optionsMenu.background")); // NOI18N
        optionsMenu.setMnemonic('O');
        optionsMenu.setText(resourceMap.getString("optionsMenu.text")); // NOI18N

        uiMenuItem.setIcon(resourceMap.getIcon("uiMenuItem.icon")); // NOI18N
        uiMenuItem.setText(resourceMap.getString("uiMenuItem.text")); // NOI18N

        grpThemes.add(standardThemeMenuItem);
        standardThemeMenuItem.setSelected(true);
        standardThemeMenuItem.setText(resourceMap.getString("standardThemeMenuItem.text")); // NOI18N
        standardThemeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                standardThemeMenuItemActionPerformed(evt);
            }
        });
        uiMenuItem.add(standardThemeMenuItem);

        grpThemes.add(motifMenuItem);
        motifMenuItem.setText(resourceMap.getString("motifMenuItem.text")); // NOI18N
        motifMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motifMenuItemActionPerformed(evt);
            }
        });
        uiMenuItem.add(motifMenuItem);

        grpThemes.add(plasticMenuItem);
        plasticMenuItem.setText(resourceMap.getString("plasticMenuItem.text")); // NOI18N
        plasticMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plasticMenuItemActionPerformed(evt);
            }
        });
        uiMenuItem.add(plasticMenuItem);

        grpThemes.add(metalMenuItem);
        metalMenuItem.setText(resourceMap.getString("metalMenuItem.text")); // NOI18N
        metalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metalMenuItemActionPerformed(evt);
            }
        });
        uiMenuItem.add(metalMenuItem);

        optionsMenu.add(uiMenuItem);
        optionsMenu.add(jSeparator13);

        briefmailMenuItem.setIcon(resourceMap.getIcon("briefmailMenuItem.icon")); // NOI18N
        briefmailMenuItem.setText(resourceMap.getString("briefmailMenuItem.text")); // NOI18N
        briefmailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                briefmailMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(briefmailMenuItem);

        briefvorlagenMenuItem.setIcon(resourceMap.getIcon("briefvorlagenMenuItem.icon")); // NOI18N
        briefvorlagenMenuItem.setMnemonic('W');
        briefvorlagenMenuItem.setText(resourceMap.getString("briefvorlagenMenuItem.text")); // NOI18N
        briefvorlagenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                briefvorlagenMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(briefvorlagenMenuItem);

        faxvorlagenMenuItem.setIcon(resourceMap.getIcon("faxvorlagenMenuItem.icon")); // NOI18N
        faxvorlagenMenuItem.setMnemonic('F');
        faxvorlagenMenuItem.setText(resourceMap.getString("faxvorlagenMenuItem.text")); // NOI18N
        faxvorlagenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faxvorlagenMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(faxvorlagenMenuItem);

        emailvorlagenMenuItem.setIcon(resourceMap.getIcon("emailvorlagenMenuItem.icon")); // NOI18N
        emailvorlagenMenuItem.setMnemonic('E');
        emailvorlagenMenuItem.setText(resourceMap.getString("emailvorlagenMenuItem.text")); // NOI18N
        emailvorlagenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailvorlagenMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(emailvorlagenMenuItem);

        wissendokumenteVorlagen.setMnemonic('V');
        wissendokumenteVorlagen.setText(resourceMap.getString("wissendokumenteVorlagen.text")); // NOI18N
        wissendokumenteVorlagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wissendokumenteVorlagenActionPerformed(evt);
            }
        });
        optionsMenu.add(wissendokumenteVorlagen);
        optionsMenu.add(jSeparator20);

        reportMenuItem.setIcon(resourceMap.getIcon("reportMenuItem.icon")); // NOI18N
        reportMenuItem.setMnemonic('R');
        reportMenuItem.setText(resourceMap.getString("reportMenuItem.text")); // NOI18N
        reportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(reportMenuItem);
        optionsMenu.add(jSeparator9);

        textbausteineMenuItem.setIcon(resourceMap.getIcon("textbausteineMenuItem.icon")); // NOI18N
        textbausteineMenuItem.setMnemonic('T');
        textbausteineMenuItem.setText(resourceMap.getString("textbausteineMenuItem.text")); // NOI18N
        textbausteineMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textbausteineMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(textbausteineMenuItem);

        tagSettingsMenuItem.setIcon(resourceMap.getIcon("tagSettingsMenuItem.icon")); // NOI18N
        tagSettingsMenuItem.setMnemonic('M');
        tagSettingsMenuItem.setText(resourceMap.getString("tagSettingsMenuItem.text")); // NOI18N
        tagSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagSettingsMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(tagSettingsMenuItem);
        optionsMenu.add(jSeparator21);

        checkdbMenuItem.setIcon(resourceMap.getIcon("checkdbMenuItem.icon")); // NOI18N
        checkdbMenuItem.setMnemonic('D');
        checkdbMenuItem.setText(resourceMap.getString("checkdbMenuItem.text")); // NOI18N
        optionsMenu.add(checkdbMenuItem);

        scriptMenu.setMnemonic('S');
        scriptMenu.setText(resourceMap.getString("scriptMenu.text")); // NOI18N

        databaseSQLMenuItem.setIcon(resourceMap.getIcon("databaseSQLMenuItem.icon")); // NOI18N
        databaseSQLMenuItem.setMnemonic('D');
        databaseSQLMenuItem.setText(resourceMap.getString("databaseSQLMenuItem.text")); // NOI18N
        databaseSQLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseSQLMenuItemActionPerformed(evt);
            }
        });
        scriptMenu.add(databaseSQLMenuItem);

        sqlskriptMenuItem.setIcon(resourceMap.getIcon("sqlskriptMenuItem.icon")); // NOI18N
        sqlskriptMenuItem.setMnemonic('S');
        sqlskriptMenuItem.setText(resourceMap.getString("sqlskriptMenuItem.text")); // NOI18N
        sqlskriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sqlskriptMenuItemActionPerformed(evt);
            }
        });
        scriptMenu.add(sqlskriptMenuItem);

        maklerpointskriptMenuItem.setIcon(resourceMap.getIcon("maklerpointskriptMenuItem.icon")); // NOI18N
        maklerpointskriptMenuItem.setMnemonic('M');
        maklerpointskriptMenuItem.setText(resourceMap.getString("maklerpointskriptMenuItem.text")); // NOI18N
        maklerpointskriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maklerpointskriptMenuItemActionPerformed(evt);
            }
        });
        scriptMenu.add(maklerpointskriptMenuItem);

        optionsMenu.add(scriptMenu);

        jSeparator2.setBackground(resourceMap.getColor("jSeparator2.background")); // NOI18N
        optionsMenu.add(jSeparator2);

        spartenMenuItem.setMnemonic('S');
        spartenMenuItem.setText(resourceMap.getString("spartenMenuItem.text")); // NOI18N
        spartenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spartenMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(spartenMenuItem);

        waehrungMenuItem.setIcon(resourceMap.getIcon("waehrungMenuItem.icon")); // NOI18N
        waehrungMenuItem.setMnemonic('W');
        waehrungMenuItem.setText(resourceMap.getString("waehrungMenuItem.text")); // NOI18N
        waehrungMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waehrungMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(waehrungMenuItem);

        stammdatenMenuItem.setMnemonic('S');
        stammdatenMenuItem.setText(resourceMap.getString("stammdatenMenuItem.text")); // NOI18N
        stammdatenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stammdatenMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(stammdatenMenuItem);
        optionsMenu.add(jSeparator14);

        shortcutSettingsMenuItem.setBackground(resourceMap.getColor("shortcutSettingsMenuItem.background")); // NOI18N
        shortcutSettingsMenuItem.setIcon(resourceMap.getIcon("shortcutSettingsMenuItem.icon")); // NOI18N
        shortcutSettingsMenuItem.setMnemonic('S');
        shortcutSettingsMenuItem.setText(resourceMap.getString("shortcutSettingsMenuItem.text")); // NOI18N
        optionsMenu.add(shortcutSettingsMenuItem);

        settingsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        settingsMenuItem.setBackground(resourceMap.getColor("settingsMenuItem.background")); // NOI18N
        settingsMenuItem.setIcon(resourceMap.getIcon("settingsMenuItem.icon")); // NOI18N
        settingsMenuItem.setMnemonic('E');
        settingsMenuItem.setText(resourceMap.getString("settingsMenuItem.text")); // NOI18N
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(settingsMenuItem);

        menuBar.add(optionsMenu);

        windowMenu.setBackground(resourceMap.getColor("windowMenu.background")); // NOI18N
        windowMenu.setMnemonic('F');
        windowMenu.setText(resourceMap.getString("windowMenu.text")); // NOI18N

        checkToolbarItem.setBackground(resourceMap.getColor("checkToolbarItem.background")); // NOI18N
        checkToolbarItem.setSelected(true);
        checkToolbarItem.setText(resourceMap.getString("checkToolbarItem.text")); // NOI18N
        checkToolbarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkToolbarItemActionPerformed(evt);
            }
        });
        windowMenu.add(checkToolbarItem);

        checkLeftBar.setSelected(true);
        checkLeftBar.setText(resourceMap.getString("checkLeftBar.text")); // NOI18N
        checkLeftBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkLeftBarActionPerformed(evt);
            }
        });
        windowMenu.add(checkLeftBar);

        checkStatusBarItem.setBackground(resourceMap.getColor("checkStatusBarItem.background")); // NOI18N
        checkStatusBarItem.setSelected(true);
        checkStatusBarItem.setText(resourceMap.getString("checkStatusBarItem.text")); // NOI18N
        checkStatusBarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkStatusBarItemActionPerformed(evt);
            }
        });
        windowMenu.add(checkStatusBarItem);

        menuBar.add(windowMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N

        HelpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        HelpMenuItem.setIcon(resourceMap.getIcon("HelpMenuItem.icon")); // NOI18N
        HelpMenuItem.setMnemonic('H');
        HelpMenuItem.setText(resourceMap.getString("HelpMenuItem.text")); // NOI18N
        HelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(HelpMenuItem);

        changelogMenuItem.setIcon(resourceMap.getIcon("changelogMenuItem.icon")); // NOI18N
        changelogMenuItem.setMnemonic('N');
        changelogMenuItem.setText(resourceMap.getString("changelogMenuItem.text")); // NOI18N
        changelogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changelogMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(changelogMenuItem);
        helpMenu.add(jSeparator1);

        onlineHelpMenuItem.setIcon(resourceMap.getIcon("onlineHelpMenuItem.icon")); // NOI18N
        onlineHelpMenuItem.setMnemonic('S');
        onlineHelpMenuItem.setText(resourceMap.getString("onlineHelpMenuItem.text")); // NOI18N
        onlineHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineHelpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(onlineHelpMenuItem);
        helpMenu.add(jSeparator4);

        fehlerProtokollMenuItem.setIcon(resourceMap.getIcon("fehlerProtokollMenuItem.icon")); // NOI18N
        fehlerProtokollMenuItem.setMnemonic('F');
        fehlerProtokollMenuItem.setText(resourceMap.getString("fehlerProtokollMenuItem.text")); // NOI18N
        fehlerProtokollMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fehlerProtokollMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(fehlerProtokollMenuItem);

        checkupdateMenuItem.setIcon(resourceMap.getIcon("checkupdateMenuItem.icon")); // NOI18N
        checkupdateMenuItem.setMnemonic('U');
        checkupdateMenuItem.setText(resourceMap.getString("checkupdateMenuItem.text")); // NOI18N
        checkupdateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkupdateMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(checkupdateMenuItem);
        helpMenu.add(jSeparator8);

        lizenzInfoMenuItem.setIcon(resourceMap.getIcon("lizenzInfoMenuItem.icon")); // NOI18N
        lizenzInfoMenuItem.setMnemonic('L');
        lizenzInfoMenuItem.setText(resourceMap.getString("lizenzInfoMenuItem.text")); // NOI18N
        lizenzInfoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lizenzInfoMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(lizenzInfoMenuItem);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("aboutMenuItem.icon")); // NOI18N
        aboutMenuItem.setMnemonic('M');
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setToolTipText(resourceMap.getString("aboutMenuItem.toolTipText")); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        CRMViewHelper.initializeMenu(this);

        statusPanel.setBackground(resourceMap.getColor("statusPanel.background")); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(740, 25));
        statusPanel.setLayout(new java.awt.GridBagLayout());

        panel_advertising.setBackground(resourceMap.getColor("panel_advertising.background")); // NOI18N
        panel_advertising.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_advertising.setLayout(new java.awt.GridLayout(1, 0, 50, 5));

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        panel_advertising.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 90.0;
        statusPanel.add(panel_advertising, gridBagConstraints);

        panel_database.setBackground(resourceMap.getColor("panel_database.background")); // NOI18N
        panel_database.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_database.setPreferredSize(new java.awt.Dimension(20, 20));

        label_database.setForeground(resourceMap.getColor("label_database.foreground")); // NOI18N
        label_database.setText(resourceMap.getString("label_database.text")); // NOI18N
        if(Config.getConfigBoolean("offlineModus", false) == true) {
            this.label_database.setText("Im Offline Modus (Embedded DB)");
        } else {
            if (!CRM.DEMONSTRATION_MODE) {
                this.label_database.setText("Verbunden mit " + DatabaseConfig.DBNAME + " [" + DatabaseConfig.getDatabaseName() + "]");
                //this.label_database.setText("Verbuden mit mpofficeDB" + " [" + DatabaseConfig.getDatabaseName() + "]");
            } else {
                this.label_database.setText("Verbuden mit mpofficeDB" + " [" + DatabaseConfig.getDatabaseName() + "]");
            }
        }

        javax.swing.GroupLayout panel_databaseLayout = new javax.swing.GroupLayout(panel_database);
        panel_database.setLayout(panel_databaseLayout);
        panel_databaseLayout.setHorizontalGroup(
            panel_databaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
            .addGroup(panel_databaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_databaseLayout.createSequentialGroup()
                    .addGap(0, 24, Short.MAX_VALUE)
                    .addComponent(label_database)
                    .addGap(0, 25, Short.MAX_VALUE)))
        );
        panel_databaseLayout.setVerticalGroup(
            panel_databaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
            .addGroup(panel_databaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_databaseLayout.createSequentialGroup()
                    .addGap(0, 4, Short.MAX_VALUE)
                    .addComponent(label_database)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.weightx = 10.0;
        statusPanel.add(panel_database, gridBagConstraints);

        panel_user.setBackground(resourceMap.getColor("panel_user.background")); // NOI18N
        panel_user.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_user.setForeground(resourceMap.getColor("panel_user.foreground")); // NOI18N
        panel_user.setPreferredSize(new java.awt.Dimension(20, 20));
        panel_user.setLayout(new java.awt.BorderLayout());

        label_username.setForeground(resourceMap.getColor("label_username.foreground")); // NOI18N
        label_username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_username.setText(resourceMap.getString("label_username.text")); // NOI18N
        label_username.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_username.setPreferredSize(new java.awt.Dimension(50, 15));
        panel_user.add(label_username, java.awt.BorderLayout.CENTER);
        label_username.setText(BasicRegistry.currentUser.getUsername() + " [" + BasicRegistry.currentUser.getKennung() + "]");
        label_username.setForeground(Color.white);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.weightx = 5.0;
        statusPanel.add(panel_user, gridBagConstraints);

        panel_date.setBackground(resourceMap.getColor("panel_date.background")); // NOI18N
        panel_date.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_date.setLayout(new java.awt.BorderLayout());

        label_datetime.setForeground(resourceMap.getColor("label_datetime.foreground")); // NOI18N
        label_datetime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_datetime.setText(resourceMap.getString("label_datetime.text")); // NOI18N
        label_datetime.setPreferredSize(new java.awt.Dimension(50, 15));
        panel_date.add(label_datetime, java.awt.BorderLayout.CENTER);
        DigitalClock digi = new DigitalClock(label_datetime);
        Thread t = new Thread(digi);
        t.start();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.weightx = 5.0;
        statusPanel.add(panel_date, gridBagConstraints);

        panel_icons.setBackground(resourceMap.getColor("panel_icons.background")); // NOI18N
        panel_icons.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_icons.setPreferredSize(new java.awt.Dimension(20, 46));
        panel_icons.setLayout(new java.awt.BorderLayout(2, 0));

        poplb_database.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poplb_database.setIcon(resourceMap.getIcon("poplb_database.icon")); // NOI18N
        poplb_database.setText(resourceMap.getString("poplb_database.text")); // NOI18N
        poplb_database.setToolTipText(resourceMap.getString("poplb_database.toolTipText")); // NOI18N
        poplb_database.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        poplb_database.setPreferredSize(new java.awt.Dimension(20, 20));
        poplb_database.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                poplb_databaseMouseClicked(evt);
            }
        });
        panel_icons.add(poplb_database, java.awt.BorderLayout.WEST);

        poplb_mail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poplb_mail.setIcon(resourceMap.getIcon("poplb_mail.icon")); // NOI18N
        poplb_mail.setText(resourceMap.getString("poplb_mail.text")); // NOI18N
        poplb_mail.setToolTipText(resourceMap.getString("poplb_mail.toolTipText")); // NOI18N
        poplb_mail.setMinimumSize(new java.awt.Dimension(18, 16));
        poplb_mail.setPreferredSize(new java.awt.Dimension(19, 16));
        poplb_mail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                poplb_mailMouseClicked(evt);
            }
        });
        panel_icons.add(poplb_mail, java.awt.BorderLayout.LINE_END);

        poplb_update.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poplb_update.setIcon(resourceMap.getIcon("poplb_update.icon")); // NOI18N
        poplb_update.setText(resourceMap.getString("poplb_update.text")); // NOI18N
        poplb_update.setToolTipText(resourceMap.getString("poplb_update.toolTipText")); // NOI18N
        poplb_update.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        poplb_update.setPreferredSize(new java.awt.Dimension(16, 20));
        poplb_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                poplb_updateMouseClicked(evt);
            }
        });
        panel_icons.add(poplb_update, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.weightx = 2.0;
        statusPanel.add(panel_icons, gridBagConstraints);

        toolBar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, resourceMap.getColor("toolBar.border.matteColor"))); // NOI18N
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setMinimumSize(new java.awt.Dimension(28, 32));
        toolBar.setPreferredSize(new java.awt.Dimension(100, 35));

        synchronizeLocalMenuItem.setText(resourceMap.getString("synchronizeLocalMenuItem.text")); // NOI18N
        popupDatabase.add(synchronizeLocalMenuItem);

        resetLocalMenuItem.setSelected(true);
        resetLocalMenuItem.setText(resourceMap.getString("resetLocalMenuItem.text")); // NOI18N
        popupDatabase.add(resetLocalMenuItem);

        checkforupdateMenuItem.setText(resourceMap.getString("checkforupdateMenuItem.text")); // NOI18N
        checkforupdateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkforupdateMenuItemActionPerformed(evt);
            }
        });
        popupUpdate.add(checkforupdateMenuItem);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(toolBar);
    }// </editor-fold>//GEN-END:initComponents

    public void setWindowTitle() {
        if (!CRM.DEMONSTRATION_MODE) {
            CRMView.super.getFrame().setTitle("MaklerPoint Office " + Version.build_name
                    + " - Lizensiert für: " + LicenseConfig.get("licensor", ""));
        } else {
            CRMView.super.getFrame().setTitle("MaklerPoint Office " + Version.build_name
                    + " - Lizensiert für: MaklerPoint");
        }

        CRMView.super.getFrame().setIconImage(MPointKonstanten.icon);
    }

    /**
     *
     */
    private void neuerBenutzerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuerBenutzerMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        benutzerAssistent = new NeuerBenutzerAssistent(mainFrame, false);
        benutzerAssistent.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(benutzerAssistent);
    }//GEN-LAST:event_neuerBenutzerMenuItemActionPerformed

    private CRMView getThis() {
        return this;
    }

    /**
     * 
     * @param evt
     */
    private void privatkundenItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privatkundenItemActionPerformed
        addToolPK();
    }//GEN-LAST:event_privatkundenItemActionPerformed

    /**
     * 
     * @param evt
     */
    private void checkToolbarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkToolbarItemActionPerformed
        if (checkToolbarItem.isSelected()) {
            toolBar.setVisible(true);
            toolBar.revalidate();
        } else {
            toolBar.setVisible(false);
            toolBar.revalidate();
        }
    }//GEN-LAST:event_checkToolbarItemActionPerformed

    private void checkStatusBarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkStatusBarItemActionPerformed
        if (checkStatusBarItem.isSelected()) {
            statusPanel.setVisible(true);
            statusPanel.revalidate();
        } else {
            statusPanel.setVisible(false);
            statusPanel.revalidate();
        }
    }//GEN-LAST:event_checkStatusBarItemActionPerformed

    private void kartenItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kartenItemActionPerformed
        loadKarte();
    }//GEN-LAST:event_kartenItemActionPerformed

    private void benutzerManagementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benutzerManagementMenuItemActionPerformed
        addToolBenutzer();
    }//GEN-LAST:event_benutzerManagementMenuItemActionPerformed

    private void kalenderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalenderMenuItemActionPerformed
        addToolKalender();
    }//GEN-LAST:event_kalenderMenuItemActionPerformed

    /**
     * 
     * @param evt
     */
    private void sicherungMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sicherungMenuItemActionPerformed
        openSicherungDialog();
    }//GEN-LAST:event_sicherungMenuItemActionPerformed

    /**
     * 
     * @param evt
     */
    private void uebersichtItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uebersichtItemActionPerformed
        addToolUebersicht();
    }//GEN-LAST:event_uebersichtItemActionPerformed

    private void tagSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagSettingsMenuItemActionPerformed
        openTagsDialog();
    }//GEN-LAST:event_tagSettingsMenuItemActionPerformed

    private void onlineHelpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineHelpMenuItemActionPerformed
        try {
            desktop.browse(new URI("http://www.maklerpoint.de/support"));
        } catch (Exception ex) {
            Log.logger.fatal("Fehler: Konnte Support Seite nicht im Browser öffnen", ex);
            ShowException.showException("Der Browser konnte nicht für die Anzeige der Support Seite geöffnet werden. "
                    + "Gehen Sie bitte manuell auf http://www.maklerpoint.de/support",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Browser nicht öffnen");

        }
    }//GEN-LAST:event_onlineHelpMenuItemActionPerformed

    private void startseiteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startseiteMenuItemActionPerformed
        addToolStartseite();
    }//GEN-LAST:event_startseiteMenuItemActionPerformed

    private void fehlerProtokollMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fehlerProtokollMenuItemActionPerformed
        addToolLog();
    }//GEN-LAST:event_fehlerProtokollMenuItemActionPerformed

    private void standardThemeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_standardThemeMenuItemActionPerformed
        configureUI(null);
    }//GEN-LAST:event_standardThemeMenuItemActionPerformed

    private void motifMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motifMenuItemActionPerformed
        configureUI("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    }//GEN-LAST:event_motifMenuItemActionPerformed

    private void plasticMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plasticMenuItemActionPerformed
        String name = LookUtils.IS_OS_WINDOWS_XP ? Options.getCrossPlatformLookAndFeelClassName()
                : Options.getSystemLookAndFeelClassName();

        configureUI(name);
    }//GEN-LAST:event_plasticMenuItemActionPerformed

    private void metalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metalMenuItemActionPerformed
        configureUI("javax.swing.plaf.metal.MetalLookAndFeel");
    }//GEN-LAST:event_metalMenuItemActionPerformed

    private void lizenzInfoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lizenzInfoMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        licenseDialog = new LicenseInformationDialog(mainFrame, false, Version.version,
                Version.build, DatabaseConfig.getDatabaseName());
        licenseDialog.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(licenseDialog);
    }//GEN-LAST:event_lizenzInfoMenuItemActionPerformed

    private void beratungsdokumentationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beratungsdokumentationMenuItemActionPerformed
//
//        NewOkCancelDialogTest test = NewOkCancelDialogTest(null, true);

        Object kundeobj = KundenAuswahlHelper.getKunde();

        if (kundeobj == null) {
            return;
        }

        try {
            KundenObj kunde = (KundenObj) kundeobj;
            BeratungsprotokollHelper.open(kunde);
        } catch (Exception e) {
            FirmenObj kunde = (FirmenObj) kundeobj;
            BeratungsprotokollHelper.open(kunde);
        }


    }//GEN-LAST:event_beratungsdokumentationMenuItemActionPerformed

    private void stammdatenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stammdatenMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        stammdatenDialog = new StammdatenDialog(mainFrame, false);
        stammdatenDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(stammdatenDialog);
    }//GEN-LAST:event_stammdatenMenuItemActionPerformed

    private void HelpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpMenuItemActionPerformed
        showHelp("intro_html");
    }//GEN-LAST:event_HelpMenuItemActionPerformed

    private void autoSicherungMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoSicherungMenuItemActionPerformed
        if (autoBackupBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            autoBackupBox = new AutoBackupDialog(mainFrame, false);
            autoBackupBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(autoBackupBox);
    }//GEN-LAST:event_autoSicherungMenuItemActionPerformed

    private void briefvorlagenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_briefvorlagenMenuItemActionPerformed
        try {
            desktop.open(new File(Filesystem.getTemplatePath() + File.separatorChar + "word" + File.separatorChar));
        } catch (IOException ex) {
            Log.logger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
            ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Briefvorlagen im Verzeichnis \""
                    + Filesystem.getTemplatePath() + File.separatorChar + "word" + File.separatorChar + "\" .",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");

        }
    }//GEN-LAST:event_briefvorlagenMenuItemActionPerformed

    private void emailvorlagenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailvorlagenMenuItemActionPerformed
        try {
            desktop.open(new File(Filesystem.getTemplatePath() + File.separatorChar + "email" + File.separatorChar));
        } catch (IOException ex) {
            Log.logger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
            ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Emailvorlagen im Verzeichnis \""
                    + Filesystem.getTemplatePath() + File.separatorChar + "email" + File.separatorChar + "\" .",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");
        }
    }//GEN-LAST:event_emailvorlagenMenuItemActionPerformed

    private void spartenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spartenMenuItemActionPerformed
        openSparteDialog();
    }//GEN-LAST:event_spartenMenuItemActionPerformed

    private void waehrungMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_waehrungMenuItemActionPerformed
        openWaehrungenDialog();
    }//GEN-LAST:event_waehrungMenuItemActionPerformed

    private void offlineCheckMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offlineCheckMenuItemActionPerformed
        if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.EMBEDDED_DERBY) {
            JOptionPane.showMessageDialog(null, "Sie benutzen bereits eine lokale Datenbank.");
            return;
        }

        if (offlineCheckMenuItem.isSelected()) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            goofflineBox = new GoOfflineDialog(mainFrame, true, this);
            goofflineBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(goofflineBox);

        } else {
            boolean possible = DatabaseConnection.testConnection(Config.getConfigInt("databaseType", DatabaseTypes.MYSQL));

            if (possible == false) {
                this.offlineCheckMenuItem.setSelected(true);
                JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung zur externen Datenbank  \""
                        + DatabaseConfig.DBNAME + "\" [" + DatabaseConfig.getDatabaseName() + "] hergestellt werden. "
                        + "Bitte überprüfen Sie die Verbindung und / oder Ihre Datenbank Einstellungen.",
                        "Keine Verbindung zur externen Datenbank", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Config.setBoolean("offlineModus", false);
            try {
                SessionTools.createSession(BasicRegistry.currentUser);
            } catch (Exception e) {
                Log.databaselogger.fatal("Fehler: Konnte Session nicht erstellen", e);
                ShowException.showException("Konnte keine neue Session für den Benutzer erstellen",
                        ExceptionDialogGui.LEVEL_WARNING, e,
                        "Schwerwiegend: Konnte Session nicht erstellen");

            }
            this.label_database.setText("Verbunden mit " + DatabaseConfig.DBNAME
                    + " [" + DatabaseConfig.getDatabaseName() + "]");
            BenutzerRegistry.allUser = BenutzerRegistry.getAllBenutzer(Status.NORMAL);
            BenutzerTasks.doBenutzerTasks();
        }
    }//GEN-LAST:event_offlineCheckMenuItemActionPerformed

    private void versichererMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_versichererMenuItemActionPerformed
        addToolGesellschaften();
    }//GEN-LAST:event_versichererMenuItemActionPerformed

    private void firmenkundenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firmenkundenMenuItemActionPerformed
        addToolGK();
    }//GEN-LAST:event_firmenkundenMenuItemActionPerformed

    private void textbausteineMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textbausteineMenuItemActionPerformed
        TextbausteinDialogHelper.openTb();
    }//GEN-LAST:event_textbausteineMenuItemActionPerformed

    private void notizenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notizenMenuItemActionPerformed
        NotizenDialogHelper.openNotizen();
    }//GEN-LAST:event_notizenMenuItemActionPerformed

    private void wissendokumenteVorlagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wissendokumenteVorlagenActionPerformed
        WissendokumenteDialogHelper.openTb();
    }//GEN-LAST:event_wissendokumenteVorlagenActionPerformed

    private void newsletterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newsletterMenuItemActionPerformed
        addToolNewsletter();
    }//GEN-LAST:event_newsletterMenuItemActionPerformed

    private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
        if (settingsBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            settingsBox = new ConfigurationDialog(mainFrame, false);
            settingsBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(settingsBox);
    }//GEN-LAST:event_settingsMenuItemActionPerformed

    private void poplb_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poplb_updateMouseClicked
        popupUpdate.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_poplb_updateMouseClicked

    private void poplb_databaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poplb_databaseMouseClicked
        Log.logger.debug("Evt x: " + evt.getX() + " | evt y: " + +evt.getY() + " | poupWidth: " + popupDatabase.getWidth());
        popupDatabase.show(evt.getComponent(), evt.getX(), evt.getY());
//        popupDatabase.setLocation((int) (poplb_database.getLocation().getX() - popupDatabase.getPreferredSize().getWidth()),
//                (int) (poplb_database.getLocation().getY() - popupDatabase.getPreferredSize().getHeight()));
    }//GEN-LAST:event_poplb_databaseMouseClicked

    private void checkupdateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkupdateMenuItemActionPerformed
        checkForUpdate();
    }//GEN-LAST:event_checkupdateMenuItemActionPerformed

    private void checkforupdateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkforupdateMenuItemActionPerformed
        checkForUpdate();
    }//GEN-LAST:event_checkforupdateMenuItemActionPerformed

    private void produkteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produkteMenuItemActionPerformed
        addToolProdukte();
    }//GEN-LAST:event_produkteMenuItemActionPerformed

    private void checkLeftBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkLeftBarActionPerformed
        if (checkLeftBar.isSelected()) {

            addToolWindows();
            //ToolWindow[] tools = toolWindowManager.getToolWindows();  
            //for(int i = 0; i < tools.length; i++) {
            //    tools[i].setActive(true);
            //}
        } else {
            toolWindowManager.unregisterAllToolWindow();
        }
    }//GEN-LAST:event_checkLeftBarActionPerformed

    private void faxvorlagenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faxvorlagenMenuItemActionPerformed
        try {
            desktop.open(new File(Filesystem.getTemplatePath() + File.separatorChar + "fax" + File.separatorChar));
        } catch (IOException ex) {
            Log.databaselogger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
            ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Fax Vorlagen im Verzeichnis \""
                    + Filesystem.getTemplatePath() + File.separatorChar + "fax" + File.separatorChar + "\" .",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");
        }
    }//GEN-LAST:event_faxvorlagenMenuItemActionPerformed

    private void MedenScannerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MedenScannerMenuItemActionPerformed
        if (mediascanworking) {
            return;
        }

        mediascanworking = true;

        mediascan = new SwingWorker<Boolean, Void>() {

            public Boolean doInBackground() {
                FilesystemMediaScanner.scan();
                return true;
            }

            @Override
            protected void done() {
                CRMView.mediascanworking = false;
            }
        };

        mediascan.execute();
    }//GEN-LAST:event_MedenScannerMenuItemActionPerformed

    private void sendmailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmailMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        mailDialog = new SendEmailDialog(mainFrame, false);
        mailDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(mailDialog);
    }//GEN-LAST:event_sendmailMenuItemActionPerformed

    private void kundenImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kundenImportMenuItemActionPerformed
        if (privateImportDialog == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            privateImportDialog = new KundenImportDialog(mainFrame, true);
            privateImportDialog.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(privateImportDialog);
    }//GEN-LAST:event_kundenImportMenuItemActionPerformed

    private void databaseSQLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseSQLMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        sqlExecuteDialog = new SQLExecutorDialog(mainFrame, true);
        sqlExecuteDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(sqlExecuteDialog);
    }//GEN-LAST:event_databaseSQLMenuItemActionPerformed

    private void sqlskriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sqlskriptMenuItemActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Die Ausführung eines SQL Skriptes kann eventuell die Datenbank beschädigen.\n"
                + "Bitte erstellen Sie vor der Ausführung in jedem Fall eine Sicherung.\n"
                + "Wollen Sie trotzdem fortfahren?\n",
                "Bestätigung: SQL Skript ausführung",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        String filename = FileTools.openFile("SQL Datei auswählen");

        if (filename == null) {
            return;
        }

        try {
            String[] statements = LocalDatabaseTools.getSQLStatementsFromFile(new File(filename));
            LocalDatabaseTools.executeSQL(DatabaseConnection.open(), statements);
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte SQL Skript nicht ausführen", e);
            ShowException.showException("Das SQL Skript wurde nicht erfolgreich ausgeführt.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte SQL-Skript nicht ausführen");
        }
    }//GEN-LAST:event_sqlskriptMenuItemActionPerformed

    private void maklerpointskriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maklerpointskriptMenuItemActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Die Ausführung eines Skriptes kann eventuell die Datenbank beschädigen.\n"
                + "Bitte erstellen Sie vor der Ausführung in jedem Fall eine Sicherung. Wollen Sie mit der Ausführung fortfahren?\n",
                "Bestätigung: Skript ausführung",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        String filename = FileTools.openFile("MaklerPoint Skript Datei auswählen");

        if (filename == null) {
            return;
        }

        try {
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte SQL Skript nicht ausführen", e);
            ShowException.showException("Das SQL Skript wurde nicht erfolgreich ausgeführt.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte SQL-Skript nicht ausführen");
        }
    }//GEN-LAST:event_maklerpointskriptMenuItemActionPerformed

    private void benutzerNachrichtenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benutzerNachrichtenMenuItemActionPerformed
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("benutzernachrichten") == null) {
                    toolWindowManager.getContentManager().addContent("benutzernachrichten",
                            "Benutzer Nachrichten",
                            ImageTools.createImageIcon(ResourceStrings.BENUTZER_NACHRICHTEN_ICON), // An icon
                            new BenutzerNachrichtenJxPanel());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("benutzernachrichten"));
                }

                toolWindowManager.getContentManager().getContent("benutzernachrichten").setSelected(true);
            }
        });
    }//GEN-LAST:event_benutzerNachrichtenMenuItemActionPerformed

    private void poplb_mailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poplb_mailMouseClicked
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (toolWindowManager.getContentManager().getContent("benutzernachrichten") == null) {
                    toolWindowManager.getContentManager().addContent("benutzernachrichten",
                            "Benutzer Nachrichten",
                            ImageTools.createImageIcon(ResourceStrings.BENUTZER_NACHRICHTEN_ICON), // An icon
                            new BenutzerNachrichtenJxPanel());

                    setDoggyContent(toolWindowManager.getContentManager().getContent("benutzernachrichten"));
                }

                toolWindowManager.getContentManager().getContent("benutzernachrichten").setSelected(true);
            }
        });
    }//GEN-LAST:event_poplb_mailMouseClicked

    private void briefmailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_briefmailMenuItemActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        BriefDialog auswahl = new BriefDialog(mainFrame, true, BriefDialog.EDIT);
        auswahl.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(auswahl);
    }//GEN-LAST:event_briefmailMenuItemActionPerformed

    private void geburtstagslisteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geburtstagslisteMenuItemActionPerformed
        addToolGeburtstag();
    }//GEN-LAST:event_geburtstagslisteMenuItemActionPerformed

    private void vertraegeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vertraegeMenuItemActionPerformed
        addToolVertr();
    }//GEN-LAST:event_vertraegeMenuItemActionPerformed

    private void reportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportMenuItemActionPerformed
        iReport.runIREPORT();
    }//GEN-LAST:event_reportMenuItemActionPerformed

    private void stoerfaelleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stoerfaelleMenuItemActionPerformed
        addToolStoer();
    }//GEN-LAST:event_stoerfaelleMenuItemActionPerformed

    private void schaedenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schaedenMenuItemActionPerformed
        addToolSchaden();
    }//GEN-LAST:event_schaedenMenuItemActionPerformed

    private void serienbriefMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serienbriefMenuItemActionPerformed
        addToolSerienBrief();
    }//GEN-LAST:event_serienbriefMenuItemActionPerformed

    private void changelogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changelogMenuItemActionPerformed
        showChangelogDialog();
    }//GEN-LAST:event_changelogMenuItemActionPerformed

    /**
     * 
     */
    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    private class NeuToolbarPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton privat = new JCommandMenuButton("Privat Kunde", new EmptyResizableIcon(16));
            privat.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    NewKundeHelper.openNewKundeBox();
                }
            });
            popupMenu.addMenuButton(privat);
            popupMenu.addMenuButton(new JCommandMenuButton("Firmen Kunde", new EmptyResizableIcon(16)));
            return popupMenu;
        }
    }
    /**
     * 
     */
//
//    private void openStartpage() {
////        panel_content.removeAll();
//        JxPanelStartpage toc = new JxPanelStartpage();
////        toc.setBorder(new DropShadowBorder());
////        panel_content.add(toc, BorderLayout.CENTER);
////        panel_content.revalidate();
//        toolWindowManager.getContentManager().addContent("start",
//                                                         "Startseite",
//                                                         null,           // An icon
//                                                         toc);
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem HelpMenuItem;
    public javax.swing.JMenuItem MedenScannerMenuItem;
    public javax.swing.JMenuItem autoSicherungMenuItem;
    public javax.swing.JMenuItem benutzerManagementMenuItem;
    public javax.swing.JMenuItem benutzerNachrichtenMenuItem;
    public javax.swing.JMenuItem beratungsdokumentationMenuItem;
    public javax.swing.JMenuItem briefmailMenuItem;
    public javax.swing.JMenuItem briefvorlagenMenuItem;
    public javax.swing.JMenuItem changelogMenuItem;
    public javax.swing.JCheckBoxMenuItem checkLeftBar;
    public javax.swing.JCheckBoxMenuItem checkStatusBarItem;
    public javax.swing.JCheckBoxMenuItem checkToolbarItem;
    public javax.swing.JMenuItem checkdbMenuItem;
    public javax.swing.JMenuItem checkforupdateMenuItem;
    public javax.swing.JMenuItem checkupdateMenuItem;
    public javax.swing.JMenuItem databaseSQLMenuItem;
    public javax.swing.JMenuItem emailvorlagenMenuItem;
    public javax.swing.JMenu extrasMenu;
    public javax.swing.JMenuItem faxvorlagenMenuItem;
    public javax.swing.JMenuItem fehlerProtokollMenuItem;
    public javax.swing.JMenuItem finanzanalyseMenuItem;
    public javax.swing.JMenuItem firmenkundenMenuItem;
    public javax.swing.JMenuItem geburtstagslisteMenuItem;
    public javax.swing.ButtonGroup grpThemes;
    public javax.swing.JMenuItem inkassMenuItem;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPopupMenu.Separator jSeparator1;
    public javax.swing.JPopupMenu.Separator jSeparator11;
    public javax.swing.JPopupMenu.Separator jSeparator12;
    public javax.swing.JPopupMenu.Separator jSeparator13;
    public javax.swing.JPopupMenu.Separator jSeparator14;
    public javax.swing.JPopupMenu.Separator jSeparator17;
    public javax.swing.JPopupMenu.Separator jSeparator18;
    public javax.swing.JPopupMenu.Separator jSeparator19;
    public javax.swing.JPopupMenu.Separator jSeparator2;
    public javax.swing.JPopupMenu.Separator jSeparator20;
    public javax.swing.JPopupMenu.Separator jSeparator21;
    public javax.swing.JPopupMenu.Separator jSeparator4;
    public javax.swing.JPopupMenu.Separator jSeparator6;
    public javax.swing.JPopupMenu.Separator jSeparator8;
    public javax.swing.JPopupMenu.Separator jSeparator9;
    public javax.swing.JMenuItem kalenderMenuItem;
    public javax.swing.JMenuItem kartenItem;
    public javax.swing.JMenuItem kundenImportMenuItem;
    public javax.swing.JMenuItem kundenImportMenuItem1;
    public javax.swing.JLabel label_database;
    public javax.swing.JLabel label_datetime;
    public javax.swing.JLabel label_username;
    public javax.swing.JMenuItem lizenzInfoMenuItem;
    public javax.swing.JMenuItem mahnungenMenuItem;
    public javax.swing.JPanel mainPanel;
    public javax.swing.JMenuItem maklerpointskriptMenuItem;
    public javax.swing.JMenu marketingMenu;
    public javax.swing.JMenuBar menuBar;
    public javax.swing.JRadioButtonMenuItem metalMenuItem;
    public javax.swing.JRadioButtonMenuItem motifMenuItem;
    public javax.swing.JMenuItem neuerBenutzerMenuItem;
    public javax.swing.JMenuItem newsletterMenuItem;
    public javax.swing.JMenuItem notizenMenuItem;
    public javax.swing.JCheckBoxMenuItem offlineCheckMenuItem;
    public javax.swing.JMenuItem onlineHelpMenuItem;
    public javax.swing.JMenuItem onlinepostMenuItem;
    public javax.swing.JMenu optionsMenu;
    public javax.swing.JPanel panel_advertising;
    public javax.swing.JPanel panel_database;
    public javax.swing.JPanel panel_date;
    public javax.swing.JPanel panel_icons;
    public javax.swing.JPanel panel_user;
    public javax.swing.JRadioButtonMenuItem plasticMenuItem;
    public javax.swing.JLabel poplb_database;
    public static javax.swing.JLabel poplb_mail;
    public javax.swing.JLabel poplb_update;
    public javax.swing.JPopupMenu popupDatabase;
    public javax.swing.JPopupMenu popupUpdate;
    public javax.swing.JMenuItem privatkundenItem;
    public javax.swing.JMenuItem produktImportMenuItem;
    public javax.swing.JMenuItem produkteMenuItem;
    public javax.swing.JMenuItem rechnungMenuItem;
    public javax.swing.JMenuItem reportMenuItem;
    public javax.swing.JCheckBoxMenuItem resetLocalMenuItem;
    public javax.swing.JMenuItem schaedenMenuItem;
    public javax.swing.JMenu scriptMenu;
    public javax.swing.JMenuItem sendmailMenuItem;
    public javax.swing.JMenuItem serienbriefMenuItem;
    public javax.swing.JMenuItem settingsMenuItem;
    public javax.swing.JMenuItem shortcutSettingsMenuItem;
    public javax.swing.JMenuItem sicherungMenuItem;
    public javax.swing.JMenuItem spartenMenuItem;
    public javax.swing.JMenuItem sqlskriptMenuItem;
    public javax.swing.JMenuItem stammdatenMenuItem;
    public javax.swing.JRadioButtonMenuItem standardThemeMenuItem;
    public javax.swing.JMenuItem startseiteMenuItem;
    public javax.swing.JPanel statusPanel;
    public javax.swing.JMenuItem stoerfaelleMenuItem;
    public javax.swing.JMenuItem synchronizeLocalMenuItem;
    public javax.swing.JMenuItem tagSettingsMenuItem;
    public javax.swing.JMenuItem textbausteineMenuItem;
    public javax.swing.JToolBar toolBar;
    public javax.swing.JMenuItem uebersichtItem;
    public javax.swing.JMenu uiMenuItem;
    public javax.swing.JMenuItem versichererImportMenuItem;
    public javax.swing.JMenuItem versichererMenuItem;
    public javax.swing.JMenuItem vertraegeMenuItem;
    public javax.swing.JMenu viewMenu;
    public javax.swing.JMenuItem vmportalMenuItem;
    public javax.swing.JMenuItem waehrungMenuItem;
    public javax.swing.JMenu windowMenu;
    public javax.swing.JMenuItem wissendokumenteVorlagen;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog autoBackupBox;
    private JDialog backupBox;
    private JDialog goofflineBox;
    private JDialog benutzerAssistent;
    private JDialog settingsBox;
    private JDialog tagSettingsBox;
    private JDialog terminDialog;
    private JDialog aufgabenDialog;
    private JDialog licenseDialog;
    private JDialog stammdatenDialog;
    private JDialog spartenDialog;
    private JDialog waehrungenDialog;
    private JDialog mailDialog;
    private JDialog privateImportDialog;
    private JDialog sqlExecuteDialog;
    private JDialog changelogDialog;
    private SwingWorker mediascan;
    public static boolean mediascanworking = false;
    /* TOOLBAR */
    private JButton btnKarte;
    private JCommandButton neuButton;
    private JButton btnNeuTermin;
    private JButton btnNeuAufgabe;
    private JButton btnHilfe;
    private JButton btnBeratungsdokumentation;
    private JButton btnSendMail;
    private JButton btnNewsletter;
    private JButton btnSerienbrief;
    private JButton btnSicherung;
    private JButton btnBenutzerMail;
    private JButton btnNotizen;
    private JButton btnSettings;
    private JButton btnWaehrungen;
    private JButton btnSparten;
    private JButton btnTextbausteine;
    private JButton btnTags;
    private JTextField field_search;
    private JButton btnSearch;
    private JLabel label_search;
    private JLabel label_search_in;
    private JComboBox combo_search;
    private JPanel search;
    private javax.swing.JToolBar.Separator seperator;
    private Content sucheCont;
    private boolean updatesearch = false;
}