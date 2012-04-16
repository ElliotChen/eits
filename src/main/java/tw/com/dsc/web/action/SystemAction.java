package tw.com.dsc.web.action;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.User;
import tw.com.dsc.util.SystemConstants;
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
	
	public String login() {
		if (user.isL2User() || user.isL3User()) {
			ThreadLocalHolder.setUser(user);
		}
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
