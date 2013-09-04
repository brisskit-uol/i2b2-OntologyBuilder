package ws;

import java.util.ArrayList;
import java.util.List;

public class SearchBean {

	int ontologyVersionId;
	int ontologyId;
	String ontologyDisplayLabel;
	String recordType;
	String objectType;
	String conceptId;
	String conceptIdShort;
	String preferredName;
	String contents;
	int isObsolete;
	
	public int getOntologyVersionId() {
		return ontologyVersionId;
	}
	public void setOntologyVersionId(int ontologyVersionId) {
		this.ontologyVersionId = ontologyVersionId;
	}
	public int getOntologyId() {
		return ontologyId;
	}
	public void setOntologyId(int ontologyId) {
		this.ontologyId = ontologyId;
	}
	public String getOntologyDisplayLabel() {
		return ontologyDisplayLabel;
	}
	public void setOntologyDisplayLabel(String ontologyDisplayLabel) {
		this.ontologyDisplayLabel = ontologyDisplayLabel;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getConceptId() {
		return conceptId;
	}
	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}
	public String getConceptIdShort() {
		return conceptIdShort;
	}
	public void setConceptIdShort(String conceptIdShort) {
		this.conceptIdShort = conceptIdShort;
	}
	public String getPreferredName() {
		return preferredName;
	}
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getIsObsolete() {
		return isObsolete;
	}
	public void setIsObsolete(int isObsolete) {
		this.isObsolete = isObsolete;
	}
	
	
}
