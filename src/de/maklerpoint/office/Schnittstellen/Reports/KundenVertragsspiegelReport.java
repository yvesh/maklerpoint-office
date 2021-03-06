/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package de.maklerpoint.office.Schnittstellen.Reports;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Filesystem.Filesystem;
import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */

public class KundenVertragsspiegelReport {

    public static final int HTML = 0;
    public static final int PDF = 1;
    public static final int XML = 2;
    public static final int XLS = 3;
    
    private int type = -1;
    private String zielfile;

    public KundenVertragsspiegelReport(int type, String zielfile) {
        this.type = type;
        this.zielfile = zielfile;

    }

    public void write() throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;

        jasperReport = JasperCompileManager.compileReport(Filesystem.getTemplatePath() + File.separator + "reports"
                + File.separator + "Vertragsspiegel.jrxml");
        jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), DatabaseConnection.open());

        if (type == HTML) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, zielfile);
        } else if (type == PDF) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, zielfile);
        } else if (type == XML) {
            JasperExportManager.exportReportToXmlFile(jasperPrint, zielfile, false);
        } else if (type == XLS) {            
            JRXlsExporter exporterXLS = new JRXlsExporter(); 
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, zielfile); 
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE); 
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
            exporterXLS.exportReport(); 
        }
    }
}
