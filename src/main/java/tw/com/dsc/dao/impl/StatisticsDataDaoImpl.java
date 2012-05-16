package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.StatisticsDataDao;
import tw.com.dsc.domain.StatisticsData;

@Repository("statisticsDataDao")
public class StatisticsDataDaoImpl extends AbstractBaseDao<StatisticsData, Long> implements
		StatisticsDataDao {
	private static final Logger logger = LoggerFactory.getLogger(StatisticsDataDaoImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}

	

}
