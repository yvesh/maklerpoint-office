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

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class JLabelCellRenderer extends DefaultTableCellRenderer {

    public void fillColor(JTable t, JLabel l, boolean isSelected) {

        //setting the background and foreground when JLabel is selected

        if (isSelected) {
            l.setBackground(t.getSelectionBackground());
            l.setForeground(t.getSelectionForeground());
        } else {
            l.setBackground(t.getBackground());
            l.setForeground(t.getForeground());
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        if (value instanceof JLabel) {
            JLabel label = (JLabel)value;
            label.setOpaque(true);
            fillColor(table,label,isSelected);
            return label;

        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }


    }
}
