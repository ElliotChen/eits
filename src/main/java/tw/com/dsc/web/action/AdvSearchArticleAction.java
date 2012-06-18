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
import tw.com.dsc.util.SystemUtils;
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
	
	private String advLanguageOid;
	private Source advSourceType;
	private String advProjectCode;
	private Boolean advNews;
	private ArticleType advType;
	private String advDateType; //entryDate, lastUpdate, publishDate
	private Date advBeginDate;
	private Date advEndDate;
	private String advApType; //publishedType, statusType
	private Boolean advPublished;
	private Status advStatus;
	private String advAgentSearchType; //group, agent, self
	private String advGroup;
	private String advAccount;
	private Level[] advLevels;
	private String advTechnology;
	private String advProduct;
	private String advFirmware;
	private OperationEnum advViewsType;
	private Integer advHitCount;
	private OperationEnum advRatingType;
	private Float advAvgRate;
	private Boolean reload = Boolean.FALSE;
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
		
		if (StringUtils.isNotEmpty(this.advLanguageOid)) {
			example.setLanguage(new Language(advLanguageOid, null));
		}
		
		if (null != advSourceType) {
			example.setSource(advSourceType);
			if (Source.Project == advSourceType) {
				example.setProjectCode(advProjectCode);
			}
		}
		if (null != advNews) {
			example.setNews(advNews);
		}
		if (null != advType) {
			example.setType(advType);
		}
		
		if (StringUtils.isNotEmpty(advDateType)) {
//			beginDate = DateUtils.begin(beginDate);
//			endDate = DateUtils.end(endDate);
			if (null != this.advBeginDate && null != this.advEndDate) {
				if (this.advBeginDate.before(this.advEndDate)) {
					conds.add(new BetweenCondition(advDateType, DateUtils.begin(advBeginDate), DateUtils.end(advEndDate)));
				} else {
					conds.add(new BetweenCondition(advDateType, DateUtils.begin(advEndDate), DateUtils.end(advBeginDate)));
				}
			} else if (null != this.advBeginDate) {
				conds.add(new SimpleCondition(advDateType, DateUtils.begin(this.advBeginDate), OperationEnum.GE));
			} else if (null != this.advEndDate) {
				conds.add(new SimpleCondition(advDateType, DateUtils.end(this.advEndDate), OperationEnum.LE));
			}
		}
		
		if (StringUtils.isNotEmpty(advApType)) {
			if ("publishedType".equals(advApType)) {
				if (!advPublished) {
					conds.add(new SimpleCondition("status", Status.Published, OperationEnum.NE));
				} else {
					conds.add(new SimpleCondition("status", Status.Published, OperationEnum.EQ));
				}
			} else if ("statusType".equals(advApType)) {
				if (null != advStatus) {
					example.setStatus(advStatus);
				}
			}
		}
		
		if (StringUtils.isNotEmpty(advAgentSearchType)) {
			if ("self".equals(advAgentSearchType)) {
				example.setEntryUser(op.getAccount());
			} else if ("group".equals(advAgentSearchType)) {
				if (StringUtils.isEmpty(this.advGroup)) {
					List<Group> findGroups = this.systemService.findGroups();
					String[] groups = new String[findGroups.size()];
					for (int i = 0; i < groups.length; i++) {
						groups[i] = findGroups.get(i).getTeamName();
					}
					
					conds.add(new InCondition("userGroup", groups));
				} else {
					example.setUserGroup(advGroup);
				}
			} else if ("agent".equals(advAgentSearchType)) {
				if (StringUtils.isEmpty(this.advAccount)) {
					List<Account> accList = this.systemService.findTeamAccounts();
					String[] accs = new String[accList.size()];
					for (int i=0; i<accs.length; i++) {
						accs[i] = accList.get(i).getId();
					}
					conds.add(new InCondition("entryUser", accs));
				} else {
					example.setEntryUser(advAccount);
				}
			}
		}
		
		if (null != this.advLevels) {
			conds.add(new InCondition("level", this.advLevels));
		}
		
		if (StringUtils.isNotEmpty(this.advTechnology)) {
			conds.add(parseString(this.advTechnology, "technology"));
		}
		
		if (StringUtils.isNotEmpty(this.advProduct)) {
			conds.add(parseString(this.advProduct, "product"));
		}
		
		if (StringUtils.isNotEmpty(this.advFirmware)) {
			example.setFirmware(advFirmware);
		}
		
		if (null != this.advViewsType && null != advHitCount) {
			conds.add(new SimpleCondition("hitCount", advHitCount, advViewsType));
		}
		
		if (null != this.advRatingType && null != advAvgRate) {
			conds.add(new SimpleCondition("avgRate", advAvgRate, advRatingType));
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
	
	

	@Override
	public String toString() {
		return "AdvSearchArticleAction [languageOid=" + advLanguageOid
				+ ", sourceType=" + advSourceType + ", projectCode=" + advProjectCode
				+ ", news=" + advNews + ", type=" + advType + ", dateType="
				+ advDateType + ", beginDate=" + advBeginDate + ", endDate="
				+ advEndDate + ", apType=" + advApType + ", published=" + advPublished
				+ ", status=" + advStatus + ", agentSearchType=" + advAgentSearchType
				+ ", group=" + advGroup + ", account=" + advAccount + ", levels="
				+ Arrays.toString(advLevels) + ", technology=" + advTechnology
				+ ", product=" + advProduct + ", firmware=" + advFirmware
				+ ", viewsType=" + advViewsType + ", hitCount=" + advHitCount
				+ ", ratingType=" + advRatingType + ", avgRate=" + advAvgRate + "]";
	}

	public String getAdvLanguageOid() {
		return advLanguageOid;
	}

	public void setAdvLanguageOid(String advLanguageOid) {
		this.advLanguageOid = advLanguageOid;
	}

	public Source getAdvSourceType() {
		return advSourceType;
	}

	public void setAdvSourceType(Source advSourceType) {
		this.advSourceType = advSourceType;
	}

	public String getAdvProjectCode() {
		return advProjectCode;
	}

	public void setAdvProjectCode(String advProjectCode) {
		this.advProjectCode = advProjectCode;
	}

	public Boolean getAdvNews() {
		return advNews;
	}

	public void setAdvNews(Boolean advNews) {
		this.advNews = advNews;
	}

	public ArticleType getAdvType() {
		return advType;
	}

	public void setAdvType(ArticleType advType) {
		this.advType = advType;
	}

	public String getAdvDateType() {
		return advDateType;
	}

	public void setAdvDateType(String advDateType) {
		this.advDateType = advDateType;
	}

	public Date getAdvBeginDate() {
		return advBeginDate;
	}

	public void setAdvBeginDate(Date advBeginDate) {
		this.advBeginDate = advBeginDate;
	}

	public Date getAdvEndDate() {
		return advEndDate;
	}

	public void setAdvEndDate(Date advEndDate) {
		this.advEndDate = advEndDate;
	}

	public String getAdvApType() {
		return advApType;
	}

	public void setAdvApType(String advApType) {
		this.advApType = advApType;
	}

	public Boolean getAdvPublished() {
		return advPublished;
	}

	public void setAdvPublished(Boolean advPublished) {
		this.advPublished = advPublished;
	}

	public Status getAdvStatus() {
		return advStatus;
	}

	public void setAdvStatus(Status advStatus) {
		this.advStatus = advStatus;
	}

	public String getAdvAgentSearchType() {
		return advAgentSearchType;
	}

	public void setAdvAgentSearchType(String advAgentSearchType) {
		this.advAgentSearchType = advAgentSearchType;
	}

	public String getAdvGroup() {
		return advGroup;
	}

	public void setAdvGroup(String advGroup) {
		this.advGroup = advGroup;
	}

	public String getAdvAccount() {
		return advAccount;
	}

	public void setAdvAccount(String advAccount) {
		this.advAccount = advAccount;
	}

	public Level[] getAdvLevels() {
		return advLevels;
	}

	public void setAdvLevels(Level[] advLevels) {
		this.advLevels = advLevels;
	}

	public String getAdvTechnology() {
		return advTechnology;
	}

	public void setAdvTechnology(String advTechnology) {
		this.advTechnology = advTechnology;
	}

	public String getAdvProduct() {
		return advProduct;
	}

	public void setAdvProduct(String advProduct) {
		this.advProduct = advProduct;
	}

	public String getAdvFirmware() {
		return advFirmware;
	}

	public void setAdvFirmware(String advFirmware) {
		this.advFirmware = advFirmware;
	}

	public OperationEnum getAdvViewsType() {
		return advViewsType;
	}

	public void setAdvViewsType(OperationEnum advViewsType) {
		this.advViewsType = advViewsType;
	}

	public Integer getAdvHitCount() {
		return advHitCount;
	}

	public void setAdvHitCount(Integer advHitCount) {
		this.advHitCount = advHitCount;
	}

	public OperationEnum getAdvRatingType() {
		return advRatingType;
	}

	public void setAdvRatingType(OperationEnum advRatingType) {
		this.advRatingType = advRatingType;
	}

	public Float getAdvAvgRate() {
		return advAvgRate;
	}

	public void setAdvAvgRate(Float advAvgRate) {
		this.advAvgRate = advAvgRate;
	}

	public Boolean getReload() {
		return reload;
	}

	public void setReload(Boolean reload) {
		this.reload = reload;
	}

	
	
}
