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

package de.maklerpoint.office.System.Configuration;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class IdleConfig {

    // Time for rechecking computer state (in senconds)
    public static int STATE_POLLING_TIME = Config.getConfigInt("timerPolling", 30);

    // Time in seconds without user action for away state
    public static int STATE_AWAY_TIME = Config.getConfigInt("timerAway", 900);

    // Time in seconds without user action for idle state
    public static int STATE_IDLE_TIME = Config.getConfigInt("timerIdle", 1800);

}
