package tw.com.dsc.util;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.to.User;


public abstract class ThreadLocalHolder {
	private static final User DEFAULT_USER = new User("System", "NA", "System", "Guest", AgentType.Guest);
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
