package tw.com.dsc.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

@Entity
@Table(name = "T_EITS_ARTICLE")
@SequenceGenerator(name = "ENTITY_SEQ", sequenceName = "SEQ_EITS_ARTICLE", allocationSize = 1)
public class Article extends AbstractSeqIdObjectAuditable {
	private static final Logger logger = LoggerFactory.getLogger(Article.class);
	private static final long serialVersionUID = -2523850559752209196L;
	public static final Pattern parser = Pattern.compile("(.*)--(.*)$");
	@ManyToOne
	private ArticleId articleId;

	@ManyToOne
	private Language language;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SOURCE", length = 10)
	private Source source;
	
	@Column(name = "PROJECT_CODE", length = 20)
	private String projectCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "AGENT_TYPE", length = 10)
	private AgentType agentType;
	
	@Column(name = "USER_GROUP", length = 20)
	private String userGroup;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", length = 20)
	private ArticleType type;
	
	@Type(type = "yes_no")
	@Column(name = "NEWS", length = 3)
	private Boolean news;
	
	@Column(name = "SUMMARY", length = 100)
	private String summary;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "EXPIRE_TYPE", length = 10)
	private ExpireType expireType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 32)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_DATE")
	private Date entryDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUBLISH_DATE")
	private Date publishDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRE_DATE")
	private Date expireDate;
	
	@Column(name="KEYWORDS", length=100)
	private String keywords;
	
	@Lob
	@Column(name = "QUESTION")
	private String zquestion;
	
	@Lob
	@Column(name = "ANSWER")
	private String zanswer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "LEVEL_TYPE", length = 10)
	private Level level;
	
	@Column(name = "HITCOUNT")
	private Integer hitCount;
	
	@Column(name = "ENTRY_USER", length = 30)
	private String entryUser;
	
	@Column(name = "TECHNOLOGY", length = 100)
	private String technology;
	
	@Column(name = "PRODUCT", length = 100)
	private String product;
	
	@Column(name = "FIRMWARE", length = 50)
	private String firmware;
	/*
	@Type(type = "yes_no")
	@Column(name = "ACTIVE", length = 3)
	private Boolean active;
	*/
	/**Type = SpecInfo**/
	@Column(name = "TICKET_ID", length = 50)
	private String ticketId;
	
	/** Application and TroubleShooting **/
	@Lob
	@Column(name = "SCENARIO")
	private String zscenario;
	
	@Lob
	@Column(name = "STEP")
	private String zstep;
	
	@Lob
	@Column(name = "VERIFICATION")
	private String zverification;
	
	/**Type = Issue**/
	@Lob
	@Column(name = "PROBLEM")
	private String zproblem;
	@Lob
	@Column(name = "SOLUTION")
	private String zsolution;
	
	@Lob
	@Column(name = "PROCEDURE_DATA")
	private String zprocedure;
	
	@Column(name = "RATE_1")
	private Integer rate1;
	@Column(name = "RATE_2")
	private Integer rate2;
	@Column(name = "RATE_3")
	private Integer rate3;
	@Column(name = "RATE_4")
	private Integer rate4;
	@Column(name = "RATE_5")
	private Integer rate5;
	
	@Column(name = "AVG_RATE")
	private Float avgRate;
	@ManyToOne(fetch=FetchType.LAZY)
	private ExportPackage exportPackage;
	
	@Transient
	private String entryUserName;
	@Transient
	private String leaderAccount;
	public ArticleId getArticleId() {
		return articleId;
	}

	public void setArticleId(ArticleId articleId) {
		this.articleId = articleId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getQuestion() {
		return zquestion;
	}

	public void setQuestion(String question) {
		this.zquestion = question;
	}

	public String getAnswer() {
		return zanswer;
	}

	public void setAnswer(String answer) {
		this.zanswer = answer;
	}

	public ExpireType getExpireType() {
		return expireType;
	}

	public void setExpireType(ExpireType expireType) {
		this.expireType = expireType;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public String getEntryUser() {
		return entryUser;
	}

	public void setEntryUser(String entryUser) {
		this.entryUser = entryUser;
	}

	public Boolean getNews() {
		return news;
	}

	public void setNews(Boolean news) {
		this.news = news;
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
	/*
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	*/
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getScenario() {
		return zscenario;
	}

	public void setScenario(String scenario) {
		this.zscenario = scenario;
	}

	public String getStep() {
		return zstep;
	}

	public void setStep(String step) {
		this.zstep = step;
	}

	public String getVerification() {
		return zverification;
	}

	public void setVerification(String verification) {
		this.zverification = verification;
	}

	public String getProblem() {
		return zproblem;
	}

	public void setProblem(String problem) {
		this.zproblem = problem;
	}

	public String getSolution() {
		return zsolution;
	}

	public void setSolution(String solution) {
		this.zsolution = solution;
	}

	public String getProcedure() {
		return zprocedure;
	}

	public void setProcedure(String procedure) {
		this.zprocedure = procedure;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	
	public Integer getRate1() {
		return rate1;
	}

	public void setRate1(Integer rate1) {
		this.rate1 = rate1;
	}

	public Integer getRate2() {
		return rate2;
	}

	public void setRate2(Integer rate2) {
		this.rate2 = rate2;
	}

	public Integer getRate3() {
		return rate3;
	}

	public void setRate3(Integer rate3) {
		this.rate3 = rate3;
	}

	public Integer getRate4() {
		return rate4;
	}

	public void setRate4(Integer rate4) {
		this.rate4 = rate4;
	}

	public Integer getRate5() {
		return rate5;
	}

	public void setRate5(Integer rate5) {
		this.rate5 = rate5;
	}

	public ExportPackage getExportPackage() {
		return exportPackage;
	}

	public void setExportPackage(ExportPackage exportPackage) {
		this.exportPackage = exportPackage;
	}

	public Float getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Float avgRate) {
		this.avgRate = avgRate;
	}

	public String getEntryUserName() {
		return entryUserName;
	}

	public void setEntryUserName(String entryUserName) {
		this.entryUserName = entryUserName;
	}

	public String getLeaderAccount() {
		return leaderAccount;
	}

	public void setLeaderAccount(String leaderAccount) {
		this.leaderAccount = leaderAccount;
	}

	public List<Status> getAvailableStatus() {
		User op = ThreadLocalHolder.getOperator();
		ArrayList<Status> result = new ArrayList<Status>();
		
		if (null == this.status) {
			
			if (AgentType.L2 == op.getAgentType()) {
				result.add(Status.Published);
			} else if(AgentType.L3 == op.getAgentType() && op.isL3Manager()) {
				result.add(Status.Published);
			} else {
				//logger.error("Please check AgentType[{}] for User[{}]", op.getAgentType(), op.getAccount());
			}

			if (!op.isGuest()) {
				result.add(Status.WaitForApproving);
				result.add(Status.Draft);
				
			} else {
				logger.warn("Guest can't get any available status!");
			}
			
			logger.debug("No status in this Article[{}] for Agent[{}], available status are [{}].", new Object[] {oid,op.getAgentType(), result});
			return result;
		}
		
		switch (this.status) {
			case Draft:
				result.add(Status.WaitForApproving);
				if (AgentType.L2 == this.agentType && op.isL2Manager()) {
					result.add(Status.Published);
				}
				break;
			case WaitForApproving:
				
				if (AgentType.L2 == this.agentType && op.isL2Manager()) {
					result.add(Status.LeaderReject);
					result.add(Status.Published);
				} else if(AgentType.L3 == this.agentType && op.isL3Manager()) {
					result.add(Status.LeaderReject);
					result.add(Status.LeaderApproved);
					result.add(Status.Published);
				}
				break;
			case LeaderReject:
				result.add(Status.WaitForApproving);
				break;
			case LeaderApproved:
				//result.add(Status.WaitForProofRead);
				break;
			case WaitForProofRead:
				if(AgentType.L3 == this.agentType && op.isL3Manager()) {
					result.add(Status.ReadyToUpdate);
				}
				break;
			case ReadyToUpdate:
				if(AgentType.L3 == this.agentType) {
					result.add(Status.ReadyToPublish);
				}
				break;
			case ReadyToPublish:
				if(AgentType.L3 == this.agentType && op.isL3Manager()) {
					result.add(Status.Published);
					result.add(Status.RejectToUpdate);
				}
				break;
			case Published:
				if (op.isLeader()) {
					result.add(Status.WaitForRepublish);
				}
				break;
			case WaitForRepublish:
				result.add(Status.Published);
				result.add(Status.Archived);
				break;
			case Archived:
				break;
			default:
				break;
		}
		
		logger.debug("Current status in Article[{}] is [{}] and available status are [{}]", new Object[] {this, this.status, result});
		return result;
	}

	public String getRatingInfo() {
		int count = 0;
		if (null != this.rate1) {
			count += this.rate1;
		}
		if (null != this.rate2) {
			count += this.rate2;
		}
		if (null != this.rate3) {
			count += this.rate3;
		}
		if (null != this.rate4) {
			count += this.rate4;
		}
		if (null != this.rate5) {
			count += this.rate5;
		}
		
		return count != 0 ? this.avgRate+" ("+count+" votes)":"";
	}
	
	public Float countRating() {
		int count = 0;
		float points = 0;
		if (null != this.rate1) {
			count += this.rate1;
			points += 1f * this.rate1;
		}
		if (null != this.rate2) {
			count += this.rate2;
			points += 2f * this.rate2;
		}
		if (null != this.rate3) {
			count += this.rate3;
			points += 3f * this.rate3;
		}
		if (null != this.rate4) {
			count += this.rate4;
			points += 4f * this.rate4;
		}
		if (null != this.rate5) {
			count += this.rate5;
			points += 5f * this.rate5;
		}
		
		if (0 == count) {
			return 0f;
		}
		return points/count;
	}
	
	public boolean isReadonly() {
		switch (this.status) {
		case Archived:
		//case Published:
		case WaitForRepublish:
		case ReadyToPublish:
		case Deleted:
			return true;
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", language=" + language
				+ ", agentType=" + agentType + ", userGroup=" + userGroup
				+ ", status=" + status + ", level=" + level + ", oid=" + oid
				+ "]";
	}

	public List<String> getFormattedTech() {
		return this.parser(this.technology);
	}
	
	public List<String> getFormattedLiteModel() {
		if (StringUtils.isEmpty(this.product)) {
			return new ArrayList<String>();
		}
		String[] sources = this.product.split(",");
		logger.debug("Size[{}]", sources.length);
		if (3 >= sources.length) {
			return this.parser(this.product);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(sources[0]);
		for (int i = 1; i < 3; i++) {
			sb.append(",");
			sb.append(sources[i]);
		}
		
		return this.parser(sb.toString());
	}
	
	public List<String> getFormattedModel() {
		return this.parser(this.product);
	}
	
	public String getFormattedSeries() {
		List<String> all = this.parser(this.product);
		StringBuilder sb = new StringBuilder();		
		for (String a : all) {
			if (!a.startsWith("--")) {
				sb.append(a+"<br>");
			}
		}
		return sb.toString();
	}
	
	public String getLineTech() {
		List<String> list = this.parseLine(this.technology);
		String[] src = new String[list.size()];
		src = list.toArray(src);
		
		StringBuilder sb = new StringBuilder();
		if (0 < src.length) {
		sb.append(src[0]);
		for (int i = 1; i < src.length; i++) {
			sb.append(",");
			sb.append(src[i]);
		}
		}
		if (3 < list.size()) {
			sb.append("...");
		}
		return sb.toString();
	}
	
	public String getLineModel() {
		List<String> list = this.parseLine(this.product);
		String[] src = new String[list.size()];
		src = list.toArray(src);
		
		StringBuilder sb = new StringBuilder();
		if (0 < src.length) {
		sb.append(src[0]);
		for (int i = 1; i < src.length; i++) {
			sb.append(",");
			sb.append(src[i]);
		}
		}
		if (3 < list.size()) {
			sb.append("...");
		}
		return sb.toString();
	}
	
	protected List<String> parseLine(String origin) {
		String[] sp = origin.split(",");
		List<String> tmp = new ArrayList<String>();
		for (int i =0 ; (i < 3 && i < sp.length); i++) {
			tmp.add(sp[i]);
		}
		List<String> result = new ArrayList<String>();
		for (String source : tmp) {
			Matcher matcher = parser.matcher(source);
			if (matcher.matches()) {
				result.add(matcher.group(2));
			}
		}
		return result;
	}
	
	
	
	public List<String> parser(String origin) {
		List<String> result = new ArrayList<String>();
		Map<String, List<String>> container = new TreeMap<String, List<String>>();
		if (StringUtils.isNotEmpty(origin)) {
			String[] sources = origin.split(",");
			
			for (String source : sources) {
				Matcher matcher = parser.matcher(source);
				if (matcher.matches()) {
					String key = matcher.group(1);
					String value = matcher.group(2);
					if (container.containsKey(key)) {
						container.get(key).add("--"+value);
					} else {
						List<String> values = new ArrayList<String>();
						values.add("--"+value);
						container.put(key, values);
					}
//					logger.debug("Key[{}] -- Value[{}]", key, value);
				}
			}
		}
		for (Entry<String, List<String>> entry : container.entrySet()) {
			result.add(entry.getKey());
			for (String value : entry.getValue()) {
				result.add(value);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Parse Data[{}] to ", origin);
			for (String format : result) {
				logger.debug(format);
			}
		}
		return result;
	}
	
	public String getI18nStatus() {
		String result = "Draft";
		if (null == this.status) {
			return result;
		}
		switch(this.status) {
		case LeaderReject:
			result = "Leader Rejected";
			break;
		case LeaderApproved:
			result = "Leader Approved";
			break;
		case WaitForApproving:
			result = "Waiting for Approving";
			break;
		case WaitForProofRead:
			result = "Waiting for Proofread";
			break;
		case ReadyToUpdate:
			result = "Ready to Update";
			break;
		case ReadyToPublish:
			result = "Ready to Publish";
			break;
		case Published:
			result = "Published";
			break;
		case WaitForRepublish:
			result = "Expired";
			break;
		case Archived:
			result = "Archived";
			break;
		case Deleted:
			result = "Deleted";
			break;
		}
		
		return result;
	}
	
	public String getLeaderGroupId() {
		String groupId = this.userGroup;
		String leaderGroupId = "";
		if (AgentType.L3 == this.agentType) {
			if (!"L3_Admin".equals(groupId)) {
				leaderGroupId = groupId+"_Leader";
			} else {
				leaderGroupId = groupId;
			}
		} else if (AgentType.L2 == this.agentType) {
			leaderGroupId = groupId+"_Leader";
		} else {
			logger.error("AgentType[{}] doesn't have Group Leader named [{}].", this.agentType, this.userGroup);
		}
		
		return leaderGroupId;
	}

	public String getZanswer() {
		return zanswer;
	}

	public void setZanswer(String zanswer) {
		this.zanswer = zanswer;
	}

	public String getZquestion() {
		return zquestion;
	}

	public void setZquestion(String zquestion) {
		this.zquestion = zquestion;
	}

	public String getZscenario() {
		return zscenario;
	}

	public void setZscenario(String zscenario) {
		this.zscenario = zscenario;
	}

	public String getZstep() {
		return zstep;
	}

	public void setZstep(String zstep) {
		this.zstep = zstep;
	}

	public String getZverification() {
		return zverification;
	}

	public void setZverification(String zverification) {
		this.zverification = zverification;
	}

	public String getZproblem() {
		return zproblem;
	}

	public void setZproblem(String zproblem) {
		this.zproblem = zproblem;
	}

	public String getZsolution() {
		return zsolution;
	}

	public void setZsolution(String zsolution) {
		this.zsolution = zsolution;
	}

	public String getZprocedure() {
		return zprocedure;
	}

	public void setZprocedure(String zprocedure) {
		this.zprocedure = zprocedure;
	}
	
	
}
