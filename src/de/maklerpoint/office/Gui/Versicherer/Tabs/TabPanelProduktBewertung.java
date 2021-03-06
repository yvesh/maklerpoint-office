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
 * TabPanelProduktBewertung.java
 *
 * Created on 17.09.2010, 12:49:10
 */
package de.maklerpoint.office.Gui.Versicherer.Tabs;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Textbausteine.TextbausteinObj;
import de.maklerpoint.office.Textbausteine.Tools.TextbausteineSQLMethods;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author yves
 */
public class TabPanelProduktBewertung extends iTabsAbstractProdukt {

    private ProduktObj prod;
    private VersichererObj vers;
    private TextbausteinObj currentTb = null;
    private boolean hasChanged = false;

    /** Creates new form TabPanelProduktBewertung */
    public TabPanelProduktBewertung() {
        initComponents();
        text_grund.setBackground(new Color(237, 236, 235));
    }

    public String getTabName() {
        return "Bewertung";
    }

    public void load(ProduktObj prod) {
        saveBaustein(); // Saving Baustein on Change
        this.prod = prod;
        loadTable();
    }

    public void load(ProduktObj prod, VersichererObj vers) {
        saveBaustein(); // Saving Baustein on Change
        this.prod = prod;
        this.vers = vers;
        loadTable();
    }

    public void disableElements() {
        this.text_baustein.setEnabled(false);
    }

    public void enableElements() {
        this.text_baustein.setEnabled(true);
    }

    private void updateBaustein() {
        hasChanged = true;
        this.currentTb.setBeschreibung(this.text_baustein.getText());
    }

    private void saveBaustein() {
        if (!hasChanged) {
            return;
        }

        if (currentTb == null) {
            return;
        }

        if (Log.databaselogger.isDebugEnabled()) {
            Log.databaselogger.debug("Aktuallisiere Textbaustein für Produkt " + prod.getBezeichnung());
        }

        try {
            boolean success = TextbausteineSQLMethods.updatetextbausteine(
                    DatabaseConnection.open(), currentTb);
            if (!success) {
                Log.databaselogger.fatal("Konnte Produktbewertung nicht aktualisieren");
                ShowException.showException("Datenbankfehler: Die Produktbewertung konnte nicht aktualisiert werden.",
                        ExceptionDialogGui.LEVEL_WARNING,
                        "Schwerwiegend: Konnte die Produktbewertung nicht aktualisieren");
            }

            hasChanged = false;
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte Produktbewertung nicht laden", e);
            ShowException.showException("Datenbankfehler: Die Produktbewertung konnte nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte die Produktbewertung nicht laden");
        }
    }

    /**
     * Lade Tabelle
     */
    private void loadTable() {
        this.text_baustein.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent de) {
                updateBaustein();
            }

            public void removeUpdate(DocumentEvent de) {
                updateBaustein();
            }

            public void changedUpdate(DocumentEvent de) {
//                saveBaustein();
            }
        });

        try {
            TextbausteinObj tb = TextbausteineSQLMethods.getProduktTextbaustein(
                    DatabaseConnection.open(), prod.getId());
            if (tb != null) {
                currentTb = tb;
                this.text_baustein.setText(tb.getBeschreibung());
                hasChanged = false;
            } else {
                tb = new TextbausteinObj();
                tb.setGroup(3); // Hardcoded hmm TODO think about it
                tb.setBenutzerId(BasicRegistry.currentUser.getId());
                tb.setBeschreibung("");
                tb.setName("Produktbewertung " + prod.getBezeichnung());
                tb.setProdId(prod.getId());
                tb.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
                tb.setModified(new java.sql.Timestamp(System.currentTimeMillis()));

                int id = TextbausteineSQLMethods.insertIntotextbausteine(DatabaseConnection.open(), tb);
                tb.setId(id);
                currentTb = tb;
                this.text_baustein.setText("");
                hasChanged = false;
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte Produktbewertung nicht laden", e);
            ShowException.showException("Datenbankfehler: Die Produktbewertung konnte nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte die Produktbewertung nicht laden");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        text_baustein = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_grund = new javax.swing.JTextArea();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TabPanelProduktBewertung.class);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane1.border.title"))); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        text_baustein.setColumns(20);
        text_baustein.setRows(5);
        text_baustein.setName("text_baustein"); // NOI18N
        text_baustein.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_bausteinFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(text_baustein);

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setViewportBorder(null);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        text_grund.setColumns(20);
        text_grund.setEditable(false);
        text_grund.setLineWrap(true);
        text_grund.setRows(5);
        text_grund.setText(resourceMap.getString("text_grund.text")); // NOI18N
        text_grund.setWrapStyleWord(true);
        text_grund.setBorder(null);
        text_grund.setName("text_grund"); // NOI18N
        jScrollPane2.setViewportView(text_grund);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void text_bausteinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_bausteinFocusLost
        saveBaustein(); // Saving Baustein on Change
    }//GEN-LAST:event_text_bausteinFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea text_baustein;
    public javax.swing.JTextArea text_grund;
    // End of variables declaration//GEN-END:variables
   
}