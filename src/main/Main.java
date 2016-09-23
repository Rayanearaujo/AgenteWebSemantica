package main;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import impl.SemanticCrawlerImpl;

public class Main {
	public static void main(String[] args){
		Model model = ModelFactory.createDefaultModel();
		String resourceUri = "http://liyangyu.com/foaf.rdf"; 
		SemanticCrawlerImpl sci = new SemanticCrawlerImpl();
		sci.search(model, resourceUri);
	}
}
