package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", length = 10)
	private State state;

	@Column(name = "SUMMARY", length = 100)
	private String summary;

	public ArticleId getArticleId() {
		return articleId;
	}

	public void setArticleId(ArticleId articleId) {
		this.articleId = articleId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
