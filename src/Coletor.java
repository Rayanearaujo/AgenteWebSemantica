import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


public class Coletor {
	
	public static void main(String[] args){
		//http://dbpedia.org/resource/Zico,
		String resourceUri = "http://liyangyu.com/foaf.rdf"; 
		coletarDados(resourceUri);
	}
	
	public static void coletarDados(String uriRecurso){
		Model model = ModelFactory.createDefaultModel();
		model.read(uriRecurso);
		model.write(System.out, "N3"); 
	}

}
