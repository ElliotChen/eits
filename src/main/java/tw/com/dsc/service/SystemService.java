package tw.com.dsc.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Technology;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;

public interface SystemService {
	List<Series> listAllSeries();
	ErrorType login(final User user);
	
	/**
	 * Find Article's group leaders
	 * @param article
	 * @return
	 */
	List<Account> findGroupLeaders(Article article);
	
	/**
	 * Find Article's group administrators
	 * @param article
	 * @return
	 */
	List<Account> findGroupAdmins(Article article);
	
	/**
	 * Find Account By Oid
	 * @param oid
	 * @return
	 */
	Account findAccountByOid(String oid);
	
	List<Technology> listAllTech();
}
