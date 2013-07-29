/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelStoerfall.java
 *
 * Created on 17.07.2011, 12:56:39
 */
package de.maklerpoint.office.Gui.Stoerfall;

import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.External.iReport;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Export.ExportDialog;
import de.maklerpoint.office.Gui.Kunden.KundenTabelleSorter;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Konstanten.Stoerfaelle;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Stoerfalle.Tools.StoerfaelleSQLMethods;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.KundenUebersichtModel;
import de.maklerpoint.office.Table.StoerfallUebersichtHeader;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
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
public class PanelStoerfaelle extends javax.swing.JPanel {

    private Preferences prefs = Preferences.userRoot().node(PanelStoerfaelle.class.getName());
    private JDialog tableSettingsBox;
    private JDialog operatorBox;
    private JDialog stoerBox;
    private JDialog briefBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private Object[] activeItems;
    private Object[] inactiveItems;
    private String[] defaultColumns = {"Auswahl", "Kdnr",}; // TODO
    private boolean firstLoad = true;
    private int datenCount = 0;
    private Desktop desktop = Desktop.getDesktop();
    private SimpleDateFormat df_day = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat df_hour = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private StoerfallObj selstoer = null;
    private AddStoerfallPanels panelAdd = new AddStoerfallPanels();
    private int operator = TableValueChooseDialog.ENTHAELT;
    private int currow = -1;

    /** Creates new form PanelStoerfall */
    public PanelStoerfaelle() {
        initComponents();
        initialize();
    }

    private void initialize() {
        loadButtons();
        loadTableFieldSearch();
        initTable();
        loadTable();
    }

    private void loadButtons() {
        addExportCommandButton();
        addBriefCommandButton();
        addPrintCommandButton();
        addReportCommandButton();
        addAnsichtButtons();
    }

    /**
     * Brief Command Button
     */
    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail senden");
        RichTooltip tooltip = new RichTooltip("Brief / E-Mail / Fax", "Schreiben Sie einen Brief, "
                + "ein Fax oder eine E-Mail zu dem ausgewählten (markierten) Störfall.");

