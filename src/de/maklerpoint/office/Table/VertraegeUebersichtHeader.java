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
public class VertraegeUebersichtHeader {     
    
      public static final String[] ColumnsDB = new String[]{"id", "parentId",
          "versichererId", "produktId", "kundenKennung", "beratungsprotokollId",
          "mandantenId", "benutzerId", "vertragsTyp", "vertragsGrp", "policennr",
          "policeDatum", "wertungDatum", "courtage", "zahlWeise", "zahlArt", "selbstbeteiligung",
          "jahresNetto", "steuer", "gebuehr", "jahresBrutto", "rabatt", "zuschlag",
          "antrag", "faellig", "hauptfaellig", "beginn", "ablauf", "maklerEingang",
          "stornoDatum", "storno", "stornoGrund", "laufzeit", "courtage_datum",
          "comments", "custom1", "custom2", "custom3", "custom4", "custom5",
          "created", "modified", "status"          
      };
      
      public static final String[] Columns = new String[]{"Id", "Obervertrag", 
          "Versicherer", "Produkt", "Kundennr.", "Kunde", // 5          
          "Beratungsprotkoll Id",
          "Mandanten Id", "Benutzer", "Vertragstyp", "Vertrags Gruppen Id", "Policennummer", // 11
          "Policen Datum", "Wertungsdatum", "Courtage", "Zahlweise", "Zahlart", "Selbstbeteiligung", // 17
          "Jahres Netto", "Steuer", "Gebühr", "Jahres Brutto", "Rabatt", "Zuschlag", // 23
          "Antrag", "Fälligkeit", "Hauptfälligkeit", "Beginn", "Ablauf", "Eingang (Makler)", // 29
          "Storno Datum", "Storno Einreichung", "Storno Grund", "Laufzeit", "Courtage Datum",
          "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", 
          "Benutzerdefiniert 4", "Benutzerdefiniert 5",
          "Erstellt", "Zuletzt geändert", "Status"              
      }; // LENGTH 44 (ergo 43)
      
      public static final String[] SearchColumns = new String[]{"Id", "Obervertrag Id", 
          "Versicherer Id", "Produkt Id", "Kundennr.", "Beratungsprotkoll Id",
          "Mandanten Id", "Benutzer", "Vertragstyp", "Vertrags Gruppen Id", "Policennummer",
          "Policen Datum", "Wertungsdatum", "Courtage", "Zahlweise", "Zahlart", "Selbstbeteiligung",
          "Jahres Netto", "Steuer", "Gebühr", "Jahres Brutto", "Rabatt", "Zuschlag",
          "Antrag", "Fälligkeit", "Hauptfälligkeit", "Beginn", "Ablauf", "Eingang (Makler)",
          "Storno Datum", "Storno Einreichung", "Storno Grund", "Laufzeit", "Courtage Datum",
          "Kommentare", "Benutzerdefiniert 1", "Benutzerdefiniert 2", "Benutzerdefiniert 3", 
          "Benutzerdefiniert 4", "Benutzerdefiniert 5",
          "Erstellt", "Zuletzt geändert", "Status"              
      };
      
      public static Object[] getSearchColumnsWithField() {

        Object[] col = new Object[SearchColumns.length];
        int cnt = 0;

        for(int i = 0; i < SearchColumns.length; i++) {
            String column = SearchColumns[i];

            if(column != null) {
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
