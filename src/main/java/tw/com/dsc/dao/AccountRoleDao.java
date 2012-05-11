package tw.com.dsc.dao;

import java.util.List;

import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AccountRolePK;

public interface AccountRoleDao extends BaseDao<AccountRole, AccountRolePK> {
	List<AccountRole> listByAccount(String account);
}
