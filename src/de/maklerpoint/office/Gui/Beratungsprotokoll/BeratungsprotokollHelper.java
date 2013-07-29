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

package de.maklerpoint.office.Gui.Beratungsprotokoll;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class BeratungsprotokollHelper {

    public static boolean open = false;

    public static void open(KundenObj kunde) {
        if(open == false) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            beratungsBox = new BeratungsprotokollDialog(mainFrame, true, kunde);
            beratungsBox.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(beratungsBox);
        } else {
            JOptionPane.showMessageDialog(null, "Das Beratungsprotokoll ist schon geöffnet");
        }
    }

    public static void open(FirmenObj kunde) {
        if(open == false) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            beratungsBox = new BeratungsprotokollDialog(mainFrame, true, kunde);
            beratungsBox.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(beratungsBox);
        } else {
            JOptionPane.showMessageDialog(null, "Das Beratungsprotokoll ist schon geöffnet");
        }
    }

    private static JDialog beratungsBox;

}
