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
 * Class that represents a trigger object for Computer Watcher (use Starter)
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class IdleTrigger extends Thread {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_PAUSED = 1;
    public static final int STATE_COMPLETE = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_BLOCKED = 4;
    public static final int STATE_NONE = -1;

    private String jobName;
    private int State;
    private String jobDescription;
    private Class jobClass;

    /**
     * Creates a new trigger object. Use static vars in ComputerState to specify
     * the state. Jobclass needs to implement Job!!
     * @param jobname - the name of the job, just for logging, threadnames etc.
     * @param state - the state on which the trigger should be executed
     * @param jobclass - the class which should be executed when trigger is executed
     */

    public IdleTrigger(String jobname, int state, Class jobclass){
        super(jobname + state);
        setName(jobname);
        setClassState(state);
        setJobClass(jobclass);
    }

    /**
     * Creates a new trigger object. Use static vars in ComputerState to specify
     * the state. Jobclass needs to implement Job!!
     * @param jobname - the name of the job, just for logging, threadnames etc.
     * @param description 
     * @param state - the state on which the trigger should be executed
     * @param jobclass - the class which should be executed when trigger is executed
     */

    public IdleTrigger(String jobname, String description, int state, Class jobclass){
        super(jobname + state);
        setJobName(jobname);
        setJobDescription(description);
        setClassState(state);
        setJobClass(jobclass);
    }

    public String getJobName(){
        return jobName;
    }

    public String getJobDescription(){
        return jobDescription;
    }

    public int getClassState(){
        return State;
    }

    public Class getJobClass(){
        return jobClass;
    }

    public void setJobName(String jobname) {
        this.jobName = jobname;
    }

    public void setClassState(int state) {
        this.State = state;
    }

    public void setJobClass(Class jobclass){
        this.jobClass = jobclass;
    }

    public void setJobDescription(String jobdescription){
        this.jobDescription = jobdescription;
    }
}
