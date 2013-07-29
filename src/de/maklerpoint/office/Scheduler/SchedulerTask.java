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
package de.maklerpoint.office.Scheduler;

import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Kalender.Termine.TriggerTerminJob;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Nachrichten.Tools.TriggerMessageJob;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Wiedervorlage.TriggerWiedervorlagenJob;
import de.maklerpoint.office.Wiedervorlage.WiedervorlagenObj;
import java.util.Calendar;
import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SchedulerTask extends Thread {

    public static Scheduler AcyScheduler = null;
    public static int JOBCOUNT = 0;


    public SchedulerTask() {
        super("scheduler");
    }

    @Override
    public void run() {
        try {
            if(AcyScheduler != null)
                AcyScheduler.shutdown();

            SchedulerFactory sf = new StdSchedulerFactory();
            AcyScheduler = sf.getScheduler();

            /* Setting up Termine */

            TerminObj[] termine = KalenderRegistry.getTermine(true);

            if(termine == null) {
                Log.logger.debug("Keine Termine in der Datenbank.");                
            } else {
                Log.logger.debug("Starte Scheduler zur Erinnerung an die Termine.");
                Calendar calendar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();

                calendar.setTime(new Date(System.currentTimeMillis()));
                
                for(int i = 0; i < termine.length; i++) {
                    TerminObj termin = termine[i];
                    if(termin.getErinnerung() != null) {
                        calendar2.setTime(termin.getErinnerung());

                        boolean after = SchedulerTime.afterToday(calendar, calendar2);
                        if(after){
//                            System.out.println("Added Termin: " +termin.getBeschreibung());
                            JobDetail terminJob = new JobDetail(termin.getBeschreibung(), "termin", TriggerTerminJob.class);
                            terminJob.getJobDataMap().put("terminId", termin.getId());
                            terminJob.getJobDataMap().put("termin", termin);
                            SimpleTrigger terminTrigger = new SimpleTrigger(termin.getBeschreibung() + "Trigger", "termin", termin.getBeschreibung(),
                                    "termin", termin.getErinnerung(), null, 1, 60000L);
//                            System.out.println("Triggers at: " + termin.getErinnerung());

                            AcyScheduler.scheduleJob(terminJob, terminTrigger);
                            JOBCOUNT++;
                        }
                    }
                }
            }

            // Wiedervorlagen
            
            WiedervorlagenObj[] vorlagen = KalenderRegistry.getWiedervorlagen(true);

            if(vorlagen != null) {
                Log.logger.debug("Starte Scheduler für neue Wiedervorlagen");
                Calendar calendar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();

                calendar.setTime(new Date(System.currentTimeMillis()));
                calendar.set(Calendar.HOUR_OF_DAY, 0);

                for(int i = 0; i < vorlagen.length; i++) {

                    if(vorlagen[i].getErinnerung() != null) {
                        calendar2.setTime(vorlagen[i].getErinnerung());
                        boolean after = SchedulerTime.afterToday(calendar, calendar2);
                        if(after) {
                            JobDetail vorlagenJob = new JobDetail(vorlagen[i].getBeschreibung(), 
                                    "wiedervorlage", TriggerWiedervorlagenJob.class);
                            vorlagenJob.getJobDataMap().put("vorlageId", vorlagen[i].getId());
                            vorlagenJob.getJobDataMap().put("vorlage", vorlagen[i]);
                            SimpleTrigger vorlagenTrigger = new SimpleTrigger(vorlagen[i].getBeschreibung() + "Trigger",
                                    "wiedervorlage", vorlagen[i].getBeschreibung(), 
                                    "wiedervorlage", vorlagen[i].getErinnerung(), null, 1, 60000L);

                            AcyScheduler.scheduleJob(vorlagenJob, vorlagenTrigger);

                            JOBCOUNT++;
                        }
                    }
                }
            }
            
            // CheckforMails jede x Sek.            
            Log.logger.debug("Starte Scheduler zur Überprüfung auf neue Benutzernachrichten.");
            
            JobDetail mailCheckJob = new JobDetail("mailcheck", "messages", TriggerMessageJob.class);
            SimpleTrigger mailTrigger = new SimpleTrigger("mailTrigger", "messages", "mailcheck", "messages",
                    new java.sql.Timestamp(System.currentTimeMillis()), null, SimpleTrigger.REPEAT_INDEFINITELY, 
                    Config.getConfigInt("messagesPolling", 300) * 1000L);
            
            AcyScheduler.scheduleJob(mailCheckJob, mailTrigger);
            JOBCOUNT++;
            
            
            // Starte Scheduler
            AcyScheduler.start();            
        } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte Scheduler nicht starten", e);
        }
    }




}
