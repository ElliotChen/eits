package tw.com.dsc.web.action;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("systemAction")
@Scope("prototype")
public class SystemAction extends ActionSupport implements Serializable, Preparable {
	private static final Logger logger = LoggerFactory.getLogger(SystemAction.class);
	private static final long serialVersionUID = -2499466800597200018L;
	private User user;
	
	private String userRole;
	@Override
	public void prepare() throws Exception {
		user = new User();
	}
	public String index() {
		return "index";
	}
	public String login() {
		if (user.getAccount().startsWith("l2")) {
			user.setL2leader(true);
			user.setL2user(true);
			user.setGuest(false);
			user.setAgentType(AgentType.L2);
		} else if (user.getAccount().startsWith("l3")) {
			user.setL3leader(true);
			user.setL3user(true);
			user.setAdmin(true);
			user.setGuest(false);
			user.setAgentType(AgentType.L3);
		} else {
			this.addActionError("Login Failed! Please check account/password then login again.");
		}
		ThreadLocalHolder.setUser(user);
		return "index";
	}

	public String logout() {
		user = new User();
		user.setAccount("Guest");
		user.setName("Guest");
		user.setAdmin(false);
		user.setGuest(true);
		user.setAgentType(AgentType.Guest);
		ThreadLocalHolder.setUser(user);
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
		} else if ("l3user".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(false);
			op.setL2user(false);
			op.setL3leader(false);
			op.setL3user(true);
		} else if ("l2leader".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(true);
			op.setL2user(true);
			op.setL3leader(false);
			op.setL3user(false);
		} else if ("l2user".equals(this.userRole)) {
			op.setAdmin(false);
			op.setGuest(false);
			op.setL2leader(false);
			op.setL2user(true);
			op.setL3leader(false);
			op.setL3user(false);
		} else {
			logger.warn("Switch User[{}] to Guest!", op.getAccount());
			op.setAdmin(false);
			op.setGuest(true);
			op.setL2leader(false);
			op.setL2user(false);
			op.setL3leader(false);
			op.setL3user(false);
		}
		
		
		return null;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
