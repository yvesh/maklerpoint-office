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
package de.maklerpoint.office.Email.Tools;

import de.maklerpoint.office.Email.EmailObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class EmailSQLMethods {

    /**
     * Java method that inserts a row in the generated sql table
     * and returns the new generated id
     * @param con (open java.sql.Connection)
     * @param creatorId
     * @return id (database row id [id])
     * @throws SQLException
     */
    public static int insertIntoEmails(Connection con, EmailObj mail)
            throws SQLException {
        int generatedId = -1;
        String sql = "INSERT INTO emails (creatorId, kundenKennung, benutzerId, versichererId, produktId, bpId, "
                + "schadenId, stoerId, vertragId, filetype, empfaenger, absender, "
                + "cc, betreff, body, nohtml, send, created, "
                + "modified, status, mandantenId, sendTime)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, mail.getCreatorId());
        statement.setString(2, mail.getKundenKennung());
        statement.setInt(3, mail.getBenutzerId());
        statement.setInt(4, mail.getVersichererId());
        statement.setInt(5, mail.getProduktId());
        statement.setInt(6, mail.getBpId());
        statement.setInt(7, mail.getSchadenId());
        statement.setInt(8, mail.getStoerId());
        statement.setInt(9, mail.getVertragId());
        statement.setInt(10, mail.getFiletype());
        statement.setString(11, mail.getEmpfaenger());
        statement.setString(12, mail.getAbsender());
        statement.setString(13, mail.getCc());
        statement.setString(14, mail.getBetreff());
        statement.setString(15, mail.getBody());
        statement.setString(16, mail.getNohtml());
        statement.setBoolean(17, mail.isSend());
        statement.setTimestamp(18, mail.getCreated());
        statement.setTimestamp(19, mail.getModified());
        statement.setInt(20, mail.getStatus());        
        statement.setInt(21, mail.getMandantenId());
        statement.setTimestamp(22, mail.getSendTime());
        statement.execute();
        ResultSet auto = statement.getGeneratedKeys();

        if (auto.next()) {
            generatedId = auto.getInt(1);
        } else {
            generatedId = -1;
        }

        statement.close();
        con.close();
        return generatedId;
    }

    /**
     * Java method that updates a row in the generated sql table
     * @param con (open java.sql.Connection)
     * @param creatorId
     * @param kundenKennung
     * @param benutzerId
     * @param versichererId
     * @param produktId
     * @param bpId
     * @param schadenId
     * @param stoerId
     * @param vertragId
     * @param filetype
     * @param empfaenger
     * @param absender
     * @param cc
     * @param betreff
     * @param body
     * @param nohtml
     * @param send
     * @param created
     * @param modified
     * @param status
     * @return boolean (true on success)
     * @throws SQLException
     */
    public static boolean updateEmails(Connection con, EmailObj mail)
            throws SQLException {
        String sql = "SELECT * FROM emails WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, mail.getId());
        ResultSet entry = statement.executeQuery();

        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            entry.close();
            statement.close();
            con.close();
            return false;
        }
        entry.next();

        entry.updateInt("creatorId", mail.getCreatorId());
        entry.updateString("kundenKennung", mail.getKundenKennung());
        entry.updateInt("benutzerId", mail.getBenutzerId());
        entry.updateInt("versichererId", mail.getVersichererId());
        entry.updateInt("produktId", mail.getProduktId());
        entry.updateInt("bpId", mail.getBpId());
        entry.updateInt("schadenId", mail.getSchadenId());
        entry.updateInt("stoerId", mail.getStoerId());
        entry.updateInt("vertragId", mail.getVertragId());
        entry.updateInt("filetype", mail.getFiletype());
        entry.updateString("empfaenger", mail.getEmpfaenger());
        entry.updateString("absender", mail.getAbsender());
        entry.updateString("cc", mail.getCc());
        entry.updateString("betreff", mail.getBetreff());
        entry.updateString("body", mail.getBody());
        entry.updateString("nohtml", mail.getNohtml());
        entry.updateBoolean("send", mail.isSend());
        entry.updateTimestamp("sendTime", mail.getSendTime());        
        entry.updateTimestamp("created", mail.getCreated());
        entry.updateTimestamp("modified", mail.getModified());
        entry.updateInt("status", mail.getStatus());

        entry.updateRow();
        entry.close();
        statement.close();
        con.close();
        return true;
    }

    /**
     * Java method that deletes a row from the generated sql table
     * @param con (open java.sql.Connection)
     * @param keyId (the primary key to the row)
     * @throws SQLException
     */
    public static void deleteEndgueltigFromEmails(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM emails WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
        con.close();
    }
}
