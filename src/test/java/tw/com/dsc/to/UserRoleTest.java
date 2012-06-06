package tw.com.dsc.to;

import static org.junit.Assert.*;

import org.junit.Test;

import tw.com.dsc.domain.Role;
import tw.com.dsc.util.SystemUtils;

public class UserRoleTest {

	@Test
	public void test() {
		UserRole ur = new UserRole(Role.L2Admin, "L2_ZyDK_TEAM_DK");
		
		ur = new UserRole(Role.L2Admin, "L2_ZyDE_L2+Agent_Team");
		ur = new UserRole(Role.L2Admin, "L2_ZyDE_EURBU-Admin_Team");
		
		
		System.out.println(SystemUtils.parseGroupId("L2_ZyDK_TEAM_DK_Team"));
		System.out.println(SystemUtils.parseGroupId("L2_ZyDE_L2+Agent_Team"));
		System.out.println(SystemUtils.parseGroupId("L2_ZyDE_EURBU-Admin_Team"));
	}

}
