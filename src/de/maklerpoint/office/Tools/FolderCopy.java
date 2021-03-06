/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class FolderCopy {
    
     public static void copyDir(File quelle, File ziel) throws FileNotFoundException, IOException { 
         FileCopy fc = new FileCopy();
         File[] files = quelle.listFiles();
         ziel.mkdirs();
         
         for(File file: files) {
             if (file.isDirectory()) { 
                 copyDir(file, new File(ziel.getAbsolutePath() + File.separator + file.getName()));
             } else {
                 fc.copy(file, new File(ziel.getAbsolutePath() + File.separator + file.getName()));
             }
         }
     }
    
}
