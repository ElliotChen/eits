package tw.com.dsc.task;

import java.util.ArrayList;
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
public class ProofreadMailTask extends MailTask {
	
	public ProofreadMailTask() {
		super();
	}

	public ProofreadMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String serverUrl) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
	}

	@Override
	public List<Account> getReceivers() {
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(agent);
		for (Account acc : this.leaders) {
			if (!agent.getId().equals(acc.getId())) {
				accounts.add(acc);
			}
		}
		return accounts;
	}

	@Override
	public List<Account> getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "[Tech. Writer Proofreading] KB and ZyTech News Exported For Proofread Notification ";
	}

	@Override
	public String getMessage(Account receiver) {
		Map<String, Object> map = initBaseMap(receiver);
		String message = "";
		if (agent.getId().equals(receiver.getId())) {
			message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/proofreadAgent.vm", map);
		} else {
			message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/proofreadLeader.vm", map);
		}
		return message;
	}

}
