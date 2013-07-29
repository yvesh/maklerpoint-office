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
 * ExportDialog.java
 *
 * Created on Jul 14, 2010, 9:27:59 AM
 */
package de.maklerpoint.office.Gui.Export;

import de.maklerpoint.office.Bank.Tools.BankKontoSQLMethods;
import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schnittstellen.CSV.ExportCSV;
import de.maklerpoint.office.Schnittstellen.Excel.ExportExcelXLS;
import de.maklerpoint.office.Schnittstellen.Excel.ExportExcelXLSX;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.PDF.ExportListePDF;
import de.maklerpoint.office.Schnittstellen.Txt.ExportTxtTable;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.FirmenKundenUebersichtHeader;
import de.maklerpoint.office.Table.KundenUebersichtHeader;
import de.maklerpoint.office.Tools.ArrayStringTools;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportDialog extends javax.swing.JDialog {

    private static final int DEFAULT = 0;
    private static final int KUNDEN = 1;
    private static final int FIRMEN = 2;
    private static final int VERSICHERER = 3;
    private static final int BENUTZER = 4;
    private static final int VERTRAEGE = 5;
    private static final int STOERFAELLE = 6;
    private static final int SCHAEDEN = 7;
    
    private int type;
    private Object[][] allData;
    private Object[] activeItems;
    private Object[] inactiveItems;
    private String[] columnNames;
    private Object[][] exportData = null;
    
    private KundenObj[] kunden;
    private FirmenObj[] firmen;
    private BenutzerObj[] benutzer;
    private VersichererObj[] versicherer;
    private VertragObj[] vtr;
    private StoerfallObj[] stoer;
    private SchadenObj[] sch;
    
    private String filename;
    private Desktop desktop = Desktop.getDesktop();
    private int which = DEFAULT;
    private SimpleDateFormat dftable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleDateFormat dfgeb = new SimpleDateFormat("dd.MM.yyyy");

    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            Object[][] allData, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.allData = allData;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }

    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            KundenObj[] kunden, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = KUNDEN;
        this.kunden = kunden;
        this.firmen = null;
        this.benutzer = null;
        this.vtr = null;
        this.stoer = null;
        this.sch = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }

    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            VersichererObj[] vs, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = VERSICHERER;
        this.versicherer = vs;
        this.firmen = null;
        this.kunden = null;
        this.benutzer = null;
        this.vtr = null;
        this.stoer = null;
        this.sch = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }

    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            FirmenObj[] firmen, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = FIRMEN;
        this.firmen = firmen;
        this.kunden = null;
        this.benutzer = null;
        this.vtr = null;
        this.stoer = null;
        this.sch = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }
    
    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            VertragObj[] vtr, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = VERTRAEGE;
        this.firmen = null;
        this.kunden = null;
        this.benutzer = null;
        this.vtr = vtr;
        this.sch = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }
    
    
     public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            BenutzerObj[] ben, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = BENUTZER;
        this.firmen = null;
        this.kunden = null;
        this.vtr = null;
        this.stoer = null;
        this.benutzer = ben;
        this.sch = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }
          
     public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            StoerfallObj[] stoer, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = STOERFAELLE;
        this.firmen = null;
        this.kunden = null;
        this.vtr = null;
        this.benutzer = null;
        this.sch = null;
        this.stoer = stoer;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }
     
    public ExportDialog(java.awt.Frame parent, boolean modal, int type,
            SchadenObj[] sch, Object[] activeItems,
            Object[] inactiveItems) {
        super(parent, modal);
        this.type = type;
        this.which = SCHAEDEN;
        this.firmen = null;
        this.kunden = null;
        this.vtr = null;
        this.benutzer = null;
        this.sch = sch;
        this.stoer = null;
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        initComponents();
        setUpTitle();
    }

    /** Creates new form ExportDialog */
    public ExportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.dispose();
        initComponents();
    }

    public void exportKunden(String ids) {
        String[] results = ids.split(",");
        columnNames = new String[results.length];

        for (int i = 0; i < results.length; i++) {
            String columnName = KundenUebersichtHeader.Columns[Integer.valueOf(results[i].trim())];
            columnNames[i] = columnName;
        }

        exportData = new Object[kunden.length][results.length];

        for (int i = 0; i < kunden.length; i++) {
            KundenObj kunde = kunden[i];

            for (int j = 0; j < results.length; j++) {
                int result = Integer.valueOf(results[j]);
                if (result == 0) {
                    exportData[i][j] = kunde.getId();
                } else if (result == 1) {
                    exportData[i][j] = BenutzerRegistry.getBenutzer(kunde.getBetreuerId());
                } else if (result == 2) {
                    exportData[i][j] = BenutzerRegistry.getBenutzer(kunde.getCreatorId());
                } else if (result == 3) {
                    exportData[i][j] = kunde.getKundenNr();
                } else if (result == 4) {
                    exportData[i][j] = kunde.getAnrede();
                } else if (result == 5) {
                    exportData[i][j] = kunde.getTitel();
                } else if (result == 6) {
                    exportData[i][j] = kunde.getFirma();
                } else if (result == 7) {
                    exportData[i][j] = kunde.getVorname();
                } else if (result == 8) {
                    exportData[i][j] = kunde.getVorname2();
                } else if (result == 9) {
                    exportData[i][j] = kunde.getVornameWeitere();
                } else if (result == 10) {
                    exportData[i][j] = kunde.getNachname();
                } else if (result == 11) {
                    exportData[i][j] = kunde.getStreet();
                } else if (result == 12) {
                    exportData[i][j] = kunde.getPlz();
                } else if (result == 13) {
                    exportData[i][j] = kunde.getStadt();
                } else if (result == 14) {
                    exportData[i][j] = kunde.getBundesland();
                } else if (result == 15) {
                    exportData[i][j] = kunde.getLand();
                } else if (result == 16) {
                    exportData[i][j] = kunde.getAdresseZusatz();
                } else if (result == 17) {
                    exportData[i][j] = kunde.getCommunication1();
                } else if (result == 18) {
                    exportData[i][j] = kunde.getCommunication2();
                } else if (result == 19) {
                    exportData[i][j] = kunde.getCommunication3();
                } else if (result == 20) {
                    exportData[i][j] = kunde.getCommunication4();
                } else if (result == 21) {
                    exportData[i][j] = kunde.getCommunication5();
                } else if (result == 22) {
                    exportData[i][j] = kunde.getCommunication6();
                } else if (result == 23) {
                    exportData[i][j] = "";
                } else if (result == 24) {
                    exportData[i][j] = "";
                } else if (result == 25) {
                    exportData[i][j] = "";
                } else if (result == 26) {
                    exportData[i][j] = "";
                } else if (result == 27) {
                    exportData[i][j] = "";
                } else if (result == 28) {
                    exportData[i][j] = "";
                } else if (result == 29) {
                    exportData[i][j] = kunde.getTyp();
                } else if (result == 30) {
                    exportData[i][j] = kunde.getFamilienStand();
                } else if (result == 31) {
                    if(!kunde.getEhepartnerKennung().equalsIgnoreCase("-1"))
                        exportData[i][j] = KundenRegistry.getKunde(kunde.getEhepartnerKennung());
                    else
                        exportData[i][j] = "";
                } else if (result == 32) {
                    exportData[i][j] = kunde.getGeburtsdatum();
                } else if (result == 33) {
                    exportData[i][j] = kunde.getBeruf();
                } else if (result == 34) {
                    exportData[i][j] = kunde.getBerufsTyp();
                } else if (result == 35) {
                    exportData[i][j] = kunde.getBerufsOptionen();
                } else if (result == 36) {
                    exportData[i][j] = kunde.isBeamter();
                } else if (result == 37) {
                    exportData[i][j] = kunde.isOeffentlicherDienst();
                } else if (result == 38) {
                    exportData[i][j] = kunde.getEinkommen();
                } else if (result == 39) {
                    exportData[i][j] = kunde.getEinkommenNetto();
                } else if (result == 40) {
                    exportData[i][j] = kunde.getSteuerklasse();
                } else if (result == 41) {
                    exportData[i][j] = kunde.getKinderZahl();
                } else if (result == 42) {
                    exportData[i][j] = kunde.getReligion();
                } else if (result == 43) {
                    exportData[i][j] = kunde.getWeiterePersonen();
                } else if (result == 44) {
                    exportData[i][j] = kunde.getWeiterePersonenInfo();
                } else if (result == 45) {
                    exportData[i][j] = kunde.getFamilienPlanung();
                } else if (result == 46) {
                    exportData[i][j] = KundenRegistry.getKunde(kunde.getWerberKennung());
                } else if (result == 47) {
                    exportData[i][j] = "";
                } else if (result == 48) {
                    exportData[i][j] = "";
                } else if (result == 49) {
                    exportData[i][j] = "";
                } else if (result == 50) {
                    exportData[i][j] = "";
                } else if (result == 51) {
                    exportData[i][j] = "";
                } else if (result == 52) {
                    exportData[i][j] = "";
                } else if (result == 53) {
                    exportData[i][j] = kunde.getComments();
                } else if (result == 54) {
                    exportData[i][j] = kunde.getCustom1();
                } else if (result == 55) {
                    exportData[i][j] = kunde.getCustom2();
                } else if (result == 56) {
                    exportData[i][j] = kunde.getCustom3();
                } else if (result == 57) {
                    exportData[i][j] = kunde.getCustom4();
                } else if (result == 58) {
                    exportData[i][j] = kunde.getCustom5();
                } else if (result == 59) {
                    exportData[i][j] = kunde.getGeburtsname();
                } else if (result == 60) {
                    exportData[i][j] = kunde.getEhedatum();
                } else if (result == 61) {
                    exportData[i][j] = dftable.format(kunde.getCreated());
                } else if (result == 62) {
                    exportData[i][j] = dftable.format(kunde.getModified());
                } else if (result == 63) {
                    exportData[i][j] = kunde.getStatus();
                }
            }
        }

//      System.out.println("Exporting..");
        if (type == ExportImportTypen.CSV) {
            ExportCSV export = new ExportCSV(columnNames, exportData, filename);
            try {
                export.write();
            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als CSV Datei exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine CSV Datei exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");

            }
        } else if (type == ExportImportTypen.XLS) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            ExportExcelXLS export = new ExportExcelXLS("Kunden Export", "Kunden Export " + df.format(new Date(System.currentTimeMillis())),
                    filename, columnNames, exportData);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Excel Datei (xls) exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine Excel Datei (xls) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.XLSX) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            ExportExcelXLSX export = new ExportExcelXLSX("Kunden Export", "Kunden Export " + df.format(new Date(System.currentTimeMillis())),
                    filename, columnNames, exportData);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Excel Datei (xls) exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine Excel 2007 Datei (xlsx) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.PDF) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            ExportListePDF export = new ExportListePDF(filename, columnNames, exportData, "Kunden Export " + df.format(new Date(System.currentTimeMillis())), null);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als PDF Datei (pdf) exportieren", e);
                ShowException.showException("Die Kundenliste konnte nicht als PDF Datei exportiert werden",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.TXT) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            ExportTxtTable export = new ExportTxtTable(filename, "Kunden Export " + df.format(new Date(System.currentTimeMillis())),
                    null, columnNames, exportData, Config.get("exportSeperator", ","));

            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }
            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Text Datei (txt) exportieren", e);
                ShowException.showException("Die Kundenliste konnte nicht als Text Datei (.txt) exportiert werden",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        }

        this.dispose();
    }

    public void exportFirmen(String ids) {
        String[] results = ids.split(",");
        columnNames = new String[results.length];

        for (int i = 0; i < results.length; i++) {
            String columnName = FirmenKundenUebersichtHeader.Columns[Integer.valueOf(results[i].trim())];
            columnNames[i] = columnName;
        }

        exportData = new Object[firmen.length][results.length];

        for (int i = 0; i < firmen.length; i++) {
            FirmenObj firma = firmen[i];

            for (int j = 0; j < results.length; j++) {
                int result = Integer.valueOf(results[j]);
                if (result == 0) {
                    exportData[i][j] = firma.getId();
                } else if (result == 1) {
                    exportData[i][j] = BenutzerRegistry.getBenutzer(firma.getCreator(), false);
                } else if (result == 2) {
                    if (firma.getParentFirma() != -1) {
                        exportData[i][j] = KundenRegistry.getFirmenKunde(firma.getParentFirma(), false);
                    } else {
                        exportData[i][j] = "Keine";
                    }
                } else if (result == 3) {
                    exportData[i][j] = BenutzerRegistry.getBenutzer(firma.getBetreuer(), false);
                } else if (result == 4) {
                    exportData[i][j] = firma.getType();
                } else if (result == 5) {
                    exportData[i][j] = firma.getKundenNr();
                } else if (result == 6) {
                    exportData[i][j] = firma.getFirmenName();
                } else if (result == 7) {
                    exportData[i][j] = firma.getFirmenNameZusatz();
                } else if (result == 8) {
                    exportData[i][j] = firma.getFirmenNameZusatz2();
                } else if (result == 9) {
                    exportData[i][j] = firma.getFirmenStrasse();
                } else if (result == 10) {
                    exportData[i][j] = firma.getFirmenPLZ();
                } else if (result == 11) {
                    exportData[i][j] = firma.getFirmenStadt();
                } else if (result == 12) {
                    exportData[i][j] = firma.getFirmenBundesland();
                } else if (result == 13) {
                    exportData[i][j] = firma.getFirmenLand();
                } else if (result == 14) {
                    exportData[i][j] = firma.getFirmenTyp();
                } else if (result == 15) {
                    exportData[i][j] = firma.getFirmenSize();
                } else if (result == 16) {
                    exportData[i][j] = firma.isFirmenPostfach();
                } else if (result == 17) {
                    exportData[i][j] = firma.getFirmenPostfachName();
                } else if (result == 18) {
                    exportData[i][j] = firma.getFirmenPostfachPlz();
                } else if (result == 19) {
                    exportData[i][j] = firma.getFirmenPostfachOrt();
                } else if (result == 20) {
                    exportData[i][j] = firma.getFirmenRechtsform();
                } else if (result == 21) {
                    exportData[i][j] = firma.getFirmenEinkommen();
                } else if (result == 22) {
                    exportData[i][j] = firma.getFirmenBranche();
                } else if (result == 23) {
                    exportData[i][j] = dfgeb.format(firma.getFirmenGruendungDatum());
                } else if (result == 24) {
                    exportData[i][j] = firma.getFirmenGeschaeftsfuehrer();
                } else if (result == 25) {
                    exportData[i][j] = ArrayStringTools.arrayToString(firma.getFirmenProKura(), ", ");
                } else if (result == 26) {
                    exportData[i][j] = ArrayStringTools.arrayToString(firma.getFirmenStandorte(), ", ");
                } else if (result == 27) {
                    exportData[i][j] = firma.getCommunication1();
                } else if (result == 28) {
                    exportData[i][j] = firma.getCommunication2();
                } else if (result == 29) {
                    exportData[i][j] = firma.getCommunication3();
                } else if (result == 30) {
                    exportData[i][j] = firma.getCommunication4();
                } else if (result == 31) {
                    exportData[i][j] = firma.getCommunication5();
                } else if (result == 32) {
                    exportData[i][j] = firma.getCommunication6();
                } else if (result == 33) {
                    exportData[i][j] = "";
                } else if (result == 34) {
                    exportData[i][j] = "";
                } else if (result == 35) {
                    exportData[i][j] = "";
                } else if (result == 36) {
                    exportData[i][j] = "";
                } else if (result == 37) {
                    exportData[i][j] = "";
                } else if (result == 38) {
                    exportData[i][j] = "";
                } else if (result == 39) {
                    if (firma.getDefaultKonto() == -1) {
                        exportData[i][j] = "Keines";
                    } else {
                        try {
                            exportData[i][j] = BankKontoSQLMethods.getKonto(DatabaseConnection.open(), firma.getDefaultKonto());
                        } catch (SQLException ex) {
                            Exceptions.printStackTrace(ex);
                            exportData[i][j] = "";
                        }
                    }
                } else if (result == 40) {
                    if (firma.getDefaultAnsprechpartner() == -1) {
                        exportData[i][j] = "Niemand";
                    } else {
                        try {
                            exportData[i][j] = FirmenAnsprechpartnerSQLMethods.getAnsprechpartner(DatabaseConnection.open(), 
                                    firma.getDefaultAnsprechpartner());
                        } catch (SQLException ex) {
                            Exceptions.printStackTrace(ex);
                            exportData[i][j] = "";
                        }
                    }
                } else if (result == 41) {
                    exportData[i][j] = "";
                } else if (result == 42) {
                    exportData[i][j] = "";
                } else if (result == 43) {
                    exportData[i][j] = "";
                } else if (result == 44) {
                    exportData[i][j] = "";
                } else if (result == 45) {
                    exportData[i][j] = KundenRegistry.getKunde(firma.getWerber());
                } else if (result == 46) {
                    exportData[i][j] = firma.getComments();
                } else if (result == 47) {
                    exportData[i][j] = firma.getCustom1();
                } else if (result == 48) {
                    exportData[i][j] = firma.getCustom2();
                } else if (result == 49) {
                    exportData[i][j] = firma.getCustom3();
                } else if (result == 50) {
                    exportData[i][j] = firma.getCustom4();
                } else if (result == 51) {
                    exportData[i][j] = firma.getCustom5();
                } else if (result == 52) {
                    exportData[i][j] = dftable.format(firma.getCreated());
                } else if (result == 53) {
                    exportData[i][j] = dftable.format(firma.getModified());
                } else if (result == 54) {
                    exportData[i][j] = Status.getName(firma.getStatus());
                }
            }
        }

//      System.out.println("Exporting..");
        if (type == ExportImportTypen.CSV) {
            ExportCSV export = new ExportCSV(columnNames, exportData, filename);
            try {
                export.write();
            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als CSV Datei exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine CSV Datei exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");

            }
        } else if (type == ExportImportTypen.XLS) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            ExportExcelXLS export = new ExportExcelXLS("Geschäftskunden Export", "Geschäftskunden Export " + df.format(new Date(System.currentTimeMillis())),
                    filename, columnNames, exportData);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Excel Datei (xls) exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine Excel Datei (xls) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.XLSX) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            ExportExcelXLSX export = new ExportExcelXLSX("Geschäftskunden Export", "Geschäftskunden Export " + df.format(new Date(System.currentTimeMillis())),
                    filename, columnNames, exportData);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Excel Datei (xls) exportieren", e);
                ShowException.showException("Konnte die Daten nicht in eine Excel 2007 Datei (xlsx) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.PDF) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            ExportListePDF export = new ExportListePDF(filename, columnNames, exportData, "Geschäftskunden Export " + df.format(new Date(System.currentTimeMillis())), null);
            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }

            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als PDF Datei (pdf) exportieren", e);
                ShowException.showException("Konnte Kundenliste nicht als PDF Datei exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        } else if (type == ExportImportTypen.TXT) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            ExportTxtTable export = new ExportTxtTable(filename, "Geschäftskunden Export " + df.format(new Date(System.currentTimeMillis())),
                    "", columnNames, exportData, Config.get("exportSeperator", ","));

            try {
                export.write();

                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(filename));
                }
            } catch (Exception e) {
                Log.logger.fatal("Fehler: Konnte Daten nicht als Text Datei (txt) exportieren", e);
                ShowException.showException("Konnte Geschäftskunden nicht als Text Datei (.txt) exportieren",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
            }
        }

        this.dispose();
    }

    private void setUpTitle() {
        switch (type) {
            case ExportImportTypen.CSV:
                this.setTitle("Export im CSV Format");
                break;

            case ExportImportTypen.XLS:
                this.setTitle("Export im Excel Format (.xls)");
                break;

            case ExportImportTypen.DOC:
                this.setTitle("Export im Word Format (.doc)");
                break;

            case ExportImportTypen.PDF:
                this.setTitle("Export im PDF Format (.pdf)");
                break;

            case ExportImportTypen.XLSX:
                this.setTitle("Export im Excel 2007 Format (.xlsx)");
                break;

            case ExportImportTypen.SQL:
                this.setTitle("Export im SQL Format (.sql)");
                break;

            case ExportImportTypen.TXT:
                this.setTitle("Export im Text Format (.txt)");
                break;

            default:
                this.setTitle("Export als Fehler");
                break;
        }
    }

    /**
     * 
     */
    public void loadActive() {
        DefaultListModel activeModel = new DefaultListModel();
        for (int i = 0; i < activeItems.length; i++) {
            activeModel.add(i, activeItems[i]);
        }
        list_active.setModel(activeModel);
        list_active.revalidate();
    }

    /**
     * 
     */
    public void loadInactive() {
        DefaultListModel inactiveModel = new DefaultListModel();
        for (int i = 0; i < inactiveItems.length; i++) {
            inactiveModel.add(i, inactiveItems[i]);
        }
        list_inactive.setModel(inactiveModel);
        list_inactive.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        list_inactive = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_active = new javax.swing.JList();
        btnUp = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnExport = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        field_dateiname = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(ExportDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        list_inactive.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_inactive.setName("list_inactive"); // NOI18N
        jScrollPane2.setViewportView(list_inactive);
        this.loadInactive();

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_active.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_active.setName("list_active"); // NOI18N
        jScrollPane1.setViewportView(list_active);
        this.loadActive();

        btnUp.setIcon(resourceMap.getIcon("btnUp.icon")); // NOI18N
        btnUp.setName("btnUp"); // NOI18N
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        btnLeft.setIcon(resourceMap.getIcon("btnLeft.icon")); // NOI18N
        btnLeft.setName("btnLeft"); // NOI18N
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setIcon(resourceMap.getIcon("btnRight.icon")); // NOI18N
        btnRight.setName("btnRight"); // NOI18N
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnDown.setIcon(resourceMap.getIcon("btnDown.icon")); // NOI18N
        btnDown.setName("btnDown"); // NOI18N
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        btnExport.setMnemonic('E');
        btnExport.setText(resourceMap.getString("btnExport.text")); // NOI18N
        btnExport.setName("btnExport"); // NOI18N
        btnExport.setPreferredSize(new java.awt.Dimension(90, 27));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(90, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        field_dateiname.setText(resourceMap.getString("field_dateiname.text")); // NOI18N
        field_dateiname.setName("field_dateiname"); // NOI18N
        field_dateiname.setPreferredSize(new java.awt.Dimension(150, 25));
        field_dateiname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_dateinameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUp)
                            .addComponent(btnLeft)
                            .addComponent(btnRight)
                            .addComponent(btnDown))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_dateiname, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(field_dateiname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnUp)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLeft)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnRight)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnDown)
                            .addGap(60, 60, 60))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();

        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        if (selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if (index != 0) {
                Object temp = active.remove(index);
                active.add(index - 1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index - 1);
                list_active.revalidate();
            }
        }
}//GEN-LAST:event_btnUpActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        Object[] selected = list_inactive.getSelectedValues();

        if (selected == null) {
            return;
        }

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for (int i = 0; i < selected.length; i++) {
            active.addElement(selected[i]);
            inactive.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
}//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for (int i = 0; i < selected.length; i++) {
            inactive.addElement(selected[i]);
            active.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
}//GEN-LAST:event_btnRightActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();
        Object[] selected = list_active.getSelectedValues();

        if (selected == null) {
            return;
        }

        if (selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if (index < active.size() - 1) {
                Object temp = active.remove(index);
                active.add(index + 1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index + 1);
                list_active.revalidate();
            }
        }
    }//GEN-LAST:event_btnDownActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void field_dateinameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_dateinameMouseClicked
        String file = FileTools.saveFile(ExportImportTypen.getDialogName(type), ExportImportTypen.getTypeName(type));

        if (file != null) {
            this.filename = file;
            this.field_dateiname.setText(file);
        }
    }//GEN-LAST:event_field_dateinameMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        if (this.field_dateiname.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Dateinamen zum exportieren aus",
                    "Fehler: Keine Datei ausgewählt", JOptionPane.ERROR_MESSAGE);
            return;
        }

        filename = this.field_dateiname.getText();

        if (new File(filename).exists()) {
            int answer = JOptionPane.showConfirmDialog(null, "Eine Datei mit dem Namen " + new File(filename).getName()
                    + " existiert schon. Möchten Sie sie überschreiben?", "Datei überschreiben?", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        DefaultListModel active = (DefaultListModel) list_active.getModel();

        StringBuilder ids = new StringBuilder();

        for (int i = 0; i < active.getSize(); i++) {
            Object selected = active.get(i);

            try {
                de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht.ColumnHead sel =
                        (de.maklerpoint.office.Gui.Kunden.PanelKundenUebersicht.ColumnHead) selected;

                ids.append(sel.getId());
            } catch (Exception e) {
                de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht.ColumnHead sel =
                        (de.maklerpoint.office.Gui.Firmenkunden.PanelFirmenKundenUebersicht.ColumnHead) selected;

                ids.append(sel.getId());
            }

            if (i != active.getSize() - 1) {
                ids.append(",");
            }
        }

        if (this.which == KUNDEN) {
            this.exportKunden(ids.toString());
        } else if (this.which == FIRMEN) {
            this.exportFirmen(ids.toString());
        }
    }//GEN-LAST:event_btnExportActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ExportDialog dialog = new ExportDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnUp;
    private javax.swing.JTextField field_dateiname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList list_active;
    private javax.swing.JList list_inactive;
    // End of variables declaration//GEN-END:variables
}
