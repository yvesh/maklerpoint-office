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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class Log extends Thread {

    public static final int LOG_FATAL = 0;
    public static final int LOG_WARNING = 1;
    public static final int LOG_INFO = 2;
    public static final int LOG_DEBUG = 3;
    public static final int LOG_ALL = 4;
    
    public static Logger logger = Logger.getLogger("de.maklerpoint.Basic");
    public static Logger pluginlogger = Logger.getLogger("de.maklerpoint.PluginLogger");
    public static Logger databaselogger = Logger.getLogger("de.maklerpoint.DatabaseLogger");

    public static StringBuilder completeLog = new StringBuilder();

    public static int[] levellog = new int[]{0,0,0,0}; // 0 fatal, 1 warning, 2 info, 3 error

    
    public static void setLevel(int level) {
        setLevel(InitializeLog.getLoglevel(level));
    }
    
    public static void setLevel(Level level) {
        InitializeLog.currentLevel = level;
        logger.setLevel(level);       
        pluginlogger.setLevel(level);
        databaselogger.setLevel(level);
    }

}
