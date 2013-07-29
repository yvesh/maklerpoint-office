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

/*
 * panelTagesAnsicht.java
 *
 * Created on Jul 10, 2010, 3:04:53 PM
 */

package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Kalender.KalenderBerechnungen;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.KalenderRegistry;
import de.maklerpoint.office.Tags.TagObj;
import de.maklerpoint.office.Tags.Tags;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class panelTagesAnsicht extends javax.swing.JPanel {

    private Date activeDate;
    private org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelTagesAnsicht.class);
    private ArrayList<TerminObj> heutigeTermine = new ArrayList<TerminObj>();

    private Vector<Boolean> timeline;
    private Vector<Boolean> timeline2;

    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    private CRMView crm;

    /** Creates new form panelTagesAnsicht */
    public panelTagesAnsicht(Date curdate, CRMView crm) {
        activeDate = curdate;
        this.crm = crm;
        initComponents();
    }

    //        GridBagConstraints c = new GridBagConstraints();
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 0;
//        c.weighty = 0;
//        c.anchor = GridBagConstraints.NORTHWEST;


    /**
     * 
     */

    public void loadPanels() {
        heutigeTermine.clear();
        timeline = new Vector<Boolean>();
        timeline2 = new Vector<Boolean>();

        for(int i = 0; i < 1440; i++) {
            timeline.add(false);
            timeline2.add(false);
        }
        
        ImageIcon icon = resourceMap.getImageIcon("icon.bgcal");
        ImageIcon micon = resourceMap.getImageIcon("icon.bgcalm");

        JViewport viewport = scroll_kalender.getViewport();

        JLabel time0 = new JLabel();
        time0.setText("00");
        time0.setFont(Font.decode("Arial-bold-12"));
        time0.setBounds(9,0,20,20);

        JLabel time1 = new JLabel();
        time1.setText("01");
        time1.setFont(Font.decode("Arial-bold-12"));
        time1.setBounds(9,0,20,20);

        JLabel time2 = new JLabel();
        time2.setText("02");
        time2.setFont(Font.decode("Arial-bold-12"));
        time2.setBounds(9,0,20,20);

        JLabel time3 = new JLabel();
        time3.setText("03");
        time3.setFont(Font.decode("Arial-bold-12"));
        time3.setBounds(9,0,20,20);

        JLabel time4 = new JLabel();
        time4.setText("04");
        time4.setFont(Font.decode("Arial-bold-12"));
        time4.setBounds(9,0,20,20);

        JLabel time5 = new JLabel();
        time5.setText("05");
        time5.setFont(Font.decode("Arial-bold-12"));
        time5.setBounds(9,0,20,20);

        JLabel time6 = new JLabel();
        time6.setText("06");
        time6.setFont(Font.decode("Arial-bold-12"));
        time6.setBounds(9,0,20,20);

        JLabel time7 = new JLabel();
        time7.setText("07");
        time7.setFont(Font.decode("Arial-bold-12"));
        time7.setBounds(9,0,20,20);

        JLabel time8 = new JLabel();
        time8.setText("08");
        time8.setFont(Font.decode("Arial-bold-12"));
        time8.setBounds(9,0,20,20);

        JLabel time9 = new JLabel();
        time9.setText("09");
        time9.setFont(Font.decode("Arial-bold-12"));
        time9.setBounds(9,0,20,20);

        JLabel time10 = new JLabel();
        time10.setText("10");
        time10.setFont(Font.decode("Arial-bold-12"));
        time10.setBounds(9,0,20,20);

        JLabel time11 = new JLabel();
        time11.setText("11");
        time11.setFont(Font.decode("Arial-bold-12"));
        time11.setBounds(9,0,20,20);

        JLabel time12 = new JLabel();
        time12.setText("12");
        time12.setFont(Font.decode("Arial-bold-12"));
        time12.setBounds(9,0,20,20);

        JLabel time13 = new JLabel();
        time13.setText("13");
        time13.setFont(Font.decode("Arial-bold-12"));
        time13.setBounds(9,0,20,20);

        JLabel time14 = new JLabel();
        time14.setText("14");
        time14.setFont(Font.decode("Arial-bold-12"));
        time14.setBounds(9,0,20,20);

        JLabel time15 = new JLabel();
        time15.setText("15");
        time15.setFont(Font.decode("Arial-bold-12"));
        time15.setBounds(9,0,20,20);

        JLabel time16 = new JLabel();
        time16.setText("16");
        time16.setFont(Font.decode("Arial-bold-12"));
        time16.setBounds(9,0,20,20);

        JLabel time17 = new JLabel();
        time17.setText("17");
        time17.setFont(Font.decode("Arial-bold-12"));
        time17.setBounds(9,0,20,20);

        JLabel time18 = new JLabel();
        time18.setText("18");
        time18.setFont(Font.decode("Arial-bold-12"));
        time18.setBounds(9,0,20,20);

        JLabel time19 = new JLabel();
        time19.setText("19");
        time19.setFont(Font.decode("Arial-bold-12"));
        time19.setBounds(9,0,20,20);

        JLabel time20 = new JLabel();
        time20.setText("20");
        time20.setFont(Font.decode("Arial-bold-12"));
        time20.setBounds(9,0,20,20);

        JLabel time21 = new JLabel();
        time21.setText("21");
        time21.setFont(Font.decode("Arial-bold-12"));
        time21.setBounds(9,0,20,20);

        JLabel time22 = new JLabel();
        time22.setText("22");
        time22.setFont(Font.decode("Arial-bold-12"));
        time22.setBounds(9,0,20,20);

        JLabel time23 = new JLabel();
        time23.setText("23");
        time23.setFont(Font.decode("Arial-bold-12"));
        time23.setBounds(9,0,20,20);

        JPanel day = new JPanel();
       
        KalenderTagPanel cal0 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal0.setLayout(null);
        cal0.add(time0);
        KalenderTagPanel cal1 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal1.setLayout(null);
        cal1.add(time1);
        KalenderTagPanel cal2 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal2.setLayout(null);
        cal2.add(time2);
        KalenderTagPanel cal3 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal3.setLayout(null);
        cal3.add(time3);
        KalenderTagPanel cal4 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal4.setLayout(null);
        cal4.add(time4);
        KalenderTagPanel cal5 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal5.setLayout(null);
        cal5.add(time5);
        KalenderTagPanel cal6 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal6.setLayout(null);
        cal6.add(time6);
        KalenderTagPanel cal7 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal7.setLayout(null);
        cal7.add(time7);
        KalenderTagPanel cal8 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal8.setLayout(null);
        cal8.add(time8);
        KalenderTagPanel cal9 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal9.setLayout(null);
        cal9.add(time9);
        KalenderTagPanel cal10 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal10.setLayout(null);
        cal10.add(time10);
        KalenderTagPanel cal11 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal11.setLayout(null);
        cal11.add(time11);
        KalenderTagPanel cal12 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal12.setLayout(null);
        cal12.add(time12);
        KalenderTagPanel cal13 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal13.setLayout(null);
        cal13.add(time13);
        KalenderTagPanel cal14 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal14.setLayout(null);
        cal14.add(time14);
        KalenderTagPanel cal15 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal15.setLayout(null);
        cal15.add(time15);
        KalenderTagPanel cal16 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal16.setLayout(null);
        cal16.add(time16);
        KalenderTagPanel cal17 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal17.setLayout(null);
        cal17.add(time17);
        KalenderTagPanel cal18 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal18.setLayout(null);
        cal18.add(time18);
        KalenderTagPanel cal19 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal19.setLayout(null);
        cal19.add(time19);
        KalenderTagPanel cal20 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal20.setLayout(null);
        cal20.add(time20);
        KalenderTagPanel cal21 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal21.setLayout(null);
        cal21.add(time21);
        KalenderTagPanel cal22 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal22.setLayout(null);
        cal22.add(time22);
        KalenderTagPanel cal23 = new KalenderTagPanel(icon, micon, day, activeDate);
        cal23.setLayout(null);
        cal23.add(time23);

//        MigLayout layout = new MigLayout("fill, insets 0 0 0 0");
//
//        JPanel day = new JPanel(layout);
//        day.setSize(this.scroll_kalender.getWidth(), 40 * 24);
//        day.add(cal0, "wrap 0px, growx, height 40:40:40");
//        day.add(cal1,"wrap 0px, growx, height 40:40:40");
//        day.add(cal2, "wrap 0px, growx, height 40:40:40");
//        day.add(cal3, "wrap 0px, growx, height 40:40:40");
//        day.add(cal4, "wrap 0px, growx, height 40:40:40");
//        day.add(cal5, "wrap 0px, growx, height 40:40:40");
//        day.add(cal6, "wrap 0px, growx, height 40:40:40");
//        day.add(cal7, "wrap 0px, growx, height 40:40:40");
//        day.add(cal8, "wrap 0px, growx, height 40:40:40");
//        day.add(cal9, "wrap 0px, growx, height 40:40:40");
//        day.add(cal10, "wrap 0px, growx, height 40:40:40");
//        day.add(cal11, "wrap 0px, growx, height 40:40:40");
//        day.add(cal12, "wrap 0px, growx, height 40:40:40");
//        day.add(cal13, "wrap 0px, growx, height 40:40:40");
//        day.add(cal14, "wrap 0px, growx, height 40:40:40");
//        day.add(cal15, "wrap 0px, growx, height 40:40:40");
//        day.add(cal16, "wrap 0px, growx, height 40:40:40");
//        day.add(cal17, "wrap 0px, growx, height 40:40:40");
//        day.add(cal18, "wrap 0px, growx, height 40:40:40");
//        day.add(cal19, "wrap 0px, growx, height 40:40:40");
//        day.add(cal20, "wrap 0px, growx, height 40:40:40");
//        day.add(cal21, "wrap 0px, growx, height 40:40:40");
//        day.add(cal22, "wrap 0px, growx, height 40:40:40");
//        day.add(cal23, "wrap 0px, growx, height 40:40:40");

//        MigLayout layout = new MigLayout("fill, insets 0 0 0 0");

       
//        JPanel holder = new JPanel();
//        LayoutManager overlay = new OverlayLayout(holder);
//
//        holder.setSize(1000, 960);
//        holder.setLayout(overlay);
//        holder.setPreferredSize(new Dimension(1000,960));
//        holder.setMinimumSize(new Dimension(1000, 960));
//
//
//        JButton button = new JButton("Small");
//        button.setMaximumSize(new Dimension(300,300));
//        button.setBackground(Color.red);
//        button.setBounds(20, 200, 200, 200);
//        holder.add(button);
//
//        holder.add(day);

        day.setLayout(null);
        day.setSize(800, 40 * 24);
        day.setPreferredSize(new Dimension(1800,960));
        time23.setBounds(9,0,20,20);

        cal0.setBounds(0, 0, 1800, 40);
        cal1.setBounds(0, 40, 1800, 40);
        cal2.setBounds(0, 80, 1800, 40);
        cal3.setBounds(0, 120, 1800, 40);
        cal4.setBounds(0, 160, 1800, 40);
        cal5.setBounds(0, 200, 1800, 40);
        cal6.setBounds(0, 240, 1800, 40);
        cal7.setBounds(0, 280, 1800, 40);
        cal8.setBounds(0, 320, 1800, 40);
        cal9.setBounds(0, 360, 1800, 40);
        cal10.setBounds(0, 400, 1800, 40);
        cal11.setBounds(0, 440, 1800, 40);
        cal12.setBounds(0, 480, 1800, 40);
        cal13.setBounds(0, 520, 1800, 40);
        cal14.setBounds(0, 560, 1800, 40);
        cal15.setBounds(0, 600, 1800, 40);
        cal16.setBounds(0, 640, 1800, 40);
        cal17.setBounds(0, 680, 1800, 40);
        cal18.setBounds(0, 720, 1800, 40);
        cal19.setBounds(0, 760, 1800, 40);
        cal20.setBounds(0, 800, 1800, 40);
        cal21.setBounds(0, 840, 1800, 40);
        cal22.setBounds(0, 880, 1800, 40);
        cal23.setBounds(0, 920, 1800, 40);

        // Loading Termine for today

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(activeDate);

        TerminObj[] termine = KalenderRegistry.getTermine(true);

        if(termine == null) {
            System.out.println("Keine Termine");
        } else {

            for(int i = 0; i < termine.length; i++) {
                   TerminObj termin = termine[i];

                   calendar2.setTime(termin.getStart());
                   boolean same = sameDay(calendar, calendar2);

                   if(same) {
                        heutigeTermine.add(termin);
                   } else {
        //               calendar2.setTime(termin.getEnde());
                       boolean after = this.afterToday(calendar, calendar2); // Start Datum nicht heute
                       if(after == false) {
                           calendar2.setTime(termin.getEnde()); // End Datum heute?
                           same = sameDay(calendar, calendar2);
                           if(same) {
                               heutigeTermine.add(termin);
                           } else {
                               after = afterToday(calendar, calendar2);
                               if(after) {
                                   heutigeTermine.add(termin);
                               }
                           }
                       }
                   }
                } // End for
           }

            if(heutigeTermine.isEmpty()) {
                Log.logger.info("Keine Termine am " + df.format(activeDate));
            } else {

        //       displayTermine(day);
                for(int i = 0; i < heutigeTermine.toArray().length; i ++) {
                    TerminObj termin = (TerminObj) heutigeTermine.toArray()[i];
                    ImageIcon terminicon = resourceMap.getImageIcon("icon.bgtermin");

        //                String termintext = termin.getBeschreibung()  + "\n(" + termin.getOrt() + ")";

                    TagObj tag = Tags.getTag(termin.getTag());

                    if(tag == null) {
                        tag = Tags.getTag("Standard");
                        Log.logger.warn("Das Tag mit dem Namen " + termin.getTag() + " existiert in dieser Installation nicht");
                    }

                    TerminPanel terminPanel = new TerminPanel(terminicon, termin, crm, tag, this);
        //          terminPanel.setBorder(BorderFactory.createEtchedBorder());

                    calendar.setTime(termin.getStart());
                    calendar2.setTime(termin.getEnde());

                    long diffMinutes = (calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / (60 * 1000);

                    Log.logger.debug("Terminkalender differenz [Tagesansicht]: " + diffMinutes);

                    // 0.6666

                    int height = (int) (diffMinutes * 0.66667);

                    calendar2.set(Calendar.HOUR_OF_DAY, 0);
                    calendar2.set(Calendar.MINUTE, 0);

                    long diffZero = (calendar.getTimeInMillis() - calendar2.getTimeInMillis()) / (60 * 1000);
                    Log.logger.debug("Terminkalender differenz von Mitternacht [Tagesansicht]: " + diffZero);

                    int ypos = (int) (diffZero * 0.66667);

                    boolean belegt = false;
                    boolean belegt2 = false;

                    for(int j = 0; j < 1440; j++) {
                        if(j >= diffZero && j <= diffZero + diffMinutes) {
                            if(timeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(timeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    timeline2.set(j, true);
                                }
                            } else {
                                timeline.set(j, true);
                            }
                        }
                    }

                    terminPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                    if(belegt) {
                        if(belegt2) {
                            terminPanel.setBounds(535, ypos, 250, height);
                        } else {
                            terminPanel.setBounds(280, ypos, 250, height);
                        }
                    } else {
                        terminPanel.setBounds(27, ypos, 250, height);
                    }

        //          terminPanel.setPreferredSize(new Dimension(200,200));
        //          terminPanel.setMaximumSize(new Dimension(200,200));
        //          terminPanel.setAlignmentX(0.5f);
        //          terminPanel.setAlignmentY(0.5f);

                    day.add(terminPanel);
                    day.revalidate();
                } // End for
            }
       
        day.add(cal0);
        day.add(cal1);
        day.add(cal2);
        day.add(cal3);
        day.add(cal4);
        day.add(cal5);
        day.add(cal6);
        day.add(cal7);
        day.add(cal8);
        day.add(cal9);
        day.add(cal10);
        day.add(cal11);
        day.add(cal12);
        day.add(cal13);
        day.add(cal14);
        day.add(cal15);
        day.add(cal16);
        day.add(cal17);
        day.add(cal18);
        day.add(cal19);
        day.add(cal20);
        day.add(cal21);
        day.add(cal22);
        day.add(cal23);


        viewport.add(day);
        viewport.revalidate();
        scroll_kalender.createVerticalScrollBar();
//        viewport.setView(cal8);

        
//        System.out.println("Val: " + scroll_kalender.getVerticalScrollBar().getValue());
//        System.out.println("val am: " + scroll_kalender.getVerticalScrollBar().getVisibleAmount());
//        System.out.println("Panel dimensions: " + day.getSize());


        //scroll_kalender.getVerticalScrollBar().setMaximum(1000);
//        scroll_kalender.getVerticalScrollBar().setValue(10);
//
//
        scroll_kalender.getViewport().setViewPosition((new java.awt.Point(0, 40*7)));
        scroll_kalender.validate();

        label_day.setText(KalenderBerechnungen.getWochenTag(activeDate) + ", den " + df.format(activeDate));

        Date gestern = new Date(activeDate.getTime() - 24 * 60 * 60 * 1000);
        Date morgen = new Date(activeDate.getTime() + 24 * 60 * 60 * 1000);
        label_previousday.setText(KalenderBerechnungen.getWochenTag(gestern));
        label_nextday.setText(KalenderBerechnungen.getWochenTag(morgen));


        revalidate();
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */

    public boolean sameDay(Calendar c1, Calendar c2) {
          return (
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) ) &&
              ( c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) );
    }

    /**
     * 
     * @param c1
     * @param c2
     * @return
     */

    public boolean afterToday(Calendar c1, Calendar c2) {

        if(c1.getTimeInMillis() < c2.getTimeInMillis())
            return true;
        else
            return false;
    }

    /**
     * 
     */

