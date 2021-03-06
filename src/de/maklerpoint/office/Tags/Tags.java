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

import de.maklerpoint.office.Tools.ArrayStringTools;
import java.util.prefs.Preferences;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class Tags {

    public static TagObj[] tags;

    /**
     * 
     * @param tagName
     * @return
     */
    public static TagObj getTag(String tagName) {
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].getName().equalsIgnoreCase(tagName)) {
                return tags[i];
            }
        }

        return null;
    }

    /**
     * 
     * @param id
     * @return
     */
    public static TagObj getTag(int id) {
        return tags[id];
    }

    /**
     * Entfernt den Tag und lädt die Liste neu
     * @param tagName 
     */
    public static void removeTag(String tagName) {
        Preferences prefs = Preferences.userRoot().node(Tags.class.getName());
        String taglist = prefs.get("tagListe", "Standard,Öffentlich,Wichtig,Dienstlich,Persönlich");

        String[] result = taglist.split(",");

        if (result == null) {
            return;
        }

        String[] clean = (String[]) ArrayUtils.removeElement(result, tagName);
        prefs.put("tagListe", ArrayStringTools.arrayToString(clean, ","));



        InitializeTags.loadTags();
    }
    /**
     * 
     * @param tag 
     */
    public static void addTag(TagObj tag) {
        Preferences prefs = Preferences.userRoot().node(Tags.class.getName());
        String taglist = prefs.get("tagListe", "Standard,Öffentlich,Wichtig,Dienstlich,Persönlich");

        String[] result = taglist.split(",");

        if (result == null) {
            return;
        }

        String[] clean = (String[]) ArrayUtils.add(result, tag.getName());
        prefs.put("tagListe", ArrayStringTools.arrayToString(clean, ","));

        prefs.put("tag" + tag.getName(), tag.getTagColor() + "," + tag.getFontColor());

        InitializeTags.loadTags();
    }
    /**
     * 
     * @param tag
     * @param oldname 
     */
    public static void updateTag(TagObj tag, String oldname) {
        Preferences prefs = Preferences.userRoot().node(Tags.class.getName());
        String taglist = prefs.get("tagListe", "Standard,Öffentlich,Wichtig,Dienstlich,Persönlich");

        String[] result = taglist.split(",");

        if (result == null) {
            return;
        }
        
        boolean contains = ArrayUtils.contains(result, tag.getName());
        
        if(contains) {
            prefs.put("tag" + tag.getName(), tag.getTagColor() + "," + tag.getFontColor());   
            InitializeTags.loadTags();
        } else {
            Tags.removeTag(oldname);
            addTag(tag);
        }                
    }
}
