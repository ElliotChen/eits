package tw.com.dsc.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.SystemService;

/**
 * Reject Notification – Notify the agent that there is a KB article has been rejected.
 * @author elliot
 *
 */
public class RejectMailTask extends MailTask {

	
	public RejectMailTask() {
		super();
	}

	public RejectMailTask(Long articleOid, JavaMailSender mailSender,
			SystemService systemService, ArticleService articleService,
			ArticleLogService articleLogService, String sender, VelocityEngine velocityEngine) {
		super(articleOid, mailSender, systemService, articleService, articleLogService, sender, velocityEngine);
	}

	@Override
	public String[] getReceivers() {
		return new String[] {this.agent.getEmail()};
	}

	@Override
	public String[] getCcReceivers() {
		return this.getLeaders();
	}

	@Override
	public String getTitle() {
		return "KB article["+this.article.getArticleId().getOid()+"] has been rejected";
	}

	@Override
	public String getMessage() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("articleOid", String.valueOf(this.article.getOid()));
		map.put("summary", this.article.getSummary());
		map.put("entryUser", this.article.getEntryUser());
		
		ArticleLog log = this.articleLogService.getLatestRejectReason(this.article.getOid());
		if (null != log) {
			map.put("reason", log.getMessage());
		} else {
			map.put("reason", "Unknow");
		}
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/reject.vm", map);
		return message;
	}

}
