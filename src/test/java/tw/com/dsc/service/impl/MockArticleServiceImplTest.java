package tw.com.dsc.service.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.dao.ArticleDao;
import tw.com.dsc.dao.ArticleIdDao;
import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ExpireType;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.test.BaseTest;
import tw.com.dsc.util.ThreadLocalHolder;

public class MockArticleServiceImplTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImplTest.class);
	
	ArticleServiceImpl service = null;
	ArticleDao articleDao = null;
	ArticleLogDao articleLogDao = null;
	ArticleIdDao articleIdDao = null;
	
	@Before
	public void before() {
		service = new ArticleServiceImpl();
		articleDao = Mockito.mock(ArticleDao.class);
		articleLogDao = Mockito.mock(ArticleLogDao.class);
		articleIdDao = Mockito.mock(ArticleIdDao.class);
		
		service.setDao(articleDao);
		service.setArticleLogDao(articleLogDao);
		service.setArticleIdDao(articleIdDao);
	}
	@Test
	public void testSearchDraftPage() {
		ThreadLocalHolder.setUser(l3leader);
		Page<Article> page = new Page<Article>();
		
		Article example = new Article();
		page.setExample(example);
		
		service.searchDraftPage(page);
		logger.debug("Conditions:[{}]", page.getConditions());
		
	}
	
	@Test
	public void testDraftNewL2Article() {
		ThreadLocalHolder.setUser(l2agent);
		
		Article article = new Article();
		article.setArticleId(new ArticleId("123456"));
		service.draftNewArticle(article);
		Assert.assertEquals(Status.Draft, article.getStatus());
	}
	
	@Test
	public void testFinalNewL2Article() {
		ThreadLocalHolder.setUser(l2agent);
		
		Article article = new Article();
		article.setArticleId(new ArticleId("123456"));
		service.finalNewArticle(article);
		Assert.assertEquals(Status.WaitForApproving, article.getStatus());
	}
	
	@Test
	public void testPublishNewL2Article() {
		ThreadLocalHolder.setUser(l3leader);
		
		Article article = new Article();
		//Fail test
		service.publishNewL2Article(article);
		Assert.assertNull(article.getExpireDate());
		
		ThreadLocalHolder.setUser(l2leader);
		article = new Article();
		article.setArticleId(new ArticleId("123456"));
		article.setExpireType(ExpireType.M1);
		service.publishNewL2Article(article);
		Assert.assertNotNull(article.getExpireDate());
	}
	
	@Test
	public void testFinalL2Article() {
		
	}
}
