package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_EITS_LANGUAGE")
public class Language extends AbstractOIdObjectAuditable {

	private static final long serialVersionUID = 4815255597802616325L;

	public Language() {
		super();
	}
	
	public Language(String oid, String name) {
		this.oid = oid;
		this.name = name;
	}
	
	@Column(name="NAME", length=50 , unique=true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Language [name=" + name + ", oid=" + oid + "]";
	}
}
