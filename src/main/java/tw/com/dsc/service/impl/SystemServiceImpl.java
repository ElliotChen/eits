package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		logger.debug("User[{}] try to login", user);
		Account u = accountDao.findByOid(user.getAccount());
		if (null == u) {
			return ErrorType.NotFound;
		}
		logger.debug("Find Account[{}]", u);
		
		Date now = new Date();
		Date endDate = StringUtils.isNotEmpty(u.getEndDate())?DateUtils.pareseActiveDate(u.getEndDate()):null;
		Date startDate = StringUtils.isNotEmpty(u.getStartDate())?DateUtils.pareseActiveDate(u.getStartDate()):null;
		if (null == endDate || null == startDate) {
			logger.error("Account doesn't keep Active StartDay[{}] or EndDay[{}]", u.getStartDate(), u.getEndDate());
		} else {
			if ((null != endDate && !now.before(endDate)) || (null != startDate && !now.after(startDate))) {
				logger.error("Account is a inactive account for StartDay[{}] and EndDay[{}]", u.getStartDate(), u.getEndDate());
				return ErrorType.Inactive;
			}
		}
		
		user.setName(u.getName());
		user.setMail(u.getEmail());
		
		SystemUtils.parseRole(this.accountRoleDao.listByAccount(user.getAccount()), user);
		SystemUtils.parseGroup(u.getGroups(), user);
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
			logger.error("");
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
			logger.error("");
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
}
