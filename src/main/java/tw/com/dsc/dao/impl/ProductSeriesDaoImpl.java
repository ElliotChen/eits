package tw.com.dsc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.ProductSeriesDao;
import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.domain.ProductSeries;
import tw.com.dsc.to.SeriesModel;

@Repository("productSeriesDao")
public class ProductSeriesDaoImpl implements ProductSeriesDao, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ProductSeriesDaoImpl.class);
	
	private static final String L3_SERIES_ID = "SELECT ID, SERIES_NAME FROM EITS_SYS_PRODUCT_SERIES WHERE ID = ?";
	private static final String L3_SERIES = "SELECT ID, SERIES_NAME FROM EITS_SYS_PRODUCT_SERIES";
	private static final String L3_MODELS = "SELECT t.ID, t.MODEL_NAME from eits_sys_product_model t where NVL(END_OF_CSO_DATE, '3000/01/01') >= TO_CHAR(sysdate, 'YYYY/MM/DD') And t.SERIES_ID = ? and is_show ='Y'  order by t.MODEL_NAME asc";
	private static final String L3_ALL_MODELS = "SELECT t.ID, t.MODEL_NAME from eits_sys_product_model t where NVL(END_OF_CSO_DATE, '3000/01/01') >= TO_CHAR(sysdate, 'YYYY/MM/DD') and is_show ='Y'  order by t.MODEL_NAME asc";
	private static final String L3_BRANCH_SERIES = "SELECT DISTINCT PS.ID, PS.SERIES_NAME FROM EITS_SYS_PRODUCT_MODEL_SETUP PMS INNER JOIN EITS_SYS_PRODUCT_MODEL PM ON (PMS.ID = PM.ID) INNER JOIN EITS_SYS_PRODUCT_SERIES PS ON (PM.SERIES_ID = PS.ID)  where PMS.BRANCH_CODE = ? And NVL(PM.END_OF_CSO_DATE, '3000/01/01') >= TO_CHAR(sysdate, 'YYYY/MM/DD') order by PS.SERIES_NAME asc";
	private static final String L3_BRANCH_MODELS = "SELECT PM.ID, PM.MODEL_NAME FROM EITS_SYS_PRODUCT_MODEL PM, EITS_SYS_PRODUCT_MODEL_SETUP PMS WHERE PM.ID = PMS.ID AND NVL(PM.END_OF_CSO_DATE, '3000/01/01') >= TO_CHAR(sysdate, 'YYYY/MM/DD') AND PMS.BRANCH_CODE = ? AND PM.SERIES_ID = ? order by PM.MODEL_NAME ASC";
	private static final String L3_SERIES_BY_PROJECT_CODE = "SELECT m.ID, m.MODEL_NAME, s.ID as seriesId, s.SERIES_NAME , pd.project_code FROM eits_sys_product_model m, EITS_SYS_PRODUCT_SERIES s, EITS_SYS_PROJECT_DETAIL pd WHERE NVL (m.END_OF_CSO_DATE, '3000/01/01') >= TO_CHAR (SYSDATE, 'YYYY/MM/DD') and m.is_show ='Y' and m.ID = pd.PRODUCT_MODEL_ID and m.SERIES_ID = s.ID and pd.project_code = ? order by s.SERIES_NAME ASC";
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	@Override
	public ProductSeries findBySeriesId(String seriesId) {
		List<ProductSeries> list = jdbcTemplate.query(L3_SERIES_ID, new Object[] {seriesId}, new SeriesMapper());
		return list.isEmpty()?null : list.get(0);
	}
	
	@Override
	public List<ProductSeries> listAllSeries() {
		List<ProductSeries> list = jdbcTemplate.query(L3_SERIES, new SeriesMapper());
		return list;
	}
	
	@Override
	public List<ProductModel> listAllModels() {
		List<ProductModel> list = jdbcTemplate.query(L3_ALL_MODELS, new ModelMapper());
		return list;
	}

	@Override
	public List<ProductModel> listModels(String seriesId) {
		return jdbcTemplate.query(L3_MODELS, new Object[] {seriesId}, new ModelMapper());
	}
	
	@Override
	public List<ProductSeries> listSeries(String branchCode) {
		return jdbcTemplate.query(L3_BRANCH_SERIES, new Object[] {branchCode}, new SeriesMapper());
	}
	
	@Override
	public List<ProductModel> listModels(String seriesId, String brahchCode) {
		return jdbcTemplate.query(L3_BRANCH_MODELS, new Object[] {brahchCode, seriesId}, new ModelMapper());
	}
	
	@Override
	public List<ProductSeries> listSeriesByProjectCode(String projectCode) {
		List<SeriesModel> sms = jdbcTemplate.query(L3_SERIES_BY_PROJECT_CODE, new Object[] {projectCode}, new SeriesModelMapper());
		logger.debug("Find ProductSeries[{}] for ProjectCode[{}]", sms.size(), projectCode);
		ProductSeries lastSeries = null;
		if (!sms.isEmpty()) {
			lastSeries = new ProductSeries();
			SeriesModel sm = sms.get(0);
			lastSeries.setId(sm.getSeriesId());
			lastSeries.setName(sm.getSeriesName());
			List<ProductModel> mods = new ArrayList<ProductModel>();
			ProductModel mod = new ProductModel();
			mod.setOid(sm.getModelId());
			mod.setName(sm.getModelName());
			mods.add(mod);
			
			lastSeries.setModels(mods);
		}
		List<ProductSeries> series = new ArrayList<ProductSeries>();
		for (SeriesModel sm : sms) {
			if (null == lastSeries || !sm.getSeriesId().equals(lastSeries.getId())) {
				lastSeries = new ProductSeries();
				lastSeries.setId(sm.getSeriesId());
				lastSeries.setName(sm.getSeriesName());
				List<ProductModel> mods = new ArrayList<ProductModel>();
				ProductModel mod = new ProductModel();
				mod.setOid(sm.getModelId());
				mod.setName(sm.getModelName());
				mods.add(mod);
				
				series.add(lastSeries);
			} else {
				ProductModel mod = new ProductModel();
				mod.setOid(sm.getModelId());
				mod.setName(sm.getModelName());
				lastSeries.getModels().add(mod);
			}
		}
		logger.debug("Parse ProductSeries[{}] for ProjectCode[{}]", series.size(), projectCode);
		return series;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
}

class SeriesMapper implements RowMapper<ProductSeries> {
	@Override
	public ProductSeries mapRow(ResultSet rs, int index) throws SQLException {
		ProductSeries entity = new ProductSeries();
//		entity.setOid(rs.getString(1));
		entity.setId(rs.getString(1));
		entity.setName(rs.getString(2));
		return entity;
	}
}

class ModelMapper implements RowMapper<ProductModel> {
	@Override
	public ProductModel mapRow(ResultSet rs, int index) throws SQLException {
		ProductModel entity = new ProductModel();
		entity.setOid(rs.getString(1));
		entity.setName(rs.getString(2));
		return entity;
	}
}

class SeriesModelMapper implements RowMapper<SeriesModel> {
	@Override
	public SeriesModel mapRow(ResultSet rs, int index) throws SQLException {
		SeriesModel entity = new SeriesModel();
		entity.setModelId(rs.getString(1));
		entity.setModelName(rs.getString(2));
		entity.setSeriesId(rs.getString(3));
		entity.setSeriesName(rs.getString(4));
		return entity;
	}
}