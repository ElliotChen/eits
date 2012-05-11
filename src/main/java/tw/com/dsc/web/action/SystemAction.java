package tw.com.dsc.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Role;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.Preparable;

@Component("systemAction")
@Scope("prototype")
public class SystemAction extends BaseAction implements Preparable {
	private static final Logger logger = LoggerFactory.getLogger(SystemAction.class);
	private static final long serialVersionUID = -2499466800597200018L;
	private User loginUser;
	
	private Role userRole;
	
	@Autowired
	private SystemService systemService;
	@Override
	public void prepare() throws Exception {
		loginUser = ThreadLocalHolder.getOperator();
	}
	public String index() {
		return "index";
	}
	public String login() {
		this.systemService.login(loginUser);
		ThreadLocalHolder.setUser(loginUser);
		return "index";
	}

	public String logout() {
		loginUser = new User();
		loginUser.setAccount("Guest");
		loginUser.setName("Guest");
		loginUser.setAdmin(false);
		loginUser.setGuest(true);
		ThreadLocalHolder.setUser(loginUser);
		return "index";
	}

	public String switchRole() {
		User op = ThreadLocalHolder.getUser();
		logger.debug("User[{}] try to switch role to [{}]", op.getAccount(), this.userRole);
		op.switchRole(userRole);
		return "index";
	}
	
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public Role getUserRole() {
		return userRole;
	}
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}
	public SystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
