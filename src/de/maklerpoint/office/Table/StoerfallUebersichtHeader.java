/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       17.07.2011 14:11:11
 *  File:       StoerfallUebersichtHeader
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 MaklerPoint Software - Yves Hoppe.  All Rights Reserved.
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
public class StoerfallUebersichtHeader {

    public static final String[] Columns = new String[]{
        "id", "Erstellt von", null, "Kunden-Nr.", "Kunde", "Policen-Nr.", "Vertrag", // 6
        "Versicherer", "Produkt", "Betreuer", // 9
        "Störfall Nummer", "Grund", "Eingang", "Fälligkeit", "Frist",
        null, "Rückstand", "Mahnstatus", "Kategorie", null, "Notiz", // 20
        "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Erstellt am", "Zuletzt geändert am", "Status"
    };
    public static final String[] SearchColumns = new String[]{
        "id", "Ersteller Id", null, "Kunden-Nr.", "VertragId", "BetreuerId",
        "Störfall Nummer", "Grund", "Eingang", "Fälligkeit", "Frist",
        null, "Rückstand", "Mahnstatus", "Kategorie", null, "Notiz",
        "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Erstellt am", "Zuletzt geändert am", "Status"
    };
    public static final String[] ColumnsDB = new String[]{
        "id", "creatorId", null, "kundenNr", "vertragsId", "betreuerId",
        "stoerfallNr", "grund", "eingang", "faelligkeit", "fristTage",
        null, "rueckstand", "mahnstatus", "kategorie", null, "notiz",
        "custom1", "custom2", "custom3",
        "created", "modified", "status"
    };

    /**
     * Störfall Columns mit Feld (DB)
     * @return 
     */
    public static Object[] getSearchColumnsWithField() {

        Object[] col = new Object[SearchColumns.length];
        int cnt = 0;

        for (int i = 0; i < SearchColumns.length; i++) {
            String column = SearchColumns[i];

            if (column != null) {
                col[cnt] = new ColumnsWithTablefield(cnt, ColumnsDB[i], column);
//                  System.out.println(cnt + " " + column + "(" + ColumnsDB[i] + ")");
                cnt++;
            }
        }

        return col;
    }

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
}
