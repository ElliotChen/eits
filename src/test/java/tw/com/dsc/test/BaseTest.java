package tw.com.dsc.test;

import tw.com.dsc.domain.Role;
import tw.com.dsc.to.User;
import tw.com.dsc.to.UserRole;

public class BaseTest {
	public static User guest = new User("Guest","","Guest","localhost", new UserRole(Role.Guest, "Guest"));
	public static User l3leader = new User("L3_Leader","","Test","localhost", new UserRole(Role.L3Leader, "Test"));
	public static User l3agnet = new User("L3_Agent","","Test","localhost", new UserRole(Role.L3Agent, "Test"));
	public static User l2leader = new User("L2_Leader","","Test","localhost", new UserRole(Role.L2Leader, "Test"));
	public static User l2agent = new User("L2_Agent","","Test","localhost", new UserRole(Role.L2Agent, "Test"));
}
