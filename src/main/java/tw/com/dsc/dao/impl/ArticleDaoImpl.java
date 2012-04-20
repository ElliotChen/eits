package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.domain.Article;

@Repository("articleDao")
public class ArticleDaoImpl extends AbstractBaseDao<Article, Long> implements ArticleDao {
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}

}
