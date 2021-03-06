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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class MD5 {

    private MD5(){};

    /**
     * Hashes a given String, for radix 40 will be used
     * @param plain
     * @return hashed String
     * @throws NoSuchAlgorithmException
     */

    public static String hash(String plain) throws NoSuchAlgorithmException{
        return hash(plain, 40);
    }

    /**
     * Hashes a given String with the given radix
     * @param plain
     * @param radix
     * @return hashed String
     * @throws NoSuchAlgorithmException
     */

    public static String hash(String plain, int radix) throws NoSuchAlgorithmException{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plain.getBytes(), 0, plain.length());
            return new BigInteger(1, md5.digest()).toString(radix);
    }

}
