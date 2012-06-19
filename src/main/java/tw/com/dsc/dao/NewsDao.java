package tw.com.dsc.dao;

import java.util.Date;
import java.util.List;

import tw.com.dsc.to.NewsUser;
import tw.com.dsc.to.PackagedArticle;

public interface NewsDao {
	List<String> findUpdateableNewsId(Date beginDate, Date endDate);
	void updateNewsPackageId(String packageId, String newsId);
	List<NewsUser> findLeaders();
	PackagedArticle findPAByNewsId(String newsId);
}
