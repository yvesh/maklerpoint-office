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

package de.maklerpoint.office.Gui.Wissendokumente;

import de.maklerpoint.office.start.CRM;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author yves
 */
public class WissendokumenteDialogHelper {

     public static boolean open = false;

     public static void openTb() {
        if(open == false || wissenbox == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            wissenbox = new WissendokumenteDialog(mainFrame, true);
            wissenbox.setLocationRelativeTo(mainFrame);
            wissenbox.toFront();

            CRM.getApplication().show(wissenbox);
        } else {
            CRM.getApplication().show(wissenbox);
            wissenbox.toFront();
        }
    }

     private static JDialog wissenbox;
}
