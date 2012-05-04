package tw.com.dsc.task;

import org.springframework.mail.MailSender;

import tw.com.dsc.domain.Article;
import tw.com.dsc.to.User;

/**
 * Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
 * 
 * Receiver : Leaders
 * @author elliot
 *
 */
public class ApprovalMailTask extends MailTask {

	
	public ApprovalMailTask() {
		super();
	}

	public ApprovalMailTask(MailSender mailSender, User agent, User leader, Article article) {
		super(mailSender, agent, leader, article);
	}

	@Override
	public String[] getReceivers() {
		return this.getLeaders();
	}

	@Override
	public String[] getCcReceivers() {
		return null;
	}

	@Override
	public String getTitle() {
		return "Please Approval Article";
	}

	@Override
	public String getMessage() {
		return "Article is .....";
	}

}
