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
package de.maklerpoint.office.Briefe.Tools;

import de.maklerpoint.office.Kunden.KundenObj;
import java.util.Hashtable;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KundenBriefTools {

    private static Hashtable htAnschreiben;

    public static String getKundenBriefAnrede(KundenObj kunde) {
        StringBuilder sb = new StringBuilder();

        if (kunde.getAnrede().equalsIgnoreCase("frau")) {
            sb.append("Sehr geehrte Frau ");
        } else if (kunde.getAnrede().equalsIgnoreCase("herr")) {
            sb.append("Sehr geehrter Herr ");
        } else if (kunde.getAnrede().equalsIgnoreCase("herr und frau")) {
            sb.append("Sehr geehrter Herr und Frau ");
        } else if (kunde.getAnrede().equalsIgnoreCase("familie")) {
            sb.append("Sehr geehrte Framile ");
        }

        sb.append(kunde.getNachname());
        sb.append(",");

        return (sb.toString());
    }

    public static String getKundenBriefAnschriftONELINE(KundenObj kunde) {
        String sb = null;

        if (kunde.getAdresseZusatz() == null && kunde.getAdresseZusatz().length() > 0) {
            sb = kunde.getAnrede() + " " + kunde.getVorname() + " " + kunde.getNachname() + " - " + kunde.getAdresseZusatz()
                    + ", " + kunde.getStreet() + ", " + kunde.getPlz() + " " + kunde.getStadt();
        } else {
            sb = kunde.getAnrede() + " " + kunde.getVorname() + " " + kunde.getNachname()
                    + ", " + kunde.getStreet() + ", " + kunde.getPlz() + " " + kunde.getStadt();
        }

        return sb;
    }

    public static String generateKundeAnschrift(KundenObj kunde) {
        String anschrift = "";

        if (kunde.getAdresseZusatz() == null && kunde.getAdresseZusatz2() == null) {
            if (kunde.getTitel() != null) {
                anschrift = kunde.getAnrede() + " " + kunde.getTitel() + " " + kunde.getVorname() + " " + kunde.getNachname() + "\n";
                anschrift = anschrift + kunde.getStreet() + "\n" + kunde.getPlz() + " " + kunde.getStadt();
            } else {
                anschrift = kunde.getAnrede() + " " + kunde.getVorname() + " " + kunde.getNachname() + "\n";
                anschrift = anschrift + kunde.getStreet() + "\n" + kunde.getPlz() + " " + kunde.getStadt();
            }

        } else {
            anschrift = kunde.getAnrede() + " " + kunde.getTitel() + " " + kunde.getVorname() + " " + kunde.getNachname() + "\n";

            if (kunde.getAdresseZusatz() != null && kunde.getAdresseZusatz2() != null) {
                anschrift = anschrift + kunde.getAdresseZusatz() + "\n" + kunde.getAdresseZusatz2()
                        + "\n" + kunde.getStreet() + "\n" + kunde.getPlz() + " " + kunde.getStadt();

            } else {
                if (kunde.getAdresseZusatz() != null) {
                    anschrift = anschrift + kunde.getAdresseZusatz() + "\n";
                } else {
                    anschrift = anschrift + kunde.getAdresseZusatz2() + "\n";
                }

                anschrift = anschrift + kunde.getStreet() + "\n";
                anschrift = anschrift + kunde.getPlz() + " " + kunde.getStadt();
            }
        }

        return anschrift;
    }

    private static void hashPut(String val, Object val2) {
        if (val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if (val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        htAnschreiben.put(val, val2);
    }

    public static Hashtable generateBriefAdresse(KundenObj kunde) {
        htAnschreiben = new Hashtable();

        if (kunde.getAdresseZusatz() == null && kunde.getAdresseZusatz2() == null) {
            hashPut("ANSCHRIFT1", kunde.getAnrede() + " " + kunde.getTitel() + " "
                    + kunde.getVorname() + " " + kunde.getNachname());
            hashPut("ANSCHRIFT2", kunde.getStreet());
            hashPut("ANSCHRIFT3", "");
            hashPut("ANSCHRIFT4", kunde.getPlz() + " " + kunde.getStadt());
            hashPut("ANSCHRIFT5", "");
        } else {
            hashPut("ANSCHRIFT1", kunde.getAnrede() + " " + kunde.getTitel() + " "
                    + kunde.getVorname() + " " + kunde.getNachname());
            if (kunde.getAdresseZusatz() != null && kunde.getAdresseZusatz2() != null) {
                hashPut("ANSCHRIFT2", kunde.getAdresseZusatz());
                hashPut("ANSCHRIFT3", kunde.getAdresseZusatz2());
                hashPut("ANSCHRIFT4", kunde.getStreet());
                hashPut("ANSCHRIFT5", kunde.getPlz() + " " + kunde.getStadt());
            } else {
                if (kunde.getAdresseZusatz() != null) {
                    hashPut("ANSCHRIFT2", kunde.getAdresseZusatz());
                } else {
                    hashPut("ANSCHRIFT2", kunde.getAdresseZusatz2());
                }
                hashPut("ANSCHRIFT3", kunde.getStreet());
                hashPut("ANSCHRIFT4", "");
                hashPut("ANSCHRIFT5", kunde.getPlz() + " " + kunde.getStadt());
            }
        }

        return htAnschreiben;
    }
}
