package tw.com.dsc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Immutable
@Table(name = "DOTJ_ACM2_ACCOUNT")
public class Account implements Identifiable<String> {

	private static final Logger logger = LoggerFactory.getLogger(Account.class);
	private static final long serialVersionUID = -7591933042359525776L;

	@Id
	@Column(name = "ID", length = 100)
	private String id;

	@Column(name = "NAME", length = 255)
	private String name;

	@Column(name = "PASSWORD", length = 255)
	private String password;

	@Column(name = "EMAIL", length = 255)
	private String email;

	@Column(name = "DEFAULT_ROLE_ID", length = 100)
	private String defaultRoleId;
	
	@Column(name = "ACTIVE_END_DATE", length = 8)
	private String endDate;
	
	@Column(name = "ACTIVE_START_DATE ", length = 8)
	private String startDate;
	
	@ManyToMany
	@JoinTable(name="DOTJ_ACM2_ACCOUNT_GROUP", 
		joinColumns={@JoinColumn(name="ACCOUNT_ID")}, 
		inverseJoinColumns={@JoinColumn(name="GROUP_ID")})
	private List<Group> groups;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String getOid() {
		return this.id;
	}

	@Override
	public void setOid(String oid) {
		this.id = oid;
	}

	public String getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(String defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", defaultRoleId=" + defaultRoleId + ", endDate=" + endDate + ", startDate=" + startDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
