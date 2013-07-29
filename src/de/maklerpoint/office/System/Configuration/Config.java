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
package de.maklerpoint.office.System.Configuration;

import com.google.gdata.util.common.util.Base64;
import de.maklerpoint.office.Tools.Base64Encode;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.prefs.Preferences;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class Config {

    // Nur als Beispiel, der Key sollte ander gespeichert werden
    private static final String skey = "[B@5b6df84b45687"; // 16 Chars
    private static final String ivx = "1568261547893211";    
    private static final SecretKeySpec keySpec = new SecretKeySpec(skey.getBytes(), "AES");
    private static final IvParameterSpec ivSpec = new IvParameterSpec(ivx.getBytes());
    private static Cipher cipher_enc = getCypher(keySpec, ivSpec, Cipher.ENCRYPT_MODE);
    private static Cipher cipher_dec = getCypher(keySpec, ivSpec, Cipher.DECRYPT_MODE);

    public static Cipher getCypher(SecretKeySpec keySpec,
            IvParameterSpec ivSpec, int mode) {
        // Get a cipher object.
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("invalid algorithm", e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("invalid padding", e);
        }
        try {
            cipher.init(mode, keySpec, ivSpec);
        } catch (InvalidKeyException e) {
            //throw new CryptographyException("invalid key", e);
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid algorithm parameter.", e);
        }
        return cipher;
    }
    // Preferences
    private static Preferences prefs = Preferences.userRoot().node(Config.class.getName());
    public static int LOG_SIZE = Config.getConfigInt("logSize", 10000024);
    public static String LOG_DIR = Config.get("logDir", "includes" + File.separator + "logs");
    public static int LOG_LEVEL = Config.getConfigInt("logLevel", 2);
    public static boolean FIRST_RUN = false;

    public static String get(String value, String defaultValue) {
        String val = prefs.get(value, defaultValue);
        return val;
    }

    public static int getInt(String value, int defaultValue) {
        return getConfigInt(value, defaultValue);
    }

    public static String getConfigValue(String value) {
        return getConfigValue(value, "");
    }

    public static String getConfigValue(String value, String defaultValue) {
        String val = prefs.get(value, defaultValue);
        return val;
    }

    public static boolean getConfigBoolean(String value, boolean defaultValue) {
        boolean val = prefs.getBoolean(value, defaultValue);
        return val;
    }

    public static int getConfigInt(String value, int defaultValue) {
        int val = prefs.getInt(value, defaultValue);
        return val;
    }

    public static void set(String name, String value) {
        prefs.put(name, value);
    }

    public static void setInt(String name, int value) {
        prefs.putInt(name, value);
    }

    public static void setBoolean(String name, boolean value) {
        prefs.putBoolean(name, value);
    }

    public static void setLong(String name, long value) {
        prefs.putLong(name, value);
    }

    public static Preferences getPreferences() {
        return prefs;
    }
    
    /**
     * 
     * @param name
     * @return 
     */

    public static String getCrypted(String name) {
        try {
            // decode the BASE64 coded message                       
            String encrypted = Config.get(name, null);
            
            if(encrypted == null)
                return null;
            
            byte[] raw = Base64Encode.decode(encrypted);

            byte[] stringBytes = cipher_dec.doFinal(raw);

            String val = new String(stringBytes, "UTF8");
//            System.out.println("Decrypted: " + val);
            return val;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }

    public static void setCrypted(String name, String value) {
        try {
            byte[] stringBytes = value.getBytes("UTF8");
            byte[] raw;

            raw = cipher_enc.doFinal(stringBytes);

            // converts to base64 for easier display.
            String base64 = Base64.encode(raw);

            Config.set(name, base64);

//            System.out.println("RAW: " + raw);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}