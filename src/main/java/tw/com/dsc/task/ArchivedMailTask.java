package tw.com.dsc.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
 * Expired Notification for Agent – Notify the agent that one of his KB article already expired.
 * @author elliot
 *
 */
public class ArchivedMailTask extends MailTask {

	private String allGroup;
	
	public ArchivedMailTask() {
		super();
	}

	public ArchivedMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String allGroup) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine);
		this.allGroup = allGroup;
	}

	@Override
	public String[] getReceivers() {
		return new String[]{allGroup};
	}

	@Override
	public String[] getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "Article["+this.article.getArticleId().getOid()+"] has been archived";
	}

	@Override
	public String getMessage() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("articleOid", String.valueOf(this.article.getOid()));
		map.put("summary", this.article.getSummary());
		map.put("entryUser", this.article.getEntryUser());
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/archived.vm", map);
		return message;
	}

}