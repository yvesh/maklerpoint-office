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


package de.maklerpoint.office.Kalender.Termine;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
@Root(name = "termine")
@Namespace(reference = "http://www.maklerpoint.de/dtd/termine.dtd")
public class TermineXMLParent {
    
    @ElementArray
    @Namespace(reference = "http://www.maklerpoint.de/dtd/termin-object.dtd")
    private TerminObj[] termine;

    public TerminObj[] getTermine() {
        return termine;
    }

    public void setTermine(TerminObj[] termine) {
        this.termine = termine;
    }
            
}
