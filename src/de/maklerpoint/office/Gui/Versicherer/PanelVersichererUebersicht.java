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
 * PanelVersichererUebersicht.java
 *
 * Created on 17.08.2010, 12:35:05
 */
package de.maklerpoint.office.Gui.Versicherer;

import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.External.iReport;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Email.SendEmailDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Export.ExportDialog;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Gui.Kunden.KundenTabelleSorter;
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.Word.ExportVersichererDatenblatt;
import de.maklerpoint.office.Schnittstellen.XML.VersichererXMLExport;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.VersichererModel;
import de.maklerpoint.office.Table.VersichererUebersichtHeader;
import de.maklerpoint.office.Table.VersichererUebersichtHeader.ColumnsWithTablefield;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Tools.SearchVersicherer;
import de.maklerpoint.office.Versicherer.Tools.VersichererSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
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
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.openide.awt.DropDownButtonFactory;
import org.openide.util.Exceptions;

/**
 *
 * @author yves
 */
public class PanelVersichererUebersicht extends javax.swing.JPanel {

    private Preferences prefs = Preferences.userRoot().node(PanelVersichererUebersicht.class.getName());
    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean firstLoad = true;
    private JDialog tableSettingsBox;
    private JDialog operatorBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private int operator = TableValueChooseDialog.ENTHAELT;
    private int datenCount = 0;
    private JDialog vsDokumenteBox;
    private Desktop desktop = Desktop.getDesktop();
    private CRMView crm;
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private VersichererObj currentVers = null;
    private AddVersichererPanel panelAdd = new AddVersichererPanel();
    private VersichererObj selvers = null;
    
    private int currow = -1;

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
                final int row = table_versicherungen.rowAtPoint(point);
                
