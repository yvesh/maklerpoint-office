/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package de.maklerpoint.office.Table;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SpartenUebersichtHeader {

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
        "id", "Spartennummer", "Bezeichnung", "Gruppe", "Steuersatz", "status" // 5
    };

    public static final String[] columnsId = new String[]{
        "id", "spartenNummer", "bezeichnung", "gruppe", "steuersatz", "status"
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
