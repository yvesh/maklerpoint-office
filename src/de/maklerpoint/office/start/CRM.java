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
package de.maklerpoint.office.start;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Benutzer.BenutzerLoginGui;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Firstrun.EinrichtungsAssistentDialog;
import de.maklerpoint.office.Gui.License.EnterLicenseDialog;
import de.maklerpoint.office.Logging.InitializeLog;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.ShutdownHook.ShutdownHook;
import de.maklerpoint.office.Startup.StartupTasks;
import de.maklerpoint.office.System.Configuration.Config;
import de.acyrance.licensor.Config.LicenseConfig;
import de.acyrance.licensor.License;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class CRM extends SingleFrameApplication {

    public static final boolean DEMONSTRATION_MODE = false;
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new CRMView(this));
    }    

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of CRM
     */
    public static CRM getApplication() {
        return Application.getInstance(CRM.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {

        /* Initialize Logging (Console, File and Syslog / NT Event Logging) */

        InitializeLog.initialize();     

        if (!Config.getConfigBoolean("firstrun", true)) {
            Connection con = DatabaseConnection.open();

            // Check DB
            if (!Config.getConfigBoolean("offlineModus", false) && Config.getConfigInt("databaseType",
                    DatabaseTypes.MYSQL) != DatabaseTypes.EMBEDDED_DERBY) {
                try {
                    if (con != null) {
                        if (!con.getMetaData().getTables(null, null, "benutzer", null).next()) {
                            ShowException.showException("Die MaklerPoint Office Installation scheint in der angegeben Datenbank "
                                    + "nicht zu existieren. Bitte überprüfen Sie Ihre Installation. "
                                    + "Die Anwendung wird beendet.",
                                    ExceptionDialogGui.LEVEL_FATAL, "Schwerwiegend: Keine Datenbank vorhanden");
                            con.close();
                        }
                    }
                } catch (Exception ex) {
                    Log.databaselogger.fatal("Die Datenbank scheint nicht zu existieren", ex);
                }
            }

        }
        Tester.test();

//        Color bgColor = JColorChooser.showDialog(null, "Fabe", Color.yellow);
//        System.out.println(bgColor.getRGB());

        if (LicenseConfig.getConfigBoolean("serial", false) == false) {
            if (liBox0O424 == null) {
//                JFrame mainFrame = CRM.getApplication().getMainFrame();
                liBox0O424 = new EnterLicenseDialog(null, false, args);
//                liBox0O424.setLocationRelativeTo(mainFrame);
            }
            CRM.getApplication().show(liBox0O424);
        } else {
            String val203O44 = License.validate(LicenseConfig.get("licensor", ""), LicenseConfig.get("licenseKey", ""));
            if (val203O44.equals("faf4s!l3s")) {
                launchAnmeldung(args);
            } else {
                JOptionPane.showConfirmDialog(null, "Der gespeicherte Lizenzcode ist nicht gültig. Bitte "
                        + "geben Sie Ihre Lizenzinformationen ein.", "Fehler: Falscher Lizenzcode", JOptionPane.OK_OPTION);

                if (liBox0O424 == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    liBox0O424 = new EnterLicenseDialog(mainFrame, false, args);
                    liBox0O424.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(liBox0O424);
            }
        }
    }

    public static void launchAnmeldung(String[] args) {

        if (Config.getConfigBoolean("firstrun", true)) {
            if (firstrunBox == null) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                firstrunBox = new EinrichtungsAssistentDialog(null, true);
                firstrunBox.setLocationRelativeTo(mainFrame);
            }
            CRM.getApplication().show(firstrunBox);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StartupTasks.doStartupTasks();
                /* Inserting ShutdownHook */
                ShutdownHook shutdownHook = new ShutdownHook();
                Runtime.getRuntime().addShutdownHook(shutdownHook);
            }
        });

        if (loginBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            loginBox = new BenutzerLoginGui(null, false, args);
            loginBox.setLocationRelativeTo(mainFrame);
        }
//        loginBox.setVisible(true);
        CRM.getApplication().show(loginBox);
    }

    public static void launch(String[] args) {
        if (CRMView.open == false) {
            launch(CRM.class, args);
        }
    }
    private static JDialog firstrunBox;
    private static JDialog loginBox;
    private static JDialog liBox0O424;
}
