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
 * PanelKundenUebersicht.java
 *
 * Created on Jul 7, 2010, 11:40:17 AM
 */
package de.maklerpoint.office.Gui.Kunden;

import com.google.gdata.util.AuthenticationException;
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
import de.maklerpoint.office.Gui.Dokumente.KundenDokumente;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Export.ExportDialog;
import de.maklerpoint.office.Gui.Firmenkunden.NewFirmenKundeDialog;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.KundenAdresseAuswahlHelper;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.SearchKunden;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Google.GoogleContacts;
import de.maklerpoint.office.Schnittstellen.Reports.KundenVertragsspiegelReport;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.Word.ExportPrivatKundenDatenblatt;
import de.maklerpoint.office.Schnittstellen.Word.ExportPrivatKundenInfoblatt;
import de.maklerpoint.office.Schnittstellen.XML.PrivatkundenXMLExport;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.KundenUebersichtHeader;
import de.maklerpoint.office.Table.KundenUebersichtHeader.ColumnsWithTablefield;
import de.maklerpoint.office.Table.KundenUebersichtModel;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class PanelKundenUebersicht extends javax.swing.JPanel {

    private Preferences prefs = Preferences.userRoot().node(PanelKundenUebersicht.class.getName());
//    private KundenUebersichtHelper helper = new KundenUebersichtHelper();
    private JDialog tableSettingsBox;
    private boolean kundenOpen = false;
    private JDialog newKundeBox;
    private JDialog newFirmenKundeBox;
    private JDialog kundeDokumenteBox;
    private JDialog operatorBox;
    private JDialog briefBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private JDialog empfohlenekundenDialog;
    private Object[] activeItems;
    private Object[] inactiveItems;
    private String[] defaultColumns = {"Auswahl", "Kdnr", "Vorname", "Nachname", "Strasse", "PLZ", "Ort",
        "Kommunikation 1", "Kommunikation 2", "Kommunikation 3"};
    private int datenCount = 0;
    private Desktop desktop = Desktop.getDesktop();
    private CRMView crm;
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private KundenObj selkunde = null;
    private AddKundenPanels panelAdd = new AddKundenPanels();
    private int operator = TableValueChooseDialog.ENTHAELT;
    private int currow = -1;

    /** Creates new form PanelKundenUebersicht */
    public PanelKundenUebersicht(CRMView crm) {
        prefs = Preferences.userRoot().node(PanelKundenUebersicht.class.getName());
        this.crm = crm;
        initComponents();
        initialize();
    }

    /**
     *
     * @param crm
     * @param kunde
     */
    public PanelKundenUebersicht(CRMView crm, KundenObj kunde) {
        prefs = Preferences.userRoot().node(PanelKundenUebersicht.class.getName());
        this.selkunde = kunde;
        this.crm = crm;
        initComponents();
        initialize();
    }

    private void initialize() {
        loadCmd();
        loadTableFieldSearch();
        initTable();
        loadTable();
    }

    /**
     * 
     * @param panel
     */
    public void deleteTable(PanelKundenUebersicht panel) {
        panel.table_kunden.setVisible(false);
        panel.updateUI();
        panel.revalidate();
        panel.remove(panel.table_kunden);
    }

    /**
     * TODO get it working
     * @param kunde
     */
    public final void changeSelection(KundenObj kunde) {
        table_kunden.changeSelection(1, 0, true, true);
    }

    /**
     *
     */
    private class NeuPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton privat = new JCommandMenuButton("Privatkunde",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/icon_clean/user-white.png"));
            privat.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    newKundeBox = new NewKundeDialog(mainFrame, false);
                    newKundeBox.addWindowListener(new WindowAdapter() {

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
                    newKundeBox.setLocationRelativeTo(mainFrame);

                    CRM.getApplication().show(newKundeBox);
                }
            });

            popupMenu.addMenuButton(privat);

            JCommandMenuButton firma = new JCommandMenuButton("Geschäftskunde",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/icon_clean/user-business-boss.png"));
            firma.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    newFirmenKundeBox = new NewFirmenKundeDialog(mainFrame, false);
                    newFirmenKundeBox.addWindowListener(new WindowAdapter() {

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
                    newFirmenKundeBox.setLocationRelativeTo(mainFrame);

                    CRM.getApplication().show(newFirmenKundeBox);
                }
            });

            popupMenu.addMenuButton(firma);

            return popupMenu;
        }
    }

    /**
     *
     */
    public void openExportDialog(int type) {
        KundenObj[] kunden = getSelectedKunden();
        if (kunden == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exportBox = new ExportDialog(mainFrame, false, type, kunden,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }

    /**
     * Working :)
     * @return 
     */
    public KundenObj[] getSelectedKunden() {

        ArrayList<KundenObj> kundenList = new ArrayList<KundenObj>();

        for (int i = 0; i < table_kunden.getRowCount(); i++) {
            Boolean sel = (Boolean) table_kunden.getModel().getValueAt(i, 0);
            if (sel) {
                kundenList.add((KundenObj) table_kunden.getModel().getValueAt(i, 1));
            }
        }

        if (kundenList.isEmpty()) {
            KundenObj kunde = getSelectedKunde();
            if (kunde != null) {
                kundenList.add(kunde);
            } else {
//                JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestenes einen Kunden aus.",
//                        "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        }

        KundenObj kunden[] = new KundenObj[kundenList.size()];

        kunden = kundenList.toArray(kunden);

        return kunden;
    }

    /* Needs work */
    public KundenObj getSelectedKunde() {
        int rowcount = table_kunden.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.",
                    "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_kunden.getSelectedRow();
        KundenObj kunde = (KundenObj) table_kunden.getModel().getValueAt(row, 1);

        return kunde;
    }

    /**
     * DOC EXPORT
     */
    public void exportKundenDatenblatt() {
        KundenObj kunde = getSelectedKunde();

        if (kunde == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null || file.length() < 1) {
            return;
        }

        try {
            ExportPrivatKundenDatenblatt ed = new ExportPrivatKundenDatenblatt(file, kunde);
            ed.write();
            File createdFile = new File(file);
            desktop.open(createdFile);

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte ExportPrivatKundenDatenblatt nicht als nicht exportieren", e);
            ShowException.showException("Konnte das Kundendatenblatt nicht als Worddatei (doc) exportieren.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
        }
    }
    
    private void exportKundenInfoblatt() {
        KundenObj kunde = getSelectedKunde();

        if (kunde == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null || file.length() < 1) {
            return;
        }

        try {
            ExportPrivatKundenInfoblatt ed = new ExportPrivatKundenInfoblatt(file, kunde);
            ed.write();
            File createdFile = new File(file);
            desktop.open(createdFile);

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte ExportPrivatKundenInfoblatt nicht als Word Datei exportieren", e);
            ShowException.showException("Das Kundeninformationsblatt konnte nicht "
                    + "als Worddatei (doc) exportiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
        }
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

            RichTooltip tooltip1 = new RichTooltip("Export als CSV Datei", "Exportiert die ausgewählten Kunden und Spalten als "
                    + "kommagetrennte Textdatei");

            csv.setPopupRichTooltip(tooltip1);

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

            JCommandMenuButton doc = new JCommandMenuButton("Kundendatenblatt (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.setExtraText("Die kompletten zum Kunden vorhandenen Daten (auch Verträge, Konten, Zusatzadressen etc.) - lang.");
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportKundenDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            JCommandMenuButton kundeninfo = new JCommandMenuButton("Kundeninfo (.doc)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            kundeninfo.setExtraText("Ein einseitiges Informationsdatenblatt zum Kunden mit den wichtigsten Daten. (anpassbar)");
            kundeninfo.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    exportKundenInfoblatt();
                }
            });

            popupMenu.addMenuButton(kundeninfo);

            JCommandMenuButton google = new JCommandMenuButton("Zu Ihren Google Kontakten)",
                    getResizableIconFromResource(ResourceStrings.GOOGLE_ICON));
            google.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportGoogle();
                }
            });

            popupMenu.addMenuButton(google);

            return popupMenu;
        }
    }

    private void exportGoogle() {
        KundenObj[] kndn = getSelectedKunden();

        if (kndn == null) {
            return;
        }
        try {
            GoogleContacts.transmitKunden(kndn);
        } catch (MalformedURLException e) {
            Log.logger.fatal("Die Feedadresse des Google Kontakt Services stimmt nicht.", e);
            ShowException.showException("Die Anmeldung mit Ihrem Google Account ist fehlgeschlagen (Falscher Benutzername / Password). "
                    + "Bitte überprüfen Sie die Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Google Anmeldung nicht durchführen");
        } catch (AuthenticationException e) {
            Log.logger.fatal("Die Authorisierung mit den Google Servern ist fehlgeschlagen.", e);
            ShowException.showException("Die Anmeldung mit Ihrem Google Account ist fehlgeschlagen (Falscher Benutzername / Password). "
                    + "Bitte überprüfen Sie die Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Google Anmeldung nicht durchführen");
        }
    }

    /**
     * BRIEF POPUP
     */
    private class BriefPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();

            JCommandMenuButton algbrief = new JCommandMenuButton("Allgem. Brief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algbrief.setExtraText("Einen allgemeiner Brief an den ausgewählten Kunden.");
            algbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algbrief);


            // TODO: Herausfinden ob ein Submenü geht
//            JCommandPopupMenu popupMenu2 = new JCommandPopupMenu();
//
//            JCommandMenuButton anKunden = new JCommandMenuButton("An Kunden", getResizableIconFromResource(
//                    "de/acyrance/CRM/Gui/resources/icon_clean/user-white.png"));
//            popupMenu2.addMenuButton(anKunden); 
//                        
//            popupMenu.add(popupMenu2);        


            JCommandMenuButton algfax = new JCommandMenuButton("Allgem. Fax",
                    getResizableIconFromResource(ResourceStrings.FAX_ICON));
            algfax.setExtraText("Ein allgemeines Fax an den ausgewählten Kunden.");
            algfax.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(2)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algfax);

            JCommandMenuButton algemail = new JCommandMenuButton("Allgem. E-Mail",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            algemail.setExtraText("Eine allgemeine E-Mail an den ausgewählten Kunden.");
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

            JCommandMenuButton makauftrag = new JCommandMenuButton("Maklerauftrag",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            makauftrag.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(13)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(makauftrag);

            JCommandMenuButton makeinzel = new JCommandMenuButton("Maklereinzelauftrag",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            makeinzel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(15)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(makeinzel);


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

        if (brief == null) {
            return;
        }

        KundenObj kunde = getSelectedKunde();

        if (kunde == null) {
            return;
        }

        ZusatzadressenObj za = null;

        // TODO add CustimizeDialog für Kombi mit Vertrag und Co.

        if (brief.getType() == Briefe.TYPE_BRIEF) {
            try {
                ZusatzadressenObj[] zas = ZusatzadressenSQLMethods.loadZusatzadressen(
                        DatabaseConnection.open(), kunde.getKundenNr(), Status.NORMAL);
                if (zas != null) {
                    za = KundenAdresseAuswahlHelper.getZusatzadresse(kunde.getKundenNr());
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }


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
                if (za == null) {
                    exp = new ExportBrief(brief, file, kunde);
                } else {
                    exp = new ExportBrief(brief, file, kunde, za);
                }

                exp.write();
                // Todo if(config.get("saveInKundenOrdner) ..
                desktop.open(new File(file));
            } else if (brief.getType() == Briefe.TYPE_FAX) {
            } else if (brief.getType() == Briefe.TYPE_EMAIL) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();

                mailDialog = new SendEmailDialog(mainFrame, true, Filesystem.getRootPath() + File.separator + brief.getFullpath(),
                        brief.getName(), KundenMailHelper.getKundenMail(kunde), kunde);
                mailDialog.setLocationRelativeTo(mainFrame);

                CRM.getApplication().show(mailDialog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void openPrintDialog(int type) {
    }

    /**
     * PRINTING
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
                    exportVertragsspiegel();
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

    /**
     * 
     * @param resource
     * @return
     */
    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource),
                new Dimension(16, 16));
    }

    /**
     * 
     */
    public void addNeuCommandButton() {

        JCommandButton neuButton = new JCommandButton("Neu", getResizableIconFromResource(
                "de/acyrance/CRM/Gui/resources/add.png"));
        neuButton.setExtraText("Neuer Privat- oder Geschäftskunde");
        neuButton.setPopupCallback(new NeuPopupCallback());
        neuButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
        neuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        neuButton.setFlat(true);

        neuButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                newKundeBox = new NewKundeDialog(mainFrame, false);
                newKundeBox.addWindowListener(new WindowAdapter() {

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
                newKundeBox.setLocationRelativeTo(mainFrame);

                CRM.getApplication().show(newKundeBox);
            }
        });

        this.kundenToolbar.add(neuButton, 0);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail senden");
        RichTooltip tooltip = new RichTooltip("Brief / E-Mail / Fax", "Schreiben Sie einen Brief, "
                + "ein Fax oder eine E-Mail an den ausgewählten (markierten) Kunden.");

        briefButton.setPopupRichTooltip(tooltip);

        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.kundenToolbar.add(briefButton, 6);
    }

    /**
     *
     */
    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Kunde(n) exportieren");

        RichTooltip tooltip = new RichTooltip("Kundenexport", "Exportiert die ausgewählten Kunden und Spalten im gewünschten Format.");

        exportButton.setPopupRichTooltip(tooltip);

        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.kundenToolbar.add(exportButton, 7);
    }

    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Kundenansicht");
        //dropDownButton.setText();
        this.kundenToolbar.add(dropDownButton);
    }

    public void openTableSettings() {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        tableSettingsBox = new KundenTabelleSorter(mainFrame, false, activeItems, inactiveItems, this);
        tableSettingsBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(tableSettingsBox);
    }

    /**
     * 
     */
    public void createNewKunde() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newKundeBox = new NewKundeDialog(mainFrame, true);
        newKundeBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        System.out.println("Closed");
                        return null;
                    }
                });
            }
        });
        newKundeBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(newKundeBox);
    }

    private void initTable() {
        table_kunden.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kunden.setColumnSelectionAllowed(false);
        table_kunden.setCellSelectionEnabled(false);
        table_kunden.setRowSelectionAllowed(true);
        table_kunden.setAutoCreateRowSorter(true);

//        table.getSelectionModel().addListSelectionListener(new RowListener());
        table_kunden.setFillsViewportHeight(true);
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


        KundenObj[] kunden = null;

        if (this.eigeneMenuItem.isSelected()) {
            kunden = KundenRegistry.getEigeneKunden(getStatus());
        } else if (this.alleMenuItem.isSelected()) {
            kunden = KundenRegistry.getKunden(getStatus());
        } else {
            // FALLBACK
            kunden = KundenRegistry.getEigeneKunden(getStatus());
        }

//        long end = System.currentTimeMillis();
//        System.out.println("Zeit für Query: " + (end - start) + " milliseconds");
        loadSWTable(kunden);

    }

    private void loadSWTable(final KundenObj[] kunden) {

//        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){
//
//            @Override
//            protected Void doInBackground() throws Exception {
//        long start = System.currentTimeMillis();
        
//        SwingUtilities.invokeLater(new Runnable(){
//
//            public void run() {
                createTable(kunden);
//            }            
//        });
        
//        long end = System.currentTimeMillis();
//        System.out.println("Zeit für creatTable: " + (end - start) + " milliseconds");
//                return null;
//            }
//        };
//        
//        sw.execute();
    }

    /**
     * 
     */
    public void createTable(KundenObj[] kunden) {
//        long begin = System.currentTimeMillis();

        Object[][] data = null;
        int selrow = -1;

        if (columnNames == null) {
            createColumnNames();
        }

        if (kunden != null) {
            data = new Object[kunden.length][columnNames.length + 2];

            for (int i = 0; i < kunden.length; i++) {
                KundenObj kunde = kunden[i];

                if (selkunde != null) {
                    if (kunde.getId() == selkunde.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = false;
                data[i][1] = kunde;

//                System.out.println("Columnnames length: " + columnNames.length);

                for (int j = 2; j < columnNames.length; j++) {
                    int result = columnNames[j].getId();
                    data[i][j] = getKundeResult(kunde, result);
//                    System.out.println("Result: " + result + " (j = " + j + ")");

//                    if (result == 0) {
//                        data[i][j] = kunde.getId();
//                    } else if (result == 1) {
//                        data[i][j] = BenutzerRegistry.getBenutzer(kunde.getBetreuerId());
//                    } else if (result == 2) {
//                        data[i][j] = BenutzerRegistry.getBenutzer(kunde.getCreatorId());
//                    } else if (result == 3) {
//                        data[i][j] = kunde.getKundenNr();
//                    } else if (result == 4) {
//                        data[i][j] = kunde.getAnrede();
//                    } else if (result == 5) {
//                        data[i][j] = kunde.getTitel();
//                    } else if (result == 6) {
//                        data[i][j] = kunde.getFirma();
//                    } else if (result == 7) {
//                        data[i][j] = kunde.getVorname();
//                    } else if (result == 8) {
//                        data[i][j] = kunde.getVorname2();
//                    } else if (result == 9) {
//                        data[i][j] = kunde.getVornameWeitere();
//                    } else if (result == 10) {
//                        data[i][j] = kunde.getNachname();
//                    } else if (result == 11) {
//                        data[i][j] = kunde.getStreet();
//                    } else if (result == 12) {
//                        data[i][j] = kunde.getPlz();
//                    } else if (result == 13) {
//                        data[i][j] = kunde.getStadt();
//                    } else if (result == 14) {
//                        data[i][j] = kunde.getBundesland();
//                    } else if (result == 15) {
//                        data[i][j] = kunde.getLand();
//                    } else if (result == 16) {
//                        data[i][j] = kunde.getAdresseZusatz();
//                    } else if (result == 17) {
//                        if (kunde.getCommunication1() != null && kunde.getCommunication1().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication1());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication1Type()]);
//
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 18) {
//                        if (kunde.getCommunication2() != null && kunde.getCommunication2().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication2());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication2Type()]);
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 19) {
//                        if (kunde.getCommunication3() != null && kunde.getCommunication3().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication3());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication3Type()]);
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 20) {
//                        if (kunde.getCommunication4() != null && kunde.getCommunication4().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication4());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication4Type()]);
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 21) {
//                        if (kunde.getCommunication5() != null && kunde.getCommunication5().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication5());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication5Type()]);
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 22) {
//                        if (kunde.getCommunication6() != null && kunde.getCommunication6().length() > 0) {
//                            JLabel com = new JLabel();
//                            com.setText(kunde.getCommunication6());
//                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[kunde.getCommunication6Type()]);
//                            data[i][j] = com;
//                        } else {
//                            JLabel com = new JLabel();
//                            data[i][j] = com;
//                        }
//                    } else if (result == 23) {
//                        data[i][j] = "";
//                    } else if (result == 24) {
//                        data[i][j] = "";
//                    } else if (result == 25) {
//                        data[i][j] = "";
//                    } else if (result == 26) {
//                        data[i][j] = "";
//                    } else if (result == 27) {
//                        data[i][j] = "";
//                    } else if (result == 28) {
//                        data[i][j] = "";
//                    } else if (result == 29) {
//                        data[i][j] = kunde.getTyp(); // TODo
//                    } else if (result == 30) {
//                        data[i][j] = kunde.getFamilienStand();
//                    } else if (result == 31) {
//                        data[i][j] = kunde.getEhepartnerKennung(); // TODO
//                    } else if (result == 32) {
//                        data[i][j] = kunde.getGeburtsdatum();
//                    } else if (result == 33) {
//                        data[i][j] = kunde.getBeruf();
//                    } else if (result == 34) {
//                        data[i][j] = kunde.getBerufsTyp();
//                    } else if (result == 35) {
//                        data[i][j] = kunde.getBerufsOptionen();
//                    } else if (result == 36) {
//                        data[i][j] = kunde.isBeamter();
//                    } else if (result == 37) {
//                        data[i][j] = kunde.isOeffentlicherDienst();
//                    } else if (result == 38) {
//                        data[i][j] = kunde.getEinkommen();
//                    } else if (result == 39) {
//                        data[i][j] = kunde.getEinkommenNetto();
//                    } else if (result == 40) {
//                        data[i][j] = kunde.getSteuerklasse();
//                    } else if (result == 41) {
//                        data[i][j] = kunde.getKinderZahl();
//                    } else if (result == 42) {
//                        data[i][j] = kunde.getReligion();
//                    } else if (result == 43) {
//                        data[i][j] = kunde.getWeiterePersonen();
//                    } else if (result == 44) {
//                        data[i][j] = kunde.getWeiterePersonenInfo();
//                    } else if (result == 45) {
//                        data[i][j] = kunde.getFamilienPlanung();
//                    } else if (result == 46) {
//                        data[i][j] = KundenRegistry.getKunde(kunde.getWerberKennung());
//                    } else if (result == 47) {
//                        data[i][j] = "";
//                    } else if (result == 48) {
//                        data[i][j] = "";
//                    } else if (result == 49) {
//                        data[i][j] = "";
//                    } else if (result == 50) {
//                        data[i][j] = "";
//                    } else if (result == 51) {
//                        data[i][j] = "";
//                    } else if (result == 52) {
//                        data[i][j] = "";
//                    } else if (result == 53) {
//                        data[i][j] = kunde.getComments();
//                    } else if (result == 54) {
//                        data[i][j] = kunde.getCustom1();
//                    } else if (result == 55) {
//                        data[i][j] = kunde.getCustom2();
//                    } else if (result == 56) {
//                        data[i][j] = kunde.getCustom3();
//                    } else if (result == 57) {
//                        data[i][j] = kunde.getCustom4();
//                    } else if (result == 58) {
//                        data[i][j] = kunde.getCustom5();
//                    } else if (result == 59) {
//                        data[i][j] = kunde.getGeburtsname();
//                    } else if (result == 60) {
//                        data[i][j] = kunde.getEhedatum();
//                    } else if (result == 61) {
//                        data[i][j] = dftable.format(kunde.getCreated());
//                    } else if (result == 62) {
//                        data[i][j] = dftable.format(kunde.getModified());
//                    } else if (result == 63) {
//                        data[i][j] = kunde.getStatus();
//                    }
                }
            }

            datenCount = kunden.length;

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
        
//        long end = System.currentTimeMillis();
//        System.out.println("Zeit zum Kunden laden: " + (end - begin) + " milliseconds");

        setTable(data, selrow);
    }

    /**
     * 
     * @param kunde
     * @param result
     * @return 
     */
    public Object getKundeResult(KundenObj kunde, int result) {
        switch (result) {
            case 0:
                return kunde.getId();
            case 1:
                return BenutzerRegistry.getBenutzer(kunde.getBetreuerId());
            case 2:
                return BenutzerRegistry.getBenutzer(kunde.getCreatorId());
            case 3:
                return kunde.getKundenNr();
            case 4:
                return kunde.getAnrede();
            case 5:
                return kunde.getTitel();
            case 6:
                return kunde.getFirma();
            case 7:
                return kunde.getVorname();
            case 8:
                return kunde.getVorname2();
            case 9:
                return kunde.getVornameWeitere();
            case 10:
                return kunde.getNachname();
            case 11:
                return kunde.getStreet();
            case 12:
                return kunde.getPlz();
            case 13:
                return kunde.getStadt();
            case 14:
                return kunde.getBundesland();
            case 15:
                return kunde.getLand();
            case 16:
                return kunde.getAdresseZusatz();
            case 17:
                return CommunicationTypes.getCommunicationLabel(kunde, 1);
            case 18:
                return CommunicationTypes.getCommunicationLabel(kunde, 2);
            case 19:
                return CommunicationTypes.getCommunicationLabel(kunde, 3);
            case 20:
                return CommunicationTypes.getCommunicationLabel(kunde, 4);
            case 21:
                return CommunicationTypes.getCommunicationLabel(kunde, 5);
            case 22:
                return CommunicationTypes.getCommunicationLabel(kunde, 6);
            case 23:
                return "";
            case 24:
                return "";
            case 25:
                return "";
            case 26:
                return "";
            case 27:
                return "";
            case 28:
                return "";
            case 29:
                return kunde.getTyp(); // TODO
            case 30:
                return kunde.getFamilienStand();
            case 31:
                return kunde.getEhepartnerKennung(); // TODO
            case 32:
                return kunde.getGeburtsdatum();
            case 33:
                return kunde.getBeruf();
            case 34:
                return kunde.getBerufsTyp();
            case 35:
                return kunde.getBerufsOptionen();
            case 36:
                return kunde.isBeamter();
            case 37:
                return kunde.isOeffentlicherDienst();

            case 38:
                return kunde.getEinkommen();
            case 39:
                return kunde.getEinkommenNetto();
            case 40:
                return kunde.getSteuerklasse();
            case 41:
                return kunde.getKinderZahl();
            case 42:
                return kunde.getReligion();
            case 43:
                return kunde.getWeiterePersonen();
            case 44:
                return kunde.getWeiterePersonenInfo();
            case 45:
                return kunde.getFamilienPlanung();
            case 46:
                return KundenRegistry.getKunde(kunde.getWerberKennung());
            case 47:
                return "";

            case 48:
                return "";
            case 49:
                return "";
            case 50:
                return "";
            case 51:
                return "";
            case 52:
                return "";
            case 53:
                return kunde.getComments();
            case 54:
                return kunde.getCustom1();
            case 55:
                return kunde.getCustom2();
            case 56:
                return kunde.getCustom3();
            case 57:
                return kunde.getCustom4();
            case 58:
                return kunde.getCustom5();
            case 59:
                return kunde.getGeburtsname();
            case 60:
                return kunde.getEhedatum();
            case 61:
                return dftable.format(kunde.getCreated());
            case 62:
                return dftable.format(kunde.getModified());
            case 63:
                return Status.getName(kunde.getStatus());

            default:
                return "";
        }
    }
    // ColumnNames
    private ColumnHead[] columnNames = null;

    public void createColumnNames() {
        String columnHeadsIds = prefs.get("tableColumns", "3,4,7,10,11,12,13,17,20,21");

        String[] results = columnHeadsIds.split(",");
        activeItems = new Object[results.length];

        columnNames = new ColumnHead[results.length + 2];

        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = KundenUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < KundenUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                if (KundenUebersichtHeader.Columns[i] != null) {
                    al.add(new ColumnHead(KundenUebersichtHeader.Columns[i], i, false));
                }
            }
        }
        inactiveItems = al.toArray();
    }

    /**
     * Setting the table
     * @param data
     * @param selrow 
     */
    
    private void setTable(Object data[][], int selrow) {
//        long begin = System.currentTimeMillis();
        table_kunden.setModel(new KundenUebersichtModel(data, columnNames));
        table_kunden.removeColumn(table_kunden.getColumnModel().getColumn(1));

        TableColumn check = table_kunden.getColumnModel().getColumn(0);
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
        table_kunden.addMouseListener(popupListener);

        JTableHeader header = table_kunden.getTableHeader();
        MouseListener popupHeaderListener = new TableHeaderPopupListener();
        header.addMouseListener(popupHeaderListener);

        table_kunden.packAll();

        table_kunden.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_kunden.getColumnModel().getColumn(0).setMaxWidth(20);

//        long end = System.currentTimeMillis();
//        System.out.println("Zeit zum Tabelle laden und zeichnen " + (end - begin) + " milliseconds");

        if (datenCount == 0) {
            try {
                if (panelAdd.isLoaded() == false) {
                    panelAdd.add(this);
                }
                panelAdd.disElements();
                this.label_activekunde.setText("Kein Kunde ausgewählt");
            } catch (Exception e) {
                e.printStackTrace(); // Sollte nicht passieren
            }
        } else {
            if (selrow != -1) {
                showKunde(selkunde);
                table_kunden.requestFocusInWindow();
                table_kunden.changeSelection(selrow, 0, false, false);

                selkunde = null;
            } else {
                showKunde((KundenObj) data[0][1]);
                table_kunden.requestFocusInWindow();
                table_kunden.changeSelection(0, 0, false, false);
            }
        }
    }

    /**
     * Zeigt den ausgewählten Kunden an
     * @param kunde
     */
    private void showKunde(KundenObj kunde) {
//        long begin2 = System.currentTimeMillis();;
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Privatkunde: " + kunde.toString());
//            begin2 = System.currentTimeMillis();
        }


        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        }
        

