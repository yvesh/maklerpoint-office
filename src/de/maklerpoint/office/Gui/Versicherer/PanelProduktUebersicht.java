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
 * PanelProduktUebersicht.java
 *
 * Created on 17.09.2010, 10:53:00
 */
package de.maklerpoint.office.Gui.Versicherer;

import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.Email.SendEmailDialog;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Kunden.KundenTabelleSorter;
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.XML.ProduktXMLExport;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.ProduktUebersichtHeader;
import de.maklerpoint.office.Table.VersichererModel;
import de.maklerpoint.office.Tools.BooleanTools;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.Tools.ProdukteSQLMethods;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
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
import java.io.File;
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
public class PanelProduktUebersicht extends javax.swing.JPanel {

    private Preferences prefs = Preferences.userRoot().node(PanelProduktUebersicht.class.getName());
    private JDialog exportBox;
    private JDialog tableSettingsBox;
    private JDialog operatorBox;
    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean firstLoad = true;
    private JDialog mailDialog;
    private int datenCount = 0;
    private Desktop desktop = Desktop.getDesktop();
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private ProduktObj selprod;
    AddProduktePanel panelAdd = new AddProduktePanel();
    private int operator = TableValueChooseDialog.ENTHAELT;
    
    public PanelProduktUebersicht() {
        initComponents();
        intialize();
    }

    public PanelProduktUebersicht(ProduktObj prod) {
        this.selprod = prod;
        initComponents();
        intialize();
    }

    private void intialize() {
        addCommandButtons();
        loadTableFieldSearch();
        loadTable();
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
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Ausgewählter Versicherer (combo sel): " + this.combo_versicherer.getSelectedIndex());
        }

