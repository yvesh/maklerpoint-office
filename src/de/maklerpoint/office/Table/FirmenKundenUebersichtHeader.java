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
 * @author yves
 */
public class FirmenKundenUebersichtHeader {

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
    public static final String[] Columns = new String[]{"id", "Ersteller", "Mutterfirma", // 2
        "Betreuer", "Typ", "Kunden Nr.", "Firmen Name", "Name Zusatz", "Name Zusatz 2", "Straße", // 9
        "PLZ", "Ort", "Bundesland", "Land", /*13*/ "Typ", "Mitarbeiter Zahl", "Postfach verwenden", "Postfach", // 17
        "Postfach Plz", "Postfach Ort", "Rechtsform", "Umsatz (jährl.)", "Branche", "Gründungsdatum", // 23
        "Geschäftsführer", "Vertretungsberechtige", "Weitere Standorte", "Kommunikation 1", "Kommunikation 2",
        "Kummunikation 3", "Kommunikation 4", "Kommunikation 5", "Kommunikation 6", //32
        null, null, null, null, null, null, "Standardkonto", "Standardansprechpartner", null,
        null, null, null, "Werber", "Kommentare", "Benutzerdefiniert 1",
        "Benutzerdefiniert 2", "Benutzerdefiniert 3", "Benutzerdefiniert 4", "Benutzerdefiniert 5",
        "Erstellt", "Zuletzt geändert", "Status"};

    public static Object[] getColumnsWithField() {

        Object[] col = new Object[Columns.length - 10];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for (int i = 0; i < Columns.length; i++) {
            String column = Columns[i];

            if (column != null) {
                if (i == 0) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "id", column);
                } else if (i == 1) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "creator", column);
                } else if (i == 2) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "parentFirma", column);
                } else if (i == 3) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "betreuer", column);
                } else if (i == 4) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "type", column);
                } else if (i == 5) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "kundenNr", column);
                } else if (i == 6) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenName", column);
                } else if (i == 7) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenNameZusatz", column);
                } else if (i == 8) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenNameZusatz2", column);
                } else if (i == 9) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenStrasse", column);
                } else if (i == 10) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenPLZ", column);
                } else if (i == 11) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenStadt", column);
                } else if (i == 12) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenBundesLand", column);
                } else if (i == 13) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenLand", column);
                } else if (i == 14) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenTyp", column);
                } else if (i == 15) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenSize", column);
                } else if (i == 16) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenPostfach", column);
                } else if (i == 17) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenPostfachName", column);
                } else if (i == 18) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenPostfachPlz", column);
                } else if (i == 19) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenPostfachOrt", column);
                } else if (i == 20) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenRechtsform", column);
                } else if (i == 21) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenEinkommen", column);
                } else if (i == 22) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenBranche", column);
                } else if (i == 23) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenGruendungDatum", column);
                } else if (i == 24) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenGeschaeftsfuehrer", column);
                } else if (i == 25) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenProKura", column);
                } else if (i == 26) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "firmenStandorte", column);
                } else if (i == 27) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication1", column);
                } else if (i == 28) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication2", column);
                } else if (i == 29) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication3", column);
                } else if (i == 30) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication4", column);
                } else if (i == 31) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication5", column);
                } else if (i == 32) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication6", column);
                } else if (i == 33) {
                    cnt--;
                } else if (i == 34) {
                    cnt--;
                } else if (i == 35) {
                    cnt--;
                } else if (i == 36) {
                    cnt--;
                } else if (i == 37) {
                    cnt--;
                } else if (i == 38) {
                    cnt--;
                } else if (i == 39) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "defaultKonto", column);
                } else if (i == 40) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "defaultAnsprechpartner", column);
                } else if (i == 41) {   
                    cnt--;
                } else if (i == 42) {                   
                    cnt--;
                } else if (i == 43) {
                    cnt--;
                } else if (i == 44) {
                    cnt--;
                } else if (i == 45) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "werber", column);
                } else if (i == 46) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "comments", column);
                } else if (i == 47) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom1", column);
                } else if (i == 48) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom2", column);
                } else if (i == 49) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom3", column);
                } else if (i == 50) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom4", column);
                } else if (i == 51) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom5", column);
                } else if (i == 52) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "created", column);
                } else if (i == 53) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "modified", column);
                } else if (i == 54) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "status", column);
                }

                cnt++;
            }
        }

        return col;
    }
}
