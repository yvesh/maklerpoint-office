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

import de.maklerpoint.office.Backup.BackupFiletypes;
import de.maklerpoint.office.System.Configuration.Config;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class FilesystemBackup {

    /**
     * 
     * @return
     */

    public static String getBackupPath() {
        String filePath = Filesystem.getRootPath();

        String path = filePath + File.separatorChar + Config.get("backupOrdner", "backup");

        return path;
    }

    /**
     * 
     * @return
     */

    public static String getBackupFilename() {
        int type = Config.getConfigInt("backupType", BackupFiletypes.ZIP);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String filename = "maklerpoint_backup_" + df.format(new Date(System.currentTimeMillis())) + BackupFiletypes.getFileEnding(type);
        
        return filename;
    }
}
