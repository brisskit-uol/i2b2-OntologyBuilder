package ws;

import java.util.Comparator;

public class Ontology {

	  int id;
	  String name;
	  int ontologyId;
	  
	
	public int getOntologyId() {
		return ontologyId;
	}
	public void setOntologyId(int ontologyId) {
		this.ontologyId = ontologyId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	public static Comparator<Ontology> OntologyNameComparator 
                   = new Comparator<Ontology>() {

		public int compare(Ontology ontology1, Ontology ontology2) {
		
		String ontologyName1 = ontology1.getName().toUpperCase();
		String ontologyName2 = ontology2.getName().toUpperCase();
		
		//ascending order
		return ontologyName1.compareTo(ontologyName2);
		
		//descending order
		//return fruitName2.compareTo(fruitName1);
		}
	};
	  
}
