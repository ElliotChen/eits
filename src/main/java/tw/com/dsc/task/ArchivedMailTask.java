package tw.com.dsc.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Status;
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
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String allGroup, String serverUrl) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
		this.allGroup = allGroup;
	}

	@Override
	public List<Account> getReceivers() {
		Account fake = new Account();
		fake.setEmail(this.allGroup);
		fake.setName("All");
		
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(fake);
		return accounts;
	}

	@Override
	public List<Account> getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "[Archived] KB System Notification";
	}

	@Override
	public String getMessage(Account receiver) {
		Map<String, Object> map = initBaseMap(receiver);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/archived.vm", map);
		return message;
	}

	@Override
	public Status getAvailableStatus() {
		return Status.Archived;
	}
}
