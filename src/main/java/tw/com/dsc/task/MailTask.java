package tw.com.dsc.task;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

public abstract class MailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	protected JavaMailSender javaMailSender;
	protected Long articleOid;
	protected Account agent;
	protected List<Account> leaders;
	protected Article article;
	protected VelocityEngine velocityEngine;
	
	protected String sender;
	
	protected SystemService systemService;
	
	protected ArticleService articleService;
	
	protected ArticleLogService articleLogService;
	
	public MailTask() {
		super();
	}

	public MailTask(Long articleOid, JavaMailSender javaMailSender, SystemService systemService, ArticleService articleService, ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine) {
		this.articleOid = articleOid;
		this.javaMailSender = javaMailSender;
		this.systemService = systemService;
		this.articleService = articleService;
		this.articleLogService = articleLogService;
		this.sender = sender;
		this.velocityEngine = velocityEngine;
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
		this.agent = this.systemService.findAccountByOid(article.getEntryUser());
		this.leaders = this.systemService.findGroupLeaders(article);
		
		MimeMessage mail = this.javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			String[] receivers = this.getReceivers();
			String[] cc = this.getCcReceivers();
			String title = this.getTitle();
			String message = this.getMessage();
			
			helper.setFrom(this.sender);
			helper.setTo(receivers);
			if (null != cc && cc.length > 0) {
				helper.setCc(cc);
			}
			helper.setSubject(title);
			helper.setText(message, true);
			logger.info("Send Mail[{}] to [{}]", message, receivers);
			javaMailSender.send(mail);
		} catch (Exception e) {

		}
		
		logger.info("Send Mail Success!");
		
	}
	
	public abstract String[] getReceivers();
	public abstract String[] getCcReceivers();
	public abstract String getTitle();
	public abstract String getMessage();
	
	
	protected String[] getLeaders() {
		if (null == this.leaders || this.leaders.isEmpty()) {
			logger.error("Leader could not be null.");
		}
		String[] leaderMails = new String[this.leaders.size()];
		for (int i=0; i < this.leaders.size(); i++) {
			leaderMails[i] = this.leaders.get(i).getEmail();
		}
		return leaderMails;
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
