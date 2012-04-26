package tw.com.dsc.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.Language;

@Repository("articleDao")
public class ArticleDaoImpl extends AbstractBaseDao<Article, Long> implements ArticleDao {
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	protected void postCreateCriteria(Criteria criteria, Article example, MatchMode matchMode) {
		Criteria subCriteria = null;
		if (null != example.getLanguage()) {
			Language lan = example.getLanguage();
			subCriteria = criteria.createCriteria("language");
			if (StringUtils.isNotEmpty(lan.getOid())) {
				subCriteria.add(Restrictions.eq("oid", lan.getOid()));
			}
		}
		
		if (null != example.getArticleId()) {
			ArticleId ai = example.getArticleId();
			subCriteria = criteria.createCriteria("articleId");
			if (StringUtils.isNotEmpty(ai.getOid())) {
				subCriteria.add(Restrictions.eq("oid", ai.getOid()));
			}
		}
	}

	
}
