package crawler;

import org.apache.jena.rdf.model.Model;

public interface SemanticCrawler {
	public Model search(Model graph, String resourceURI);

}
