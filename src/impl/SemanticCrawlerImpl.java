package impl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import crawler.SemanticCrawler;


public class SemanticCrawlerImpl implements SemanticCrawler{
	
	public void search(Model graph, String resourceURI){
		
		graph.read(resourceURI);
		graph.write(System.out, "N3"); 
	}
	
}