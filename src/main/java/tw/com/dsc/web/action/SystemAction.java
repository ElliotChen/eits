package tw.com.dsc.web.action;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("systemAction")
@Scope("prototype")
public class SystemAction extends ActionSupport implements Serializable, Preparable {
	private static final long serialVersionUID = -2499466800597200018L;
	private User user;
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
		} else if (user.getAccount().startsWith("l3")) {
			user.setL3leader(true);
			user.setL3user(true);
			user.setAdmin(true);
			user.setGuest(false);
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
		ThreadLocalHolder.setUser(user);
		return "index";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
