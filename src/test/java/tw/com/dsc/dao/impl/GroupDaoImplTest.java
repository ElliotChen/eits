package tw.com.dsc.dao.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.GroupDao;
import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Group;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
public class GroupDaoImplTest {
	private static final Logger logger = LoggerFactory.getLogger(GroupDaoImplTest.class);
	@Autowired
	private GroupDao groupDao;
	@Test
	public void test() {
		Group example = new Group();
		example.setId("L3_Admin");
		groupDao.listByExample(example);
		
		Group group = groupDao.findByOid("L3_Admin");
		Assert.assertNotNull(group);
		Assert.assertFalse(group.getAccounts().isEmpty());
		
		Account account = group.getAccounts().get(0);
		logger.debug("Find Account[{}] in group", account.getId());
	}

}
