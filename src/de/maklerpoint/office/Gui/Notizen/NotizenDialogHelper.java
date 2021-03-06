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

package de.maklerpoint.office.Gui.Notizen;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Notizen.NotizenObj;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author yves
 */
public class NotizenDialogHelper {

     public static boolean open = false;

     public static void openNotizen() {
         if(open == false || notizenDialog == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            notizenDialog = new NotizenDialog(mainFrame, true);
            notizenDialog.setLocationRelativeTo(mainFrame);
            notizenDialog.toFront();

            CRM.getApplication().show(notizenDialog);
         } else {
            CRM.getApplication().show(notizenDialog);
            notizenDialog.toFront();
         }
     }

     public static void openNotizen(NotizenObj notiz) {
         if(open == false || notizenDialog == null) {
            open = true;
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            notizenDialog = new NotizenDialog(mainFrame, true, notiz);
            notizenDialog.setLocationRelativeTo(mainFrame);
            notizenDialog.toFront();

            CRM.getApplication().show(notizenDialog);
         } else {
            CRM.getApplication().show(notizenDialog);
            notizenDialog.toFront();
         }
     }

     private static JDialog notizenDialog;

}
