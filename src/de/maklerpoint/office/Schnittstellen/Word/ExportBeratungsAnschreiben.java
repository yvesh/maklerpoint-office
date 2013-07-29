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

import de.maklerpoint.office.Schnittstellen.Word.Hashtables.GenerateAnsprechpartnerHashtable;
import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
import de.maklerpoint.office.Briefe.Tools.BriefTools;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Briefe.Tools.FirmenKundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
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

public class ExportBeratungsAnschreiben {

    private String filename;
    private KundenObj kunde;
    private FirmenObj firma;

    private BeratungsprotokollObj protokoll;
    private String templatePath = Filesystem.getTemplatePath() +
            File.separatorChar + "word" + File.separatorChar + "anschreiben-beratungsprotokoll.xml";
    private Hashtable ht = new Hashtable();
    private String betreff;

    public ExportBeratungsAnschreiben(String filename, KundenObj kunde,
            BeratungsprotokollObj protokoll, String betreff) {
        this.filename = filename;
        this.kunde = kunde;
        this.protokoll = protokoll;
        this.betreff = betreff;
    }

     public ExportBeratungsAnschreiben(String filename, FirmenObj firma,
            BeratungsprotokollObj protokoll, String betreff) {
        this.filename = filename;
        this.firma = firma;
        this.protokoll = protokoll;
        this.betreff = betreff;
    }

    public void write() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(templatePath));
        File destination = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
        generateHashTable();
        generateMandanteninfo();
        generateAnsprech();
        generateAnschriftHash();

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

    private void generateHashTable(){
        hashPut("ANSCHREIBEN", protokoll.getKundenAnschreiben());
        hashPut("BETREFF", betreff);

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        hashPut("DATUM", df.format(new Date(System.currentTimeMillis())));

        StringBuilder anlagen = new StringBuilder();

        anlagen.append("Anlagen:");
        anlagen.append("\n");
        anlagen.append("\n");

        if(protokoll.isCheckBeratungsDokuVerzicht()) {
            anlagen.append("Beratungs- und Dokumentationsverzicht\n");
        }

        if(protokoll.isCheckBeratungsDokumentation()) {
            anlagen.append("Beratungsdokumentation\n");
        }

        if(protokoll.isCheckInformationsPflichten()) {
            anlagen.append("Informationspflichten\n");
        }
                
        if(protokoll.isCheckDruckstuecke() && protokoll.getDokumente() != null) {
            for(int i = 0; i < protokoll.getDokumente().length; i++) {
                String dokument = protokoll.getDokumente()[i];
                anlagen.append(dokument);
                anlagen.append("\n");
            }
        }

        hashPut("ANLAGEN", anlagen.toString());
    }

    public void generateAnschriftHash() {
        if(kunde != null)
            ht.putAll(KundenBriefTools.generateBriefAdresse(kunde));
        else if(firma != null)
            ht.putAll(FirmenKundenBriefTools.generateBriefAdresse(firma));
    }

    public void generateMandanteninfo() {
        ht.putAll(MandantenHashtable.generateMandantenhash(true));
    }

    public void generateAnsprech() {
        ht.putAll(GenerateAnsprechpartnerHashtable.generateAnsprechpartnerHashmap());
    }

    private static String XmlEncode(String text) {
            int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
            for(int i = 0; i < charsRequiringEncoding.length - 1; i++) {
                    text = text.replaceAll(String.valueOf((char)charsRequiringEncoding[i]),"&#"+charsRequiringEncoding[i]+";");
            }

            text = text.replaceAll("\n", "<w:br w:type=\"text-wrapping\"/>");
            return text;
    }

    private void hashPut(String val, Object val2) {
        if(val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if(val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }
    

}
