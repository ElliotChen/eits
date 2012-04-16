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
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("searchArticleAction")
@Scope("prototype")
public class SearchArticleAction extends ActionSupport implements Preparable, RequestAware {

	private static final long serialVersionUID = -2112640721474475257L;
	private static final Logger logger = LoggerFactory.getLogger(SearchArticleAction.class);
	private Map<String, Object> request;
	private ArticleTO example;
	private String articleId;
	private String oid;
	private ArticleTO article;

	private String message;

	@Override
	public void prepare() throws Exception {
		example = new ArticleTO();
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

		return "list";
	}

	public String detail() {
		article = new ArticleTO();
		if ("1".equals(this.oid) || "2".equals(this.oid)) {
			article.setOid(oid);
			article.setId("artId01");
			article.setSummary("Summary XYZ");
			article.setPublishDate(new Date());
			article.setLanguage("English");
			article.setHitCount(120);

			article.setQuestion("How to restore and clear rom-d on P-663 in English");
			article.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
		} else {
			article.setOid(oid);
			article.setId("artId01");
			article.setSummary("Summary XYZ");
			article.setPublishDate(new Date());
			article.setLanguage("Chinese");
			article.setHitCount(120);

			article.setQuestion("如何回復與清除P-663的ROM ? Chinese");
			article.setAnswer("你知道這是中文就好！");
		}
		return "detail";
	}

	private void mockArticles(final ArrayList<ArticleTO> list) {
		ArticleTO a1 = new ArticleTO();
		a1.setOid("1");
		a1.setId("19230");
		a1.setSummary("All in!");
		a1.setPublishDate(new Date());
		a1.setHitCount(271);
		a1.setLanguage("EN");

		ArticleTO a2 = new ArticleTO();
		a2.setOid("2");
		a2.setId("15490");
		a2.setSummary("Don't do this!");
		a2.setPublishDate(new Date());
		a2.setHitCount(157);
		a1.setLanguage("EN");

		list.add(a1);
		list.add(a2);
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public ArticleTO getExample() {
		return example;
	}

	public void setExample(ArticleTO example) {
		this.example = example;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public ArticleTO getArticle() {
		return article;
	}

	public void setArticle(ArticleTO article) {
		this.article = article;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}
}
