package tw.com.dsc.service;

import tw.com.dsc.domain.Article;

public interface MailService {

	/**
	 * 1. Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
2. Reject Notification – Notify the agent that there is a KB article has been rejected.
3. Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
4. Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
5. Expired Notification for Agent – Notify the agent that one of his KB article already expired.
6. Republished Notification – Notify agent leaders that there is a KB article has been republished.
7. Archived Notification – Notify all members that a KB article has been set as archived.
	 */
	
	void approval(String group, Long articleOid);
	void approval(String group, Article article);
	void reject(String user, Long articleOid);
	void reject(String user, Article article);
	void readyPublish(String group, Long articleOid);
	void readyPublish(String group, Article article);
	void expired(Long articleOid);
	void expired(Article article);
	
}