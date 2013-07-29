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
 * NewsletterPanel.java
 *
 * Created on 23.08.2010, 15:05:16
 */
package de.maklerpoint.office.Gui.Marketing;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Kunden.Tools.FirmenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Kunden.Tools.KundenSQLMethods;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Marketing.NewsletterObj;
import de.maklerpoint.office.Marketing.NewsletterSubscriberObj;
import de.maklerpoint.office.Marketing.Tools.NewsletterSQLMethods;
import de.maklerpoint.office.Marketing.Tools.NewsletterSubscriberSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.MarketingRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.NewsletterModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author yves
 */
public class NewsletterPanel extends javax.swing.JPanel {

    private String[] subscriberColumnNames = new String[]{"Hidden", "Kunden-Nr.", "Name", "E-mail", 
        "Hinzugefügt am", "Zuletzt bearbeitet am"};
    private Object[][] subscriberData = null;
    private int subCount = 0;
    private NewsletterObj currentNl = null;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /** Creates new form NewsletterPanel */
    public NewsletterPanel() {
        initComponents();
        setUp();
    }

    private void setUp() {
        loadNewsletter(-1);
        setupSubscribers();
    }

    /**
     * 
     * @param id
     */
    private void loadNewsletter(int id) {
        NewsletterObj[] nl = MarketingRegistry.getNewsletter(true);

        if (nl == null) {
            this.combo_newslettersend.setSelectedItem("Keine Newsletter vorhanden");
            this.combo_newsletter.setSelectedItem("Keine Newsletter vorhanden");
            loadDefaults();
            return;
        }

        this.combo_newsletter.setModel(new DefaultComboBoxModel(nl));
        this.combo_newslettersend.setModel(new DefaultComboBoxModel(nl));

        if (id == -1) {
            shownewsletter(nl[0]);
        } else {
            shownewsletter(MarketingRegistry.getNewsletter(id, false));
        }
    }

    private void loadDefaults() {
        this.combo_absender.setSelectedItem(BasicRegistry.currentUser.getVorname()
                + " " + BasicRegistry.currentUser.getNachname());
        if (BasicRegistry.currentUser.getEmail() != null) {
            this.combo_absenderemail.setSelectedItem(BasicRegistry.currentUser.getEmail());
        }
    }

    /**
     * 
     * @param nl
     */
    private void shownewsletter(NewsletterObj nl) {
        this.combo_betreff.setSelectedItem(nl.getSubject());
        this.combo_absender.setSelectedItem(nl.getSender());
        this.combo_absenderemail.setSelectedItem(nl.getSenderMail());
        this.editor_newsletter.setText(nl.getText());
        this.editor_newsletterpreview.setText(nl.getText());
        this.combo_newsletter.setSelectedItem(nl);
        this.combo_newslettersend.setSelectedItem(nl);

        currentNl = nl;
    }

    public void searchTable() {
        int result = table_subscriber.getSearchable().search(field_search.getText());

        if (result == -1) {
            return;
        }

    }

    private int getStatus() {
        return Status.NORMAL;
    }

