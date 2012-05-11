package tw.com.dsc.to;

import tw.com.dsc.domain.Role;

public class UserRole {
	private Role role;
	private String group;
	public UserRole() {
		super();
	}
	public UserRole(Role role, String group) {
		super();
		this.role = role;
		this.group = group;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
