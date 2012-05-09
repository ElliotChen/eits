package tw.com.dsc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
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
	
}