//        long end2 = System.currentTimeMillis();
//        System.out.println("Zeit zum hinzufügen der Tabs: " + (end2 - begin2) + " milliseconds");
        
//        long begin = System.currentTimeMillis();;

        label_activekunde.setText("Aktiver Kunde: ".concat(kunde.toString()));
        panelAdd.load(kunde);

//        long end = System.currentTimeMillis();
//        System.out.println("Zeit zum Tabs laden: " + (end - begin) + " milliseconds");
    }

    /**
     * Export im XML Format
     */
    public void xmlExport() {
        KundenObj[] kunden = getSelectedKunden();

        if (kunden == null) {
//            System.out.println("Kunden ist null");
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.XML),
                ExportImportTypen.getTypeName(ExportImportTypen.XML));

        if (file == null || file.length() < 1) {
//            System.out.println("File is null");
            return;
        }

        PrivatkundenXMLExport pxml = new PrivatkundenXMLExport(file, kunden);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
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
                final int row = table_kunden.rowAtPoint(point);
                table_kunden.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {

                        if (row != -1) {
                            if (row != currow) {
                                currow = row;
                                KundenObj kunde = (KundenObj) table_kunden.getModel().getValueAt(row, 1);
                                showKunde(kunde);
                            }
                        }
                    }
                });
            }
        }
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(
                KundenUebersichtHeader.getColumnsWithFieldSearch()));
        combo_sucheFilter.setSelectedIndex(3);
        combo_sucheFilter.revalidate();
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

    private void loadCmd() {
        this.addNeuCommandButton();
        this.addBriefCommandButton();
        this.addExportCommandButton();
        this.addPrintCommandButton();
        addAnsichtButtons();
        addReportCommandButton();
    }

    /**
     * Print Command Button
     */
    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("Ausgewählte Kunden drucken");
        RichTooltip tooltip = new RichTooltip("Druck", "Druckt die ausgewählten Kudnden und Spalten.");
        printButton.setPopupRichTooltip(tooltip);

        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.kundenToolbar.add(printButton, 7);
    }

    /**
     *  Report Command Button
     */
    private void addReportCommandButton() {
        JCommandButton reportButton = new JCommandButton("Report",
                getResizableIconFromResource(ResourceStrings.REPORT_ICON));
        reportButton.setExtraText("Report erstellen");
        RichTooltip tooltip = new RichTooltip("Kundenreporting (Berichtswesen)", "Erstellen Sie professionelle Berichte zur "
                + "Ermittlung wichtiger Eckdaten Ihres Geschäftsbetriebes.");

        reportButton.setPopupRichTooltip(tooltip);

        reportButton.setPopupCallback(new ReportPopupCallback());
        reportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        reportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        reportButton.setFlat(true);

        this.kundenToolbar.add(reportButton, 12);
    }

    /**
     * 
     */
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

        boolean own = this.eigeneMenuItem.isSelected();

        try {
            KundenObj[] ergebnis = SearchKunden.quickSearch(DatabaseConnection.open(), keyword, own, getStatus());
            createTable(ergebnis);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Kunden nicht durchsuchen", e);
            ShowException.showException("Die Schnellsuche konnte nicht durchgeführt werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Schnellsuche nicht durchführen");
        }
    }

    /**
     * 
     */
    public void fieldSearch() {
        String keyword = this.fieldDetailsuche.getText().trim();
//        System.out.println("Keyword: " + keyword);  
        if (keyword == null) {
            return;
        }

        if (keyword.length() < 3) {

            if (keyword.length() == 0) {
                loadTable();
            }

            return;
        }

        KundenUebersichtHeader.ColumnsWithTablefield field = (ColumnsWithTablefield) this.combo_sucheFilter.getSelectedItem();

        boolean own = this.eigeneMenuItem.isSelected();

        try {
            KundenObj[] ergebnis = SearchKunden.searchKundenObject(DatabaseConnection.open(), field.getType(), keyword,
                    own, getStatus(), operator);
            createTable(ergebnis);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Detailssuche nicht durchführen", e);
            ShowException.showException("Die Detailsuche konnte nicht durchgeführt werden",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Detailsuche nicht durchführen");
        }
    }

    private void deleteSelectedKunden() {
        KundenObj[] kunden = getSelectedKunden();

        if (kunden == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Kunden wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            for (int i = 0; i < kunden.length; i++) {
                KundenSQLMethods.deleteFromkunden(DatabaseConnection.open(), kunden[i].getId());
            }
        } catch (SQLException e) {
            Log.databaselogger.warn("Fehler: Konnte Kunde nicht löschen", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht gelöscht werden. "
                    + "Unter Details finden Sie weitere Informationen zur Fehlermeldung",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht löschen");
        }

        loadTable();
    }

    private void archiveSelectedKunden() {
        KundenObj[] kunden = this.getSelectedKunden();

        if (kunden == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie die Kunden wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            for (int i = 0; i < kunden.length; i++) {
                KundenSQLMethods.archiveFromkunden(DatabaseConnection.open(), kunden[i].getId());
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Kunde nicht archivieren", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde(n) nicht archivieren");
        }

        loadTable();
    }

    /**
     * 
     * @param input
     * @return
     */
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showKundeKarte() {
        KundenObj kunde = getSelectedKunde();
        if (kunde == null) {
            return;
        }

        KarteSuche.doExteneralSearch(kunde.getStreet() + ", " + kunde.getStadt(), crm);
    }

    private void showKundeDokumente() {
        KundenObj kunde = this.getSelectedKunde();

        if (kunde == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        kundeDokumenteBox = new KundenDokumente(mainFrame, false, kunde);
        kundeDokumenteBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(kundeDokumenteBox);
    }

    public KundenObj getSelkunde() {
        return selkunde;
    }

    public void setSelkunde(KundenObj selkunde) {
        this.selkunde = selkunde;
    }

    private void editKunde() {
        final int row = table_kunden.getSelectedRow();

        if (row == -1) {
            return;
        }


        KundenObj kunde = (KundenObj) table_kunden.getModel().getValueAt(row, 1);
        selkunde = kunde;
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newKundeBox = new NewKundeDialog(mainFrame, true, kunde);
        newKundeBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        Log.logger.debug("KundenDialog geschlossen");
                        return null;
                    }
                });
            }
        });
        newKundeBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(newKundeBox);
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
        newKundeItem = new javax.swing.JMenuItem();
        editKundeItem = new javax.swing.JMenuItem();
        archiveKundeItem = new javax.swing.JMenuItem();
        deleteKundeItem = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        tableRefresh = new javax.swing.JMenuItem();
        tableEinstellungenItem = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        briefMenu = new javax.swing.JMenu();
        briefKundeMenu = new javax.swing.JMenu();
        AlgBriefMenuItem = new javax.swing.JMenuItem();
        TerminbestaetigungMenuItem = new javax.swing.JMenuItem();
        geburtstagsbriefMenuItem = new javax.swing.JMenuItem();
        MaklerauftragMenuItem = new javax.swing.JMenuItem();
        MaklereinzelAuftragMenuItem = new javax.swing.JMenuItem();
        briefBenutzerMenu = new javax.swing.JMenu();
        AlgBriefBenMenuItem = new javax.swing.JMenuItem();
        BriefVorlagenOrdnerMenuItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        emailMenu = new javax.swing.JMenu();
        mailKundeMenu = new javax.swing.JMenu();
        AlgEmailMenuItem = new javax.swing.JMenuItem();
        terminbestMenuItem = new javax.swing.JMenuItem();
        geburtstagsemailMenuItem = new javax.swing.JMenuItem();
        mailBenutzerMenu = new javax.swing.JMenu();
        AlgEmailBenMenuItem = new javax.swing.JMenuItem();
        EmailVorlagenMenuItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        exportMenu = new javax.swing.JMenu();
        datenblattMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        printMenu = new javax.swing.JMenu();
        datenblattMenuItem1 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        reportMenu = new javax.swing.JMenu();
        datenblattMenuItem2 = new javax.swing.JMenuItem();
        statistikMenuItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        karteMenuItem = new javax.swing.JMenuItem();
        empfohleneMenuItem = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        DokumenteMenuItem = new javax.swing.JMenuItem();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        newKundeItem1 = new javax.swing.JMenuItem();
        tableEinstellungenItem1 = new javax.swing.JMenuItem();
        ansichtPopup = new javax.swing.JPopupMenu();
        eigeneMenuItem = new javax.swing.JCheckBoxMenuItem();
        alleMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        alledbMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        deleteddbMenuItem = new javax.swing.JCheckBoxMenuItem();
        popupNewKunde = new javax.swing.JPopupMenu();
        radioSchaeden = new javax.swing.ButtonGroup();
        btnGrpKunden = new javax.swing.ButtonGroup();
        grp_kundenview = new javax.swing.ButtonGroup();
        grp_dbkunden = new javax.swing.ButtonGroup();
        kundenToolbar = new javax.swing.JToolBar();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDokumente = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnKarte = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnStatistik = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnTableSettings = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        toolbarKunden2 = new javax.swing.JToolBar();
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
        split_kunden = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_kunden = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activekunde = new javax.swing.JLabel();
        pane_kundendetails = new javax.swing.JTabbedPane();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelKundenUebersicht.class);
        tablePopup.setBackground(resourceMap.getColor("tablePopup.background")); // NOI18N
        tablePopup.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablePopup.setName("tablePopup"); // NOI18N

        newKundeItem.setIcon(resourceMap.getIcon("newKundeItem.icon")); // NOI18N
        newKundeItem.setMnemonic('N');
        newKundeItem.setText(resourceMap.getString("newKundeItem.text")); // NOI18N
        newKundeItem.setName("newKundeItem"); // NOI18N
        newKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(newKundeItem);

        editKundeItem.setIcon(resourceMap.getIcon("editKundeItem.icon")); // NOI18N
        editKundeItem.setMnemonic('b');
        editKundeItem.setText(resourceMap.getString("editKundeItem.text")); // NOI18N
        editKundeItem.setName("editKundeItem"); // NOI18N
        editKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(editKundeItem);

        archiveKundeItem.setIcon(resourceMap.getIcon("archiveKundeItem.icon")); // NOI18N
        archiveKundeItem.setMnemonic('a');
        archiveKundeItem.setText(resourceMap.getString("archiveKundeItem.text")); // NOI18N
        archiveKundeItem.setName("archiveKundeItem"); // NOI18N
        archiveKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(archiveKundeItem);

        deleteKundeItem.setIcon(resourceMap.getIcon("deleteKundeItem.icon")); // NOI18N
        deleteKundeItem.setMnemonic('l');
        deleteKundeItem.setText(resourceMap.getString("deleteKundeItem.text")); // NOI18N
        deleteKundeItem.setName("deleteKundeItem"); // NOI18N
        deleteKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(deleteKundeItem);

        jSeparator15.setName("jSeparator15"); // NOI18N
        tablePopup.add(jSeparator15);

        tableRefresh.setIcon(resourceMap.getIcon("tableRefresh.icon")); // NOI18N
        tableRefresh.setMnemonic('a');
        tableRefresh.setText(resourceMap.getString("tableRefresh.text")); // NOI18N
        tableRefresh.setName("tableRefresh"); // NOI18N
        tableRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableRefreshActionPerformed(evt);
            }
        });
        tablePopup.add(tableRefresh);

        tableEinstellungenItem.setIcon(resourceMap.getIcon("tableEinstellungenItem.icon")); // NOI18N
        tableEinstellungenItem.setText(resourceMap.getString("tableEinstellungenItem.text")); // NOI18N
        tableEinstellungenItem.setName("tableEinstellungenItem"); // NOI18N
        tableEinstellungenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableEinstellungenItemActionPerformed(evt);
            }
        });
        tablePopup.add(tableEinstellungenItem);

        jSeparator13.setName("jSeparator13"); // NOI18N
        tablePopup.add(jSeparator13);

        briefMenu.setIcon(resourceMap.getIcon("briefMenu.icon")); // NOI18N
        briefMenu.setText(resourceMap.getString("briefMenu.text")); // NOI18N
        briefMenu.setName("briefMenu"); // NOI18N

        briefKundeMenu.setText(resourceMap.getString("briefKundeMenu.text")); // NOI18N
        briefKundeMenu.setActionCommand(resourceMap.getString("briefKundeMenu.actionCommand")); // NOI18N
        briefKundeMenu.setName("briefKundeMenu"); // NOI18N

        AlgBriefMenuItem.setMnemonic('A');
        AlgBriefMenuItem.setText(resourceMap.getString("AlgBriefMenuItem.text")); // NOI18N
        AlgBriefMenuItem.setName("AlgBriefMenuItem"); // NOI18N
        AlgBriefMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlgBriefMenuItemActionPerformed(evt);
            }
        });
        briefKundeMenu.add(AlgBriefMenuItem);

        TerminbestaetigungMenuItem.setMnemonic('T');
        TerminbestaetigungMenuItem.setText(resourceMap.getString("TerminbestaetigungMenuItem.text")); // NOI18N
        TerminbestaetigungMenuItem.setName("TerminbestaetigungMenuItem"); // NOI18N
        TerminbestaetigungMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminbestaetigungMenuItemActionPerformed(evt);
            }
        });
        briefKundeMenu.add(TerminbestaetigungMenuItem);

        geburtstagsbriefMenuItem.setMnemonic('G');
        geburtstagsbriefMenuItem.setText(resourceMap.getString("geburtstagsbriefMenuItem.text")); // NOI18N
        geburtstagsbriefMenuItem.setName("geburtstagsbriefMenuItem"); // NOI18N
        geburtstagsbriefMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geburtstagsbriefMenuItemActionPerformed(evt);
            }
        });
        briefKundeMenu.add(geburtstagsbriefMenuItem);

        MaklerauftragMenuItem.setMnemonic('M');
        MaklerauftragMenuItem.setText(resourceMap.getString("MaklerauftragMenuItem.text")); // NOI18N
        MaklerauftragMenuItem.setName("MaklerauftragMenuItem"); // NOI18N
        MaklerauftragMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaklerauftragMenuItemActionPerformed(evt);
            }
        });
        briefKundeMenu.add(MaklerauftragMenuItem);

        MaklereinzelAuftragMenuItem.setMnemonic('M');
        MaklereinzelAuftragMenuItem.setText(resourceMap.getString("MaklereinzelAuftragMenuItem.text")); // NOI18N
        MaklereinzelAuftragMenuItem.setName("MaklereinzelAuftragMenuItem"); // NOI18N
        MaklereinzelAuftragMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaklereinzelAuftragMenuItemActionPerformed(evt);
            }
        });
        briefKundeMenu.add(MaklereinzelAuftragMenuItem);

        briefMenu.add(briefKundeMenu);

        briefBenutzerMenu.setText(resourceMap.getString("briefBenutzerMenu.text")); // NOI18N
        briefBenutzerMenu.setActionCommand(resourceMap.getString("briefBenutzerMenu.actionCommand")); // NOI18N
        briefBenutzerMenu.setName("briefBenutzerMenu"); // NOI18N

        AlgBriefBenMenuItem.setMnemonic('A');
        AlgBriefBenMenuItem.setText(resourceMap.getString("AlgBriefBenMenuItem.text")); // NOI18N
        AlgBriefBenMenuItem.setName("AlgBriefBenMenuItem"); // NOI18N
        briefBenutzerMenu.add(AlgBriefBenMenuItem);

        briefMenu.add(briefBenutzerMenu);

        BriefVorlagenOrdnerMenuItem.setMnemonic('V');
        BriefVorlagenOrdnerMenuItem.setText(resourceMap.getString("BriefVorlagenOrdnerMenuItem.text")); // NOI18N
        BriefVorlagenOrdnerMenuItem.setToolTipText(resourceMap.getString("BriefVorlagenOrdnerMenuItem.toolTipText")); // NOI18N
        BriefVorlagenOrdnerMenuItem.setName("BriefVorlagenOrdnerMenuItem"); // NOI18N
        BriefVorlagenOrdnerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefVorlagenOrdnerMenuItemActionPerformed(evt);
            }
        });
        briefMenu.add(BriefVorlagenOrdnerMenuItem);

        tablePopup.add(briefMenu);

        jSeparator16.setName("jSeparator16"); // NOI18N
        tablePopup.add(jSeparator16);

        emailMenu.setIcon(resourceMap.getIcon("emailMenu.icon")); // NOI18N
        emailMenu.setText(resourceMap.getString("emailMenu.text")); // NOI18N
        emailMenu.setName("emailMenu"); // NOI18N

        mailKundeMenu.setText(resourceMap.getString("mailKundeMenu.text")); // NOI18N
        mailKundeMenu.setActionCommand(resourceMap.getString("mailKundeMenu.actionCommand")); // NOI18N
        mailKundeMenu.setName("mailKundeMenu"); // NOI18N

        AlgEmailMenuItem.setMnemonic('A');
        AlgEmailMenuItem.setText(resourceMap.getString("AlgEmailMenuItem.text")); // NOI18N
        AlgEmailMenuItem.setName("AlgEmailMenuItem"); // NOI18N
        AlgEmailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlgEmailMenuItemActionPerformed(evt);
            }
        });
        mailKundeMenu.add(AlgEmailMenuItem);

        terminbestMenuItem.setMnemonic('T');
        terminbestMenuItem.setText(resourceMap.getString("terminbestMenuItem.text")); // NOI18N
        terminbestMenuItem.setName("terminbestMenuItem"); // NOI18N
        terminbestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminbestMenuItemActionPerformed(evt);
            }
        });
        mailKundeMenu.add(terminbestMenuItem);

        geburtstagsemailMenuItem.setMnemonic('G');
        geburtstagsemailMenuItem.setText(resourceMap.getString("geburtstagsemailMenuItem.text")); // NOI18N
        geburtstagsemailMenuItem.setName("geburtstagsemailMenuItem"); // NOI18N
        geburtstagsemailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geburtstagsemailMenuItemActionPerformed(evt);
            }
        });
        mailKundeMenu.add(geburtstagsemailMenuItem);

        emailMenu.add(mailKundeMenu);

        mailBenutzerMenu.setText(resourceMap.getString("mailBenutzerMenu.text")); // NOI18N
        mailBenutzerMenu.setName("mailBenutzerMenu"); // NOI18N

        AlgEmailBenMenuItem.setMnemonic('A');
        AlgEmailBenMenuItem.setText(resourceMap.getString("AlgEmailBenMenuItem.text")); // NOI18N
        AlgEmailBenMenuItem.setName("AlgEmailBenMenuItem"); // NOI18N
        mailBenutzerMenu.add(AlgEmailBenMenuItem);

        emailMenu.add(mailBenutzerMenu);

        EmailVorlagenMenuItem.setMnemonic('V');
        EmailVorlagenMenuItem.setText(resourceMap.getString("EmailVorlagenMenuItem.text")); // NOI18N
        EmailVorlagenMenuItem.setToolTipText(resourceMap.getString("EmailVorlagenMenuItem.toolTipText")); // NOI18N
        EmailVorlagenMenuItem.setName("EmailVorlagenMenuItem"); // NOI18N
        EmailVorlagenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailVorlagenMenuItemActionPerformed(evt);
            }
        });
        emailMenu.add(EmailVorlagenMenuItem);

        tablePopup.add(emailMenu);

        jSeparator17.setName("jSeparator17"); // NOI18N
        tablePopup.add(jSeparator17);

        exportMenu.setIcon(resourceMap.getIcon("exportMenu.icon")); // NOI18N
        exportMenu.setText(resourceMap.getString("exportMenu.text")); // NOI18N
        exportMenu.setName("exportMenu"); // NOI18N

        datenblattMenuItem.setIcon(resourceMap.getIcon("datenblattMenuItem.icon")); // NOI18N
        datenblattMenuItem.setMnemonic('K');
        datenblattMenuItem.setText(resourceMap.getString("datenblattMenuItem.text")); // NOI18N
        datenblattMenuItem.setName("datenblattMenuItem"); // NOI18N
        datenblattMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datenblattMenuItemActionPerformed(evt);
            }
        });
        exportMenu.add(datenblattMenuItem);

        tablePopup.add(exportMenu);

        jSeparator7.setName("jSeparator7"); // NOI18N
        tablePopup.add(jSeparator7);

        printMenu.setIcon(resourceMap.getIcon("printMenu.icon")); // NOI18N
        printMenu.setText(resourceMap.getString("printMenu.text")); // NOI18N
        printMenu.setName("printMenu"); // NOI18N

        datenblattMenuItem1.setMnemonic('K');
        datenblattMenuItem1.setText(resourceMap.getString("datenblattMenuItem1.text")); // NOI18N
        datenblattMenuItem1.setName("datenblattMenuItem1"); // NOI18N
        datenblattMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datenblattMenuItem1ActionPerformed(evt);
            }
        });
        printMenu.add(datenblattMenuItem1);

        tablePopup.add(printMenu);

        jSeparator8.setName("jSeparator8"); // NOI18N
        tablePopup.add(jSeparator8);

        reportMenu.setIcon(resourceMap.getIcon("reportMenu.icon")); // NOI18N
        reportMenu.setText(resourceMap.getString("reportMenu.text")); // NOI18N
        reportMenu.setName("reportMenu"); // NOI18N

        datenblattMenuItem2.setMnemonic('K');
        datenblattMenuItem2.setText(resourceMap.getString("datenblattMenuItem2.text")); // NOI18N
        datenblattMenuItem2.setName("datenblattMenuItem2"); // NOI18N
        datenblattMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datenblattMenuItem2ActionPerformed(evt);
            }
        });
        reportMenu.add(datenblattMenuItem2);

        tablePopup.add(reportMenu);

        statistikMenuItem.setIcon(resourceMap.getIcon("statistikMenuItem.icon")); // NOI18N
        statistikMenuItem.setMnemonic('a');
        statistikMenuItem.setText(resourceMap.getString("statistikMenuItem.text")); // NOI18N
        statistikMenuItem.setName("statistikMenuItem"); // NOI18N
        statistikMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statistikMenuItemActionPerformed(evt);
            }
        });
        tablePopup.add(statistikMenuItem);

        jSeparator9.setName("jSeparator9"); // NOI18N
        tablePopup.add(jSeparator9);

        karteMenuItem.setIcon(resourceMap.getIcon("karteMenuItem.icon")); // NOI18N
        karteMenuItem.setMnemonic('K');
        karteMenuItem.setText(resourceMap.getString("karteMenuItem.text")); // NOI18N
        karteMenuItem.setToolTipText(resourceMap.getString("karteMenuItem.toolTipText")); // NOI18N
        karteMenuItem.setName("karteMenuItem"); // NOI18N
        karteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karteMenuItemActionPerformed(evt);
            }
        });
        tablePopup.add(karteMenuItem);

        empfohleneMenuItem.setIcon(resourceMap.getIcon("empfohleneMenuItem.icon")); // NOI18N
        empfohleneMenuItem.setText(resourceMap.getString("empfohleneMenuItem.text")); // NOI18N
        empfohleneMenuItem.setToolTipText(resourceMap.getString("empfohleneMenuItem.toolTipText")); // NOI18N
        empfohleneMenuItem.setName("empfohleneMenuItem"); // NOI18N
        empfohleneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empfohleneMenuItemActionPerformed(evt);
            }
        });
        tablePopup.add(empfohleneMenuItem);

        jSeparator18.setName("jSeparator18"); // NOI18N
        tablePopup.add(jSeparator18);

        DokumenteMenuItem.setIcon(resourceMap.getIcon("DokumenteMenuItem.icon")); // NOI18N
        DokumenteMenuItem.setMnemonic('D');
        DokumenteMenuItem.setText(resourceMap.getString("DokumenteMenuItem.text")); // NOI18N
        DokumenteMenuItem.setToolTipText(resourceMap.getString("DokumenteMenuItem.toolTipText")); // NOI18N
        DokumenteMenuItem.setName("DokumenteMenuItem"); // NOI18N
        DokumenteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DokumenteMenuItemActionPerformed(evt);
            }
        });
        tablePopup.add(DokumenteMenuItem);

        tableHeaderPopup.setName("tableHeaderPopup"); // NOI18N

        newKundeItem1.setText(resourceMap.getString("newKundeItem1.text")); // NOI18N
        newKundeItem1.setName("newKundeItem1"); // NOI18N
        newKundeItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newKundeItem1ActionPerformed(evt);
            }
        });
        tableHeaderPopup.add(newKundeItem1);

        tableEinstellungenItem1.setText(resourceMap.getString("tableEinstellungenItem1.text")); // NOI18N
        tableEinstellungenItem1.setName("tableEinstellungenItem1"); // NOI18N
        tableEinstellungenItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableEinstellungenItem1ActionPerformed(evt);
            }
        });
        tableHeaderPopup.add(tableEinstellungenItem1);

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        grp_kundenview.add(eigeneMenuItem);
        eigeneMenuItem.setMnemonic('E');
        eigeneMenuItem.setSelected(true);
        eigeneMenuItem.setText(resourceMap.getString("eigeneMenuItem.text")); // NOI18N
        eigeneMenuItem.setName("eigeneMenuItem"); // NOI18N
        eigeneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eigeneMenuItemActionPerformed(evt);
            }
        });
        ansichtPopup.add(eigeneMenuItem);

        grp_kundenview.add(alleMenuItem);
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

        popupNewKunde.setName("popupNewKunde"); // NOI18N

        setName("Form"); // NOI18N

        kundenToolbar.setFloatable(false);
        kundenToolbar.setRollover(true);
        kundenToolbar.setName("kundenToolbar"); // NOI18N

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
        kundenToolbar.add(btnArchive);

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
        kundenToolbar.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        kundenToolbar.add(jSeparator2);

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
        kundenToolbar.add(btnDokumente);

        jSeparator4.setName("jSeparator4"); // NOI18N
        kundenToolbar.add(jSeparator4);

        jSeparator3.setName("jSeparator3"); // NOI18N
        kundenToolbar.add(jSeparator3);

        btnKarte.setIcon(resourceMap.getIcon("btnKarte.icon")); // NOI18N
        btnKarte.setText(resourceMap.getString("btnKarte.text")); // NOI18N
        btnKarte.setToolTipText(resourceMap.getString("btnKarte.toolTipText")); // NOI18N
        btnKarte.setFocusable(false);
        btnKarte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnKarte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKarte.setName("btnKarte"); // NOI18N
        btnKarte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKarteActionPerformed(evt);
            }
        });
        kundenToolbar.add(btnKarte);

        jSeparator5.setName("jSeparator5"); // NOI18N
        kundenToolbar.add(jSeparator5);

        btnStatistik.setIcon(resourceMap.getIcon("btnStatistik.icon")); // NOI18N
        btnStatistik.setText(resourceMap.getString("btnStatistik.text")); // NOI18N
        btnStatistik.setFocusable(false);
        btnStatistik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStatistik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnStatistik.setName("btnStatistik"); // NOI18N
        btnStatistik.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        kundenToolbar.add(btnStatistik);

        jSeparator6.setName("jSeparator6"); // NOI18N
        kundenToolbar.add(jSeparator6);

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
        kundenToolbar.add(btnTableSettings);

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
        kundenToolbar.add(btnRefresh);

        toolbarKunden2.setFloatable(false);
        toolbarKunden2.setRollover(true);
        toolbarKunden2.setName("toolbarKunden2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(93, 15));
        toolbarKunden2.add(jLabel1);

        fieldSchnellsuche.setText(resourceMap.getString("fieldSchnellsuche.text")); // NOI18N
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
        toolbarKunden2.add(fieldSchnellsuche);

        btnSchnellSuche.setIcon(resourceMap.getIcon("btnSchnellSuche.icon")); // NOI18N
        btnSchnellSuche.setMnemonic('S');
        btnSchnellSuche.setText(resourceMap.getString("btnSchnellSuche.text")); // NOI18N
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
        toolbarKunden2.add(btnSchnellSuche);

        jSeparator11.setName("jSeparator11"); // NOI18N
        jSeparator11.setPreferredSize(new java.awt.Dimension(10, 0));
        toolbarKunden2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(94, 16));
        jLabel2.setRequestFocusEnabled(false);
        toolbarKunden2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbarKunden2.add(jSeparator12);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setFocusable(false);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
        jLabel3.setVerifyInputWhenFocusTarget(false);
        toolbarKunden2.add(jLabel3);

        combo_sucheFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sucheFilter.setName("combo_sucheFilter"); // NOI18N
        toolbarKunden2.add(combo_sucheFilter);

        btnOperator.setFont(resourceMap.getFont("btnOperator.font")); // NOI18N
        btnOperator.setIcon(resourceMap.getIcon("btnOperator.icon")); // NOI18N
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
        toolbarKunden2.add(btnOperator);

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
        toolbarKunden2.add(fieldDetailsuche);

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
        toolbarKunden2.add(btnFieldsearch);

        split_kunden.setDividerLocation(300);
        split_kunden.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        split_kunden.setName("split_kunden"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_kunden.setModel(new KundenUebersichtModel(null, defaultColumns));
        table_kunden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kunden.setMinimumSize(new java.awt.Dimension(400, 150));
        table_kunden.setName("table_kunden"); // NOI18N
        table_kunden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kundenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_kunden);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(1294, 26));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1034, Short.MAX_VALUE)
                .addComponent(label_activekunde)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext)
                .addComponent(label_activekunde, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tableholderLayout = new javax.swing.GroupLayout(panel_tableholder);
        panel_tableholder.setLayout(panel_tableholderLayout);
        panel_tableholderLayout.setHorizontalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tableStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
        );
        panel_tableholderLayout.setVerticalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tableholderLayout.createSequentialGroup()
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panel_tableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        split_kunden.setTopComponent(panel_tableholder);

        pane_kundendetails.setName("pane_kundendetails"); // NOI18N
        split_kunden.setBottomComponent(pane_kundendetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kundenToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
            .addComponent(toolbarKunden2, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
            .addComponent(split_kunden, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kundenToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbarKunden2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(split_kunden, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableEinstellungenItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEinstellungenItemActionPerformed
        openTableSettings();
    }//GEN-LAST:event_tableEinstellungenItemActionPerformed

    private void tableEinstellungenItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEinstellungenItem1ActionPerformed
        openTableSettings();
    }//GEN-LAST:event_tableEinstellungenItem1ActionPerformed

    private void newKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newKundeItemActionPerformed
        createNewKunde();
    }//GEN-LAST:event_newKundeItemActionPerformed

    private void newKundeItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newKundeItem1ActionPerformed
        createNewKunde();
    }//GEN-LAST:event_newKundeItem1ActionPerformed

    private void editKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKundeItemActionPerformed
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.",
                    "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        editKunde();
    }//GEN-LAST:event_editKundeItemActionPerformed

    /**
     * 
     * @param evt
     */
    private void btnDokumenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokumenteActionPerformed
        showKundeDokumente();
    }//GEN-LAST:event_btnDokumenteActionPerformed

    private void deleteKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteKundeItemActionPerformed
        deleteSelectedKunden();
    }//GEN-LAST:event_deleteKundeItemActionPerformed

    private void btnSchnellSucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSchnellSucheActionPerformed
        this.quickSearch();
    }//GEN-LAST:event_btnSchnellSucheActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        showKundeKarte();
    }//GEN-LAST:event_btnKarteActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedKunden();
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnFieldsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFieldsearchActionPerformed
        fieldSearch();
    }//GEN-LAST:event_btnFieldsearchActionPerformed

    private void fieldDetailsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDetailsucheActionPerformed
        fieldSearch();
    }//GEN-LAST:event_fieldDetailsucheActionPerformed

    private void fieldSchnellsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSchnellsucheActionPerformed
        this.quickSearch();
    }//GEN-LAST:event_fieldSchnellsucheActionPerformed

    private void fieldDetailsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDetailsucheKeyReleased
        if (this.fieldDetailsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
    }//GEN-LAST:event_fieldDetailsucheKeyReleased

    private void fieldSchnellsucheKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldSchnellsucheKeyReleased
        if (this.fieldSchnellsuche.getText().length() == 0) {
            this.loadTable();
        }

        if (Config.getConfigBoolean("searchOntype", false)) {
            this.quickSearch();
        }
    }//GEN-LAST:event_fieldSchnellsucheKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedKunden();
    }//GEN-LAST:event_btnDeleteActionPerformed

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

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void table_kundenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kundenMouseClicked
        final int row = table_kunden.getSelectedRow();

        if (row == -1) {
            return;
        }

        if (row != currow) {
            currow = row;
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    KundenObj kunde = (KundenObj) table_kunden.getModel().getValueAt(row, 1);
                    showKunde(kunde);
                }
            });

