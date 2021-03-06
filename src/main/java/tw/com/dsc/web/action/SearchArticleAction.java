package tw.com.dsc.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import tw.com.dsc.domain.Attachment;
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
import tw.com.dsc.service.AttachmentService;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.JsonMsg;
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
	private Integer pageNo;
	
	private Boolean rated;
	
	private String exSeries;
	private String exModel;
	
	private List<Language> languages;
	private Page<Article> faqArticles;
	private Page<Article> latestArticles;
	private List<Article> sameArticles;
	private Page<Article> publishedArticles;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private AttachmentService attachmentService;
	
	private List<ProductSeries> productSeries;
	private List<ProductModel> productModels;
	
	private Long attOid;
	private String contentType;
	private InputStream fileStream;
	private String fileName;
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
		faqArticles= new Page<Article>(example);
		latestArticles= new Page<Article>(example);
		publishedArticles = new Page<Article>(example);
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String index() {
		User op = ThreadLocalHolder.getOperator();
		//Language defaultLan = languageService.findDefaultLanguage();
		
		this.languages = this.languageService.listAll();
		this.productSeries = this.systemService.listAllSeries();
//		this.productModels = this.systemService.listAllModels();
		this.productModels = new ArrayList<ProductModel>();
		example.setLanguage(new Language(op.getDefaultLanguageOid(), null));
		faqArticles = this.articleService.searchFaqArticlesPage(faqArticles);
		latestArticles = this.articleService.searchLatestArticlesPage(latestArticles);
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
		faqArticles.setExample(faq);
		
		
		Article last = new Article();
		last.setProduct(this.example.getProduct());
		last.setLanguage(this.example.getLanguage());
		last.setKeywords(this.example.getKeywords());
		latestArticles.setExample(last);
		
		faqArticles = this.articleService.searchFaqArticlesPage(faqArticles);
		latestArticles = this.articleService.searchLatestArticlesPage(latestArticles);

		return "list";
	}
	
	public String detail() {
		if (StringUtils.isNotEmpty(this.articleId)) {
//			example.setArticleId(new ArticleId(articleId));
			Language dlan = this.languageService.findDefaultLanguage();
			List<Condition> conds = new ArrayList<Condition>();
			conds.add(new SimpleCondition("articleId.oid", articleId, OperationEnum.EQ));
			conds.add(new InCondition("status", new Object[] {Status.Published,Status.WaitForRepublish}));
			/*
			if (null != dlan) {
				conds.add(new SimpleCondition("language.oid", dlan.getOid(), OperationEnum.EQ));
			*/
			sameArticles = this.articleService.listByExample(example, conds, LikeMode.NONE, null, null);
			if (sameArticles.isEmpty()) {
				this.addActionError("No such article id : ["+this.articleId+"]");
				return "redirect";
			}
			this.article = sameArticles.get(0);
		} else {
			List<Condition> conds = new ArrayList<Condition>();
			conds.add(new InCondition("status", new Object[] {Status.Published,Status.WaitForRepublish}));
			example.setArticleId(this.article.getArticleId());
			sameArticles = this.articleService.listByExample(example, conds, LikeMode.NONE, null, null);
		}
		
		User op = ThreadLocalHolder.getOperator();
		if (article.getStatus() != Status.Published && article.getStatus() != Status.WaitForRepublish) {
			this.addActionError("This article is not public.");
			return "redirect";
		} else if (Level.L3CSO == article.getLevel() && AgentType.L3 != op.getAgentType()) {
			this.addActionError("You have no authorization for this article");
			return "redirect";
		} else if (Level.Partner == article.getLevel() && op.isGuest()) {
			this.addActionError("You have no authorization for this article");
			return "redirect";
		}
		
		this.articleService.addHitCount(article);
		this.usedLanguage = this.articleService.listUsedLanguage(article);
		
		this.rated = Boolean.FALSE;
		if (!op.isGuest()) {
			this.rated = this.articleService.checkRated(this.article.getOid(), op.getAccount());
		}
		return "detail";
	}
	public String viewDetail() {
		this.article = this.articleService.findByOid(oid);
		if (null == article) {
			this.addActionError("No such article id : ["+this.articleId+"]");
			return "redirect";
		}
 		User op = ThreadLocalHolder.getOperator();
		if (article.getStatus() != Status.Published && article.getStatus() != Status.WaitForRepublish) {
			this.addActionError("This article is not public.");
			return "redirect";
		} else if (Level.L3CSO == article.getLevel() && AgentType.L3 != op.getAgentType()) {
			this.addActionError("You have no authorization for this article");
			return "redirect";
		} else if (Level.Partner == article.getLevel() && op.isGuest()) {
			this.addActionError("You have no authorization for this article");
			return "redirect";
		}
		
		List<Condition> conds = new ArrayList<Condition>();
		conds.add(new SimpleCondition("articleId.oid", this.article.getArticleId().getOid(), OperationEnum.EQ));
		conds.add(new InCondition("status", new Object[] {Status.Published,Status.WaitForRepublish}));
		sameArticles = this.articleService.listByExample(example, conds, LikeMode.NONE, null, null);
		
		this.articleService.addHitCount(article);
		this.usedLanguage = this.articleService.listUsedLanguage(article);
		
		this.rated = Boolean.FALSE;
		if (!op.isGuest()) {
			this.rated = this.articleService.checkRated(this.article.getOid(), op.getAccount());
		}
		
		return "viewDetail";
	}
	public String faq() {
		if (null != pageNo) {
			faqArticles.setPageNo(pageNo);
		} else {
			faqArticles.setPageNo(1);
		}
		if (null==this.faqArticles.getExample().getLanguage() || StringUtils.isEmpty(this.faqArticles.getExample().getLanguage().getOid())) {
			this.faqArticles.getExample().setLanguage(new Language("EN", null));
		}
		faqArticles = this.articleService.searchFaqArticlesPage(faqArticles);
		return "faq";
	}
	
	public String latest() {
		if (null != pageNo) {
			latestArticles.setPageNo(pageNo);
		} else {
			latestArticles.setPageNo(1);
		}
		if (null==this.latestArticles.getExample().getLanguage() || StringUtils.isEmpty(this.latestArticles.getExample().getLanguage().getOid())) {
			this.latestArticles.getExample().setLanguage(new Language("EN", null));
		}
		latestArticles = this.articleService.searchLatestArticlesPage(latestArticles);
		return "latest";
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
	
	public String publishedIndex() {
		publishedArticles = this.articleService.searchL3LatestPublishedPage(publishedArticles);
		return "publishedIndex";
	}
	
	public String latestL3Published() {
		if (null != pageNo) {
			publishedArticles.setPageNo(pageNo);
		} else {
			publishedArticles.setPageNo(1);
		}
		publishedArticles = this.articleService.searchL3LatestPublishedPage(publishedArticles);
		return "published";
	}
	
	public boolean getEditable() {
		boolean result = false;
		User op = ThreadLocalHolder.getOperator();
		if (op.isL3Admin() || op.getAccount().equals(article.getEntryUser())) {
			result = true;
		} else {
			for (String group : op.getAvailableGroups()) {
				if (group.equals(article.getUserGroup())) {
					result =true;
					break;
				}
			}
		}
		
		
		return result;
	}
	
	public String viewBlob() {
		if (null == this.attOid) {
			return null;
		}
		Attachment attach = attachmentService.findByOid(attOid);
		if (null == attach) {
			return null;
		}
		
		this.contentType = attach.getContentType();
		this.fileName = attach.getName();
		this.fileStream = new ByteArrayInputStream(attach.getContent());
		
		return "stream";
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

	public Page<Article> getPublishedArticles() {
		return publishedArticles;
	}

	public void setPublishedArticles(Page<Article> publishedArticles) {
		this.publishedArticles = publishedArticles;
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

	public Long getAttOid() {
		return attOid;
	}

	public void setAttOid(Long attOid) {
		this.attOid = attOid;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
