package tw.com.dsc.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;

@Repository("languageDao")
public class LanguageDaoImpl extends AbstractBaseDao<Language, String> implements LanguageDao {
	private static final Logger logger = LoggerFactory.getLogger(LanguageDaoImpl.class);
	
	@Override
	protected void postCreateCriteria(Criteria criteria, Language example, MatchMode matchMode) {
		/*
		if (StringUtils.isNotEmpty(example.getOid())) {
			criteria.add(Restrictions.eq("oid", example.getOid()));
		}
		*/
	}

	@Override
	public Logger getLogger() {
		return logger;
	}


}
