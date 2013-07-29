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
 * NewFirmenKundeDialog.java
 *
 * Created on Aug 9, 2010, 4:35:37 PM
 */
package de.maklerpoint.office.Gui.Firmenkunden;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Kunden.NewKundeHelper;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Konstanten.FirmenInformationen;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenKennungHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Tools.ImageComboBoxRenderer;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NewFirmenKundeDialog extends javax.swing.JDialog {

    private boolean update = false;
    private FirmenObj firma = null;
    private String kdnr = null;

    /** Creates new form NewFirmenKundeDialog */
    public NewFirmenKundeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.update = false;
        this.kdnr = null;
        initComponents();
        setupCombos();
        setupTitle();
    }

    /** Creates new form NewFirmenKundeDialog */
    public NewFirmenKundeDialog(java.awt.Frame parent, boolean modal, FirmenObj firma) {
        super(parent, modal);
        this.update = true;
        this.firma = firma;
        this.kdnr = firma.getKundenNr();
        initComponents();
        setupCombos();
        loadFirma();
        setupTitle();
    }

    private void setupTitle() {
        if (update == false) {
            this.setTitle("Neuer Geschäftskunde");
        } else {
            this.setTitle(firma.getFirmenName() + " [" + firma.getKundenNr() + "]");
        }
    }

    private void setupCombos() {
        this.field_kundenNr.setEnabled(true);
//        this.combo_comtype1 = new JComboBox(CommunicationTypes.COMMUNICATION_INTARRAY)M
        this.combo_comtype1.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype1.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype1.setSelectedIndex(0);

        this.combo_comtype2.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype2.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype2.setSelectedIndex(1);

        this.combo_comtype3.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype3.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype3.setSelectedIndex(4);

        this.combo_comtype4.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype4.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype4.setSelectedIndex(7);

        this.combo_comtype5.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype5.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype5.setSelectedIndex(10);

        this.combo_comtype6.setModel(new DefaultComboBoxModel(CommunicationTypes.COMMUNICATION_INTARRAY));
        this.combo_comtype6.setRenderer(new ImageComboBoxRenderer(CommunicationTypes.COMMUNCATION_IMAGES,
                CommunicationTypes.COMMUNICATIONTYPES));
        this.combo_comtype6.setSelectedIndex(14);

        if (kdnr != null) {
            this.combo_defaultKonto.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getDefaultKontoCombo("Kein Standardkonto", kdnr)));
        }

        if (kdnr != null) {
            this.combo_defaultAnsprechpartner.setModel(new DefaultComboBoxModel(
                    ComboBoxGetter.getDefaultFirmenAnsprechpartnerCombo("Kein Standardkonto", kdnr)));
        }

        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));
        combo_filialtyp.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_FILIAL_TYPEN));
        combo_branche.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_BRANCHEN));

        NewKundeHelper.loadWerber(this);
        NewKundeHelper.loadBetreuer(this, update);

        if (firma == null) {
            try {
                this.field_kundenNr.setValue(Integer.valueOf(KundenKennungHelper.getNextKundennummer(DatabaseConnection.open())));
            } catch (SQLException ex) {
                this.field_kundenNr.setValue(-1);
            }
        }

    }

    private void loadFirma() {
//        this.field_bankleitzahl;
//        this.field_bankname;
//        this.field_bic;
//        this.field_communication1;
//        this.field_communication2;
//        this.field_communication3;
//        this.field_communication4;
//        this.field_communication5;
//        this.field_communication6;
//        this.field_custom1;
//        this.field_custom2;
//        this.field_custom3;
//        this.field_custom4;
//        this.field_custom5;
//        this.field_firma;
//        this.field_firmenZusatz;
//        this.field_firmenZusatz2;
//        this.field_geschaeftsleiter;
//        this.field_iban;
//        this.field_kontoeigentuemer;
//        this.field_kontonummer;
//        this.field_kundenNr;
//        this.field_ort;
//        this.field_plz;
//        this.field_postfach;
//        this.field_postfachOrt;
//        this.field_postfachPLZ;
//        this.field_strasse;
//        this.combo_anzahlmitarbeiter;
//        this.combo_betreuer;
//        this.combo_branche;
//        this.combo_bundesland;
//        this.combo_comtype1;
//        this.combo_comtype2;
//        this.combo_comtype3;
//        this.combo_comtype4;
//        this.combo_comtype5;
//        this.combo_comtype6;
//        this.combo_filialtyp;
//        this.combo_gesellschaft;
//        this.combo_land;
//        this.combo_rechtsform;
//        this.combo_umsatz;
//        this.combo_werber;
//        this.check_elv;
//        this.check_usepostfach;
//        this.jxlist_geschaeftsfuehrer;
//        this.jxlist_prokura;
//        this.jxlist_standorte;
//        this.text_comments;
//        this.date_gruendungsdatum;

        if (this.firma == null) {
            return;
        }


        this.field_communication1.setText(firma.getCommunication1());
        this.field_communication2.setText(firma.getCommunication2());
        this.field_communication3.setText(firma.getCommunication3());
        this.field_communication4.setText(firma.getCommunication4());
        this.field_communication5.setText(firma.getCommunication5());
        this.field_communication6.setText(firma.getCommunication6());
        this.field_custom1.setText(firma.getCustom1());
        this.field_custom2.setText(firma.getCustom2());
        this.field_custom3.setText(firma.getCustom3());
        this.field_custom4.setText(firma.getCustom4());
        this.field_custom5.setText(firma.getCustom5());
        this.field_firma.setText(firma.getFirmenName());
        this.field_firmenZusatz.setText(firma.getFirmenNameZusatz());
        this.field_firmenZusatz2.setText(firma.getFirmenNameZusatz2());
        this.field_geschaeftsleiter.setText(firma.getFirmenGeschaeftsfuehrer());

        this.field_kundenNr.setValue(Integer.valueOf(firma.getKundenNr()));
        this.field_kundenNr.setEnabled(false); // Nicht mehr änderbar

        this.field_ort.setText(firma.getFirmenStadt());
        this.field_plz.setText(firma.getFirmenPLZ());
        this.field_postfach.setText(firma.getFirmenPostfachName());
        this.field_postfachOrt.setText(firma.getFirmenPostfachOrt());
        this.field_postfachPLZ.setText(firma.getFirmenPostfachPlz());
        this.field_strasse.setText(firma.getFirmenStrasse());
        this.combo_anzahlmitarbeiter.setSelectedItem(firma.getFirmenSize());

        this.combo_betreuer.setSelectedItem(BenutzerRegistry.getBenutzer(firma.getBetreuer()));

        this.combo_branche.setSelectedItem(firma.getFirmenBranche());
        this.combo_bundesland.setSelectedItem(firma.getFirmenBundesland());
        this.combo_land.setSelectedItem(firma.getFirmenLand());

        this.combo_comtype1.setSelectedIndex(firma.getCommunication1Type());
        this.combo_comtype2.setSelectedIndex(firma.getCommunication2Type());
        this.combo_comtype3.setSelectedIndex(firma.getCommunication3Type());
        this.combo_comtype4.setSelectedIndex(firma.getCommunication4Type());
        this.combo_comtype5.setSelectedIndex(firma.getCommunication5Type());
        this.combo_comtype6.setSelectedIndex(firma.getCommunication6Type());

        this.combo_filialtyp.setSelectedItem(firma.getFirmenTyp());

        if (firma.getParentFirma() != -1) {
            this.combo_gesellschaft.setSelectedItem(
                    KundenRegistry.getFirmenKunde(firma.getParentFirma()));
        }

        this.combo_rechtsform.setSelectedItem(firma.getFirmenRechtsform());
        this.combo_umsatz.setSelectedItem(firma.getFirmenEinkommen());

        if (!"Unbekannt".equalsIgnoreCase(firma.getWerber())) {
            this.combo_werber.setSelectedItem(KundenRegistry.getKunde(firma.getWerber()));
        }


        this.check_usepostfach.setSelected(firma.isFirmenPostfach());

        this.text_comments.setText(firma.getComments());

        if (firma.getFirmenGruendungDatum() != null) {
            this.date_gruendung.setDate(firma.getFirmenGruendungDatum());
        }

        //TODO
//        this.jxlist_geschaeftsfuehrer;
//        this.jxlist_prokura;
//        this.jxlist_standorte;

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane_kundeninfos = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        field_firma = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        field_firmenZusatz = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        field_firmenZusatz2 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        combo_gesellschaft = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        combo_rechtsform = new javax.swing.JComboBox();
        combo_filialtyp = new javax.swing.JComboBox();
        combo_anzahlmitarbeiter = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        field_geschaeftsleiter = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        combo_branche = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        field_kundenNr = new javax.swing.JSpinner();
        date_gruendung = new com.toedter.calendar.JDateChooser();
        panel_rechtlich = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jxlist_prokura = new org.jdesktop.swingx.JXList();
        btnAddVertreter = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jxlist_standorte = new org.jdesktop.swingx.JXList();
        btnAddStandorte = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        combo_umsatz = new javax.swing.JComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        combo_defaultKonto = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        combo_defaultAnsprechpartner = new javax.swing.JComboBox();
        panel_anschrift = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        field_strasse = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        field_plz = new javax.swing.JTextField();
        field_ort = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        field_postfach = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        field_postfachPLZ = new javax.swing.JTextField();
        field_postfachOrt = new javax.swing.JTextField();
        check_usepostfach = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        combo_land = new javax.swing.JComboBox();
        combo_bundesland = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        panel_kontakt = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        combo_comtype1 = new javax.swing.JComboBox();
        field_communication1 = new javax.swing.JTextField();
        combo_comtype2 = new javax.swing.JComboBox();
        field_communication2 = new javax.swing.JTextField();
        combo_comtype3 = new javax.swing.JComboBox();
        field_communication3 = new javax.swing.JTextField();
        combo_comtype4 = new javax.swing.JComboBox();
        combo_comtype5 = new javax.swing.JComboBox();
        combo_comtype6 = new javax.swing.JComboBox();
        field_communication4 = new javax.swing.JTextField();
        field_communication5 = new javax.swing.JTextField();
        field_communication6 = new javax.swing.JTextField();
        panel_sonstiges = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        field_custom1 = new javax.swing.JTextField();
        btnMaxCustom1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        field_custom2 = new javax.swing.JTextField();
        btnMaxCustom2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        field_custom3 = new javax.swing.JTextField();
        btnMaxCustom3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        field_custom4 = new javax.swing.JTextField();
        btnMaxCustom4 = new javax.swing.JButton();
        btnMaxCustom5 = new javax.swing.JButton();
        field_custom5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        btnMaxTextKommentar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        combo_betreuer = new javax.swing.JComboBox();
        jLabel57 = new javax.swing.JLabel();
        combo_werber = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NewFirmenKundeDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        pane_kundeninfos.setName("pane_kundeninfos"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        field_firma.setName("field_firma"); // NOI18N
        field_firma.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_firmenZusatz.setName("field_firmenZusatz"); // NOI18N
        field_firmenZusatz.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_firmenZusatz2.setName("field_firmenZusatz2"); // NOI18N
        field_firmenZusatz2.setPreferredSize(new java.awt.Dimension(80, 25));

        jSeparator5.setName("jSeparator5"); // NOI18N

        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N

        combo_gesellschaft.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine" }));
        combo_gesellschaft.setName("combo_gesellschaft"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        combo_rechtsform.setEditable(true);
        combo_rechtsform.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_rechtsform.setName("combo_rechtsform"); // NOI18N

        combo_filialtyp.setEditable(true);
        combo_filialtyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_filialtyp.setName("combo_filialtyp"); // NOI18N

        combo_anzahlmitarbeiter.setEditable(true);
        combo_anzahlmitarbeiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Mitarbeiter", "1-3 Mitarbeiter", "3-5 Mitarbeiter", "5-10 Mitarbeiter", "10-50 Mitarbeiter", "50-100 Mitarbeiter", "mehr als 100 Mitarbeiter" }));
        combo_anzahlmitarbeiter.setName("combo_anzahlmitarbeiter"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N

        field_geschaeftsleiter.setName("field_geschaeftsleiter"); // NOI18N

        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N

        combo_branche.setEditable(true);
        combo_branche.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_branche.setName("combo_branche"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        field_kundenNr.setName("field_kundenNr"); // NOI18N

        date_gruendung.setName("date_gruendung"); // NOI18N

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_firma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(field_firmenZusatz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(field_firmenZusatz2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_kundenNr, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(combo_gesellschaft, 0, 218, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel25))
                        .addGap(38, 38, 38)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(combo_rechtsform, javax.swing.GroupLayout.Alignment.LEADING, 0, 294, Short.MAX_VALUE)
                            .addComponent(combo_filialtyp, 0, 294, Short.MAX_VALUE)
                            .addComponent(combo_branche, javax.swing.GroupLayout.Alignment.LEADING, 0, 294, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                        .addComponent(date_gruendung, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(combo_anzahlmitarbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_geschaeftsleiter, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_gesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(field_kundenNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(field_firma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(field_firmenZusatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(field_firmenZusatz2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(combo_rechtsform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(combo_filialtyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_branche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(combo_anzahlmitarbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_gruendung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(field_geschaeftsleiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pane_kundeninfos.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_rechtlich.setName("panel_rechtlich"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jxlist_prokura.setName("jxlist_prokura"); // NOI18N
        jScrollPane6.setViewportView(jxlist_prokura);

        btnAddVertreter.setIcon(resourceMap.getIcon("btnAddVertreter.icon")); // NOI18N
        btnAddVertreter.setToolTipText(resourceMap.getString("btnAddVertreter.toolTipText")); // NOI18N
        btnAddVertreter.setName("btnAddVertreter"); // NOI18N
        btnAddVertreter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVertreterActionPerformed(evt);
            }
        });

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jxlist_standorte.setName("jxlist_standorte"); // NOI18N
        jScrollPane7.setViewportView(jxlist_standorte);

        btnAddStandorte.setIcon(resourceMap.getIcon("btnAddStandorte.icon")); // NOI18N
        btnAddStandorte.setToolTipText(resourceMap.getString("btnAddStandorte.toolTipText")); // NOI18N
        btnAddStandorte.setName("btnAddStandorte"); // NOI18N
        btnAddStandorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStandorteActionPerformed(evt);
            }
        });

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        combo_umsatz.setEditable(true);
        combo_umsatz.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "bis         100.000", "bis         500.000", "bis      1.000.000", "bis    10.000.000", "über 10.000.000" }));
        combo_umsatz.setName("combo_umsatz"); // NOI18N

        jSeparator9.setName("jSeparator9"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        combo_defaultKonto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Konten vorhanden" }));
        combo_defaultKonto.setName("combo_defaultKonto"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        combo_defaultAnsprechpartner.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Ansprechpartner" }));
        combo_defaultAnsprechpartner.setName("combo_defaultAnsprechpartner"); // NOI18N

        javax.swing.GroupLayout panel_rechtlichLayout = new javax.swing.GroupLayout(panel_rechtlich);
        panel_rechtlich.setLayout(panel_rechtlichLayout);
        panel_rechtlichLayout.setHorizontalGroup(
            panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlichLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(panel_rechtlichLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(combo_umsatz, 0, 277, Short.MAX_VALUE))
                    .addComponent(jLabel14)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rechtlichLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddVertreter))
                    .addComponent(jLabel15)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rechtlichLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddStandorte))
                    .addGroup(panel_rechtlichLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(combo_defaultKonto, 0, 296, Short.MAX_VALUE))
                    .addGroup(panel_rechtlichLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(combo_defaultAnsprechpartner, 0, 250, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_rechtlichLayout.setVerticalGroup(
            panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlichLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(combo_umsatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddVertreter)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddStandorte)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(combo_defaultKonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(combo_defaultAnsprechpartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pane_kundeninfos.addTab(resourceMap.getString("panel_rechtlich.TabConstraints.tabTitle"), panel_rechtlich); // NOI18N

        panel_anschrift.setName("panel_anschrift"); // NOI18N

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        field_strasse.setName("field_strasse"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        field_plz.setName("field_plz"); // NOI18N

        field_ort.setName("field_ort"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        field_postfach.setName("field_postfach"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        field_postfachPLZ.setName("field_postfachPLZ"); // NOI18N

        field_postfachOrt.setName("field_postfachOrt"); // NOI18N

        check_usepostfach.setText(resourceMap.getString("check_usepostfach.text")); // NOI18N
        check_usepostfach.setName("check_usepostfach"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        combo_land.setEditable(true);
        combo_land.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_land.setName("combo_land"); // NOI18N

        combo_bundesland.setEditable(true);
        combo_bundesland.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_bundesland.setName("combo_bundesland"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        javax.swing.GroupLayout panel_anschriftLayout = new javax.swing.GroupLayout(panel_anschrift);
        panel_anschrift.setLayout(panel_anschriftLayout);
        panel_anschriftLayout.setHorizontalGroup(
            panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_anschriftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(combo_land, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_bundesland, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(check_usepostfach)
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(field_postfach, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_anschriftLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(field_postfachPLZ, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_postfachOrt, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_anschriftLayout.setVerticalGroup(
            panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_anschriftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(combo_bundesland, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_land, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_usepostfach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(field_postfach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_anschriftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(field_postfachPLZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_postfachOrt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        NewKundeHelper.loadLaender(this);
        NewKundeHelper.loadBundeslaender(0, this);

        pane_kundeninfos.addTab(resourceMap.getString("panel_anschrift.TabConstraints.tabTitle"), panel_anschrift); // NOI18N

        panel_kontakt.setName("panel_kontakt"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        combo_comtype1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype1.setName("combo_comtype1"); // NOI18N

        field_communication1.setName("field_communication1"); // NOI18N

        combo_comtype2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype2.setName("combo_comtype2"); // NOI18N

        field_communication2.setName("field_communication2"); // NOI18N

        combo_comtype3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype3.setName("combo_comtype3"); // NOI18N

        field_communication3.setName("field_communication3"); // NOI18N

        combo_comtype4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype4.setName("combo_comtype4"); // NOI18N

        combo_comtype5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype5.setName("combo_comtype5"); // NOI18N

        combo_comtype6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype6.setName("combo_comtype6"); // NOI18N

        field_communication4.setName("field_communication4"); // NOI18N

        field_communication5.setName("field_communication5"); // NOI18N

        field_communication6.setName("field_communication6"); // NOI18N

        javax.swing.GroupLayout panel_kontaktLayout = new javax.swing.GroupLayout(panel_kontakt);
        panel_kontakt.setLayout(panel_kontaktLayout);
        panel_kontaktLayout.setHorizontalGroup(
            panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kontaktLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_kontaktLayout.setVerticalGroup(
            panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kontaktLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        pane_kundeninfos.addTab(resourceMap.getString("panel_kontakt.TabConstraints.tabTitle"), panel_kontakt); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_custom1.setName("field_custom1"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        field_custom4.setName("field_custom4"); // NOI18N

        btnMaxCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom4.setToolTipText(resourceMap.getString("btnMaxCustom4.toolTipText")); // NOI18N
        btnMaxCustom4.setName("btnMaxCustom4"); // NOI18N
        btnMaxCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom4ActionPerformed(evt);
            }
        });

        btnMaxCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom5.setToolTipText(resourceMap.getString("btnMaxCustom5.toolTipText")); // NOI18N
        btnMaxCustom5.setName("btnMaxCustom5"); // NOI18N
        btnMaxCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom5ActionPerformed(evt);
            }
        });

        field_custom5.setName("field_custom5"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane1.setViewportView(text_comments);

        btnMaxTextKommentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxTextKommentar.setToolTipText(resourceMap.getString("btnMaxTextKommentar.toolTipText")); // NOI18N
        btnMaxTextKommentar.setName("btnMaxTextKommentar"); // NOI18N
        btnMaxTextKommentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxTextKommentarActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        combo_betreuer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_betreuer.setName("combo_betreuer"); // NOI18N

        jLabel57.setText(resourceMap.getString("jLabel57.text")); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N

        combo_werber.setEditable(true);
        combo_werber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_werber.setName("combo_werber"); // NOI18N

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel57))
                        .addGap(18, 18, 18)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_werber, 0, 327, Short.MAX_VALUE)
                            .addComponent(combo_betreuer, 0, 327, Short.MAX_VALUE)))
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar))
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom5, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom5))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom4, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(combo_betreuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_werber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnMaxCustom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(btnMaxCustom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(btnMaxCustom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(btnMaxCustom4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(btnMaxCustom5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pane_kundeninfos.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
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
                .addContainerGap(142, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pane_kundeninfos, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(508, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pane_kundeninfos, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed

    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed

    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed

    private void btnMaxCustom4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom4ActionPerformed
        MaximizeHelper.openMax(this.field_custom4, "Benutzerdefiniert 4");
}//GEN-LAST:event_btnMaxCustom4ActionPerformed

    private void btnMaxCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom5ActionPerformed
        MaximizeHelper.openMax(this.field_custom5, "Benutzerdefiniert 5");
}//GEN-LAST:event_btnMaxCustom5ActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    private void btnAddVertreterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVertreterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddVertreterActionPerformed

    private void btnAddStandorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStandorteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddStandorteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            firma = new FirmenObj();
        }

        if (this.field_kundenNr.getValue().toString() == null || this.field_kundenNr.getValue().toString().equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Kundennummer an.");
            return;
        }

        if (this.field_firma.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Firmennamen an.");
            return;
        }


        firma.setCommunication1(field_communication1.getText());
        firma.setCommunication2(field_communication2.getText());
        firma.setCommunication3(field_communication3.getText());
        firma.setCommunication4(field_communication4.getText());
        firma.setCommunication5(field_communication5.getText());
        firma.setCommunication6(field_communication6.getText());
        firma.setCustom1(field_custom1.getText());
        firma.setCustom2(field_custom2.getText());
        firma.setCustom3(field_custom3.getText());
        firma.setCustom4(field_custom4.getText());
        firma.setCustom5(field_custom5.getText());
        firma.setFirmenName(field_firma.getText());
        firma.setFirmenNameZusatz(field_firmenZusatz.getText());
        firma.setFirmenNameZusatz2(field_firmenZusatz2.getText());
        firma.setFirmenGeschaeftsfuehrer(field_geschaeftsleiter.getText());
//        firma.setKundenNr(field_kundenNr.getValue().toString());

        if (update == false) {
            String newkdr = this.field_kundenNr.getValue().toString();

            if (Integer.valueOf(newkdr) <= 0) {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Kundennummer aus",
                        "Fehler: Keine Kundennummer", JOptionPane.WARNING_MESSAGE);
                return;
            }


            try {
                newkdr = KundenKennungHelper.verifyNochaktuell(newkdr);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            firma.setKundenNr(newkdr);
        }


        firma.setFirmenStadt(field_ort.getText());
        firma.setFirmenPLZ(field_plz.getText());
//        System.out.println("PLZ: " + field_plz.getText());
        firma.setFirmenPostfachName(field_postfach.getText());
        firma.setFirmenPostfachOrt(field_postfachOrt.getText());
        firma.setFirmenPostfachPlz(field_postfachPLZ.getText());
        firma.setFirmenStrasse(field_strasse.getText());
        firma.setFirmenSize((String) combo_anzahlmitarbeiter.getSelectedItem());

        BenutzerObj ben = (BenutzerObj) combo_betreuer.getSelectedItem();
        firma.setBetreuer(ben.getId());

        // firma.setBetreuer(this.combo_betreuer.getSelectedItem());

        firma.setFirmenBranche((String) combo_branche.getSelectedItem());
        firma.setFirmenBundesland((String) combo_bundesland.getSelectedItem());
        firma.setCommunication1Type(this.combo_comtype1.getSelectedIndex());
        firma.setCommunication2Type(this.combo_comtype2.getSelectedIndex());
        firma.setCommunication3Type(this.combo_comtype3.getSelectedIndex());
        firma.setCommunication4Type(this.combo_comtype4.getSelectedIndex());
        firma.setCommunication5Type(this.combo_comtype5.getSelectedIndex());
        firma.setCommunication6Type(this.combo_comtype6.getSelectedIndex());

        firma.setFirmenTyp((String) combo_filialtyp.getSelectedItem());


        try {
            FirmenObj parentfi = (FirmenObj) combo_gesellschaft.getSelectedItem();
            firma.setParentFirma(parentfi.getId());
        } catch (Exception e) {
            firma.setParentFirma(-1);
        }


        firma.setFirmenLand((String) combo_land.getSelectedItem());
        firma.setFirmenRechtsform((String) combo_rechtsform.getSelectedItem());
        firma.setFirmenEinkommen((String) combo_umsatz.getSelectedItem());
        
        try {
            Object obj = combo_werber.getSelectedItem();
            if(obj.getClass().equals(KundenObj.class)){
                KundenObj knd = (KundenObj) obj;
                firma.setWerber(knd.getKundenNr());
            } else if(obj.getClass().equals(FirmenObj.class)){
                FirmenObj knd = (FirmenObj) obj;
                firma.setWerber(knd.getKundenNr());
            } else {
                firma.setWerber("Unbekannt");
            }
                
        } catch (Exception e) {
            firma.setWerber("Unbekannt");
        }
  
        firma.setFirmenPostfach(check_usepostfach.isSelected());

        firma.setComments(text_comments.getText());

        if (date_gruendung.getDate() != null) {
            firma.setFirmenGruendungDatum(new java.sql.Date(date_gruendung.getDate().getTime()));
        }

        firma.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

//        TODO
//        this.jxlist_geschaeftsfuehrer;
//        this.jxlist_prokura;
//        this.jxlist_standorte;
        try {
            if (update == false) {
                firma.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                int id = FirmenSQLMethods.insertIntofirmenkunden(DatabaseConnection.open(), firma);
                firma.setId(id);
                NewKundeHelper.doCreationTasks(firma);

            } else {
                boolean success = FirmenSQLMethods.updatefirmenkunden(DatabaseConnection.open(), firma);

                if (!success) {
                    Log.databaselogger.fatal("Konnte den Firmenkunden mit der Kennung "
                            + firma.getKundenNr() + " konnte nicht geupdated werden (nicht gefunden).");
                    System.out.println("ADD Exception: Firma nicht geupdt");
                }
            }
            this.dispose();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Firmenkunden mit der Kennung "
                    + firma.getKundenNr() + " konnte nicht geupdated / neu hinzufügt werden.", e);
            ShowException.showException("Der Firmenkunde mit der Kennung \""
                    + firma.getKundenNr() + "\" konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Datenbankfehler: Konnte Firmenkunde nicht speichern");
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewFirmenKundeDialog dialog = new NewFirmenKundeDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddStandorte;
    private javax.swing.JButton btnAddVertreter;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnMaxCustom4;
    private javax.swing.JButton btnMaxCustom5;
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox check_usepostfach;
    private javax.swing.JComboBox combo_anzahlmitarbeiter;
    public javax.swing.JComboBox combo_betreuer;
    private javax.swing.JComboBox combo_branche;
    public javax.swing.JComboBox combo_bundesland;
    private javax.swing.JComboBox combo_comtype1;
    private javax.swing.JComboBox combo_comtype2;
    private javax.swing.JComboBox combo_comtype3;
    private javax.swing.JComboBox combo_comtype4;
    private javax.swing.JComboBox combo_comtype5;
    private javax.swing.JComboBox combo_comtype6;
    private javax.swing.JComboBox combo_defaultAnsprechpartner;
    private javax.swing.JComboBox combo_defaultKonto;
    private javax.swing.JComboBox combo_filialtyp;
    private javax.swing.JComboBox combo_gesellschaft;
    public javax.swing.JComboBox combo_land;
    private javax.swing.JComboBox combo_rechtsform;
    private javax.swing.JComboBox combo_umsatz;
    public javax.swing.JComboBox combo_werber;
    private com.toedter.calendar.JDateChooser date_gruendung;
    private javax.swing.JTextField field_communication1;
    private javax.swing.JTextField field_communication2;
    private javax.swing.JTextField field_communication3;
    private javax.swing.JTextField field_communication4;
    private javax.swing.JTextField field_communication5;
    private javax.swing.JTextField field_communication6;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JTextField field_custom4;
    private javax.swing.JTextField field_custom5;
    private javax.swing.JTextField field_firma;
    private javax.swing.JTextField field_firmenZusatz;
    private javax.swing.JTextField field_firmenZusatz2;
    private javax.swing.JTextField field_geschaeftsleiter;
    private javax.swing.JSpinner field_kundenNr;
    private javax.swing.JTextField field_ort;
    private javax.swing.JTextField field_plz;
    private javax.swing.JTextField field_postfach;
    private javax.swing.JTextField field_postfachOrt;
    private javax.swing.JTextField field_postfachPLZ;
    private javax.swing.JTextField field_strasse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private org.jdesktop.swingx.JXList jxlist_prokura;
    private org.jdesktop.swingx.JXList jxlist_standorte;
    private javax.swing.JTabbedPane pane_kundeninfos;
    public javax.swing.JPanel panel_anschrift;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JPanel panel_kontakt;
    private javax.swing.JPanel panel_rechtlich;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JTextArea text_comments;
    // End of variables declaration//GEN-END:variables
}
