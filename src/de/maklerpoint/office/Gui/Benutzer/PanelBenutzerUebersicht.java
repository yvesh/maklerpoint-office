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
 * PanelBenutzerUebersicht.java
 *
 * Created on Jul 10, 2010, 11:56:36 AM
 */
package de.maklerpoint.office.Gui.Benutzer;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Benutzer.Tools.BenutzerSQLMethods;
import de.maklerpoint.office.Benutzer.Tools.SearchBenutzer;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
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
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Word.ExportBenutzerDatenblatt;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.XML.BenutzerXMLExport;
import de.maklerpoint.office.Security.SecurityRoles;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.BenutzerUebersichtHeader;
import de.maklerpoint.office.Table.BenutzerUebersichtHeader.BenutzerColumnsWithTablefield;
import de.maklerpoint.office.Table.BenutzerUebersichtModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ImageTools;
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
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class PanelBenutzerUebersicht extends javax.swing.JPanel {

    private JDialog tableSettingsBox;
    private JDialog newBenutzerBox;
    private JDialog benDokumenteBox;
    private JDialog operatorBox;
    private JDialog briefBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean firstLoad = true;
    private Preferences prefs = Preferences.userRoot().node(PanelBenutzerUebersicht.class.getName());
    private int datenCount = 0;
    private Desktop desktop = Desktop.getDesktop();
    private AddBenutzerPanels panelAdd = new AddBenutzerPanels();
    private CRMView crm;
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private BenutzerObj selben = null;
    private int operator = TableValueChooseDialog.ENTHAELT;

    /** Creates new form PanelBenutzerUebersicht */
    public PanelBenutzerUebersicht(CRMView crm) {
        this.crm = crm;
        this.selben = null;
        initComponents();
        initialize();
    }

    public PanelBenutzerUebersicht(CRMView crm, BenutzerObj ben) {
        this.crm = crm;
        this.selben = ben;
        initComponents();
        initialize();
    }
    
    private void initialize() {
        addButtons();
        loadTableFieldSearch();
        loadTable();
        addReportCommandButton();
    }

    private void addButtons() {
        this.addExportCommandButton();
        this.addBriefCommandButton();
        this.addPrintCommandButton();
        addAnsichtButtons();
    }

    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Benutzeransicht");
        //dropDownButton.setText();
        this.toolbarBenutzer.add(dropDownButton);
    }
    
    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        reportButton.setPopupCallback(new ReportPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.toolbarBenutzer.add(reportButton, 11);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail senden");
        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.toolbarBenutzer.add(briefButton, 4);
    }

    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken", 
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("Ausgewählte Kunde(n) drucken");
        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.toolbarBenutzer.add(printButton, 6);
    }

    /**
     * BRIEF POPUP
     */
    private class BriefPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();

            JCommandMenuButton algbrief = new JCommandMenuButton("Allgem. Brief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algbrief.setExtraText("Einen allgemeiner Brief an den ausgewählten Benutzer.");
            algbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algbrief);

            JCommandMenuButton algfax = new JCommandMenuButton("Allgem. Fax",
                    getResizableIconFromResource(ResourceStrings.FAX_ICON));
            algfax.setExtraText("Ein allgemeines Fax an den ausgewählten Benutzer.");
            algfax.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(2)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algfax);

            JCommandMenuButton algemail = new JCommandMenuButton("Allgem. E-Mail",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            algemail.setExtraText("Eine allgemeines E-Mail an den ausgewählten Benutzer.");
            algemail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algemail);

            JCommandMenuButton terminbest = new JCommandMenuButton("Terminbestätigung Brief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            terminbest.setExtraText("Eine Terminbestätigung per Brief an den ausgewählten Kunden.");
            terminbest.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(4)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(terminbest);

            JCommandMenuButton terminbestmail = new JCommandMenuButton("Terminbestätigung (E-Mail)",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            terminbestmail.setExtraText("Eine Terminbestätigung  per E-Mail an den ausgewählten Kunden.");
            terminbestmail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(6)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(terminbestmail);


            JCommandMenuButton gebbrief = new JCommandMenuButton("Geburtstagsbrief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            gebbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(8)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(gebbrief);

            


            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.BEN);
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

        BenutzerObj ben = getSelectedBenutz();

        if (ben == null) {
            return;
        }

        // TODO Zusatzadresse für Benutzer? :)
//        ZusatzadressenObj za = null;
//
//        
//
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

                exp = new ExportBrief(brief, file, ben);
                exp.write();
                // TODO if(config.get("saveInKundenOrdner) ..
                desktop.open(new File(file));
            } else if (brief.getType() == Briefe.TYPE_FAX) {
            } else if (brief.getType() == Briefe.TYPE_EMAIL) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();

                mailDialog = new SendEmailDialog(mainFrame, true, Filesystem.getRootPath() + File.separator + brief.getFullpath(),
                        brief.getName(), ben.getEmail(), ben);
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

            JCommandMenuButton doc = new JCommandMenuButton("Benutzerdatenblatt (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportBenuzterDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            return popupMenu;
        }
    }

    public void openExportDialog(int type){
        BenutzerObj[] bens = getSelectedBenutzer();
        if(bens == null) {
            return;
        }
        
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exportBox = new ExportDialog(mainFrame, false, type, bens,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }
    
    public void xmlExport() {
        BenutzerObj[] bens = getSelectedBenutzer();

        if (bens == null) {
//            System.out.println("Kunden ist null");
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.XML),
                ExportImportTypen.getTypeName(ExportImportTypen.XML));

        if (file == null || file.length() < 1) {
//            System.out.println("File is null");
            return;
        }

        BenutzerXMLExport pxml = new BenutzerXMLExport(file, bens);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }
    
    /**
     * 
     */
    public void exportBenuzterDatenblatt() {
        BenutzerObj benutzer = getSelectedBenutz();
        if (benutzer == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null) {
            JOptionPane.showMessageDialog(null, "Sie müssen eine Datei zum speichern auswählen.",
                    "Keine Datei ausgewählt", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                ExportBenutzerDatenblatt ed = new ExportBenutzerDatenblatt(file, benutzer);
                ed.write();
                File createdFile = new File(file);
                desktop.open(createdFile);

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Benutzer nicht als Word Datei nicht exportieren", e);
                ShowException.showException("Konnte das Benutzerdatenblatt nicht als Worddatei (doc) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");

            }
        }

    }

    /**
     * 
     */
    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Kundenübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.KUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Kundendatenblatt",
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
    
    /**
     *
     */
    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Benutzer exportieren");
        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbarBenutzer.add(exportButton, 4);
    }

    /**
     * 
     * @return
     */
    public BenutzerObj getSelectedBenutz() {
        int rowcount = table_benutzer.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Benutzer aus.",
                    "Kein Benutzer ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_benutzer.getSelectedRow();
        BenutzerObj benutzer = (BenutzerObj) table_benutzer.getModel().getValueAt(row, 1);

        return benutzer;
    }

    public BenutzerObj[] getSelectedBenutzer() {

        ArrayList<BenutzerObj> benList = new ArrayList<BenutzerObj>();

        for (int i = 0; i < table_benutzer.getRowCount(); i++) {
            Boolean sel = (Boolean) table_benutzer.getModel().getValueAt(i, 0);
            if (sel) {
                benList.add((BenutzerObj) table_benutzer.getModel().getValueAt(i, 1));
            }
        }

        if (benList.isEmpty()) {
            BenutzerObj ben = getSelectedBenutz();
            if (ben != null) {
                benList.add(ben);
            } else {
//                JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestenes einen Kunden aus.",
//                        "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        }

        BenutzerObj bens[] = new BenutzerObj[benList.size()];

        bens = benList.toArray(bens);

        return bens;
    }

    public void openSorter() {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        tableSettingsBox = new BenutzerTabelleSorter(mainFrame, false, activeItems, inactiveItems, this);
        tableSettingsBox.setLocationRelativeTo(mainFrame);

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
//        System.out.println("Status: " + getStatus());
        BenutzerObj[] benutzer = BenutzerRegistry.getAllBenutzer(getStatus());
        createTable(benutzer);
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    public void createTable(BenutzerObj[] benutzer) {

        Object[][] data = null;
        Object[] columnNames = null;

        int selrow = -1;

        String columnHeadsIds = prefs.get("benutzerTableColumns", "5,6,7,9,11,12,15,3");

        String[] results = columnHeadsIds.split(",");
        activeItems = new Object[results.length];

        columnNames = new Object[results.length + 2];

        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = BenutzerUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < BenutzerUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                if (BenutzerUebersichtHeader.Columns[i] != null) {
                    al.add(new ColumnHead(BenutzerUebersichtHeader.Columns[i], i, false));
                }
            }
        }
        inactiveItems = al.toArray();


        if (benutzer != null) {
            data = new Object[benutzer.length][results.length + 3];

            for (int i = 0; i < benutzer.length; i++) {
                BenutzerObj benutz = benutzer[i];

                if (selben != null) {
                    if (selben.getId() == benutz.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = new Boolean(false);
                data[i][1] = benutzer[i];

                for (int j = 2; j <= results.length + 1; j++) {
                    //                System.out.println("i: " + i +"| j: " + j);
                    int result = Integer.valueOf(results[j - 2]);
                    if (result == 0) {
                        data[i][j] = benutz.getId();
                    } else if (result == 1) {
                        data[i][j] = benutz.getParentId();
                    } else if (result == 2) {
                        data[i][j] = benutz.getFirmenId();
                    } else if (result == 3) {
                        data[i][j] = SecurityRoles.getUserLevelName(benutz.getLevel());
                    } else if (result == 4) {
                        data[i][j] = benutz.isUnterVermittler();
                    } else if (result == 5) {
                        data[i][j] = benutz.getKennung();
                    } else if (result == 6) {
                        data[i][j] = benutz.getVorname();
                    } else if (result == 7) {
                        data[i][j] = benutz.getNachname();
                    } else if (result == 8) {
                        data[i][j] = "";
                    } else if (result == 9) {
                        data[i][j] = benutz.getStrasse();
                    } else if (result == 10) {
                        data[i][j] = benutz.getStrasse2();
                    } else if (result == 11) {
                        data[i][j] = benutz.getPlz();
                    } else if (result == 12) {
                        data[i][j] = benutz.getOrt();
                    } else if (result == 13) {
                        data[i][j] = benutz.getAddresseZusatz();
                    } else if (result == 14) {
                        data[i][j] = benutz.getAddresseZusatz2();
                    } else if (result == 15) {
                        data[i][j] = benutz.getTelefon();
                    } else if (result == 16) {
                        data[i][j] = benutz.getTelefon2();
                    } else if (result == 17) {
                        data[i][j] = benutz.getFax();
                    } else if (result == 18) {
                        data[i][j] = benutz.getFax2();
                    } else if (result == 19) {
                        data[i][j] = benutz.getMobil();
                    } else if (result == 20) {
                        data[i][j] = benutz.getMobil2();
                    } else if (result == 21) {
                        data[i][j] = benutz.getEmail();
                    } else if (result == 22) {
                        data[i][j] = benutz.getEmail2();
                    } else if (result == 23) {
                        data[i][j] = benutz.getHomepage();
                    } else if (result == 24) {
                        data[i][j] = benutz.getHomepage2();
                    } else if (result == 25) {
                        data[i][j] = benutz.getUsername();
                    } else if (result == 26) {
                        data[i][j] = "";
                    } else if (result == 27) {
                        data[i][j] = benutz.getComments();
                    } else if (result == 28) {
                        data[i][j] = benutz.getCustom1();
                    } else if (result == 29) {
                        data[i][j] = benutz.getCustom2();
                    } else if (result == 30) {
                        data[i][j] = benutz.getCustom3();
                    } else if (result == 31) {
                        data[i][j] = benutz.getCustom4();
                    } else if (result == 32) {
                        data[i][j] = benutz.getCustom5();
                    } else if (result == 33) {
                        data[i][j] = benutz.getCreated();
                    } else if (result == 34) {
                        data[i][j] = benutz.getLastlogin();
                    } else if (result == 35) {
                        data[i][j] = benutz.getLogincount();
                    } else if (result == 36) {
                        data[i][j] = benutz.getStatus();
                    }
                }
            }

            datenCount = benutzer.length;

            if (benutzer.length == 1) {
                this.label_tablestatustext.setText("Ein Datensatz");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            } else {
                this.label_tablestatustext.setText(benutzer.length + " Datensätze");
                this.label_tablestatustext.setForeground(new Color(-16738048));
            }
        } else {
            this.label_tablestatustext.setText("Keine Datensätze");
            this.label_tablestatustext.setForeground(Color.red);
            datenCount = 0;
        }

        TableModel model = new BenutzerUebersichtModel(data, columnNames);
        table_benutzer.setModel(model);
        table_benutzer.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());

        table_benutzer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_benutzer.setColumnSelectionAllowed(false);
        table_benutzer.setCellSelectionEnabled(false);
        table_benutzer.setRowSelectionAllowed(true);
        table_benutzer.setAutoCreateRowSorter(true);

//        table.getSelectionModel().addListSelectionListener(new RowListener());
        table_benutzer.setFillsViewportHeight(true);
        table_benutzer.removeColumn(table_benutzer.getColumnModel().getColumn(1));

        TableColumn check = table_benutzer.getColumnModel().getColumn(0);
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
        table_benutzer.addMouseListener(popupListener);

        for (int i = 3; i <= results.length + 1; i++) {
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(columnNames[i - 1].toString(), true);
//            System.out.println("C: " + columnNames[i-1].toString());
            tableHeaderPopup.add(menuItem);
        }

        JTableHeader header = table_benutzer.getTableHeader();
        MouseListener popupHeaderListener = new TableHeaderPopupListener();
        header.addMouseListener(popupHeaderListener);

        table_benutzer.packAll();
//
        table_benutzer.tableChanged(new TableModelEvent(table_benutzer.getModel()));
        table_benutzer.revalidate();

        table_benutzer.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_benutzer.getColumnModel().getColumn(0).setMaxWidth(20);

        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                this.label_activekunde.setText("Kein Benutzer ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        } else {
            if (selrow != -1) {
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Zeile des ausgewählten Benutzers in der Tabelle: " + selrow);
                }

                showBenutzer(selben);
                table_benutzer.requestFocusInWindow();
                table_benutzer.changeSelection(selrow, 0, false, false);

                selben = null;
            } else {
                showBenutzer(benutzer[0]);
                table_benutzer.requestFocusInWindow();
                table_benutzer.changeSelection(0, 0, false, false);
            }
        }
    }

    private void showBenutzer(BenutzerObj ben) {
        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        }

        this.label_activekunde.setText("Aktiver Benutzer: " + ben.getVorname() + " "
                + ben.getNachname() + " [" + ben.getKennung() + "]");
        panelAdd.load(ben);
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
                int row = table_benutzer.rowAtPoint(point);

                if (row != -1) {
                    BenutzerObj ben = (BenutzerObj) table_benutzer.getModel().getValueAt(row, 1);
                    showBenutzer(ben);
                }

                table_benutzer.changeSelection(row, 0, false, false);

                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(
                BenutzerUebersichtHeader.getSearchColumnsWithField()));
        combo_sucheFilter.setSelectedIndex(3);
        combo_sucheFilter.revalidate();
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

