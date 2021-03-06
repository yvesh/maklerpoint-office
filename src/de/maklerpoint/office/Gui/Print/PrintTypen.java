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

package de.maklerpoint.office.Gui.Print;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class PrintTypen {

    public static final int KUNDENDATENBLATT = 0;
    public static final int KUNDENUEBERSICHT = 1;

    public static final int FIRMENKUNDENDATENBLATT = 2;
    public static final int FIRMENKUNDENUEBERSICHT = 3;

    public static final int VERSICHERERDATENBLATT = 4;
    public static final int VERSICHERERUEBERSICHT = 5;

    public static String getDialogName(int type){
         switch(type) {
            case KUNDENDATENBLATT:
                return("Kundendatenblatt drucken");

           case KUNDENUEBERSICHT:
                return("Kundenübersicht drucken");

           case FIRMENKUNDENDATENBLATT:
                return("Firmenkundendatenblatt drucken");

           case FIRMENKUNDENUEBERSICHT:
                return("Firmenkundenübersicht drucken");

           case VERSICHERERDATENBLATT:
             return("Versichererdatenblatt drucken");

           case VERSICHERERUEBERSICHT:
             return ("Versichererübersicht drucken");

           default:
                 return null;
        }
    }
}
