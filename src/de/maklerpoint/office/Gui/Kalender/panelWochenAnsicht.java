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
 * panelWochenAnsicht.java
 *
 * Created on Jul 27, 2010, 11:46:32 AM
 */

package de.maklerpoint.office.Gui.Kalender;

import de.maklerpoint.office.Gui.CRMView;
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
public class panelWochenAnsicht extends javax.swing.JPanel {

    private org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(panelWochenAnsicht.class);
    
    private Vector<Boolean> moTimeline;
    private Vector<Boolean> moTimeline2;
    private Vector<Boolean> diTimeline;
    private Vector<Boolean> diTimeline2;
    private Vector<Boolean> miTimeline;
    private Vector<Boolean> miTimeline2;
    private Vector<Boolean> doTimeline;
    private Vector<Boolean> doTimeline2;
    private Vector<Boolean> frTimeline;
    private Vector<Boolean> frTimeline2;
    private Vector<Boolean> saTimeline;
    private Vector<Boolean> saTimeline2;
    private Vector<Boolean> soTimeline;
    private Vector<Boolean> soTimeline2;

    private ArrayList<TerminObj> weekTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> moTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> diTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> miTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> doTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> frTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> saTermine = new ArrayList<TerminObj>();
    private ArrayList<TerminObj> soTermine = new ArrayList<TerminObj>();
    private CRMView crm;

    private Date activeDate;

    /** Creates new form panelWochenAnsicht */
    public panelWochenAnsicht(Date curdate, CRMView crm) {
        this.activeDate = curdate;
        this.crm = crm;
        initComponents();
    }

    public void loadPanels() {
        moTermine.clear();
        diTermine.clear();
        miTermine.clear();
        doTermine.clear();
        frTermine.clear();
        saTermine.clear();
        soTermine.clear();

        moTimeline = new Vector<Boolean>();
        moTimeline2 = new Vector<Boolean>();
        diTimeline = new Vector<Boolean>();
        diTimeline2 = new Vector<Boolean>();
        miTimeline = new Vector<Boolean>();
        miTimeline2 = new Vector<Boolean>();
        doTimeline = new Vector<Boolean>();
        doTimeline2 = new Vector<Boolean>();
        frTimeline = new Vector<Boolean>();
        frTimeline2 = new Vector<Boolean>();
        saTimeline = new Vector<Boolean>();
        saTimeline2 = new Vector<Boolean>();
        soTimeline = new Vector<Boolean>();
        soTimeline2 = new Vector<Boolean>();

        for(int i = 0; i < 1440; i++) {
            moTimeline.add(false);
            moTimeline2.add(false);
            diTimeline.add(false);
            diTimeline2.add(false);
            miTimeline.add(false);
            miTimeline2.add(false);
            doTimeline.add(false);
            doTimeline2.add(false);
            frTimeline.add(false);
            frTimeline2.add(false);
            saTimeline.add(false);
            saTimeline2.add(false);
            soTimeline.add(false);
            soTimeline2.add(false);
        }

        ImageIcon icon = resourceMap.getImageIcon("icon.bgcal");
        ImageIcon micon = resourceMap.getImageIcon("icon.bgcalm");

        JViewport viewport = scroll_week.getViewport();

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
        
        JPanel week = new JPanel();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM");

        Calendar c4 = Calendar.getInstance();
        

        c4.setTime(activeDate);
        JLabel montag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 2);
        montag.setText("Mo, " + df.format(c4.getTime()));
        montag.setFont(Font.decode("Arial-bold-12"));
        montag.setBounds(40, 10, 180, 20);
        montag.setHorizontalAlignment(JLabel.CENTER);

