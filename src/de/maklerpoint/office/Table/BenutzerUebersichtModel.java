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

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BenutzerUebersichtModel extends AbstractTableModel {

        Object[] columnNames;
        private Object[][] data = null;

        public BenutzerUebersichtModel(Object data[][], Object[] columnnames) {
            this.data = data;
            this.columnNames = columnnames;
        }

        @Override
        public int getRowCount() {
            if(data != null)
                return data.length;
            else
                return 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col].toString();

        }

        @Override
        public Object getValueAt(int row, int col) {
              if(data != null)
                return data[row][col];
            else
                return "";
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if(col == 0)
                return true;
            else
                return false;
        }


        @Override
        public Class getColumnClass(int c) {
            Object value=this.getValueAt(0,c);
            return (value==null?Object.class:value.getClass());
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

}
