package tw.com.dsc.to;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.Role;


public class User {
	private static final Logger logger = LoggerFactory.getLogger(User.class);
	private String account;
	private String password;
	private String name;
	private String group;
	private String mail;
	private String ip;
	
	private AgentType agentType;
	private boolean admin;
	private boolean guest;
	private boolean l3Admin;
	private boolean l3Leader;
	private boolean l3Agent;
	private boolean l2Admin;
	private boolean l2Leader;
	private boolean l2Agent;
	private boolean partner;
	
	private UserRole currentUserRole;
	
//	private List<Role> roles = new ArrayList<Role>();
	
	private List<String> l3LeaderGroups = new ArrayList<String>();
	private List<String> l3AgentGroups = new ArrayList<String>();
	private List<String> l2AdminGroups = new ArrayList<String>();
	private List<String> l2LeaderGroups = new ArrayList<String>();
	private List<String> l2AgentGroups = new ArrayList<String>();
	
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	public User() {
		this("","","","", AgentType.Guest);
	}
	
	public User(String account, String passowrd, String name, String group, AgentType agentType) {
		this(account, passowrd, name, group, "localhost", agentType, false, true, false, false, false, false, false, false, false);
	}
	
	public User(String account, String passowrd, String name, String group, String ip, AgentType agentType,
			boolean admin, boolean guest, boolean l3Admin, boolean l3Leader, boolean l3Agnet, 
			boolean l2Admin, boolean l2Leader, boolean l2Agnet, boolean partner) {
		this.account = account;
		this.password = passowrd;
		this.name = name;
		this.group = group;
		
		this.ip = ip;
		
		this.agentType = agentType;
		
		this.admin = admin;
		this.guest = guest;
		
		this.l3Admin = l3Admin;
		this.l3Leader = l3Leader;
		this.l3Agent = l3Agnet;
		
		this.l2Admin = l2Admin;
		this.l2Leader = l2Leader;
		this.l2Agent = l2Agnet;
		
		this.partner = partner;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Level[] getAvailableLevels() {
		ArrayList<Level> results = new ArrayList<Level>();
		results.add(Level.Public);
		if (AgentType.L2 == this.agentType) {
			results.add(Level.Partner);
		} else if (AgentType.L3 == this.agentType) {
			results.add(Level.Partner);
			results.add(Level.L3CSO);
		} else {
			
		}
		
		Level[] ls = new Level[results.size()];
		ls = results.toArray(ls);
		logger.debug("Get Available Levels[{}] for User[{}]", ls, this);
		return ls;
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", group=" + group + ", ip=" + ip + ", agentType=" + agentType + "]";
	}

	public List<String> getL3LeaderGroups() {
		return l3LeaderGroups;
	}

	public List<String> getL3AgentGroups() {
		return l3AgentGroups;
	}

	public List<String> getL2AdminGroups() {
		return l2AdminGroups;
	}

	public List<String> getL2LeaderGroups() {
		return l2LeaderGroups;
	}

	public List<String> getL2AgentGroups() {
		return l2AgentGroups;
	}

	public boolean isL3Admin() {
		return l3Admin;
	}

	public void setL3Admin(boolean l3Admin) {
		this.l3Admin = l3Admin;
	}

	public boolean isL3Leader() {
		return l3Leader;
	}

	public void setL3Leader(boolean l3Leader) {
		this.l3Leader = l3Leader;
	}

	public boolean isL3Agent() {
		return l3Agent;
	}

	public void setL3Agent(boolean l3Agent) {
		this.l3Agent = l3Agent;
	}

	public boolean isL2Admin() {
		return l2Admin;
	}

	public void setL2Admin(boolean l2Admin) {
		this.l2Admin = l2Admin;
	}

	public boolean isL2Leader() {
		return l2Leader;
	}

	public void setL2Leader(boolean l2Leader) {
		this.l2Leader = l2Leader;
	}

	public boolean isL2Agent() {
		return l2Agent;
	}

	public void setL2Agent(boolean l2Agent) {
		this.l2Agent = l2Agent;
	}

	public boolean isPartner() {
		return partner;
	}

	public void setPartner(boolean partner) {
		this.partner = partner;
	}
	
	public boolean isLeader() {
		return this.l3Admin || this.l2Admin || this.l3Leader || this.l2Leader;
	}
	
	public boolean isL3Manager() {
		return this.l3Admin || this.l3Leader;
	}
	
	public boolean isL2Manager() {
		return this.l2Admin || this.l2Leader;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public UserRole getCurrentUserRole() {
		return currentUserRole;
	}

	public void setCurrentUserRole(UserRole currentUserRole) {
		this.currentUserRole = currentUserRole;
	}
	
}
