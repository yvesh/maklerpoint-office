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
 * AdressAuswahlDialog.java
 *
 * Created on 03.09.2010, 13:22:13
 */

package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Table.TermineModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author yves
 */
public class AdressAuswahlDialog extends javax.swing.JDialog {

    private String _kennung;
    private String _title;
    private ZusatzadressenObj[] adressen;
    private ZusatzadressenObj returnAdr = null;

    private String[] Columns = new String[]{"Hidden", "Name", "Adresse Zusatz", "Strasse", "PLZ", "Ort"};
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public AdressAuswahlDialog(java.awt.Frame parent, boolean modal, KundenObj kunde, ZusatzadressenObj[] adressen) {
        super(parent, modal);
        this.adressen = adressen;
        this._kennung = kunde.getKundenNr();
        this._title = kunde.getVorname() + " " + kunde.getNachname() + " [" + kunde.getKundenNr() + "]";
        initComponents();
    }

    public AdressAuswahlDialog(java.awt.Frame parent, boolean modal, FirmenObj firma, ZusatzadressenObj[] adressen) {
        super(parent, modal);
        this.adressen = adressen;
        this._kennung = firma.getKundenNr();
        this._title = firma.getFirmenName() + " [" + firma.getKundenNr() + "]";
        initComponents();
    }

    public AdressAuswahlDialog(java.awt.Frame parent, boolean modal, ZusatzadressenObj[] adressen) {
        super(parent, modal);
        this.adressen = adressen;
        this._kennung = adressen[0].getKundenKennung();
        this._title = adressen[0].getKundenKennung();
        initComponents();
    }

    private void setupTitle(){
        this.setTitle("Adressauswahl für " + _title);
    }

    private void doClose() {
        returnAdr = null;
        setVisible(false);
        dispose();
    }

    /**
     * 
     * @param za
     */

     private void doClose(ZusatzadressenObj za) {
        returnAdr = za;
        setVisible(false);
        dispose();
    }

    /**
     * 
     */

    private void loadTable() {
         if(this.adressen == null) {
             this.dispose();
             return;
         }

         Object[][] data = null;
         data = new Object[adressen.length][6];

         for(int i = 0; i < adressen.length; i++)
         {
            data[i][0] = adressen[i];
            data[i][1] = adressen[i].getName();
            data[i][2] = adressen[i].getNameZusatz();
            data[i][3] = adressen[i].getStreet();
            data[i][4] = adressen[i].getPlz();
            data[i][5] = adressen[i].getOrt();
         }

         setTable(data, Columns);
     }

    /**
     * 
     * @param data
     * @param columns
     */

     private void setTable(Object[][] data, String[] columns) {

        this.table_zusatzadressen.setModel(new TermineModel(data, columns));

        table_zusatzadressen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_zusatzadressen.setColumnSelectionAllowed(false);
        table_zusatzadressen.setCellSelectionEnabled(false);
        table_zusatzadressen.setRowSelectionAllowed(true);
        table_zusatzadressen.setAutoCreateRowSorter(true);

        table_zusatzadressen.setFillsViewportHeight(true);
        table_zusatzadressen.removeColumn(table_zusatzadressen.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_zusatzadressen.addMouseListener(popupListener);
        table_zusatzadressen.setColumnControlVisible(true);

        JTableHeader header = table_zusatzadressen.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_zusatzadressen.tableChanged(new TableModelEvent(table_zusatzadressen.getModel()));
        table_zusatzadressen.revalidate();
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
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
    }

    /**
     * 
     * @return
     */

    public ZusatzadressenObj getReturnStatus() {
        return returnAdr;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_protokolle = new javax.swing.JScrollPane();
        table_zusatzadressen = new org.jdesktop.swingx.JXTable();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(AdressAuswahlDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_zusatzadressen.setModel(new javax.swing.table.DefaultTableModel(
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
        table_zusatzadressen.setColumnControlVisible(true);
        table_zusatzadressen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_zusatzadressen.setName("table_zusatzadressen"); // NOI18N
        scroll_protokolle.setViewportView(table_zusatzadressen);

        cancelButton.setMnemonic('A');
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setMnemonic('O');
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(353, Short.MAX_VALUE)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose();
}//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        int row = table_zusatzadressen.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(null, "Keine Adresse ausgewählt.");
            return;
        }

        ZusatzadressenObj za = (ZusatzadressenObj) this.table_zusatzadressen.getModel().getValueAt(row, 0);

        if(za == null)
            return;

        doClose(za);
}//GEN-LAST:event_okButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdressAuswahlDialog dialog = new AdressAuswahlDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_zusatzadressen;
    // End of variables declaration//GEN-END:variables

}
