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
import tw.com.dsc.to.JsonMsg;

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
	@Override
	public void prepare() throws Exception {
		example = new ArticleTO();
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String rating() {
		this.jsonMsg = new JsonMsg("Thanks for your rating for articleId - "+articleId+"");
		return "rating";
	}
	public String index() {
		return "index";
	}

	public String detail() {
		ArticleTO article = new ArticleTO();
		article.setId(articleId);
		article.setSummary("Summary XYZ");
		article.setPublishDate(new Date());
		article.setHitCount(120);
		request.put("article", article);
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
	
}
