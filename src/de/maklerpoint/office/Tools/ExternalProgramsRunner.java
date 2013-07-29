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
 * Class with static methods for running external programs
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */
public class ExternalProgramsRunner extends Thread {

    public static String runProgram(String cmd) {
//        System.out.println("Running: " + cmd);
//        String programreturn = null;
//        String ls_str = null;

        try {
            Process ls_proc = Runtime.getRuntime().exec(cmd);
//            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
//
//            while ((ls_str = ls_in.readLine()) != null) {
//                programreturn = ls_str.trim();
//            }
//
//            ls_in.close();
        } catch (Exception e) {
            Log.logger.warn("Konnte externes Programm " + cmd + " nicht ausführen", e);
        }
        return null;
    }
    
    
    public static String runProgramAndGetOutput(String cmd) {
//        System.out.println("Running: " + cmd);
        String programreturn = null;
        String ls_str = null;

        try {
            Process ls_proc = Runtime.getRuntime().exec(cmd);
            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

            while ((ls_str = ls_in.readLine()) != null) {
                programreturn = ls_str.trim();
            }

            ls_in.close();
        } catch (Exception e) {
            Log.logger.warn("Konnte externes Programm " + cmd + " nicht ausführen", e);
        }
        return programreturn;
    }
}
