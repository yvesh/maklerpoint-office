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

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class AbstractStandardModel extends AbstractTableModel {

    String[] columnNames;
    private Object[][] data;

    public AbstractStandardModel(Object data[][], String[] columnnames) {
        this.data = data;
        this.columnNames = columnnames;
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
//        System.out.println("row: " + row + " | col " + col);
        if (data != null && data.length != 0) {
            return data[row][col];
        } else {
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    @Override
    public Class getColumnClass(int c) {
        Object value = this.getValueAt(0, c);
        //System.out.println("Value class: " + value.getClass());
        return (value == null ? Object.class : value.getClass());
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public void setData(Object[][] data) {
        this.data = data;
        fireTableDataChanged();
    }
    
    public void setColumnNames(String[] columnames) {
        this.columnNames = columnames;       
    }
}
