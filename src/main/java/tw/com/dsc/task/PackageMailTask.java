package tw.com.dsc.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;

public abstract class PackageMailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	
	protected Account receiver;
	protected String serverUrl;
	protected String sender;
	protected List<Article> articles;
	protected String mailTemplate;
	protected JavaMailSender javaMailSender;
	protected VelocityEngine velocityEngine;
	
	
	public PackageMailTask() {
		super();
	}

	public PackageMailTask(Account receiver, String serverUrl, String sender,
			List<Article> articles, String mailTemplate,
			JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
		super();
		this.receiver = receiver;
		this.serverUrl = serverUrl;
		this.sender = sender;
		this.articles = articles;
		this.mailTemplate = mailTemplate;
		this.javaMailSender = javaMailSender;
		this.velocityEngine = velocityEngine;
	}


	@Override
	public void run() {
		List<Article> kbs = new ArrayList<Article>();
		List<Article> news = new ArrayList<Article>();
		
		for (Article article : this.articles) {
			if (article.getNews()) {
				news.add(article);
			} else {
				kbs.add(article);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiver", receiver);
		map.put("server", "localhost");
		map.put("kbs", kbs);
		map.put("news", news);
		
		MimeMessage mail = this.javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			String title = this.getTitle();
			String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mailTemplate, map);
			helper.setFrom(this.sender);
			helper.setTo(receiver.getEmail());
			helper.setSubject(title);
			helper.setText(message, true);
			javaMailSender.send(mail);
			logger.info("Send Mail[{}] to [{}]", message, receiver.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sending mail failed.",e);
		}
		
	}

	public abstract String getTitle();
}
