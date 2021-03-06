/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DokumentenInfo.java
 *
 * Created on 02.06.2011, 13:01:33
 */
package de.maklerpoint.office.Gui.Dokumente;

import de.maklerpoint.office.Dokumente.DokumentenObj;
import de.maklerpoint.office.Gui.Configuration.TagDialogHelper;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tags.Tags;
import de.maklerpoint.office.Tools.BasicRegex;
import de.maklerpoint.office.Tools.FormatFileSize;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class DokumentenInfo extends javax.swing.JDialog {

    private DokumentenObj dok;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public DokumentenInfo(java.awt.Frame parent, boolean modal, DokumentenObj dok) {
        super(parent, modal);
        this.dok = dok;
        initComponents();
        setUp();
    }

    private void setUp() {
        if (dok == null) {
            return;
        }
        this.setTitle("Dateiinformationen für " + dok.getName());        

        this.label_dok_changed.setText(df.format(dok.getModified()));
        this.label_dok_checksum.setText(dok.getChecksum());
        this.label_dok_created.setText(df.format(dok.getCreated()));
        this.label_dok_creator.setText(BenutzerRegistry.getBenutzer(dok.getBenutzerId(), true).toString());
        this.label_dok_dateiname.setText(dok.getName());
        this.label_dok_fullpath.setText(BasicRegex.shortenString(80, dok.getFullPath(), true));
        this.label_dok_id.setText("" + dok.getId());
        this.label_dok_lastview.setText(df.format(dok.getLastviewed()));
        this.label_dok_size.setText(FormatFileSize.formatSize(new File(dok.getFullPath()).length(),
                FormatFileSize.KB) + " kB");

        this.combo_benutzer.setModel(new DefaultComboBoxModel(ComboBoxGetter.getBenutzerCombo("Kein Benutzer")));
        this.combo_kunden.setModel(new DefaultComboBoxModel(ComboBoxGetter.getAlleKundenCombo("Kein Kunde",
                Status.NORMAL)));
        this.combo_versicherung.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVersichererCombo("Kein Versicherer")));

        if (!"-1".equalsIgnoreCase(dok.getKundenKennung())) {
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo("Kein Vertrag",
                    dok.getKundenKennung())));
        } else {
            this.combo_vertrag.setModel(new DefaultComboBoxModel(new Object[]{"Zuerst Kunden auswählen"}));
        }        
        
        this.combo_produkt.setModel(new DefaultComboBoxModel(new Object[]{"Bitte zuerst Versicherer wählen"}));

        this.combo_tags.setModel(new DefaultComboBoxModel(Tags.tags));

        if (dok.getBenutzerId() != -1) {
            this.combo_benutzer.setSelectedIndex(dok.getBenutzerId());
        }

        if (dok.getVersichererId() != -1) {
            this.combo_versicherung.setSelectedIndex(dok.getVersichererId());
            this.combo_produkt.setModel(new DefaultComboBoxModel(ComboBoxGetter.getProduktCombo(
                    "Kein Produkt", dok.getVersichererId())));

            if (dok.getProduktId() != -1) {
                this.combo_produkt.setSelectedItem(VersicherungsRegistry.getProdukt(dok.getProduktId()));
            }

        }

        if (dok.getVertragId() != -1) {
            this.combo_vertrag.setSelectedIndex(dok.getVertragId());
        }

        if (!dok.getKundenKennung().equalsIgnoreCase("-1")) {
            this.combo_kunden.setSelectedItem(KundenRegistry.getKunde(dok.getKundenKennung()));
        }

        this.combo_tags.setSelectedItem(dok.getTag());


    }

    private void loadVertragCombo(Object knd) {
        if (knd.getClass().equals(KundenObj.class)) {
            KundenObj knde = (KundenObj) knd;
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(
                    "Kein Vertrag", knde.getKundenNr())));
        } else if (knd.getClass().equals(KundenObj.class)) {
            FirmenObj knde = (FirmenObj) knd;
            this.combo_vertrag.setModel(new DefaultComboBoxModel(ComboBoxGetter.getVertragCombo(
                    "Kein Vertrag", knde.getKundenNr())));
        } else {
            this.combo_vertrag.setModel(new DefaultComboBoxModel(new Object[]{"Zuerst Kunden auswählen"}));
        }
    }
    
    private void loadProduktCombo(VersichererObj vers) {
        if(vers != null) {
            this.combo_produkt.setModel(new DefaultComboBoxModel(ComboBoxGetter.getProduktCombo(
                    "Kein Produkt", vers.getId())));
        } else {
            this.combo_produkt.setSelectedItem(new Object[]{"Zuerst Gesellschaft auswählen"});
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

        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        tab_info = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        label_dok_id = new javax.swing.JLabel();
        label_dok_creator = new javax.swing.JLabel();
        label_dok_dateiname = new javax.swing.JLabel();
        label_dok_fullpath = new javax.swing.JLabel();
        label_dok_size = new javax.swing.JLabel();
        label_dok_created = new javax.swing.JLabel();
        label_dok_changed = new javax.swing.JLabel();
        label_dok_lastview = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        label_dok_checksum = new javax.swing.JLabel();
        panel_erweitert = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        combo_vertrag = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        combo_versicherung = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        combo_produkt = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        field_beschreibung = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        combo_tags = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        combo_kunden = new javax.swing.JComboBox();
        combo_benutzer = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        btnTags = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        btnCancel.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(DokumentenInfo.class);
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(120, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tab_info.setName("tab_info"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        label_dok_id.setText(resourceMap.getString("label_dok_id.text")); // NOI18N
        label_dok_id.setName("label_dok_id"); // NOI18N

        label_dok_creator.setText(resourceMap.getString("label_dok_creator.text")); // NOI18N
        label_dok_creator.setName("label_dok_creator"); // NOI18N

        label_dok_dateiname.setText(resourceMap.getString("label_dok_dateiname.text")); // NOI18N
        label_dok_dateiname.setName("label_dok_dateiname"); // NOI18N

        label_dok_fullpath.setText(resourceMap.getString("label_dok_fullpath.text")); // NOI18N
        label_dok_fullpath.setName("label_dok_fullpath"); // NOI18N

        label_dok_size.setText(resourceMap.getString("label_dok_size.text")); // NOI18N
        label_dok_size.setName("label_dok_size"); // NOI18N

        label_dok_created.setText(resourceMap.getString("label_dok_created.text")); // NOI18N
        label_dok_created.setName("label_dok_created"); // NOI18N

        label_dok_changed.setText(resourceMap.getString("label_dok_changed.text")); // NOI18N
        label_dok_changed.setName("label_dok_changed"); // NOI18N

        label_dok_lastview.setText(resourceMap.getString("label_dok_lastview.text")); // NOI18N
        label_dok_lastview.setName("label_dok_lastview"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        label_dok_checksum.setText(resourceMap.getString("label_dok_checksum.text")); // NOI18N
        label_dok_checksum.setName("label_dok_checksum"); // NOI18N

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(label_dok_id))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 482, Short.MAX_VALUE)
                        .addComponent(label_dok_creator))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 485, Short.MAX_VALUE)
                        .addComponent(label_dok_dateiname))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 491, Short.MAX_VALUE)
                        .addComponent(label_dok_fullpath))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 513, Short.MAX_VALUE)
                        .addComponent(label_dok_size))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 484, Short.MAX_VALUE)
                        .addComponent(label_dok_created))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 429, Short.MAX_VALUE)
                        .addComponent(label_dok_changed))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                        .addComponent(label_dok_lastview))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 481, Short.MAX_VALUE)
                        .addComponent(label_dok_checksum)))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(label_dok_id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(label_dok_creator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(label_dok_dateiname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(label_dok_fullpath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(label_dok_size))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(label_dok_created))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(label_dok_changed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(label_dok_lastview))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(label_dok_checksum))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        tab_info.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_erweitert.setName("panel_erweitert"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        combo_vertrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Zuerst einen Kunden auswählen" }));
        combo_vertrag.setName("combo_vertrag"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        combo_versicherung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Gesellschaft" }));
        combo_versicherung.setName("combo_versicherung"); // NOI18N
        combo_versicherung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_versicherungActionPerformed(evt);
            }
        });

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setName("jComboBox3"); // NOI18N

        combo_produkt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Zuerst eine Gesellschaft auswählen" }));
        combo_produkt.setName("combo_produkt"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        field_beschreibung.setName("field_beschreibung"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        combo_tags.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kein Tag" }));
        combo_tags.setName("combo_tags"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        combo_kunden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kein Kunde" }));
        combo_kunden.setName("combo_kunden"); // NOI18N
        combo_kunden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kundenActionPerformed(evt);
            }
        });

        combo_benutzer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kein Benutzer" }));
        combo_benutzer.setName("combo_benutzer"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        btnTags.setIcon(resourceMap.getIcon("btnTags.icon")); // NOI18N
        btnTags.setName("btnTags"); // NOI18N
        btnTags.setPreferredSize(new java.awt.Dimension(25, 25));
        btnTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_erweitertLayout = new javax.swing.GroupLayout(panel_erweitert);
        panel_erweitert.setLayout(panel_erweitertLayout);
        panel_erweitertLayout.setHorizontalGroup(
            panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 377, Short.MAX_VALUE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_erweitertLayout.createSequentialGroup()
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel13)
                            .addComponent(jLabel18)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(133, 133, 133)
                        .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_produkt, 0, 331, Short.MAX_VALUE)
                            .addComponent(combo_versicherung, 0, 331, Short.MAX_VALUE)
                            .addComponent(combo_vertrag, 0, 331, Short.MAX_VALUE)
                            .addComponent(combo_benutzer, javax.swing.GroupLayout.Alignment.TRAILING, 0, 331, Short.MAX_VALUE)
                            .addComponent(field_beschreibung, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                            .addComponent(combo_kunden, javax.swing.GroupLayout.Alignment.TRAILING, 0, 331, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_erweitertLayout.createSequentialGroup()
                                .addComponent(combo_tags, 0, 300, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panel_erweitertLayout.setVerticalGroup(
            panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_erweitertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(field_beschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_tags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(btnTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_kunden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_benutzer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_vertrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_versicherung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_produkt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(97, 97, 97)
                .addGroup(panel_erweitertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        tab_info.addTab(resourceMap.getString("panel_erweitert.TabConstraints.tabTitle"), panel_erweitert); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(334, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tab_info, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(294, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tab_info, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
//        } catch (SQLException e) {
//            Log.databaselogger.fatal("Datenbankfehler: Konnte neue Aufgabe nicht speichern", e);
//            ShowException.showException("Bei der Speicherung der Aufgabe ist ein Datenbank Fehler aufgetretten. "
//                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
//                    ExceptionDialogGui.LEVEL_WARNING, e,
//                    "Schwerwiegend: Konnte die Aufgabe nicht speichern");
//        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void combo_kundenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kundenActionPerformed
        loadVertragCombo(combo_kunden.getSelectedItem());
    }//GEN-LAST:event_combo_kundenActionPerformed

    private void combo_versicherungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_versicherungActionPerformed
        try {
            VersichererObj vers = (VersichererObj) combo_versicherung.getSelectedItem();
            loadProduktCombo(vers);
        } catch (Exception e){
            loadProduktCombo(null);
        }
    }//GEN-LAST:event_combo_versicherungActionPerformed

    private void btnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagsActionPerformed
        TagDialogHelper.showTagDialog();
}//GEN-LAST:event_btnTagsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DokumentenInfo dialog = new DokumentenInfo(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnTags;
    private javax.swing.JComboBox combo_benutzer;
    private javax.swing.JComboBox combo_kunden;
    private javax.swing.JComboBox combo_produkt;
    private javax.swing.JComboBox combo_tags;
    private javax.swing.JComboBox combo_versicherung;
    private javax.swing.JComboBox combo_vertrag;
    private javax.swing.JTextField field_beschreibung;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel label_dok_changed;
    private javax.swing.JLabel label_dok_checksum;
    private javax.swing.JLabel label_dok_created;
    private javax.swing.JLabel label_dok_creator;
    private javax.swing.JLabel label_dok_dateiname;
    private javax.swing.JLabel label_dok_fullpath;
    private javax.swing.JLabel label_dok_id;
    private javax.swing.JLabel label_dok_lastview;
    private javax.swing.JLabel label_dok_size;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JPanel panel_erweitert;
    private javax.swing.JTabbedPane tab_info;
    // End of variables declaration//GEN-END:variables
}
