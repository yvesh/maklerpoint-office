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

package de.maklerpoint.office.Tools;

import de.maklerpoint.office.Logging.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Helper for external programs
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class ExternalProgramsHelper extends Thread {

    /**
     * 
     * @param cmd
     * @return 
     */
    
    public static String getLinuxPath(String cmd){
        String path = null;
        String ls_str = null;

        try {
            Process ls_proc = Runtime.getRuntime().exec("which " + cmd);
            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

            while ((ls_str = ls_in.readLine()) != null) {
                path = ls_str.trim();
             }

            ls_in.close();
        } catch (Exception e) {
                 Log.logger.warn("Konnte den Pfad für den Befehl: " + cmd + " nicht herausfinden", e);
        }

        return path;
    }

    /**
     * 
     * @param cmd
     * @return 
     */
    
    public static boolean checkForProgram(String cmd){
        boolean runs = true;
        String test;
        String ls_str = null;

        try {
            Process ls_proc = Runtime.getRuntime().exec(cmd);
            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

             while ((ls_str = ls_in.readLine()) != null) {
                test = ls_str.trim();
             }

            ls_in.close();
        } catch (Exception e) {                
                runs = false;
        }
        return runs;
    }

}
