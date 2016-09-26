package main;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import impl.SemanticCrawlerImpl;

public class Main {
	public static void main(String[] args){
		Model model = ModelFactory.createDefaultModel();
		Model answer = ModelFactory.createDefaultModel();
		String resourceUri = "http://dbpedia.org/resource/Girls_(Brazilian_band)"; 
		SemanticCrawlerImpl sci = new SemanticCrawlerImpl();
		answer = sci.search(model, resourceUri);
		answer.write(System.out);
	}
}