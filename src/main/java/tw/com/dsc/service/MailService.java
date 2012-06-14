package tw.com.dsc.service;

import tw.com.dsc.domain.Article;

/**
 * 1. Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
2. Reject Notification – Notify the agent that there is a KB article has been rejected.
3. Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
4. Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
5. Expired Notification for Agent – Notify the agent that one of his KB article already expired.
6. Republished Notification – Notify agent leaders that there is a KB article has been republished.
7. Archived Notification – Notify all members that a KB article has been set as archived.
 */
public interface MailService {

	
	
	/**
	 * WaitForApprov
	 * Approval Notification – Notify agent leaders that there is a KB article waiting for approval.
	 * @param articleOid
	 */
	void approval(Long articleOid);
	void approval(Article article);
	
	/**
	 * Reject Notification – Notify the agent that there is a KB article has been rejected.
	 * @param articleOid
	 */
	void reject(Long articleOid);
	
	/**
	 * Ready to Publish Notification – Notify agent leaders that there is a KB article has been proof-read and updated by an agent. Only for L3.
	 * @param articleOid
	 */
	void readyPublish(Long articleOid);
	void readyUpdate(Long articleOid);
	void proofread(Long articleOid);
	
	/**
	 * Expired Notification for Leader – Notify agent leaders that there is a KB article already expired.
	 * Expired Notification for Agent – Notify the agent that one of his KB article already expired.
	 * @param articleOid
	 */
	void expired(Long articleOid);
	
	/**
	 * Republished Notification – Notify agent leaders that there is a KB article has been republished.
	 * @param articleOid
	 */
	void republish(Long articleOid);
	void archived(Long articleOid);
	
}
