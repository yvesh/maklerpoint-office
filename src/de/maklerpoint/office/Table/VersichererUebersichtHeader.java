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
public class VersichererUebersichtHeader {

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


    // id parentId xx
    public static final String[] Columns = new String[]{"id", "Mutterkonzern Id", "Mutterkonzern", "VU-Nummer", // 3
        "Name", "Name Zusatz", "Name Zusatz 2", "Kürzel", "Gesellschafts Nummer", "Straße", "Plz", // 10
        "Ort", "Bundesland", "Land", "Postfach verwenden", "Postfach", "Postfach Plz", "Postfach Ort", "Vermittelbar", // 18
        "Kommunikation 1", "Kommunikation 2", "Kummunikation 3", "Kommunikation 4", "Kommunikation 5", "Kommunikation 6", // 24
        null, null, null, null, null, "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", //33
        "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt", "Zuletzt geändert", "Status"
    };

    /**
     * 
     * @return
     */

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
                    col[cnt] = new ColumnsWithTablefield(cnt, "parentId", column);
                else if (i == 2)
                    col[cnt] = new ColumnsWithTablefield(cnt, "parentName", column);
                else if (i == 3)
                    col[cnt] = new ColumnsWithTablefield(cnt, "vuNummer", column);
                else if (i == 4)
                    col[cnt] = new ColumnsWithTablefield(cnt, "name", column);
                else if (i == 5)
                    col[cnt] = new ColumnsWithTablefield(cnt, "nameZusatz", column);
                else if (i == 6)
                    col[cnt] = new ColumnsWithTablefield(cnt, "nameZusatz2", column);
                else if (i == 7)
                    col[cnt] = new ColumnsWithTablefield(cnt, "kuerzel", column);
                else if (i == 8)
                    col[cnt] = new ColumnsWithTablefield(cnt, "gesellschaftsNr", column);
                else if (i == 9)
                    col[cnt] = new ColumnsWithTablefield(cnt, "strasse", column);
                else if (i == 10)
                    col[cnt] = new ColumnsWithTablefield(cnt, "plz", column);
                else if (i == 11)
                    col[cnt] = new ColumnsWithTablefield(cnt, "stadt", column);
                else if (i == 12)
                    col[cnt] = new ColumnsWithTablefield(cnt, "bundesLand", column);
                else if (i == 13)
                    col[cnt] = new ColumnsWithTablefield(cnt, "land", column);
                else if (i == 14)
                    col[cnt] = new ColumnsWithTablefield(cnt, "postfach", column);
                else if (i == 15)
                    col[cnt] = new ColumnsWithTablefield(cnt, "postfachName", column);
                else if (i == 16)
                    col[cnt] = new ColumnsWithTablefield(cnt, "postfachPlz", column);
                else if (i == 17)
                    col[cnt] = new ColumnsWithTablefield(cnt, "postfachOrt", column);
                else if (i == 18)
                    col[cnt] = new ColumnsWithTablefield(cnt, "vermittelbar", column);
                else if (i == 19)
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication1", column);
                else if (i == 20)
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication2", column);
                else if (i == 21){
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication3", column);
                } else  if (i == 22)
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication4", column);
                else if (i == 23)
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication5", column);
                else if (i == 24)
                    col[cnt] = new ColumnsWithTablefield(cnt, "communication6", column);
                else if (i == 25) {
                    
                } else if (i == 26){

                } else if (i == 27){
                    
                } else if (i == 28) {

                } else if (i == 29){
                    
                } else if (i == 30){

                } else if (i == 31)
                    col[cnt] = new ColumnsWithTablefield(cnt, "comments", column);
                else if (i == 32)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom1", column);
                else if (i == 33)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom2", column);
                else if (i == 34)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom3", column);
                else if (i == 35)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom4", column);
                else if (i == 36)
                    col[cnt] = new ColumnsWithTablefield(cnt, "custom5", column);
                else if (i == 37)
                    col[cnt] = new ColumnsWithTablefield(cnt, "created", column);
                else if (i == 38)
                    col[cnt] = new ColumnsWithTablefield(cnt, "modified", column);
                else if (i == 39)
                    col[cnt] = new ColumnsWithTablefield(cnt, "status", column);                

                cnt++;
            }

        }

        return col;
    }



}
