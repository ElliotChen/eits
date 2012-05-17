package tw.com.dsc.service;

import tw.com.dsc.domain.Language;

public interface LanguageService extends BaseDomainService<Language, String> {
	/**
	 * 
	 * @param language
	 * @return ture is duplicate.
	 */
	boolean checkDuplicate(Language language);
	
	Language findDefaultLanguage();
}
