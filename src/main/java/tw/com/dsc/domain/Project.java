package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "EITS_SYS_PROJECT_MASTER")
public class Project implements Identifiable<String> {
	private static final long serialVersionUID = 761987381223320305L;

	@Id
	@Column(name = "GUID", length = 50)
	private String oid;
	
	@Column(name="PROJECT_CODE", length = 100)
	private String projectCode;
	@Override
	public String getOid() {
		return this.oid;
	}

	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

}
