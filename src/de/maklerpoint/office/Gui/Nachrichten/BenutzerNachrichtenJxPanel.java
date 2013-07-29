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
 * JxPanelStartpage.java
 *
 * Created on Jul 9, 2010, 5:04:36 PM
 */
package de.maklerpoint.office.Gui.Nachrichten;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Nachrichten.MessageObj;
import de.maklerpoint.office.Nachrichten.Tools.MessageSQLMethods;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.ImageTools;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.openide.awt.DropDownButtonFactory;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerNachrichtenJxPanel extends JXTitledPanel {

    private int nachrichtenCount;
    public static final String[] ColumnsEmpf = {"Hidden", "Betreff", "Absender", "Empfangen am"};
    public static final String[] ColumnsSend = {"Hidden", "Betreff", "Empfänger", "Gesendet am"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /** Creates new form JxPanelStartpage */
    public BenutzerNachrichtenJxPanel() {
        initComponents();
        addAnsichtButtons();
        loadTable();
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

    private void addAnsichtButtons() {
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/table-join.png"), popupDBStatus);
        dropDownButton.setText("Ansicht");
        dropDownButton.setToolTipText("Bankkonten Ansicht");
        //dropDownButton.setText();
        this.toolbar.add(dropDownButton);
    }

    private void loadTable() {
        MessageObj[] msg = null;

        try {
            Object[][] data = null;

            boolean sendMsgs = false;

            if (this.btnEingang.isSelected()) {
                msg = MessageSQLMethods.getAllMessagesEmpfang(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), getStatus());
                sendMsgs = false;
            } else {
                msg = MessageSQLMethods.getAllMessagesSend(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), getStatus());
                sendMsgs = true;
            }

            if (msg != null) {
                data = new Object[msg.length][4];

                for (int i = 0; i < msg.length; i++) {
                    data[i][0] = msg[i];

                    JLabel btr = new JLabel();

                    if (!msg[i].isRead()) {
                        btr.setText("<html><b>" + msg[i].getBetreff() + "</b></html>");
                    } else {
                        btr.setText("<html>" + msg[i].getBetreff() + "</html>");
                    }

                    data[i][1] = btr;

                    String empfsends = null;
                    BenutzerObj ben = BenutzerRegistry.getBenutzer(msg[i].getSenderId(), true);

                    if (ben != null) {
                        empfsends = BenutzerRegistry.getBenutzer(msg[i].getEmpfaengerId(), true).toString();
                    }

                    if (!sendMsgs) {
                        ben = BenutzerRegistry.getBenutzer(msg[i].getSenderId(), true);
                        if (ben != null) {
                            empfsends = ben.toString();
                        }
                    }

                    JLabel empfsend = new JLabel();

                    if (!msg[i].isRead()) {
                        empfsend.setText("<html><b>" + empfsends + "</b></html>");
                    } else {
                        empfsend.setText("<html>" + empfsends + "</html>");
                    }

                    data[i][2] = empfsend;

                    JLabel datelb = new JLabel();

                    if (!msg[i].isRead()) {
                        datelb.setText("<html><b>" + df.format(msg[i].getCreated()) + "</b></html>");
                    } else {
                        datelb.setText("<html>" + df.format(msg[i].getCreated()) + "</html>");
                    }

                    data[i][3] = datelb;
                }

                nachrichtenCount = msg.length;

                if (nachrichtenCount == 1) {
                    this.label_tablestatustext.setText("Eine Nachricht");
                    this.label_tablestatustext.setForeground(new Color(-16738048));
                } else {
                    this.label_tablestatustext.setText(nachrichtenCount + " Nachrichten");
                    this.label_tablestatustext.setForeground(new Color(-16738048));
                }
            } else {
                this.label_tablestatustext.setText("Keine Nachrichten");
                this.label_tablestatustext.setForeground(Color.red);
                nachrichtenCount = 0;
            }


            if (sendMsgs) {
                setTable(data, ColumnsSend);
            } else {
                setTable(data, ColumnsEmpf);
            }

            if (msg != null) {
                showMessage(msg[0]);
                table_nachrichten.requestFocusInWindow();
                table_nachrichten.changeSelection(0, 0, false, false);
            }

        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Nachrichten nicht laden", e);
            ShowException.showException("Die Nachrichten konnten nicht geladen werden",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Nachrichten nicht laden");
        }
    }

    private void showMessage(MessageObj msg) {
        if (msg == null) {
            return;
        }

        this.text_nachricht.setText(msg.getContext());

        this.label_activemail.setText("Aktive Nachricht: " + msg.getBetreff());

        try {
            MessageSQLMethods.setRead(DatabaseConnection.open(), msg);
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Nachrichten nicht als gelesen markieren", e);
            ShowException.showException("Die Nachricht konnte nicht als gelesen markiert werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Nachrichten nicht laden");
        }

    }

    private void setTable(Object[][] data, String[] columns) {
        this.table_nachrichten.setModel(new AbstractStandardModel(data, columns));
        this.table_nachrichten.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());

        table_nachrichten.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_nachrichten.setHighlighters(HighlighterFactory.createSimpleStriping());
        table_nachrichten.setColumnSelectionAllowed(false);
        table_nachrichten.setCellSelectionEnabled(false);
        table_nachrichten.setRowSelectionAllowed(true);
        table_nachrichten.setAutoCreateRowSorter(true);

        table_nachrichten.setFillsViewportHeight(true);
        table_nachrichten.removeColumn(table_nachrichten.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_nachrichten.addMouseListener(popupListener);
        table_nachrichten.setColumnControlVisible(true);

        JTableHeader header = table_nachrichten.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_nachrichten.packAll();

        table_nachrichten.tableChanged(new TableModelEvent(table_nachrichten.getModel()));
        table_nachrichten.revalidate();
    }

    private void neueNachricht(){
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        mesasgedialog = new NeueBenutzerNachrichtDialog(mainFrame, true);
        mesasgedialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(mesasgedialog);

        this.loadTable();
    }
    
    private void antworten(){
        int row = table_nachrichten.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Nachricht aus.");
            return;
        }

        MessageObj msg = (MessageObj) this.table_nachrichten.getModel().getValueAt(row, 0);

        if (msg == null) {
            return;
        }
        
        if(msg.getSenderId() == -1){
            JOptionPane.showMessageDialog(null, "Sie können nicht auf eine System Nachricht antworten.");
            return;
        }

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        mesasgedialog = new NeueBenutzerNachrichtDialog(mainFrame, true, msg);
        mesasgedialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(mesasgedialog);

        this.loadTable();
    }
    
    private void archiveNachricht(){
        int row = table_nachrichten.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Nachricht aus.");
            return;
        }

        MessageObj msg = (MessageObj) this.table_nachrichten.getModel().getValueAt(row, 0);

        if (msg == null) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Nachricht wirklich archivieren?",
                "Wirklich archivieren?", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            MessageSQLMethods.archiveFromMessage(DatabaseConnection.open(), msg);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Nachricht nicht archivieren", e);
            ShowException.showException("Datenbankfehler: Die Nachricht konnte nicht archiviert werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Nachricht nicht archivieren");
        }

        loadTable();
    }
    
    private void deleteNachricht(){
        int row = table_nachrichten.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Nachricht aus.");
            return;
        }

        MessageObj msg = (MessageObj) this.table_nachrichten.getModel().getValueAt(row, 0);

        if (msg == null) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "Wollen Sie die Nachricht wirklich löschen?",
                "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            MessageSQLMethods.deleteFromMessage(DatabaseConnection.open(), msg);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Nachricht nicht löschen", e);
            ShowException.showException("Datenbankfehler: Das Nachricht konnte nicht gelöscht werden. "
                    + "Bitte wenden Sie sich an Ihren Systemadministrator oder an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Nachricht nicht löschen");
        }

        loadTable();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableNachrichtenPopupMenu = new javax.swing.JPopupMenu();
        neuNachrichtMenuItem = new javax.swing.JMenuItem();
        answerMessageMenuItem = new javax.swing.JMenuItem();
        archiveMessageMenuItem = new javax.swing.JMenuItem();
        deleteMessageMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        grpPost = new javax.swing.ButtonGroup();
        popupDBStatus = new javax.swing.JPopupMenu();
        alleDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        aktiveDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        archivedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        deletedDBMenuItem = new javax.swing.JCheckBoxMenuItem();
        grp_dbstatus = new javax.swing.ButtonGroup();
        toolbar = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnNeu = new javax.swing.JButton();
        btnAntworten = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnEingang = new javax.swing.JToggleButton();
        btnAusgang = new javax.swing.JToggleButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        split_kunden = new javax.swing.JSplitPane();
        panel_tableholder = new javax.swing.JPanel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_nachrichten = new org.jdesktop.swingx.JXTable();
        panel_tableStatus = new javax.swing.JPanel();
        label_tablestatustext = new javax.swing.JLabel();
        label_activemail = new javax.swing.JLabel();
        panel_nachrichtcontent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_nachricht = new javax.swing.JTextArea();

        tableNachrichtenPopupMenu.setName("tableNachrichtenPopupMenu"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(BenutzerNachrichtenJxPanel.class);
        neuNachrichtMenuItem.setText(resourceMap.getString("neuNachrichtMenuItem.text")); // NOI18N
        neuNachrichtMenuItem.setName("neuNachrichtMenuItem"); // NOI18N
        neuNachrichtMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuNachrichtMenuItemActionPerformed(evt);
            }
        });
        tableNachrichtenPopupMenu.add(neuNachrichtMenuItem);

        answerMessageMenuItem.setText(resourceMap.getString("answerMessageMenuItem.text")); // NOI18N
        answerMessageMenuItem.setName("answerMessageMenuItem"); // NOI18N
        answerMessageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerMessageMenuItemActionPerformed(evt);
            }
        });
        tableNachrichtenPopupMenu.add(answerMessageMenuItem);

        archiveMessageMenuItem.setText(resourceMap.getString("archiveMessageMenuItem.text")); // NOI18N
        archiveMessageMenuItem.setName("archiveMessageMenuItem"); // NOI18N
        archiveMessageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveMessageMenuItemActionPerformed(evt);
            }
        });
        tableNachrichtenPopupMenu.add(archiveMessageMenuItem);

        deleteMessageMenuItem.setText(resourceMap.getString("deleteMessageMenuItem.text")); // NOI18N
        deleteMessageMenuItem.setName("deleteMessageMenuItem"); // NOI18N
        deleteMessageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMessageMenuItemActionPerformed(evt);
            }
        });
        tableNachrichtenPopupMenu.add(deleteMessageMenuItem);

        refreshMenuItem.setMnemonic('a');
        refreshMenuItem.setText(resourceMap.getString("refreshMenuItem.text")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        tableNachrichtenPopupMenu.add(refreshMenuItem);

        popupDBStatus.setName("popupDBStatus"); // NOI18N

        grp_dbstatus.add(alleDBMenuItem);
        alleDBMenuItem.setMnemonic('A');
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
        aktiveDBMenuItem.setText(resourceMap.getString("aktiveDBMenuItem.text")); // NOI18N
        aktiveDBMenuItem.setToolTipText(resourceMap.getString("aktiveDBMenuItem.toolTipText")); // NOI18N
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
        archivedDBMenuItem.setToolTipText(resourceMap.getString("archivedDBMenuItem.toolTipText")); // NOI18N
        archivedDBMenuItem.setName("archivedDBMenuItem"); // NOI18N
        archivedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(archivedDBMenuItem);

        grp_dbstatus.add(deletedDBMenuItem);
        deletedDBMenuItem.setMnemonic('G');
        deletedDBMenuItem.setText(resourceMap.getString("deletedDBMenuItem.text")); // NOI18N
        deletedDBMenuItem.setToolTipText(resourceMap.getString("deletedDBMenuItem.toolTipText")); // NOI18N
        deletedDBMenuItem.setName("deletedDBMenuItem"); // NOI18N
        deletedDBMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedDBMenuItemActionPerformed(evt);
            }
        });
        popupDBStatus.add(deletedDBMenuItem);

        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setOpaque(true);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setAutoscrolls(true);
        setName("Form"); // NOI18N
        getContentContainer().setLayout(new java.awt.BorderLayout());

        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setName("toolbar"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        toolbar.add(jSeparator1);

        btnNeu.setIcon(resourceMap.getIcon("btnNeu.icon")); // NOI18N
        btnNeu.setText(resourceMap.getString("btnNeu.text")); // NOI18N
        btnNeu.setToolTipText(resourceMap.getString("btnNeu.toolTipText")); // NOI18N
        btnNeu.setFocusable(false);
        btnNeu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNeu.setName("btnNeu"); // NOI18N
        btnNeu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuActionPerformed(evt);
            }
        });
        toolbar.add(btnNeu);

        btnAntworten.setIcon(resourceMap.getIcon("btnAntworten.icon")); // NOI18N
        btnAntworten.setText(resourceMap.getString("btnAntworten.text")); // NOI18N
        btnAntworten.setToolTipText(resourceMap.getString("btnAntworten.toolTipText")); // NOI18N
        btnAntworten.setFocusable(false);
        btnAntworten.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAntworten.setName("btnAntworten"); // NOI18N
        btnAntworten.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAntworten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntwortenActionPerformed(evt);
            }
        });
        toolbar.add(btnAntworten);

        btnArchive.setIcon(resourceMap.getIcon("btnArchive.icon")); // NOI18N
        btnArchive.setText(resourceMap.getString("btnArchive.text")); // NOI18N
        btnArchive.setToolTipText(resourceMap.getString("btnArchive.toolTipText")); // NOI18N
        btnArchive.setFocusable(false);
        btnArchive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnArchive.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        btnDelete.setToolTipText(resourceMap.getString("btnDelete.toolTipText")); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        toolbar.add(btnDelete);

        jSeparator6.setName("jSeparator6"); // NOI18N
        toolbar.add(jSeparator6);

        grpPost.add(btnEingang);
        btnEingang.setIcon(resourceMap.getIcon("btnEingang.icon")); // NOI18N
        btnEingang.setSelected(true);
        btnEingang.setText(resourceMap.getString("btnEingang.text")); // NOI18N
        btnEingang.setFocusable(false);
        btnEingang.setName("btnEingang"); // NOI18N
        btnEingang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEingang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEingangActionPerformed(evt);
            }
        });
        toolbar.add(btnEingang);

        grpPost.add(btnAusgang);
        btnAusgang.setIcon(resourceMap.getIcon("btnAusgang.icon")); // NOI18N
        btnAusgang.setText(resourceMap.getString("btnAusgang.text")); // NOI18N
        btnAusgang.setFocusable(false);
        btnAusgang.setName("btnAusgang"); // NOI18N
        btnAusgang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAusgang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAusgangActionPerformed(evt);
            }
        });
        toolbar.add(btnAusgang);

        jSeparator7.setName("jSeparator7"); // NOI18N
        toolbar.add(jSeparator7);

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

        getContentContainer().add(toolbar, java.awt.BorderLayout.NORTH);

        split_kunden.setDividerLocation(300);
        split_kunden.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        split_kunden.setName("split_kunden"); // NOI18N

        panel_tableholder.setName("panel_tableholder"); // NOI18N

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_nachrichten.setModel(new javax.swing.table.DefaultTableModel(
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
        table_nachrichten.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_nachrichten.setMinimumSize(new java.awt.Dimension(400, 150));
        table_nachrichten.setName("table_nachrichten"); // NOI18N
        table_nachrichten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_nachrichtenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_nachrichten);

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(1294, 26));

        label_tablestatustext.setText(resourceMap.getString("label_tablestatustext.text")); // NOI18N
        label_tablestatustext.setName("label_tablestatustext"); // NOI18N

        label_activemail.setText(resourceMap.getString("label_activemail.text")); // NOI18N
        label_activemail.setName("label_activemail"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_tablestatustext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 755, Short.MAX_VALUE)
                .addComponent(label_activemail)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_tablestatustext)
                .addComponent(label_activemail, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tableholderLayout = new javax.swing.GroupLayout(panel_tableholder);
        panel_tableholder.setLayout(panel_tableholderLayout);
        panel_tableholderLayout.setHorizontalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tableStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
        );
        panel_tableholderLayout.setVerticalGroup(
            panel_tableholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tableholderLayout.createSequentialGroup()
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        split_kunden.setTopComponent(panel_tableholder);

        panel_nachrichtcontent.setName("panel_nachrichtcontent"); // NOI18N
        panel_nachrichtcontent.setLayout(new javax.swing.BoxLayout(panel_nachrichtcontent, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_nachricht.setColumns(20);
        text_nachricht.setEditable(false);
        text_nachricht.setLineWrap(true);
        text_nachricht.setRows(5);
        text_nachricht.setWrapStyleWord(true);
        text_nachricht.setName("text_nachricht"); // NOI18N
        jScrollPane1.setViewportView(text_nachricht);

        panel_nachrichtcontent.add(jScrollPane1);

        split_kunden.setRightComponent(panel_nachrichtcontent);

        getContentContainer().add(split_kunden, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void table_nachrichtenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_nachrichtenMouseClicked
        int row = table_nachrichten.getSelectedRow();

        if (row == -1) {
            return;
        }

        MessageObj msg = (MessageObj) table_nachrichten.getModel().getValueAt(row, 0);

        if (msg == null) {
            return;
        }

        this.showMessage(msg);
}//GEN-LAST:event_table_nachrichtenMouseClicked

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        archiveNachricht();
}//GEN-LAST:event_btnArchiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteNachricht();
}//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuActionPerformed
        neueNachricht();
    }//GEN-LAST:event_btnNeuActionPerformed

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

    private void btnAntwortenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntwortenActionPerformed
        antworten();
    }//GEN-LAST:event_btnAntwortenActionPerformed

    private void btnAusgangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAusgangActionPerformed
        this.btnAntworten.setEnabled(false);
        this.answerMessageMenuItem.setEnabled(false);                
        loadTable();
    }//GEN-LAST:event_btnAusgangActionPerformed

    private void btnEingangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEingangActionPerformed
        this.btnAntworten.setEnabled(true);
        this.answerMessageMenuItem.setEnabled(true);
        loadTable();
    }//GEN-LAST:event_btnEingangActionPerformed

    private void deleteMessageMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMessageMenuItemActionPerformed
        deleteNachricht();
    }//GEN-LAST:event_deleteMessageMenuItemActionPerformed

    private void archiveMessageMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveMessageMenuItemActionPerformed
        archiveNachricht();
    }//GEN-LAST:event_archiveMessageMenuItemActionPerformed

    private void neuNachrichtMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuNachrichtMenuItemActionPerformed
        neueNachricht();
    }//GEN-LAST:event_neuNachrichtMenuItemActionPerformed

    private void answerMessageMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerMessageMenuItemActionPerformed
        antworten();
    }//GEN-LAST:event_answerMessageMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        loadTable();
    }//GEN-LAST:event_refreshMenuItemActionPerformed

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
                int row = table_nachrichten.rowAtPoint(point);

                if (row != -1) {
                    MessageObj msg = (MessageObj) table_nachrichten.getModel().getValueAt(row, 0);
                    showMessage(msg);
                }

                table_nachrichten.changeSelection(row, 0, false, false);

                tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBoxMenuItem aktiveDBMenuItem;
    public javax.swing.JCheckBoxMenuItem alleDBMenuItem;
    public javax.swing.JMenuItem answerMessageMenuItem;
    public javax.swing.JMenuItem archiveMessageMenuItem;
    public javax.swing.JCheckBoxMenuItem archivedDBMenuItem;
    public javax.swing.JButton btnAntworten;
    public javax.swing.JButton btnArchive;
    public javax.swing.JToggleButton btnAusgang;
    public javax.swing.JButton btnDelete;
    public javax.swing.JToggleButton btnEingang;
    public javax.swing.JButton btnNeu;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JMenuItem deleteMessageMenuItem;
    public javax.swing.JCheckBoxMenuItem deletedDBMenuItem;
    public javax.swing.ButtonGroup grpPost;
    public javax.swing.ButtonGroup grp_dbstatus;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JToolBar.Separator jSeparator1;
    public javax.swing.JToolBar.Separator jSeparator6;
    public javax.swing.JToolBar.Separator jSeparator7;
    public javax.swing.JLabel label_activemail;
    public javax.swing.JLabel label_tablestatustext;
    public javax.swing.JMenuItem neuNachrichtMenuItem;
    public javax.swing.JPanel panel_nachrichtcontent;
    public javax.swing.JPanel panel_tableStatus;
    public javax.swing.JPanel panel_tableholder;
    public javax.swing.JPopupMenu popupDBStatus;
    public javax.swing.JMenuItem refreshMenuItem;
    public javax.swing.JScrollPane scroll_protokolle;
    public javax.swing.JSplitPane split_kunden;
    public javax.swing.JPopupMenu tableNachrichtenPopupMenu;
    public org.jdesktop.swingx.JXTable table_nachrichten;
    public javax.swing.JTextArea text_nachricht;
    public javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    private JDialog mesasgedialog;
}
