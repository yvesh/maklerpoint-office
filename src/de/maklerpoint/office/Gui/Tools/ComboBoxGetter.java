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
package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.Bank.BankKontoObj;
import de.maklerpoint.office.Bank.Tools.BankKontoSQLMethods;
import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Briefe.BriefCategoryObj;
import de.maklerpoint.office.Briefe.Tools.BriefeSQLMethods;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenAnsprechpartnerObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.FirmenAnsprechpartnerSQLMethods;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Registry.KundenRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Registry.VertragRegistry;
import de.maklerpoint.office.Schaeden.SchadenObj;
import de.maklerpoint.office.Stoerfalle.StoerfallObj;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Tools.ArrayTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.SQLException;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class ComboBoxGetter {

    /**
     * Alle Adressen inkl. die normale des Kunden
     * @param erstesObjekt
     * @param kdnr
     * @return 
     */
    public static Object[] getZusatzAdressenCombo(String erstesObjekt, String kdnr) {
        try {
            ZusatzadressenObj[] zb = ZusatzadressenSQLMethods.loadZusatzadressen(
                    DatabaseConnection.open(), kdnr, Status.NORMAL);
            if (zb != null) {
                return ArrayTools.arrayPlusOne(zb, erstesObjekt);
            } else {
                return new Object[]{"Standardadresse verwenden"};
            }
        } catch (Exception e) {
            Log.databaselogger.fatal("Fehler: Konnte Zusatzadressen nicht aus der Datenbank laden", e);
            ShowException.showException("Datenbankfehler: Die Kundenzusatzadressen konnten nicht geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadressen nicht laden");
        }

        return null;
    }

    /**
     * 
     * @param beginning
     * @return 
     */
    public static Object[] getVersichererCombo(String erstesObjekt) {
        VersichererObj[] vs = VersicherungsRegistry.getVersicherer(Status.NORMAL);
        if (vs != null) {
            return ArrayTools.arrayPlusOne(vs, erstesObjekt);
        } else {
            return new Object[]{"Keine Versicherer vorhanden"};
        }
    }

    /**
     * 
     * @param erstesObjekt
     * @param vsid
     * @param reload
     * @return 
     */
    public static Object[] getProduktCombo(String erstesObjekt, int vsid) {
        if (vsid == -1) {
            return new Object[]{"Keine Produkte vorhanden"};
        }

        ProduktObj[] pr = VersicherungsRegistry.getProduktVersicherer(vsid, Status.NORMAL);
        if (pr != null) {
            return ArrayTools.arrayPlusOne(pr, erstesObjekt);
        } else {
            return new Object[]{"Keine Produkte vorhanden"};
        }
    }

    /**
     * 
     * @param erstesObjekt
     * @param kdnr
     * @return 
     */
    public static Object[] getVertragCombo(String erstesObjekt, String kdnr) {
        VertragObj[] vtr = VertragRegistry.getKundenVertraege(kdnr);
        if (vtr != null) {
            if (erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(vtr, erstesObjekt);
            } else {
                return vtr;
            }
        } else {
            return new Object[]{"Keine Verträge vorhanden"};
        }
    }

    public static Object[] getVertragStoerfallCombo(String erstesObjekt, int vertragId) {
        StoerfallObj[] vtr = VertragRegistry.getVertragStoerfaelle(vertragId, Status.NORMAL);
        if (vtr != null) {
            if (erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(vtr, erstesObjekt);
            } else {
                return vtr;
            }
        } else {
            return new Object[]{"Keine Störfälle vorhanden"};
        }
    }

    public static Object[] getVertragSchaedenCombo(String erstesObjekt, int vertragId) {
        System.out.println("Hole Vertrags schaden");
        SchadenObj[] vtr = VertragRegistry.getVertragSchaeden(vertragId, Status.NORMAL);
        if (vtr != null) {

            System.out.println("Schäden vorhanden");
            if (erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(vtr, erstesObjekt);
            } else {
                return vtr;
            }
        } else {
            return new Object[]{"Keine Schäden vorhanden"};
        }
    }

    public static Object[] getDefaultKontoCombo(String erstesObjekt, String kdnr) {
        BankKontoObj[] kto = null;
        try {
            kto = BankKontoSQLMethods.getKonten(DatabaseConnection.open(), kdnr, Status.NORMAL);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (kto != null) {
            return ArrayTools.arrayPlusOne(kto, erstesObjekt);
        } else {
            return new Object[]{"Keine Konten vorhanden"};
        }
    }

    public static Object[] getDefaultFirmenAnsprechpartnerCombo(String erstesObjekt, String kdnr) {
        FirmenAnsprechpartnerObj[] ansp = null;
        try {
            ansp = FirmenAnsprechpartnerSQLMethods.loadAnsprechpartner(DatabaseConnection.open(), kdnr, Status.NORMAL);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (ansp != null) {
            return ArrayTools.arrayPlusOne(ansp, erstesObjekt);
        } else {
            return new Object[]{"Keine Ansprechpartner vorhanden"};
        }
    }

    /**
     * 
     * @param erstesObjekt
     * @return 
     */
    public static Object[] getBenutzerCombo(String erstesObjekt) {
        BenutzerObj[] ben = BenutzerRegistry.getAllBenutzer(Status.NORMAL);
        if (ben != null) {
            if (erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(ben, erstesObjekt);
            } else {
                return ben;
            }
        } else {
            return new Object[]{"Keine Benutzer vorhanden?!"};
        }
    }

    public static Object[] getBriefCategoriesCombo(String erstesObjekt) {
        try {
            BriefCategoryObj[] brf_cat = BriefeSQLMethods.getBriefCategories(DatabaseConnection.open(), Status.NORMAL);
            if (brf_cat != null && erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(brf_cat, erstesObjekt);
            } else if (brf_cat != null) {
                return brf_cat;
            } else {
                return new Object[]{"Keine Kategorien vorhanden"};
            }
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte die Brief Kategorien nicht aus der DB laden", e);
            ShowException.showException("Datenbankfehler: Die Briefkategorien konnten nicht aus der Datenbank geladen werden.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Briefkategorien nicht laden");
        }
        return null;
    }
    /**
     * 
     * @param erstesObjekt
     * @param reload
     * @return 
     */
    private static int _kundestatus = -1;
    private static Object[] knd = null;

    public static Object[] getAlleKundenCombo(String erstesObjekt, int status) {
        KundenObj[] privkunden = KundenRegistry.getKunden(status);
        FirmenObj[] firmkunden = KundenRegistry.getFirmenKunden(false, status);

        knd = null;

        if (privkunden == null && firmkunden == null) {
            return new Object[]{"Keine Kunden vorhanden"};
        } else if (privkunden != null && firmkunden != null) {
            knd = new Object[privkunden.length + firmkunden.length];
        } else if (privkunden != null) {
            knd = new Object[privkunden.length];
        } else {
            knd = new Object[firmkunden.length];
        }

        int cnt = 0;

        if (privkunden != null) {
            for (int i = 0; i < privkunden.length; i++) {
                knd[cnt] = privkunden[i];
                cnt++;
            }
        }

        if (firmkunden != null) {
            for (int i = 0; i < firmkunden.length; i++) {
                knd[cnt] = firmkunden[i];
                cnt++;
            }
        }

        if (knd != null) {
            if (erstesObjekt != null) {
                return ArrayTools.arrayPlusOne(knd, erstesObjekt);
            } else {
                return knd;
            }
        } else {
            return new Object[]{"Keine Kunden vorhanden"};
        }
    }

    public static Object[] getPrivatkundenCombo(String erstesObjekt) {
        KundenObj[] privkunden = KundenRegistry.getKunden(Status.NORMAL);

        if (privkunden != null) {
            return ArrayTools.arrayPlusOne(knd, erstesObjekt);
        } else {
            return new Object[]{"Keine Kunden vorhanden"};
        }
    }
}
