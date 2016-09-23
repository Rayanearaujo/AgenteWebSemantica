package impl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;

import crawler.SemanticCrawler;


public class SemanticCrawlerImpl implements SemanticCrawler{
	
	public void search(Model graph, String resourceURI){
		
		graph.read(resourceURI);
		//graph.write(System.out, "N3");
		
		//Show all instances that have a owl:samAss property
		System.out.println("\nfollowing instances have owl:samAss property:");
		StmtIterator statements = graph.listStatements((Resource)null,OWL.sameAs,(RDFNode)null);
		while (statements.hasNext()){
			Statement statement = statements.nextStatement();
			Resource subject = statement.getSubject();

			if(subject.isAnon())
				System.out.print( "(" + subject.getId() + ")" );
			else
				System.out.print( "(" + subject.getURI() + ")" );

			System.out.print(" OWL.sameAs ");
			Resource object = (Resource) (statement.getObject());

			if(object.isAnon())
				System.out.print( "(" + object + ")" );
			else if (object.isLiteral())
				System.out.print( "(" + object.toString() + ")" );
			else if (object.isResource())
				System.out.print( "(" + object.getURI() + ")" );

			System.out.println();
		
		}
	
	}	
}