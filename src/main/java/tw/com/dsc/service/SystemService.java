package tw.com.dsc.service;

import java.util.List;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;

public interface SystemService {
	List<Series> listAllSeries();
	ErrorType login(final User user);
	List<Account> findGroupLeaders(Article article);
	List<Account> findGroupAdmins(Article article);
	Account findAccountByOid(String oid);
}
