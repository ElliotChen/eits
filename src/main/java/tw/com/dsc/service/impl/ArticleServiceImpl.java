package tw.com.dsc.service.impl;

import java.util.ArrayList;
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
		
		example.setStatus(Status.LeaderPublished);
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		example.setActive(Boolean.TRUE);
		
		return this.dao.listByPage(page);
	}
	
	@Transactional(readOnly=false)
	public void createArticle(Article article) {
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
		article.setActive(Boolean.TRUE);
		
		//3.Create Article
		this.dao.create(article);
		
		//4.Log this action
		ArticleLog log = new ArticleLog(article.getOid(), ActionType.Create, op.getAccount(), "Created", op.getIp());
		this.articleLogDao.create(log);
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
		conds.add(new SimpleCondition("status", Status.ReadyPublished, OperationEnum.EQ));
//		conds.add(new InCondition("status", new Object[] {Status.ReadyPublished}));
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		example.setActive(Boolean.TRUE);
		
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
		
		example.setActive(Boolean.TRUE);
		
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
		conds.add(new SimpleCondition("status", Status.Disabled, OperationEnum.EQ));
//		conds.add(new InCondition("status", new Object[] {Status.ReadyPublished}));
		
		User op = ThreadLocalHolder.getOperator();
		if (op.isGuest()) {
			conds.add(new SimpleCondition("level", Level.Public, OperationEnum.EQ));
		} else if (op.isL2leader() || op.isL2user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.Partner}));
		} else if (op.isL3leader() || op.isL3user()) {
			conds.add(new InCondition("level", new Object[] {Level.Public, Level.L3CSO}));
		}
		
		example.setActive(Boolean.TRUE);
		
		return this.dao.listByPage(page);
	}

}
