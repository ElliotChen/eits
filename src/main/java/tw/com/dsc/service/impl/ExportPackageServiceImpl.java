package tw.com.dsc.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.ExportPackageDao;
import tw.com.dsc.dao.NewsDao;
import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.ExportPackage;
import tw.com.dsc.service.ExportPackageService;

@Service("exportPackageService")
@Transactional(readOnly=true)
public class ExportPackageServiceImpl extends
		AbstractDomainService<ExportPackageDao, ExportPackage, String> implements ExportPackageService {
	private static final Logger logger = LoggerFactory.getLogger(ExportPackageServiceImpl.class);
	@Autowired
	private ExportPackageDao dao;
	
	@Autowired
	private NewsDao newsDao;
	@Autowired
	@Qualifier("exportIdSeq")
	private DataFieldMaxValueIncrementer incrementer;
	@Override
	public ExportPackageDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	public DataFieldMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	@Transactional(readOnly=false)
	public ExportPackage create(Boolean news, Date beginDate, Date endDate, ArticleType[] types) {
		StringBuilder sb = new StringBuilder();
		if (null != types && 0 < types.length) {
			sb.append(types[0]);
			for (int i = 1; i < types.length; i++) {
				sb.append(","+types[i]);
			}
		}
		String nextId = this.incrementer.nextStringValue();
		logger.debug("Get new Id[{}] for ExportPackage", nextId);
		ExportPackage ep = new ExportPackage();
		ep.setOid(nextId);
		ep.setNews(news);
		ep.setBeginDate(beginDate);
		ep.setEndDate(endDate);
		ep.setClosed(Boolean.FALSE);
		ep.setArticleTypes(sb.toString());
		this.dao.create(ep);
		return ep;
	}
	/*
	@Transactional(readOnly=false)
	public String updateNewsForEP(String epOid, Date beginDate, Date endDate) {
		List<String> newsIds = this.newsDao.findUpdateableNewsId(beginDate, endDate);
		for (String newsId : newsIds) {
			this.newsDao.updateNewsPackageId(epOid, newsId);
		}
		
		StringBuilder sb = new StringBuilder();
		if (!newsIds.isEmpty()) {
			sb.append(newsIds.get(0));
			for (int i = 1; i < newsIds.size(); i++) {
				sb.append(","+newsIds.get(i));
			}
		}
		return sb.toString();
	}
	*/
}
