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

package de.maklerpoint.office.Exception;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class NoUserException extends Exception {


    /**
     * Creates a new instance of <code>DatabaseException</code> without detail message.
     */
    public NoUserException() {
    }


    /**
     * Constructs an instance of <code>DatabaseException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoUserException(String msg) {
        super(msg);
    }

}
