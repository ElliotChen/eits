package tw.com.dsc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.service.ArticleLogService;

@Service("e")
@Transactional(readOnly=true)
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

	@Override
	public ArticleLog getLatestRejectReason(Long articleOid) {
		ArticleLog example = new ArticleLog();
		example.setArticleOid(articleOid);
		
		List<ArticleLog> list = this.dao.listByExample(example, null, LikeMode.NONE, new String[] {}, new String[] {"createdDate"});
		if (list.isEmpty()) {
			logger.warn("Can't find any reject reason for Article[{}]", articleOid);
		}
		
		return list.isEmpty()?null:list.get(0);
	}
}
