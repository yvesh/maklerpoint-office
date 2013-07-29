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

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class Status {

    public static final int ALLE = -1;
    public static final int NORMAL = 0;
    // Für Termine etc.
    public static final int SUCCESS = 1;
    public static final int ARCHIVED = 2;
    public static final int DELETED = 5;
    public static final int PRIVATE = 6;

    // Dokumente    

    public static String getName(int status) {
        switch(status) {
            case ALLE:
                return "Alle";

            case NORMAL:
                return "Aktiv";

            case SUCCESS:
                return "Erfolgreich / Beendet";

            case ARCHIVED:
                return "Archiviert";

            case DELETED:
                return "Gelöscht";

            case PRIVATE:
                return "Privat";

            default:
                return "Unbekannt";
        }
    }
}
