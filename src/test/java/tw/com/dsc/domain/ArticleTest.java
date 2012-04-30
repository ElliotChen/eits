package tw.com.dsc.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.test.BaseTest;
import tw.com.dsc.util.ThreadLocalHolder;

public class ArticleTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(ArticleTest.class);
	
	@Test
	public void testL3leaderAvailableStatus() {
		ThreadLocalHolder.setUser(l3leader);
		Article article = new Article();
		List<Status> availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForProofRead));
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		
		article.setAgentType(ThreadLocalHolder.getOperator().getAgentType());
		article.setStatus(Status.Draft);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		
		article.setStatus(Status.WaitForApproving);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		Assert.assertTrue(availableStatus.contains(Status.WaitForProofRead));
		
		article.setStatus(Status.WaitForProofRead);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.ReadyToUpdate));
		
		article.setStatus(Status.ReadyToUpdate);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.ReadyToPublish));
		
		article.setStatus(Status.ReadyToPublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.Published);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForRepublish));
		
		article.setStatus(Status.WaitForRepublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Archived));
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.Archived);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
	}
	
	@Test
	public void testL3userAvailableStatus() {
		ThreadLocalHolder.setUser(l3user);
		Article article = new Article();
		List<Status> availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		
		article.setAgentType(ThreadLocalHolder.getOperator().getAgentType());
		article.setStatus(Status.Draft);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		
		article.setStatus(Status.WaitForApproving);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.WaitForProofRead);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.ReadyToUpdate);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.ReadyToPublish));
		
		article.setStatus(Status.ReadyToPublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.Published);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.WaitForRepublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.Archived);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
	}

	@Test
	public void testL2leaderAvailableStatus() {
		ThreadLocalHolder.setUser(l2leader);
		Article article = new Article();
		List<Status> availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Published));
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		
		article.setAgentType(ThreadLocalHolder.getOperator().getAgentType());
		article.setStatus(Status.Draft);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Published));
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		
		article.setStatus(Status.WaitForApproving);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.WaitForProofRead);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.ReadyToUpdate);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.ReadyToPublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.Published);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForRepublish));
		
		article.setStatus(Status.WaitForRepublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Archived));
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.Archived);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
	}
	
	@Test
	public void testL2userAvailableStatus() {
		ThreadLocalHolder.setUser(l2user);
		Article article = new Article();
		List<Status> availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		Assert.assertTrue(availableStatus.contains(Status.Draft));
		
		article.setAgentType(ThreadLocalHolder.getOperator().getAgentType());
		article.setStatus(Status.Draft);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.WaitForApproving));
		
		article.setStatus(Status.WaitForApproving);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.WaitForProofRead);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.ReadyToUpdate);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.ReadyToPublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.Published);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
		
		article.setStatus(Status.WaitForRepublish);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.contains(Status.Published));
		
		article.setStatus(Status.Archived);
		availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
	}
	
	@Test
	public void testGuestAvailableStatus() {
		ThreadLocalHolder.setUser(guest);
		Article article = new Article();
		List<Status> availableStatus = article.getAvailableStatus();
		Assert.assertTrue(availableStatus.isEmpty());
	}
}
