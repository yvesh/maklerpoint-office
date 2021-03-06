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

package de.maklerpoint.office.Schnittstellen.Word;

import com.javadocx.cTransformDoc;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class DocXConvert {

    /**
     * 
     * @param sourceFile
     * @param zielFile
     */

    public static void convertToHTML(String sourceFile, String zielFile) {
        cTransformDoc objDocument = new cTransformDoc();
        objDocument.setStrFile(sourceFile);
        objDocument.fGenerateXHTML();
        objDocument.fValidatorXHTML();
        String generated = objDocument.toString();
        System.out.println("generated: " + generated);
        System.out.println("get" + objDocument.getStrXHTML());
    }

    /**
     *
     * @param sourceFile
     * @param zielFile
     */

    public static void convertToPDF(String sourceFile, String zielFile) {
        cTransformDoc objDocument = new cTransformDoc();
        objDocument.setStrFile(sourceFile);
        objDocument.fGenerateXHTML();
        objDocument.fGeneratePDF();
        String generated = objDocument.toString();
                System.out.println("generated: " + generated);
        System.out.println("get" + objDocument.getStrXHTML());
    }
    

}
