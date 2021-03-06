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
 * BeratungsprotokollDialog.java
 *
 * Created on Jul 29, 2010, 1:06:12 PM
 */
package de.maklerpoint.office.Gui.Beratungsprotokoll;

import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Beratungsprotokoll.Tools.BeratungsprotokollSQLMethods;
import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Dokumente.Tools.DokumentenSQLMethods;
import de.maklerpoint.office.Dokumente.Tools.DokumentenTrigger;
import de.maklerpoint.office.Dokumente.WissenDokumentenObj;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Filesystem.FilesystemKunden;
import de.maklerpoint.office.Gui.TextbauSteine.TextbausteinDialogHelper;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Briefe.Tools.FirmenKundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.DokumentenRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Word.ExportBeratungsAnschreiben;
import de.maklerpoint.office.Schnittstellen.Word.ExportBeratungsDokumentation;
import de.maklerpoint.office.Schnittstellen.Word.ExportBeratungsVerzicht;
import de.maklerpoint.office.Schnittstellen.Word.ExportKundenInformationen;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayTools;
import de.maklerpoint.office.Tools.DirectoryTools;
import de.maklerpoint.office.Tools.FileCopy;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.ZipFiles;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import de.schlichtherle.io.ArchiveException;
import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BeratungsprotokollDialog extends javax.swing.JDialog {

    private KundenObj kunde = null;
    private FirmenObj fakunde = null;
    private BeratungsprotokollObj protokoll = null;
    private boolean update = false;
    private Desktop desktop = Desktop.getDesktop();
    private WissenDokumentenObj[] dokumente;
    private boolean loading = true;
    private String exportfilename = null;
    private String kdnr;
    private int type = -1;
    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;

    /** Creates new form BeratungsprotokollDialog
     * @deprecated 
     */
    public BeratungsprotokollDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setUpForms();
    }

    /** Creates new form BeratungsprotokollDialog */
    public BeratungsprotokollDialog(java.awt.Frame parent, boolean modal, KundenObj kunde) {
        super(parent, modal);
        this.type = PRIVAT;
        this.kunde = kunde;
        this.kdnr = kunde.getKundenNr();
        initComponents();
        setUpForms();
        setupTitle();
    }

    public BeratungsprotokollDialog(java.awt.Frame parent, boolean modal, FirmenObj fa) {
        super(parent, modal);
        this.type = FIRMA;
        this.fakunde = fa;
        this.kdnr = fa.getKundenNr();
        initComponents();
        setUpForms();
        setupTitle();
    }

    public BeratungsprotokollDialog(java.awt.Frame parent, boolean modal, BeratungsprotokollObj protokoll) {
        super(parent, modal);
        this.update = true;
        this.protokoll = protokoll;
        this.kdnr = protokoll.getKundenKennung();

        Object knd = KundenRegistry.getKunde(protokoll.getKundenKennung());

        if (knd == null) {
            Log.logger.warn("Fehler: Konnte Kunden mit der Kennung " + protokoll.getKundenKennung() + " nicht laden");
        } else {
            try {
                this.kunde = (KundenObj) knd;
                this.type = PRIVAT;
            } catch (Exception e) {
                this.fakunde = (FirmenObj) knd;
                this.type = FIRMA;
            }
        }

        initComponents();
        setUpForms();
        setupTitle();
        loadProtokoll();
    }

    private void setUpForms() {
        loadVersicherungssparte();
        loadVersicherungen();
        loadVertraege();
    }

    private void setupTitle() {
        if (kunde != null) {
            this.setTitle("Beratungsprotokoll für " + kunde.getVorname() + " "
                    + kunde.getNachname() + " [" + kunde.getKundenNr() + "]");
        }

        if (fakunde != null) {
            this.setTitle("Beratungsprotokoll für " + fakunde.getFirmenName()
                    + " [" + fakunde.getKundenNr() + "]");
        }
    }

    private void generateAnschreiben() {
        StringBuilder sb = new StringBuilder();

        if (type == PRIVAT) {
            sb.append(KundenBriefTools.getKundenBriefAnrede(kunde));
        } else if (type == FIRMA) {
            sb.append(FirmenKundenBriefTools.getKundenBriefAnrede());
        }
        sb.append("\n\n");

        sb.append("anbei erhalten Sie die Dokumente zu Ihrer Beratung.");

        sb.append("\n\n\n");
        sb.append("Mit freundlichen Grüßen, \n\n");
        sb.append(BasicRegistry.currentUser.getVorname()).append(" ").append(BasicRegistry.currentUser.getNachname());

        text_anschreiben.setText(sb.toString());
    }

    private void loadProtokoll() {
//        this.fieldKundenwuensche.setText();
//        this.fieldKundenbedarf.setText(null);
//        this.text_rat;
//        this.text_entscheidung;
//        this.combo_markt;
//        this.comboSparte;
//        this.comboGesellschaft;
//        this.comboBeratungsverzichtArt;
//        this.list_active;
//        this.check_anschreiben;
//        this.check_dokumentation;
//        this.check_druckstuecke;
//        this.check_verzicht;
//        this.check_pflichten;
//        this.date_wiedervorlage;
//        this.text_anschreiben;

        if (protokoll == null) {
            return;
        }

        this.fieldKundenwuensche.setText(protokoll.getKundenWuensche());
        this.fieldKundenbedarf.setText(protokoll.getKundenBedarf());
        this.text_rat.setText(protokoll.getRat());
        this.text_entscheidung.setText(protokoll.getEntscheidung());

        this.combo_markt.setSelectedItem(protokoll.getMarktuntersuchung());
        this.text_bemerkungen.setText(protokoll.getKundenBemerkungen());

        this.comboSparte.setSelectedItem(protokoll.getMarktuntersuchung());

        if (protokoll.getVersicherungsGesellschaft() != null) {
            this.comboGesellschaft.setSelectedItem(protokoll.getVersicherungsGesellschaft());
        }
        this.comboBeratungsverzichtArt.setSelectedItem(protokoll.getBeratungsVerzichtArt());
        if (protokoll.getVertragId() != -1) {
            this.comboVertrag.setSelectedIndex(protokoll.getVertragId()); // TODO testen
        }
        if (protokoll.getProduktId() != -1) {
            this.comboProdukt.setSelectedItem(VersicherungsRegistry.getProdukt(protokoll.getProduktId()));
        }

        //TODO
        //this.list_active;

        this.check_anschreiben.setSelected(protokoll.isCheckKundenAnschreiben());
        this.check_dokumentation.setSelected(protokoll.isCheckBeratungsDokumentation());
        this.check_druckstuecke.setSelected(protokoll.isCheckDruckstuecke());
        this.check_verzicht.setSelected(protokoll.isCheckBeratungsDokuVerzicht());
        this.check_pflichten.setSelected(protokoll.isCheckInformationsPflichten());

        // TODO ADD WIEDERVORLAGEN
        if (protokoll.isWiederVorlage()) {
            this.date_wiedervorlage.setDate(null);
        } else {
            this.date_wiedervorlage.setDate(null);
        }

        this.text_anschreiben.setText(protokoll.getKundenAnschreiben());
    }

    private void loadVersicherungssparte() {
        comboSparte.setModel(new DefaultComboBoxModel(VersicherungsRegistry.getSparten(true)));
        comboSparte.revalidate();
    }

    private void loadVersicherungen() {
        VersichererObj[] vs = VersicherungsRegistry.getVersicherer(Status.NORMAL);
        this.comboGesellschaft.setModel(new DefaultComboBoxModel(ArrayTools.arrayPlusOne(
                vs, "--Bitte wählen--")));
        comboGesellschaft.revalidate();
        if (vs != null) {
            loadProdukte(vs[0].getId());
        }
    }

    private void loadProdukte(int vsid) {
        this.comboProdukt.setModel(new DefaultComboBoxModel(ComboBoxGetter.getProduktCombo("--Bitte wählen--", vsid)));
    }

    private void loadVertraege() {
        this.comboVertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo("--Bitte wählen--", kdnr)));
    }

    private void exportfile() {
        String file = null;

        if (radio_exportzip.isSelected()) {
            file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.ZIP),
                    ExportImportTypen.getTypeName(ExportImportTypen.ZIP));
        } else {
            file = DirectoryTools.getDirectory();
        }

        if (file != null) {
            this.exportfilename = file;
            this.field_exportZiel.setText(file);
        }
    }

    private BeratungsprotokollObj getBeratungsprotokoll() {
        if (update == false) {
            protokoll = new BeratungsprotokollObj();
        }
        int versid = -1;
        int pid = -1;
        int vtrid = -1;

        protokoll.setKundenWuensche(fieldKundenwuensche.getText());
        protokoll.setKundenBedarf(fieldKundenbedarf.getText());
        protokoll.setRat(text_rat.getText());
        protokoll.setEntscheidung(text_entscheidung.getText());
        protokoll.setMarktuntersuchung((String) combo_markt.getSelectedItem());
        protokoll.setKundenBemerkungen(text_bemerkungen.getText());
        protokoll.setVersicherungsSparte(comboSparte.getSelectedItem().toString());
        protokoll.setVersicherungsGesellschaft(comboGesellschaft.getSelectedItem().toString());

        try {
            VersichererObj vs = (VersichererObj) comboGesellschaft.getSelectedItem();
            versid = vs.getId();
        } catch (Exception e) {
        }

        try {
            ProduktObj prd = (ProduktObj) comboProdukt.getSelectedItem();
            pid = prd.getId();
        } catch (Exception e) {
        }

        try {
            VertragObj vtr = (VertragObj) comboVertrag.getSelectedItem();
            vtrid = vtr.getId();
        } catch (Exception e) {
        }

        protokoll.setVersicherungsId(versid);
        protokoll.setProduktId(pid);
        protokoll.setVertragId(vtrid);

        protokoll.setBeratungsVerzichtArt((String) comboBeratungsverzichtArt.getSelectedItem());


//        for(int i = 0; i < list_active.get; i++) {
//            list_active.getElementAt(i);
//        }

        DefaultListModel activeM = (DefaultListModel) list_active.getModel();

        Object[] activeF = activeM.toArray();
        String[] activefiles = new String[activeF.length];

        for (int i = 0; i < activeF.length; i++) {
            WissenDokumentenObj dok = (WissenDokumentenObj) activeF[i];
            activefiles[i] = "" + dok.getId();
        }

        // TODo
        protokoll.setDokumente(activefiles);

        protokoll.setCheckKundenAnschreiben(check_anschreiben.isSelected());
        protokoll.setCheckBeratungsDokumentation(check_dokumentation.isSelected());
        protokoll.setCheckDruckstuecke(check_druckstuecke.isSelected());
        protokoll.setCheckBeratungsDokuVerzicht(check_verzicht.isSelected());
        protokoll.setCheckInformationsPflichten(this.check_pflichten.isSelected());

        Date wiedervorlage = this.date_wiedervorlage.getDate();

        if (wiedervorlage == null) {
            protokoll.setWiederVorlage(false);
        } else {
            protokoll.setWiederVorlage(true);
        }

        protokoll.setKundenAnschreiben(text_anschreiben.getText());

        if (type == PRIVAT) {
            protokoll.setKundenKennung(kunde.getKundenNr());
        } else if (type == FIRMA) {
            protokoll.setKundenKennung(fakunde.getKundenNr());
        }

        if (protokoll.getBenutzerId() == -1) {
            protokoll.setBenutzerId(BasicRegistry.currentUser.getId());
        }

        protokoll.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        if (update == false) {
            protokoll.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        }

        return protokoll;
    }

    public void saveBeratungsprotokoll() {
        protokoll = getBeratungsprotokoll();

        try {
            if (update == false) {
                int id = BeratungsprotokollSQLMethods.insertIntoberatungsprotokolle(DatabaseConnection.open(), protokoll);
                protokoll.setId(id);
                update = true;
            } else {
                BeratungsprotokollSQLMethods.updateberatungsprotokolle(DatabaseConnection.open(), protokoll);
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte das Beratungsprotokoll für den "
                    + "Kunden mit der Kennung \"" + protokoll.getKundenKennung() + "\" nicht speichern", e);
            ShowException.showException("Datenbankfehler: Das Beratungsprotokoll konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Protokoll nicht speichern");

        }
    }

    public void loadWissensDokumente() {
        dokumente = DokumentenRegistry.getWissenDokumente();
        if (dokumente == null) {
            return;
        }

        TreeSet<String> categories = new TreeSet<String>();

        DefaultListModel inactiveModel = new DefaultListModel();

        int cnt = 0;

        categories.add("Allgemein");

        for (int i = 0; i < dokumente.length; i++) {
            WissenDokumentenObj dokument = dokumente[i];

            if (!dokument.getCategory().equalsIgnoreCase("allgemein")) {
                categories.add(dokument.getCategory());
            }

            if (dokument.getCategory().equalsIgnoreCase("allgemein")) {
                inactiveModel.add(cnt, dokument);
                cnt++;
            }
        }

//        for(int i = 0; i < categories.toArray().length; i++) {
//            System.out.println("cat: " + categories.toArray()[i]);
//        }

        combo_which.setModel(new DefaultComboBoxModel(categories.toArray()));
        combo_which.setSelectedIndex(0);
        list_inactive.setModel(inactiveModel);
        list_inactive.revalidate();
        this.loading = false;
    }

    public void loadActiveDokumente() {
        if (update == false) {
            DefaultListModel activeModel = new DefaultListModel();
//            activeModel.add(0, "Keine Dateien ausgewählt");
            list_active.setModel(activeModel);
            list_active.revalidate();
        } else {
            //@todo load
            DefaultListModel activeModel = new DefaultListModel();
//            activeModel.add(0, "Keine Dateien ausgewählt");
            list_active.setModel(activeModel);
            list_active.revalidate();
        }
    }

    public void loadDokumente(String category) {
        dokumente = DokumentenRegistry.getWissenDokumente();

        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();
        inactive.removeAllElements();

        int cnt = 0;

        for (int i = 0; i < dokumente.length; i++) {
            WissenDokumentenObj dokument = dokumente[i];

            if (dokument.getCategory().equalsIgnoreCase(category)) {
                inactive.addElement(dokument);
                cnt++;
            }
        }
        if (cnt == 0) {
            inactive.addElement("Keine Dateien");
        }

        list_inactive.setModel(inactive);
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

        btnDokumente = new javax.swing.ButtonGroup();
        btnGrpExport = new javax.swing.ButtonGroup();
        paneBp = new javax.swing.JTabbedPane();
        panelBasisinformationen = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        comboSparte = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        comboGesellschaft = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        comboProdukt = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        comboVertrag = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        comboBeratungsverzichtArt = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelDokumentation = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fieldKundenwuensche = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fieldKundenbedarf = new javax.swing.JTextField();
        btnMaxBedarf = new javax.swing.JButton();
        btnMaxWuensche = new javax.swing.JButton();
        btnTbWuensche = new javax.swing.JButton();
        btnTbBedarf = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_rat = new javax.swing.JTextArea();
        btnMaxRat = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_entscheidung = new javax.swing.JTextArea();
        btnMaxEntscheidung = new javax.swing.JButton();
        btnTbRat = new javax.swing.JButton();
        btnTbEntscheidung = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        text_bemerkungen = new javax.swing.JTextArea();
        btnMaxBemerkungen = new javax.swing.JButton();
        btnTbBemerkungen = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        combo_markt = new javax.swing.JComboBox();
        panelRisiko = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        combo_which = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        text_dokbeschreibung = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        list_active = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_inactive = new javax.swing.JList();
        panelFinish = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        check_anschreiben = new javax.swing.JCheckBox();
        check_dokumentation = new javax.swing.JCheckBox();
        check_verzicht = new javax.swing.JCheckBox();
        check_druckstuecke = new javax.swing.JCheckBox();
        btnPrint = new javax.swing.JButton();
        check_pflichten = new javax.swing.JCheckBox();
        btnExport = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        date_wiedervorlage = new org.jdesktop.swingx.JXDatePicker();
        check_savekundenordner = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        radio_exportzip = new javax.swing.JRadioButton();
        radio_exportword = new javax.swing.JRadioButton();
        field_exportZiel = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnFileopen = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnMaxAnschreiben = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        text_anschreiben = new javax.swing.JTextArea();
        btnTbAnschreiben = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(BeratungsprotokollDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        paneBp.setName("paneBp"); // NOI18N

        panelBasisinformationen.setName("panelBasisinformationen"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        comboSparte.setEditable(true);
        comboSparte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboSparte.setName("comboSparte"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        comboGesellschaft.setEditable(true);
        comboGesellschaft.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboGesellschaft.setName("comboGesellschaft"); // NOI18N
        comboGesellschaft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGesellschaftActionPerformed(evt);
            }
        });

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        comboProdukt.setEditable(true);
        comboProdukt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wählen Sie zuerst eine Gesellschaft" }));
        comboProdukt.setName("comboProdukt"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        comboVertrag.setEditable(true);
        comboVertrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Verträge zum Kunden vorhanden" }));
        comboVertrag.setName("comboVertrag"); // NOI18N

        jLabel9.setForeground(resourceMap.getColor("jLabel9.foreground")); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setIcon(resourceMap.getIcon("jLabel9.icon")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        comboBeratungsverzichtArt.setEditable(true);
        comboBeratungsverzichtArt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Auf eine Beratung und Dokumentation wird ausdrücklich verzichtet.", "Auf eine Dokumentation wird ausdrücklich verzichtet.", "Auf eine Beratung wird ausdrücklich verzichtet." }));
        comboBeratungsverzichtArt.setName("comboBeratungsverzichtArt"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        javax.swing.GroupLayout panelBasisinformationenLayout = new javax.swing.GroupLayout(panelBasisinformationen);
        panelBasisinformationen.setLayout(panelBasisinformationenLayout);
        panelBasisinformationenLayout.setHorizontalGroup(
            panelBasisinformationenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBasisinformationenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBasisinformationenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(comboVertrag, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSparte, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(comboGesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(comboProdukt, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                    .addComponent(comboBeratungsverzichtArt, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelBasisinformationenLayout.setVerticalGroup(
            panelBasisinformationenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBasisinformationenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboVertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboSparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboGesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboProdukt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBeratungsverzichtArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        paneBp.addTab(resourceMap.getString("panelBasisinformationen.TabConstraints.tabTitle"), panelBasisinformationen); // NOI18N

        panelDokumentation.setName("panelDokumentation"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        fieldKundenwuensche.setText(resourceMap.getString("fieldKundenwuensche.text")); // NOI18N
        fieldKundenwuensche.setName("fieldKundenwuensche"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        fieldKundenbedarf.setName("fieldKundenbedarf"); // NOI18N

        btnMaxBedarf.setIcon(resourceMap.getIcon("btnMaxBedarf.icon")); // NOI18N
        btnMaxBedarf.setText(resourceMap.getString("btnMaxBedarf.text")); // NOI18N
        btnMaxBedarf.setToolTipText(resourceMap.getString("btnMaxBedarf.toolTipText")); // NOI18N
        btnMaxBedarf.setMinimumSize(new java.awt.Dimension(30, 25));
        btnMaxBedarf.setName("btnMaxBedarf"); // NOI18N
        btnMaxBedarf.setPreferredSize(new java.awt.Dimension(30, 25));
        btnMaxBedarf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxBedarfActionPerformed(evt);
            }
        });

        btnMaxWuensche.setIcon(resourceMap.getIcon("btnMaxWuensche.icon")); // NOI18N
        btnMaxWuensche.setToolTipText(resourceMap.getString("btnMaxWuensche.toolTipText")); // NOI18N
        btnMaxWuensche.setMaximumSize(new java.awt.Dimension(30, 25));
        btnMaxWuensche.setName("btnMaxWuensche"); // NOI18N
        btnMaxWuensche.setPreferredSize(new java.awt.Dimension(30, 25));
        btnMaxWuensche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxWuenscheActionPerformed(evt);
            }
        });

        btnTbWuensche.setIcon(resourceMap.getIcon("btnTbWuensche.icon")); // NOI18N
        btnTbWuensche.setToolTipText(resourceMap.getString("btnTbWuensche.toolTipText")); // NOI18N
        btnTbWuensche.setMinimumSize(new java.awt.Dimension(30, 25));
        btnTbWuensche.setName("btnTbWuensche"); // NOI18N
        btnTbWuensche.setPreferredSize(new java.awt.Dimension(30, 25));
        btnTbWuensche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbWuenscheActionPerformed(evt);
            }
        });

        btnTbBedarf.setIcon(resourceMap.getIcon("btnTbBedarf.icon")); // NOI18N
        btnTbBedarf.setToolTipText(resourceMap.getString("btnTbBedarf.toolTipText")); // NOI18N
        btnTbBedarf.setMinimumSize(new java.awt.Dimension(30, 25));
        btnTbBedarf.setName("btnTbBedarf"); // NOI18N
        btnTbBedarf.setPreferredSize(new java.awt.Dimension(30, 25));
        btnTbBedarf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbBedarfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(fieldKundenwuensche, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMaxWuensche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(fieldKundenbedarf, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMaxBedarf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTbBedarf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTbWuensche, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMaxWuensche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldKundenwuensche, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTbWuensche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldKundenbedarf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnMaxBedarf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTbBedarf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_rat.setColumns(20);
        text_rat.setRows(5);
        text_rat.setName("text_rat"); // NOI18N
        jScrollPane1.setViewportView(text_rat);

        btnMaxRat.setIcon(resourceMap.getIcon("btnMaxRat.icon")); // NOI18N
        btnMaxRat.setToolTipText(resourceMap.getString("btnMaxRat.toolTipText")); // NOI18N
        btnMaxRat.setMinimumSize(new java.awt.Dimension(30, 25));
        btnMaxRat.setName("btnMaxRat"); // NOI18N
        btnMaxRat.setPreferredSize(new java.awt.Dimension(30, 25));
        btnMaxRat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxRatActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        text_entscheidung.setColumns(20);
        text_entscheidung.setRows(5);
        text_entscheidung.setName("text_entscheidung"); // NOI18N
        jScrollPane2.setViewportView(text_entscheidung);

        btnMaxEntscheidung.setIcon(resourceMap.getIcon("btnMaxEntscheidung.icon")); // NOI18N
        btnMaxEntscheidung.setToolTipText(resourceMap.getString("btnMaxEntscheidung.toolTipText")); // NOI18N
        btnMaxEntscheidung.setMinimumSize(new java.awt.Dimension(30, 25));
        btnMaxEntscheidung.setName("btnMaxEntscheidung"); // NOI18N
        btnMaxEntscheidung.setPreferredSize(new java.awt.Dimension(30, 25));
        btnMaxEntscheidung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxEntscheidungActionPerformed(evt);
            }
        });

        btnTbRat.setIcon(resourceMap.getIcon("btnTbRat.icon")); // NOI18N
        btnTbRat.setToolTipText(resourceMap.getString("btnTbRat.toolTipText")); // NOI18N
        btnTbRat.setMinimumSize(new java.awt.Dimension(30, 25));
        btnTbRat.setName("btnTbRat"); // NOI18N
        btnTbRat.setPreferredSize(new java.awt.Dimension(30, 25));
        btnTbRat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbRatActionPerformed(evt);
            }
        });

        btnTbEntscheidung.setIcon(resourceMap.getIcon("btnTbEntscheidung.icon")); // NOI18N
        btnTbEntscheidung.setToolTipText(resourceMap.getString("btnTbEntscheidung.toolTipText")); // NOI18N
        btnTbEntscheidung.setMinimumSize(new java.awt.Dimension(30, 25));
        btnTbEntscheidung.setName("btnTbEntscheidung"); // NOI18N
        btnTbEntscheidung.setPreferredSize(new java.awt.Dimension(30, 25));
        btnTbEntscheidung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbEntscheidungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxRat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnTbRat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxEntscheidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnTbEntscheidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnMaxRat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTbRat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTbEntscheidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaxEntscheidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        text_bemerkungen.setColumns(20);
        text_bemerkungen.setRows(5);
        text_bemerkungen.setName("text_bemerkungen"); // NOI18N
        jScrollPane6.setViewportView(text_bemerkungen);

        btnMaxBemerkungen.setIcon(resourceMap.getIcon("btnMaxBemerkungen.icon")); // NOI18N
        btnMaxBemerkungen.setToolTipText(resourceMap.getString("btnMaxBemerkungen.toolTipText")); // NOI18N
        btnMaxBemerkungen.setMinimumSize(new java.awt.Dimension(30, 25));
        btnMaxBemerkungen.setName("btnMaxBemerkungen"); // NOI18N
        btnMaxBemerkungen.setPreferredSize(new java.awt.Dimension(30, 25));
        btnMaxBemerkungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxBemerkungenActionPerformed(evt);
            }
        });

        btnTbBemerkungen.setIcon(resourceMap.getIcon("btnTbBemerkungen.icon")); // NOI18N
        btnTbBemerkungen.setToolTipText(resourceMap.getString("btnTbBemerkungen.toolTipText")); // NOI18N
        btnTbBemerkungen.setMinimumSize(new java.awt.Dimension(30, 25));
        btnTbBemerkungen.setName("btnTbBemerkungen"); // NOI18N
        btnTbBemerkungen.setPreferredSize(new java.awt.Dimension(30, 25));
        btnTbBemerkungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbBemerkungenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMaxBemerkungen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnTbBemerkungen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxBemerkungen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTbBemerkungen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setName("jPanel9"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        combo_markt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Der Versicherungsmakler stützt seinen Rat auf eine objektive, ausgewogene Marktuntersuchung.", "Der Versicherungsmakler stützt seinen Rat nicht auf eine objektive, ausgewogene Marktuntersuchung." }));
        combo_markt.setName("combo_markt"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(combo_markt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_markt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDokumentationLayout = new javax.swing.GroupLayout(panelDokumentation);
        panelDokumentation.setLayout(panelDokumentationLayout);
        panelDokumentationLayout.setHorizontalGroup(
            panelDokumentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelDokumentationLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelDokumentationLayout.setVerticalGroup(
            panelDokumentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDokumentationLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        paneBp.addTab(resourceMap.getString("panelDokumentation.TabConstraints.tabTitle"), panelDokumentation); // NOI18N

        panelRisiko.setName("panelRisiko"); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setName("jPanel6"); // NOI18N

        jRadioButton1.setSelected(true);
        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N

        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRadioButton1)
                .addComponent(jRadioButton2))
        );

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        combo_which.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Allgemein", "Risikoanalyse", "Bank" }));
        combo_which.setName("combo_which"); // NOI18N
        combo_which.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_whichActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        btnLeft.setIcon(resourceMap.getIcon("btnLeft.icon")); // NOI18N
        btnLeft.setName("btnLeft"); // NOI18N
        btnLeft.setPreferredSize(new java.awt.Dimension(30, 30));
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setIcon(resourceMap.getIcon("btnRight.icon")); // NOI18N
        btnRight.setName("btnRight"); // NOI18N
        btnRight.setPreferredSize(new java.awt.Dimension(30, 30));
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        text_dokbeschreibung.setColumns(20);
        text_dokbeschreibung.setEditable(false);
        text_dokbeschreibung.setLineWrap(true);
        text_dokbeschreibung.setRows(5);
        text_dokbeschreibung.setText(resourceMap.getString("text_dokbeschreibung.text")); // NOI18N
        text_dokbeschreibung.setWrapStyleWord(true);
        text_dokbeschreibung.setName("text_dokbeschreibung"); // NOI18N
        jScrollPane8.setViewportView(text_dokbeschreibung);

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        list_active.setName("list_active"); // NOI18N
        this.loadActiveDokumente();
        jScrollPane4.setViewportView(list_active);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        list_inactive.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_inactive.setName("list_inactive"); // NOI18N
        list_inactive.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_inactiveValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(list_inactive);
        this.loadWissensDokumente();

        javax.swing.GroupLayout panelRisikoLayout = new javax.swing.GroupLayout(panelRisiko);
        panelRisiko.setLayout(panelRisikoLayout);
        panelRisikoLayout.setHorizontalGroup(
            panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelRisikoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRisikoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_which, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRisikoLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(221, 221, 221))
                    .addGroup(panelRisikoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(panelRisikoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelRisikoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(582, Short.MAX_VALUE))
        );
        panelRisikoLayout.setVerticalGroup(
            panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRisikoLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(combo_which, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRisikoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addGroup(panelRisikoLayout.createSequentialGroup()
                        .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        paneBp.addTab(resourceMap.getString("panelRisiko.TabConstraints.tabTitle"), panelRisiko); // NOI18N

        panelFinish.setName("panelFinish"); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setName("jPanel7"); // NOI18N

        check_anschreiben.setSelected(true);
        check_anschreiben.setText(resourceMap.getString("check_anschreiben.text")); // NOI18N
        check_anschreiben.setName("check_anschreiben"); // NOI18N

        check_dokumentation.setSelected(true);
        check_dokumentation.setText(resourceMap.getString("check_dokumentation.text")); // NOI18N
        check_dokumentation.setName("check_dokumentation"); // NOI18N

        check_verzicht.setText(resourceMap.getString("check_verzicht.text")); // NOI18N
        check_verzicht.setName("check_verzicht"); // NOI18N

        check_druckstuecke.setSelected(true);
        check_druckstuecke.setText(resourceMap.getString("check_druckstuecke.text")); // NOI18N
        check_druckstuecke.setName("check_druckstuecke"); // NOI18N

        btnPrint.setIcon(resourceMap.getIcon("btnPrint.icon")); // NOI18N
        btnPrint.setText(resourceMap.getString("btnPrint.text")); // NOI18N
        btnPrint.setName("btnPrint"); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        check_pflichten.setSelected(true);
        check_pflichten.setText(resourceMap.getString("check_pflichten.text")); // NOI18N
        check_pflichten.setName("check_pflichten"); // NOI18N

        btnExport.setIcon(resourceMap.getIcon("btnExport.icon")); // NOI18N
        btnExport.setText(resourceMap.getString("btnExport.text")); // NOI18N
        btnExport.setName("btnExport"); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        date_wiedervorlage.setName("date_wiedervorlage"); // NOI18N

        check_savekundenordner.setSelected(true);
        check_savekundenordner.setText(resourceMap.getString("check_savekundenordner.text")); // NOI18N
        check_savekundenordner.setName("check_savekundenordner"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(check_anschreiben)
                    .addComponent(check_dokumentation)
                    .addComponent(check_verzicht)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(check_druckstuecke)
                    .addComponent(check_pflichten, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date_wiedervorlage, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(check_savekundenordner))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check_anschreiben)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_dokumentation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_verzicht)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_pflichten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_druckstuecke)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_savekundenordner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(date_wiedervorlage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint)
                    .addComponent(btnExport))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setName("jPanel8"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        btnGrpExport.add(radio_exportzip);
        radio_exportzip.setSelected(true);
        radio_exportzip.setText(resourceMap.getString("radio_exportzip.text")); // NOI18N
        radio_exportzip.setName("radio_exportzip"); // NOI18N

        btnGrpExport.add(radio_exportword);
        radio_exportword.setText(resourceMap.getString("radio_exportword.text")); // NOI18N
        radio_exportword.setName("radio_exportword"); // NOI18N

        field_exportZiel.setText(resourceMap.getString("field_exportZiel.text")); // NOI18N
        field_exportZiel.setName("field_exportZiel"); // NOI18N
        field_exportZiel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                field_exportZielMouseClicked(evt);
            }
        });

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        btnFileopen.setIcon(resourceMap.getIcon("btnFileopen.icon")); // NOI18N
        btnFileopen.setToolTipText(resourceMap.getString("btnFileopen.toolTipText")); // NOI18N
        btnFileopen.setName("btnFileopen"); // NOI18N
        btnFileopen.setPreferredSize(new java.awt.Dimension(30, 25));
        btnFileopen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileopenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(field_exportZiel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFileopen, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addComponent(jLabel5)
                    .addComponent(jLabel17)
                    .addComponent(radio_exportzip)
                    .addComponent(radio_exportword))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnFileopen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(field_exportZiel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radio_exportzip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radio_exportword)
                        .addContainerGap(146, Short.MAX_VALUE))))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setName("jPanel10"); // NOI18N

        btnMaxAnschreiben.setIcon(resourceMap.getIcon("btnMaxAnschreiben.icon")); // NOI18N
        btnMaxAnschreiben.setToolTipText(resourceMap.getString("btnMaxAnschreiben.toolTipText")); // NOI18N
        btnMaxAnschreiben.setName("btnMaxAnschreiben"); // NOI18N
        btnMaxAnschreiben.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxAnschreibenActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        text_anschreiben.setColumns(20);
        text_anschreiben.setRows(5);
        text_anschreiben.setName("text_anschreiben"); // NOI18N
        generateAnschreiben();
        jScrollPane5.setViewportView(text_anschreiben);

        btnTbAnschreiben.setIcon(resourceMap.getIcon("btnTbAnschreiben.icon")); // NOI18N
        btnTbAnschreiben.setToolTipText(resourceMap.getString("btnTbAnschreiben.toolTipText")); // NOI18N
        btnTbAnschreiben.setName("btnTbAnschreiben"); // NOI18N
        btnTbAnschreiben.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTbAnschreibenActionPerformed(evt);
            }
        });

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTbAnschreiben, 0, 0, Short.MAX_VALUE)
                    .addComponent(btnMaxAnschreiben, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnMaxAnschreiben, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTbAnschreiben))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelFinishLayout = new javax.swing.GroupLayout(panelFinish);
        panelFinish.setLayout(panelFinishLayout);
        panelFinishLayout.setHorizontalGroup(
            panelFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFinishLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelFinishLayout.setVerticalGroup(
            panelFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFinishLayout.createSequentialGroup()
                .addGroup(panelFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneBp.addTab(resourceMap.getString("panelFinish.TabConstraints.tabTitle"), panelFinish); // NOI18N

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(90, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(438, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paneBp, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(573, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(paneBp, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(51, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnMaxWuenscheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxWuenscheActionPerformed
        MaximizeHelper.openMax(this.fieldKundenwuensche, "Kundenwünsche");
    }//GEN-LAST:event_btnMaxWuenscheActionPerformed

    private void btnMaxBedarfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxBedarfActionPerformed
        MaximizeHelper.openMax(this.fieldKundenbedarf, "Kundenbedarf");
    }//GEN-LAST:event_btnMaxBedarfActionPerformed

    private void btnMaxRatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxRatActionPerformed
        MaximizeHelper.openMax(this.text_rat, "Rat an den Kunden mit Begründung");
    }//GEN-LAST:event_btnMaxRatActionPerformed

    private void btnMaxEntscheidungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxEntscheidungActionPerformed
        MaximizeHelper.openMax(this.text_entscheidung, "Kundenentscheidung");
    }//GEN-LAST:event_btnMaxEntscheidungActionPerformed

    private void btnMaxAnschreibenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxAnschreibenActionPerformed
        MaximizeHelper.openMax(this.text_anschreiben, "Kundenanschreiben");
    }//GEN-LAST:event_btnMaxAnschreibenActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        BeratungsprotokollHelper.open = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        BeratungsprotokollHelper.open = false;
        protokoll = getBeratungsprotokoll();

        try {
            if (update == false) {
                int id = BeratungsprotokollSQLMethods.insertIntoberatungsprotokolle(DatabaseConnection.open(), protokoll);
                protokoll.setId(id);
            } else {
                BeratungsprotokollSQLMethods.updateberatungsprotokolle(DatabaseConnection.open(), protokoll);
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte das Beratungsprotokoll für den "
                    + "Kunden mit der Kennung \"" + protokoll.getKundenKennung() + "\" nicht speichern", e);
            ShowException.showException("Datenbankfehler: Das Beratungsprotokoll konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Protokoll nicht speichern");
        }

        this.dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BeratungsprotokollHelper.open = false;
    }//GEN-LAST:event_formWindowClosing

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        saveBeratungsprotokoll();
        SimpleDateFormat df = new SimpleDateFormat("ddMMhhmmss");
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yy hh:mm");

        int versId = -1;
        int produktId = -1;
        int vertragId = -1;

        try {
            VersichererObj vs = (VersichererObj) this.comboGesellschaft.getSelectedItem();
            versId = vs.getId();
        } catch (Exception e) {
        }

        try {
            ProduktObj prod = (ProduktObj) this.comboProdukt.getSelectedItem();
            produktId = prod.getId();
        } catch (Exception e) {
        }

        try {
            VertragObj vtr = (VertragObj) this.comboVertrag.getSelectedItem();
            vertragId = vtr.getId();
        } catch (Exception e) {
        }

        try {
            if (this.check_anschreiben.isSelected()) {
                String file = Filesystem.getTmpPath() + File.separatorChar + "bpa_" + kdnr + "_"
                        + df.format(new Date(System.currentTimeMillis())) + ".doc";

                System.out.println("File: " + file);
                if (type == PRIVAT) {
                    ExportBeratungsAnschreiben exp = new ExportBeratungsAnschreiben(file, kunde, protokoll,
                            "Unterlagen zur Beratungsdokumentation");

                    exp.write();
                } else if (type == PRIVAT) {
                    ExportBeratungsAnschreiben exp = new ExportBeratungsAnschreiben(file, fakunde, protokoll,
                            "Unterlagen zur Beratungsdokumentation");

                    exp.write();
                }

                File print = new File(file);

//                if(desktop.isPrintable(file))
                try {

                    // Anschreiben im Kundenordner speichern 
                    if (this.check_savekundenordner.isSelected()) {
                        String bpkf = "beratungsprotokoll_anschreiben_" + kdnr + "_"
                                + df.format(new Date(System.currentTimeMillis())) + ".doc";
                        String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                                + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                                + bpkf;

                        new FileCopy().copy(print, new File(bpka));

                        DokumentenObj dok = new DokumentenObj();
                        dok.setCreatorId(BasicRegistry.currentUser.getId());
                        dok.setBenutzerId(BasicRegistry.currentUser.getId());
                        dok.setKundenKennung(kdnr);
                        dok.setBpId(protokoll.getId());
                        dok.setVersichererId(versId);
                        dok.setProduktId(produktId);
                        dok.setVertragId(vertragId);
                        dok.setFiletype(FileTypes.WXML);
                        dok.setName(bpkf);
                        dok.setFullPath(bpka);
                        dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setStatus(Status.NORMAL);
                        long checksum = FileTools.getChecksum(new File(bpka).getPath());
                        dok.setChecksum(String.valueOf(checksum));
                        dok.setBeschreibung("Beratungsprotokoll Anschreiben " + df2.format(System.currentTimeMillis()));
                        dok.setViewcount(0);

                        DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                    }

                    desktop.print(print);
                    print.deleteOnExit();
                } catch (java.lang.UnsupportedOperationException e) {
                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                    if (answer == JOptionPane.OK_OPTION) {
                        desktop.open(print);
                    } else {
                        print.deleteOnExit();
                    }
                }
            }

            if (this.check_dokumentation.isSelected()) {
                String dokufile = Filesystem.getTmpPath() + File.separatorChar + "doku"
                        + df.format(new Date(System.currentTimeMillis())) + ".doc";

                String betr = "Beratungsdokumentation";

                if (type == PRIVAT) {
                    ExportBeratungsDokumentation exp = new ExportBeratungsDokumentation(dokufile, BasicRegistry.currentMandant,
                            kunde, BasicRegistry.currentUser, protokoll, betr);

                    exp.write();
                } else if (type == FIRMA) {
                    ExportBeratungsDokumentation exp = new ExportBeratungsDokumentation(dokufile, BasicRegistry.currentMandant,
                            fakunde, BasicRegistry.currentUser, protokoll, betr);

                    exp.write();
                }
                File docfile = new File(dokufile);
                try {

                    if (this.check_savekundenordner.isSelected()) {
                        String bpkf = "beratungsprotokoll_dokumentation_" + kdnr + "_"
                                + df.format(new Date(System.currentTimeMillis())) + ".doc";
                        String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                                + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                                + bpkf;

                        new FileCopy().copy(docfile, new File(bpka));

                        DokumentenObj dok = new DokumentenObj();
                        dok.setCreatorId(BasicRegistry.currentUser.getId());
                        dok.setBenutzerId(BasicRegistry.currentUser.getId());
                        dok.setKundenKennung(kdnr);
                        dok.setBpId(protokoll.getId());
                        dok.setVersichererId(versId);
                        dok.setProduktId(produktId);
                        dok.setVertragId(vertragId);
                        dok.setFiletype(FileTypes.WXML);
                        dok.setName(bpkf);
                        dok.setFullPath(bpka);
                        dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setStatus(Status.NORMAL);
                        long checksum = FileTools.getChecksum(new File(bpka).getPath());
                        dok.setChecksum(String.valueOf(checksum));
                        dok.setBeschreibung("Beratungsprotokoll Dokumentation " + df2.format(System.currentTimeMillis()));
                        dok.setViewcount(0);

                        DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                    }
                    desktop.print(docfile);
                    docfile.deleteOnExit();
                } catch (java.lang.UnsupportedOperationException e) {
                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                    if (answer == JOptionPane.OK_OPTION) {
                        desktop.open(docfile);
                    } else {
                        docfile.deleteOnExit();
                    }
                }
            }

            if (this.check_verzicht.isSelected()) {
                String vzfile = Filesystem.getTmpPath() + File.separatorChar + "verz"
                        + df.format(new Date(System.currentTimeMillis())) + ".doc";

                String betr = null;

                if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase("Auf eine Beratung und Dokumentation wird ausdrücklich verzichtet.")) {
                    betr = "Beratungs- und Dokumentationsverzicht";
                } else if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase("Auf eine Dokumentation wird ausdrücklich verzichtet.")) {
                    betr = "Dokumentationsverzicht";
                } else if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase("Auf eine Beratung wird ausdrücklich verzichtet.")) {
                    betr = "Beratungsverzicht";
                } else {
                    betr = protokoll.getBeratungsVerzichtArt();
                }

                if (type == PRIVAT) {
                    ExportBeratungsVerzicht verz = new ExportBeratungsVerzicht(vzfile, kunde,
                            BasicRegistry.currentMandant, BasicRegistry.currentUser, protokoll, betr);

                    verz.write();
                } else if (type == FIRMA) {
                    ExportBeratungsVerzicht verz = new ExportBeratungsVerzicht(vzfile, fakunde,
                            BasicRegistry.currentMandant, BasicRegistry.currentUser, protokoll, betr);

                    verz.write();
                }

                File verzprint = new File(vzfile);
                try {
                    if (this.check_savekundenordner.isSelected()) {
                        String bpkf = "beratungsprotokoll_verzicht_" + kdnr + "_"
                                + df.format(new Date(System.currentTimeMillis())) + ".doc";
                        String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                                + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                                + bpkf;

                        new FileCopy().copy(verzprint, new File(bpka));

                        DokumentenObj dok = new DokumentenObj();
                        dok.setCreatorId(BasicRegistry.currentUser.getId());
                        dok.setBenutzerId(BasicRegistry.currentUser.getId());
                        dok.setKundenKennung(kdnr);
                        dok.setBpId(protokoll.getId());
                        dok.setVersichererId(versId);
                        dok.setProduktId(produktId);
                        dok.setVertragId(vertragId);
                        dok.setFiletype(FileTypes.WXML);
                        dok.setName(bpkf);
                        dok.setFullPath(bpka);
                        dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setStatus(Status.NORMAL);
                        long checksum = FileTools.getChecksum(new File(bpka).getPath());
                        dok.setChecksum(String.valueOf(checksum));
                        dok.setBeschreibung("Beratungsprotokoll Verzicht " + df2.format(System.currentTimeMillis()));
                        dok.setViewcount(0);

                        DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                    }
                    desktop.print(verzprint);
                    verzprint.deleteOnExit();
                } catch (java.lang.UnsupportedOperationException e) {
                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                    if (answer == JOptionPane.OK_OPTION) {
                        desktop.open(verzprint);
                    } else {
                        verzprint.deleteOnExit();
                    }
                }
            }

            if (this.check_pflichten.isSelected()) {
                String kiffile = Filesystem.getTmpPath() + File.separatorChar + "info"
                        + df.format(new Date(System.currentTimeMillis())) + ".doc";

                ExportKundenInformationen kif = new ExportKundenInformationen(kiffile, BasicRegistry.currentMandant);
                kif.write();

                File kifprint = new File(kiffile);
                try {
                    if (this.check_savekundenordner.isSelected()) {
                        String bpkf = "beratungsprotokoll_informationen_" + kdnr + "_"
                                + df.format(new Date(System.currentTimeMillis())) + ".doc";
                        String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                                + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                                + bpkf;

                        new FileCopy().copy(kifprint, new File(bpka));

                        DokumentenObj dok = new DokumentenObj();
                        dok.setCreatorId(BasicRegistry.currentUser.getId());
                        dok.setBenutzerId(BasicRegistry.currentUser.getId());
                        dok.setKundenKennung(kdnr);
                        dok.setBpId(protokoll.getId());
                        dok.setVersichererId(versId);
                        dok.setProduktId(produktId);
                        dok.setVertragId(vertragId);
                        dok.setFiletype(FileTypes.WXML);
                        dok.setName(bpkf);
                        dok.setFullPath(bpka);
                        dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        dok.setStatus(Status.NORMAL);
                        long checksum = FileTools.getChecksum(new File(bpka).getPath());
                        dok.setChecksum(String.valueOf(checksum));
                        dok.setBeschreibung("Beratungsprotokoll Kundeninformationen "
                                + df2.format(System.currentTimeMillis()));
                        dok.setViewcount(0);

                        DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                    }
                    desktop.print(kifprint);
                    kifprint.deleteOnExit();
                } catch (java.lang.UnsupportedOperationException e) {
                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                    if (answer == JOptionPane.OK_OPTION) {
                        desktop.open(kifprint);
                    } else {
                        kifprint.deleteOnExit();
                    }
                }
            }

            if (this.check_druckstuecke.isSelected()) {
                DefaultListModel activeM = (DefaultListModel) list_active.getModel();

                if (activeM.toArray().length != 0) {
                    for (int i = 0; i < activeM.toArray().length; i++) {
                        WissenDokumentenObj dokument = (WissenDokumentenObj) activeM.toArray()[i];
                        String filepath = Filesystem.getRootPath() + File.separatorChar + dokument.getFullPath();
                        File file = new File(filepath);

                        if (file.exists()) {
                            if (dokument.getTriggerClass() == null) {
                                try {
                                    desktop.print(file);
                                } catch (java.lang.UnsupportedOperationException e) {
                                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                                    if (answer == JOptionPane.OK_OPTION) {
                                        desktop.open(file);
                                    }
                                }
                            } else {
                                String filename = Filesystem.getTmpPath() + File.separatorChar
                                        + dokument.getName() + df.format(new Date(System.currentTimeMillis())) + ".doc";

                                if (type == PRIVAT) {
                                    DokumentenTrigger trigger = (DokumentenTrigger) dokument.getTriggerClass().newInstance();
                                    trigger.execute(filename, kunde);
                                } else if (type == FIRMA) {
                                    DokumentenTrigger trigger = (DokumentenTrigger) dokument.getTriggerClass().newInstance();
                                    trigger.execute(filename, fakunde);
                                }

                                File trigfile = new File(filename);
                                try {
                                    desktop.print(trigfile);
                                    trigfile.deleteOnExit();
                                } catch (java.lang.UnsupportedOperationException e) {
                                    int answer = JOptionPane.showConfirmDialog(null, "Das direkte Drucken wird von Ihrem Betriebssystem leider nicht unterstützt."
                                            + " Deshalb wird die Datei im vorgesehenen Programm geöffnet.", "Fehler: Kein direktes Drucken möglich.", JOptionPane.OK_CANCEL_OPTION);
                                    Log.logger.warn("Das Client Betriebssystem unterstützt die Drucken (desktop.print) Funktion nicht.", e);
                                    if (answer == JOptionPane.OK_OPTION) {
                                        desktop.open(trigfile);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte das Beratungsprotokoll für den "
                    + "Kunden mit der Id \"" + protokoll.getKundenKennung() + "\" nicht drucken", e);
            ShowException.showException("Konnte das Beratungsprtokoll nicht drucken.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Protokoll nicht drucken");
        }

    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnMaxBemerkungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxBemerkungenActionPerformed
        MaximizeHelper.openMax(this.text_bemerkungen, "Bemerkungen");
    }//GEN-LAST:event_btnMaxBemerkungenActionPerformed

//    
    private void combo_whichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_whichActionPerformed
        if (combo_which.getSelectedIndex() == -1) {
            return;
        }

        if (loading == false) {
            this.loadDokumente((String) combo_which.getSelectedItem());
        }
    }//GEN-LAST:event_combo_whichActionPerformed

    private void list_inactiveValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_inactiveValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }

        if (list_inactive.getSelectedIndex() == -1) {
            return;
        }

        if (!list_inactive.getSelectedValue().toString().equalsIgnoreCase("keine dateien")) {
            WissenDokumentenObj dok = (WissenDokumentenObj) list_inactive.getSelectedValue();

            StringBuilder sb = new StringBuilder();

            sb.append("Name: ").append(dok.getName());
            sb.append("\n");
            String creator = "System";

            if (dok.getCreator() == -1) {
                creator = "MaklerPoint";
            } else {
                creator = BenutzerRegistry.getBenutzer(dok.getCreator(), false).toString();
            }

            sb.append("Ersteller: ").append(creator);
            sb.append("\n\n");
            sb.append(dok.getBeschreibung());


            this.text_dokbeschreibung.setText(sb.toString());
        }
    }//GEN-LAST:event_list_inactiveValueChanged

    private void field_exportZielMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_field_exportZielMouseClicked
        exportfile();
    }//GEN-LAST:event_field_exportZielMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed

        SimpleDateFormat df = new SimpleDateFormat("ddMMhhmmss");
        this.exportfilename = this.field_exportZiel.getText().trim();

        if (this.exportfilename == null || this.exportfilename.length() < 2) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Zielordner / eine Zieldatei für den Export aus.");
            return;
        }

        Log.logger.debug("Exportfilename: " + exportfilename);

        boolean zip = this.radio_exportzip.isSelected();

        if (zip) {
            if (new File(this.exportfilename).exists()) {
                int answer = JOptionPane.showConfirmDialog(null, "Eine Datei mit diesem Namen ist schon vorhanden. Soll Sie überschrieben werden?",
                        "Die Datei ist schon vorhanden", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.NO_OPTION) {
                    return;
                } else {
                    new File(this.exportfilename).delete();
                }
            }

            if (new File(this.exportfilename).isDirectory()) {
                exportfilename = exportfilename + File.separatorChar + "BeratungsProtokoll-"
                        + kunde.getNachname() + "_" + df.format(new Date(System.currentTimeMillis()));
            }
        } else {
            if (!new File(this.exportfilename).isDirectory()) {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie ein Verzeichnis (keine Datei) "
                        + "aus in das das Beratungsprotokoll exportiert werden sollen");
                return;
            }
        }

        saveBeratungsprotokoll();
        ArrayList<String> zipFiles = new ArrayList<String>();

        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yy hh:mm");

        int versId = -1;
        int produktId = -1;
        int vertragId = -1;

        try {
            VersichererObj vs = (VersichererObj) this.comboGesellschaft.getSelectedItem();
            versId = vs.getId();
        } catch (Exception e) {
        }

        try {
            ProduktObj prod = (ProduktObj) this.comboProdukt.getSelectedItem();
            produktId = prod.getId();
        } catch (Exception e) {
        }

        try {
            VertragObj vtr = (VertragObj) this.comboVertrag.getSelectedItem();
            vertragId = vtr.getId();
        } catch (Exception e) {
        }

        try {
            if (this.check_anschreiben.isSelected()) {

                String file = null;

                if (type == PRIVAT) {
                    if (zip) {
                        file = Filesystem.getTmpPath() + File.separatorChar + "bp-anschreiben-"
                                + kunde.getNachname() + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        file = this.exportfilename + File.separatorChar + "bp-anschreiben-"
                                + kunde.getNachname() + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }

                    ExportBeratungsAnschreiben exp = new ExportBeratungsAnschreiben(file, kunde, protokoll,
                            "Unterlagen zur Beratungsdokumentation");

                    exp.write();
                } else if (type == FIRMA) {
                    if (zip) {
                        file = Filesystem.getTmpPath() + File.separatorChar + "bp-anschreiben-"
                                + fakunde.getFirmenName() + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        file = this.exportfilename + File.separatorChar + "bp-anschreiben-"
                                + fakunde.getFirmenName() + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }

                    ExportBeratungsAnschreiben exp = new ExportBeratungsAnschreiben(file, fakunde, protokoll,
                            "Unterlagen zur Beratungsdokumentation");

                    exp.write();
                }

                if (this.check_savekundenordner.isSelected()) {
                    String bpkf = "beratungsprotokoll_anschreiben_" + kdnr + "_"
                            + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                            + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                            + bpkf;

                    new FileCopy().copy(new File(file), new File(bpka));

                    DokumentenObj dok = new DokumentenObj();
                    dok.setCreatorId(BasicRegistry.currentUser.getId());
                    dok.setBenutzerId(BasicRegistry.currentUser.getId());
                    dok.setKundenKennung(kdnr);
                    dok.setBpId(protokoll.getId());
                    dok.setVersichererId(versId);
                    dok.setProduktId(produktId);
                    dok.setVertragId(vertragId);
                    dok.setFiletype(FileTypes.WXML);
                    dok.setName(bpkf);
                    dok.setFullPath(bpka);
                    dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setStatus(Status.NORMAL);
                    long checksum = FileTools.getChecksum(new File(bpka).getPath());
                    dok.setChecksum(String.valueOf(checksum));
                    dok.setBeschreibung("Beratungsprotokoll Anschreiben "
                            + df2.format(System.currentTimeMillis()));
                    dok.setViewcount(0);

                    DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                }


                if (zip) {
                    zipFiles.add(file);
                    new File(file).deleteOnExit();
                }
            }

            if (this.check_dokumentation.isSelected()) {

                String dokufile = null;

                if (type == PRIVAT) {
                    if (zip) {
                        dokufile = Filesystem.getTmpPath() + File.separatorChar + "bp-dokumentation-" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        dokufile = this.exportfilename + File.separatorChar + "bp-dokumentation-" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                } else if (type == FIRMA) {
                    if (zip) {
                        dokufile = Filesystem.getTmpPath() + File.separatorChar + "bp-dokumentation-" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        dokufile = this.exportfilename + File.separatorChar + "bp-dokumentation-" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                }

                String betr = "Beratungsdokumentation";

                if (type == PRIVAT) {
                    ExportBeratungsDokumentation exp = new ExportBeratungsDokumentation(dokufile, BasicRegistry.currentMandant,
                            kunde, BasicRegistry.currentUser, protokoll, betr);

                    exp.write();
                } else if (type == FIRMA) {
                    ExportBeratungsDokumentation exp = new ExportBeratungsDokumentation(dokufile, BasicRegistry.currentMandant,
                            fakunde, BasicRegistry.currentUser, protokoll, betr);

                    exp.write();
                }

                if (this.check_savekundenordner.isSelected()) {
                    String bpkf = "beratungsprotokoll_dokumentation_" + kdnr + "_"
                            + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                            + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                            + bpkf;

                    new FileCopy().copy(new File(dokufile), new File(bpka));

                    DokumentenObj dok = new DokumentenObj();
                    dok.setCreatorId(BasicRegistry.currentUser.getId());
                    dok.setBenutzerId(BasicRegistry.currentUser.getId());
                    dok.setKundenKennung(kdnr);
                    dok.setBpId(protokoll.getId());
                    dok.setVersichererId(versId);
                    dok.setProduktId(produktId);
                    dok.setVertragId(vertragId);
                    dok.setFiletype(FileTypes.WXML);
                    dok.setName(bpkf);
                    dok.setFullPath(bpka);
                    dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setStatus(Status.NORMAL);
                    long checksum = FileTools.getChecksum(new File(bpka).getPath());
                    dok.setChecksum(String.valueOf(checksum));
                    dok.setBeschreibung("Beratungsprotokoll Dokumentation "
                            + df2.format(System.currentTimeMillis()));
                    dok.setViewcount(0);

                    DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                }

                if (zip) {
                    zipFiles.add(dokufile);
                    new File(dokufile).deleteOnExit();
                }
            }

            if (this.check_verzicht.isSelected()) {
                String vzfile = null;

                if (type == PRIVAT) {
                    if (zip) {
                        vzfile = Filesystem.getTmpPath() + File.separatorChar + "bp-verzicht-" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        vzfile = this.exportfilename + File.separatorChar + "bp-verzicht-" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                } else if (type == FIRMA) {
                    if (zip) {
                        vzfile = Filesystem.getTmpPath() + File.separatorChar + "bp-verzicht-" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        vzfile = this.exportfilename + File.separatorChar + "bp-verzicht-" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                }

                String betr = null;

                if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase(
                        "Auf eine Beratung und Dokumentation wird ausdrücklich verzichtet.")) {
                    betr = "Beratungs- und Dokumentationsverzicht";
                } else if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase(
                        "Auf eine Dokumentation wird ausdrücklich verzichtet.")) {
                    betr = "Dokumentationsverzicht";
                } else if (protokoll.getBeratungsVerzichtArt().equalsIgnoreCase(
                        "Auf eine Beratung wird ausdrücklich verzichtet.")) {
                    betr = "Beratungsverzicht";
                } else {
                    betr = protokoll.getBeratungsVerzichtArt();
                }

                if (type == PRIVAT) {
                    ExportBeratungsVerzicht verz = new ExportBeratungsVerzicht(vzfile, kunde,
                            BasicRegistry.currentMandant, BasicRegistry.currentUser, protokoll, betr);

                    verz.write();
                } else if (type == PRIVAT) {
                    ExportBeratungsVerzicht verz = new ExportBeratungsVerzicht(vzfile, fakunde,
                            BasicRegistry.currentMandant, BasicRegistry.currentUser, protokoll, betr);

                    verz.write();
                }

                if (this.check_savekundenordner.isSelected()) {
                    String bpkf = "beratungsprotokoll_verzicht_" + kdnr + "_"
                            + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                            + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                            + bpkf;

                    new FileCopy().copy(new File(vzfile), new File(bpka));

                    DokumentenObj dok = new DokumentenObj();
                    dok.setCreatorId(BasicRegistry.currentUser.getId());
                    dok.setBenutzerId(BasicRegistry.currentUser.getId());
                    dok.setKundenKennung(kdnr);
                    dok.setBpId(protokoll.getId());
                    dok.setVersichererId(versId);
                    dok.setProduktId(produktId);
                    dok.setVertragId(vertragId);
                    dok.setFiletype(FileTypes.WXML);
                    dok.setName(bpkf);
                    dok.setFullPath(bpka);
                    dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setStatus(Status.NORMAL);
                    long checksum = FileTools.getChecksum(new File(bpka).getPath());
                    dok.setChecksum(String.valueOf(checksum));
                    dok.setBeschreibung("Beratungsprotokoll Verzicht "
                            + df2.format(System.currentTimeMillis()));
                    dok.setViewcount(0);

                    DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                }

                if (zip) {
                    zipFiles.add(vzfile);
                    new File(vzfile).deleteOnExit();
                }
            }

            if (this.check_pflichten.isSelected()) {
                String kiffile = null;

                if (type == PRIVAT) {
                    if (zip) {
                        kiffile = Filesystem.getTmpPath() + File.separatorChar + "kundeninformationen_" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        kiffile = this.exportfilename + File.separatorChar + "kundeninformationen_" + kunde.getNachname()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                } else {
                    if (zip) {
                        kiffile = Filesystem.getTmpPath() + File.separatorChar + "kundeninformationen_" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    } else {
                        kiffile = this.exportfilename + File.separatorChar + "kundeninformationen_" + fakunde.getFirmenName()
                                + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    }
                }

                ExportKundenInformationen kif = new ExportKundenInformationen(kiffile, BasicRegistry.currentMandant);
                kif.write();

                if (this.check_savekundenordner.isSelected()) {
                    String bpkf = "beratungsprotokoll_informationen_" + kdnr + "_"
                            + df.format(new Date(System.currentTimeMillis())) + ".doc";
                    String bpka = FilesystemKunden.getKundenPath(kdnr) + File.separatorChar
                            + Config.get("bpOrdner", "beratungsprotokoll") + File.separatorChar
                            + bpkf;

                    new FileCopy().copy(new File(kiffile), new File(bpka));

                    DokumentenObj dok = new DokumentenObj();
                    dok.setCreatorId(BasicRegistry.currentUser.getId());
                    dok.setBenutzerId(BasicRegistry.currentUser.getId());
                    dok.setKundenKennung(kdnr);
                    dok.setBpId(protokoll.getId());
                    dok.setVersichererId(versId);
                    dok.setProduktId(produktId);
                    dok.setVertragId(vertragId);
                    dok.setFiletype(FileTypes.WXML);
                    dok.setName(bpkf);
                    dok.setFullPath(bpka);
                    dok.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                    dok.setStatus(Status.NORMAL);
                    long checksum = FileTools.getChecksum(new File(bpka).getPath());
                    dok.setChecksum(String.valueOf(checksum));
                    dok.setBeschreibung("Beratungsprotokoll Kundeninformationen "
                            + df2.format(System.currentTimeMillis()));
                    dok.setViewcount(0);

                    DokumentenSQLMethods.insertIntodokumente(DatabaseConnection.open(), dok);
                }

                if (zip) {
                    zipFiles.add(kiffile);
                    new File(kiffile).deleteOnExit();
                }
            }

            if (this.check_druckstuecke.isSelected()) {
                DefaultListModel activeM = (DefaultListModel) list_active.getModel();
                Log.logger.debug("Anzahl Druckstücke: " + activeM.toArray().length);

                if (activeM.toArray().length != 0) {
                    for (int i = 0; i < activeM.toArray().length; i++) {
                        WissenDokumentenObj dokument = (WissenDokumentenObj) activeM.toArray()[i];
                        String filepath = Filesystem.getRootPath() + File.separatorChar + dokument.getFullPath();
                        File file = new File(filepath);

                        if (file.exists()) {
                            if (dokument.getTriggerClass() == null) {
                                if (!zip) {
                                    FileCopy copy = new FileCopy();
                                    if (!new File(this.exportfilename + File.separatorChar + dokument.getFileName()).exists()) {
                                        copy.copy(file, new File(this.exportfilename
                                                + File.separatorChar + dokument.getFileName()));
                                    }
                                } else {
                                    zipFiles.add(filepath);
                                }
                            } else {
                                String filename = null;
                                if (zip) {
                                    filename = Filesystem.getTmpPath() + File.separatorChar
                                            + dokument.getName() + df.format(new Date(System.currentTimeMillis())) + ".doc";
                                } else {
                                    filename = this.exportfilename + File.separatorChar + dokument.getName() + "-" + kunde.getNachname()
                                            + "_" + df.format(new Date(System.currentTimeMillis())) + ".doc";
                                }
                                DokumentenTrigger trigger = (DokumentenTrigger) dokument.getTriggerClass().newInstance();
                                if (type == PRIVAT) {
                                    trigger.execute(filename, kunde);
                                } else if (type == FIRMA) {
                                    trigger.execute(filename, fakunde);
                                }

                                File trigfile = new File(filename);

                                if (zip) {
                                    zipFiles.add(filepath);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Das Dokument \"" + dokument.getName() + "\" wurde nicht gefunden.");
                            Log.logger.warn("Das Dokument \"" + dokument.getName() + "\" mit "
                                    + "der id " + dokument.getId() + " wurde nicht gefunden.");
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte das Beratungsprotokoll für den "
                    + "Kunden mit der Id \"" + protokoll.getKundenKennung() + "\" nicht exportieren", e);
            ShowException.showException("Konnte das Beratungsprotokoll für den "
                    + "Kunden mit der Id \"" + protokoll.getKundenKennung() + "\" nicht exportieren.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Protokoll nicht exportieren");
        }

        if (zip) {
            try {
                String[] filenames = new String[zipFiles.toArray().length];

                for (int i = 0; i < zipFiles.toArray().length; i++) {
                    filenames[i] = (String) zipFiles.toArray()[i];
                }

                ZipFiles.zipFiles(exportfilename, filenames);
                if (Config.getConfigBoolean("exportOpendocument", true)) {
                    desktop.open(new File(exportfilename));
                }

//                for(int i = 0; i < zipFiles.toArray().length; i++) {
//                    String filename = (String) zipFiles.toArray()[i];
//                    new File(filename).deleteOnExit();
//                }

            } catch (ArchiveException ex) {
                Log.logger.fatal("Fehler: Konnte das Beratungsprotokoll für den "
                        + "Kunden mit der Kennung \"" + protokoll.getKundenKennung() + "\" nicht zippen", ex);
                ShowException.showException("Konnte das Beratungsprtokoll für den "
                        + "Kunden mit der Kennung \"" + protokoll.getKundenKennung() + "\" nicht als Zip Datei speichern.",
                        ExceptionDialogGui.LEVEL_WARNING, ex,
                        "Schwerwiegend: Konnte Protokoll nicht zippen");

            } catch (Exception ex) {
                Log.logger.fatal("Fehler: Konnte das gezippte Beratungsprotokoll für den "
                        + "Kunden mit der Kennung \"" + protokoll.getKundenKennung() + "\" nicht öffnen", ex);
            }
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnTbWuenscheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbWuenscheActionPerformed
        TextbausteinDialogHelper.openTb(this.fieldKundenwuensche);
    }//GEN-LAST:event_btnTbWuenscheActionPerformed

    private void btnTbBedarfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbBedarfActionPerformed
        TextbausteinDialogHelper.openTb(this.fieldKundenbedarf);
    }//GEN-LAST:event_btnTbBedarfActionPerformed

    private void btnTbRatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbRatActionPerformed
        TextbausteinDialogHelper.openTb(this.text_rat);
    }//GEN-LAST:event_btnTbRatActionPerformed

    private void btnTbEntscheidungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbEntscheidungActionPerformed
        TextbausteinDialogHelper.openTb(this.text_entscheidung);
    }//GEN-LAST:event_btnTbEntscheidungActionPerformed

    private void btnTbBemerkungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbBemerkungenActionPerformed
        TextbausteinDialogHelper.openTb(this.text_bemerkungen);
    }//GEN-LAST:event_btnTbBemerkungenActionPerformed

    private void btnTbAnschreibenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTbAnschreibenActionPerformed
        TextbausteinDialogHelper.openTb(this.text_anschreiben);
    }//GEN-LAST:event_btnTbAnschreibenActionPerformed

    private void comboGesellschaftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGesellschaftActionPerformed

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                int vsid = -1;

                try {
                    VersichererObj vs = (VersichererObj) comboGesellschaft.getSelectedItem();
                    vsid = vs.getId();
                } catch (Exception e) {
                }

                loadProdukte(vsid);

            }
        });
    }//GEN-LAST:event_comboGesellschaftActionPerformed

    private void btnFileopenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileopenActionPerformed
        exportfile();
    }//GEN-LAST:event_btnFileopenActionPerformed

    private void onClose() {
        BeratungsprotokollHelper.open = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BeratungsprotokollDialog dialog = new BeratungsprotokollDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup btnDokumente;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnFileopen;
    private javax.swing.ButtonGroup btnGrpExport;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnMaxAnschreiben;
    private javax.swing.JButton btnMaxBedarf;
    private javax.swing.JButton btnMaxBemerkungen;
    private javax.swing.JButton btnMaxEntscheidung;
    private javax.swing.JButton btnMaxRat;
    private javax.swing.JButton btnMaxWuensche;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTbAnschreiben;
    private javax.swing.JButton btnTbBedarf;
    private javax.swing.JButton btnTbBemerkungen;
    private javax.swing.JButton btnTbEntscheidung;
    private javax.swing.JButton btnTbRat;
    private javax.swing.JButton btnTbWuensche;
    private javax.swing.JCheckBox check_anschreiben;
    private javax.swing.JCheckBox check_dokumentation;
    private javax.swing.JCheckBox check_druckstuecke;
    private javax.swing.JCheckBox check_pflichten;
    private javax.swing.JCheckBox check_savekundenordner;
    private javax.swing.JCheckBox check_verzicht;
    private javax.swing.JComboBox comboBeratungsverzichtArt;
    private javax.swing.JComboBox comboGesellschaft;
    private javax.swing.JComboBox comboProdukt;
    private javax.swing.JComboBox comboSparte;
    private javax.swing.JComboBox comboVertrag;
    private javax.swing.JComboBox combo_markt;
    private javax.swing.JComboBox combo_which;
    private org.jdesktop.swingx.JXDatePicker date_wiedervorlage;
    private javax.swing.JTextField fieldKundenbedarf;
    private javax.swing.JTextField fieldKundenwuensche;
    private javax.swing.JTextField field_exportZiel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList list_active;
    private javax.swing.JList list_inactive;
    private javax.swing.JTabbedPane paneBp;
    private javax.swing.JPanel panelBasisinformationen;
    private javax.swing.JPanel panelDokumentation;
    private javax.swing.JPanel panelFinish;
    private javax.swing.JPanel panelRisiko;
    private javax.swing.JRadioButton radio_exportword;
    private javax.swing.JRadioButton radio_exportzip;
    private javax.swing.JTextArea text_anschreiben;
    private javax.swing.JTextArea text_bemerkungen;
    private javax.swing.JTextArea text_dokbeschreibung;
    private javax.swing.JTextArea text_entscheidung;
    private javax.swing.JTextArea text_rat;
    // End of variables declaration//GEN-END:variables
}
