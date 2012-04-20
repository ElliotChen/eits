package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.ArticleLog;

@Repository("articleLogDao")
public class ArticleLogDaoImpl extends AbstractBaseDao<ArticleLog, Long> implements ArticleLogDao {
	private static final Logger logger = LoggerFactory.getLogger(ArticleLogDaoImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}

}
