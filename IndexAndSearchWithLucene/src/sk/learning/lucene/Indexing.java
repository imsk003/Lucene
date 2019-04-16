package sk.learning.lucene;

import java.io.File;

public class Indexing {

	public static void main(String args[]) throws Exception {
		File indexDir = new File("D:\\lucene\\index");
		File dataDir = new File("D:\\lucene\\files");
		
		Indexer indexer = new Indexer();
		indexer.index(indexDir, dataDir);        
	
	}
}
