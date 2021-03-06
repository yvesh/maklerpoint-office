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

package de.maklerpoint.office.Bugzilla;

import de.maklerpoint.office.Tools.Base64Encode;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

/**
 *
 * @author yves
 */

public class BugzillaReport {

    private String serverURL;
    private String login;
    private String password;

    private String product;
    private String component;

    private XmlRpcClient rpcClient;

    private int id = -1;
    private int bugid = -1;

    /**
     * 
     * @param serverURL
     * @param login
     * @param password
     * @param product
     * @param component
     */

    public BugzillaReport(String serverURL, String login, String password,
                String product, String component) {
        this.serverURL = serverURL;
        this.login = login;
        this.password = password;
        this.product = product;
        this.component = component;
    }

    public Map login()  {
        try {            
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(serverURL));

            HttpClient httpClient = new HttpClient();
            rpcClient = new XmlRpcClient();
            XmlRpcCommonsTransportFactory factory = new XmlRpcCommonsTransportFactory(rpcClient);
            factory.setHttpClient(httpClient);
            rpcClient.setTransportFactory(factory);

            rpcClient.setConfig(config);

//            ArrayList<Object> params = new ArrayList<Object>();
           
            Map map = new HashMap();
            map.put("login",login);
            map.put("password",password);
            map.put("rememberlogin", "Bugzilla_remember");

//            executionData.put("password", password);
//            executionData.put("remember", true);
//            params.add(executionData);

            Map result = (Map) rpcClient.execute("User.login",  new Object[]{map});

            Logger.getLogger(getClass()).debug("Bugreport Datenbank id: " +result);

            for(Object name:result.keySet()) {
//                System.out.println("Name: " + name);
//                System.out.println("Nummer: " + result.get(name));
                id = Integer.valueOf(result.get(name).toString());
            }

            return result;
        } catch (Exception e) {
            Logger.getLogger(getClass()).fatal("Fehler beim senden des Bugreports", e);
        }
        return null;
    }

    public int createBugreport(String summary, String version, String desc, String op_sys,
                                        String platform, String severity) { //, String priority, String severity) {
        Map map = new HashMap();
        map.put("product", product);
        map.put("component", component);
        map.put("summary", summary);
        map.put("version", version);
        map.put("description", desc);
        map.put("op_sys", op_sys);
        map.put("platform", platform);
//        map.put("priority", priority);
//        map.put("severity", severity);

        try {
            Map result = (Map) rpcClient.execute("Bug.create",  new Object[]{map});
            System.out.println("result: " +result);

            for(Object name:result.keySet()) {
//                System.out.println("Name: " + name);
//                System.out.println("Nummer: " + result.get(name));
                bugid = Integer.valueOf(result.get(name).toString());
            }

            return bugid;
        } catch (Exception e) {
            Logger.getLogger(getClass()).fatal("Fehler beim senden des Bugreports", e);
        } 
        
        return bugid;
        //int bugid =
    }

    public void attachToBugreport(String data, String filename, String summary, 
            String content_type, String comment, boolean is_private) throws IOException{
        Map map = new HashMap();
        map.put("ids", String.valueOf(bugid));
        map.put("data", Base64Encode.encodeString(data));
        map.put("file_name", filename);
        map.put("summary", summary);
        map.put("content_type", content_type);
        map.put("comment", comment);
        map.put("is_private", is_private);

        try {
            Map result = (Map) rpcClient.execute("Bug.add_attachment",  new Object[]{map});
            Logger.getLogger(getClass()).debug("Bug Attachment id: " +result);
        } catch (Exception e) {
            Logger.getLogger(getClass()).fatal("Fehler beim senden des Bugreports Attachments", e);
        }

    }

}
