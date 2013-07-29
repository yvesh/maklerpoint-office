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

package de.maklerpoint.office.Idle;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Class to get the Idle Time (User away time) of a mac os operating system (with jna)
 * Used in RunWatcher
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class MacIdleTime {

    public interface ApplicationServices extends Library {

    ApplicationServices INSTANCE = (ApplicationServices)
            Native.loadLibrary( "ApplicationServices" , ApplicationServices.class);

    int kCGAnyInputEventType = ~0;
    int kCGEventSourceStatePrivate = -1;
    int kCGEventSourceStateCombinedSessionState =0;
    int kCGEventSourceStateHIDSystemState = 1;
    public double CGEventSourceSecondsSinceLastEventType (int sourceStateId, int eventType);
    }

    public long getIdleTimeMillisecondsMac() {
        double idleTimeSeconds = ApplicationServices.INSTANCE.CGEventSourceSecondsSinceLastEventType(ApplicationServices.kCGEventSourceStateCombinedSessionState, ApplicationServices.kCGAnyInputEventType);
        return (long) (idleTimeSeconds * 1000);
    }

}
