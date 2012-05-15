package tw.com.dsc.service.impl;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.MailService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.ApprovalMailTask;
import tw.com.dsc.task.ExpiredMailTask;
import tw.com.dsc.task.ReadyPublishMailTask;
import tw.com.dsc.task.RejectMailTask;
import tw.com.dsc.task.RepublishMailTask;

/**
 * 1. Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
2. Reject Notification – Notify the agent that there is a KB article has been rejected.
3. Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
4. Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
5. Expired Notification for Agent – Notify the agent that one of his KB article already expired.
6. Republished Notification – Notify agent leaders that there is a KB article has been republished.
7. Archived Notification – Notify all members that a KB article has been set as archived.
 */
@Component("mailService")
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	@Autowired
	private MailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${mail.sender}")
	private String sender;
	
	private String allAddress;
	@Autowired
	private TaskManager taskManager;

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ArticleLogService articleLogService;
	
	@Override
	public void approval(Long articleOid) {
		System.out.println("!-------"+sender);
		this.taskManager.arrangeMailTask(new ApprovalMailTask(articleOid, mailSender, systemService, articleService, articleLogService));
	}

	@Override
	public void reject(Long articleOid) {
		this.taskManager.arrangeMailTask(new RejectMailTask(articleOid, mailSender, systemService, articleService, articleLogService));
	}

	@Override
	public void readyPublish(Long articleOid) {
		this.taskManager.arrangeMailTask(new ReadyPublishMailTask(articleOid, mailSender, systemService, articleService, articleLogService));
	}

	@Override
	public void expired(Long articleOid) {
		this.taskManager.arrangeMailTask(new ExpiredMailTask(articleOid, mailSender, systemService, articleService, articleLogService));
	}

	@Override
	public void republish(Long articleOid) {
		this.taskManager.arrangeMailTask(new RepublishMailTask(articleOid, mailSender, systemService, articleService, articleLogService));
	}
	
	@Override
	public void archived(Long articleOid) {
		
	}
	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAllAddress() {
		return allAddress;
	}

	public void setAllAddress(String allAddress) {
		this.allAddress = allAddress;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
}
