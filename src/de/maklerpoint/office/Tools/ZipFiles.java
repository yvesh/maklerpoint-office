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

package de.maklerpoint.office.Tools;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ZipFiles {

    public static boolean zipFiles(String filename, String[] filePaths) throws ArchiveException, IOException {
        File zipFile = new File(filename);

        if(!zipFile.exists()) {
            if(!zipFile.mkdirs()) {  
                throw new IOException("Das Archiv konnte nicht erstellt werden");
            }
        }

        if ( !zipFile.isArchive() )
        {
            throw new IOException("Die Zipdatei ist kein Archiv");
        }


        if ( !zipFile.isDirectory() )
        {
            throw new IOException("Die Zipdatei ist kein Verzeichnis");
        }


        for(int i = 0; i < filePaths.length; i++) {
            File toAdd = new File(filePaths[i], DefaultArchiveDetector.NULL);
            File zipEntry = new File( zipFile, toAdd.getName(), ArchiveDetector.NULL );

            if(!toAdd.copyTo(zipEntry))
                throw new IOException("Fehler beim hinzufügen der Datei " + toAdd.getAbsolutePath() + " zum Archiv");
        }

        File.umount();

        return true;
    }

}
