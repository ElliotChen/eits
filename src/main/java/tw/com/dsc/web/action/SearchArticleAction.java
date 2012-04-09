package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.ArticleTO;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("searchArticle")
@Scope("prototype")
public class SearchArticleAction extends ActionSupport implements Preparable, RequestAware {

	private static final long serialVersionUID = -2112640721474475257L;
	private static final Logger logger = LoggerFactory.getLogger(SearchArticleAction.class);
	private Map<String, Object> request;
	@Override
	public void prepare() throws Exception {

	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String index() {
		return "index";
	}
	
	public String search() {
		logger.error("Do Search");
		ArrayList<ArticleTO> faqArticles = new ArrayList<ArticleTO>();
		
		ArrayList<ArticleTO> latestArticles = new ArrayList<ArticleTO>();
		
		this.mockArticles(faqArticles);
		this.mockArticles(latestArticles);
		
		request.put("faqArticles", faqArticles);
		request.put("latestArticles", latestArticles);
		
		return "articles";
	}
	
	private void mockArticles(final ArrayList<ArticleTO> list) {
		ArticleTO a1 = new ArticleTO();
		a1.setId("19230");
		a1.setSummary("All in!");
		a1.setPublishDate(new Date());
		a1.setHitCount(271);
		
		ArticleTO a2 = new ArticleTO();
		a2.setId("15490");
		a2.setSummary("Don't do this!");
		a2.setPublishDate(new Date());
		a2.setHitCount(157);
		
		list.add(a1);
		list.add(a2);
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
