/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabPanelAnsprechpartner.java
 *
 * Created on 07.09.2010, 10:13:01
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Kunden.NewKundeHelper;
import de.maklerpoint.office.Konstanten.FirmenInformationen;
import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Table.TermineModel;
import de.maklerpoint.office.Tools.ImageComboBoxRenderer;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.openide.awt.DropDownButtonFactory;
import org.openide.util.Exceptions;

/**
 *
 * @author yves
 */
public class TabPanelAnsprechpartner extends javax.swing.JPanel implements iTabs {

    private int PRIVAT = 0;
    private int FIRMA = 1;
    private int VERSICHERER = 2;
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private String kennung = null;
    private String[] Columns = new String[]{"Hidden", "Vorname", "Nachname", "Abteilung",
        "Funktion", "Kommunikation 1", "Kommunikation 2"};
    private FirmenAnsprechpartnerObj currentAnsprech = null;
    private boolean enabled = true;
    
    private FirmenAnsprechpartnerObj currentfa = null;

    /** Creates new form TabPanelAnsprechpartner */
    public TabPanelAnsprechpartner() {
        initComponents();
        setupCombos();
        addAnsichtButtons();
    }

    private void addAnsichtButtons() {
        dropDownButton = DropDownButtonFactory.createDropDownButton(
                ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Ansprechpartner Ansicht");
        //dropDownButton.setText();
        this.jToolBar1.add(dropDownButton);
    }

    public String getTabName() {
        return "Ansprechpartner";
    }

    public void load(KundenObj kunde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(FirmenObj firma) {
        this.firma = firma;
        this.kennung = firma.getKundenNr();
        type = FIRMA;
        setUp(-1);
    }

    public void load(VersichererObj versicherer) {
        this.versicherer = versicherer;
        this.type = VERSICHERER;
        setUp(-1);
    }

    public void load(ProduktObj produkt) {
        this.versicherer = VersicherungsRegistry.getVersicher(produkt.getVersichererId());
        this.type = VERSICHERER;
        setUp(-1);
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void resetFields() {
        this.field_communication1.setText(null);
        this.field_communication2.setText(null);
        this.field_communication3.setText(null);
        this.field_communication4.setText(null);
        this.field_communication5.setText(null);

        this.combo_comtype1.setSelectedIndex(0);
        this.combo_comtype2.setSelectedIndex(1);
        this.combo_comtype3.setSelectedIndex(4);
        this.combo_comtype4.setSelectedIndex(7);
        this.combo_comtype5.setSelectedIndex(10);
        
        this.combo_anrede.setSelectedItem("Herr");
        this.combo_abteilung.setSelectedItem("Unbekannt");
        this.combo_funktion.setSelectedItem("Unbekannt");
        this.combo_status.setSelectedIndex(0);
        this.combo_prioritaet.setSelectedIndex(0);
        this.combo_titel.setSelectedItem("");
                        
        this.field_vorname.setText(null);
        this.field_nachname.setText(null);
        this.date_geburtsdatum.setDate(null);
    }

    public void disableElements() {
        enabled = false;

        setTable(null, Columns);

        this.table_ansprechpartner.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnNeu.setEnabled(false);
        this.btnSearch.setEnabled(false);
        this.field_search.setEnabled(false);
        this.dropDownButton.setEnabled(false);

        disableAnsprechpartnerElements();
    }

    public void enableElements() {
        enabled = true;

        this.table_ansprechpartner.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnNeu.setEnabled(true);
        this.btnSearch.setEnabled(true);
        this.field_search.setEnabled(true);
        this.dropDownButton.setEnabled(true);

        enableAnsprechpartnerElements();
    }

    private void disableAnsprechpartnerElements() {
        this.btnSave.setEnabled(false);
        this.field_communication1.setEnabled(false);
        this.field_communication2.setEnabled(false);
        this.field_communication3.setEnabled(false);
        this.field_communication4.setEnabled(false);
        this.field_communication5.setEnabled(false);
        this.field_nachname.setEnabled(false);
        this.field_vorname.setEnabled(false);

        this.combo_abteilung.setEnabled(false);
        this.combo_anrede.setEnabled(false);
        this.combo_comtype1.setEnabled(false);
        this.combo_comtype2.setEnabled(false);
        this.combo_comtype3.setEnabled(false);
        this.combo_comtype4.setEnabled(false);
        this.combo_comtype5.setEnabled(false);
        this.combo_funktion.setEnabled(false);
        this.combo_prioritaet.setEnabled(false);
        this.combo_status.setEnabled(false);
        this.combo_titel.setEnabled(false);
        this.date_geburtsdatum.setEnabled(false);
//        System.out.println("Deaktiviere Ansprechpartner Buttons");
    }

    private void enableAnsprechpartnerElements() {
        this.btnSave.setEnabled(true);
        this.field_communication1.setEnabled(true);
        this.field_communication2.setEnabled(true);
        this.field_communication3.setEnabled(true);
        this.field_communication4.setEnabled(true);
        this.field_communication5.setEnabled(true);
        this.field_nachname.setEnabled(true);
        this.field_vorname.setEnabled(true);
        this.combo_abteilung.setEnabled(true);
        this.combo_anrede.setEnabled(true);
        this.combo_comtype1.setEnabled(true);
        this.combo_comtype2.setEnabled(true);
        this.combo_comtype3.setEnabled(true);
        this.combo_comtype4.setEnabled(true);
        this.combo_comtype5.setEnabled(true);
        this.combo_funktion.setEnabled(true);
        this.combo_prioritaet.setEnabled(true);
        this.combo_status.setEnabled(true);
        this.combo_titel.setEnabled(true);
        this.date_geburtsdatum.setEnabled(true);
//        System.out.println("Aktiviere Ansprechpartner Buttons");
    }

    public void load(BenutzerObj benutzer) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    private void setupCombos() {
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

        this.combo_abteilung.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_ABTEILUNGEN));
        this.combo_funktion.setModel(new DefaultComboBoxModel(FirmenInformationen.FIRMEN_FUNKTIONEN));
    }

    private void setUp(int id) {
        try {          
            resetFields();

            Object[][] data = null;
            FirmenAnsprechpartnerObj[] fas = null;

            int selrow = -1;
            FirmenAnsprechpartnerObj selkund = null;

            if (type == PRIVAT || type == FIRMA) {
                fas = FirmenAnsprechpartnerSQLMethods.loadAnsprechpartner(DatabaseConnection.open(), kennung, getStatus());
            } else if (type == VERSICHERER) {
                fas = FirmenAnsprechpartnerSQLMethods.loadAnsprechpartnerVers(DatabaseConnection.open(),
                        versicherer.getId(), getStatus());
            }

            if (fas != null) {
                data = new Object[fas.length][7];

                for (int i = 0; i < fas.length; i++) {

                    if (fas[i].getId() == id) {
                        selrow = i;
                        selkund = fas[i];
                    }

                    data[i][0] = fas[i];
                    data[i][1] = fas[i].getVorname();
                    data[i][2] = fas[i].getNachname();
                    data[i][3] = fas[i].getAbteilung();
                    data[i][4] = fas[i].getFunktion();
                    if (fas[i].getCommunication1() != null && fas[i].getCommunication1().length() > 0) {
                        JLabel com = new JLabel();
                        com.setText(fas[i].getCommunication1());
                        com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[fas[i].getCommunication1Type()]);

                        data[i][5] = com;
                    } else {
                        JLabel com = new JLabel();
                        data[i][5] = com;
                    }

                    if (fas[i].getCommunication2() != null && fas[i].getCommunication2().length() > 0) {
                        JLabel com = new JLabel();
                        com.setText(fas[i].getCommunication2());
                        com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[fas[i].getCommunication2Type()]);

                        data[i][6] = com;
                    } else {
                        JLabel com = new JLabel();
                        data[i][6] = com;
                    }
                }
            }

            setTable(data, Columns);

            if (selkund != null) {
                showAnsprechpartner(selkund);
                this.table_ansprechpartner.changeSelection(selrow, 0, false, false);
                enableAnsprechpartnerElements();
            } else if (fas != null) {
                table_ansprechpartner.changeSelection(0, 0, false, false);
                showAnsprechpartner(fas[0]);
                enableAnsprechpartnerElements();
            } else {
//                System.out.println("Keine Ansprechpartner - deaktiviere Controls");
                disableAnsprechpartnerElements();
            }

        } catch (SQLException e) {
            Log.databaselogger.fatal("Die Ansprechpartner konnten nicht aus der Datenbank geladen werden", e);
            ShowException.showException("Datenbankfehler: Die Ansprechpartner konnten nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Ansprechpartner nicht laden");
        }
    }

    private AbstractStandardModel atm = null;
    
    private void setTable(Object[][] data, String[] columns) {

        if(atm == null ){
            atm = new AbstractStandardModel(data, columns);
            table_ansprechpartner.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_ansprechpartner.getModel();
            atm.setData(data);
            table_ansprechpartner.packAll();
            return;
        }

        table_ansprechpartner.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());
        table_ansprechpartner.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_ansprechpartner.setColumnSelectionAllowed(false);
        table_ansprechpartner.setCellSelectionEnabled(false);
        table_ansprechpartner.setRowSelectionAllowed(true);
        table_ansprechpartner.setAutoCreateRowSorter(true);

        table_ansprechpartner.setFillsViewportHeight(true);
        table_ansprechpartner.removeColumn(table_ansprechpartner.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_ansprechpartner.addMouseListener(popupListener);
        table_ansprechpartner.setColumnControlVisible(true);

        JTableHeader header = table_ansprechpartner.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_ansprechpartner.packAll();
    }

    private void searchTable() {
        int result = table_ansprechpartner.getSearchable().search(field_search.getText());
    }

    private void saveAnsprechpartner() {
        if (currentAnsprech != null) {
            try {
                currentAnsprech.setNachname(this.field_nachname.getText());
                currentAnsprech.setVorname(this.field_vorname.getText());

                currentAnsprech.setAbteilung(this.combo_abteilung.getSelectedItem().toString());
                currentAnsprech.setAnrede(this.combo_anrede.getSelectedItem().toString());
                currentAnsprech.setFunktion(this.combo_funktion.getSelectedItem().toString());
                currentAnsprech.setPrioritaet(combo_prioritaet.getSelectedIndex());

                int selstat = combo_status.getSelectedIndex();

                if (selstat == -1 || selstat == 0) {
                    currentAnsprech.setStatus(Status.NORMAL);
                } else if (selstat == 1) {
                    currentAnsprech.setStatus(Status.ARCHIVED);
                } else if (selstat == 2) {
                    currentAnsprech.setStatus(Status.DELETED);
                }


                currentAnsprech.setTitle(this.combo_titel.getSelectedItem().toString());

                currentAnsprech.setCommunication1(this.field_communication1.getText());
                currentAnsprech.setCommunication2(this.field_communication2.getText());
                currentAnsprech.setCommunication3(this.field_communication3.getText());
                currentAnsprech.setCommunication4(this.field_communication4.getText());
                currentAnsprech.setCommunication5(this.field_communication5.getText());
                currentAnsprech.setCommunication1Type(this.combo_comtype1.getSelectedIndex());
                currentAnsprech.setCommunication2Type(this.combo_comtype2.getSelectedIndex());
                currentAnsprech.setCommunication3Type(this.combo_comtype3.getSelectedIndex());
                currentAnsprech.setCommunication4Type(this.combo_comtype4.getSelectedIndex());
                currentAnsprech.setCommunication5Type(this.combo_comtype5.getSelectedIndex());

                if (this.date_geburtsdatum.getDate() != null) {
                    currentAnsprech.setGeburtdatumDate(this.date_geburtsdatum.getDate());
                }

                FirmenAnsprechpartnerSQLMethods.updateFirmen_ansprechpartner(DatabaseConnection.open(), currentAnsprech);
            } catch (SQLException e) {
                Log.databaselogger.fatal("Konnte den Ansprechpartner nicht aktuallisieren.", e);
                ShowException.showException("Datenbankfehler: Der Ansprechpartner konnten nicht aktualisiert werden werden. ",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Ansprechpartner nicht aktualisieren");
            }
        }
        
        setUp(currentAnsprech.getId());
    }

    /**
     * 
     * @param fa 
     */
    private void showAnsprechpartner(FirmenAnsprechpartnerObj fa) {
        if (fa == null) {
            return;
        }
        
        if(fa == currentfa) {
            return;
        } else {
            currentfa = fa;
        }

//        saveAnsprechpartner();

        this.field_communication1.setText(fa.getCommunication1());
        this.field_communication2.setText(fa.getCommunication2());
        this.field_communication3.setText(fa.getCommunication3());
        this.field_communication4.setText(fa.getCommunication4());
        this.field_communication5.setText(fa.getCommunication5());

        this.field_nachname.setText(fa.getNachname());
        this.field_vorname.setText(fa.getVorname());

        this.combo_abteilung.setSelectedItem(fa.getAbteilung());
        this.combo_anrede.setSelectedItem(fa.getAnrede());
        this.combo_funktion.setSelectedItem(fa.getFunktion());
        this.combo_prioritaet.setSelectedIndex(fa.getPrioritaet());

        int status = 0;

        if (type == PRIVAT) {
            status = kunde.getStatus();
        } else if (type == FIRMA) {
            status = firma.getStatus();
        } else if (type == VERSICHERER) {
            status = versicherer.getStatus();
        }

        if (status == Status.NORMAL) {
            combo_status.setSelectedIndex(0);
        } else if (status == Status.ARCHIVED) {
            combo_status.setSelectedIndex(2);
        } else if (status == Status.DELETED) {
            combo_status.setSelectedIndex(5);
        } else if (status == Status.PRIVATE) {
            combo_status.setSelectedIndex(6);
        }

//        this.combo_status.setSelectedIndex(fa.getStatus());
        this.combo_titel.setSelectedItem(fa.getTitle());

        this.combo_comtype1.setSelectedIndex(fa.getCommunication1Type());
        this.combo_comtype2.setSelectedIndex(fa.getCommunication2Type());
        this.combo_comtype3.setSelectedIndex(fa.getCommunication3Type());
        this.combo_comtype4.setSelectedIndex(fa.getCommunication4Type());
        this.combo_comtype5.setSelectedIndex(fa.getCommunication5Type());

        try {
            if (fa.getGeburtdatum() != null) {
                this.date_geburtsdatum.setDate(fa.getGeburtdatumDate());
            }
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        currentAnsprech = fa;
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
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
                int row = table_ansprechpartner.rowAtPoint(point);
                table_ansprechpartner.changeSelection(row, 0, false, false);
                tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                
                if (row == -1) {
                    return;
                }

                FirmenAnsprechpartnerObj fa = (FirmenAnsprechpartnerObj) 
                        table_ansprechpartner.getModel().getValueAt(row, 0);

                if (fa == null) {
                    return;
                }

                showAnsprechpartner(fa);                
            }
        }
    }

    private void newAnsprech(){
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie den Nachnamen des neuen Ansprechpartners an.",
                "Nachname des Ansprechpartners", JOptionPane.INFORMATION_MESSAGE);

        if (name == null || name.length() < 1) {
            return;
        }

        FirmenAnsprechpartnerObj fa = new FirmenAnsprechpartnerObj();
        fa.setCreatorId(BasicRegistry.currentUser.getId());

        if (type == FIRMA) {
            fa.setKundenKennung(firma.getKundenNr());
        } else if (type == PRIVAT) {
            fa.setKundenKennung(kunde.getKundenNr());
        } else if (type == VERSICHERER) {
            fa.setVersichererId(versicherer.getId());
        }

        fa.setNachname(name);

        fa.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        fa.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        fa.setStatus(Status.NORMAL);

        try {
            int id = FirmenAnsprechpartnerSQLMethods.insertIntoFirmen_ansprechpartner(DatabaseConnection.open(), fa);
            fa.setId(id);
            setUp(id);
//            this.showAnsprechpartner(fa); doppelt
        } catch (SQLException e) {
            Log.databaselogger.fatal("Der neue Ansprechpartner  \"" + name + "\" konnte nicht gespeichert werden.", e);
            ShowException.showException("Datenbankfehler: Der neue Ansprechpartner \"" + name + "\" konnte nicht gespeichert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Ansprechpartner nicht speichern");
        }
    }
    
    private void deleteAnsprech(){
        int row = table_ansprechpartner.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Ansprechpartner ausgewählt.");
            return;
        }

        FirmenAnsprechpartnerObj fa = (FirmenAnsprechpartnerObj) this.table_ansprechpartner.getModel().getValueAt(row, 0);

        if (fa == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Ansprechpartner wirklich löschen?",
                    "Bestätigung löschen", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            FirmenAnsprechpartnerSQLMethods.deleteFromfirmen_ansprechpartner(DatabaseConnection.open(), fa);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Ansprechpartner nicht aus der Datenbank löschen", e);
            ShowException.showException("Datenbankfehler: Der Ansprechpartner konnten nicht gelöscht werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Ansprechpartner nicht löschen");
        }

        setUp(-1);
    }
    
    private void archiveAnsprech(){
        int row = table_ansprechpartner.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Kein Ansprechpartner ausgewählt.");
            return;
        }

        FirmenAnsprechpartnerObj fa = (FirmenAnsprechpartnerObj) this.table_ansprechpartner.getModel().getValueAt(row, 0);

        if (fa == null) {
            return;
        }

        if (Config.getConfigBoolean("deletearchiveConfirm", true)) {
            int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie den Ansprechpartner wirklich archivieren?",
                    "Bestätigung archivieren", JOptionPane.YES_NO_OPTION);

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            FirmenAnsprechpartnerSQLMethods.archiveFromfirmen_ansprechpartner(DatabaseConnection.open(), fa);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte den Ansprechpartner nicht aus der Datenbank archiveren", e);
            ShowException.showException("Datenbankfehler: Der Ansprechpartner konnten nicht archiviert werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Ansprechpartner nicht archivieren");
        }

        setUp(-1);
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
        archiveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel6 = new javax.swing.JLabel();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_ansprechpartner = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        combo_titel = new javax.swing.JComboBox();
        combo_anrede = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        field_vorname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        field_nachname = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        combo_abteilung = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();
        combo_funktion = new javax.swing.JComboBox();
        date_geburtsdatum = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        combo_comtype1 = new javax.swing.JComboBox();
        combo_comtype2 = new javax.swing.JComboBox();
        combo_comtype3 = new javax.swing.JComboBox();
        combo_comtype4 = new javax.swing.JComboBox();
        combo_comtype5 = new javax.swing.JComboBox();
        field_communication1 = new javax.swing.JTextField();
        field_communication2 = new javax.swing.JTextField();
        field_communication3 = new javax.swing.JTextField();
        field_communication4 = new javax.swing.JTextField();
        field_communication5 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        combo_prioritaet = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        combo_status = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelAnsprechpartner.class);
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

        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(newMenuItem);

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

        refreshMenuItem.setText(resourceMap.getString("refreshMenuItem.text")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        tablePopupMenu.add(refreshMenuItem);

        setName("Form"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

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
        jToolBar1.add(btnNeu);

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSave.setFocusable(false);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

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
        jToolBar1.add(btnArchive);

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
        jToolBar1.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar1.add(jSeparator2);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(resourceMap.getIcon("jLabel6.icon")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar1.add(jLabel6);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
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

        jSeparator5.setName("jSeparator5"); // NOI18N
        jToolBar1.add(jSeparator5);

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
        jToolBar1.add(btnRefresh);

        jSplitPane1.setDividerLocation(120);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(400, 120));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_ansprechpartner.setModel(new javax.swing.table.DefaultTableModel(
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
        table_ansprechpartner.setColumnControlVisible(true);
        table_ansprechpartner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_ansprechpartner.setName("table_ansprechpartner"); // NOI18N
        table_ansprechpartner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_ansprechpartnerMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_ansprechpartner);

        jSplitPane1.setTopComponent(scroll_protokolle);

        jPanel1.setMaximumSize(new java.awt.Dimension(2000, 160));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 160));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(964, 160));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel44.setText(resourceMap.getString("jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        combo_titel.setEditable(true);
        combo_titel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_titel.setName("combo_titel"); // NOI18N

        combo_anrede.setEditable(true);
        combo_anrede.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_anrede.setName("combo_anrede"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        field_vorname.setName("field_vorname"); // NOI18N
        field_vorname.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        field_nachname.setName("field_nachname"); // NOI18N
        field_nachname.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        combo_abteilung.setEditable(true);
        combo_abteilung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_abteilung.setName("combo_abteilung"); // NOI18N

        jLabel46.setText(resourceMap.getString("jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        combo_funktion.setEditable(true);
        combo_funktion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_funktion.setName("combo_funktion"); // NOI18N

        date_geburtsdatum.setName("date_geburtsdatum"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        combo_comtype1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype1.setName("combo_comtype1"); // NOI18N

        combo_comtype2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype2.setName("combo_comtype2"); // NOI18N

        combo_comtype3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype3.setName("combo_comtype3"); // NOI18N

        combo_comtype4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype4.setName("combo_comtype4"); // NOI18N

        combo_comtype5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_comtype5.setName("combo_comtype5"); // NOI18N

        field_communication1.setName("field_communication1"); // NOI18N

        field_communication2.setName("field_communication2"); // NOI18N

        field_communication3.setName("field_communication3"); // NOI18N

        field_communication4.setName("field_communication4"); // NOI18N

        field_communication5.setName("field_communication5"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        combo_prioritaet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        combo_prioritaet.setName("combo_prioritaet"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        combo_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Archiviert", "Gelöscht" }));
        combo_status.setName("combo_status"); // NOI18N

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setName("jSeparator1"); // NOI18N

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setName("jSeparator4"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(field_nachname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_vorname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_anrede, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_status, 0, 156, Short.MAX_VALUE)
                            .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_abteilung, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_titel, 0, 162, Short.MAX_VALUE)
                            .addComponent(combo_funktion, 0, 162, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_prioritaet, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(field_communication3, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(field_communication4, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(field_communication5, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(field_communication2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(field_communication1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addGap(79, 79, 79))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_communication1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_comtype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_communication2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_comtype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_communication3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_comtype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_communication4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_comtype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_communication5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_comtype5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel44)
                                .addComponent(combo_titel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel45)
                                .addComponent(combo_abteilung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel46)
                                .addComponent(combo_funktion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combo_prioritaet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(combo_anrede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(field_vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(field_nachname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(date_geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(190, Short.MAX_VALUE))
        );

        NewKundeHelper.loadTitel(this);
        NewKundeHelper.loadAnreden(this);

        jSplitPane1.setBottomComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(482, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        newAnsprech();
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteAnsprech();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void table_ansprechpartnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_ansprechpartnerMouseClicked
        int row = table_ansprechpartner.getSelectedRow();

        if (row == -1) {
            return;
        }

        FirmenAnsprechpartnerObj fa = (FirmenAnsprechpartnerObj) this.table_ansprechpartner.getModel().getValueAt(row, 0);

        if (fa == null) {
            return;
        }

        showAnsprechpartner(fa);
    }//GEN-LAST:event_table_ansprechpartnerMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        setUp(-1);
}//GEN-LAST:event_btnRefreshActionPerformed

    private void alleDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alleDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_alleDBMenuItemActionPerformed

    private void aktiveDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktiveDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_aktiveDBMenuItemActionPerformed

    private void archivedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivedDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_archivedDBMenuItemActionPerformed

    private void deletedDBMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedDBMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_deletedDBMenuItemActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveAnsprech();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveAnsprechpartner();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newAnsprech();
}//GEN-LAST:event_newMenuItemActionPerformed

    private void archiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMenuItemActionPerformed
        archiveAnsprech();
}//GEN-LAST:event_archiveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteAnsprech();
}//GEN-LAST:event_deleteMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        setUp(-1);
}//GEN-LAST:event_refreshMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    private javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    private javax.swing.JMenuItem archiveMenuItem;
    private javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    public javax.swing.JComboBox combo_abteilung;
    public javax.swing.JComboBox combo_anrede;
    private javax.swing.JComboBox combo_comtype1;
    private javax.swing.JComboBox combo_comtype2;
    private javax.swing.JComboBox combo_comtype3;
    private javax.swing.JComboBox combo_comtype4;
    private javax.swing.JComboBox combo_comtype5;
    public javax.swing.JComboBox combo_funktion;
    private javax.swing.JComboBox combo_prioritaet;
    private javax.swing.JComboBox combo_status;
    public javax.swing.JComboBox combo_titel;
    private com.toedter.calendar.JDateChooser date_geburtsdatum;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    private javax.swing.JTextField field_communication1;
    private javax.swing.JTextField field_communication2;
    private javax.swing.JTextField field_communication3;
    private javax.swing.JTextField field_communication4;
    private javax.swing.JTextField field_communication5;
    private javax.swing.JTextField field_nachname;
    private javax.swing.JTextField field_search;
    private javax.swing.JTextField field_vorname;
    private javax.swing.ButtonGroup grp_dbstatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPopupMenu popupDBStatus;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JScrollPane scroll_protokolle;
    private javax.swing.JPopupMenu tablePopupMenu;
    private org.jdesktop.swingx.JXTable table_ansprechpartner;
    // End of variables declaration//GEN-END:variables
    private JButton dropDownButton;
}
