package impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.atlas.web.HttpException;

import crawler.SemanticCrawler;


public class SemanticCrawlerImpl implements SemanticCrawler{
	static ArrayList<String> visitedURIs = new ArrayList<String>();
	
	public void search(Model graph, String resourceURI){
		
		//HttpException he = new HttpException(resourceURI);
		//System.out.println("***** Http exception: " + he.getResponse());
		
		try{		
			graph.read(resourceURI);
			
			if(!visitedURIs.contains(resourceURI))
				visitedURIs.add(resourceURI);
			
			if(graph.contains(graph.createResource(resourceURI),OWL.sameAs)){
				if(graph.contains(graph.createResource(resourceURI),OWL.sameAs,graph.createResource(resourceURI)))
					graph.remove(graph.createResource(resourceURI),OWL.sameAs,graph.createResource(resourceURI));
				findAllSameAsInstances(graph, resourceURI);
			}
			
		}catch(HttpException httpException){
			System.out.println("***** deu erro ");
		}
		
		
	}	

	public void findAllSameAsInstances(Model graph, String resourceUri){
		//Show all instances that have a owl:sameAs property
		System.out.println("\nThe following instances have owl:samAss property:");
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
			
			String nextURI;
			if(subject.getURI().equals(resourceUri))
				nextURI = object.getURI();
			else
				nextURI = subject.getURI();
			
			
			
			CharsetEncoder enc = Charset.forName("ISO-8859-1").newEncoder();
			if (enc.canEncode(nextURI))
			{
				Model model = ModelFactory.createDefaultModel();
				
				if(!visitedURIs.contains(nextURI))
					search(model, nextURI);
			}
			
			
			
			
			System.out.println();
		
		}
	}
}