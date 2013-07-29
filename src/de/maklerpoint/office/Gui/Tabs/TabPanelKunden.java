/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelKunden.java
 *
 * Created on 01.06.2011, 15:25:46
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Firmenkunden.NewFirmenKundeDialog;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Gui.Kunden.NewKundeDialog;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TabPanelKunden extends javax.swing.JPanel implements iTabs {

    private static final int BENUTZER = 0;
    private static final int VERSICHERER = 1;
    private static final int VERTRAG = 2;
    private int type = -1;
    private BenutzerObj benutzer = null;
    private VersichererObj vers = null;
    private VertragObj vtr = null;
    private String[] Columns = new String[]{"Hidden", "Kunden-Nr.", "Name", "Strasse",
        "PLZ", "Ort", "Kommunikation 1", "Kommunikation 2"}; // TODO Extend

    /** Creates new form TabPanelKunden */
    public TabPanelKunden() {
        initComponents();
        addAnsichtButtons();
        addNeuCommandButton();
    }

    public String getTabName() {
        return "Kunden";
    }

    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(FirmenObj firma) {
        throw new UnsupportedOperationException("Der Reiter unterstützt die ausgewählte Ansicht nicht!");
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.vers = versicherer;
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        loadTable();
    }

    public void load(ProduktObj produkt) {
        this.type = VERSICHERER;
        this.vers = VersicherungsRegistry.getVersicher(produkt.getVersichererId());
        loadTable();
    }

    public void load(VertragObj vertrag) {
        this.type = VERTRAG;
        this.vtr = vertrag;
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon(
                "de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Kunden Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private int getStatus() {
        int status = Status.NORMAL;

        if (this.alleDBMenuItem.isSelected()) {
            return -1;
        } else if (this.aktiveDBMenuItem.isSelected()) {
            return Status.NORMAL;
        } else if (this.archivedDBMenuItem.isSelected()) {
            return Status.ARCHIVED;
        } else if (this.deletedDBMenuItem.isSelected()) {
            return Status.DELETED;
        }

        return status;
    }

    private void loadTable() {
        try {
            Object[][] data = null;
            KundenObj[] pk = null;
            FirmenObj[] fk = null;

            if (type == BENUTZER) {
                pk = KundenSQLMethods.loadEigeneKunden(DatabaseConnection.open(), benutzer, getStatus());
                fk = FirmenSQLMethods.loadFirmenKunden(DatabaseConnection.open(), benutzer.getId(), getStatus());
            } else if (type == VERTRAG) {
                KundenObj vtrknd = KundenSQLMethods.loadKunde(DatabaseConnection.open(), vtr.getKundenKennung());

                if (vtrknd != null) {
                    pk = new KundenObj[1];
                    pk[0] = vtrknd;
                }

                FirmenObj vtrfir = KundenRegistry.getFirmenKunde(vtr.getKundenKennung());

                if (vtrfir != null) {
                    fk = new FirmenObj[1];
                    fk[0] = vtrfir;
                }
            } else if (type == VERSICHERER) {
                // TODO implement
            }

            if (pk != null || fk != null) {
                if (pk != null && fk != null) {
                    data = new Object[pk.length + fk.length][8];
                } else if (pk != null) {
                    data = new Object[pk.length][8];
                } else if (fk != null) {
                    data = new Object[fk.length][8];
                }

                int cnt = 0;

                if (pk != null) {
                    for (int i = 0; i < pk.length; i++) {
                        data[i][0] = pk[i];
                        data[i][1] = pk[i].getKundenNr();
                        data[i][2] = pk[i].getVorname() + " " + pk[i].getNachname();
                        data[i][3] = pk[i].getStreet();
                        data[i][4] = pk[i].getPlz();
                        data[i][5] = pk[i].getStadt();

                        data[i][6] = CommunicationTypes.getCommunicationLabel(pk[i], 1);
                        data[i][7] = CommunicationTypes.getCommunicationLabel(pk[i], 2);



                        cnt++;
                    }
                }

                if (fk != null) {
                    for (int i = 0; i < fk.length; i++) {
                        data[cnt][0] = fk[i];
                        data[cnt][1] = fk[i].getKundenNr();
                        data[cnt][2] = fk[i].getFirmenName() + " " + fk[i].getFirmenNameZusatz();
                        data[cnt][3] = fk[i].getFirmenStrasse();
                        data[cnt][4] = fk[i].getFirmenPLZ();
                        data[cnt][5] = fk[i].getFirmenStadt();
                        if (fk[i].getCommunication1() != null && fk[i].getCommunication1().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(fk[i].getCommunication1());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[fk[i].getCommunication1Type()]);

                            data[cnt][6] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[cnt][6] = com;
                        }
                        if (fk[i].getCommunication2() != null && fk[i].getCommunication2().length() > 0) {
                            JLabel com = new JLabel();
                            com.setText(fk[i].getCommunication2());
                            com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[fk[i].getCommunication2Type()]);

                            data[cnt][7] = com;
                        } else {
                            JLabel com = new JLabel();
                            data[cnt][7] = com;
                        }

                        cnt++;
                    }
                }

            } // END if

            setTable(data, Columns);

        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Kunden nicht aus der Datenbank laden", e);
            ShowException.showException("Konnte die Kunden nicht aus der Datenbank laden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Kunden nicht laden");
        }
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {

        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_kunden.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_kunden.getModel();
            atm.setData(data);
            table_kunden.packAll();
            return;
        }

        table_kunden.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kunden.setColumnSelectionAllowed(false);
        table_kunden.setCellSelectionEnabled(false);
        table_kunden.setRowSelectionAllowed(true);
        table_kunden.setAutoCreateRowSorter(true);

        table_kunden.setFillsViewportHeight(true);
        table_kunden.removeColumn(table_kunden.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_kunden.addMouseListener(popupListener);
        table_kunden.setColumnControlVisible(true);

        JTableHeader header = table_kunden.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_kunden.packAll();
    }

    public void disableElements() {
        setTable(null, Columns);

        this.table_kunden.setEnabled(false);
        this.btnArchive.setEnabled(false);
        this.btnDelete.setEnabled(false);
        if (neuButton != null) {
            this.neuButton.setEnabled(false);
        }
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.btnKarte.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnRefresh.setEnabled(false);
        this.dropDownButton.setEnabled(false);
    }

    public void enableElements() {
        this.table_kunden.setEnabled(true);
        this.btnArchive.setEnabled(true);
        this.btnDelete.setEnabled(true);
        if (neuButton != null) {
            this.neuButton.setEnabled(true);
        }
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.btnKarte.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnRefresh.setEnabled(true);
        this.dropDownButton.setEnabled(true);
    }

    private void addNeuCommandButton() {

        neuButton = new JCommandButton("Neu", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/add.png"));
        neuButton.setExtraText("Neuer Privat- oder Geschäftskunde");

        RichTooltip tooltip1 = new RichTooltip("Neuer Kunde", "Erstellen Sie einen neuen Privat- oder Geschäftskunden.");

        neuButton.setPopupRichTooltip(tooltip1);
        neuButton.setPopupCallback(new NeuPopupCallback());
        neuButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        neuButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        neuButton.setFlat(true);

        this.toolbar.add(neuButton, 0);
    }

    /**
     *
     */
    private class NeuPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton privat = new JCommandMenuButton("Privatkunde",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/icon_clean/user-white.png"));
            privat.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    newPrivatkunde();
                }
            });

            popupMenu.addMenuButton(privat);

            JCommandMenuButton firma = new JCommandMenuButton("Geschäftskunde",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/icon_clean/user-business-boss.png"));
            firma.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    newGeschaeftskunde();
                }
            });

            popupMenu.addMenuButton(firma);

            return popupMenu;
        }
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16, 16));
    }

    /**
     * POPUP LISTENER
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
                int row = table_kunden.rowAtPoint(point);
                table_kunden.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void newPrivatkunde() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newPKundeBox = new NewKundeDialog(mainFrame, false);
        newPKundeBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        return null;
                    }
                });
            }
        });
        newPKundeBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(newPKundeBox);
    }

    private void newGeschaeftskunde() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        newFKundeBox = new NewFirmenKundeDialog(mainFrame, false);
        newFKundeBox.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                SwingUtilities.invokeLater(new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        loadTable();
                        return null;
                    }
                });
            }
        });
        newFKundeBox.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(newFKundeBox);
    }

    private void searchTable() {
        int result = table_kunden.getSearchable().search(field_search.getText());
    }

    /**
     * Kunde bearbeiten
     */
    private void editKunde() {
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.");
            return;
        }

        Object kunde = this.table_kunden.getModel().getValueAt(row, 0);

        if (kunde.getClass().equals(KundenObj.class)) {
            KundenObj pk = (KundenObj) kunde;

            if (pk == null) {
                return;
            }

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            newPKundeBox = new NewKundeDialog(mainFrame, true, pk);
            newPKundeBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(newPKundeBox);

        } else if (kunde.getClass().equals(FirmenObj.class)) {
            FirmenObj fk = (FirmenObj) kunde;

            if (fk == null) {
                return;
            }

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            newFKundeBox = new NewFirmenKundeDialog(mainFrame, true, fk);
            newFKundeBox.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(newFKundeBox);
        } else {
            return;
        }

        this.loadTable();
    }

    /**
     * Kunde archivieren
     */
    private void archiveKunde() {
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.");
            return;
        }

        Object kunde = this.table_kunden.getModel().getValueAt(row, 0);
        boolean success = KundenRegistry.archiveKunde(kunde);

        if (success) {
            this.loadTable();
        }
    }

    private void deleteKunde() {
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Kunden aus.");
            return;
        }

        Object kunde = this.table_kunden.getModel().getValueAt(row, 0);
        boolean success = KundenRegistry.deleteKunde(kunde);

        if (success) {
            this.loadTable();
        }
    }

    private void showKarte() {
        int row = table_kunden.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Zusatzadresse ausgewählt.");
            return;
        }

        Object kunde = this.table_kunden.getModel().getValueAt(row, 0);

        if (kunde.getClass().equals(KundenObj.class)) {
            KundenObj pk = (KundenObj) kunde;

            if (pk == null) {
                return;
            }

            KarteSuche.doExteneralSearch(pk.getStreet() + ", "
                    + pk.getStadt(), CRMView.getInstance());
        } else if (kunde.getClass().equals(FirmenObj.class)) {
            FirmenObj fk = (FirmenObj) kunde;
            if (fk == null) {
                return;
            }

            KarteSuche.doExteneralSearch(fk.getFirmenStrasse() + ", "
                    + fk.getFirmenStadt(), CRMView.getInstance());
        } else {
            throw new NullPointerException("Kein Kunden POJO");
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

        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        tablePopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        newGKMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        editMenuItem = new javax.swing.JMenuItem();
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        karteMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        refreshMenuItem = new javax.swing.JMenuItem();
        toolbar = new javax.swing.JToolBar();
        btnEdit = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnKarte = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_kunden = new org.jdesktop.swingx.JXTable();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelKunden.class);
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
        aktiveDBMenuItem.setMnemonic('A');
        aktiveDBMenuItem.setSelected(true);
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setName("aktiveDBMenuItem"); // NOI18N
        aktiveDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktiveDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(aktiveDBMenuItem);

        grp_dbstatus.add(archivedDBMenuItem);
        archivedDBMenuItem.setMnemonic('A');
        archivedDBMenuItem.setText(resourceMap.getString("archivedDBMenuItem.text")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('A');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        tablePopupMenu.setName("tablePopupMenu"); // NOI18N

        newMenuItem.setMnemonic('P');
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

        newGKMenuItem.setMnemonic('G');
        newGKMenuItem.setText(resourceMap.getString("newGKMenuItem.text")); // NOI18N
        newGKMenuItem.setName("newGKMenuItem"); // NOI18N
        newGKMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGKMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newGKMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        tablePopupMenu.add(jSeparator1);

        editMenuItem.setMnemonic('b');
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(editMenuItem);

        archiveMenuItem.setText(resourceMap.getString("archiveMenuItem.text")); // NOI18N
        archiveMenuItem.setName("archiveMenuItem"); // NOI18N
        archiveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(archiveMenuItem);

        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(deleteMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        tablePopupMenu.add(jSeparator3);

        karteMenuItem.setMnemonic('K');
        karteMenuItem.setText(resourceMap.getString("karteMenuItem.text")); // NOI18N
        karteMenuItem.setName("karteMenuItem"); // NOI18N
        karteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karteMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(karteMenuItem);

        jSeparator4.setName("jSeparator4"); // NOI18N
        tablePopupMenu.add(jSeparator4);

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

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setText(resourceMap.getString("btnArchive.text")); // NOI18N
        btnArchive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnArchive.setFocusable(false);
        btnArchive.setName("btnArchive"); // NOI18N
        btnArchive.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        toolbar.add(btnArchive);

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        toolbar.add(btnDelete);

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

        btnKarte.setIcon(resourceMap.getIcon("btnKarte.icon")); // NOI18N
        btnKarte.setText(resourceMap.getString("btnKarte.text")); // NOI18N
        btnKarte.setToolTipText(resourceMap.getString("btnKarte.toolTipText")); // NOI18N
        btnKarte.setFocusable(false);
        btnKarte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnKarte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKarte.setName("btnKarte"); // NOI18N
        btnKarte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKarte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKarteActionPerformed(evt);
            }
        });
        toolbar.add(btnKarte);

        jSeparator6.setName("jSeparator6"); // NOI18N
        toolbar.add(jSeparator6);

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

        table_kunden.setModel(new javax.swing.table.DefaultTableModel(
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
        table_kunden.setColumnControlVisible(true);
        table_kunden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kunden.setName("table_kunden"); // NOI18N
        table_kunden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kundenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_kunden);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editKunde();
}//GEN-LAST:event_btnEditActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveKunde();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteKunde();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKarteActionPerformed
        showKarte();
}//GEN-LAST:event_btnKarteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void archivedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_archivedDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_deletedDBMenuItemActionPerformed

    private void table_kundenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kundenMouseClicked
        if (evt.getClickCount() >= 2) {
            final int row = table_kunden.getSelectedRow();

            if (row == -1) {
                return;
            }

            editKunde();
        }
    }//GEN-LAST:event_table_kundenMouseClicked

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newPrivatkunde();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editKunde();
}//GEN-LAST:event_editMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveKunde();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteKunde();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
}//GEN-LAST:event_refreshMenuItemActionPerformed

    private void newGKMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGKMenuItemActionPerformed
        newGeschaeftskunde();
    }//GEN-LAST:event_newGKMenuItemActionPerformed

    private void karteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karteMenuItemActionPerformed
        showKarte();
    }//GEN-LAST:event_karteMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JMenuItem archiveMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnKarte;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextField field_search;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JMenuItem karteMenuItem;
    private javax.swing.JMenuItem newGKMenuItem;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_kunden;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
    private JDialog newPKundeBox;
    private JDialog newFKundeBox;
    private JCommandButton neuButton;
}
