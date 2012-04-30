package tw.com.dsc.test;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.to.User;

public class BaseTest {
	public static User guest = new User("guest","","guest","guest","", AgentType.Guest, false, true, false, false, false, false, false, false);
	public static User l3leader = new User("l3leader","","l3leader","g1","", AgentType.L3, true, false, true, true, true, false, false, false);
	public static User l3user = new User("l3user","","l3user","g1","", AgentType.L3,  false, false, false, false, true, false, false, false);
	public static User l2leader = new User("l2leader","","l2leader","g2","", AgentType.L2, false, false, false, false, false, true, true, true);
	public static User l2user = new User("l2user","","l2user","g2","", AgentType.L2, false, false, false, false, false, false, false, true);
}
