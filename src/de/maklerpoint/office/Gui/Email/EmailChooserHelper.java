/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       27.07.2011 12:01:15
 *  File:       EmailChooserHelper
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */
package de.maklerpoint.office.Gui.Email;

import de.maklerpoint.office.start.CRM;
import javax.swing.JFrame;

/**
 * 
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class EmailChooserHelper {
    
    /**
     * 
     * @return E-Mail
     */
    public static String getEmail(){        
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        EmailChooserDialog auswahl = new EmailChooserDialog(mainFrame, true);
        auswahl.setLocationRelativeTo(mainFrame);                

        CRM.getApplication().show(auswahl);
        
        String mail = auswahl.getReturnStatus();
        
        return mail;
    }
    
}
