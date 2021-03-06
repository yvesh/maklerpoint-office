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

package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.start.CRM;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KalenderTagPanel extends JPanel implements MouseListener {
    ImageIcon icon;
    ImageIcon micon;
    JPanel holder;
    private JPopupMenu popup;
    public static Date curdate;

    private boolean mouseovered = false;


    public KalenderTagPanel(ImageIcon icon, ImageIcon micon, JPanel holder, final Date curdate) {
        this.icon = icon;
        this.micon = micon;
        this.holder = holder;
        KalenderTagPanel.curdate = curdate;

        popup = new JPopupMenu();

        JMenuItem neuItem = new JMenuItem("Neuer Termin");
        neuItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                JDialog neuTerminBox = new NeuerTermin(mainFrame, false, curdate);
                neuTerminBox.setLocationRelativeTo(mainFrame);
                CRM.getApplication().show(neuTerminBox);
            }

        });

        popup.add(neuItem);
        popup.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(mouseovered == false) {
            if(this.icon != null) {
                g.drawImage(icon.getImage(), 0, 0, this);
            }
        } else {
            if(this.micon != null) {
                g.drawImage(micon.getImage(), 0, 0, this);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
    }

    public void mouseEntered(MouseEvent e) {       
//        Graphics g = super.getGraphics();
//        super.paint(g);
//        if(this.micon != null) {
//            g.drawImage(micon.getImage(), 0, 0, this);
//        }
        mouseovered = true;
        super.repaint();
        holder.repaint();
    }

    public void mouseExited(MouseEvent e) {      
//        Graphics g = super.getGraphics();
//        super.paint(g);
//        if(this.icon != null) {
//            g.drawImage(icon.getImage(), 0, 0, this);
//        }
        mouseovered = false;
         super.repaint();
         holder.repaint();
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
    

}
