package tw.com.dsc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Article;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.MailService;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.ApprovalMailTask;
import tw.com.dsc.task.ArchivedMailTask;
import tw.com.dsc.task.ExpiredMailTask;
import tw.com.dsc.task.ProofreadMailTask;
import tw.com.dsc.task.ReadyPublishMailTask;
import tw.com.dsc.task.ReadyUpdateMailTask;
import tw.com.dsc.task.RejectMailTask;
import tw.com.dsc.task.RejectUpdateMailTask;
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
	private JavaMailSender javaMailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${mail.sender}")
	private String sender;
	@Value("${mail.allGroup}")
	private String allGroup;
	
	@Value("${server.url}")
	private String serverUrl;
	
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
		this.taskManager.arrangeMailTask(new ApprovalMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}
	
	@Override
	public void approval(Article article) {
		this.taskManager.arrangeMailTask(new ApprovalMailTask(article, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}

	@Override
	public void reject(Long articleOid) {
		this.taskManager.arrangeMailTask(new RejectMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}

	@Override
	public void readyPublish(Long articleOid) {
		this.taskManager.arrangeMailTask(new ReadyPublishMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}
	
	@Override
	public void proofread(Long articleOid) {
		this.taskManager.arrangeMailTask(new ProofreadMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}
	
	@Override
	public void readyUpdate(Long articleOid) {
		this.taskManager.arrangeMailTask(new ReadyUpdateMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}

	@Override
	public void rejectToUpdate(Long articleOid) {
		this.taskManager.arrangeMailTask(new RejectUpdateMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}
	@Override
	public void expired(Long articleOid) {
		this.taskManager.arrangeMailTask(new ExpiredMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}

	@Override
	public void republish(Long articleOid) {
		this.taskManager.arrangeMailTask(new RepublishMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, serverUrl));
	}
	
	@Override
	public void archived(Long articleOid) {
		if (StringUtils.isEmpty(allGroup)) {
			logger.error("Please check configuration mail.allGroup in system.propertis");
			return;
		}
		this.taskManager.arrangeMailTask(new ArchivedMailTask(articleOid, javaMailSender, systemService, articleService, articleLogService, sender, velocityEngine, allGroup, serverUrl));
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

	public String getAllGroup() {
		return allGroup;
	}

	public void setAllGroup(String allGroup) {
		this.allGroup = allGroup;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public ArticleLogService getArticleLogService() {
		return articleLogService;
	}

	public void setArticleLogService(ArticleLogService articleLogService) {
		this.articleLogService = articleLogService;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
}
