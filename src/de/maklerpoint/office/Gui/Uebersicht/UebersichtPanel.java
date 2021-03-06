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
 * UebersichtPanel.java
 *
 * Created on Jul 16, 2010, 7:20:05 PM
 */

package de.maklerpoint.office.Gui.Uebersicht;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Statistik.KundenStatistik;
import de.maklerpoint.office.Statistik.VertragStatistik;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.UebersichtModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
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
public class UebersichtPanel extends javax.swing.JPanel {

    /** Creates new form UebersichtPanel */
    public UebersichtPanel() {
        initComponents();
    }

    private class ExportPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton csv = new JCommandMenuButton("als CSV Datei (.csv)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/csv.png"));
            csv.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(csv);

            JCommandMenuButton xls = new JCommandMenuButton("als Excel Datei (.xls)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/excel7.png"));
            xls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(xls);

            JCommandMenuButton xlsx = new JCommandMenuButton("als Excel 2007 Datei (.xlsx)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/excel7.png"));
            xlsx.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(xlsx);

            JCommandMenuButton pdf = new JCommandMenuButton("als PDF Datei (.pdf)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/pdf.png"));
            pdf.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });

            popupMenu.addMenuButton(pdf);

            JCommandMenuButton txt = new JCommandMenuButton("als Text Datei (.txt)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/txt.gif"));
            txt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });

            popupMenu.addMenuButton(txt);

            JCommandMenuButton doc = new JCommandMenuButton("als Word Dokument (.doc)",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/word7.png"));
            doc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });

            popupMenu.addMenuButton(doc);

            return popupMenu;
        }
     }


     /**
      *
      * @param resource
      * @return
      */

     public static ResizableIcon getResizableIconFromResource(String resource) {
         return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(16,16));
     }

     public void addExportCommandButton() {
         JCommandButton exportButton = new JCommandButton("Export", getResizableIconFromResource("de/acyrance/CRM/Gui/resources/export.png"));
         exportButton.setExtraText("Die Übersicht exportieren");
         exportButton.setPopupCallback(new ExportPopupCallback());
         exportButton.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
         exportButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
         exportButton.setFlat(true);

         this.toolbarUebersicht.add(exportButton, 10);
     }


     private void loadKundenTable() {
        try {
            Object[][] data = new Object[20][4];
            String[] columnNames = new String[]{"Bereich", "Bezeichnung", "Eigene", "Insgesamt"};
            
            float pkeigencount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.NORMAL);
            float pkcount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), Status.NORMAL);
            
            float fkeigencount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.NORMAL);
            float fkcount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), Status.NORMAL);
            
            int i = 0;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl aktiver Kunden";
            data[i][2] = pkeigencount + fkeigencount;
            data[i][3] = pkcount + fkcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl aktiver Privatkunden";
            data[i][2] = pkeigencount;
            data[i][3] = pkcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl aktiver Geschäftskunden";
            data[i][2] = fkeigencount;
            data[i][3] = fkcount;
            
            i++;
            
            
            float pkaveigencount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.ARCHIVED);
            float pkavcount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), Status.ARCHIVED);
            
            float fkaveigencount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.ARCHIVED);
            float fkavcount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), Status.ARCHIVED);
            

            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl archivierter Kunden";
            data[i][2] = pkaveigencount + fkaveigencount;
            data[i][3] = pkavcount + fkavcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl archivierter Privatkunden";
            data[i][2] = pkaveigencount;
            data[i][3] = pkavcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl archivierter Geschäftskunden";
            data[i][2] = fkaveigencount;
            data[i][3] = fkavcount;
            
            i++;
            
            float pkdeleigencount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.DELETED);
            float pkdelcount = KundenStatistik.getAnzahlPrivatKunden(DatabaseConnection.open(), Status.DELETED);
            
            float fkdeleigencount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.DELETED);
            float fkdelcount = KundenStatistik.getAnzahlFirmenKunden(DatabaseConnection.open(), Status.DELETED);
            

            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl gelöschter Kunden";
            data[i][2] = pkdeleigencount + fkdeleigencount;
            data[i][3] = pkdelcount + fkdelcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl gelöschter Privatkunden";
            data[i][2] = pkdeleigencount;
            data[i][3] = pkdelcount;
            
            i++;
            
            data[i][0] = "Kunden";
            data[i][1] = "Gesamtzahl gelöschter Geschäftskunden";
            data[i][2] = fkdeleigencount;
            data[i][3] = fkdelcount;
            
            i++;
            
            float vtrAllcount = VertragStatistik.getAnzahlVertraege(DatabaseConnection.open(), Status.NORMAL);
            float vtrEigcount = VertragStatistik.getAnzahlBenutzerVertraege(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), Status.NORMAL);
            
            float vtravAllcount = VertragStatistik.getAnzahlVertraege(DatabaseConnection.open(), Status.ARCHIVED);
            float vtravEigcount = VertragStatistik.getAnzahlBenutzerVertraege(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), Status.ARCHIVED);
            
            float vtrdelAllcount = VertragStatistik.getAnzahlVertraege(DatabaseConnection.open(), Status.DELETED);
            float vtrdelEigcount = VertragStatistik.getAnzahlBenutzerVertraege(DatabaseConnection.open(),
                        BasicRegistry.currentUser.getId(), Status.DELETED);
            
            data[i][0] = "Verträge";
            data[i][1] = "Aktive Verträge";
            data[i][2] = vtrEigcount;
            data[i][3] = vtrAllcount;            
            i++;
            
            data[i][0] = "Verträge";
            data[i][1] = "Archivierte Verträge";
            data[i][2] = vtravEigcount;
            data[i][3] = vtravAllcount;            
            i++;
            
            data[i][0] = "Verträge";
            data[i][1] = "Gelöschte Verträge";
            data[i][2] = vtrdelAllcount;
            data[i][3] = vtrdelEigcount;            
            i++;
                        
            data[i][0] = "Verträge";
            data[i][1] = "Verträge pro Kunde (Durchschnitt)";
            data[i][2] = vtrEigcount / (pkeigencount + fkeigencount);
            data[i][3] = vtrAllcount / (pkcount + fkcount);
            i++;
            
            
            TableModel model = new UebersichtModel(data, columnNames);
            this.table_kunden.setModel(model);
            table_kunden.setColumnControlVisible(true);
            table_kunden.setAutoCreateRowSorter(true);
            table_kunden.tableChanged(new TableModelEvent(table_kunden.getModel()));
            JTableHeader header = table_kunden.getTableHeader();
            header.repaint();
            
            table_kunden.revalidate();            

            if(btnEigene.isSelected()) {
                table_kunden.removeColumn(table_kunden.getColumnModel().getColumn(3));
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
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

        btnGrpUebersicht = new javax.swing.ButtonGroup();
        toolbarUebersicht = new javax.swing.JToolBar();
        btnEigene = new javax.swing.JToggleButton();
        btnVertrieb = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_kunden = new org.jdesktop.swingx.JXTable();

        setName("Form"); // NOI18N

        toolbarUebersicht.setFloatable(false);
        toolbarUebersicht.setRollover(true);
        toolbarUebersicht.setName("toolbarUebersicht"); // NOI18N

        btnGrpUebersicht.add(btnEigene);
        btnEigene.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(UebersichtPanel.class);
        btnEigene.setText(resourceMap.getString("btnEigene.text")); // NOI18N
        btnEigene.setFocusable(false);
        btnEigene.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEigene.setMaximumSize(new java.awt.Dimension(27, 27));
        btnEigene.setName("btnEigene"); // NOI18N
        btnEigene.setPreferredSize(new java.awt.Dimension(100, 27));
        btnEigene.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEigene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEigeneActionPerformed(evt);
            }
        });
        toolbarUebersicht.add(btnEigene);

        btnGrpUebersicht.add(btnVertrieb);
        btnVertrieb.setText(resourceMap.getString("btnVertrieb.text")); // NOI18N
        btnVertrieb.setFocusable(false);
        btnVertrieb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVertrieb.setName("btnVertrieb"); // NOI18N
        btnVertrieb.setPreferredSize(new java.awt.Dimension(100, 27));
        btnVertrieb.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVertrieb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVertriebActionPerformed(evt);
            }
        });
        toolbarUebersicht.add(btnVertrieb);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        table_kunden.setName("table_kunden"); // NOI18N
        loadKundenTable();
        jScrollPane1.setViewportView(table_kunden);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarUebersicht, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbarUebersicht, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEigeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEigeneActionPerformed
       loadKundenTable();
    }//GEN-LAST:event_btnEigeneActionPerformed

    private void btnVertriebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVertriebActionPerformed
        loadKundenTable();
    }//GEN-LAST:event_btnVertriebActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnEigene;
    private javax.swing.ButtonGroup btnGrpUebersicht;
    private javax.swing.JToggleButton btnVertrieb;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTable table_kunden;
    private javax.swing.JToolBar toolbarUebersicht;
    // End of variables declaration//GEN-END:variables

}
