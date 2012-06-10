package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.dao.ArticleIdDao;
import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.dao.StatisticsDataDao;
import tw.com.dsc.domain.ActionType;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.ExpireType;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.StatisticsData;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.InCondition;
import tw.com.dsc.domain.support.LikeCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.OrCondition;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.MailService;
import tw.com.dsc.to.ExportInfo;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

@Service("articleService")
@Transactional(readOnly=true)
public class ArticleServiceImpl extends AbstractDomainService<ArticleDao, Article, Long>
		implements ArticleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleDao dao;
	
	@Autowired
	private ArticleIdDao articleIdDao;
	
	@Autowired
	private ArticleLogDao articleLogDao;
	
	@Autowired
	private StatisticsDataDao statisticsDataDao;
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DataFieldMaxValueIncrementer incrementer;
	@Override
	public ArticleDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}
	
	public ArticleIdDao getArticleIdDao() {
		return articleIdDao;
	}

	public void setArticleIdDao(ArticleIdDao articleIdDao) {
		this.articleIdDao = articleIdDao;
	}
	
	public ArticleLogDao getArticleLogDao() {
		return articleLogDao;
	}

	public void setArticleLogDao(ArticleLogDao articleLogDao) {
		this.articleLogDao = articleLogDao;
	}
	
	public void setDao(ArticleDao dao) {
		this.dao = dao;
	}
	
	public StatisticsDataDao getStatisticsDataDao() {
		return statisticsDataDao;
	}

	public void setStatisticsDataDao(StatisticsDataDao statisticsDataDao) {
		this.statisticsDataDao = statisticsDataDao;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Transactional(readOnly=false)
	public void draftNewArticle(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.Draft)) {
			logger.warn("Article[{}] can't be drafted for User[{}]", article, op.getAccount());
			return;
		}
		this.newArticle(article, Status.Draft);
	}
	
	@Transactional(readOnly=false)
	public void finalNewArticle(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.WaitForApproving)) {
			logger.warn("Article[{}] can't be approved for User[{}]", article, op.getAccount());
			return;
		}
		this.newArticle(article, Status.WaitForApproving);
	}
	
	@Transactional(readOnly=false)
	public void publishNewL2Article(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.Published)) {
			logger.warn("Article[{}] can't be published for User[{}]", article, op.getAccount());
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		article.setPublishDate(new Date());
		if (null == article.getExpireType()) {
			logger.warn("Publish an article without expire type!");
			article.setExpireType(ExpireType.M1);
		}
		cal.add(Calendar.MONTH, article.getExpireType().getMonth());
		article.setExpireDate(cal.getTime());
		this.newArticle(article, Status.Published);
	}
	
	@Transactional(readOnly=false)
	public void publishNewL3Article(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.WaitForProofRead)) {
			logger.warn("Article[{}] can't be proof read for User[{}]", article, op.getAccount());
			return;
		}
		this.newArticle(article, Status.WaitForProofRead);
	}
	
	protected void newArticle(Article article, Status status) {
		User op = ThreadLocalHolder.getOperator();
		//1.Check ArticleId
		ArticleId aid = article.getArticleId();
		if (null == aid) {
			logger.error("article.articleId can't be null! Please check your data.");
			return;
		}
		this.articleIdDao.saveOrUpdate(aid);
		
		//2.Update Entry User and Date
		article.setEntryUser(op.getAccount());
		article.setUserGroup(op.getGroup());
		article.setEntryDate(new Date());
		article.setHitCount(0);
		article.setAgentType(op.getAgentType());
		article.setStatus(status);
		
		//Rate
		article.setRate1(0);
		article.setRate2(0);
		article.setRate3(0);
		article.setRate4(0);
		article.setRate5(0);
		
		//3.Create Article
		this.dao.create(article);
		
		//4.Log this action
		if (Status.Draft == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft", op.getIp()));
		} else if (Status.WaitForApproving == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Save as Final", op.getIp()));
			this.mailService.approval(article);
		} else if (Status.Published == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Published", op.getIp()));
		} else if (Status.WaitForProofRead == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Waiting for Proof Read", op.getIp()));
		}
	}
	
	@Transactional(readOnly=false)
	public void finalArticle(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.WaitForApproving)) {
			logger.warn("Article[{}] can't be finalized for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.WaitForApproving);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Final, op.getAccount(), "Update Status to Waiting for Approving", op.getIp()));
		this.mailService.approval(article.getOid());
	}
	
	@Transactional(readOnly=false)
	public void approve(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.WaitForProofRead)) {
			logger.warn("Article[{}] can't be approved for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.WaitForProofRead);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Approve, op.getAccount(), "Approved", op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void reject(Article article, String reason) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.Draft)) {
			logger.warn("Article[{}] can't be rejected for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.Draft);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Reject, op.getAccount(), "Reason:" + reason, op.getIp()));
		this.mailService.reject(article.getOid());
	}
	
	@Transactional(readOnly=false)
	public void disable(Article article) {
		User op = ThreadLocalHolder.getOperator();
		
		article.setUpdateDate(new Date());
		article.setStatus(Status.Deleted);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Delete, op.getAccount(), "Deleted", op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void readyUpdate(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.ReadyToUpdate)) {
			logger.warn("Article[{}] can't be ReadyToUpdate for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.ReadyToUpdate);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Approve, op.getAccount(), "Ready to Updated", op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void readyPublish(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.ReadyToPublish)) {
			logger.warn("Article[{}] can't be ReadyToPublish for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.ReadyToPublish);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Final, op.getAccount(), "Ready to Published", op.getIp()));
		this.mailService.readyPublish(article.getOid());
	}
	
	@Transactional(readOnly=false)
	public void publish(Article article) {
		
		User op = ThreadLocalHolder.getOperator();
		AgentType at = article.getAgentType();
		logger.info("User[{}] try to publish a Article[{}]", op.getAccount(), article);

		if (AgentType.L2 == at) {
			if (Status.WaitForApproving == article.getStatus() || Status.Draft == article.getStatus()) {
				this.realPublish(article);
			} else if (Status.WaitForRepublish == article.getStatus()) {
				this.republish(article);
			} else {
				logger.error("Incorrect Status[{}] for Publish", article.getStatus());
			}
		} else if (AgentType.L3 == at) {
			if (Status.ReadyToPublish == article.getStatus()) {
				this.realPublish(article);
			} else if (Status.WaitForRepublish == article.getStatus()) {
				this.republish(article);
			} else if (Status.WaitForApproving == article.getStatus()) {
				this.approve(article);
			} else {
				logger.error("Incorrect Status[{}] for Publish", article.getStatus());
			}
		} else {
			logger.error("Incorrect AgentType[{}] for Publish!", article.getAgentType());
		}
		
		
	}
	
	/**
	 * @param article
	 */
	protected void realPublish(Article article) {
		User op = ThreadLocalHolder.getOperator();
		Calendar cal = Calendar.getInstance();
		article.setPublishDate(new Date());
		if (null == article.getExpireType()) {
			logger.warn("Publish an article without expire type!");
			article.setExpireType(ExpireType.M1);
		}
		cal.add(Calendar.MONTH, article.getExpireType().getMonth());
		article.setUpdateDate(new Date());
		article.setExpireDate(cal.getTime());
		article.setStatus(Status.Published);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Publish, op.getAccount(), "Publish", op.getIp()));

	}
	
	protected void republish(Article article) {
		User op = ThreadLocalHolder.getOperator();
		Calendar cal = Calendar.getInstance();
		article.setPublishDate(new Date());
		if (null == article.getExpireType()) {
			logger.warn("Publish an article without expire type!");
			article.setExpireType(ExpireType.M1);
		}
		cal.add(Calendar.MONTH, article.getExpireType().getMonth());
		article.setUpdateDate(new Date());
		article.setExpireDate(cal.getTime());
		article.setStatus(Status.Published);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Republish, op.getAccount(), "Republish", op.getIp()));

	}
	
	@Transactional(readOnly=false)
	public void rate(Article article, int point) {
		User op = ThreadLocalHolder.getOperator();
		logger.debug("User[{}] rate [{}] to Article[{}]", new Object[] {op, point, article.getOid()});
		ActionType at = ActionType.Rating1;
		switch(point) {
		case 1:
			at = ActionType.Rating1;
			article.setRate1(article.getRate1()+1);
			break;
		case 2:
			at = ActionType.Rating2;
			article.setRate2(article.getRate2()+1);
			break;
		case 3:
			at = ActionType.Rating3;
			article.setRate3(article.getRate3()+1);
			break;
		case 4:
			at = ActionType.Rating4;
			article.setRate4(article.getRate4()+1);
			break;
		case 5:
			at = ActionType.Rating5;
			article.setRate5(article.getRate5()+1);
			break;
		default:
			logger.error("Unknow rate number[{}] for Article[{}]", point, article.getOid());
		}
		this.dao.saveOrUpdate(article);
//		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Rating, op.getAccount(), String.valueOf(point), op.getIp()));
		this.statisticsDataDao.create(new StatisticsData(article.getOid(), at, op.getAccount(), op.getIp()));
	}
	@Transactional(readOnly=false)
	public void comment(Article article, String comment) {
		User op = ThreadLocalHolder.getOperator();
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Comment, op.getAccount(), "Comment:"+comment, op.getIp()));
		this.statisticsDataDao.create(new StatisticsData(article.getOid(), ActionType.Comment, op.getAccount(), op.getIp()));
	}
	
	public Page<Article> searchFaqArticlesPage(Page<Article> page) {
		Article example = page.getExample();
		User op = ThreadLocalHolder.getOperator();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		this.initFaqLastestSearch(page);
		page.setDescOrders(new String[] {"hitCount"});
		
		
		return this.dao.listByPage(page);
	}
	
	/**
	 * Active = true
	 * Status = LeaderPublished
	 * Level
	 * 	Public = public
	 * 	L2/Sub in {public or Partner}
	 * 	L3 in {public or L3 CSO}
	 * @param page
	 * @return
	 */
	public Page<Article> searchLatestArticlesPage(Page<Article> page) {
		Article example = page.getExample();
		User op = ThreadLocalHolder.getOperator();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		this.initFaqLastestSearch(page);
		page.setDescOrders(new String[] {"publishDate"});
		
		return this.dao.listByPage(page);
	}
	
	protected void initFaqLastestSearch(Page<Article> page) {
		Article example = page.getExample();
		User op = ThreadLocalHolder.getOperator();
		String keyword = example.getKeywords();
		
		List<Condition> conds = page.getConditions();
		conds.add(new InCondition("level", op.getAvailableLevels()));
		conds.add(new InCondition("status", new Status[] {Status.Published, Status.WaitForRepublish}));
		//example.setStatus(Status.Published);
		/*
		if (AgentType.L3 != op.getAgentType()) {
			example.setAgentType(AgentType.L2);
		}
		*/
		if (StringUtils.isNotEmpty(keyword)) {
			String likeValue = "%"+keyword+"%";
			Condition qcond = new LikeCondition("question", likeValue);
			Condition acond = new LikeCondition("answer", likeValue);
			Condition scond = new LikeCondition("scenario", likeValue);
			Condition stcond = new LikeCondition("step", likeValue);
			Condition vcond = new LikeCondition("verification", likeValue);
			Condition pmcond = new LikeCondition("problem", likeValue);
			Condition socond = new LikeCondition("solution", likeValue);
			Condition pecond = new LikeCondition("procedure", likeValue);
			Condition smcond = new LikeCondition("summary", likeValue);
			conds.add(new OrCondition(qcond, acond, scond, stcond, vcond, pmcond, socond, pecond, smcond));
			example.setKeywords(null);
		}
	}
	
	public Page<Article> searchUnpublishedPage(Page<Article> page) {
		User op = ThreadLocalHolder.getOperator();
		if (!op.isLeader()) {
			page.setTotalCount(0);
			return page;
		}
		
		Article example = page.getExample();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		List<Condition> conds = page.getConditions();
		/*
		if (null != page.getConditions()) {
			for (Condition cond : page.getConditions()) {
				conds.add(cond);
			}
		}
		*/
		String[] groups = op.getAvailableGroups();
		if (groups.length > 0) {
			conds.add(new InCondition("userGroup", groups));
		}
		conds.add(new InCondition("status", new Object[] {Status.WaitForApproving, Status.WaitForProofRead, Status.ReadyToUpdate, Status.ReadyToPublish}));
		conds.add(new InCondition("level", op.getAvailableLevels()));
		conds.add(new SimpleCondition("agentType", op.getAgentType(), OperationEnum.EQ));
		
		return this.dao.listByPage(page);
	}
	
	public Page<Article> searchDraftPage(Page<Article> page) {
		Article example = page.getExample();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		User op = ThreadLocalHolder.getOperator();
		List<Condition> conds = page.getConditions();
		
		conds.add(new InCondition("status", new Object[] {Status.Draft, Status.ReadyToUpdate}));
		conds.add(new InCondition("level", op.getAvailableLevels()));
		conds.add(new SimpleCondition("agentType", op.getAgentType(), OperationEnum.EQ));
		if (!op.isLeader()) {
			conds.add(new SimpleCondition("entryUser", op.getAccount(), OperationEnum.EQ));
		} else {
			String[] groups = op.getAvailableGroups();
			if (groups.length > 0) {
				conds.add(new InCondition("userGroup", groups));
			}
		}
		
		return this.dao.listByPage(page);
	}
	
	public Page<Article> searchExpiredPage(Page<Article> page) {
		User op = ThreadLocalHolder.getOperator();
		
		Article example = page.getExample();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		List<Condition> conds = page.getConditions();
		// 4/26 Glavine : 1. 當 KB expired 後 , 要出現在此 KB creater 的 Unpublished Article List 中
		if (!op.isLeader()) {
			conds.add(new SimpleCondition("entryUser", op.getAccount(), OperationEnum.EQ));
		} else {
			String[] groups = op.getAvailableGroups();
			if (groups.length > 0) {
				conds.add(new InCondition("userGroup", groups));
			}
		}
		conds.add(new SimpleCondition("status", Status.WaitForRepublish, OperationEnum.EQ));
		conds.add(new InCondition("level", op.getAvailableLevels()));
		conds.add(new SimpleCondition("agentType", op.getAgentType(), OperationEnum.EQ));
		
		return this.dao.listByPage(page);
	}
	
	public Page<Article> searchL3LatestPublishedPage(Page<Article> page) {
		User op = ThreadLocalHolder.getOperator();
		
		Article example = page.getExample();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		List<Condition> conds = page.getConditions();
		
		conds.add(new SimpleCondition("status", Status.Published, OperationEnum.EQ));
		conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner} ));
		conds.add(new SimpleCondition("agentType", AgentType.L3, OperationEnum.EQ));
		conds.add(new SimpleCondition("publishDate", cal.getTime(), OperationEnum.GE));
		page.setDescOrders(new String[] {"publishDate"});
		return this.dao.listByPage(page);
	}
	@Transactional(readOnly=false)
	public void addHitCount(Article article) {
		User op = ThreadLocalHolder.getOperator();
		article.setHitCount(article.getHitCount()+1);
		this.dao.saveOrUpdate(article);
//		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.View,  op.getAccount(), "View Detail.", op.getIp()));
		this.statisticsDataDao.create(new StatisticsData(article.getOid(), ActionType.View,  op.getAccount(), op.getIp()));
	}

	@Transactional(readOnly=false)
	public void expire() {
		logger.info("Execute KB expire task");
		User op = ThreadLocalHolder.getOperator();
		Article example = new Article();
		example.setStatus(Status.Published);
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("expireDate", new Date(), OperationEnum.LE));
		
		List<Article> list = this.dao.listByExample(example, conds, LikeMode.NONE, new String[0], new String[0]);
		
		for (Article art : list) {
			art.setStatus(Status.WaitForRepublish);
			art.setExpireDate(null);
			this.dao.saveOrUpdate(art);
			this.articleLogDao.create(new ArticleLog(art.getOid(), ActionType.Expired, "System", "Wait For Republish", op.getIp()));
			this.mailService.expired(art.getOid());
		}
		
		logger.info("Execute KB expire task finished, and expire [{}] article(s)", list.size());
	}
	
	@Transactional(readOnly=false)
	public void archive(Article article) {
		User op = ThreadLocalHolder.getOperator();
		if (!article.getAvailableStatus().contains(Status.Archived)) {
			logger.warn("Article[{}] can't be archived for User[{}]", article, op.getAccount());
			return;
		}
		article.setUpdateDate(new Date());
		article.setStatus(Status.Archived);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Archived, op.getAccount(), "Archived", op.getIp()));
		this.mailService.archived(article.getOid());
	}

	@Transactional(readOnly=false)
	public String getNextArticleId() {
		return this.incrementer.nextStringValue();
	}
	
	public DataFieldMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}
	
	
	public Boolean checkRated(Long articleOid, String account) {
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("articleOid", articleOid, OperationEnum.EQ));
		conds.add(new SimpleCondition("account", account, OperationEnum.EQ));
		conds.add(new InCondition("action", new Object[] {ActionType.Rating1, ActionType.Rating2, ActionType.Rating3, ActionType.Rating4, ActionType.Rating5}));
		List<StatisticsData> statis = this.statisticsDataDao.listByExample(new StatisticsData(), conds, LikeMode.NONE, null, null);
		
		return !statis.isEmpty();
	}
	
	@Override
	public List<ExportInfo> listProofReadArticles() {
		List<ExportInfo> infos = new ArrayList<ExportInfo>();
		
		Article example = new Article();
		example.setStatus(Status.WaitForProofRead);
		ExportInfo info = null;
		for (Article article : this.dao.listByExample(example, null, null, new String[] {"entryUser"}, null)) {
			if (null == info || !article.getEntryUser().equals(info.getAccount())) {
				info = new ExportInfo();
				info.setAccount(article.getEntryUser());
				infos.add(info);
			}
			info.getArticles().add(article);
		}
		return infos;
	}
	
	@Override
	public List<ExportInfo> listProofReadNews() {
		List<ExportInfo> infos = new ArrayList<ExportInfo>();
		
		Article example = new Article();
		example.setStatus(Status.WaitForProofRead);
		example.setNews(Boolean.TRUE);
		ExportInfo info = null;
		for (Article article : this.dao.listByExample(example, null, null, new String[] {"entryUser"}, null)) {
			if (null == info || !article.getEntryUser().equals(info.getAccount())) {
				info = new ExportInfo();
				info.setAccount(article.getEntryUser());
				infos.add(info);
			}
			info.getArticles().add(article);
		}
		return infos;
	}
	@Override
	public List<ExportInfo> listProofReadKB() {
		List<ExportInfo> infos = new ArrayList<ExportInfo>();
		
		Article example = new Article();
		example.setStatus(Status.WaitForProofRead);
		example.setNews(Boolean.FALSE);
		ExportInfo info = null;
		for (Article article : this.dao.listByExample(example, null, null, new String[] {"entryUser"}, null)) {
			if (null == info || !article.getEntryUser().equals(info.getAccount())) {
				info = new ExportInfo();
				info.setAccount(article.getEntryUser());
				infos.add(info);
			}
			info.getArticles().add(article);
		}
		return infos;
	}
	
	public List<Language> listUsedLanguage(Article article) {
		List<Language> usedLanguage = new ArrayList<Language>();
		
		Article example = new Article();
		example.setArticleId(article.getArticleId());
		
		List<Article> articles = this.dao.listByExample(example);
		
		for (Article art : articles) {
			usedLanguage.add(art.getLanguage());
		}
		
		return usedLanguage;
	}
}