    /**
     * 
     */
    private void setupSubscribers() {
        NewsletterSubscriberObj[] sub = MarketingRegistry.getNewsletterSubscriber(getStatus());

        if (sub != null) {
            subscriberData = new Object[sub.length][6];

            for (int i = 0; i < sub.length; i++) {
                subscriberData[i][0] = sub[i];
                subscriberData[i][1] = sub[i].getKennung();
                subscriberData[i][2] = sub[i].getName();
                subscriberData[i][3] = sub[i].getEmail();
                subscriberData[i][4] = df.format(sub[i].getCreated());
                subscriberData[i][5] = df.format(sub[i].getModified());
            }

            subCount = sub.length;
        } else {
            subscriberData = null;
            subCount = 0;
        }

        NewsletterModel nm = new NewsletterModel(subscriberData, subscriberColumnNames);
        this.table_subscriber.setModel(nm);

        table_subscriber.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_subscriber.setColumnSelectionAllowed(false);
        table_subscriber.setCellSelectionEnabled(false);
        table_subscriber.setRowSelectionAllowed(true);
        table_subscriber.setAutoCreateRowSorter(true);

        table_subscriber.setFillsViewportHeight(true);
        table_subscriber.removeColumn(table_subscriber.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_subscriber.addMouseListener(popupListener);
        table_subscriber.setColumnControlVisible(true);

        JTableHeader header = table_subscriber.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_subscriber.packAll();

        table_subscriber.tableChanged(new TableModelEvent(table_subscriber.getModel()));
        table_subscriber.revalidate();        
    }

    /**
     * 
     */
    private void newSubscriber() {
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        NewsletterSubscriberDialog subd = new NewsletterSubscriberDialog(mainFrame, true);
        subd.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(subd);

        NewsletterSubscriberObj nsub = subd.getReturnStatus();

        if (nsub == null) {
            return;
        }

        try {
            NewsletterSubscriberSQLMethods.insertIntonewsletter_sub(DatabaseConnection.open(), nsub);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den neuen Newsletter Abonnenten nicht erstellen", e);
            ShowException.showException("Datenbankfehler: Der Newsletter Abonnent konnte nicht nicht erstellt werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Abonnenten nicht erstellen");
        }
        
         this.setupSubscribers();
    }    

    private NewsletterSubscriberObj getSelectedSub() {
        int row = this.table_subscriber.getSelectedRow();

        if (row == -1) {
            return null;
        }

        NewsletterSubscriberObj sub = (NewsletterSubscriberObj) table_subscriber.getModel().getValueAt(row, 0);

        return sub;
    }

    /**
     * 
     */
    private void deleteSubscriber() {
        NewsletterSubscriberObj sub = getSelectedSub();

        if (sub == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Abonnenten aus.");
            return;
        }

        try {
            NewsletterSubscriberSQLMethods.deleteFromNewsletter_sub(DatabaseConnection.open(), sub.getId());
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Newsletter Abonnenten mit der id \"" + sub.getId() + "\" nicht löschen", e);
            ShowException.showException("Datenbankfehler: Der Newsletter Abonnent mit der id \""
                    + sub.getId() + "\" konnte nicht nicht gelöscht werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Abonnent nicht löschen.");
        }

        this.setupSubscribers();
    }

    /**
     * 
     */
    private void editSubscriber() {
        NewsletterSubscriberObj sub = getSelectedSub();

        if (sub == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen Abonnenten aus.");
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        NewsletterSubscriberDialog subd = new NewsletterSubscriberDialog(mainFrame, true, sub);
        subd.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(subd);

        NewsletterSubscriberObj nsub = subd.getReturnStatus();

        if (nsub == null) {
            return;
        }

        try {
            boolean success = NewsletterSubscriberSQLMethods.updatenewsletter_sub(DatabaseConnection.open(), nsub);

            if (!success) {
                System.out.println("ADD Exception: update failed");
            }

        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den neuen Newsletter Abonnenten nicht erstellen", e);
            ShowException.showException("Datenbankfehler: Der Newsletter Abonnent konnte nicht nicht erstellt werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Abonnenten nicht erstellen");
        }

        this.setupSubscribers();
    }

    /**
     * 
     */
    class TablePopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                tableSubPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void synchronizeSubscriberSW() {
        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                synchronizeSubscriber();
                return null;
            }

            @Override
            protected void done() {
                setupSubscribers();
            }
        };

