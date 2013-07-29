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

import java.io.IOException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class ExportSimplePDF {

    private String filename;
    private String contents;

    public ExportSimplePDF(String filename, String contents) {
        this.filename = filename;
        this.contents = contents;
    }

    public void write() throws IOException, COSVisitorException {
        PDDocument doc = null;
        try
        {
            doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage( page );
            PDFont font = PDType1Font.HELVETICA;

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.beginText();
            contentStream.setFont( font, 12 );
            contentStream.moveTextPositionByAmount( 100, 700 );
            contentStream.drawString( contents );

            contentStream.endText();
            contentStream.close();
            

            doc.save( filename );
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }

    }

}
