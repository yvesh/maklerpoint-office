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

package de.maklerpoint.office.Filesystem;

import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Tools.FolderCopy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class InitializeFilesystem {
    
    /**
     * 
     * @param ziel
     * @return Initialisiere Dateisystem, sollte nur einmal vorkommen :)
     * @throws FileNotFoundException
     * @throws IOException 
     */
    
    public static boolean initialize(String ziel) throws FileNotFoundException, IOException {
        Log.logger.warn("Initialisiere Dateisystem");
        
        File zielfile = new File(ziel);
        
        if(!zielfile.isDirectory())
            return false;                     
        
        FolderCopy.copyDir(new File("includes" + File.separator + "localstorage"), zielfile);
                
        return true;
    }    
    
}