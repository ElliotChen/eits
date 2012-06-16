package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.domain.ProductSeries;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.InCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.JsonMsg;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.Preparable;

@Component("advSearchArticleAction")
@Scope("prototype")
public class AdvSearchArticleAction extends BaseAction implements Preparable, RequestAware {

	private static final long serialVersionUID = -772891469904917298L;
	private static final Logger logger = LoggerFactory.getLogger(AdvSearchArticleAction.class);
	private Map<String, Object> request;
	private Article example;
	private String articleId;
	private Long oid;
	private Article article;

	private String message;
	
	private Integer ratingNumber;
	private String suggestion;
	private JsonMsg jsonMsg;
	private Integer pageNo;
	
	private Boolean rated;
	
	private String exSeries;
	private String exModel;
	
	private List<Language> languages;
	private Page<Article> articles;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SystemService systemService;
	
	private List<ProductSeries> productSeries;
	private List<ProductModel> productModels;
	
	/**
	 * 已使用的language
	 */
	private List<Language> usedLanguage;
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
		articles= new Page<Article>(example);
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String index() {
		User op = ThreadLocalHolder.getOperator();
		this.languages = this.languageService.listAll();
		this.productSeries = this.systemService.listAllSeries();
		this.productModels = new ArrayList<ProductModel>();
		example.setLanguage(new Language(op.getDefaultLanguageOid(), null));
		articles = this.articleService.searchFaqArticlesPage(articles);
		return "index";
	}

	public String search() {
		User op = ThreadLocalHolder.getOperator();
		if (StringUtils.isNotEmpty(this.exSeries)) {
			ProductSeries ps = this.systemService.findBySeriesId(exSeries);
			if (null != ps) {
				if (StringUtils.isNotEmpty(this.exModel)) {
					this.example.setProduct(ps.getName() +"--"+this.exModel);
				} else {
					this.example.setProduct(ps.getName());
				}
			}
		} else if(StringUtils.isNotEmpty(this.exModel)) {
			this.example.setProduct(this.exModel);
		}
		if (null==this.example.getLanguage() || StringUtils.isEmpty(this.example.getLanguage().getOid())) {
			this.example.setLanguage(new Language(op.getDefaultLanguageOid(), null));
		}
		Article faq = new Article();
		faq.setProduct(this.example.getProduct());
		faq.setLanguage(this.example.getLanguage());
		faq.setKeywords(this.example.getKeywords());
		articles.setExample(faq);
		
		
		articles = this.articleService.searchFaqArticlesPage(articles);

		return "list";
	}

	
	public List<ProductSeries> getSeries() {
		return systemService.listAllSeries();
	}

	public String rating() {
		this.articleService.rate(article, ratingNumber);
		this.jsonMsg = new JsonMsg("Thanks for your rating.");
		return "rating";
	}
	
	public String suggest() {
		this.articleService.comment(article, suggestion);
		this.jsonMsg = new JsonMsg("Thanks for your suggestion.");
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
		return articles;
	}

	public void setFaqArticles(Page<Article> faqArticles) {
		this.articles = faqArticles;
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

	public List<ProductSeries> getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(List<ProductSeries> productSeries) {
		this.productSeries = productSeries;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Boolean getRated() {
		return rated;
	}

	public void setRated(Boolean rated) {
		this.rated = rated;
	}

	public String getExSeries() {
		return exSeries;
	}

	public void setExSeries(String exSeries) {
		this.exSeries = exSeries;
	}

	public String getExModel() {
		return exModel;
	}

	public void setExModel(String exModel) {
		this.exModel = exModel;
	}

	public List<ProductModel> getProductModels() {
		return productModels;
	}

	public void setProductModels(List<ProductModel> productModels) {
		this.productModels = productModels;
	}

	public List<Language> getUsedLanguage() {
		return usedLanguage;
	}

	public void setUsedLanguage(List<Language> usedLanguage) {
		this.usedLanguage = usedLanguage;
	}
	
	

}
