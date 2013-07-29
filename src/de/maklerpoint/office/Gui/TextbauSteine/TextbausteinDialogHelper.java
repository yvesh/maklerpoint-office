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

package de.maklerpoint.office.Gui.TextbauSteine;

import de.maklerpoint.office.start.CRM;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author yves
 */
public class TextbausteinDialogHelper {

    public static boolean open = false;

    public static void openTb() {
        if(open == false || tbBox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tbBox = new TextbausteinDialog(mainFrame, true);
            tbBox.setLocationRelativeTo(mainFrame);
            tbBox.toFront();

            CRM.getApplication().show(tbBox);
        } else {
            CRM.getApplication().show(tbBox);
            tbBox.toFront();
        }
    }

    public static void openTb(JTextField field) {
        if(open == false || tbBox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tbBox = new TextbausteinDialog(mainFrame, true, field);
            tbBox.setLocationRelativeTo(mainFrame);
            tbBox.toFront();

            CRM.getApplication().show(tbBox);
        } else {
            CRM.getApplication().show(tbBox);
            tbBox.toFront();
        }
    }

    public static void openTb(JTextArea area) {
        if(open == false || tbBox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            tbBox = new TextbausteinDialog(mainFrame, true, area);
            tbBox.setLocationRelativeTo(mainFrame);
            tbBox.toFront();

            CRM.getApplication().show(tbBox);
        } else {
            CRM.getApplication().show(tbBox);
            tbBox.toFront();
        }
    }

    private static JDialog tbBox;

}
