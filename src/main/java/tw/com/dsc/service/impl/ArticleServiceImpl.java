package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.domain.Article;
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
	
	@Override
	public ArticleDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

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
		
		return this.dao.listByPage(page);
	}
	
	public void createArticle(Article article) {
		
		
	}
}
