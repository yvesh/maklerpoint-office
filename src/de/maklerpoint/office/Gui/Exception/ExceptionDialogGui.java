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
 * ExceptionDialogGui.java
 *
 * Created on Jul 6, 2010, 11:38:38 AM
 */
package de.maklerpoint.office.Gui.Exception;

import de.maklerpoint.office.Bugzilla.BugzillaReport;
import de.maklerpoint.office.Konstanten.MPointKonstanten;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import de.maklerpoint.office.System.Environment;
import de.maklerpoint.office.System.Version;
import de.acyrance.licensor.License;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExceptionDialogGui extends javax.swing.JDialog {

    public static final int LEVEL_INFO = 0;
    public static final int LEVEL_HELP = 1;
    public static final int LEVEL_WARNING = 2;
    public static final int LEVEL_ERROR = 3;
    public static final int LEVEL_FATAL = 4;
    private int errorLevel;
    private String errorMessage;
    private String errorTitle;
    private String stacktrace;
    private org.jdesktop.application.ResourceMap resourceMap = 
            org.jdesktop.application.Application.getInstance(
            de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(ExceptionDialogGui.class);
    private boolean detailsVisible = false;
    private Throwable stack;

    /** Creates new form ExceptionDialogGui */
    public ExceptionDialogGui(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public ExceptionDialogGui(java.awt.Frame parent, boolean modal, int level,
            String msg, String title, Throwable e) {
        super(parent, modal);
        this.errorLevel = level;
        this.errorMessage = msg;
        this.errorTitle = title;
        if (e != null) {
//            System.out.println("Stacktrace vorhanden");
            this.stack = e;
            this.stacktrace = e.getLocalizedMessage();

            if (stacktrace == null) {
                this.stacktrace = e.toString();
            }

            if (stacktrace == null) {
                this.stacktrace = getStackTrace(e);
            }

        } else {
//            System.out.println("Kein Stacktrace");
            this.stacktrace = "Kein Stacktrace";
        }
        initComponents();

        loadIcon();
        loadMessage();
        if (this.stacktrace == null) {
            label_details.setVisible(false);
        } else {
            label_details.setVisible(true);
        }
        scroll_stacktrace.setVisible(false);
        loadStacktrace();
    }

    private void reportBugzilla() {
        String summary = stack.getLocalizedMessage();
        String description = this.errorMessage + "\n" + this.getStackTrace(stack);

        String os = "Windows";

        if (Environment.OS == Environment.FREEBSD) {
            os = "Other";
        } else if (Environment.OS == Environment.LINUX) {
            os = "Linux";
        } else if (Environment.OS == Environment.WINDOWS) {
            os = "Windows";
        } else if (Environment.OS == Environment.MACOSX) {
            os = "Mac OS";
        } else if (Environment.OS == Environment.SOLARIS) {
            os = "Other";
        } else {
            os = "Other";
        }


        String severity = "normal";

        if (errorLevel == LEVEL_FATAL) {
            severity = "blocker";
        } else if (errorLevel == LEVEL_WARNING) {
            severity = "critical";
        } else if (errorLevel == LEVEL_INFO) {
            severity = "normal";
        }
        BugzillaReport br = new BugzillaReport(MPointKonstanten.BUGZILLA_URL,
                MPointKonstanten.BUGZILLA_USER, MPointKonstanten.BUGZILLA_PASSWORD,
                MPointKonstanten.BUGZILLA_PRODUCT, MPointKonstanten.BUGZILLA_COMPONENT);
        br.login();
        // TODO update before beta release
        int bugid = br.createBugreport(summary, "alpha", description, os, "PC", severity);
        uploadLog(bugid);

    }

    private void uploadLog(int bugid) {
        StringBuilder sb = new StringBuilder();

        try {
            sb.append(URLEncoder.encode("clientVersion", "UTF-8")).append("=").append(URLEncoder.encode(Version.version, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientBuild", "UTF-8")).append("=").append(URLEncoder.encode(Version.build_name, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientSerial", "UTF-8")).append("=").append(URLEncoder.encode(License.getHashNumber(), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOS", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(Environment.OS), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSName", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_NAME, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSArch", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_ARCH, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSVersion", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_VERSION, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientVMVendor", "UTF-8")).append("=").append(URLEncoder.encode(Environment.VM_VENDOR, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientVMVersion", "UTF-8")).append("=").append(URLEncoder.encode(Environment.VM_VERSION, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientUsername", "UTF-8")).append("=").append(URLEncoder.encode(Environment.USERNAME, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientUserdir", "UTF-8")).append("=").append(URLEncoder.encode(Environment.USERDIR, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientEnvironment", "UTF-8")).append("=").append(URLEncoder.encode(DatabaseConfig.getDatabaseName()
                    + ", " + DatabaseConfig.DBNAME + ", " + DatabaseConfig.DATABASE, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("databaseLog", "UTF-8")).append("=").append(URLEncoder.encode(readfile(Config.LOG_DIR
                    + File.separatorChar + "database.log"), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("defaultLog", "UTF-8")).append("=").append(URLEncoder.encode(readfile(Config.LOG_DIR
                    + File.separatorChar + "basic.log"), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("pluginLog", "UTF-8")).append("=").append(URLEncoder.encode(readfile(Config.LOG_DIR
                    + File.separatorChar + "plugin.log"), "UTF-8"));

//            System.out.println(readfile(Config.LOG_DIR + File.separatorChar + "database.log"));

            URL url = new URL(MPointKonstanten.MP_LOGUPURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(sb.toString());
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.contains("success")) {
                    JOptionPane.showMessageDialog(null, "Die Fehlermeldung wurde erfolgreich mit der id \"" + bugid
                            + "\" an uns übermittelt und in unserer Fehlerdatenbank angelegt. \n"
                            + "Ihr Fehlerprotokoll finden Sie unter http://www.maklerpoint.de/bugzilla/show_bug.cgi?id="
                            + bugid + ". Vielen Dank für Ihre Unterstützung.");
                }
//                System.out.println("line: " + line);
            }

        } catch (java.net.ConnectException e) {
            Log.logger.warn("Konnte keine Verbindung zum MaklerPoint Server herstellen");
            BasicRegistry.internetAvailable = false;
        } catch (UnsupportedEncodingException e) {
            Log.logger.warn("Fehler beim codieren des Fehlerprotokolls", e);
        } catch (MalformedURLException e) {
            Log.logger.warn("Fehler beim hochladen des Fehlerprotkolls", e);
        } catch (IOException e) {
            Log.logger.warn("Fehler beim hochladen des Fehlerprotkolls", e);
        }
    }

    /**
     * 
     * @param aThrowable
     * @return
     */
    public String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    private String readfile(String filename) throws FileNotFoundException, IOException {
        try {
            StringBuilder sb = new StringBuilder();

            BufferedReader test = new BufferedReader(new FileReader(filename));
            String input = "";

            while ((input = test.readLine()) != null) {
                sb.append(input).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadIcon() {
        switch (errorLevel) {
            default:
            case LEVEL_INFO:
                icon.setIcon(resourceMap.getIcon("icon.info"));
                break;

            case LEVEL_HELP:
                icon.setIcon(resourceMap.getIcon("icon.help"));
                break;

            case LEVEL_WARNING:
                icon.setIcon(resourceMap.getIcon("icon.warning"));
                break;

            case LEVEL_ERROR:
                icon.setIcon(resourceMap.getIcon("icon.error"));
                break;

            case LEVEL_FATAL:
                icon.setIcon(resourceMap.getIcon("icon.fatal"));
                break;
        }
    }

    private void loadMessage() {
        StringBuilder msg = new StringBuilder();

        msg.append("<html>\n");
        msg.append("<body>\n");

        int charsperline = 40;

        int zeilen = errorMessage.length() / charsperline;

        String[] result = errorMessage.split(" ");

        int cnt = 0;
        for (int i = 0; i < result.length; i++) {
            if (cnt < charsperline) {
                cnt += result[i].length();
                msg.append(result[i]);
                msg.append(" ");
            } else {
                cnt = 0;
                msg.append("<br>");
                cnt += result[i].length();
                msg.append(result[i]);
                msg.append(" ");
            }
        }

        msg.append("</body>");
        msg.append("</html>");

        label_message.setText(msg.toString());
    }

    private void loadStacktrace() {
        this.text_stacktrace.setText(stacktrace);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icon = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        label_message = new javax.swing.JLabel();
        label_details = new javax.swing.JLabel();
        scroll_stacktrace = new javax.swing.JScrollPane();
        text_stacktrace = new javax.swing.JTextArea();
        btnReport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(ExceptionDialogGui.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        setResizable(false);

        icon.setIcon(resourceMap.getIcon("icon.icon")); // NOI18N
        icon.setText(resourceMap.getString("icon.text")); // NOI18N
        icon.setName("icon"); // NOI18N

        btnOk.setMnemonic('A');
        btnOk.setText(resourceMap.getString("btnOk.text")); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.setPreferredSize(new java.awt.Dimension(70, 27));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        label_message.setText(resourceMap.getString("label_message.text")); // NOI18N
        label_message.setName("label_message"); // NOI18N

        label_details.setFont(resourceMap.getFont("label_details.font")); // NOI18N
        label_details.setIcon(resourceMap.getIcon("label_details.icon")); // NOI18N
        label_details.setText(resourceMap.getString("label_details.text")); // NOI18N
        label_details.setName("label_details"); // NOI18N
        label_details.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_detailsMouseClicked(evt);
            }
        });

        scroll_stacktrace.setBorder(null);
        scroll_stacktrace.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_stacktrace.setName("scroll_stacktrace"); // NOI18N
        scroll_stacktrace.setOpaque(false);

        text_stacktrace.setColumns(20);
        text_stacktrace.setEditable(false);
        text_stacktrace.setLineWrap(true);
        text_stacktrace.setRows(5);
        text_stacktrace.setWrapStyleWord(true);
        text_stacktrace.setName("text_stacktrace"); // NOI18N
        text_stacktrace.setOpaque(false);
        scroll_stacktrace.setViewportView(text_stacktrace);

        btnReport.setMnemonic('F');
        btnReport.setText(resourceMap.getString("btnReport.text")); // NOI18N
        btnReport.setToolTipText(resourceMap.getString("btnReport.toolTipText")); // NOI18N
        btnReport.setName("btnReport"); // NOI18N
        btnReport.setPreferredSize(new java.awt.Dimension(180, 27));
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(scroll_stacktrace, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(label_details))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(icon))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(label_message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_details)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_stacktrace, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (errorLevel == LEVEL_FATAL) {
            System.exit(11);
        } else {
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void label_detailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_detailsMouseClicked
        if (detailsVisible == false) {
            scroll_stacktrace.setVisible(true);
            label_details.setIcon(resourceMap.getIcon("icon.expand"));
            detailsVisible = true;
            this.setSize(this.getWidth(), this.getHeight() + 80);
        } else {
            scroll_stacktrace.setVisible(false);
            label_details.setIcon(resourceMap.getIcon("icon.reexpand"));
            detailsVisible = false;
            this.setSize(this.getWidth(), this.getHeight() - 80);
        }
        this.validate();
    }//GEN-LAST:event_label_detailsMouseClicked

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        this.btnReport.setEnabled(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        reportBugzilla();
        this.btnReport.setEnabled(true);
        this.setCursor(Cursor.getDefaultCursor());
        this.dispose();
    }//GEN-LAST:event_btnReportActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ExceptionDialogGui dialog = new ExceptionDialogGui(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnOk;
    public javax.swing.JButton btnReport;
    public javax.swing.JLabel icon;
    public javax.swing.JLabel label_details;
    public javax.swing.JLabel label_message;
    public javax.swing.JScrollPane scroll_stacktrace;
    public javax.swing.JTextArea text_stacktrace;
    // End of variables declaration//GEN-END:variables
}
