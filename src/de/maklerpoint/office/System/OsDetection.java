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
public class OsDetection {

        /**
     * Tries to detect the current Operating System on os.name
     * Falls back to windows if none found
     * Returns Operating Systems id (int), use static vars on Environment class to
     * identify them.. Environment.LINUX is for example 0
     *
     * Todo: based on "os.name" should be changed
     * @return OS (int representing Operating System, see Environment class)
     */

    public static int getOS(){
        String os = System.getProperty("os.name").toLowerCase();


        if(os.contains("linux"))
            Environment.OS = Environment.LINUX;
        else if(os.contains("mac os"))
            Environment.OS = Environment.MACOSX;
        else if(os.contains("solaris"))
            Environment.OS = Environment.SOLARIS;
        else if(os.contains("freebsd"))
            Environment.OS = Environment.FREEBSD;
        else if(os.contains("windows"))
            Environment.OS = Environment.WINDOWS;
        else {
            Environment.OS = Environment.WINDOWS; // Falling back to windows..
            // Todo: Insert a logging warning
        }

        /* We need to fallback here to windows, cause on some windows system
         os.name returns unknown ...! */

        return Environment.OS;
    }

    /**
     * Sets (forces) the os to the given OS id (see Environment for details)
     * @param os
     */

    public static void setOS(int os){
        Environment.OS = os;
    }

}
