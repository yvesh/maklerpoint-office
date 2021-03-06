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

package de.maklerpoint.office.Schnittstellen.Txt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportTxtTable {

    private String filename;
    private String title;
    private String footer;
    private String[] titles;
    private Object[][] data;
    private String seperator;

    private static String newline = System.getProperty("line.separator");

    public ExportTxtTable(String filename, String title, String footer,
                    String[] titles, Object[][] data, String seperator) {
        this.filename = filename;
        this.title = title;
        this.footer = footer;
        this.titles = titles;
        this.data = data;
        this.seperator = seperator;
    }


    public void write() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));

        out.write(title);
        out.write(newline);
        out.write(newline);

        for(int i = 0; i < titles.length; i++) {
            out.write(titles[i]);
            out.write(seperator);
        }

        out.write(newline);

        for(int i = 0; i < data.length; i++) {
            for(int j= 0; j < data[i].length; j++) {
                if(data[i][j] == null)
                    data[i][j] = "";

                out.write(data[i][j].toString());
                out.write(seperator);
            }
        }

        out.write(newline);
        out.write(newline);

        out.write(footer);

        out.close();
    }

}
