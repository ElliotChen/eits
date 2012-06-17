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

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.Status;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

public abstract class MailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	protected JavaMailSender javaMailSender;
	protected Long articleOid;
	protected Account agent;
	protected Account receiver;
	protected List<Account> leaders;
	protected List<Account> admins;
	protected Article article;
	protected String serverUrl;
	protected VelocityEngine velocityEngine;
	
	protected String sender;
	
	protected SystemService systemService;
	
	protected ArticleService articleService;
	
	protected ArticleLogService articleLogService;

	
	public MailTask() {
		super();
	}

	public MailTask(Long articleOid, JavaMailSender javaMailSender, SystemService systemService, ArticleService articleService, ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine, String serverUrl) {
		this.articleOid = articleOid;
		this.javaMailSender = javaMailSender;
		this.systemService = systemService;
		this.articleService = articleService;
		this.articleLogService = articleLogService;
		this.sender = sender;
		this.velocityEngine = velocityEngine;
		this.serverUrl = serverUrl;
	}

	@Override
	public void run() {
		if (null == this.article) {
			this.article = this.articleService.findByOid(articleOid);
			if (null == article) {
				logger.error("Please check Article[{}], can't find this article in system!", this.articleOid);
				return;
			}
		}
		
		if (this.getAvailableStatus() != article.getStatus()) {
			logger.warn("Article[{}]'s status is [{}] not [{}]", new Object[] {this.article.getOid(), this.article.getStatus(), this.getAvailableStatus()});
			return;
		}
		this.agent = this.systemService.findAccountByOid(article.getEntryUser());
		this.leaders = this.systemService.findGroupLeaders(article);
		this.admins = this.systemService.findGroupAdmins(article);
		
		MimeMessage mail = this.javaMailSender.createMimeMessage();
		try {
			
			List<Account> receivers = this.getReceivers();
//			List<Account> cc = this.getCcReceivers();
			
			for (Account receiver : receivers) {
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
				String title = this.getTitle();
				String message = this.getMessage(receiver);
			
				helper.setFrom(this.sender);
				helper.setTo(receiver.getEmail());
				/*
				if (null != cc && cc.length > 0) {
					helper.setCc(cc);
				}
				*/
				helper.setSubject(title);
				helper.setText(message, true);
				logger.info("Send Mail[{}] to [{}]", message, receivers);
				javaMailSender.send(mail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sending mail failed.",e);
		}
		
		logger.info("Send Mail Success!");
		
	}
	
	public abstract List<Account> getReceivers();
	public abstract List<Account> getCcReceivers();
	public abstract String getTitle();
	public abstract String getMessage(Account receiver);
	public abstract Status getAvailableStatus();
	
	
	protected List<Account> getLeaders() {
		return this.leaders;
	}
	
	protected List<Account> getAdminAndLeaders() {
		List<Account> accounts = new ArrayList<Account>();
		accounts.addAll(this.leaders);
		accounts.addAll(this.admins);
		return accounts;
	}
	
	protected Map<String, Object> initBaseMap(Account receiver) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("article", this.article);
		map.put("agent", agent);
		map.put("receiver", receiver);
		map.put("server", this.serverUrl);
		
		return map;
	}
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public Long getArticleOid() {
		return articleOid;
	}

	public void setArticleOid(Long articleOid) {
		this.articleOid = articleOid;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public ArticleLogService getArticleLogService() {
		return articleLogService;
	}

	public void setArticleLogService(ArticleLogService articleLogService) {
		this.articleLogService = articleLogService;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
}
