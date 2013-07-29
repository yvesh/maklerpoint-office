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

package de.maklerpoint.office.Schnittstellen.Excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportExcelXLS {

    private static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");

    private String sheetName;
    private String footName;
    private String filename;
    private String[] titles;
    private Object[][] data;

    /**
     * 
     * @param sheetname
     * @param footname
     * @param filename
     * @param titles
     * @param data
     */

    public ExportExcelXLS(String sheetname, String footname, String filename,
                                        String[] titles, Object[][] data) {
        super();
        this.footName = footname;
        this.sheetName = sheetname;
        this.filename = filename;
        this.titles = titles;
        this.data = data;        
    }

    /**
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */

    public void write() throws FileNotFoundException, IOException {
        FileOutputStream out = new FileOutputStream(new File(filename));
        HSSFWorkbook wb;

        wb = new HSSFWorkbook();
       
        Map<String, HSSFCellStyle> styles = createStyles(wb);
        HSSFSheet sheet = wb.createSheet(sheetName);

         //turn off gridlines
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);

        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);

        int[][] width = new int[titles.length][titles.length];

        for (int i = 0; i < titles.length; i++)
        {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(styles.get("header"));

            width[i][0] = titles[i].length();
        }

        HSSFRow row;
        HSSFCell cell;
        int rownum = 1;

        for (int i = 0; i < data.length; i++, rownum++) {
            row = sheet.createRow(rownum);
            if(data[i] == null) continue;

            for(int j = 0; j < data[i].length; j++) {
                cell = row.createCell(j);
                if(data[i][j] == null)
                    data[i][j] = "";

                cell.setCellValue(data[i][j].toString());

                if(data[i][j].toString().length() > width[j][0])
                    width[j][0] = data[i][j].toString().length();
            }
        }

        for(int i = 0; i < titles.length; i++) {
            int widthShort = (256 * (width[i][0] + 3));

            sheet.setColumnWidth(i, widthShort);
        }

        int position = (titles.length / 2) - 1;

        row = sheet.createRow(rownum + 3);
        cell = row.createCell(position);
        
        if(footName == null) {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            cell.setCellValue("Export MaklerPoint vom " + df.format(new Date(System.currentTimeMillis())) + " - www.maklerpoint.de");
        } else {
            cell.setCellValue(footName);
        }

        sheet.setZoom(3, 4);

        wb.write(out);
        out.close();       
    }


    /**
     * create a library of cell styles
     */
    private static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb){
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();
        HSSFDataFormat df = wb.createDataFormat();

        HSSFCellStyle style;
        HSSFFont headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("dd.MM.yyyy"));
        styles.put("header_date", style);

        HSSFFont font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("dd.MM.yyyy"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("dd.MM.yyyy"));
        styles.put("cell_g", style);

        HSSFFont font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("dd.MM.yyyy"));
        styles.put("cell_bg", style);

        HSSFFont font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("dd.MM.yyyy"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        return styles;
    }

    private static HSSFCellStyle createBorderedStyle(HSSFWorkbook wb){
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

}
