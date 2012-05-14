package tw.com.dsc.task;

import java.util.List;

import org.springframework.mail.MailSender;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;

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

	public ApprovalMailTask(MailSender mailSender, Account agent, List<Account> leaders, Article article) {
		super(mailSender, agent, leaders, article);
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
