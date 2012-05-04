package tw.com.dsc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tw.com.dsc.service.MailService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_Mail.xml"})
public class MailServiceImplTest {
	@Autowired
	private MailService mailService;
	@Test
	public void test() {
		mailService.approval("GA", 1L);
	}

}
