package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.domain.Group;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
public class SystemServiceImplTest {
	@Autowired
	private SystemService systemService;
	@Test
	public void test() {
		User user = new User();
		user.setAccount("L3_Admin");
		
		this.systemService.login(user);
	}

	@Test
	public void testParseGroup() {
		List<Group> groups = new ArrayList<Group>();
		User user = new User();
		Group group = new Group();
		groups.add(group);
		
		group.setId("L3_Team1_Leader");
		systemService.parseGroup(groups, user);
		Assert.assertTrue(user.getL3LeaderGroups().contains("L3_Team1"));
		
		group.setId("L3_Team1_Team");
		systemService.parseGroup(groups, user);
		Assert.assertTrue(user.getL3AgentGroups().contains("L3_Team1"));
		
		group.setId("L2_ZyIN_Admin");
		systemService.parseGroup(groups, user);
		Assert.assertTrue(user.getL2AdminGroups().contains("L2_ZyIN"));
		
		group.setId("L2_ZyIN_Team1_Leader");
		systemService.parseGroup(groups, user);
		Assert.assertTrue(user.getL2LeaderGroups().contains("L2_ZyIN_Team1"));
		
		group.setId("L2_ZyIN_Team1_Team");
		systemService.parseGroup(groups, user);
		Assert.assertTrue(user.getL2AgentGroups().contains("L2_ZyIN_Team1"));
		
		group.setId("C_ZyUK");
		systemService.parseGroup(groups, user);
	}
}