        if (this.combo_versicherer.getSelectedIndex() == 0 || this.combo_versicherer.getSelectedIndex() == -1) {
            createTable(VersicherungsRegistry.getProdukte(getStatus()));
        } else {
            try {
                VersichererObj vers = (VersichererObj) this.combo_versicherer.getSelectedItem();
                ProduktObj[] prods = VersicherungsRegistry.getProduktVersicherer(vers.getId(), getStatus());
                createTable(prods);
            } catch (Exception e) {
                Log.databaselogger.warn("Beim laden der Produkte für einen Versicherer trat ein Fehler auf.", e);
                createTable(VersicherungsRegistry.getProdukte(getStatus())); // Fallback
            }
        }
    }
    
    /**
     * 
     * @param prod 
     */
    
    private void createTable(ProduktObj[] prod) {

        Object[][] data = null;
        Object[] columnNames = null;

        int selrow = -1;

        String columnHeadsIds = prefs.get("tableColumns", "7,2,5,6,8,19,3");

        String[] results = columnHeadsIds.split(",");

        activeItems = new Object[results.length];

        columnNames = new Object[results.length + 2];

        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = ProduktUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < ProduktUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                al.add(new ColumnHead(ProduktUebersichtHeader.Columns[i], i, false));
            }
        }

        inactiveItems = al.toArray();

        if (prod != null) {
            data = new Object[prod.length][results.length + 3];

            for (int i = 0; i < prod.length; i++) {
                ProduktObj pr = prod[i];
                WaehrungenObj waer = VersicherungsRegistry.getWaehrung(pr.getWaehrung());

                data[i][0] = false;
                data[i][1] = pr;

                if (selprod != null) {
                    if (selprod.getId() == pr.getId()) {
                        selrow = i;
                    }
                }

                for (int j = 2; j <= results.length + 1; j++) {
                    int result = Integer.valueOf(results[j - 2]);

                    if (result == 0) {
                        data[i][j] = pr.getId();
                    } else if (result == 1) {
                        data[i][j] = VersicherungsRegistry.getVersicher(pr.getVersichererId()).toString();
                    } else if (result == 2) {
                        data[i][j] = VersicherungsRegistry.getSparte(pr.getSparteId()).toString();
                    } else if (result == 3) {
                        data[i][j] = BenutzerRegistry.getBenutzer(pr.getCreatorId()).toString();
                    } else if (result == 4) {
                        data[i][j] = pr.getArt(); // TODO
                    } else if (result == 5) {
                        data[i][j] = pr.getTarif(); // TODO
                    } else if (result == 6) {
                        data[i][j] = pr.getTarifBasis();
                    } else if (result == 7) {
                        data[i][j] = pr.getBezeichnung();
                    } else if (result == 8) {
                        data[i][j] = pr.getKuerzel();
                    } else if (result == 9) {
                        data[i][j] = "";
                    } else if (result == 10) {
                        data[i][j] = BooleanTools.getBooleanJaNein(pr.isVermittelbar());
                    } else if (result == 11) {
                        data[i][j] = pr.getVersicherungsart(); // TODO
                    } else if (result == 12) {
                        data[i][j] = pr.getRisikotyp();
                    } else if (result == 13) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getVersicherungsSumme(), waer);
                    } else if (result == 14) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getBewertungsSumme(), waer);
                    } else if (result == 15) {
                        data[i][j] = pr.getBedingungen();
                    } else if (result == 16) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getSelbstbeteiligung(), waer);
                    } else if (result == 17) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getNettopraemiePauschal(), waer);
                    } else if (result == 18) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getNettopraemieZusatz(), waer);
                    } else if (result == 19) {
                        data[i][j] = WaehrungFormat.getFormatedWaehrung(pr.getNettopraemieGesamt(), waer);
                    } else if (result == 20) {
                        data[i][j] = "";
                    } else if (result == 21) {
                        data[i][j] = pr.getZusatzInfo();
                    } else if (result == 22) {
                        data[i][j] = pr.getComments();
                    } else if (result == 23) {
                        data[i][j] = pr.getCustom1();
                    } else if (result == 24) {
                        data[i][j] = pr.getCustom2();
                    } else if (result == 25) {
                        data[i][j] = pr.getCustom3();
                    } else if (result == 26) {
                        data[i][j] = pr.getCustom4();
                    } else if (result == 27) {
                        data[i][j] = pr.getCustom5();
                    } else if (result == 28) {
                        data[i][j] = dftable.format(pr.getCreated());
                    } else if (result == 29) {
                        data[i][j] = dftable.format(pr.getModified());
                    } else if (result == 30) {
                        data[i][j] = Status.getName(pr.getStatus());
                    }
                }
            }

            datenCount = prod.length;

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

        table_produkte.setModel(model);
        table_produkte.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_produkte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_produkte.setColumnSelectionAllowed(false);
        table_produkte.setCellSelectionEnabled(false);
        table_produkte.setRowSelectionAllowed(true);
        table_produkte.setAutoCreateRowSorter(true);

        table_produkte.setFillsViewportHeight(true);
        table_produkte.removeColumn(table_produkte.getColumnModel().getColumn(1));

        TableColumn check = table_produkte.getColumnModel().getColumn(0);
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

        table_produkte.addMouseListener(popupListener);

        JTableHeader header = table_produkte.getTableHeader();
        header.addMouseListener(popupListener);

        table_produkte.packAll();

        table_produkte.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_produkte.getColumnModel().getColumn(0).setMaxWidth(20);


        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                panelAdd.disElements();
                this.label_activevers.setText("Kein Produkt ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        } else {
            if (selprod != null) {
                firstLoad = false;
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Zeile des ausgewählten Produkts in der Tabelle: " + selrow);
                }

                if (selrow != -1) {
                    showProdukt(selprod);
                    table_produkte.requestFocusInWindow();
                    table_produkte.changeSelection(selrow, 0, false, false);
                } else {
                    showProdukt(prod[0]);
                    table_produkte.requestFocusInWindow();
                    table_produkte.changeSelection(0, 0, false, false);
                }

                selprod = null;
            } else {
                showProdukt(prod[0]);
                table_produkte.requestFocusInWindow();
                table_produkte.changeSelection(0, 0, false, false);
            }
        }
    }

    private void showProdukt(ProduktObj prod) {
        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // Sollte nicht passieren
            }
        }

        this.label_activevers.setText("Aktives Produkt: " + prod.toString());
        panelAdd.load(prod);
    }

    private void addCommandButtons() {
        this.addExportCommandButton();
        this.addPrintCommandButton();
        this.addBriefCommandButton();
        this.addAnsichtButtons();
        addReportCommandButton();

        this.combo_versicherer.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVersichererCombo("Alle Gesellschaften")));
    }

    public void openExportDialog(int type) {
        JFrame mainFrame = CRM.getApplication().getMainFrame();

//         VersichererObj[] vs = getSelectedVersicherer();
//         if(vs == null)
//             return;
//
//         exportBox = new ExportDialog(mainFrame, false, type, vs,
//                                        activeItems, inactiveItems);
//         exportBox.setLocationRelativeTo(mainFrame);
//         CRM.getApplication().show(exportBox);
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(ProduktUebersichtHeader.getColumnsWithField()));
        combo_sucheFilter.setSelectedIndex(7);
        combo_sucheFilter.revalidate();
    }

    public void addExportCommandButton() {
        JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Produkte exportieren");
        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbar.add(exportButton, 4);
    }

    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        reportButton.setPopupCallback(new PrintPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.toolbar.add(reportButton, 8);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail senden");
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
        dropDownButton.setToolTipText("Produktansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton, 11);
    }

    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("Ausgewählte Produkte drucken");
        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.toolbar.add(printButton, 5);
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
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

            JCommandMenuButton doc = new JCommandMenuButton("Produktdatenblatt (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    exportKundenDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            return popupMenu;
        }
    }

    public void xmlExport() {
        ProduktObj[] prods = getSelectedProdukte();

        if (prods == null) {
//            System.out.println("Kunden ist null");
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.XML),
                ExportImportTypen.getTypeName(ExportImportTypen.XML));

        if (file == null || file.length() < 1) {
//            System.out.println("File is null");
            return;
        }

        ProduktXMLExport pxml = new ProduktXMLExport(file, prods);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
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
                    schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.PROD);
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

        ProduktObj prod = getSelectedProdukt();

        if (prod == null) {
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
                    exp = new ExportBrief(brief, file, prod);
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
                        brief.getName(), KundenMailHelper.getProduktMail(prod), prod);
                mailDialog.setLocationRelativeTo(mainFrame);

                CRM.getApplication().show(mailDialog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Produktübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.KUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Produktdatenblatt",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            datenblatt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.KUNDENDATENBLATT);
                }
            });

            popupMenu.addMenuButton(datenblatt);

            return popupMenu;
        }
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
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                Point point = e.getPoint();
                int row = table_produkte.rowAtPoint(point);

                table_produkte.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                if (row != -1) {
                    ProduktObj prod = (ProduktObj) table_produkte.getModel().getValueAt(row, 1);
                    showProdukt(prod);
                }
            }
        }
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

        // TODO
        //keyword = "*" + keyword + "*";

