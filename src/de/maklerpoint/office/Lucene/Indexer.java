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
package de.maklerpoint.office.Lucene;

import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.DatabaseConfig;
import de.maklerpoint.office.Tools.FormatFileSize;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LimitTokenCountAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class Indexer extends Thread {

    private IndexWriter writer;
    private IndexReader reader; // TODO Implement updater, atm n
    private ArrayList<File> queue = new ArrayList<File>();
    private String indexDir = Filesystem.getRootPath() + File.separator + "sonstiges" + File.separator + "index";
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Indexer() {
        super();
    }

    @Override
    public void run() {
        try {
            Analyzer an = new StandardAnalyzer(Version.LUCENE_32);
            FSDirectory dir = FSDirectory.open(new File(indexDir));


            IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_32, an);
            LimitTokenCountAnalyzer lt = new LimitTokenCountAnalyzer(an, Integer.MAX_VALUE);
            conf.setOpenMode(OpenMode.CREATE); // Immer neuer INdex

            writer = new IndexWriter(dir, conf);

            int originalNumDocs = writer.numDocs();
            Log.logger.info("Anzahl indexierte Dateien: " + originalNumDocs);

//            indexFileorDir(Filesystem.getRootPath());
            indexDatabase();

            int newNumDocs = writer.numDocs();
            Log.logger.info((newNumDocs - originalNumDocs) + " neue Dokumente indexiert.");

            writer.close();
        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (CorruptIndexException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    private void indexFileorDir(String fileName) throws IOException {
        listFiles(new File(fileName));

        for (File f : queue) {
            FileReader fr = null;
            try {
                if (f.getName().startsWith(".")) {
//                    System.out.println("Versteckte datei: " + f.getName());
                    // TODO add html, xml parsers
                } else if (f.getName().endsWith(".htm") || f.getName().endsWith(".html")
                        || f.getName().endsWith(".xml") || f.getName().endsWith(".txt")) {
                    Document doc = new Document();

                    //===================================================
                    // add contents of file
                    //===================================================
                    fr = new FileReader(f);
                    doc.add(new Field("contents", fr));

                    //===================================================
                    //adding second field which contains the path of the file
                    //===================================================
                    doc.add(new Field("path", f.getPath(),
                            Field.Store.YES,
                            Field.Index.ANALYZED));
                    /**
                     * Adding Typ
                     */
                    doc.add(new Field("type", String.valueOf(FileTypes.TXT),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("modified", df.format(f.lastModified()),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("filesize", String.valueOf(FormatFileSize.formatSize(f.length(),
                            FormatFileSize.KB)), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(doc);
                } else if (f.getName().endsWith(".pdf")) {
                    PDFParser parser = new PDFParser(new FileInputStream(f));
                    parser.parse();
                    COSDocument cd = parser.getDocument();
                    PDFTextStripper stripper = new PDFTextStripper();

                    String text = stripper.getText(new PDDocument(cd));

                    Document doc = new Document();

                    doc.add(new Field("contents", text, Field.Store.YES, Field.Index.ANALYZED));
                    doc.add(new Field("path", f.getPath(),
                            Field.Store.YES,
                            Field.Index.ANALYZED));
                    doc.add(new Field("type", String.valueOf(FileTypes.PDF),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("modified", df.format(f.lastModified()),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("filesize", String.valueOf(FormatFileSize.formatSize(f.length(),
                            FormatFileSize.KB)), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(doc);
                    cd.close();
                } else if (f.getName().endsWith(".doc") || f.getName().endsWith(".docx")) {


                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(f));
                    WordExtractor extractor = new WordExtractor(fs);
                    String wordText = extractor.getText();

                    Document doc = new Document();
                    doc.add(new Field("contents", wordText, Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("path", f.getPath(),
                            Field.Store.YES,
                            Field.Index.ANALYZED));
                    doc.add(new Field("type", String.valueOf(FileTypes.DOC),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("modified", df.format(f.lastModified()),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("filesize", String.valueOf(FormatFileSize.formatSize(f.length(),
                            FormatFileSize.KB)), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(doc);
                } else if (f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(f));
                    ExcelExtractor extractor = new ExcelExtractor(fs);
                    String excelText = extractor.getText();

                    Document doc = new Document();
                    doc.add(new Field("contents", excelText, Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("path", f.getPath(),
                            Field.Store.YES,
                            Field.Index.ANALYZED));
                    doc.add(new Field("type", String.valueOf(FileTypes.XLS),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("modified", df.format(f.lastModified()),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("filesize", String.valueOf(FormatFileSize.formatSize(f.length(),
                            FormatFileSize.KB)), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(doc);
                } else if (f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")) {
                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(f));
                    PowerPointExtractor extractor = new PowerPointExtractor(fs);
                    String ppttext = extractor.getText();

                    Document doc = new Document();
                    doc.add(new Field("contents", ppttext, Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("path", f.getPath(),
                            Field.Store.YES,
                            Field.Index.ANALYZED));

                    doc.add(new Field("modified", df.format(f.lastModified()),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("type", String.valueOf(FileTypes.PPT),
                            Field.Store.YES, Field.Index.NOT_ANALYZED));

                    doc.add(new Field("filesize", String.valueOf(FormatFileSize.formatSize(f.length(),
                            FormatFileSize.KB)), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(doc);
                }

                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Lucene | Neue Datei indexiert: " + f);
                }
            } catch (Exception e) {
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Datei konnte nicht indexiert werden: " + f, e);
                }
                continue;
            } finally {
//                fr.close();
            }
        }

        writer.optimize();
        queue.clear();

    }

    private void indexDatabase() throws SQLException, CorruptIndexException, IOException {
        for (int i = 0; i < DatabaseConfig.TABLES_INDEX.length; i++) {
            Connection con = DatabaseConnection.open();
            String sql = "SELECT * FROM " + DatabaseConfig.TABLES_INDEX[i];
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int columnCount = rsMetaData.getColumnCount();
            while (rs.next()) {
                int id = rs.getInt("id");
                String modified = df.format(rs.getTimestamp("modified"));

                for (int j = 1; j <= columnCount; j++) {
                    Document d = new Document();
                    d.add(new Field("dbid", String.valueOf(id), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    d.add(new Field("path", rsMetaData.getColumnName(j), Field.Store.YES, Field.Index.NOT_ANALYZED));

                    if (rs.getString(j) != null) {
                        d.add(new Field("contents", rs.getString(j), Field.Store.YES, Field.Index.ANALYZED));
                    }

                    d.add(new Field("type", String.valueOf(FileTypes.DATABASE_TABLE), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    d.add(new Field("table", DatabaseConfig.TABLES_INDEX[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
                    d.add(new Field("modified", modified, Field.Store.YES, Field.Index.NOT_ANALYZED));

//                    System.out.println("table: " + DatabaseConfig.TABLES_INDEX[i]);
//                    System.out.println("column: " + rsMetaData.getColumnName(j));
//                    System.out.println("content: " + rs.getString(j));
                    writer.addDocument(d);
                }
            }
            stmt.close();
            con.close();
        }

        writer.optimize();
    }

    private void listFiles(File file) {
        if (!file.exists()) {
            System.out.println(file + " does not exist.");
        }
        if (!file.getName().startsWith(".")) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    listFiles(f);
                }
            } else {
                queue.add(file);
            }
        }
    }

    /**
     * Close the index.
     * @throws java.io.IOException
     */
    public void closeIndex() throws IOException {
        writer.optimize();
        writer.close();
    }
}
