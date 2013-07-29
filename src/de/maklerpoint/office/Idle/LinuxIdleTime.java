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
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.platform.unix.X11;

/**
 * Class to get the Idle Time (User away time) of a linux / X11 / x.org operating system (with jna)
 * Used in RunWatcher
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class LinuxIdleTime {

    interface Xss extends Library 
    {
        Xss INSTANCE = (Xss)Native.loadLibrary( "Xss", Xss.class);

        public class XScreenSaverInfo extends Structure
        {
            public X11.Window window; /* screen saver window */
            public int state; /* ScreenSaver{Off,On,Disabled} */
            public int kind; /* ScreenSaver{Blanked,Internal,External} */
            public NativeLong til_or_since; /* milliseconds */
            public NativeLong idle; /* milliseconds */
            public NativeLong event_mask; /* events */
        }

        XScreenSaverInfo XScreenSaverAllocInfo();
             int XScreenSaverQueryInfo(X11.Display dpy, X11.Drawable drawable, XScreenSaverInfo saver_info);
    }

    public long getIdleTimeMillisLinux() {
        X11.Window win=null;
        Xss.XScreenSaverInfo info=null;
        X11.Display dpy=null;

        final X11 x11 = X11.INSTANCE;
        final Xss xss = Xss.INSTANCE;

        long idlemillis = 0L;
        try {
            dpy = x11.XOpenDisplay(null);
            win = x11.XDefaultRootWindow(dpy);
            info = xss.XScreenSaverAllocInfo();
            xss.XScreenSaverQueryInfo(dpy, win, info);

            idlemillis = info.idle.longValue();
        } finally {
            if(info != null)
                x11.XFree(info.getPointer());
            info = null;

            if(dpy != null)
                x11.XCloseDisplay(dpy);
            dpy = null;
        }
        return idlemillis;
    }

}