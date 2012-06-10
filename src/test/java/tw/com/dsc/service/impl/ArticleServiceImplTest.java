package tw.com.dsc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.support.Page;
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
	
	@Test
	public void testFaq() {
		Page<Article> page = new Page<Article>();
		Article example = new Article();
		page.setExample(example);
		example.setKeywords("123");
		this.articleService.searchFaqArticlesPage(page);
	}
}
