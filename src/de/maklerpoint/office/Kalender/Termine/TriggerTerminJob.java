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

package de.maklerpoint.office.Kalender.Termine;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Kalender.TerminErinnerungDialog;
import de.maklerpoint.office.Gui.Leftpane.panelKarte;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TriggerTerminJob implements Job {


    /**
     * 
     * @param jec
     * @throws JobExecutionException
     */

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap data = jec.getJobDetail().getJobDataMap();
        TerminObj termin = (TerminObj) data.get("termin");

        JFrame mainFrame = CRM.getApplication().getMainFrame();
        JDialog terminDialog = new TerminErinnerungDialog(mainFrame, false, termin);
       
        terminDialog.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(terminDialog);
    }

}
