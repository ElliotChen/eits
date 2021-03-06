package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.dao.AccountRoleDao;
import tw.com.dsc.dao.GroupDao;
import tw.com.dsc.dao.ProductSeriesDao;
import tw.com.dsc.dao.ProjectDao;
import tw.com.dsc.dao.TechnologyDao;
import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.domain.ProductSeries;
import tw.com.dsc.domain.Project;
import tw.com.dsc.domain.Technology;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.LikeCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.DateUtils;
import tw.com.dsc.util.EncryptUtils;
import tw.com.dsc.util.SystemUtils;
import tw.com.dsc.util.ThreadLocalHolder;

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
	@Autowired
	private TechnologyDao technologyDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ProductSeriesDao productSeriesDao;
	
	private String securityAuthentication = "simple";
    private String securityPrincipalDomain = "ZyXEL.com";
    @Value("${ad.url}")
    private String ldapUrl;
    @Value("${ad.skip}")
    private Boolean skipAd;
    @Value("${eits.url}")
    private String eitsUrl;
	@Override
	@Cacheable(value="series")
	public List<ProductSeries> listAllSeries() {
		List<ProductSeries> result = new ArrayList<ProductSeries>();
//		List<ProductSeries> series = this.productSeriesDao.listAllSeries();
		for (ProductSeries s : this.productSeriesDao.listAllSeries()) {
			List<ProductModel> models = this.listModels(s.getId());
			if (!models.isEmpty()) {
				s.getModels().addAll(models);
				result.add(s);
			}
			
		}
		return result;
	}
	
	@Override
	@Cacheable(value="model")
	public List<ProductModel> listAllModels() {
		return this.productSeriesDao.listAllModels();
	}
	
	@Override
	@Cacheable(value="series")
	public List<ProductSeries> listSeries(String branchCode) {
		if (StringUtils.isEmpty(branchCode)) {
			logger.error("List Series for Branch Code can't accept emtpy parameter. Please check login user data.");
			return new ArrayList<ProductSeries>();
		}
		List<ProductSeries> result = new ArrayList<ProductSeries>();
//		List<ProductSeries> series = this.productSeriesDao.listSeries(branchCode);
		for (ProductSeries s : this.productSeriesDao.listSeries(branchCode)) {
			List<ProductModel> models = this.listModels(s.getId(), branchCode);
			if (!models.isEmpty()) {
				s.getModels().addAll(models);
				result.add(s);
			}
			
		}
		return result;
	}

	@Override
	@Cacheable(value="model")
	public List<ProductModel> listModels(String seriesId) {
		if (StringUtils.isEmpty(seriesId)) {
			logger.error("List Models for Series Code can't accept emtpy parameter. Please check login user data.");
			return new ArrayList<ProductModel>();
		}
		return this.productSeriesDao.listModels(seriesId);
	}
	
	@Override
	@Cacheable(value="model")
	public List<ProductModel> listModels(String seriesId, String branchCode) {
		if (StringUtils.isEmpty(seriesId) || StringUtils.isEmpty(branchCode)) {
			logger.error("List Models for Branch Code can't accept emtpy parameter. Please check login user data.");
			return new ArrayList<ProductModel>();
		}
		return this.productSeriesDao.listModels(seriesId, branchCode);
	}
	
	@Override
	@Cacheable(value="technologies")
	public List<Technology> listAllTech() {
		List<Technology> techs = this.technologyDao.listAll();
		for (Technology tech : techs) {
			tech.getItems().size();
		}
		return techs;
	}
	
	@Override
	@Cacheable(value="projects")
	public List<Project> listAllProject() {
		return this.projectDao.listAll();
	}
	
	@Override
	public ErrorType adLogin(final User user) {
		if (this.skipAd) {
			return null;
		}
		String result = null;
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, this.securityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, user.getAccount() + "@" + this.securityPrincipalDomain);
        env.put(Context.SECURITY_CREDENTIALS, user.getPassword() == null ? "" : user.getPassword());
        try {
            //可登入成功, 代表存在AD server裡
        	logger.debug("=======================AD Login start");
            DirContext ctx = new InitialDirContext(env);
            SearchControls search = new SearchControls();
            search.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrList = {"mail"};
            search.setReturningAttributes(attrList);
            search.setCountLimit(0);
            search.setTimeLimit(0);
            NamingEnumeration results = ctx.search("dc=ZyXEL,dc=com", "(cn=" + user.getAccount() + "*)", search);
            while (result == null && results.hasMore()) {
            	logger.debug("=======================get results : " + results);
                SearchResult si = (SearchResult)results.next();
                logger.debug("=======================get SearchResult : " + si);
                //登入後, 取得mail
                logger.debug("=======================get Mail start : " + si.getAttributes());
                result = si.getAttributes().get("mail") == null ? null : (String)si.getAttributes().get("mail").get(0);
            }
            logger.debug("=======================AD Login Success\n mail = " + result);
            user.setAccount(result);
            return null;
        } catch (Exception e) {
        	logger.debug("=======================AD Login Fail : " + e.getMessage());
            return ErrorType.NotFound;
        }
	}
	
	public ErrorType login(final User user, boolean skipPassword) {
		/* 1. Check account
		 * 2. Check password 
		 * 3. Check active date
		 */
		logger.debug("User[{}] try to login", user);
		//Check account
		if (null == user || StringUtils.isEmpty(user.getAccount())) {
			return ErrorType.NotFound;
		}
		Account example = new Account();
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("id", user.getAccount(), OperationEnum.EQ, true));
		List<Account> accounts = accountDao.listByExample(example, conds, null, null, null);
