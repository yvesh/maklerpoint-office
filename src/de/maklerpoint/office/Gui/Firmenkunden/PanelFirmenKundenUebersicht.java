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
 * PanelFirmenKundenUebersicht.java
 *
 * Created on 17.08.2010, 15:04:45
 */
package de.maklerpoint.office.Gui.Firmenkunden;

import com.google.gdata.util.AuthenticationException;
import de.maklerpoint.office.Bank.Tools.BankKontoSQLMethods;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeHelper;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Briefe.BriefDialog;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Email.SendEmailDialog;
import de.maklerpoint.office.Gui.Dokumente.KundenDokumente;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Export.ExportDialog;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Gui.Kunden.KundenTabelleSorter;
import de.maklerpoint.office.Gui.Kunden.NewKundeDialog;
import de.maklerpoint.office.Gui.Print.PrintTypen;
import de.maklerpoint.office.Gui.Tools.KundenAdresseAuswahlHelper;
import de.maklerpoint.office.Gui.Tools.TableValueChooseDialog;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Kunden.Tools.FirmenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.Tools.SearchFirmen;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.ToolsRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Google.GoogleContacts;
import de.maklerpoint.office.Schnittstellen.Word.ExportBrief;
import de.maklerpoint.office.Schnittstellen.Word.ExportFirmenDatenblatt;
import de.maklerpoint.office.Schnittstellen.Word.ExportFirmenInfoblatt;
import de.maklerpoint.office.Schnittstellen.XML.FirmenkundenXMLExport;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.FirmenKundenModel;
import de.maklerpoint.office.Table.FirmenKundenUebersichtHeader;
import de.maklerpoint.office.Table.FirmenKundenUebersichtHeader.ColumnsWithTablefield;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.ArrayStringTools;
import de.maklerpoint.office.Tools.BooleanTools;
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
public class PanelFirmenKundenUebersicht extends javax.swing.JPanel {

    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean firstLoad = true;
    private int datenCount = 0;
    private JDialog tableSettingsBox;
    private JDialog operatorBox;
    private JDialog fkundenDokumenteBox;
    private JDialog exportBox;
    private JDialog mailDialog;
    private JDialog newPrivatKundeBox;
    private JDialog newFirmenKundeBox;
    private Desktop desktop = Desktop.getDesktop();
    private CRMView crm;
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleDateFormat dfgeb = new SimpleDateFormat("dd.MM.yyyy");
    private Preferences prefs = Preferences.userRoot().node(PanelFirmenKundenUebersicht.class.getName());
    private AddFirmenPanels panelAdd = new AddFirmenPanels();
    private FirmenObj selkunde = null;
    private int operator = TableValueChooseDialog.ENTHAELT;
    private int currow = -1;

    /** Creates new form PanelFirmenKundenUebersicht */
    public PanelFirmenKundenUebersicht(CRMView crm) {
        initComponents();
        this.crm = crm;
        initialize();
    }

    /**
     * 
     * @param crm
     * @param firma
     */
    public PanelFirmenKundenUebersicht(CRMView crm, FirmenObj firma) {
        this.selkunde = firma;
        initComponents();
        this.crm = crm;
        initialize();

    }

    private void initialize() {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
        addCommandButtons();
        loadTableFieldSearch();
        loadTable();
//            }
//        });
    }

    private void addCommandButtons() {
        this.addNeuCommandButton();
        this.addExportCommandButton();
        this.addPrintCommandButton();
        this.addBriefCommandButton();
        this.addAnsichtButtons();
    }

