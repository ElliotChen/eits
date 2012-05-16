package tw.com.dsc.task;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
 * Expired Notification for Agent – Notify the agent that one of his KB article already expired.
 * @author elliot
 *
 */
public class ExpiredMailTask extends MailTask {

	
	public ExpiredMailTask() {
		super();
	}

	public ExpiredMailTask(Long articleOid, JavaMailSender mailSender,
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
		return new String[] {this.agent.getEmail()};
	}

	@Override
	public String getTitle() {
		return "Article["+this.article.getArticleId().getOid()+"] Reject has been rejected";
	}

	@Override
	public String getMessage() {
		return "your article["+this.article.getArticleId().getOid()+"] has been rejected";
	}

}
