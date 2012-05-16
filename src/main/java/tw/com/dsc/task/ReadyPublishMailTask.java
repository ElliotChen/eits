package tw.com.dsc.task;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

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

	public ReadyPublishMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine);
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
