package tw.com.dsc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.AccountRoleDao;
import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AccountRolePK;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.SimpleCondition;

@Repository("accountRoleDao")
public class AccountRoleDaoImpl extends AbstractBaseDao<AccountRole, AccountRolePK> implements AccountRoleDao {
	private static final Logger logger = LoggerFactory.getLogger(AccountRoleDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	@Override
	public List<AccountRole> listByAccount(String account) {
		AccountRole example = new AccountRole();
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("accountRolePK.accountId", account, OperationEnum.EQ));
		return this.listByExample(example, conds, LikeMode.NONE, null ,null);
	}

	

}