        briefButton.setPopupRichTooltip(tooltip);

        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.toolbar.add(briefButton, 6);
    }

    /**
     * BRIEF POPUP
     */
    private class BriefPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();

            JCommandMenuButton algbrief = new JCommandMenuButton("Allgem. Brief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algbrief);

            JCommandMenuButton algfax = new JCommandMenuButton("Allgem. Fax",
                    getResizableIconFromResource(ResourceStrings.FAX_ICON));
            algfax.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(2)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algfax);

            JCommandMenuButton algemail = new JCommandMenuButton("Allgem. E-Mail",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            algemail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algemail);

            JCommandMenuButton terminbest = new JCommandMenuButton("Schadensreport",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            terminbest.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(4)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(terminbest);

            JCommandMenuButton terminbestmail = new JCommandMenuButton("Terminbestätigung (E-Mail)",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            terminbestmail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(6)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(terminbestmail);


//            JCommandMenuButton gebbrief = new JCommandMenuButton("Geburtstagsbrief",
//                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
//            gebbrief.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
//                    schreibeBrief(ToolsRegistry.getBrief(8)); // Hardcoded, ids sollten sich nicht ändern!
//                }
//            });
//
//            popupMenu.addMenuButton(gebbrief);
//
//            JCommandMenuButton makauftrag = new JCommandMenuButton("Maklerauftrag",
//                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
//            makauftrag.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
//                    schreibeBrief(ToolsRegistry.getBrief(13)); // Hardcoded, ids sollten sich nicht ändern!
//                }
//            });
//
//            popupMenu.addMenuButton(makauftrag);

            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.PRIVAT);
                    schreibeBrief(brief);
                }
            });

            popupMenu.addMenuButton(mehr);

            return popupMenu;
        }
    }

    private void schreibeBrief(BriefObj brief) {
    }

    /**
     * Export Command Button
     */
    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Störfälle exportieren");

        RichTooltip tooltip = new RichTooltip("Störfallexport",
                "Exportiert die ausgewählten Störfälle und Spalten im gewünschten Format.");

        exportButton.setPopupRichTooltip(tooltip);

        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbar.add(exportButton, 6);
    }

    /**
     * EXPORT POPUP
     */
    private class ExportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton csv = new JCommandMenuButton("als CSV Datei (.csv)",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            csv.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.CSV);
                }
            });

            popupMenu.addMenuButton(csv);

            JCommandMenuButton xls = new JCommandMenuButton("als Excel Datei (.xls)",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            xls.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.XLS);
                }
            });

            popupMenu.addMenuButton(xls);

            JCommandMenuButton xlsx = new JCommandMenuButton("als Excel 2007+ Datei (.xlsx)",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            xlsx.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.XLSX);
                }
            });

            popupMenu.addMenuButton(xlsx);

            JCommandMenuButton pdf = new JCommandMenuButton("als PDF Datei (.pdf)",
                    getResizableIconFromResource(ResourceStrings.PDF_ICON));
            pdf.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.PDF);
                }
            });

            popupMenu.addMenuButton(pdf);

            JCommandMenuButton xml = new JCommandMenuButton("als XML Datei",
                    getResizableIconFromResource(ResourceStrings.XML_ICON));
            xml.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportXML();
                }
            });

            popupMenu.addMenuButton(xml);

            JCommandMenuButton txt = new JCommandMenuButton("als Text Datei (.txt)",
                    getResizableIconFromResource(ResourceStrings.TEXT_ICON));
            txt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.TXT);
                }
            });

            popupMenu.addMenuButton(txt);

            JCommandMenuButton doc = new JCommandMenuButton("Störfalldatenblatt (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportStoerfallDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);


            return popupMenu;
        }
    }

    public void openExportDialog(int type) {
        StoerfallObj[] stoer = getSelectedStoerfaelle();
        if (stoer == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exportBox = new ExportDialog(mainFrame, false, type, stoer,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }

    private void exportStoerfallDatenblatt() {
        // TODO
    }

    private void exportXML() {
        // TODO
    }

    /**
     * Print Command Button
     */
    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("Ausgewählte Störfälle drucken");
        RichTooltip tooltip = new RichTooltip("Druck", "Druckt die ausgewählten Störfalle und Spalten.");
        printButton.setPopupRichTooltip(tooltip);

        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.toolbar.add(printButton, 8);
    }

    /**
     * PRINTING Popup Panel
     */
    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Kundenübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    openExportDialog(PrintTypen.KUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Kundendatenblatt",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            datenblatt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    openExportDialog(PrintTypen.KUNDENDATENBLATT);
                }
            });

            popupMenu.addMenuButton(datenblatt);

            return popupMenu;
        }
    }

    /**
     * Report Command Button
     */
    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        RichTooltip tooltip = new RichTooltip("Reports zu Störfällen (Berichtswesen)",
                "Erstellen Sie professionelle Berichte zur "
                + "Ermittlung wichtiger Eckdaten Ihres Geschäftsbetriebes.");

        reportButton.setPopupRichTooltip(tooltip);

        reportButton.setPopupCallback(new ReportPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.toolbar.add(reportButton, 11);
    }

    /**
     * REPORTING
     */
    private class ReportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton kundenspiegel = new JCommandMenuButton("Störfallspiegel",
                    getResizableIconFromResource(ResourceStrings.REPORT_ICON));
            kundenspiegel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO
                }
            });

            popupMenu.addMenuButton(kundenspiegel);

            JCommandMenuButton vertragsspiegel = new JCommandMenuButton("Vertragsspiegel",
                    getResizableIconFromResource(ResourceStrings.REPORT_ICON));
            vertragsspiegel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    exportVertragsspiegel();
                }
            });

            popupMenu.addMenuButton(vertragsspiegel);

            JCommandMenuButton besuchsspiegel = new JCommandMenuButton("Besuchsspiegel",
                    getResizableIconFromResource(ResourceStrings.REPORT_ICON));
            besuchsspiegel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO
                }
            });

            popupMenu.addMenuButton(besuchsspiegel);


            JCommandMenuButton reportdes = new JCommandMenuButton("Report Designer",
                    getResizableIconFromResource(ResourceStrings.REPORT_DESIGNER_ICON));
            reportdes.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    iReport.runIREPORT();
                }
            });

            popupMenu.addMenuButton(reportdes);



            return popupMenu;
        }
    }

    /**
     * Ansichts Button
     */
    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Störfallansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(
                StoerfallUebersichtHeader.getSearchColumnsWithField()));
    }

    private void initTable() {
        table_stoerfall.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_stoerfall.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_stoerfall.setColumnSelectionAllowed(false);
        table_stoerfall.setCellSelectionEnabled(false);
        table_stoerfall.setRowSelectionAllowed(true);
        table_stoerfall.setAutoCreateRowSorter(true);

//        table.getSelectionModel().addListSelectionListener(new RowListener());
        table_stoerfall.setFillsViewportHeight(true);
    }

    private int getStatus() {
        int status = Stoerfaelle.STATUS_OFFEN;

        if (this.alledbMenuItem.isSelected()) {
            return -1;
        } else if (this.aktivedbMenuItem.isSelected()) {
            return Stoerfaelle.STATUS_OFFEN;
        } else if (this.archivedbMenuItem.isSelected()) {
            return Stoerfaelle.STATUS_ERLEDIGT; // TODO
        } else if (this.deleteddbMenuItem.isSelected()) {
            // TODO return Status.DELETED;
        }

        return status;
    }

    public void loadTable() {
        StoerfallObj[] stoer = null;

        if (this.eigeneMenuItem.isSelected()) {
            stoer = VertragRegistry.getBenutzerStoerfaelle(BasicRegistry.currentUser.getId(),
                    getStatus());
        } else if (this.alleMenuItem.isSelected()) {
            stoer = VertragRegistry.getAlleStoerfaelle(getStatus());
        } else {
            // Fallback :)
            stoer = VertragRegistry.getBenutzerStoerfaelle(BasicRegistry.currentUser.getId(), 
                    getStatus());
        }

        loadSWTable(stoer);
    }

    private void loadSWTable(final StoerfallObj[] stoer) {

//        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){
//
//            @Override
//            protected Void doInBackground() throws Exception {
        createTable(stoer);
//                return null;
//            }
//        };
//        
//        sw.execute();
    }

    public void createTable(StoerfallObj[] stoers) {
        Object[][] data = null;
        int selrow = -1;

        if (columnNames == null) {
            createColumnNames();
        }

        if (stoers != null) {
            data = new Object[stoers.length][columnNames.length + 2];

            for (int i = 0; i < stoers.length; i++) {
                StoerfallObj stoer = stoers[i];

                if (selstoer != null) {
                    if (selstoer.getId() == stoer.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = false;
                data[i][1] = stoer;
                
                VertragObj vtr = VertragRegistry.getVertrag(stoer.getVertragsId());

                for (int j = 2; j < columnNames.length; j++) {
                    int result = columnNames[j].getId();
                    data[i][j] = getStoerResult(stoer, result, vtr);
                }
            }

            datenCount = stoers.length;

            if (datenCount == 1) {
                label_tablestatustext.setText("Ein Datensatz");
                label_tablestatustext.setForeground(new Color(-16738048));
            } else {
                label_tablestatustext.setText(datenCount + " Datensätze");
                label_tablestatustext.setForeground(new Color(-16738048));
            }

        } else {
            label_tablestatustext.setText("Keine Datensätze");
            label_tablestatustext.setForeground(Color.red);
            datenCount = 0;
        }

        setTable(data, selrow);
    }

    /**
     * 
     * @param stoer
     * @param result
     * @param vtr
     * @return 
     */
    public Object getStoerResult(StoerfallObj stoer, int result, VertragObj vtr) {
        switch (result) {
            case 0:
                return stoer.getId();

            case 1:
                return BenutzerRegistry.getBenutzer(stoer.getCreatorId()).toString();

            case 2:
                return "";

            case 3:
                return stoer.getKundenNr();

            case 4:
                return KundenRegistry.getKunde(stoer.getKundenNr()).toString();
                
            case 5:
                return vtr.getPolicennr();
                
            case 6:
                return vtr.toString();
                
            case 7:
                return VersicherungsRegistry.getVersicher(vtr.getVersichererId()).toString();
                
            case 8:
                return VersicherungsRegistry.getProdukt(vtr.getProduktId()).toString();

            case 9:
                return BenutzerRegistry.getBenutzer(stoer.getBetreuerId()).toString();

            case 10:
                return stoer.getStoerfallNr();

            case 11:
                return stoer.getGrund();

            case 12:
                return df_hour.format(stoer.getEingang());

            case 13:
                return df_day.format(stoer.getFaelligkeit());

            case 14:
                return stoer.getFristTage() + " Tage";

            case 15:
                return "";

            case 16:
                return WaehrungFormat.getFormatedWaehrung(stoer.getRueckstand(),
                        VersicherungsRegistry.getWaehrung(vtr.getWaehrungId())); // FORMAT

            case 17:
                return stoer.getMahnstatus();

            case 18:
                return stoer.getKategorie();

            case 19:
                return "";

            case 20:
                return stoer.getNotiz();

            case 21:
                return stoer.getCustom1();

            case 22:
                return stoer.getCustom2();

            case 23:
                return stoer.getCustom3();

            case 24:
                return df_hour.format(stoer.getCreated());

            case 25:
                return df_hour.format(stoer.getModified());

            case 26:
                return Stoerfaelle.getStatusName(stoer.getStatus());

            default:
                return "";
        }
    }
    // ColumnNames
    private ColumnHead[] columnNames = null;

    public void createColumnNames() {
        String columnHeadsIds = prefs.get("tableColumns", "10,4,5,7,9,11,13,16,18");
        String[] results = columnHeadsIds.split(",");
        activeItems = new Object[results.length];
        columnNames = new ColumnHead[results.length + 2];
        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = StoerfallUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < StoerfallUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                if (StoerfallUebersichtHeader.Columns[i] != null) {
                    al.add(new ColumnHead(StoerfallUebersichtHeader.Columns[i], i, false));
                }
            }
        }
        inactiveItems = al.toArray();
    }

    /**
     * 
     * @param data
     * @param selrow 
     */
    private void setTable(Object[][] data, int selrow) {
        table_stoerfall.setModel(new KundenUebersichtModel(data, columnNames));
        table_stoerfall.removeColumn(table_stoerfall.getColumnModel().getColumn(1));

        TableColumn check = table_stoerfall.getColumnModel().getColumn(0);
        check.setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean isFocused, int row, int col) {
                boolean marked = (Boolean) value;
                JCheckBox rendererComponent = new JCheckBox();
                if (marked) {
                    rendererComponent.setSelected(true);
                }
                return rendererComponent;
            }
        });

        MouseListener popupListener = new TablePopupListener();

        check.setPreferredWidth(5);
        table_stoerfall.addMouseListener(popupListener);

