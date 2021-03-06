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
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Karte.KarteSuche;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Tags.TagObj;
import de.maklerpoint.office.Tags.Tags;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.StringReader;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class TerminPanel extends JPanel implements ActionListener, MouseListener {

    private ImageIcon background;
    private String text;
    private TagObj tag;
    private JPanel platzHolder;
    private JPopupMenu popup;
    private TerminObj termin;

    private CRMView crm;

    private panelTagesAnsicht tagpanel;
    private panelWochenAnsicht wochenpanel;

    /**
     * 
     * @param background
     * @param termin
     * @param tag
     * @param wochenpanel
     */

    public TerminPanel(ImageIcon background, final TerminObj termin, CRMView crm, TagObj tag, panelWochenAnsicht wochenpanel) {
        this.background = background;
        this.tag = tag;
        this.termin = termin;
        this.crm = crm;
        this.wochenpanel = wochenpanel;
        initializeIt();
    }

    /**
     * 
     * @param background
     * @param termin
     * @param tag
     * @param tagpanel
     */
    
    public TerminPanel(ImageIcon background, final TerminObj termin, CRMView crm, TagObj tag, panelTagesAnsicht tagpanel) {
        this.background = background;
        this.tag = tag;
        this.termin = termin;
        this.crm = crm;
        this.tagpanel = tagpanel;
        initializeIt();
    }

    /**
     *
     * @param background
     * @param termin
     * @param tag
     */

    public TerminPanel(ImageIcon background, final TerminObj termin, CRMView crm, TagObj tag ) {
        this.background = background;        
        this.tag = tag;
        this.crm = crm;
        this.termin = termin;
        initializeIt();
    }

    /**
     * 
     */

    private void initializeIt() {
        platzHolder = new JPanel();
        platzHolder.setSize(40, super.getHeight());
        platzHolder.setPreferredSize(new Dimension(20, super.getHeight()));
        platzHolder.setOpaque(false);

        String termintext = "<html>" + "<body>" + termin.getBeschreibung() + "</body></html>";

        if(termin.getOrt() != null && termin.getOrt().length() > 0)
            termintext = "<html>" + "<body>" + termin.getBeschreibung()  + "<br />\n"
                    + "(<a href=\"" + termin.getId() + "\" title=\"Karte öffnen\">" + termin.getOrt() + "</a>)";

        this.text = termintext;

        JEditorPane area_text = new JEditorPane();
        area_text.setContentType("text/html");
        Font font = new JLabel().getFont();

         String stylesheet = "a { color:#4040D9; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:"
                 + font.getName() + "; font-size:" + font.getSize() + "pt;}";

        HTMLEditorKit kit = new HTMLEditorKit();
        area_text.setEditorKit(kit);
        HTMLDocument htmlDocument = (HTMLDocument)area_text.getDocument();
        StyleSheet sheet = new StyleSheet();
        try {
            sheet.loadRules(new StringReader(stylesheet), null);
            htmlDocument.getStyleSheet().addStyleSheet(sheet);
        } catch (Exception e) {
        }
        htmlDocument.setAsynchronousLoadPriority(-1);
        area_text.setDocument(htmlDocument);
        area_text.setText(text);

        area_text.setEditable(false);
        area_text.setForeground(new Color(Integer.valueOf(tag.getFontColor())));
        area_text.setOpaque(false);
        area_text.addHyperlinkListener(new HyperlinkListener(){

            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    KarteSuche.doExteneralSearch(termin.getOrt(), crm);
                }
            }

            });

        this.setLayout(new BorderLayout());
        this.add(platzHolder, BorderLayout.WEST);
        this.add(area_text, BorderLayout.CENTER);

        popup = new JPopupMenu();

        JMenuItem neuItem = new JMenuItem("Neuer Termin");
        neuItem.setMnemonic(KeyEvent.VK_N);
        neuItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                JDialog neuTerminBox = new NeuerTermin(mainFrame, false);
                neuTerminBox.setLocationRelativeTo(mainFrame);
                CRM.getApplication().show(neuTerminBox);
            }

        });

        popup.add(neuItem);

        popup.addSeparator();

        JMenuItem editItem = new JMenuItem("Bearbeiten");
        editItem.setMnemonic(KeyEvent.VK_B);
        editItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = CRM.getApplication().getMainFrame();
                JDialog neuTerminBox = new NeuerTermin(mainFrame, false, termin);
                neuTerminBox.setLocationRelativeTo(mainFrame);
                CRM.getApplication().show(neuTerminBox);
            }

        });

        popup.add(editItem);


        JMenuItem printItem = new JMenuItem("Drucken");
        printItem.setMnemonic(KeyEvent.VK_D);
        printItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                // TODO at print
            }

        });

        popup.add(printItem);

        popup.addSeparator();

        JMenu tagmenu = new JMenu("Markierung (Tag)");
        ButtonGroup group = new ButtonGroup();

        for(int i = 0; i < Tags.tags.length; i ++) {
           final TagObj avaibleTag = Tags.tags[i];
           JRadioButtonMenuItem kategorie = new JRadioButtonMenuItem(avaibleTag.getName());
           kategorie.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    TerminHelper.setTerminTag(termin, avaibleTag);
                }
           });
           if(avaibleTag.getName().equalsIgnoreCase(termin.getTag())) {
               kategorie.setSelected(true);
           }
           group.add(kategorie);

           tagmenu.add(kategorie);
        }

        popup.add(tagmenu);

        popup.addSeparator();

        JMenuItem deleteItem = new JMenuItem("Löschen");
        deleteItem.setMnemonic(KeyEvent.VK_L);
        deleteItem.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if(tagpanel != null)
                    TerminHelper.deleteTermin(termin.getId(), tagpanel);
                else if (wochenpanel != null)
                    TerminHelper.deleteTermin(termin.getId(), wochenpanel);
                else
                    TerminHelper.deleteTermin(termin.getId());
            }

        });

        popup.add(deleteItem);

        popup.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        popup.setForeground(Color.lightGray);

        platzHolder.addMouseListener(this);
        super.addMouseListener(this);
        area_text.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        if(mouseovered == false) {
//            if(this.icon != null) {
//                g.drawImage(icon.getImage(), 0, 0, this);
//            }
//        } else {
//            if(this.micon != null) {
//                g.drawImage(micon.getImage(), 0, 0, this);
//            }
//        }
        super.setBackground(new Color(Integer.valueOf(tag.getTagColor())));
        g.drawImage(background.getImage(), 0, 0, this);        
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {       
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
