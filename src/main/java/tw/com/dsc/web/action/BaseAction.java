package tw.com.dsc.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);
	private static final long serialVersionUID = -6472064155944913159L;

	public User getUser() {
		return ThreadLocalHolder.getUser();
	}
	
	protected void renderText(String text) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error("Render Text "+text+" Failed!",e);
		}
	}
}
