package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "EITS_SYS_TECHNOLOGY_ITEM")
public class TechnologyItem implements Identifiable<String> {

	private static final long serialVersionUID = 6514883676311731634L;

	@Id
	@Column(name = "GUID", length = 50)
	private String oid;
	
	@Column(name = "ITEM", length = 50)
	private String name;
	
	@Override
	public String getOid() {
		return this.oid;
	}

	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
