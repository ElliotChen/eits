package tw.com.dsc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Role;
import tw.com.dsc.to.User;
import tw.com.dsc.to.UserRole;


public abstract class ThreadLocalHolder {
	private static final Logger logger = LoggerFactory.getLogger(ThreadLocalHolder.class);
	public static final User DEFAULT_USER = new User("System", "NA", "System", "Localhost", new UserRole(Role.Guest, "Guest"));
	private static ThreadLocal<User> USER_HOLDER = new ThreadLocal<User>();
	public static User getUser() {
		return USER_HOLDER.get();
	}
	
	public static void setUser(User user) {
		USER_HOLDER.set(user);
	}
	
	public static void removeUser() {
		USER_HOLDER.remove();
	}
	
	public static User getOperator() {
		return null != USER_HOLDER.get()?USER_HOLDER.get():DEFAULT_USER;
	}
}
