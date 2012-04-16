package tw.com.dsc.to;

import java.io.Serializable;

public class LanguageTO implements Serializable {

	private static final long serialVersionUID = 1341580010661600298L;
	
	private String oid;
	private String id;
	private String name;
	public LanguageTO() {
		this("","","");
	}
	
	public LanguageTO(String oid, String id, String name) {
		this.oid = oid;
		this.id = id;
		this.name = name;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
