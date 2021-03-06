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

package de.maklerpoint.office.Tools;

import java.util.regex.Pattern;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class BasicRegex {

    /**
     * Splits an String with " " as splitter
     * @param command
     * @return String[] splitted String Array
     */

    public static String[] splitCommand(String command){
        return splitCommand(command, " ");
    }

    /**
     * Splits an String with the given splitter
     * @param command
     * @param split
     * @return  String[] splitted String Array
     */

    public static String[] splitCommand(String command, String split){
        String[] result = null;

        String pattern = split;
        Pattern splitter = Pattern.compile(pattern);
        result = splitter.split(command);

        return result;
    }

    /**
     * 
     * @param chars
     * @param str
     * @return Gekürzter String ohne Punkte
     */
    public static String shortenString(int chars, String str){
        return shortenString(chars, str, false);
    }

    /**
     * Splits an string to the given length (when it is longer)
     * @param chars
     * @param str
     * @return Gekürzter String mit oder ohne Punkte
     */

     public static String shortenString(int chars, String str, boolean pointed){
        String shortened = null;

        if(str != null)
        {
            if (str.length() <= chars)
                shortened = str; // Nothing to do all matchs
            else {
                shortened = str.substring(0, chars);
                if(pointed) {
                    shortened = shortened + "..";
                }
            }
        }

        return shortened;
    }

     /**
      * testing
      * @param allowed
      * @param plz
      * @return allowed
      */
     
     public static boolean isAllowedPlz(String allowed, String plz){
        String regEx = allowed.trim()+"\\d{0,"+(5-allowed.length())+"}";
        boolean b = Pattern.matches(regEx, plz.trim());
        //System.out.println(b);
        return b;
    }



}