        JLabel dienstag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 3);
        dienstag.setText("Di., " + df.format(c4.getTime()));
        dienstag.setFont(Font.decode("Arial-bold-12"));
        dienstag.setBounds(220, 10, 180, 20);
        dienstag.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel mittwoch = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 4);
        mittwoch.setText("Mi., " + df.format(c4.getTime()));
        mittwoch.setFont(Font.decode("Arial-bold-12"));
        mittwoch.setBounds(400, 10, 180, 20);
        mittwoch.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel donnerstag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 5);
        donnerstag.setText("Do., " + df.format(c4.getTime()));
        donnerstag.setFont(Font.decode("Arial-bold-12"));
        donnerstag.setBounds(580, 10, 180, 20);
        donnerstag.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel freitag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 6);
        freitag.setText("Fr., " + df.format(c4.getTime()));
        freitag.setFont(Font.decode("Arial-bold-12"));
        freitag.setBounds(760, 10, 180, 20);
        freitag.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel samstag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 7);
        samstag.setText("Sam., " + df.format(c4.getTime()));
        samstag.setFont(Font.decode("Arial-bold-12"));
        samstag.setBounds(940, 10, 180, 20);
        samstag.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel sonntag = new JLabel();
        c4.set(Calendar.DAY_OF_WEEK, 1);
        sonntag.setText("Son., " + df.format(c4.getTime()));
        sonntag.setFont(Font.decode("Arial-bold-12"));
        sonntag.setBounds(1120, 10, 180, 20);
        sonntag.setHorizontalAlignment(JLabel.CENTER);

        JPanel header = new JPanel();
        header.setLayout(null);
        header.add(montag);
        header.add(dienstag);
        header.add(mittwoch);
        header.add(donnerstag);
        header.add(freitag);
        header.add(samstag);
        header.add(sonntag);

        header.setBounds(0, 0, 1299, 30);
     
        KalenderWochePanel cal0 = new KalenderWochePanel(icon, micon, week);
        cal0.setLayout(null);
        cal0.add(time0);
        KalenderWochePanel cal1 = new KalenderWochePanel(icon, micon, week);
        cal1.setLayout(null);
        cal1.add(time1);
        KalenderWochePanel cal2 = new KalenderWochePanel(icon, micon, week);
        cal2.setLayout(null);
        cal2.add(time2);
        KalenderWochePanel cal3 = new KalenderWochePanel(icon, micon, week);
        cal3.setLayout(null);
        cal3.add(time3);
        KalenderWochePanel cal4 = new KalenderWochePanel(icon, micon, week);
        cal4.setLayout(null);
        cal4.add(time4);
        KalenderWochePanel cal5 = new KalenderWochePanel(icon, micon, week);
        cal5.setLayout(null);
        cal5.add(time5);
        KalenderWochePanel cal6 = new KalenderWochePanel(icon, micon, week);
        cal6.setLayout(null);
        cal6.add(time6);
        KalenderWochePanel cal7 = new KalenderWochePanel(icon, micon, week);
        cal7.setLayout(null);
        cal7.add(time7);
        KalenderWochePanel cal8 = new KalenderWochePanel(icon, micon, week);
        cal8.setLayout(null);
        cal8.add(time8);
        KalenderWochePanel cal9 = new KalenderWochePanel(icon, micon, week);
        cal9.setLayout(null);
        cal9.add(time9);
        KalenderWochePanel cal10 = new KalenderWochePanel(icon, micon, week);
        cal10.setLayout(null);
        cal10.add(time10);
        KalenderWochePanel cal11 = new KalenderWochePanel(icon, micon, week);
        cal11.setLayout(null);
        cal11.add(time11);
        KalenderWochePanel cal12 = new KalenderWochePanel(icon, micon, week);
        cal12.setLayout(null);
        cal12.add(time12);
        KalenderWochePanel cal13 = new KalenderWochePanel(icon, micon, week);
        cal13.setLayout(null);
        cal13.add(time13);
        KalenderWochePanel cal14 = new KalenderWochePanel(icon, micon, week);
        cal14.setLayout(null);
        cal14.add(time14);
        KalenderWochePanel cal15 = new KalenderWochePanel(icon, micon, week);
        cal15.setLayout(null);
        cal15.add(time15);
        KalenderWochePanel cal16 = new KalenderWochePanel(icon, micon, week);
        cal16.setLayout(null);
        cal16.add(time16);
        KalenderWochePanel cal17 = new KalenderWochePanel(icon, micon, week);
        cal17.setLayout(null);
        cal17.add(time17);
        KalenderWochePanel cal18 = new KalenderWochePanel(icon, micon, week);
        cal18.setLayout(null);
        cal18.add(time18);
        KalenderWochePanel cal19 = new KalenderWochePanel(icon, micon, week);
        cal19.setLayout(null);
        cal19.add(time19);
        KalenderWochePanel cal20 = new KalenderWochePanel(icon, micon, week);
        cal20.setLayout(null);
        cal20.add(time20);
        KalenderWochePanel cal21 = new KalenderWochePanel(icon, micon, week);
        cal21.setLayout(null);
        cal21.add(time21);
        KalenderWochePanel cal22 = new KalenderWochePanel(icon, micon, week);
        cal22.setLayout(null);
        cal22.add(time22);
        KalenderWochePanel cal23 = new KalenderWochePanel(icon, micon, week);
        cal23.setLayout(null);
        cal23.add(time23);

        week.setLayout(null);
        week.setSize(1299, 60 * 24);
        week.setPreferredSize(new Dimension(1299, 60*24));

        cal0.setBounds(0, 0, 1299, 60);
        cal1.setBounds(0, 60, 1299, 60);
        cal2.setBounds(0, 120, 1299, 60);
        cal3.setBounds(0, 180, 1299, 60);
        cal4.setBounds(0, 240, 1299, 60);
        cal5.setBounds(0, 300, 1299, 60);
        cal6.setBounds(0, 360, 1299, 60);
        cal7.setBounds(0, 420, 1299, 60);
        cal8.setBounds(0, 480, 1299, 60);
        cal9.setBounds(0, 540, 1299, 60);
        cal10.setBounds(0, 600, 1299, 60);
        cal11.setBounds(0, 660, 1299, 60);
        cal12.setBounds(0, 720, 1299, 60);
        cal13.setBounds(0, 780, 1299, 60);
        cal14.setBounds(0, 840, 1299, 60);
        cal15.setBounds(0, 900, 1299, 60);
        cal16.setBounds(0, 960, 1299, 60);
        cal17.setBounds(0, 1020, 1299, 60);
        cal18.setBounds(0, 1080, 1299, 60);
        cal19.setBounds(0, 1140, 1299, 60);
        cal20.setBounds(0, 1200, 1299, 60);
        cal21.setBounds(0, 1260, 1299, 60);
        cal22.setBounds(0, 1320, 1299, 60);
        cal23.setBounds(0, 1380, 1299, 60);

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(activeDate);

        TerminObj[] termine = KalenderRegistry.getTermine(true);

        if(termine == null) {
            Log.logger.info("Keine Termine in der Datenbank " + 
                    calendar.get(Calendar.WEEK_OF_YEAR) + " "
                    + calendar.get(Calendar.YEAR));
        } else {
            System.out.println("Lade Termine");

            for(int i = 0; i < termine.length; i++) {
                TerminObj termin = termine[i];
                calendar2.setTime(termin.getStart());

                if(sameWeek(calendar, calendar2)) {
                    weekTermine.add(termin);
                } else {
                    // Ende der Woche
                    calendar2.setTime(termin.getEnde());

                    if(sameWeek(calendar, calendar2)) {
                        weekTermine.add(termin);
                    }
                }
            }
        }

        if(weekTermine.isEmpty()) {
            Log.logger.info("Keine Termine in dieser Woche " + calendar.get(Calendar.WEEK_OF_YEAR) + " " + calendar.get(Calendar.YEAR));
        } else {
            for(int i = 0; i < weekTermine.size(); i++) {
                TerminObj termin = (TerminObj) weekTermine.toArray()[i];
                ImageIcon terminicon = resourceMap.getImageIcon("icon.bgtermin");

                TagObj tag = Tags.getTag(termin.getTag());
                
                if(tag == null) {
                    tag = Tags.getTag("Standard");
                    Log.logger.warn("Das Tag mit dem Namen " + termin.getTag() + " existiert in dieser Installation nicht");
                }

                TerminPanel terminPanel = new TerminPanel(terminicon, termin, crm, tag, this);
                
                calendar.setTime(termin.getStart());
                calendar2.setTime(termin.getEnde());

                long diffMinutes = (calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / (60 * 1000);

                Log.logger.debug("Terminkalender differenz [Wochenansicht]: " + diffMinutes);

                int height = (int) (diffMinutes);

                calendar2.set(Calendar.HOUR_OF_DAY, 0);
                calendar2.set(Calendar.MINUTE, 0);

                long diffZero = (calendar.getTimeInMillis() - calendar2.getTimeInMillis()) / (60 * 1000);
                Log.logger.debug("Terminkalender differenz von Mitternacht [Tagesansicht]: " + diffZero);

                calendar2.setTime(termin.getEnde());

                int ypos = (int) (diffZero);

                int startdayofweek = calendar.get(Calendar.DAY_OF_WEEK);
                int enddayofweek = calendar2.get(Calendar.DAY_OF_WEEK);

                int xpos = 43;

                if(startdayofweek == Calendar.SUNDAY)
                    xpos = 1123; // End
                else if(startdayofweek == Calendar.MONDAY)
                    xpos = 43;
                else if(startdayofweek == Calendar.TUESDAY)
                    xpos = 223;
                else if(startdayofweek == Calendar.WEDNESDAY)
                    xpos = 403;
                else if(startdayofweek == Calendar.THURSDAY)
                    xpos = 583;
                else if(startdayofweek == Calendar.FRIDAY)
                    xpos = 763;
                else if(startdayofweek == Calendar.SATURDAY)
                    xpos = 943;

                boolean belegt = false;
                boolean belegt2 = false;

                for(int j = 0; j < 1440; j++) {
                    if(j >= diffZero && j <= diffZero + diffMinutes) {
                        if(startdayofweek == Calendar.MONDAY) {
                            if(moTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(moTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    moTimeline2.set(j, true);
                                }
                            } else {
                               moTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.TUESDAY) {
                            if(diTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(diTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    diTimeline2.set(j, true);
                                }
                            } else {
                               diTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.WEDNESDAY) {
                            if(miTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(miTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    miTimeline2.set(j, true);
                                }
                            } else {
                               miTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.THURSDAY) {
                            if(doTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(doTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    doTimeline2.set(j, true);
                                }
                            } else {
                               doTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.FRIDAY) {
                            if(frTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(frTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    frTimeline2.set(j, true);
                                }
                            } else {
                               frTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.SATURDAY) {
                            if(frTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(frTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    frTimeline2.set(j, true);
                                }
                            } else {
                               frTimeline.set(j, true);
                            }
                        }
                        if(startdayofweek == Calendar.SUNDAY) {
                            if(frTimeline.get(j) == (Boolean) true) {
                                belegt = true;
                                if(frTimeline2.get(j) == (Boolean) true) {
                                    belegt2 = true;
                                } else {
                                    frTimeline2.set(j, true);
                                }
                            } else {
                               frTimeline.set(j, true);
                            }
                        }
                    } // END IF
                } // END FOR
                

                terminPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                if(belegt) {
                    if(belegt2) {
                        terminPanel.setBounds(xpos, ypos, 170, height);
                    } else {
                        terminPanel.setBounds(xpos, ypos, 170, height);
                    }
                } else {
                    terminPanel.setBounds(xpos, ypos, 170, height);
                }

                week.add(terminPanel);
                week.revalidate();
            }// End for
         }

        week.add(header);
        week.add(cal0);
        week.add(cal1);
        week.add(cal2);
        week.add(cal3);
        week.add(cal4);
        week.add(cal5);
        week.add(cal6);
        week.add(cal7);
        week.add(cal8);
        week.add(cal9);
        week.add(cal10);
        week.add(cal11);
        week.add(cal12);
        week.add(cal13);
        week.add(cal14);
        week.add(cal15);
        week.add(cal16);
        week.add(cal17);
        week.add(cal18);
        week.add(cal19);
        week.add(cal20);
        week.add(cal21);
        week.add(cal22);
        week.add(cal23);

        viewport.add(week);
        viewport.revalidate();

        scroll_week.createHorizontalScrollBar();
        scroll_week.createVerticalScrollBar();

        scroll_week.getViewport().setViewPosition((new java.awt.Point(0, 60*7)));
        scroll_week.validate();

        revalidate();
    }

    public boolean sameWeek(Calendar c1, Calendar c2) {
          return ( c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR)) &&
              ( c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) );
    }

    public boolean sameDay(Calendar c1, Calendar c2) {
          return ( c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) ) &&
              ( c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) );
    }

    public boolean afterToday(Calendar c1, Calendar c2) {

        if(c1.getTimeInMillis() < c2.getTimeInMillis())
            return true;
        else
            return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll_week = new javax.swing.JScrollPane();

        setName("Form"); // NOI18N

        scroll_week.setName("scroll_week"); // NOI18N
        this.loadPanels();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_week, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_week, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scroll_week;
    // End of variables declaration//GEN-END:variables

}
