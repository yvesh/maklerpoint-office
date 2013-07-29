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

package de.maklerpoint.office.Tools;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author yves
 */
public class ImageComboBoxRenderer extends JLabel implements ListCellRenderer{

    private ImageIcon[] images;
    String[] data;

    public ImageComboBoxRenderer(ImageIcon[] images, String[] data) {
        this.data = data;
        this.images = images;
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        int selectedIndex = ((Integer)value).intValue();

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon = images[selectedIndex];
            String text = data[selectedIndex];
            setIcon(icon);
            if (icon != null) {
                setText(text);
                setFont(list.getFont());
            } else {
                setText(text);
                setFont(list.getFont());
            }
            return this;
    }

}
