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
 * StammdatenDialog.java
 *
 * Created on Jul 31, 2010, 6:02:38 PM
 */

package de.maklerpoint.office.Gui.Stammdaten;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Konstanten.FirmenInformationen;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Mandanten.Tools.MandantenSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.MandantenRegistry;
import de.maklerpoint.office.Tools.ArrayStringTools;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class StammdatenDialog extends javax.swing.JDialog {

    private boolean update = true;
    private MandantenObj mandant = null;

    /** Creates new form StammdatenDialog */
    public StammdatenDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.mandant = BasicRegistry.currentMandant;       
        initComponents();
        this.loadStammdaten();
    }

    public StammdatenDialog(java.awt.Frame parent, boolean modal, MandantenObj mandant) {
        super(parent, modal);        
        initComponents();
        this.loadStammdaten();
    }

    private void loadStammdaten() {
        if(update == false || mandant == null)
            return;

        this.setTitle("Stammdaten Verwaltung " + mandant.getFirmenName());

//        this.field_bankBIC;
//        this.field_bankBkz;
//        this.field_bankEigentuemer;
//        this.field_bankIBAN;
//        this.field_bankKto;
//        this.field_bankname;
//        this.field_custom1;
//        this.field_custom2;
//        this.field_custom3;
//        this.field_custom4;
//        this.field_custom5;
//        this.field_custom6;
//        this.field_custom7;
//        this.field_custom8;
//        this.field_custom9;
//        this.field_custom10;
//        this.field_fax;
//        this.field_fax2;
//        this.field_firmenZusatz;
//        this.field_firmenZusatz2;
//        this.field_firmenname;
//        this.field_geschaeftsleiter;
//        this.field_gesellschafter;
//        this.field_gewerbamtOrt;
//        this.field_gewerbeamtName;
//        this.field_gewerbeamtPlz;
//        this.field_handelsregisterName;
//        this.field_handelsregisterNummer;
//        this.field_handelsregisterOrt;
//        this.field_handelsregisterPlz;
//        this.field_homepage;
//        this.field_ihkbesonderheiten;
//        this.field_ihkname;
//        this.field_ihknummer;
//        this.field_mobil;
//        this.field_mobil2;
//        this.field_ort;
//        this.field_plz;
//        this.field_postfach;
//        this.field_postfachOrt;
//        this.field_postfachPLZ;
//        this.field_steuernummer;
//        this.field_strasse;
//        this.field_telefon;
//        this.field_telefon2;
//        this.field_ustid;
//        this.field_vermoegensschaden;
//        this.check_34c;
//        this.check_34d;
//        this.check_anteileMAK;
//        this.check_anteileVU;
//        this.check_gewerbeamtshow;
//        this.check_handelsregistershow;
//        this.text_comments;
//        this.text_emailsignatur;
//        this.combo_anzahlmitarbeiter;
//        this.combo_beratertyp;
//        this.combo_filialtyp;
//        this.combo_gesellschaft;
//        this.combo_ihkstatus;
//        this.combo_rechtsform;

        if(mandant.getBankBIC() != null)
            this.field_bankBIC.setText(mandant.getBankBIC());

        if(mandant.getBankLeitzahl() != null)
            this.field_bankBkz.setText(mandant.getBankLeitzahl());
        
        this.field_bankEigentuemer.setText(mandant.getBankEigentuemer());
        this.field_bankIBAN.setText(mandant.getBankIBAN());
        this.field_bankKto.setText(mandant.getBankKonto());
        this.field_bankname.setText(mandant.getBankName());
        this.field_custom1.setText(mandant.getCustom1());
        this.field_custom2.setText(mandant.getCustom2());
        this.field_custom3.setText(mandant.getCustom3());
        this.field_custom4.setText(mandant.getCustom4());
        this.field_custom5.setText(mandant.getCustom5());
        this.field_custom6.setText(mandant.getCustom6());
        this.field_custom7.setText(mandant.getCustom7());
        this.field_custom8.setText(mandant.getCustom8());
        this.field_custom9.setText(mandant.getCustom9());
        this.field_custom10.setText(mandant.getCustom10());
        this.field_fax.setText(mandant.getFax());
        this.field_fax2.setText(mandant.getFax2());
        this.field_firmenZusatz.setText(mandant.getFirmenZusatz());
        this.field_firmenZusatz2.setText(mandant.getFirmenZusatz2());
        this.field_firmenname.setText(mandant.getFirmenName());
        this.field_geschaeftsleiter.setText(mandant.getGeschaeftsleiter());
        this.field_gesellschafter.setText(ArrayStringTools.arrayToString(mandant.getGesellschafter(), ","));
        this.field_gewerbamtOrt.setText(mandant.getGewerbeamtOrt());
        this.field_gewerbeamtName.setText(mandant.getGewerbeamtName());
        this.field_gewerbeamtPlz.setText(mandant.getGewerbeamtPLZ());
        this.field_handelsregisterName.setText(mandant.getHandelsregisterName());
        this.field_handelsregisterNummer.setText(mandant.getHandelsregisterRegistrierNummer());
        this.field_handelsregisterOrt.setText(mandant.getHandelsregisterOrt());
        this.field_handelsregisterPlz.setText(mandant.getHandelsregisterPLZ());
        this.field_homepage.setText(mandant.getHomepage());
        this.field_ihkbesonderheiten.setText(mandant.getIhkAbweichungen());
        this.field_ihkname.setText(mandant.getIhkName());
        this.field_ihknummer.setText(mandant.getIhkRegistriernummer());
        this.field_mobil.setText(mandant.getMobil());
        this.field_mobil2.setText(mandant.getMobil2());
        this.field_ort.setText(mandant.getOrt());
        this.field_plz.setText(mandant.getPlz());
        this.field_postfach.setText(mandant.getPostfach());
        this.field_postfachOrt.setText(mandant.getPostfachOrt());
        this.field_postfachPLZ.setText(mandant.getPostfachPlz());
        this.field_steuernummer.setText(mandant.getSteuerNummer());
        this.field_strasse.setText(mandant.getStrasse());
        this.field_telefon.setText(mandant.getTelefon());
        this.field_telefon2.setText(mandant.getTelefon2());
        this.field_ustid.setText(mandant.getUstNummer());
        this.field_vermoegensschaden.setText(mandant.getVermoegensHaftpflicht());
        this.check_34c.setSelected(mandant.is34c());
        this.check_34d.setSelected(mandant.is34d());
        this.check_anteileMAK.setSelected(mandant.isBeteiligungenMAK());
        this.check_anteileVU.setSelected(mandant.isBeteiligungenVU());
        this.check_gewerbeamtshow.setSelected(mandant.isGewerbeamtShow());
        this.check_handelsregistershow.setSelected(mandant.isHandelsregisterShow());
        this.text_comments.setText(mandant.getComments());
        this.text_emailsignatur.setText(mandant.getEmailSignatur());
        this.combo_anzahlmitarbeiter.setSelectedItem(mandant.getFilialMitarbeiterZahl());
        this.combo_beratertyp.setSelectedItem(mandant.getBeraterTyp());
        this.combo_filialtyp.setSelectedItem(mandant.getFilialTyp());

        // TODO
        this.combo_gesellschaft.setSelectedItem(mandant.getFirmenName());
        
        this.combo_ihkstatus.setSelectedItem(mandant.getIhkStatus());
        this.combo_rechtsform.setSelectedItem(mandant.getFirmenRechtsform());
    }

    public void loadGesellschaften() {
        Object[] gesellschaften = new Object[MandantenRegistry.alleMandanten.length + 1];
        gesellschaften[0] = "Keine";
        
        System.arraycopy(MandantenRegistry.alleMandanten, 0, gesellschaften, 1, MandantenRegistry.alleMandanten.length);

        this.combo_gesellschaft.setModel(new DefaultComboBoxModel(gesellschaften));
        combo_gesellschaft.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneStammdaten = new javax.swing.JTabbedPane();
        panel_basisdaten = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        field_firmenname = new javax.swing.JTextField();
        field_firmenZusatz = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        field_firmenZusatz2 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        combo_rechtsform = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        field_strasse = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        field_plz = new javax.swing.JTextField();
        field_ort = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        field_postfach = new javax.swing.JTextField();
        field_postfachOrt = new javax.swing.JTextField();
        field_postfachPLZ = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel30 = new javax.swing.JLabel();
        combo_filialtyp = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        combo_anzahlmitarbeiter = new javax.swing.JComboBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        field_geschaeftsleiter = new javax.swing.JTextField();
        combo_gesellschaft = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        panel_erweitertedaten = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        field_bankname = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        field_bankKto = new javax.swing.JTextField();
        field_bankBkz = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        field_bankEigentuemer = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        field_bankIBAN = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        field_bankBIC = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jxlist_verbandsmitgliedschaften = new org.jdesktop.swingx.JXList();
        jLabel55 = new javax.swing.JLabel();
        btnAddVerbandsmitglied = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jxlist_beschwerdestelle = new org.jdesktop.swingx.JXList();
        btnAddBeschwerdestellen = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        panel_kontaktdaten = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        field_telefon = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        field_telefon2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        field_mobil = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        field_mobil2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        field_fax = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        field_fax2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        field_homepage = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_emailsignatur = new javax.swing.JTextArea();
        btnMaxEmailSig = new javax.swing.JButton();
        panel_rechtlicheangaben = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jxlist_vertretpersonen = new org.jdesktop.swingx.JXList();
        jLabel34 = new javax.swing.JLabel();
        btnAddVertrettpers = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        check_34c = new javax.swing.JCheckBox();
        check_34d = new javax.swing.JCheckBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        combo_beratertyp = new javax.swing.JComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        field_ihkname = new javax.swing.JTextField();
        btnMaxIHKName = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        field_ihknummer = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        combo_ihkstatus = new javax.swing.JComboBox();
        jLabel39 = new javax.swing.JLabel();
        field_ihkbesonderheiten = new javax.swing.JTextField();
        btnMaxIHKBesonderheiten = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        field_steuernummer = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        field_ustid = new javax.swing.JTextField();
        panel_rechtlicheangaben2 = new javax.swing.JPanel();
        check_anteileVU = new javax.swing.JCheckBox();
        check_anteileMAK = new javax.swing.JCheckBox();
        jSeparator11 = new javax.swing.JSeparator();
        check_gewerbeamtshow = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        field_gewerbeamtName = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        field_gewerbeamtPlz = new javax.swing.JTextField();
        field_gewerbamtOrt = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        check_handelsregistershow = new javax.swing.JCheckBox();
        field_handelsregisterName = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        field_handelsregisterPlz = new javax.swing.JTextField();
        field_handelsregisterOrt = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        field_handelsregisterNummer = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel47 = new javax.swing.JLabel();
        field_gesellschafter = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        field_vermoegensschaden = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        panel_sonstiges = new javax.swing.JPanel();
        btnMaxCustom1 = new javax.swing.JButton();
        field_custom1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        field_custom2 = new javax.swing.JTextField();
        btnMaxCustom2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        field_custom3 = new javax.swing.JTextField();
        btnMaxCustom3 = new javax.swing.JButton();
        btnMaxCustom4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        field_custom4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        field_custom5 = new javax.swing.JTextField();
        btnMaxCustom5 = new javax.swing.JButton();
        btnMaxCustom6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        field_custom6 = new javax.swing.JTextField();
        field_custom7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnMaxCustom7 = new javax.swing.JButton();
        btnMaxCustom8 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        field_custom8 = new javax.swing.JTextField();
        btnMaxCustom9 = new javax.swing.JButton();
        field_custom9 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        field_custom10 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnMaxCustom10 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        btnMaxTextKommentar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(StammdatenDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        paneStammdaten.setName("paneStammdaten"); // NOI18N

        panel_basisdaten.setName("panel_basisdaten"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        field_firmenname.setText(resourceMap.getString("field_firmenname.text")); // NOI18N
        field_firmenname.setName("field_firmenname"); // NOI18N

        field_firmenZusatz.setName("field_firmenZusatz"); // NOI18N

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        field_firmenZusatz2.setName("field_firmenZusatz2"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        combo_rechtsform.setEditable(true);
        combo_rechtsform.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_rechtsform.setName("combo_rechtsform"); // NOI18N
        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        field_strasse.setName("field_strasse"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        field_plz.setName("field_plz"); // NOI18N

        field_ort.setName("field_ort"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        field_postfach.setName("field_postfach"); // NOI18N

        field_postfachOrt.setName("field_postfachOrt"); // NOI18N

        field_postfachPLZ.setName("field_postfachPLZ"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jSeparator5.setName("jSeparator5"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        combo_filialtyp.setEditable(true);
        combo_filialtyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_filialtyp.setName("combo_filialtyp"); // NOI18N
        combo_filialtyp.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_FILIAL_TYPEN));

        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        combo_anzahlmitarbeiter.setEditable(true);
        combo_anzahlmitarbeiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Mitarbeiter", "1-3 Mitarbeiter", "3-5 Mitarbeiter", "5-10 Mitarbeiter", "mehr als 10 Mitarbeiter" }));
        combo_anzahlmitarbeiter.setName("combo_anzahlmitarbeiter"); // NOI18N
        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));

        jSeparator6.setName("jSeparator6"); // NOI18N

        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N

        field_geschaeftsleiter.setName("field_geschaeftsleiter"); // NOI18N

        combo_gesellschaft.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine" }));
        combo_gesellschaft.setName("combo_gesellschaft"); // NOI18N
        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));

        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N

        javax.swing.GroupLayout panel_basisdatenLayout = new javax.swing.GroupLayout(panel_basisdaten);
        panel_basisdaten.setLayout(panel_basisdatenLayout);
        panel_basisdatenLayout.setHorizontalGroup(
            panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisdatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(field_postfach, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_postfachPLZ, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_postfachOrt, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_firmenZusatz2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addComponent(field_firmenZusatz, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addComponent(field_firmenname, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addGroup(panel_basisdatenLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combo_filialtyp, javax.swing.GroupLayout.Alignment.TRAILING, 0, 307, Short.MAX_VALUE)
                                    .addComponent(combo_rechtsform, 0, 307, Short.MAX_VALUE)))))
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(combo_anzahlmitarbeiter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_geschaeftsleiter, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisdatenLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(combo_gesellschaft, 0, 213, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_basisdatenLayout.setVerticalGroup(
            panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisdatenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_gesellschaft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(field_firmenname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(field_firmenZusatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(field_firmenZusatz2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(combo_rechtsform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(combo_filialtyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(combo_anzahlmitarbeiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(field_geschaeftsleiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(field_postfach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(field_postfachPLZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_postfachOrt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        paneStammdaten.addTab(resourceMap.getString("panel_basisdaten.TabConstraints.tabTitle"), panel_basisdaten); // NOI18N

        panel_erweitertedaten.setName("panel_erweitertedaten"); // NOI18N

        jLabel49.setText(resourceMap.getString("jLabel49.text")); // NOI18N
        jLabel49.setName("jLabel49"); // NOI18N

        field_bankname.setName("field_bankname"); // NOI18N

        jLabel50.setText(resourceMap.getString("jLabel50.text")); // NOI18N
        jLabel50.setToolTipText(resourceMap.getString("jLabel50.toolTipText")); // NOI18N
        jLabel50.setName("jLabel50"); // NOI18N

        field_bankKto.setName("field_bankKto"); // NOI18N

        field_bankBkz.setName("field_bankBkz"); // NOI18N

        jLabel51.setText(resourceMap.getString("jLabel51.text")); // NOI18N
        jLabel51.setToolTipText(resourceMap.getString("jLabel51.toolTipText")); // NOI18N
        jLabel51.setName("jLabel51"); // NOI18N

        jLabel52.setText(resourceMap.getString("jLabel52.text")); // NOI18N
        jLabel52.setName("jLabel52"); // NOI18N

        field_bankEigentuemer.setName("field_bankEigentuemer"); // NOI18N

        jLabel53.setText(resourceMap.getString("jLabel53.text")); // NOI18N
        jLabel53.setName("jLabel53"); // NOI18N

        field_bankIBAN.setName("field_bankIBAN"); // NOI18N

        jSeparator15.setName("jSeparator15"); // NOI18N

        field_bankBIC.setName("field_bankBIC"); // NOI18N

        jLabel54.setText(resourceMap.getString("jLabel54.text")); // NOI18N
        jLabel54.setName("jLabel54"); // NOI18N

        jSeparator16.setName("jSeparator16"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jxlist_verbandsmitgliedschaften.setName("jxlist_verbandsmitgliedschaften"); // NOI18N
        jScrollPane4.setViewportView(jxlist_verbandsmitgliedschaften);

        jLabel55.setText(resourceMap.getString("jLabel55.text")); // NOI18N
        jLabel55.setName("jLabel55"); // NOI18N

        btnAddVerbandsmitglied.setIcon(resourceMap.getIcon("btnAddVerbandsmitglied.icon")); // NOI18N
        btnAddVerbandsmitglied.setToolTipText(resourceMap.getString("btnAddVerbandsmitglied.toolTipText")); // NOI18N
        btnAddVerbandsmitglied.setName("btnAddVerbandsmitglied"); // NOI18N
        btnAddVerbandsmitglied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVerbandsmitgliedActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jxlist_beschwerdestelle.setName("jxlist_beschwerdestelle"); // NOI18N
        jScrollPane5.setViewportView(jxlist_beschwerdestelle);

        btnAddBeschwerdestellen.setIcon(resourceMap.getIcon("btnAddBeschwerdestellen.icon")); // NOI18N
        btnAddBeschwerdestellen.setToolTipText(resourceMap.getString("btnAddBeschwerdestellen.toolTipText")); // NOI18N
        btnAddBeschwerdestellen.setName("btnAddBeschwerdestellen"); // NOI18N
        btnAddBeschwerdestellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBeschwerdestellenActionPerformed(evt);
            }
        });

        jLabel56.setText(resourceMap.getString("jLabel56.text")); // NOI18N
        jLabel56.setName("jLabel56"); // NOI18N

        javax.swing.GroupLayout panel_erweitertedatenLayout = new javax.swing.GroupLayout(panel_erweitertedaten);
        panel_erweitertedaten.setLayout(panel_erweitertedatenLayout);
        panel_erweitertedatenLayout.setHorizontalGroup(
            panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertedatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_bankname, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                    .addGroup(panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_bankEigentuemer, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                    .addGroup(panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(field_bankKto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_bankBkz, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                    .addComponent(jSeparator15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(field_bankIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(field_bankBIC, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddVerbandsmitglied))
                    .addComponent(jLabel55)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddBeschwerdestellen))
                    .addComponent(jLabel56))
                .addContainerGap())
        );
        panel_erweitertedatenLayout.setVerticalGroup(
            panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertedatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(field_bankname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(field_bankEigentuemer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(field_bankBkz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_bankKto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(field_bankIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(field_bankBIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_erweitertedatenLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel55))
                    .addComponent(btnAddBeschwerdestellen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertedatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddVerbandsmitglied)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        paneStammdaten.addTab(resourceMap.getString("panel_erweitertedaten.TabConstraints.tabTitle"), panel_erweitertedaten); // NOI18N

        panel_kontaktdaten.setName("panel_kontaktdaten"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        field_telefon.setText(resourceMap.getString("field_telefon.text")); // NOI18N
        field_telefon.setName("field_telefon"); // NOI18N
        field_telefon.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        field_telefon2.setName("field_telefon2"); // NOI18N
        field_telefon2.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        field_mobil.setName("field_mobil"); // NOI18N
        field_mobil.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        field_mobil2.setName("field_mobil2"); // NOI18N
        field_mobil2.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        field_fax.setName("field_fax"); // NOI18N
        field_fax.setPreferredSize(new java.awt.Dimension(120, 25));

        jTextField16.setName("jTextField16"); // NOI18N
        jTextField16.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jTextField17.setName("jTextField17"); // NOI18N
        jTextField17.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        field_fax2.setName("field_fax2"); // NOI18N
        field_fax2.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        field_homepage.setName("field_homepage"); // NOI18N
        field_homepage.setPreferredSize(new java.awt.Dimension(120, 25));

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        text_emailsignatur.setColumns(20);
        text_emailsignatur.setRows(5);
        text_emailsignatur.setName("text_emailsignatur"); // NOI18N
        jScrollPane2.setViewportView(text_emailsignatur);

        btnMaxEmailSig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxEmailSig.setToolTipText(resourceMap.getString("btnMaxEmailSig.toolTipText")); // NOI18N
        btnMaxEmailSig.setName("btnMaxEmailSig"); // NOI18N
        btnMaxEmailSig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxEmailSigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_kontaktdatenLayout = new javax.swing.GroupLayout(panel_kontaktdaten);
        panel_kontaktdaten.setLayout(panel_kontaktdatenLayout);
        panel_kontaktdatenLayout.setHorizontalGroup(
            panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                        .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(field_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(field_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(field_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_kontaktdatenLayout.createSequentialGroup()
                                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18))
                            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(43, 43, 43)))
                        .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(field_mobil2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_telefon2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(32, 32, 32)
                        .addComponent(field_fax2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(field_homepage, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_kontaktdatenLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxEmailSig)))
                .addContainerGap())
        );
        panel_kontaktdatenLayout.setVerticalGroup(
            panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kontaktdatenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(field_telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(field_telefon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(field_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(field_mobil2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(field_fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(field_fax2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(field_homepage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_kontaktdatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxEmailSig)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        paneStammdaten.addTab(resourceMap.getString("panel_kontaktdaten.TabConstraints.tabTitle"), panel_kontaktdaten); // NOI18N

        panel_rechtlicheangaben.setName("panel_rechtlicheangaben"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jxlist_vertretpersonen.setName("jxlist_vertretpersonen"); // NOI18N
        jScrollPane3.setViewportView(jxlist_vertretpersonen);

        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N

        btnAddVertrettpers.setIcon(resourceMap.getIcon("btnAddVertrettpers.icon")); // NOI18N
        btnAddVertrettpers.setToolTipText(resourceMap.getString("btnAddVertrettpers.toolTipText")); // NOI18N
        btnAddVertrettpers.setName("btnAddVertrettpers"); // NOI18N
        btnAddVertrettpers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVertrettpersActionPerformed(evt);
            }
        });

        jSeparator7.setName("jSeparator7"); // NOI18N

        check_34c.setText(resourceMap.getString("check_34c.text")); // NOI18N
        check_34c.setName("check_34c"); // NOI18N

        check_34d.setText(resourceMap.getString("check_34d.text")); // NOI18N
        check_34d.setName("check_34d"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N

        combo_beratertyp.setEditable(true);
        combo_beratertyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Versicherungsmakler", "Versicherungsberater", "Versicherungsvertretter für ein VU", "Mehrfirmenvertretter" }));
        combo_beratertyp.setName("combo_beratertyp"); // NOI18N
        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));

        jSeparator9.setName("jSeparator9"); // NOI18N

        jLabel36.setText(resourceMap.getString("jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N

        field_ihkname.setName("field_ihkname"); // NOI18N

        btnMaxIHKName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxIHKName.setToolTipText(resourceMap.getString("btnMaxIHKName.toolTipText")); // NOI18N
        btnMaxIHKName.setName("btnMaxIHKName"); // NOI18N
        btnMaxIHKName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxIHKNameActionPerformed(evt);
            }
        });

        jLabel37.setText(resourceMap.getString("jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N

        field_ihknummer.setName("field_ihknummer"); // NOI18N

        jLabel38.setText(resourceMap.getString("jLabel38.text")); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N

        combo_ihkstatus.setEditable(true);
        combo_ihkstatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vorhanden", "Beantragt", "Nicht vorhanden" }));
        combo_ihkstatus.setName("combo_ihkstatus"); // NOI18N
        combo_rechtsform.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_RECHTSFORMEN_SHORT));

        jLabel39.setText(resourceMap.getString("jLabel39.text")); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N

        field_ihkbesonderheiten.setName("field_ihkbesonderheiten"); // NOI18N

        btnMaxIHKBesonderheiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxIHKBesonderheiten.setToolTipText(resourceMap.getString("btnMaxIHKBesonderheiten.toolTipText")); // NOI18N
        btnMaxIHKBesonderheiten.setName("btnMaxIHKBesonderheiten"); // NOI18N
        btnMaxIHKBesonderheiten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxIHKBesonderheitenActionPerformed(evt);
            }
        });

        jSeparator10.setName("jSeparator10"); // NOI18N

        jLabel40.setText(resourceMap.getString("jLabel40.text")); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N

        field_steuernummer.setName("field_steuernummer"); // NOI18N

        jLabel41.setText(resourceMap.getString("jLabel41.text")); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N

        field_ustid.setName("field_ustid"); // NOI18N

        javax.swing.GroupLayout panel_rechtlicheangabenLayout = new javax.swing.GroupLayout(panel_rechtlicheangaben);
        panel_rechtlicheangaben.setLayout(panel_rechtlicheangabenLayout);
        panel_rechtlicheangabenLayout.setHorizontalGroup(
            panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                        .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rechtlicheangabenLayout.createSequentialGroup()
                                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddVertrettpers))
                            .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                                .addComponent(check_34c)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(check_34d))
                            .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(88, 88, 88)
                                .addComponent(combo_beratertyp, 0, 213, Short.MAX_VALUE)
                                .addGap(41, 41, 41))
                            .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(field_ihkbesonderheiten, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                                    .addComponent(combo_ihkstatus, 0, 228, Short.MAX_VALUE)
                                    .addComponent(field_ihknummer, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxIHKBesonderheiten))
                            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(field_steuernummer, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(field_ustid, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
            .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel36)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(field_ihkname, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnMaxIHKName)
                    .addGap(12, 12, 12)))
        );
        panel_rechtlicheangabenLayout.setVerticalGroup(
            panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addGap(9, 9, 9)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddVertrettpers)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(check_34c)
                    .addComponent(check_34d))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(combo_beratertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                        .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_ihknummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(combo_ihkstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_ihkbesonderheiten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)))
                    .addComponent(btnMaxIHKBesonderheiten))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_steuernummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_ustid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_rechtlicheangabenLayout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panel_rechtlicheangabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_ihkname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addComponent(btnMaxIHKName))
                    .addContainerGap(213, Short.MAX_VALUE)))
        );

        paneStammdaten.addTab(resourceMap.getString("panel_rechtlicheangaben.TabConstraints.tabTitle"), panel_rechtlicheangaben); // NOI18N

        panel_rechtlicheangaben2.setName("panel_rechtlicheangaben2"); // NOI18N

        check_anteileVU.setText(resourceMap.getString("check_anteileVU.text")); // NOI18N
        check_anteileVU.setName("check_anteileVU"); // NOI18N

        check_anteileMAK.setText(resourceMap.getString("check_anteileMAK.text")); // NOI18N
        check_anteileMAK.setName("check_anteileMAK"); // NOI18N

        jSeparator11.setName("jSeparator11"); // NOI18N

        check_gewerbeamtshow.setText(resourceMap.getString("check_gewerbeamtshow.text")); // NOI18N
        check_gewerbeamtshow.setName("check_gewerbeamtshow"); // NOI18N

        jLabel42.setText(resourceMap.getString("jLabel42.text")); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N

        field_gewerbeamtName.setName("field_gewerbeamtName"); // NOI18N

        jLabel43.setText(resourceMap.getString("jLabel43.text")); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N

        field_gewerbeamtPlz.setName("field_gewerbeamtPlz"); // NOI18N

        field_gewerbamtOrt.setName("field_gewerbamtOrt"); // NOI18N

        jSeparator12.setName("jSeparator12"); // NOI18N

        check_handelsregistershow.setText(resourceMap.getString("check_handelsregistershow.text")); // NOI18N
        check_handelsregistershow.setName("check_handelsregistershow"); // NOI18N

        field_handelsregisterName.setName("field_handelsregisterName"); // NOI18N

        jLabel44.setText(resourceMap.getString("jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        field_handelsregisterPlz.setName("field_handelsregisterPlz"); // NOI18N

        field_handelsregisterOrt.setName("field_handelsregisterOrt"); // NOI18N

        jLabel46.setText(resourceMap.getString("jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        field_handelsregisterNummer.setName("field_handelsregisterNummer"); // NOI18N

        jSeparator13.setName("jSeparator13"); // NOI18N

        jLabel47.setText(resourceMap.getString("jLabel47.text")); // NOI18N
        jLabel47.setName("jLabel47"); // NOI18N

        field_gesellschafter.setToolTipText(resourceMap.getString("field_gesellschafter.toolTipText")); // NOI18N
        field_gesellschafter.setName("field_gesellschafter"); // NOI18N

        jLabel48.setText(resourceMap.getString("jLabel48.text")); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N

        field_vermoegensschaden.setText(resourceMap.getString("field_vermoegensschaden.text")); // NOI18N
        field_vermoegensschaden.setName("field_vermoegensschaden"); // NOI18N

        jSeparator14.setName("jSeparator14"); // NOI18N

        javax.swing.GroupLayout panel_rechtlicheangaben2Layout = new javax.swing.GroupLayout(panel_rechtlicheangaben2);
        panel_rechtlicheangaben2.setLayout(panel_rechtlicheangaben2Layout);
        panel_rechtlicheangaben2Layout.setHorizontalGroup(
            panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(check_anteileVU)
                    .addComponent(check_anteileMAK)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(check_gewerbeamtshow)
                    .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(94, 94, 94)
                        .addComponent(field_gewerbeamtName, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_gewerbeamtPlz, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_gewerbamtOrt, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(check_handelsregistershow)
                    .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_handelsregisterName, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_handelsregisterNummer, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                    .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(field_handelsregisterPlz, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_handelsregisterOrt, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(94, 94, 94)
                        .addComponent(field_gesellschafter, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                    .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_vermoegensschaden, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                    .addComponent(jSeparator14, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_rechtlicheangaben2Layout.setVerticalGroup(
            panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_rechtlicheangaben2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check_anteileVU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_anteileMAK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(field_vermoegensschaden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_gewerbeamtshow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(field_gewerbeamtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(field_gewerbeamtPlz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_gewerbamtOrt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_handelsregistershow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(field_handelsregisterName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(field_handelsregisterNummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(field_handelsregisterPlz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_handelsregisterOrt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_rechtlicheangaben2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(field_gesellschafter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        paneStammdaten.addTab(resourceMap.getString("panel_rechtlicheangaben2.TabConstraints.tabTitle"), panel_rechtlicheangaben2); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        field_custom1.setText(resourceMap.getString("field_custom1.text")); // NOI18N
        field_custom1.setName("field_custom1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        btnMaxCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom3.setToolTipText(resourceMap.getString("btnMaxCustom3.toolTipText")); // NOI18N
        btnMaxCustom3.setName("btnMaxCustom3"); // NOI18N
        btnMaxCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom3ActionPerformed(evt);
            }
        });

        btnMaxCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom4.setToolTipText(resourceMap.getString("btnMaxCustom4.toolTipText")); // NOI18N
        btnMaxCustom4.setName("btnMaxCustom4"); // NOI18N
        btnMaxCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom4ActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_custom4.setName("field_custom4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_custom5.setName("field_custom5"); // NOI18N

        btnMaxCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom5.setToolTipText(resourceMap.getString("btnMaxCustom5.toolTipText")); // NOI18N
        btnMaxCustom5.setName("btnMaxCustom5"); // NOI18N
        btnMaxCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom5ActionPerformed(evt);
            }
        });

        btnMaxCustom6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom6.setToolTipText(resourceMap.getString("btnMaxCustom6.toolTipText")); // NOI18N
        btnMaxCustom6.setName("btnMaxCustom6"); // NOI18N
        btnMaxCustom6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom6ActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        field_custom6.setName("field_custom6"); // NOI18N

        field_custom7.setName("field_custom7"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        btnMaxCustom7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom7.setToolTipText(resourceMap.getString("btnMaxCustom7.toolTipText")); // NOI18N
        btnMaxCustom7.setName("btnMaxCustom7"); // NOI18N
        btnMaxCustom7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom7ActionPerformed(evt);
            }
        });

        btnMaxCustom8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom8.setToolTipText(resourceMap.getString("btnMaxCustom8.toolTipText")); // NOI18N
        btnMaxCustom8.setName("btnMaxCustom8"); // NOI18N
        btnMaxCustom8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom8ActionPerformed(evt);
            }
        });

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        field_custom8.setName("field_custom8"); // NOI18N

        btnMaxCustom9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom9.setToolTipText(resourceMap.getString("btnMaxCustom9.toolTipText")); // NOI18N
        btnMaxCustom9.setName("btnMaxCustom9"); // NOI18N
        btnMaxCustom9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom9ActionPerformed(evt);
            }
        });

        field_custom9.setName("field_custom9"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        field_custom10.setName("field_custom10"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        btnMaxCustom10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom10.setToolTipText(resourceMap.getString("btnMaxCustom10.toolTipText")); // NOI18N
        btnMaxCustom10.setName("btnMaxCustom10"); // NOI18N
        btnMaxCustom10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom10ActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

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
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom5, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom5))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom7, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom7))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom6, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom9, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom8, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom8))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom10, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom10))))
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar)))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnMaxCustom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(btnMaxCustom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(btnMaxCustom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(btnMaxCustom4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnMaxCustom5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(btnMaxCustom6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(btnMaxCustom7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(btnMaxCustom8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(btnMaxCustom9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(btnMaxCustom10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneStammdaten.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

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
                .addContainerGap(224, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paneStammdaten, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(535, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(paneStammdaten, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE)))
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

    private void btnMaxCustom6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom6ActionPerformed
        MaximizeHelper.openMax(this.field_custom6, "Benutzerdefiniert 6");
    }//GEN-LAST:event_btnMaxCustom6ActionPerformed

    private void btnMaxCustom7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom7ActionPerformed
        MaximizeHelper.openMax(this.field_custom7, "Benutzerdefiniert 7");
    }//GEN-LAST:event_btnMaxCustom7ActionPerformed

    private void btnMaxCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom8ActionPerformed
        MaximizeHelper.openMax(this.field_custom8, "Benutzerdefiniert 8");
    }//GEN-LAST:event_btnMaxCustom8ActionPerformed

    private void btnMaxCustom9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom9ActionPerformed
       MaximizeHelper.openMax(this.field_custom9, "Benutzerdefiniert 9");
    }//GEN-LAST:event_btnMaxCustom9ActionPerformed

    private void btnMaxCustom10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom10ActionPerformed
        MaximizeHelper.openMax(this.field_custom10, "Benutzerdefiniert 10");
    }//GEN-LAST:event_btnMaxCustom10ActionPerformed

    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
    }//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    private void btnMaxEmailSigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxEmailSigActionPerformed
        MaximizeHelper.openMax(this.text_emailsignatur, "E-Mail Signatur");
    }//GEN-LAST:event_btnMaxEmailSigActionPerformed

    private void btnAddVertrettpersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVertrettpersActionPerformed
       
    }//GEN-LAST:event_btnAddVertrettpersActionPerformed

    private void btnMaxIHKNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxIHKNameActionPerformed
        MaximizeHelper.openMax(this.field_ihkname, "IHK Name");
    }//GEN-LAST:event_btnMaxIHKNameActionPerformed

    private void btnMaxIHKBesonderheitenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxIHKBesonderheitenActionPerformed
        MaximizeHelper.openMax(this.field_ihkbesonderheiten, "IHK Besonderheiten");
    }//GEN-LAST:event_btnMaxIHKBesonderheitenActionPerformed

    private void btnAddVerbandsmitgliedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVerbandsmitgliedActionPerformed
    
    }//GEN-LAST:event_btnAddVerbandsmitgliedActionPerformed

    private void btnAddBeschwerdestellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBeschwerdestellenActionPerformed
       
    }//GEN-LAST:event_btnAddBeschwerdestellenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(update == false) {
            mandant = new MandantenObj();
        }

        mandant.setBankBIC(this.field_bankBIC.getText());
        mandant.setBankLeitzahl(field_bankBkz.getText());
        mandant.setBankEigentuemer(field_bankEigentuemer.getText());
        mandant.setBankIBAN(field_bankIBAN.getText());
        mandant.setBankKonto(this.field_bankKto.getText());
        mandant.setBankName(this.field_bankname.getText());
        mandant.setCustom1(this.field_custom1.getText());
        mandant.setCustom2(this.field_custom2.getText());
        mandant.setCustom3(this.field_custom3.getText());
        mandant.setCustom4(this.field_custom4.getText());
        mandant.setCustom5(this.field_custom5.getText());
        mandant.setCustom6(this.field_custom6.getText());
        mandant.setCustom7(this.field_custom7.getText());
        mandant.setCustom8(this.field_custom8.getText());
        mandant.setCustom9(this.field_custom9.getText());
        mandant.setCustom10(this.field_custom10.getText());
        mandant.setFax(this.field_fax.getText());
        mandant.setFax2(this.field_fax2.getText());
        mandant.setFirmenZusatz(this.field_firmenZusatz.getText());
        mandant.setFirmenZusatz2(this.field_firmenZusatz2.getText());
        mandant.setFirmenName(this.field_firmenname.getText());
        mandant.setGeschaeftsleiter(this.field_geschaeftsleiter.getText());
        mandant.setGesellschafter(ArrayStringTools.stringToArray(this.field_gesellschafter.getText(), ","));
        mandant.setGewerbeamtOrt(this.field_gewerbamtOrt.getText());
        mandant.setGewerbeamtName(this.field_gewerbeamtName.getText());
        mandant.setGewerbeamtPLZ(this.field_gewerbeamtPlz.getText());
        mandant.setHandelsregisterName(this.field_handelsregisterName.getText());
        mandant.setHandelsregisterRegistrierNummer(this.field_handelsregisterNummer.getText());
        mandant.setHandelsregisterOrt(this.field_handelsregisterOrt.getText());
        mandant.setHandelsregisterPLZ(this.field_handelsregisterPlz.getText());
        mandant.setHomepage(this.field_homepage.getText());
        mandant.setIhkAbweichungen(this.field_ihkbesonderheiten.getText());
        mandant.setIhkName(this.field_ihkname.getText());
        mandant.setIhkRegistriernummer(this.field_ihknummer.getText());
        mandant.setMobil(this.field_mobil.getText());
        mandant.setMobil2(this.field_mobil2.getText());
        mandant.setOrt(this.field_ort.getText());
        mandant.setPlz(this.field_plz.getText());
        mandant.setPostfach(this.field_postfach.getText());
        mandant.setPostfachOrt(this.field_postfachOrt.getText());
        mandant.setPostfachPlz(this.field_postfachPLZ.getText());
        mandant.setSteuerNummer(this.field_steuernummer.getText());
        mandant.setStrasse(this.field_strasse.getText());
        mandant.setTelefon(this.field_telefon.getText());
        mandant.setTelefon2(this.field_telefon2.getText());
        mandant.setUstNummer(this.field_ustid.getText());
        mandant.setVermoegensHaftpflicht(this.field_vermoegensschaden.getText());
        mandant.set34c(this.check_34c.isSelected());
        mandant.set34d(this.check_34d.isSelected());
        mandant.setBeteiligungenMAK(this.check_anteileMAK.isSelected());
        mandant.setBeteiligungenVU(this.check_anteileVU.isSelected());
        mandant.setGewerbeamtShow(this.check_gewerbeamtshow.isSelected());
        mandant.setHandelsregisterShow(this.check_handelsregistershow.isSelected());
        mandant.setComments(this.text_comments.getText());
        mandant.setEmailSignatur(this.text_emailsignatur.getText());
        mandant.setFilialMitarbeiterZahl((String) this.combo_anzahlmitarbeiter.getSelectedItem());
        mandant.setBeraterTyp((String) this.combo_beratertyp.getSelectedItem());
        mandant.setFilialTyp((String) this.combo_filialtyp.getSelectedItem());
        // TODO
