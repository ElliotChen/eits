package tw.com.dsc.to;

import java.util.ArrayList;
import java.util.List;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Level;


public class User {
	private String account;
	private String password;
	private String name;
	private String group;
	
	private String ip;
	
	private AgentType agentType;
	private boolean admin;
	private boolean guest;
	private boolean l3leader;
	private boolean l3user;
	private boolean l2leader;
	private boolean l2user;
	
	public User() {
		this("","","","", AgentType.Guest);
	}
	
	public User(String account, String passowrd, String name, String group, AgentType agentType) {
		this(account, passowrd, name, group, "localhost", agentType, false, true, false, false, false, false);
	}
	
	public User(String account, String passowrd, String name, String group, String ip, AgentType agentType,
			boolean admin, boolean guest, boolean l3leader, boolean l3user, 
			boolean l2leader, boolean l2user) {
		this.account = account;
		this.password = passowrd;
		this.name = name;
		this.group = group;
		
		this.ip = ip;
		
		this.agentType = agentType;
		
		this.admin = admin;
		this.guest = guest;
		this.l3leader = l3leader;
		this.l3user = l3user;
		this.l2leader = l2leader;
		this.l2user = l2user;
	}
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	public boolean isL3leader() {
		return l3leader;
	}

	public void setL3leader(boolean l3leader) {
		this.l3leader = l3leader;
	}

	public boolean isL3user() {
		return l3user;
	}

	public void setL3user(boolean l3user) {
		this.l3user = l3user;
	}

	public boolean isL2leader() {
		return l2leader;
	}

	public void setL2leader(boolean l2leader) {
		this.l2leader = l2leader;
	}

	public boolean isL2user() {
		return l2user;
	}

	public void setL2user(boolean l2user) {
		this.l2user = l2user;
	}
	public boolean isLeader() {
		return this.l3leader || this.l2leader;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public List<Level> getAvailableLevels() {
		ArrayList<Level> results = new ArrayList<Level>();
		if (AgentType.L2 == this.agentType) {
			results.add(Level.Public);
			results.add(Level.Partner);
		} else if (AgentType.L3 == this.agentType) {
			results.add(Level.Public);
			results.add(Level.Partner);
			results.add(Level.L3CSO);
		}
		return results;
	}
	
}
