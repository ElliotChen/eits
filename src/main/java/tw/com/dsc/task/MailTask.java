package tw.com.dsc.task;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import tw.com.dsc.domain.Article;
import tw.com.dsc.to.User;

public abstract class MailTask implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
	private MailSender mailSender;
	
	protected User agent;
	protected User leader;
	protected Article article;
	
	
	public MailTask() {
		super();
	}

	public MailTask(MailSender mailSender, User agent, User leader, Article article) {
		this.mailSender = mailSender;
		this.agent = agent;
		this.leader = leader;
		this.article = article;
	}

	@Override
	public void run() {
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
		if (null == this.leader) {
			logger.error("Leader could not be null.");
		}
		String leaderAddr = "";
		if (null != this.leader) {
			leaderAddr = this.leader.getMail();
		}
		return StringUtils.isEmpty(leaderAddr)? new String[0] : new String[] {leaderAddr};
	}
}
