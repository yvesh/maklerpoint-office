/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2011/01/04 13:10
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
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class WaehrungenUebersichtHeader {

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

    public static final String[] Columns = new String[]{
        "id", "Sortierung", "ISO-Code", "Bezeichnung", "Neuanlage", "Status" // 5
    };

    public static final String[] columnsId = new String[]{
        "id", "ordering", "Isocode", "bezeichnung", "neuanlage", "status"
    };

    public static Object[] getColumnsWithField() {

        ColumnsWithTablefield[] col = new ColumnsWithTablefield[Columns.length];
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

}
