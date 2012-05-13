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
//	private String group;
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
		this("","","","Localhost", new UserRole(Role.Guest, "Guest"));
	}
	
	public User(String account, String password, String name, String ip, UserRole userRole) {
		this.account = account;
		this.password = password;
		this.name = name;
		this.ip = ip;
		this.currentUserRole = userRole;
//		this.userRoles.add(userRole);
		
		this.checkAuthorization();
	}
	/*
	public User(String account, String passowrd, String name, String ip, UserRole userRole,
			boolean admin, boolean guest, boolean l3Admin, boolean l3Leader, boolean l3Agnet, 
			boolean l2Admin, boolean l2Leader, boolean l2Agnet, boolean partner) {
		this.account = account;
		this.password = passowrd;
		this.name = name;
		
		this.ip = ip;
		
		this.currentUserRole = userRole;
		this.userRoles.add(userRole);
		
		
		
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
	*/
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
		return null == this.currentUserRole?"":this.currentUserRole.getGroup();
	}
	/*
	public void setGroup(String group) {
		this.group = group;
	}
	*/
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public AgentType getAgentType() {
		return this.agentType;
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
		if (AgentType.L2 == this.getAgentType()) {
			results.add(Level.Partner);
		} else if (AgentType.L3 == this.getAgentType()) {
			results.add(Level.Partner);
			results.add(Level.L3CSO);
		} else {
			
		}
		
		Level[] ls = new Level[results.size()];
		ls = results.toArray(ls);
		logger.debug("Get Available Levels[{}] for User[{}]", ls, this);
		return ls;
	}
	
	public void reset() {
		this.admin = false;
		this.account = "Guest";
		this.currentUserRole = new UserRole(Role.Guest, "");
		checkAuthorization();
	}
	
	@Override
	public String toString() {
		return "User [account=" + account + ", ip=" + ip + ", currentUserRole=" + this.currentUserRole + "]";
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

	public boolean isAdmin() {
		return admin;
	}

	public boolean isGuest() {
		return guest;
	}

	public boolean isL3Admin() {
		return l3Admin;
	}

	public boolean isL3Leader() {
		return l3Leader;
	}

	public boolean isL3Agent() {
		return l3Agent;
	}

	public boolean isL2Admin() {
		return l2Admin;
	}

	public boolean isL2Leader() {
		return l2Leader;
	}

	public boolean isL2Agent() {
		return l2Agent;
	}

	public boolean isPartner() {
		return partner;
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
	
	public boolean isL3() {
		return this.l3Admin || this.l3Leader || this.l3Agent;
	}
	
	public boolean isL2() {
		return this.l2Admin || this.l2Leader || this.l2Agent || this.partner;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public UserRole getCurrentUserRole() {
		return currentUserRole;
	}
	/*
	public void setCurrentUserRole(UserRole currentUserRole) {
		this.currentUserRole = currentUserRole;
	}
	*/
	
	public void switchRole(Role role) {
		for (UserRole ur : userRoles) {
			if (ur.getRole() == role) {
				logger.debug("User[{}] switch role as {} success!", this, role);
				this.currentUserRole = ur;
				checkAuthorization();
				break;
			}
		}
	}
	
	public void checkAuthorization() {
		Role role = this.getCurrentRole();
		
		this.guest = false;
		this.l2Admin = false;
		this.l2Agent = false;
		this.l2Leader = false;
		this.l3Admin = false;
		this.l3Agent = false;
		this.l3Leader = false;
		this.admin = false;
		this.partner = false;
		
		
		switch(role) {
		case Guest:
			this.guest = true;
			this.agentType = AgentType.Guest;
			break;
		case Partner:
			this.partner = true;
			this.agentType = AgentType.L2;
			break;
		case L2Admin:
			this.l2Admin = true;
			this.admin = true;
			this.agentType = AgentType.L2;
			break;
		case L2Leader:
			this.l2Leader = true;
			this.agentType = AgentType.L2;
			break;
		case L2Agent:
			this.l2Agent = true;
			this.agentType = AgentType.L2;
			break;
		case L3Admin:
			this.l3Admin = true;
			this.admin = true;
			this.agentType = AgentType.L3;
			break;
		case L3Leader:
			this.l3Leader = true;
			this.agentType = AgentType.L3;
			break;
		case L3Agent:
			this.l3Agent = true;
			this.agentType = AgentType.L3;
			break;
		}
	}
	
	public String[] getAvailableGroups() {
		String[] result = null;
		List<String> groups = null;
		Role role = this.getCurrentRole();
		switch(role) {
		case L2Admin:
			groups = this.l2AdminGroups;
			break;
		case L2Leader:
			groups = this.l2LeaderGroups;
			break;
		case L2Agent:
			groups = this.l2AgentGroups;
			break;
		case L3Leader:
			groups = this.l3LeaderGroups;
			break;
		case L3Agent:
			groups = this.l3AgentGroups;
			break;
		case L3Admin:
		case Guest:
		case Partner:
		default:
			groups = new ArrayList<String>();
		}
		logger.debug("Get Available Groups [{}] for User[{}]", groups, this);
		result = new String[groups.size()];
		return groups.toArray(result);
	}
	
	public Role getCurrentRole() {
		Role role = null;
		if (null == this.currentUserRole || null == this.currentUserRole.getRole()) {
			logger.error("Please init CurrentUserRole for checkAuthorization");
			role = Role.Guest;
		} else {
			role = this.currentUserRole.getRole();
		}
		return role;
	}
}