//		Account account = accountDao.findByOid(user.getAccount());
		Account account = null;
		if (!accounts.isEmpty()) {
			account = accounts.get(0);
			user.setAccount(account.getId());
		}
		
		if (null == account) {
			logger.warn("Can't find Account[{}] from host[{}]", user.getAccount(), user.getIp());
			return ErrorType.NotFound;
		}
		logger.debug("Find Account[{}]", account);
		
		//check password
		String md5 = DigestUtils.md5Hex(user.getPassword());
		if (!skipPassword && !md5.equals(account.getPassword())) {
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
		user.setDefaultRoleId(account.getDefaultRoleId());
		
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
		/*
		if (AgentType.L3 == article.getAgentType()) {
			if (!"L3_Admin".equals(groupId)) {
				leaderGroupId = groupId+"_Leader";
			} else {
				leaderGroupId = groupId;
			}
		} else if (AgentType.L2 == article.getAgentType()) {
			leaderGroupId = groupId+"_Leader";
		} else {
			logger.error("AgentType[{}] doesn't have Group Leader.", article.getAgentType());
		}
		*/
		leaderGroupId = article.getLeaderGroupId();
		
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
	
	@Override
	public ProductSeries findBySeriesId(String seriesId) {
		return this.productSeriesDao.findBySeriesId(seriesId);
	}
	
	public List<ProductSeries> listSeriesByProjectCode(String projectGuid) {
		Project example = new Project();
//		List<Condition> conds = new ArrayList<Condition>();
//		conds.add(new SimpleCondition("oid", projectGuid, OperationEnum.EQ));
		Project project = this.projectDao.findByOid(projectGuid);
//		List<Project> projects = this.projectDao.listByExample(example, conds, LikeMode.NONE, null, null);
		if (null == project) {
			logger.warn("Can't find any projects for guid[{}]", projectGuid);
			return new ArrayList<ProductSeries>();
		}
		
		return this.productSeriesDao.listSeriesByProjectCode(project.getProjectCode());
	}
	
	@Override
	public ErrorType eitsLogin(final String token) {
		User user = ThreadLocalHolder.getOperator();
		logger.info("Eits Login Token [{}]", token);
		String base64 = "";
		try {
			base64 = EncryptUtils.decrypt(token);
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		Pattern eitsPattern = Pattern.compile("^(.*)____(.*)____(.*)$");
		
		Matcher matcher = eitsPattern.matcher(base64);
		if (!matcher.matches()) {
			logger.error("Incorrect token data [{}] From host[{}]", token, user.getIp());
			return ErrorType.TokenIncorrect;
		}
		
		String activeDate = matcher.group(1);
		String accountId = matcher.group(2);
		String host = matcher.group(3);
		
		logger.info("Parsing token result Date[{}], Account[{}], Host[{}]", new String[] {activeDate, accountId, host});
		
		Date date = DateUtils.pareseDateTime(activeDate);
		if (date.before(new Date())) {
			return ErrorType.TokenExpired;
		}
		
		Account example = new Account();
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("id", accountId, OperationEnum.EQ, true));
		List<Account> accounts = accountDao.listByExample(example, conds, null, null, null);
		Account account = null;

		if (!accounts.isEmpty()) {
			account = accounts.get(0);
			user.setAccount(account.getId());
		}
		
		if (null == account) {
			logger.warn("Can't find Account[{}] from host[{}]", accountId, user.getIp());
			return ErrorType.NotFound;
		}
		
		logger.debug("Find Account[{}]", account);
		user.setAccount(account.getId());
		user.setName(account.getName());
		user.setMail(account.getEmail());
		user.setDefaultRoleId(account.getDefaultRoleId());
		
		SystemUtils.parseRole(this.accountRoleDao.listByAccount(user.getAccount()), user);
		SystemUtils.parseGroup(account.getGroups(), user);
		return null;
		
	}
	@Override
	public void initEitsUrl(User user, HttpServletRequest request) {
		try {
			String token = EncryptUtils.encrypt(request, user.getAccount());
			user.setEitsUrl(eitsUrl+token);
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public List<Account> findTeamAccounts() {
		User user = ThreadLocalHolder.getOperator();
		if (user.isL3()) {
			return this.accountDao.findByL3();
		} else if (user.isL2()) {
			return this.accountDao.findByL2Branch(user.getCurrentUserRole().getBranchCode());
		} else {
			logger.warn("Unknown {} for findTeamAccounts", user);
			return new ArrayList<Account>();
		}
	}
	
	public List<Group> findGroups() {
		User user = ThreadLocalHolder.getOperator();
		Group example = new Group();
		List<Condition> conds = new ArrayList<Condition>();
		List<Group> tmps = null;
		List<Group> groups = new ArrayList<Group>();
		if (user.isL3()) {
			conds.add(new LikeCondition("id", "L3_%"));
			tmps = this.groupDao.listByExample(example, conds, LikeMode.START, null, null);
		} else if (user.isL2()) {
			conds.add(new LikeCondition("id", "L2_"+user.getCurrentUserRole().getBranchCode()+"%"));
			tmps = this.groupDao.listByExample(example, conds, LikeMode.START, null, null);
		} else {
			logger.warn("Unknown {} for findGroups", user);
			return new ArrayList<Group>();
		}
		Set<String> gname = new HashSet<String>();
		for (Group group : tmps) {
			String gidname = SystemUtils.parseGroupId(group.getId());
			logger.debug("Parsing Group Id [{}] as [{}]", group.getId(), gidname);
			if (StringUtils.isNotEmpty(gidname) && !gname.contains(gidname)) {
				gname.add(gidname);
				group.setTeamName(gidname);
				groups.add(group);
			}
		}
		
		return groups;
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

	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public Boolean getSkipAd() {
		return skipAd;
	}

	public void setSkipAd(Boolean skipAd) {
		this.skipAd = skipAd;
	}

	public String getEitsUrl() {
		return eitsUrl;
	}

	public void setEitsUrl(String eitsUrl) {
		this.eitsUrl = eitsUrl;
	}
	
}
