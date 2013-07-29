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
package de.maklerpoint.office.Tags;

import java.util.prefs.Preferences;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class InitializeTags {

    private static Preferences prefs;

    public static void loadTags() {
        prefs = Preferences.userRoot().node(Tags.class.getName());
        String taglist = prefs.get("tagListe", "Standard,Öffentlich,Wichtig,Dienstlich,Persönlich");

        String[] results = taglist.split(",");

        Tags.tags = new TagObj[results.length];

        String tageigen = null;

        for (int i = 0; i < results.length; i++) {
            Tags.tags[i] = new TagObj();

            String result = results[i];

            if (result.equalsIgnoreCase("standard")) {
                tageigen = prefs.get("tag" + result, "-1,-13421773"); // Weiss
            } else if (result.equalsIgnoreCase("öffentlich")) {
                tageigen = prefs.get("tag" + result, "-16724941,-1"); // Grün
            } else if (result.equalsIgnoreCase("wichtig")) {
                tageigen = prefs.get("tag" + result, "-6750157,-1"); // Rot
            } else if (result.equalsIgnoreCase("dienstlich")) {
                tageigen = prefs.get("tag" + result, "-16750849,-13421773"); // Blau
            } else if (result.equalsIgnoreCase("persönlich")) {
                tageigen = prefs.get("tag" + result, "-256,-13421773"); // Gelb
            } else {
                tageigen = prefs.get("tag" + result, "-1,-13421773"); // Weiss Default
            }

            String[] colors = tageigen.split(",");

            Tags.tags[i].setId(i);
            Tags.tags[i].setName(result);
            Tags.tags[i].setTagColor(colors[0]);
            Tags.tags[i].setFontColor(colors[1]);
        }

    }
}
