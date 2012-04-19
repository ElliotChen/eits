package tw.com.dsc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;
import tw.com.dsc.service.LanguageService;

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
}
