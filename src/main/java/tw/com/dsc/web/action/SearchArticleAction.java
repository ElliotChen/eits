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
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("searchArticleAction")
@Scope("prototype")
public class SearchArticleAction extends BaseAction implements Preparable, RequestAware {

	private static final long serialVersionUID = -2112640721474475257L;
	private static final Logger logger = LoggerFactory.getLogger(SearchArticleAction.class);
	private Map<String, Object> request;
	private Article example;
	private String articleId;
	private String oid;
	private Article article;

	private String message;

	private List<Language> languages;
	private ArrayList<Article> faqArticles;
	private ArrayList<Article> latestArticles;
	
	@Autowired
	private LanguageService languageService;
	@Override
	public void prepare() throws Exception {
		example = new Article();
		
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
		logger.error("Do Search");
		faqArticles = new ArrayList<Article>();

		latestArticles = new ArrayList<Article>();

		this.mockArticles(faqArticles);
		this.mockArticles(latestArticles);

		return "list";
	}

	public String detail() {
		article = new Article();
		if ("1".equals(this.oid) || "2".equals(this.oid)) {
			article.setOid(1L);
			article.setArticleId(new ArticleId("123456"));
			article.setSummary("Summary XYZ");
			article.setPublishDate(new Date());
			article.setLanguage(new Language("EN", "English"));
			article.setHitCount(120);

			article.setQuestion("How to restore and clear rom-d on P-663 in English");
			article.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
		} else {
			article.setOid(5L);
			article.setArticleId(new ArticleId("765432"));
			article.setSummary("Summary XYZ");
			article.setPublishDate(new Date());
			article.setLanguage(new Language("EN", "English"));
			article.setHitCount(120);

			article.setQuestion("如何回復與清除P-663的ROM ? Chinese");
			article.setAnswer("你知道這是中文就好！");
		}
		return "detail";
	}

	private void mockArticles(final ArrayList<Article> list) {
		Article a1 = new Article();
		a1.setOid(1L);
		a1.setArticleId(new ArticleId("123456"));
		a1.setSummary("All in!");
		a1.setPublishDate(new Date());
		a1.setHitCount(271);
		a1.setLanguage(new Language("EN", "English"));

		Article a2 = new Article();
		a2.setOid(2L);
		a2.setArticleId(new ArticleId("765432"));
		a2.setSummary("Don't do this!");
		a2.setPublishDate(new Date());
		a2.setHitCount(157);
		a2.setLanguage(new Language("EN", "English"));

		list.add(a1);
		list.add(a2);
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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

	public ArrayList<Article> getFaqArticles() {
		return faqArticles;
	}

	public void setFaqArticles(ArrayList<Article> faqArticles) {
		this.faqArticles = faqArticles;
	}

	public ArrayList<Article> getLatestArticles() {
		return latestArticles;
	}

	public void setLatestArticles(ArrayList<Article> latestArticles) {
		this.latestArticles = latestArticles;
	}
	
}
