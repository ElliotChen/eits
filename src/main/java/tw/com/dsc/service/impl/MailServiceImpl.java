package tw.com.dsc.service.impl;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;

import tw.com.dsc.domain.Article;
import tw.com.dsc.service.MailService;
import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.ApprovalMailTask;
import tw.com.dsc.task.MailTask;

public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(VelocityEngine.class);
	private MailSender mailSender;
	private VelocityEngine velocityEngine;
	private String sender;
	
	private String allAddress;
	private TaskManager taskManager;
	@Override
	public void approval(String group, Long articleOid) {
		logger.info("Send approval for Group[{}]", group);
		MailTask task = new ApprovalMailTask(mailSender, null, null, null);
		this.taskManager.arrangeMailTask(task);
	}

	@Override
	public void approval(String group, Article article) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reject(String user, Long articleOid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reject(String user, Article article) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readyPublish(String group, Long articleOid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readyPublish(String group, Article article) {
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
