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
import org.apache.log4j.PatternLayout;

/**
 * Some predefined Layout methods
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class Layouts extends Thread {

    public static String getSyslogLayoutString() {
        return "%d{ISO8601} %-5p [%t] %c{1} %x - %m%n";
    }

    public static Layout getSyslogLayout() {
        return new PatternLayout("%d{ISO8601} %-5p [%t] %c{1} %x - %m%n");
    }

    public static String getConsoleLayoutString() {
        return "%d{ISO8601} %-5p [%t] %c{1} %x - %m%n";
    }

    public static Layout getConsoleLayout() {
        return new PatternLayout("%d{ISO8601} %-5p [%t] %c{1} %x - %m%n");
    }

    public static String getFileLayoutString() {
        return "%d{ISO8601} %-5p [%t] %c{1} %x - %m%n";
    }

    public static Layout getFileLayout() {
        return new PatternLayout("%d{ISO8601} %-5p [%t] %c{1} %x - %m%n");
    }
}
