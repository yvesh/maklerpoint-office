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

package de.maklerpoint.office.Database;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DatabaseHelper extends Thread {

    private String _ConnectionURL;
    private String _user;
    private String _password;

    public DatabaseHelper(){
        super();
        _ConnectionURL="";
        _user="";
        _password="";
    }

    public DatabaseHelper(String ConnectionURL, String Username, String Password){
        super();
        _ConnectionURL=ConnectionURL;
        _user=Username;
        _password=Password;
    }

    public void setConnectionURL(String value){_ConnectionURL = value;}
    public void setUsername(String value){_user = value;}
    public void setPassword(String value){_password = value;}

    public String getConnectionURL(){return _ConnectionURL;}
    public String getUsername(){return _user;}
    public String getPassword(){return _password;}
}
