/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KontakteDialog.java
 *
 * Created on 25.07.2011, 13:47:27
 */
package de.maklerpoint.office.Gui.Kontakte;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.MaximizeHelper;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kontakte.Tools.KontakteSQLMethods;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.Tools.ImageComboBoxRenderer;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class KontakteDialog extends javax.swing.JDialog {
    
    private static final int PRIVAT = 0;
    private static final int FIRMA = 1;
    private static final int BENUTZER = 2;
    private static final int VERSICHERER = 3;
    private static final int PRODUKT = 4;
    private static final int VERTRAG = 5;
    public static final int STOERFALL = 6;
    public static final int SCHADEN = 7;
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private ProduktObj prod = null;
    private VertragObj vtr = null;
    private StoerfallObj stoer = null;
    private SchadenObj schaden = null;
    private boolean update = false;
    private String kdnr = null;
    private KontaktObj kon = null;
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, String kdnr) {
        super(parent, modal);
        this.kdnr = kdnr;
        this.type = PRIVAT; // EGAL Kunde
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, SchadenObj schaden) {
        super(parent, modal);
        this.schaden = schaden;
        this.type = SCHADEN;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, StoerfallObj stoer) {
        super(parent, modal);
        this.stoer = stoer;
        this.type = STOERFALL;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, VertragObj vtr) {
        super(parent, modal);
        this.vtr = vtr;
        this.type = VERTRAG;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, ProduktObj prod) {
        super(parent, modal);
        this.prod = prod;
        this.type = PRODUKT;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, VersichererObj vers) {
        super(parent, modal);
        this.vers = vers;
        this.type = VERSICHERER;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, BenutzerObj ben) {
        super(parent, modal);
        this.benutzer = ben;
        this.type = BENUTZER;
        this.update = false;
        initComponents();
        setUp();
    }
    
    public KontakteDialog(java.awt.Frame parent, boolean modal, KontaktObj kon) {
        super(parent, modal);
        this.kon = kon;
        this.update = true;
        initComponents();
        setUp();
    }
    
    private void setUp() {
        setUpCombos();
        setTitle();
        loadKontakt();        
    }
    
    private void loadKontakt() {
        if (update == false) {
            return;
        }
        
        this.field_name.setText(kon.getName());
        this.field_adresse.setText(kon.getAdresse());
        
        this.field_communication1.setText(kon.getCommunication1());
        this.field_communication2.setText(kon.getCommunication2());
        this.field_communication3.setText(kon.getCommunication3());
        this.field_communication4.setText(kon.getCommunication4());
        this.field_communication5.setText(kon.getCommunication5());
        this.field_communication6.setText(kon.getCommunication6());
        
        this.field_custom1.setText(kon.getCustom1());
        this.field_custom2.setText(kon.getCustom2());
        this.field_custom3.setText(kon.getCustom3());
        
        this.text_comments.setText(kon.getComments());
        
        this.combo_comtype1.setSelectedIndex(kon.getCommunication1Type());
        this.combo_comtype2.setSelectedIndex(kon.getCommunication2Type());
        this.combo_comtype3.setSelectedIndex(kon.getCommunication3Type());
        this.combo_comtype4.setSelectedIndex(kon.getCommunication4Type());
        this.combo_comtype5.setSelectedIndex(kon.getCommunication5Type());
        this.combo_comtype6.setSelectedIndex(kon.getCommunication6Type());        
    }

    /**
     * Combos
     */
    private void setUpCombos() {
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
    }

    /**
     * Titel
     */
    private void setTitle() {
        if (kon != null) {
            this.setTitle("Kontakt " + kon.toString() + " bearbeiten");
        } else {
            this.setTitle("Neuer Kontakt");
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

        pane = new javax.swing.JTabbedPane();
        panel_basis = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        field_name = new javax.swing.JTextField();
        field_adresse = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        combo_comtype1 = new javax.swing.JComboBox();
        combo_comtype3 = new javax.swing.JComboBox();
        combo_comtype2 = new javax.swing.JComboBox();
        combo_comtype4 = new javax.swing.JComboBox();
        combo_comtype5 = new javax.swing.JComboBox();
        combo_comtype6 = new javax.swing.JComboBox();
        field_communication6 = new javax.swing.JTextField();
        field_communication5 = new javax.swing.JTextField();
        field_communication4 = new javax.swing.JTextField();
        field_communication3 = new javax.swing.JTextField();
        field_communication2 = new javax.swing.JTextField();
        field_communication1 = new javax.swing.JTextField();
        panel_sonstiges = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        field_custom1 = new javax.swing.JTextField();
        btnMaxCustom1 = new javax.swing.JButton();
        field_custom2 = new javax.swing.JTextField();
        btnMaxCustom2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        field_custom3 = new javax.swing.JTextField();
        btnMaxCustom3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_comments = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        btnMaxTextKommentar = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(KontakteDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        pane.setName("pane"); // NOI18N

        panel_basis.setName("panel_basis"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        field_name.setName("field_name"); // NOI18N

        field_adresse.setName("field_adresse"); // NOI18N

        jSeparator6.setName("jSeparator6"); // NOI18N

        combo_comtype1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype1.setName("combo_comtype1"); // NOI18N

        combo_comtype3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype3.setName("combo_comtype3"); // NOI18N

        combo_comtype2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype2.setName("combo_comtype2"); // NOI18N

        combo_comtype4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype4.setName("combo_comtype4"); // NOI18N

        combo_comtype5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype5.setName("combo_comtype5"); // NOI18N

        combo_comtype6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype6.setName("combo_comtype6"); // NOI18N

        field_communication6.setName("field_communication6"); // NOI18N

        field_communication5.setName("field_communication5"); // NOI18N

        field_communication4.setName("field_communication4"); // NOI18N

        field_communication3.setName("field_communication3"); // NOI18N

        field_communication2.setName("field_communication2"); // NOI18N

        field_communication1.setName("field_communication1"); // NOI18N

        javax.swing.GroupLayout panel_basisLayout = new javax.swing.GroupLayout(panel_basis);
        panel_basis.setLayout(panel_basisLayout);
        panel_basisLayout.setHorizontalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_adresse, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(field_name, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
                    .addGroup(panel_basisLayout.createSequentialGroup()
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(field_communication1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(field_communication2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(field_communication3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(field_communication4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(field_communication5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(field_communication6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_basisLayout.setVerticalGroup(
            panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_basisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(field_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(field_adresse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_basisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_communication6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
        );

        pane.addTab(resourceMap.getString("panel_basis.TabConstraints.tabTitle"), panel_basis); // NOI18N

        panel_sonstiges.setName("panel_sonstiges"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        field_custom1.setName("field_custom1"); // NOI18N

        btnMaxCustom1.setToolTipText(resourceMap.getString("btnMaxCustom1.toolTipText")); // NOI18N
        btnMaxCustom1.setName("btnMaxCustom1"); // NOI18N
        btnMaxCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom1ActionPerformed(evt);
            }
        });

        field_custom2.setName("field_custom2"); // NOI18N

        btnMaxCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/acyrance/CRM/Gui/resources/lupe.gif"))); // NOI18N
        btnMaxCustom2.setToolTipText(resourceMap.getString("btnMaxCustom2.toolTipText")); // NOI18N
        btnMaxCustom2.setName("btnMaxCustom2"); // NOI18N
        btnMaxCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaxCustom2ActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

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

        jSeparator1.setName("jSeparator1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_comments.setColumns(20);
        text_comments.setRows(5);
        text_comments.setName("text_comments"); // NOI18N
        jScrollPane1.setViewportView(text_comments);

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

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
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addComponent(field_custom3, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMaxCustom3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_custom1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(field_custom2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMaxCustom2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnMaxCustom1, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addComponent(jLabel11)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_sonstigesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaxTextKommentar))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_sonstigesLayout.setVerticalGroup(
            panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_sonstigesLayout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_sonstigesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaxTextKommentar)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                .addContainerGap())
        );

        pane.addTab(resourceMap.getString("panel_sonstiges.TabConstraints.tabTitle"), panel_sonstiges); // NOI18N

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.update == false) {
            int dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
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
            kon = new KontaktObj();
        }
        
        if (this.field_name.getText() == null) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen an.");
            return;
        }
        
        if (!update) {
            if (type == PRIVAT || type == FIRMA) {
                kon.setKundenKennung(kdnr);
            } else if (type == VERSICHERER) {
                kon.setVersichererId(vers.getId());
            } else if (type == VERTRAG) {
                kon.setVertragId(vtr.getId());
            } else if (type == STOERFALL) {
                kon.setStoerId(stoer.getId());
            } else if (type == SCHADEN) {
                kon.setSchadenId(schaden.getId());
            } else if (type == PRODUKT) {
                kon.setProduktId(prod.getId());
            } else if (type == BENUTZER) {
//                kontakte = KontakteSQLMethods.(DatabaseConnection.open(), vers.getId(), getStatus());
            } else {
                throw new NullPointerException("Unbekannter Typ");
            }
        }
        
        if (!update) {
            kon.setCreatorId(BasicRegistry.currentUser.getId());
        }
        
        kon.setName(field_name.getText());
        kon.setAdresse(field_adresse.getText());
        
        kon.setCommunication1(field_communication1.getText());
        kon.setCommunication2(field_communication2.getText());
        kon.setCommunication3(field_communication3.getText());
        kon.setCommunication4(field_communication4.getText());
        kon.setCommunication5(field_communication5.getText());
        kon.setCommunication6(field_communication6.getText());
        kon.setCustom1(field_custom1.getText());
        kon.setCustom2(field_custom2.getText());
        kon.setCustom3(field_custom3.getText());
        
        kon.setCommunication1Type(this.combo_comtype1.getSelectedIndex());
        kon.setCommunication2Type(this.combo_comtype2.getSelectedIndex());
        kon.setCommunication3Type(this.combo_comtype3.getSelectedIndex());
        kon.setCommunication4Type(this.combo_comtype4.getSelectedIndex());
        kon.setCommunication5Type(this.combo_comtype5.getSelectedIndex());
        kon.setCommunication6Type(this.combo_comtype6.getSelectedIndex());
        
        kon.setComments(text_comments.getText());
        
        kon.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        try {
            if (update == false) {
                kon.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                int id = KontakteSQLMethods.insertIntoKontakte(DatabaseConnection.open(), kon);
                
            } else {
                boolean success = KontakteSQLMethods.updateKontakte(DatabaseConnection.open(), kon);
                
                if (!success) {
                    Log.databaselogger.fatal("Konnte den Kontakt nicht updaten (nicht gefunden).");
                    ShowException.showException("Datenbankfehler: Der Kontakt konnte nicht aktualisiert werden",
                            ExceptionDialogGui.LEVEL_WARNING, "Konnte Kontakt nicht speichern");
                }
            }
            this.dispose();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Kontakt nicht speichern.", e);
            ShowException.showException("Datenbankfehler: Der Kontakt konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Konnte Kontakt nicht speichern");
        }
}//GEN-LAST:event_btnSaveActionPerformed
    
    private void btnMaxCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom1ActionPerformed
        MaximizeHelper.openMax(this.field_custom1, "Benutzerdefiniert 1");
}//GEN-LAST:event_btnMaxCustom1ActionPerformed
    
    private void btnMaxCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom2ActionPerformed
        MaximizeHelper.openMax(this.field_custom2, "Benutzerdefiniert 2");
}//GEN-LAST:event_btnMaxCustom2ActionPerformed
    
    private void btnMaxCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxCustom3ActionPerformed
        MaximizeHelper.openMax(this.field_custom3, "Benutzerdefiniert 3");
}//GEN-LAST:event_btnMaxCustom3ActionPerformed
    
    private void btnMaxTextKommentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaxTextKommentarActionPerformed
        MaximizeHelper.openMax(this.text_comments, "Kommentar");
}//GEN-LAST:event_btnMaxTextKommentarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                KontakteDialog dialog = new KontakteDialog(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton btnMaxCustom1;
    private javax.swing.JButton btnMaxCustom2;
    private javax.swing.JButton btnMaxCustom3;
    private javax.swing.JButton btnMaxTextKommentar;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox combo_comtype1;
    private javax.swing.JComboBox combo_comtype2;
    private javax.swing.JComboBox combo_comtype3;
    private javax.swing.JComboBox combo_comtype4;
    private javax.swing.JComboBox combo_comtype5;
    private javax.swing.JComboBox combo_comtype6;
    private javax.swing.JTextField field_adresse;
    private javax.swing.JTextField field_communication1;
    private javax.swing.JTextField field_communication2;
    private javax.swing.JTextField field_communication3;
    private javax.swing.JTextField field_communication4;
    private javax.swing.JTextField field_communication5;
    private javax.swing.JTextField field_communication6;
    private javax.swing.JTextField field_custom1;
    private javax.swing.JTextField field_custom2;
    private javax.swing.JTextField field_custom3;
    private javax.swing.JTextField field_name;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane pane;
    private javax.swing.JPanel panel_basis;
    private javax.swing.JPanel panel_sonstiges;
    private javax.swing.JTextArea text_comments;
    // End of variables declaration//GEN-END:variables
}
