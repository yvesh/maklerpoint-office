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
package de.maklerpoint.office.Gui;

import de.maklerpoint.office.Database.DatabaseTypes;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityRoles;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Configuration.Config;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class CRMViewHelper {

    /**
     * Initializes the menu
     * @param view
     */
    public static void initializeMenu(CRMView view) {
        Log.logger.debug("Richte Menü ein");
        setupSecurity(view);
        setupMenu(view);
        setupOfflinemode(view);
    }

    public static void setupOfflinemode(CRMView view) {
        if (Config.getConfigBoolean("offlineModus", false) == true) {
            Log.logger.info("Starte im Offline Modus");
            JOptionPane.showMessageDialog(null, "Information: Sie befinden noch im Offline Modus.");
            view.offlineCheckMenuItem.setSelected(true);
        } else {
            Log.logger.info("Starte im externen Datenbank Modus");
        }

        if (Config.getConfigInt("databaseType", DatabaseTypes.MYSQL) == DatabaseTypes.EMBEDDED_DERBY) {
            view.offlineCheckMenuItem.setSelected(true);
            view.offlineCheckMenuItem.setEnabled(false);
        }
    }

    public static void setupMenu(CRMView view) {
        if (BenutzerRegistry.isNewMail()) {
            CRMView.poplb_mail.setVisible(true); // Neue Mails da        
        } else {
            CRMView.poplb_mail.setVisible(false);
        }
    }

    public static void setupSecurity(CRMView view) {
        view.neuerBenutzerMenuItem.setEnabled(Security.isAllowed(SecurityTasks.BENUTZER_CREATE));
        view.benutzerManagementMenuItem.setEnabled(Security.isAllowed(SecurityTasks.BENUTZER_MANAGEMENT));
        view.sicherungMenuItem.setEnabled(Security.isAllowed(SecurityTasks.BACKUP));
        view.autoSicherungMenuItem.setEnabled(Security.isAllowed(SecurityTasks.BACKUP_AUTO));
        view.databaseSQLMenuItem.setEnabled(Security.isAllowed(SecurityTasks.DATABASE_SQL_ZUGRIFF));
        view.sqlskriptMenuItem.setEnabled(Security.isAllowed(SecurityTasks.DATABASE_SQL_SKRIPT));
        view.checkdbMenuItem.setEnabled(Security.isAllowed(SecurityTasks.DATABASE_CHECK));
        view.maklerpointskriptMenuItem.setEnabled(Security.isAllowed(SecurityTasks.MAKLERPOINT_SKRIPT));
    }
}