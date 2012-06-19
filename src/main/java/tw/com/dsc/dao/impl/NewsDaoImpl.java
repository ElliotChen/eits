package tw.com.dsc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.NewsDao;
import tw.com.dsc.to.NewsUser;
import tw.com.dsc.to.PackagedArticle;

@Repository("newDao")
public class NewsDaoImpl implements NewsDao{
	private static final String SELECT_TARGET_IDS = "select n.news_id as nid from user u, news n where n.news_agent=u.user_id and u.user_disabled !=1 and news_status_id='2' and news_disabled ='0' and news_export_id='0' and news_last_update between ? and ?";
	private static final String UPDATE_PACKAGE_ID = "update news set news_last_update=NOW() , news_status_id='6', news_export_id = ? where news_id = ?";
	private static final String SELECT_LEADER = "SELECT user_id, user_name, user_login, user_team_id FROM user WHERE user_level_id =3 AND user_id !=63";
	private static final String SELECT_FW_STATUS = "select n.news_subject,u.user_name,n.news_id,s.kb_status_name from news n, user u, knowledgebase_status s where n.news_status_id =s.kb_status_id and n.news_agent =u.user_id and n.news_id = ?"; //order by u.user_id, n.news_id
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<String> findUpdateableNewsId(Date beginDate, Date endDate) {
		return this.jdbcTemplate.queryForList(SELECT_TARGET_IDS,String.class, beginDate, endDate);
	}
	
	public void updateNewsPackageId(String packageId, String newsId) {
		this.jdbcTemplate.update(UPDATE_PACKAGE_ID, packageId, newsId);
	}
	public List<NewsUser> findLeaders() {
		return this.jdbcTemplate.query(SELECT_LEADER, new NewsUserMapper());
	}
	
	public PackagedArticle findPAByNewsId(String newsId) {
		List<PackagedArticle> query = this.jdbcTemplate.query(SELECT_FW_STATUS, new Object[]{newsId}, new PackagedArticleMapper());
		return query.isEmpty()?null:query.get(0);
	}
	
}

class PackagedArticleMapper implements RowMapper<PackagedArticle> {

	@Override
	public PackagedArticle mapRow(ResultSet rs, int rowNum) throws SQLException {
		PackagedArticle pa = new PackagedArticle();
		pa.setSummary(rs.getString(1));
		pa.setAgent(rs.getString(2));
		pa.setArticleId(rs.getString(3));
		return pa;
	}
	
}

class NewsUserMapper implements RowMapper<NewsUser> {

	@Override
	public NewsUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsUser user = new NewsUser();
		user.setId(rs.getString(1));
		user.setName(rs.getString(2));
		user.setTeamId(rs.getString(3));
		return user;
	}
	
}