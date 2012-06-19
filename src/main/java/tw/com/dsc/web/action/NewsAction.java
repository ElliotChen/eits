package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.ExportPackage;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.OperationEnum;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.domain.support.SimpleCondition;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.ExportPackageService;
import tw.com.dsc.to.ExportInfo;
import tw.com.dsc.to.PackagedArticle;
import tw.com.dsc.util.ThreadLocalHolder;

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
	private Page<ExportPackage> closedPackages;
	private List<ExportInfo> infos;
	private List<PackagedArticle> packagedArticle;
	private Integer pageNo;
	private Boolean news;
	private Date beginDate;
	private Date endDate;
	private ArticleType[] types;
	private String[] epOids;
	private String epOid;
	private List<String> sels;
	@Override
	public void prepare() throws Exception {
		if (null == pageNo) {
			pageNo = 1;
		}
	}

	public String index() {
		if (!ThreadLocalHolder.getOperator().isL3Admin()) {
			return "redirect";
		}
		this.searchExportPackage();
		this.searchClosedPackage();
		
		return "index";
	}
	
	public String searchExportPackage() {
		ExportPackage example = new ExportPackage();
		example.setClosed(Boolean.FALSE);
		Page<ExportPackage> page = new Page<ExportPackage>(example);
		page.setPageNo(pageNo);
		page.setDescOrders(new String[] {"oid"});
		
		this.exportPackages = this.exportPackageService.listByPage(page);
		
		return "packageList";
	}
	
	public String searchClosedPackage() {
		ExportPackage example = new ExportPackage();
		example.setClosed(Boolean.TRUE);
		Page<ExportPackage> page = new Page<ExportPackage>(example);
		page.setPageNo(pageNo);
		page.setDescOrders(new String[] {"oid"});
		
		this.closedPackages = this.exportPackageService.listByPage(page);
		
		return "closedList";
	}
	
	public String preExport() {
		infos = this.articleService.searchForProofRead(news, beginDate, endDate, types);
		return "export";
	}
	public String export() {
		ExportPackage ep = exportPackageService.create(news, beginDate, endDate, types);
		infos = this.articleService.exportProofRead(ep.getOid(), types);
		this.addActionMessage("Export Finished. Please reload ZyTech News for more details.");
		return "exportRes";
	}
	
	public String viewPackage() {
		infos = this.articleService.viewExportPackage(epOid);
		return "export";
	}
	public String updateProofRead() {
		for (String epOid : this.epOids) {
			this.articleService.readyUpdate(epOid);
		}
		this.addActionMessage("Update status success.");
		return this.searchExportPackage();
	}

	public String viewPackageStatus() {
		packagedArticle = this.articleService.findPAByExportPackage(epOid);
		return "articleStatus";
	}
	
	public String gennews() {
		ExportPackage ep = null;
		this.sels = new ArrayList<String>();
		if (null != this.epOids) {
			for (String oid : epOids) {
				ep = this.exportPackageService.findByOid(oid);
				if (null != ep) {
					if (StringUtils.isNotEmpty(ep.getOidList())) {
						sels.add("0_"+ep.getOidList());
					}
					if (StringUtils.isNotEmpty(ep.getNewsIdList())) {
						sels.add("1_"+ep.getNewsIdList());
					}
				}
			}
		}
		return "gennews";
	}
	
	public String viewFirmwareStatus() {
		packagedArticle = this.articleService.findFWPAByExportPackage(epOid);
		return "firmwareStatus";
	}
	public boolean getExportable() {
		return StringUtils.isEmpty(this.epOid);
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

	public String[] getEpOids() {
		return epOids;
	}

	public void setEpOids(String[] epOids) {
		this.epOids = epOids;
	}

	public String getEpOid() {
		return epOid;
	}

	public void setEpOid(String epOid) {
		this.epOid = epOid;
	}

	public Page<ExportPackage> getClosedPackages() {
		return closedPackages;
	}

	public void setClosedPackages(Page<ExportPackage> closedPackages) {
		this.closedPackages = closedPackages;
	}

	public List<PackagedArticle> getPackagedArticle() {
		return packagedArticle;
	}

	public void setPackagedArticle(List<PackagedArticle> packagedArticle) {
		this.packagedArticle = packagedArticle;
	}

	public List<String> getSels() {
		return sels;
	}

	public void setSels(List<String> sels) {
		this.sels = sels;
	}
	
}
