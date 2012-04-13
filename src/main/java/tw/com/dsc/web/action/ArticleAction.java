package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.ArticleTO;
import tw.com.dsc.to.JsonMsg;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("articleAction")
@Scope("prototype")
public class ArticleAction extends ActionSupport implements Preparable, RequestAware {

	private static final long serialVersionUID = -1334862612883735089L;

	private static final Logger logger = LoggerFactory.getLogger(ArticleAction.class);
	private Map<String, Object> request;
	private ArticleTO example;
	private String articleId;
	private String ratingNumber;
	private JsonMsg jsonMsg;
	private String oid;
	private ArticleTO article;
	private ArticleTO copy;
	@Override
	public void prepare() throws Exception {
		example = new ArticleTO();
		article = new ArticleTO();
		copy = new ArticleTO();
		
		if (StringUtils.isNotEmpty(oid)) {
			article.setOid(oid);
			article.setId("artId01");
			article.setSummary("Summary XYZ");
			article.setType("General");
			article.setPublishDate(new Date());
			article.setLanguage("English");
			article.setHitCount(120);
			article.setEntryDate(new Date());
			article.setKeywords("keyword123");
			article.setQuestion("How to restore and clear rom-d on P-663 in English");
			article.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
		}
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String rating() {
		this.jsonMsg = new JsonMsg("Thanks for your rating for articleId - "+articleId+"");
		return "rating";
	}
	
	public String precopy() {
		return "copy";
	}
	
	public String index() {
		return "index";
	}
	
	public String previewCopy() {
		this.copy.setEntryUser(ThreadLocalHolder.getUser().getName());
		this.article = this.copy;
		return "preview";
	}
	
	public String copy() {
		this.oid = article.getOid();
		return "detail";
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

	public String getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(String ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public JsonMsg getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(JsonMsg jsonMsg) {
		this.jsonMsg = jsonMsg;
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

	public ArticleTO getCopy() {
		return copy;
	}

	public void setCopy(ArticleTO copy) {
		this.copy = copy;
	}
	
}
