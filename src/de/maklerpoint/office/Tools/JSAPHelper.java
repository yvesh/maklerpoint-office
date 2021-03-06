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

package de.maklerpoint.office.Tools;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import de.maklerpoint.office.Logging.Log;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class JSAPHelper {

    private JSAP jsap;
    private JSAPResult registration;

    public JSAPHelper(){
        jsap = new JSAP();
    }

    private void addParameter(Parameter param){
        try {
            jsap.registerParameter(param);
        } catch (JSAPException ex) {
            Log.logger.warn("Error registering parameter", ex);
        }
    }

    //etc.

}
