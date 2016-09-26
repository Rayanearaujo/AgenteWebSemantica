package impl;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import crawler.SemanticCrawler;
public class SemanticCrawlerImpl implements SemanticCrawler{
	static ArrayList<String> visitedURIs = new ArrayList<String>();
	
	public Model search(Model graph, String resourceURI){
		try{ 
			Model model = ModelFactory.createDefaultModel();
			
			model.read(resourceURI); 
			if(!visitedURIs.contains(resourceURI))
				visitedURIs.add(resourceURI);
			
			if(model.contains(model.createResource(resourceURI),OWL.sameAs)){
				if(model.contains(model.createResource(resourceURI),OWL.sameAs,model.createResource(resourceURI)))
					model.remove(model.createResource(resourceURI),OWL.sameAs,model.createResource(resourceURI));
				//findAllSameAsInstances(model, resourceURI);
	
				//Show all instances that have a owl:sameAs property
				//System.out.println("\nThe following instances have owl:samAss property:");
				StmtIterator statements = model.listStatements((Resource)null,OWL.sameAs,(RDFNode)null);
				while (statements.hasNext()){
					Statement statement = statements.nextStatement();
					Resource subject = statement.getSubject();
					//System.out.println("............");
					
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
					if(subject.getURI().equals(resourceURI))
						nextURI = object.getURI();
					else
						nextURI = subject.getURI();
					
					
					
					CharsetEncoder enc = Charset.forName("ISO-8859-1").newEncoder();
					if (enc.canEncode(nextURI) && !nextURI.contains("freebase"))
					{
						
						if(!visitedURIs.contains(nextURI))
							graph = graph.add(search(graph, nextURI));
					}
		
					System.out.println();
				}
			}
			graph = graph.add(model);
		}
		catch(HttpException httpException){ 
			System.out.println("*** não foi possível ler essa URI "); 
		}
		return graph;
		
	}
}