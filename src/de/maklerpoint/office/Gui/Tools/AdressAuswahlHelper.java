/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.ZusatzadressenSQLMethods;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Status;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author yves
 */
public class AdressAuswahlHelper {

    public static ZusatzadressenObj getAdresse(KundenObj kunde) {

        JFrame mainFrame = CRM.getApplication().getMainFrame();

        try {
            ZusatzadressenObj std = new ZusatzadressenObj();
            ZusatzadressenObj[] za = ZusatzadressenSQLMethods.loadZusatzadressen(DatabaseConnection.open(), 
                    kunde.getKundenNr(), Status.NORMAL);
                           
            std.setBundesland(kunde.getBundesland());
            std.setName(kunde.getVorname() + " " + kunde.getVorname2() + " " + kunde.getNachname());
            std.setNameZusatz(kunde.getAdresseZusatz());
            std.setNameZusatz2(kunde.getAdresseZusatz2());
            std.setStreet(kunde.getStreet());
            std.setKundenKennung(kunde.getKundenNr());
            std.setLand(kunde.getLand());
            std.setPlz(kunde.getPlz());
            std.setOrt(kunde.getStadt());
            std.setId(-1);
            std.setLand(kunde.getLand());
            std.setComments("Autogenerated");

            if(za == null)
                return std;            

            AdressAuswahlDialog auswahl = new AdressAuswahlDialog(mainFrame, true, kunde, za);
            auswahl.setLocationRelativeTo(mainFrame);

            CRM.getApplication().show(auswahl);

            ZusatzadressenObj ret = auswahl.getReturnStatus();

            if(ret == null)
                return std;

            return ret;

        } catch (SQLException e) {
            Log.databaselogger.fatal("Konnte die Zusatzadressen für den Kunden \"" + kunde.getKundenNr() + "\" nicht aus der Datenbank laden", e);
            ShowException.showException("Konnte die Zusatzadressen für den Kunden \"" + kunde.getKundenNr() + "\" nicht aus der Datenbank laden",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Zusatzadressen nicht laden");
        }

        return null;
    }

}
