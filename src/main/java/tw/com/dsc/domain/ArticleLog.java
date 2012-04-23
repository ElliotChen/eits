package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_EITS_ARTICLELOG")
@SequenceGenerator(name = "ENTITY_SEQ", sequenceName = "SEQ_EITS_ARTICLELOG", allocationSize = 1)
public class ArticleLog extends AbstractSeqIdObject {
	
	private static final long serialVersionUID = 3409382400440482055L;
	public ArticleLog() {
		
	}
	
	public ArticleLog(Long articleOid, ActionType action, String account, String message, String ip) {
		this.articleOid = articleOid;
		this.action = action;
		this.account = account;
		this.message = message;
	}
	
	@Column(name = "ARTICLE_OID")
	private Long articleOid;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ACTION", length = 10)
	private ActionType action;
	
	@Column(name = "ACCOUNT", length = 50)
	private String account;
	
	@Column(name = "MESSAGE", length = 200)
	private String message;
	
	@Column(name = "MESSAGE", length = 40)
	private String ip;
	public ActionType getAction() {
		return action;
	}

	public void setAction(ActionType action) {
		this.action = action;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getArticleOid() {
		return articleOid;
	}

	public void setArticleOid(Long articleOid) {
		this.articleOid = articleOid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
