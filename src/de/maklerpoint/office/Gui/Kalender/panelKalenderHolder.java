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
 * panelKalenderHolder.java
 *
 * Created on Jul 10, 2010, 7:30:13 PM
 */
package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kalender.Aufgaben.AufgabenObj;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Schnittstellen.Google.GoogleCalendar;
import de.maklerpoint.office.Schnittstellen.XML.TermineXMLExport;
import de.maklerpoint.office.Schnittstellen.iCal.iCalExport;
import de.maklerpoint.office.Tags.Tags;
import de.maklerpoint.office.Tools.BasicRegex;
import de.maklerpoint.office.Tools.FileTools;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DatePanel;
import org.jdesktop.swingx.JXLabel;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class panelKalenderHolder extends javax.swing.JPanel {

    public static final int TAGESANSICHT = 0;
    public static final int WOCHENANSICHT = 1;
    public static final int MONATSANSICHT = 2;
    public static final int JAHRESANSICHT = 3;
    private Date currentDate = null;
    private Date today = new Date(System.currentTimeMillis());
    private ArrayList<AufgabenObj> aktiveAufgaben = new ArrayList<AufgabenObj>();
    private CRMView crm = null;
    private int view = 1;

    /** Creates new form panelKalenderHolder */
    public panelKalenderHolder(Date currentdate, CRMView crm) {
        this.currentDate = currentdate;
        this.crm = crm;
        initComponents();
        setUp();
    }

    private void setUp() {
        System.out.println("Setting up");
        addExportCommandButton();
        loadKalender();
        loadDatePanel();
        loadAufgaben();
        loadGeburtstage(currentDate);
    }

    private void addExportCommandButton() {
        JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource(
                "de/acyrance/CRM/Gui/resources/export.png"));
        exportButton.setExtraText("Ausgewählte Kunde(n) exportieren");

        RichTooltip tooltip = new RichTooltip("Terminexport", "Exportiert die aktuelle Kalenderansicht oder den "
                + "gesamten Kalender in das gewünscht Format oder zu dem gewünschten Service.");

        exportButton.setPopupRichTooltip(tooltip);

        exportButton.setPopupCallback(new ExportPopupCallback());
        exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
        exportButton.setFlat(true);

        this.toolbarKalender.add(exportButton, 3);
    }

    /**
     * EXPORT POPUP
     */
    private class ExportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {
//
            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
//            JCommandMenuButton csv = new JCommandMenuButton("als CSV Datei (.csv)",
//                    getResizableIconFromResource(ResourceStrings.CSV_ICON));
//            csv.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
////                    openExportDialog(ExportImportTypen.CSV);
//                }
//            });
//
//            popupMenu.addMenuButton(csv);
//
//            JCommandMenuButton xls = new JCommandMenuButton("als Excel Kalender (.xls)",
//                    getResizableIconFromResource(ResourceStrings.EXCEL_ICON));
//            xls.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
////                    openExportDialog(ExportImportTypen.XLS);
//                }
//            });
//
//            popupMenu.addMenuButton(xls);
//
//            JCommandMenuButton pdf = new JCommandMenuButton("Tagesansicht als Word Datei",
//                    getResizableIconFromResource(ResourceStrings.WORD_ICON));
//            pdf.addActionListener(new ActionListener() {
//
//                public void actionPerformed(ActionEvent e) {
////                    openExportDialog(ExportImportTypen.PDF);
//                }
//            });

//            popupMenu.addMenuButton(pdf);

            JCommandMenuButton xml = new JCommandMenuButton("als XML Datei",
                    getResizableIconFromResource(ResourceStrings.XML_ICON));
            xml.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportXML();
                }
            });

            popupMenu.addMenuButton(xml);

            JCommandMenuButton google = new JCommandMenuButton("Zu Ihrem Google Kalender",
                    getResizableIconFromResource(ResourceStrings.GOOGLE_ICON));
            google.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportGoogle();
                }
            });

            popupMenu.addMenuButton(google);

            JCommandMenuButton ical = new JCommandMenuButton("iCal Kalender (.ica)",
                    getResizableIconFromResource(ResourceStrings.ICAL_ICON));
            ical.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportIcal();
                }
            });

            popupMenu.addMenuButton(ical);

            JCommandMenuButton outlook = new JCommandMenuButton("Nach Outlook",
                    getResizableIconFromResource(ResourceStrings.OUTLOOK_ICON));
            outlook.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportOutlook();
                }
            });

            popupMenu.addMenuButton(outlook);

            return popupMenu;
        }
    }

    private void exportGoogle() {
        GoogleCalendar.transmitAlleEigenenTermine();
    }

    private void exportIcal() {
        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.ICAL),
                ExportImportTypen.getTypeName(ExportImportTypen.ICAL));

        if (file == null || file.length() < 1) {
            return;
        }

        iCalExport.exportAlleEigenenTermine(file);
    }

    private void exportXML() {
        String file = FileTools.saveFile(ExportImportTypen.getDialogName(ExportImportTypen.ICAL),
                ExportImportTypen.getTypeName(ExportImportTypen.ICAL));

        if (file == null || file.length() < 1) {
            return;
        }

        TermineXMLExport xml = new TermineXMLExport(file, KalenderRegistry.getTermine());
        try {
            xml.write();
        } catch (Exception e) {
            Log.logger.fatal("Beim Export der Termine in das XML Format "
                    + "ist ein Fehler aufgetretten. ", e);
            ShowException.showException("Beim XML-Export der Termine ist ein Fehler aufgetretten.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht exportieren");
        }
    }

    private void exportOutlook() {
        // TODO implement
    }

    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource),
                new Dimension(16, 16));
    }

    /**
     * Load panel
     */
    public void loadDatePanel() {
        final DatePanel datepanel = CalendarFactory.createDatePanel();
        jxpanel_datum.add(datepanel, BorderLayout.CENTER);

        datepanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Date choosed = (Date) datepanel.getValue();
                Calendar c1 = Calendar.getInstance();

                c1.setTime(choosed);
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 0);

                currentDate = c1.getTime();
                loadKalender();
                loadAufgaben();
                loadGeburtstage(currentDate);
            }
        });

        jxpanel_datum.revalidate();
    }

    /**
     * 
     */
    public void loadKalender() {

        this.panelKalenderHolder.removeAll();

        if (view == TAGESANSICHT) {
            JPanel tagesAnsicht = new panelTagesAnsicht(currentDate, crm);
//            tagesAnsicht.setSize(panelKalenderHolder.getWidth(), panelKalenderHolder.getHeight());
            tagesAnsicht.setVisible(true);
            panelKalenderHolder.add(tagesAnsicht, BorderLayout.CENTER);
            panelKalenderHolder.revalidate();
            panelKalenderHolder.repaint();
        } else if (view == WOCHENANSICHT) {
            JPanel wochenAnsicht = new panelWochenAnsicht(currentDate, crm);
            wochenAnsicht.setVisible(true);
            panelKalenderHolder.add(wochenAnsicht, BorderLayout.CENTER);
            panelKalenderHolder.revalidate();
            panelKalenderHolder.repaint();
        }

        this.revalidate();
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */
    public boolean sameDay(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    public void refresh() {
        loadKalender();
        loadAufgaben();
        loadGeburtstage(currentDate);
    }

    public void refreshKalender() {
        loadKalender();
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public boolean afterToday(Calendar c1, Calendar c2) {

        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  Aufgaben
     */
    public void loadAufgaben() {
        
        aktiveAufgaben.clear();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(today);

        AufgabenObj[] aufgaben = KalenderRegistry.getAufgaben();

        
        if (aufgaben == null) {
            Log.logger.info("Keine Aufgaben in der Datenbank");
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Keine Aufgaben");
            DefaultMutableTreeNode show = new DefaultMutableTreeNode("Keine Aufgaben vorhanden");
            top.add(show);
            tree_aufgaben.setModel(new DefaultTreeModel(top));
            tree_aufgaben.revalidate();
            return;
        }
        
        System.out.println("Lade Aufgaben " + aufgaben.length);

        for (int i = 0; i < aufgaben.length; i++) {
            AufgabenObj aufgabe = aufgaben[i];
            calendar2.setTime(aufgabe.getStart());

            boolean same = sameDay(calendar, calendar2);

            if (same) {
                aktiveAufgaben.add(aufgabe);
            } else {
                boolean after = this.afterToday(calendar, calendar2);
                if (after == false) {
                    calendar2.setTime(aufgabe.getEnde());
                    same = sameDay(calendar, calendar2);
                    if (same) {
                        aktiveAufgaben.add(aufgabe);
                    } else {
                        after = afterToday(calendar, calendar2);
                        if (after) {
                            aktiveAufgaben.add(aufgabe);
                        }
                    }
                }
            }
        }// End for
        
        if (aktiveAufgaben.isEmpty()) {
            Log.logger.info("Keine aktiven Aufgaben vorhanden");
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Keine Aufgaben");
            DefaultMutableTreeNode show = new DefaultMutableTreeNode("Keine aktiven Aufgaben vorhanden");
            top.add(show);
            tree_aufgaben.setModel(new DefaultTreeModel(top));
            tree_aufgaben.revalidate();
            return;
        }
        System.out.println("Aktive Aufgaben: " + aktiveAufgaben.size());

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("");
//        DefaultMutableTreeNode[] categories = new DefaultMutableTreeNode[Tags.tags.length];
//
//        for (int i = 0; i < Tags.tags.length; i++) {
//            categories[i] = new DefaultMutableTreeNode(Tags.tags[i].getName());
//        }

//        ArrayList usedTags = new ArrayList();

        for (int i = 0; i < aktiveAufgaben.toArray().length; i++) {
            AufgabenObj aufgabe = (AufgabenObj) aktiveAufgaben.toArray()[i];

            DefaultMutableTreeNode auf = new DefaultMutableTreeNode(BasicRegex.shortenString(30, aufgabe.toString(), true));
            top.add(auf);
        }
               
        tree_aufgaben.setModel(new DefaultTreeModel(top));        
        tree_aufgaben.revalidate();
    }

    public void loadGeburtstage(Date date) {
        KundenObj[] kunden = KalenderRegistry.getKundenGeburtstag(date);
        BenutzerObj[] ben = KalenderRegistry.getBenutzerGeburtstage(date);
        FirmenObj[] firm = KalenderRegistry.getFirmenGeburtstag(date);

        if (kunden == null && ben == null && firm == null) {
//            Log.logger.info("Keine Geburtstage in der Datenbank");
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Keine Geburtstage");
            DefaultMutableTreeNode show = new DefaultMutableTreeNode("Keine Geburtstage heute");
            top.add(show);
            tree_geburtstage.setModel(new DefaultTreeModel(top));
            tree_geburtstage.revalidate();
            return;
        }

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("");


        if (kunden != null) {
            for (int i = 0; i < kunden.length; i++) {
                DefaultMutableTreeNode auf = new DefaultMutableTreeNode(kunden[i].toString());
                top.add(auf);
            }
        }

        if (firm != null) {
            for (int i = 0; i < firm.length; i++) {
                DefaultMutableTreeNode auf = new DefaultMutableTreeNode(firm[i].toString());
                top.add(auf);
            }
        }

        if (ben != null) {
            for (int i = 0; i < ben.length; i++) {
                DefaultMutableTreeNode auf = new DefaultMutableTreeNode(ben[i].toString());
                top.add(auf);
            }
        }
        
        tree_geburtstage.setModel(new DefaultTreeModel(top)); 
        tree_geburtstage.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpAnsicht = new javax.swing.ButtonGroup();
        toolbarKalender = new javax.swing.JToolBar();
        btnNeuertermin = new javax.swing.JButton();
        btnNeuaufgabe = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnTagesansicht = new javax.swing.JToggleButton();
        btnWochenansicht = new javax.swing.JToggleButton();
        splitKalender = new javax.swing.JSplitPane();
        panelKalenderHolder = new javax.swing.JPanel();
        panelKalenderRight = new javax.swing.JPanel();
        splitKalenderRight = new javax.swing.JSplitPane();
        jxpanel_datum = new org.jdesktop.swingx.JXTitledPanel();
        jPanel1 = new javax.swing.JPanel();
        jxpanel_aufgaben = new org.jdesktop.swingx.JXTitledPanel();
        scroll_aufgaben = new javax.swing.JScrollPane();
        tree_aufgaben = new javax.swing.JTree();
        jxpanel_geburtstage = new org.jdesktop.swingx.JXTitledPanel();
        scroll_aufgaben1 = new javax.swing.JScrollPane();
        tree_geburtstage = new javax.swing.JTree();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        toolbarKalender.setFloatable(false);
        toolbarKalender.setRollover(true);
        toolbarKalender.setName("toolbarKalender"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelKalenderHolder.class);
        btnNeuertermin.setIcon(resourceMap.getIcon("btnNeuertermin.icon")); // NOI18N
        btnNeuertermin.setText(resourceMap.getString("btnNeuertermin.text")); // NOI18N
        btnNeuertermin.setFocusable(false);
        btnNeuertermin.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuertermin.setName("btnNeuertermin"); // NOI18N
        btnNeuertermin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuertermin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuerterminActionPerformed(evt);
            }
        });
        toolbarKalender.add(btnNeuertermin);

        btnNeuaufgabe.setIcon(resourceMap.getIcon("btnNeuaufgabe.icon")); // NOI18N
        btnNeuaufgabe.setText(resourceMap.getString("btnNeuaufgabe.text")); // NOI18N
        btnNeuaufgabe.setFocusable(false);
        btnNeuaufgabe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNeuaufgabe.setName("btnNeuaufgabe"); // NOI18N
        btnNeuaufgabe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNeuaufgabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNeuaufgabeActionPerformed(evt);
            }
        });
        toolbarKalender.add(btnNeuaufgabe);

        jSeparator2.setName("jSeparator2"); // NOI18N
        toolbarKalender.add(jSeparator2);

        jSeparator3.setName("jSeparator3"); // NOI18N
        toolbarKalender.add(jSeparator3);

        grpAnsicht.add(btnTagesansicht);
        btnTagesansicht.setIcon(resourceMap.getIcon("btnTagesansicht.icon")); // NOI18N
        btnTagesansicht.setText(resourceMap.getString("btnTagesansicht.text")); // NOI18N
        btnTagesansicht.setFocusable(false);
        btnTagesansicht.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTagesansicht.setName("btnTagesansicht"); // NOI18N
        btnTagesansicht.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTagesansicht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagesansichtActionPerformed(evt);
            }
        });
        toolbarKalender.add(btnTagesansicht);

        grpAnsicht.add(btnWochenansicht);
        btnWochenansicht.setIcon(resourceMap.getIcon("btnWochenansicht.icon")); // NOI18N
        btnWochenansicht.setSelected(true);
        btnWochenansicht.setText(resourceMap.getString("btnWochenansicht.text")); // NOI18N
        btnWochenansicht.setFocusable(false);
        btnWochenansicht.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnWochenansicht.setName("btnWochenansicht"); // NOI18N
        btnWochenansicht.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnWochenansicht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWochenansichtActionPerformed(evt);
            }
        });
        toolbarKalender.add(btnWochenansicht);

        add(toolbarKalender, java.awt.BorderLayout.PAGE_START);

        splitKalender.setDividerLocation(700);
        splitKalender.setName("splitKalender"); // NOI18N

        panelKalenderHolder.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelKalenderHolder.setMinimumSize(new java.awt.Dimension(300, 200));
        panelKalenderHolder.setName("panelKalenderHolder"); // NOI18N
        panelKalenderHolder.setPreferredSize(new java.awt.Dimension(1000, 600));
        panelKalenderHolder.setLayout(new java.awt.BorderLayout());
        splitKalender.setLeftComponent(panelKalenderHolder);

        panelKalenderRight.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelKalenderRight.setName("panelKalenderRight"); // NOI18N
        panelKalenderRight.setLayout(new java.awt.BorderLayout());

        splitKalenderRight.setDividerLocation(150);
        splitKalenderRight.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitKalenderRight.setName("splitKalenderRight"); // NOI18N

        jxpanel_datum.setOpaque(true);
        jxpanel_datum.setTitle(resourceMap.getString("jxpanel_datum.title")); // NOI18N
        jxpanel_datum.setBorder(null);
        jxpanel_datum.setMaximumSize(new java.awt.Dimension(300, 300));
        jxpanel_datum.setMinimumSize(new java.awt.Dimension(100, 150));
        jxpanel_datum.setName("jxpanel_datum"); // NOI18N
        jxpanel_datum.getContentContainer().setLayout(new java.awt.BorderLayout());
        splitKalenderRight.setLeftComponent(jxpanel_datum);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jxpanel_aufgaben.setTitle(resourceMap.getString("jxpanel_aufgaben.title")); // NOI18N
        jxpanel_aufgaben.setBorder(null);
        jxpanel_aufgaben.setName("jxpanel_aufgaben"); // NOI18N

        scroll_aufgaben.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_aufgaben.setViewportBorder(null);
        scroll_aufgaben.setName("scroll_aufgaben"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        tree_aufgaben.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree_aufgaben.setName("tree_aufgaben"); // NOI18N
        tree_aufgaben.setRootVisible(false);
        tree_aufgaben.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                tree_aufgabenValueChanged(evt);
            }
        });
        scroll_aufgaben.setViewportView(tree_aufgaben);

        javax.swing.GroupLayout jxpanel_aufgabenLayout = new javax.swing.GroupLayout(jxpanel_aufgaben.getContentContainer());
        jxpanel_aufgaben.getContentContainer().setLayout(jxpanel_aufgabenLayout);
        jxpanel_aufgabenLayout.setHorizontalGroup(
            jxpanel_aufgabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_aufgaben, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jxpanel_aufgabenLayout.setVerticalGroup(
            jxpanel_aufgabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_aufgaben, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jPanel1.add(jxpanel_aufgaben);

        jxpanel_geburtstage.setTitle(resourceMap.getString("jxpanel_geburtstage.title")); // NOI18N
        jxpanel_geburtstage.setBorder(null);
        jxpanel_geburtstage.setName("jxpanel_geburtstage"); // NOI18N

        scroll_aufgaben1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_aufgaben1.setViewportBorder(null);
        scroll_aufgaben1.setName("scroll_aufgaben1"); // NOI18N

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        tree_geburtstage.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree_geburtstage.setName("tree_geburtstage"); // NOI18N
        tree_geburtstage.setRootVisible(false);
        tree_geburtstage.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                tree_geburtstageValueChanged(evt);
            }
        });
        scroll_aufgaben1.setViewportView(tree_geburtstage);

        javax.swing.GroupLayout jxpanel_geburtstageLayout = new javax.swing.GroupLayout(jxpanel_geburtstage.getContentContainer());
        jxpanel_geburtstage.getContentContainer().setLayout(jxpanel_geburtstageLayout);
        jxpanel_geburtstageLayout.setHorizontalGroup(
            jxpanel_geburtstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
            .addComponent(scroll_aufgaben1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );
        jxpanel_geburtstageLayout.setVerticalGroup(
            jxpanel_geburtstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
            .addComponent(scroll_aufgaben1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jPanel1.add(jxpanel_geburtstage);

        splitKalenderRight.setRightComponent(jPanel1);

        panelKalenderRight.add(splitKalenderRight, java.awt.BorderLayout.CENTER);

        splitKalender.setRightComponent(panelKalenderRight);

        add(splitKalender, java.awt.BorderLayout.CENTER);
        splitKalender.setDividerLocation(0.8);
        splitKalender.repaint();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNeuerterminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuerterminActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neuTerminBox = new NeuerTermin(mainFrame, false, currentDate);
        neuTerminBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(neuTerminBox);
    }//GEN-LAST:event_btnNeuerterminActionPerformed

    private void btnNeuaufgabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNeuaufgabeActionPerformed
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        neuAufgabeBox = new NeueAufgabe(mainFrame, false);
        neuAufgabeBox.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(neuAufgabeBox);
    }//GEN-LAST:event_btnNeuaufgabeActionPerformed

    /**
     * 
     * @param evt
     */
    private void tree_aufgabenValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_tree_aufgabenValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree_aufgaben.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        if (aktiveAufgaben.isEmpty()) {
            return;
        }

        Object nodeInfo = node.getUserObject();

        if (node.isLeaf()) {
            AufgabenObj aufgabe = (AufgabenObj) nodeInfo;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            neuAufgabeBox = new NeueAufgabe(mainFrame, false, aufgabe);
            neuAufgabeBox.setLocationRelativeTo(mainFrame);
            CRM.getApplication().show(neuAufgabeBox);
        }
    }//GEN-LAST:event_tree_aufgabenValueChanged

    private void btnTagesansichtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagesansichtActionPerformed
        view = TAGESANSICHT;
        loadKalender();
    }//GEN-LAST:event_btnTagesansichtActionPerformed

    private void btnWochenansichtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWochenansichtActionPerformed
        view = WOCHENANSICHT;
        loadKalender();
    }//GEN-LAST:event_btnWochenansichtActionPerformed

    private void tree_geburtstageValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_tree_geburtstageValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tree_geburtstageValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnNeuaufgabe;
    public javax.swing.JButton btnNeuertermin;
    public javax.swing.JToggleButton btnTagesansicht;
    public javax.swing.JToggleButton btnWochenansicht;
    public javax.swing.ButtonGroup grpAnsicht;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JToolBar.Separator jSeparator3;
    public org.jdesktop.swingx.JXTitledPanel jxpanel_aufgaben;
    public org.jdesktop.swingx.JXTitledPanel jxpanel_datum;
    public org.jdesktop.swingx.JXTitledPanel jxpanel_geburtstage;
    public javax.swing.JPanel panelKalenderHolder;
    public javax.swing.JPanel panelKalenderRight;
    public javax.swing.JScrollPane scroll_aufgaben;
    public javax.swing.JScrollPane scroll_aufgaben1;
    public javax.swing.JSplitPane splitKalender;
    public javax.swing.JSplitPane splitKalenderRight;
    public javax.swing.JToolBar toolbarKalender;
    public javax.swing.JTree tree_aufgaben;
    public javax.swing.JTree tree_geburtstage;
    // End of variables declaration//GEN-END:variables
    private JDialog neuTerminBox;
    private JDialog neuAufgabeBox;
}
