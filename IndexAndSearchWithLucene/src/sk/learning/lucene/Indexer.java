package sk.learning.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class Indexer {

    void index(File indexDir, File dataDir) throws Exception {        

    	Directory dir = FSDirectory.open(Paths.get(indexDir.toURI()));
  	   Analyzer analyzer = new StandardAnalyzer();
  	   IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
  	   IndexWriter writer = new IndexWriter(dir, iwc);    
  	   indexDirectory(writer, dataDir);       
        writer.close();            
    }

    private void indexDirectory(IndexWriter indexWriter, File dataDir) throws IOException, TikaException {
        File[] files = dataDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexWriter, f);
            }
            else {
                indexFileWithIndexWriter(indexWriter, Path.of(f.toURI()));
            }
        }
    }
    
    private void indexFileWithIndexWriter(IndexWriter indexWriter, Path f) throws IOException, TikaException {
        System.out.println("Indexing file:... " + f.getFileName());  
//        InputStream stream = Files.newInputStream(f);
        Tika tika = new Tika();
        Document doc = new Document();
        doc.add(new StringField("path", f.toString(), Field.Store.YES));
        doc.add(new TextField("contents",tika.parseToString(f), Field.Store.YES));  //new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
        doc.add(new TextField("filename",f.getFileName().toString(), Field.Store.YES));
        indexWriter.updateDocument(new Term("path", f.toString()), doc);
//        indexWriter.addDocument(doc);
    }
    
//    public String parseToPlainText(Path f) throws IOException, SAXException, TikaException {
//        BodyContentHandler handler = new BodyContentHandler(-1);
//       
//        AutoDetectParser parser = new AutoDetectParser();
//        Metadata metadata = new Metadata();
//        try (InputStream stream = new FileInputStream(f.toString())) {
//            parser.parse(stream, handler, metadata);
//            return handler.toString();
//        }
//    }
}