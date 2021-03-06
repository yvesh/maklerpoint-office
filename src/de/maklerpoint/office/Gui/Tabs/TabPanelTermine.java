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
 * TabPanelTermine.java
 *
 * Created on Jul 17, 2010, 12:13:28 PM
 */
package de.maklerpoint.office.Gui.Tabs;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Gui.Kalender.NeueAufgabe;
import de.maklerpoint.office.Gui.Kalender.NeuerTermin;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.TermineModel;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import de.maklerpoint.office.Wiedervorlage.WiedervorlagenObj;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TabPanelTermine extends javax.swing.JPanel implements iTabs {

    public static final int KUNDE = 0;
    public static final int GESCH = 1;
    public static final int VERSICHERER = 2;
    public static final int BENUTZER = 3;
    private int type = -1;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private VersichererObj versicherer = null;
    private BenutzerObj benutzer = null;
    private String[] kundenTerminColumn = new String[]{"Hidden", "Beschreibung", "Ort", "Start", "Ende", "Benutzer"};
    private String[] firmenTerminColumn = new String[]{"Hidden", "Beschreibung", "Ort", "Start", "Ende", "Benutzer"};
    private String[] benutzerTerminColumn = new String[]{"Hidden", "Kunde", "Beschreibung", "Ort", "Start", "Ende"};
    private String[] versTerminColumn = new String[]{"Hidden", "Beschreibung", "Start", "Ort", "Ende", "Benutzer"};
    private String[] kundenAufgabeColumn = new String[]{"Hidden", "Beschreibung", "Start", "Ende", "Benutzer"};
    private String[] firmenAufgabeColumn = new String[]{"Hidden", "Beschreibung", "Start", "Ende", "Benutzer"};
    private String[] benutzerAufgabeColumn = new String[]{"Hidden", "Kunde", "Beschreibung", "Start", "Ende"};
    private String[] versAufgabeColumn = new String[]{"Hidden", "Beschreibung", "Start", "Ende", "Benutzer"};
    private String[] wiedervorlagenColumn = new String[]{"Hidden", "Beschreibung", "Datum", "Benutzer"};
    private boolean showall = false;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private boolean enabled = true;

    /** Creates new form TabPanelTermine */
    public TabPanelTermine() {
        initComponents();
    }

    public String getTabName() {
        return "Termine / Aufgaben / Wiedervorlagen";
    }

    public void load(KundenObj kunde) {
        this.type = KUNDE;
        this.kunde = kunde;
        Log.logger.debug("Lade Termine für Kunden " + kunde.getKundenNr());
        loadTable();
    }

    public void load(FirmenObj firma) {
        this.type = GESCH;
        this.firma = firma;
        Log.logger.debug("Lade Termine für Kunden " + firma.getKundenNr());
        loadTable();
    }

    public void load(VersichererObj versicherer) {
        this.type = VERSICHERER;
        this.versicherer = versicherer;
        Log.logger.debug("Lade Termine für Versicherung " + versicherer.getName());
        loadTable();
    }

    public void load(BenutzerObj benutzer) {
        this.type = BENUTZER;
        this.benutzer = benutzer;
        Log.logger.debug("Lade Termine für Benutzer " + benutzer);
        loadTable();
    }

    public void load(StoerfallObj stoerfall) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(SchadenObj schaden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(ProduktObj produkt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(VertragObj vertrag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disableElements() {
        enabled = false;

        setTable(null, kundenTerminColumn);

        this.btnNeueAufgabe.setEnabled(false);
        this.btnDelete.setEnabled(false);
        this.btnNeuerTermin.setEnabled(false);
        this.radio_alletermine.setEnabled(false);
        this.radio_aufgaben.setEnabled(false);
        this.radio_nachbearbeitung.setEnabled(false);
        this.radio_ticket.setEnabled(false);
        this.radio_wiedervorlagen.setEnabled(false);
        this.check_finished.setEnabled(false);

        this.table_termine.setEnabled(false);

    }

    public void enableElements() {
        if (enabled == true) {
            return;
        }

        enabled = true;

        this.btnNeueAufgabe.setEnabled(true);
        this.btnDelete.setEnabled(true);
        this.btnNeuerTermin.setEnabled(true);
        this.radio_alletermine.setEnabled(true);
        this.radio_aufgaben.setEnabled(true);
        this.radio_nachbearbeitung.setEnabled(true);
        this.radio_ticket.setEnabled(true);
        this.radio_wiedervorlagen.setEnabled(true);
        this.check_finished.setEnabled(true);

        this.table_termine.setEnabled(true);
    }

    private int getStatus() {
        return Status.NORMAL; // TODO
    }

    private void loadTable() {

//        System.out.println("Type: " + type);
        if (this.radio_alletermine.isSelected()) {
            if (type == KUNDE) {
                if (this.check_finished.isSelected()) {
                    TerminObj[] termine = KalenderRegistry.getTermine(kunde.getKundenNr());
                    createTableTermine(termine, kundenTerminColumn);
                } else {
                    TerminObj[] termine = getNewerTermine(KalenderRegistry.getTermine(kunde.getKundenNr()));
                    createTableTermine(termine, kundenTerminColumn);
                }
            } else if (type == GESCH) {
                if (this.check_finished.isSelected()) {
                    TerminObj[] termine = KalenderRegistry.getTermine(firma.getKundenNr());
                    createTableTermine(termine, firmenTerminColumn);
                } else {
                    TerminObj[] termine = getNewerTermine(KalenderRegistry.getTermine(firma.getKundenNr()));
                    createTableTermine(termine, firmenTerminColumn);
                }
            } else if (type == BENUTZER) {
                if (this.check_finished.isSelected()) {
                    TerminObj[] termine = KalenderRegistry.getTermine(true, benutzer.getId());
                    createTableTermine(termine, benutzerTerminColumn);
                } else {
                    TerminObj[] termine = getNewerTermine(KalenderRegistry.getTermine(true, benutzer.getId()));
                    createTableTermine(termine, benutzerTerminColumn);
                }
            } else if (type == VERSICHERER) {
                if (this.check_finished.isSelected()) {
                    TerminObj[] termine = KalenderRegistry.getVersichererTermine(versicherer.getId());
                    createTableTermine(termine, versTerminColumn); // TODo
                } else {
                    TerminObj[] termine = getNewerTermine(KalenderRegistry.getVersichererTermine(versicherer.getId()));
                    createTableTermine(termine, versTerminColumn);
                }
            }

            // END TERMINE
        } else if (this.radio_aufgaben.isSelected()) {

            if (type == KUNDE) {
                if (this.check_finished.isSelected()) {
                    AufgabenObj[] aufgaben = KalenderRegistry.getKundenAufgaben(kunde.getKundenNr(), getStatus());
                    createTableAufgaben(aufgaben, kundenAufgabeColumn);
                } else {
                    AufgabenObj[] aufgaben = getNewerAufgaben(KalenderRegistry.getKundenAufgaben(kunde.getKundenNr(), getStatus()));
                    createTableAufgaben(aufgaben, kundenAufgabeColumn);
                }
            } else if (type == GESCH) {
                if (this.check_finished.isSelected()) {
                    AufgabenObj[] aufgaben = KalenderRegistry.getKundenAufgaben(firma.getKundenNr(), getStatus());
                    createTableAufgaben(aufgaben, firmenAufgabeColumn);
                } else {
                    AufgabenObj[] aufgaben = getNewerAufgaben(KalenderRegistry.getKundenAufgaben(firma.getKundenNr(), getStatus()));
                    createTableAufgaben(aufgaben, firmenAufgabeColumn);
                }
            } else if (type == BENUTZER) {
                if (this.check_finished.isSelected()) {
                    AufgabenObj[] aufgaben = KalenderRegistry.getBenutzerAufgaben(benutzer.getId(), getStatus());
                    createTableAufgaben(aufgaben, benutzerAufgabeColumn);
                } else {
                    AufgabenObj[] aufgaben = getNewerAufgaben(KalenderRegistry.getBenutzerAufgaben(benutzer.getId(), getStatus()));
                    createTableAufgaben(aufgaben, benutzerAufgabeColumn);
                }
            } else if (type == VERSICHERER) {
                if (this.check_finished.isSelected()) {
                    AufgabenObj[] aufgaben = KalenderRegistry.getVersichererAufgaben(versicherer.getId(), getStatus());
                    createTableAufgaben(aufgaben, versAufgabeColumn);
                } else {
                    AufgabenObj[] aufgaben = getNewerAufgaben(KalenderRegistry.getVersichererAufgaben(versicherer.getId(), getStatus()));
                    createTableAufgaben(aufgaben, versAufgabeColumn);
                }
            }
            // END AUFGABEN
        } else if (this.radio_wiedervorlagen.isSelected()) {

            if (type == KUNDE) {
                if (this.check_finished.isSelected()) {
                    WiedervorlagenObj[] wv = KalenderRegistry.getWiedervorlagen(kunde.getKundenNr());
                    createTableWiederVorlagen(wv, wiedervorlagenColumn);
                } else {
                    WiedervorlagenObj[] wv = getNewerWiedervorlagen(KalenderRegistry.getWiedervorlagen(kunde.getKundenNr()));
                    createTableWiederVorlagen(wv, wiedervorlagenColumn);
                }
            } else if (type == GESCH) {
                if (this.check_finished.isSelected()) {
                    WiedervorlagenObj[] wv = KalenderRegistry.getWiedervorlagen(firma.getKundenNr());
                    createTableWiederVorlagen(wv, wiedervorlagenColumn);
                } else {
                    WiedervorlagenObj[] wv = getNewerWiedervorlagen(KalenderRegistry.getWiedervorlagen(firma.getKundenNr()));
                    createTableWiederVorlagen(wv, wiedervorlagenColumn);
                }
            } else if (type == BENUTZER) {
//                if(this.check_finished.isSelected()) {
//                    AufgabenObj[] aufgaben = KalenderRegistry.getAufgaben(true, benutzer.getId());
//                    createTableWiederVorlagen(aufgaben, wiedervorlagenColumn);
//                } else {
//
//                }
            }
            // END WIEDERVORLAGEN
        }
    }

    private TerminObj[] getNewerTermine(TerminObj[] termine) {
        if (termine == null) {
            return null;
        }

        ArrayList<TerminObj> as = new ArrayList<TerminObj>();

        for (int i = 0; i < termine.length; i++) {
            if (termine[i].getStart().getTime() > System.currentTimeMillis()) {
                as.add(termine[i]);
            }
        }

        TerminObj[] trm = new TerminObj[as.size()];

        System.arraycopy(as.toArray(), 0, trm, 0, as.size());

        return trm;
    }

    private AufgabenObj[] getNewerAufgaben(AufgabenObj[] aufgaben) {
        if (aufgaben == null) {
            return null;
        }

        ArrayList<AufgabenObj> as = new ArrayList<AufgabenObj>();

        for (int i = 0; i < aufgaben.length; i++) {
            if (aufgaben[i].getStart().getTime() > System.currentTimeMillis()) {
                as.add(aufgaben[i]);
            }
        }

        AufgabenObj[] trm = new AufgabenObj[as.size()];

        System.arraycopy(as.toArray(), 0, trm, 0, as.size());

        return trm;
    }

    private WiedervorlagenObj[] getNewerWiedervorlagen(WiedervorlagenObj[] wv) {
        if (wv == null) {
            return null;
        }

        ArrayList as = new ArrayList();

        for (int i = 0; i < wv.length; i++) {
            if (wv[i].getDate().getTime() > System.currentTimeMillis()) {
                as.add(wv[i]);
            }
        }

        WiedervorlagenObj[] trm = new WiedervorlagenObj[as.size()];

        System.arraycopy(as.toArray(), 0, trm, 0, as.size());

        return trm;
    }

    private void createTableWiederVorlagen(WiedervorlagenObj[] wv, String[] columns) {
        Object[][] data = null;

        if (wv != null) {
            data = new Object[wv.length][5];

            for (int i = 0; i < wv.length; i++) {
                data[i][0] = wv[i];
                data[i][1] = wv[i].getBeschreibung();
                data[i][2] = df.format(wv[i].getDate());
                data[i][3] = BenutzerRegistry.getBenutzer(wv[i].getBenutzerId());
            }
        }

        setTable(data, columns);
    }

    private void createTableTermine(TerminObj[] termine, String[] columns) {
        Object[][] data = null;

        if (termine != null) {
            data = new Object[termine.length][6];

            for (int i = 0; i < termine.length; i++) {
                data[i][0] = termine[i];

                if (type == KUNDE || type == GESCH || type == VERSICHERER) {
                    data[i][1] = termine[i].getBeschreibung();
                    data[i][2] = termine[i].getOrt();
                    data[i][3] = df.format(termine[i].getStart());
                    data[i][4] = df.format(termine[i].getEnde());
                    data[i][5] = BenutzerRegistry.getBenutzer(termine[i].getBesitzer());
                } else if (type == BENUTZER) {
                    data[i][1] = KundenRegistry.getKunde(termine[i].getKundeKennung());
                    data[i][2] = termine[i].getBeschreibung();
                    data[i][3] = termine[i].getOrt();
                    data[i][4] = df.format(termine[i].getStart());
                    data[i][5] = df.format(termine[i].getEnde());
                }
            }
        }

        setTable(data, columns);
    }

    private void createTableAufgaben(AufgabenObj[] aufgaben, String[] columns) {
        Object[][] data = null;

        if (aufgaben != null) {
            data = new Object[aufgaben.length][5];

            for (int i = 0; i < aufgaben.length; i++) {
                data[i][0] = aufgaben[i];
                if (type == KUNDE || type == GESCH || type == VERSICHERER) {
                    data[i][1] = aufgaben[i].getBeschreibung();
                    data[i][2] = df.format(aufgaben[i].getStart());
                    data[i][3] = df.format(aufgaben[i].getEnde());
                    data[i][4] = BenutzerRegistry.getBenutzer(aufgaben[i].getBenutzerId());
                } else if (type == BENUTZER) {
                    data[i][1] = KundenRegistry.getKunde(aufgaben[i].getKundenKennung());
                    data[i][2] = aufgaben[i].getBeschreibung();
                    data[i][3] = df.format(aufgaben[i].getStart());
                    data[i][4] = df.format(aufgaben[i].getEnde());
                }
            }
        }

        setTable(data, columns);
    }
    private AbstractStandardModel atm = null;

    private void setTable(Object[][] data, String[] columns) {

        if (atm == null) {
            atm = new AbstractStandardModel(data, columns);
            table_termine.setModel(atm);
        } else {
            atm = (AbstractStandardModel) table_termine.getModel();
            atm.setColumnNames(columns); // TODO test ob das hier funktioniert
            atm.setData(data);
            table_termine.packAll();
            return;
        }

        table_termine.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_termine.setColumnSelectionAllowed(false);
        table_termine.setCellSelectionEnabled(false);
        table_termine.setRowSelectionAllowed(true);
        table_termine.setAutoCreateRowSorter(true);

        table_termine.setFillsViewportHeight(true);
        table_termine.removeColumn(table_termine.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_termine.addMouseListener(popupListener);
        table_termine.setColumnControlVisible(true);
        table_termine.packAll();

        JTableHeader header = table_termine.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

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
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void editIT() {
        int row = table_termine.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Termin oder eine Aufgabe.");
            return;
        }

        Object obj = this.table_termine.getModel().getValueAt(row, 0);

        if (obj.getClass().equals(TerminObj.class)) {
            TerminObj termin = (TerminObj) obj;

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            neuTerminBox = new NeuerTermin(mainFrame, false, termin);
            neuTerminBox.addWindowListener(new WindowAdapter() {

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

            neuTerminBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(neuTerminBox);

        } else if (obj.getClass().equals(AufgabenObj.class)) {
            AufgabenObj aufg = (AufgabenObj) obj;

            JFrame mainFrame = CRM.getApplication().getMainFrame();
            neuAufgabeBox = new NeueAufgabe(mainFrame, false, aufg);
            neuAufgabeBox.setLocationRelativeTo(mainFrame);
            neuAufgabeBox.addWindowListener(new WindowAdapter() {

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
            CRM.getApplication().show(neuAufgabeBox);

        } else if (obj.getClass().equals(WiedervorlagenObj.class)) {
            WiedervorlagenObj wied = (WiedervorlagenObj) obj;

            // TODO
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

        grpTermine = new javax.swing.ButtonGroup();
        panel_schaedenwahl = new javax.swing.JPanel();
        radio_alletermine = new javax.swing.JRadioButton();
        radio_wiedervorlagen = new javax.swing.JRadioButton();
        radio_aufgaben = new javax.swing.JRadioButton();
        radio_ticket = new javax.swing.JRadioButton();
        radio_nachbearbeitung = new javax.swing.JRadioButton();
        scroll_tabtermine = new javax.swing.JScrollPane();
        table_termine = new org.jdesktop.swingx.JXTable();
        btnNeuerTermin = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNeueAufgabe = new javax.swing.JButton();
        check_finished = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        panel_schaedenwahl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_schaedenwahl.setName("panel_schaedenwahl"); // NOI18N

        grpTermine.add(radio_alletermine);
        radio_alletermine.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelTermine.class);
        radio_alletermine.setText(resourceMap.getString("radio_alletermine.text")); // NOI18N
        radio_alletermine.setName("radio_alletermine"); // NOI18N
        radio_alletermine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_alletermineActionPerformed(evt);
            }
        });

        grpTermine.add(radio_wiedervorlagen);
        radio_wiedervorlagen.setText(resourceMap.getString("radio_wiedervorlagen.text")); // NOI18N
        radio_wiedervorlagen.setName("radio_wiedervorlagen"); // NOI18N
        radio_wiedervorlagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_wiedervorlagenActionPerformed(evt);
            }
        });

        grpTermine.add(radio_aufgaben);
        radio_aufgaben.setText(resourceMap.getString("radio_aufgaben.text")); // NOI18N
        radio_aufgaben.setName("radio_aufgaben"); // NOI18N
        radio_aufgaben.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_aufgabenActionPerformed(evt);
            }
        });

        grpTermine.add(radio_ticket);
        radio_ticket.setText(resourceMap.getString("radio_ticket.text")); // NOI18N
        radio_ticket.setName("radio_ticket"); // NOI18N
        radio_ticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_ticketActionPerformed(evt);
            }
        });

        grpTermine.add(radio_nachbearbeitung);
        radio_nachbearbeitung.setText(resourceMap.getString("radio_nachbearbeitung.text")); // NOI18N
        radio_nachbearbeitung.setName("radio_nachbearbeitung"); // NOI18N
        radio_nachbearbeitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_nachbearbeitungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_schaedenwahlLayout = new javax.swing.GroupLayout(panel_schaedenwahl);
        panel_schaedenwahl.setLayout(panel_schaedenwahlLayout);
        panel_schaedenwahlLayout.setHorizontalGroup(
            panel_schaedenwahlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_schaedenwahlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radio_alletermine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_aufgaben)
                .addGap(18, 18, 18)
                .addComponent(radio_wiedervorlagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_nachbearbeitung)
                .addGap(18, 18, 18)
                .addComponent(radio_ticket)
                .addContainerGap(195, Short.MAX_VALUE))
        );
        panel_schaedenwahlLayout.setVerticalGroup(
            panel_schaedenwahlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_schaedenwahlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radio_alletermine)
                .addComponent(radio_aufgaben)
                .addComponent(radio_wiedervorlagen)
                .addComponent(radio_nachbearbeitung)
                .addComponent(radio_ticket))
        );

        scroll_tabtermine.setName("scroll_tabtermine"); // NOI18N

        table_termine.setModel(new javax.swing.table.DefaultTableModel(
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
        table_termine.setName("table_termine"); // NOI18N
        table_termine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_termineMouseClicked(evt);
            }
        });
        scroll_tabtermine.setViewportView(table_termine);

        btnNeuerTermin.setIcon(resourceMap.getIcon("btnNeuerTermin.icon")); // NOI18N
        btnNeuerTermin.setText(resourceMap.getString("btnNeuerTermin.text")); // NOI18N
        btnNeuerTermin.setName("btnNeuerTermin"); // NOI18N
        btnNeuerTermin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuerTerminActionPerformed(evt);
            }
        });

        btnDelete.setIcon(resourceMap.getIcon("btnDelete.icon")); // NOI18N
        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setName("btnDelete"); // NOI18N

        btnNeueAufgabe.setIcon(resourceMap.getIcon("btnNeueAufgabe.icon")); // NOI18N
        btnNeueAufgabe.setText(resourceMap.getString("btnNeueAufgabe.text")); // NOI18N
        btnNeueAufgabe.setName("btnNeueAufgabe"); // NOI18N
        btnNeueAufgabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeueAufgabeActionPerformed(evt);
            }
        });

        check_finished.setText(resourceMap.getString("check_finished.text")); // NOI18N
        check_finished.setName("check_finished"); // NOI18N
        check_finished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_finishedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_schaedenwahl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNeuerTermin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNeueAufgabe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_finished)
                .addContainerGap(177, Short.MAX_VALUE))
            .addComponent(scroll_tabtermine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_schaedenwahl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_tabtermine, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNeuerTermin)
                    .addComponent(btnNeueAufgabe)
                    .addComponent(btnDelete)
                    .addComponent(check_finished))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void check_finishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_finishedActionPerformed
        loadTable();
    }//GEN-LAST:event_check_finishedActionPerformed

    private void radio_alletermineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_alletermineActionPerformed
        loadTable();
    }//GEN-LAST:event_radio_alletermineActionPerformed

    private void radio_aufgabenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_aufgabenActionPerformed
        loadTable();
    }//GEN-LAST:event_radio_aufgabenActionPerformed

    private void radio_wiedervorlagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_wiedervorlagenActionPerformed
        loadTable();
    }//GEN-LAST:event_radio_wiedervorlagenActionPerformed

    private void radio_nachbearbeitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_nachbearbeitungActionPerformed
        loadTable();
    }//GEN-LAST:event_radio_nachbearbeitungActionPerformed

    private void radio_ticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_ticketActionPerformed
        loadTable();
    }//GEN-LAST:event_radio_ticketActionPerformed

    private void btnNeuerTerminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuerTerminActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neuTerminBox = new NeuerTermin(mainFrame, false);
        neuTerminBox.addWindowListener(new WindowAdapter() {

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

        neuTerminBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(neuTerminBox);
    }//GEN-LAST:event_btnNeuerTerminActionPerformed

    private void btnNeueAufgabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeueAufgabeActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neuAufgabeBox = new NeueAufgabe(mainFrame, false);
        neuAufgabeBox.setLocationRelativeTo(mainFrame);
        neuAufgabeBox.addWindowListener(new WindowAdapter() {

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
        CRM.getApplication().show(neuAufgabeBox);
    }//GEN-LAST:event_btnNeueAufgabeActionPerformed

    private void table_termineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_termineMouseClicked
        if (evt.getClickCount() > 1) {
            int row = table_termine.getSelectedRow();

            if (row == -1) {
                return;
            }

            editIT();
        }
    }//GEN-LAST:event_table_termineMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNeueAufgabe;
    private javax.swing.JButton btnNeuerTermin;
    private javax.swing.JCheckBox check_finished;
    private javax.swing.ButtonGroup grpTermine;
    private javax.swing.JPanel panel_schaedenwahl;
    private javax.swing.JRadioButton radio_alletermine;
    private javax.swing.JRadioButton radio_aufgaben;
    private javax.swing.JRadioButton radio_nachbearbeitung;
    private javax.swing.JRadioButton radio_ticket;
    private javax.swing.JRadioButton radio_wiedervorlagen;
    private javax.swing.JScrollPane scroll_tabtermine;
    private org.jdesktop.swingx.JXTable table_termine;
    // End of variables declaration//GEN-END:variables
    private JDialog neuTerminBox;
    private JDialog neuAufgabeBox;
}