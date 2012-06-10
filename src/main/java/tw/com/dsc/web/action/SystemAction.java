package tw.com.dsc.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.ErrorType;
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
	
	private String token;
	@Autowired
	private SystemService systemService;
	@Override
	public void prepare() throws Exception {
		loginUser = ThreadLocalHolder.getOperator();
	}
	public String index() {
//		return "index";
		return "loginRedirect";
	}
	public String login() {
		ErrorType error = this.systemService.login(loginUser);
		if (null != error) {
			this.addActionError(this.getText("error."+error.name()));
		}
		
		ThreadLocalHolder.setUser(loginUser);
//		return "index";
		return "loginRedirect";
	}

	public String logout() {
		loginUser = new User();
		ThreadLocalHolder.setUser(loginUser);
		return "loginRedirect";
	}

	public String switchRole() {
		User op = ThreadLocalHolder.getUser();
		logger.debug("User[{}] try to switch role to [{}]", op.getAccount(), this.userRole);
		op.switchRole(userRole);
		return "loginRedirect";
	}
	
	public String eitsLogin() {
		ErrorType error = this.systemService.eitsLogin(token);
		if (null != error) {
			this.addActionError(this.getText("error."+error.name()));
		}
		return "loginRedirect";
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
