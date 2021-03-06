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
package de.maklerpoint.office.Schnittstellen.Word;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefTools;
import de.maklerpoint.office.Briefe.Tools.FirmenKundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.VersichererBriefTools;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.BenutzerHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.FirmenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.GenerateAnsprechpartnerHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.KundenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.MandantenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.ProduktHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.StoerfallHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.VersichererHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.VertragHashtable;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ExportBrief {

    private int type = -1;
    private int kdtype = -1;
    public static final int PRIVAT = 0;
    public static final int GESCH = 1;
    public static final int VERSICHERER = 2;
    public static final int PRODUKT = 3;
    public static final int VERTRAG = 4;
    public static final int STOERFALL = 5;
    public static final int BENUTZER = 6;
    private String zielfile;
    private BriefObj brief = null;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private String kdnr = null;
    private String text = null;
    private VersichererObj vers = null;
    private Object stoer = null;
    private VertragObj vertr = null;
    private ProduktObj prod = null;
    private BenutzerObj ben = null;
    private ZusatzadressenObj za = null;
    private Hashtable ht = new Hashtable();
    private SimpleDateFormat df_day = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat df_hour = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /**
     * KUNDEN BRIEF
     * @param brief
     * @param zielfile
     * @param kunde 
     */
    public ExportBrief(BriefObj brief, String zielfile, KundenObj kunde) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.kunde = kunde;
        this.type = 0;
        if (kunde.getBetreuerId() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(kunde.getBetreuerId());
        }

        this.kdtype = PRIVAT;
    }
    
    public ExportBrief(BriefObj brief, String zielfile, KundenObj kunde, ZusatzadressenObj za) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.kunde = kunde;
        this.za = za;
        this.type = 0;
        if (kunde.getBetreuerId() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(kunde.getBetreuerId());
        }

        this.kdtype = PRIVAT;
    }

    /**
     * FIRMEN BRIEF
     * @param brief
     * @param zielfile
     * @param firma 
     */
    public ExportBrief(BriefObj brief, String zielfile, FirmenObj firma) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.firma = firma;
        this.type = GESCH;
        if (this.firma.getBetreuer() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(firma.getBetreuer());
        }


        this.kdtype = GESCH;
    }
    
    public ExportBrief(BriefObj brief, String zielfile, FirmenObj firma, ZusatzadressenObj za) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.firma = firma;
        this.za = za;
        this.type = GESCH;
        if (this.firma.getBetreuer() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(firma.getBetreuer());
        }

        this.kdtype = GESCH;
    }

    /**
     * VERSICHERER BRIEF
     * @param brief
     * @param zielfile
     * @param vers 
     */
    public ExportBrief(BriefObj brief, String zielfile, VersichererObj vers) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.vers = vers;
        this.type = VERSICHERER;
    }

    /**
     * PRODUKT BRIEF
     * @param brief
     * @param zielfile
     * @param prod 
     */
    public ExportBrief(BriefObj brief, String zielfile, ProduktObj prod) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.prod = prod;

        if (prod.getVersichererId() != -1) {
            this.vers = VersicherungsRegistry.getVersicher(prod.getVersichererId());
        }
        this.type = PRODUKT;
    }

    /**
     * VERTRAG BRIEF
     * @param brief
     * @param zielfile
     * @param vertrag 
     */
    public ExportBrief(BriefObj brief, String zielfile, VertragObj vertrag) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.vertr = vertrag;

        if (vertrag.getVersichererId() != -1) {
            this.vers = VersicherungsRegistry.getVersicher(vertrag.getVersichererId());
        }

        if (vertrag.getProduktId() != -1) {
            this.prod = VersicherungsRegistry.getProdukt(vertrag.getProduktId());
        }

        if (vertrag.getBenutzerId() != -1) {
            this.ben = BenutzerRegistry.getBenutzer(vertrag.getBenutzerId(), true);
        }

        if (!vertrag.getKundenKennung().equalsIgnoreCase("-1")) {
            Object knd = KundenRegistry.getKunde(vertrag.getKundenKennung());
            if (knd != null) {
                try {
                    kunde = (KundenObj) knd;
                    this.kdnr = kunde.getKundenNr();
                    kdtype = PRIVAT;
                } catch (Exception e) {
                    firma = (FirmenObj) knd;
                    this.kdnr = firma.getKundenNr();
                    kdtype = GESCH;
                }
            }
        }

        this.type = VERTRAG;
    }

    /**
     * STOERFALL BRIEF (NICHT FERTIG)
     * @param brief
     * @param zielfile
     * @param stoerfall 
     */
    public ExportBrief(BriefObj brief, String zielfile, Object stoerfall) {
        this.brief = brief;
        this.zielfile = zielfile;
        //this.vertr = vertrag; TODo
        this.type = STOERFALL;
    }

    /**
     * BENUTZER BRIEF
     * @param brief
     * @param zielfile
     * @param ben 
     */
    public ExportBrief(BriefObj brief, String zielfile, BenutzerObj ben) {
        this.brief = brief;
        this.zielfile = zielfile;
        this.ben = ben;
        this.type = BENUTZER;
    }

    public void write() throws FileNotFoundException, IOException {
        String path = brief.getFullpath();

        if (!brief.getFullpath().startsWith("/")) {
            path = Filesystem.getRootPath() + File.separatorChar + brief.getFullpath();
        }

        BufferedReader reader = new BufferedReader(new FileReader(path));
        File destination = new File(zielfile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
        generateHashTable();
        generateBriefTable();

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
        if (kunde != null) {
            ht.putAll(KundenHashtable.generatKundenhash(kunde, true));
        }
        if (firma != null) {
            ht.putAll(FirmenHashtable.generatFirmenhash(firma));
        }
        if (vers != null) {
            ht.putAll(VersichererHashtable.generateVersichererhash(vers));
        }
        if (ben != null) {
            ht.putAll(BenutzerHashtable.generateBenutzerhash(ben, true));
        } else {
            ht.putAll(BenutzerHashtable.generateBenutzerhash(BasicRegistry.currentUser, true));
        }
        if (prod != null) {
            ht.putAll(ProduktHashtable.generateProdukthash(prod));
        }
        if (stoer != null) {
            ht.putAll(StoerfallHashtable.generateStoerfallhash(stoer));
        }
        if (vertr != null) {
            ht.putAll(VertragHashtable.generatVersichererhash(vertr));
        }
    }

    private void generateBriefTable() {
        if (kunde != null || firma != null) {
            hashPut("ANSCHRIFT", generateKundenAnschrift());
            if (kunde != null) {
                hashPut("ANSCHRIFT_EINZEILER", BriefTools.generateKundenAnschrift(kunde));
            } else {
                hashPut("ANSCHRIFT_EINZEILER", BriefTools.generateKundenAnschrift(firma));
            }

            if (kunde != null) {
                hashPut("ANREDE", KundenBriefTools.getKundenBriefAnrede(kunde));
            } else {
                hashPut("ANREDE", FirmenKundenBriefTools.getKundenBriefAnrede());
            }
        } else {
            if(vers != null) {
                hashPut("ANSCHRIFT", BriefTools.generateVersichererAnschricht(vers));
                hashPut("ANREDE", VersichererBriefTools.getVersichererAnrede(vers));
                hashPut("ANSCHRIFT_EINZEILER", VersichererBriefTools.getVersichererAnschriftONLINE(vers));                
            }                        
        }

        
        
        if (ben != null) { // TODO FIX
            ht.putAll(GenerateAnsprechpartnerHashtable.generateAnsprechpartnerHashmap(ben, BasicRegistry.currentMandant));
        } else {
            ht.putAll(GenerateAnsprechpartnerHashtable.generateAnsprechpartnerHashmap());
        }
        
        if(za != null) {
            // TODO Add Overwrite
            
        }

        hashPut("TEXT", text);

        hashPut("DATUM", df_day.format(new Date(System.currentTimeMillis())));
    }

    private String generateKundenAnschrift() {

        String anschrift = "";
        if (kunde != null) {
            anschrift = BriefTools.generateKundenAnschrift(kunde);
        } else if (firma != null) {
            anschrift = BriefTools.generateKundenAnschrift(firma);
        }

        return anschrift;
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
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if (val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }

    /**
     * --------------------------------------
     * Getter setter
     * --------------------------------------
     */
    public BenutzerObj getBen() {
        return ben;
    }

    public void setBen(BenutzerObj ben) {
        this.ben = ben;
    }

    public BriefObj getBrief() {
        return brief;
    }

    public void setBrief(BriefObj brief) {
        this.brief = brief;
    }

    public String getZielfile() {
        return zielfile;
    }

    public void setZielfile(String zielfile) {
        this.zielfile = zielfile;
    }

    public FirmenObj getFirma() {
        return firma;
    }

    public void setFirma(FirmenObj firma) {
        this.firma = firma;
    }

    public KundenObj getKunde() {
        return kunde;
    }

    public void setKunde(KundenObj kunde) {
        this.kunde = kunde;
    }

    public ProduktObj getProd() {
        return prod;
    }

    public void setProd(ProduktObj prod) {
        this.prod = prod;
    }

    public VersichererObj getVers() {
        return vers;
    }

    public void setVers(VersichererObj vers) {
        this.vers = vers;
    }

    public VertragObj getVertr() {
        return vertr;
    }

    public void setVertr(VertragObj vertr) {
        this.vertr = vertr;
    }

    public String getKdnr() {
        return kdnr;
    }

    public void setKdnr(String kdnr) {
        this.kdnr = kdnr;
    }

    public int getKdtype() {
        return kdtype;
    }

    public void setKdtype(int kdtype) {
        this.kdtype = kdtype;
    }

    public Object getStoer() {
        return stoer;
    }

    public void setStoer(Object stoer) {
        this.stoer = stoer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
