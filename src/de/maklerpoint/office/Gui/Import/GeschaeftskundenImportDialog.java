/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KundenImportDialog.java
 *
 * Created on 12.06.2011, 14:17:04
 */
package de.maklerpoint.office.Gui.Import;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.KundenXMLParent;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.FileTools;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.openide.util.Exceptions;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class GeschaeftskundenImportDialog extends javax.swing.JDialog {

    private Desktop desktop = Desktop.getDesktop();
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private KundenObj[] kunden = null;
    private String[] Column = {
        "betreuerId", "Kundennummer", "Anrede", "Titel", "Vorname", "Vorname 2", "Vorname Weitere", "Nachname",
        "Geburtsname", "Strasse", "PLZ", "Ort", "Bundesland", "Land", "Adresse Zusatz", "Adresse Zusatz 2",
        "Kommunikation 1", "Kommunikation 2", "Kommunikation 3", "Kommunikation 4", "Kommunikation 5", "Kommunikation 6",
        "KommunikationsTyp 1", "KommunikationsTyp 2", "KommunikationsTyp 3", "KommunikationsTyp 4", "KommunikationsTyp 5",
        "KommunikationsTyp 6", "Familienstand", "Ehepartner Kennung", "Ehedatum", "Geburtsdatum", "Nationalität",
        "Beruf", "Berufstyp", "Berufsoptionen", "Berufsbesonderheiten", "Anteil Bürotätigkeit",
        "Beginn Rente", "Beamter", "Öffentlicher Dienst", "Einkommen Brutto", "Einkommen Netto",
        "Steuertabelle", "Steuerklasse", "Kirchensteuer", "Kinderfreibetrag", "Religion", "Rolle im Haushalt",
        "Weitere Personen (Haushalt)", "Weitere Personen Info", "Werber Kundennummer", "Benutzerdefiniert 1",
        "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Benutzerdefiniert 4", "Benutzerdefiniert 5",
        "Kommentare", "Erstellt am", "Status"
    };

    /** Creates new form KundenImportDialog */
    public GeschaeftskundenImportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.kunden = null;
        initComponents();
        setUp();
    }

    private void setUp() {
        addButtons();
        setTable(null, Column);
    }

    private void addButtons() {
        JCommandButton neuButton = new JCommandButton("Neue Importvorlage", getResizableIconFromResource(
                "de/acyrance/CRM/Gui/resources/add.png"));
        neuButton.setExtraText("Neue Importdaten");
        neuButton.setPopupCallback(new NeuPopupCallback());
        neuButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        neuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        neuButton.setFlat(true);

        this.toolbar.add(neuButton, 1);

        JCommandButton importButton = new JCommandButton("Import", getResizableIconFromResource(
                "de/acyrance/CRM/Gui/resources/import.png"));
        importButton.setExtraText("Kundendaten importieren");
        importButton.setPopupCallback(new ImportPopupCallback());
        importButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        importButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        importButton.setFlat(true);

        this.toolbar.add(importButton, 2);
    }

    /**
     *
     */
    private class NeuPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton excel = new JCommandMenuButton("Excel (.xls) Importvorlage",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            excel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    try {
                        // TODO Copy file to tmp first
                        desktop.open(new File(Filesystem.getVorlagenPath() + File.separatorChar
                                + "import" + File.separatorChar + "kundenimport_vorlage.xls"));
                    } catch (IOException ex) {
                        Log.logger.fatal("Fehler: Konnte die Importvorlage nicht öffnen", ex);
                        ShowException.showException("Der Importvorlage konnte nicht geöffnet werden. "
                                + "Sie finden die Vorlage im Verzeichnis \""
                                + Filesystem.getVorlagenPath() + File.separatorChar + "import" 
                                + File.separatorChar + "\" .",
                                ExceptionDialogGui.LEVEL_WARNING, ex,
                                "Schwerwiegend: Konnte Importvorlage nicht öffnen");

                    }
                }
            });

            popupMenu.addMenuButton(excel);

            JCommandMenuButton csv = new JCommandMenuButton("CSV Importvorlage",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            csv.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    try {
                        desktop.open(new File(Filesystem.getVorlagenPath() + File.separatorChar
                                + "import" + File.separatorChar + "kundenimport_vorlage.csv"));
                    } catch (IOException ex) {
                        Log.logger.fatal("Fehler: Konnte die Importvorlage nicht öffnen", ex);
                        ShowException.showException("Der Importvorlage konnte nicht geöffnet werden. "
                                + "Sie finden die Vorlage im Verzeichnis \""
                                + Filesystem.getVorlagenPath() + File.separatorChar + "import" 
                                + File.separatorChar + "\" .",
                                ExceptionDialogGui.LEVEL_WARNING, ex,
                                "Schwerwiegend: Konnte Importvorlage nicht öffnen");

                    }
                }
            });

            popupMenu.addMenuButton(csv);

            return popupMenu;
        }
    }

    private class ImportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton excel = new JCommandMenuButton("Excel Import",
                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
            excel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    try {
                        importExcel();
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });

            popupMenu.addMenuButton(excel);

            JCommandMenuButton csv = new JCommandMenuButton("CSV Import",
                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
            csv.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            popupMenu.addMenuButton(csv);

            JCommandMenuButton xml = new JCommandMenuButton("XML Import",
                    getResizableIconFromResource(ResourceStrings.XML_ICON));
            xml.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            popupMenu.addMenuButton(xml);

            return popupMenu;
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().
                getResource(resource), new Dimension(16, 16));
    }

    private void searchTable() {
        int result = table_kunden.getSearchable().search(field_search.getText());
    }

    private void importXML() {
        String filename = FileTools.openFile("XML Datei wählen");

        if (filename == null) {
            return;
        }

        Serializer serializer = new Persister();

        File source = new File(filename);
        try {

            Object[][] data = new Object[kunden.length][this.Column.length];
            KundenXMLParent kundenParent = serializer.read(KundenXMLParent.class, source);

            if (kundenParent == null) {
                return;
            }

            this.kunden = kundenParent.getKunden();

            if (kunden == null) {
                return;
            }

            for (int i = 0; i < kunden.length; i++) {
                KundenObj kunde = kunden[i];


                for (int j = 0; j <= 59; j++) {
                    if (j == 0) {
                        data[i][j] = kunde.getBetreuerId();
                    } else if (j == 1) {
                        data[i][j] = kunde.getKundenNr();
                    } else if (j == 2) {
                        data[i][j] = kunde.getAnrede();
                    } else if (j == 3) {
                        data[i][j] = kunde.getTitel();
                    } else if (j == 4) {
                        data[i][j] = kunde.getVorname();
                    } else if (j == 5) {
                        data[i][j] = kunde.getVorname2();
                    } else if (j == 6) {
                        data[i][j] = kunde.getVornameWeitere();
                    } else if (j == 7) {
                        data[i][j] = kunde.getNachname();
                    } else if (j == 8) {
                        data[i][j] = kunde.getGeburtsname();
                    } else if (j == 9) {
                        data[i][j] = kunde.getStreet();
                    } else if (j == 10) {
                        data[i][j] = kunde.getPlz();
                    } else if (j == 11) {
                        data[i][j] = kunde.getStadt();
                    } else if (j == 12) {
                        data[i][j] = kunde.getBundesland();
                    } else if (j == 13) {
                        data[i][j] = kunde.getLand();
                    } else if (j == 14) {
                        data[i][j] = kunde.getAdresseZusatz();
                    } else if (j == 15) {
                        data[i][j] = kunde.getAdresseZusatz2();
                    } else if (j == 16) {
                        data[i][j] = kunde.getCommunication1();
                    } else if (j == 17) {
                        data[i][j] = kunde.getCommunication2();
                    } else if (j == 18) {
                        data[i][j] = kunde.getCommunication3();
                    } else if (j == 19) {
                        data[i][j] = kunde.getCommunication4();
                    } else if (j == 20) {
                        data[i][j] = kunde.getCommunication5();
                    } else if (j == 21) {
                        data[i][j] = kunde.getCommunication6();
                    } else if (j == 22) {
                        data[i][j] = kunde.getCommunication1Type();
                    } else if (j == 23) {
                        data[i][j] = kunde.getCommunication2Type();
                    } else if (j == 24) {
                        data[i][j] = kunde.getCommunication3Type();
                    } else if (j == 25) {
                        data[i][j] = kunde.getCommunication4Type();
                    } else if (j == 26) {
                        data[i][j] = kunde.getCommunication5Type();
                    } else if (j == 27) {
                        data[i][j] = kunde.getCommunication6Type();
                    } else if (j == 28) {
                        data[i][j] = kunde.getFamilienStand();
                    } else if (j == 29) {
                        data[i][j] = kunde.getEhepartnerKennung();
                    } else if (j == 30) {
                        data[i][j] = kunde.getEhedatum();
                    } else if (j == 31) {
                        data[i][j] = kunde.getGeburtsdatum();
                    } else if (j == 32) {
                        data[i][j] = kunde.getNationalitaet();
                    } else if (j == 33) {
                        data[i][j] = kunde.getBeruf();
                    } else if (j == 34) {
                        data[i][j] = kunde.getBerufsTyp();
                    } else if (j == 35) {
                        data[i][j] = kunde.getBerufsOptionen();
                    } else if (j == 36) {
                        data[i][j] = kunde.getBerufsBesonderheiten();
                    } else if (j == 37) {
                        data[i][j] = kunde.getAnteilBuerotaetigkeit();
                    } else if (j == 38) {
                        data[i][j] = kunde.getBeginnRente();
                    } else if (j == 39) {
                        data[i][j] = kunde.isBeamter();
                    } else if (j == 40) {
                        data[i][j] = kunde.isOeffentlicherDienst();
                    } else if (j == 41) {
                        data[i][j] = kunde.getEinkommen();
                    } else if (j == 42) {
                        data[i][j] = kunde.getEinkommenNetto();
                    } else if (j == 43) {
                        data[i][j] = kunde.getSteuertabelle();
                    } else if (j == 44) {
                        data[i][j] = kunde.getSteuerklasse();
                    } else if (j == 45) {
                        data[i][j] = kunde.getKirchenSteuer();
                    } else if (j == 46) {
                        data[i][j] = kunde.getKinderFreibetrag();
                    } else if (j == 47) {
                        data[i][j] = kunde.getReligion();
                    } else if (j == 48) {
                        data[i][j] = kunde.getRolleImHaushalt();
                    } else if (j == 49) {
                        data[i][j] = kunde.getWeiterePersonen();
                    } else if (j == 50) {
                        data[i][j] = kunde.getWeiterePersonenInfo();
                    } else if (j == 51) {
                        data[i][j] = kunde.getWerberKennung();
                    } else if (j == 52) {
                        data[i][j] = kunde.getCustom1();
                    } else if (j == 53) {
                        data[i][j] = kunde.getCustom2();
                    } else if (j == 54) {
                        data[i][j] = kunde.getCustom3();
                    } else if (j == 55) {
                        data[i][j] = kunde.getCustom4();
                    } else if (j == 56) {
                        data[i][j] = kunde.getCustom5();
                    } else if (j == 57) {
                        data[i][j] = kunde.getComments();
                    } else if (j == 58) {
                        data[i][j] = kunde.getCreated();
                    } else if (j == 59) {
                        data[i][j] = kunde.getStatus();
                    }
                }

            }


            setTable(data, Column);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void importExcel() throws IOException {
        String filename = FileTools.openFile("Excel Datei wählen");

        if (filename == null) {
            return;
        }

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filename));
        System.out.println("Data dump:\n");


        HSSFSheet sheet = wb.getSheetAt(0); // Erstes Sheet der Vorlage
        int rows = sheet.getPhysicalNumberOfRows();

        if (rows == 0 || rows == 1) {
            JOptionPane.showMessageDialog(null, "Die Datei enthält keine Einträge.");
            return;
        }

        kunden = new KundenObj[rows - 1];
        Object[][] data = new Object[rows - 1][this.Column.length];

        for (int r = 0; r < rows; r++) {
            HSSFRow row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            kunden[r] = new KundenObj();

            int cells = row.getPhysicalNumberOfCells();

            System.out.println("\nROW " + row.getRowNum() + " has " + cells
                    + " cell(s).");

            for (int c = 0; c < cells; c++) {

                HSSFCell cell = row.getCell(c);
                String value = null;

                switch (cell.getCellType()) {

                    case HSSFCell.CELL_TYPE_FORMULA:
                        value = cell.getCellFormula();
                        break;

                    case HSSFCell.CELL_TYPE_NUMERIC:
                        value = "" + cell.getNumericCellValue();
                        break;

                    case HSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;

                    default:
                }

                if (value.length() < 1) {
                    value = null;
                }

                data[r][c] = value;

                switch (cell.getColumnIndex()) {
                    case 0:
                        if (value != null && value.length() > 0) {
                            kunden[r].setBetreuerId(Integer.valueOf(value));
                        }
                        break;

                    case 1:
                        if (value != null && value.length() > 0) {
                            kunden[r].setKundenNr(value);
                        }
                        break;

                    case 2:
                        kunden[r].setAnrede(value);
                        break;

                    case 3:
                        kunden[r].setTitel(value);
                        break;

                    case 4:
                        kunden[r].setVorname(value);
                        break;

                    case 5:
                        kunden[r].setVorname2(value);
                        break;

                    case 6:
                        kunden[r].setVornameWeitere(value);
                        break;

                    case 7:
                        kunden[r].setNachname(value);
                        break;

                    case 8:
                        kunden[r].setGeburtsname(value);
                        break;

                    case 9:
                        kunden[r].setStreet(value);
                        break;

                    case 10:
                        kunden[r].setPlz(value);
                        break;

                    case 11:
                        kunden[r].setStadt(value);
                        break;

                    case 12:
                        kunden[r].setBundesland(value);
                        break;

                    case 13:
                        kunden[r].setLand(value);
                        break;

                    case 14:
                        kunden[r].setAdresseZusatz(value);
                        break;

                    case 15:
                        kunden[r].setAdresseZusatz2(value);
                        break;

                    case 16:
                        kunden[r].setCommunication1(value);
                        break;

                    case 17:
                        kunden[r].setCommunication2(value);
                        break;

                    case 18:
                        kunden[r].setCommunication3(value);
                        break;

                    case 19:
                        kunden[r].setCommunication4(value);
                        break;

                    case 20:
                        kunden[r].setCommunication5(value);
                        break;

                    case 21:
                        kunden[r].setCommunication6(value);
                        break;

                    case 22:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication1Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication1Type(0);
                        }
                        break;

                    case 23:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication2Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication2Type(1);
                        }
                        break;

                    case 24:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication3Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication3Type(4);
                        }
                        break;

                    case 25:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication4Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication4Type(7);
                        }
                        break;

                    case 26:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication5Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication5Type(10);
                        }
                        break;

                    case 27:
                        if (value != null && value.length() > 0) {
                            kunden[r].setCommunication6Type(Integer.valueOf(value));
                        } else {
                            kunden[r].setCommunication6Type(14);
                        }
                        break;

                    case 28:
                        kunden[r].setFamilienStand(value);
                        break;

                    case 29:
                        if (value != null && value.length() > 0) {
                            kunden[r].setEhepartnerId(value);
                        }
                        break;

                    case 30:
                        if (value != null && value.length() > 0) {
                            kunden[r].setEhedatum(value);
                        }
                        break;

                    case 31:
                        kunden[r].setGeburtsdatum(value);
                        break;

                    case 32:
                        kunden[r].setNationalitaet(value);
                        break;

                    case 33:
                        kunden[r].setBeruf(value);
                        break;

                    case 34:
                        kunden[r].setBerufsTyp(value);
                        break;

                    case 35:
                        kunden[r].setBerufsOptionen(value);
                        break;

                    case 36:
                        kunden[r].setBerufsBesonderheiten(value);
                        break;

                    case 37:
                        kunden[r].setAnteilBuerotaetigkeit(value);
                        break;

                    case 38:
                        kunden[r].setBeginnRente(value);
                        break;

                    case 39:
                        if (value != null && value.length() > 1) {
                            kunden[r].setBeamter(Boolean.parseBoolean(value)); // TODO Change to ja nein
                        }
                        break;

                    case 40:
                        if (value != null && value.length() > 1) {
                            kunden[r].setOeffentlicherDienst(Boolean.parseBoolean(value));
                        }
                        break;

                    case 41:
                        kunden[r].setEinkommen(Double.valueOf(value));
                        break;

                    case 42:
                        kunden[r].setEinkommenNetto(Double.valueOf(value));
                        break;

                    case 43:
                        kunden[r].setSteuertabelle(value);
                        break;

                    case 44:
                        kunden[r].setSteuerklasse(value);
                        break;

                    case 45:
                        kunden[r].setKirchenSteuer(value);
                        break;

                    case 46:
                        kunden[r].setKinderFreibetrag(value);
                        break;

                    case 47:
                        kunden[r].setReligion(value);
                        break;

                    case 48:
                        kunden[r].setRolleImHaushalt(value);
                        break;

                    case 49:
                        kunden[r].setWeiterePersonen(value);
                        break;

                    case 50:
                        kunden[r].setWeiterePersonenInfo(value);
                        break;

                    case 51:
                        kunden[r].setWerberKennung(value);
                        break;

                    case 52:
                        kunden[r].setCustom1(value);
                        break;

                    case 53:
                        kunden[r].setCustom2(value);
                        break;

                    case 54:
                        kunden[r].setCustom3(value);
                        break;

                    case 55:
                        kunden[r].setCustom4(value);
                        break;

                    case 56:
                        kunden[r].setCustom5(value);
                        break;

                    case 57:
                        kunden[r].setComments(value);
                        break;

                    case 58:
                        try {
                            kunden[r].setCreated(new java.sql.Timestamp(df.parse(value).getTime()));
                        } catch (ParseException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case 59:
                        if (value != null && value.length() > 0) {
                            kunden[r].setStatus(Integer.valueOf(value));
                        } else {
                            kunden[r].setStatus(Status.NORMAL);
                        }
                        break;

                }
