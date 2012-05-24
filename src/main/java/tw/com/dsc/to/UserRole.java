package tw.com.dsc.to;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.com.dsc.domain.Role;

public class UserRole {
	public static final Pattern L2_BRANCH = Pattern.compile("^L2_(.*)_.+$");
	private Role role;
	private String group;
	private String branchCode;
//	private AgentType agentType;
	public UserRole() {
		super();
	}
	public UserRole(Role role, String group) {
		this.role = role;
		this.group = group;
		if (Role.L2Admin == role || Role.L2Agent == role || Role.L2Leader == role) {
			System.out.println("Check Group "+group+" for BranchCode");
			Matcher matcher = L2_BRANCH.matcher(group);
			if (matcher.matches()) {
				this.branchCode = matcher.group(1);
				System.out.println("Check Group for BranchCode:"+branchCode);
			}
		}
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
		return "UserRole [role=" + role + ", group=" + group + ", branchCode=" + branchCode + "]";
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}