//        for (int i = 3; i <= results.length + 1; i++) {
//            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(columnNames[i - 1].toString(), true);
//            System.out.println("C: " + columnNames[i-1].toString());
//            tableHeaderPopup.add(menuItem);
//        }

        JTableHeader header = table_stoerfall.getTableHeader();
        MouseListener popupHeaderListener = new TableHeaderPopupListener();
        header.addMouseListener(popupHeaderListener);

        table_stoerfall.packAll();

        table_stoerfall.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_stoerfall.getColumnModel().getColumn(0).setMaxWidth(20);

        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                panelAdd.disElements();
                this.label_activestoerfall.setText("Kein Störfall ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // Sollte nicht passieren
            }
        } else {
            if (selrow != -1) {

                showStoerfall(selstoer);
                table_stoerfall.requestFocusInWindow();
                table_stoerfall.changeSelection(selrow, 0, false, false);

                selstoer = null;
            } else {
                showStoerfall((StoerfallObj) data[0][1]);
                table_stoerfall.requestFocusInWindow();
                table_stoerfall.changeSelection(0, 0, false, false);
            }
        }
    }

    private void showStoerfall(StoerfallObj stoer) {
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Störfall: " + stoer.toString());
        }

        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        }

        label_activestoerfall.setText("Aktiver Störfall: ".concat(stoer.toString()));
        panelAdd.load(stoer);
    }

    /**
     * 
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
                final int row = table_stoerfall.rowAtPoint(point);
                table_stoerfall.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {

                        if (row != -1) {
                            if (row != currow) {
                                currow = row;
                                StoerfallObj stoer = (StoerfallObj) 
                                        table_stoerfall.getModel().getValueAt(row, 1);
                                showStoerfall(stoer);
                            }
                        }
                    }
                });
            }
        }
    }

    // Tabellen Funktionen
    /**
     * Läd alle ausgewählten Störfälle
     * @return Stoerfälle
     */
    public StoerfallObj[] getSelectedStoerfaelle() {

        ArrayList<StoerfallObj> stoerList = new ArrayList<StoerfallObj>();

        for (int i = 0; i < table_stoerfall.getRowCount(); i++) {
            Boolean sel = (Boolean) table_stoerfall.getModel().getValueAt(i, 0);
            if (sel) {
                stoerList.add((StoerfallObj) table_stoerfall.getModel().getValueAt(i, 1));
            }
        }

        if (stoerList.isEmpty()) {
            StoerfallObj stoer = getSelectedStoerfall();
            if (stoer != null) {
                stoerList.add(stoer);
            } else {
                return null;
            }
        }

        StoerfallObj stoere[] = new StoerfallObj[stoerList.size()];

        stoere = stoerList.toArray(stoere);

        return stoere;
    }

    /**
     * 
     * @return 
     */
    public StoerfallObj getSelectedStoerfall() {
        int rowcount = table_stoerfall.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Störfall aus.",
                    "Kein Störfall ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_stoerfall.getSelectedRow();
        StoerfallObj stoer = (StoerfallObj) table_stoerfall.getModel().getValueAt(row, 1);

        return stoer;
    }

    /**
     * Tabellen Einstellungen
     */
    public void openTableSettings() {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        tableSettingsBox = new KundenTabelleSorter(mainFrame, false,
                activeItems, inactiveItems, this);
        tableSettingsBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(tableSettingsBox);
    }

    // Neu, bearbeiten, Archive, löschen & Co.
    /**
     * Neuer Störfall
     */
    private void newStoerfall() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        stoerBox = new StoerfallDialog(mainFrame, true);
        stoerBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        Log.logger.debug("Störfall Dialog geschlossen");
                        return null;
                    }
                });
            }
        });

        stoerBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(stoerBox);

    }

    /**
     * Störfall bearbeiten
     */
    private void editStoerfall() {
        final int row = table_stoerfall.getSelectedRow();

        if (row == -1) {
            return;
        }

        StoerfallObj stoer = (StoerfallObj) table_stoerfall.getModel().getValueAt(row, 1);

        selstoer = stoer;
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        stoerBox = new StoerfallDialog(mainFrame, true, stoer);
        stoerBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        Log.logger.debug("StörfallDialog geschlossen");
                        return null;
                    }
                });
            }
        });

        stoerBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(stoerBox);
    }

