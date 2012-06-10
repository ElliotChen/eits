package tw.com.dsc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
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
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String serverUrl) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
	}

	@Override
	public List<Account> getReceivers() {
		return this.getLeaders();
	}

	@Override
	public List<Account> getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "A KB article is ready to be published.";
	}

	@Override
	public String getMessage(Account receiver) {
		/*
		Map<String, String> map = new HashMap<String, String>();
		map.put("articleOid", String.valueOf(this.article.getOid()));
		map.put("summary", this.article.getSummary());
		map.put("entryUser", this.article.getEntryUser());
		*/
		Map<String, Object> map = initBaseMap(receiver);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/readyPublish.vm", map);
		return message;
	}

}
