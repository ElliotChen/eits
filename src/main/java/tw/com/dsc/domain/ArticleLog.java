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
	
	public ArticleLog(ActionType action, String account, String message) {
		this.action = action;
		this.account = account;
		this.message = message;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ACTION", length = 10)
	private ActionType action;
	
	@Column(name = "ACCOUNT", length = 50)
	private String account;
	
	@Column(name = "MESSAGE", length = 200)
	private String message;

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
}
