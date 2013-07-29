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

import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.BenutzerHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.KundenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.MandantenHashtable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportPrivatKundenInfoblatt {

    private String filename;
    private KundenObj kunde;
    private String templatePath = Filesystem.getTemplatePath()
            + File.separatorChar + "word" + File.separatorChar + "privatkundeninfoblatt.xml";
    private Hashtable ht = new Hashtable();

    public ExportPrivatKundenInfoblatt(String filename, KundenObj kunde) {
        super();
        this.filename = filename;
        this.kunde = kunde;
    }

    public void write() throws FileNotFoundException, IOException {
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
//                        thisLine= thisLine.replaceAll("##" + name.toUpperCase() + "##", value);
            }
            writer.write(thisLine);
            writer.newLine();
            i++;
        }
        writer.close();
    }

    private void generateHashTable() {

        ht.putAll(MandantenHashtable.generateMandantenhash(true));
        ht.putAll(KundenHashtable.generatKundenhash(kunde, true));
        ht.putAll(BenutzerHashtable.generateBenutzerhash(BenutzerRegistry.getBenutzer(
                kunde.getBetreuerId()), true));        
        
        //TODO              
        hashPut("VERTRAG1", "");
        hashPut("VERTRAG2", "");
        hashPut("VERTRAG3", "");
        hashPut("VERTRAG4", "");
        hashPut("VERTRAG5", "");

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        hashPut("DATUM", df.format(new Date(System.currentTimeMillis())));
    }

    private static String XmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for (int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char) charsRequiringEncoding[i]), "&#" + charsRequiringEncoding[i] + ";");
        }

        text = text.replaceAll("\n", "<w:br w:type=\"text-wrapping\"/>");
        return text;
    }

    private void hashPut(String val, Object val2) {
        if (val == null) {
            return;
        }

        if (val2 == null) {
            val2 = "";
        }

        ht.put(val, val2);
    }
}
