package tw.com.dsc.task;

import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;

public class PackageUpdateMailTask extends PackageMailTask {

	public PackageUpdateMailTask() {
		super();
	}

	public PackageUpdateMailTask(Account receiver, String serverUrl,
			String sender, List<Article> articles, String mailTemplate,
			JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
		super(receiver, serverUrl, sender, articles, mailTemplate, javaMailSender,
				velocityEngine);
	}

	@Override
	public String getTitle() {
		return "[Tech. Writer Proofreading] Return KB and ZyTech News System Notification";
	}

}
