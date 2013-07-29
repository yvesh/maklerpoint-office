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

package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.start.CRM;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MaximizeHelper {

    public static boolean open = false;

    public static void openMax(JTextArea area, String title) {
        if(open == false || maxBox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            maxBox = new MaximizeDialog(mainFrame, true, area, title);
            maxBox.setLocationRelativeTo(mainFrame);
            maxBox.toFront();

            CRM.getApplication().show(maxBox);
        } else {
            CRM.getApplication().show(maxBox);
            maxBox.toFront();
        }
    }

    public static void openMax(JTextField field, String title) {
        if(open == false || maxBox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            maxBox = new MaximizeDialog(mainFrame, true, field, title);
            maxBox.setLocationRelativeTo(mainFrame);
            maxBox.toFront();

            CRM.getApplication().show(maxBox);
        } else {
            CRM.getApplication().show(maxBox);
            maxBox.toFront();
        }
    }


    private static JDialog maxBox;
}
