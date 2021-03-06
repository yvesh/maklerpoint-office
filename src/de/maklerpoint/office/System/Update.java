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
package de.maklerpoint.office.System;

import de.maklerpoint.office.Konstanten.MPointKonstanten;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.acyrance.licensor.License;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author yves
 */
public class Update {

    public boolean check() {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(URLEncoder.encode("clientVersion", "UTF-8")).append("=").append(URLEncoder.encode(Version.version, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientBuild", "UTF-8")).append("=").append(URLEncoder.encode(Version.build_name, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientSerial", "UTF-8")).append("=").append(URLEncoder.encode(License.getHashNumber(), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOS", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(Environment.OS), "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSName", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_NAME, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSArch", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_ARCH, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientOSVersion", "UTF-8")).append("=").append(URLEncoder.encode(Environment.OS_VERSION, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientVMVendor", "UTF-8")).append("=").append(URLEncoder.encode(Environment.VM_VENDOR, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientVMVersion", "UTF-8")).append("=").append(URLEncoder.encode(Environment.VM_VERSION, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientUsername", "UTF-8")).append("=").append(URLEncoder.encode(Environment.USERNAME, "UTF-8"));
            sb.append("&");
            sb.append(URLEncoder.encode("clientUserdir", "UTF-8")).append("=").append(URLEncoder.encode(Environment.USERDIR, "UTF-8"));

            URL url = new URL(MPointKonstanten.MP_UPDATEURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(sb.toString());
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.startsWith("update")) {
                    BasicRegistry.updateAvailable = Boolean.valueOf(getResult(line));
                }

                if (line.startsWith("currentversion")) {
                    BasicRegistry.updateVersion = getResult(line);
                }

                if (line.startsWith("changelog")) {
                    BasicRegistry.updateChangelogURL = getResult(line);
//                    BasicRegistry.updateChangelog = getChangelog(getResult(line));
                }

                if (line.startsWith("filepath")) {
                    BasicRegistry.updateFilepath = getResult(line);
                }

                if (line.startsWith("size")) {
                    BasicRegistry.updateSize = getResult(line);               
                }

                if (line.startsWith("md5")) {
                    BasicRegistry.updateMd5 = getResult(line);
                }

            }
            wr.close();
            rd.close();
        } catch (java.net.ConnectException e) {
            Log.logger.warn("Konnte keine Verbindung zum MaklerPoint Updateserver herstellen "
                    + "- Keine Internetverbindung verfügbar?");
            BasicRegistry.internetAvailable = false;
        } catch (java.net.SocketException e) {
            Log.logger.warn("Konnte keine Verbindung zum MaklerPoint Updateserver herstellen "
                    + "- Keine Internetverbindung verfügbar?");
            BasicRegistry.internetAvailable = false;
        } catch (java.net.UnknownHostException e) {
            Log.logger.warn("Konnte keine Verbindung zum MaklerPoint Updateserver herstellen "
                    + "- Keine Internetverbindung verfügbar?");
            BasicRegistry.internetAvailable = false;
        } catch (UnsupportedEncodingException e) {
            Log.logger.warn("Fehler beim überprüfen auf Updates", e);
        } catch (MalformedURLException e) {
            Log.logger.warn("Fehler beim überprüfen auf Updates", e);
        } catch (IOException e) {
            Log.logger.warn("Fehler beim überprüfen auf Updates", e);
        }


        return BasicRegistry.updateAvailable;
    }

    private String getResult(String line) {
        String[] result = line.split("=");

        return result[1];
    }

    private static String getChangelog(String address) throws MalformedURLException, IOException {
        URL url = new URL(address);
        InputStream html = null;
        html = url.openStream();
        int c = 0;
        StringBuilder buffer = new StringBuilder();
        while (c != -1) {
            c = html.read();
            buffer.append((char) c);

        }
        return buffer.toString();

    }
}
