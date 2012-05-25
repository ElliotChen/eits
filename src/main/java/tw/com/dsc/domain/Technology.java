package tw.com.dsc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

	@OneToMany
	@JoinColumn(referencedColumnName="GUID", name="GUID_TECHNOLOGY")
	private List<TechnologyItem> items;
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

	public List<TechnologyItem> getItems() {
		return items;
	}

	public void setItems(List<TechnologyItem> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technology other = (Technology) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}
}
