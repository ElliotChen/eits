package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.domain.Role;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.Model;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;
import tw.com.dsc.util.DateUtils;

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
	
	public static final Pattern L3_LEADER = Pattern.compile("^(L3_.*)_Leader$");
	public static final Pattern L3_AGENT = Pattern.compile("^(L3_.*)_Team$");
	public static final Pattern L2_ADMIN = Pattern.compile("^(L2_.*)_Admin$");
	public static final Pattern L2_LEADER = Pattern.compile("^(L2_.*)_Leader$");
	public static final Pattern L2_AGENT = Pattern.compile("^(L2_.*)_Team$");
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
		
		parseGroup(u.getGroups(), user);
		return null;
	}
	
	public void parseGroup(List<Group> groups, User user) {

		String gid = "";
		String gname = "";
		for (Group group : groups) {
			gid = group.getId();
			logger.debug("Check Group[{}] for Role", gid);
			if ("L3_Admin".equals(gid)) {
				logger.debug("Group[{}] is a L3_Admin Group", gid);
				user.setL3admin(true);
				user.getRoles().add(Role.L3Admin);
				continue;
			} 
			
			Matcher matcher = L3_LEADER.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L3_Leader for [{}]", gid, gname);
				user.setL3leader(true);
				user.getL3LeaderGroups().add(gname);
				user.getRoles().add(Role.L3Leader);
				continue;
			}
			
			matcher = L3_AGENT.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L3_Agent for [{}]", gid, gname);
				user.setL3user(true);
				user.getL3AgentGroups().add(gname);
				user.getRoles().add(Role.L3Agent);
				continue;
			}
			
			matcher = L2_ADMIN.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Admin for [{}]", gid, gname);
				user.setL2admin(true);
				user.getL2AdminGroups().add(gname);
				user.getRoles().add(Role.L2Admin);
				continue;
			}
			
			matcher = L2_LEADER.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Leader for [{}]", gid, gname);
				user.setL2leader(true);
				user.getL2LeaderGroups().add(gname);
				user.getRoles().add(Role.L2Leader);
				continue;
			}
			
			matcher = L2_AGENT.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Agent for [{}]", gid, gname);
				user.setL2user(true);
				user.getL2AgentGroups().add(gname);
				user.getRoles().add(Role.L2Agent);
				continue;
			}
			
			logger.debug("Group[{}] is not a valid Group for eITS", gid);
		}
	}
}
