/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelVertraege.java
 *
 * Created on 20.06.2011, 09:53:30
 */
package de.maklerpoint.office.Gui.Vertraege;

import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.External.iReport;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.Email.SendEmailDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Export.ExportDialog;
import de.maklerpoint.office.Gui.Kunden.KundenTabelleSorter;
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Konstanten.Vertraege;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Reports.KundenVertragsspiegelReport;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.XML.VertragXMLExport;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.VertraegeUebersichtHeader;
import de.maklerpoint.office.Table.VertraegeUebersichtModel;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import de.maklerpoint.office.Vertraege.Tools.VertraegeSQLMethods;
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
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
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
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class PanelVertraege extends javax.swing.JPanel {

    private Object[] activeItems;
    private Object[] inactiveItems;
    private int datenCount = 0;
    private JDialog tableSettingsBox;
    private JDialog operatorBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private Desktop desktop = Desktop.getDesktop();
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleDateFormat df_day = new SimpleDateFormat("dd.MM.yyyy");
    private String[] defaultColumns = {"Auswahl", "Kunde", "Policen-Nr.", "Fälligkeit"}; // TODO
    private Preferences prefs = Preferences.userRoot().node(PanelVertraege.class.getName());
    private VertragObj selvertr = null;
    private int operator = TableValueChooseDialog.ENTHAELT;
    private AddVertragePanels panelAdd = new AddVertragePanels();
    private int currow = -1;

    /** Creates new form PanelVertraege */
    public PanelVertraege() {
        initComponents();
        initialize();
    }

    private void initialize() {
        addCommandButtons();
        loadTableFieldSearch();
        initTable();
        loadTable();
    }

    private void addCommandButtons() {
        addExportCommandButton();
        addPrintCommandButton();
        addBriefCommandButton();
        addAnsichtButtons();
        addReportCommandButton();
    }

    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Verträge exportieren");
        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbar.add(exportButton, 4);
    }

    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("");
        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.toolbar.add(printButton, 5);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail zu dem ausgewählten Vertrag versenden");
        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.toolbar.add(briefButton, 4);
    }

    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Vertragsansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    /**
     *  Report Command Button
     */
    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        RichTooltip tooltip = new RichTooltip("Vertragsreporting (Berichtswesen)", "Erstellen Sie professionelle Berichte zur "
                + "Ermittlung wichtiger Eckdaten Ihres Geschäftsbetriebes.");

        reportButton.setPopupRichTooltip(tooltip);

        reportButton.setPopupCallback(new ReportPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.toolbar.add(reportButton, 9);
    }

    /**
     * REPORTING
     */
    private class ReportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();

            JCommandMenuButton vertragsspiegel = new JCommandMenuButton("Vertragsspiegel",
                    getResizableIconFromResource(ResourceStrings.REPORT_ICON));
            vertragsspiegel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportVertragsspiegel();
                }
            });

            popupMenu.addMenuButton(vertragsspiegel);

            JCommandMenuButton kundenspiegel = new JCommandMenuButton("Stornospiegel",
                    getResizableIconFromResource(ResourceStrings.REPORT_ICON));
            kundenspiegel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO
                }
            });

            popupMenu.addMenuButton(kundenspiegel);

            JCommandMenuButton besuchsspiegel = new JCommandMenuButton("Zufriedenheitsspiegel",
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
     * 
     */
    private void exportVertragsspiegel() {
        String file = FileTools.saveFile("Report exportieren",
                null);

        if (file == null || file.length() < 1) {
            return;
        }

        try {
            KundenVertragsspiegelReport kvr = new KundenVertragsspiegelReport(
                    Config.getConfigInt("reportingFormat", KundenVertragsspiegelReport.PDF), file);
            kvr.write();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(
                VertraegeUebersichtHeader.getSearchColumnsWithField()));
        combo_sucheFilter.setSelectedIndex(10);
        combo_sucheFilter.revalidate();
    }

    public void openTableSettings() {
        if (tableSettingsBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tableSettingsBox = new KundenTabelleSorter(mainFrame, false, activeItems, inactiveItems, this);
            tableSettingsBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(tableSettingsBox);
    }

    private int getStatus() {
        int status = Status.NORMAL;

        if (this.alledbMenuItem.isSelected()) {
            return -1;
        } else if (this.aktivedbMenuItem.isSelected()) {
            return Status.NORMAL;
        } else if (this.archivedbMenuItem.isSelected()) {
            return Status.ARCHIVED;
        } else if (this.deleteddbMenuItem.isSelected()) {
            return Status.DELETED;
        }

        return status;
    }

    public void loadTable() {
        VertragObj[] vtr = null;

        if (eigeneMenuItem.isSelected()) {
            vtr = VertragRegistry.loadKundenVertraege(true, getStatus());
        } else if (alleMenuItem.isSelected()) {
            vtr = VertragRegistry.loadKundenVertraege(false, getStatus());
        } else {
            // Fallback
            vtr = VertragRegistry.loadKundenVertraege(true, getStatus());
        }

        loadSWTable(vtr);
    }

    private void loadSWTable(final VertragObj[] vtr) {
        // Falls doch mal ein SwingWorker oder invoke later eingebaut wird
        createTable(vtr);
    }

    private void initTable() {
        table_vertraege.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_vertraege.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_vertraege.setColumnSelectionAllowed(false);
        table_vertraege.setCellSelectionEnabled(false);
        table_vertraege.setRowSelectionAllowed(true);
        table_vertraege.setAutoCreateRowSorter(true);

//        table.getSelectionModel().addListSelectionListener(new RowListener());
        table_vertraege.setFillsViewportHeight(true);
    }

    private void createTable(VertragObj[] vtr) {

        Object[][] data = null;
        int selrow = -1;

        if (columnNames == null) {
            createColumnNames();
        }

        if (vtr != null) {
            data = new Object[vtr.length][columnNames.length + 2];

            for (int i = 0; i < vtr.length; i++) {
                VertragObj vt = vtr[i];

                if (selvertr != null) {
                    if (selvertr.getId() == vt.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = false;
                data[i][1] = vt;

                for (int j = 2; j < columnNames.length; j++) {
                    int result = columnNames[j].getId();
                    if (result == 0) {
                        data[i][j] = vt.getId();
                    } else if (result == 1) {
                        data[i][j] = vt.getParentId();
                    } else if (result == 2) {
                        data[i][j] = VersicherungsRegistry.getVersicher(vt.getVersichererId()).toString();
                    } else if (result == 3) {
                        data[i][j] = VersicherungsRegistry.getProdukt(vt.getProduktId()).toString();
                    } else if (result == 4) {
                        data[i][j] = vt.getKundenKennung();
                    } else if (result == 5) {
                        data[i][j] = KundenRegistry.getKunde(vt.getKundenKennung()).toString();
                    } else if (result == 6) {
                        data[i][j] = vt.getBeratungsprotokollId();
                    } else if (result == 7) {
                        data[i][j] = "";
                    } else if (result == 8) {
                        data[i][j] = BenutzerRegistry.getBenutzer(vt.getBenutzerId()).toString();
                    } else if (result == 9) {
                        data[i][j] = Vertraege.getTypName(vt.getVertragsTyp());
                    } else if (result == 10) {
                        data[i][j] = vt.getVertragGrp();
                    } else if (result == 11) {
                        data[i][j] = vt.getPolicennr();
                    } else if (result == 12) {
                        data[i][j] = df_day.format(vt.getPoliceDatum());
                    } else if (result == 13) {
                        data[i][j] = df_day.format(vt.getWertungDatum());
                    } else if (result == 14) {
                        data[i][j] = BooleanTools.getBooleanJaNein(vt.isCourtage());
                    } else if (result == 15) {
                        data[i][j] = Vertraege.getZahlungName(vt.getZahlWeise());
                    } else if (result == 16) {
                        data[i][j] = vt.getZahlArt();
                    } else if (result == 17) {
                        data[i][j] = vt.getSelbstbeteiligung();
                    } else if (result == 18) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(vt.getJahresNetto(), vt.getWaehrungId());
                    } else if (result == 19) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(vt.getSteuer(), vt.getWaehrungId());
                    } else if (result == 20) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(vt.getGebuehr(), vt.getWaehrungId());
                    } else if (result == 21) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(vt.getJahresBrutto(), vt.getWaehrungId());
                    } else if (result == 22) {
                        data[i][j] = vt.getRabatt();
                    } else if (result == 23) {
                        data[i][j] = vt.getZuschlag();
                    } else if (result == 24) {
                        data[i][j] = df_day.format(vt.getAntrag());
                    } else if (result == 25) {
                        data[i][j] = df_day.format(vt.getFaellig());
                    } else if (result == 26) {
                        data[i][j] = df_day.format(vt.getHauptfaellig());
                    } else if (result == 27) {
                        data[i][j] = df_day.format(vt.getBeginn());
                    } else if (result == 28) {
                        data[i][j] = df_day.format(vt.getAblauf());
                    } else if (result == 29) {
                        data[i][j] = dftable.format(vt.getMaklerEingang());
                    } else if (result == 30) {
                        data[i][j] = df_day.format(vt.getStornoDatum());
                    } else if (result == 31) {
                        data[i][j] = dftable.format(vt.getStorno());
                    } else if (result == 32) {
                        data[i][j] = vt.getStornoGrund();
                    } else if (result == 33) {
                        data[i][j] = vt.getLaufzeit();
                    } else if (result == 34) {
                        data[i][j] = df_day.format(vt.getCourtage_datum());
                    } else if (result == 35) {
                        data[i][j] = vt.getComments();
                    } else if (result == 36) {
                        data[i][j] = vt.getCustom1();
                    } else if (result == 37) {
                        data[i][j] = vt.getCustom2();
                    } else if (result == 38) {
                        data[i][j] = vt.getCustom3();
                    } else if (result == 39) {
                        data[i][j] = vt.getCustom4();
                    } else if (result == 40) {
                        data[i][j] = vt.getCustom5();
                    } else if (result == 41) {
                        data[i][j] = dftable.format(vt.getCreated());
                    } else if (result == 42) {
                        data[i][j] = dftable.format(vt.getModified());
                    } else if (result == 43) {
                        data[i][j] = Vertraege.getStatusName(vt.getStatus());
                    }
                }
            }
            datenCount = vtr.length;
            if (datenCount == 1) {
                this.label_tablestatustext.setText("Ein Datensatz");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            } else {
                this.label_tablestatustext.setText(datenCount + " Datensätze");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            }
        } else {
            this.label_tablestatustext.setText("Keine Datensätze");
            this.label_tablestatustext.setForeground(Color.red);
            datenCount = 0;
        }

        setTable(data, selrow);
    }

    /**
     * 
     * @param data
     * @param selrow 
     */
    private void setTable(Object data[][], int selrow) {
        table_vertraege.setModel(new VertraegeUebersichtModel(data, columnNames));
        table_vertraege.removeColumn(table_vertraege.getColumnModel().getColumn(1));

        TableColumn check = table_vertraege.getColumnModel().getColumn(0);
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
        table_vertraege.addMouseListener(popupListener);

        JTableHeader header = table_vertraege.getTableHeader();
        MouseListener popupHeaderListener = new TableHeaderPopupListener();
        header.addMouseListener(popupHeaderListener);

        table_vertraege.packAll();

        table_vertraege.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_vertraege.getColumnModel().getColumn(0).setMaxWidth(20);

        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                panelAdd.disElements();
                this.label_activekunde.setText("Kein Vertrag ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // Sollte nicht passieren
            }
        } else {
            if (selrow != -1) {
                showVertrag(selvertr);
                table_vertraege.requestFocusInWindow();
                table_vertraege.changeSelection(selrow, 0, false, false);

                selvertr = null;
            } else {
                //System.out.println("Row: " + selrow);
                showVertrag((VertragObj) data[0][1]);
                table_vertraege.requestFocusInWindow();
                table_vertraege.changeSelection(0, 0, false, false);
            }
        }
    }
    // ColumnNames
    private ColumnHead[] columnNames = null;

    public void createColumnNames() {
        String columnHeadsIds = prefs.get("tableColumns", "11,5,2,3,18,27,26,43"); // Policennummer, Kunde, Versicherer, Produkt

        String[] results = columnHeadsIds.split(",");
        activeItems = new Object[results.length];

        columnNames = new ColumnHead[results.length + 2];
        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = VertraegeUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < VertraegeUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                al.add(new ColumnHead(VertraegeUebersichtHeader.Columns[i], i, false));
            }
        }
        inactiveItems = al.toArray();
    }

    private void showVertrag(VertragObj vtr) {
        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        }

        this.label_activekunde.setText("Aktiver Vertrag: ".concat(vtr.toString()));
        panelAdd.load(vtr);
    }

    class TablePopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {

                Point point = e.getPoint();
                final int row = table_vertraege.rowAtPoint(point);
                table_vertraege.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {

                        if (row != -1) {
                            VertragObj vtr = (VertragObj) table_vertraege.getModel().getValueAt(row, 1);
                            showVertrag(vtr);
                        }
                    }
                });
            }
        }
    }

    /**
     * 
     * @return Verträge
     */
    public VertragObj[] getSelectedVertraege() {
        ArrayList<VertragObj> vertragList = new ArrayList<VertragObj>();

        for (int i = 0; i < table_vertraege.getRowCount(); i++) {
            Boolean sel = (Boolean) table_vertraege.getModel().getValueAt(i, 0);
            if (sel) {
                vertragList.add((VertragObj) table_vertraege.getModel().getValueAt(i, 1));
            }
        }

        if (vertragList.isEmpty()) {
            VertragObj fk = getSelectedVertrag();
            if (fk != null) {
                vertragList.add(fk);
            } else {
                return null;
            }
        }

        VertragObj vtr[] = new VertragObj[vertragList.size()];
        vtr = vertragList.toArray(vtr);

        return vtr;
    }

    /**
     * 
     * @return Vertrag
     */
    public VertragObj getSelectedVertrag() {
        int rowcount = table_vertraege.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Vertrag aus.",
                    "Kein Vertrag ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_vertraege.getSelectedRow();
        VertragObj fk = (VertragObj) table_vertraege.getModel().getValueAt(row, 1);

        return fk;
    }

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

            JCommandMenuButton xlsx = new JCommandMenuButton("als Excel 2007 Datei (.xlsx)",
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
                    xmlExport();
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

            JCommandMenuButton doc = new JCommandMenuButton("Vertragsdatenblatt (Word)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.setExtraText("Informationsdatenblatt zum ausgewählten Vertrag.");
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportVertragDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            return popupMenu;
        }
    }

    public void xmlExport() {
        VertragObj[] vtrs = getSelectedVertraege();

        if (vtrs == null) {
//            System.out.println("Kunden ist null");
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.XML),
                ExportImportTypen.getTypeName(ExportImportTypen.XML));

        if (file == null || file.length() < 1) {
//            System.out.println("File is null");
            return;
        }

        VertragXMLExport pxml = new VertragXMLExport(file, vtrs);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }

    public void exportVertragDatenblatt() {
        VertragObj vtr = getSelectedVertrag();

        if (vtr == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null) {
            return;
        }

        // TODO
    }

    public void openExportDialog(int type) {
        VertragObj[] vtr = getSelectedVertraege();

        if (vtr == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exportBox = new ExportDialog(mainFrame, false, type, vtr,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }

    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Vertragsübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.FIRMENKUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Vertragsdatenblatt",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            datenblatt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.FIRMENKUNDENDATENBLATT);
                }
            });

            popupMenu.addMenuButton(datenblatt);

            return popupMenu;
        }
    }

    private class BriefPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton algbrief = new JCommandMenuButton("Allgem. Kunden Brief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algbrief.setExtraText("Ein allgemeiner Brief an den Kunden des ausgewählten Vertrages.");
            algbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algbrief);

            JCommandMenuButton algfax = new JCommandMenuButton("Allgem. Kunden Fax",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algbrief.setExtraText("Ein allgemeines Fax an den Kunden des ausgewählten Vertrages.");

            algfax.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(2)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algfax);

            JCommandMenuButton algemail = new JCommandMenuButton("Allgem. Kunden E-Mail",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            algbrief.setExtraText("Eine allgemeine E-Mail an den Kunden des ausgewählten Vertrages.");
            algemail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algemail);

            JCommandMenuButton bestueb = new JCommandMenuButton("Bestandsübertragung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            bestueb.setExtraText("Mitteilung einer Bestandübertragung an den Versicherer.");
            bestueb.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(16)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(bestueb);


            JCommandMenuButton vertraend = new JCommandMenuButton("Vertragsänderung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            vertraend.setExtraText("Mitteilung einer Vertragsänderung an den Versicherer.");
            vertraend.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(16)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(vertraend);

            JCommandMenuButton adressaend = new JCommandMenuButton("Adressänderung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            adressaend.setExtraText("Mitteilung über eine Adressänderung des Kunden an den Versicherer.");
            adressaend.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(4)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(adressaend);

            JCommandMenuButton kuendigung = new JCommandMenuButton("Kündigung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            kuendigung.setExtraText("Mitteilung der Kündigung an den Versicherer.");
            kuendigung.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(6)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(kuendigung);


            JCommandMenuButton stillleg = new JCommandMenuButton("Stillegung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            stillleg.setExtraText("Mitteilung der Stilllegung an den Versicherer.");
            stillleg.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(10)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(stillleg);


            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.VERTR);
                    schreibeBrief(brief);
                }
            });

            popupMenu.addMenuButton(mehr);

            return popupMenu;
        }
    }

    private void schreibeBrief(BriefObj brief) {
        if (brief == null) {
            return;
        }

        VertragObj vtr = getSelectedVertrag();

        if (vtr == null) {
            return;
        }


        String file = null;

        if (brief.getType() != Briefe.TYPE_EMAIL) {
            file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                    ExportImportTypen.getTypeName(ExportImportTypen.DOC));

            if (file == null || file.length() < 1) {
                return;
            }
        }

        try {
            if (brief.getType() == Briefe.TYPE_BRIEF) {

                ExportBrief exp = null;
                exp = new ExportBrief(brief, file, vtr);


                exp.write();
                // Todo if(config.get("saveInKundenOrdner) ..
                desktop.open(new File(file));
            } else if (brief.getType() == Briefe.TYPE_FAX) {
            } else if (brief.getType() == Briefe.TYPE_EMAIL) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();

                Object kunde = KundenRegistry.getKunde(vtr.getKundenKennung());

                // TODO Change to getClass
                try {
                    KundenObj knd = (KundenObj) kunde;
                    mailDialog = new SendEmailDialog(mainFrame, true, Filesystem.getRootPath() + File.separator + brief.getFullpath(),
                            brief.getName(), KundenMailHelper.getKundenMail(knd), knd);
                    mailDialog.setLocationRelativeTo(mainFrame);

                    CRM.getApplication().show(mailDialog);
                } catch (Exception e) {
                    FirmenObj knd = (FirmenObj) kunde;
                    mailDialog = new SendEmailDialog(mainFrame, true, Filesystem.getRootPath() + File.separator + brief.getFullpath(),
                            brief.getName(), KundenMailHelper.getGeschKundenMail(knd), knd);
                    mailDialog.setLocationRelativeTo(mainFrame);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vertrag bearbeiten
     * danach Tabelle neuladen
     */
    private void editVertrag() {
        final int row = table_vertraege.getSelectedRow();

        if (row == -1) {
            return;
        }

        VertragObj vt = (VertragObj) table_vertraege.getModel().getValueAt(row, 1);

        selvertr = vt;
//            JFrame mainFrame = CRM.getApplication().getMainFrame();
//            newFirmenKundeBox = new NewFirmenKundeDialog(mainFrame, true, fa);
//            newFirmenKundeBox.addWindowListener(new WindowAdapter() {
//
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {
//
//                        @Override
//                        protected Void doInBackground() throws Exception {
//                            loadTable();
//                            Log.logger.debug("Geschäftskunden Dialog wurde geschlossen.");
//                            return null;
//                        }
//                    });
//                }
//            });
//            newFirmenKundeBox.setLocationRelativeTo(mainFrame);
//
//            CRM.getApplication().show(newFirmenKundeBox);
    }

    /**
     * Ausgewählte Verträge archivieren
     */
    private void archiveSelectedVertraege() {
        VertragObj[] vtr = this.getSelectedVertraege();

        if (vtr == null) {
            return;
        }

        int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Verträge wirklich archivieren?",
                "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

        if (choose != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            for (int i = 0; i < vtr.length; i++) {
                VertraegeSQLMethods.archiveFromVertraege(DatabaseConnection.open(), vtr[i]);
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Verträge nicht archivieren", e);
            ShowException.showException("Die ausgewählten Verträge konnten nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Verträge nicht archivieren");
        }

        loadTable();
    }

    /**
     * Ausgewählte Verträge löschen
     */
    private void deleteSelectedVertraege() {
        VertragObj[] vtr = this.getSelectedVertraege();

        if (vtr == null) {
            return;
        }

        int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Verträge wirklich löschen?",
                "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

        if (choose != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            for (int i = 0; i < vtr.length; i++) {
                VertraegeSQLMethods.deleteFromVertraege(DatabaseConnection.open(), vtr[i]);
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Verträge nicht löschen", e);
            ShowException.showException("Die ausgewählten Verträge konnten nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Verträge nicht archivieren");
        }

        loadTable();
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    private void quickSearch() {
    }

    private void fieldSearch() {
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
        btnGrpKunden = new javax.swing.ButtonGroup();
        grp_dbkunden = new javax.swing.ButtonGroup();
        tablePopup = new javax.swing.JPopupMenu();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        newVertrag = new javax.swing.JMenuItem();
        tableEinstellungenItem1 = new javax.swing.JMenuItem();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jSeparator5 = new javax.swing.JToolBar.Separator();
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
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_vertraege = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activekunde = new javax.swing.JLabel();
        pane_contentholder = new javax.swing.JTabbedPane();

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        btnGrpKunden.add(eigeneMenuItem);
        eigeneMenuItem.setMnemonic('E');
        eigeneMenuItem.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelVertraege.class);
        eigeneMenuItem.setText(resourceMap.getString("eigeneMenuItem.text")); // NOI18N
        eigeneMenuItem.setName("eigeneMenuItem"); // NOI18N
        eigeneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eigeneMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(eigeneMenuItem);

        btnGrpKunden.add(alleMenuItem);
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

        grp_dbkunden.add(alledbMenuItem);
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

        grp_dbkunden.add(aktivedbMenuItem);
        aktivedbMenuItem.setMnemonic('A');
        aktivedbMenuItem.setSelected(true);
        aktivedbMenuItem.setText(resourceMap.getString("aktivedbMenuItem.text")); // NOI18N
        aktivedbMenuItem.setName("aktivedbMenuItem"); // NOI18N
        aktivedbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktivedbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(aktivedbMenuItem);

        grp_dbkunden.add(archivedbMenuItem);
        archivedbMenuItem.setMnemonic('A');
        archivedbMenuItem.setText(resourceMap.getString("archivedbMenuItem.text")); // NOI18N
        archivedbMenuItem.setName("archivedbMenuItem"); // NOI18N
        archivedbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(archivedbMenuItem);

        grp_dbkunden.add(deleteddbMenuItem);
        deleteddbMenuItem.setMnemonic('G');
        deleteddbMenuItem.setText(resourceMap.getString("deleteddbMenuItem.text")); // NOI18N
        deleteddbMenuItem.setName("deleteddbMenuItem"); // NOI18N
        deleteddbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteddbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(deleteddbMenuItem);

        tablePopup.setBackground(resourceMap.getColor("tablePopup.background")); // NOI18N
        tablePopup.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablePopup.setName("tablePopup"); // NOI18N

        tableHeaderPopup.setName("tableHeaderPopup"); // NOI18N

        newVertrag.setText(resourceMap.getString("newVertrag.text")); // NOI18N
        newVertrag.setName("newVertrag"); // NOI18N
        newVertrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newVertragActionPerformed(evt);
            }
        });
        tableHeaderPopup.add(newVertrag);

        tableEinstellungenItem1.setText(resourceMap.getString("tableEinstellungenItem1.text")); // NOI18N
        tableEinstellungenItem1.setName("tableEinstellungenItem1"); // NOI18N
        tableEinstellungenItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableEinstellungenItem1ActionPerformed(evt);
            }
        });
        tableHeaderPopup.add(tableEinstellungenItem1);

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

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setText(resourceMap.getString("btnArchive.text")); // NOI18N
        btnArchive.setToolTipText(resourceMap.getString("btnArchive.toolTipText")); // NOI18N
        btnArchive.setFocusable(false);
        btnArchive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnArchive.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        btnDelete.setToolTipText(resourceMap.getString("btnDelete.toolTipText")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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

        jSeparator5.setName("jSeparator5"); // NOI18N
        toolbar.add(jSeparator5);

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
        fieldSchnellsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldSchnellsucheKeyTyped(evt);
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
        toolbar2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(104, 16));
        toolbar2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbar2.add(jSeparator12);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldDetailsucheKeyTyped(evt);
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

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(600, 166));
        jSplitPane1.setName("jSplitPane1"); // NOI18N
        jSplitPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        panel_tableholder.setName("panel_tableholder"); // NOI18N
        panel_tableholder.setPreferredSize(new java.awt.Dimension(1066, 400));
        panel_tableholder.setLayout(new java.awt.BorderLayout());

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
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
        table_vertraege.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_vertraege.setMinimumSize(new java.awt.Dimension(400, 150));
        table_vertraege.setName("table_vertraege"); // NOI18N
        table_vertraege.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_vertraegeMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_vertraege);

        panel_tableholder.add(scroll_protokolle, java.awt.BorderLayout.CENTER);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(1062, 25));

        label_tablestatustext.setText(resourceMap.getString("label_tablestatustext.text")); // NOI18N
        label_tablestatustext.setName("label_tablestatustext"); // NOI18N

        label_activekunde.setText(resourceMap.getString("label_activekunde.text")); // NOI18N
        label_activekunde.setName("label_activekunde"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_tablestatustext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 606, Short.MAX_VALUE)
                .addComponent(label_activekunde)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(label_activekunde, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
        );

        panel_tableholder.add(panel_tableStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setTopComponent(panel_tableholder);

        pane_contentholder.setName("pane_contentholder"); // NOI18N
        jSplitPane1.setBottomComponent(pane_contentholder);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
            .addComponent(toolbar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedVertraege();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedVertraege();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void fieldSchnellsucheKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldSchnellsucheKeyTyped
        if (this.fieldSchnellsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
}//GEN-LAST:event_fieldSchnellsucheKeyTyped

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

    private void fieldDetailsucheKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDetailsucheKeyTyped
        if (this.fieldDetailsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            fieldSearch();
        }
}//GEN-LAST:event_fieldDetailsucheKeyTyped

    private void btnFieldsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFieldsearchActionPerformed
        fieldSearch();
}//GEN-LAST:event_btnFieldsearchActionPerformed

    private void table_vertraegeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_vertraegeMouseClicked
        final int row = table_vertraege.getSelectedRow();

        if (row == -1) {
            return;
        }

        if (row != currow) {
            currow = row;
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    VertragObj vt = (VertragObj) table_vertraege.getModel().getValueAt(row, 1);
                    showVertrag(vt);
                }
            });
        }

        if (evt.getClickCount() >= 2) {
            editVertrag();
        }
}//GEN-LAST:event_table_vertraegeMouseClicked

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

    private void newVertragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newVertragActionPerformed
//        newKunde();
}//GEN-LAST:event_newVertragActionPerformed

    private void tableEinstellungenItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEinstellungenItem1ActionPerformed
        openTableSettings();
}//GEN-LAST:event_tableEinstellungenItem1ActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNeuActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    private javax.swing.JCheckBoxMenuItem alleMenuItem;
    private javax.swing.JCheckBoxMenuItem alledbMenuItem;
    private javax.swing.JPopupMenu ansichtPopup;
    private javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFieldsearch;
    private javax.swing.ButtonGroup btnGrpKunden;
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
    private javax.swing.ButtonGroup grp_dbkunden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel label_activekunde;
    private javax.swing.JLabel label_tablestatustext;
    private javax.swing.JMenuItem newVertrag;
    public javax.swing.JTabbedPane pane_contentholder;
    private javax.swing.JPanel panel_tableStatus;
    private javax.swing.JPanel panel_tableholder;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JMenuItem tableEinstellungenItem1;
    private javax.swing.JPopupMenu tableHeaderPopup;
    private javax.swing.JPopupMenu tablePopup;
    private org.jdesktop.swingx.JXTable table_vertraege;
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
}