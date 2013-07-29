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

import de.maklerpoint.office.Kalender.Termine.TerminObj;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ExportKalenderExcel {

    private static final String[] days = {
            "Sonntag", "Montag", "Dienstag",
            "Mittwoch", "Donnerstag", "Freitag", "Samstag"};

     private static final String[]  months = {
            "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August",
            "September", "Oktober", "November", "Dezember"};


     private String filename;
     private boolean xlsx = false;
     private TerminObj[] termine;

     /**
      * 
      * @param filename
      * @param termine
      */

     public ExportKalenderExcel(String filename, TerminObj[] termine) {
         this.filename = filename;
         this.termine = termine;
     }

     /**
      * 
      * @throws FileNotFoundException
      * @throws IOException
      */

     public void write() throws FileNotFoundException, IOException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        HSSFWorkbook wb = new HSSFWorkbook();
        Map<String, HSSFCellStyle> styles = createStyles(wb);

        for (int month = 0; month < 12; month++) {
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //create a sheet for each month
            HSSFSheet sheet = wb.createSheet(months[month]);

            //turn off gridlines
            sheet.setDisplayGridlines(false);
            sheet.setPrintGridlines(false);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            HSSFPrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            //the following three statements are required only for HSSF
            sheet.setAutobreaks(true);
            printSetup.setFitHeight((short)1);
            printSetup.setFitWidth((short)1);

            //the header row: centered text in 48pt font
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(80);
            HSSFCell titleCell = headerRow.createCell(0);
            titleCell.setCellValue(months[month] + " " + year);
            titleCell.setCellStyle(styles.get("title"));
//                sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$N$1"));

            //header with month titles
            HSSFRow monthRow = sheet.createRow(1);
            for (int i = 0; i < days.length; i++) {
                //set column widths, the width is measured in units of 1/256th of a character width
                sheet.setColumnWidth( (i * 2), (5*256)); //the column is 5 characters wide
                sheet.setColumnWidth((i * 2 + 1), (13*256)); //the column is 13 characters wide
                //sheet.addMergedRegion(new Region(1, (short) 1, i*2, (short) (i * 2 + 1)));
                sheet.addMergedRegion(new CellRangeAddress(1, i * 2, 1, (i * 2 + 1))); // TODO Test
                HSSFCell monthCell = monthRow.createCell((i * 2));
                monthCell.setCellValue(days[i]);
                monthCell.setCellStyle(styles.get("month"));
            }

            int cnt = 1, day=1;
            int rownum = 2;
            for (int j = 0; j < 6; j++) {
                HSSFRow row = sheet.createRow(rownum++);
                row.setHeightInPoints(100);
                for (int i = 0; i < days.length; i++) {
                    HSSFCell dayCell_1 = row.createCell((i * 2));
                    HSSFCell dayCell_2 = row.createCell((i * 2 + 1));

                    int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
                    if(cnt >= day_of_week && calendar.get(Calendar.MONTH) == month) {
                        dayCell_1.setCellValue(day);
                        calendar.set(Calendar.DAY_OF_MONTH, ++day);

                        if(i == 0 || i == days.length-1) {
                            dayCell_1.setCellStyle(styles.get("weekend_left"));
                            dayCell_2.setCellStyle(styles.get("weekend_right"));
                        } else {
                            dayCell_1.setCellStyle(styles.get("workday_left"));
                            dayCell_2.setCellStyle(styles.get("workday_right"));
                        }
                    } else {
                        dayCell_1.setCellStyle(styles.get("grey_left"));
                        dayCell_2.setCellStyle(styles.get("grey_right"));
                    }
                    cnt++;
                }
                if(calendar.get(Calendar.MONTH) > month) break;
            }
        }

        // Write the output to a file        
        
        FileOutputStream out = new FileOutputStream(this.filename);
        wb.write(out);
        out.close();             
     }


      private static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb){
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();

        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

        HSSFCellStyle style;
        HSSFFont titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)48);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        HSSFFont monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)12);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        monthFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        styles.put("month", style);

        HSSFFont dayFont = wb.createFont();
        dayFont.setFontHeightInPoints((short)14);
        dayFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("weekend_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("weekend_right", style);

        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("workday_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("workday_right", style);

        style = wb.createCellStyle();
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_left", style);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_right", style);

        return styles;
    }
}
