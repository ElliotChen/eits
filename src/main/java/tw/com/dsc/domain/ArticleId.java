package tw.com.dsc.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "T_EITS_ARTICLEID")
public class ArticleId extends AbstractOIdObject {
	private static final Logger logger = LoggerFactory.getLogger(ArticleId.class);
	private static final long serialVersionUID = -5703587994225011088L;
	public ArticleId() {
		
	}
	
	public ArticleId(String oid) {
		this.oid = oid;
	}
	@Override
	public String toString() {
		return "ArticleId [oid=" + oid + "]";
	}
}
