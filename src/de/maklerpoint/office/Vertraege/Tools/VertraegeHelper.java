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
package de.maklerpoint.office.Vertraege.Tools;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class VertraegeHelper {
    
    /**
     * 
     * @param con
     * @param vtr
     * @return
     * @throws SQLException 
     */
    
    public static int createGrpAndUpdateVertraege(Connection con, VertragObj[] vtr) throws SQLException{
        String sql = "SELECT MAX(grpId) AS hoechste_grpid FROM vertraege_grp";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet entry = statement.executeQuery(); 
        
        entry.next();
        int grpid = entry.getByte("hoechste_grpid");
        entry.close();
        statement.close();
        
        grpid ++;
        
        Log.logger.debug("Die neue Gruppen Id für die Vertragsgruppe ist " + grpid);
        
        for(int i = 0; i < vtr.length; i++) {
            String sql2 = "INSERT INTO vertraege_grp (grpId, vertragId) " +
                          "VALUES (?, ?)";
            PreparedStatement statement2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            statement2.setInt(1, grpid);
            statement2.setInt(2, vtr[i].getId());
            statement2.execute();                
            statement2.close();
            
            // Update Vertraege
            vtr[i].setVertragGrp(grpid);
            VertraegeSQLMethods.updateVertragToGRP(DatabaseConnection.open(), vtr[i]);
            
        }
            
        return grpid;
    }
    
}
