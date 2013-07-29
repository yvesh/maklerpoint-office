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
package de.maklerpoint.office.Gui.Tools;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Vertraege.VertragObj;
import javax.swing.JFrame;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class VertragAuswahlHelper {
    
    public static VertragObj getKundenVertrag(String kdnr){
        JFrame mainFrame = CRM.getApplication().getMainFrame();
        VertragAuswahlDialog vdial = new VertragAuswahlDialog(mainFrame, true, kdnr);
        vdial.setLocationRelativeTo(mainFrame);
        CRM.getApplication().show(vdial);
        
        return vdial.getReturn();
    }
            
    
}