//        mandant.setParentId(this.combo_gesellschaft.getSelectedItem());

        mandant.setIhkStatus((String) this.combo_ihkstatus.getSelectedItem());
        mandant.setFirmenRechtsform((String) this.combo_rechtsform.getSelectedItem());

        if(update) {
            try {
                boolean success = MandantenSQLMethods.updatemandanten(DatabaseConnection.open(), mandant);
                if(success == false) {
                    ShowException.showException("Der Mandant mit der Id " + mandant.getId() + " kann nicht geupdated werden. Die Id wurde nicht gefunden.",
                        ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte Mandant nicht geupdated werden");
                    Log.databaselogger.fatal("Die Mandant mit der Id " + mandant.getId() + " konnte nicht gespeicher wurden. Die Id wurde nicht gefunden.");
                }
            } catch (SQLException e) {
                ShowException.showException("Der Mandant mit der Id " + mandant.getId() + " kann nicht geupdated werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Mandant nicht geupdated werden");
                Log.databaselogger.fatal("Die Mandant mit der Id " + mandant.getId() + " konnte nicht geupdated werden.", e);
            }
        } else {
            Log.logger.fatal("Fehler die Stammdaten konnten nicht erstellt werden.");
            System.out.println("Fehler: Stammdaten");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StammdatenDialog dialog = new StammdatenDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddBeschwerdestellen;
    private javax.swing.JButton btnAddVerbandsmitglied;
    private javax.swing.JButton btnAddVertrettpers;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom10;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnMaxCustom4;
    private javax.swing.JButton btnMaxCustom5;
    private javax.swing.JButton btnMaxCustom6;
    private javax.swing.JButton btnMaxCustom7;
    private javax.swing.JButton btnMaxCustom8;
    private javax.swing.JButton btnMaxCustom9;
    private javax.swing.JButton btnMaxEmailSig;
    private javax.swing.JButton btnMaxIHKBesonderheiten;
    private javax.swing.JButton btnMaxIHKName;
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox check_34c;
    private javax.swing.JCheckBox check_34d;
    private javax.swing.JCheckBox check_anteileMAK;
    private javax.swing.JCheckBox check_anteileVU;
    private javax.swing.JCheckBox check_gewerbeamtshow;
    private javax.swing.JCheckBox check_handelsregistershow;
    private javax.swing.JComboBox combo_anzahlmitarbeiter;
    private javax.swing.JComboBox combo_beratertyp;
    private javax.swing.JComboBox combo_filialtyp;
    private javax.swing.JComboBox combo_gesellschaft;
    private javax.swing.JComboBox combo_ihkstatus;
    private javax.swing.JComboBox combo_rechtsform;
    private javax.swing.JTextField field_bankBIC;
    private javax.swing.JTextField field_bankBkz;
    private javax.swing.JTextField field_bankEigentuemer;
    private javax.swing.JTextField field_bankIBAN;
    private javax.swing.JTextField field_bankKto;
    private javax.swing.JTextField field_bankname;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom10;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JTextField field_custom4;
    private javax.swing.JTextField field_custom5;
    private javax.swing.JTextField field_custom6;
    private javax.swing.JTextField field_custom7;
    private javax.swing.JTextField field_custom8;
    private javax.swing.JTextField field_custom9;
    private javax.swing.JTextField field_fax;
    private javax.swing.JTextField field_fax2;
    private javax.swing.JTextField field_firmenZusatz;
    private javax.swing.JTextField field_firmenZusatz2;
    private javax.swing.JTextField field_firmenname;
    private javax.swing.JTextField field_geschaeftsleiter;
    private javax.swing.JTextField field_gesellschafter;
    private javax.swing.JTextField field_gewerbamtOrt;
    private javax.swing.JTextField field_gewerbeamtName;
    private javax.swing.JTextField field_gewerbeamtPlz;
    private javax.swing.JTextField field_handelsregisterName;
    private javax.swing.JTextField field_handelsregisterNummer;
    private javax.swing.JTextField field_handelsregisterOrt;
    private javax.swing.JTextField field_handelsregisterPlz;
    private javax.swing.JTextField field_homepage;
    private javax.swing.JTextField field_ihkbesonderheiten;
    private javax.swing.JTextField field_ihkname;
    private javax.swing.JTextField field_ihknummer;
    private javax.swing.JTextField field_mobil;
    private javax.swing.JTextField field_mobil2;
    private javax.swing.JTextField field_ort;
    private javax.swing.JTextField field_plz;
    private javax.swing.JTextField field_postfach;
    private javax.swing.JTextField field_postfachOrt;
    private javax.swing.JTextField field_postfachPLZ;
    private javax.swing.JTextField field_steuernummer;
    private javax.swing.JTextField field_strasse;
    private javax.swing.JTextField field_telefon;
    private javax.swing.JTextField field_telefon2;
    private javax.swing.JTextField field_ustid;
    private javax.swing.JTextField field_vermoegensschaden;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private org.jdesktop.swingx.JXList jxlist_beschwerdestelle;
    private org.jdesktop.swingx.JXList jxlist_verbandsmitgliedschaften;
    private org.jdesktop.swingx.JXList jxlist_vertretpersonen;
    private javax.swing.JTabbedPane paneStammdaten;
    private javax.swing.JPanel panel_basisdaten;
    private javax.swing.JPanel panel_erweitertedaten;
    private javax.swing.JPanel panel_kontaktdaten;
    private javax.swing.JPanel panel_rechtlicheangaben;
    private javax.swing.JPanel panel_rechtlicheangaben2;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JTextArea text_comments;
    private javax.swing.JTextArea text_emailsignatur;
    // End of variables declaration//GEN-END:variables

}