//    /**
//     * Archiviert die ausgewählten Störfälle
//     */
//    private void archiveSelectedStoerfaelle() {
//        StoerfallObj[] stoer = getSelectedStoerfaelle();
//
//        if (stoer == null) {
//            return;
//        }
//
//        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
//            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Störfälle wirklich archivieren?",
//                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);
//
//            if (choose != JOptionPane.YES_OPTION) {
//                return;
//            }
//        }
//
//        try {
//            for (int i = 0; i < stoer.length; i++) {
//                StoerfaelleSQLMethods.archiveFromStoerfaelle(DatabaseConnection.open(), stoer[i].getId());
//            }
//        } catch (SQLException e) {
//            Log.databaselogger.fatal("Fehler: Konnte den Störfall nicht archivieren", e);
//            ShowException.showException("Die ausgewählten Störfälle konnte nicht archiviert werden.",
//                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte die Störfälle nicht archivieren");
//        }
//
//        loadTable();
//    }

    /**
     * 
     */
    private void reguliereSelectedStoerfaelle() {
        StoerfallObj[] stoer = getSelectedStoerfaelle();

        if (stoer == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Störfälle wirklich als erledigt markieren?",
                    "Bestätigung Reguliert", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            for (int i = 0; i < stoer.length; i++) {
                StoerfaelleSQLMethods.setErledigtFromStoerfaelle(DatabaseConnection.open(), stoer[i].getId());
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Störfall nicht als erledigt markieren", e);
            ShowException.showException("Die ausgewählten Störfälle konnte nicht als erledigt markiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte die Störfälle nicht regulieren");
        }

        loadTable();
    }

    // Suche
    private void quickSearch() {
        // TODO
    }

    private void fieldSearch() {
        // TODO
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ansichtPopup = new javax.swing.JPopupMenu();
        eigeneMenuItem = new javax.swing.JCheckBoxMenuItem();
        alleMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        alledbMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        deleteddbMenuItem = new javax.swing.JCheckBoxMenuItem();
        grpDBStatus = new javax.swing.ButtonGroup();
        grpWhich = new javax.swing.ButtonGroup();
        tablePopup = new javax.swing.JPopupMenu();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnErledigt = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDokumente = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnStatistik = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnTableSettings = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        toolbar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        fieldSchnellsuche = new javax.swing.JTextField();
        btnSchnellSuche = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        combo_sucheFilter = new javax.swing.JComboBox();
        btnOperator = new javax.swing.JButton();
        fieldDetailsuche = new javax.swing.JTextField();
        btnFieldsearch = new javax.swing.JButton();
        split_stoerfall = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_stoerfall = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activestoerfall = new javax.swing.JLabel();
        pane_stoerfalldetails = new javax.swing.JTabbedPane();

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        grpWhich.add(eigeneMenuItem);
        eigeneMenuItem.setMnemonic('E');
        eigeneMenuItem.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelStoerfaelle.class);
        eigeneMenuItem.setText(resourceMap.getString("eigeneMenuItem.text")); // NOI18N
        eigeneMenuItem.setName("eigeneMenuItem"); // NOI18N
        eigeneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eigeneMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(eigeneMenuItem);

        grpWhich.add(alleMenuItem);
        alleMenuItem.setMnemonic('A');
        alleMenuItem.setText(resourceMap.getString("alleMenuItem.text")); // NOI18N
        alleMenuItem.setName("alleMenuItem"); // NOI18N
        alleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alleMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(alleMenuItem);

        jSeparator14.setName("jSeparator14"); // NOI18N
        ansichtPopup.add(jSeparator14);

        grpDBStatus.add(alledbMenuItem);
        alledbMenuItem.setMnemonic('A');
        alledbMenuItem.setText(resourceMap.getString("alledbMenuItem.text")); // NOI18N
        alledbMenuItem.setToolTipText(resourceMap.getString("alledbMenuItem.toolTipText")); // NOI18N
        alledbMenuItem.setName("alledbMenuItem"); // NOI18N
        alledbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alledbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(alledbMenuItem);

        grpDBStatus.add(aktivedbMenuItem);
        aktivedbMenuItem.setMnemonic('O');
        aktivedbMenuItem.setSelected(true);
        aktivedbMenuItem.setText(resourceMap.getString("aktivedbMenuItem.text")); // NOI18N
        aktivedbMenuItem.setName("aktivedbMenuItem"); // NOI18N
        aktivedbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktivedbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(aktivedbMenuItem);

        grpDBStatus.add(archivedbMenuItem);
        archivedbMenuItem.setMnemonic('A');
        archivedbMenuItem.setText(resourceMap.getString("archivedbMenuItem.text")); // NOI18N
        archivedbMenuItem.setName("archivedbMenuItem"); // NOI18N
        archivedbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(archivedbMenuItem);

        grpDBStatus.add(deleteddbMenuItem);
        deleteddbMenuItem.setMnemonic('G');
        deleteddbMenuItem.setText(resourceMap.getString("deleteddbMenuItem.text")); // NOI18N
        deleteddbMenuItem.setName("deleteddbMenuItem"); // NOI18N
        deleteddbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteddbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(deleteddbMenuItem);

        tablePopup.setName("tablePopup"); // NOI18N

        tableHeaderPopup.setName("tableHeaderPopup"); // NOI18N

        setName("Form"); // NOI18N

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setToolTipText(resourceMap.getString("btnNeu.toolTipText")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        toolbar.add(btnNeu);

        jSeparator5.setName("jSeparator5"); // NOI18N
        toolbar.add(jSeparator5);

        btnErledigt.setIcon(resourceMap.getIcon("btnErledigt.icon")); // NOI18N
        btnErledigt.setText(resourceMap.getString("btnErledigt.text")); // NOI18N
        btnErledigt.setToolTipText(resourceMap.getString("btnErledigt.toolTipText")); // NOI18N
        btnErledigt.setFocusable(false);
        btnErledigt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnErledigt.setName("btnErledigt"); // NOI18N
        btnErledigt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnErledigt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnErledigtActionPerformed(evt);
            }
        });
        toolbar.add(btnErledigt);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbar.add(jSeparator2);

        btnDokumente.setIcon(resourceMap.getIcon("btnDokumente.icon")); // NOI18N
        btnDokumente.setText(resourceMap.getString("btnDokumente.text")); // NOI18N
        btnDokumente.setToolTipText(resourceMap.getString("btnDokumente.toolTipText")); // NOI18N
        btnDokumente.setFocusable(false);
        btnDokumente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDokumente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDokumente.setName("btnDokumente"); // NOI18N
        btnDokumente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDokumente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokumenteActionPerformed(evt);
            }
        });
        toolbar.add(btnDokumente);

        jSeparator4.setName("jSeparator4"); // NOI18N
        toolbar.add(jSeparator4);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbar.add(jSeparator3);

        btnStatistik.setIcon(resourceMap.getIcon("btnStatistik.icon")); // NOI18N
        btnStatistik.setText(resourceMap.getString("btnStatistik.text")); // NOI18N
        btnStatistik.setFocusable(false);
        btnStatistik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStatistik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnStatistik.setName("btnStatistik"); // NOI18N
        btnStatistik.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(btnStatistik);

        jSeparator6.setName("jSeparator6"); // NOI18N
        toolbar.add(jSeparator6);

        btnTableSettings.setIcon(resourceMap.getIcon("btnTableSettings.icon")); // NOI18N
        btnTableSettings.setToolTipText(resourceMap.getString("btnTableSettings.toolTipText")); // NOI18N
        btnTableSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTableSettings.setFocusable(false);
        btnTableSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableSettings.setName("btnTableSettings"); // NOI18N
        btnTableSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableSettingsActionPerformed(evt);
            }
        });
        toolbar.add(btnTableSettings);

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

        toolbar2.setFloatable(false);
        toolbar2.setRollover(true);
        toolbar2.setName("toolbar2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(93, 15));
        toolbar2.add(jLabel1);

        fieldSchnellsuche.setName("fieldSchnellsuche"); // NOI18N
        fieldSchnellsuche.setPreferredSize(new java.awt.Dimension(200, 25));
        fieldSchnellsuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSchnellsucheActionPerformed(evt);
            }
        });
        fieldSchnellsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldSchnellsucheKeyReleased(evt);
            }
        });
        toolbar2.add(fieldSchnellsuche);

        btnSchnellSuche.setIcon(resourceMap.getIcon("btnSchnellSuche.icon")); // NOI18N
        btnSchnellSuche.setMnemonic('S');
        btnSchnellSuche.setToolTipText(resourceMap.getString("btnSchnellSuche.toolTipText")); // NOI18N
        btnSchnellSuche.setFocusable(false);
        btnSchnellSuche.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSchnellSuche.setName("btnSchnellSuche"); // NOI18N
        btnSchnellSuche.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSchnellSuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSchnellSucheActionPerformed(evt);
            }
        });
        toolbar2.add(btnSchnellSuche);

        jSeparator11.setName("jSeparator11"); // NOI18N
        jSeparator11.setPreferredSize(new java.awt.Dimension(10, 0));
        toolbar2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(94, 16));
        jLabel2.setRequestFocusEnabled(false);
        toolbar2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbar2.add(jSeparator12);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setFocusable(false);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
        jLabel3.setVerifyInputWhenFocusTarget(false);
        toolbar2.add(jLabel3);

        combo_sucheFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sucheFilter.setName("combo_sucheFilter"); // NOI18N
        toolbar2.add(combo_sucheFilter);

        btnOperator.setText(resourceMap.getString("btnOperator.text")); // NOI18N
        btnOperator.setToolTipText(resourceMap.getString("btnOperator.toolTipText")); // NOI18N
        btnOperator.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOperator.setFocusable(false);
        btnOperator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOperator.setName("btnOperator"); // NOI18N
        btnOperator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOperator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOperatorMouseClicked(evt);
            }
        });
        toolbar2.add(btnOperator);

        fieldDetailsuche.setName("fieldDetailsuche"); // NOI18N
        fieldDetailsuche.setPreferredSize(new java.awt.Dimension(200, 25));
        fieldDetailsuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldDetailsucheActionPerformed(evt);
            }
        });
        fieldDetailsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldDetailsucheKeyReleased(evt);
            }
        });
        toolbar2.add(fieldDetailsuche);

        btnFieldsearch.setIcon(resourceMap.getIcon("btnFieldsearch.icon")); // NOI18N
        btnFieldsearch.setMnemonic('S');
        btnFieldsearch.setToolTipText(resourceMap.getString("btnFieldsearch.toolTipText")); // NOI18N
        btnFieldsearch.setFocusable(false);
        btnFieldsearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFieldsearch.setName("btnFieldsearch"); // NOI18N
        btnFieldsearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFieldsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFieldsearchActionPerformed(evt);
            }
        });
        toolbar2.add(btnFieldsearch);

        split_stoerfall.setDividerLocation(300);
        split_stoerfall.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        split_stoerfall.setName("split_stoerfall"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_stoerfall.setModel(new KundenUebersichtModel(null, defaultColumns));
        table_stoerfall.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_stoerfall.setMinimumSize(new java.awt.Dimension(400, 150));
        table_stoerfall.setName("table_stoerfall"); // NOI18N
        table_stoerfall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_stoerfallMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_stoerfall);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(1294, 26));

        label_tablestatustext.setText(resourceMap.getString("label_tablestatustext.text")); // NOI18N
        label_tablestatustext.setName("label_tablestatustext"); // NOI18N

        label_activestoerfall.setText(resourceMap.getString("label_activestoerfall.text")); // NOI18N
        label_activestoerfall.setName("label_activestoerfall"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_tablestatustext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 561, Short.MAX_VALUE)
                .addComponent(label_activestoerfall)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext)
                .addComponent(label_activestoerfall, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tableholderLayout = new javax.swing.GroupLayout(panel_tableholder);
        panel_tableholder.setLayout(panel_tableholderLayout);
        panel_tableholderLayout.setHorizontalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tableStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
        );
        panel_tableholderLayout.setVerticalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tableholderLayout.createSequentialGroup()
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panel_tableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        split_stoerfall.setTopComponent(panel_tableholder);

        pane_stoerfalldetails.setName("pane_stoerfalldetails"); // NOI18N
        split_stoerfall.setBottomComponent(pane_stoerfalldetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(toolbar2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addComponent(split_stoerfall, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbar2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(split_stoerfall, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnErledigtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnErledigtActionPerformed
        reguliereSelectedStoerfaelle();
}//GEN-LAST:event_btnErledigtActionPerformed

    private void btnDokumenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokumenteActionPerformed
//        showKundeDokumente();
}//GEN-LAST:event_btnDokumenteActionPerformed

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void fieldSchnellsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSchnellsucheActionPerformed
        this.quickSearch();
}//GEN-LAST:event_fieldSchnellsucheActionPerformed

    private void fieldSchnellsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldSchnellsucheKeyReleased
        if (this.fieldSchnellsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
}//GEN-LAST:event_fieldSchnellsucheKeyReleased

    private void btnSchnellSucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSchnellSucheActionPerformed
        this.quickSearch();
}//GEN-LAST:event_btnSchnellSucheActionPerformed

    private void btnOperatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOperatorMouseClicked
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        operatorBox = new TableValueChooseDialog(mainFrame, true);

        //Point point = btnOperator.getLocation();
        //point.setLocation(point.getX(), point.getY() + 60);
        operatorBox.setLocationRelativeTo(btnOperator);
        Point point = operatorBox.getLocation();
        point.setLocation(point.getX(), point.getY() + 60.00);
        operatorBox.setLocation(point);
        CRM.getApplication().show(operatorBox);

        int val = TableValueChooseDialog.getReturnValue();

        if (val == -1) {
            return;
        }

        operator = val;
        this.btnOperator.setText(TableValueChooseDialog.ZEICHEN[val]);
        this.btnOperator.setToolTipText(TableValueChooseDialog.ZEICHEN_TOOLTIP[val]);

        // System.out.println("Point x " + point.getX() + " | Point Y: " + point.getY());
}//GEN-LAST:event_btnOperatorMouseClicked

    private void fieldDetailsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDetailsucheActionPerformed
        fieldSearch();
}//GEN-LAST:event_fieldDetailsucheActionPerformed

    private void fieldDetailsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDetailsucheKeyReleased
        if (this.fieldDetailsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
}//GEN-LAST:event_fieldDetailsucheKeyReleased

    private void btnFieldsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFieldsearchActionPerformed
        fieldSearch();
}//GEN-LAST:event_btnFieldsearchActionPerformed

    private void table_stoerfallMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_stoerfallMouseClicked
        final int row = table_stoerfall.getSelectedRow();

        if (row == -1) {
            return;
        }

        if (row != currow) {
            currow = row;
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    StoerfallObj stoer = (StoerfallObj) table_stoerfall.getModel().getValueAt(row, 1);
                    showStoerfall(stoer);
                }
            });

            //            System.out.println("Lade Kunde: " + kunde.getKundenNr());
        }

        if (evt.getClickCount() >= 2) {
            editStoerfall();
        }
}//GEN-LAST:event_table_stoerfallMouseClicked

    private void eigeneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eigeneMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_eigeneMenuItemActionPerformed

    private void alleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_alleMenuItemActionPerformed

    private void alledbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alledbMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_alledbMenuItemActionPerformed

    private void aktivedbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktivedbMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_aktivedbMenuItemActionPerformed

    private void archivedbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedbMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_archivedbMenuItemActionPerformed

    private void deleteddbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteddbMenuItemActionPerformed
        this.loadTable();
}//GEN-LAST:event_deleteddbMenuItemActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newStoerfall();
    }//GEN-LAST:event_btnNeuActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    private javax.swing.JCheckBoxMenuItem alleMenuItem;
    private javax.swing.JCheckBoxMenuItem alledbMenuItem;
    private javax.swing.JPopupMenu ansichtPopup;
    private javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    private javax.swing.JButton btnDokumente;
    private javax.swing.JButton btnErledigt;
    private javax.swing.JButton btnFieldsearch;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnOperator;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSchnellSuche;
    private javax.swing.JButton btnStatistik;
    private javax.swing.JButton btnTableSettings;
    private javax.swing.JComboBox combo_sucheFilter;
    private javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    private javax.swing.JCheckBoxMenuItem eigeneMenuItem;
    private javax.swing.JTextField fieldDetailsuche;
    private javax.swing.JTextField fieldSchnellsuche;
    private javax.swing.ButtonGroup grpDBStatus;
    private javax.swing.ButtonGroup grpWhich;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JLabel label_activestoerfall;
    private javax.swing.JLabel label_tablestatustext;
    public javax.swing.JTabbedPane pane_stoerfalldetails;
    private javax.swing.JPanel panel_tableStatus;
    private javax.swing.JPanel panel_tableholder;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JSplitPane split_stoerfall;
    private javax.swing.JPopupMenu tableHeaderPopup;
    private javax.swing.JPopupMenu tablePopup;
    private org.jdesktop.swingx.JXTable table_stoerfall;
    private javax.swing.JToolBar toolbar;
    private javax.swing.JToolBar toolbar2;
    // End of variables declaration//GEN-END:variables

    public class ColumnHead {

        private String _name;
        private int _id;
        private boolean _active;
        private String _type;

        public ColumnHead(String _name, int _id, boolean _active) {
            this._name = _name;
            this._id = _id;
            this._active = _active;
        }

        public boolean isActive() {
            return _active;
        }

        public void setActive(boolean _active) {
            this._active = _active;
        }

        public int getId() {
            return _id;
        }

        public void setId(int _id) {
            this._id = _id;
        }

        public String getName() {
            return _name;
        }

        public void setName(String _name) {
            this._name = _name;
        }

        public String getType() {
            return _type;
        }

        public void setType(String _type) {
            this._type = _type;
        }

        @Override
        public String toString() {
            return this._name;
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource),
                new Dimension(16, 16));
    }

    /**
     *
     */
    class TableHeaderPopupListener extends MouseAdapter {

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
                tableHeaderPopup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
