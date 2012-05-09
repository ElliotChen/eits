package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.domain.Account;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractBaseDao<Account, String> implements AccountDao {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void create(Account entity) {
	}

	@Override
	public void update(Account entity) {
	}

	@Override
	public void delete(Account entity) {
	}
	
	@Override
	public void merge(Account entity) {
	}

}
