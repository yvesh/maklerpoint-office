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

import com.sun.jna.*;
import com.sun.jna.win32.*;

/**
 * Class to get the Idle Time (User away time) of a windows operating system (with jna)
 * Used in RunWatcher
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class Win32IdleTime extends Thread {

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);
       
        public int GetTickCount();
    };

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32)Native.loadLibrary("user32", User32.class);


        public static class LASTINPUTINFO extends Structure {
            public int cbSize = 8;

            public int dwTime;
        }

        public boolean GetLastInputInfo(LASTINPUTINFO result);
    };

    public int getIdleTimeMillisWin32() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
    }
}
