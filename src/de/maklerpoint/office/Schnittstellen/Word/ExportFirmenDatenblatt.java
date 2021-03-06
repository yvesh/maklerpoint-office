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
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.FirmenHashtable;
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
 * @author yves
 */
public class ExportFirmenDatenblatt {

    private String filename;
    private FirmenObj firma;
    private String templatePath = Filesystem.getTemplatePath() +
            File.separatorChar + "word" + File.separatorChar + "geschaeftskunden_datenblatt.xml";
    private Hashtable ht = new Hashtable();

    public ExportFirmenDatenblatt(String filename, FirmenObj firma) {
        this.filename = filename;
        this.firma = firma;
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
                        //thisLine= thisLine.replaceAll("##" + name.toUpperCase() + "##", value);
                }
                writer.write(thisLine);
                writer.newLine();
                i++;
        }
        writer.close();
    }

    private void generateHashTable(){
        ht.putAll(FirmenHashtable.generatFirmenhash(firma));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        //TODO
        hashPut("VERSICHERUNG1", "");
        hashPut("VERSICHERUNG2", "");
        hashPut("VERSICHERUNG3", "");
        hashPut("VERSICHERUNG4", "");
        hashPut("VERSICHERUNG5", "");
        hashPut("VERSICHERUNG1TEXT", "");
        hashPut("VERSICHERUNG2TEXT", "");
        hashPut("VERSICHERUNG3TEXT", "");
        hashPut("VERSICHERUNG4TEXT", "");
        hashPut("VERSICHERUNG5TEXT","");

        hashPut("DATUM", df.format(new Date(System.currentTimeMillis())));
    }

    private String XmlEncode(String text) {
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



//        hashPut("KUNDENNUMMER", firma.getKundenNr());
//        hashPut("FIRMENNAME", firma.getFirmenName());
//
//        if(firma.getParentFirma() == -1)
//            hashPut("MUTTERKONZERN", "Keiner");
//        else
//            hashPut("MUTTERKONZERN", firma.getParentFirma());
//
//        hashPut("FIRMENNAMEZUSATZ", firma.getFirmenNameZusatz());
//        hashPut("FIRMENNAMEZUSATZ2", firma.getFirmenNameZusatz2());
//        hashPut("STREET", firma.getFirmenStrasse());
//        hashPut("PLZ", firma.getFirmenPLZ());
//        hashPut("ORT", firma.getFirmenStadt());
//        hashPut("LAND", firma.getFirmenLand());
//        hashPut("POSTFACH", firma.getFirmenPostfachName());
//        hashPut("POSTFACHPLZ", firma.getFirmenPostfachPlz());
//        hashPut("POSTFACHORT", firma.getFirmenPostfachOrt());
//
//        hashPut("KOMMTYPE1", CommunicationTypes.getName(firma.getCommunication1Type()));
//        hashPut("KOMMTYPE2", CommunicationTypes.getName(firma.getCommunication2Type()));
//        hashPut("KOMMTYPE3", CommunicationTypes.getName(firma.getCommunication3Type()));
//        hashPut("KOMMTYPE4", CommunicationTypes.getName(firma.getCommunication4Type()));
//        hashPut("KOMMTYPE5", CommunicationTypes.getName(firma.getCommunication5Type()));
//        hashPut("KOMMTYPE6", CommunicationTypes.getName(firma.getCommunication6Type()));
//
//        hashPut("KOMMUNIKATION1", firma.getCommunication1());
//        hashPut("KOMMUNIKATION2", firma.getCommunication2());
//        hashPut("KOMMUNIKATION3", firma.getCommunication3());
//        hashPut("KOMMUNIKATION4", firma.getCommunication4());
//        hashPut("KOMMUNIKATION5", firma.getCommunication5());
//        hashPut("KOMMUNIKATION6", firma.getCommunication6());
//
//        hashPut("FIRMENGROESSE", firma.getFirmenSize());
//        hashPut("UMSATZ", firma.getFirmenEinkommen());
//        hashPut("PROKURA", ArrayStringTools.arrayToString(firma.getFirmenProKura(), ", "));
//        hashPut("RECHTSFORM", firma.getFirmenRechtsform());
//        hashPut("BRANCHE", firma.getFirmenBranche());
//        hashPut("GESCHAEFTSFUEHRER", firma.getFirmenGeschaeftsfuehrer());
//        hashPut("GRUENDUNG", df2.format(firma.getFirmenGruendungDatum()));
//
//        hashPut("CREATED", df.format(firma.getCreated()));
//        hashPut("MODIFIED", df.format(firma.getModified()));
//
//        hashPut("BETREUER", BenutzerRegistry.getBenutzer(firma.getBetreuer(), true));
//
//        
}
