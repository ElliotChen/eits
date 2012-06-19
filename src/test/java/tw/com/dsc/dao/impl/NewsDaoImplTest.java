package tw.com.dsc.dao.impl;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@Transactional
public class NewsDaoImplTest {
	private static final Logger logger = LoggerFactory.getLogger(NewsDaoImplTest.class);
	@Autowired
	private NewsDaoImpl dao;
	@Test
	public void test() {
		List<String> ids = dao.findUpdateableNewsId(DateUtils.pareseDate("2008/05/01"), DateUtils.pareseDate("2008/10/01"));
		for (String id : ids) {
			logger.debug(id);
		}
	}

	@Test
	public void testUpdate() {
		dao.updateNewsPackageId("999", "514");
	}
}