                table_versicherungen.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());
                
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        if (row != -1) {
                            if (row != currow) {
                                currow = row;
                                VersichererObj vers = (VersichererObj) 
                                        table_versicherungen.getModel().getValueAt(row, 1);
                                showVersicherer(vers);
                            }
                        }
                    }
                });
                
            }
        }
    }

    /** Creates new form PanelVersichererUebersicht */
    public PanelVersichererUebersicht(CRMView crm) {
        this.crm = crm;
        this.selvers = null;
        initComponents();
        initialize();
    }
    
    public PanelVersichererUebersicht(CRMView crm, VersichererObj vers) {
        this.crm = crm;
        this.selvers = vers;
        initComponents();
        initialize();
    }

    private void initialize() {
        addCommandButtons();
        loadTableFieldSearch();
        loadTable();
    }

    private void addCommandButtons() {
        this.addExportCommandButton();
        this.addPrintCommandButton();
        this.addBriefCommandButton();
        this.addAnsichtButtons();
        addReportCommandButton();
    }

    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        reportButton.setPopupCallback(new ReportPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.toolbar.add(reportButton, 13);
    }

    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("VVersichereransicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(VersichererUebersichtHeader.getColumnsWithField()));
        combo_sucheFilter.setSelectedIndex(4);
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
        createTable(VersicherungsRegistry.getVersicherer(getStatus()));
    }

    public void createTable(VersichererObj[] versicherer) {
        Object[][] data = null;
        Object[] columnNames = null;

        int selrow = -1;

        String columnHeadsIds = prefs.get("tableColumns", "4,3,8,9,10,11,19,20");

        String[] results = columnHeadsIds.split(",");

        activeItems = new Object[results.length];

        columnNames = new Object[results.length + 2];

        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = VersichererUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < VersichererUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                al.add(new ColumnHead(VersichererUebersichtHeader.Columns[i], i, false));
            }
        }

        inactiveItems = al.toArray();

        if (versicherer != null) {
            data = new Object[versicherer.length][results.length + 3];

            for (int i = 0; i < versicherer.length; i++) {
                VersichererObj versicher = versicherer[i];

                if (selvers != null) {
                    //System.out.println("Not null!");
                    if (versicher.getId() == selvers.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = false;
                data[i][1] = versicher;

                for (int j = 2; j <= results.length + 1; j++) {
                    int result = Integer.valueOf(results[j - 2]);

                    if (result == 0) {
                        data[i][j] = versicher.getId();
                    } else if (result == 1) {
                        data[i][j] = versicher.getParentId();
                    } else if (result == 2) {
                        data[i][j] = versicher.getParentName();
                    } else if (result == 3) {
                        data[i][j] = versicher.getVuNummer();
                    } else if (result == 4) {
                        data[i][j] = versicher.getName();
                    } else if (result == 5) {
                        data[i][j] = versicher.getNameZusatz();
                    } else if (result == 6) {
                        data[i][j] = versicher.getNameZusatz2();
                    } else if (result == 7) {
                        data[i][j] = versicher.getKuerzel();
                    } else if (result == 8) {
                        data[i][j] = versicher.getGesellschaftsNr();
                    } else if (result == 9) {
                        data[i][j] = versicher.getStrasse();
                    } else if (result == 10) {
                        data[i][j] = versicher.getPlz();
                    } else if (result == 11) {
                        data[i][j] = versicher.getStadt();
                    } else if (result == 12) {
                        data[i][j] = versicher.getBundesLand();
                    } else if (result == 13) {
                        data[i][j] = versicher.getLand();
                    } else if (result == 14) {
                        data[i][j] = versicher.isPostfach();
                    } else if (result == 15) {
                        data[i][j] = versicher.getPostfachName();
                    } else if (result == 16) {
                        data[i][j] = versicher.getPostfachPlz();
                    } else if (result == 17) {
                        data[i][j] = versicher.getPostfachOrt();
                    } else if (result == 18) {
                        data[i][j] = versicher.isVermittelbar();
                    } else if (result == 19) {
                        if (versicher.getCommunication1() != null && versicher.getCommunication1().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication1());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication1Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 20) {
                        if (versicher.getCommunication2() != null && versicher.getCommunication2().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication2());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication2Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 21) {
                        if (versicher.getCommunication3() != null && versicher.getCommunication3().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication3());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication3Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 22) {
                        if (versicher.getCommunication4() != null && versicher.getCommunication4().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication4());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication4Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 23) {
                        if (versicher.getCommunication5() != null && versicher.getCommunication5().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication5());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication5Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 24) {
                        if (versicher.getCommunication6() != null && versicher.getCommunication6().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(versicher.getCommunication6());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[versicher.getCommunication6Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 25) {
                        data[i][j] = "";
                    } else if (result == 26) {
                        data[i][j] = "";
                    } else if (result == 27) {
                        data[i][j] = "";
                    } else if (result == 28) {
                        data[i][j] = "";
                    } else if (result == 29) {
                        data[i][j] = "";
                    } else if (result == 30) {
                        data[i][j] = "";
                    } else if (result == 31) {
                        data[i][j] = versicher.getComments();
                    } else if (result == 32) {
                        data[i][j] = versicher.getCustom1();
                    } else if (result == 33) {
                        data[i][j] = versicher.getCustom2();
                    } else if (result == 34) {
                        data[i][j] = versicher.getCustom3();
                    } else if (result == 35) {
                        data[i][j] = versicher.getCustom4();
                    } else if (result == 36) {
                        data[i][j] = versicher.getCustom5();
                    } else if (result == 37) {
                        data[i][j] = dftable.format(versicher.getCreated());
                    } else if (result == 38) {
                        data[i][j] = dftable.format(versicher.getModified());
                    } else if (result == 39) {
                        data[i][j] = Status.getName(versicher.getStatus());
                    }
                }
            }

            datenCount = versicherer.length;

            if (datenCount == 1) {
                this.label_tablestatustext.setText("Ein Datensatz");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            } else {
                this.label_tablestatustext.setText(datenCount + " Datensätze");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            }

        } else {
            datenCount = 0;
            this.label_tablestatustext.setText("Keine Datensätze");
            this.label_tablestatustext.setForeground(Color.red);
        }

        TableModel model = new VersichererModel(data, columnNames);

        table_versicherungen.setModel(model);
        table_versicherungen.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_versicherungen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_versicherungen.setColumnSelectionAllowed(false);
        table_versicherungen.setCellSelectionEnabled(false);
        table_versicherungen.setRowSelectionAllowed(true);
        table_versicherungen.setAutoCreateRowSorter(true);

        table_versicherungen.setFillsViewportHeight(true);
        table_versicherungen.removeColumn(table_versicherungen.getColumnModel().getColumn(1));

        TableColumn check = table_versicherungen.getColumnModel().getColumn(0);
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

        check.setPreferredWidth(5);

        MouseListener popupListener = new TablePopupListener();

        table_versicherungen.addMouseListener(popupListener);

        JTableHeader header = table_versicherungen.getTableHeader();
        header.addMouseListener(popupListener);

        table_versicherungen.packAll();

        table_versicherungen.tableChanged(new TableModelEvent(table_versicherungen.getModel()));
        table_versicherungen.revalidate();

        table_versicherungen.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_versicherungen.getColumnModel().getColumn(0).setMaxWidth(20);

        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                panelAdd.disElements();
                this.label_activevers.setText("Keine Gesellschaft ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        } else {
            if (firstLoad && selvers != null) {
                firstLoad = false;
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Zeile der Gesellschaft"
                            + " in der Tabelle: " + selrow);
                }

                if (selrow != -1) {
                    showVersicherer(selvers);
                    table_versicherungen.requestFocusInWindow();
                    table_versicherungen.changeSelection(selrow, 0, false, false);
                } else {
                    showVersicherer(versicherer[0]);
                    table_versicherungen.requestFocusInWindow();
                    table_versicherungen.changeSelection(selrow, 0, false, false);
                }

                selvers = null;
            } else if (selvers != null) {
                if (selrow != -1) {
                    showVersicherer(selvers);
                    table_versicherungen.requestFocusInWindow();
                    table_versicherungen.changeSelection(selrow, 0, false, false);
                } else {
                    showVersicherer(versicherer[0]);
                    table_versicherungen.requestFocusInWindow();
                    table_versicherungen.changeSelection(0, 0, false, false);
                }
                selvers = null;
            } else {
                showVersicherer(versicherer[0]);
                table_versicherungen.requestFocusInWindow();
                table_versicherungen.changeSelection(0, 0, false, false);
            }
        }
    }

    /**
     * 
     * @param vers
     */
    private void showVersicherer(VersichererObj vers) {
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Gesellschaft: " + vers.getName());
        }
                
        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // Sollte nicht passieren
            }
        }

        this.label_activevers.setText("Aktive Gesellschaft: "
                + vers.getName() + " [" + vers.getVuNummer() + "]");
        panelAdd.load(vers);
    }

    public void quickSearch() {
        String keyword = this.fieldSchnellsuche.getText().trim();

        if (keyword == null) {
            return;
        }

        if (keyword.length() < 2) {
            loadTable();
            return;
        }

        try {
            VersichererObj[] res = SearchVersicherer.quickSearch(DatabaseConnection.open(), keyword);
            this.createTable(res);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Gesellschaften nicht durchsuchen", e);
            ShowException.showException("Die Schnellsuche konnte nicht durchgeführt werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Schnellsuche nicht durchführen");
        }
    }

    public void fieldSearch() {
        String keyword = this.fieldDetailsuche.getText().trim();
//        System.out.println("Keyword: " + keyword);
        if (keyword == null) {
            return;
        }

        if (keyword.length() < 2) {
            loadTable();
            return;
        }

        VersichererUebersichtHeader.ColumnsWithTablefield field = (ColumnsWithTablefield) this.combo_sucheFilter.getSelectedItem();

        try {
            VersichererObj[] vers = SearchVersicherer.searchVersichererObject(DatabaseConnection.open(), field.getType(), keyword);
            this.createTable(vers);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Detailsuche für die Gesellschaften nicht durchführen", e);
            ShowException.showException("Die Detailsuche konnte nicht durchgeführt werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Detailsuche nicht durchführen");
        }
    }

    public VersichererObj[] getSelectedVersicherer() {
        ArrayList<VersichererObj> versList = new ArrayList<VersichererObj>();


        for (int i = 0; i < table_versicherungen.getRowCount(); i++) {
            Boolean sel = (Boolean) table_versicherungen.getModel().getValueAt(i, 0);
            if (sel) {
                versList.add((VersichererObj) table_versicherungen.getModel().getValueAt(i, 1));
            }
        }

        if (versList.isEmpty()) {
            VersichererObj vers = getSelectedVersicherung();
            if (vers != null) {
                versList.add(vers);
            } else {
                return null;
            }
        }

        VersichererObj vers[] = new VersichererObj[versList.size()];

        vers = versList.toArray(vers);

        return vers;
    }

    /* Needs work */
    public VersichererObj getSelectedVersicherung() {
        int rowcount = table_versicherungen.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Gesellschaft aus.",
                    "Keine Gesellschaft ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_versicherungen.getSelectedRow();
        VersichererObj vs = (VersichererObj) table_versicherungen.getModel().getValueAt(row, 1);

        return vs;
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

            JCommandMenuButton doc = new JCommandMenuButton("Versichererdatenblatt (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportVersichererDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            return popupMenu;
        }
    }

    public void xmlExport() {
        VersichererObj[] vers = getSelectedVersicherer();

        if (vers == null) {
//            System.out.println("Kunden ist null");
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.XML),
                ExportImportTypen.getTypeName(ExportImportTypen.XML));

        if (file == null || file.length() < 1) {
//            System.out.println("File is null");
            return;
        }

        VersichererXMLExport pxml = new VersichererXMLExport(file, vers);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }

    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Versichererübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.KUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Versichererdatenblatt",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            datenblatt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.KUNDENDATENBLATT);
                }
            });

            popupMenu.addMenuButton(datenblatt);

            return popupMenu;
        }
    }

    public void openExportDialog(int type) {
        JFrame mainFrame = CRM.getApplication().getMainFrame();

        VersichererObj[] vs = getSelectedVersicherer();
        if (vs == null) {
            return;
        }

        exportBox = new ExportDialog(mainFrame, false, type, vs,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }

    /**
     * 
     */
    public void exportVersichererDatenblatt() {
        VersichererObj vs = this.getSelectedVersicherung();
        if (vs == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null) {
            JOptionPane.showMessageDialog(null, "Sie müssen eine Datei zum speichern auswählen.",
                    "Keine Datei ausgewählt", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                ExportVersichererDatenblatt ed = new ExportVersichererDatenblatt(file, vs);
                ed.write();
                File createdFile = new File(file);
                desktop.open(createdFile);

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Gesellschaft nicht als Word Datei nicht exportieren", e);
                ShowException.showException("Konnte das Gesellschaftsdatenblatt nicht als Worddatei (doc) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        }

    }

    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Gesellschaft(en) exportieren");
        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbar.add(exportButton, 6);
    }

    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("Ausgewählte Gesellschaft(n) drucken");
        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.toolbar.add(printButton, 7);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail senden");
        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.toolbar.add(briefButton, 6);
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

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
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
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

            JCommandMenuButton terminbest = new JCommandMenuButton("Terminbestätigung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            terminbest.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO
                }
            });

            popupMenu.addMenuButton(terminbest);


            JCommandMenuButton gebbrief = new JCommandMenuButton("Änderungsantrag",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            gebbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // TODO
                }
            });

            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.VERS);
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

        VersichererObj vers = getSelectedVersicherung();

        if (vers == null) {
            return;
        }

        ZusatzadressenObj za = null;

        // TODO add Ansprechpartner oder CustimizeDialog für Kombi mit Vertrag und Co.

