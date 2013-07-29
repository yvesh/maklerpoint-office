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

package de.maklerpoint.office.Gui.Splashscreen;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @deprecated
 * @author yves
 */

public class FakeSplashScreen {

    public FakeSplashScreen() {
        JProgressBar pb = new JProgressBar(0,100);
        pb.setPreferredSize(new Dimension(300,20));
        pb.setString("Working");
        pb.setStringPainted(true);
        pb.setValue(0);

        JLabel label = new JLabel("Progress: ");

        JPanel center_panel = new JPanel();
        center_panel.add(label);
        center_panel.add(pb);

        
    }

}
