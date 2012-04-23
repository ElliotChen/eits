package tw.com.dsc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.service.ArticleLogService;

public class ArticleLogServiceImpl extends AbstractDomainService<ArticleLogDao, ArticleLog, Long> implements ArticleLogService {
private static final Logger logger = LoggerFactory.getLogger(ArticleLogServiceImpl.class);
	
	@Autowired
	private ArticleLogDao dao;
	
	@Override
	public List<ArticleLog> findByArticleOid(Long articleOid) {
		return this.dao.listByExample(new ArticleLog(articleOid, null, null, null, null));
	}

	@Override
	public ArticleLogDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	

}
