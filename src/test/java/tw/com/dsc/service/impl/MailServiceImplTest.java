package tw.com.dsc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.service.MailService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class MailServiceImplTest {
	@Autowired
	private MailService mailService;
	@Test
	public void testMail() throws Exception {
		mailService.approval(8L);
		/*
		mailService.reject(8L);
		mailService.readyPublish(8L);
		mailService.expired(8L);
		mailService.republish(8L);
		*/
		Thread.sleep(30*1000);
	}

}
