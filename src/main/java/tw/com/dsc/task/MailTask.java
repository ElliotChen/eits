package tw.com.dsc.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.to.User;

public abstract class MailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	private MailSender mailSender;
	
	protected Account agent;
	protected List<Account> leaders;
	protected Article article;
	
	
	public MailTask() {
		super();
	}

	public MailTask(MailSender mailSender, Account agent, List<Account> leaders, Article article) {
		this.mailSender = mailSender;
		this.agent = agent;
		this.leaders = leaders;
		this.article = article;
	}

	@Override
	public void run() {
		
		
		String[] receivers = this.getReceivers();
		
		logger.info("Send Mail to [{}]", receivers);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("test@elliot.tw");
		mail.setTo(this.getReceivers());
		mail.setSubject(this.getTitle());
		mail.setText(this.getMessage());
		
		mailSender.send(mail);
		
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
}