        sw.execute();
    }

    /**
     * Synchonisiert alle Kunden, Zusatzadressen, 
     * Ansprechpartner die eine E-Mail Adressen haben
     */
    private void synchronizeSubscriber() {
//        Object[] knd = KundenRegistry.getAlleAktivenKunden();
//        
//        if(knd == null)
//            return;
        try {
//            Object[] subs = NewsletterSubscriberSQLMethods.loadNewsletter_sub(
//                    DatabaseConnection.open(), Status.NORMAL);

            KundenObj[] pks = null;
            FirmenObj[] fks = null;
            FirmenAnsprechpartnerObj[] fas = null;
            ZusatzadressenObj[] zas = null;

            Connection con = DatabaseConnection.open();

            // Privatkunden

            String sql = "SELECT * FROM kunden WHERE NOT EXISTS(SELECT kennung, zaId FROM "
                    + "newsletter_sub WHERE kunden.kundenNr = newsletter_sub.kennung "
                    + "AND newsletter_sub.zaId = -1)";

            PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet entry = statement.executeQuery();

            entry.last();
            int rows = entry.getRow();
            entry.beforeFirst();

            if (Log.logger.isDebugEnabled()) {
                Log.logger.debug("Anzahl nicht synchronisierter Privatkunden: " + rows);
            }

            if (rows != 0) {
                pks = new KundenObj[rows];

                for (int i = 0; i < rows; i++) {
                    entry.next();
                    pks[i] = KundenSQLMethods.getKundeEntry(entry);
                }
            }

            // Firmenkunden

            sql = "SELECT * FROM firmenkunden WHERE NOT EXISTS(SELECT kennung, zaId, ansprechpartnerId FROM "
                    + "newsletter_sub WHERE firmenkunden.kundenNr = newsletter_sub.kennung "
                    + "AND newsletter_sub.zaId = -1 AND newsletter_sub.ansprechpartnerId = -1)";

            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            entry = statement.executeQuery();

            entry.last();
            rows = entry.getRow();
            entry.beforeFirst();


            if (Log.logger.isDebugEnabled()) {
                Log.logger.debug("Anzahl nicht synchronisierter Geschäftskunden: " + rows);
            }

            if (rows != 0) {
                fks = new FirmenObj[rows];

                for (int i = 0; i < rows; i++) {
                    entry.next();
                    fks[i] = FirmenSQLMethods.getFirmenEntry(entry);
                }
            }

            sql = "SELECT * FROM firmen_ansprechpartner WHERE NOT EXISTS(SELECT ansprechpartnerId FROM "
                    + "newsletter_sub WHERE firmen_ansprechpartner.kundenKennung != -1 "
                    + "AND firmen_ansprechpartner.id = newsletter_sub.ansprechpartnerId)";

            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            entry = statement.executeQuery();

            entry.last();
            rows = entry.getRow();
            entry.beforeFirst();


            if (Log.logger.isDebugEnabled()) {
                Log.logger.debug("Anzahl nicht synchronisierter Ansprechpartner: " + rows);
            }

            if (rows != 0) {
                fas = new FirmenAnsprechpartnerObj[rows];

                for (int i = 0; i < rows; i++) {
                    entry.next();
                    fas[i] = FirmenAnsprechpartnerSQLMethods.getResultSetEntry(entry);
                }
            }

            // Zusatzadressen

            sql = "SELECT * FROM kunden_zusatzadressen WHERE NOT EXISTS(SELECT zaId FROM "
                    + "newsletter_sub WHERE kunden_zusatzadressen.kundenKennung != -1 "
                    + "AND kunden_zusatzadressen.id = newsletter_sub.zaId)";

            statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            entry = statement.executeQuery();

            entry.last();
            rows = entry.getRow();
            entry.beforeFirst();


            if (Log.logger.isDebugEnabled()) {
                Log.logger.debug("Anzahl nicht synchronisierter Zusatzadressen: " + rows);
            }

            if (rows != 0) {
                zas = new ZusatzadressenObj[rows];

                for (int i = 0; i < rows; i++) {
                    entry.next();
                    zas[i] = ZusatzadressenSQLMethods.getResultSetEntry(entry);
                }
            }


            statement.close();
            con.close();


            if (pks != null) {
                for (int i = 0; i < pks.length; i++) {
                    String mail = KundenMailHelper.getKundenMail(pks[i]);

                    if (mail != null && mail.length() > 0) {
                        NewsletterSubscriberObj ns = new NewsletterSubscriberObj();
                        ns.setEmail(mail);
                        ns.setKennung(pks[i].getKundenNr());
                        ns.setName(pks[i].getVorname() + " " + pks[i].getNachname());
                        ns.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setStatus(Status.NORMAL);
                        NewsletterSubscriberSQLMethods.insertIntonewsletter_sub(
                                DatabaseConnection.open(), ns);
                    }
                }
            }

            if (fks != null) {
                for (int i = 0; i < fks.length; i++) {
                    String mail = KundenMailHelper.getGeschKundenMail(fks[i]);

                    if (mail != null && mail.length() > 0) {
                        NewsletterSubscriberObj ns = new NewsletterSubscriberObj();
                        ns.setEmail(mail);
                        ns.setKennung(fks[i].getKundenNr());
                        ns.setName(fks[i].getFirmenName());
                        ns.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setStatus(Status.NORMAL);
                        NewsletterSubscriberSQLMethods.insertIntonewsletter_sub(DatabaseConnection.open(), ns);
                    }
                }
            }

            if (fas != null) {
                for (int i = 0; i < fas.length; i++) {
                    String mail = KundenMailHelper.getAnsprechpartnerMail(fas[i]);

                    if (mail != null && mail.length() > 0) {
                        NewsletterSubscriberObj ns = new NewsletterSubscriberObj();
                        ns.setEmail(mail);
                        ns.setKennung(fas[i].getKundenKennung());
                        ns.setAnsprechpartnerId(fas[i].getId());
                        ns.setName(fas[i].getVorname() + " " + fas[i].getNachname());
                        ns.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setStatus(Status.NORMAL);
                        NewsletterSubscriberSQLMethods.insertIntonewsletter_sub(DatabaseConnection.open(), ns);
                    }
                }
            }

            if (zas != null) {
                for (int i = 0; i < zas.length; i++) {
                    String mail = KundenMailHelper.getZusatzadressenMail(zas[i]);

                    if (mail != null && mail.length() > 0) {
                        NewsletterSubscriberObj ns = new NewsletterSubscriberObj();
                        ns.setEmail(mail);
                        ns.setKennung(zas[i].getKundenKennung());
                        ns.setZaId(zas[i].getId());
                        ns.setName(zas[i].getName());
                        ns.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
                        ns.setStatus(Status.NORMAL);
                        NewsletterSubscriberSQLMethods.insertIntonewsletter_sub(DatabaseConnection.open(), ns);
                    }
                }
            }



        } catch (Exception e) {
            Log.databaselogger.fatal("Bei der Newsletter Kundensynchronisation tratt ein schwerer Fehler auf", e);
            ShowException.showException("Datenbankfehler: Der Newsletterabonnenten konnten nicht nicht synchronisiert werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Abonnenten nicht synchronisieren.");
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

        tableSubPopupMenu = new javax.swing.JPopupMenu();
        newMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        pane_newsletter = new javax.swing.JTabbedPane();
        panel_newsletter = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        combo_newslettersend = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo_absender = new javax.swing.JComboBox();
        combo_absenderemail = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        btnSend = new javax.swing.JButton();
        btnSendtest = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        editor_newsletterpreview = new javax.swing.JEditorPane();
        panel_newsedit = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnNeu = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        btnDeleteNewsletter = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        combo_newsletter = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        editor_newsletter = new net.atlanticbb.tantlinger.shef.HTMLEditorPane();
        jLabel6 = new javax.swing.JLabel();
        combo_betreff = new javax.swing.JComboBox();
        panel_subscribers = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        btnNeuSub = new javax.swing.JButton();
        btnSynch = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnEditSubscriber = new javax.swing.JButton();
        btnDeleteSubscriber = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        field_search = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_subscriber = new org.jdesktop.swingx.JXTable();
        panel_import = new javax.swing.JPanel();

        tableSubPopupMenu.setName("tableSubPopupMenu"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(NewsletterPanel.class);
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        tableSubPopupMenu.add(newMenuItem);

        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        tableSubPopupMenu.add(editMenuItem);

        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        tableSubPopupMenu.add(deleteMenuItem);

        setName("Form"); // NOI18N

        pane_newsletter.setName("pane_newsletter"); // NOI18N

        panel_newsletter.setName("panel_newsletter"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        combo_newslettersend.setName("combo_newslettersend"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                    .addComponent(combo_newslettersend, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(combo_newslettersend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_absender.setEditable(true);
        combo_absender.setName("combo_absender"); // NOI18N

        combo_absenderemail.setEditable(true);
        combo_absenderemail.setName("combo_absenderemail"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_absenderemail, 0, 661, Short.MAX_VALUE)
                    .addComponent(combo_absender, 0, 661, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_absender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_absenderemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnSend.setIcon(resourceMap.getIcon("btnSend.icon")); // NOI18N
        btnSend.setText(resourceMap.getString("btnSend.text")); // NOI18N
        btnSend.setFocusable(false);
        btnSend.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSend.setName("btnSend"); // NOI18N
        btnSend.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSend);

        btnSendtest.setIcon(resourceMap.getIcon("btnSendtest.icon")); // NOI18N
        btnSendtest.setText(resourceMap.getString("btnSendtest.text")); // NOI18N
        btnSendtest.setFocusable(false);
        btnSendtest.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSendtest.setName("btnSendtest"); // NOI18N
        btnSendtest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSendtest);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        editor_newsletterpreview.setBorder(null);
        editor_newsletterpreview.setEditable(false);
        editor_newsletterpreview.setName("editor_newsletterpreview"); // NOI18N
        jScrollPane1.setViewportView(editor_newsletterpreview);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 181, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_newsletterLayout = new javax.swing.GroupLayout(panel_newsletter);
        panel_newsletter.setLayout(panel_newsletterLayout);
        panel_newsletterLayout.setHorizontalGroup(
            panel_newsletterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_newsletterLayout.setVerticalGroup(
            panel_newsletterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_newsletterLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pane_newsletter.addTab(resourceMap.getString("panel_newsletter.TabConstraints.tabTitle"), panel_newsletter); // NOI18N

        panel_newsedit.setName("panel_newsedit"); // NOI18N

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

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
        jToolBar2.add(btnNeu);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar2.add(jSeparator2);

        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave);

        btnDeleteNewsletter.setIcon(resourceMap.getIcon("btnDeleteNewsletter.icon")); // NOI18N
        btnDeleteNewsletter.setText(resourceMap.getString("btnDeleteNewsletter.text")); // NOI18N
        btnDeleteNewsletter.setFocusable(false);
        btnDeleteNewsletter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDeleteNewsletter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDeleteNewsletter.setName("btnDeleteNewsletter"); // NOI18N
        btnDeleteNewsletter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteNewsletter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNewsletterActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDeleteNewsletter);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar2.add(jSeparator3);

        combo_newsletter.setName("combo_newsletter"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jSeparator5.setName("jSeparator5"); // NOI18N

        editor_newsletter.setName("editor_newsletter"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        combo_betreff.setName("combo_betreff"); // NOI18N

        javax.swing.GroupLayout panel_newseditLayout = new javax.swing.GroupLayout(panel_newsedit);
        panel_newsedit.setLayout(panel_newseditLayout);
        panel_newseditLayout.setHorizontalGroup(
            panel_newseditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addGroup(panel_newseditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_newsletter, 0, 655, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_newseditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_newseditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_betreff, 0, 725, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_newseditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editor_newsletter, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_newseditLayout.setVerticalGroup(
            panel_newseditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_newseditLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_newseditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_newsletter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_newseditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_betreff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(editor_newsletter, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );

        pane_newsletter.addTab(resourceMap.getString("panel_newsedit.TabConstraints.tabTitle"), panel_newsedit); // NOI18N

        panel_subscribers.setName("panel_subscribers"); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        btnNeuSub.setIcon(resourceMap.getIcon("btnNeuSub.icon")); // NOI18N
        btnNeuSub.setText(resourceMap.getString("btnNeuSub.text")); // NOI18N
        btnNeuSub.setFocusable(false);
        btnNeuSub.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuSub.setName("btnNeuSub"); // NOI18N
        btnNeuSub.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuSubActionPerformed(evt);
            }
        });
        jToolBar3.add(btnNeuSub);

        btnSynch.setIcon(resourceMap.getIcon("btnSynch.icon")); // NOI18N
        btnSynch.setText(resourceMap.getString("btnSynch.text")); // NOI18N
        btnSynch.setToolTipText(resourceMap.getString("btnSynch.toolTipText")); // NOI18N
        btnSynch.setFocusable(false);
        btnSynch.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSynch.setName("btnSynch"); // NOI18N
        btnSynch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSynch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSynchActionPerformed(evt);
            }
        });
        jToolBar3.add(btnSynch);

        jSeparator7.setName("jSeparator7"); // NOI18N
        jToolBar3.add(jSeparator7);

        btnEditSubscriber.setIcon(resourceMap.getIcon("btnEditSubscriber.icon")); // NOI18N
        btnEditSubscriber.setText(resourceMap.getString("btnEditSubscriber.text")); // NOI18N
        btnEditSubscriber.setFocusable(false);
        btnEditSubscriber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnEditSubscriber.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEditSubscriber.setName("btnEditSubscriber"); // NOI18N
        btnEditSubscriber.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditSubscriber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSubscriberActionPerformed(evt);
            }
        });
        jToolBar3.add(btnEditSubscriber);

        btnDeleteSubscriber.setIcon(resourceMap.getIcon("btnDeleteSubscriber.icon")); // NOI18N
        btnDeleteSubscriber.setText(resourceMap.getString("btnDeleteSubscriber.text")); // NOI18N
        btnDeleteSubscriber.setFocusable(false);
        btnDeleteSubscriber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDeleteSubscriber.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDeleteSubscriber.setName("btnDeleteSubscriber"); // NOI18N
        btnDeleteSubscriber.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteSubscriber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSubscriberActionPerformed(evt);
            }
        });
        jToolBar3.add(btnDeleteSubscriber);

        jSeparator8.setName("jSeparator8"); // NOI18N
        jToolBar3.add(jSeparator8);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(resourceMap.getIcon("jLabel7.icon")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(73, 16));
        jToolBar3.add(jLabel7);

        jSeparator9.setName("jSeparator9"); // NOI18N
        jToolBar3.add(jSeparator9);

        field_search.setName("field_search"); // NOI18N
        field_search.setPreferredSize(new java.awt.Dimension(150, 25));
        field_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_searchKeyTyped(evt);
            }
        });
        jToolBar3.add(field_search);

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
        jToolBar3.add(btnSearch);

        jSeparator10.setName("jSeparator10"); // NOI18N
        jToolBar3.add(jSeparator10);

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
        jToolBar3.add(btnRefresh);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        table_subscriber.setModel(new javax.swing.table.DefaultTableModel(
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
        table_subscriber.setColumnControlVisible(true);
        table_subscriber.setName("table_subscriber"); // NOI18N
        jScrollPane2.setViewportView(table_subscriber);

        javax.swing.GroupLayout panel_subscribersLayout = new javax.swing.GroupLayout(panel_subscribers);
        panel_subscribers.setLayout(panel_subscribersLayout);
        panel_subscribersLayout.setHorizontalGroup(
            panel_subscribersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
        );
        panel_subscribersLayout.setVerticalGroup(
            panel_subscribersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_subscribersLayout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
        );

        pane_newsletter.addTab(resourceMap.getString("panel_subscribers.TabConstraints.tabTitle"), panel_subscribers); // NOI18N

        panel_import.setName("panel_import"); // NOI18N

        javax.swing.GroupLayout panel_importLayout = new javax.swing.GroupLayout(panel_import);
        panel_import.setLayout(panel_importLayout);
        panel_importLayout.setHorizontalGroup(
            panel_importLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 807, Short.MAX_VALUE)
        );
        panel_importLayout.setVerticalGroup(
            panel_importLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 437, Short.MAX_VALUE)
        );

        pane_newsletter.addTab(resourceMap.getString("panel_import.TabConstraints.tabTitle"), panel_import); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pane_newsletter, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pane_newsletter, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        String name = JOptionPane.showInputDialog(null, "Bitte geben Sie den Namen für den neuen Newsletter ein.",
                "Name des neuen Newsletters", JOptionPane.INFORMATION_MESSAGE);

        if (name == null) {
            return;
        }

        NewsletterObj nl = new NewsletterObj();
        int id = -1;

        nl.setBenutzerId(BasicRegistry.currentUser.getId());
        nl.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        nl.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        nl.setSend(false);
        nl.setSender(Config.get("newsletterSender", "MaklerPoint"));
        nl.setSenderMail(Config.get("newsletterSendermail", "info@maklerpoint.de"));
        nl.setSubject(name);
        nl.setText(null);
        nl.setStatus(Status.NORMAL);

        try {
            id = NewsletterSQLMethods.insertIntonewsletter(DatabaseConnection.open(), nl);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den neuen Newsletter nicht erstellen", e);
            ShowException.showException("Datenbankfehler: Der Newsletter konnte nicht nicht erstellt werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Newsletter nicht erstellen");
        }

        loadNewsletter(id);
}//GEN-LAST:event_btnNeuActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (currentNl == null) {
            JOptionPane.showMessageDialog(null, "Kein Newsletter zum speichern ausgewählt");
            return;
        }

        currentNl.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
        currentNl.setText(this.editor_newsletter.getText());
        currentNl.setSender((String) this.combo_absender.getSelectedItem());
        currentNl.setSenderMail((String) this.combo_absenderemail.getSelectedItem());
        currentNl.setSubject((String) this.combo_betreff.getSelectedItem());

        try {
            NewsletterSQLMethods.updatenewsletter(DatabaseConnection.open(), currentNl);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Newsletter nicht speichern", e);
            ShowException.showException("Datenbankfehler: Der Newsletter konnte nicht nicht gespeichert werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Newsletter nicht speichern");
        }
        loadNewsletter(currentNl.getId());
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteNewsletterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNewsletterActionPerformed
        if (currentNl == null) {
            JOptionPane.showMessageDialog(null, "Kein Newsletter zum löschen ausgewählt");
            return;
        }

        try {
            NewsletterSQLMethods.deleteFromNewsletter(DatabaseConnection.open(), currentNl.getId());
            currentNl = null;
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte den Newsletter nicht speichern", e);
            ShowException.showException("Datenbankfehler: Der Newsletter konnte nicht nicht gespeichert werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Newsletter nicht speichern");
        }
        loadNewsletter(-1);
}//GEN-LAST:event_btnDeleteNewsletterActionPerformed

    private void btnNeuSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuSubActionPerformed
        newSubscriber();
    }//GEN-LAST:event_btnNeuSubActionPerformed

    private void btnDeleteSubscriberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSubscriberActionPerformed
        deleteSubscriber();
    }//GEN-LAST:event_btnDeleteSubscriberActionPerformed

    private void field_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_searchKeyTyped
        if (field_search.getText().length() > 3) {
            searchTable();
        }
}//GEN-LAST:event_field_searchKeyTyped

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchTable();
}//GEN-LAST:event_btnSearchActionPerformed

    private void btnSynchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSynchActionPerformed
