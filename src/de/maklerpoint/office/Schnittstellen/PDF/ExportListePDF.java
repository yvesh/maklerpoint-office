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

package de.maklerpoint.office.Schnittstellen.PDF;

//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class ExportListePDF {

    private String title;
    private String footer;

    private String filename;
    private String[] titles;
    private Object[][] data;


    public ExportListePDF(String filename, String[] titles, Object[][] data,
                            String doctitle, String footer) {
        this.title = doctitle;
        this.footer = footer;
        this.filename = filename;
        this.titles = titles;
        this.data = data;
    }

    public void write() throws DocumentException, FileNotFoundException {

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Document doc = null;

        if(titles.length > 7)
            doc = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
        else
            doc = new Document(PageSize.A4, 20, 20, 20, 20);
        
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename));
        doc.addAuthor("MaklerPoint - www.maklerpoint.de");
        doc.addCreator("MaklerPoint - www.maklerpoint.de");
        doc.addCreationDate();
        doc.addTitle(title);

        doc.open();
              
        doc.add(new Paragraph(title, FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, Color.BLACK)));

        Table t = new Table(titles.length, data.length + 1);
        t.setPadding(3);
        t.setSpacing(0);
        t.setBorderWidth(1);

        for(int i = 0; i < titles.length; i++) {
            Cell c1 = new Cell(titles[i]);
            c1.setHeader(true);
            t.addCell(c1);
        }
        t.endHeaders();

        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                Cell c1 = null;
                if(data[i][j] != null)
                    c1 = new Cell(data[i][j].toString());
                else
                    c1 = new Cell("");
                t.addCell(c1);
            }            
        }

        doc.add(t);

        if(footer == null) {
            doc.add(new Paragraph(("Export " + title + " - Genereriert am " + df.format(new Date(System.currentTimeMillis()))) + " von MaklerPoint",
                    FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, Color.black)));
        } else {
            doc.add(new Paragraph(footer,
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, Color.black)));
        }

        doc.close();
    }

}
