package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.TechnologyDao;
import tw.com.dsc.domain.Technology;

@Repository("technologyDao")
public class TechnologyDaoImpl extends AbstractBaseDao<Technology, String> implements TechnologyDao {
	private static final Logger logger = LoggerFactory.getLogger(TechnologyDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

}
