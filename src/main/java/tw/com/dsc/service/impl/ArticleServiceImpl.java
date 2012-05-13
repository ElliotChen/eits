package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.dao.ArticleIdDao;
import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.ActionType;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.ExpireType;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.InCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.ArticleService;
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
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Final as new", op.getIp()));
		} else if (Status.Published == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Publish as public", op.getIp()));
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
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Approve, op.getAccount(), "Approve", op.getIp()));
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
	}
	
	@Transactional(readOnly=false)
	public void disable(Article article) {
		User op = ThreadLocalHolder.getOperator();
		
		article.setUpdateDate(new Date());
		article.setStatus(Status.Deleted);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Delete, op.getAccount(), "Delete", op.getIp()));
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
	}
	
	@Transactional(readOnly=false)
	public void publish(Article article) {
		
		User op = ThreadLocalHolder.getOperator();
		AgentType at = article.getAgentType();
		logger.info("User[{}] try to publish a Article[{}]", op.getAccount(), article);
		Calendar cal = Calendar.getInstance();
		article.setPublishDate(new Date());
		if (null == article.getExpireType()) {
			logger.warn("Publish an article without expire type!");
			article.setExpireType(ExpireType.M1);
		}
		cal.add(Calendar.MONTH, article.getExpireType().getMonth());
		article.setUpdateDate(new Date());
		
		if (AgentType.L2 == at) {
			article.setExpireDate(cal.getTime());
			article.setStatus(Status.Published);
		} else if (AgentType.L3 == at) {
			if (Status.WaitForApproving == article.getStatus()) {
				article.setStatus(Status.WaitForProofRead);
			} else if (Status.ReadyToPublish == article.getStatus() || Status.WaitForRepublish == article.getStatus()) {
				article.setStatus(Status.Published);
			} else {
				logger.error("Incorrect Status[{}] for Publish", article.getStatus());
			}
		} else {
			logger.error("Incorrect AgentType[{}] for Publish!", article.getAgentType());
		}
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Publish, op.getAccount(), "Publish", op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void ready(Article article) {
		
	}
	@Transactional(readOnly=false)
	public void rate(Article article, int point) {
		User op = ThreadLocalHolder.getOperator();
		logger.debug("User[{}] rate [{}] to Article[{}]", new Object[] {op, point, article.getOid()});
		switch(point) {
		case 1:
			article.setRate1(article.getRate1()+1);
			break;
		case 2:
			article.setRate2(article.getRate2()+1);
			break;
		case 3:
			article.setRate3(article.getRate3()+1);
			break;
		case 4:
			article.setRate4(article.getRate4()+1);
			break;
		case 5:
			article.setRate5(article.getRate5()+1);
			break;
		default:
			logger.error("Unknow rate number[{}] for Article[{}]", point, article.getOid());
		}
		this.dao.saveOrUpdate(article);
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Rating, op.getAccount(), String.valueOf(point), op.getIp()));
	}
	@Transactional(readOnly=false)
	public void comment(Article article, String comment) {
		User op = ThreadLocalHolder.getOperator();
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Comment, op.getAccount(), "Comment:"+comment, op.getIp()));
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
	public Page<Article> searchPublicArticlesPage(Page<Article> page) {
		Article example = page.getExample();
		
		if (null == example) {
			logger.warn("Page.example could not be null!");
			example = new Article();
			example.setOid(-1l);
			
			page.setExample(example);
		}
		
		List<Condition> conds = new ArrayList<Condition>();
		if (null != page.getConditions()) {
			for (Condition cond : page.getConditions()) {
				conds.add(cond);
			}
		}
		
		example.setStatus(Status.Published);
		page.setDescOrders(new String[] {"publishDate"});
		User op = ThreadLocalHolder.getOperator();
		conds.add(new InCondition("level", new Object[] {op.getAvailableLevels()}));
		
		return this.dao.listByPage(page);
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
	
	@Transactional(readOnly=false)
	public void addHitCount(Article article) {
		User op = ThreadLocalHolder.getOperator();
		article.setHitCount(article.getHitCount()+1);
		this.dao.saveOrUpdate(article);
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.View,  op.getAccount(), "View Detail.", op.getIp()));
	}

	@Transactional(readOnly=false)
	public void expire() {
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
			this.articleLogDao.create(new ArticleLog(art.getOid(), ActionType.Unpublish, op.getAccount(), "Wait For Republish", op.getIp()));
		}
	}
}
