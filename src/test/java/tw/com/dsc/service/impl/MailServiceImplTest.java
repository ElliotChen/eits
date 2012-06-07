package tw.com.dsc.service.impl;

import javax.mail.internet.MimeMessage;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.service.MailService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_Mail_Test.xml"})
public class MailServiceImplTest {
	
	private MailService mailService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Ignore
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
}
