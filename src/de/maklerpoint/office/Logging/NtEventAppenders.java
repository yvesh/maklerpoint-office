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

package de.maklerpoint.office.Logging;

import org.apache.log4j.Layout;
import org.apache.log4j.nt.NTEventLogAppender;

/**
 * Static functions to create Appenders for NT Event logging
 * used in Windows. (works with Windows NT, 2000, XP, Vista, 7 or newer)
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */


public class NtEventAppenders extends Thread {

    public static NTEventLogAppender createNTEventLogAppender (String server, String source, Layout layout){
        NTEventLogAppender appender = null;
        try {
            appender = new NTEventLogAppender(server, source, layout);
        } catch (Exception e) {
        }

        return appender;
    }

    public static NTEventLogAppender createNTEventLogAppender (String source, Layout layout){
        NTEventLogAppender appender = null;
        try {
            appender = new NTEventLogAppender(source, layout);
        } catch (Exception e) {
        }
        return appender;
    }

    public static NTEventLogAppender createNTEventLogAppender (String source){
        NTEventLogAppender appender = null;
        try {
            appender = new NTEventLogAppender(source);
        } catch (Exception e) {
        }
        
        return appender;
    }

    public static NTEventLogAppender createNTEventLogAppender (Layout layout){
        NTEventLogAppender appender = null;
        try {
            appender = new NTEventLogAppender(layout);
        } catch (Exception e) {
        }
        
        return appender;
    }

    public static NTEventLogAppender createNTEventLogAppender(){
        NTEventLogAppender appender = null;
        try {
            appender = new NTEventLogAppender();
        } catch (Exception e) {
        }
        return appender;
    }

}