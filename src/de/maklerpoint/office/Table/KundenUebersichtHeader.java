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

package de.maklerpoint.office.Table;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class KundenUebersichtHeader {

    public static class ColumnsWithTablefield {

        private int _id = -1;
        private String _tablefield;
        private String _name;

        public ColumnsWithTablefield(int id, String type, String _name) {
            super();
            this._tablefield = type;
            this._name = _name;
            this._id = id;
        }

        public int getId() {
            return _id;
        }

        public void setId(int _id) {
            this._id = _id;
        }

        public String getName() {
            return _name;
        }

        public void setName(String _name) {
            this._name = _name;
        }

        public String getType() {
            return _tablefield;
        }

        public void setType(String _type) {
            this._tablefield = _type;
        }

        @Override
        public String toString() {
            return _name;
        }
    }

    public static final String[] ColumnsSearch = new String[]{"id", "Betreuer Id", "Angelegt von Id",       // 2
        "KundenNr", "Anrede", "Titel", "Arbeitgeber", "Vorname", "Zweiter Vorname", "Weitere Vorname", // 9
        "Nachname", "Straße", "Plz", "Stadt", "Bundesland", "Land", "Adresse Zusatz", // 16
        "Kommunikation 1", "Kommunikation 2", "Kommunikation 3", "Kommunikation 4", // 20
        "Kommunikation 5", "Kommunikation 6",  null, null, null, null, null, null, // 28
        "Typ", "Familienstand", "Ehepartner KundenNr", "Geburtsdatum", "Beruf", "Berufs Typ", // 34
        "Berufs Optionen", "Beamter", "Öffentl. Dienst", "Einkommen (Brutto)", "Einkommen (Netto)", 
        "Steuerklasse", "Anzahl Kinder", // 41
        "Religion", "Weitere Pers. im Haushalt", "Weitere Personen im Haushalt Info", "Familienplanung", // 45
        "Werber KundenNr", null, null, null, null, null, null, // 52
        "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Benutzerdefiniert 4", // 57
        "Benutzerdefiniert 5", "Geburtsname", "Ehedatum", "Erstellt am", "Zuletzt geändert", "Status"};
    

    public static final String[] Columns = new String[]{"id", "Betreuer", "Angelegt von",       // 2
        "KundenNr", "Anrede", "Titel", "Arbeitgeber", "Vorname", "Zweiter Vorname", "Weitere Vorname", // 9
        "Nachname", "Straße", "Plz", "Stadt", "Bundesland", "Land", "Adresse Zusatz", // 16
        "Kommunikation 1", "Kommunikation 2", "Kommunikation 3", "Kommunikation 4", // 20
        "Kommunikation 5", "Kommunikation 6",  null, null, null, null, null, null, // 28
        "Typ", "Familienstand", "Ehepartner", "Geburtsdatum", "Beruf", "Berufs Typ", // 34
        "Berufs Optionen", "Beamter", "Öffentl. Dienst", "Einkommen (Brutto)", "Einkommen (Netto)", "Steuerklasse", "Anzahl Kinder", // 41
        "Religion", "Weitere Pers. im Haushalt", "Weitere Personen im Haushalt Info", "Familienplanung", // 45
        "Werber", null, null, null, null, null, null, // 52
        "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Benutzerdefiniert 4", // 57
        "Benutzerdefiniert 5", "Geburtsname", "Ehedatum", "Erstellt am", "Zuletzt geändert", "Status"}; // 63
     
    public static final String[] columnsId = new String[]{
        "id", "betreuerId", "creatorId", "kundenNr", "anrede", "titel", "firma", "vorname", "vorname2",
        "vornameWeitere", "nachname", "street", "plz", "stadt", "bundesland", "land", "adresseZusatz", // 17
        "communication1", "communication2", "communication3", "communication4", "communication5",
        "communication6", null, null, null, null, null, null, "typ", "familienstand", "ehepartnerid",
        "geburtsdatum",  "beruf", "berufsTyp", "berufsOptionen",
        "beamter", "oeffentlicherDienst", "einkommen", "einkommenNetto", "steuerklasse",
        "kinderZahl", "religion", "weiterePersonen", "weiterePersonenInfo", "familienPlanung", 
        "werberKennung", null, null, null, null, null, null, "comments", "custom1", "custom2", "custom3", "custom4",
        "custom5", "geburtsname", "ehedatum", "created", "modified", "status"
    };

    public static Object[] getColumnsWithField() {

        ColumnsWithTablefield[] col = new ColumnsWithTablefield[ColumnsSearch.length - 5];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for(int i = 0; i < ColumnsSearch.length; i++) {
            String column = ColumnsSearch[i];
            String columnId = columnsId[i];

            if(column != null) {
                //System.out.println("Column id " + i + " | cnt: " + cnt +  " | value " + column + " | db: " + columnId );
                col[cnt] = new ColumnsWithTablefield(cnt, columnId, column);
                cnt++;
            }
        }
        return col;
    }
    
    public static Object[] getColumnsWithFieldSearch() {

        ColumnsWithTablefield[] col = new ColumnsWithTablefield[Columns.length - 5];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for(int i = 0; i < Columns.length; i++) {
            String column = Columns[i];
            String columnId = columnsId[i];

            if(column != null) {
                //System.out.println("Column id " + i + " | cnt: " + cnt +  " | value " + column + " | db: " + columnId );
                col[cnt] = new ColumnsWithTablefield(cnt, columnId, column);
                cnt++;
            }
        }
        return col;
    }


//    public static Object[] getColumnsWithField() {
//
//        Object[] col = new Object[Columns.length - 1];
//        int cnt = 0;
////        System.out.println("Length: " + Columns.length);
//        for(int i = 0; i < Columns.length; i++) {
//            String column = Columns[i];
//
//            if(column != null) {
//                if(i == 0)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "id", column);
//                else if (i == 1)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "betreuer", column);
//                else if (i == 2)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "besitzer", column);
//                else if (i == 3)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "kundenNr", column);
//                else if (i == 4)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "anrede", column);
//                else if (i == 5)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "titel", column);
//                else if (i == 6)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "firma", column);
//                else if (i == 7)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "vorname", column);
//                else if (i == 8)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "vorname2", column);
//                else if (i == 9)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "vornameWeitere", column);
//                else if (i == 10)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "nachname", column);
//                else if (i == 11)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "street", column);
//                else if (i == 12)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "plz", column);
//                else if (i == 13)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "stadt", column);
//                else if (i == 14)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "bundesland", column);
//                else if (i == 15)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "land", column);
//                else if (i == 16)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "typ", column);
//                else if (i == 17)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "familienStand", column);
//                else if (i == 18)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "ehepartnerId", column);
//                else if (i == 19)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "telefon", column);
//                else if (i == 20)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "telefon2", column);
//                else if (i == 21){
//
//                } else  if (i == 22)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "dienstlichTelefon", column);
//                else if (i == 23)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "fax", column);
//                else if (i == 24)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "mobil", column);
//                else if (i == 25)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "mail", column);
//                else if (i == 26)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "mail2", column);
//                else if (i == 27)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "geburtsdatum", column);
//                else if (i == 28)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "homepage", column);
//                else if (i == 29)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "beruf", column);
//                else if (i == 30)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "berufsTyp", column);
//                else if (i == 31)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "berufsOptionen", column);
//                else if (i == 32)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "beamter", column);
//                else if (i == 33)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "oeffentlicherdienst", column);
//                else if (i == 34)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "einkommen", column);
//                else if (i == 35)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "steuerklasse", column);
//                else if (i == 36)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "kinderZahl", column);
//                else if (i == 37)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "religion", column);
//                else if (i == 38)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "weiterePersonen", column);
//                else if (i == 39)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "weiterePersonenInfo", column);
//                else if (i == 40)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "familienPlanung", column);
//                else if (i == 41)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "werberKennung", column);
//                else if (i == 42)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "kontonummer", column);
//                else if (i == 43)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "bankleitzahl", column);
//                else if (i == 44)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "bankname", column);
//                else if (i == 45)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "bankeigentuemer", column);
//                else if (i == 46)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "kontoiban", column);
//                else if (i == 47)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "kontobic", column);
//                else if (i == 48)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "comments", column);
//                else if (i == 49)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "custom1", column);
//                else if (i == 50)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "custom2", column);
//                else if (i == 51)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "custom3", column);
//                else if (i == 52)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "custom4", column);
//                else if (i == 53)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "custom5", column);
//                else if (i == 54)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "created", column);
//                else if (i == 55)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "modified", column);
//                else if (i == 56)
//                    col[cnt] = new ColumnsWithTablefield(cnt, "status", column);
//
//                cnt++;
//            }
//
//        }
//
//        return col;
//    }
}