//        keyword = "*" + keyword + "*";        

        try {
            BenutzerObj[] ergebnis = SearchBenutzer.quickSearch(DatabaseConnection.open(), keyword);
            createTable(ergebnis);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Benutzer nicht durchsuchen", e);
            ShowException.showException("Die Benutzer Schnellsuche konnte nicht durchgeführt werden",
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

        BenutzerUebersichtHeader.BenutzerColumnsWithTablefield field = (BenutzerColumnsWithTablefield) this.combo_sucheFilter.getSelectedItem();

        try {
            BenutzerObj[] ergebnis = SearchBenutzer.searchBenutzerObject(DatabaseConnection.open(), field.getType(), keyword);
            createTable(ergebnis);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Benutzer Detailssuche nicht durchführen", e);
            ShowException.showException("Die Benutzer Detailsuche konnte nicht durchgeführt werden",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Detailsuche nicht durchführen");

        }
    }

    private void archiveSelectedBenutzer() {
        BenutzerObj[] bens = this.getSelectedBenutzer();

        if (bens == null) {
            return;
        }

        try {
            for (int i = 0; i < bens.length; i++) {
                BenutzerSQLMethods.archiveFromBenutzer(DatabaseConnection.open(), bens[i]);
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Kunde nicht archivieren", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde(n) nicht archivieren");
        }

        loadTable();
    }

    private void deleteSelectedBenutzer() {
        BenutzerObj[] bens = this.getSelectedBenutzer();

        if (bens == null) {
            return;
        }

        int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Benutzer wirklich löschen?",
                "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

        if (choose != JOptionPane.YES_OPTION) {
            return;
        }


        try {
            for (int i = 0; i < bens.length; i++) {
                BenutzerSQLMethods.deleteFromBenutzer(DatabaseConnection.open(), bens[i]);
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Kunde nicht archivieren", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde(n) nicht archivieren");
        }

        loadTable();
    }

    public BenutzerObj getSelben() {
        return selben;
    }

    public void setSelben(BenutzerObj selben) {
        this.selben = selben;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopupMenu = new javax.swing.JPopupMenu();
        neuBenutzerMenuItem = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenuItem();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        menuHeaderSettingsItem = new javax.swing.JMenuItem();
        grp_dbkunden = new javax.swing.ButtonGroup();
        ansichtPopup = new javax.swing.JPopupMenu();
        alledbMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        deleteddbMenuItem = new javax.swing.JCheckBoxMenuItem();
        toolbarBenutzer = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnKarte = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnStatistik = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnTableSettings = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        toolbarBenutzer2 = new javax.swing.JToolBar();
        jSeparator10 = new javax.swing.JToolBar.Separator();
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
        btnQuicksearch = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_benutzer = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activekunde = new javax.swing.JLabel();
        paneBenutzerDetails = new javax.swing.JTabbedPane();

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        neuBenutzerMenuItem.setMnemonic('N');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelBenutzerUebersicht.class);
        neuBenutzerMenuItem.setText(resourceMap.getString("neuBenutzerMenuItem.text")); // NOI18N
        neuBenutzerMenuItem.setName("neuBenutzerMenuItem"); // NOI18N
        tablePopupMenu.add(neuBenutzerMenuItem);

        menuSettings.setMnemonic('E');
        menuSettings.setText(resourceMap.getString("menuSettings.text")); // NOI18N
        menuSettings.setName("menuSettings"); // NOI18N
        menuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettingsActionPerformed(evt);
            }
        });
        tablePopupMenu.add(menuSettings);

        tableHeaderPopup.setName("tableHeaderPopup"); // NOI18N

        menuHeaderSettingsItem.setText(resourceMap.getString("menuHeaderSettingsItem.text")); // NOI18N
        menuHeaderSettingsItem.setName("menuHeaderSettingsItem"); // NOI18N
        menuHeaderSettingsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHeaderSettingsItemActionPerformed(evt);
            }
        });
        tableHeaderPopup.add(menuHeaderSettingsItem);

        ansichtPopup.setName("ansichtPopup"); // NOI18N

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

        toolbarBenutzer.setFloatable(false);
        toolbarBenutzer.setRollover(true);
        toolbarBenutzer.setName("toolbarBenutzer"); // NOI18N

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
        toolbarBenutzer.add(btnNeu);

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
        toolbarBenutzer.add(btnArchive);

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
        toolbarBenutzer.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbarBenutzer.add(jSeparator2);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbarBenutzer.add(jSeparator3);

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
        toolbarBenutzer.add(btnKarte);

        jSeparator4.setName("jSeparator4"); // NOI18N
        toolbarBenutzer.add(jSeparator4);

        btnStatistik.setIcon(resourceMap.getIcon("btnStatistik.icon")); // NOI18N
        btnStatistik.setText(resourceMap.getString("btnStatistik.text")); // NOI18N
        btnStatistik.setFocusable(false);
        btnStatistik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStatistik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnStatistik.setName("btnStatistik"); // NOI18N
        btnStatistik.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarBenutzer.add(btnStatistik);

        jSeparator6.setName("jSeparator6"); // NOI18N
        toolbarBenutzer.add(jSeparator6);

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
        toolbarBenutzer.add(btnTableSettings);

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
        toolbarBenutzer.add(btnRefresh);

        toolbarBenutzer2.setFloatable(false);
        toolbarBenutzer2.setRollover(true);
        toolbarBenutzer2.setName("toolbarBenutzer2"); // NOI18N

        jSeparator10.setName("jSeparator10"); // NOI18N
        toolbarBenutzer2.add(jSeparator10);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(93, 15));
        toolbarBenutzer2.add(jLabel1);

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
        toolbarBenutzer2.add(fieldSchnellsuche);

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
        toolbarBenutzer2.add(btnSchnellSuche);

        jSeparator11.setName("jSeparator11"); // NOI18N
        toolbarBenutzer2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(104, 16));
        toolbarBenutzer2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbarBenutzer2.add(jSeparator12);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
        toolbarBenutzer2.add(jLabel3);

        combo_sucheFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sucheFilter.setName("combo_sucheFilter"); // NOI18N
        toolbarBenutzer2.add(combo_sucheFilter);

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
        toolbarBenutzer2.add(btnOperator);

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
        toolbarBenutzer2.add(fieldDetailsuche);

        btnQuicksearch.setIcon(resourceMap.getIcon("btnQuicksearch.icon")); // NOI18N
        btnQuicksearch.setMnemonic('S');
        btnQuicksearch.setToolTipText(resourceMap.getString("btnQuicksearch.toolTipText")); // NOI18N
        btnQuicksearch.setFocusable(false);
        btnQuicksearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuicksearch.setName("btnQuicksearch"); // NOI18N
        btnQuicksearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQuicksearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuicksearchActionPerformed(evt);
            }
        });
        toolbarBenutzer2.add(btnQuicksearch);

        jSeparator13.setName("jSeparator13"); // NOI18N
        toolbarBenutzer2.add(jSeparator13);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N
        panel_tableholder.setLayout(new java.awt.BorderLayout());

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_benutzer.setModel(new javax.swing.table.DefaultTableModel(
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
        table_benutzer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_benutzer.setMinimumSize(new java.awt.Dimension(400, 150));
        table_benutzer.setName("table_benutzer"); // NOI18N
        table_benutzer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_benutzerMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_benutzer);

        panel_tableholder.add(scroll_protokolle, java.awt.BorderLayout.CENTER);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 782, Short.MAX_VALUE)
                .addComponent(label_activekunde)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addComponent(label_activekunde))
        );

        panel_tableholder.add(panel_tableStatus, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setTopComponent(panel_tableholder);

        paneBenutzerDetails.setName("paneBenutzerDetails"); // NOI18N
        jSplitPane1.setBottomComponent(paneBenutzerDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarBenutzer, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
            .addComponent(toolbarBenutzer2, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbarBenutzer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbarBenutzer2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(548, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSchnellSucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSchnellSucheActionPerformed
        this.quickSearch();
}//GEN-LAST:event_btnSchnellSucheActionPerformed

    private void menuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSettingsActionPerformed
        openSorter();
    }//GEN-LAST:event_menuSettingsActionPerformed

    private void menuHeaderSettingsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHeaderSettingsItemActionPerformed
        openSorter();
    }//GEN-LAST:event_menuHeaderSettingsItemActionPerformed

    private void fieldDetailsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDetailsucheKeyReleased
        if (this.fieldDetailsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            fieldSearch();
        }
    }//GEN-LAST:event_fieldDetailsucheKeyReleased

    private void fieldSchnellsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSchnellsucheActionPerformed
        this.quickSearch();
    }//GEN-LAST:event_fieldSchnellsucheActionPerformed

    private void fieldDetailsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDetailsucheActionPerformed
        fieldSearch();
    }//GEN-LAST:event_fieldDetailsucheActionPerformed

    private void btnQuicksearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuicksearchActionPerformed
        fieldSearch();
    }//GEN-LAST:event_btnQuicksearchActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        BenutzerObj ben = this.getSelectedBenutz();

        if (ben == null) {
            return;
        }

        KarteSuche.doExteneralSearch(ben.getStrasse() + ", " + ben.getOrt(), crm);
    }//GEN-LAST:event_btnKarteActionPerformed

    private void fieldSchnellsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldSchnellsucheKeyReleased
        if (this.fieldSchnellsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
    }//GEN-LAST:event_fieldSchnellsucheKeyReleased

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openSorter();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedBenutzer();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void table_benutzerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_benutzerMouseClicked
        int row = table_benutzer.getSelectedRow();

        if (row == -1) {
            return;
        }

        BenutzerObj ben = (BenutzerObj) table_benutzer.getModel().getValueAt(row, 1);

        this.showBenutzer(ben);

        if (evt.getClickCount() >= 2) {
//            selkunde = kunde;
//            JFrame mainFrame = CRM.getApplication().getMainFrame();
//            newKundeBox = new NewKundeDialog(mainFrame, false, kunde);
//            newKundeBox.addWindowListener(new WindowAdapter() {
//
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {
//
//                        @Override
//                        protected Void doInBackground() throws Exception {
//                            loadTable();
//                            Log.logger.debug("KundenDialog geschlossen");
//                            return null;
//                        }
//                    });
//                }
//            });
//            newKundeBox.setLocationRelativeTo(mainFrame);
//
//            CRM.getApplication().show(newKundeBox);
        }
}//GEN-LAST:event_table_benutzerMouseClicked

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

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedBenutzer();
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNeuActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    public javax.swing.JCheckBoxMenuItem alledbMenuItem;
    public javax.swing.JPopupMenu ansichtPopup;
    public javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    public javax.swing.JButton btnArchive;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnKarte;
    public javax.swing.JButton btnNeu;
    public javax.swing.JButton btnOperator;
    public javax.swing.JButton btnQuicksearch;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JButton btnSchnellSuche;
    public javax.swing.JButton btnStatistik;
    public javax.swing.JButton btnTableSettings;
    public javax.swing.JComboBox combo_sucheFilter;
    public javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    public javax.swing.JTextField fieldDetailsuche;
    public javax.swing.JTextField fieldSchnellsuche;
    public javax.swing.ButtonGroup grp_dbkunden;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JToolBar.Separator jSeparator10;
    public javax.swing.JToolBar.Separator jSeparator11;
    public javax.swing.JToolBar.Separator jSeparator12;
    public javax.swing.JToolBar.Separator jSeparator13;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator3;
    public javax.swing.JToolBar.Separator jSeparator4;
    public javax.swing.JToolBar.Separator jSeparator6;
    public javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JLabel label_activekunde;
    public javax.swing.JLabel label_tablestatustext;
    public javax.swing.JMenuItem menuHeaderSettingsItem;
    public javax.swing.JMenuItem menuSettings;
    public javax.swing.JMenuItem neuBenutzerMenuItem;
    public javax.swing.JTabbedPane paneBenutzerDetails;
    public javax.swing.JPanel panel_tableStatus;
    public javax.swing.JPanel panel_tableholder;
    public javax.swing.JScrollPane scroll_protokolle;
    public javax.swing.JPopupMenu tableHeaderPopup;
    public javax.swing.JPopupMenu tablePopupMenu;
    public org.jdesktop.swingx.JXTable table_benutzer;
    public javax.swing.JToolBar toolbarBenutzer;
    public javax.swing.JToolBar toolbarBenutzer2;
    // End of variables declaration//GEN-END:variables

    public class ColumnHead {

        private String _name;
        private int _id;
        private boolean _active;

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

        @Override
        public String toString() {
            return this._name;
        }
    }

    class TableHeaderPopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

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
