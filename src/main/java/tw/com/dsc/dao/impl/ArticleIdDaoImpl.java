package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ArticleIdDao;
import tw.com.dsc.domain.ArticleId;

@Repository("articleIdDao")
public class ArticleIdDaoImpl extends AbstractBaseDao<ArticleId, String> implements ArticleIdDao {
	private static final Logger logger = LoggerFactory.getLogger(ArticleIdDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

	

}
