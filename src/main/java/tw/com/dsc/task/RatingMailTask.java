package tw.com.dsc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Rating Notification – 
 * 
 * Receiver : Leaders
 * @author elliot
 *
 */
public class RatingMailTask extends MailTask {

	private String rank;
	
	public RatingMailTask() {
		super();
	}
	
	public RatingMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String rank, String serverUrl) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
		this.rank = rank;
	}

	public RatingMailTask(Article article, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String rank, String serverUrl) {
		super(null, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
		this.article = article;
		this.articleOid = article.getOid();
		this.rank = rank;
	}


	@Override
	public List<Account> getReceivers() {
		return this.getAdminAndLeaders();
	}

	@Override
	public List<Account> getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "Rating Notification";
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
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/rating.vm", map);
		return message;
	}

}
