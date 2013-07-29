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
 * NewKundeDialog.java
 *
 * Created on Jul 8, 2010, 12:59:44 PM
 */
package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Bank.BankKontoObj;
import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Konstanten.Laender;
import de.maklerpoint.office.Kunden.KinderObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenKennungHelper;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Tools.ImageComboBoxRenderer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NewKundeDialog extends javax.swing.JDialog {

    /**
     *
     */
    private boolean update = false;
    private KundenObj kunde = null;
    private KinderObj[] kinder = null;

    /**
     * 
     * @param parent
     * @param modal
     */
    public NewKundeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupCombos();
    }

    /**
     * 
     * @param parent
     * @param modal
     * @param kunde
     */
    public NewKundeDialog(java.awt.Frame parent, boolean modal, KundenObj kunde) {
        super(parent, modal);
        this.update = true;
        this.kunde = kunde;
        initComponents();
        setupCombos();
        loadKunde();
    }

    private void setupCombos() {
        NewKundeHelper.loadSteuerKlassen(this);
        NewKundeHelper.loadSteuerKirche(this);
        NewKundeHelper.loadBetreuer(this, update);
        NewKundeHelper.loadWerber(this);
        NewKundeHelper.loadAnreden(this);
        NewKundeHelper.loadTitel(this);
        combo_nationalitaet.setModel(new DefaultComboBoxModel(Laender.NATIONALITAETEN));
        NewKundeHelper.loadLaender(this);
        NewKundeHelper.loadBundeslaender(0, this);
        

        NewKundeHelper.loadBerufsBesonderheiten(this);
        NewKundeHelper.loadBerufsStatus(this);
        this.spinnerKundenNr.setEnabled(true);

        this.ffield_einkommenbrutto.setValue(0.00);
        this.ffield_einkommennetto.setValue(0.00);

        label_eheschliess.setVisible(false);
        label_ehepartnerid.setVisible(false);
        this.combo_ehepartner.setVisible(false);
        this.date_eheschliessung.setVisible(false);

        this.combo_ehepartner.setModel(new DefaultComboBoxModel(ComboBoxGetter.getPrivatkundenCombo("Keiner")));

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

        if (kunde == null) {
            try {
                this.spinnerKundenNr.setValue(Integer.valueOf(KundenKennungHelper.getNextKundennummer(DatabaseConnection.open())));
            } catch (SQLException ex) {
                this.spinnerKundenNr.setValue(-1);
            }
        }
    }

    /**
     * 
     */
    private void loadKunde() {
        if (kunde == null) {
            return;
        }

        if (kunde.getAnrede().equalsIgnoreCase("herr")) {
            this.combo_anrede.setSelectedIndex(0);
        } else if (kunde.getAnrede().equalsIgnoreCase("frau")) {
            this.combo_anrede.setSelectedIndex(1);
        } else if (kunde.getAnrede().equalsIgnoreCase("herr und frau")) {
            this.combo_anrede.setSelectedIndex(2);
        } else if (kunde.getAnrede().equalsIgnoreCase("familie")) {
            this.combo_anrede.setSelectedIndex(3);
        } else {
            this.combo_anrede.setSelectedItem(kunde.getAnrede());
        }

        if (kunde.getDefaultKonto() != -1) {
            this.combo_defaultkonto.setSelectedIndex(kunde.getDefaultKonto());
        }

        this.field_beruf.setText(kunde.getBeruf());

        if (kunde.getBerufsTyp().equalsIgnoreCase("unbekannt")) {
            this.combo_berufstatus.setSelectedIndex(0);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("arbeitnehmer/-in")) {
            this.combo_berufstatus.setSelectedIndex(1);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("arbeiter/-in")) {
            this.combo_berufstatus.setSelectedIndex(2);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("beamte/-r")) {
            this.combo_berufstatus.setSelectedIndex(3);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("freiberufler/-in")) {
            this.combo_berufstatus.setSelectedIndex(4);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("arbeitslose/-r")) {
            this.combo_berufstatus.setSelectedIndex(5);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("hausmann/-frau")) {
            this.combo_berufstatus.setSelectedIndex(6);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("nicht erwerbstätige/-r")) {
            this.combo_berufstatus.setSelectedIndex(7);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("in berufsausbildung")) {
            this.combo_berufstatus.setSelectedIndex(8);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("praktikant/-in")) {
            this.combo_berufstatus.setSelectedIndex(9);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("schüler/-in")) {
            this.combo_berufstatus.setSelectedIndex(10);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("selbständige/-r")) {
            this.combo_berufstatus.setSelectedIndex(11);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("student/-in")) {
            this.combo_berufstatus.setSelectedIndex(12);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("wehrpflichtiger")) {
            this.combo_berufstatus.setSelectedIndex(13);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("zivildienstleistende/-r")) {
            this.combo_berufstatus.setSelectedIndex(14);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("pensionär/-in")) {
            this.combo_berufstatus.setSelectedIndex(15);
        } else if (kunde.getBerufsTyp().equalsIgnoreCase("rentner/-in")) {
            this.combo_berufstatus.setSelectedIndex(16);
        }

        this.combo_berufbesonderheiten.setSelectedItem(kunde.getBerufsBesonderheiten());

        this.combo_betreuer.setSelectedItem(BenutzerRegistry.getBenutzer(kunde.getBetreuerId()));

        if (kunde.getBundesland().equalsIgnoreCase("unbekannt")) {
            this.combo_bundesland.setSelectedIndex(0);
        } else if (kunde.getBundesland().equalsIgnoreCase("baden-württemberg")) {
            this.combo_bundesland.setSelectedIndex(1);
        } else if (kunde.getBundesland().equalsIgnoreCase("bayern")) {
            this.combo_bundesland.setSelectedIndex(1);
        } else if (kunde.getBundesland().equalsIgnoreCase("berlin")) {
            this.combo_bundesland.setSelectedIndex(2);
        } else if (kunde.getBundesland().equalsIgnoreCase("brandenburg")) {
            this.combo_bundesland.setSelectedIndex(3);
        } else if (kunde.getBundesland().equalsIgnoreCase("bremen")) {
            this.combo_bundesland.setSelectedIndex(4);
        } else if (kunde.getBundesland().equalsIgnoreCase("hamburg")) {
            this.combo_bundesland.setSelectedIndex(5);
        } else if (kunde.getBundesland().equalsIgnoreCase("hessen")) {
            this.combo_bundesland.setSelectedIndex(6);
        } else if (kunde.getBundesland().equalsIgnoreCase("mecklenburg-vorpommern")) {
            this.combo_bundesland.setSelectedIndex(7);
        } else if (kunde.getBundesland().equalsIgnoreCase("niedersachsen")) {
            this.combo_bundesland.setSelectedIndex(8);
        } else if (kunde.getBundesland().equalsIgnoreCase("nordrhein-westfalen")) {
            this.combo_bundesland.setSelectedIndex(9);
        } else if (kunde.getBundesland().equalsIgnoreCase("rheinland-pfalz")) {
            this.combo_bundesland.setSelectedIndex(10);
        } else if (kunde.getBundesland().equalsIgnoreCase("saarland")) {
            this.combo_bundesland.setSelectedIndex(11);
        } else if (kunde.getBundesland().equalsIgnoreCase("sachsen")) {
            this.combo_bundesland.setSelectedIndex(12);
        } else if (kunde.getBundesland().equalsIgnoreCase("sachsen-anhalt")) {
            this.combo_bundesland.setSelectedIndex(13);
        } else if (kunde.getBundesland().equalsIgnoreCase("schleswig-holstein")) {
            this.combo_bundesland.setSelectedIndex(14);
        } else if (kunde.getBundesland().equalsIgnoreCase("thüringen")) {
            this.combo_bundesland.setSelectedIndex(15);
        }

        this.area_comments.setText(kunde.getComments());
        //kunde.getCreated();
        this.field_custom1.setText(kunde.getCustom1());
        this.field_custom2.setText(kunde.getCustom2());
        this.field_custom3.setText(kunde.getCustom3());
        this.field_custom4.setText(kunde.getCustom4());
        this.field_custom5.setText(kunde.getCustom5());

        this.combo_ehepartner.setSelectedItem(KundenRegistry.getPrivatkunde(kunde.getEhepartnerKennung()));

        this.ffield_einkommenbrutto.setValue(kunde.getEinkommen());
        this.ffield_einkommennetto.setValue(kunde.getEinkommenNetto());

        this.field_adressezusatz.setText(kunde.getAdresseZusatz());
        this.field_adressezusatz2.setText(kunde.getAdresseZusatz2());

        // Todo
        //kunde.getFamilienPlanung();

        kunde.getFamilienStand();

        this.field_firma.setText(kunde.getFirma());

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if (kunde.getGeburtsdatum() != null) {
            try {
                this.date_geburtsdatum.setDate(df.parse(kunde.getGeburtsdatum()));
            } catch (Exception e) {
                Log.logger.warn("Konnte das Geburtsdatum des Kunde nicht laden", e);
            }
        }

        this.combo_comtype1.setSelectedIndex(kunde.getCommunication1Type());
        this.combo_comtype2.setSelectedIndex(kunde.getCommunication2Type());
        this.combo_comtype3.setSelectedIndex(kunde.getCommunication3Type());
        this.combo_comtype4.setSelectedIndex(kunde.getCommunication4Type());
        this.combo_comtype5.setSelectedIndex(kunde.getCommunication5Type());
        this.combo_comtype6.setSelectedIndex(kunde.getCommunication6Type());

        this.field_communication1.setText(kunde.getCommunication1());
        this.field_communication2.setText(kunde.getCommunication2());
        this.field_communication3.setText(kunde.getCommunication3());
        this.field_communication4.setText(kunde.getCommunication4());
        this.field_communication5.setText(kunde.getCommunication5());
        this.field_communication6.setText(kunde.getCommunication6());


        this.spinnerKundenNr.setValue(Integer.valueOf(kunde.getKundenNr()));
        this.spinnerKundenNr.setEnabled(false); // Nicht mehr änderbar

        this.combo_land.setSelectedItem(kunde.getLand());

        //kunde.getModified();

        this.field_nachname.setText(kunde.getNachname());
        this.field_plz.setText(String.valueOf(kunde.getPlz()));

        this.field_religion.setText(kunde.getReligion());
        this.field_ort.setText(kunde.getStadt());

        //kunde.getStatus();

        if (kunde.getSteuerklasse() == null) {
            this.combo_steuerklasse.setSelectedIndex(0);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("i")) {
            this.combo_steuerklasse.setSelectedIndex(1);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("ii")) {
            this.combo_steuerklasse.setSelectedIndex(2);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("iii")) {
            this.combo_steuerklasse.setSelectedIndex(3);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("iv")) {
            this.combo_steuerklasse.setSelectedIndex(4);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("v")) {
            this.combo_steuerklasse.setSelectedIndex(5);
        } else if (kunde.getSteuerklasse().equalsIgnoreCase("vi")) {
            this.combo_steuerklasse.setSelectedIndex(6);
        } else {
            this.combo_steuerklasse.setSelectedIndex(0);
        }

        this.combo_kirchensteuer.setSelectedItem(kunde.getKirchenSteuer());

        if (kunde.getKinderFreibetrag() != null) {
            this.combo_kinderfreibetrag.setSelectedItem(kunde.getKinderFreibetrag());
        }

        if (kunde.getSteuertabelle() == null) {
            this.combo_steuerart.setSelectedIndex(0);
        } else if (kunde.getSteuertabelle().equalsIgnoreCase("grundtabelle")) {
            this.combo_steuerart.setSelectedIndex(1);
        } else if (kunde.getSteuertabelle().equalsIgnoreCase("splittingtabelle")) {
            this.combo_steuerart.setSelectedIndex(2);
        } else {
            this.combo_steuerart.setSelectedIndex(0);
        }

        this.field_strasse.setText(kunde.getStreet());

        if (kunde.getTitel() == null) {
            this.combo_titel.setSelectedIndex(0);
        } else if (kunde.getTitel().equalsIgnoreCase("dr.")) {
            this.combo_titel.setSelectedIndex(1);
        } else if (kunde.getTitel().equalsIgnoreCase("prof.")) {
            this.combo_titel.setSelectedIndex(2);
        } else if (kunde.getTitel().equalsIgnoreCase("prof. dr.")) {
            this.combo_titel.setSelectedIndex(3);
        } else {
            this.combo_titel.setSelectedIndex(0);
        }

        this.combo_familienstand.setSelectedItem(kunde.getFamilienStand());

        // PRIVAT
        // kunde.getTyp();

        this.field_vorname.setText(kunde.getVorname());
        this.field_vorname2.setText(kunde.getVorname2());

        this.field_weiterVornamen.setText(kunde.getVornameWeitere());
        this.field_weiterePersonen.setText(kunde.getWeiterePersonen());

        this.field_weiterePersonenInfo.setText(kunde.getWeiterePersonenInfo());

        if (kunde.isBeamter()) {
            this.check_beamter.setSelected(true);
        } else {
            this.check_beamter.setSelected(false);
        }

        if (kunde.isOeffentlicherDienst()) {
            this.check_oeffentlicherdienst.setSelected(true);
        } else {
            this.check_oeffentlicherdienst.setSelected(false);
        }


        this.combo_rolleimhaushalt.setSelectedItem(kunde.getRolleImHaushalt());

        this.combo_nationalitaet.setSelectedItem(kunde.getNationalitaet());
        this.combo_buerotaetigkeit.setSelectedItem(kunde.getAnteilBuerotaetigkeit());

        this.field_geburtsname.setText(kunde.getGeburtsname());


        if (kunde.getEhedatum() != null && kunde.getEhedatum().length() > 0) {
            try {
                this.date_eheschliessung.setDate(df.parse(kunde.getEhedatum()));
            } catch (Exception e) {
                Log.logger.warn("Konnte das Ehedatum des Kunden nicht laden", e);
            }
        }

        this.combo_werber.setSelectedItem(KundenRegistry.getKunde(kunde.getWerberKennung()));

        this.setTitle("Kunde bearbeiten: " + kunde.getKundenNr() + " [" + kunde.getNachname() + ", " + kunde.getVorname() + "]");
    }

