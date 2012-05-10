package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.AccountRoleDao;
import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AccountRolePK;

@Repository("accountRoleDao")
public class AccountRoleDaoImpl extends AbstractBaseDao<AccountRole, AccountRolePK> implements AccountRoleDao {
	private static final Logger logger = LoggerFactory.getLogger(AccountRoleDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

	

}
