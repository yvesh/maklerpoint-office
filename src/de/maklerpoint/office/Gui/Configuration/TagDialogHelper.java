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

package de.maklerpoint.office.Gui.Configuration;

import de.maklerpoint.office.start.CRM;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TagDialogHelper {

    public static void showTagDialog() {
        if(tagSettingsBox == null) {
          JFrame mainFrame = CRM.getApplication().getMainFrame();
          tagSettingsBox = new TagDialog(mainFrame, true);
          tagSettingsBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(tagSettingsBox);
    }

    private static JDialog tagSettingsBox;
}
