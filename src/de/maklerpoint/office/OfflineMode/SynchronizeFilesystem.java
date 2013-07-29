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

package de.maklerpoint.office.OfflineMode;

import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class SynchronizeFilesystem {

    public static void synchronizeFilesystem() {
        try {
            FileUtils.copyDirectory(new File(Filesystem.getRootPath()),
                    new File("includes" + File.separatorChar + "localstorage" + File.separatorChar + "filesystem"));
        } catch (IOException e) {
            Log.logger.fatal("Fehler: Konnte Dateisystem nicht synchronisieren", e);
            ShowException.showException("Konnte das Dateisystem nicht synchronisieren.",
                ExceptionDialogGui.LEVEL_WARNING, e,
                "Schwerwiegend: Konnte Dateisystem nicht synchronisieren");
        }
    }

}
