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
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportKundenInformationen {

    private String filename;    
    private Hashtable ht = new Hashtable();
    private String templatePath = Filesystem.getTemplatePath() +
            File.separatorChar + "word" + File.separatorChar + "kundeninformationen.xml";
    private MandantenObj mandant;

    public ExportKundenInformationen(String filename, MandantenObj mandant) {
        this.filename = filename;        
        this.mandant = mandant;
    }

     public void write() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(templatePath));
        File destination = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));

        ht.putAll(MandantenHashtable.generateMandantenhash(true));
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

    private void generateHashTable(){          
          hashPut("MNAME", mandant.getFirmenName());
          hashPut("MNAME2", mandant.getFirmenZusatz());

          hashPut("KUNDENINFORMATIONEN", getKuString());
    }

    private String getKuString() {
        StringBuilder sb = new StringBuilder();

        sb.append("1. ").append(mandant.getFirmenName()).append("\n");
        if(mandant.getFirmenZusatz() != null) {
            sb.append(mandant.getFirmenZusatz());
            sb.append("\n");
        }
        sb.append(mandant.getStrasse());
        sb.append("\n");
        sb.append(mandant.getPlz()).append(" ").append(mandant.getOrt());
        sb.append("\n");
        sb.append("\n");

        sb.append("2. Unser Status gemäß Gewerbeordnung\n");
        if(mandant.getBeraterTyp().equalsIgnoreCase("versicherungsmakler")) {
            sb.append("Wir sind als Versicherungsmakler nach § 34d Abs. 1 der Gewerbeordnung (GEWO) tätig ");
            sb.append("und im Vermittlerregister unter der Nummer ").append(mandant.getIhkRegistriernummer()).append(" registriert");
            sb.append("\n");
        } else if (mandant.getBeraterTyp().equalsIgnoreCase("versicherungsberater")) {
            
        } else {
            // fall back
            sb.append("Wir sind als Versicherungsmakler nach § 34d Abs. 1 der Gewerbeordnung (GEWO) tätig ");
            sb.append("und im Vermittlerregister unter der Nummer ").append(mandant.getIhkRegistriernummer()).append(" registriert");
            sb.append("\n");
        }

        sb.append("\n");

        if(mandant.is34c()) {
            sb.append("Wir verfügen ausserdem über eine Genehmigung anch § 34 c Gewerbeordnung.");
            sb.append("\n");
            sb.append("Ausstellendes Behörde: ");
            sb.append(mandant.getGewerbeamtName());
            sb.append(", ");
            sb.append(mandant.getGewerbeamtPLZ()).append(" ").append(mandant.getGewerbeamtOrt());
            sb.append("\n");
            sb.append("\n");
        }

        if(mandant.getVermoegensHaftpflicht() != null) {
             sb.append("Wir verfügen über eine Vermögensschaden-Haftpflicht mit einer Deckungssumme ")
                     .append(mandant.getVermoegensHaftpflicht().substring(0, 1).toLowerCase())
                     .append(mandant.getVermoegensHaftpflicht().substring(1));
             sb.append(".");
             sb.append("\n");
             sb.append("\n");
        }
        
        sb.append("3. Bei Interesse können Sie die Angaben bei der Registerstelle überprüfen:\n");
        sb.append("Deutscher Industrie- und Handelskammertag (DIHK) e.V.\n");
        sb.append("Breite Straße 29\n");
        sb.append("D-10178 Berlin\n");
        sb.append("Telefon: 0180 500 585 0*\n");
        sb.append("Homepage: www.vermittlerregister.info\n");
        sb.append("*(14 Cent/ Min. aus dem deutschen Festnetz, höchstens 42 Cent/Minute aus Mobilfunknetzen)\n");

        sb.append("\n");

        sb.append("4. Schlichtungsstellen für außergerichtliche Streitbeilegung:\n");
        sb.append("Versicherungsombudsmann e.V.\n");
        sb.append("Postfach 08 06 32\n");
        sb.append("10006 Berlin\n\n");

        sb.append("Ombudsmann private Kranken- und Pflegeversicherung\n");
        sb.append("Postfach 06 02 22\n");
        sb.append("10052 Berlin\n\n");

        sb.append("Ombudsmann der privaten Bausparkassen\n");
        sb.append("Postfach 30 30 79\n");
        sb.append("10730 Berlin\n\n");

        if(mandant.isBeteiligungenVU() == false) {
            sb.append("5. ").append(mandant.getFirmenName()).append(" besitzt weder direkte noch "
                    + "indirekte Beteiligungen von über 10 Prozent an den Stimmrechten "
                    + "oder am Kapital eines Versicherungsunternehmens.");
        } else {
            sb.append("5. ").append(mandant.getFirmenName()).append(" besitzt "
                    + "Beteiligungen von über 10 Prozent an den Stimmrechten "
                    + "oder am Kapital eines Versicherungsunternehmens.");
        }
        sb.append("\n\n");

        if(mandant.isBeteiligungenMAK() == false) {
            sb.append("6. Kein Versicherungsunternehmen oder Mutterunternehmen eines Versicherungsunternehmens" 
                    + "besitzt eine direkte oder indirekte Beteiligung an den Stimmrechten oder am Kaptial" 
                    + " von ").append(mandant.getFirmenName());
        } else {
            sb.append("6. Versicherungsunternehmen oder Mutterunternehmen eines Versicherungsunternehmens"
                    + "besitzten eine direkte oder indirekte Beteiligung an den Stimmrechten oder am Kaptial"
                    + " von ").append(mandant.getFirmenName());
        }
        sb.append("\n");

        return sb.toString();
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