    public void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), ansichtPopup);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Kundenansicht");
        //dropDownButton.setText();
        this.kundenToolbar.add(dropDownButton);
    }

    public void loadTableFieldSearch() {
        this.combo_sucheFilter.setModel(new DefaultComboBoxModel(FirmenKundenUebersichtHeader.getColumnsWithField()));
        combo_sucheFilter.setSelectedIndex(6);
        combo_sucheFilter.revalidate();
    }

    private void newKunde() {
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

    public void openTableSettings() {
        if (tableSettingsBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tableSettingsBox = new KundenTabelleSorter(mainFrame, false,
                    activeItems, inactiveItems, this);
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
        FirmenObj[] firmen = null;

        if (eigeneMenuItem.isSelected()) {
            firmen = KundenRegistry.getFirmenKunden(true, getStatus());
        } else if (alleMenuItem.isSelected()) {
            firmen = KundenRegistry.getFirmenKunden(false, getStatus());
        } else {
            // FALLBACK
            firmen = KundenRegistry.getFirmenKunden(true, getStatus());
        }

        createTable(firmen);
    }

    public void createTable(FirmenObj[] firmen) {
//        long begin = System.currentTimeMillis();
        Object[][] data = null;
        Object[] columnNames = null;

        int selrow = -1;

        String columnHeadsIds = prefs.get("tableColumns", "6,5,2,9,10,11,27,28"); // TODO überarbeiten

        String[] results = columnHeadsIds.split(",");
        activeItems = new Object[results.length];

        columnNames = new Object[results.length + 2];

        columnNames[0] = new ColumnHead("Auswahl", -1, true);
        columnNames[1] = new ColumnHead("Hidden", -2, true);

        for (int i = 2; i <= results.length + 1; i++) {
            String columnName = FirmenKundenUebersichtHeader.Columns[Integer.valueOf(results[i - 2].trim())];
            columnNames[i] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
            activeItems[i - 2] = new ColumnHead(columnName, Integer.valueOf(results[i - 2].trim()), true);
        }

        ArrayList<ColumnHead> al = new ArrayList<ColumnHead>();

        int ok = 1;

        for (int i = 0; i < FirmenKundenUebersichtHeader.Columns.length; i++) {
            ok = 0;
            for (int j = 0; j < results.length; j++) {
                if (Integer.valueOf(results[j]) == i) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                al.add(new ColumnHead(FirmenKundenUebersichtHeader.Columns[i], i, false));
            }
        }
        inactiveItems = al.toArray();

        if (firmen != null) {
            data = new Object[firmen.length][results.length + 3];

            for (int i = 0; i < firmen.length; i++) {
                FirmenObj firma = firmen[i];

                if (selkunde != null) {
                    //System.out.println("Not null!");
                    if (firma.getId() == selkunde.getId()) {
                        selrow = i;
                    }
                }

                data[i][0] = false;
                data[i][1] = firma;

                for (int j = 2; j <= results.length + 1; j++) {
                    //                System.out.println("i: " + i +"| j: " + j);                                        

                    int result = Integer.valueOf(results[j - 2]);

//                    System.out.println("Result: " + result + " (j = " + j + ")");

                    if (result == 0) {
                        data[i][j] = firma.getId();
                    } else if (result == 1) {
                        data[i][j] = BenutzerRegistry.getBenutzer(firma.getCreator()).toString();
                    } else if (result == 2) {
                        if (firma.getParentFirma() != -1) {
                            data[i][j] = KundenRegistry.getFirmenKunde(firma.getParentFirma(), false).toString();
                        } else {
                            data[i][j] = "Keine";
                        }
                    } else if (result == 3) {
                        data[i][j] = BenutzerRegistry.getBenutzer(firma.getBetreuer(), false).toString();
                    } else if (result == 4) {
                        data[i][j] = firma.getType();
                    } else if (result == 5) {
                        data[i][j] = firma.getKundenNr();
                    } else if (result == 6) {
                        data[i][j] = firma.getFirmenName();
                    } else if (result == 7) {
                        data[i][j] = firma.getFirmenNameZusatz();
                    } else if (result == 8) {
                        data[i][j] = firma.getFirmenNameZusatz2();
                    } else if (result == 9) {
                        data[i][j] = firma.getFirmenStrasse();
                    } else if (result == 10) {
                        data[i][j] = firma.getFirmenPLZ();
                    } else if (result == 11) {
                        data[i][j] = firma.getFirmenStadt();
                    } else if (result == 12) {
                        data[i][j] = firma.getFirmenBundesland();
                    } else if (result == 13) {
                        data[i][j] = firma.getFirmenLand();
                    } else if (result == 14) {
                        data[i][j] = firma.getFirmenTyp();
                    } else if (result == 15) {
                        data[i][j] = firma.getFirmenSize();
                    } else if (result == 16) {
                        data[i][j] = BooleanTools.getBooleanJaNein(firma.isFirmenPostfach());
                    } else if (result == 17) {
                        data[i][j] = firma.getFirmenPostfachName();
                    } else if (result == 18) {
                        data[i][j] = firma.getFirmenPostfachPlz();
                    } else if (result == 19) {
                        data[i][j] = firma.getFirmenPostfachOrt();
                    } else if (result == 20) {
                        data[i][j] = firma.getFirmenRechtsform();
                    } else if (result == 21) {
                        data[i][j] = firma.getFirmenEinkommen();
                    } else if (result == 22) {
                        data[i][j] = firma.getFirmenBranche();
                    } else if (result == 23) {
                        data[i][j] = dfgeb.format(firma.getFirmenGruendungDatum());
                    } else if (result == 24) {
                        data[i][j] = firma.getFirmenGeschaeftsfuehrer();
                    } else if (result == 25) {
                        data[i][j] = ArrayStringTools.arrayToString(firma.getFirmenProKura(), ", ");
                    } else if (result == 26) {
                        data[i][j] = ArrayStringTools.arrayToString(firma.getFirmenStandorte(), ", ");
                    } else if (result == 27) {
                        if (firma.getCommunication1() != null && firma.getCommunication1().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication1());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication1Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 28) {
                        if (firma.getCommunication2() != null && firma.getCommunication2().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication2());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication2Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 29) {
                        if (firma.getCommunication3() != null && firma.getCommunication3().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication3());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication3Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 30) {
                        if (firma.getCommunication4() != null && firma.getCommunication4().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication4());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication4Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 31) {
                        if (firma.getCommunication5() != null && firma.getCommunication5().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication5());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication5Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 32) {
                        if (firma.getCommunication6() != null && firma.getCommunication6().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(firma.getCommunication6());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[firma.getCommunication6Type()]);

                            data[i][j] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[i][j] = com;
                        }
                    } else if (result == 33) {
                        data[i][j] = "";
                    } else if (result == 34) {
                        data[i][j] = "";
                    } else if (result == 35) {
                        data[i][j] = "";
                    } else if (result == 36) {
                        data[i][j] = "";
                    } else if (result == 37) {
                        data[i][j] = "";
                    } else if (result == 38) {
                        data[i][j] = "";
                    } else if (result == 39) {
                        if (firma.getDefaultKonto() == -1) {
                            data[i][j] = "Keines";
                        } else {
                            try {
                                data[i][j] = BankKontoSQLMethods.getKonto(DatabaseConnection.open(), firma.getDefaultKonto());
                            } catch (SQLException ex) {
                                Exceptions.printStackTrace(ex);
                                data[i][j] = "";
                            }
                        }
                    } else if (result == 40) {
                        if (firma.getDefaultAnsprechpartner() == -1) {
                            data[i][j] = "Niemand";
                        } else {
                            try {
                                data[i][j] = FirmenAnsprechpartnerSQLMethods.getAnsprechpartner(DatabaseConnection.open(),
                                        firma.getDefaultAnsprechpartner());
                            } catch (SQLException ex) {
                                Exceptions.printStackTrace(ex);
                                data[i][j] = "";
                            }
                        }
                    } else if (result == 41) {
                        data[i][j] = "";
                    } else if (result == 42) {
                        data[i][j] = "";
                    } else if (result == 43) {
                        data[i][j] = "";
                    } else if (result == 44) {
                        data[i][j] = "";
                    } else if (result == 45) {
                        if (!firma.getWerber().equalsIgnoreCase("Unbekannt")) {
                            data[i][j] = KundenRegistry.getKunde(firma.getWerber());
                        } else {
                            data[i][j] = "Unbekannt";
                        }
                    } else if (result == 46) {
                        data[i][j] = firma.getComments();
                    } else if (result == 47) {
                        data[i][j] = firma.getCustom1();
                    } else if (result == 48) {
                        data[i][j] = firma.getCustom2();
                    } else if (result == 49) {
                        data[i][j] = firma.getCustom3();
                    } else if (result == 50) {
                        data[i][j] = firma.getCustom4();
                    } else if (result == 51) {
                        data[i][j] = firma.getCustom5();
                    } else if (result == 52) {
                        data[i][j] = dftable.format(firma.getCreated());
                    } else if (result == 53) {
                        data[i][j] = dftable.format(firma.getModified());
                    } else if (result == 54) {
                        data[i][j] = Status.getName(firma.getStatus());
                    }
                }
            }

            datenCount = firmen.length;

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

        TableModel model = new FirmenKundenModel(data, columnNames);

        table_kunden.setModel(model);
        table_kunden.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kunden.setColumnSelectionAllowed(false);
        table_kunden.setCellSelectionEnabled(false);
        table_kunden.setRowSelectionAllowed(true);
        table_kunden.setAutoCreateRowSorter(true);

//        table.getSelectionModel().addListSelectionListener(new RowListener());
        table_kunden.setFillsViewportHeight(true);
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

        for (int i = 3; i <= results.length + 1; i++) {
//            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(columnNames[i - 1].toString(), true);  // TODO
////            System.out.println("C: " + columnNames[i-1].toString());
//            tableHeaderPopup.add(menuItem);
        }


        JTableHeader header = table_kunden.getTableHeader();
        MouseListener popupHeaderListener = new TableHeaderPopupListener();
        header.addMouseListener(popupHeaderListener);

        table_kunden.packAll();

        table_kunden.tableChanged(new TableModelEvent(table_kunden.getModel()));
        table_kunden.revalidate();

        table_kunden.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_kunden.getColumnModel().getColumn(0).setMaxWidth(20);

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
            if (firstLoad && selkunde != null) {
                firstLoad = false;
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Zeile des ausgewählten Kunden in der Tabelle: " + selrow);
                }

                if (selrow != -1) {
                    showFirma(selkunde);
                    table_kunden.requestFocusInWindow();
                    table_kunden.changeSelection(selrow, 0, false, false);
                } else {
                    showFirma(firmen[0]);
                    table_kunden.requestFocusInWindow();
                    table_kunden.changeSelection(0, 0, false, false);
                }
                selkunde = null;
            } else if (selkunde != null) {
                if (selrow != -1) {
                    showFirma(selkunde);
                    table_kunden.requestFocusInWindow();
                    table_kunden.changeSelection(selrow, 0, false, false);
                } else {
                    showFirma(firmen[0]);
                    table_kunden.requestFocusInWindow();
                    table_kunden.changeSelection(0, 0, false, false);
                }
                selkunde = null;
            } else {
                //System.out.println("Row: " + selrow);
                showFirma(firmen[0]);
                table_kunden.requestFocusInWindow();
                table_kunden.changeSelection(0, 0, false, false);
            }
        }

//        long end = System.currentTimeMillis();
//        System.out.println("Total run time of " + (end - begin) + " milliseconds");
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

    private void showFirma(FirmenObj firma) {
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Lade Geschäftskunde: " + firma.getKundenNr());
        }

        if (panelAdd.isLoaded() == false) {
            try {
                panelAdd.add(this);
            } catch (Exception e) {
                e.printStackTrace(); // SOllte nicht passieren
            }
        }

        this.label_activekunde.setText("Aktiver Kunde: " + firma.getFirmenName() + " [" + firma.getKundenNr() + "]");
        panelAdd.load(firma);
    }

    public FirmenObj[] getSelectedFirmenKunden() {
        ArrayList<FirmenObj> kundenList = new ArrayList<FirmenObj>();

        for (int i = 0; i < table_kunden.getRowCount(); i++) {
            Boolean sel = (Boolean) table_kunden.getModel().getValueAt(i, 0);
            if (sel) {
                kundenList.add((FirmenObj) table_kunden.getModel().getValueAt(i, 1));
            }
        }

        if (kundenList.isEmpty()) {
            FirmenObj fk = getSelectedFirmenKunde();
            if (fk != null) {
                kundenList.add(fk);
            } else {
//                JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestenes einen Kunden aus.",
//                        "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        }

        FirmenObj[] kunden = new FirmenObj[kundenList.size()];

        kunden = kundenList.toArray(kunden);

        return kunden;
    }

    /* Needs work */
    public FirmenObj getSelectedFirmenKunde() {
        int rowcount = table_kunden.getSelectedRowCount();

        if (rowcount == 0 || rowcount > 1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.",
                    "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        int row = table_kunden.getSelectedRow();
        FirmenObj fk = (FirmenObj) table_kunden.getModel().getValueAt(row, 1);

        return fk;
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

        boolean own = eigeneMenuItem.isSelected();

        try {
            FirmenObj[] res = SearchFirmen.quickSearch(DatabaseConnection.open(), keyword, own, getStatus());
            this.createTable(res);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Firmen nicht durchsuchen", e);
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

            if (keyword.length() == 0) {
                loadTable();
            }

            return;
        }

        FirmenKundenUebersichtHeader.ColumnsWithTablefield field = (ColumnsWithTablefield) this.combo_sucheFilter.getSelectedItem();

        boolean own = eigeneMenuItem.isSelected();

        try {
            FirmenObj[] res = SearchFirmen.searchFirmenObject(DatabaseConnection.open(), field.getType(), keyword,
                    own, getStatus(), operator);
            this.createTable(res);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Detailsuche für Firmen nicht durchführen", e);
            ShowException.showException("Die Detailsuche konnte nicht durchgeführt werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Detailsuche nicht durchführen");
        }
    }

    private class NeuPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();

            JCommandMenuButton firma = new JCommandMenuButton("Firmen Kunde",
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

            JCommandMenuButton privat = new JCommandMenuButton("Privat Kunde",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/icon_clean/user-white.png"));
            privat.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    newPrivatKundeBox = new NewKundeDialog(mainFrame, false);
                    newPrivatKundeBox.addWindowListener(new WindowAdapter() {

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
                    newPrivatKundeBox.setLocationRelativeTo(mainFrame);

                    CRM.getApplication().show(newPrivatKundeBox);
                }
            });

            popupMenu.addMenuButton(privat);



            return popupMenu;
        }
    }

    public void addNeuCommandButton() {

        JCommandButton neuButton = new JCommandButton("Neu", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/add.png"));
        neuButton.setExtraText("Neuer Geschäfts- oder Privatkunde");
        neuButton.setPopupCallback(new NeuPopupCallback());
        neuButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
        neuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        neuButton.setFlat(true);


        neuButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });

        this.kundenToolbar.add(neuButton, 0);
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

            JCommandMenuButton xml = new JCommandMenuButton("als XML Datei",
                    getResizableIconFromResource(ResourceStrings.XML_ICON));
            xml.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    xmlExport();
                }
            });

            popupMenu.addMenuButton(xml);

            popupMenu.addMenuButton(pdf);

            JCommandMenuButton txt = new JCommandMenuButton("als Text Datei (.txt)",
                    getResizableIconFromResource(ResourceStrings.TEXT_ICON));
            txt.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(ExportImportTypen.TXT);
                }
            });

            popupMenu.addMenuButton(txt);

            JCommandMenuButton doc = new JCommandMenuButton("Kundendaten (Word)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            doc.setExtraText("Die kompletten zum Kunden vorhandenen Daten (auch Verträge, Konten, Zusatzadressen etc.) - lang.");
            doc.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportKundenDatenblatt();
                }
            });

            popupMenu.addMenuButton(doc);

            JCommandMenuButton kundeninfo = new JCommandMenuButton("Kundeninfo (Word)",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            kundeninfo.setExtraText("Einseitiges Informationsdatenblatt zum Kunden mit den wichtigsten Daten. (anpassbar)");
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

    public void openExportDialog(int type) {
        FirmenObj[] fa = getSelectedFirmenKunden();

        if (fa == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exportBox = new ExportDialog(mainFrame, false, type, fa,
                activeItems, inactiveItems);
        exportBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(exportBox);
    }

    private void exportGoogle() {
        FirmenObj[] kndn = getSelectedFirmenKunden();

        if (kndn == null) {
            return;
        }
        try {
            GoogleContacts.transmitKunden(kndn);
        } catch (MalformedURLException e) {
            Log.logger.fatal("Die Feedadresse des Google Kontakt Services stimmt nicht.", e);
            ShowException.showException("Die Anmeldung mit Ihrem Google Account ist fehlgeschlagen "
                    + "(Falscher Benutzername / Password). "
                    + "Bitte überprüfen Sie die Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Google Anmeldung nicht durchführen");
        } catch (AuthenticationException e) {
            Log.logger.fatal("Die Authorisierung mit den Google Servern ist fehlgeschlagen.", e);
            ShowException.showException("Die Anmeldung mit Ihrem Google Account ist fehlgeschlagen "
                    + "(Falscher Benutzername / Password). "
                    + "Bitte überprüfen Sie die Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Google Anmeldung nicht durchführen");
        }
    }

    public void xmlExport() {
        FirmenObj[] kunden = getSelectedFirmenKunden();

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

        FirmenkundenXMLExport pxml = new FirmenkundenXMLExport(file, kunden);
        try {
            pxml.write();
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }

    public void exportKundenDatenblatt() {
        FirmenObj fa = getSelectedFirmenKunde();
        if (fa == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null) {
            return;
        }

        try {
            ExportFirmenDatenblatt ed = new ExportFirmenDatenblatt(file, fa);
            ed.write();
            File createdFile = new File(file);
            desktop.open(createdFile);

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte Kunde nicht als Word Datei nicht exportieren", e);
            ShowException.showException("Das Kundendatenblatt konnte nicht als Worddatei (doc) exportiert werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");

        }
    }
    
    public void exportKundenInfoblatt() {
        FirmenObj fa = getSelectedFirmenKunde();
        if (fa == null) {
            return;
        }

        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.DOC),
                ExportImportTypen.getTypeName(ExportImportTypen.DOC));

        if (file == null) {
            return;
        }

        try {
            ExportFirmenInfoblatt ed = new ExportFirmenInfoblatt(file, fa);
            ed.write();
            File createdFile = new File(file);
            desktop.open(createdFile);

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte exportKundenInfoblatt nicht als Word Datei nicht exportieren", e);
            ShowException.showException("Das Kundeninformationsblatt konnte nicht als Worddatei (doc) exportiert werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");

        }
    }

    /**
     *
     */
    public void addExportCommandButton() {

        JCommandButton exportButton = new JCommandButton("Export",
                getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Kunde(n) exportieren");
        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.kundenToolbar.add(exportButton, 6);
    }

    private class PrintPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton uebersicht = new JCommandMenuButton("Kundenübersicht",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            uebersicht.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openExportDialog(PrintTypen.FIRMENKUNDENUEBERSICHT);
                }
            });

            popupMenu.addMenuButton(uebersicht);

            JCommandMenuButton datenblatt = new JCommandMenuButton("Kundendatenblatt",
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

    public void addPrintCommandButton() {
        JCommandButton printButton = new JCommandButton("Drucken", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/printer.png"));
        printButton.setExtraText("");
        printButton.setPopupCallback(new PrintPopupCallback());
        printButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        printButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        printButton.setFlat(true);

        this.kundenToolbar.add(printButton, 7);
    }

    public void addBriefCommandButton() {

        JCommandButton briefButton = new JCommandButton("Brief / E-Mail", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/emailButton.png"));
        briefButton.setExtraText("Brief / Fax / E-Mail an den Kunden versenden");
        briefButton.setPopupCallback(new BriefPopupCallback());
        briefButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        briefButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        briefButton.setFlat(true);

        this.kundenToolbar.add(briefButton, 6);
    }

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

            JCommandMenuButton algfax = new JCommandMenuButton("Allgem. Fax",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            algfax.setExtraText("Ein allgemeines Fax an den ausgewählten Kunden.");

            algfax.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(2)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algfax);

            JCommandMenuButton algemail = new JCommandMenuButton("Allgem. E-Mail",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            algemail.setExtraText("Eine allgemeines E-Mail an den ausgewählten Kunden.");
            algemail.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(algemail);

            JCommandMenuButton terminbest = new JCommandMenuButton("Terminbestätigung",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            terminbest.setExtraText("Eine Terminbestätigung (Brief) an den ausgewählten Kunden.");
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


            JCommandMenuButton gebbrief = new JCommandMenuButton("Jubiläumsbrief",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            gebbrief.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(10)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(gebbrief);

            JCommandMenuButton makauftrag = new JCommandMenuButton("Maklervertrag",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            makauftrag.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(14)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(makauftrag);

            JCommandMenuButton makeinzel = new JCommandMenuButton("Maklereinzelauftrag",
                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
            makeinzel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    schreibeBrief(ToolsRegistry.getBrief(16)); // Hardcoded, ids sollten sich nicht ändern!
                }
            });

            popupMenu.addMenuButton(makeinzel);


            JCommandMenuButton mehr = new JCommandMenuButton("Mehr ..",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/box-kontakt.jpg"));
            mehr.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    BriefObj brief = BriefeHelper.openBriefDialog(BriefDialog.GESCH);
                    schreibeBrief(brief);
                }
            });

            popupMenu.addMenuButton(mehr);

            return popupMenu;
        }
    }

    private void showDokumente() {
        FirmenObj fk = this.getSelectedFirmenKunde();

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        fkundenDokumenteBox = new KundenDokumente(mainFrame, false, fk);
        fkundenDokumenteBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(fkundenDokumenteBox);
    }

    private void schreibeBrief(BriefObj brief) {
        if (brief == null) {
            return;
        }

        FirmenObj kunde = getSelectedFirmenKunde();

        if (kunde == null) {
            return;
        }

        ZusatzadressenObj za = null;

        // TODO add Ansprechpartner oder CustimizeDialog für Kombi mit Vertrag und Co.

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
                        brief.getName(), KundenMailHelper.getGeschKundenMail(kunde), kunde);
                mailDialog.setLocationRelativeTo(mainFrame);

                CRM.getApplication().show(mailDialog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    private void archiveSelectedKunde() {
        FirmenObj[] fk = this.getSelectedFirmenKunden();

        if (fk == null) {
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
            for (int i = 0; i < fk.length; i++) {
                FirmenSQLMethods.archiveFromfirmenkunden(DatabaseConnection.open(), fk[i].getId());
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Firmenkunde nicht archivieren", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht archiviert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde(n) nicht archivieren");
        }

        loadTable();
    }

    private void deleteSelectedKunde() {
        FirmenObj[] fk = this.getSelectedFirmenKunden();

        if (fk == null) {
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
            for (int i = 0; i < fk.length; i++) {
                FirmenSQLMethods.archiveFromfirmenkunden(DatabaseConnection.open(), fk[i].getId());
            }
        } catch (SQLException e) {
            Log.logger.fatal("Fehler: Konnte Firmenkunde nicht löschen", e);
            ShowException.showException("Die ausgewählten Kunden konnte nicht gelöscht werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunde(n) nicht löschen");
        }

        loadTable();
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
                final int row = table_kunden.rowAtPoint(point);

                table_kunden.changeSelection(row, 0, false, false);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        if (row != -1) {
                            if (row != currow) {
                                currow = row;
                                FirmenObj kunde = (FirmenObj) table_kunden.getModel().getValueAt(row, 1);
                                showFirma(kunde);
                            }
                        }
                    }
                });


            }
        }
    }

    private void showKundeKarte() {
        FirmenObj kunde = getSelectedFirmenKunde();
        if (kunde == null) {
            return;
        }

        KarteSuche.doExteneralSearch(kunde.getFirmenStrasse() + ", " + kunde.getFirmenStadt(), crm);
    }

    public FirmenObj getSelkunde() {
        return selkunde;
    }

    public void setSelkunde(FirmenObj selkunde) {
        this.selkunde = selkunde;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpKunden = new javax.swing.ButtonGroup();
        ansichtPopup = new javax.swing.JPopupMenu();
        eigeneMenuItem = new javax.swing.JCheckBoxMenuItem();
        alleMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        alledbMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedbMenuItem = new javax.swing.JCheckBoxMenuItem();
        deleteddbMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbkunden = new javax.swing.ButtonGroup();
        btnGrpKunden = new javax.swing.ButtonGroup();
        tablePopup = new javax.swing.JPopupMenu();
        newKundeItem = new javax.swing.JMenuItem();
        editKundeItem = new javax.swing.JMenuItem();
        archiveKundeItem = new javax.swing.JMenuItem();
        deleteKundeItem = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        tableRefresh = new javax.swing.JMenuItem();
        tableEinstellungenItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        briefMenu = new javax.swing.JMenu();
        briefKundeMenu = new javax.swing.JMenu();
        AlgBriefMenuItem = new javax.swing.JMenuItem();
        TerminbestaetigungMenuItem = new javax.swing.JMenuItem();
        MaklerauftragMenuItem = new javax.swing.JMenuItem();
        MaklereinzelAuftragMenuItem = new javax.swing.JMenuItem();
        briefBenutzerMenu = new javax.swing.JMenu();
        AlgBriefBenMenuItem = new javax.swing.JMenuItem();
        BriefVorlagenOrdnerMenuItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        emailMenu = new javax.swing.JMenu();
        mailKundeMenu = new javax.swing.JMenu();
        AlgEmailMenuItem = new javax.swing.JMenuItem();
        terminbestMenuItem = new javax.swing.JMenuItem();
        mailBenutzerMenu = new javax.swing.JMenu();
        AlgEmailBenMenuItem = new javax.swing.JMenuItem();
        EmailVorlagenMenuItem = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        karteMenuItem = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        DokumenteMenuItem = new javax.swing.JMenuItem();
        datenblattMenuItem = new javax.swing.JMenuItem();
        tableHeaderPopup = new javax.swing.JPopupMenu();
        newKundeItem1 = new javax.swing.JMenuItem();
        tableEinstellungenItem1 = new javax.swing.JMenuItem();
        kundenToolbar = new javax.swing.JToolBar();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDokumente = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnKarte = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnReport = new javax.swing.JButton();
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
        jSeparator13 = new javax.swing.JToolBar.Separator();
        jSplitPane1 = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_kunden = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activekunde = new javax.swing.JLabel();
        pane_contentholder = new javax.swing.JTabbedPane();

        ansichtPopup.setName("ansichtPopup"); // NOI18N

        btnGrpKunden.add(eigeneMenuItem);
        eigeneMenuItem.setMnemonic('E');
        eigeneMenuItem.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelFirmenKundenUebersicht.class);
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

        newKundeItem.setMnemonic('N');
        newKundeItem.setText(resourceMap.getString("newKundeItem.text")); // NOI18N
        newKundeItem.setName("newKundeItem"); // NOI18N
        newKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(newKundeItem);

        editKundeItem.setMnemonic('b');
        editKundeItem.setText(resourceMap.getString("editKundeItem.text")); // NOI18N
        editKundeItem.setName("editKundeItem"); // NOI18N
        editKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(editKundeItem);

        archiveKundeItem.setMnemonic('a');
        archiveKundeItem.setText(resourceMap.getString("archiveKundeItem.text")); // NOI18N
        archiveKundeItem.setName("archiveKundeItem"); // NOI18N
        archiveKundeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveKundeItemActionPerformed(evt);
            }
        });
        tablePopup.add(archiveKundeItem);

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

        tableRefresh.setMnemonic('a');
        tableRefresh.setText(resourceMap.getString("tableRefresh.text")); // NOI18N
        tableRefresh.setName("tableRefresh"); // NOI18N
        tableRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableRefreshActionPerformed(evt);
            }
        });
        tablePopup.add(tableRefresh);

        tableEinstellungenItem.setText(resourceMap.getString("tableEinstellungenItem.text")); // NOI18N
        tableEinstellungenItem.setName("tableEinstellungenItem"); // NOI18N
        tableEinstellungenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableEinstellungenItemActionPerformed(evt);
            }
        });
        tablePopup.add(tableEinstellungenItem);

        jSeparator16.setName("jSeparator16"); // NOI18N
        tablePopup.add(jSeparator16);

        briefMenu.setText(resourceMap.getString("briefMenu.text")); // NOI18N
        briefMenu.setName("briefMenu"); // NOI18N

        briefKundeMenu.setText(resourceMap.getString("briefKundeMenu.text")); // NOI18N
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

        jSeparator17.setName("jSeparator17"); // NOI18N
        tablePopup.add(jSeparator17);

        emailMenu.setText(resourceMap.getString("emailMenu.text")); // NOI18N
        emailMenu.setName("emailMenu"); // NOI18N

        mailKundeMenu.setText(resourceMap.getString("mailKundeMenu.text")); // NOI18N
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

        jSeparator18.setName("jSeparator18"); // NOI18N
        tablePopup.add(jSeparator18);

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

        jSeparator19.setName("jSeparator19"); // NOI18N
        tablePopup.add(jSeparator19);

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

        datenblattMenuItem.setMnemonic('K');
        datenblattMenuItem.setText(resourceMap.getString("datenblattMenuItem.text")); // NOI18N
        datenblattMenuItem.setName("datenblattMenuItem"); // NOI18N
        datenblattMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datenblattMenuItemActionPerformed(evt);
            }
        });
        tablePopup.add(datenblattMenuItem);

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

        btnReport.setIcon(resourceMap.getIcon("btnReport.icon")); // NOI18N
        btnReport.setText(resourceMap.getString("btnReport.text")); // NOI18N
        btnReport.setFocusable(false);
        btnReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReport.setName("btnReport"); // NOI18N
        kundenToolbar.add(btnReport);

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

        fieldSchnellsuche.setName("fieldSchnellsuche"); // NOI18N
        fieldSchnellsuche.setPreferredSize(new java.awt.Dimension(200, 25));
        fieldSchnellsuche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldSchnellsucheKeyTyped(evt);
            }
        });
        toolbarKunden2.add(fieldSchnellsuche);

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
        toolbarKunden2.add(btnSchnellSuche);

        jSeparator11.setName("jSeparator11"); // NOI18N
        toolbarKunden2.add(jSeparator11);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(104, 16));
        toolbarKunden2.add(jLabel2);

        jSeparator12.setName("jSeparator12"); // NOI18N
        toolbarKunden2.add(jSeparator12);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 15));
        toolbarKunden2.add(jLabel3);

        combo_sucheFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_sucheFilter.setName("combo_sucheFilter"); // NOI18N
        toolbarKunden2.add(combo_sucheFilter);

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldDetailsucheKeyTyped(evt);
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

        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setSeparatorSize(new java.awt.Dimension(15, 12));
        toolbarKunden2.add(jSeparator13);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N
        panel_tableholder.setPreferredSize(new java.awt.Dimension(1066, 400));
        panel_tableholder.setLayout(new java.awt.BorderLayout());

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_kunden.setModel(new javax.swing.table.DefaultTableModel(
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
        table_kunden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kunden.setMinimumSize(new java.awt.Dimension(400, 150));
        table_kunden.setName("table_kunden"); // NOI18N
        table_kunden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kundenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_kunden);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 802, Short.MAX_VALUE)
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
            .addComponent(kundenToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
            .addComponent(toolbarKunden2, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kundenToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(toolbarKunden2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(623, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveSelectedKunde();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDokumenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokumenteActionPerformed
        showDokumente();
}//GEN-LAST:event_btnDokumenteActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        showKundeKarte();
}//GEN-LAST:event_btnKarteActionPerformed

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

    private void fieldDetailsucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDetailsucheActionPerformed
        fieldSearch();
    }//GEN-LAST:event_fieldDetailsucheActionPerformed

    private void table_kundenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kundenMouseClicked
        final int row = table_kunden.getSelectedRow();
        if (row == -1) {
            return;
        }


        if (row != currow) {
            currow = row;
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    FirmenObj fa = (FirmenObj) table_kunden.getModel().getValueAt(row, 1);
                    showFirma(fa);
                }
            });
        }

        if (evt.getClickCount() >= 2) {
            FirmenObj fa = (FirmenObj) this.table_kunden.getModel().getValueAt(row, 1);
            selkunde = fa;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            newFirmenKundeBox = new NewFirmenKundeDialog(mainFrame, false, fa);
            newFirmenKundeBox.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                        @Override
                        protected Void doInBackground() throws Exception {
                            loadTable();
                            Log.logger.debug("Geschäftskunden Dialog wurde geschlossen.");
                            return null;
                        }
                    });
                }
            });
            newFirmenKundeBox.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(newFirmenKundeBox);
        }
}//GEN-LAST:event_table_kundenMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteSelectedKunde();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTableSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableSettingsActionPerformed
        openTableSettings();
}//GEN-LAST:event_btnTableSettingsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

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

    private void newKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newKundeItemActionPerformed
        newKunde();
}//GEN-LAST:event_newKundeItemActionPerformed

    private void editKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKundeItemActionPerformed
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.",
                    "Kein Kunde ausgewählt", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        FirmenObj kunde = (FirmenObj) table_kunden.getModel().getValueAt(row, 1);

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newFirmenKundeBox = new NewFirmenKundeDialog(mainFrame, false, kunde);
        newFirmenKundeBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
