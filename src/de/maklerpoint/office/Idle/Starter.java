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


/**
 * Main class for the Computer Status Watcher, here you setup and start the Computer Watcher Thread
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class Starter {

    private RunWatcher runTask;

    private String Online_Jobname;
    private Class Online_class;
    private String Away_Jobname;
    private Class Away_class;
    private String Idle_Jobname;
    private Class Idle_class;

    public Starter(){
        super();
    }

    /**
     * Adds a Trigger to the Starter. You need to at at least one for
     * ComputerState.Online and ComputerState.Idle
     *
     * Use static ComputerState.YOURSTATE vars to define your trigger, else
     * an unknown state exception is thrown
     *
     * @param trigger
     * @throws UnknownStateException
     */
    
    public void scheduleJob(IdleTrigger trigger){
        if (trigger.getClassState() == ComputerState.ONLINE){
            Online_Jobname = trigger.getJobName();
            Online_class = trigger.getJobClass();
        } else if (trigger.getClassState() == ComputerState.AWAY){
            Away_Jobname = trigger.getJobName();
            Away_class = trigger.getJobClass();
        } else if (trigger.getClassState() == ComputerState.IDLE){
            Idle_Jobname = trigger.getJobName();
            Idle_class = trigger.getJobClass();
        } else if (trigger.getClassState() == ComputerState.UNKNOWN){
            // do nothing
        }
    }

    /**
     * Returns the jobname of the given state
     * @param state
     * @return jobname
     */

    public String getJobName(int state){
        if (state == ComputerState.ONLINE)
            return Online_Jobname;
        else if (state == ComputerState.AWAY)
            return Away_Jobname;
        else if (state == ComputerState.IDLE)
            return Idle_Jobname;
        else
            return "Unknown";
    }

    /**
     * Starts the RunWatcher Thread, before you do this you need to setup the Starter correctly
     * @throws NoClassForStateException
     */

    public void start()  {
        checkDependency();
        runTask = new RunWatcher(Online_Jobname, Online_class, Away_Jobname, Away_class, Idle_Jobname, Idle_class);
        new Thread(runTask).start();
    }

    /**
     * Interrupts the RunWatcher Thread
     */

    public void shutdown(){
        runTask.interrupt();
    }

    /**
     * Checks if all dependencies for the RunWatcher are met, else an NoClassForStateException is thrown
     * @return boolean true if all dependencies are fulfilled
     * @throws NoClassForStateException
     */

    public boolean checkDependency() {
        
        return true;
    }
}
