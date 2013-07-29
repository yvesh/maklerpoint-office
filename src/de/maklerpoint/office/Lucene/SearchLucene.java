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

import de.maklerpoint.office.Filesystem.Filesystem;
import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SearchLucene {

    private static String indexDir = Filesystem.getRootPath() + File.separator + "sonstiges" + File.separator + "index";

    public static void searchTest(String querytxt) throws IOException, ParseException {
        FSDirectory dir = FSDirectory.open(new File(indexDir));
        IndexSearcher isearcher = new IndexSearcher(dir);
        Analyzer anal = new StandardAnalyzer(Version.LUCENE_32);
        QueryParser parser = new QueryParser(Version.LUCENE_32, "contents", anal);
        Query query = parser.parse(querytxt);
        TopDocs hits = isearcher.search(query, 100);

        ScoreDoc[] scoreDocs = hits.scoreDocs;
        
        for (int n = 0; n < scoreDocs.length; ++n) {            
             ScoreDoc sd = scoreDocs[n];
             float score = sd.score;
             int docId = sd.doc;
             Document d = isearcher.doc(docId);
             String fileName = d.get("path"); 
             System.out.println("File: " + fileName);
             System.out.println("Score: " + score);
//             System.out.println("Content: " + d.get("contents"));             
             System.out.println("Filetype: " + d.get("type"));         
        }
        
        isearcher.close();
    }
}
