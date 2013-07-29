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
import javax.swing.JFrame;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenAuswahlHelper {

    public static Object getKunde() {

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        KundenAuswahl auswahl = new KundenAuswahl(mainFrame, true);
        auswahl.setLocationRelativeTo(mainFrame);

        CRM.getApplication().show(auswahl);
       
        Object kunde = auswahl.getReturnStatus();
       
        return kunde;
    }

}
