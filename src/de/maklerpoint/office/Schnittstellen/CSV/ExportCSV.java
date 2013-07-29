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

package de.maklerpoint.office.Schnittstellen.CSV;

import de.maklerpoint.office.Logging.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportCSV {

    private String[] header;
    private Object[][] data;
    private String filename;


    public ExportCSV(String[] header, Object[][] data, String filen) {
        super();
        this.header = header;
        this.filename = filen;
        this.data = data;
    }

    public void write() throws IOException {
        // Todo add Options
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(new File(filename)), CsvPreference.EXCEL_PREFERENCE);

        try {
            Log.logger.debug("Starte CVS Export");
            writer.writeHeader(header);
            Log.logger.debug("Header Länge: " + header.length);
            Log.logger.debug("Data Länge: " + data.length);
            for(int i = 0; i < data.length; i++) {
                final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();

                Object[] dat1 = data[i];
                Log.logger.debug("Dat1 Länge: " + dat1.length);
                for(int j = 0; j < dat1.length; j++) {
                    if(dat1[j] == null)
                        dat1[j] = "";

                    data1.put(header[j], dat1[j]);
                    
                    Log.logger.debug("Header: " + header[j] + " | Data: " + dat1[j]);
                }
                Log.logger.debug("data1 " + data1.toString() + " | head:" + header.toString());
                writer.write(data1, header);
            }
        } finally {
            writer.close();
        }

    }


}
