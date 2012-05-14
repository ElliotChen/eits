package tw.com.dsc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.dao.ArticleLogDao;
import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

public abstract class MailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	private MailSender mailSender;
	protected Long articleOid;
	protected Account agent;
	protected List<Account> leaders;
	protected Article article;
	
	
	private SystemService systemService;
	
	private ArticleService articleService;
	
	private ArticleLogService articleLogService;
	
	public MailTask() {
		super();
	}

	public MailTask(Long articleOid, MailSender mailSender, SystemService systemService, ArticleService articleService, ArticleLogService articleLogService) {
		this.articleOid = articleOid;
		this.mailSender = mailSender;
		this.systemService = systemService;
		this.articleService = articleService;
		this.articleLogService = articleLogService;
	}

	@Override
	public void run() {
		this.article = this.articleService.findByOid(articleOid);
		if (null == article) {
			logger.error("Please check Article[{}], can't find this article in system!", this.articleOid);
			return;
		}
		
		this.agent = this.systemService.findAccountByOid(article.getEntryUser());
		this.leaders = this.systemService.findGroupLeaders(article);
		
		String[] receivers = this.getReceivers();
		String[] cc = this.getCcReceivers();
		logger.info("Send Mail to [{}]", receivers);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("test@elliot.tw");
		mail.setTo(this.getReceivers());
		if (null != cc && cc.length > 0) {
			mail.setCc(cc);
		}
		mail.setSubject(this.getTitle());
		mail.setText(this.getMessage());
		
		mailSender.send(mail);
		
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

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
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
	
}
