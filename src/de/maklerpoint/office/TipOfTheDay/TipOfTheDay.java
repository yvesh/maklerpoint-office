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

package de.maklerpoint.office.TipOfTheDay;

import de.maklerpoint.office.System.Configuration.Config;
import org.jdesktop.swingx.JXTipOfTheDay;
import org.jdesktop.swingx.tips.DefaultTipOfTheDayModel;
import org.jdesktop.swingx.tips.TipOfTheDayModel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class TipOfTheDay {
   

    public static void show() {
        JXTipOfTheDay tip = new JXTipOfTheDay(loadModel());

        tip.showDialog(tip, new JXTipOfTheDay.ShowOnStartupChoice() {

            public void setShowingOnStartup(boolean showOnStartup) {
                setStartupChoiceOption(showOnStartup);
            }

            public boolean isShowingOnStartup() {
                return isStartupChoiceOption();
            }
        });
    }


    private static TipOfTheDayModel loadModel() {
        DefaultTipOfTheDayModel model = new DefaultTipOfTheDayModel(TipOfTheDayTips.tips);
        return model;
    }

    private static boolean isStartupChoiceOption() {
	return Config.getConfigBoolean("showTipoftheday", true);
    }

    private static void setStartupChoiceOption(boolean val) {
        Config.setBoolean("showTipoftheday", val);
	//System.out.println("Show Tips on Startup: " + val);
	// Set the value in application settings here.
    }

    
}
