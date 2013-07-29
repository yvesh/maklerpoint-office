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
package de.maklerpoint.office.Schnittstellen.Email;

import de.maklerpoint.office.System.Configuration.Config;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SimpleEmailSender {
    
    /**
     * 
     * @param adress
     * @param Subject
     * @param body
     * @throws EmailException 
     */
    
    public static void sendSimpleEMail(String adress, String Subject, String body) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(Config.get("mailHost", ""));
        email.setSmtpPort(Config.getConfigInt("emailPort", 25));
        
        email.setTLS(Config.getConfigBoolean("mailTLS", false));        
        email.setSSL(Config.getConfigBoolean("mailSSL", false));
        
        //email.setSslSmtpPort(Config.getConfigInt("emailPort", 25));
        email.setAuthenticator(new DefaultAuthenticator(Config.get("mailUsername", ""), 
                Config.get("mailPassword", "")));
        
        email.setFrom(Config.get("mailSendermail", ""), Config.get("mailSender", ""));
        
        email.setSubject(Subject);
        email.setMsg(body);
        email.addTo(adress);
        
        email.send();
    }
    
}
