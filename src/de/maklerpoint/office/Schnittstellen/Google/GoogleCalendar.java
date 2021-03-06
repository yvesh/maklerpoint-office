/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Schnittstellen.Google;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.GoogleService.CaptchaRequiredException;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.extensions.EventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Version;
import de.maklerpoint.office.Tools.BasicRegex;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class GoogleCalendar {

    private static GoogleService myService;
    private static URL feedUrl;

    public static EventEntry commitTermin(TerminObj termin) throws AuthenticationException,
            MalformedURLException, IOException, ServiceException {

        EventEntry myEntry = new EventEntry();

        if (termin.getKundeKennung().equalsIgnoreCase("-1")) {
            myEntry.setTitle(new PlainTextConstruct(BasicRegex.shortenString(40, termin.getBeschreibung(), true)));
        } else {
            myEntry.setTitle(new PlainTextConstruct(BasicRegex.shortenString(30, termin.getBeschreibung(), true)
                    + " - " + KundenRegistry.getKunde(termin.getKundeKennung())));
        }

        StringBuilder sb = new StringBuilder();

        if (termin.getKundeKennung().equalsIgnoreCase("-1")) {
            sb.append("Kunde: ").append(KundenRegistry.getKunde(termin.getKundeKennung())).append("\n");
        }

        if (termin.getVertragId() != -1) {
            sb.append("Vertrag: ").append(VertragRegistry.getVertrag(termin.getVertragId())).append("\n");
        }

        if (termin.getSchadenId() != -1) {
            sb.append("Schaden: ").append(VertragRegistry.getSchaden(termin.getSchadenId())).append("\n");
        }

        if (termin.getStoerfallId() != -1) {
            sb.append("Störfall: ").append(VertragRegistry.getStoerfall(termin.getStoerfallId())).append("\n");
        }

        sb.append("\nBeschreibung:\n");
        sb.append(termin.getBeschreibung());
        sb.append("\n\n");

        if (termin.getOrt() != null) {
            sb.append("Ort: ").append(termin.getOrt());
        }

        myEntry.setContent(new PlainTextConstruct(sb.toString()));

        When eventTimes = new When();
        DateTime startTime = new DateTime(termin.getStart());
        DateTime endTime = new DateTime(termin.getEnde());

        eventTimes.setStartTime(startTime);
        eventTimes.setEndTime(endTime);

        myEntry.addTime(eventTimes);

        return myService.insert(feedUrl, myEntry);
    }

    public static void transmitAlleEigenenTermine() {
        transmitTermine(KalenderRegistry.getTermine());
    }

    public static void transmitTermine(final TerminObj[] termine) {
        if (Config.get("userGoogle", null) == null || Config.get("passwordGoogle", null) == null) {
            JOptionPane.showMessageDialog(null, "Vor dem Export müssen Sie Ihre "
                    + "Google Benutzerdaten in den Einstellungen angeben.");
            return;
        }

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                String username = Config.get("userGoogle", null);
                String password = Config.get("passGoogle", null);

                feedUrl = new URL("https://www.google.com/calendar/feeds/" + username + "/private/full");

                myService = new GoogleService("cl", "MaklerPoint-Office-" + Version.build);

                if (Config.get("tokenGoogle", null) == null) {
                    try {
                        myService.setUserCredentials(username, password);
                    } catch (CaptchaRequiredException e) {
                        Log.logger.warn("Google Catpcha erforderlich", e);
                        String captcha = JOptionPane.showInputDialog(null, "Bitte besuchen Sie die folgende Adresse "
                                + e.getCaptchaUrl() + " und geben Sie das Captcha in das untere Feld ein");

                        myService.setUserCredentials(username, password, e.getCaptchaToken(), captcha);
                    } catch (AuthenticationException e) {
                        Log.logger.fatal("Die Authorisierung mit den Google Servern ist fehlgeschlagen.", e);
                    }
                } else {
                    System.out.println("Benutze google token");
                    myService.setUserToken(Config.get("tokenGoogle", null));
                }

                for (int i = 0; i < termine.length; i++) {
                    try {
                        EventEntry ev = GoogleCalendar.commitTermin(termine[i]);

                        if (Log.logger.isDebugEnabled()) {
                            Log.logger.debug("Neue Google Event id: " + ev.getId());
                        }
                    } catch (Exception e) {
                        Log.logger.fatal("Beim Export zum Google Kalender ist ein Fehler aufgetretten.", e);
                        ShowException.showException("Beim Google-Export der Termine ist ein Fehler aufgetretten.",
                                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht exportieren");
                        continue;
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                try {
                    JOptionPane.showMessageDialog(null,
                            "Die Termine wurden erfolgreich "
                            + "zu Ihrem Google Kalender übertragen.");
                } catch (Exception ignore) {
                }
            }
        };

        sw.execute();
    }
}
