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
import org.apache.log4j.net.SyslogAppender;

/**
 * Static functions to create Syslog Appenders
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class SyslogAppenders extends Thread {

    public static SyslogAppender createSyslogAppender(Layout layout, String syslogHost, int syslogFacility) {
        SyslogAppender appender = null;

        try {
            appender = new SyslogAppender(layout, syslogHost, syslogFacility);
            appender.setSyslogHost("127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appender;
    }

    public static SyslogAppender createSyslogAppender(Layout layout, int syslogFacility) {
        SyslogAppender appender = null;

        try {            
            appender = new SyslogAppender(layout, syslogFacility);
            appender.setSyslogHost("127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appender;
    }

}
