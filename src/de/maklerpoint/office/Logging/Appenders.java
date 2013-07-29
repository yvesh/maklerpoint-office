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
import java.io.FileOutputStream;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;

/**
 * static functions around creating appenders for logging
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class Appenders extends Thread {

    public static RollingFileAppender createRollingFileAppender(Layout layout, String filename, boolean append){
        RollingFileAppender appender = null;
        try {
            appender = new RollingFileAppender(layout, filename, append);
            appender.setMaximumFileSize(Config.LOG_SIZE);
            appender.setMaxBackupIndex(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static RollingFileAppender createRollingFileAppender(Layout layout, String filename){
        RollingFileAppender appender = null;
        try {
            appender = new RollingFileAppender(layout, filename);
            appender.setMaximumFileSize(Config.LOG_SIZE);
            appender.setMaxBackupIndex(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

     public static FileAppender createFileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize){
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, filename, append, bufferedIO, bufferSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static FileAppender createFileAppender(Layout layout, String filename, boolean append){
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, filename, append);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }
    
    public static FileAppender createFileAppender(SimpleLayout layout, String filename, boolean append){
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, filename, append);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static FileAppender createFileAppender(SimpleLayout layout, String filename){
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static FileAppender createFileAppender(Layout layout, String filename){
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }
    
    public static ConsoleAppender createConsoleAppender(){
        ConsoleAppender appender = new ConsoleAppender();
        return appender;
    }

    public static ConsoleAppender createConsoleAppender(Layout layout){
        ConsoleAppender appender = new ConsoleAppender(layout);
        return appender;
    }
    
    public static ConsoleAppender createConsoleAppender(Layout layout, String target){
        ConsoleAppender appender = new ConsoleAppender(layout);
        return appender;
    }

    public static WriterAppender createHtmlAppender(String filename){
        WriterAppender appender = null;
        HTMLLayout layout = new HTMLLayout();

        try {
            FileOutputStream output = new FileOutputStream(filename);
            appender = new WriterAppender(layout, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static WriterAppender createWriterAppender(Layout layout, String filename){
        WriterAppender appender = null;

        try {
            FileOutputStream output = new FileOutputStream(filename);
            appender = new WriterAppender(layout, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appender;
    }

    public static TextAreaAppender createTextAreaAppender(Layout layout, LogPanel log){
        TextAreaAppender appender = new TextAreaAppender(layout);
        appender.setTextArea(log);
        return appender;
    }

}
