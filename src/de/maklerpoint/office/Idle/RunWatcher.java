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

package de.maklerpoint.office.Idle;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.IdleConfig;
import de.maklerpoint.office.System.Environment;
import de.maklerpoint.office.System.OsDetection;


/**
 * Main Thread watching the clients computer State, is started in Starter
 * runs the given trigger's that implement job
 * 
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class RunWatcher extends Thread {

    public static int STATE = 0;

    private String onlineJobname;
    private Class onlineClass;
    private String awayJobname;
    private Class awayClass;
    private String idleJobname;
    private Class idleClass;
    private IdleJob job = null;

    /**
     * Initializes and loads the RunWatcher.. Should not be run on it's on - use Starter instead
     * @param online_jobname
     * @param online_class
     * @param away_jobname
     * @param away_class
     * @param idle_jobname
     * @param idle_class
     */

    public RunWatcher(String online_jobname, Class online_class, String away_jobname, Class away_class,
                   String idle_jobname, Class idle_class){
        super("detect");
        this.onlineJobname = online_jobname;
        this.onlineClass = online_class;
        this.awayJobname = away_jobname;
        this.awayClass = away_class;
        this.idleJobname = idle_jobname;
        this.idleClass = idle_class;
    }

    /**
     * Watching sysmon Thread for switching between states and triggers
     */

    @Override
    public void run(){        
        ComputerStateEnum state = ComputerStateEnum.UNKNOWN;

        while ( ! isInterrupted() ){

            long idleSec;
            // based on OS

            if(Environment.OS == -1){
                // Log warning, that system is not initialized
                OsDetection.getOS();
            }                

            // Needs testing, using x11 detection for all unix osses...

            if(Environment.OS == Environment.LINUX || Environment.OS == Environment.FREEBSD
                                                   || Environment.OS == Environment.SOLARIS){
                LinuxIdleTime idle = new LinuxIdleTime();
                idleSec = idle.getIdleTimeMillisLinux() / 1000;
            } else if (Environment.OS == Environment.MACOSX){
                MacIdleTime idle = new MacIdleTime();
                idleSec = idle.getIdleTimeMillisecondsMac() / 1000;
            } else if (Environment.OS == Environment.WINDOWS){
                Win32IdleTime idle = new Win32IdleTime();
                idleSec = idle.getIdleTimeMillisWin32() / 1000;
            } else {
                // ADD WARNING
                Win32IdleTime idle = new Win32IdleTime();
                idleSec = idle.getIdleTimeMillisWin32() / 1000;
            }

            Log.logger.debug("Sekunden ohne Benutzerinteraktion: " + idleSec);
            
            try {
                ComputerStateEnum newState = ComputerStateEnum.UNKNOWN;
                
                if (idleSec < IdleConfig.STATE_AWAY_TIME){
                    newState = ComputerStateEnum.ONLINE;
                    STATE = ComputerState.ONLINE;
                } else if (IdleConfig.STATE_AWAY_TIME >= 30 && idleSec < IdleConfig.STATE_IDLE_TIME) {
                    newState = ComputerStateEnum.AWAY;
                    STATE = ComputerState.AWAY;
                } else if (idleSec >= IdleConfig.STATE_IDLE_TIME) {
                    newState = ComputerStateEnum.IDLE;
                    STATE = ComputerState.IDLE;
                } else {
                    newState = ComputerStateEnum.UNKNOWN;
                    STATE = ComputerState.UNKNOWN;
                }
                
                if (newState != state) {
                    state = newState;
                
                    if (state == state.ONLINE) {                   
                        job = (IdleJob) onlineClass.newInstance();
                        job.execute();
                    } else if (state == state.AWAY) {
                        // Not this importend, could be null
                        if(awayClass != null) {
                            job = (IdleJob) awayClass.newInstance();
                            job.execute();
                        }
                    } else if (state == state.IDLE) {
                        job = (IdleJob) idleClass.newInstance();
                        job.execute();
                    } 
                }

                // Checking every x milliseconds for new state
                Log.logger.debug("Deaktiviere PC Status Überwachungsthread für " + IdleConfig.STATE_POLLING_TIME + " Sekunden");
                Thread.sleep(IdleConfig.STATE_POLLING_TIME * 1000L);                
            } catch(Exception e) {
                Log.logger.warn("Fehler beim initialisieren des Computerstatuses.", e);
            }
        }
    }
}