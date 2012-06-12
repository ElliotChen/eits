package tw.com.dsc.service;

import java.util.Date;

import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.ExportPackage;

public interface ExportPackageService extends
		BaseDomainService<ExportPackage, String> {
	ExportPackage create(Boolean news, Date beginDate, Date endDate, ArticleType[] types);
}