//         try {
//            VersichererObj[] res = SearchVersicherer.quickSearch(DatabaseConnection.open(), keyword);
//            this.createTable(res);
//        } catch (SQLException e) {
//            Log.databaselogger.fatal("Fehler: Konnte Versicherer nicht durchsuchen", e);
//            ShowException.showException("Die Schnellsuche konnte nicht durchgeführt werden.",
//                    ExceptionDialogGui.LEVEL_WARNING, e,
//                    "Schwerwiegend: Konnte Schnellsuche nicht durchführen");
//        }
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

        // TODO

//        VersichererUebersichtHeader.ColumnsWithTablefield field = (ColumnsWithTablefield) this.combo_sucheFilter.getSelectedItem();
//
//        try {
//            VersichererObj[] vers = SearchVersicherer.searchVersichererObject(DatabaseConnection.open(), field.getType(), keyword);
//            this.createTable(vers);
//        } catch (SQLException e) {
//            Log.databaselogger.fatal("Fehler: Konnte die Detailsuche für Versicherer nicht durchführen", e);
//            ShowException.showException("Die Detailsuche konnte nicht durchgeführt werden.",
//                    ExceptionDialogGui.LEVEL_WARNING, e,
//                    "Schwerwiegend: Konnte Detailsuche nicht durchführen");
//        }
    }

    public ProduktObj[] getSelectedProdukte() {
        ArrayList<ProduktObj> prodList = new ArrayList<ProduktObj>();

        for (int i = 0; i < table_produkte.getRowCount(); i++) {
            Boolean sel = (Boolean) table_produkte.getModel().getValueAt(i, 0);
            if (sel) {
                prodList.add((ProduktObj) table_produkte.getModel().getValueAt(i, 1));
            }
        }

        if (prodList.isEmpty()) {
            ProduktObj fk = getSelectedProdukt();
            if (fk != null) {
                prodList.add(fk);
            } else {
//                JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestenes einen Kunden aus.",
//                        "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        }

        ProduktObj[] prods = new ProduktObj[prodList.size()];

        prods = prodList.toArray(prods);

        return prods;
    }

    public ProduktObj getSelectedProdukt() {
        int rowcount = table_produkte.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Produkt aus.",
                    "Kein Produkt ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_produkte.getSelectedRow();
        ProduktObj vs = (ProduktObj) table_produkte.getModel().getValueAt(row, 1);

        return vs;
    }

    private void archiveSelectedProdukte() {
        ProduktObj[] prods = this.getSelectedProdukte();

        if (prods == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Produkte wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            for (int i = 0; i < prods.length; i++) {
                ProdukteSQLMethods.archiveFromProdukte(DatabaseConnection.open(), prods[i].getId());
            }
        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte Produkte nicht archivieren", e);
            ShowException.showException("Die ausgewählten Produkte konnte nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkte nicht archivieren");
        }

        loadTable();
    }

    private void deleteSelectedProdukte() {
        ProduktObj[] prods = this.getSelectedProdukte();

        if (prods == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Produkte wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            for (int i = 0; i < prods.length; i++) {
                ProdukteSQLMethods.deleteFromProdukte(DatabaseConnection.open(), prods[i].getId());
            }
        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte Produkte nicht löschen", e);
            ShowException.showException("Die ausgewählten Produkte konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Produkte nicht löschen");
        }

        loadTable();
    }

    public void openTableSettings() {
        if (tableSettingsBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tableSettingsBox = new KundenTabelleSorter(mainFrame, false,
                    activeItems, inactiveItems, this);
            tableSettingsBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(tableSettingsBox);
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
        toolbarVersicherer2 = new javax.swing.JToolBar();
        jLabel5 = new javax.swing.JLabel();
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
        jSeparator14 = new javax.swing.JToolBar.Separator();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnTableSettings = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        combo_versicherer = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_produkte = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activevers = new javax.swing.JLabel();
        pane_content = new javax.swing.JTabbedPane();

        tablePopup.setName("tablePopup"); // NOI18N

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        grp_dbkunden.add(alledbMenuItem);
        alledbMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelProduktUebersicht.class);
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

        setName("Form"); // NOI18N

        toolbarVersicherer2.setFloatable(false);
        toolbarVersicherer2.setRollover(true);
        toolbarVersicherer2.setMinimumSize(new java.awt.Dimension(450, 30));
        toolbarVersicherer2.setName("toolbarVersicherer2"); // NOI18N
        toolbarVersicherer2.setPreferredSize(new java.awt.Dimension(450, 30));

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(93, 15));
        toolbarVersicherer2.add(jLabel5);

        fieldSchnellsuche.setName("fieldSchnellsuche"); // NOI18N
        fieldSchnellsuche.setPreferredSize(new java.awt.Dimension(200, 25));
        fieldSchnellsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldSchnellsucheKeyTyped(evt);
            }
        });
        toolbarVersicherer2.add(fieldSchnellsuche);

        btnSchnellSuche.setIcon(resourceMap.getIcon("btnSchnellSuche.icon")); // NOI18N
        btnSchnellSuche.setMnemonic('S');
        btnSchnellSuche.setFocusable(false);
        btnSchnellSuche.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSchnellSuche.setName("btnSchnellSuche"); // NOI18N
        btnSchnellSuche.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSchnellSuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSchnellSucheActionPerformed(evt);
            }
        });
        toolbarVersicherer2.add(btnSchnellSuche);

        jSeparator11.setName("jSeparator11"); // NOI18N
        toolbarVersicherer2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(104, 16));
        toolbarVersicherer2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbarVersicherer2.add(jSeparator12);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
        toolbarVersicherer2.add(jLabel3);

        combo_sucheFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sucheFilter.setName("combo_sucheFilter"); // NOI18N
        toolbarVersicherer2.add(combo_sucheFilter);

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
        toolbarVersicherer2.add(btnOperator);

        fieldDetailsuche.setName("fieldDetailsuche"); // NOI18N
        fieldDetailsuche.setPreferredSize(new java.awt.Dimension(200, 25));
        fieldDetailsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldDetailsucheKeyReleased(evt);
            }
        });
        toolbarVersicherer2.add(fieldDetailsuche);

        btnFieldsearch.setIcon(resourceMap.getIcon("btnFieldsearch.icon")); // NOI18N
        btnFieldsearch.setMnemonic('S');
        btnFieldsearch.setFocusable(false);
        btnFieldsearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFieldsearch.setName("btnFieldsearch"); // NOI18N
        btnFieldsearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFieldsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFieldsearchActionPerformed(evt);
            }
        });
        toolbarVersicherer2.add(btnFieldsearch);

        jSeparator14.setName("jSeparator14"); // NOI18N
        toolbarVersicherer2.add(jSeparator14);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setMinimumSize(new java.awt.Dimension(450, 30));
        toolbar.setName("toolbar"); // NOI18N
        toolbar.setPreferredSize(new java.awt.Dimension(450, 30));

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
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

        jSeparator4.setName("jSeparator4"); // NOI18N
        toolbar.add(jSeparator4);

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

        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setSeparatorSize(new java.awt.Dimension(15, 12));
        toolbar.add(jSeparator13);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(83, 15));
        toolbar.add(jLabel1);

        combo_versicherer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_versicherer.setLightWeightPopupEnabled(false);
        combo_versicherer.setMaximumSize(new java.awt.Dimension(250, 32767));
        combo_versicherer.setName("combo_versicherer"); // NOI18N
        combo_versicherer.setPreferredSize(new java.awt.Dimension(200, 25));
        combo_versicherer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_versichererActionPerformed(evt);
            }
        });
        toolbar.add(combo_versicherer);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N
        jSplitPane1.setPreferredSize(new java.awt.Dimension(600, 456));

        panel_tableholder.setName("panel_tableholder"); // NOI18N
        panel_tableholder.setPreferredSize(new java.awt.Dimension(1066, 300));
        panel_tableholder.setLayout(new java.awt.BorderLayout());

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_produkte.setModel(new javax.swing.table.DefaultTableModel(
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
        table_produkte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_produkte.setMinimumSize(new java.awt.Dimension(400, 150));
        table_produkte.setName("table_produkte"); // NOI18N
        table_produkte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_produkteMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_produkte);

        panel_tableholder.add(scroll_protokolle, java.awt.BorderLayout.CENTER);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(450, 22));

        label_tablestatustext.setText(resourceMap.getString("label_tablestatustext.text")); // NOI18N
        label_tablestatustext.setName("label_tablestatustext"); // NOI18N

        label_activevers.setText(resourceMap.getString("label_activevers.text")); // NOI18N
        label_activevers.setName("label_activevers"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_tablestatustext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 604, Short.MAX_VALUE)
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

        jSplitPane1.setTopComponent(panel_tableholder);

        pane_content.setMinimumSize(new java.awt.Dimension(450, 150));
        pane_content.setName("pane_content"); // NOI18N
        pane_content.setPreferredSize(new java.awt.Dimension(450, 150));
        jSplitPane1.setBottomComponent(pane_content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
            .addComponent(toolbarVersicherer2, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbarVersicherer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(537, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)))
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

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        produktBox = new ProduktDialog(mainFrame, false);
        produktBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        return null;
                    }
                });
            }
        });
        produktBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(produktBox);
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedProdukte();
}//GEN-LAST:event_btnArchiveActionPerformed

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

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedProdukte();
}//GEN-LAST:event_btnDeleteActionPerformed

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

    private void table_produkteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_produkteMouseClicked
        final int row = table_produkte.getSelectedRow();

        if (row == -1) {
            return;
        }
