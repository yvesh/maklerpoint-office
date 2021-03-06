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
package de.maklerpoint.office.Security;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SecurityRoles {
    
    public static final int UNTERVERMITTLER = 0;
    public static final int VERMITTLER = 1;
    public static final int EDITOR = 2;
    public static final int ADMINISTRATOR = 3;
    public static final int SUPERADMINISTRATOR = 4;
    
    public static String getUserLevelName(int type) {
        switch(type) {
            case 0:
                return "Untervermittler";

            case 1:
                return "Vermittler";

            case 2:
                return "Editor";

            case 3:
                return "Administrator";

            case 4:
                return "Super Administrator";

                // ADD Security exception
            default:
                return "Unbekannt";
        }
    }
    
}
