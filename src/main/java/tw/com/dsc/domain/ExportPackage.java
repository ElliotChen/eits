package tw.com.dsc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "T_EITS_EXPORT_PACKAGE")
public class ExportPackage extends AbstractOIdObjectAuditable {

	private static final long serialVersionUID = -1293044464050868909L;
	
	@Type(type = "yes_no")
	@Column(name = "NEWS", length = 3)
	private Boolean news;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPORT_DATE")
	private Date exportDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGIN_DATE")
	private Date beginDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name="ARTICLE_TYPES", length=100)
	private String articleTypes;
	
	@Column(name="ARTICLE_ID_LIST", length=500)
	private String articleIdList;
	
	@Type(type = "yes_no")
	@Column(name = "CLOSED", length = 3)
	private Boolean closed;

	public Boolean getNews() {
		return news;
	}

	public void setNews(Boolean news) {
		this.news = news;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
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

	public String getArticleIdList() {
		return articleIdList;
	}

	public void setArticleIdList(String articleIdList) {
		this.articleIdList = articleIdList;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getArticleTypes() {
		return articleTypes;
	}

	public void setArticleTypes(String articleTypes) {
		this.articleTypes = articleTypes;
	}
	
}
