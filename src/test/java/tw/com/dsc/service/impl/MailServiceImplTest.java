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
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MailServiceImplTest {
	@Autowired
	private MailService mailService;
	
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
}
