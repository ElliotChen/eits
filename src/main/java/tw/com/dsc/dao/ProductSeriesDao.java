package tw.com.dsc.dao;

import java.util.List;

import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.domain.ProductSeries;

public interface ProductSeriesDao {
	List<ProductSeries> listAllSeries();
	List<ProductSeries> listSeries(String branchCode);
	List<ProductModel> listModels(String seriesId);
	List<ProductModel> listModels(String seriesId, String branchCode);
}
