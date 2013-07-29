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

import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DigitalClock implements Runnable {

    private JLabel _clock;

    public DigitalClock(JLabel clock)
    {
        this._clock = clock;
    }

    public void run() {
        while(true){
            try {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                _clock.setText(df.format(System.currentTimeMillis()));
                _clock.setForeground(Color.white);
                Thread.currentThread().sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
