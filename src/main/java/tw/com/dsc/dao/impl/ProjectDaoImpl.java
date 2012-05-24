package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ProjectDao;
import tw.com.dsc.domain.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends AbstractBaseDao<Project, String> implements ProjectDao {
	private static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

}
