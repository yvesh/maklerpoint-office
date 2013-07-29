/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       19.07.2011 10:38:08
 *  File:       SchaedenUebersichtHeader
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
public class SchaedenUebersichtHeader {
    
     public static final String[] Columns = new String[]{
        "Id", "Erstellt von", null, "Kunden-Nr.", "Kunde", "Policen-Nr.", "Vertrag", "Versicherer", "Produkt",        
        "Interne Schadens-Nr.", // 9
        "Art der Meldung", "Meldung von", "Meldung Zeit", "Schaden Zeit", // 13
        "Meldung Polizei", "Kategorie", "Bearbeiter", "Ort", "Umfang", "Hergang", // 19
        "Weiterleitung VU am", "Weiterleitung VU Art", // 21
        "Risiko", "Schadenshöhe", "Arbrechnungsart", "Gutachten erforderlich",
        "Schadens-Nr. (VU)", "Status vom", null, "Interne Info", "Notizen",       
        "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt am", 
        "Zuletzt geändert am", "Status"
    };
    
    public static final String[] SearchColumns = new String[]{
        "Id", "Ersteller Id", null, "Kunden-Nr.", "Vertrags Id", "Interne Schadens-Nr.",
        "Art der Meldung", "Meldung von", "Meldung Zeit", "Schaden Zeit", 
        "Meldung Polizei", "Kategorie", "Bearbeiter Id", "Ort", "Umfang", "Hergang",
        "Weiterleitung VU am", "Weiterleitung VU Art",
        "Risiko", "Schadenshöhe", "Arbrechnungsart", "Gutachten erforderlich",
        "Schadens-Nr. (VU)", "Status vom", null, "Interne Info", "Notizen",       
        "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3",
        "Benutzerdefiniert 4", "Benutzerdefiniert 5", "Erstellt am", 
        "Zuletzt geändert am", "Status"
    };       
    
    public static final String[] ColumnsDB = new String[]{
        "id", "creatorId", null, "kundenNr", "vertragsId", "schadenNr",
        "meldungArt", "meldungVon", "meldungTime", "schaedenTime", 
        "schadenPolizei", "schadenKategorie", "schadenBearbeiter", "schadenOrt", 
        "schadenUmfang", "schadenHergang",
        "vuWeiterleitungTime", "vuWeiterleitungArt",
        "risiko", "schadenHoehe", "schadenAbrechnungArt", "vuGutachten",
        "vuSchadennummer", "vuStatusDatum", null, "interneInfo", "notiz",       
        "custom1", "custom2", "custom3",
        "custom4", "custom5", "created", 
        "modified", "status"
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
