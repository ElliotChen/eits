package tw.com.dsc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.support.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
public class LanguageServiceTest {
	@Autowired
	private LanguageService service;
	
	@Test
	@Rollback(false)
	public void testCreate() {
		Language lan = new Language();
		lan.setOid("DE");
		lan.setName("DEX");
		
		this.service.create(lan);
	}
	
	@Test
	public void testFindByPage() {
		Page<Language> page = new Page<Language>(new Language());
		this.service.listByPage(page);
		System.out.println(page.getResult().size());
	}
}
