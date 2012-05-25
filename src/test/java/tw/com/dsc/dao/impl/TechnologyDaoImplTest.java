package tw.com.dsc.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.TechnologyDao;
import tw.com.dsc.domain.Technology;
import tw.com.dsc.domain.TechnologyItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@Transactional
public class TechnologyDaoImplTest {
	private static final Logger logger = LoggerFactory.getLogger(TechnologyDaoImplTest.class);
	@Autowired
	private TechnologyDao technologyDao;
	@Test
	public void testListAll() {
		for (Technology tech : technologyDao.listAll()) {
			logger.debug("Technology name : {}", tech.getTechnology());
			for (TechnologyItem item: tech.getItems()) {
			//	logger.debug("	Technology Item name : {}", item.getName());
			}
		}
	}

}
