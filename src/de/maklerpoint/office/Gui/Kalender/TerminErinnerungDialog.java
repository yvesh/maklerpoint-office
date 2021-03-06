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
 * TerminErinnerungDialog.java
 *
 * Created on Jul 13, 2010, 10:16:26 AM
 */

package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Leftpane.panelKarte;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kalender.Termine.Tools.TermineSQLMethods;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Scheduler.SchedulerTask;
import java.awt.Font;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TerminErinnerungDialog extends javax.swing.JDialog {

    private TerminObj termin = null;

    /** Creates new form TerminErinnerungDialog */
    public TerminErinnerungDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.dispose();
        initComponents();        
    }

    public TerminErinnerungDialog(java.awt.Frame parent, boolean modal, TerminObj termin) {
        super(parent, modal);
        this.termin = termin;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btn_ok = new javax.swing.JButton();
        combo_wiederholung = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_beschreibung = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        editor_ort = new javax.swing.JEditorPane();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        label_kunde = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        label_zeitraum = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(TerminErinnerungDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        btn_ok.setMnemonic('O');
        btn_ok.setText(resourceMap.getString("btn_ok.text")); // NOI18N
        btn_ok.setName("btn_ok"); // NOI18N
        btn_ok.setPreferredSize(new java.awt.Dimension(100, 27));
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        combo_wiederholung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nicht wiederholen", "In 1 Minute wiederholen", "In 5 Minuten wiederholen", "In 30 Minuten wiederholen", "In 1 Stunde wiederholen", "In 2 Stunden wiederholen", "In 1 Tag wiederholen", "In 1 Woche wiederholen", "--------------------------------------", "1 Stunde vor dem Termin", "2 Stunden vor dem Termin", "1 Tag vor dem Termin" }));
        combo_wiederholung.setName("combo_wiederholung"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        area_beschreibung.setColumns(20);
        area_beschreibung.setEditable(false);
        area_beschreibung.setRows(5);
        area_beschreibung.setWrapStyleWord(true);
        area_beschreibung.setName("area_beschreibung"); // NOI18N
        jScrollPane1.setViewportView(area_beschreibung);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        editor_ort.setContentType(resourceMap.getString("editor_ort.contentType")); // NOI18N
        editor_ort.setEditable(false);
        editor_ort.setName("editor_ort"); // NOI18N
        jScrollPane2.setViewportView(editor_ort);

        jSeparator2.setName("jSeparator2"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        label_kunde.setText(resourceMap.getString("label_kunde.text")); // NOI18N
        label_kunde.setName("label_kunde"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        label_zeitraum.setText(resourceMap.getString("label_zeitraum.text")); // NOI18N
        label_zeitraum.setName("label_zeitraum"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(combo_wiederholung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(14, 14, 14)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(label_kunde))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(label_zeitraum)))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_zeitraum))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_kunde))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_wiederholung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        loadTermin();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        if(combo_wiederholung.getSelectedIndex() == 0) {
            this.dispose();
        } else {
//Nicht wiederholen
//In 1 Minute wiederholen
//In 5 Minuten wiederholen
//In 30 Minuten wiederholen
//In 1 Stunde wiederholen
//In 2 Stunden wiederholen
//In 1 Tag wiederholen
//In 1 Woche wiederholen
//--------------------------------------
//1 Stunde vor dem Termin
//2 Stunden vor dem Termin
//1 Tag vor dem Termin
            if(combo_wiederholung.getSelectedIndex() == 0 || combo_wiederholung.getSelectedIndex() == -1) {
                this.dispose();
            } else if (combo_wiederholung.getSelectedIndex() == 1) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (60 * 1000)));
            } else if (combo_wiederholung.getSelectedIndex() == 2) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (5 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 3) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (30 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 4) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (60 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 5) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (120 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 6) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (24 * 60 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 7) {
                termin.setErinnerung(new java.sql.Timestamp(System.currentTimeMillis() + (7 * 24 * 60 * 60000)));
            } else if (combo_wiederholung.getSelectedIndex() == 8) {
                JOptionPane.showMessageDialog(null, "Ungültige Wiederholung");
                return;
            } else if (combo_wiederholung.getSelectedIndex() == 9) {
                termin.setErinnerung(new java.sql.Timestamp(termin.getStart().getTime() - (60 * 600000)));
            } else if (combo_wiederholung.getSelectedIndex() == 10) {
                termin.setErinnerung(new java.sql.Timestamp(termin.getStart().getTime() - (120 * 600000)));
            } else if (combo_wiederholung.getSelectedIndex() == 11) {
                termin.setErinnerung(new java.sql.Timestamp(termin.getStart().getTime() - (24 * 60 * 600000)));
            }
            try {
                boolean success = TermineSQLMethods.updateTermin(DatabaseConnection.open(), termin);
                if(success == false) {
                     ShowException.showException("Bei der Speicherung des Erinnerungsdatums ist ein Datenbank Fehler aufgetretten. "
                    + "Der Termin wurde nicht gefunden.", ExceptionDialogGui.LEVEL_WARNING, null, "Schwerwiegend: Konnte den Termin nicht speichern");
                }

                new SchedulerTask().start();
            } catch (SQLException e) {
                ShowException.showException("Bei der Speicherung des Erinnerungsdatums ist ein Datenbank Fehler aufgetretten. "
                    + "Sollte dieser häufiger auftretten wenden Sie sich bitte an den Support.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Termin nicht speichern");
                Log.databaselogger.fatal("Datenbankfehler: Konnte den neuen Erinnerungstermin nicht speichern", e);
            }

            this.dispose();
        }
    }//GEN-LAST:event_btn_okActionPerformed

    public void loadTermin() {
        if(termin == null)
            return;

       if(termin.getKundeKennung() == null)
             label_kunde.setText("Keiner");
       else {            
           Object kunde = KundenRegistry.getKunde(termin.getKundeKennung());
           this.label_kunde.setText(kunde.toString());
       }

       Calendar cal1 = Calendar.getInstance();
       Calendar cal2 = Calendar.getInstance();

       cal1.setTime(termin.getStart());
       cal2.setTime(termin.getEnde());

       boolean same = sameDay(cal1, cal2);

       SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

       String minute1 = "0";
       if(cal1.get(Calendar.MINUTE) < 10)
            minute1 = "0" + cal1.get(Calendar.MINUTE);
       else
            minute1 = "" + cal1.get(Calendar.MINUTE);

       String minute2 = "0";
       if(cal2.get(Calendar.MINUTE) < 10)
            minute2 = "0" + cal2.get(Calendar.MINUTE);
       else
            minute2 = "" + cal2.get(Calendar.MINUTE);

       if(same) {                    
           label_zeitraum.setText(df.format(termin.getStart()) + " von " + cal1.get(Calendar.HOUR_OF_DAY) + ":" +
                   minute1 + " bis " + cal2.get(Calendar.HOUR_OF_DAY) + ":" +
                   minute2);
       } else {
           label_zeitraum.setText(df.format(termin.getStart()) + " " + cal1.get(Calendar.HOUR_OF_DAY) + ":" +
                   minute1 + " bis " + df.format(termin.getEnde()) + " " + cal2.get(Calendar.HOUR_OF_DAY) + ":" +
                   minute2);
       }

       area_beschreibung.setText(termin.getBeschreibung());


        editor_ort.setContentType("text/html");
        Font font = new JLabel().getFont();

         String stylesheet = "a { color:#4040D9; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:" + font.getName() + "; font-size:" + font.getSize() + "pt;}";

        HTMLEditorKit kit = new HTMLEditorKit();
        editor_ort.setEditorKit(kit);
        HTMLDocument htmlDocument = (HTMLDocument)editor_ort.getDocument();
        StyleSheet sheet = new StyleSheet();
        try {
            sheet.loadRules(new StringReader(stylesheet), null);
            htmlDocument.getStyleSheet().addStyleSheet(sheet);
        } catch (Exception e) {
        }
        htmlDocument.setAsynchronousLoadPriority(-1);
        editor_ort.setDocument(htmlDocument);
//        area_text.setText(text);
        String orthtml = "<html>" + "<body><a href=\""+ termin.getId() + "\" title=\"Karte öffnen\">"
                + termin.getOrt() + "</body>" + "</html>";

        editor_ort.setText(orthtml);

        editor_ort.setEditable(false);
        editor_ort.setOpaque(false);
        editor_ort.addHyperlinkListener(new HyperlinkListener(){
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    panelKarte.search(termin.getOrt());
                    TerminErinnerungDialog.super.dispose();
                }
            }
        });
       

       this.validate();
    }


    public boolean sameDay(Calendar c1, Calendar c2) {
          return (
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) ) &&
              ( c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) );
    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TerminErinnerungDialog dialog = new TerminErinnerungDialog(new javax.swing.JFrame(), true);
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
    public javax.swing.JTextArea area_beschreibung;
    public javax.swing.JButton btn_ok;
    public javax.swing.JComboBox combo_wiederholung;
    public javax.swing.JEditorPane editor_ort;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JLabel label_kunde;
    public javax.swing.JLabel label_zeitraum;
    // End of variables declaration//GEN-END:variables

}
