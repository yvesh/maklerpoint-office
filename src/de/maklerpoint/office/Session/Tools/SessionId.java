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

package de.maklerpoint.office.Session.Tools;

import de.maklerpoint.office.Tools.MD5;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SessionId {

    /**
     * Generates a new uniqueId
     * @param nickname
     * @return uniqueId
     * @throws NoSuchAlgorithmException
     */

    // Todo: Needs some updating
    public static String generateSessionId(String username) throws NoSuchAlgorithmException{

        Random rndNumber = new Random();
        int nbr = 0;

        for (int i = 1; i < 30; i++) {
            nbr = rndNumber.nextInt();
        }

        String rand = username + nbr;

        return MD5.hash(rand).substring(0, 32);
    }

}
