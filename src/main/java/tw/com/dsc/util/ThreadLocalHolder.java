package tw.com.dsc.util;

import tw.com.dsc.to.User;


public abstract class ThreadLocalHolder {
	private static ThreadLocal<User> USER_HOLDER = new ThreadLocal<User>();
	public static User getUser() {
		return USER_HOLDER.get();
	}
	
	public static void setUser(User user) {
		USER_HOLDER.set(user);
	}
	
}
