package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.com.dsc.domain.Account;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.service.MailService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MailServiceImplTest {
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Ignore
	@Test
	public void testMail() throws Exception {
		mailService.readyUpdate(1L);
		/*
		mailService.approval(1L);
		mailService.reject(1L);
		mailService.readyPublish(1L);
		*/
		/*
		mailService.republish(4L);
		mailService.archived(3L);
		mailService.reject(8L);
		mailService.readyPublish(8L);
		mailService.expired(8L);
		mailService.republish(8L);
		*/
		Thread.sleep(30*1000);
	}
	/*
	@Test
	public void testZyxelMail() {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setReplyTo("tasker@zyxel.com.tw");
		mail.setFrom("tasker@zyxel.com.tw");
		mail.setTo("chenensign@gmail.com");
		mail.setSubject("zyxel");
		mail.setText("TT");
		this.javaMailSender.send(mail);
	}
	*/
	
	@Test
	public void testVelocity() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Article> kbs = new ArrayList<Article>();
		List<Article> news = new ArrayList<Article>();
		
		Account receiver = new Account();
		receiver.setName("REC");
		
		Article a1 = new Article();
		a1.setOid(1l);
		a1.setArticleId(new ArticleId("001"));
		a1.setNews(Boolean.FALSE);
		a1.setSummary("a1_123");
		a1.setEntryUserName("a1n");
		a1.setEntryUser("a1e");
		a1.setEntryDate(new Date());
		
		Article a2 = new Article();
		a2.setOid(2l);
		a2.setArticleId(new ArticleId("002"));
		a2.setNews(Boolean.FALSE);
		a2.setSummary("a2_123");
		a2.setEntryUserName("a2n");
		a2.setEntryUser("a2e");
		a2.setEntryDate(new Date());
		
		Article a3 = new Article();
		a3.setOid(3l);
		a3.setArticleId(new ArticleId("003"));
		a3.setNews(Boolean.TRUE);
		a3.setSummary("a3_123");
		a3.setEntryUserName("a3n");
		a3.setEntryUser("a3e");
		a3.setEntryDate(new Date());
		
		Article a4 = new Article();
		a4.setOid(4l);
		a4.setArticleId(new ArticleId("004"));
		a4.setNews(Boolean.TRUE);
		a4.setSummary("a4_123");
		a4.setEntryUserName("a4n");
		a4.setEntryUser("a4e");
		a4.setEntryDate(new Date());
		
		kbs.add(a1);
		kbs.add(a2);
		
		news.add(a3);
		news.add(a4);
		
		map.put("receiver", receiver);
		map.put("server", "localhost");
		map.put("kbs", kbs);
		map.put("news", news);
		
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail/packageReadAgent.vm", map);
		System.out.println(message);
	}
}
