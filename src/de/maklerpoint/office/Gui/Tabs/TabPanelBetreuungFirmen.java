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
 * TabPanelBetreuung.java
 *
 * Created on 28.08.2010, 10:20:12
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Kunden_BetreuungObj;
import de.maklerpoint.office.Kunden.Tools.Kunden_BetreuungSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author yves
 */
public class TabPanelBetreuungFirmen extends javax.swing.JPanel implements iTabs {

    private int PRIVAT = 0;
    private int FIRMA = 1;
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private String kennung = null;
    private Kunden_BetreuungObj btr = null;
    private boolean enabled = true;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /** Creates new form TabPanelBetreuung */
    public TabPanelBetreuungFirmen() {
        initComponents();
//        addActionListener();
    }

    public String getTabName() {
        return "Betreuung";
    }

    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Dafür gibts Privatkunde!!");
    }

    public void load(FirmenObj firma) {
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        type = FIRMA;
        setUp();
    }

    public void disableElements() {
        enabled = false;

        this.combo_kundentyp.setEnabled(false);
        this.combo_loyalitaet.setEnabled(false);
        this.combo_prioritaet.setEnabled(false);
        this.combo_zielgruppe.setEnabled(false);
        this.field_verwaltungskosten.setEnabled(false);
        this.check_analyse.setEnabled(false);
        this.check_geburtstagskarte.setEnabled(false);
        this.check_maklervertrag.setEnabled(false);
        this.check_erstinformationen.setEnabled(false);
        this.check_newsletter.setEnabled(false);
        this.check_osterkarte.setEnabled(false);
        this.check_weihnachtskarte.setEnabled(false);
        this.check_zeitschrift.setEnabled(false);
        this.date_ersterKontakt.setEnabled(false);
        this.date_letzteAnalyse.setEnabled(false);
        this.date_letzteRoutine.setEnabled(false);
        this.date_letzterKontakt.setEnabled(false);
        this.date_maklerBeginn.setEnabled(false);
        this.date_maklerEnde.setEnabled(false);
        this.date_naechsteAnalyse.setEnabled(false);
             
        this.field_verwaltungskosten.setEnabled(false);
    }

    public void enableElements() {
        if (enabled == true) {
            return;
        }

        enabled = true;

        this.combo_kundentyp.setEnabled(true);
        this.combo_loyalitaet.setEnabled(true);
        this.combo_prioritaet.setEnabled(true);
        this.combo_zielgruppe.setEnabled(true);
        this.field_verwaltungskosten.setEnabled(true);
        this.check_analyse.setEnabled(true);
        this.check_geburtstagskarte.setEnabled(true);
        this.check_maklervertrag.setEnabled(true);
        this.check_erstinformationen.setEnabled(true);
        this.check_newsletter.setEnabled(true);
        this.check_osterkarte.setEnabled(true);
        this.check_weihnachtskarte.setEnabled(true);
        this.check_zeitschrift.setEnabled(true);
        this.date_ersterKontakt.setEnabled(true);
        this.date_letzteAnalyse.setEnabled(true);
        this.date_letzteRoutine.setEnabled(true);
        this.date_letzterKontakt.setEnabled(true);
        this.date_maklerBeginn.setEnabled(true);
        this.date_maklerEnde.setEnabled(true);
        this.date_naechsteAnalyse.setEnabled(true);
        this.field_verwaltungskosten.setEnabled(true);
    }

    public void load(VersichererObj versicherer) {
        throw new UnsupportedOperationException("Sollte nicht verwendet werden?!");
    }

    public void load(BenutzerObj benutzer) {
        throw new UnsupportedOperationException("Sollte nicht verwendet werden?!");
    }

    public void load(ProduktObj produkt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ActionListener update = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            save();
        }
    };

    private void addActionListener() {
        this.combo_kundentyp.addActionListener(update);
        this.combo_loyalitaet.addActionListener(update);
        this.combo_prioritaet.addActionListener(update);
        this.combo_zielgruppe.addActionListener(update);
        this.field_verwaltungskosten.addActionListener(update);
        this.check_analyse.addActionListener(update);
        this.check_geburtstagskarte.addActionListener(update);
        this.check_maklervertrag.addActionListener(update);
        this.check_newsletter.addActionListener(update);
        this.check_osterkarte.addActionListener(update);
        this.check_weihnachtskarte.addActionListener(update);
        this.check_zeitschrift.addActionListener(update);
        this.date_ersterKontakt.addActionListener(update);
        this.date_letzteAnalyse.addActionListener(update);
        this.date_letzteRoutine.addActionListener(update);
        this.date_letzterKontakt.addActionListener(update);
        this.date_maklerBeginn.addActionListener(update);
        this.date_maklerEnde.addActionListener(update);
        this.date_naechsteAnalyse.addActionListener(update);
    }

    private void setUp() {
        if (btr != null) {
            save();
        }

        try {
            btr = Kunden_BetreuungSQLMethods.getKunden_Betreuung(DatabaseConnection.open(), kennung);
            if (btr == null) {
                btr = new Kunden_BetreuungObj();
                btr.setKundenKennung(kennung);
                btr.setPrioritaet(0);
                btr.setKundenTyp("Interessent");
                btr.setLoyalitaet("Normal");
                btr.setZielgruppe("Unbekannt");
                btr.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                btr.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                btr.setStatus(Status.NORMAL);

                int id = Kunden_BetreuungSQLMethods.insertIntoKunden_betreuung(DatabaseConnection.open(), btr);
                btr.setId(id);
            }

            this.combo_kundentyp.setSelectedItem(btr.getKundenTyp());
            this.combo_loyalitaet.setSelectedItem(btr.getLoyalitaet());
            this.combo_prioritaet.setSelectedItem(btr.getPrioritaet());
            this.combo_zielgruppe.setSelectedItem(btr.getZielgruppe());
            this.field_verwaltungskosten.setText("" + btr.getVerwaltungskosten());
            this.check_analyse.setSelected(btr.isAnalyse());
            this.check_geburtstagskarte.setSelected(btr.isGeburtstagskarte());
            this.check_maklervertrag.setSelected(btr.isMaklerVertrag());
            this.check_newsletter.setSelected(btr.isNewsletter());
            this.check_osterkarte.setSelected(btr.isOsterkarte());
            this.check_weihnachtskarte.setSelected(btr.isWeihnachtskarte());
            this.check_zeitschrift.setSelected(btr.isWeihnachtskarte());

            this.check_gestorben.setSelected(btr.isGestorben());

            String pflegbtr = String.valueOf(btr.getPflegeBeitrag()).replace(".", ",");

            try {
                if (btr.getGestorbenDatum() != null) {
                    this.date_gestorben.setDate(df.parse(btr.getGestorbenDatum()));
                } else {
                    this.date_gestorben.setDate(null);
                }


                if (btr.getErsterKontakt() != null) {
                    this.date_ersterKontakt.setDate(df.parse(btr.getErsterKontakt()));
                } else {
                    this.date_ersterKontakt.setDate(null);
                }

                if (btr.getAnalyseLetzte() != null) {
                    this.date_letzteAnalyse.setDate(df.parse(btr.getAnalyseLetzte()));
                } else {
                    this.date_letzteAnalyse.setDate(null);
                }

                if (btr.getLetzteRoutine() != null) {
                    this.date_letzteRoutine.setDate(df.parse(btr.getLetzteRoutine()));
                } else {
                    this.date_letzteRoutine.setDate(null);
                }

                if (btr.getLetzerKontakt() != null) {
                    this.date_letzterKontakt.setDate(df.parse(btr.getLetzerKontakt()));
                } else {
                    this.date_letzterKontakt.setDate(null);
                }

                if (btr.getMaklerBeginn() != null) {
                    this.date_maklerBeginn.setDate(df.parse(btr.getMaklerBeginn()));
                } else {
                    this.date_maklerBeginn.setDate(null);
                }

                if (btr.getMaklerEnde() != null) {
                    this.date_maklerEnde.setDate(df.parse(btr.getMaklerEnde()));
                } else {
                    this.date_maklerEnde.setDate(null);
                }

                if (btr.getAnalyseNaechste() != null) {
                    this.date_naechsteAnalyse.setDate(df.parse(btr.getAnalyseNaechste()));
                } else {
                    this.date_naechsteAnalyse.setDate(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.check_erstinformationen.setSelected(btr.isErstinformationen());


        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Kunden Betreuungsdaten nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Kunden Betreuungsdaten konnte nicht aus der Datenbank geladen werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Betreuungsdaten nicht laden");
        }
    }

    private void save() {
        btr.setKundenTyp((String) combo_kundentyp.getSelectedItem());
        btr.setLoyalitaet((String) combo_loyalitaet.getSelectedItem());
        btr.setPrioritaet(Integer.valueOf(combo_prioritaet.getSelectedItem().toString()));
        btr.setZielgruppe((String) combo_zielgruppe.getSelectedItem());
        if (field_verwaltungskosten.getText() != null && field_verwaltungskosten.getText().length() > 0) {
            btr.setVerwaltungskosten(Integer.valueOf(field_verwaltungskosten.getText()));
        }
        btr.setAnalyse(check_analyse.isSelected());
        btr.setGeburtstagskarte(check_geburtstagskarte.isSelected());
        btr.setMaklerVertrag(check_erstinformationen.isSelected());
        btr.setNewsletter(check_newsletter.isSelected());
        btr.setOsterkarte(check_osterkarte.isSelected());
        btr.setWeihnachtskarte(check_weihnachtskarte.isSelected());
        btr.setKundenzeitschrift(check_zeitschrift.isSelected());

        if (date_ersterKontakt.getDate() != null) {
            btr.setErsterKontakt(df.format(this.date_ersterKontakt.getDate()));
        }

        if (date_letzteAnalyse.getDate() != null) {
            btr.setAnalyseLetzte(df.format(date_letzteAnalyse.getDate()));
        }

        if (date_letzteRoutine.getDate() != null) {
            btr.setLetzteRoutine(df.format(date_letzteRoutine.getDate()));
        }

        if (date_letzterKontakt.getDate() != null) {
            btr.setLetzerKontakt(df.format(date_letzterKontakt.getDate()));
        }

        if (date_maklerBeginn.getDate() != null) {
            btr.setMaklerBeginn(df.format(date_maklerBeginn.getDate()));
        }

        if (date_maklerEnde.getDate() != null) {
            btr.setMaklerEnde(df.format(date_maklerEnde.getDate()));
        }

        if (date_naechsteAnalyse.getDate() != null) {
            btr.setAnalyseNaechste(df.format(date_naechsteAnalyse.getDate()));
        }
  
        if(this.date_gestorben.getDate() != null) {
            btr.setGestorbenDatum(df.format(this.date_gestorben.getDate()));
        }
        
        btr.setGestorben(this.check_gestorben.isSelected());        

        btr.setErstinformationen(this.check_erstinformationen.isSelected());

        try {
            Kunden_BetreuungSQLMethods.updateKunden_betreuung(DatabaseConnection.open(), btr);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Kunden Betreuungsdaten konnten nicht geupdated werden", e);
            ShowException.showException("Die Kunden Betreuungsdaten konnten nicht aktualisiert werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Betreuungsdaten nicht aktualisieren");
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

        combo_kundentyp = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        combo_prioritaet = new javax.swing.JComboBox();
        combo_loyalitaet = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        combo_zielgruppe = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        date_ersterKontakt = new org.jdesktop.swingx.JXDatePicker();
        date_letzterKontakt = new org.jdesktop.swingx.JXDatePicker();
        date_letzteRoutine = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        date_letzteAnalyse = new org.jdesktop.swingx.JXDatePicker();
        date_naechsteAnalyse = new org.jdesktop.swingx.JXDatePicker();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        check_analyse = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        date_maklerBeginn = new org.jdesktop.swingx.JXDatePicker();
        date_maklerEnde = new org.jdesktop.swingx.JXDatePicker();
        check_erstinformationen = new javax.swing.JCheckBox();
        check_newsletter = new javax.swing.JCheckBox();
        check_zeitschrift = new javax.swing.JCheckBox();
        check_geburtstagskarte = new javax.swing.JCheckBox();
        check_weihnachtskarte = new javax.swing.JCheckBox();
        check_osterkarte = new javax.swing.JCheckBox();
        field_verwaltungskosten = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        check_maklervertrag = new javax.swing.JCheckBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        check_gestorben = new javax.swing.JCheckBox();
        date_gestorben = new org.jdesktop.swingx.JXDatePicker();

        setName("Form"); // NOI18N

        combo_kundentyp.setEditable(true);
        combo_kundentyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Interessent", "Kunde", "Mandant" }));
        combo_kundentyp.setName("combo_kundentyp"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelBetreuungFirmen.class);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_prioritaet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        combo_prioritaet.setName("combo_prioritaet"); // NOI18N

        combo_loyalitaet.setEditable(true);
        combo_loyalitaet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "Sehr hoch", "Hoch", "Normal", "Gering", "Sehr gering" }));
        combo_loyalitaet.setSelectedIndex(3);
        combo_loyalitaet.setName("combo_loyalitaet"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        combo_zielgruppe.setEditable(true);
        combo_zielgruppe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unbekannt", "GK mit Umsatz bis 100.000 €", "GK mit Umsatz bis 1.000.000 €", "GK mit Umsatz bis 10.000.000 €", " " }));
        combo_zielgruppe.setName("combo_zielgruppe"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        date_ersterKontakt.setName("date_ersterKontakt"); // NOI18N

        date_letzterKontakt.setName("date_letzterKontakt"); // NOI18N

        date_letzteRoutine.setName("date_letzteRoutine"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        date_letzteAnalyse.setName("date_letzteAnalyse"); // NOI18N

        date_naechsteAnalyse.setName("date_naechsteAnalyse"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        check_analyse.setText(resourceMap.getString("check_analyse.text")); // NOI18N
        check_analyse.setName("check_analyse"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        date_maklerBeginn.setName("date_maklerBeginn"); // NOI18N

        date_maklerEnde.setName("date_maklerEnde"); // NOI18N

        check_erstinformationen.setText(resourceMap.getString("check_erstinformationen.text")); // NOI18N
        check_erstinformationen.setName("check_erstinformationen"); // NOI18N

        check_newsletter.setText(resourceMap.getString("check_newsletter.text")); // NOI18N
        check_newsletter.setName("check_newsletter"); // NOI18N

        check_zeitschrift.setText(resourceMap.getString("check_zeitschrift.text")); // NOI18N
        check_zeitschrift.setName("check_zeitschrift"); // NOI18N

        check_geburtstagskarte.setText(resourceMap.getString("check_geburtstagskarte.text")); // NOI18N
        check_geburtstagskarte.setName("check_geburtstagskarte"); // NOI18N

        check_weihnachtskarte.setText(resourceMap.getString("check_weihnachtskarte.text")); // NOI18N
        check_weihnachtskarte.setName("check_weihnachtskarte"); // NOI18N

        check_osterkarte.setText(resourceMap.getString("check_osterkarte.text")); // NOI18N
        check_osterkarte.setName("check_osterkarte"); // NOI18N

        field_verwaltungskosten.setName("field_verwaltungskosten"); // NOI18N
        field_verwaltungskosten.setPreferredSize(new java.awt.Dimension(60, 25));

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setName("jSeparator3"); // NOI18N

        check_maklervertrag.setText(resourceMap.getString("check_maklervertrag.text")); // NOI18N
        check_maklervertrag.setName("check_maklervertrag"); // NOI18N

        jSeparator6.setName("jSeparator6"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel16.setFont(resourceMap.getFont("jLabel16.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jSeparator7.setName("jSeparator7"); // NOI18N

        jSeparator9.setName("jSeparator9"); // NOI18N

        check_gestorben.setText(resourceMap.getString("check_gestorben.text")); // NOI18N
        check_gestorben.setName("check_gestorben"); // NOI18N

        date_gestorben.setName("date_gestorben"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(combo_prioritaet, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_zielgruppe, 0, 0, Short.MAX_VALUE)
                            .addComponent(combo_loyalitaet, 0, 184, Short.MAX_VALUE)
                            .addComponent(combo_kundentyp, javax.swing.GroupLayout.Alignment.TRAILING, 0, 184, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_letzteAnalyse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_naechsteAnalyse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(check_analyse))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date_letzteRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_letzterKontakt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_ersterKontakt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(check_maklervertrag)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date_maklerBeginn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_maklerEnde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_erstinformationen)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(field_verwaltungskosten, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_newsletter)
                            .addComponent(check_zeitschrift)
                            .addComponent(check_geburtstagskarte)
                            .addComponent(check_weihnachtskarte)
                            .addComponent(check_osterkarte)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(check_gestorben)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_gestorben, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(field_verwaltungskosten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(check_gestorben)
                            .addComponent(date_gestorben, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_newsletter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_zeitschrift)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_geburtstagskarte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_weihnachtskarte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_osterkarte))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_kundentyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_prioritaet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_loyalitaet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_zielgruppe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_analyse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(date_letzteAnalyse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(date_naechsteAnalyse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(date_ersterKontakt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(date_letzterKontakt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date_letzteRoutine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_maklervertrag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(date_maklerBeginn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(date_maklerEnde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(check_erstinformationen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox check_analyse;
    private javax.swing.JCheckBox check_erstinformationen;
    private javax.swing.JCheckBox check_geburtstagskarte;
    private javax.swing.JCheckBox check_gestorben;
    private javax.swing.JCheckBox check_maklervertrag;
    private javax.swing.JCheckBox check_newsletter;
    private javax.swing.JCheckBox check_osterkarte;
    private javax.swing.JCheckBox check_weihnachtskarte;
    private javax.swing.JCheckBox check_zeitschrift;
    private javax.swing.JComboBox combo_kundentyp;
    private javax.swing.JComboBox combo_loyalitaet;
    private javax.swing.JComboBox combo_prioritaet;
    private javax.swing.JComboBox combo_zielgruppe;
    private org.jdesktop.swingx.JXDatePicker date_ersterKontakt;
    private org.jdesktop.swingx.JXDatePicker date_gestorben;
    private org.jdesktop.swingx.JXDatePicker date_letzteAnalyse;
    private org.jdesktop.swingx.JXDatePicker date_letzteRoutine;
    private org.jdesktop.swingx.JXDatePicker date_letzterKontakt;
    private org.jdesktop.swingx.JXDatePicker date_maklerBeginn;
    private org.jdesktop.swingx.JXDatePicker date_maklerEnde;
    private org.jdesktop.swingx.JXDatePicker date_naechsteAnalyse;
    private javax.swing.JTextField field_verwaltungskosten;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    // End of variables declaration//GEN-END:variables
}