package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Level;
import tw.com.dsc.domain.ProductSeries;
import tw.com.dsc.domain.Project;
import tw.com.dsc.domain.Source;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.Technology;
import tw.com.dsc.domain.support.BetweenCondition;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.InCondition;
import tw.com.dsc.domain.support.LikeCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.OrCondition;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.DateUtils;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.Preparable;

@Component("advSearchArticleAction")
@Scope("prototype")
public class AdvSearchArticleAction extends BaseAction  implements Preparable {

	private static final long serialVersionUID = -772891469904917298L;
	private static final Logger logger = LoggerFactory.getLogger(AdvSearchArticleAction.class);
	private Map<String, Object> request;
	private Article example;
	private String articleId;
	private Long oid;
	private Article article;

	private String message;
	
	private List<Language> languages;
	private List<ArticleLog> articleLogs;
	private List<Technology> technologies;
	private List<Project> projects;
	private List<ProductSeries> products;
	private List<Account> accounts;
	private List<Group> groups;
	private List<Article> articles;
	
	@Autowired
	private LanguageService languageService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private SystemService systemService;
	
	private String languageOid;
	private Source sourceType;
	private String projectCode;
	private Boolean news;
	private ArticleType type;
	private String dateType; //entryDate, lastUpdate, publishDate
	private Date beginDate;
	private Date endDate;
	private String apType; //publishedType, statusType
	private Boolean published;
	private Status status;
	private String agentSearchType; //group, agent, self
	private String group;
	private String account;
	private Level[] levels;
	private String technology;
	private String product;
	private String firmware;
	private OperationEnum viewsType;
	private Integer hitCount;
	private OperationEnum ratingType;
	private Float avgRate;
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
//		articles= new Page<Article>(example);
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String index() {
		User op = ThreadLocalHolder.getOperator();
		this.languages = this.languageService.listAll();
		this.technologies = this.systemService.listAllTech();
		this.projects = this.systemService.listAllProject();
		if (op.isL3()) {
			this.products = this.systemService.listAllSeries();
		} else {
			this.products = this.systemService.listSeries(op.getCurrentUserRole().getBranchCode());
		}
		this.languages = this.languageService.listAll();
		example.setLanguage(new Language(op.getDefaultLanguageOid(), null));
//		articles = this.articleService.searchFaqArticlesPage(articles);
		
		this.technologies = this.systemService.listAllTech();
		this.projects = this.systemService.listAllProject();
		this.accounts = this.systemService.findTeamAccounts();
		this.groups = this.systemService.findGroups();
		if (op.isL3()) {
			this.products = this.systemService.listAllSeries();
		} else {
			this.products = this.systemService.listSeries(op.getCurrentUserRole().getBranchCode());
		}
		
		return "index";
	}

	public String search() {
		User op = ThreadLocalHolder.getOperator();
		example = new Article();
		List<Condition> conds = new ArrayList<Condition>();
		/*
		 * private String languageOid;
	private Source sourceType;
	private String projectCode;
	private Boolean news;
	private ArticleType type;
	private String dateType; //entryDate, updateDate, publishDate
	private String apType; //publishedType, statusType
	private Boolean published;
	private Status status;
	private String agentSearchType; //group, agent, self
	private Level level;
	private String technology;
	private String product;
	private String firmware;
	private OperationEnum viewsType;
	private Integer hitCount;
	private OperationEnum ratingType;
	private Float avgRate;
		 * 
		 */
		logger.info(this.toString());
		
		if (StringUtils.isNotEmpty(this.languageOid)) {
			example.setLanguage(new Language(languageOid, null));
		}
		
		if (null != sourceType) {
			example.setSource(sourceType);
			if (Source.Project == sourceType) {
				example.setProjectCode(projectCode);
			}
		}
		if (null != news) {
			example.setNews(news);
		}
		if (null != type) {
			example.setType(type);
		}
		
		if (StringUtils.isNotEmpty(dateType)) {
			beginDate = DateUtils.begin(beginDate);
			endDate = DateUtils.end(endDate);
			conds.add(new BetweenCondition(dateType, beginDate, endDate));
		}
		
		if (StringUtils.isNotEmpty(apType)) {
			if ("publishedType".equals(apType)) {
				if (!published) {
					conds.add(new SimpleCondition("status", Status.Published, OperationEnum.NE));
				} else {
					conds.add(new SimpleCondition("status", Status.Published, OperationEnum.EQ));
				}
			} else if ("statusType".equals(apType)) {
				if (null != status) {
					example.setStatus(status);
				}
			}
		}
		
		if (StringUtils.isNotEmpty(agentSearchType)) {
			if ("self".equals(agentSearchType)) {
				example.setEntryUser(op.getAccount());
			} else if ("group".equals(agentSearchType)) {
				example.setUserGroup(group);
			} else if ("agent".equals(agentSearchType)) {
				example.setEntryUser(account);
			}
		}
		
		if (null != this.levels) {
			conds.add(new InCondition("level", this.levels));
		}
		
		if (StringUtils.isNotEmpty(this.technology)) {
			conds.add(parseString(this.technology, "technology"));
		}
		
		if (StringUtils.isNotEmpty(this.product)) {
			conds.add(parseString(this.product, "product"));
		}
		
		if (StringUtils.isNotEmpty(this.firmware)) {
			example.setFirmware(firmware);
		}
		
		if (null != this.viewsType && null != hitCount) {
			conds.add(new SimpleCondition("hitCount", hitCount, viewsType));
		}
		
		if (null != this.ratingType && null != avgRate) {
			conds.add(new SimpleCondition("avgRate", avgRate, ratingType));
		}
		this.articles = this.articleService.listByExample(example, conds, LikeMode.ANYWHERE, new String[] {"oid"}, null);

		return "list";
	}
	
