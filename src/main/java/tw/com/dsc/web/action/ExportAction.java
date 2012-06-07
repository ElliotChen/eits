package tw.com.dsc.web.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.service.ArticleService;
import tw.com.dsc.to.ExportInfo;

import com.opensymphony.xwork2.Preparable;

@Component("exportAction")
@Scope("prototype")
public class ExportAction extends BaseAction implements Preparable {
	private static final Logger logger = LoggerFactory.getLogger(ExportAction.class);
	
	@Autowired
	private ArticleService articleService;
	
	
	private List<ExportInfo> infos;
	@Override
	public void prepare() throws Exception {
		
	}
	
	public String export() {
		this.infos = this.articleService.listProofReadArticles();
		return "exportHtml";
	}
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public List<ExportInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<ExportInfo> infos) {
		this.infos = infos;
	}

}
