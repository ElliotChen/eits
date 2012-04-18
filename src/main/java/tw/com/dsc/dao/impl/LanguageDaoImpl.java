package tw.com.dsc.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;

@Repository("languageDao")
public class LanguageDaoImpl extends AbstractBaseDao<Language, String> implements LanguageDao {

	@Override
	protected void postCreateCriteria(Criteria criteria, Language example, MatchMode matchMode) {
		/*
		if (StringUtils.isNotEmpty(example.getOid())) {
			criteria.add(Restrictions.eq("oid", example.getOid()));
		}
		*/
	}


}
