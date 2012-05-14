package tw.com.dsc.task;

import org.springframework.mail.MailSender;

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
public class ApprovalMailTask extends MailTask {

	
	public ApprovalMailTask() {
		super();
	}
	
	

	public ApprovalMailTask(Long articleOid, MailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService) {
		super(articleOid, mailSender, systemService, articleService, articleLogService);
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
