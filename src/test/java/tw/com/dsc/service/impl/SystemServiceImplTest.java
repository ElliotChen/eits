package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AccountRolePK;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.SystemUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@Transactional
public class SystemServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImplTest.class);
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
		SystemUtils.parseGroup(groups, user);
		Assert.assertTrue(user.getL3LeaderGroups().contains("L3_Team1"));
		
		group.setId("L3_Team1_Team");
		SystemUtils.parseGroup(groups, user);
		Assert.assertTrue(user.getL3AgentGroups().contains("L3_Team1"));
		
		group.setId("L2_ZyIN_Admin");
		SystemUtils.parseGroup(groups, user);
		Assert.assertTrue(user.getL2AdminGroups().contains("L2_ZyIN"));
		
		group.setId("L2_ZyIN_Team1_Leader");
		SystemUtils.parseGroup(groups, user);
		Assert.assertTrue(user.getL2LeaderGroups().contains("L2_ZyIN_Team1"));
		
		group.setId("L2_ZyIN_Team1_Team");
		SystemUtils.parseGroup(groups, user);
		Assert.assertTrue(user.getL2AgentGroups().contains("L2_ZyIN_Team1"));
		
		group.setId("C_ZyUK");
		SystemUtils.parseGroup(groups, user);
	}
	
	@Test
	public void testParseRole() {
		User user = new User();
		List<AccountRole> ars = new ArrayList<AccountRole>();
		AccountRole ar = new AccountRole();
		AccountRolePK pk = new AccountRolePK();
		ar.setAccountRolePK(pk);
		ars.add(ar);
		
		pk.setRoleId("L2_Agent");
		ar.setDefaultGroupId("DR_GROUP");
		
		
		
		SystemUtils.parseRole(ars, user);
		
		Assert.assertNotNull(user.getCurrentUserRole());
	}
	
	@Test
	public void testFindLeader() {
		List<Account> accounts = null;
		Article article = new Article();
		article.setAgentType(AgentType.L2);
		article.setUserGroup("L2_ZyDE_Team1");
		accounts = systemService.findGroupLeaders(article);
		logger.debug("Group[{}]:Leader Account[{}]", article.getUserGroup(), accounts);
		Assert.assertFalse(accounts.isEmpty());
		
		article.setAgentType(AgentType.L3);
		article.setUserGroup("L3_Team1");
		accounts = systemService.findGroupLeaders(article);
		logger.debug("Group[{}]:Leader Account[{}]", article.getUserGroup(), accounts);
		Assert.assertFalse(accounts.isEmpty());
		
		
		article.setAgentType(AgentType.L2);
		article.setUserGroup("abc");
		accounts = systemService.findGroupLeaders(article);
		Assert.assertTrue(accounts.isEmpty());
		
		article.setAgentType(AgentType.L3);
		article.setUserGroup("abc");
		accounts = systemService.findGroupLeaders(article);
		Assert.assertTrue(accounts.isEmpty());
	}
	
	@Test
	public void testFindAdmin() {
		List<Account> accounts = null;
		Article article = new Article();
		article.setAgentType(AgentType.L2);
		article.setUserGroup("L2_ZyDE_Team1");
		accounts = systemService.findGroupAdmins(article);
		logger.debug("Group[{}]:Admin Account[{}]", article.getUserGroup(), accounts);
		Assert.assertTrue(accounts.isEmpty());
		
		article.setAgentType(AgentType.L3);
		article.setUserGroup("L3_Team1");
		accounts = systemService.findGroupAdmins(article);
		logger.debug("Group[{}]:Admin Account[{}]", article.getUserGroup(), accounts);
		Assert.assertFalse(accounts.isEmpty());
	}
	
	@Test
	public void testLogin() {
		User user = new User();
		user.setAccount("L3");
		user.setPassword("123");
		ErrorType et = this.systemService.login(user);
		Assert.assertEquals(ErrorType.NotFound, et);
		
		user.setAccount("L3_Admin");
		user.setPassword("xxx");
		et = this.systemService.login(user);
		Assert.assertEquals(ErrorType.Password, et);
		
		
	}
}
