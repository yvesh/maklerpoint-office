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

package de.maklerpoint.office.Gui.Components;

import de.maklerpoint.office.Gui.CRMView;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class AcyranceTabComponent extends JPanel {

    private final JTabbedPane pane;
    private CRMView view;

    public AcyranceTabComponent(final JTabbedPane pane, CRMView view, Icon icon){
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if(pane == null)
        {
            throw new NullPointerException("TabbedPane ist null");
        }
        this.pane = pane;
        this.view = view;
        setOpaque(false);        

        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(AcyranceTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);

        label.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        JButton button = new TabCloseButton();
        label.setIcon(icon);
        add(button);
        setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
    }


    private class TabCloseButton extends JButton implements ActionListener {
        public TabCloseButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Tab schliessen");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(AcyranceTabComponent.this);
            String name = pane.getComponentAt(i).getName();
//            if(name.equalsIgnoreCase("panelLeftAdressbuch")) {
//                view.addressbuchCheckItem.setSelected(false);
//            } else if (name.equalsIgnoreCase("panelLeftKarte")) {
//                view.karteCheckItem.setSelected(false);
//            } else if (name.equalsIgnoreCase("panelLeftTasks")) {
//                view.uebersichtCheckItem.setSelected(false);
//            } else if (name.equalsIgnoreCase("panelLeftHelp")) {
//                view.hilfeCheckItem.setSelected(false);
//            } else if (name.equalsIgnoreCase("panelLeftShortcuts")) {
//                view.shortcutsCheckItem.setSelected(false);
//            }
//
//            if (i != -1) {
//                pane.remove(i);
//                if(view.paneLeft.getTabCount() == 0)
//                {
//                    view.paneLeft.setVisible(false);
//                    view.checkLeftItem.setSelected(false);
//                }
//            }            
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };

}
