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

package de.maklerpoint.office.Idle.Execute;

import de.maklerpoint.office.Idle.IdleJob;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class IdleExecute implements IdleJob {

    public void execute() {
        if(Config.getConfigBoolean("autoClose", true)){
            Log.logger.fatal("Melde den Benutzer wegen zu langer inaktivität ab.");
            System.exit(33);
        }
    }

}
