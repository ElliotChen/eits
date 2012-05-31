package tw.com.dsc.service;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.support.Page;

public interface ArticleService extends BaseDomainService<Article, Long> {
	void draftNewArticle(Article article);
	void finalNewArticle(Article article);
	void publishNewL2Article(Article article);
	void publishNewL3Article(Article article);
	
	void finalArticle(Article article);
	void approve(Article article);
	void reject(Article article, String reason);
	void publish(Article article);
	void disable(Article article);
	void readyUpdate(Article article);
	void readyPublish(Article article);
	void expire();
	void archive(Article article);
	
	void rate(Article article, int point);
	void comment(Article article, String comment);
	
	Page<Article> searchFaqArticlesPage(Page<Article> page);
	Page<Article> searchLatestArticlesPage(Page<Article> page);
	
	Page<Article> searchUnpublishedPage(Page<Article> page);
	Page<Article> searchDraftPage(Page<Article> page);
	Page<Article> searchExpiredPage(Page<Article> page);
	
	void addHitCount(Article article);
	
	String getNextArticleId();
}
