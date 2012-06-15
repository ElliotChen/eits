package tw.com.dsc.service;

import java.util.List;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.domain.ProductSeries;
import tw.com.dsc.domain.Project;
import tw.com.dsc.domain.Technology;
import tw.com.dsc.to.User;

public interface SystemService {
	List<ProductSeries> listAllSeries();
	List<ProductModel> listAllModels();
	ErrorType adLogin(final User user);
	ErrorType login(final User user, boolean skipPassword);
	ErrorType eitsLogin(final String token);
	
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
	List<Project> listAllProject();
	List<ProductSeries> listSeries(String branchCode);
	List<ProductModel> listModels(String seriesId);
	List<ProductModel> listModels(String seriesId, String branchCode);
	ProductSeries findBySeriesId(String seriesId);
	List<ProductSeries> listSeriesByProjectCode(String projectCode);
}
