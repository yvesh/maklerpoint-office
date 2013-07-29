/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Konstanten;

import de.maklerpoint.office.start.CRM;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author yves
 */
public class MPointKonstanten {

    public static final Image icon = createImageIcon("de/acyrance/CRM/Gui/resources/icon-64px-transparent.png", "App").getImage();
    public static final ImageIcon iconImage = createImageIcon("de/acyrance/CRM/Gui/resources/icon-64px-transparent.png", "App");

    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = CRM.class.getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public final static String BUGZILLA_URL = "http://www.maklerpoint.de/bugzilla/xmlrpc.cgi";
    public final static String BUGZILLA_USER = "bugreport@maklerpoint.de";
    public final static String BUGZILLA_PASSWORD = "n4sT3vk0";

    public final static String BUGZILLA_PRODUCT = "MaklerPoint Office";
    public final static String BUGZILLA_COMPONENT = "Hauptprogramm";

    public final static String MP_LOGUPURL = "http://www.maklerpoint.de/appcon/061/uploadlog.php";
    public final static String MP_UPDATEURL = "http://www.maklerpoint.de/appcon/061/update.php";
    
    // Clients
    
    public final static int MP_OFFICE_CLIENT = 0;
    public final static int MP_OFFICE_WEB = 1;
    public final static int MP_OFFICE_CLOUD = 2;
    
    public static String clientVersion(int client){
        switch(client)
        {
            case 0:
                return "MaklerPoint Office";
                
            case 1:
                return "MaklerPoint Office Web";
                
            case 2:
                return "MaklerPoint Office Cloud";                
                
            default:   
                return "Unbekannt!";
                
        }
    }
    
}
