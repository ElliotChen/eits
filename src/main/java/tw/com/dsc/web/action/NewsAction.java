package tw.com.dsc.web.action;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.ExportPackage;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.ExportPackageService;
import tw.com.dsc.to.ExportInfo;

import com.opensymphony.xwork2.Preparable;

@Component("newsAction")
@Scope("prototype")
public class NewsAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 3758911474494498345L;

	private static final Logger logger = LoggerFactory.getLogger(NewsAction.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ExportPackageService exportPackageService;
	
	private Page<ExportPackage> exportPackages;
	private List<ExportInfo> infos;
	
	private Integer pageNo;
	private Boolean news;
	private Date beginDate;
	private Date endDate;
	private ArticleType[] types;
	@Override
	public void prepare() throws Exception {
		if (null == pageNo) {
			pageNo = 1;
		}
	}

	public String index() {
		
		ExportPackage example = new ExportPackage();
		Page<ExportPackage> page = new Page<ExportPackage>(example);
		page.setPageNo(pageNo);
		page.setDescOrders(new String[] {"oid"});
		
		this.exportPackages = this.exportPackageService.listByPage(page);
		
		return "index";
	}
	
	public String searchExportPackage() {
		ExportPackage example = new ExportPackage();
		Page<ExportPackage> page = new Page<ExportPackage>(example);
		page.setPageNo(pageNo);
		page.setDescOrders(new String[] {"oid"});
		
		this.exportPackages = this.exportPackageService.listByPage(page);
		
		return "packageList";
	}
	
	public String export() {
		ExportPackage ep = exportPackageService.create(news, beginDate, endDate, types);
		infos = this.articleService.exportProofRead(ep.getOid(), types);
		return "export";
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public ExportPackageService getExportPackageService() {
		return exportPackageService;
	}

	public void setExportPackageService(ExportPackageService exportPackageService) {
		this.exportPackageService = exportPackageService;
	}

	public Page<ExportPackage> getExportPackages() {
		return exportPackages;
	}

	public void setExportPackages(Page<ExportPackage> exportPackages) {
		this.exportPackages = exportPackages;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArticleType[] getTypes() {
		return types;
	}

	public void setTypes(ArticleType[] types) {
		this.types = types;
	}

	public Boolean getNews() {
		return news;
	}

	public void setNews(Boolean news) {
		this.news = news;
	}

	public List<ExportInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<ExportInfo> infos) {
		this.infos = infos;
	}
	
}
