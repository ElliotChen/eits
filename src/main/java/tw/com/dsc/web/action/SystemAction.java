package tw.com.dsc.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.Preparable;

@Component("systemAction")
@Scope("prototype")
public class SystemAction extends BaseAction implements Preparable {
	private static final Logger logger = LoggerFactory.getLogger(SystemAction.class);
	private static final long serialVersionUID = -2499466800597200018L;
	private User loginUser;
	
	private String userRole;
	@Override
	public void prepare() throws Exception {
		loginUser = ThreadLocalHolder.getOperator();
	}
	public String index() {
		return "index";
	}
	public String login() {
		if ("l3leader".equals(loginUser.getAccount())) {
			loginUser.setAdmin(true);
			loginUser.setGuest(false);
			loginUser.setL2leader(false);
			loginUser.setL2user(false);
			loginUser.setL3leader(true);
			loginUser.setL3user(true);
			loginUser.setAgentType(AgentType.L3);
		} else if ("l3user".equals(loginUser.getAccount())) {
			loginUser.setAdmin(false);
			loginUser.setGuest(false);
			loginUser.setL2leader(false);
			loginUser.setL2user(false);
			loginUser.setL3leader(false);
			loginUser.setL3user(true);
			loginUser.setAgentType(AgentType.L3);
		} else if ("l2leader".equals(loginUser.getAccount())) {
			loginUser.setAdmin(false);
			loginUser.setGuest(false);
			loginUser.setL2leader(true);
			loginUser.setL2user(true);
			loginUser.setL3leader(false);
			loginUser.setL3user(false);
			loginUser.setAgentType(AgentType.L2);
		} else if ("l2user".equals(loginUser.getAccount())) {
			loginUser.setAdmin(false);
			loginUser.setGuest(false);
			loginUser.setL2leader(false);
			loginUser.setL2user(true);
			loginUser.setL3leader(false);
			loginUser.setL3user(false);
			loginUser.setAgentType(AgentType.L2);
		} else {
			logger.warn("Switch User[{}] to Guest!", loginUser.getAccount());
			loginUser.setAdmin(false);
			loginUser.setGuest(true);
			loginUser.setL2leader(false);
			loginUser.setL2user(false);
			loginUser.setL3leader(false);
			loginUser.setL3user(false);
			loginUser.setAgentType(AgentType.Guest);
		}
		ThreadLocalHolder.setUser(loginUser);
		return "index";
	}

	public String logout() {
		loginUser = new User();
		loginUser.setAccount("Guest");
		loginUser.setName("Guest");
		loginUser.setAdmin(false);
		loginUser.setGuest(true);
		loginUser.setAgentType(AgentType.Guest);
		ThreadLocalHolder.setUser(loginUser);
		return "index";
	}

	public String switchRole() {
		User op = ThreadLocalHolder.getUser();
		logger.debug("User[{}] try to switch role to [{}]", op.getAccount(), this.userRole);
		
		if ("l3leader".equals(this.userRole)) {
			op.setAdmin(true);
			op.setGuest(false);
			op.setL2leader(false);
			op.setL2user(false);
			op.setL3leader(true);
			op.setL3user(true);
			op.setAgentType(AgentType.L3);
		} else if ("l3user".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(false);
			op.setL2user(false);
			op.setL3leader(false);
			op.setL3user(true);
			op.setAgentType(AgentType.L3);
		} else if ("l2leader".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(true);
			op.setL2user(true);
			op.setL3leader(false);
			op.setL3user(false);
			op.setAgentType(AgentType.L2);
		} else if ("l2user".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(false);
			op.setL2user(true);
			op.setL3leader(false);
			op.setL3user(false);
			op.setAgentType(AgentType.L2);
		} else {
			logger.warn("Switch User[{}] to Guest!", op.getAccount());
			op.setAdmin(false);
			op.setGuest(true);
			op.setL2leader(false);
			op.setL2user(false);
			op.setL3leader(false);
			op.setL3user(false);
			op.setAgentType(AgentType.Guest);
		}
		
		
		return "index";
	}
	
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
