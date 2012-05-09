package tw.com.dsc.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "DOTJ_ACM2_GROUP")
public class Group implements Identifiable<String> {

	private static final Logger logger = LoggerFactory.getLogger(Group.class);
	private static final long serialVersionUID = 1532505631702727483L;
	
	@Id
	@Column(name = "ID", length = 100)
	private String id;

	@Column(name = "NAME", length = 255)
	private String name;
	
	@ManyToMany(mappedBy="groups")
	private List<Account> accounts;

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

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
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
