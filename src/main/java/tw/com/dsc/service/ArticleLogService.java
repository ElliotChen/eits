package tw.com.dsc.service;

import java.util.List;

import tw.com.dsc.domain.ArticleLog;

public interface ArticleLogService extends BaseDomainService<ArticleLog, Long>{
	List<ArticleLog> findByArticleOid(Long articleOid);
}
