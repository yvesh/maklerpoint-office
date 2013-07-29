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

package de.maklerpoint.office.Gui.Kunden;

import de.maklerpoint.office.Gui.Firmenkunden.NewFirmenKundeDialog;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.FilesystemKunden;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Gui.Tabs.TabPanelAnsprechpartner;
import de.maklerpoint.office.Gui.Tools.ComboBoxGetter;
import de.maklerpoint.office.Konstanten.Anreden;
import de.maklerpoint.office.Konstanten.Beruf;
import de.maklerpoint.office.Konstanten.Bundeslaender;
import de.maklerpoint.office.Konstanten.Laender;
import de.maklerpoint.office.Konstanten.Steuer;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.System.Status;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NewKundeHelper {

    private static JDialog newKundeBox;
    private static JDialog newKundeBoxUpdate;

    /**
     * 
     */

    public static void openNewKundeBox(){
        if (newKundeBox == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            newKundeBox = new NewKundeDialog(mainFrame, false);
            newKundeBox.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(newKundeBox);
    }

    /**
     * 
     * @param kunde
     */

    public static void openNewKundeBox(KundenObj kunde){
        if (newKundeBoxUpdate == null) {
            JFrame mainFrame = CRM.getApplication().getMainFrame();
            newKundeBoxUpdate = new NewKundeDialog(mainFrame, false, kunde);
            newKundeBoxUpdate.setLocationRelativeTo(mainFrame);
        }
        CRM.getApplication().show(newKundeBoxUpdate);
    }
    
    /**
     * 
     * @param land
     * @param dial
     */

    public static void loadBundeslaender(int land, NewKundeDialog dial) {
        switch(land) {
            default:
            case Bundeslaender.DEUTSCHLAND:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));
                break;

            case Bundeslaender.OESTERREICH:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_OESTERREICH));
                break;


            case Bundeslaender.SCHWEIZ:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_SCHWEIZ));
                break;

        }

        dial.combo_bundesland.revalidate();
    }

    /**
     * 
     * @param land
     * @param dial
     */

    public static void loadBundeslaender(int land, NewKundenAdresse dial) {
        switch(land) {
            default:
            case Bundeslaender.DEUTSCHLAND:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));
                break;

            case Bundeslaender.OESTERREICH:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_OESTERREICH));
                break;


            case Bundeslaender.SCHWEIZ:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_SCHWEIZ));
                break;

        }

        dial.combo_bundesland.revalidate();
    }
    
    /**
     * 
     * @param land
     * @param dial
     */

    public static void loadBundeslaender(int land, NewFirmenKundeDialog dial) {
        switch(land) {
            default:
            case Bundeslaender.DEUTSCHLAND:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_DEUTSCHLAND));
                break;

            case Bundeslaender.OESTERREICH:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_OESTERREICH));
                break;


            case Bundeslaender.SCHWEIZ:
                dial.combo_bundesland.setModel(new DefaultComboBoxModel(Bundeslaender.BUNDESLAENDER_SCHWEIZ));
                break;

        }

        dial.combo_bundesland.revalidate();
    }

    /**
     *
     */

    public static void loadWerber(NewKundeDialog dial){
        dial.combo_werber.setModel(new DefaultComboBoxModel(ComboBoxGetter.getAlleKundenCombo("Keiner", Status.NORMAL)));
        dial.combo_werber.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadWerber(NewFirmenKundeDialog dial){
        KundenObj[] kunden = KundenRegistry.getKunden();
        Object[] combos = new Object[kunden.length + 1];
        combos[0] = "Unbekannt";

        for(int i=1; i <= kunden.length; i++) {
            combos[i] = kunden[i - 1];
        }

        // Add Benutzer
        dial.combo_werber.setModel(new DefaultComboBoxModel(combos));
        dial.combo_werber.revalidate();
    }

    /**
     *
     */

    public static void loadBetreuer(NewKundeDialog dial, boolean update) {
        dial.combo_betreuer.setModel(new DefaultComboBoxModel(BenutzerRegistry.getAllBenutzer(Status.NORMAL)));
        if(update == false) {
            dial.combo_betreuer.setSelectedItem(BasicRegistry.currentUser);
        }

        dial.combo_betreuer.revalidate();
    }

    /**
     * 
     * @param dial
     * @param update
     */

    public static void loadBetreuer(NewFirmenKundeDialog dial, boolean update) {
        dial.combo_betreuer.setModel(new DefaultComboBoxModel(BenutzerRegistry.getAllBenutzer(Status.NORMAL)));
        if(update == false) {
            dial.combo_betreuer.setSelectedItem(BasicRegistry.currentUser);
        }

        dial.combo_betreuer.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadAnreden(NewKundeDialog dial) {
        dial.combo_anrede.setModel(new DefaultComboBoxModel(Anreden.ANREDEN));
        dial.combo_anrede.revalidate();
    }

    public static void loadAnreden(TabPanelAnsprechpartner dial) {
        dial.combo_anrede.setModel(new DefaultComboBoxModel(Anreden.ANREDEN));
        dial.combo_anrede.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadTitel(NewKundeDialog dial) {
        dial.combo_titel.setModel(new DefaultComboBoxModel(Anreden.TITEL));
        dial.combo_titel.revalidate();
    }

    public static void loadTitel(TabPanelAnsprechpartner dial) {
        dial.combo_titel.setModel(new DefaultComboBoxModel(Anreden.TITEL));
        dial.combo_titel.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadLaender(NewKundeDialog dial) {
        dial.combo_land.setModel(new DefaultComboBoxModel(Laender.LAENDER));
        dial.combo_land.revalidate();
    }


    /**
     * 
     * @param dial
     */

     public static void loadLaender(NewKundenAdresse dial) {
        dial.combo_land.setModel(new DefaultComboBoxModel(Laender.LAENDER));
        dial.combo_land.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadLaender(NewFirmenKundeDialog dial) {
        dial.combo_land.setModel(new DefaultComboBoxModel(Laender.LAENDER));
        dial.combo_land.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadBerufsStatus(NewKundeDialog dial) {
        dial.combo_berufstatus.setModel(new DefaultComboBoxModel(Beruf.BERUFS_STATUS));
        dial.combo_berufstatus.revalidate();
    }

    /**
     *
     * @param dial
     */

    public static void loadBerufsBesonderheiten(NewKundeDialog dial) {
        dial.combo_berufbesonderheiten.setModel(new DefaultComboBoxModel(Beruf.BERUFS_BESONDERHEITEN));
        dial.combo_berufbesonderheiten.revalidate();
    }


    /**
     * 
     * @param dial
     */

    public static void loadSteuerArten(NewKundeDialog dial) {
        dial.combo_steuerart.setModel(new DefaultComboBoxModel(Steuer.STEUER_ARTEN));
        dial.combo_steuerart.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadSteuerKlassen(NewKundeDialog dial) {
        dial.combo_steuerklasse.setModel(new DefaultComboBoxModel(Steuer.STEUER_KLASSEN));
        dial.combo_steuerklasse.revalidate();
    }

    /**
     * 
     * @param dial
     */

    public static void loadSteuerKirche(NewKundeDialog dial) {
        dial.combo_kirchensteuer.setModel(new DefaultComboBoxModel(Steuer.STEUER_KIRCHE));
        dial.combo_kirchensteuer.revalidate();
    }


    public static void doCreationTasks(KundenObj kunde) {
        try {
            FilesystemKunden.createKundenDirectory(kunde);
        } catch (IOException e) {
            Log.logger.fatal("Konnte Verzeichnisstruktur für den Kunden nicht erstellen", e);
            ShowException.showException("Beim erstellen der Vereichnisstruktur für den Kunden ist ein schwerer Fehler aufgetretten.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Kunden nicht speichern");            
        }
    }

    public static void doCreationTasks(FirmenObj firma) {
        try {
            FilesystemKunden.createKundenDirectory(firma);
        } catch (IOException e) {
            Log.logger.fatal("Konnte Verzeichnisstruktur für den Kunden nicht erstellen", e);
            ShowException.showException("Beim erstellen der Vereichnisstruktur für den Kunden ist ein schwerer Fehler aufgetretten.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte den Kunden nicht speichern");
        }
    }

}
