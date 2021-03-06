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

import de.maklerpoint.office.Logging.Log;
import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SchedulerHelper {

    public static JobDetail createJobDetail(String name, String group, Class classfile) {
        return new JobDetail(name, group, classfile);
    }

    public static SimpleTrigger createSimpleTrigger(String name, String group, Date date) {
        return new SimpleTrigger(name, group, date);
    }

    public static SimpleTrigger createSimpleTrigger(String name, String group, String job, String jobgroup, Date date) {
        return new SimpleTrigger(name, group, job, jobgroup, date, null, 0, 0);
    }

    public static SimpleTrigger createRepeatTrigger(String name, String group, String job, String jobgroup, Date date, int repeat, int delaybetween) {
        return new SimpleTrigger(name, group, job, jobgroup, date, null, repeat, delaybetween);
    }

    public static SimpleTrigger createInfiniteTrigger(String name, String group, String job, String jobgroup, Date date, int delaybetween){
        return new SimpleTrigger(name, group, job, jobgroup, date, null, SimpleTrigger.REPEAT_INDEFINITELY, delaybetween);
    }

    public static SimpleTrigger createDailyInfiniteTrigger(String name, String group, String job, String jobgroup){
        return new SimpleTrigger(name, group, job, jobgroup, new Date(SchedulerTime.getOneDayFromNow()), null, SimpleTrigger.REPEAT_INDEFINITELY, TriggerUtils.MILLISECONDS_IN_DAY);
    }

    public static SimpleTrigger createHourlyInfiniteTrigger(String name, String group, String job, String jobgroup){
        return new SimpleTrigger(name, group, job, jobgroup, new Date(SchedulerTime.getOneHourFromNow()), null, SimpleTrigger.REPEAT_INDEFINITELY, TriggerUtils.MILLISECONDS_IN_HOUR);
    }

    public static SimpleTrigger createMinuteInfiniteTrigger(String name, String group, String job, String jobgroup){
        return new SimpleTrigger(name, group, job, jobgroup, new Date(SchedulerTime.getOneMinuteFromNow()), null, SimpleTrigger.REPEAT_INDEFINITELY, TriggerUtils.MILLISECONDS_IN_MINUTE);
    }

    public static Date scheduleSimpleTrigger(SimpleTrigger trigger){
        Date ft = null;
        try {
        ft = SchedulerTask.AcyScheduler.scheduleJob(trigger);
        } catch (Exception e) {
            Log.logger.warn("Fehler beim hinzufügen von Trigger: " + trigger.getName() + " zur Bearbeitungsliset", e);
        }
        return ft;
    }

    public static Date scheduleSimpleTrigger(JobDetail job, SimpleTrigger trigger){
        Date ft = null;
        try {
        ft = SchedulerTask.AcyScheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
             Log.logger.warn("Fehler beim hinzufügen von Trigger: " + trigger.getName() + " zur Bearbeitungsliset", e);
        }
        return ft;
    }

    public static void deleteJob(String name, String group){
        try {
            SchedulerTask.AcyScheduler.deleteJob(name, group);
        } catch (Exception e) {
            Log.logger.warn("Fehler beim löschen der geplanten Aufgabe " + name  + " | " + group, e);
        }
    }

    /**
     * Shuts down the scheduler
     */

    public static void shutdownScheduler(){
        try {
            Log.logger.warn("Fahre Aufgabenplaner herunter");
            if(SchedulerTask.AcyScheduler != null) {
                SchedulerTask.AcyScheduler.shutdown();
                Log.logger.warn("Jobs executed: " + getNumberJobsExecuted());
            }
        } catch (Exception e) {
             Log.logger.fatal("Fehler beim herunterfahren des Aufgabenplaners", e);
        }
    }

    public static void shutdownScheduler(boolean force){
        try {
            Log.logger.warn("Der Aufgabenplanner wird beendet");
            if(SchedulerTask.AcyScheduler != null) {
                SchedulerTask.AcyScheduler.shutdown(force);
                Log.logger.warn("Jobs executed: " + getNumberJobsExecuted());
            }
        } catch (Exception e) {
             Log.logger.fatal("Fehler beim herunterfahren des Aufgabenplaners", e);
        }
    }

    public static void startScheduler(){
        try {
            SchedulerTask.AcyScheduler.start();
        } catch (Exception e) {
            Log.logger.fatal("Fehler konnte Scheduler nicht starten", e);
        }
    }

    public static SchedulerMetaData getSchedulerMetaData(){
        SchedulerMetaData metaData = null;
        try {
            metaData = SchedulerTask.AcyScheduler.getMetaData();
        } catch (Exception e) {
            Log.logger.warn("Error at getting MetaData for Scheduler", e);
        }
        return metaData;
    }

    public static int getNumberJobsExecuted(){
        return getSchedulerMetaData().getNumberOfJobsExecuted();
    }

}
