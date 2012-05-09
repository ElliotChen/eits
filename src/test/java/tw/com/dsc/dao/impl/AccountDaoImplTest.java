package tw.com.dsc.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.domain.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
public class AccountDaoImplTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImplTest.class);
	@Autowired
	private AccountDao accountDao;
	@Test
	public void test() {
		Account example = new Account();
		example.setName("L3_Admin");
		this.accountDao.listByExample(example);
		
		
		Account account = this.accountDao.findByOid("L3_Admin");
		logger.debug("Group Size is [{}]", account.getGroups().size());
	}

}
