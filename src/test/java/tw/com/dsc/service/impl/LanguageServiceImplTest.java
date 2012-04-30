package tw.com.dsc.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;

public class LanguageServiceImplTest {
	LanguageServiceImpl service = null;
	LanguageDao dao = null;
	
	@Before
	public void prepare() {
		service = new LanguageServiceImpl();
		dao = Mockito.mock(LanguageDao.class);
		
		service.setDao(dao);
	}
	@Test
	public void test() {
		Language lan = new Language();
		lan.setSystem(Boolean.TRUE);
		
		service.delete(lan);
		lan.setSystem(Boolean.FALSE);
		service.delete(lan);
	}

}
