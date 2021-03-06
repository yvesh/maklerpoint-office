/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StoerfallDialog.java
 *
 * Created on 07.07.2011, 12:04:19
 */
package de.maklerpoint.office.Gui.Stoerfall;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemStoerfaelle;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Kalender.Aufgaben.Tools.AufgabenSQLMethods;
import de.maklerpoint.office.Konstanten.Stoerfaelle;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Stoerfalle.Tools.StoerfaelleHelper;
import de.maklerpoint.office.Stoerfalle.Tools.StoerfaelleSQLMethods;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class StoerfallDialog extends javax.swing.JDialog {

    private boolean update = false;
    private String kdnr = null;
    private VertragObj vtr = null;
    private StoerfallObj stoer = null;

    /** Creates new form StoerfallDialog */
    public StoerfallDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        this.vtr = null;
        this.kdnr = null;
        this.stoer = null;
        initComponents();
        setUp();
    }

    public StoerfallDialog(java.awt.Frame parent, boolean modal, String kdnr) {
        super(parent, modal);
        this.update = false;
        this.vtr = null;
        this.stoer = null;
        this.kdnr = kdnr;
        initComponents();
        setUp();
    }

    public StoerfallDialog(java.awt.Frame parent, boolean modal, VertragObj vtr) {
        super(parent, modal);
        this.update = false;
        this.vtr = vtr;
        this.stoer = null;
        this.kdnr = vtr.getKundenKennung();
        initComponents();
        setUp();
    }

    public StoerfallDialog(java.awt.Frame parent, boolean modal, StoerfallObj sch) {
        super(parent, modal);
        this.update = true;
        this.vtr = VertragRegistry.getVertrag(sch.getVertragsId());
        this.stoer = sch;
        this.kdnr = vtr.getKundenKennung();
        initComponents();
        setUp();
    }

    private void setUp() {
        loadCombos();
        this.ffield_rueckstand.setValue(0.00);
        this.ffield_frist.setValue(14);
        this.date_aufgabe.setDate(new Date());
        this.date_faelligkeit.setDate(new Date());
        this.date_eingang.setDate(new Date());
        this.spinnerStoerfallNr.setEnabled(true);

        if (this.stoer == null) {
            this.setTitle("Neuer Störfall");
        } else {
            this.setTitle("Störfall " + stoer.getStoerfallNr() + " ("
                    + KundenRegistry.getKunde(stoer.getKundenNr()).toString() + ")");
        }

        loadStoerfall();
    }

    private void loadCombos() {
        this.combo_bearbeiter.setModel(new DefaultComboBoxModel(ComboBoxGetter.getBenutzerCombo(null)));
        this.combo_bearbeiter.setSelectedItem(BasicRegistry.currentUser);

        Object[] kunden = ComboBoxGetter.getAlleKundenCombo(null, Status.NORMAL);
        this.combo_kunde.setModel(new DefaultComboBoxModel(kunden));

        try {
            if (stoer == null) {
                this.spinnerStoerfallNr.setValue(StoerfaelleHelper.getNextStoerfallnummer(
                        DatabaseConnection.open()));
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex); // TODO
            this.spinnerStoerfallNr.setValue(0);
            this.spinnerStoerfallNr.setEnabled(false);
        }

        this.combo_grund.setModel(new DefaultComboBoxModel(Stoerfaelle.GRUENDE));
        this.combo_kategorie.setModel(new DefaultComboBoxModel(Stoerfaelle.KATEGORIEN));
        this.combo_mahnstatus.setModel(new DefaultComboBoxModel(Stoerfaelle.MAHNSTATUS));

        if (kdnr != null) {
            this.combo_kunde.setSelectedItem(KundenRegistry.getKunde(kdnr));
            this.combo_vertrag.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getVertragCombo(null, kdnr)));
        } else {
            if (kunden != null) {
                if (kunden[0].getClass().equals(KundenObj.class)) {
                    KundenObj knd = (KundenObj) kunden[0];
                    this.combo_vertrag.setModel(new DefaultComboBoxModel(
                            ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));
                } else if (kunden[0].getClass().equals(FirmenObj.class)) {
                    FirmenObj knd = (FirmenObj) kunden[0];
                    this.combo_vertrag.setModel(new DefaultComboBoxModel(
                            ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));
                }
            }
        }
    }

    private void loadStoerfall() {
        if (stoer == null) {
            return;
        }

        this.area_comments.setText(stoer.getNotiz());

        if (stoer.getAufgabenId() != -1) {
            this.check_aufgabeanlegen.setSelected(true);
            try {
                AufgabenObj aufg = AufgabenSQLMethods.getAufgabe(DatabaseConnection.open(), stoer.getAufgabenId());
                this.date_aufgabe.setDate(aufg.getStart());
                this.date_aufgabe.setEnabled(true);                
            } catch (SQLException e) {
                Log.logger.fatal("Konnte die Aufgabe für den Störfall nicht laden", e);
                ShowException.showException("Beim laden der Aufgabe für den Störfall "
                        + "ist ein Fehler aufgetretten.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte die Aufgabe nicht laden");
            }
        }

        this.spinnerStoerfallNr.setValue(Integer.valueOf(stoer.getStoerfallNr()));
        this.spinnerStoerfallNr.setEnabled(false); // Nicht mehr änderbar

        this.combo_bearbeiter.setSelectedItem(BenutzerRegistry.getBenutzer(stoer.getBetreuerId()));
        this.combo_grund.setSelectedItem(stoer.getGrund());
        this.combo_kategorie.setSelectedItem(stoer.getKategorie());
        this.combo_kunde.setSelectedItem(KundenRegistry.getKunde(stoer.getKundenNr()));

        this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, stoer.getKundenNr())));

        this.combo_mahnstatus.setSelectedItem(stoer.getMahnstatus());
        this.combo_vertrag.setSelectedItem(VertragRegistry.getVertrag(stoer.getVertragsId()));

