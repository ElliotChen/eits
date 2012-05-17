package tw.com.dsc.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Republished Notification – Notify agent leaders that there is a KB article has been republished.
 * @author elliot
 *
 */
public class RepublishMailTask extends MailTask {

	
	public RepublishMailTask() {
		super();
	}

	public RepublishMailTask(Long articleOid, JavaMailSender mailSender,
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
		return "KB article["+this.article.getArticleId().getOid()+"] has been republished";
	}

	@Override
	public String getMessage() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("articleOid", String.valueOf(this.article.getOid()));
		map.put("summary", this.article.getSummary());
		map.put("entryUser", this.article.getEntryUser());

		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/republish.vm", map);
		return message;
	}

}
