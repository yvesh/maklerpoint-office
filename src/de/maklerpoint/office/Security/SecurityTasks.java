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

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Logging.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Key = static final identifier
 * Value = min. UserLevel
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SecurityTasks {

    public static final int BACKUP = 1;
    public static final int BACKUP_AUTO = 2;
    public static final int DOKUMENT_DELETE = 3;
    public static final int DOKUMENT_ARCHIVE = 4;
    public static final int DOKUMENT_EDIT = 5;
    public static final int DOKUMENT_DELETE_ANDERERBENUTZER = 6;
    public static final int DOKUMENT_ARCHIVE_ANDERERBENUTZER = 7;
    public static final int AUFGABE_EDIT_ANDERERBENUTZER = 8;
    public static final int AUFGABE_PUBLIC = 9;
    public static final int TERMIN_EDIT_ANDERERBENUTZER = 10;
    public static final int TERMIN_PUBLIC = 11;
    public static final int STARTNACHRICHT_EDIT = 12;
    public static final int STARTNACHRICHT_DELETE = 13;
    public static final int STARTNACHRICHT_CREATE = 14;
    
    public static final int BENUTZER_CREATE = 15;
    public static final int BENUTZER_MANAGEMENT = 16;
    
    public static final int DATABASE_CHECK = 17;
    public static final int DATABASE_SQL_ZUGRIFF = 18;
    public static final int DATABASE_SQL_SKRIPT = 19;
    public static final int MAKLERPOINT_SKRIPT = 20;
    
    
    public static HashMap<Integer, Integer> hs = new HashMap<Integer, Integer>();

    public static void initializeDefault() {
        hs.put(BACKUP, SecurityRoles.ADMINISTRATOR);
        hs.put(BACKUP_AUTO, SecurityRoles.SUPERADMINISTRATOR);
        hs.put(DOKUMENT_DELETE, SecurityRoles.VERMITTLER);
        hs.put(DOKUMENT_ARCHIVE, SecurityRoles.VERMITTLER);
        hs.put(DOKUMENT_EDIT, SecurityRoles.VERMITTLER);
        hs.put(DOKUMENT_DELETE_ANDERERBENUTZER, SecurityRoles.ADMINISTRATOR);
        hs.put(DOKUMENT_ARCHIVE_ANDERERBENUTZER, SecurityRoles.ADMINISTRATOR);
        
        hs.put(AUFGABE_EDIT_ANDERERBENUTZER, SecurityRoles.ADMINISTRATOR);
        hs.put(AUFGABE_PUBLIC, SecurityRoles.EDITOR);
        
        hs.put(TERMIN_EDIT_ANDERERBENUTZER, SecurityRoles.EDITOR);
        hs.put(TERMIN_PUBLIC, SecurityRoles.EDITOR);
        
        hs.put(STARTNACHRICHT_EDIT, SecurityRoles.ADMINISTRATOR);
        hs.put(STARTNACHRICHT_DELETE, SecurityRoles.ADMINISTRATOR);
        hs.put(STARTNACHRICHT_CREATE, SecurityRoles.ADMINISTRATOR);
        
        hs.put(BENUTZER_CREATE, SecurityRoles.SUPERADMINISTRATOR);
        hs.put(BENUTZER_MANAGEMENT, SecurityRoles.ADMINISTRATOR);
        
        hs.put(DATABASE_CHECK, SecurityRoles.ADMINISTRATOR);
        hs.put(DATABASE_SQL_ZUGRIFF, SecurityRoles.SUPERADMINISTRATOR);
        hs.put(DATABASE_SQL_SKRIPT, SecurityRoles.SUPERADMINISTRATOR);
        hs.put(MAKLERPOINT_SKRIPT, SecurityRoles.SUPERADMINISTRATOR);
        
        Log.logger.info("Das Rechtesystem wurde erfolgreich initialisiert");
    }

    public static int getValue(int key) {
        try {      
            Connection con = DatabaseConnection.open();
            String sql = "Select task, value FROM benutzer_acl WHERE task = ?";
            PreparedStatement statement = con.prepareStatement(sql, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, key);
            
            ResultSet entry = statement.executeQuery();
        
            entry.last();
            int rows = entry.getRow();
            entry.beforeFirst();

            if (rows == 0) {
                entry.close();
                statement.close();
                con.close();
                return hs.get(key); // DEFAULT VALUE
            }
            
            entry.next();                        
            
            int value = entry.getInt("value");
            
            statement.close();
            con.close();
            
            return value;
            
        } catch (Exception e) {
            System.out.println("Key: " + key);
            Log.databaselogger.warn("Fehler beim laden des Zugriffslevels für " + key + ". Falle Zurück auf Standardrechte", e);
            return hs.get(key); // DEFAULT VALUE
        }       
    }
}