//                        System.out.println("Closed");
                        return null;
                    }
                });
            }
        });
        newFirmenKundeBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(newFirmenKundeBox);
}//GEN-LAST:event_editKundeItemActionPerformed

    private void deleteKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteKundeItemActionPerformed
        deleteSelectedKunde();
}//GEN-LAST:event_deleteKundeItemActionPerformed

    private void tableRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_tableRefreshActionPerformed

    private void tableEinstellungenItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEinstellungenItemActionPerformed
        openTableSettings();
}//GEN-LAST:event_tableEinstellungenItemActionPerformed

    private void AlgBriefMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlgBriefMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(1)); // Hardcoded, ids sollten sich nicht ändern!
}//GEN-LAST:event_AlgBriefMenuItemActionPerformed

    private void TerminbestaetigungMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminbestaetigungMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(4)); // Hardcoded, ids sollten sich nicht ändern!
}//GEN-LAST:event_TerminbestaetigungMenuItemActionPerformed

    private void MaklerauftragMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaklerauftragMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(14)); // Hardcoded, ids sollten sich nicht ändern!
}//GEN-LAST:event_MaklerauftragMenuItemActionPerformed

    private void MaklereinzelAuftragMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaklereinzelAuftragMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(16)); // Hardcoded, ids sollten sich nicht ändern!
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

    private void AlgEmailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlgEmailMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(3)); // Hardcoded, ids sollten sich nicht ändern!
}//GEN-LAST:event_AlgEmailMenuItemActionPerformed

    private void terminbestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminbestMenuItemActionPerformed
        schreibeBrief(ToolsRegistry.getBrief(6)); // Hardcoded, ids sollten sich nicht ändern!
}//GEN-LAST:event_terminbestMenuItemActionPerformed

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

    private void karteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karteMenuItemActionPerformed
        showKundeKarte();
}//GEN-LAST:event_karteMenuItemActionPerformed

    private void DokumenteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DokumenteMenuItemActionPerformed
        showDokumente();
}//GEN-LAST:event_DokumenteMenuItemActionPerformed

    private void datenblattMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datenblattMenuItemActionPerformed
        exportKundenDatenblatt();
}//GEN-LAST:event_datenblattMenuItemActionPerformed

    private void newKundeItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newKundeItem1ActionPerformed
        newKunde();
}//GEN-LAST:event_newKundeItem1ActionPerformed

    private void tableEinstellungenItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEinstellungenItem1ActionPerformed
        openTableSettings();
}//GEN-LAST:event_tableEinstellungenItem1ActionPerformed

    private void archiveKundeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveKundeItemActionPerformed
        archiveSelectedKunde();
    }//GEN-LAST:event_archiveKundeItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AlgBriefBenMenuItem;
    private javax.swing.JMenuItem AlgBriefMenuItem;
    private javax.swing.JMenuItem AlgEmailBenMenuItem;
    private javax.swing.JMenuItem AlgEmailMenuItem;
    private javax.swing.JMenuItem BriefVorlagenOrdnerMenuItem;
    private javax.swing.JMenuItem DokumenteMenuItem;
    private javax.swing.JMenuItem EmailVorlagenMenuItem;
    private javax.swing.JMenuItem MaklerauftragMenuItem;
    private javax.swing.JMenuItem MaklereinzelAuftragMenuItem;
    private javax.swing.JMenuItem TerminbestaetigungMenuItem;
    private javax.swing.JCheckBoxMenuItem aktivedbMenuItem;
    private javax.swing.JCheckBoxMenuItem alleMenuItem;
    private javax.swing.JCheckBoxMenuItem alledbMenuItem;
    private javax.swing.JPopupMenu ansichtPopup;
    private javax.swing.JMenuItem archiveKundeItem;
    private javax.swing.JCheckBoxMenuItem archivedbMenuItem;
    private javax.swing.JMenu briefBenutzerMenu;
    private javax.swing.JMenu briefKundeMenu;
    private javax.swing.JMenu briefMenu;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDokumente;
    private javax.swing.JButton btnFieldsearch;
    private javax.swing.ButtonGroup btnGrpKunden;
    private javax.swing.JButton btnKarte;
    private javax.swing.JButton btnOperator;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSchnellSuche;
    private javax.swing.JButton btnStatistik;
    private javax.swing.JButton btnTableSettings;
    private javax.swing.JComboBox combo_sucheFilter;
    private javax.swing.JMenuItem datenblattMenuItem;
    private javax.swing.JMenuItem deleteKundeItem;
    private javax.swing.JCheckBoxMenuItem deleteddbMenuItem;
    private javax.swing.JMenuItem editKundeItem;
    private javax.swing.JCheckBoxMenuItem eigeneMenuItem;
    private javax.swing.JMenu emailMenu;
    private javax.swing.JTextField fieldDetailsuche;
    private javax.swing.JTextField fieldSchnellsuche;
    private javax.swing.ButtonGroup grpKunden;
    private javax.swing.ButtonGroup grp_dbkunden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenuItem karteMenuItem;
    private javax.swing.JToolBar kundenToolbar;
    private javax.swing.JLabel label_activekunde;
    private javax.swing.JLabel label_tablestatustext;
    private javax.swing.JMenu mailBenutzerMenu;
    private javax.swing.JMenu mailKundeMenu;
    private javax.swing.JMenuItem newKundeItem;
    private javax.swing.JMenuItem newKundeItem1;
    public javax.swing.JTabbedPane pane_contentholder;
    private javax.swing.JPanel panel_tableStatus;
    private javax.swing.JPanel panel_tableholder;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JMenuItem tableEinstellungenItem;
    private javax.swing.JMenuItem tableEinstellungenItem1;
    private javax.swing.JPopupMenu tableHeaderPopup;
    private javax.swing.JPopupMenu tablePopup;
    private javax.swing.JMenuItem tableRefresh;
    private org.jdesktop.swingx.JXTable table_kunden;
    private javax.swing.JMenuItem terminbestMenuItem;
    private javax.swing.JToolBar toolbarKunden2;
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
