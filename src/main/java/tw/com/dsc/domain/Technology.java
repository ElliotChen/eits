package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "EITS_SYS_TECHNOLOGY")
public class Technology implements Identifiable<String> {

	private static final long serialVersionUID = 7009678841148222131L;

	@Id
	@Column(name = "GUID", length = 50)
	private String oid;
	
	@Column(name="TECHNOLOGY", length = 20)
	private String technology;
	@Override
	public String getOid() {
		return this.oid;
	}

	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	

}
