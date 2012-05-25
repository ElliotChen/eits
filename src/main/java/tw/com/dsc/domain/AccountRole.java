package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "DOTJ_ACM2_ACCOUNT_ROLE")
public class AccountRole implements Identifiable<AccountRolePK> {
	private static final long serialVersionUID = 9012314065005605051L;

	@EmbeddedId
	private AccountRolePK accountRolePK;
	
	@Column(name = "DEFAULT_GROUP_ID", length = 100)
	private String defaultGroupId;

	public AccountRolePK getAccountRolePK() {
		return accountRolePK;
	}

	public void setAccountRolePK(AccountRolePK accountRolePK) {
		this.accountRolePK = accountRolePK;
	}

	public String getDefaultGroupId() {
		return defaultGroupId;
	}

	public void setDefaultGroupId(String defaultGroupId) {
		this.defaultGroupId = defaultGroupId;
	}

	@Override
	public AccountRolePK getOid() {
		return this.accountRolePK;
	}

	@Override
	public void setOid(AccountRolePK oid) {
		this.accountRolePK = oid;
	}
	
	
}
