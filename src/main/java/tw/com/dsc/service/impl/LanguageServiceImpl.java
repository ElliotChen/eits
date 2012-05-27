package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.util.ThreadLocalHolder;

@Service("languageService")
@Transactional(readOnly=true)
public class LanguageServiceImpl extends AbstractDomainService<LanguageDao, Language, String> implements LanguageService {
	private static final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);
	@Autowired
	private LanguageDao dao;
	
	@Override
	public LanguageDao getDao() {
		return this.dao;
	}

	public void setDao(LanguageDao dao) {
		this.dao = dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}
	
	@Override
	@Transactional(readOnly=false)
	public void delete(Language language) {
		logger.info("User[{}] try to delete Lanaguage[{}]", ThreadLocalHolder.getOperator().getAccount(), language);
		if (language.getSystem()) {
			logger.warn("System Default Language can't not be deleted.");
			return;
		}
		
		this.dao.delete(language);
	}
	
	@Override
	public boolean checkDuplicateName(Language language) {
		Language example = new Language();
		List<Condition> conds = new ArrayList<Condition>();
		if (StringUtils.isNotEmpty(language.getOid())) {
			conds.add(new SimpleCondition("oid", language.getOid(), OperationEnum.NE));
		}
		conds.add(new SimpleCondition("name", language.getName(), OperationEnum.EQ));
		
		List<Language> list = this.dao.listByExample(example, conds , LikeMode.NONE, new String[0], new String[0]);
		logger.debug("check duplication Name for language[{}] and size is [{}]", language, list.size());
		return list.isEmpty();
	}
	
	@Override
	public boolean checkDuplicateOid(Language language) {
		Language example = new Language();
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("oid", language.getOid(), OperationEnum.EQ));
		
		List<Language> list = this.dao.listByExample(example, conds , LikeMode.NONE, new String[0], new String[0]);
		logger.debug("check duplication Oid for language[{}] and size is [{}]", language, list.size());
		return list.isEmpty();
	}
	
	@Override
	public Language findDefaultLanguage() {
		Language example = new Language();
		example.setSystem(Boolean.TRUE);
		
		List<Language> list = this.dao.listByExample(example);
		
		if (1 != list.size()) {
			logger.error("Please check system default language, there are [{}] default language in system", list.size());
		}
		
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<Language> listAll() {
		Language example  = new Language();
		return this.listByExample(example, null, LikeMode.NONE, null, new String[]{"system", "oid"});
	}
	
	
}