	private OrCondition parseString(String source, String field) {
		String[] split = source.split(",");
		LikeCondition[] likes = new LikeCondition[split.length];
		for (int i=0; i < split.length; i++) {
			likes[i] = new LikeCondition(field, "%"+split[i]+"%");
		}
		return new OrCondition(likes);
	}
	
	
	public List<ProductSeries> getSeries() {
		return systemService.listAllSeries();
	}
	
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

	public List<Language> getUsedLanguage() {
		return usedLanguage;
	}

	public void setUsedLanguage(List<Language> usedLanguage) {
		this.usedLanguage = usedLanguage;
	}

	public List<ArticleLog> getArticleLogs() {
		return articleLogs;
	}

	public void setArticleLogs(List<ArticleLog> articleLogs) {
		this.articleLogs = articleLogs;
	}

	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<ProductSeries> getProducts() {
		return products;
	}

	public void setProducts(List<ProductSeries> products) {
		this.products = products;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/********************************************/
	/** Form Parameter **/
	/********************************************/
	
	public String getLanguageOid() {
		return languageOid;
	}

	public void setLanguageOid(String languageOid) {
		this.languageOid = languageOid;
	}

	public Source getSourceType() {
		return sourceType;
	}

	public void setSourceType(Source sourceType) {
		this.sourceType = sourceType;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Boolean getNews() {
		return news;
	}

	public void setNews(Boolean news) {
		this.news = news;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getApType() {
		return apType;
	}

	public void setApType(String apType) {
		this.apType = apType;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAgentSearchType() {
		return agentSearchType;
	}

	public void setAgentSearchType(String agentSearchType) {
		this.agentSearchType = agentSearchType;
	}

	public Level[] getLevels() {
		return levels;
	}

	public void setLevels(Level[] levels) {
		this.levels = levels;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public OperationEnum getViewsType() {
		return viewsType;
	}

	public void setViewsType(OperationEnum viewsType) {
		this.viewsType = viewsType;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public OperationEnum getRatingType() {
		return ratingType;
	}

	public void setRatingType(OperationEnum ratingType) {
		this.ratingType = ratingType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Float getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Float avgRate) {
		this.avgRate = avgRate;
	}

	@Override
	public String toString() {
		return "AdvSearchArticleAction [languageOid=" + languageOid
				+ ", sourceType=" + sourceType + ", projectCode=" + projectCode
				+ ", news=" + news + ", type=" + type + ", dateType="
				+ dateType + ", beginDate=" + beginDate + ", endDate="
				+ endDate + ", apType=" + apType + ", published=" + published
				+ ", status=" + status + ", agentSearchType=" + agentSearchType
				+ ", group=" + group + ", account=" + account + ", levels="
				+ Arrays.toString(levels) + ", technology=" + technology
				+ ", product=" + product + ", firmware=" + firmware
				+ ", viewsType=" + viewsType + ", hitCount=" + hitCount
				+ ", ratingType=" + ratingType + ", avgRate=" + avgRate + "]";
	}

	
}
