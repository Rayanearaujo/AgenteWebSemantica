package impl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import crawler.SemanticCrawler;


public class SemanticCrawlerImpl implements SemanticCrawler{
	
	public static void main(String[] args){
		String resourceUri = "http://liyangyu.com/foaf.rdf"; 
		//search(model, resourceUri);
	}
	
	public void search(Model graph, String resourceURI){
		Model model = ModelFactory.createDefaultModel();
		model.read(resourceURI);
		model.write(System.out, "N3"); 
	}
	
}