//        synchronizeSubscriber();
        synchronizeSubscriberSW();
    }//GEN-LAST:event_btnSynchActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        newSubscriber();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editSubscriber();
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        deleteSubscriber();
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void btnEditSubscriberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSubscriberActionPerformed
        editSubscriber();
    }//GEN-LAST:event_btnEditSubscriberActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        setupSubscribers();
}//GEN-LAST:event_btnRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteNewsletter;
    private javax.swing.JButton btnDeleteSubscriber;
    private javax.swing.JButton btnEditSubscriber;
    private javax.swing.JButton btnNeu;
    private javax.swing.JButton btnNeuSub;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendtest;
    private javax.swing.JButton btnSynch;
    private javax.swing.JComboBox combo_absender;
    private javax.swing.JComboBox combo_absenderemail;
    private javax.swing.JComboBox combo_betreff;
    private javax.swing.JComboBox combo_newsletter;
    private javax.swing.JComboBox combo_newslettersend;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private net.atlanticbb.tantlinger.shef.HTMLEditorPane editor_newsletter;
    private javax.swing.JEditorPane editor_newsletterpreview;
    private javax.swing.JTextField field_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JTabbedPane pane_newsletter;
    private javax.swing.JPanel panel_import;
    private javax.swing.JPanel panel_newsedit;
    private javax.swing.JPanel panel_newsletter;
    private javax.swing.JPanel panel_subscribers;
    private javax.swing.JPopupMenu tableSubPopupMenu;
    private org.jdesktop.swingx.JXTable table_subscriber;
    // End of variables declaration//GEN-END:variables
    private JDialog subscriberBox;
}
