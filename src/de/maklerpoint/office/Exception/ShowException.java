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
package de.maklerpoint.office.Exception;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ShowException {

    public static void showException(String message, int errorLevel, Throwable e,
            String windowname) {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exceptionBox = new ExceptionDialogGui(mainFrame, true, errorLevel,
                message, windowname, e);
        exceptionBox.setLocationRelativeTo(mainFrame);

        exceptionBox.setTitle(windowname);
        exceptionBox.toFront();
        CRM.getApplication().show(exceptionBox);
        exceptionBox.toFront();
    }

    public static void showException(String message, int errorLevel,
            String windowname) {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        exceptionBox = new ExceptionDialogGui(mainFrame, true, errorLevel,
                message, windowname, null);
        exceptionBox.setLocationRelativeTo(mainFrame);

        exceptionBox.setTitle(windowname);
        exceptionBox.toFront();
        CRM.getApplication().show(exceptionBox);
        exceptionBox.toFront();
    }
    private static JDialog exceptionBox;
}
