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

package de.maklerpoint.office.Gui.Systemtray;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Gui.CRMAboutBox;
import de.maklerpoint.office.Gui.Configuration.ConfigurationDialog;
import de.maklerpoint.office.Logging.Log;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.apache.log4j.Level;

/**
 *
 * @author yves
 */

public class Systemtray {

    private JDialog aboutBox;
    private JDialog settingsBox;

    public static CheckboxMenuItem fatalItem;
    public static CheckboxMenuItem warningItem;
    public static CheckboxMenuItem infoItem;
    public static CheckboxMenuItem debugItem;
    public static CheckboxMenuItem allItem;

    public Systemtray(){

    final TrayIcon trayIcon;

        if (SystemTray.isSupported())
        {
            SystemTray tray = SystemTray.getSystemTray();
            Image trayimage = Toolkit.getDefaultToolkit().getImage("includes/images/icon.gif");

            /* Creating listeners */

            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    CRM.launch(null);
                }

                public void mouseEntered(MouseEvent e) {
    //                System.out.println("Tray Icon - Mouse entered!");
                }

                public void mouseExited(MouseEvent e) {
    //                System.out.println("Tray Icon - Mouse exited!");
                }

                public void mousePressed(MouseEvent e) {
    //                System.out.println("Tray Icon - Mouse pressed!");
                }

                public void mouseReleased(MouseEvent e) {
    //                System.out.println("Tray Icon - Mouse released!");
                }
            };

            ActionListener exitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
               }
            };

            ActionListener aboutListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (aboutBox == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    aboutBox = new CRMAboutBox(mainFrame);
                    aboutBox.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(aboutBox);
             }
            };

            ActionListener settingsListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(settingsBox == null) {
                    JFrame mainFrame = CRM.getApplication().getMainFrame();
                    settingsBox = new ConfigurationDialog(mainFrame, false);
                    settingsBox.setLocationRelativeTo(mainFrame);
                }
                CRM.getApplication().show(settingsBox);
                settingsBox.toFront();
                }
            };

            ItemListener logInfoListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    Log.setLevel(Level.INFO);
                    Log.logger.info("Logging auf Info Level umgestellt.");
                }
            };

            ItemListener logErrorListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    Log.setLevel(Level.FATAL);
                    Log.logger.fatal("Logging auf Schwere Fehler umgestellt.");
                }
            };

            ItemListener logWarningListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    Log.setLevel(Level.WARN);
                    Log.logger.warn("Logging auf Warnungen umgestellt.");
                }
            };

            ItemListener logAllListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    Log.setLevel(Level.ALL);
                    Log.logger.debug("Logging auf Alles umgestellt.");
                }
            };

            ItemListener logDebugListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    Log.setLevel(Level.DEBUG);
                    Log.logger.debug("Logging auf Debug umgestellt.");
                }
            };



            /* Creating Popup Menu */

            final PopupMenu popup = new PopupMenu();

            // Exit
            MenuItem exitItem = new MenuItem("MaklerPoint beenden");
            exitItem.addActionListener(exitListener);
            popup.add(exitItem);

            // About
            MenuItem aboutItem = new MenuItem("Über MaklerPoint");
            aboutItem.addActionListener(aboutListener);
            popup.add(aboutItem);

            popup.addSeparator();

            // Settings
            MenuItem settingsItem = new MenuItem("Einstellungen");
            settingsItem.addActionListener(settingsListener);
            popup.add(settingsItem);
            
            popup.addSeparator();

            // Logger Level

            Menu displayMenu = new Menu("Protokoll Level");

            fatalItem = new CheckboxMenuItem("Schwere Fehler");
            fatalItem.addItemListener(logErrorListener);

            warningItem = new CheckboxMenuItem("Warnungen");
            warningItem.addItemListener(logWarningListener);

            infoItem = new CheckboxMenuItem("Informationen");
            infoItem.addItemListener(logInfoListener);

            debugItem = new CheckboxMenuItem("Debug");
            debugItem.addItemListener(logDebugListener);

            allItem = new CheckboxMenuItem("Alles");
            allItem.addItemListener(logAllListener);

            popup.add(displayMenu);
            displayMenu.add(fatalItem);
            displayMenu.add(warningItem);
            displayMenu.add(infoItem);
            displayMenu.add(debugItem);
            displayMenu.add(allItem);

            popup.addSeparator();
            popup.add(exitItem);

            // Defining tray Icon

            trayIcon = new TrayIcon(trayimage, "MaklerPoint", popup);

            ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trayIcon.displayMessage("Action Event",
                    "An Action Event Has Been Performed!",
                    TrayIcon.MessageType.INFO);
              }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);
            trayIcon.setToolTip("MaklerPoint");
            trayIcon.displayMessage("MaklerPoint gestartet", "MaklerPoint wurde erfolgreich gestartet.",
                                    TrayIcon.MessageType.INFO);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                Log.logger.warn("Das SystemTray Icon konnte nicht hinzugefügt werden.");
                return;
            }
        } else {
            Log.logger.warn("Der SystemTray wird von Ihrem Betriebssystem / Desktop nicht unterstützt.");
        }
    }

    /**
     * Changes the logging display
     * @param level
     */

    public static void changeLogging(int level){
         fatalItem.setState(false);
         warningItem.setState(false);
         infoItem.setState(false);
         debugItem.setState(false);
         allItem.setState(false);

         switch(level){
             case 0:
                fatalItem.setState(true);
                break;

             case 1:
                 warningItem.setState(true);
                 break;

             case 2:
                 infoItem.setState(true);
                 break;

             case 3:
                 debugItem.setState(true);
                 break;

             case 4:
                 allItem.setState(true);
                 break;

             default:
                 fatalItem.setState(true);
                 break;
         }
    }

}
