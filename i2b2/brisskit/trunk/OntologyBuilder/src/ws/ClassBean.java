package ws;

import java.util.Comparator;

public class ClassBean {

	String parentid;
	
	String id;
	String fullId;
	String label;
	
	
	
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullId() {
		return fullId;
	}
	public void setFullId(String fullId) {
		this.fullId = fullId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	  public boolean equals(Object o) {
	      return (o instanceof ClassBean) && (((ClassBean) o).getLabel()).equals(this.getLabel());
	  }

	  public int hashCode() {
	      return label.hashCode();
	  }
	  
	public static Comparator<ClassBean> ClassBeanNameComparator 
    = new Comparator<ClassBean>() {

public int compare(ClassBean classbean1, ClassBean classbean2) {

String classbeanName1 = classbean1.getLabel().toUpperCase();
String classbeanName2 = classbean2.getLabel().toUpperCase();

//ascending order
return classbeanName1.compareTo(classbeanName2);

//descending order
//return fruitName2.compareTo(fruitName1);
}
};
	
}
