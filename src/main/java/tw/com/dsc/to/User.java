package tw.com.dsc.to;

import org.apache.commons.lang.StringUtils;

public class User {
	private String account;
	private String password;
	private String name;
	private String group;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isL3Admin() {
		return "l3admin".equals(this.account);
	}
	
	public boolean isL2Admin() {
		return "l2admin".equals(this.account);
	}
	
	public boolean isL3User() {
		return this.account.startsWith("l3");
	}
	
	public boolean isL2User() {
		return this.account.startsWith("l2");
	}
	
	public boolean isGuest() {
		if (StringUtils.isEmpty(account) || "Guest".equalsIgnoreCase(account)) {
			return true;
		}
		return !this.account.startsWith("l2") && !this.account.startsWith("l3");
	}
}
