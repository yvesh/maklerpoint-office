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

package de.maklerpoint.office.System;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class Environment {

     public static final int LINUX = 0;
        public static final int MACOSX = 1;
        public static final int SOLARIS = 2;
        public static final int FREEBSD = 3;
        public static final int WINDOWS = 4;

        public static int OS = -1;

        public static String OS_NAME = System.getProperty("os.name");
        public static String OS_ARCH = System.getProperty("os.arch");
        public static String OS_VERSION = System.getProperty("os.version");

        public static String VM_VENDOR = System.getProperty("java.vm.vendor");
        public static String VM_VERSION = System.getProperty("java.vm.version");

        public static String USERNAME = System.getProperty("user.name");
        public static String USERDIR = System.getProperty("user.dir");

        public static int processors = Runtime.getRuntime().availableProcessors();
        public static int maxmemory = (int) Runtime.getRuntime().maxMemory() / 1000000; // MB

        public static String cpudetection = System.getProperty("sun.cpu.isalist");
        
        public static boolean isLinux(){
            if(OS == LINUX)
                return true;
            else
                return false;
        }
        
        public static boolean isMac(){
            if(OS == MACOSX)
                return true;
            else
                return false;
        }
        
        public static boolean isWindows(){
            if(OS == WINDOWS)
                return true;
            else
                return false;
        }
}
