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
public class ProduktUebersichtHeader {

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

    public static final String[] Columns = new String[]{"id", "Versicherung", "Sparte", "Erstellt von",
        "Art", "Tarif", "Tarif-/Basis", "Bezeichnung", "Kürzel", null, "Vermittelbar", "Versicherungsart",
        "Risikotyp", "Versicherungssumme", "Bewertungssumme", "Bedingungen", "Selbstbeteiligung",
        "Nettoprämie (Pauschal)", "Nettoprämie (Zusatz)", "Nettoprämie (gesamt)", null,
        "Zusatzinformationen", "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", 
        "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt", "Zuletzt geändert", "Status"
    };
    
    // TODO
     public static final String[] SearchColumns = new String[]{"id", "Versicherung Id", "Sparte Id", "Ersteller Id",
        "Art", "Tarif", "Tarif-/Basis", "Bezeichnung", "Kürzel", null, "Vermittelbar", "Versicherungsart",
        "Risikotyp", "Versicherungssumme", "Bewertungssumme", "Bedingungen", "Selbstbeteiligung",
        "Nettoprämie (Pauschal)", "Nettoprämie (Zusatz)", "Nettoprämie (gesamt)", null,
        "Zusatzinformationen", "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", 
        "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt", "Zuletzt geändert", "Status"
    };


    public static Object[] getColumnsWithField() {
        Object[] col = new Object[Columns.length - 1];
        int cnt = 0;
//        System.out.println("Length: " + Columns.length);
        for(int i = 0; i < Columns.length; i++) {
            String column = Columns[i];

            if(column != null) {
                if(i == 0)
                    col[cnt] = new ColumnsWithTablefield(cnt, "id", column);
                else if (i == 1)
                    col[cnt] = new ColumnsWithTablefield(cnt, "versichererId", column);
                else if (i == 2)
                    col[cnt] = new ColumnsWithTablefield(cnt, "sparteId", column);
                else if (i == 3)
                    col[cnt] = new ColumnsWithTablefield(cnt, "creatorId", column);
                else if (i == 4)
                    col[cnt] = new ColumnsWithTablefield(cnt, "art", column);
                else if (i == 5)
                    col[cnt] = new ColumnsWithTablefield(cnt, "tarif", column);
                else if (i == 6)
                    col[cnt] = new ColumnsWithTablefield(cnt, "tarifBasis", column);
                else if (i == 7)
                    col[cnt] = new ColumnsWithTablefield(cnt, "bezeichnung", column);
                else if (i == 8)
                    col[cnt] = new ColumnsWithTablefield(cnt, "kuerzel", column);
                else if  (i == 9){
                    // null
                }
                else if (i == 10)
                    col[cnt] = new ColumnsWithTablefield(cnt, "vermittelbar", column);
                else if (i == 11)
                    col[cnt] = new ColumnsWithTablefield(cnt, "versicherungsart", column);
                else if (i == 12)
                    col[cnt] = new ColumnsWithTablefield(cnt, "risikotyp", column);
                else if (i == 13)
                    col[cnt] = new ColumnsWithTablefield(cnt, "versicherungsSumme", column);
                else if (i == 14)
                    col[cnt] = new ColumnsWithTablefield(cnt, "bewertungsSumme", column);
                else if (i == 15)
                    col[cnt] = new ColumnsWithTablefield(cnt, "bedingungen", column);
                else if (i == 16)
                    col[cnt] = new ColumnsWithTablefield(cnt, "selbstbeteiligung", column);
                else if (i == 17)
                    col[cnt] = new ColumnsWithTablefield(cnt, "nettopraemiePauschal", column);
                else if (i == 18)
                    col[cnt] = new ColumnsWithTablefield(cnt, "nettopraemieZusatz", column);
                else if (i == 19)
                    col[cnt] = new ColumnsWithTablefield(cnt, "nettopraemieGesamt", column);
                else if (i == 20) {
                    // null
                } else if (i == 21) {
                    col[cnt] = new ColumnsWithTablefield(cnt, "zusatzInfo", column);
                } else if (i == 22)
                    col[cnt] = new ColumnsWithTablefield(cnt, "comments", column);
                else if (i == 23)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom1", column);
                else if (i == 24)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom2", column);
                else if (i == 25)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom3", column);
                else if (i == 26)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom4", column);
                else if (i == 27)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom5", column);
                else if (i == 28)
                    col[cnt] = new ColumnsWithTablefield(cnt, "created", column);
                else if (i == 29)
                    col[cnt] = new ColumnsWithTablefield(cnt, "modified", column);
                else if (i == 30)
                    col[cnt] = new ColumnsWithTablefield(cnt, "status", column);

                cnt++;
            }

        }

        return col;
    }
}