//        this.date_aufgabe.setDate(stoer.get); TODO

        this.date_eingang.setDate(stoer.getEingang());
        this.date_faelligkeit.setDate(stoer.getFaelligkeit());

        this.ffield_frist.setValue(stoer.getFristTage());
        this.ffield_rueckstand.setValue(stoer.getRueckstand());

        this.field_custom1.setText(stoer.getCustom1());
        this.field_custom2.setText(stoer.getCustom2());
        this.field_custom3.setText(stoer.getCustom3());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneschaeden = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_vertrag = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        combo_kunde = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        spinnerStoerfallNr = new javax.swing.JSpinner();
        jSeparator4 = new javax.swing.JSeparator();
        combo_bearbeiter = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        check_aufgabeanlegen = new javax.swing.JCheckBox();
        date_eingang = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        date_faelligkeit = new com.toedter.calendar.JDateChooser();
        jSeparator12 = new javax.swing.JSeparator();
        ffield_rueckstand = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        ffield_frist = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        combo_grund = new javax.swing.JComboBox();
        date_aufgabe = new com.toedter.calendar.JDateChooser();
        panel_sonstiges = new javax.swing.JPanel();
        jSeparator17 = new javax.swing.JSeparator();
        btnMaxCustom3 = new javax.swing.JButton();
        btnMaxCustom2 = new javax.swing.JButton();
        btnMaxCustom1 = new javax.swing.JButton();
        field_custom1 = new javax.swing.JTextField();
        field_custom2 = new javax.swing.JTextField();
        field_custom3 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        area_comments = new javax.swing.JTextArea();
        btnMaxComments3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        combo_kategorie = new javax.swing.JComboBox();
        combo_mahnstatus = new javax.swing.JComboBox();
        jSeparator18 = new javax.swing.JSeparator();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(StoerfallDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        paneschaeden.setName("paneschaeden"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_vertrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte wählen Sie zuerst einen Kunden" }));
        combo_vertrag.setName("combo_vertrag"); // NOI18N
        combo_vertrag.setPreferredSize(new java.awt.Dimension(134, 27));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_kunde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_kunde.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_kunde.setName("combo_kunde"); // NOI18N
        combo_kunde.setPreferredSize(new java.awt.Dimension(134, 27));
        combo_kunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kundeActionPerformed(evt);
            }
        });

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        spinnerStoerfallNr.setName("spinnerStoerfallNr"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        combo_bearbeiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Model" }));
        combo_bearbeiter.setName("combo_bearbeiter"); // NOI18N
        combo_bearbeiter.setPreferredSize(new java.awt.Dimension(134, 27));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        check_aufgabeanlegen.setText(resourceMap.getString("check_aufgabeanlegen.text")); // NOI18N
        check_aufgabeanlegen.setName("check_aufgabeanlegen"); // NOI18N
        check_aufgabeanlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_aufgabeanlegenActionPerformed(evt);
            }
        });

        date_eingang.setDateFormatString(resourceMap.getString("date_eingang.dateFormatString")); // NOI18N
        date_eingang.setMinimumSize(new java.awt.Dimension(38, 27));
        date_eingang.setName("date_eingang"); // NOI18N
        date_eingang.setPreferredSize(new java.awt.Dimension(144, 27));

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        date_faelligkeit.setDateFormatString(resourceMap.getString("date_faelligkeit.dateFormatString")); // NOI18N
        date_faelligkeit.setMinimumSize(new java.awt.Dimension(38, 27));
        date_faelligkeit.setName("date_faelligkeit"); // NOI18N
        date_faelligkeit.setPreferredSize(new java.awt.Dimension(144, 27));

        jSeparator12.setName("jSeparator12"); // NOI18N

        ffield_rueckstand.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_rueckstand.setName("ffield_rueckstand"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jSeparator13.setName("jSeparator13"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        ffield_frist.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0 Tage"))));
        ffield_frist.setName("ffield_frist"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        combo_grund.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_grund.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_grund.setName("combo_grund"); // NOI18N
        combo_grund.setPreferredSize(new java.awt.Dimension(134, 27));
        combo_grund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_grundActionPerformed(evt);
            }
        });

        date_aufgabe.setDateFormatString(resourceMap.getString("date_aufgabe.dateFormatString")); // NOI18N
        date_aufgabe.setEnabled(false);
        date_aufgabe.setMinimumSize(new java.awt.Dimension(38, 27));
        date_aufgabe.setName("date_aufgabe"); // NOI18N
        date_aufgabe.setPreferredSize(new java.awt.Dimension(144, 27));

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                        .addComponent(spinnerStoerfallNr, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(combo_bearbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addComponent(date_eingang, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                        .addComponent(date_faelligkeit, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                        .addComponent(ffield_rueckstand, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(ffield_frist, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(check_aufgabeanlegen, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                        .addGap(124, 124, 124)
                        .addComponent(date_aufgabe, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_grund, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_kunde, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_vertrag, 0, 249, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_kunde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_vertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(combo_grund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(spinnerStoerfallNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_bearbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(date_eingang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(date_faelligkeit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ffield_rueckstand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ffield_frist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(check_aufgabeanlegen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_aufgabe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        paneschaeden.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jSeparator17.setName("jSeparator17"); // NOI18N

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        field_custom1.setName("field_custom1"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jScrollPane4.setBorder(null);
        jScrollPane4.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane4.viewportBorder.title"))); // NOI18N
        jScrollPane4.setName("jScrollPane4"); // NOI18N

        area_comments.setColumns(20);
        area_comments.setRows(5);
        area_comments.setName("area_comments"); // NOI18N
        jScrollPane4.setViewportView(area_comments);

        btnMaxComments3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments3.setToolTipText(resourceMap.getString("btnMaxComments3.toolTipText")); // NOI18N
        btnMaxComments3.setName("btnMaxComments3"); // NOI18N
        btnMaxComments3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxComments3ActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        combo_kategorie.setName("combo_kategorie"); // NOI18N
        combo_kategorie.setPreferredSize(new java.awt.Dimension(134, 27));

        combo_mahnstatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_mahnstatus.setMinimumSize(new java.awt.Dimension(134, 27));
        combo_mahnstatus.setName("combo_mahnstatus"); // NOI18N
        combo_mahnstatus.setPreferredSize(new java.awt.Dimension(134, 27));
        combo_mahnstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_mahnstatusActionPerformed(evt);
            }
        });

        jSeparator18.setName("jSeparator18"); // NOI18N

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_mahnstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator18, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(btnMaxComments3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(combo_mahnstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(combo_kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(btnMaxCustom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addComponent(btnMaxCustom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addComponent(btnMaxCustom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxComments3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        paneschaeden.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 27));
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
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paneschaeden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(512, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(paneschaeden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(49, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            stoer = new StoerfallObj();
        }

        if (this.spinnerStoerfallNr.getValue() == null || !(this.spinnerStoerfallNr.getValue().toString().length() > 0)) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Störfallnummer aus",
                    "Fehler: Keine Störfallnummer", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        if (update == false) {
            stoer.setCreatorId(BasicRegistry.currentUser.getId());

            stoer.setMandantenId(BasicRegistry.currentMandant.getId());

            String snr = this.spinnerStoerfallNr.getValue().toString();

            if (Integer.valueOf(snr) < 0) {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Störfallnummer aus",
                        "Fehler: Keine Störfallnummer", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                snr = StoerfaelleHelper.verifyNochaktuell(snr);
            } catch (SQLException e) {
                Exceptions.printStackTrace(e); // TODO
            }

            stoer.setStoerfallNr(snr);
        }

        Object obj = combo_kunde.getSelectedItem();

        if (obj.getClass().equals(KundenObj.class)) {
            KundenObj knd = (KundenObj) obj;
            stoer.setKundenNr(knd.getKundenNr());
            this.kdnr = knd.getKundenNr();
        } else if (obj.getClass().equals(FirmenObj.class)) {
            FirmenObj fir = (FirmenObj) obj;
            stoer.setKundenNr(fir.getKundenNr());
            this.kdnr = fir.getKundenNr();
        } else {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus");
            return;
        }

        VertragObj choosenvtr = null;
        try {
            choosenvtr = (VertragObj) combo_vertrag.getSelectedItem();
            stoer.setVertragsId(choosenvtr.getId());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Vertrag aus.");
            return;
        }


        try {
            BenutzerObj ben = (BenutzerObj) combo_bearbeiter.getSelectedItem();
            stoer.setBetreuerId(ben.getId());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Betreuer aus.");
            return;
        }

        stoer.setGrund(this.combo_grund.getSelectedItem().toString());
        stoer.setKategorie(this.combo_kategorie.getSelectedItem().toString());
        stoer.setMahnstatus(this.combo_mahnstatus.getSelectedItem().toString());

        stoer.setEingang(new java.sql.Timestamp(this.date_eingang.getDate().getTime()));
        stoer.setFaelligkeit(new java.sql.Timestamp(this.date_faelligkeit.getDate().getTime()));

        stoer.setFristTage(Integer.valueOf(this.ffield_frist.getValue().toString()));
        stoer.setRueckstand(Double.valueOf(this.ffield_rueckstand.getValue().toString()));

        stoer.setNotiz(this.area_comments.getText());
        stoer.setCustom1(this.field_custom1.getText());
        stoer.setCustom2(this.field_custom2.getText());
        stoer.setCustom3(this.field_custom3.getText());

        stoer.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        try {
            if (update == false) {
                stoer.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                int id = StoerfaelleSQLMethods.insertIntoStoerfaelle(DatabaseConnection.open(), stoer);
                stoer.setId(id);

                if (this.check_aufgabeanlegen.isSelected()) {
                    Date dt = this.date_aufgabe.getDate();

                    if (dt == null) {
                        return;
                    }

                    AufgabenObj aufgabe = new AufgabenObj();

                    aufgabe.setCreatorId(BasicRegistry.currentUser.getId());

                    StringBuilder sb = new StringBuilder();

                    sb.append("Störfall Nr. ".concat(stoer.getStoerfallNr().concat("\n")));
                    sb.append("Vertrag. ".concat(choosenvtr.toString()).concat("\n"));
                    sb.append("Grund: ".concat(stoer.getGrund()).concat("\n\n"));

                    aufgabe.setBeschreibung(sb.toString());

                    aufgabe.setKundenKennung(stoer.getKundenNr());
                    aufgabe.setVertragId(stoer.getVertragsId());
                    aufgabe.setStoerfallId(stoer.getId());

                    aufgabe.setStart(new java.sql.Timestamp(dt.getTime()));
                    aufgabe.setEnde(new java.sql.Timestamp(dt.getTime()));

                    aufgabe.setBenutzerId(stoer.getBetreuerId());
                    aufgabe.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                    aufgabe.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                    try {
                        int aid = AufgabenSQLMethods.insertIntoAufgaben(DatabaseConnection.open(), aufgabe);
                        stoer.setAufgabenId(aid);
                        StoerfaelleSQLMethods.updateStoerfaelle(DatabaseConnection.open(), stoer);
                    } catch (Exception e) {
                        Log.logger.fatal("Konnte die Aufgabe für den Störfall nicht erstellen", e);
                        ShowException.showException("Beim erstellen der Aufgabe für den Störfall "
                                + "ist ein Fehler aufgetretten.",
                                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Störfall nicht speichern");
                    }
                }

                try {
                    FilesystemStoerfaelle.createSchadenDirectory(stoer);
                } catch (Exception e) {
                    Log.logger.fatal("Konnte Verzeichnisstruktur für den Störfall nicht erstellen", e);
                    ShowException.showException("Beim erstellen der Vereichnisstruktur für den Störfall "
                            + "ist ein Fehler aufgetretten.",
                            ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Störfall nicht speichern");
                }
                this.dispose();
            } else {
                boolean success = StoerfaelleSQLMethods.updateStoerfaelle(DatabaseConnection.open(), stoer);
                if (!success) {
                    Log.databaselogger.fatal("Datenbankfehler: Konnte den Störfall nicht updaten");
                    ShowException.showException("Beim aktualisieren des Störfall ist ein Datenbank Fehler aufgetreten. "
                            + "Sollte dieser häufiger auftreten wenden Sie sich bitte an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte den Schadenfall nicht updaten");
                }

                if (this.check_aufgabeanlegen.isSelected()) {
                    if (stoer.getAufgabenId() != -1) {
                        AufgabenObj aufg = AufgabenSQLMethods.getAufgabe(DatabaseConnection.open(), stoer.getAufgabenId());

                        if (aufg == null) {
                            Log.databaselogger.fatal("Die Aufgabe ist nicht vorhanden??");
                            throw new NullPointerException("Die Aufgabe ist nicht vorhanden.");
                        }
                        Date dt = this.date_aufgabe.getDate();
                        if (aufg.getStart() != new java.sql.Timestamp(dt.getTime())) {
                            aufg.setStart(new java.sql.Timestamp(dt.getTime()));
                            aufg.setEnde(new java.sql.Timestamp(dt.getTime()));
                            StringBuilder sb = new StringBuilder();

                            sb.append("Störfall Nr. ".concat(stoer.getStoerfallNr().concat(" \n")));
                            sb.append("Vertrag. ".concat(choosenvtr.toString()).concat(" \n"));
                            sb.append("Grund: ".concat(stoer.getGrund()).concat(" \n\n"));

                            aufg.setBeschreibung(sb.toString());

                            AufgabenSQLMethods.updateAufgaben(DatabaseConnection.open(), aufg);
                        }
                    } else {
                        Date dt = this.date_aufgabe.getDate();

                        if (dt == null) {
                            return;
                        }

                        AufgabenObj aufgabe = new AufgabenObj();

                        aufgabe.setCreatorId(BasicRegistry.currentUser.getId());

                        StringBuilder sb = new StringBuilder();

                        sb.append("Störfall Nr. ".concat(stoer.getStoerfallNr().concat("\n")));
                        sb.append("Vertrag. ".concat(vtr.toString()).concat("\n"));
                        sb.append("Grund: ".concat(stoer.getGrund()).concat("\n\n"));

                        aufgabe.setBeschreibung(sb.toString());

                        aufgabe.setKundenKennung(stoer.getKundenNr());
                        aufgabe.setVertragId(stoer.getVertragsId());
                        aufgabe.setStoerfallId(stoer.getId());

                        aufgabe.setStart(new java.sql.Timestamp(dt.getTime()));
                        aufgabe.setEnde(new java.sql.Timestamp(dt.getTime()));

                        aufgabe.setBenutzerId(stoer.getBetreuerId());
                        aufgabe.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        aufgabe.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        try {
                            int aid = AufgabenSQLMethods.insertIntoAufgaben(DatabaseConnection.open(), aufgabe);
                            stoer.setAufgabenId(aid);
                            StoerfaelleSQLMethods.updateStoerfaelle(DatabaseConnection.open(), stoer);
                        } catch (Exception e) {
                            Log.logger.fatal("Konnte die Aufgabe für den Störfall nicht erstellen", e);
                            ShowException.showException("Beim erstellen der Aufgabe für den Störfall "
                                    + "ist ein Fehler aufgetretten.",
                                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Störfall nicht speichern");
                        }
                    }
                }

                this.dispose();
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Datenbankfehler: Konnte den Störfall nicht speichern", e);
            ShowException.showException("Beim Speichern des Störfalls ist ein Datenbank Fehler aufgetreten. "
                    + "Sollte dieser häufiger auftreten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Störfall nicht speichern");
        }
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        if (this.update == false) {
            int dial = JOptionPane.NO_OPTION;
            dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
                    + "gehen in diesem Fall verloren.", "Wollen Sie das Fenster schließen?", JOptionPane.YES_NO_OPTION);
            if (dial == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        } else {
            this.dispose();
        }
}//GEN-LAST:event_btnCancelActionPerformed

    private void combo_kundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundeActionPerformed
        try {
            KundenObj knd = (KundenObj) this.combo_kunde.getSelectedItem();
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));

        } catch (Exception e) {
            FirmenObj knd = (FirmenObj) this.combo_kunde.getSelectedItem();
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(null, knd.getKundenNr())));

        }
}//GEN-LAST:event_combo_kundeActionPerformed

    private void check_aufgabeanlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_aufgabeanlegenActionPerformed
        if (check_aufgabeanlegen.isSelected()) {
            this.date_aufgabe.setEnabled(true);
        } else {
            this.date_aufgabe.setEnabled(true);
        }
}//GEN-LAST:event_check_aufgabeanlegenActionPerformed

    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed

    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxComments3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxComments3ActionPerformed
        MaximizeHelper.openMax(this.area_comments, "Notizen");
}//GEN-LAST:event_btnMaxComments3ActionPerformed

    private void combo_mahnstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_mahnstatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_mahnstatusActionPerformed

    private void combo_grundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_grundActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_grundActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                StoerfallDialog dialog = new StoerfallDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea area_comments;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxComments3;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox check_aufgabeanlegen;
    private javax.swing.JComboBox combo_bearbeiter;
    private javax.swing.JComboBox combo_grund;
    private javax.swing.JComboBox combo_kategorie;
    private javax.swing.JComboBox combo_kunde;
    private javax.swing.JComboBox combo_mahnstatus;
    private javax.swing.JComboBox combo_vertrag;
    private com.toedter.calendar.JDateChooser date_aufgabe;
    private com.toedter.calendar.JDateChooser date_eingang;
    private com.toedter.calendar.JDateChooser date_faelligkeit;
    private javax.swing.JFormattedTextField ffield_frist;
    private javax.swing.JFormattedTextField ffield_rueckstand;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JTabbedPane paneschaeden;
    private javax.swing.JSpinner spinnerStoerfallNr;
    // End of variables declaration//GEN-END:variables
}