//        if (brief.getType() == Briefe.TYPE_BRIEF) {
//            za = KundenAdresseAuswahlHelper.getZusatzadresse(kunde.getKundenNr());
//        }

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
                if (za == null) {
                    exp = new ExportBrief(brief, file, vers);
                } else {
//                    exp = new ExportBrief(brief, file, prod, za);
                }

                exp.write();
                // Todo if(config.get("saveInKundenOrdner) ..
                desktop.open(new File(file));
            } else if (brief.getType() == Briefe.TYPE_FAX) {
            } else if (brief.getType() == Briefe.TYPE_EMAIL) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();

                mailDialog = new SendEmailDialog(mainFrame, true, Filesystem.getRootPath() + File.separator + brief.getFullpath(),
                        brief.getName(), KundenMailHelper.getVersichererMail(vers), vers);
                mailDialog.setLocationRelativeTo(mainFrame);

                CRM.getApplication().show(mailDialog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * REPORTING
     */
    private class ReportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton kundenspiegel = new JCommandMenuButton("Kundenspiegel",
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

    private void archiveSelectedVersicherer() {
        VersichererObj[] vers = this.getSelectedVersicherer();

        if (vers == null) {
            return;
        }

        int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Gesellschaften wirklich archivieren?",
                "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

        if (choose != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            for (int i = 0; i < vers.length; i++) {
                VersichererSQLMethods.archiveFromVersicherer(DatabaseConnection.open(), vers[i]);
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Gesellschaften nicht archivieren", e);
            ShowException.showException("Die ausgewählten Gesellschaften konnten nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Gesellschaften nicht archivieren");
        }

        loadTable();
    }

    private void deleteSelectedVersicherer() {
        VersichererObj[] vers = this.getSelectedVersicherer();

        if (vers == null) {
            return;
        }

        int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Gesellschaften wirklich löschen?",
                "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

        if (choose != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            for (int i = 0; i < vers.length; i++) {
                VersichererSQLMethods.archiveFromVersicherer(DatabaseConnection.open(), vers[i]);
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Gesellschaften nicht löschen", e);
            ShowException.showException("Die ausgewählten Gesellschaften konnten nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Gesellschaften nicht löschen");
        }

        loadTable();
    }

    public VersichererObj getSelvers() {
        return selvers;
    }

    public void setSelvers(VersichererObj selvers) {
        this.selvers = selvers;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopup = new javax.swing.JPopupMenu();
        ansichtPopup = new javax.swing.JPopupMenu();
        alledbMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        deleteddbMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbkunden = new javax.swing.ButtonGroup();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        newVertrag = new javax.swing.JMenuItem();
        tableEinstellungenItem1 = new javax.swing.JMenuItem();
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
        jSeparator13 = new javax.swing.JToolBar.Separator();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDokumente = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnKarte = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnStatistik = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnTableSettings = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activevers = new javax.swing.JLabel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_versicherungen = new org.jdesktop.swingx.JXTable();
        pane_content = new javax.swing.JTabbedPane();

        tablePopup.setName("tablePopup"); // NOI18N

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        alledbMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelVersichererUebersicht.class);
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

        archivedbMenuItem.setMnemonic('A');
        archivedbMenuItem.setText(resourceMap.getString("archivedbMenuItem.text")); // NOI18N
        archivedbMenuItem.setName("archivedbMenuItem"); // NOI18N
        archivedbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(archivedbMenuItem);

        deleteddbMenuItem.setMnemonic('G');
        deleteddbMenuItem.setText(resourceMap.getString("deleteddbMenuItem.text")); // NOI18N
        deleteddbMenuItem.setName("deleteddbMenuItem"); // NOI18N
        deleteddbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteddbMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(deleteddbMenuItem);

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
        jLabel3.setPreferredSize(new java.awt.Dimension(35, 15));
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

        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setSeparatorSize(new java.awt.Dimension(15, 12));
        toolbar2.add(jSeparator13);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setToolTipText(resourceMap.getString("btnNeu.toolTipText")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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

        jSeparator7.setName("jSeparator7"); // NOI18N
        toolbar.add(jSeparator7);

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

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N
        panel_tableholder.setPreferredSize(new java.awt.Dimension(1066, 300));
        panel_tableholder.setLayout(new java.awt.BorderLayout());

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N

        label_tablestatustext.setText(resourceMap.getString("label_tablestatustext.text")); // NOI18N
        label_tablestatustext.setName("label_tablestatustext"); // NOI18N

        label_activevers.setText(resourceMap.getString("label_activevers.text")); // NOI18N
        label_activevers.setName("label_activevers"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_tablestatustext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 776, Short.MAX_VALUE)
                .addComponent(label_activevers)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext)
                .addComponent(label_activevers, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
        );

        panel_tableholder.add(panel_tableStatus, java.awt.BorderLayout.SOUTH);

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_versicherungen.setModel(new javax.swing.table.DefaultTableModel(
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
        table_versicherungen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_versicherungen.setMinimumSize(new java.awt.Dimension(400, 150));
        table_versicherungen.setName("table_versicherungen"); // NOI18N
        table_versicherungen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_versicherungenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_versicherungen);

        panel_tableholder.add(scroll_protokolle, java.awt.BorderLayout.CENTER);

        jSplitPane1.setTopComponent(panel_tableholder);

        pane_content.setName("pane_content"); // NOI18N
        jSplitPane1.setBottomComponent(pane_content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
            .addComponent(toolbar2, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbar2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(522, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedVersicherer();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDokumenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokumenteActionPerformed

        VersichererObj vs = this.getSelectedVersicherung();

        // TODO

//        JFrame mainFrame = CRM.getApplication().getMainFrame();
//        kundeDokumenteBox = new KundenDokumente(mainFrame, false, kunde);
//        kundeDokumenteBox.setLocationRelativeTo(mainFrame);
//        CRM.getApplication().show(kundeDokumenteBox);
}//GEN-LAST:event_btnDokumenteActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNeuActionPerformed

    private void fieldDetailsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDetailsucheKeyReleased
        if (this.fieldSchnellsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.fieldSearch();
        }
    }//GEN-LAST:event_fieldDetailsucheKeyReleased

    private void btnFieldsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFieldsearchActionPerformed
        this.fieldSearch();
    }//GEN-LAST:event_btnFieldsearchActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        VersichererObj vers = this.getSelectedVersicherung();
        if (vers == null) {
            return;
        }

        KarteSuche.doExteneralSearch(vers.getStrasse() + ", " + vers.getStadt(), crm);
}//GEN-LAST:event_btnKarteActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedVersicherer();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void table_versicherungenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_versicherungenMouseClicked
        int row = table_versicherungen.getSelectedRow();
        if (row == -1) {
            return;
        }

        if (row != currow) {
            currow = row;
            VersichererObj vt = (VersichererObj) this.table_versicherungen.getModel().getValueAt(row, 1);
            this.showVersicherer(vt);
        }

        if (evt.getClickCount() >= 2) {
            VersichererObj vt = (VersichererObj) this.table_versicherungen.getModel().getValueAt(row, 1);
            selvers = vt;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            //            newFirmenKundeBox = new NewFirmenKundeDialog(mainFrame, false, fa);
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
}//GEN-LAST:event_table_versicherungenMouseClicked

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    private javax.swing.JCheckBoxMenuItem alledbMenuItem;
    private javax.swing.JPopupMenu ansichtPopup;
    private javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDokumente;
    private javax.swing.JButton btnFieldsearch;
    private javax.swing.JButton btnKarte;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnOperator;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSchnellSuche;
    private javax.swing.JButton btnStatistik;
    private javax.swing.JButton btnTableSettings;
    private javax.swing.JComboBox combo_sucheFilter;
    private javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    private javax.swing.JTextField fieldDetailsuche;
    private javax.swing.JTextField fieldSchnellsuche;
    private javax.swing.ButtonGroup grp_dbkunden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel label_activevers;
    private javax.swing.JLabel label_tablestatustext;
    private javax.swing.JMenuItem newVertrag;
    public javax.swing.JTabbedPane pane_content;
    private javax.swing.JPanel panel_tableStatus;
    private javax.swing.JPanel panel_tableholder;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JMenuItem tableEinstellungenItem1;
    private javax.swing.JPopupMenu tableHeaderPopup;
    private javax.swing.JPopupMenu tablePopup;
    private org.jdesktop.swingx.JXTable table_versicherungen;
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