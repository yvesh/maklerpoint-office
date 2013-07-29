/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelSchaeden.java
 *
 * Created on 12.06.2011, 12:31:29
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Schaden.SchaedenDialog;
import de.maklerpoint.office.Konstanten.Schaeden;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Schaeden.Tools.SchaedenSQLMethods;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import de.maklerpoint.office.Waehrungen.WaehrungenObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelSchaeden extends javax.swing.JPanel implements iTabs {

    public static final int KUNDE = 0;
    public static final int GESCHAEFT = 1;
    public static final int VERSICHERER = 2;
    public static final int BENUTZER = 3;
    public static final int PRODUKT = 4;
    public static final int VERTRAG = 5;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private ProduktObj produkt = null;
    private VertragObj vertrag = null;
    private String kdnr = null;
    private static final String[] kundenColumn = {"Hidden", "Bearbeiter", "Vertrag", "Interne Schaden-Nr.", "Schadenshöhe", "Schadensdatum", "Schaden-Nr. (VU)"};
    private static final String[] benutzerColumn = {"Hidden", "Kunde", "Vertrag", "Interne Schaden-Nr.", "Schadenshöhe", "Schadensdatum", "Schaden-Nr. (VU)"};
    private static final String[] vertragColumn = {"Hidden", "Bearbeiter", "Kunde", "Interne SchadenNr.", "Schadenshöhe", "Schadensdatum", "Schaden-Nr. (VU)"};

    /** Creates new for m TabPanelSchaeden */
    public TabPanelSchaeden() {
        initComponents();
        addAnsichtButtons();
    }

    public String getTabName() {
        return "Schäden";
    }

    public void load(KundenObj kunde) {
        this.type = KUNDE;
        this.kunde = kunde;
        kdnr = kunde.getKundenNr();
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.type = GESCHAEFT;
        this.firma = firma;
        kdnr = firma.getKundenNr();
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VersichererObj versicherer) {
//        this.type = VERSICHERER;
//        this.versicherer = versicherer;
//        kdnr = null;
//        loadTable();
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        kdnr = null;
        loadTable();
    }

    public void load(ProduktObj produkt) {
//        this.type = PRODUKT;
//        this.produkt = produkt;
//        kdnr = null;
//        loadTable();
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vertrag = vertrag;
        this.kdnr = vertrag.getKundenKennung();
        loadTable();
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Schadensfall Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private int getStatus() {
        int status = Schaeden.STATUS_OFFEN;
        
        if(this.aktiveDBMenuItem.isSelected()){
            status = Schaeden.STATUS_OFFEN;
        } else if(this.alleDBMenuItem.isSelected()){
            status = -1;
        } if(this.regulierteDBMenuItem.isSelected()){
            status = Schaeden.STATUS_REGULIERT;
        }        
        
        return status;
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            SchadenObj[] sch = null;

            if (type == KUNDE || type == GESCHAEFT) {
                sch = SchaedenSQLMethods.getKundenSchaeden(DatabaseConnection.open(), kdnr, getStatus());
            } else if (type == VERTRAG) {
                sch = SchaedenSQLMethods.getVertragSchaeden(DatabaseConnection.open(), vertrag.getId(), getStatus());
            } else if (type == BENUTZER) {
                sch = SchaedenSQLMethods.getBenutzerSchaeden(DatabaseConnection.open(), benutzer.getId(), getStatus());
            }

            if (sch != null) {
                data = new Object[sch.length][7];

                for (int i = 0; i < sch.length; i++) {
                    VertragObj vtr = VertragRegistry.getVertrag(sch[i].getVertragsId());
                    WaehrungenObj waer = VersicherungsRegistry.getWaehrung(vtr.getWaehrungId());


                    data[i][0] = sch[i];

                    if (type == KUNDE || type == GESCHAEFT || type == VERTRAG) {
                        data[i][1] = BenutzerRegistry.getBenutzer(sch[i].getSchadenBearbeiter());
                    } else if (type == BENUTZER) {
                        data[i][1] = KundenRegistry.getKunde(sch[i].getKundenNr());
                    }

                    if (type == KUNDE || type == GESCHAEFT || type == BENUTZER) {
                        data[i][2] = vtr;
                    } else if (type == VERTRAG) {
                        data[i][2] = KundenRegistry.getKunde(sch[i].getKundenNr());
                    }

                    data[i][3] = sch[i].getSchadenNr();

                    data[i][4] = WaehrungFormat.getFormatedWaehrung(sch[i].getSchadenHoehe(), waer);

                    data[i][5] = df.format(sch[i].getSchaedenTime());

                    data[i][6] = sch[i].getVuSchadennummer();
                }
            }


            if (type == KUNDE || type == GESCHAEFT) {
                setTable(data, kundenColumn);
            } else if (type == VERTRAG) {
                setTable(data, vertragColumn);
            } else if (type == BENUTZER) {
                setTable(data, benutzerColumn);
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Die Schäden konnten nicht geladen werden.", e);
            ShowException.showException("Datenbankfehler: Die Schäden konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Schäden nicht laden");
            setTable(null, kundenColumn);
        }
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {
        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_schaeden.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_schaeden.getModel();
            atm.setData(data);
            table_schaeden.packAll();
            return;
        }

        table_schaeden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_schaeden.setColumnSelectionAllowed(false);
        table_schaeden.setCellSelectionEnabled(false);
        table_schaeden.setRowSelectionAllowed(true);
        table_schaeden.setAutoCreateRowSorter(true);

        table_schaeden.setFillsViewportHeight(true);
        table_schaeden.removeColumn(table_schaeden.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_schaeden.addMouseListener(popupListener);
        table_schaeden.setColumnControlVisible(true);

        JTableHeader header = table_schaeden.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_schaeden.packAll();
    }

    /**
     * Mousepopup
     */
    class TablePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point point = e.getPoint();
                int row = table_schaeden.rowAtPoint(point);
                table_schaeden.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void disableElements() {
        this.btnReguliert.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.table_schaeden.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);
        setTable(null, kundenColumn);
    }

    public void enableElements() {
        this.btnReguliert.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.table_schaeden.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void searchTable() {
        int result = table_schaeden.getSearchable().search(field_search.getText());
    }

    private void newSchaden() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();

        if (type == KUNDE) {
            schaedenDialog = new SchaedenDialog(mainFrame, true, kunde.getKundenNr());
        } else if (type == GESCHAEFT) {
            schaedenDialog = new SchaedenDialog(mainFrame, true, firma.getKundenNr());
        } else if (type == VERTRAG) {
            schaedenDialog = new SchaedenDialog(mainFrame, true, vertrag);
        } else {
            schaedenDialog = new SchaedenDialog(mainFrame, true);
        }

        schaedenDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(schaedenDialog);

        this.loadTable();
    }

    private void editSchaden() {
        int row = table_schaeden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Schaden ausgewählt.");
            return;
        }

        SchadenObj sch = (SchadenObj) this.table_schaeden.getModel().getValueAt(row, 0);

        if (sch == null) {
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        schaedenDialog = new SchaedenDialog(mainFrame, true, sch);
        schaedenDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(schaedenDialog);

        this.loadTable();
    }

    /**
     * Schaden als reguliert (STATUS) markierne 
     */
    private void reguliereSchaden() {
        int row = table_schaeden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Schaden aus.");
            return;
        }

        SchadenObj sch = (SchadenObj) this.table_schaeden.getModel().getValueAt(row, 0);

        if (sch == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Schaden wirklich als Reguliert markieren?",
                    "Bestätigung Regulation", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            SchaedenSQLMethods.reguliereFromSchaeden(DatabaseConnection.open(), sch);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Schaden " + sch.getId() + " nicht regulieren", e);
            ShowException.showException("Datenbankfehler: Der Schaden konnte nicht als Reguliert markiert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Schaden nicht regulieren");
        }

        this.loadTable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        regulierteDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        reguliertMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        toolbar = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnReguliert = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_schaeden = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelSchaeden.class);
        alleDBMenuItem.setText(resourceMap.getString("alleDBMenuItem.text")); // NOI18N
        alleDBMenuItem.setToolTipText(resourceMap.getString("alleDBMenuItem.toolTipText")); // NOI18N
        alleDBMenuItem.setName("alleDBMenuItem"); // NOI18N
        alleDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alleDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(alleDBMenuItem);

        grp_dbstatus.add(aktiveDBMenuItem);
        aktiveDBMenuItem.setMnemonic('O');
        aktiveDBMenuItem.setSelected(true);
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setName("aktiveDBMenuItem"); // NOI18N
        aktiveDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktiveDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(aktiveDBMenuItem);

        grp_dbstatus.add(regulierteDBMenuItem);
        regulierteDBMenuItem.setMnemonic('R');
        regulierteDBMenuItem.setText(resourceMap.getString("regulierteDBMenuItem.text")); // NOI18N
        regulierteDBMenuItem.setName("regulierteDBMenuItem"); // NOI18N
        regulierteDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regulierteDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(regulierteDBMenuItem);

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        newMenuItem.setMnemonic('N');
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

        editMenuItem.setMnemonic('b');
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(editMenuItem);

        reguliertMenuItem.setText(resourceMap.getString("reguliertMenuItem.text")); // NOI18N
        reguliertMenuItem.setName("reguliertMenuItem"); // NOI18N
        reguliertMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reguliertMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(reguliertMenuItem);

        refreshMenuItem.setText(resourceMap.getString("refreshMenuItem.text")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(refreshMenuItem);

        setName("Form"); // NOI18N

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        toolbar.add(btnNeu);

        btnEdit.setIcon(resourceMap.getIcon("btnEdit.icon")); // NOI18N
        btnEdit.setText(resourceMap.getString("btnEdit.text")); // NOI18N
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        toolbar.add(btnEdit);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbar.add(jSeparator3);

        btnReguliert.setIcon(resourceMap.getIcon("btnReguliert.icon")); // NOI18N
        btnReguliert.setText(resourceMap.getString("btnReguliert.text")); // NOI18N
        btnReguliert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnReguliert.setFocusable(false);
        btnReguliert.setName("btnReguliert"); // NOI18N
        btnReguliert.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReguliert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReguliertActionPerformed(evt);
            }
        });
        toolbar.add(btnReguliert);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbar.add(jSeparator2);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        toolbar.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        toolbar.add(field_search);

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
        toolbar.add(btnSearch);

        jSeparator5.setName("jSeparator5"); // NOI18N
        toolbar.add(jSeparator5);

        btnRefresh.setIcon(resourceMap.getIcon("btnRefresh.icon")); // NOI18N
        btnRefresh.setToolTipText(resourceMap.getString("btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        toolbar.add(btnRefresh);

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_schaeden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_schaeden.setColumnControlVisible(true);
        table_schaeden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_schaeden.setName("table_schaeden"); // NOI18N
        table_schaeden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_schaedenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_schaeden);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newSchaden();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editSchaden();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnReguliertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReguliertActionPerformed
        reguliereSchaden();
}//GEN-LAST:event_btnReguliertActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        searchTable();
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void regulierteDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regulierteDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_regulierteDBMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newSchaden();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editSchaden();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void reguliertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reguliertMenuItemActionPerformed
        reguliereSchaden();
}//GEN-LAST:event_reguliertMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    private void table_schaedenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_schaedenMouseClicked
        if (evt.getClickCount() > 1) {
            editSchaden();
        }
    }//GEN-LAST:event_table_schaedenMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReguliert;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JMenuItem reguliertMenuItem;
    private javax.swing.JCheckBoxMenuItem regulierteDBMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_schaeden;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
    private JDialog schaedenDialog;
}
