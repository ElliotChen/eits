package tw.com.dsc.task;

import org.springframework.mail.MailSender;

import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
 * 
 * Receiver : Leaders
 * @author elliot
 *
 */
public class ReadyPublishMailTask extends MailTask {
	
	public ReadyPublishMailTask() {
		super();
	}

	public ReadyPublishMailTask(Long articleOid, MailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService) {
		super(articleOid, mailSender, systemService, articleService, articleLogService);
	}

	@Override
	public String[] getReceivers() {
		return this.getLeaders();
	}

	@Override
	public String[] getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "A KB article is ready to be published.";
	}

	@Override
	public String getMessage() {
		return "There is a KB["+this.article.getArticleId().getOid()+"] article has been proof-read and updated by an agent["+agent.getOid()+"]";
	}

}
