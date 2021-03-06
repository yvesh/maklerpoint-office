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

import de.maklerpoint.office.Gui.Log.LogPanel;
import de.maklerpoint.office.System.Configuration.Config;
import java.io.File;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;


/**
 * Initializes and sets up the Log system, should only run once
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class InitializeLog extends Thread{

    public static Level currentLevel;
    
    public static void initialize(){
        Log.logger = Logger.getLogger("de.maklerpoint.Basic");
        Log.pluginlogger = Logger.getLogger("de.maklerpoint.PluginLogger");
        Log.databaselogger = Logger.getLogger("de.maklerpoint.DatabaseLogger");

        // Setting up console appender

//        ConsoleAppender console = new ConsoleAppender(Layouts.getConsoleLayout());
//        Log.logger.addAppender(console);
//        Log.pluginlogger.addAppender(console);
//        Log.databaselogger.addAppender(console);

        // Setting up file logging

        RollingFileAppender basicFile = Appenders.createRollingFileAppender(Layouts.getFileLayout(),
                Config.LOG_DIR + File.separatorChar + "basic.log", true);
        Log.logger.addAppender(basicFile);
        

        RollingFileAppender pluginFile = Appenders.createRollingFileAppender(Layouts.getFileLayout(),
                Config.LOG_DIR + File.separatorChar + "plugin.log", true);
        Log.pluginlogger.addAppender(pluginFile);

        RollingFileAppender databaseFile = Appenders.createRollingFileAppender(Layouts.getFileLayout(),
                Config.LOG_DIR + File.separatorChar + "database.log", true);
        Log.databaselogger.addAppender(databaseFile);

        try {
            TextAreaAppender logTextArea = Appenders.createTextAreaAppender(Layouts.getConsoleLayout(), LogPanel.class.newInstance());
            Log.logger.addAppender(logTextArea);
            Log.databaselogger.addAppender(logTextArea);
            Log.pluginlogger.addAppender(logTextArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Setting up Syslog or Windows (NT) Event logging

        // SyslogAppender syslogServer = SyslogAppenders.createSyslogAppender(Layouts.getSyslogLayout(), "127.0.0.1", SyslogAppender.LOG_AUTH);
        // Log.serverlogger.addAppender(syslogServer);
     
        // Setting up level

        currentLevel = getLoglevel();
        
        Log.setLevel(currentLevel);

        // Initialize complete

        Log.logger.info("Das Logsystem wurde erfolgreich gestartet. (Level " + currentLevel.toString() + ")");
    }

    public static Level getCurrentLogLevel(){
        return currentLevel;
    }
    
    public static Level getLoglevel(){
        int loglevel = Config.LOG_LEVEL;
        Level log = Level.WARN;

        switch (loglevel){
            case 0:
                log = Level.FATAL;
                break;

            case 1:
                log = Level.WARN;
                break;

            case 2:
                log = Level.INFO;
                break;

            case 3:
                log = Level.DEBUG;
                break;

            case 4:
                log = Level.ALL;
                break;

            default:
                log = Level.WARN;
                break;
        }

        
        
        return log;
    }

    /**
     * Returns the loglevel on the given int
     * @param loglevel
     * @return Level (org.apache.log4j.Level)
     */

     public static Level getLoglevel(int loglevel){
        Level log = Level.WARN;

        switch (loglevel){
            case 0:
                log = Level.FATAL;
                break;

            case 1:
                log = Level.WARN;
                break;

            case 2:
                log = Level.INFO;
                break;

            case 3:
                log = Level.DEBUG;
                break;

            case 4:
                log = Level.ALL;
                break;

            default:
                log = Level.WARN;
                break;
        }

        return log;
    }

}