//
//        Object obj = this.table_produkte.getModel().getValueAt(row, 1);
//        
//        System.out.println("Obj class: " + obj.getClass());
        
        ProduktObj prod = (ProduktObj) this.table_produkte.getModel().getValueAt(row, 1);

        this.showProdukt(prod);

        if (evt.getClickCount() >= 2) {
            selprod = prod;
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
}//GEN-LAST:event_table_produkteMouseClicked

    private void combo_versichererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_versichererActionPerformed
        loadTable();
    }//GEN-LAST:event_combo_versichererActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    public javax.swing.JCheckBoxMenuItem alledbMenuItem;
    public javax.swing.JPopupMenu ansichtPopup;
    public javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    public javax.swing.JButton btnArchive;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnFieldsearch;
    public javax.swing.JButton btnNeu;
    public javax.swing.JButton btnOperator;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JButton btnSchnellSuche;
    public javax.swing.JButton btnTableSettings;
    public javax.swing.JComboBox combo_sucheFilter;
    public javax.swing.JComboBox combo_versicherer;
    public javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    public javax.swing.JTextField fieldDetailsuche;
    public javax.swing.JTextField fieldSchnellsuche;
    public javax.swing.ButtonGroup grp_dbkunden;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JToolBar.Separator jSeparator11;
    public javax.swing.JToolBar.Separator jSeparator12;
    public javax.swing.JToolBar.Separator jSeparator13;
    public javax.swing.JToolBar.Separator jSeparator14;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator4;
    public javax.swing.JToolBar.Separator jSeparator6;
    public javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JLabel label_activevers;
    public javax.swing.JLabel label_tablestatustext;
    public javax.swing.JTabbedPane pane_content;
    public javax.swing.JPanel panel_tableStatus;
    public javax.swing.JPanel panel_tableholder;
    public javax.swing.JScrollPane scroll_protokolle;
    public javax.swing.JPopupMenu tablePopup;
    public org.jdesktop.swingx.JXTable table_produkte;
    public javax.swing.JToolBar toolbar;
    public javax.swing.JToolBar toolbarVersicherer2;
    // End of variables declaration//GEN-END:variables
    private JDialog produktBox;

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
