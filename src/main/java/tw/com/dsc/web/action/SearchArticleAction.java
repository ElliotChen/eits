package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.JsonMsg;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.Preparable;

@Component("searchArticleAction")
@Scope("prototype")
public class SearchArticleAction extends BaseAction implements Preparable, RequestAware {

	private static final long serialVersionUID = -2112640721474475257L;
	private static final Logger logger = LoggerFactory.getLogger(SearchArticleAction.class);
	private Map<String, Object> request;
	private Article example;
	private String articleId;
	private Long oid;
	private Article article;

	private String message;
	
	private Integer ratingNumber;
	private String suggestion;
	private JsonMsg jsonMsg;
	
	private List<Language> languages;
	private Page<Article> faqArticles;
	private Page<Article> latestArticles;
	private List<Article> sameArticles;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SystemService systemService;
	@Override
	public void prepare() throws Exception {
		if (null != oid) {
			this.article = this.articleService.findByOid(oid);
		}
		
		if (null == this.article) {
			article = new Article();
		}
		example = new Article();
		example.setArticleId(new ArticleId());
		example.setLanguage(new Language());
		faqArticles= new Page<Article>(example);
		latestArticles= new Page<Article>(example);
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String index() {
		this.languages = this.languageService.listAll();
		return "index";
	}

	public String search() {
		faqArticles = this.articleService.searchFaqArticlesPage(faqArticles);
		latestArticles = this.articleService.searchLatestArticlesPage(latestArticles);

		return "list";
	}

	public String detail() {
		example.setArticleId(this.article.getArticleId());
		
		sameArticles = this.articleService.listByExample(example);
		
		this.articleService.addHitCount(article);
		return "detail";
	}
	
	public String faq() {
		faqArticles = this.articleService.searchFaqArticlesPage(faqArticles);
		return "faq";
	}
	
	public String latest() {
		latestArticles = this.articleService.searchLatestArticlesPage(latestArticles);
		return "latest";
	}
	
	public List<Series> getSeries() {
		return systemService.listAllSeries();
	}

	public String rating() {
		this.articleService.rate(article, ratingNumber);
		this.jsonMsg = new JsonMsg("Thanks for your rating for articleId");
		return "rating";
	}
	
	public String suggest() {
		this.articleService.comment(article, suggestion);
		this.jsonMsg = new JsonMsg("Thanks for your suggestion, ["+this.suggestion+"]");
		return "rating";
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Article getExample() {
		return example;
	}

	public void setExample(Article example) {
		this.example = example;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public Page<Article> getFaqArticles() {
		return faqArticles;
	}

	public void setFaqArticles(Page<Article> faqArticles) {
		this.faqArticles = faqArticles;
	}

	public Page<Article> getLatestArticles() {
		return latestArticles;
	}

	public void setLatestArticles(Page<Article> latestArticles) {
		this.latestArticles = latestArticles;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public List<Article> getSameArticles() {
		return sameArticles;
	}

	public void setSameArticles(List<Article> sameArticles) {
		this.sameArticles = sameArticles;
	}

	public Integer getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(Integer ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public JsonMsg getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(JsonMsg jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	
}
