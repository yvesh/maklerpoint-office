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

package de.maklerpoint.office.Mime;

import eu.medsea.mimeutil.MimeType;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Helper Methods for mime
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */
public class MimeHelper extends Thread{

    @SuppressWarnings("static-access")
    public static void addMimeType(MimeType mimetype){
        InitializeMime.mimeUtil.addKnownMimeType(mimetype);
    }

    @SuppressWarnings("static-access")
    public static void addMimeType(String mimetype){
        InitializeMime.mimeUtil.addKnownMimeType(mimetype);
    }

    public static Object[] getMimeTypes(File file){
        return InitializeMime.mimeUtil.getMimeTypes(file).toArray();
    }

    public static Object[] getMimeTypes(byte[] data){
        return InitializeMime.mimeUtil.getMimeTypes(data).toArray();
    }

    public static Object[] getMimeTypes(URL url){
        return InitializeMime.mimeUtil.getMimeTypes(url).toArray();
    }
    
    public static Object[] getMimeTypes(InputStream in){
        return InitializeMime.mimeUtil.getMimeTypes(in).toArray();
    }

    public static Object[] getMimeTypes(String filename){
        return InitializeMime.mimeUtil.getMimeTypes(filename).toArray();
    }

    




}
