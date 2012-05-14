package tw.com.dsc.task;

import org.springframework.mail.MailSender;

import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Reject Notification – Notify the agent that there is a KB article has been rejected.
 * @author elliot
 *
 */
public class RejectMailTask extends MailTask {

	
	public RejectMailTask() {
		super();
	}

	public RejectMailTask(Long articleOid, MailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService) {
		super(articleOid, mailSender, systemService, articleService, articleLogService);
	}

	@Override
	public String[] getReceivers() {
		return new String[] {this.agent.getEmail()};
	}

	@Override
	public String[] getCcReceivers() {
		return this.getLeaders();
	}

	@Override
	public String getTitle() {
		return "KB article["+this.article.getArticleId().getOid()+"] has been rejected";
	}

	@Override
	public String getMessage() {
		return "KB article["+this.article.getArticleId().getOid()+"] has been rejected";
	}

}
