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
package de.maklerpoint.office.start;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Kunden.Tools.KundenKennungHelper;
import de.maklerpoint.office.Lucene.Indexer;
import de.maklerpoint.office.Lucene.SearchLucene;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Table.SchaedenUebersichtHeader;
import de.maklerpoint.office.Table.VertraegeUebersichtHeader;
import de.maklerpoint.office.Tags.Tags;
import de.maklerpoint.office.Tools.ArrayStringTools;
import de.maklerpoint.office.Tools.WaehrungFormat;
import java.net.URL;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.prefs.Preferences;
import javax.crypto.KeyGenerator;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class Tester {

    public static void test() {
        try {
//            for(long i = 2678751388L; i < 2678752388L; i++) {
//                if(i % 512 == 0){
//                    System.out.println("I: " + i);
//                }
//            }
//            generateKey();
//            Config.setCrypted("testCrypt", "ijustcametosayhelle2");
//            Config.getCrypted("testCrypt");

//            KundenKennungHelper.getNextKundennummer(DatabaseConnection.open());

//            ClassLoader classloader =
//               org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
//            URL res = classloader.getResource(
//                         "org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
//            String path = res.getPath();
//            System.out.println("Core POI came from " + path);

//            VertraegeUebersichtHeader.getColumnsWithField();
            //VertraegeHelper.createGrpAndUpdateVertraege(DatabaseConnection.open(), null);
            //VertraegeSQLMethods.getKundenVertraege(DatabaseConnection.open(), "11001", -1);        
            //System.out.println(Filesystem.getTmpPath());

//            
//             try {
//            SearchLucene.searchTest("kunde");
//            } catch (Exception ex) {
//                Exceptions.printStackTrace(ex);
//            }
//             
//            FilesystemMediaScanner.scan();

//            System.out.println("Columns: " + SchaedenUebersichtHeader.Columns.length);

//            System.out.println("Formatted: " + WaehrungFormat.getFormatedWaehrung(100.339, VersicherungsRegistry.getWaehrung(1)));
//            System.out.println("Columns: " + VertraegeUebersichtHeader.Columns.length);
//            Preferences prefs = Preferences.userRoot().node(Tags.class.getName());
//            prefs.put("tagListe", "Standard,Öffentlich,Wichtig,Dienstlich,Persönlich");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator generator;
        generator = KeyGenerator.getInstance("AES");
        generator.init(new SecureRandom());
        Key key = generator.generateKey();
        System.out.println("Key: " + key.getEncoded());
    }

    public static void main(String[] args) {
        Tester.test();
    }
}
