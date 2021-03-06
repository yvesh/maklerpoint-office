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

import java.util.Calendar;
import java.util.Date;
import org.quartz.TriggerUtils;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SchedulerTime {

    /**
     * 
     * @return
     */

    public static long getOneMinuteFromNow(){
        long minute = TriggerUtils.getNextGivenMinuteDate(null, 1).getTime();
        return minute;
    }

    /**
     * 
     * @return
     */

    public static long getOneHourFromNow(){
        long hour = TriggerUtils.getNextGivenMinuteDate(null, 59).getTime();
        return hour;
    }

    /**
     * 
     * @return
     */

    public static long getOneDayFromNow(){
        Date tommorow = new Date(System.currentTimeMillis() + (24*60*60*1000));
        long day = TriggerUtils.getNextGivenMinuteDate(tommorow, 0).getTime();
        return day;
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */

    public static boolean sameDay(Calendar c1, Calendar c2) {
          return (
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) ) &&
              ( c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) );
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */

    public static boolean afterToday(Calendar c1, Calendar c2) {
        if(c1.getTimeInMillis() < c2.getTimeInMillis())
            return true;
        else
            return false;
    }

}