//    /**
//     *
//     * @param parentId
//     */
//
//    public void loadKinder(int parentId){
//        try {
//            kinder = KinderSQLMethods.loadKinder(DatabaseConnection.open(), parentId);
//
//            if(kinder == null || kinder.length == 0)
//                return;
//
//            for(int i = 0; i < kinder.length; i++) {
//                JPanel panelKinder = new JPanel();
//                JLabel label_vorname = new JLabel();
//                label_vorname.setText("Vorname");
//                JLabel label_nachname = new JLabel();
//                label_nachname.setText("Nachname");
//                JLabel label_geburtsdatum = new JLabel();
//                label_geburtsdatum.setText("Geburtsdatum");
//                JLabel label_beruf = new JLabel();
//                label_beruf.setText("Beruf");
//                JLabel label_wohnort = new JLabel();
//                label_wohnort.setText("Wohnort");
//                JTextField kind_vorname = new JTextField();
//                kind_vorname.setText(kinder[i].getKindVorname());
//
//                JTextField kind_nachname = new JTextField();
//                kind_nachname.setText(kinder[i].getKindName());
//
//                DateControl kind_geburtstag = new DateControl();
//                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//                if(kinder[i].getKindGeburtsdatum() != null)
//                    kind_geburtstag.setDate(df.parse(kinder[i].getKindGeburtsdatum()));
//
//                JTextField kind_beruf = new JTextField();
//                kind_beruf.setText(kinder[i].getKindBeruf());
//
//                String data[] = new String[]{"Unbekannt", "Bei den Eltern", "Beim Vater",
//                        "Bei der Mutter", "Eigene Wohnung"};
//                JComboBox combo_wohnort = new JComboBox(data);
//
//                panel_holder.add(panelKinder);
//            }
//
//            panel_holder.revalidate();
//        } catch (SQLException e) {
//            ShowException.showException("Die Kinderliste konnte nicht aus der Datenbank geladen werden. Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
//                    ExceptionDialogGui.LEVEL_WARNING, e.getLocalizedMessage(), "Schwerwiegend: Konnte Kinder nicht laden");
//                Log.databaselogger.fatal("Fehler: Konnte Kinder nicht aus der Datenbank laden", e);
//        } catch (Exception e) {
//             ShowException.showException("Beim laden der Liste der Kinder kam es zu einem Fehler.",
//                    ExceptionDialogGui.LEVEL_WARNING, e.getLocalizedMessage(), "Schwerwiegend: Konnte Kinder nicht laden");
//             Log.databaselogger.warn("Konnte Kinder nicht laden", e);
//        }
//    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedNewKunde = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        field_vorname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        field_nachname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        field_strasse = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        field_ort = new javax.swing.JTextField();
        field_plz = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        combo_bundesland = new javax.swing.JComboBox();
        combo_land = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        combo_titel = new javax.swing.JComboBox();
        combo_anrede = new javax.swing.JComboBox();
        combo_nationalitaet = new javax.swing.JComboBox();
        field_adressezusatz = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        date_geburtsdatum = new com.toedter.calendar.JDateChooser();
        spinnerKundenNr = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        field_adressezusatz2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        panel_erweitert = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        field_vorname2 = new javax.swing.JTextField();
        field_weiterVornamen = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        field_geburtsname = new javax.swing.JTextField();
        combo_familienstand = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        combo_rolleimhaushalt = new javax.swing.JComboBox();
        jSeparator10 = new javax.swing.JSeparator();
        label_eheschliess = new javax.swing.JLabel();
        label_ehepartnerid = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel55 = new javax.swing.JLabel();
        field_weiterePersonen = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        field_weiterePersonenInfo = new javax.swing.JTextField();
        date_eheschliessung = new com.toedter.calendar.JDateChooser();
        btnMaxWeiterePers = new javax.swing.JButton();
        btnMaxWeitererPersInfo = new javax.swing.JButton();
        combo_ehepartner = new javax.swing.JComboBox();
        panel_kontakt = new javax.swing.JPanel();
        combo_comtype1 = new javax.swing.JComboBox();
        field_communication1 = new javax.swing.JTextField();
        field_communication2 = new javax.swing.JTextField();
        field_communication3 = new javax.swing.JTextField();
        field_communication4 = new javax.swing.JTextField();
        field_communication5 = new javax.swing.JTextField();
        field_communication6 = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        combo_comtype6 = new javax.swing.JComboBox();
        combo_comtype5 = new javax.swing.JComboBox();
        combo_comtype4 = new javax.swing.JComboBox();
        combo_comtype2 = new javax.swing.JComboBox();
        combo_comtype3 = new javax.swing.JComboBox();
        panel_sonstiges = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_comments = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        combo_betreuer = new javax.swing.JComboBox();
        jLabel57 = new javax.swing.JLabel();
        combo_werber = new javax.swing.JComboBox();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        btnMaxCustom1 = new javax.swing.JButton();
        btnMaxCustom2 = new javax.swing.JButton();
        btnMaxCustom3 = new javax.swing.JButton();
        btnMaxCustom4 = new javax.swing.JButton();
        field_custom5 = new javax.swing.JTextField();
        field_custom4 = new javax.swing.JTextField();
        field_custom3 = new javax.swing.JTextField();
        field_custom2 = new javax.swing.JTextField();
        field_custom1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnMaxCustom5 = new javax.swing.JButton();
        jSeparator17 = new javax.swing.JSeparator();
        btnMaxComments = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        combo_defaultkonto = new javax.swing.JComboBox();
        panel_steuer = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        combo_steuerart = new javax.swing.JComboBox();
        combo_steuerklasse = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        combo_kirchensteuer = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        combo_kinderfreibetrag = new javax.swing.JComboBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel58 = new javax.swing.JLabel();
        field_religion = new javax.swing.JTextField();
        panel_beruf = new javax.swing.JPanel();
        check_oeffentlicherdienst = new javax.swing.JCheckBox();
        check_beamter = new javax.swing.JCheckBox();
        combo_berufstatus = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        combo_berufbesonderheiten = new javax.swing.JComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        field_firma = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        field_beruf = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        combo_buerotaetigkeit = new javax.swing.JComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        combo_rentenbegin = new javax.swing.JComboBox();
        ffield_einkommenbrutto = new javax.swing.JFormattedTextField();
        ffield_einkommennetto = new javax.swing.JFormattedTextField();
        panel_menu = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NewKundeDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        tabbedNewKunde.setName("tabbedNewKunde"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        field_vorname.setName("field_vorname"); // NOI18N
        field_vorname.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_nachname.setName("field_nachname"); // NOI18N
        field_nachname.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        field_strasse.setName("field_strasse"); // NOI18N
        field_strasse.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_ort.setName("field_ort"); // NOI18N
        field_ort.setPreferredSize(new java.awt.Dimension(140, 25));

        field_plz.setMaximumSize(new java.awt.Dimension(50, 25));
        field_plz.setMinimumSize(new java.awt.Dimension(50, 25));
        field_plz.setName("field_plz"); // NOI18N
        field_plz.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        combo_bundesland.setEditable(true);
        combo_bundesland.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_bundesland.setName("combo_bundesland"); // NOI18N

        combo_land.setEditable(true);
        combo_land.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_land.setName("combo_land"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        combo_titel.setEditable(true);
        combo_titel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_titel.setName("combo_titel"); // NOI18N

        combo_anrede.setEditable(true);
        combo_anrede.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_anrede.setName("combo_anrede"); // NOI18N

        combo_nationalitaet.setEditable(true);
        combo_nationalitaet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Albanisch", "Belgisch", "Deutsch", "Dänisch", "Englisch", "Französisch", "Irisch", "Italienisch", "Kroatisch", "Luxemburgisch", "Niederländisch", "Russisch", "Schottisch", "Schwedisch", "Schweizerisch", "Slowenisch", "Türkisch", "Österreichisch" }));
        combo_nationalitaet.setSelectedIndex(2);
        combo_nationalitaet.setName("combo_nationalitaet"); // NOI18N

        field_adressezusatz.setName("field_adressezusatz"); // NOI18N
        field_adressezusatz.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        date_geburtsdatum.setName("date_geburtsdatum"); // NOI18N

        spinnerKundenNr.setName("spinnerKundenNr"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel44.setText(resourceMap.getString("jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        field_adressezusatz2.setName("field_adressezusatz2"); // NOI18N
        field_adressezusatz2.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel46.setText(resourceMap.getString("jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        jSeparator12.setName("jSeparator12"); // NOI18N

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_nachname, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addComponent(field_vorname, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel4)
                            .addComponent(jLabel45))
                        .addGap(35, 35, 35)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_adressezusatz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(field_strasse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(field_adressezusatz2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                                .addComponent(field_plz, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(combo_land, 0, 309, Short.MAX_VALUE)
                            .addComponent(combo_bundesland, 0, 309, Short.MAX_VALUE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(combo_nationalitaet, 0, 306, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel19)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_titel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_anrede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinnerKundenNr, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerKundenNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(combo_anrede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_titel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44)))
                .addGap(12, 12, 12)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_nachname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_strasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_adressezusatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_adressezusatz2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_ort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(field_plz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_bundesland, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_land, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_nationalitaet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        tabbedNewKunde.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_erweitert.setName("panel_erweitert"); // NOI18N

        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N

        field_vorname2.setName("field_vorname2"); // NOI18N
        field_vorname2.setPreferredSize(new java.awt.Dimension(150, 25));

        field_weiterVornamen.setName("field_weiterVornamen"); // NOI18N
        field_weiterVornamen.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N

        jSeparator9.setName("jSeparator9"); // NOI18N

        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N

        field_geburtsname.setName("field_geburtsname"); // NOI18N
        field_geburtsname.setPreferredSize(new java.awt.Dimension(150, 25));

        combo_familienstand.setEditable(true);
        combo_familienstand.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "Ledig / alleinstehend", "Verheiratet", "Verwitwet", "Geschieden", "Eheähnliche Gemeinschaft" }));
        combo_familienstand.setName("combo_familienstand"); // NOI18N
        combo_familienstand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_familienstandActionPerformed(evt);
            }
        });

        jLabel36.setText(resourceMap.getString("jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N

        jLabel37.setText(resourceMap.getString("jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N

        combo_rolleimhaushalt.setEditable(true);
        combo_rolleimhaushalt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "Ehepartner", "Elternteil", "Enkel", "Freund(in)", "Geschwister", "Großeltern", "Kind", "Lebenspartner", "Nichte/Neffe", "Cousin(e)", "Onkel/Tante", "Verschwägert" }));
        combo_rolleimhaushalt.setName("combo_rolleimhaushalt"); // NOI18N

        jSeparator10.setName("jSeparator10"); // NOI18N

        label_eheschliess.setText(resourceMap.getString("label_eheschliess.text")); // NOI18N
        label_eheschliess.setName("label_eheschliess"); // NOI18N

        label_ehepartnerid.setText(resourceMap.getString("label_ehepartnerid.text")); // NOI18N
        label_ehepartnerid.setName("label_ehepartnerid"); // NOI18N

        jSeparator14.setName("jSeparator14"); // NOI18N

        jLabel55.setText(resourceMap.getString("jLabel55.text")); // NOI18N
        jLabel55.setName("jLabel55"); // NOI18N

        field_weiterePersonen.setName("field_weiterePersonen"); // NOI18N
        field_weiterePersonen.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel56.setText(resourceMap.getString("jLabel56.text")); // NOI18N
        jLabel56.setName("jLabel56"); // NOI18N

        field_weiterePersonenInfo.setName("field_weiterePersonenInfo"); // NOI18N
        field_weiterePersonenInfo.setPreferredSize(new java.awt.Dimension(150, 25));

        date_eheschliessung.setName("date_eheschliessung"); // NOI18N

        btnMaxWeiterePers.setIcon(resourceMap.getIcon("btnMaxWeiterePers.icon")); // NOI18N
        btnMaxWeiterePers.setToolTipText(resourceMap.getString("btnMaxWeiterePers.toolTipText")); // NOI18N
        btnMaxWeiterePers.setName("btnMaxWeiterePers"); // NOI18N
        btnMaxWeiterePers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxWeiterePersActionPerformed(evt);
            }
        });

        btnMaxWeitererPersInfo.setIcon(resourceMap.getIcon("btnMaxWeitererPersInfo.icon")); // NOI18N
        btnMaxWeitererPersInfo.setToolTipText(resourceMap.getString("btnMaxWeitererPersInfo.toolTipText")); // NOI18N
        btnMaxWeitererPersInfo.setName("btnMaxWeitererPersInfo"); // NOI18N
        btnMaxWeitererPersInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxWeitererPersInfoActionPerformed(evt);
            }
        });

        combo_ehepartner.setEditable(true);
        combo_ehepartner.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keiner" }));
        combo_ehepartner.setName("combo_ehepartner"); // NOI18N

        javax.swing.GroupLayout panel_erweitertLayout = new javax.swing.GroupLayout(panel_erweitert);
        panel_erweitert.setLayout(panel_erweitertLayout);
        panel_erweitertLayout.setHorizontalGroup(
            panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(jSeparator10, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertLayout.createSequentialGroup()
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel_erweitertLayout.createSequentialGroup()
                                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(combo_familienstand, 0, 269, Short.MAX_VALUE)
                                    .addComponent(combo_rolleimhaushalt, 0, 269, Short.MAX_VALUE)))
                            .addGroup(panel_erweitertLayout.createSequentialGroup()
                                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(field_weiterVornamen, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                    .addComponent(field_geburtsname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(field_vorname2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(26, 26, 26))
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel56))
                        .addGap(18, 18, 18)
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(field_weiterePersonenInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(field_weiterePersonen, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMaxWeiterePers)
                            .addComponent(btnMaxWeitererPersInfo))
                        .addGap(26, 26, 26))
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(jSeparator14, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(label_ehepartnerid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(combo_ehepartner, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(label_eheschliess)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(date_eheschliessung, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        panel_erweitertLayout.setVerticalGroup(
            panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_geburtsname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_vorname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_weiterVornamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_familienstand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(combo_rolleimhaushalt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_weiterePersonen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_weiterePersonenInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56)))
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(btnMaxWeiterePers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMaxWeitererPersInfo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_eheschliess)
                    .addComponent(date_eheschliessung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ehepartnerid)
                    .addComponent(combo_ehepartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        tabbedNewKunde.addTab(resourceMap.getString("panel_erweitert.TabConstraints.tabTitle"), panel_erweitert); // NOI18N

        panel_kontakt.setName("panel_kontakt"); // NOI18N

        combo_comtype1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype1.setName("combo_comtype1"); // NOI18N

        field_communication1.setName("field_communication1"); // NOI18N

        field_communication2.setName("field_communication2"); // NOI18N

        field_communication3.setName("field_communication3"); // NOI18N

        field_communication4.setName("field_communication4"); // NOI18N

        field_communication5.setName("field_communication5"); // NOI18N

        field_communication6.setName("field_communication6"); // NOI18N

        jSeparator18.setName("jSeparator18"); // NOI18N

        combo_comtype6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype6.setName("combo_comtype6"); // NOI18N

        combo_comtype5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype5.setName("combo_comtype5"); // NOI18N

        combo_comtype4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype4.setName("combo_comtype4"); // NOI18N

        combo_comtype2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype2.setName("combo_comtype2"); // NOI18N

        combo_comtype3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype3.setName("combo_comtype3"); // NOI18N

        javax.swing.GroupLayout panel_kontaktLayout = new javax.swing.GroupLayout(panel_kontakt);
        panel_kontakt.setLayout(panel_kontaktLayout);
        panel_kontaktLayout.setHorizontalGroup(
            panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kontaktLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_kontaktLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_kontaktLayout.createSequentialGroup()
                        .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
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
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        tabbedNewKunde.addTab(resourceMap.getString("panel_kontakt.TabConstraints.tabTitle"), panel_kontakt); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane1.viewportBorder.title"))); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        area_comments.setColumns(20);
        area_comments.setRows(5);
        area_comments.setName("area_comments"); // NOI18N
        jScrollPane1.setViewportView(area_comments);

        jLabel54.setText(resourceMap.getString("jLabel54.text")); // NOI18N
        jLabel54.setName("jLabel54"); // NOI18N

        combo_betreuer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", " " }));
        combo_betreuer.setName("combo_betreuer"); // NOI18N

        jLabel57.setText(resourceMap.getString("jLabel57.text")); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N

        combo_werber.setEditable(true);
        combo_werber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_werber.setName("combo_werber"); // NOI18N

        jSeparator15.setName("jSeparator15"); // NOI18N

        jSeparator16.setName("jSeparator16"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        btnMaxCustom1.setIcon(resourceMap.getIcon("btnMaxCustom1.icon")); // NOI18N
        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
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

        field_custom5.setName("field_custom5"); // NOI18N

        field_custom4.setName("field_custom4"); // NOI18N

        field_custom3.setName("field_custom3"); // NOI18N

        field_custom2.setName("field_custom2"); // NOI18N

        field_custom1.setName("field_custom1"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        btnMaxCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom5.setToolTipText(resourceMap.getString("btnMaxCustom5.toolTipText")); // NOI18N
        btnMaxCustom5.setName("btnMaxCustom5"); // NOI18N
        btnMaxCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom5ActionPerformed(evt);
            }
        });

        jSeparator17.setName("jSeparator17"); // NOI18N

        btnMaxComments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxComments.setToolTipText(resourceMap.getString("btnMaxComments.toolTipText")); // NOI18N
        btnMaxComments.setName("btnMaxComments"); // NOI18N
        btnMaxComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCommentsActionPerformed(evt);
            }
        });

        jLabel59.setText(resourceMap.getString("jLabel59.text")); // NOI18N
        jLabel59.setName("jLabel59"); // NOI18N

        combo_defaultkonto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Konten vorhanden" }));
        combo_defaultkonto.setName("combo_defaultkonto"); // NOI18N

        javax.swing.GroupLayout panel_sonstigesLayout = new javax.swing.GroupLayout(panel_sonstiges);
        panel_sonstiges.setLayout(panel_sonstigesLayout);
        panel_sonstigesLayout.setHorizontalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator15, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom5, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom5))
                            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom4, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxComments))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel57))
                        .addGap(18, 18, 18)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_werber, 0, 322, Short.MAX_VALUE)
                            .addComponent(combo_betreuer, 0, 322, Short.MAX_VALUE)))
                    .addGroup(panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addGap(18, 18, 18)
                        .addComponent(combo_defaultkonto, 0, 291, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_betreuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_werber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_defaultkonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addComponent(btnMaxCustom4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_custom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(btnMaxCustom5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxComments)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        tabbedNewKunde.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        panel_steuer.setName("panel_steuer"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        combo_steuerart.setEditable(true);
        combo_steuerart.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_steuerart.setName("combo_steuerart"); // NOI18N

        combo_steuerklasse.setEditable(true);
        combo_steuerklasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_steuerklasse.setName("combo_steuerklasse"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        combo_kirchensteuer.setEditable(true);
        combo_kirchensteuer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_kirchensteuer.setName("combo_kirchensteuer"); // NOI18N

        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        combo_kinderfreibetrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1,0", "1,5", "2,0", "2,5", "3,0", "3,5", "4,0", "4,5", "5,0", "5,5", "6,0" }));
        combo_kinderfreibetrag.setName("combo_kinderfreibetrag"); // NOI18N

        jSeparator8.setName("jSeparator8"); // NOI18N

        jLabel58.setText(resourceMap.getString("jLabel58.text")); // NOI18N
        jLabel58.setName("jLabel58"); // NOI18N

        field_religion.setName("field_religion"); // NOI18N
        field_religion.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout panel_steuerLayout = new javax.swing.GroupLayout(panel_steuer);
        panel_steuer.setLayout(panel_steuerLayout);
        panel_steuerLayout.setHorizontalGroup(
            panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_steuerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_steuerLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                        .addComponent(combo_kinderfreibetrag, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addGroup(panel_steuerLayout.createSequentialGroup()
                        .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(jLabel30)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addGap(73, 73, 73)
                        .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_steuerart, javax.swing.GroupLayout.Alignment.TRAILING, 0, 237, Short.MAX_VALUE)
                            .addComponent(combo_kirchensteuer, 0, 237, Short.MAX_VALUE)
                            .addComponent(field_religion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(combo_steuerklasse, javax.swing.GroupLayout.Alignment.TRAILING, 0, 237, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_steuerLayout.setVerticalGroup(
            panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_steuerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(combo_steuerart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_steuerklasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(field_religion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_kirchensteuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_steuerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_kinderfreibetrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(329, Short.MAX_VALUE))
        );

        NewKundeHelper.loadSteuerArten(this);

        tabbedNewKunde.addTab(resourceMap.getString("panel_steuer.TabConstraints.tabTitle"), panel_steuer); // NOI18N

        panel_beruf.setName("panel_beruf"); // NOI18N

        check_oeffentlicherdienst.setText(resourceMap.getString("check_oeffentlicherdienst.text")); // NOI18N
        check_oeffentlicherdienst.setName("check_oeffentlicherdienst"); // NOI18N

        check_beamter.setText(resourceMap.getString("check_beamter.text")); // NOI18N
        check_beamter.setName("check_beamter"); // NOI18N

        combo_berufstatus.setEditable(true);
        combo_berufstatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_berufstatus.setName("combo_berufstatus"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        combo_berufbesonderheiten.setEditable(true);
        combo_berufbesonderheiten.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_berufbesonderheiten.setName("combo_berufbesonderheiten"); // NOI18N

        jSeparator5.setName("jSeparator5"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        field_firma.setText(resourceMap.getString("field_firma.text")); // NOI18N
        field_firma.setName("field_firma"); // NOI18N
        field_firma.setPreferredSize(new java.awt.Dimension(180, 25));

        jSeparator6.setName("jSeparator6"); // NOI18N

        field_beruf.setName("field_beruf"); // NOI18N
        field_beruf.setPreferredSize(new java.awt.Dimension(180, 25));

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        combo_buerotaetigkeit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100%", "90%", "80%", "70%", "60%", "50%", "40%", "30%", "20%", "10%", "0%" }));
        combo_buerotaetigkeit.setName("combo_buerotaetigkeit"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        combo_rentenbegin.setEditable(true);
        combo_rentenbegin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "mit 67 Jahren", "mit 65 Jahren", "mit 63 Jahren" }));
        combo_rentenbegin.setName("combo_rentenbegin"); // NOI18N

        ffield_einkommenbrutto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_einkommenbrutto.setText(resourceMap.getString("ffield_einkommenbrutto.text")); // NOI18N
        ffield_einkommenbrutto.setName("ffield_einkommenbrutto"); // NOI18N

        ffield_einkommennetto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ffield_einkommennetto.setText(resourceMap.getString("ffield_einkommennetto.text")); // NOI18N
        ffield_einkommennetto.setName("ffield_einkommennetto"); // NOI18N

        javax.swing.GroupLayout panel_berufLayout = new javax.swing.GroupLayout(panel_beruf);
        panel_beruf.setLayout(panel_berufLayout);
        panel_berufLayout.setHorizontalGroup(
            panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_berufLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_firma, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                            .addComponent(field_beruf, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_berufLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                        .addComponent(combo_buerotaetigkeit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_berufLayout.createSequentialGroup()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addComponent(check_oeffentlicherdienst)
                        .addContainerGap(271, Short.MAX_VALUE))
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addComponent(check_beamter)
                        .addContainerGap(333, Short.MAX_VALUE))
                    .addGroup(panel_berufLayout.createSequentialGroup()
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ffield_einkommenbrutto, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(combo_rentenbegin, 0, 228, Short.MAX_VALUE)
                            .addComponent(ffield_einkommennetto, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_berufLayout.createSequentialGroup()
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_berufLayout.createSequentialGroup()
                                .addComponent(combo_berufbesonderheiten, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(panel_berufLayout.createSequentialGroup()
                                .addComponent(combo_berufstatus, 0, 259, Short.MAX_VALUE)
                                .addGap(12, 12, 12))))))
        );
        panel_berufLayout.setVerticalGroup(
            panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_berufLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(combo_berufstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_berufbesonderheiten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(23, 23, 23)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(field_beruf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(field_firma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_buerotaetigkeit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(ffield_einkommenbrutto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ffield_einkommennetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_berufLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_rentenbegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_oeffentlicherdienst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_beamter)
                .addGap(55, 55, 55))
        );

        tabbedNewKunde.addTab(resourceMap.getString("panel_beruf.TabConstraints.tabTitle"), panel_beruf); // NOI18N

        getContentPane().add(tabbedNewKunde, java.awt.BorderLayout.CENTER);

        panel_menu.setName("panel_menu"); // NOI18N

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(resourceMap.getIcon("btnCancel.icon")); // NOI18N
        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_menuLayout = new javax.swing.GroupLayout(panel_menu);
        panel_menu.setLayout(panel_menuLayout);
        panel_menuLayout.setHorizontalGroup(
            panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menuLayout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_menuLayout.setVerticalGroup(
            panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_menuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(panel_menu, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo_familienstandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_familienstandActionPerformed

        if (combo_familienstand.getSelectedIndex() == 2) {
            this.label_ehepartnerid.setVisible(true);
            this.label_eheschliess.setVisible(true);
            this.date_eheschliessung.setVisible(true);
            this.combo_ehepartner.setVisible(true);
        } else {
            this.label_ehepartnerid.setVisible(false);
            this.label_eheschliess.setVisible(false);
            this.date_eheschliessung.setVisible(false);
            this.combo_ehepartner.setVisible(false);
        }

        this.panel_erweitert.revalidate();
    }//GEN-LAST:event_combo_familienstandActionPerformed

    /**
     * 
     * @param evt
     */
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (update == false) {
            kunde = new KundenObj();
        }

        if (this.spinnerKundenNr.getValue() == null || !(this.spinnerKundenNr.getValue().toString().length() > 0)) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Kundennummer aus",
                    "Fehler: Keine Kundennummer", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.field_vorname.getText() == null || !(this.field_vorname.getText().length() > 0)) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Vornamen an.");
            return;
        }

        if (this.field_nachname.getText() == null || !(this.field_nachname.getText().length() > 0)) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Nachnamen an.");
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        if (update == false) {
            kunde.setCreatorId(BasicRegistry.currentUser.getId());

            kunde.setMandantenId(BasicRegistry.currentMandant.getId());

            String kdnr = this.spinnerKundenNr.getValue().toString();

            if (Integer.valueOf(kdnr) <= 0) {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine gültige Kundennummer aus",
                        "Fehler: Keine Kundennummer", JOptionPane.WARNING_MESSAGE);
                return;
            }


            try {
                kdnr = KundenKennungHelper.verifyNochaktuell(kdnr);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            kunde.setKundenNr(kdnr);
        }

        kunde.setAnrede(this.combo_anrede.getSelectedItem().toString());

        kunde.setTitel(this.combo_titel.getSelectedItem().toString());

        kunde.setFirma(this.field_firma.getText());

        kunde.setVorname(this.field_vorname.getText());
        kunde.setVorname2(this.field_vorname2.getText());
        kunde.setVornameWeitere(this.field_weiterVornamen.getText());

        kunde.setNachname(this.field_nachname.getText());

        kunde.setStreet(this.field_strasse.getText());
        kunde.setPlz(this.field_plz.getText());
        kunde.setStadt(this.field_ort.getText());
        kunde.setBundesland(this.combo_bundesland.getSelectedItem().toString());
        kunde.setLand(this.combo_land.getSelectedItem().toString());

        kunde.setAdresseZusatz(this.field_adressezusatz.getText());
        kunde.setAdresseZusatz2(this.field_adressezusatz2.getText());

        kunde.setCommunication1(field_communication1.getText());
        kunde.setCommunication2(field_communication2.getText());
        kunde.setCommunication3(field_communication3.getText());
        kunde.setCommunication4(field_communication4.getText());
        kunde.setCommunication5(field_communication5.getText());
        kunde.setCommunication6(field_communication6.getText());

        kunde.setCommunication1Type(this.combo_comtype1.getSelectedIndex());
        kunde.setCommunication2Type(this.combo_comtype2.getSelectedIndex());
        kunde.setCommunication3Type(this.combo_comtype3.getSelectedIndex());
        kunde.setCommunication4Type(this.combo_comtype4.getSelectedIndex());
        kunde.setCommunication5Type(this.combo_comtype5.getSelectedIndex());
        kunde.setCommunication6Type(this.combo_comtype6.getSelectedIndex());

        kunde.setTyp("Privat"); 

        kunde.setFamilienStand(this.combo_familienstand.getSelectedItem().toString());
        try {
            Object ehep = this.combo_ehepartner.getSelectedItem();
            if(ehep != null) {
                if(ehep.getClass().equals(KundenObj.class)) {
                    KundenObj kund = (KundenObj) ehep;
                    kunde.setEhepartnerId(kund.getKundenNr());
                } else {
                    kunde.setEhepartnerId(null);
                }
            } else {
                kunde.setEhepartnerId(null);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            kunde.setEhepartnerId(null);
        }

        if (this.date_geburtsdatum.getDate() != null) {
            kunde.setGeburtsdatum(df.format(this.date_geburtsdatum.getDate())); //TODo
        }

        kunde.setNationalitaet(this.combo_nationalitaet.getSelectedItem().toString());
        kunde.setBeruf(this.field_beruf.getText());
        kunde.setBerufsTyp(this.combo_berufstatus.getSelectedItem().toString());

        // kunde.setBerufsOptionen(this.combo_); TODO Fix this#
               
        if (this.combo_berufbesonderheiten.getSelectedItem() != null) {
            kunde.setBerufsBesonderheiten(this.combo_berufbesonderheiten.getSelectedItem().toString());           
        }

        kunde.setAnteilBuerotaetigkeit(this.combo_buerotaetigkeit.getSelectedItem().toString().trim());
        kunde.setBeginnRente((String) this.combo_rentenbegin.getSelectedItem());

        kunde.setBeamter(this.check_beamter.isSelected());
        kunde.setOeffentlicherDienst(this.check_oeffentlicherdienst.isSelected());

        kunde.setEinkommen(Double.valueOf(this.ffield_einkommenbrutto.getValue().toString()));
        kunde.setEinkommenNetto(Double.valueOf(this.ffield_einkommennetto.getValue().toString()));

        kunde.setSteuertabelle(this.combo_steuerart.getSelectedItem().toString());
        kunde.setSteuerklasse(this.combo_steuerklasse.getSelectedItem().toString());

        if (this.combo_kirchensteuer.getSelectedItem() != null) {
            kunde.setKirchenSteuer(this.combo_kirchensteuer.getSelectedItem().toString());
        }

        if (this.combo_kinderfreibetrag.getSelectedItem() != null) {
            kunde.setKinderFreibetrag(this.combo_kinderfreibetrag.getSelectedItem().toString());
        }

        kunde.setReligion(this.field_religion.getText());

        if (this.combo_rolleimhaushalt.getSelectedItem() != null) {
            kunde.setRolleImHaushalt(this.combo_rolleimhaushalt.getSelectedItem().toString());
        }

        kunde.setWeiterePersonen(this.field_weiterePersonen.getText());
        kunde.setWeiterePersonenInfo(this.field_weiterePersonenInfo.getText());

        // Todo
        //kunde.getFamilienPlanung();

        try {
            KundenObj knd1 = (KundenObj) this.combo_werber.getSelectedItem();
            kunde.setWerberKennung(knd1.getKundenNr());
        } catch (Exception e) {
            kunde.setWerberKennung(null);
        }

        int bkid = -1;

        try {
            BankKontoObj bk = (BankKontoObj) this.combo_defaultkonto.getSelectedItem();
            bkid = bk.getId();
        } catch (Exception e) {
        }

        kunde.setDefaultKonto(bkid);

        try {
            BenutzerObj ben = (BenutzerObj) this.combo_betreuer.getSelectedItem();
            kunde.setBetreuerId(ben.getId());
        } catch (Exception e) {
            e.printStackTrace();

            kunde.setBetreuerId(BasicRegistry.currentUser.getId()); // Fallback
        }

        kunde.setComments(this.area_comments.getText());
        kunde.setCustom1(this.field_custom1.getText());
        kunde.setCustom2(this.field_custom2.getText());
        kunde.setCustom3(this.field_custom3.getText());
        kunde.setCustom4(this.field_custom4.getText());
        kunde.setCustom5(this.field_custom5.getText());

        kunde.setGeburtsname(this.field_geburtsname.getText());
        if (this.date_eheschliessung.getDate() != null) {
            kunde.setEhedatum(df.format(this.date_eheschliessung.getDate()));
        }

        kunde.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

        int id = -1;
        try {
            if (update == false) {
                kunde.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                id = KundenSQLMethods.insertIntoKunden(DatabaseConnection.open(), kunde);
                kunde.setId(id);
                NewKundeHelper.doCreationTasks(kunde);
//                 KundenRegistry.refresh();
                this.dispose();
            } else {
                boolean success = KundenSQLMethods.updateKunden(DatabaseConnection.open(), kunde);

                if (!success) {
                    Log.databaselogger.fatal("Datenbankfehler: Konnte Kunden nicht updaten");
                    ShowException.showException("Beim updaten des Kunden ist ein Datenbank Fehler aufgetretten. "
                            + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                            ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte den Kunden nicht updaten");
                }

//                KundenRegistry.refresh();
                this.dispose();
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Datenbankfehler: Konnte neuen Kunden nicht speichern", e);
            ShowException.showException("Beim Speichern des Kunden ist ein Datenbank Fehler aufgetretten. "
                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Kunden nicht speichern");
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnMaxWeiterePersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxWeiterePersActionPerformed
        MaximizeHelper.openMax(this.field_weiterePersonen, "Weitere Personen");
}//GEN-LAST:event_btnMaxWeiterePersActionPerformed

    private void btnMaxWeitererPersInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxWeitererPersInfoActionPerformed
        MaximizeHelper.openMax(this.field_weiterePersonenInfo, "Weitere Personen Informationen");
    }//GEN-LAST:event_btnMaxWeitererPersInfoActionPerformed

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

    private void btnMaxCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCommentsActionPerformed
        MaximizeHelper.openMax(this.area_comments, "Kommentare");
    }//GEN-LAST:event_btnMaxCommentsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewKundeDialog dialog = new NewKundeDialog(new javax.swing.JFrame(), true);
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
    public javax.swing.JTextArea area_comments;
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnMaxComments;
    public javax.swing.JButton btnMaxCustom1;
    public javax.swing.JButton btnMaxCustom2;
    public javax.swing.JButton btnMaxCustom3;
    public javax.swing.JButton btnMaxCustom4;
    public javax.swing.JButton btnMaxCustom5;
    public javax.swing.JButton btnMaxWeiterePers;
    public javax.swing.JButton btnMaxWeitererPersInfo;
    public javax.swing.JButton btnSave;
    public javax.swing.JCheckBox check_beamter;
    public javax.swing.JCheckBox check_oeffentlicherdienst;
    public javax.swing.JComboBox combo_anrede;
    public javax.swing.JComboBox combo_berufbesonderheiten;
    public javax.swing.JComboBox combo_berufstatus;
    public javax.swing.JComboBox combo_betreuer;
    public javax.swing.JComboBox combo_buerotaetigkeit;
    public javax.swing.JComboBox combo_bundesland;
    public javax.swing.JComboBox combo_comtype1;
    public javax.swing.JComboBox combo_comtype2;
    public javax.swing.JComboBox combo_comtype3;
    public javax.swing.JComboBox combo_comtype4;
    public javax.swing.JComboBox combo_comtype5;
    public javax.swing.JComboBox combo_comtype6;
    public javax.swing.JComboBox combo_defaultkonto;
    public javax.swing.JComboBox combo_ehepartner;
    public javax.swing.JComboBox combo_familienstand;
    public javax.swing.JComboBox combo_kinderfreibetrag;
    public javax.swing.JComboBox combo_kirchensteuer;
    public javax.swing.JComboBox combo_land;
    public javax.swing.JComboBox combo_nationalitaet;
    public javax.swing.JComboBox combo_rentenbegin;
    public javax.swing.JComboBox combo_rolleimhaushalt;
    public javax.swing.JComboBox combo_steuerart;
    public javax.swing.JComboBox combo_steuerklasse;
    public javax.swing.JComboBox combo_titel;
    public javax.swing.JComboBox combo_werber;
    public com.toedter.calendar.JDateChooser date_eheschliessung;
    public com.toedter.calendar.JDateChooser date_geburtsdatum;
    public javax.swing.JFormattedTextField ffield_einkommenbrutto;
    public javax.swing.JFormattedTextField ffield_einkommennetto;
    public javax.swing.JTextField field_adressezusatz;
    public javax.swing.JTextField field_adressezusatz2;
    public javax.swing.JTextField field_beruf;
    public javax.swing.JTextField field_communication1;
    public javax.swing.JTextField field_communication2;
    public javax.swing.JTextField field_communication3;
    public javax.swing.JTextField field_communication4;
    public javax.swing.JTextField field_communication5;
    public javax.swing.JTextField field_communication6;
    public javax.swing.JTextField field_custom1;
    public javax.swing.JTextField field_custom2;
    public javax.swing.JTextField field_custom3;
    public javax.swing.JTextField field_custom4;
    public javax.swing.JTextField field_custom5;
    public javax.swing.JTextField field_firma;
    public javax.swing.JTextField field_geburtsname;
    public javax.swing.JTextField field_nachname;
    public javax.swing.JTextField field_ort;
    public javax.swing.JTextField field_plz;
    public javax.swing.JTextField field_religion;
    public javax.swing.JTextField field_strasse;
    public javax.swing.JTextField field_vorname;
    public javax.swing.JTextField field_vorname2;
    public javax.swing.JTextField field_weiterVornamen;
    public javax.swing.JTextField field_weiterePersonen;
    public javax.swing.JTextField field_weiterePersonenInfo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel26;
    public javax.swing.JLabel jLabel27;
    public javax.swing.JLabel jLabel28;
    public javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel30;
    public javax.swing.JLabel jLabel31;
    public javax.swing.JLabel jLabel33;
    public javax.swing.JLabel jLabel34;
    public javax.swing.JLabel jLabel35;
    public javax.swing.JLabel jLabel36;
    public javax.swing.JLabel jLabel37;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel44;
    public javax.swing.JLabel jLabel45;
    public javax.swing.JLabel jLabel46;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel54;
    public javax.swing.JLabel jLabel55;
    public javax.swing.JLabel jLabel56;
    public javax.swing.JLabel jLabel57;
    public javax.swing.JLabel jLabel58;
    public javax.swing.JLabel jLabel59;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JSeparator jSeparator10;
    public javax.swing.JSeparator jSeparator12;
    public javax.swing.JSeparator jSeparator14;
    public javax.swing.JSeparator jSeparator15;
    public javax.swing.JSeparator jSeparator16;
    public javax.swing.JSeparator jSeparator17;
    public javax.swing.JSeparator jSeparator18;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JSeparator jSeparator5;
    public javax.swing.JSeparator jSeparator6;
    public javax.swing.JSeparator jSeparator7;
    public javax.swing.JSeparator jSeparator8;
    public javax.swing.JSeparator jSeparator9;
    public javax.swing.JLabel label_ehepartnerid;
    public javax.swing.JLabel label_eheschliess;
    public javax.swing.JPanel panel_basis;
    public javax.swing.JPanel panel_beruf;
    public javax.swing.JPanel panel_erweitert;
    public javax.swing.JPanel panel_kontakt;
    public javax.swing.JPanel panel_menu;
    public javax.swing.JPanel panel_sonstiges;
    public javax.swing.JPanel panel_steuer;
    public javax.swing.JSpinner spinnerKundenNr;
    public javax.swing.JTabbedPane tabbedNewKunde;
    // End of variables declaration//GEN-END:variables
}
