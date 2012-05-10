package tw.com.dsc.dao.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
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
	@Autowired
	private AccountRoleDao accountRoleDao;
	@Test
	public void test() {
		AccountRolePK oid = new AccountRolePK("L3_Admin", "L3_Admin");
		AccountRole ar = accountRoleDao.findByOid(oid);
		Assert.assertEquals("L3_Admin", ar.getDefaultGroupId());
	}

}
