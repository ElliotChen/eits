package tw.com.dsc.dao;

import java.util.List;

import tw.com.dsc.domain.Account;

public interface AccountDao extends BaseDao<Account, String> {
	List<Account> findByL3();
	List<Account> findByL2Branch(String branch);
}
