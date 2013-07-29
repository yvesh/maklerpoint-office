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

package de.maklerpoint.office.Dokumente.Trigger;

import de.maklerpoint.office.Dokumente.Tools.DokumentenTrigger;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Schnittstellen.Word.ExportMaklerAuftrag;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MaklerAuftragTrigger implements DokumentenTrigger {

    public void execute(String filename, KundenObj kunde) {
       ExportMaklerAuftrag exp = new ExportMaklerAuftrag(filename, kunde, BasicRegistry.currentMandant, BasicRegistry.currentUser);
       try {
            exp.write();
       } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte den Maklerauftrag nicht erstellen.", e);
            ShowException.showException("Konnte den Maklerautrag nicht generieren.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Maklerauftrag nicht generieren");            
       }
    }


    public void execute(String filename, FirmenObj firma) {
       ExportMaklerAuftrag exp = new ExportMaklerAuftrag(filename, firma, BasicRegistry.currentMandant, BasicRegistry.currentUser);
       try {
            exp.write();
       } catch (Exception e) {
            Log.logger.fatal("Fehler: Konnte den Maklerauftrag nicht erstellen.", e);
            ShowException.showException("Konnte den Maklerautrag nicht generieren.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte Maklerauftrag nicht generieren");            
       }
    }
}
