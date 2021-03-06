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

package de.maklerpoint.office.Schnittstellen.Outlook;

import de.maklerpoint.office.Tools.URIEncoder;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SimpleMailExport {

    private Desktop desktop = Desktop.getDesktop();

    private void mail(String address, String cc, String betreff, String bodytext)
                                          throws URISyntaxException, IOException {


        String mailtext = null;

        if(cc != null && cc.length() > 0)
            mailtext = "mailto:" + address + "?cc=" + cc + "&subject=" + betreff + "&body=" + bodytext;
        else
            mailtext = "mailto:" + address + "&subject=" + betreff + "&body=" + bodytext;

        mailtext = URIEncoder.encode(mailtext);

        URI mail =  new URI(mailtext);

        desktop.mail(mail);
    }


}
