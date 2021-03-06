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
public class BenutzerUebersichtHeader {

    public static class BenutzerColumnsWithTablefield {

        private int _id = -1;
        private String _tablefield;
        private String _name;

        public BenutzerColumnsWithTablefield(int id, String type, String _name) {
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
    
    public static final String[] ColumnsSearch = new String[]{"id", "Obervermittler Id", "Mandanten Id", // 2
        "Benutzerlevel", "Untervermittler", "Kennung", "Vorname", "Nachname", null, "Strasse", // 9
        "Strasse 2", "PLZ", "Ort", "Adresse Zusatz", "Adresse Zusatz 2", "Telefon", "Telefon alternativ", "Fax", // 17
        "Fax alternativ", "Mobil", "Mobil Alternativ", "E-Mail", "E-Mail Alternativ", "Homepage", // 23
        "Homepage alternativ", "Benutzername", null, "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2",
        "Benutzerdefiniert 3", "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt", "Letzte Anmeldung",
        "Anzahl Anmeldungen", "Status"
    };
    
    public static final String[] Columns = new String[]{"id", "Obervermittler", "Mandant", // 2
        "Benutzerlevel", "Untervermittler", "Kennung", "Vorname", "Nachname", null, "Strasse", // 9
        "Strasse 2", "PLZ", "Ort", "Adresse Zusatz", "Adresse Zusatz 2", "Telefon", "Telefon alternativ", "Fax", // 17
        "Fax alternativ", "Mobil", "Mobil Alternativ", "E-Mail", "E-Mail Alternativ", "Homepage", // 23
        "Homepage alternativ", "Benutzername", null, "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2",
        "Benutzerdefiniert 3", "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt", "Letzte Anmeldung",
        "Anzahl Anmeldungen", "Status"
    };

    public static Object[] getColumnsWithField() {
        Object[] col = new Object[Columns.length - 1];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for (int i = 0; i < Columns.length; i++) {
            String column = Columns[i];

            if (column != null) {
                if (i == 0) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "id", column);
                } else if (i == 1) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "parentId", column);
                } else if (i == 2) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "firmenId", column);
                } else if (i == 3) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "level", column);
                } else if (i == 4) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "unterVermittler", column);
                } else if (i == 5) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "kennung", column);
                } else if (i == 6) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "vorname", column);
                } else if (i == 7) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "nachname", column);
                } else if (i == 8) {
                    
                } else if (i == 9) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "strasse", column);
                } else if (i == 10) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "strasse2", column);
                } else if (i == 11) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "plz", column);
                } else if (i == 12) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "ort", column);
                } else if (i == 13) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "addresseZusatz", column);
                } else if (i == 14) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "addresseZusatz2", column);
                } else if (i == 15) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "telefon", column);
                } else if (i == 16) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "telefon2", column);
                } else if (i == 17) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "fax", column);
                } else if (i == 18) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "fax2", column);
                } else if (i == 19) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "mobil", column);
                } else if (i == 20) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "mobil2", column);
                } else if (i == 21) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "email", column);
                } else if (i == 22) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "email2", column);
                } else if (i == 23) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "homepage", column);
                } else if (i == 24) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "homepage2", column);
                } else if (i == 25) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "username", column);
                } else if (i == 26) {
                } else if (i == 27) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "comments", column);
                } else if (i == 28) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom1", column);
                } else if (i == 29) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom2", column);
                } else if (i == 30) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom3", column);
                } else if (i == 31) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom4", column);
                } else if (i == 32) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom5", column);
                } else if (i == 33) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "created", column);
                } else if (i == 34) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "lastlogin", column);
                } else if (i == 35) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "logincount", column);
                } else if (i == 36) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "status", column);
                }

                cnt++;
            }
        }

        return col;
    }
    
    
    public static Object[] getSearchColumnsWithField() {
        Object[] col = new Object[ColumnsSearch.length - 1];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for (int i = 0; i < ColumnsSearch.length; i++) {
            String column = ColumnsSearch[i];

            if (column != null) {
                if (i == 0) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "id", column);
                } else if (i == 1) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "parentId", column);
                } else if (i == 2) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "firmenId", column);
                } else if (i == 3) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "level", column);
                } else if (i == 4) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "unterVermittler", column);
                } else if (i == 5) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "kennung", column);
                } else if (i == 6) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "vorname", column);
                } else if (i == 7) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "nachname", column);
                } else if (i == 8) {
                    
                } else if (i == 9) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "strasse", column);
                } else if (i == 10) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "strasse2", column);
                } else if (i == 11) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "plz", column);
                } else if (i == 12) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "ort", column);
                } else if (i == 13) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "addresseZusatz", column);
                } else if (i == 14) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "addresseZusatz2", column);
                } else if (i == 15) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "telefon", column);
                } else if (i == 16) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "telefon2", column);
                } else if (i == 17) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "fax", column);
                } else if (i == 18) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "fax2", column);
                } else if (i == 19) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "mobil", column);
                } else if (i == 20) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "mobil2", column);
                } else if (i == 21) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "email", column);
                } else if (i == 22) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "email2", column);
                } else if (i == 23) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "homepage", column);
                } else if (i == 24) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "homepage2", column);
                } else if (i == 25) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "username", column);
                } else if (i == 26) {
                } else if (i == 27) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "comments", column);
                } else if (i == 28) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom1", column);
                } else if (i == 29) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom2", column);
                } else if (i == 30) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom3", column);
                } else if (i == 31) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom4", column);
                } else if (i == 32) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "custom5", column);
                } else if (i == 33) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "created", column);
                } else if (i == 34) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "lastlogin", column);
                } else if (i == 35) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "logincount", column);
                } else if (i == 36) {
                    col[cnt] = new BenutzerColumnsWithTablefield(cnt, "status", column);
                }

                cnt++;
            }
        }

        return col;
    }
}
