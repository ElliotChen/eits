package tw.com.dsc.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusHelper {
	private static final Logger logger = LoggerFactory.getLogger(StatusHelper.class);
	public ErrorType saveAsDraft(final Article article) {
		if (null == article) {
			logger.error("saveAsDraft can't accept empty article.");
			return ErrorType.Params;
		}
		
		
		if (!(null == article.getStatus() || Status.Draft == article.getStatus())) {
			logger.error("Article[{}] can't be save as draft from status[{}]", article.getOid(), article.getStatus());
			return ErrorType.Status;
		}
		
		
		
		return null;
	}
}
