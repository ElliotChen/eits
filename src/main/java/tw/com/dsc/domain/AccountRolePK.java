package tw.com.dsc.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountRolePK implements Serializable {

	private static final long serialVersionUID = -4190177345388679886L;

	@Column(name = "ACCOUNT_ID", length = 100)
	private String accountId;
	@Column(name = "ROLE_ID", length = 100)
	private String roleId;
	
	public AccountRolePK() {
		super();
	}
	
	public AccountRolePK(String accountId, String roleId) {
		super();
		this.accountId = accountId;
		this.roleId = roleId;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
}