//            System.out.println("Lade Kunde: " + kunde.getKundenNr());
        }

        if (evt.getClickCount() >= 2) {
            editKunde();
        }
    }//GEN-LAST:event_table_kundenMouseClicked

    private void tableRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableRefreshActionPerformed
        loadTable();
    }//GEN-LAST:event_tableRefreshActionPerformed

    private void AlgBriefMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlgBriefMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_AlgBriefMenuItemActionPerformed

    private void TerminbestaetigungMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminbestaetigungMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(4)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_TerminbestaetigungMenuItemActionPerformed

    private void geburtstagsbriefMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geburtstagsbriefMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(8)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_geburtstagsbriefMenuItemActionPerformed

    private void MaklerauftragMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaklerauftragMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(13)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_MaklerauftragMenuItemActionPerformed

    private void MaklereinzelAuftragMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaklereinzelAuftragMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(15)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_MaklereinzelAuftragMenuItemActionPerformed

    private void BriefVorlagenOrdnerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefVorlagenOrdnerMenuItemActionPerformed
        try {
            desktop.open(new File(Filesystem.getTemplatePath() + File.separatorChar + "word" + File.separatorChar));
        } catch (IOException ex) {
            Log.logger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
            ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Briefvorlagen im Verzeichnis \""
                    + Filesystem.getTemplatePath() + File.separatorChar + "word" + File.separatorChar + "\" .",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");

        }
    }//GEN-LAST:event_BriefVorlagenOrdnerMenuItemActionPerformed

    private void EmailVorlagenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailVorlagenMenuItemActionPerformed
        try {
            desktop.open(new File(Filesystem.getTemplatePath() + File.separatorChar + "email" + File.separatorChar));
        } catch (IOException ex) {
            Log.databaselogger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
            ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Emailvorlagen im Verzeichnis \""
                    + Filesystem.getTemplatePath() + File.separatorChar + "email" + File.separatorChar + "\" .",
                    ExceptionDialogGui.LEVEL_WARNING, ex,
                    "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");
        }
    }//GEN-LAST:event_EmailVorlagenMenuItemActionPerformed

    private void AlgEmailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlgEmailMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_AlgEmailMenuItemActionPerformed

    private void geburtstagsemailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geburtstagsemailMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(11)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_geburtstagsemailMenuItemActionPerformed

    private void terminbestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminbestMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(6)); // Hardcoded, ids sollten sich nicht ändern!
    }//GEN-LAST:event_terminbestMenuItemActionPerformed

    private void karteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karteMenuItemActionPerformed
        showKundeKarte();
    }//GEN-LAST:event_karteMenuItemActionPerformed

    private void DokumenteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DokumenteMenuItemActionPerformed
        showKundeDokumente();
    }//GEN-LAST:event_DokumenteMenuItemActionPerformed

    private void datenblattMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datenblattMenuItemActionPerformed
        exportKundenDatenblatt();
    }//GEN-LAST:event_datenblattMenuItemActionPerformed

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

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
    }//GEN-LAST:event_btnTableSettingsActionPerformed

    private void archiveKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveKundeItemActionPerformed
        archiveSelectedKunden();
    }//GEN-LAST:event_archiveKundeItemActionPerformed

    private void empfohleneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empfohleneMenuItemActionPerformed
        KundenObj knd = this.getSelectedKunde();

        if (knd == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        empfohlenekundenDialog = new EmpfohleneKundenDialog(mainFrame, false, knd.getKundenNr());
        empfohlenekundenDialog.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(empfohlenekundenDialog);
    }//GEN-LAST:event_empfohleneMenuItemActionPerformed

    private void datenblattMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datenblattMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datenblattMenuItem1ActionPerformed

    private void datenblattMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datenblattMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datenblattMenuItem2ActionPerformed

    private void statistikMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statistikMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statistikMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem AlgBriefBenMenuItem;
    public javax.swing.JMenuItem AlgBriefMenuItem;
    public javax.swing.JMenuItem AlgEmailBenMenuItem;
    public javax.swing.JMenuItem AlgEmailMenuItem;
    public javax.swing.JMenuItem BriefVorlagenOrdnerMenuItem;
    public javax.swing.JMenuItem DokumenteMenuItem;
    public javax.swing.JMenuItem EmailVorlagenMenuItem;
    public javax.swing.JMenuItem MaklerauftragMenuItem;
    public javax.swing.JMenuItem MaklereinzelAuftragMenuItem;
    public javax.swing.JMenuItem TerminbestaetigungMenuItem;
    public javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    public javax.swing.JCheckBoxMenuItem alleMenuItem;
    public javax.swing.JCheckBoxMenuItem alledbMenuItem;
    public javax.swing.JPopupMenu ansichtPopup;
    public javax.swing.JMenuItem archiveKundeItem;
    public javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    public javax.swing.JMenu briefBenutzerMenu;
    public javax.swing.JMenu briefKundeMenu;
    public javax.swing.JMenu briefMenu;
    public javax.swing.JButton btnArchive;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnDokumente;
    public javax.swing.JButton btnFieldsearch;
    public javax.swing.ButtonGroup btnGrpKunden;
    public javax.swing.JButton btnKarte;
    public javax.swing.JButton btnOperator;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JButton btnSchnellSuche;
    public javax.swing.JButton btnStatistik;
    public javax.swing.JButton btnTableSettings;
    public javax.swing.JComboBox combo_sucheFilter;
    public javax.swing.JMenuItem datenblattMenuItem;
    public javax.swing.JMenuItem datenblattMenuItem1;
    public javax.swing.JMenuItem datenblattMenuItem2;
    public javax.swing.JMenuItem deleteKundeItem;
    public javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    public javax.swing.JMenuItem editKundeItem;
    public javax.swing.JCheckBoxMenuItem eigeneMenuItem;
    public javax.swing.JMenu emailMenu;
    public javax.swing.JMenuItem empfohleneMenuItem;
    public javax.swing.JMenu exportMenu;
    public javax.swing.JTextField fieldDetailsuche;
    public javax.swing.JTextField fieldSchnellsuche;
    public javax.swing.JMenuItem geburtstagsbriefMenuItem;
    public javax.swing.JMenuItem geburtstagsemailMenuItem;
    public javax.swing.ButtonGroup grp_dbkunden;
    public javax.swing.ButtonGroup grp_kundenview;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JToolBar.Separator jSeparator11;
    public javax.swing.JToolBar.Separator jSeparator12;
    public javax.swing.JPopupMenu.Separator jSeparator13;
    public javax.swing.JPopupMenu.Separator jSeparator14;
    public javax.swing.JPopupMenu.Separator jSeparator15;
    public javax.swing.JPopupMenu.Separator jSeparator16;
    public javax.swing.JPopupMenu.Separator jSeparator17;
    public javax.swing.JPopupMenu.Separator jSeparator18;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator3;
    public javax.swing.JToolBar.Separator jSeparator4;
    public javax.swing.JToolBar.Separator jSeparator5;
    public javax.swing.JToolBar.Separator jSeparator6;
    public javax.swing.JPopupMenu.Separator jSeparator7;
    public javax.swing.JPopupMenu.Separator jSeparator8;
    public javax.swing.JPopupMenu.Separator jSeparator9;
    public javax.swing.JMenuItem karteMenuItem;
    public javax.swing.JToolBar kundenToolbar;
    public javax.swing.JLabel label_activekunde;
    public javax.swing.JLabel label_tablestatustext;
    public javax.swing.JMenu mailBenutzerMenu;
    public javax.swing.JMenu mailKundeMenu;
    public javax.swing.JMenuItem newKundeItem;
    public javax.swing.JMenuItem newKundeItem1;
    public javax.swing.JTabbedPane pane_kundendetails;
    public javax.swing.JPanel panel_tableStatus;
    public javax.swing.JPanel panel_tableholder;
    public javax.swing.JPopupMenu popupNewKunde;
    public javax.swing.JMenu printMenu;
    public javax.swing.ButtonGroup radioSchaeden;
    public javax.swing.JMenu reportMenu;
    public javax.swing.JScrollPane scroll_protokolle;
    public javax.swing.JSplitPane split_kunden;
    public javax.swing.JMenuItem statistikMenuItem;
    public javax.swing.JMenuItem tableEinstellungenItem;
    public javax.swing.JMenuItem tableEinstellungenItem1;
    public javax.swing.JPopupMenu tableHeaderPopup;
    public javax.swing.JPopupMenu tablePopup;
    public javax.swing.JMenuItem tableRefresh;
    public org.jdesktop.swingx.JXTable table_kunden;
    public javax.swing.JMenuItem terminbestMenuItem;
    public javax.swing.JToolBar toolbarKunden2;
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