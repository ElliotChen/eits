package tw.com.dsc.dao.impl;

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

import tw.com.dsc.dao.AccountRoleDao;
import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AccountRolePK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
public class AccountRoleDaoImplTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountRoleDaoImplTest.class);
	@Autowired
	private AccountRoleDao accountRoleDao;
	@Test
	public void test() {
		AccountRolePK oid = new AccountRolePK("L3_Admin", "L3_Admin");
		AccountRole ar = accountRoleDao.findByOid(oid);
		Assert.assertEquals("L3_Admin", ar.getDefaultGroupId());
	}

	@Test
	public void testListByAccount() {
		List<AccountRole> ars = accountRoleDao.listByAccount("L3_Admin");
		logger.debug("Find [{}] AccountRole(s) for Account", ars.size(), "L3_Admin");
	}
}
