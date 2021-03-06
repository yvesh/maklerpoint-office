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
import eu.medsea.mimeutil.MimeUtil2;

import java.util.Collection;

/**
 * Methods to initalize Mime utility
 * @author Yves Hoppe <info at yves-hoppe.de>
 * @author Karpouzas George <www.webnetsoft.gr>
 */

public class InitializeMime extends Thread {

    private static Collection UNKNOWN_MIME_TYPE_COLLECTION;

    private static MimeType UNKNOWN_MIME_TYPE = new MimeType("application/octet-stream");

    public static MimeUtil2 mimeUtil;

    static {
//        UNKNOWN_MIME_TYPE_COLLECTION.add(UNKNOWN_MIME_TYPE);
    }

    /**
     * Sets up MimeUtil
     */

    public static void setUp(){
        mimeUtil = new MimeUtil2();
        mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
//	mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
    }

    /**
     * Unregisters mimeUtil
     */

    public static void tearDown(){
        mimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        mimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
//        mimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
    }

}
