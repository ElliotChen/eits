package tw.com.dsc.web.action;

import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -6472064155944913159L;

	
	public User getUser() {
		return ThreadLocalHolder.getUser();
	}

}
