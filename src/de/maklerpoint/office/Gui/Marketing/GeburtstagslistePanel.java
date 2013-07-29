/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GeburtstagslistePanel.java
 *
 * Created on 17.06.2011, 12:30:25
 */
package de.maklerpoint.office.Gui.Marketing;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Table.JLabelCellRenderer;
import de.maklerpoint.office.Tools.ImageTools;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class GeburtstagslistePanel extends javax.swing.JPanel {

    private String[] kundenColumn = {"Hidden", "Typ", "Datum", "Jahre", "Kdnr", "Name",
        "Anschrift", "Telefon", "E-Mail"};
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    private SimpleDateFormat df_geb = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat df_test = new SimpleDateFormat("dd.MM.yyyy");

    /** Creates new form GeburtstagslistePanel */
    public GeburtstagslistePanel() {
        initComponents();
        setUp();
    }

    private void setUp() {
        Date[] dates = getDateRange();

        this.date_von.setDate(dates[0]);
        this.date_bis.setDate(dates[1]);

        loadTable();
    }

    private void loadTable() {
        Date von = this.date_von.getDate();
        Date bis = this.date_bis.getDate();

        cal2.setTime(von);
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("Zeige Geburtstage / Jubiläen von: " + df_test.format(von) + " bis " + df_test.format(bis));
        }

        ArrayList kinrange = new ArrayList();

        if (von == null || bis == null) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie einen gültigen Zeitraum.");
            return;
        }

        Object[][] data = null;

        KundenObj[] kunden = KundenRegistry.getKunden(Status.NORMAL);
        FirmenObj[] fkunden = KundenRegistry.getFirmenKunden(false, Status.NORMAL);
        FirmenAnsprechpartnerObj[] ap = null;
        try {
            ap = FirmenAnsprechpartnerSQLMethods.loadAnsprechpartner(DatabaseConnection.open(), Status.NORMAL);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (kunden != null) {
            for (int i = 0; i < kunden.length; i++) {
                if (kunden[i].getGeburtsdatum() != null && kunden[i].getGeburtsdatum().length() > 1) {
                    try {
                        Date geb = df_geb.parse(kunden[i].getGeburtsdatum());

                        cal.setTime(geb);
                        cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));

                        geb = cal.getTime();
//                    System.out.println("Date geb: " + df_test.format(geb));
                        if (!(geb.before(von) || geb.after(bis))) {
                            kinrange.add(kunden[i]);
                        }
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }

        if (fkunden != null) {
            for (int i = 0; i < fkunden.length; i++) {
                if (fkunden[i].getFirmenGruendungDatum() != null) {
                    Date jub = fkunden[i].getFirmenGruendungDatum();

                    cal.setTime(jub);
                    cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));

                    jub = cal.getTime();
//                    System.out.println("Date jub: " + df_test.format(jub));
                    if (!(jub.before(von) || jub.after(bis))) {
                        kinrange.add(fkunden[i]);
                    }
                }
            }
        }

        // TODO Add Ansprechpartner

        if (ap != null) {
            for (int i = 0; i < ap.length; i++) {
                if (ap[i].getGeburtdatum() != null && ap[i].getGeburtdatum().length() > 1) {
                    try {
                        Date geb = df_geb.parse(ap[i].getGeburtdatum());

                        cal.setTime(geb);
                        cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));

                        geb = cal.getTime();

                        if (!(geb.before(von) || geb.after(bis))) {
                            kinrange.add(ap[i]);
                        }
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }

        if (!kinrange.isEmpty()) {
            Object[] allknd = kinrange.toArray();

            data = new Object[allknd.length][9];

            for (int i = 0; i < allknd.length; i++) {
                if (allknd[i].getClass().equals(KundenObj.class)) {
                    KundenObj knd = (KundenObj) allknd[i];

                    data[i][0] = knd;
                    data[i][1] = ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/user-white.png");// image
                    data[i][2] = knd.getGeburtsdatum();
                    try {
                        data[i][3] = getAlter(df_geb.parse(knd.getGeburtsdatum()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                        data[i][3] = "";
                    }
                    data[i][4] = knd.getKundenNr();
                    data[i][5] = knd.toString();
                    data[i][6] = knd.getStreet() + ", \n" + knd.getPlz() + " " + knd.getStadt();
                    data[i][7] = knd.getCommunication1();
                    data[i][8] = KundenMailHelper.getKundenMail(knd);
                } else if (allknd[i].getClass().equals(FirmenObj.class)) {
                    FirmenObj knd = (FirmenObj) allknd[i];

                    data[i][0] = knd;
                    data[i][1] = ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/user-business-boss.png");// image
                    data[i][2] = df_geb.format(knd.getFirmenGruendungDatum());
                    data[i][3] = getAlter(knd.getFirmenGruendungDatum());
                    data[i][4] = knd.getKundenNr();
                    data[i][5] = knd.toString();
                    data[i][6] = knd.getFirmenStrasse() + ", \n" + knd.getFirmenPLZ() + " " + knd.getFirmenStadt();
                    data[i][7] = knd.getCommunication1();
                    data[i][8] = KundenMailHelper.getGeschKundenMail(knd);
                } else if (allknd[i].getClass().equals(FirmenAnsprechpartnerObj.class)) {
                    FirmenAnsprechpartnerObj knd = (FirmenAnsprechpartnerObj) allknd[i];
                    FirmenObj fa = KundenRegistry.getFirmenKunde(knd.getKundenKennung());

                    data[i][0] = knd;
                    data[i][1] = ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/user-business-boss.png");// image
                    data[i][2] = knd.getGeburtdatum();
                    try {
                        data[i][3] = getAlter(df_geb.parse(knd.getGeburtdatum()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                        data[i][3] = "";
                    }
                    data[i][4] = knd.getKundenKennung();
                    data[i][5] = knd.toString();

                    if (fa != null) {
                        data[i][6] = fa.getFirmenStrasse() + ", \n" + fa.getFirmenPLZ() + " " + fa.getFirmenStadt();
                    }
                    data[i][7] = knd.getCommunication1();
                    data[i][8] = KundenMailHelper.getAnsprechpartnerMail(knd);
                }
            }

            setTable(data, kundenColumn);
        }

    }

    private void setTable(Object[][] data, String[] columns) {
        table_kunden.setModel(new AbstractStandardModel(data, columns));
        table_kunden.setDefaultRenderer(JLabel.class, new JLabelCellRenderer());

        table_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_kunden.setColumnSelectionAllowed(false);
        table_kunden.setCellSelectionEnabled(false);
        table_kunden.setRowSelectionAllowed(true);
        table_kunden.setAutoCreateRowSorter(true);

        table_kunden.setFillsViewportHeight(true);
        table_kunden.removeColumn(table_kunden.getColumnModel().getColumn(0));

//        MouseListener popupListener = new TablePopupListener();
//        table_kunden.addMouseListener(popupListener);
        table_kunden.setColumnControlVisible(true);
        table_kunden.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_kunden.getColumnModel().getColumn(0).setMaxWidth(30);

        JTableHeader header = table_kunden.getTableHeader();
//        header.addMouseListener(popupListener);
        header.validate();

        table_kunden.packAll();

        table_kunden.tableChanged(new TableModelEvent(table_kunden.getModel()));
        table_kunden.revalidate();
    }

    private int getAlter(Date geb) {
        Years y = Years.yearsBetween(new LocalDate(geb), new LocalDate());
        return y.getYears();
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public Date[] getDateRange() {
        Date[] all = new Date[2];

        Date begining, end;

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            setTimeToBeginningOfDay(calendar);
            begining = calendar.getTime();
        }

        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeToEndofDay(calendar);
            end = calendar.getTime();
        }

        all[0] = new Date();
        all[1] = new DateTime(new LocalDate().plusMonths(1).toDateTimeAtCurrentTime()).toDate();

        return all;
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
        table_kunden = new org.jdesktop.swingx.JXTable();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        date_von = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        date_bis = new com.toedter.calendar.JDateChooser();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnReport1 = new javax.swing.JButton();
        btnReport2 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

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
        table_kunden.setColumnControlVisible(true);
        table_kunden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_kunden.setMinimumSize(new java.awt.Dimension(400, 150));
        table_kunden.setName("table_kunden"); // NOI18N
        table_kunden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_kundenMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_kunden);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(GeburtstagslistePanel.class);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jToolBar1.add(jLabel2);

        date_von.setName("date_von"); // NOI18N
        jToolBar1.add(date_von);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jToolBar1.add(jLabel3);

        date_bis.setName("date_bis"); // NOI18N
        jToolBar1.add(date_bis);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jToolBar1.add(jSeparator6);

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

        btnReport.setIcon(null);
        btnReport.setMnemonic('L');
        btnReport.setText(resourceMap.getString("btnReport.text")); // NOI18N
        btnReport.setFocusable(false);
        btnReport.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReport.setName("btnReport"); // NOI18N

        btnReport1.setIcon(null);
        btnReport1.setMnemonic('B');
        btnReport1.setText(resourceMap.getString("btnReport1.text")); // NOI18N
        btnReport1.setToolTipText(resourceMap.getString("btnReport1.toolTipText")); // NOI18N
        btnReport1.setFocusable(false);
        btnReport1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReport1.setName("btnReport1"); // NOI18N

        btnReport2.setIcon(null);
        btnReport2.setMnemonic('E');
        btnReport2.setText(resourceMap.getString("btnReport2.text")); // NOI18N
        btnReport2.setToolTipText(resourceMap.getString("btnReport2.toolTipText")); // NOI18N
        btnReport2.setFocusable(false);
        btnReport2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReport2.setName("btnReport2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(582, Short.MAX_VALUE)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReport1)
                    .addComponent(btnReport2)
                    .addComponent(btnReport))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void table_kundenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_kundenMouseClicked
        int row = table_kunden.getSelectedRow();
        if (row == -1) {
            return;
        }

}//GEN-LAST:event_table_kundenMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadTable();
}//GEN-LAST:event_btnRefreshActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnReport1;
    private javax.swing.JButton btnReport2;
    private com.toedter.calendar.JDateChooser date_bis;
    private com.toedter.calendar.JDateChooser date_von;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_kunden;
    // End of variables declaration//GEN-END:variables
}
