package tw.com.dsc.service.impl;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;

import tw.com.dsc.dao.AccountDao;
import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.MailService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.ApprovalMailTask;
import tw.com.dsc.task.MailTask;

/**
 * 1. Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
2. Reject Notification – Notify the agent that there is a KB article has been rejected.
3. Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
4. Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
5. Expired Notification for Agent – Notify the agent that one of his KB article already expired.
6. Republished Notification – Notify agent leaders that there is a KB article has been republished.
7. Archived Notification – Notify all members that a KB article has been set as archived.
 */

public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	private MailSender mailSender;
	private VelocityEngine velocityEngine;
	private String sender;
	
	private String allAddress;
	private TaskManager taskManager;

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private AccountDao accountDao;
	@Override
	public void approval(Long articleOid) {
		Article article = this.articleService.findByOid(articleOid);
		this.approval(article);
	}

	@Override
	public void approval(Article article) {
		logger.info("Send approval for Article[{}]", article);
		Account account = this.accountDao.findByOid(article.getEntryUser());
		MailTask task = new ApprovalMailTask(mailSender, account, systemService.findGroupLeaders(article), article);
		this.taskManager.arrangeMailTask(task);
	}

	@Override
	public void reject(Long articleOid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reject(Article article) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readyPublish(Long articleOid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readyPublish(Article article) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expired(Long articleOid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expired(Article article) {
		// TODO Auto-generated method stub

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
