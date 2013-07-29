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
package de.maklerpoint.office.Schnittstellen.Outlook;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.DateUtilities;
import com.jacob.com.Dispatch;
import de.maklerpoint.office.Kalender.Termine.TerminObj;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class OutlookTerminExport {

    public static void exportTermin(TerminObj termin) throws IOException {
        ActiveXComponent outlook = new ActiveXComponent("Outlook.Application");
//        Object exp = xl.getObject();
        double dDataFrom = DateUtilities.convertDateToWindowsTime(termin.getStart());
        double dDataTo = DateUtilities.convertDateToWindowsTime(termin.getEnde());
        Runtime.getRuntime().exec("cmd.exe /c start outlook.exe"); // This runs Outlook, if present
        Dispatch oAppointmentItem = outlook.call(outlook, "CreateItem", 1).toDispatch();
        Dispatch.put(oAppointmentItem, "Subject", termin.getBeschreibung());
        Dispatch.put(oAppointmentItem, "Start", dDataFrom);
        Dispatch.put(oAppointmentItem, "End", dDataTo);
        Dispatch.call(oAppointmentItem, "Save");
    }
}
