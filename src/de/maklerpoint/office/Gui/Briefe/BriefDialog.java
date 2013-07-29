/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BriefDialog.java
 *
 * Created on 12.09.2010, 12:14:19
 */
package de.maklerpoint.office.Gui.Briefe;

import de.maklerpoint.office.Briefe.BriefCategoryObj;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefeSQLMethods;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Konstanten.Briefe;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author yves
 */
public class BriefDialog extends javax.swing.JDialog {

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    public static final int PRIVAT = 1;
    public static final int GESCH = 2;
    public static final int VERS = 3;
    public static final int PROD = 4;
    public static final int VERTR = 5;
    public static final int STOER = 6;
    public static final int BEN = 7;
    public static final int EDIT = 10; // Modus zum bearbeiten der Briefe
    private int type = -1;
    private int briefCount = 0;
    private String[] BriefColumnNames = new String[]{"Hidden", "Typ", "Id", "Name", "Dateipfad", "Beschreibung", "Benutzer", "Erstellt"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
    private BriefObj returnBrief = null;

    /** Creates new form BriefDialog */
    public BriefDialog(java.awt.Frame parent, boolean modal, int type) {
        super(parent, modal);
        this.type = type;
        this.returnBrief = null;
        initComponents();
        setUp();
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public Object getReturnStatus() {
        return returnBrief;
    }

    private void setUp() {
        this.combo_category.setModel(new DefaultComboBoxModel(ComboBoxGetter.getBriefCategoriesCombo("Alle")));
        loadTable(-1, -1);
    }

    public void loadTable(int cat, int type) {
        try {
            BriefObj[] briefe = null;

            System.out.println("Typ: " + type + " | Kategorie " + cat);

            if (type != -1 && cat != -1) {
                briefe = BriefeSQLMethods.getBriefe(DatabaseConnection.open(), cat, type, Status.NORMAL);
            } else if (type != -1) {
                briefe = BriefeSQLMethods.getBriefeOhneCat(DatabaseConnection.open(), type, Status.NORMAL);
            } else if (cat != -1) {
                briefe = BriefeSQLMethods.getBriefe(DatabaseConnection.open(), cat, -1, Status.NORMAL);
            } else if (type == -1 && type == -1) {
                briefe = BriefeSQLMethods.getBriefeOhneCat(DatabaseConnection.open(), -1, Status.NORMAL);
            }

            Object[][] brfData = null;

            if (briefe != null) {
                brfData = new Object[briefe.length][8];

                for (int i = 0; i < briefe.length; i++) {
                    brfData[i][0] = briefe[i];
                    brfData[i][1] = Briefe.TYPE_IMAGES[briefe[i].getType()];
                    brfData[i][2] = briefe[i].getId();
                    brfData[i][3] = briefe[i].getName();
                    brfData[i][4] = briefe[i].getFullpath();
                    brfData[i][5] = briefe[i].getBeschreibung();
                    brfData[i][6] = BenutzerRegistry.getBenutzer(briefe[i].getBenutzerId(), true);
                    brfData[i][7] = df.format(briefe[i].getCreated());
                }

                briefCount = briefe.length;
            } else {
                brfData = null;
                briefCount = 0;
            }

            AbstractStandardModel sm = new AbstractStandardModel(brfData, BriefColumnNames);
            this.table_briefe.setModel(sm);

            table_briefe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table_briefe.setColumnSelectionAllowed(false);
            table_briefe.setCellSelectionEnabled(false);
            table_briefe.setRowSelectionAllowed(true);
            table_briefe.setAutoCreateRowSorter(true);

            table_briefe.setFillsViewportHeight(true);
            table_briefe.removeColumn(table_briefe.getColumnModel().getColumn(0));

            MouseListener popupListener = new TablePopupListener();
            table_briefe.addMouseListener(popupListener);
            table_briefe.setColumnControlVisible(true);

            JTableHeader header = table_briefe.getTableHeader();
            header.addMouseListener(popupListener);
            header.validate();
            table_briefe.packAll(); // Autsize     

            table_briefe.tableChanged(new TableModelEvent(table_briefe.getModel()));
            table_briefe.revalidate();


        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Briefe nicht laden", e);
            ShowException.showException("Die Briefe konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Briefe nicht laden");
        }
    }

    class TablePopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point point = e.getPoint();
                final int row = table_briefe.rowAtPoint(point);
                tablePopup.show(e.getComponent(), e.getX(), e.getY());
                table_briefe.changeSelection(row, 0, false, false);
            }
        }
    }

    private void searchTable() {
        int result = table_briefe.getSearchable().search(field_search.getText());
    }

    private BriefObj getSelectedBrief() {
        int row = this.table_briefe.getSelectedRow();

        if (row == -1) {
            return null;
        }

        BriefObj brief = (BriefObj) table_briefe.getModel().getValueAt(row, 0);

        return brief;
    }

    private int getBriefCat() {
        int cat_id = -1;

        try {
            BriefCategoryObj bcat = (BriefCategoryObj) combo_category.getSelectedItem();
            cat_id = bcat.getId();
        } catch (Exception e) {
        }

        return cat_id;
    }

    private int getBriefType() {
        int brftype = -1;

        if (this.btnBrief.isSelected()) {
            return Briefe.TYPE_BRIEF;
        }
        if (this.btnFax.isSelected()) {
            return Briefe.TYPE_FAX;
        }
        if (this.btnMail.isSelected()) {
            return Briefe.TYPE_EMAIL;
        }

        return brftype;
    }

    private void editBrief() {
        BriefObj brief = getSelectedBrief();

        if (brief == null) {
            return;
        }
        
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neubrief = new NeuBriefDialog(mainFrame, true, brief);
        neubrief.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(neubrief);
        loadTable(getBriefCat(), getBriefType());
    }

    private void newBrief() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neubrief = new NeuBriefDialog(mainFrame, true);
        neubrief.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(neubrief);
        loadTable(getBriefCat(), getBriefType());
    }
    
    private void deleteBrief(){
        BriefObj brief = getSelectedBrief();

        if (brief == null) {
            return;
        }
        
        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int choose = JOptionPane.showConfirmDialog(null, "Wollen Sie den Brief wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (choose != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            BriefeSQLMethods.deleteFromBriefe(DatabaseConnection.open(), brief);            
        } catch (Exception e) {
            Log.databaselogger.warn("Der Brief konnte nicht gelöscht werden.", e);
            ShowException.showException("Der Brief konnte nicht gelöscht werden. Unter Details "
                    + "finden Sie weitere Informationen zur Fehlermeldung",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Brief nicht löschen");
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

        grpBrief = new javax.swing.ButtonGroup();
        tablePopup = new javax.swing.JPopupMenu();
        newBriefItem = new javax.swing.JMenuItem();
        editBriefItem = new javax.swing.JMenuItem();
        deleteBriefItem = new javax.swing.JMenuItem();
        tableRefresh = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_briefe = new org.jdesktop.swingx.JXTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        combo_category = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnAll = new javax.swing.JToggleButton();
        btnBrief = new javax.swing.JToggleButton();
        btnFax = new javax.swing.JToggleButton();
        btnMail = new javax.swing.JToggleButton();
        btnCancel = new javax.swing.JButton();
        btnChose = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(BriefDialog.class);
        tablePopup.setBackground(resourceMap.getColor("tablePopup.background")); // NOI18N
        tablePopup.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablePopup.setName("tablePopup"); // NOI18N

        newBriefItem.setIcon(null);
        newBriefItem.setMnemonic('N');
        newBriefItem.setText(resourceMap.getString("newBriefItem.text")); // NOI18N
        newBriefItem.setName("newBriefItem"); // NOI18N
        newBriefItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBriefItemActionPerformed(evt);
            }
        });
        tablePopup.add(newBriefItem);

        editBriefItem.setIcon(null);
        editBriefItem.setMnemonic('b');
        editBriefItem.setText(resourceMap.getString("editBriefItem.text")); // NOI18N
        editBriefItem.setName("editBriefItem"); // NOI18N
        editBriefItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBriefItemActionPerformed(evt);
            }
        });
        tablePopup.add(editBriefItem);

        deleteBriefItem.setIcon(null);
        deleteBriefItem.setMnemonic('l');
        deleteBriefItem.setText(resourceMap.getString("deleteBriefItem.text")); // NOI18N
        deleteBriefItem.setName("deleteBriefItem"); // NOI18N
        deleteBriefItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBriefItemActionPerformed(evt);
            }
        });
        tablePopup.add(deleteBriefItem);

        tableRefresh.setIcon(null);
        tableRefresh.setMnemonic('a');
        tableRefresh.setText(resourceMap.getString("tableRefresh.text")); // NOI18N
        tableRefresh.setName("tableRefresh"); // NOI18N
        tableRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableRefreshActionPerformed(evt);
            }
        });
        tablePopup.add(tableRefresh);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table_briefe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Kategorie", "Datei", "Öffentlich"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_briefe.setName("table_briefe"); // NOI18N
        table_briefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_briefeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_briefe);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setMnemonic('N');
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNeu);

        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setMnemonic('B');
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setFocusable(false);
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setMnemonic('L');
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setMinimumSize(new java.awt.Dimension(85, 16));
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(85, 15));
        jToolBar1.add(jLabel1);

        combo_category.setMaximumSize(new java.awt.Dimension(170, 25));
        combo_category.setName("combo_category"); // NOI18N
        combo_category.setPreferredSize(new java.awt.Dimension(150, 25));
        combo_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_categoryActionPerformed(evt);
            }
        });
        jToolBar1.add(combo_category);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(120, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        jToolBar1.add(field_search);

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
        jToolBar1.add(btnSearch);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

        grpBrief.add(btnAll);
        btnAll.setIcon(resourceMap.getIcon("btnAll.icon")); // NOI18N
        btnAll.setSelected(true);
        btnAll.setToolTipText(resourceMap.getString("btnAll.toolTipText")); // NOI18N
        btnAll.setFocusable(false);
        btnAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAll.setName("btnAll"); // NOI18N
        btnAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAll);

        grpBrief.add(btnBrief);
        btnBrief.setIcon(resourceMap.getIcon("btnBrief.icon")); // NOI18N
        btnBrief.setText(resourceMap.getString("btnBrief.text")); // NOI18N
        btnBrief.setToolTipText(resourceMap.getString("btnBrief.toolTipText")); // NOI18N
        btnBrief.setFocusable(false);
        btnBrief.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBrief.setName("btnBrief"); // NOI18N
        btnBrief.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBrief.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBriefActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBrief);

        grpBrief.add(btnFax);
        btnFax.setIcon(resourceMap.getIcon("btnFax.icon")); // NOI18N
        btnFax.setText(resourceMap.getString("btnFax.text")); // NOI18N
        btnFax.setToolTipText(resourceMap.getString("btnFax.toolTipText")); // NOI18N
        btnFax.setFocusable(false);
        btnFax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFax.setName("btnFax"); // NOI18N
        btnFax.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaxActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFax);

        grpBrief.add(btnMail);
        btnMail.setIcon(resourceMap.getIcon("btnMail.icon")); // NOI18N
        btnMail.setToolTipText(resourceMap.getString("btnMail.toolTipText")); // NOI18N
        btnMail.setFocusable(false);
        btnMail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMail.setName("btnMail"); // NOI18N
        btnMail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMail);

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnChose.setMnemonic('A');
        btnChose.setText(resourceMap.getString("btnChose.text")); // NOI18N
        btnChose.setEnabled(false);
        btnChose.setName("btnChose"); // NOI18N
        btnChose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoseActionPerformed(evt);
            }
        });

        btnHelp.setMnemonic('H');
        btnHelp.setText(resourceMap.getString("btnHelp.text")); // NOI18N
        btnHelp.setName("btnHelp"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(486, Short.MAX_VALUE)
                .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChose, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChose)
                    .addComponent(btnCancel)
                    .addComponent(btnHelp))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_briefeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_briefeMouseClicked
        BriefObj brief = getSelectedBrief();

        if (brief == null) {
            return;
        }

        this.btnChose.setEnabled(false);

        if (this.type == PRIVAT) {
            if (brief.isPrivatKunde()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == GESCH) {
            if (brief.isGeschKunde()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == VERS) {
            if (brief.isVersicherer()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == PROD) {
            if (brief.isProdukt()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == VERTR) {
            if (brief.isVertrag()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == STOER) {
            if (brief.isStoerfall()) {
                this.btnChose.setEnabled(true);
            }
        } else if (this.type == BEN) {
            if (brief.isBenutzer()) {
                this.btnChose.setEnabled(true);
            }
        }

        if (brief.isLoeschbar()) {
            this.btnDelete.setEnabled(true);
        } else {
            this.btnDelete.setEnabled(false);
        }

        if (evt.getClickCount() > 1) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            neubrief = new NeuBriefDialog(mainFrame, true, brief);
            neubrief.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(neubrief);
            loadTable(getBriefCat(), getBriefType());
        }
}//GEN-LAST:event_table_briefeMouseClicked

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void combo_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_categoryActionPerformed
        loadTable(getBriefCat(), getBriefType());
    }//GEN-LAST:event_combo_categoryActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.returnBrief = null;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        loadTable(getBriefCat(), -1);
    }//GEN-LAST:event_btnAllActionPerformed

    private void btnBriefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBriefActionPerformed
        loadTable(getBriefCat(), Briefe.TYPE_BRIEF);
    }//GEN-LAST:event_btnBriefActionPerformed

    private void btnFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaxActionPerformed
        loadTable(getBriefCat(), Briefe.TYPE_FAX);
    }//GEN-LAST:event_btnFaxActionPerformed

    private void btnMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailActionPerformed
        loadTable(getBriefCat(), Briefe.TYPE_EMAIL);
    }//GEN-LAST:event_btnMailActionPerformed

    private void btnChoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoseActionPerformed
        returnBrief = getSelectedBrief();
        this.dispose();
    }//GEN-LAST:event_btnChoseActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newBrief();
    }//GEN-LAST:event_btnNeuActionPerformed

    private void newBriefItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBriefItemActionPerformed
        newBrief();
}//GEN-LAST:event_newBriefItemActionPerformed

    private void editBriefItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBriefItemActionPerformed
        editBrief();
}//GEN-LAST:event_editBriefItemActionPerformed

    private void deleteBriefItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBriefItemActionPerformed
        deleteBrief();
}//GEN-LAST:event_deleteBriefItemActionPerformed

    private void tableRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableRefreshActionPerformed
        loadTable(getBriefCat(), getBriefType());
}//GEN-LAST:event_tableRefreshActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editBrief();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteBrief();
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BriefDialog dialog = new BriefDialog(new javax.swing.JFrame(), true, -1);
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
    public javax.swing.JToggleButton btnAll;
    public javax.swing.JToggleButton btnBrief;
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnChose;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JToggleButton btnFax;
    public javax.swing.JButton btnHelp;
    public javax.swing.JToggleButton btnMail;
    public javax.swing.JButton btnNeu;
    public javax.swing.JButton btnSearch;
    public javax.swing.JComboBox combo_category;
    public javax.swing.JMenuItem deleteBriefItem;
    public javax.swing.JMenuItem editBriefItem;
    public javax.swing.JTextField field_search;
    public javax.swing.ButtonGroup grpBrief;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JToolBar.Separator jSeparator1;
    public javax.swing.JToolBar.Separator jSeparator3;
    public javax.swing.JToolBar.Separator jSeparator4;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JMenuItem newBriefItem;
    public javax.swing.JPopupMenu tablePopup;
    public javax.swing.JMenuItem tableRefresh;
    public org.jdesktop.swingx.JXTable table_briefe;
    // End of variables declaration//GEN-END:variables
    JDialog neubrief;
}
