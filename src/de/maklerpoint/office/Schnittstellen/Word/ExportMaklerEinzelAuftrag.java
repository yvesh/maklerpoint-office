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
import de.maklerpoint.office.Beratungsprotokoll.BeratungsprotokollObj;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Briefe.Tools.FirmenKundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.MandantenHashtable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author yves
 */
public class ExportMaklerEinzelAuftrag {

    private String filename;
    private KundenObj kunde;
    private FirmenObj firma;
    private MandantenObj mandant;
    private BenutzerObj benutzer;
    private BeratungsprotokollObj bp;
    private String templatePath = Filesystem.getTemplatePath() +
            File.separatorChar + "word" + File.separatorChar + "maklereinzelauftrag.xml";
    private Hashtable ht = new Hashtable();

    public ExportMaklerEinzelAuftrag(String filename, KundenObj kunde,
            MandantenObj mandant, BenutzerObj benutzer) {
        this.filename = filename;
        this.kunde = kunde;        
        this.mandant = mandant;
        this.benutzer = benutzer;
    }

    public ExportMaklerEinzelAuftrag(String filename, KundenObj kunde,
            MandantenObj mandant, BenutzerObj benutzer, BeratungsprotokollObj bp) {
        this.filename = filename;
        this.kunde = kunde;
        this.bp = bp;
        this.mandant = mandant;
        this.benutzer = benutzer;
    }

    public ExportMaklerEinzelAuftrag(String filename, FirmenObj firma,
            MandantenObj mandant, BenutzerObj benutzer) {
        this.filename = filename;
        this.firma = firma;  
        this.mandant = mandant;
        this.benutzer = benutzer;
    }

     public ExportMaklerEinzelAuftrag(String filename, FirmenObj firma,
            MandantenObj mandant, BenutzerObj benutzer, BeratungsprotokollObj bp) {
        this.filename = filename;
        this.firma = firma;
        this.bp = bp;
        this.mandant = mandant;
        this.benutzer = benutzer;
    }

    private void generateHashTable(){
        hashPut("MNAME", mandant.getFirmenName());
        hashPut("MNAME2", mandant.getFirmenZusatz());

        hashPut("MAKLERANSCHRIFT", generateMaklerAnschrift());
        hashPut("KUNDENANSCHRIFT", generateKundenAnschrift());

        if(bp != null) {
            hashPut("VERSICHERUNGSSPARTE", bp.getVersicherungsSparte());
            hashPut("VERSICHERER", bp.getVersicherungsGesellschaft());
            hashPut("RAT", bp.getRat());
        } else {
            hashPut("VERSICHERUNGSSPARTE", "XXVersicherungssparte");
            hashPut("VERSICHERER", "XXVersicherer");
            hashPut("RAT", "XXEmpfehlung");
        }

        if(kunde != null)
            hashPut("KUNDENAME", kunde.getVorname() + " " + kunde.getNachname());
        else if (firma != null)
            hashPut("KUNDENAME", firma.getFirmenName());

        hashPut("MNAME", benutzer.getVorname() + " " + benutzer.getNachname());
    }

    private String generateMaklerAnschrift() {
        String anschrift = "";

        if(mandant.getFirmenZusatz() == null && mandant.getFirmenZusatz2() == null) {
            anschrift = mandant.getFirmenName() + "\n" + mandant.getStrasse()
                    + "\n" + mandant.getPlz() + " " + mandant.getOrt();
        } else {
            if(mandant.getFirmenZusatz() != null && mandant.getFirmenZusatz2() != null) {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz() + "\n"
                        + mandant.getFirmenZusatz2() + "\n" + mandant.getStrasse()
                        + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            } else if (mandant.getFirmenName() != null) {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz() + "\n"
                         + mandant.getStrasse() + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            } else {
                anschrift = mandant.getFirmenName() + "\n" + mandant.getFirmenZusatz2() + "\n"
                         + mandant.getStrasse() + "\n" + mandant.getPlz() + " " + mandant.getOrt();
            }
        }

        return anschrift;
    }

    private String generateKundenAnschrift() {

        String anschrift = "";
        if(kunde != null)
            anschrift = anschrift + KundenBriefTools.getKundenBriefAnschriftONELINE(kunde);
        else if(firma != null)
            anschrift = anschrift +  FirmenKundenBriefTools.getKundenBriefAnschriftONELINE(firma);

        return anschrift;
    }


    public void write () throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(templatePath));
        File destination = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
        generateHashTable();
        ht.putAll(MandantenHashtable.generateMandantenhash(true));


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
