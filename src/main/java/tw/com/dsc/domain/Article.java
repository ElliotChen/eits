package tw.com.dsc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "T_EITS_ARTICLE")
@SequenceGenerator(name = "ENTITY_SEQ", sequenceName = "SEQ_EITS_ARTICLE", allocationSize = 1)
public class Article extends AbstractSeqIdObjectAuditable {
	private static final Logger logger = LoggerFactory.getLogger(Article.class);
	private static final long serialVersionUID = -2523850559752209196L;

	@ManyToOne
	private ArticleId articleId;

	@ManyToOne
	private Language language;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SOURCE", length = 10)
	private Source source;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PROJECT_CODE", length = 20)
	private String projectCode;
	
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
	
	@Column(name="KEYWORDS", length=100)
	private String keywords;
	
	@Lob
	@Column(name = "QUESTION")
	private String question;
	
	@Lob
	@Column(name = "ANSWER")
	private String answer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "LEVEL", length = 10)
	private Level level;
	
	@Column(name = "HITCOUNT")
	private Integer hitCount;
	
	@Column(name = "ENTRY_USER", length = 30)
	private String entryUser;
	
	@Column(name = "TECHNOLOGY", length = 100)
	private String technology;
	
	@Column(name = "PRODUCT", length = 100)
	private String product;
	
	@ManyToOne
	private Attachment firmware;
	
	@Type(type = "yes_no")
	@Column(name = "ACTIVE", length = 3)
	private Boolean active;
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
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public Attachment getFirmware() {
		return firmware;
	}

	public void setFirmware(Attachment firmware) {
		this.firmware = firmware;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
