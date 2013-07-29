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
package de.maklerpoint.office.Schnittstellen.Word;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.BenutzerHashtable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class ExportBenutzerDatenblatt {

    private String filename;
    private BenutzerObj benutzer;
    private String templatePath = Filesystem.getTemplatePath()
            + File.separatorChar + "word" + File.separatorChar + "benutzerdatenblatt.xml";
    private Hashtable ht = new Hashtable();

    public ExportBenutzerDatenblatt(String filename, BenutzerObj benutzer) {
        super();
        this.filename = filename;
        this.benutzer = benutzer;
    }

    public void write() throws FileNotFoundException, IOException, SQLException {
        BufferedReader reader = new BufferedReader(new FileReader(templatePath));
        File destination = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
        generateHashTable();

        String thisLine;
        int i = 0;

        while ((thisLine = reader.readLine()) != null) {
//                System.out.println(i);
            for (java.util.Enumeration e = ht.keys(); e.hasMoreElements();) {
                String name = (String) e.nextElement();
                String value = ht.get(name).toString();
                // Use this if we need to XML-encode the string in hashtable value...
                thisLine = thisLine.replaceAll("##" + name.toUpperCase() + "##", XmlEncode(value));
//                        System.out.println("Thisline: " + thisLine);
//                        System.out.println("Name: " + name.toUpperCase());
                // ... or this if we do not need to do XML-encode.
                //thisLine= thisLine.replaceAll("##" + name.toUpperCase() + "##", value);
            }
            writer.write(thisLine);
            writer.newLine();
            i++;
        }
        writer.close();
    }

    private void generateHashTable() throws SQLException {
        ht.putAll(BenutzerHashtable.generateBenutzerhash(benutzer, true));

        //TODO
        hashPut("KUNDENCOUNT", "0");
        hashPut("KUNDENCOUNTMAKLER", "0");
        hashPut("PRIVATKUNDENCOUNT", "0");
        hashPut("FIRMENKUNDENCOUNT", "0");

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        hashPut("DATUM", df.format(new Date(System.currentTimeMillis())));
    }

    private static String XmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for (int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char) charsRequiringEncoding[i]), "&#" + charsRequiringEncoding[i] + ";");
        }
        return text;
    }

    private void hashPut(String val, Object val2) {
        if (val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if (val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }
}
