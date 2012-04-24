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
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.ExpireType;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.InCondition;
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
		this.newArticle(article, Status.Draft);
	}
	
	@Transactional(readOnly=false)
	public void finalNewArticle(Article article) {
		this.newArticle(article, Status.WaitForApproving);
	}
	
	@Transactional(readOnly=false)
	public void publishNewArticle(Article article) {
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
		article.setEntryDate(new Date());
		article.setHitCount(0);
		article.setStatus(status);
		
		//3.Create Article
		this.dao.create(article);
		
		//4.Log this action
		if (Status.Draft == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft", op.getIp()));
		} else if (Status.WaitForApproving == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Final as new", op.getIp()));
		} else if (Status.Published == status) {
			this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Create Draft and Publish as public", op.getIp()));
		}
	}
	
	@Transactional(readOnly=false)
	public void approve(Article article) {
		User op = ThreadLocalHolder.getOperator();
		
		article.setUpdateDate(new Date());
		article.setStatus(Status.WaitForProofRead);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Approve, op.getAccount(), "Approve", op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void reject(Article article, String reason) {
		User op = ThreadLocalHolder.getOperator();
		
		article.setUpdateDate(new Date());
		article.setStatus(Status.Draft);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Reject, op.getAccount(), "Reason:" + reason, op.getIp()));
	}
	
	@Transactional(readOnly=false)
	public void publish(Article article) {
		User op = ThreadLocalHolder.getOperator();
		
		Calendar cal = Calendar.getInstance();
		article.setPublishDate(new Date());
		if (null == article.getExpireType()) {
			logger.warn("Publish an article without expire type!");
			article.setExpireType(ExpireType.M1);
		}
		cal.add(Calendar.MONTH, article.getExpireType().getMonth());
		article.setExpireDate(cal.getTime());
		article.setUpdateDate(new Date());
		article.setStatus(Status.Published);
		
		this.dao.saveOrUpdate(article);
		
		this.articleLogDao.create(new ArticleLog(article.getOid(), ActionType.Publish, op.getAccount(), "Publish", op.getIp()));
	}
	
	public void rate(Article article, int point) {
		
	}
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
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		return this.dao.listByPage(page);
	}
	
	public Page<Article> searchUnpublishedPage(Page<Article> page) {
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
		conds.add(new SimpleCondition("status", Status.Published, OperationEnum.EQ));
//		conds.add(new InCondition("status", new Object[] {Status.ReadyPublished}));
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
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
		
		List<Condition> conds = page.getConditions();
		/*
		if (null != page.getConditions()) {
			for (Condition cond : page.getConditions()) {
				conds.add(cond);
			}
		}
		*/
		conds.add(new SimpleCondition("status", Status.Draft, OperationEnum.EQ));
//		conds.add(new InCondition("status", new Object[] {Status.ReadyPublished}));
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		return this.dao.listByPage(page);
	}
	
	public Page<Article> searchExpiredPage(Page<Article> page) {
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
		conds.add(new SimpleCondition("status", Status.WaitForRepublish, OperationEnum.EQ));
//		conds.add(new InCondition("status", new Object[] {Status.ReadyPublished}));
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		
		return this.dao.listByPage(page);
	}

}
