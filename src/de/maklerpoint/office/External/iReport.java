/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.External;

import de.maklerpoint.office.System.Environment;
import de.maklerpoint.office.Tools.ExternalProgramsRunner;
import java.io.File;
import java.lang.Void;
import javax.swing.SwingWorker;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class iReport {

    public static void runIREPORT() {
        // TODO think about a way adding a datasource to iReport
//        SwingWorker sw = new SwingWorker<Void, Void>(){
//
//            @Override
//            protected Void doInBackground() throws Exception {                        
//                if (Environment.isLinux() || Environment.isMac()) {
                    ExternalProgramsRunner.runProgram("sh platform" + File.separatorChar + "ext"
                            + File.separatorChar + "iReport" + File.separator + "bin" + File.separator + "ireport");
//                } else {
//                    ExternalProgramsRunner.runProgram("platform" + File.separatorChar + "ext"
//                            + File.separatorChar + "iReport" + File.separator + "bin" + File.separator + "ireport.exe");
//                }
//                return null;
//            }
//        };

    }
}
