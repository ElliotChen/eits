package tw.com.dsc.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.com.dsc.to.User;
import tw.com.dsc.util.SystemConstants;
import tw.com.dsc.util.ThreadLocalHolder;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

public class ThreadLocalHolderFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadLocalHolderFilter.class);
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		logger.info("Host[{}]:[{}] Send Request:[{}]", new Object[]{request.getRemoteHost(), request.getRemotePort(), httpRequest.getRequestURI()});
		
		HttpSession session = httpRequest.getSession(true);
		User user = (User) session.getAttribute(SystemConstants.SESSION_USER);
		//Init guest user 
		if (null == user) {
			user = new User();
			user.setAccount("Guest");
			user.setName("Guest");
			user.setIp(request.getRemoteHost());
			
			session.setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, java.util.Locale.US);
		}
		String rl = (String) httpRequest.getParameter(I18nInterceptor.DEFAULT_PARAMETER);
		if (StringUtils.isNotEmpty(rl)) {
			
		}

		ThreadLocalHolder.setUser(user);
		chain.doFilter(request, response);
		
		if (null != ThreadLocalHolder.getUser()) {
			session.setAttribute(SystemConstants.SESSION_USER, ThreadLocalHolder.getUser());
		} else {
			session.removeAttribute(SystemConstants.SESSION_USER);
		}
		
		ThreadLocalHolder.removeUser();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
	}

}
