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

package de.maklerpoint.office.Backup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BackupInfoFile {

    private String filename;
    private BackupObj backup;

    public BackupInfoFile(String filename, BackupObj backup){
        super();
        this.filename = filename;
        this.backup = backup;
    }

    public void write() throws IOException {
        File file = new File(filename);

        StringBuilder index = new StringBuilder();

        index.append("[MaklerPoint Backup]\n");
        index.append("Timestamp=");
        index.append(new java.sql.Timestamp(System.currentTimeMillis()));
        index.append("\n");
        index.append("\n");
        index.append("[Backup]\n");
        index.append("Id=");
        index.append(backup.getId());
        index.append("\nType=");
        index.append(backup.getType());
        index.append("\nBenutzerId=");
        index.append(backup.getBenutzerId());
        index.append("\nCreated=");
        index.append(backup.getCreated());
        index.append("\nAutomatic=");
        index.append(backup.isAutomatic());
        index.append("\nSuccess=");
        index.append(backup.isSuccess());

        FileWriter writer = new FileWriter(file);

        writer.write(index.toString());
        writer.close();
        
    }

}
