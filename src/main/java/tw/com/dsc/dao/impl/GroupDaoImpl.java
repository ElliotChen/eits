package tw.com.dsc.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.GroupDao;
import tw.com.dsc.domain.Group;

@Repository("groupDao")
public class GroupDaoImpl extends AbstractBaseDao<Group, String> implements GroupDao {
	private static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}


	@Override
	public void create(Group entity) {
	}

	@Override
	public void update(Group entity) {
	}

	@Override
	public void delete(Group entity) {
	}
	
	@Override
	public void merge(Group entity) {
	}
	
}
