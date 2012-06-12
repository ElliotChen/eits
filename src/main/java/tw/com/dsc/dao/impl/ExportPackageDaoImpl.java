package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ExportPackageDao;
import tw.com.dsc.domain.ExportPackage;

@Repository("exportPackageDao")
public class ExportPackageDaoImpl extends AbstractBaseDao<ExportPackage, String>
		implements ExportPackageDao {
	private static final Logger logger = LoggerFactory.getLogger(ExportPackageDaoImpl.class);
	@Override
	public Logger getLogger() {
		return logger;
	}

	
}
