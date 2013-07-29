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
 * BenutzerTabelleSorter.java
 *
 * Created on Jul 22, 2010, 11:38:22 AM
 */

package de.maklerpoint.office.Gui.Benutzer;

import de.maklerpoint.office.Gui.Benutzer.PanelBenutzerUebersicht.ColumnHead;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerTabelleSorter extends javax.swing.JDialog {

    private Object[] activeItems;
    private Object[] inactiveItems;
    private boolean changed = false;

    private Preferences prefs = Preferences.userRoot().node(PanelBenutzerUebersicht.class.getName());;
    private PanelBenutzerUebersicht panel;

    /** Creates new form BenutzerTabelleSorter */
    public BenutzerTabelleSorter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.dispose();
        initComponents();
    }


     public BenutzerTabelleSorter(java.awt.Frame parent, boolean modal, Object[] activeItems,
            Object[] inactiveItems, PanelBenutzerUebersicht panel) {
        super(parent, modal);
        this.activeItems = activeItems;
        this.inactiveItems = inactiveItems;
        this.panel = panel;
        initComponents();
    }

     public void loadActive(){
        DefaultListModel activeModel = new DefaultListModel();
        for(int i = 0; i < activeItems.length; i++)
        {
            activeModel.add(i, activeItems[i]);
        }
        list_active.setModel(activeModel);
        list_active.revalidate();
    }

    /**
     *
     */

    public void loadInactive(){
        DefaultListModel inactiveModel = new DefaultListModel();
        for(int i = 0; i < inactiveItems.length; i++)
        {
            inactiveModel.add(i, inactiveItems[i]);
        }
        list_inactive.setModel(inactiveModel);
        list_inactive.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        list_inactive = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_active = new javax.swing.JList();
        btnUp = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(BenutzerTabelleSorter.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        list_inactive.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_inactive.setName("list_inactive"); // NOI18N
        jScrollPane2.setViewportView(list_inactive);
        this.loadInactive();

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_active.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_active.setName("list_active"); // NOI18N
        jScrollPane1.setViewportView(list_active);
        this.loadActive();

        btnUp.setIcon(resourceMap.getIcon("btnUp.icon")); // NOI18N
        btnUp.setName("btnUp"); // NOI18N
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        btnLeft.setIcon(resourceMap.getIcon("btnLeft.icon")); // NOI18N
        btnLeft.setName("btnLeft"); // NOI18N
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setIcon(resourceMap.getIcon("btnRight.icon")); // NOI18N
        btnRight.setName("btnRight"); // NOI18N
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnDown.setIcon(resourceMap.getIcon("btnDown.icon")); // NOI18N
        btnDown.setName("btnDown"); // NOI18N
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnUp)
                                    .addComponent(btnLeft)
                                    .addComponent(btnRight)
                                    .addComponent(btnDown))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDown)
                                .addGap(72, 72, 72))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();

        Object[] selected = list_active.getSelectedValues();

        if(selected == null) {
            return;
        }

        if(selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if( index != 0 ) {
                Object temp = active.remove(index);
                active.add(index -1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index -1);
                list_active.revalidate();
            }
        }
}//GEN-LAST:event_btnUpActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        Object[] selected = list_inactive.getSelectedValues();

        if(selected == null)
            return;

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for(int i = 0; i < selected.length; i++){
            active.addElement(selected[i]);
            inactive.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
}//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        Object[] selected = list_active.getSelectedValues();

        if(selected == null)
            return;

        DefaultListModel active = (DefaultListModel) list_active.getModel();
        DefaultListModel inactive = (DefaultListModel) list_inactive.getModel();


        for(int i = 0; i < selected.length; i++){
            inactive.addElement(selected[i]);
            active.removeElement(selected[i]);
        }

        list_inactive.setModel(inactive);
        list_active.setModel(active);

        list_inactive.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_active.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list_inactive.revalidate();
        list_active.revalidate();
}//GEN-LAST:event_btnRightActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();
        Object[] selected = list_active.getSelectedValues();

        if(selected == null) {
            return;
        }

        if(selected.length == 1) {
            int index = list_active.getSelectedIndex();
            if( index < active.size() -1 ) {
                Object temp = active.remove(index);
                active.add(index +1, temp);
                list_active.setModel(active);
                list_active.setSelectedIndex(index +1);
                list_active.revalidate();
            }
        }
    }//GEN-LAST:event_btnDownActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        loadInactive();
        loadActive();
        this.dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        DefaultListModel active = (DefaultListModel) list_active.getModel();

        StringBuilder ids = new StringBuilder();

        for(int i = 0; i < active.getSize(); i++) {
            Object selected = active.get(i);

            ColumnHead column = (ColumnHead) selected;

            ids.append(column.getId());
            if(i != active.getSize() -1)
                ids.append(",");
        }
        
        prefs.put("benutzerTableColumns", ids.toString());

        panel.loadTable();        

        this.dispose();
}//GEN-LAST:event_btnSaveActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BenutzerTabelleSorter dialog = new BenutzerTabelleSorter(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList list_active;
    private javax.swing.JList list_inactive;
    // End of variables declaration//GEN-END:variables

}
