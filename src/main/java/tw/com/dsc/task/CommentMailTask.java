package tw.com.dsc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.Status;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
 * 
 * Receiver : Leaders
 * @author elliot
 *
 */
public class CommentMailTask extends MailTask {

	private String comment;
	public CommentMailTask() {
		super();
	}
	
	public CommentMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String comment, String serverUrl) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
		this.comment = comment;
	}

	public CommentMailTask(Article article, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String comment, String serverUrl) {
		super(null, mailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl);
		this.article = article;
		this.articleOid = article.getOid();
		this.comment = comment;
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
		return "Comment Notification";
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
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/comment.vm", map);
		return message;
	}

	@Override
	public Status getAvailableStatus() {
		return Status.Published;
	}
}
