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
package de.maklerpoint.office.Schnittstellen.iCal;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class iCalExport {

    private static Calendar calendar = null;
    private static BenutzerObj ben = null;

    public static void addTermin(TerminObj termin) throws SocketException {

        if (ben == null) {
            ben = BenutzerRegistry.getBenutzer(termin.getBenutzerId());
        } else {
            if (termin.getBenutzerId() != ben.getId()) {
                ben = BenutzerRegistry.getBenutzer(termin.getBenutzerId());
            }
        }

        DateTime start = new DateTime(termin.getStart());
        DateTime end = new DateTime(termin.getEnde());

        VEvent meeting = new VEvent(start, end, termin.getBeschreibung());

        // generate unique identifier..
        UidGenerator ug = new UidGenerator("uidGen");
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);


        Attendee benmail = new Attendee(URI.create(ben.getEmail()));
        benmail.getParameters().add(Role.REQ_PARTICIPANT);
        benmail.getParameters().add(new Cn(ben.toString()));
        meeting.getProperties().add(benmail);

        if (!termin.getKundeKennung().equalsIgnoreCase("-1")) {
            Object knd = KundenRegistry.getKunde(termin.getKundeKennung());

            if (knd.getClass().equals(KundenObj.class)) {
                KundenObj pk = (KundenObj) knd;
                String mail = KundenMailHelper.getKundenMail(pk);

                if (mail != null) {
                    Attendee kundemail = new Attendee(URI.create(mail));
                    kundemail.getParameters().add(Role.REQ_PARTICIPANT);
                    kundemail.getParameters().add(new Cn(pk.toString()));
                    meeting.getProperties().add(kundemail);
                }
//                else {
//                    Attendee kundemail = new Attendee(pk.toString());
//                    kundemail.getParameters().add(Role.REQ_PARTICIPANT);
//                    kundemail.getParameters().add(new Cn(pk.toString()));
//                    meeting.getProperties().add(kundemail);
//                }
            } else if (knd.getClass().equals(FirmenObj.class)) {
                FirmenObj pk = (FirmenObj) knd;
                String mail = KundenMailHelper.getGeschKundenMail(pk);

                if (mail != null) {
                    Attendee kundemail = new Attendee(URI.create(mail));
                    kundemail.getParameters().add(Role.REQ_PARTICIPANT);
                    kundemail.getParameters().add(new Cn(pk.toString()));
                    meeting.getProperties().add(kundemail);
                }
//                else {
//                    Attendee kundemail = new Attendee(pk.toString());
//                    kundemail.getParameters().add(Role.REQ_PARTICIPANT);
//                    kundemail.getParameters().add(new Cn(pk.toString()));
//                    meeting.getProperties().add(kundemail);
//                }
            }
        }

        calendar.getComponents().add(meeting);
    }

    public static void exportTermine(String ziel, TerminObj[] termine) throws IOException,
            ValidationException {
//        System.out.println("Erstelle neuen Kalender");
        calendar = new Calendar();
//        System.out.println("Setzte ProdId");
        try {
            calendar.getProperties().add(new ProdId("-//MaklerPoint Software//Office " + de.maklerpoint.office.System.Version.version + "//DE"));
//            calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("Setzte Version");
        calendar.getProperties().add(Version.VERSION_2_0);
//        System.out.println("Setze calScale");
        calendar.getProperties().add(CalScale.GREGORIAN);

//        System.out.println("Anzahl Termine: " + termine.length);
        for (int i = 0; i < termine.length; i++) {
            addTermin(termine[i]);
        }

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, new FileOutputStream(ziel));
    }

    public static void exportAlleEigenenTermine(final String ziel) {
        Log.logger.debug("Exportiere Termine im iCal Format nach " + ziel);

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                try {
                    iCalExport.exportTermine(ziel, KalenderRegistry.getTermine());
                } catch (Exception e) {
                    Log.logger.fatal("Beim Export in das iCal Format ist ein Fehler aufgetretten. ", e);
                    ShowException.showException("Beim iCal-Export der Termine ist ein Fehler aufgetretten.",
                        ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Termine nicht exportieren");
                }
                return null;
            }

            @Override
            protected void done() {
                try {
//                    JOptionPane.showMessageDialog(null,
//                            "Die Termine wurden erfolgreich "
//                            + "zu Ihrem Google Kalender übertragen.");
                } catch (Exception ignore) {
                }
            }
        };

        sw.execute();

    }
}
