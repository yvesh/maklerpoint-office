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

/**
 * Static final vars around ComputerState
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author George Karpouzas <www.webnetsoft.gr>
 */

public class ComputerState {

    private ComputerState(){
    }

    public final static int UNKNOWN = 0;
    public final static int ONLINE = 1;
    public final static int AWAY = 2;
    public final static int IDLE = 3;

}