//    public void displayTermine(JPanel day) {
////
////        day.
//
//        for(int i = 0; i < heutigeTermine.toArray().length; i ++) {
//            TerminObj termin = (TerminObj) heutigeTermine.toArray()[i];
//            ImageIcon icon = resourceMap.getImageIcon("icon.bgtermin");
//
//            TerminPanel terminPanel = new TerminPanel(icon, termin.getBeschreibung(), Color.red);
//
//            terminPanel.setBounds(30, 130, 200, 200);
//            terminPanel.setPreferredSize(new Dimension(200,200));
//            terminPanel.setMaximumSize(new Dimension(200,200));
//            terminPanel.setAlignmentX(0.5f);
//            terminPanel.setAlignmentY(0.5f);
//
//            day.add(terminPanel);
//            day.revalidate();
//            System.out.println("Added");
//        }
//    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTop = new javax.swing.JPanel();
        btn_nextday = new javax.swing.JButton();
        btn_previousday = new javax.swing.JButton();
        label_nextday = new javax.swing.JLabel();
        label_previousday = new javax.swing.JLabel();
        label_day = new javax.swing.JLabel();
        panelKalender = new javax.swing.JPanel();
        scroll_kalender = new javax.swing.JScrollPane();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelTagesAnsicht.class);
        panelTop.setBackground(resourceMap.getColor("panelTop.background")); // NOI18N
        panelTop.setName("panelTop"); // NOI18N
        panelTop.setPreferredSize(new java.awt.Dimension(506, 25));

        btn_nextday.setBackground(resourceMap.getColor("btn_nextday.background")); // NOI18N
        btn_nextday.setIcon(resourceMap.getIcon("btn_nextday.icon")); // NOI18N
        btn_nextday.setText(resourceMap.getString("btn_nextday.text")); // NOI18N
        btn_nextday.setBorder(null);
        btn_nextday.setName("btn_nextday"); // NOI18N
        btn_nextday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextdayActionPerformed(evt);
            }
        });

        btn_previousday.setBackground(resourceMap.getColor("btn_previousday.background")); // NOI18N
        btn_previousday.setIcon(resourceMap.getIcon("btn_previousday.icon")); // NOI18N
        btn_previousday.setText(resourceMap.getString("btn_previousday.text")); // NOI18N
        btn_previousday.setBorder(null);
        btn_previousday.setName("btn_previousday"); // NOI18N
        btn_previousday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previousdayActionPerformed(evt);
            }
        });

        label_nextday.setFont(resourceMap.getFont("label_nextday.font")); // NOI18N
        label_nextday.setForeground(resourceMap.getColor("label_nextday.foreground")); // NOI18N
        label_nextday.setText(resourceMap.getString("label_nextday.text")); // NOI18N
        label_nextday.setName("label_nextday"); // NOI18N

        label_previousday.setForeground(resourceMap.getColor("label_previousday.foreground")); // NOI18N
        label_previousday.setText(resourceMap.getString("label_previousday.text")); // NOI18N
        label_previousday.setName("label_previousday"); // NOI18N

        label_day.setForeground(resourceMap.getColor("label_day.foreground")); // NOI18N
        label_day.setText(resourceMap.getString("label_day.text")); // NOI18N
        label_day.setName("label_day"); // NOI18N

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTopLayout.createSequentialGroup()
                .addComponent(btn_previousday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_previousday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                .addComponent(label_nextday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nextday))
            .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTopLayout.createSequentialGroup()
                    .addGap(0, 244, Short.MAX_VALUE)
                    .addComponent(label_day)
                    .addGap(0, 244, Short.MAX_VALUE)))
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_nextday, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
            .addComponent(btn_previousday, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
            .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label_nextday, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addComponent(label_previousday, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
            .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTopLayout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(label_day)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        panelKalender.setName("panelKalender"); // NOI18N

        scroll_kalender.setBorder(null);
        scroll_kalender.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_kalender.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_kalender.setName("scroll_kalender"); // NOI18N
        this.loadPanels();

        javax.swing.GroupLayout panelKalenderLayout = new javax.swing.GroupLayout(panelKalender);
        panelKalender.setLayout(panelKalenderLayout);
        panelKalenderLayout.setHorizontalGroup(
            panelKalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_kalender, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );
        panelKalenderLayout.setVerticalGroup(
            panelKalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_kalender, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
            .addComponent(panelKalender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelKalender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nextdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextdayActionPerformed
        Calendar c1 = Calendar.getInstance();
        c1.setTime(activeDate);
        c1.roll(Calendar.DAY_OF_YEAR, true);
        activeDate = c1.getTime();
        loadPanels();
    }//GEN-LAST:event_btn_nextdayActionPerformed

    private void btn_previousdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previousdayActionPerformed
        Calendar c1 = Calendar.getInstance();
        c1.setTime(activeDate);
        c1.roll(Calendar.DAY_OF_YEAR, false);
        activeDate = c1.getTime();
        loadPanels();
    }//GEN-LAST:event_btn_previousdayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_nextday;
    public javax.swing.JButton btn_previousday;
    public javax.swing.JLabel label_day;
    public javax.swing.JLabel label_nextday;
    public javax.swing.JLabel label_previousday;
    public javax.swing.JPanel panelKalender;
    public javax.swing.JPanel panelTop;
    public javax.swing.JScrollPane scroll_kalender;
    // End of variables declaration//GEN-END:variables

}
