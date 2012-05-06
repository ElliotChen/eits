package tw.com.dsc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tw.com.dsc.service.ArticleService;
import tw.com.dsc.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ArticleServiceImplTest extends BaseTest {
	@Autowired
	ArticleService articleService;
	
	@Test
	public void testExpire() {
		this.articleService.expire();
	}
}
