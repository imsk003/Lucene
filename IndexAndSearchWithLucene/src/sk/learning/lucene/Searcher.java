package sk.learning.lucene;

import java.io.File;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

	void searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {        

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDir.toURI())));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("contents", analyzer);
		
        Query query = parser.parse(queryStr);        
        TopDocs topDocs = searcher.search(query, maxHits);       
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("filename"));
        }        
        System.out.println("Found " + hits.length);        

    }
}
