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
package de.maklerpoint.office.Registry;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Konstanten.MPointKonstanten;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Mandanten.MandantenObj;
import de.maklerpoint.office.Session.SessionObj;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BasicRegistry {

    private BasicRegistry() {
    }
    public static BenutzerObj currentUser;
    public static boolean logedIn = false;
    public static SessionObj currentSession;
    public static MandantenObj currentMandant; // firmenId = mandantenId
    public static boolean internetAvailable = true;
    public static boolean updateAvailable = false;
    public static String updateVersion = null;
    public static String updateChangelogURL = null;
    public static String updateChangelog = null;
    public static String updateFilepath = null;
    public static String updateMd5 = null;
    public static String updateSize = null;

    public static boolean isInternetAvailable() {
        OutputStreamWriter wr = null;
        try {
            URL url = new URL(MPointKonstanten.MP_UPDATEURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            return true;
        } catch (java.net.ConnectException e) {
            BasicRegistry.internetAvailable = false;
            return false;
        } catch (java.net.SocketException e) {
            BasicRegistry.internetAvailable = false;
            return false;
        } catch (java.net.UnknownHostException e) {
            BasicRegistry.internetAvailable = false;
            return false;
        } catch (UnsupportedEncodingException e) {
            Log.logger.warn("Fehler beim überprüfen auf Verfügbarkeit des Internets", e);
            return false;
        } catch (MalformedURLException e) {
            Log.logger.warn("Fehler beim überprüfen auf Verfügbarkeit des Internets", e);
            return false;
        } catch (IOException e) {
            Log.logger.warn("Fehler beim überprüfen auf Verfügbarkeit des Internets", e);
            return false;
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }
}
