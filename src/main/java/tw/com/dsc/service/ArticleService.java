package tw.com.dsc.service;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.support.Page;

public interface ArticleService extends BaseDomainService<Article, Long> {
	void createArticle(Article article);
	Page<Article> searchUnpublishedPage(Page<Article> page);
	Page<Article> searchDraftPage(Page<Article> page);
	Page<Article> searchExpiredPage(Page<Article> page);
}
