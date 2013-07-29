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
package de.maklerpoint.office.Registry;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Marketing.NewsletterObj;
import de.maklerpoint.office.Marketing.NewsletterSubscriberObj;
import de.maklerpoint.office.Marketing.Tools.NewsletterSQLMethods;
import de.maklerpoint.office.Marketing.Tools.NewsletterSubscriberSQLMethods;
import de.maklerpoint.office.System.Status;

/**
 *
 * @author yves
 */
public class MarketingRegistry {

    public static NewsletterObj[] newsletter = null;
    public static NewsletterSubscriberObj[] newsletter_sub = null;

    /**
     * 
     * @param reload
     * @return
     */
    public static NewsletterObj[] getNewsletter(boolean reload) {
        if (newsletter == null || reload == true) {
            try {
                newsletter = NewsletterSQLMethods.loadNewsletter(DatabaseConnection.open());
                return newsletter;
            } catch (Exception e) {
                Log.databaselogger.fatal("Konnte die Newsletter nicht aus der Datenbank laden", e);
                ShowException.showException("Datenbankfehler: Die Newsletterliste konnte nicht aus der Datenbank geladen werden. ",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Newsletter nicht laden");
            }
        } else {
            return newsletter;
        }

        return newsletter;
    }

    /**
     * 
     * @param id
     * @param reload
     * @return
     */
    public static NewsletterObj getNewsletter(int id, boolean reload) {
        if (reload == true) {
            getNewsletter(true);
        }

        if (newsletter == null) {
            return null;
        }

        for (int i = 0; i < newsletter.length; i++) {
            if (newsletter[i].getId() == id) {
                return newsletter[i];
            }
        }

        return null;
    }

    /**
     *
     * @param reload
     * @return
     */
    public static NewsletterSubscriberObj[] getNewsletterSubscriber(int status) {

        try {
            return NewsletterSubscriberSQLMethods.loadNewsletter_sub(DatabaseConnection.open(), status);
        } catch (Exception e) {
            Log.databaselogger.fatal("Konnte die Newsletter Subscriber nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Newsletter Subscriber konnten nicht aus der Datenbank geladen werden. ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Newsletter Subscriber nicht laden");
        }


        return null;
    }
}
