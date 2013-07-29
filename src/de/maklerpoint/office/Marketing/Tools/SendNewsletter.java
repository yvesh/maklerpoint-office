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

package de.maklerpoint.office.Marketing.Tools;

import de.maklerpoint.office.Marketing.NewsletterObj;
import de.maklerpoint.office.Marketing.NewsletterSubscriberObj;
import de.maklerpoint.office.System.Configuration.Config;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author yves
 */

public class SendNewsletter {

    /**
     * 
     * @param nl
     * @param sub
     * @throws EmailException
     */

    public static void sendNewsletter(NewsletterObj nl, NewsletterSubscriberObj[] sub) throws EmailException {
        
        for(int i = 0; i < sub.length; i++) {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(Config.get("mailHost", null));

            if(Config.getConfigInt("mailPort", 25) != 25)
                email.setSmtpPort(Config.getConfigInt("mailPort", 25));

            if(Config.getConfigBoolean("mailAuth", true))
                email.setAuthentication(Config.get("mailUser", null), Config.get("mailPassword", null));

            email.setFrom(nl.getSenderMail(), nl.getSender());
            email.setSubject(nl.getSubject());
            email.setHtmlMsg(nl.getText());
            email.setTextMsg("Ihr E-Mail Client unterstützt keine HTML Nachrichten.");

            email.addTo(sub[i].getEmail(), sub[i].getName());
            email.send();
        }
    }

    /**
     *
     * @param nl
     * @param temail
     * @throws EmailException
     */

    public static void sendTestNewsletter(NewsletterObj nl, String temail) throws EmailException {
        
        HtmlEmail email = new HtmlEmail();
        email.setHostName(Config.get("mailHost", null));

        if(Config.getConfigInt("mailPort", 25) != 25)
            email.setSmtpPort(Config.getConfigInt("mailPort", 25));

        if(Config.getConfigBoolean("mailAuth", true))
            email.setAuthentication(Config.get("mailUser", null), Config.get("mailPassword", null));

        email.setFrom(nl.getSenderMail(), nl.getSender());
        email.setSubject(nl.getSubject());
        email.setHtmlMsg(nl.getText());
        email.setTextMsg("Ihr E-Mail Client unterstützt keine HTML Nachrichten.");

        email.addTo(temail);
        email.send();        
    }

}
