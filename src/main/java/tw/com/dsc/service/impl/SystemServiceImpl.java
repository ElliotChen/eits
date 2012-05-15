package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.dao.AccountRoleDao;
import tw.com.dsc.dao.GroupDao;
import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.Model;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;
import tw.com.dsc.util.DateUtils;
import tw.com.dsc.util.SystemUtils;

@Service("systemService")
@Transactional(readOnly=true)
public class SystemServiceImpl implements SystemService {
	private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	
	private static List<Series> series;
	static {
		series = new ArrayList<Series>();
		
		for (int i = 0; i < 100; i++) {
			Series s = new Series("Series"+i, "Series"+i);
			series.add(s);
			int start = (i * 10) + 1;
			for (int mi = start; mi < start + 10; mi++ ) {
				s.addModel(new Model("model"+mi, "model"+mi));
			}
		}
	}
	@Override
	public List<Series> listAllSeries() {
		return series;
	}

	public ErrorType login(final User user) {
		/* 1. Check account
		 * 2. Check password 
		 * 3. Check active date
		 */
		logger.debug("User[{}] try to login", user);
		//Check account
		Account account = accountDao.findByOid(user.getAccount());
		if (null == account) {
			logger.warn("Can't find Account[{}] from host[{}]", user.getAccount(), user.getIp());
			return ErrorType.NotFound;
		}
		logger.debug("Find Account[{}]", account);
		
		//check password
		String md5 = DigestUtils.md5Hex(user.getPassword());
		if (!md5.equals(account.getPassword())) {
			logger.warn("Incorrect password for Account[{}] from host[{}]", user.getAccount(), user.getIp());
			return ErrorType.Password;
		}
		
		//Check active date
		Date now = new Date();
		Date endDate = StringUtils.isNotEmpty(account.getEndDate())?DateUtils.pareseActiveDate(account.getEndDate()):null;
		Date startDate = StringUtils.isNotEmpty(account.getStartDate())?DateUtils.pareseActiveDate(account.getStartDate()):null;
		if (null == endDate || null == startDate) {
			logger.error("Account doesn't keep Active StartDay[{}] or EndDay[{}]", account.getStartDate(), account.getEndDate());
		} else {
			if ((null != endDate && !now.before(endDate)) || (null != startDate && !now.after(startDate))) {
				logger.warn("Account is a inactive account for StartDay[{}] and EndDay[{}]", account.getStartDate(), account.getEndDate());
				return ErrorType.Inactive;
			}
		}
		
		user.setName(account.getName());
		user.setMail(account.getEmail());
		
		SystemUtils.parseRole(this.accountRoleDao.listByAccount(user.getAccount()), user);
		SystemUtils.parseGroup(account.getGroups(), user);
		return null;
	}
	
	public List<Account> findGroupLeaders(Article article) {
		List<Account> result = new ArrayList<Account>();
		//Find Leader 
		String groupId = article.getUserGroup();
		String leaderGroupId = "";
		if (StringUtils.isEmpty(groupId)) {
			logger.error("System can't find leaders for an article[{}] with an empty group", article);
			return result;
		}
		
		if (AgentType.L3 == article.getAgentType()) {
			leaderGroupId = groupId+"_Leader";
		} else if (AgentType.L2 == article.getAgentType()) {
			leaderGroupId = groupId+"_Leader";
		} else {
			logger.error("AgentType[{}] doesn't have Group Leader.", article.getAgentType());
		}
		
		logger.debug("Look up accounts for Leader Group[{}]", leaderGroupId);
		
		Group leader = this.groupDao.findByOid(leaderGroupId);
		
		if (null != leader) {
			result.addAll(leader.getAccounts());
		} else {
			logger.error("Can't find Leader Group[{}] in system", leaderGroupId);
		}
		
		return result;
	}
	
	public List<Account> findGroupAdmins(Article article) {
		List<Account> result = new ArrayList<Account>();
		//Find Leader 
		String groupId = article.getUserGroup();
		String adminGroupId = "";
		if (StringUtils.isEmpty(groupId)) {
			logger.error("System can't find leaders for an article[{}] with an empty group", article);
			return result;
		}
		
		if (AgentType.L3 == article.getAgentType()) {
			adminGroupId = "L3_Admin";
		} else if (AgentType.L2 == article.getAgentType()) {
			int ind = groupId.lastIndexOf("_");
			String parentId = groupId.substring(0, ind);
			adminGroupId = parentId + "_Admin";
		} else {
			logger.error("AgentType[{}] doesn't have Group Admin.", article.getAgentType());
		}
		
		logger.debug("Look up accounts for Admin Group[{}]", adminGroupId);
		
		Group admin = this.groupDao.findByOid(adminGroupId);

		if (null != admin) {
			result.addAll(admin.getAccounts());
		} else {
			logger.error("Can't find Admin Group[{}] in system", adminGroupId);
		}
		
		return result;
	}
	
	public Account findAccountByOid(String oid) {
		return this.accountDao.findByOid(oid);
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public AccountRoleDao getAccountRoleDao() {
		return accountRoleDao;
	}

	public void setAccountRoleDao(AccountRoleDao accountRoleDao) {
		this.accountRoleDao = accountRoleDao;
	}
	
}
