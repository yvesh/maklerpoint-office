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

package de.maklerpoint.office.Kalender;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KalenderBerechnungen {

    private static final String[] wochentage = {"Freitag", "Samstag",
    "Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag"};


    public static String getWochenTag(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int t = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        int j = cal.get(Calendar.YEAR);

        // System.out.println("Day week: " + cal.get(Calendar.DAY_OF_WEEK));

        int day = cal.get(Calendar.DAY_OF_WEEK);
        
        if(day == Calendar.SUNDAY) {
            return "Sonntag"; 
        } else if (day == Calendar.MONDAY) {
            return "Montag"; 
         } else if (day == Calendar.TUESDAY) {
            return "Dienstag";
         } else if (day == Calendar.WEDNESDAY) {
            return "Mittwoch";
         } else if (day == Calendar.THURSDAY) {
            return "Donnerstag";
         } else if (day == Calendar.FRIDAY) {
            return "Freitag";
         } else if (day == Calendar.SATURDAY) {
            return "Samstag";
         }

//        if(day == 7 )
//
//        int m2, j2=j;
//
//        if(m<2)
//        {
//            m2 =m+10;
//            j2 = j-1;
//        } else {
//            m2=m-2;
//        }
//
//        int c = j2/100;
//
//        int y = j2%100;
//
//        int h = (((26*m2-2)/10)+t+y+y/4+c/4-2*c)%7;
//
//        if(h<0)
//                h=h+7;
//
////        System.out.println(h);
//
//        if(h==0)
//            return "Dienstag";
//
//        if(h==1)
//            return "Mittwoch";
//
//        if(h==2)
//            return "Donnerstag";
//
//        if(h==3)
//            return "Freitag";
//
//        if(h==4)
//            return "Samstag";
//
//        if(h==5)
//            return "Sonntag";
//
//        if(h==6)
//            return "Montag";
     

        return null;
    }


}
