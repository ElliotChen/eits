package tw.com.dsc.to;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Role;

public class UserRole {
	private Role role;
	private String group;
//	private AgentType agentType;
	public UserRole() {
		super();
	}
	public UserRole(Role role, String group) {
		this.role = role;
		this.group = group;
//		this.agentType = agnetType;
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
	/*
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
	*/
	@Override
	public String toString() {
		return "UserRole [role=" + role + ", group=" + group + "]";
	}
}
