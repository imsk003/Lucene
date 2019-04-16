package sk.learning.lucene;

import java.io.File;

public class Searching {

	public static void main(String[] args) throws Exception {        
        File indexDir = new File("D:\\lucene\\index");
        String query = "selvakumar";
        int hits = 100;        
        Searcher searcher = new Searcher();
        searcher.searchIndex(indexDir, query, hits);        
    }
}
