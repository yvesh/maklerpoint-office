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
import java.io.File;
import java.net.URL;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class MultiEmailSender {

    private String[] adress = null;
    private String subject = null;
    private String body = null;
    private String nohtmlmsg = null;
    
    private String[] cc = null;
    private String bcc = null;

    private File[] files;
    private URL[] urls;
    private EmailAttachment[] attachments = null;

    public MultiEmailSender(String[] adress, String subject, String[] cc, String body, String nohtmlmsg) {
        this.adress = adress;
        this.subject = subject;
        this.cc = cc;
        this.body = body;
        this.nohtmlmsg = nohtmlmsg;
        this.files = null;
        this.attachments = null;
        this.urls = null;
    }

    public void send() throws EmailException {

        if(files != null && urls != null) {
            attachments = new EmailAttachment[files.length + urls.length];
            
            int cnt = 0;
            
            for(int i = 0; i < files.length; i++) {
                attachments[cnt] = new EmailAttachment();
                attachments[cnt].setPath(files[i].getPath());
                attachments[cnt].setName(files[i].getName());
                attachments[cnt].setDisposition(EmailAttachment.ATTACHMENT);
                
                cnt ++;
            }
            
            for(int i = 0; i < urls.length; i++)
            {
                attachments[cnt] = new EmailAttachment();
                attachments[cnt].setURL(urls[i]);
                attachments[cnt].setName(urls[i].getFile());
                attachments[cnt].setDisposition(EmailAttachment.ATTACHMENT);
                cnt ++;
            }
            
        } else if (files != null) {
            attachments = new EmailAttachment[files.length];
            
            for(int i = 0; i < files.length; i++) {
                attachments[i] = new EmailAttachment();
                attachments[i].setPath(files[i].getPath());
                attachments[i].setName(files[i].getName());
                attachments[i].setDisposition(EmailAttachment.ATTACHMENT);
            }
        } else if (urls != null) {
            attachments = new EmailAttachment[urls.length];
            
            for(int i = 0; i < urls.length; i++)
            {
                attachments[i] = new EmailAttachment();
                attachments[i].setURL(urls[i]);
                attachments[i].setName(urls[i].getFile());
                attachments[i].setDisposition(EmailAttachment.ATTACHMENT);
            }
        }
        
        HtmlEmail email = new HtmlEmail();
        email.setHostName(Config.get("mailHost", ""));
        email.setSmtpPort(Config.getConfigInt("mailPort", 25));

        email.setTLS(Config.getConfigBoolean("mailTLS", false));
        email.setSSL(Config.getConfigBoolean("mailSSL", false));        

        //email.setSslSmtpPort(Config.getConfigInt("emailPort", 25));
        email.setAuthenticator(new DefaultAuthenticator(Config.get("mailUsername", ""),
                Config.get("mailPassword", "")));

        email.setFrom(Config.get("mailSendermail", "info@example.de"), 
                Config.get("mailSender", null));

        email.setSubject(subject);
        email.setHtmlMsg(body);
        email.setTextMsg(nohtmlmsg);
        
        for(int i = 0; i < adress.length; i++ ) {
            email.addTo(adress[i]);
        }
        
        if(cc != null) {
            for(int i = 0; i < cc.length; i++) {
                email.addCc(cc[i]);
            }
        }
        
                
        if(attachments != null) {
            for (int i = 0; i < attachments.length; i++) {
                email.attach(attachments[i]);
            }
        }
        
        email.send();

    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public URL[] getUrls() {
        return urls;
    }

    public void setUrls(URL[] urls) {
        this.urls = urls;
    }

    public String[] getAdress() {
        return adress;
    }

    public void setAdress(String[] adress) {
        this.adress = adress;
    }

    public EmailAttachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(EmailAttachment[] attachments) {
        this.attachments = attachments;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getNohtmlmsg() {
        return nohtmlmsg;
    }

    public void setNohtmlmsg(String nohtmlmsg) {
        this.nohtmlmsg = nohtmlmsg;
    }
    
    
}
