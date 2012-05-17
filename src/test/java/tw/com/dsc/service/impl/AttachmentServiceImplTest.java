package tw.com.dsc.service.impl;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class AttachmentServiceImplTest {
	@Autowired
	private AttachmentServiceImpl service;
	@Test
	public void testUploadImage() {
		service.uploadImage("/eits", new File("/Users/elliot/Desktop/test.txt"), "dsc_account.txt", "TXT");
	}

}
