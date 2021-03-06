/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Nachrichten.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.System.Status;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Wird alle x min. (default 10) zur Überprüfung auf neue Nachrichten getriggert
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class TriggerMessageJob implements Job {

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            boolean newm = MessageSQLMethods.checkNewMessages(DatabaseConnection.open(), 
                    BasicRegistry.currentUser.getId(), Status.NORMAL);
            Log.logger.debug("Suche nach neuen Benutzernachrichten (messages).");
            if(newm) {
                Log.logger.debug("Es sind neue Benutzernachrichten vorhanden.");
                if(CRMView.poplb_mail != null)
                CRMView.poplb_mail.setVisible(true);
            } else {
                Log.logger.debug("Es sind keine neuen Benutzernachrichten vorhanden.");
                if(CRMView.poplb_mail != null)
                CRMView.poplb_mail.setVisible(false);
            }
        } catch (SQLException ex) {
            Log.databaselogger.fatal("Fehler beim überprüfen auf neue Bentuzernachrichten (Messages)", ex);
        }
    }
    
}