//                System.out.println("CELL col=" + cell.getCellNum() + " VALUE="
//                        + value);
            }
        }

        setTable(data, Column);
    }

    private void setTable(Object[][] data, String[] columns) {
        this.table_kunden.setModel(new AbstractStandardModel(data, columns));

        table_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kunden.setColumnSelectionAllowed(false);
        table_kunden.setCellSelectionEnabled(false);
        table_kunden.setRowSelectionAllowed(true);
        table_kunden.setAutoCreateRowSorter(true);

        table_kunden.setFillsViewportHeight(true);
        table_kunden.removeColumn(table_kunden.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_kunden.addMouseListener(popupListener);
        table_kunden.setColumnControlVisible(true);

        JTableHeader header = table_kunden.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_kunden.packAll();

        table_kunden.tableChanged(new TableModelEvent(table_kunden.getModel()));
        table_kunden.revalidate();
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
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_protokolle = new javax.swing.JScrollPane();
        table_kunden = new org.jdesktop.swingx.JXTable();
        toolbar = new javax.swing.JToolBar();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(GeschaeftskundenImportDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

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
        table_kunden.setColumnControlVisible(true);
        table_kunden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kunden.setHorizontalScrollEnabled(true);
        table_kunden.setName("table_kunden"); // NOI18N
        scroll_protokolle.setViewportView(table_kunden);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N
        toolbar.add(jSeparator4);

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

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('I');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(516, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        this.dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (kunden == null) {
            this.dispose();
            return;
        }

        for (int i = 0; i < kunden.length; i++) {
            if (kunden[i].getBetreuerId() == -1) {
                kunden[i].setBetreuerId(BasicRegistry.currentUser.getId());
            }

            if (kunden[i].getKundenNr() == null || kunden[i].getKundenNr().length() < 1) {
                kunden[i].setKundenNr(KundenRegistry.getNewKundenNummer());
            }
            try {
                KundenSQLMethods.insertIntoKunden(DatabaseConnection.open(), kunden[i]);
            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte neuen Kunden nicht in der Datenbank speicher (import)", e);
                ShowException.showException("Der neue Kunde (" + kunden[i].getNachname() 
                        + ") konnte nicht gespeichert werden. Bitte "
                        + " überprüfen Sie die Datenbankfehlermeldung.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht speichern");
            }
        }
}//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                GeschaeftskundenImportDialog dialog = new GeschaeftskundenImportDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JTextField field_search;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_kunden;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
}
