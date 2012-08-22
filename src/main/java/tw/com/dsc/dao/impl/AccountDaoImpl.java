package tw.com.dsc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
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

	public List<Account> findByL3() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(domainClass);
		criteria.createAlias("groups", "g").add(Restrictions.like("g.id", "L3", MatchMode.START)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	public List<Account> findByL2Branch(String branch) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(domainClass);
		criteria.createAlias("groups", "g").add(Restrictions.like("g.id", "L2_"+branch, MatchMode.START));
		return criteria.list();
	}
}
