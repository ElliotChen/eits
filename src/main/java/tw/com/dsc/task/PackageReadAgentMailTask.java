package tw.com.dsc.task;

import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;

public class PackageReadAgentMailTask extends PackageMailTask {
	
	public PackageReadAgentMailTask() {
		super();
	}

	public PackageReadAgentMailTask(Account receiver, String serverUrl,
			String sender, List<Article> articles, String mailTemplate,
			JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
		super(receiver, serverUrl, sender, articles, mailTemplate, javaMailSender,
				velocityEngine);
	}

	@Override
	public String getTitle() {
		return "[Tech. Writer Proofreading] KB and ZyTech News Exported For Proofread Notification";
	}

}
