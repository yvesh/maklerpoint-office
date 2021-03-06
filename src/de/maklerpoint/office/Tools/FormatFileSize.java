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

import java.text.DecimalFormat;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class FormatFileSize {

    public static final int BYTE = 0;
    public static final int KB = 1;
    public static final int MB = 2;
    public static final int GB = 3;

    /**
     * Returns a formated (#.##) size of an given bytesize with the
     * given format use the static final vars in FormatFileSize
     *
     * For example:
     * formatSize(10000, FormatFileSize.KB) will return 9.76 (KiloByte)
     *
     * @param bytesize
     * @param format
     * @return formated size
     */

    public static double formatSize(long bytesize, int format){

        DecimalFormat fmt = new DecimalFormat("#.##");

        switch(format){
            case 1:
                return Double.valueOf(fmt.format(bytesize / 1024));

            case 2:
                return Double.valueOf(fmt.format(bytesize / (1024*1024)));

            case 3:
                return Double.valueOf(fmt.format(bytesize / (1024*1024*1024)));

            default:
            case 0:
                return Double.valueOf(bytesize);

        }
    }

}
